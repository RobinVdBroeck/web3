package ui;

import db.DbException;
import domain.DomainException;
import domain.Person;
import domain.Product;
import domain.ShopService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static db.Configuration.*;

@WebServlet("/Controller")
public class WebshopServlet extends HttpServlet {
    private Connection connection;
    private ShopService shopService;

    @Override
    public void init() {
        // Create the connection
        Properties properties = new Properties();
        String url = "jdbc:postgresql://gegevensbanken.khleuven.be:51617/2TX31?currentSchema=r0653517_test";
        properties.setProperty("user", DB_USERNAME);
        properties.setProperty("password", DB_PASSWORD);
        properties.setProperty("ssl", "true");
        properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");

        try {
            connection = DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new DbException(e);
        }

        shopService = new ShopService(connection);
    }

    @Override
    public void destroy() {
        try {
            connection.close();
            super.destroy();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String action = req.getParameter("action");
        if (action == null) {
            index(req, res);
        } else {
            switch (action) {
                case "users":
                    users(req, res);
                    break;
                case "products":
                    products(req, res);
                    break;
                case "addProduct":
                    addProductGet(req, res);
                    break;
                case "signUp":
                    signUpGet(req, res);
                    break;
                case "updateProduct":
                    updateProductGet(req, res);
                    break;
            }
        }
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String action = req.getParameter("action");
        if (action == null) {
            // 404?
            index(req, res);
        } else {
            switch (action) {
                case "signUp":
                    signUpPost(req, res);
                    break;
                case "addProduct":
                    addProductPost(req, res);
                    break;
                case "updateProduct":
                    updateProductPost(req, res);
                    break;
            }
        }
    }


    private void index(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, res);
    }

    private void users(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final RequestDispatcher dispatcher = req.getRequestDispatcher("users.jsp");
        req.setAttribute("users", shopService.getPersons());
        dispatcher.forward(req, res);
    }

    private void products(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final RequestDispatcher dispatcher = req.getRequestDispatcher("products.jsp");
        req.setAttribute("products", shopService.getProducts());
        dispatcher.forward(req, res);
    }

    private void signUpGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final RequestDispatcher dispatcher = req.getRequestDispatcher("signUp.jsp");
        dispatcher.forward(req, res);
    }

    private void signUpPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String userid = req.getParameter("userid");
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");
        final String firstName = req.getParameter("firstName");
        final String lastName = req.getParameter("lastName");

        try {
            final Person newUser = new Person(userid, email, password, firstName, lastName);
            shopService.addPerson(newUser);
        } catch (IllegalArgumentException | DbException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("userid", userid);
            req.setAttribute("email", email);
            req.setAttribute("password", password);
            req.setAttribute("firstName", firstName);
            req.setAttribute("lastName", lastName);
            signUpGet(req, res);
        }

        index(req, res);
    }

    private void addProductGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final RequestDispatcher dispatcher = req.getRequestDispatcher("addProduct.jsp");
        dispatcher.forward(req, res);
    }

    private void addProductPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String name = req.getParameter("name");
        final String description = req.getParameter("description");
        final String priceString = req.getParameter("price");

        try {
            if (priceString.isEmpty()) {
                throw new DomainException("Price cannot be empty");
            }
            final double price = Double.parseDouble(priceString);
            final Product newProduct = new Product(name, description, price);
            shopService.addProduct(newProduct);
        } catch (NumberFormatException | DomainException | DbException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("name", name);
            req.setAttribute("description", description);
            req.setAttribute("price", priceString);
            addProductGet(req, res);
        }

        products(req, res);
    }

    private void updateProductGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String id = req.getParameter("id");
        final Product product = shopService.getProduct(Integer.parseInt(id));
        req.setAttribute("product", product);

        final RequestDispatcher dispatcher = req.getRequestDispatcher("updateProduct.jsp");
        dispatcher.forward(req, res);
    }

    private void updateProductPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get the parameters
        final String name = req.getParameter("name");
        final String description = req.getParameter("description");
        final String priceString = req.getParameter("price");
        final String id = req.getParameter("id");

        // Create a new product
        final Product product = new Product(name, description, Double.parseDouble(priceString));
        product.setId(Integer.parseInt(id));

        // Update the product in the database
        shopService.updateProduct(product);

        products(req, res);
    }
}

package ui;

import db.DbException;
import domain.DomainException;
import domain.Person;
import domain.Product;
import domain.ShopService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/Controller")
public class WebshopServlet extends HttpServlet {
    private Connection connection;
    private ShopService shopService;

    @Override
    public void init() throws ServletException {
        super.init();

        // Set up the properties
        ServletContext context = getServletContext();
        Properties properties = new Properties();
        // Load the properties from web.xml
        List<String> keys = Arrays.asList("user", "password", "ssl", "sslfactory", "url", "currentSchema");
        for (String key : keys) {
            String value = context.getInitParameter(key);
            if (value == null) {
                throw new DomainException("Property with name " + key + " does not exist");
            }
            properties.setProperty(key, value);
        }

        // Create the connection
        try {
            connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        } catch (SQLException e) {
            throw new DbException(e);
        }

        // Create the service
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
        processRequest(req.getParameter("action"), req, res);
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req.getParameter("action"), req, res);
    }

    private void processRequest(String action, HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        if (action == null) {
            action = "indexGet";
        }
        req.setAttribute("action", action);

        switch (action) {
            case "indexGet":
                indexGet(req, res);
                break;
            case "usersGet":
                usersGet(req, res);
                break;
            case "productsGet":
                productsGet(req, res);
                break;
            case "addProductGet":
                addProductGet(req, res);
                break;
            case "signUpGet":
                signUpGet(req, res);
                break;
            case "updateProductGet":
                updateProductGet(req, res);
                break;
            case "changeColorGet":
                changeColorGet(req, res);
                break;
            case "signUpPost":
                signUpPost(req, res);
                break;
            case "addProductPost":
                addProductPost(req, res);
                break;
            case "updateProductPost":
                updateProductPost(req, res);
                break;
            case "loginGet":
                loginGet(req, res);
                break;
            case "loginPost":
                loginPost(req, res);
                break;
            case "logoutGet":
                logoutGet(req, res);
                break;
            default:
                indexGet(req, res);
                break;
        }
    }


    private Map<String, Cookie> getCookies(HttpServletRequest req) {
        final Cookie[] cookies = req.getCookies();
        final Map<String, Cookie> map = new HashMap<>();

        // If there are cookies
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null) {
                    map.put(cookie.getName(), cookie);
                }
            }
        }

        return map;
    }

    private void indexGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, res);
    }

    private void usersGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final RequestDispatcher dispatcher = req.getRequestDispatcher("users.jsp");
        req.setAttribute("users", shopService.getPersons());
        dispatcher.forward(req, res);
    }

    private void productsGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
            HttpSession session = req.getSession();
            session.setAttribute("loggedInUser", newUser);
            res.sendRedirect("controller?action=indexGet");
        } catch (IllegalArgumentException | DbException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("userid", userid);
            req.setAttribute("email", email);
            req.setAttribute("password", password);
            req.setAttribute("firstName", firstName);
            req.setAttribute("lastName", lastName);
            processRequest("signupGet", req, res);
        }
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
            res.sendRedirect("Controller?action=productsGet");
        } catch (NumberFormatException | DomainException | DbException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("name", name);
            req.setAttribute("description", description);
            req.setAttribute("price", priceString);
            processRequest("addProductGet", req, res);
        }
    }

    private void updateProductGet(HttpServletRequest req, HttpServletResponse res) throws
        ServletException, IOException {
        final String id = req.getParameter("id");
        final Product product = shopService.getProduct(Integer.parseInt(id));
        req.setAttribute("product", product);

        final RequestDispatcher dispatcher = req.getRequestDispatcher("updateProduct.jsp");
        dispatcher.forward(req, res);
    }

    private void updateProductPost(HttpServletRequest req, HttpServletResponse res) throws
        IOException {
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

        res.sendRedirect("controller?action=productsGet");
    }

    private void changeColorGet(HttpServletRequest req, HttpServletResponse res) throws
        ServletException, IOException {
        // Get the current color cookie. If it does not exist, it is yellow
        final Map<String, Cookie> cookies = getCookies(req);
        final Cookie colorCookie = cookies.getOrDefault("color", new Cookie("color", "yellow"));

        // Revert it values
        if (colorCookie.getValue().equals("yellow")) {
            colorCookie.setValue("red");
        } else {
            colorCookie.setValue("yellow");
        }
        // Add it to the response
        res.addCookie(colorCookie);

        // old location
        final String oldAction = req.getParameter("oldAction");
        processRequest(oldAction, req, res);
    }

    private void loginGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get the parameters
        final RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
        dispatcher.forward(req, res);
    }

    private void loginPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String userId = req.getParameter("userid");
        final String password = req.getParameter("password");
        try {
            final Person person = shopService.getUserIfAuthenticated(userId, password);
            if (person == null) {
                throw new DomainException("Username and password do not match");
            }
            final HttpSession session = req.getSession();
            session.setAttribute("loggedInUser", person);
            res.sendRedirect("Controller?action=indexGet");
        } catch (DbException | DomainException e) {
            req.setAttribute("error", "Username and password do not match");
            req.setAttribute("userid", userId);
            req.setAttribute("password", password);
            processRequest("loginGet", req, res);
        }
    }

    private void logoutGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        session.setAttribute("loggedInUser", null);
        res.sendRedirect("/Controller?action=indexGet");
    }
}

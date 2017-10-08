package ui;

import db.DbException;
import domain.Person;
import domain.ShopService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Controller")
public class WebshopServlet extends HttpServlet {
    private final ShopService shopService;

    public WebshopServlet() {
        shopService = new ShopService();
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
                case "signUp":
                    signUp(req, res);
                    break;
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String userid = req.getParameter("userid");
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");
        final String firstName = req.getParameter("firstName");
        final String lastName = req.getParameter("lastName");


        try {
            final Person newUser = new Person(userid, email, password, firstName, lastName);
            shopService.addPerson(newUser);
        } catch (IllegalArgumentException | DbException e) {
            final RequestDispatcher dispatcher = req.getRequestDispatcher("signUp.jsp");
            req.setAttribute("error", e.getMessage());
            req.setAttribute("userid", userid);
            req.setAttribute("email", email);
            req.setAttribute("password", password);
            req.setAttribute("firstName", firstName);
            req.setAttribute("lastName", lastName);
            dispatcher.forward(req, res);
        }

        final RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, res);
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

    private void signUp(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final RequestDispatcher dispatcher = req.getRequestDispatcher("signUp.jsp");
        dispatcher.forward(req, res);
    }

}
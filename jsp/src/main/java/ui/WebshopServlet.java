package ui;

import db.PersonDatabase;
import db.PersonDatabaseInMemory;
import db.ProductDatabase;
import db.ProductDatabaseInMemory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Controller")
public class WebshopServlet extends HttpServlet {
    private final ProductDatabase productDatabase;
    private final PersonDatabase personDatabase;

    public WebshopServlet() {
        productDatabase = new ProductDatabaseInMemory();
        personDatabase = new PersonDatabaseInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String action = req.getParameter("action");
        if (action == null) {
            index(req, res);
        } else {
            switch (action) {
                case "overview":
                    overview(req, res);
                    break;
                case "signUp":
                    signUp(req, res);
                    break;
            }
        }
    }

    private void index(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, res);
    }

    private void overview(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final RequestDispatcher dispatcher = req.getRequestDispatcher("personoverview.jsp");
        dispatcher.forward(req, res);
    }

    private void signUp(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final RequestDispatcher dispatcher = req.getRequestDispatcher("signUp.jsp");
        dispatcher.forward(req, res);
    }
}

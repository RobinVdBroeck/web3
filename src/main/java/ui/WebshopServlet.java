package ui;

import db.DbException;
import domain.DomainException;
import domain.ShopService;

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
    private HandleFactory handleFactory;
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

        // Create the handleFactory
        handleFactory = new HandleFactory(shopService);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getParameter("action");
        // HandleFactory handles cases where action is null
        RequestHandler requestHandler = handleFactory.getHandler(action);
        requestHandler.handleRequest(request, response);
    }
}

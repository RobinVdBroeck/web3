package ui;

import db.DbException;
import domain.*;
import ui.handlers.RequestHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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

        // If empty, create admin account
        if (shopService.getPersons().size() == 0) {
            Person admin = new Person();
            admin.setUserid("admin");
            admin.setFirstName("Robin");
            admin.setLastName("Van den Broeck");
            admin.setEmail("robin.vandenbroeck@student.ucll.be");
            admin.setPassword("pass", false);
            admin.addRole(Role.User);
            admin.addRole(Role.Administrator);
            shopService.addPerson(admin);
        }
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
        RequestHandler requestHandler = handleFactory.getHandler(
            action,
            RolesUtility.getRoles(request)
        );
        requestHandler.handleRequest(request, response);
    }
}

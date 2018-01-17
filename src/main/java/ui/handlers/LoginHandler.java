package ui.handlers;

import db.DbException;
import domain.DomainException;
import domain.Person;
import domain.RolesUtility;
import domain.ShopService;
import ui.HandleFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginHandler implements RequestHandler {
    private final ShopService shopService;
    private final HandleFactory handleFactory;

    public LoginHandler(ShopService shopService, HandleFactory handleFactory) {
        this.shopService = shopService;
        this.handleFactory = handleFactory;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        final String userId = request.getParameter("userid");
        final String password = request.getParameter("password");
        try {
            final Person person = shopService.getUserIfAuthenticated(userId, password);
            if (person == null) {
                throw new DomainException("Username and password do not match");
            }
            final HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", person);
            response.sendRedirect("Controller?action=indexGet");
        } catch (DbException | DomainException e) {
            System.out.println(e);
            request.setAttribute("error", "Username and password do not match");
            request.setAttribute("userid", userId);
            request.setAttribute("password", password);
            RequestHandler handler = handleFactory.getHandler(
                "loginGet",
                RolesUtility.getRoles(request)
            );
            handler.handleRequest(request, response);
        }
    }
}

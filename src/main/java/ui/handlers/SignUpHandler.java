package ui.handlers;

import db.DbException;
import domain.Person;
import domain.Role;
import domain.RolesUtility;
import domain.ShopService;
import ui.HandleFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignUpHandler implements RequestHandler {
    private final ShopService service;
    private final HandleFactory handleFactory;

    public SignUpHandler(ShopService service, HandleFactory handleFactory) {
        this.service = service;
        this.handleFactory = handleFactory;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String userid = request.getParameter("userid");
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");
        final String firstName = request.getParameter("firstName");
        final String lastName = request.getParameter("lastName");

        try {
            final Person newUser = new Person(userid, email, password, firstName, lastName);
            newUser.addRole(Role.User);
            service.addPerson(newUser);
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", newUser);
            response.sendRedirect("Controller?action=indexGet");
        } catch (IllegalArgumentException | DbException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("userid", userid);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            RequestHandler handler = handleFactory.getHandler(
                "signUpGet",
                RolesUtility.getRoles(request)
            );
            handler.handleRequest(request, response);
        }
    }
}

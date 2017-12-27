package ui;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutHandler implements RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        final HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", null);
        response.sendRedirect("Controller?action=indexGet");
    }

}

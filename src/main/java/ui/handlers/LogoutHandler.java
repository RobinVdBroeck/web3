package ui.handlers;

import com.google.common.collect.ImmutableSet;
import domain.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class LogoutHandler implements RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        final HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", null);
        response.sendRedirect("Controller?action=indexGet");
    }

    @Override
    public Set<Role> getRoles() {
        return ImmutableSet.of(Role.User);
    }
}

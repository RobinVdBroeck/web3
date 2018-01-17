package ui.handlers;

import com.google.common.collect.ImmutableSet;
import domain.Role;
import domain.ShopService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class DeleteUserHandler implements RequestHandler {
    private final ShopService service;

    public DeleteUserHandler(ShopService service) {
        this.service = service;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        final String id = request.getParameter("id");
        service.deletePerson(id);
        response.sendRedirect("Controller?action=usersGet");
    }

    @Override
    public Set<Role> getRoles() {
        return ImmutableSet.of(Role.Administrator);
    }
}

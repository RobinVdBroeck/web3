package ui.handlers;

import domain.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserHandler implements RequestHandler {
    private final ShopService service;

    public DeleteUserHandler(ShopService service) {
        this.service = service;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        final String id = request.getParameter("id");
        service.deletePerson(id);
        response.sendRedirect("Controller?action=usersGet");
    }
}

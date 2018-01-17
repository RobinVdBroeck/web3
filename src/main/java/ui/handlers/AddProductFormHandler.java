package ui.handlers;

import com.google.common.collect.ImmutableSet;
import domain.Role;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class AddProductFormHandler implements RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        final RequestDispatcher dispatcher = request.getRequestDispatcher("addProduct.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public Set<Role> getRoles() {
        return ImmutableSet.of(Role.Administrator);
    }
}

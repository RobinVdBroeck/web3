package ui.handlers;

import com.google.common.collect.ImmutableSet;
import domain.Person;
import domain.Role;
import domain.ShopService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class UsersOverviewHandler implements RequestHandler {
    private final ShopService service;

    public UsersOverviewHandler(ShopService service) {
        this.service = service;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Get the users
        final List<Person> users = service.getPersons();
        request.setAttribute("users", users);

        final RequestDispatcher dispatcher = request.getRequestDispatcher("users.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public Set<Role> getRoles() {
        return ImmutableSet.of(Role.Administrator);
    }
}

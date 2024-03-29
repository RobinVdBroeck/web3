package ui.handlers;

import com.google.common.collect.ImmutableSet;
import domain.Role;
import domain.ShopService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class ProductsOverviewHandler implements RequestHandler {
    private final ShopService service;

    public ProductsOverviewHandler(ShopService service) {
        this.service = service;
    }


    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final RequestDispatcher dispatcher = request.getRequestDispatcher("products.jsp");
        request.setAttribute("products", service.getProducts());
        dispatcher.forward(request, response);
    }

    @Override
    public Set<Role> getRoles() {
        return ImmutableSet.of(Role.User);
    }
}

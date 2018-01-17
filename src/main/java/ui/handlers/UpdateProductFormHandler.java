package ui.handlers;

import com.google.common.collect.ImmutableSet;
import domain.Product;
import domain.Role;
import domain.ShopService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class UpdateProductFormHandler implements RequestHandler {
    private final ShopService service;

    public UpdateProductFormHandler(ShopService service) {
        this.service = service;
    }


    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        final String id = request.getParameter("id");
        final Product product = service.getProduct(Integer.parseInt(id));
        request.setAttribute("product", product);

        final RequestDispatcher dispatcher = request.getRequestDispatcher("updateProduct.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public Set<Role> getRoles() {
        return ImmutableSet.of(Role.Administrator);
    }
}

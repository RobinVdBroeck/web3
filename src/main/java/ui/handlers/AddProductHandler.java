package ui.handlers;

import com.google.common.collect.ImmutableSet;
import db.DbException;
import domain.*;
import ui.HandleFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class AddProductHandler implements RequestHandler {
    private final ShopService service;
    private final HandleFactory factory;

    public AddProductHandler(ShopService service, HandleFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String name = request.getParameter("name");
        final String description = request.getParameter("description");
        final String priceString = request.getParameter("price");

        try {
            if (priceString.isEmpty()) {
                throw new DomainException("Price cannot be empty");
            }
            final double price = Double.parseDouble(priceString);
            final Product newProduct = new Product(name, description, price);
            service.addProduct(newProduct);
            response.sendRedirect("Controller?action=productsGet");
        } catch (NumberFormatException | DomainException | DbException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("name", name);
            request.setAttribute("description", description);
            request.setAttribute("price", priceString);
            RequestHandler addProductGetHandler = factory.getHandler(
                "addProductGet",
                RolesUtility.getRoles(request)
            );
            addProductGetHandler.handleRequest(request, response);
        }
    }

    @Override
    public Set<Role> getRoles() {
        return ImmutableSet.of(Role.Administrator);
    }
}

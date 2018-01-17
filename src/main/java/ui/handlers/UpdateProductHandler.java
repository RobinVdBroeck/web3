package ui.handlers;

import com.google.common.collect.ImmutableSet;
import domain.Product;
import domain.Role;
import domain.ShopService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class UpdateProductHandler implements RequestHandler {
    private final ShopService service;

    public UpdateProductHandler(ShopService service) {
        this.service = service;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String name = request.getParameter("name");
        final String description = request.getParameter("description");
        final String priceString = request.getParameter("price");
        final String id = request.getParameter("id");

        // Create a new product
        final Product product = new Product(name, description, Double.parseDouble(priceString));
        product.setId(Integer.parseInt(id));

        // Update the product in the database
        service.updateProduct(product);

        response.sendRedirect("Controller?action=productsGet");
    }

    @Override
    public Set<Role> getRoles() {
        return ImmutableSet.of(Role.User);
    }
}

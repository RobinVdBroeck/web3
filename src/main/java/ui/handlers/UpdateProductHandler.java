package ui.handlers;

import domain.Product;
import domain.ShopService;
import ui.HandleFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateProductHandler implements RequestHandler {
    private final ShopService service;
    private final HandleFactory handleFactory;

    public UpdateProductHandler(ShopService service, HandleFactory handleFactory) {
        this.service = service;
        this.handleFactory = handleFactory;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}

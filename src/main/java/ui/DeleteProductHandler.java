package ui;

import domain.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductHandler implements RequestHandler {
    private final ShopService service;

    public DeleteProductHandler(ShopService service) {
        this.service = service;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        final String id = request.getParameter("id");
        service.deleteProduct(Integer.parseInt(id));
        response.sendRedirect("Controller?action=productsGet");
    }
}

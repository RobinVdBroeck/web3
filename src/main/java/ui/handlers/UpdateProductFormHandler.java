package ui.handlers;

import domain.Product;
import domain.ShopService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
}

package ui;

import domain.Role;
import domain.ShopService;
import ui.handlers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HandleFactory {
    private Map<String, RequestHandler> handlers = new HashMap<>();

    public HandleFactory(ShopService service) {
        // Index page
        handlers.put("indexGet", new IndexHandler());

        // User pages
        handlers.put("usersGet", new UsersOverviewHandler(service));
        handlers.put("deleteUser", new DeleteUserHandler(service));
        handlers.put("signUpGet", new SignUpFormHandler());
        handlers.put("signUpPost", new SignUpHandler(service, this));
        handlers.put("loginGet", new LoginFormHandler());
        handlers.put("loginPost", new LoginHandler(service, this));
        handlers.put("logoutGet", new LogoutHandler());

        // Product pages
        handlers.put("productsGet", new ProductsOverviewHandler(service));
        handlers.put("addProductGet", new AddProductFormHandler());
        handlers.put("addProductPost", new AddProductHandler(service, this));
        handlers.put("updateProductGet", new UpdateProductFormHandler(service));
        handlers.put("updateProductPost", new UpdateProductHandler(service));
        handlers.put("deleteProduct", new DeleteProductHandler(service));

        // Colors
        handlers.put("changeColorGet", new ChangeColorHandler());
    }

    /**
     * Get the handler for the action. In case of null or an unknown action, the index page get's returned.
     * TODO: 404
     *
     * @param action       The action to perform
     * @param currentRoles The roles of the current user
     * @return The request handler
     */
    public RequestHandler getHandler(String action, Set<Role> currentRoles) {
        if (action == null) {
            return handlers.get("indexGet");
        }
        if (!handlers.containsKey(action)) {
            System.out.println("Action not found: " + action);
            return handlers.get("indexGet");
        }

        final RequestHandler handler = handlers.get(action);

        if (!currentRoles.containsAll(handler.getRoles())) {
            throw new NotAuthorizedException();
        }

        return handler;
    }
}

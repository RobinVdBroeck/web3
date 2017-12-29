package ui.handlers;

import ui.HandleFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeColorHandler implements RequestHandler {
    private final HandleFactory handleFactory;

    public ChangeColorHandler(HandleFactory handleFactory) {
        this.handleFactory = handleFactory;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current color cookie. If it does not exist, it is yellow
        Cookie[] cookies = request.getCookies();
        Cookie colorCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("color")) {
                    colorCookie = cookie;
                    break;
                }
            }
        }
        // If not found or there are no cookies
        if(colorCookie == null) {
            colorCookie = new Cookie("color", "yellow");
        }

        // Revert it values
        if (colorCookie.getValue().equals("yellow")) {
            colorCookie.setValue("red");
        } else {
            colorCookie.setValue("yellow");
        }
        // Add it to the response
        response.addCookie(colorCookie);

        // old location
        final String referer = request.getHeader("Referer");
        response.sendRedirect(referer);
    }
}

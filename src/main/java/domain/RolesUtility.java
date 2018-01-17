package domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class RolesUtility {
    public static Set<Role> getRoles(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        return RolesUtility.getRoles(session);


    }

    public static Set<Role> getRoles(HttpSession session) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return new HashSet<>();
        }
        return loggedInUser.getRoles();
    }
}

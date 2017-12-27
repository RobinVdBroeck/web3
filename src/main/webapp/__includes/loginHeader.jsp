<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${!sessionScope.containsKey('loggedInUser')}">
        <li
                <c:if test="${param.page.toLowerCase() == 'sign up'}"> id="actual"</c:if>
        >
            <a href="Controller?action=signUpGet" id="signUpNav">Sign up</a>
        </li>

        <li
                <c:if test="${param.page.toLowerCase() == 'login'}"> id="actual"</c:if>
        >
            <a href="Controller?action=loginGet" id="loginNav">Login</a>
        </li>
    </c:when>
    <c:otherwise>
        <li><a href="Controller?action=logoutGet" id="logoutNav">Log out</a></li>
    </c:otherwise>
</c:choose>


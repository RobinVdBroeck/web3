<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${param.user == -1}">
        <c:choose>
            <c:when test="${param.page.toLowerCase() == 'sign up'}">
                <li id="actual"><a href="Controller?action=signUpGet">Sign up</a></li>
                <li><a href="Controller?action=loginGet">Login</a></li>
            </c:when>
            <c:when test="${param.page.toLowerCase() == 'login'}">
                <li><a href="Controller?action=signUpGet">Sign up</a></li>
                <li id="actual"><a href="Controller?action=loginGet">Login</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="Controller?action=signUpGet">Sign up</a></li>
                <li><a href="Controller?action=loginGet">Login</a></li>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <li><a href="Controller?action=logoutGet&userid=${param.user}">Log out</a></li>
    </c:otherwise>
</c:choose>


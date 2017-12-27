<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<jsp:include page="__includes/head.jsp">
    <jsp:param name="title" value="Login"/>
</jsp:include>
<body>
<div id="container">
    <jsp:include page="__includes/header.jsp">
        <jsp:param name="page" value="Login"/>
    </jsp:include>
    <main>
        <c:if test="${error != null}">
            <div class="alert-danger">
                <ul>
                    <li>
                        <c:out value="${error}"/>
                    </li>
                </ul>
            </div>
        </c:if>

        <form method="post" action="Controller?action=loginPost" novalidate="novalidate">
            <!-- novalidate in order to be able to run tests correctly -->
            <p>
                <label for="userid">User id</label>
                <input type="text" id="userid" name="userid" value="<c:out value="${userid}"/>" required>
            </p>
            <p>
                <label for="password">Password</label>
                <input type="password" id="password" name="password" value="<c:out value="${password}"/>" required>
            </p>
            <p>
                <input type="submit" id="login" value="Login">
            </p>
        </form>
    </main>
    <jsp:include page="__includes/footer.jsp"/>
</div>
</body>
</html>

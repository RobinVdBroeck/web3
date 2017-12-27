<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<jsp:include page="__includes/head.jsp">
    <jsp:param name="title" value="Sign Up"/>
</jsp:include>
<body>
<div id="container">
    <jsp:include page="__includes/header.jsp">
        <jsp:param name="page" value="Sign Up"/>
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

        <form method="post" action="" novalidate="novalidate">
            <!-- novalidate in order to be able to run tests correctly -->
            <p>
                <label for="userid">User id</label>
                <input type="text" id="userid" name="userid" value="<c:out value="${userid}"/>" required>
            </p>
            <p>
                <label for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" value="<c:out value="${firstName}"/>" required>
            </p>
            <p>
                <label for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" value="<c:out value="${lastName}"/>" required>
            </p>
            <p>
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="<c:out value="${email}"/>" required>
            </p>
            <p>
                <label for="password">Password</label>
                <input type="password" id="password" name="password" value="<c:out value="${password}"/>" required>
            </p>
            <p>
                <input type="submit" id="signUp" value="Sign Up">
            </p>
        </form>
    </main>
    <jsp:include page="__includes/footer.jsp"/>

</div>
</body>
</html>

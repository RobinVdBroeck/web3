<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="container">
    <jsp:include page="top.jsp">
        <jsp:param name="page" value="Sign Up"/>
    </jsp:include>
    <main>
        <c:if test="${error != null}">
            <div class="alert-danger">
                <ul>
                    <li>${error}
                    </li>
                </ul>
            </div>
        </c:if>

        <form method="post" action="" novalidate="novalidate">
            <!-- novalidate in order to be able to run tests correctly -->
            <p>
                <label for="userid">User id</label>
                <input type="text" id="userid" name="userid" value="${userid}" required>
            </p>
            <p>
                <label for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" value="${firstName}" required>
            </p>
            <p>
                <label for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" value="${lastName}" required>
            </p>
            <p>
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${email}" required>
            </p>
            <p>
                <label for="password">Password</label>
                <input type="password" id="password" name="password" value="${password}" required>
            </p>
            <p>
                <input type="submit" id="signUp" value="Sign Up">
            </p>
        </form>
    </main>
    <footer>
        &copy; Webontwikkeling 3, UC Leuven-Limburg
    </footer>
</div>
</body>
</html>

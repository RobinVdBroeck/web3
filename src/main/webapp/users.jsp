<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="__includes/head.jsp">
    <jsp:param name="title" value="User Overview"/>
</jsp:include>
<body>
<div id="container">
    <jsp:include page="__includes/header.jsp">
        <jsp:param name="page" value="User Overview"/>
    </jsp:include>

    <main>
        <table>
            <tr>
                <th>E-mail</th>
                <th>First Name</th>
                <th>Last Name</th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>
                        <c:out value="${user.email}"/>
                    </td>
                    <td>
                        <c:out value="${user.firstName}"/>
                    </td>
                    <td>
                        <c:out value="${user.lastName}"/>
                    </td>
                </tr>
            </c:forEach>

            <caption>Users Overview</caption>
        </table>
    </main>
    <jsp:include page="__includes/footer.jsp"/>
</div>
</body>
</html>

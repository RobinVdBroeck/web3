<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="__includes/head.jsp">
    <jsp:param name="title" value="Product Overview"/>
</jsp:include>
<body>
<div id="container">
    <jsp:include page="__includes/header.jsp">
        <jsp:param name="page" value="Product Overview"/>
    </jsp:include>
    <main>
        <table>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
            </tr>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>
                        <a href="Controller?action=updateProductGet&id=<c:out value="${product.id}" />">
                            <c:out value="${product.name}"/>
                        </a>
                    </td>
                    <td>
                        <c:out value="${product.description}"/>
                    </td>
                    <td>
                        <c:out value="${product.price}"/>
                    </td>
                </tr>
            </c:forEach>

            <caption>Product Overview</caption>
        </table>
    </main>
    <jsp:include page="__includes/footer.jsp"/>
</div>
</body>
</html>

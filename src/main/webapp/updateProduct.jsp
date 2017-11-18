<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta charset="UTF-8">
    <title>Update product</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="container">
    <jsp:include page="top.jsp">
        <jsp:param name="page" value="Update Product"/>
    </jsp:include>
    <main>
        <form method="post" action="Controller?action=updateProduct" novalidate="novalidate">
            <input type="hidden" name="id" value="${product.id}"/>
            <p>
                <label for="name">Name</label>
                <input type="text" id="name" name="name" value="${product.name}" required>
            </p>
            <p>
                <label for="description">Description</label>
                <input type="text" id="description" name="description" value="${product.description}" required>
            </p>
            <p>
                <label for="price">Price (in euros)</label>
                <input type="number" min="0" step="0.01" id="price" name="price" value="${product.price}" required>
            </p>
            <p>
                <input type="submit" value="Save"/>
            </p>
        </form>
    </main>
    <footer>
        &copy; Webontwikkeling 3, UC Leuven-Limburg
    </footer>
</div>
</body>
</html>

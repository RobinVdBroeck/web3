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
        <jsp:param name="page" value="Add Product"/>
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

        <form method="post" action="Controller?action=addProduct" novalidate="novalidate">
            <p>
                <label for="name">Name</label>
                <input type="text" id="name" name="name" value="<c:out value="${name}"/>" required/>
            </p>
            <p>
                <label for="description">Description</label>
                <input type="text" id="description" name="description" value="<c:out value="${description}"/>"
                       required/>
            </p>
            <p>
                <label for="price">Price (in euros)</label>
                <input type="number" min="0" step="0.01" id="price" name="price" value="<c:out value="${price}"/>"
                       required/>
            </p>
            <p>
                <input type="submit" id="save" value="Add"/>
            </p>
        </form>
    </main>
    <jsp:include page="__includes/footer.jsp"/>
</div>
</body>
</html>

<%--suppress XmlDuplicatedId --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <h1>
        <span>Web shop</span>
    </h1>

    <nav>

        <ul>
            <!-- Home -->
            <li
                    <c:if test="${param.page.toLowerCase().equals('home')}">
                        id="actual"
                    </c:if>
            >
                <a href="Controller">Home</a>
            </li>

            <!-- Users -->
            <li
                    <c:if test="${param.page.toLowerCase().equals('user overview')}">
                        id="actual"
                    </c:if>
            >
                <a href="Controller?action=usersGet">Users</a>
            </li>

            <!-- Products -->
            <li
                    <c:if test="${param.page.toLowerCase().equals('product overview')
                            || param.page.toLowerCase().equals('update product')}">
                        id="actual"
                    </c:if>
            >
                <a href="Controller?action=productsGet">Products</a>
            </li>

            <!-- Add Product -->
            <li
                    <c:if test="${param.page.toLowerCase().equals('add product')}">
                        id="actual"
                    </c:if>
            >
                <a href="Controller?action=addProductGet">Add Product</a>
            </li>

            <jsp:include page="loginHeader.jsp">
                <jsp:param name="page" value="${param.page}"/>
                <jsp:param name="user" value="${-1}"/>
            </jsp:include>
        </ul>
    </nav>

    <h2><c:out value="${param.page}"/></h2>
</header>

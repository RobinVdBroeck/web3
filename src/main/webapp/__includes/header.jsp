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
                <a href="Controller" id="homeNav">Home</a>
            </li>


            <!-- Users -->
            <c:if test="${sessionScope.containsKey('loggedInUser') && sessionScope.loggedInUser.isAdministrator() }">
                <li
                        <c:if test="${param.page.toLowerCase().equals('user overview')}">
                            id="actual"
                        </c:if>
                >
                    <a href="Controller?action=usersGet" id="usersNav">Users</a>
                </li>
            </c:if>

            <!-- Products -->
            <c:if test="${sessionScope.containsKey('loggedInUser') && sessionScope.loggedInUser.isUser()}">

                <li
                        <c:if test="${param.page.toLowerCase().equals('product overview')
                            || param.page.toLowerCase().equals('update product')}">
                            id="actual"
                        </c:if>
                >
                    <a href="Controller?action=productsGet" id="productsNav">Products</a>
                </li>
            </c:if>

            <!-- Add Product -->
            <c:if test="${sessionScope.containsKey('loggedInUser') && sessionScope.loggedInUser.isAdministrator()}">
                <li
                        <c:if test="${param.page.toLowerCase().equals('add product')}">
                            id="actual"
                        </c:if>
                >
                    <a href="Controller?action=addProductGet" id="addProductNav">Add Product</a>
                </li>
            </c:if>

            <jsp:include page="loginHeader.jsp">
                <jsp:param name="page" value="${param.page}"/>
            </jsp:include>
        </ul>
    </nav>

    <h2><c:out value="${param.page}"/></h2>
</header>

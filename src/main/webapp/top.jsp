<%--suppress XmlDuplicatedId --%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <h1>
        <span>Web shop</span>
    </h1>

    <nav>
        <c:choose>
            <c:when test="${param.page.toLowerCase().equals('home')}">
                <ul>
                    <li id="actual"><a href="Controller">Home</a></li>
                    <li><a href="Controller?action=users">Users</a></li>
                    <li><a href="Controller?action=products">Products</a></li>
                    <li><a href="Controller?action=addProduct">Add Product</a></li>
                    <li><a href="Controller?action=signUp">Sign up</a></li>
                </ul>
            </c:when>
            <c:when test="${param.page.toLowerCase().equals('user overview')}">
                <ul>
                    <li><a href="Controller">Home</a></li>
                    <li id="actual"><a href="Controller?action=users">Users</a></li>
                    <li><a href="Controller?action=products">Products</a></li>
                    <li><a href="Controller?action=addProduct">Add Product</a></li>
                    <li><a href="Controller?action=signUp">Sign up</a></li>
                </ul>
            </c:when>
            <c:when test="${param.page.toLowerCase().equals('product overview')
                            || param.page.toLowerCase().equals('update product')}">
                <ul>
                    <li><a href="Controller">Home</a></li>
                    <li><a href="Controller?action=users">Users</a></li>
                    <li id="actual"><a href="Controller?action=products">Products</a></li>
                    <li><a href="Controller?action=addProduct">Add Product</a></li>
                    <li><a href="Controller?action=signUp">Sign up</a></li>
                </ul>
            </c:when>
            <c:when test="${param.page.toLowerCase().equals('add product')}">
                <ul>
                    <li><a href="Controller">Home</a></li>
                    <li><a href="Controller?action=users">Users</a></li>
                    <li><a href="Controller?action=products">Products</a></li>
                    <li id="actual"><a href="Controller?action=addProduct">Add Product</a></li>
                    <li><a href="Controller?action=signUp">Sign up</a></li>
                </ul>
            </c:when>
            <c:when test="${param.page.toLowerCase().equals('sign up')}">
                <ul>
                    <li><a href="Controller">Home</a></li>
                    <li><a href="Controller?action=users">Users</a></li>
                    <li><a href="Controller?action=products">Products</a></li>
                    <li><a href="Controller?action=addProduct">Add Product</a></li>
                    <li id="actual"><a href="Controller?action=signUp">Sign up</a></li>
                </ul>
            </c:when>
        </c:choose>
    </nav>

    <h2><c:out value="${param.page}"/></h2>
</header>

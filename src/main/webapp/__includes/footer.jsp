<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<footer>
    &copy; Webontwikkeling 3, UC Leuven-Limburg
    - <a href="Controller?action=changeColorGet&oldAction=${action}" id="changeColorLink">Change Color</a>
    <c:if test="${cookie.containsKey('color')}">
        (Current color is ${cookie.get("color").value})
    </c:if>
</footer>

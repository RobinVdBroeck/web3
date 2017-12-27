<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta name="viewport" content="width=device-width, initial-scale=1"/>
<meta charset="UTF-8"/>
<title>${param.getOrDefault("title", "Webshop")}</title>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<c:choose>
    <c:when test="${cookie.get('color').value.toLowerCase().equals('red')}">
        <link rel="stylesheet" href="css/red.css"/>
    </c:when>

    <c:otherwise>
        <link rel="stylesheet" href="css/yellow.css"/>
    </c:otherwise>
</c:choose>

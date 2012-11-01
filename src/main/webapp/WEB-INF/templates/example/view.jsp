<%--@elvariable id="it" type="se.grunka.basic.webapp.example.ExampleResource.ExampleModel"--%>
<%@ taglib prefix="example" tagdir="/WEB-INF/tags/example" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Example view page</title>
</head>
<body>
<p>${it.message}</p>
<p><example:upperCase value="${it.message}"/></p>
<ul>
    <c:forEach items="${it.counter}" var="count">
        <li>${count}</li>
    </c:forEach>
</ul>
</body>
</html>

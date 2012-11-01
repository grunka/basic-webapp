<%--@elvariable id="it" type="java.util.Map<java.lang.String, java.lang.String>"--%>
<%@ taglib prefix="example" tagdir="/WEB-INF/tags/example" %>
<!DOCTYPE html>
<html>
<head>
    <title>Example view page</title>
</head>
<body>
<p>${it.message}</p>
<p><example:upperCase value="${it.message}"/></p>
</body>
</html>

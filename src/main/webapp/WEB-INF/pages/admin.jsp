<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
    </head>
    <body>
        <h1>This is Admin Page</h1>
        <p>Hello, ${userDetails.username}!</p>

        <p>User authorities: ${userAuthorities}</p>
        <c:url var="back" value="/crud"/>
        <a href="${back}">Back</a>
    </body>
</html>
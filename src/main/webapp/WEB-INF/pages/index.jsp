<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!doctype>
<html>
    <head>
        <title>Entry page</title>
    </head>
    <body>
    <h1>This is index page - Firs page after successful authentication</h1>
    <p>Your name: ${userDetails}</p>
    <p>Your role:<c:forEach items="${list}" var="role">${role.authority}
        </c:forEach></p>
        <div>
            <h1>Go to  users CRUD-page </h1>
            <a href="crud">Go!</a>

        </div>
    </body>
</html>
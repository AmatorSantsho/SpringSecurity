<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 18.01.2018
  Time: 21:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>welcome page</title>
</head>
<body>
<h1>This is welcome page - Firs page after successful authentication</h1>
<p>Your name: ${userDetails}</p>
<p>Your role:
<c:forEach items="${list}" var="role">
  <p>${role.authority}</p>
</c:forEach>
</p>Try going to adminPage. If you do not have Role_Admin you will be redirected to accsessDeniedPage.
<a href="admin"> Admin page</a>
<br>
<br>
<p>Redirect to loginPage</p>
<a href="logout">Logout</a>
</body>
</html>

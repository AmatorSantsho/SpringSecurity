<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 20.01.2018
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<p style="color: red">Accsess is denied. You do not have a Role_Admin </p>
<c:url var="back" value="/crud"/>
<a href="${back}">Back</a>
</body>
</html>

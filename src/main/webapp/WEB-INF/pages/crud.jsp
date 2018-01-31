<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 18.01.2018
  Time: 21:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>

<title>CRUD page</title>
<link href="<c:url value="/resources/style.css"/>" type="text/css" rel="stylesheet"/>
</head>
<body>
<h1>This is CRUD page - second page after successful authentication</h1>

<p>Your name: ${userDetails}</p>
<p>Your role:<c:forEach items="${list}" var="role">
<p>${role.authority}</p>
</c:forEach></p>

</p>Try going to <a href="admin"> Admin page</a>. If you do not have Role_Admin you will be redirected to accsessDeniedPage.

<br>
<br>

<p>Redirect back to loginPage</p>
<a href="logout">Logout</a>

<h1>List of users in DB</h1>
User with role ROLE_USER can only read information.
To accsess to CRUD operations you need a role ROLE_ADMIN.
<p>The password field is usually not shown. Here it is shown for an example that the password data in the database is stored in a hashed form</p>
<p>All users have the same password as the name</p>
</br>


<c:if test="${!empty users}">
    <table class="tg">
        <tr>

            <th width="120">name</th>
            <th width="180">password</th>
            <th width="120">email</th>
            <th width="80">registration</th>
            <th width="80">roles</th>
            <th width="60">Edit</th>
            <th width="60">Delete</th>

        </tr>
        <c:forEach items="${users}" var="us">
            <c:set var="time" value="${us.registration}"/>
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${time}" var="parsedD"/>
            <tr>

                <td>${us.name}</td>
                <td>${us.password}</td>
                <td>${us.email}</td>
                <td>${parsedD}</td>
                <td>${us.roles}</td>
                <td><a href="<c:url value='/update/${us.id}'/>">Edit</a></td>
                <td><a href="<c:url value='/delete/${us.id}'/>">Delete</a></td>

            </tr>
        </c:forEach>
    </table>
</c:if>

<h1>Edit/Add User</h1>
<c:url var="addAction" value="/add"/>
<form action="${addAction}" method="post">
    <table>
<c:if test="${!empty userToEdit.name }">
    <tr>
        <td>
            <input type="hidden" name="id" value="${userToEdit.id}">
        </td>
    </tr>
</c:if>
        <tr>
            <td>
                Name:
            </td>
            <td>
                <input  type="text"  name="name" value="${userToEdit.name}" size="90">
            </td>
        </tr>
        <tr>
            <td>
                Password:
            </td>
            <td>
                <input  type="text"  name="password" value="${userToEdit.password}" size="90">
            </td>
        </tr>
        <tr>
            <td>
                Email:
            </td>
                   <td>
                <input  type="text"  name="email" value="${userToEdit.email}" size="90">
            </td>
        </tr>
        <tr>
            <td>
                Roles:
            </td>
            <td>
                <input  type="text"  name="roles" value="${userToEdit.roles}" size="90">
            </td>
        </tr>
        <tr>
            <td>
                Date of registration:
            </td>
            <td>
                <input  type="datetime"  name="datatime" value="${userToEdit.registration}" size="90">
            </td>
        </tr>
        <tr>
        <td colspan="2">
        <c:if test="${!empty userToEdit.name}">
        <input type="submit"
        value="<spring:message text="Edit"/>"/>
        </c:if>
        <c:if test="${empty userToEdit.name}">
        <input type="submit"
        value="<spring:message text="Add"/>"/>
        </c:if>
        </td>
        </tr>
    </table>

</form>

</body>
</html>

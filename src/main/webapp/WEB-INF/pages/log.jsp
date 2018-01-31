<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 18.01.2018
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>LoginPage</title>
</head>
<body>
<h1>This is login page</h1>

<c:if test="${not empty errorMessage}">
  <p style="color: red"> ${errorMessage}</p>
  </c:if>
<form name="log" method="post" action="spring_security_check">
<table>
  <tr>
    <td>User:</td>
    <td><input type="text" name="username"></td>
      </tr>
  <tr>
    <td>Password:</td>
    <td><input type="password" name="password"></td>
  </tr>
  <tr>
    <td colspan="2"> <input name="submit" type="submit" value="Login"></td>

  </tr>
</table>
</form>
<p> To entry use:</p>
<table>
  <tr>
    <td>
      username:
    </td>
    <td>
      user01
    </td>
  </tr>
  <tr>
    <td>
      password:
    </td>
    <td>
      user01
    </td>
  </tr>
  <tr>
    <td>
      roles:
    </td>
    <td>
      ROLE_USER
    </td>
  </tr>
</table>
<br>
<table>
  <tr>
    <td>
      username:
    </td>
    <td>
      admin
    </td>
  </tr>
  <tr>
  <td>
    password:
  </td>
  <td>
    admin
  </td>
</tr>
<tr>
  <td>
    roles:
  </td>
  <td>
    ROLE_USER,ROLE_ADMIN
  </td>
</tr>


</table>
</body>
</html>

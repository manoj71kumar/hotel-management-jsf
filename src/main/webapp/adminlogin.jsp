<%--
  Created by IntelliJ IDEA.
  User: IBJNUG
  Date: 6/14/2020
  Time: 7:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login to First Maven App</title>
</head>
<body>
<form id="loginForm" name="loginForm" method="post" action="j_security_check">
    User name: <input id="username" type="text" name="j_username" class="text-left">
    Password: <input id="password" type="password" name="j_password" class="text-left">
    <input name="login" type="submit" value="Login" id="submit" class="btn-default">


</form>

</body>
</html>
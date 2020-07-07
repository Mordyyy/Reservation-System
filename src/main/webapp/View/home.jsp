<%@ page import="ge.edu.freeuni.Models.User" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/7/2020
  Time: 2:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sawyisi</title>
</head>
<body>
    <h1>Hello <%=((User)session.getAttribute("user")).getUsername()%></h1>
</body>
</html>

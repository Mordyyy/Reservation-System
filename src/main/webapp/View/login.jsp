<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <spring:url value="/resources/css/LoginStyle.css" var="mainCss" />
    <link type="text/css" rel="stylesheet" href="${mainCss}">
</head>
<body>
    <div class="loginbox">
        <h1>Login</h1>
        <div class="textbox">
            <input type="text" placeholder="Username" name="username" value="">
        </div>
        <div class="textbox">
            <input type="text" placeholder="Password" name="password" value="">
        </div>
        <input class="buton" type="button" name="Login" value="Login">
        <input class="buton" type="button" name="Register" value="Register">
    </div>
</body>
</html>
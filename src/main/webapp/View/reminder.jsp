<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Remind Password</title>
    <spring:url value="/resources/css/RegisterStyle.css" var="mainCss" />
    <link type="text/css" rel="stylesheet" href="${mainCss}">
</head>
<body>
<div class="loginbox">
    <h1>Forget Password</h1>
    <div class="textbox">
        <i class="fa fa-envelope" aria-hidden="true"></i>
        <input type="text" placeholder="Enter your email" name="emailadress" value="">
    </div>
    <a href="/">Main Page</a>
    <input class="buton" type="button" name="CodeSender" value="Send Password">
</div>
</body>
</html>

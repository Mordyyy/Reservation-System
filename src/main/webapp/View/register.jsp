<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Register</title>
    <spring:url value="/resources/css/RegisterStyle.css" var="mainCss" />
    <link type="text/css" rel="stylesheet" href="${mainCss}">
</head>
<body>
<div class="loginbox">
    <h1>Register</h1>
    <div class="textbox">
        <i class="fa fa-user" aria-hidden="true"></i>
        <input type="text" placeholder="Username" name="Username" value="">
    </div>
    <div class="textbox">
        <i class="fa fa-lock" aria-hidden="true"></i>
        <input type="password" placeholder="Password" name="Password1" value="">
    </div>
    <div class="textbox">
        <i class="fa fa-lock" aria-hidden="true"></i>
        <input type="password" placeholder="Re-Enter Password" name="Password2" value="">
    </div>
    <div class="textbox">
        <i class="fa fa-envelope" aria-hidden="true"></i>
        <input type="text" placeholder="E-mail" name="eMail" value="">
        <input class="bt" type="button" name="Button" value="Send">
    </div>
    <div class="textbox">
        <i class="fa fa-ambulance" aria-hidden="true"></i>
        <input type="text" placeholder="Code" name="Code" value="">
    </div>

    <input class="buton" type="button" name="Button" value="Register">
</div>
</body>
</html>

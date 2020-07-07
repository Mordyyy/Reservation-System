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
    <form action = "/register" method = "POST">
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
        </div>
        <button class = "buton" type="submit" name = "Button" value="Register">Register</button>
        <c:if test="${error != null}">
        <div class="errorico">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
        </div> <br><br>
        <div class="eror">
            Error: ${error}
        </div>
        </c:if>
    </form>
</div>
<%--    <input class="buton" type="button" name="Button" value="Register">--%>
</body>
</html>
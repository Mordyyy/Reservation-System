<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <spring:url value="/resources/css/LoginStyle.css" var="mainCss" />
    <link type="text/css" rel="stylesheet" href="${mainCss}">
</head>
<body>
        <div class="loginbox">
            <form action = "/" method = "POST">
                <h1>Login</h1>
                <div class="textbox">
                    <i class="fa fa-user" aria-hidden="true"></i>
                    <input type="text" placeholder="Username/Email" name="Username" value="">
                </div>
                <div class="textbox">
                    <i class="fa fa-lock" aria-hidden="true"></i>
                    <input type="password" placeholder="Password" name="Password" value="">
                </div>
                <a href="/forgot">Remind me password</a>
                <button class = "buton" type ="submit" name = "Button" value = "Login">Login</button>
                <button class = "buton" type ="submit" name = "Button" value = "Register">Register</button>
            </form>
            <c:if test="${Error != null}">
                <div class="errorico">
                    <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
                </div> <br>
                <div class="eror">
                    Authentication failed: ${Error}
                </div>
            </c:if>
        </div>
</body>
</html>

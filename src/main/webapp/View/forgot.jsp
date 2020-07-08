<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Forgot Password</title>
    <spring:url value="/resources/css/RegisterStyle.css" var="mainCss" />
    <link type="text/css" rel="stylesheet" href="${mainCss}">
</head>
<body>
<div class="loginbox">
    <form action="/forgot" method="post">
        <h1>Forget Password</h1>
        <div class="textbox">
            <i class="fa fa-envelope" aria-hidden="true"></i>
            <input type="text" placeholder="Enter your email" name="email" value="">
        </div>
        <a href="/">Main Page</a>
        <button class="buton" type="submit" name="CodeSender">Send Password</button>
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
</body>
</html>

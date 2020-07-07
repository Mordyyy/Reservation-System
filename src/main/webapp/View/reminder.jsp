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
<c:if test="${error != null}">
    Error: ${error}
</c:if>
<div class="loginbox">
    <form action="/reminder" method="post">
        <h1>Forget Password</h1>
        <div class="textbox">
            <i class="fa fa-envelope" aria-hidden="true"></i>
            <input type="text" placeholder="Enter your email" name="email" value="">
        </div>
        <a href="/">Main Page</a>
        <button class="buton" type="submit" name="CodeSender">Send Password</button>
    </form>
</div>
</body>
</html>

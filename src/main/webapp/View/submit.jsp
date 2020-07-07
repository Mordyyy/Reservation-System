<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Registration</title>
    <spring:url value="/resources/css/SubmitStyle.css" var="mainCss" />
    <link type="text/css" rel="stylesheet" href="${mainCss}">
</head>
<body>
<c:if test="${error != null}">
    Authentication failed: ${error}
</c:if>
<div class="loginbox">
    <form action = "/sendcode" method="POST">
        <h1>Confirm Registration</h1>
        <div class="textbox">
            <i class="fa fa-ambulance" aria-hidden="true"></i>
            <input type="text" placeholder="Code" name="Code" value="">
        </div>
        <a href="/">Main Page</a>
        <button class="buton" type="submit" name = "Button" value = "Submit">Submit Code</button>
    </form>
</div>
</body>
</html>

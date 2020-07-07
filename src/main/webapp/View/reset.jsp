<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Reset Password</title>
    <spring:url value="/resources/css/RegisterStyle.css" var="mainCss" />
    <link type="text/css" rel="stylesheet" href="${mainCss}">
</head>
<body>
<div class="loginbox">
    <form action="/reset" method="post">
        <h1>Reset Password</h1>
        <div class="textbox">
            <i class="fa fa-lock" aria-hidden="true"></i>
            <input type="password" placeholder="Current Password" name="oldpassword" value="">
        </div>
        <div class="textbox">
            <i class="fa fa-lock" aria-hidden="true"></i>
            <input type="password" placeholder="New Password" name="password1" value="">
        </div>
        <div class="textbox">
            <i class="fa fa-lock" aria-hidden="true"></i>
            <input type="password" placeholder="Re-enter Password" name="password2" value="">
        </div>
        <a href="/">Go Back</a>      <%-- instead of / there should be /user--%>
        <button class="buton" type="submit">Reset Password</button>
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

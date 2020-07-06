<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title>Hello World</title>
    <spring:url value="/resources/css/style.css" var="mainCss" />
    <link type="text/css" rel="stylesheet" href="${mainCss}">
</head>
<body>
    <div class="background">

    </div>
    <h1>Hello World</h1>
</body>
</html>

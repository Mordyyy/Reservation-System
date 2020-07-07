<%@ page import="ge.edu.freeuni.DAO.BlacklistDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Blacklist</title>
    <spring:url value="/resources/css/BlacklistStyle.css" var="mainCss" />
    <link type="text/css" rel="stylesheet" href="${mainCss}">
</head>
<body>
    <ul>
        <% BlacklistDAO dao = (BlacklistDAO)request.getServletContext().getAttribute("blacklist");
           List<String> blacklist = dao.getAll();
           for (String username: blacklist) {%>
               <li><span class="username" name="<%=username%>"><%=username%></span></li>
           <%}%>
    </ul>
</body>
</html>

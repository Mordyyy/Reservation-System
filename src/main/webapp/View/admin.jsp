<%@ page import="ge.edu.freeuni.DAO.BlacklistDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Blacklist</title>
    <spring:url value="/resources/css/AdminStyle.css" var="mainCss" />
    <link type="text/css" rel="stylesheet" href="${mainCss}">
</head>
<body>

    <div class="sendmailbox">
        <h1>Send Mail</h1>
        <form action="/admin" method="post">
            <div class="Trio">
                <i class="fa fa-envelope" aria-hidden="true"></i>
                <input type="email" placeholder="E-mail(s)" name="emailstosend" value="" multiple>
            </div>
            <div class="Trio">
                <i class="fa fa-address-book" aria-hidden="true"></i>
                <input type="text" placeholder="Subject" name="subject" value="">
            </div>
            <div class="trio">
                <div class="textfield">
                    <textarea type="text" placeholder="Text" name="text" value=""></textarea>
                </div>
                <button type="submit" class="send" name="Button" value="send">Send</button>
                <button type="submit" class="send" name="Button" value="sendall">Send To All</button>
            </div>
        </form>
    </div>
    <div class="blacklist">
        <h1>Blacklist</h1>
        <div class="Trio">
            <input type="text" placeholder="Username">
            <button type="submit" class="block" value="unblock">Unblock</button>
            <button type="submit" class="block" value="block">Block</button>
        </div>
        <nav>
            <ul>
                <% BlacklistDAO dao = (BlacklistDAO)request.getServletContext().getAttribute("blacklist");
                   List<String> blacklist = dao.getAll();
                   for (String username: blacklist) {%>
                       <li><span class="username" name="<%=username%>"><%=username%></span></li>
                   <%}%>
            </ul>
        </nav>
    </div>
    <div class="logout">
        <a href="/login">Log Out</a>
    </div>
</body>
</html>

<%@ page import="ge.edu.freeuni.DAO.BlacklistDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="ge.edu.freeuni.Models.Cell" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Admin</title>
    <spring:url value="/resources/css/AdminStyle.css" var="mainCss"/>
    <link type="text/css" rel="stylesheet" href="${mainCss}">
</head>
<div id="wrapper">
    <body>
    <form action="/admin" method="post">
        <div class="sendmailbox">
            <h1>Send Mail</h1>
            <div class="Trio">
                <i class="fa fa-envelope" aria-hidden="true"></i>
                <input type="text" placeholder="E-mail(s)" name="emailstosend" value="" multiple>
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
        </div>
        <div class="timetable">
            <table class="table table-stripped table-bordered">
                <% Cell[][] table = (Cell[][]) request.getSession().getAttribute("table");
                    for (int i = 0; i < table.length; i++) {%>
                <tr style="background-color: blueviolet; color: white">
                    <%
                        for (int j = 0; j < table[i].length; j++) {
                            if (i == 0 && j == 0) {%>
                    <th>Time</th>
                    <%}
                    else if (i == 0) {%>
                    <th>Comp<%=j - 1%>
                    </th>
                    <%} else if (j == 0) {%>
                    <%if (i < 10) {%>
                    <th style="background-color: blueviolet">1<%=i - 1%>:00-1<%=i%>:00</th>
                    <%}
                    else if (i == 10){%>
                    <th style="background-color: blueviolet">19:00-20:00</th>
                    <%}
                    else {%>
                    <th style="background-color: blueviolet">2<%=i - 11%>:00-2<%=i - 10%>:00</th>
                    <%}%>
                    <%} else {%>
                    <th id="c<%=i + Integer.toString(j - 1)%>" style="background-color: <%=table[i][j].getColor()%>">
                        <%=table[i][j].getText()%>
                    </th>
                    <%}%>
                    <%}%>
                </tr>
                <%}%>
            </table>
        </div>
        <div class="blacklist">
            <h1>Blacklist</h1>
            <div class="Trio">
                <input type="text" placeholder="Username" name="toBlock">
                <button type="submit" class="block" name="Button" value="unblock">Unblock</button>
                <button type="submit" class="block" name="Button" value="block">Block</button>
            </div>
            <nav>
                <ul>
                    <% BlacklistDAO dao = (BlacklistDAO) request.getServletContext().getAttribute("blacklist");
                        List<String> blacklist = dao.getAll();
                        for (String username : blacklist) {%>
                    <li><span class="username" name="<%=username%>"><%=username%></span></li>
                    <%}%>
                </ul>
            </nav>
        </div>
    </form>
    <div class="hrefs">
        <a href="/reset">Reset Password!</a> <br>
        <a href="/">Log Out</a>
    </div>
    </body>
</div>
</html>

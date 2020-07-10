<%@ page import="ge.edu.freeuni.Models.User" %>
<%@ page import="ge.edu.freeuni.DAO.UsersDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="ge.edu.freeuni.DAO.ImageDAO" %>
<%@ page import="ge.edu.freeuni.Models.Image" %>
<%@ page import="ge.edu.freeuni.Models.Cell" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <spring:url value="/resources/css/HomeStyle.css" var="mainCss" />
    <link type="text/css" rel="stylesheet" href="${mainCss}">
    <title>Home Page</title>
</head>
<body>
    <img src = "../resources/theme1/images/pic.jpg" width = "150" height="150"/>
    <h1>Hello <%=((User)session.getAttribute("user")).getUsername()%></h1>
    <a href="/recChallenges">Received Challenges</a>
    <form action="/home" method="post">
        <select name = "avatar">
            <% ImageDAO db = (ImageDAO) request.getServletContext().getAttribute("images");
               List<Image> images = db.getAll();
               for (int i = 0; i < images.size(); i++) {
                   String curImage = images.get(i).getName();%>
                   <option value="<%=i%>"><%=curImage%></option>
                <%}%>
        </select>
        <button type="submit" name = "Button" value = "Change avatar">Change Avatar</button>
        <select name = "time">
            <option value="10" selected>10:00 - 11:00</option>
            <option value="11">11:00 - 12:00</option>
            <option value="12">12:00 - 13:00</option>
            <option value="13">13:00 - 14:00</option>
            <option value="14">14:00 - 15:00</option>
            <option value="15">15:00 - 16:00</option>
            <option value="16">16:00 - 17:00</option>
            <option value="17">17:00 - 18:00</option>
            <option value="18">18:00 - 19:00</option>
            <option value="19">19:00 - 20:00</option>
            <option value="20">20:00 - 21:00</option>
        </select>
        <select name = "computer">
            <option value = "comp 0" selected>Computer 0</option>
            <option value = "comp 1">Computer 1</option>
            <option value = "comp 2">Computer 2</option>
            <option value = "comp 3">Computer 3</option>
            <option value = "comp 4">Computer 4</option>
            <option value = "comp 5">Computer 5</option>
            <option value = "comp 6">Computer 6</option>
            <option value = "comp 7">Computer 7</option>
            <option value = "comp 8">Computer 8</option>
            <option value = "comp 9">Computer 9</option>
        </select>
        <button type="submit" name = "Button" value = "reserve">Reserve a seat</button>
    </form>
    <div class="hrefs">
        <a href = "/reset">Reset Password!</a><br>
        <a href="/">Log Out</a>
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
                <th style="background-color: blueviolet">1<%=i - 1%>:00-1<%=i %>:00</th>
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
</body>
</html>

<%@ page import="ge.edu.freeuni.Models.User" %>
<%@ page import="ge.edu.freeuni.DAO.UsersDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="ge.edu.freeuni.DAO.ImageDAO" %>
<%@ page import="ge.edu.freeuni.Models.Image" %>
<%@ page import="ge.edu.freeuni.Models.Cell" %>
<%@ page import="ge.edu.freeuni.DAO.TimeTableDAO" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
<head>
    <spring:url value="/resources/css/HomeStyle.css" var="bla" />
    <link type="text/css" rel="stylesheet" href="${bla}">
    <title>Home Page</title>
</head>
<form action="/home" method="post">
    <body>
    <% UsersDAO usersDAO = (UsersDAO)request.getServletContext().getAttribute("db");
        User curUser = (User)session.getAttribute("user");
        String imgfile = curUser.getAvatar();
    %>
    <img style="float: left" width="200px" height="200px"
         src="/resources/images/<%=imgfile%>.jpg"/>
    <div class="info">
        <h1 class="nameval">Hello <%=((User)session.getAttribute("user")).getUsername()%></h1><br><br><br><br><br>
        <a href="/recChallenges">Received Challenges</a><br><br>
        <a href = "/reset">Reset Password!</a><br><br>
        <a href="/">Log Out</a>

    </div> <br>
        <div class="changeavatar">
            <select name = "avatar">
            <% ImageDAO db = (ImageDAO) request.getServletContext().getAttribute("images");
                List<Image> images = db.getAll();
                for (int i = 0; i < images.size(); i++) {
                    String curImage = images.get(i).getName();%>
            <option value="<%=curImage%>"><%=curImage%></option>
            <%}%>
        </select>
        <button type="submit" name = "Button" value = "Change avatar">Change Avatar</button>
    </div>

    <div class="timetable">
        <table class="table table-stripped table-bordered">
            <% TimeTableDAO tableDAO = (TimeTableDAO) request.getServletContext().getAttribute("table");
                for (int i = 0; i < 13; i++) {%>
            <tr style="background-color: blueviolet; color: white">
                <%
                    for (int j = 0; j < 11; j++) {
                        Cell curCell = tableDAO.get(i + 9, j - 1);
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
                <th id="c<%=i + Integer.toString(j - 1)%>" style="background-color: <%=curCell.getColor()%>">
                    <%=curCell.getText()%>
                </th>
                <%}%>
                <%}%>
            </tr>
            <%}%>
        </table>
        <div class="reservation">
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
                <option value="21">21:00 - 22:00</option>
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
            <button type="submit" name = "Button" value = "reserve">Reserve a seat</button><br>
            <select name = "user">
                <% List<User> users = usersDAO.getAll();
                    for (User user:users) {
                        String username = user.getUsername();
                        if (!username.equals("admin") && !username.equals(curUser.getUsername())) {%>
                <option value = "<%=username%>"><%=username%></option>
                <%}
                }%>
            </select>
            <input type="checkbox" name="WannaChallenge"><label>Wanna Challenge</label>
            <input type="checkbox" name="PlayAlone"/><label>Play Alone</label>

            <c:if test="${error != null}">
                <div class="errorico">
                    <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
                </div> <br><br>
                <div class="eror">
                        ${error}
                </div>
            </c:if>
        </div>
    </div>
    </body>
</form>
</html>

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
    <img style="float: left" width="200px" height="200px" id="avatarr"
         src="/resources/images/${imgfile}"/>
    <div class="info">
        <h1 class="nameval">Hello <%=((User)session.getAttribute("user")).getUsername()%></h1><br><br><br><br><br>
        <h3 class="reliability">Reliability: <%=(((User)session.getAttribute("user")).getReliability()) + "/10"%></h3>
        <h3 class="bonus">Bonus coupons: <%=((User)session.getAttribute("user")).getBonus()%></h3>
        <a href="/recChallenges">Received Challenges</a><br><br>
        <a href = "/reset">Reset Password!</a><br><br>
        <a href="/">Log Out</a>
    </div> <br>
        <div class="changeavatar">
            <select name = "avatar">
            <% for (Image img : (List<Image>)request.getAttribute("images")) {%>
                <option value="<%=img.getUrl()%>"><%=img.getName()%></option>
            <%}%>
        </select>
        <button style="margin: 10px 40px;" type="submit" name = "Button" value = "Change avatar">Change Avatar</button><br>
            <a href="/info">Have a question?</a><br><br><br>
            <c:if test="${label != null}">
                <label class="message"> ${label} </label>
            </c:if>
            <c:if test="${label == null}">
                OOPS!
            </c:if>
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
            <select id="timeselect" name = "time">
                <% for (int i = 10; i <= 21; i++) {%>
                    <option value="<%=i%>>"><%=i%>:00 - <%=i + 1%>:00</option>
                <%}%>
            </select>
            <select id="timeselect" name = "computer">
                <% for (int i = 0; i < 10; i++) {%>
                    <option value = "comp <%=i%>">Computer <%=i%></option>
                <%}%>
            </select><br>
            <input id="usernamefield" type="text" value="" name="user" placeholder="Input User to Challenge"><br>
            <input type="checkbox" name="WannaChallenge"><label>Wanna Challenge</label>
            <input type="checkbox" name="PlayAlone"/><label>Play Alone</label><br>
            <button style="width: 20%" type="submit" name = "Button" value = "reserve">Reserve a seat</button><br>
            <c:if test="${error != null}">
                <div class="errorico">
                    <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
                    <div class="eror">
                            ${error}
                    </div>
                </div> <br>
            </c:if>
        </div>
    </div>
    </body>
</form>
</html>

<%@ page import="ge.edu.freeuni.DAO.BlacklistDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="ge.edu.freeuni.Models.Cell" %>
<%@ page import="ge.edu.freeuni.DAO.TimeTableDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        </div>
        <div class="buttons">
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
            <button type="submit" name = "Button" value = "reserve">Reserve a seat</button><br><br>
        </div>
<%--        <div class="checking">--%>
            <input class="check" type="text" placeholder="Username" name="check">
<%--            <div class="checkico">--%>
<%--                <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>--%>
<%--            </div>--%>
<%--        </div>--%>
        <div class="buttons">
            <select name = "timetocheck">
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
            <select name = "computertocheck">
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
            <button type="submit" name = "Button" value = "check">Check</button>
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
        <button type="submit" name = "Button" value="reset">Start New Day</button>
    </form>
    <div class="hrefs">
        <a href="/reset">Reset Password!</a> <br>
        <a href="/">Log Out</a>
    </div><br><br><br>
    <div class="errors">
        <c:if test="${error != null}">
            <div class="errorico">
                <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
            </div> <br>
            <div class="eror">
                    ${error}
            </div>
        </c:if>






        <c:if test="${contains != null}">
            <c:if test="${contains}">
<%--                esaa shesacvleli--%>
                <%System.out.println("User has reserved");%>
            </c:if>
            <c:if test="${!contains}">
<%--                                da es--%>
                <%System.out.println("User has not reserved");%>
            </c:if>
        </c:if>







    </div>
    </body>
</div>
</html>

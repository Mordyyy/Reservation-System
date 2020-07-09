<%@ page import="ge.edu.freeuni.Models.User" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/7/2020
  Time: 2:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
    <img src = "" width = "150" height="150"/>
    <h1>Hello <%=((User)session.getAttribute("user")).getUsername()%></h1>
    <a href = "/reset">Reset Password!</a> <br>
    <form>
        <select name = "time">
            <option value="10" selected>"10:00 - 11:00"</option>
            <option value="11">"11:00 - 12:00"</option>
            <option value="12">"12:00 - 13:00"</option>
            <option value="13">"13:00 - 14:00"</option>
            <option value="14">"14:00 - 15:00"</option>
            <option value="15">"15:00 - 16:00"</option>
            <option value="16">"16:00 - 17:00"</option>
            <option value="17">"17:00 - 18:00"</option>
            <option value="18">"18:00 - 19:00"</option>
            <option value="19">"19:00 - 20:00"</option>
            <option value="20">"20:00 - 21:00"</option>
        </select>
        <select name = "computer">
            <option value = "comp 0" selected>"Computer 0</option>
            <option value = "comp 1">"Computer 1</option>
            <option value = "comp 2">"Computer 2</option>
            <option value = "comp 3">"Computer 3</option>
            <option value = "comp 4">"Computer 4</option>
            <option value = "comp 5">"Computer 5</option>
            <option value = "comp 6">"Computer 6</option>
            <option value = "comp 7">"Computer 7</option>
            <option value = "comp 8">"Computer 8</option>
            <option value = "comp 9">"Computer 9</option>
        </select>
        <button type="submit" name = "Button" value = "reserve">Reserve a seat</button>
    </form>
</body>
</html>

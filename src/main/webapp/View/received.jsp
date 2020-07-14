<%@ page import="ge.edu.freeuni.Models.User" %>
<%@ page import="ge.edu.freeuni.DAO.ChallengesDAO" %>
<%@ page import="ge.edu.freeuni.Models.Challenge" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Received Challenges</title>
    <spring:url value="/resources/css/ReceivedStyle.css" var="bla" />
    <link type="text/css" rel="stylesheet" href="${bla}">
</head>
<body>
    <header>
        <h1>Received Challenges</h1>
        <a href="/home">Main Page</a>
    </header>
    <%
        User user = (User)session.getAttribute("user");
        String username = user.getUsername();
        ChallengesDAO dao = (ChallengesDAO) request.getServletContext().getAttribute("challenges");
        ArrayList<Challenge> allReceived = (ArrayList<Challenge>) dao.getAllReceived(username);
    %>
    <nav>
        <ul>
            <%
                for(Challenge ch:allReceived){%>
                    <li>
                        <form action = "/recChallenges" method = "POST">
                            <p>From: <%=ch.getFromUser()%>, Time: <%=ch.getTime()%>:00, Computer ID: <%=ch.getComputerID()%></p>
                            <input type="hidden" name = "hiddenID" value="<%=ch.getId()%>"/>
                            <button class="buts" type = "submit" id="acceptbt" name = "Button" value = "Accept">Accept</button>
                            <button class="buts" type = "submit"id="rejectbt" name = "Button" value = "Reject">Reject</button>
                        </form>
                    </li>
                <%}%>
        </ul>
    </nav>
    <div>
        <a href = "/home">Go Back</a>
    </div>
    <c:if test="${error != null}">
        <div class="errorico">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
        </div> <br><br>
        <div class="eror">
                ${error}
        </div>
    </c:if>
</body>
</html>

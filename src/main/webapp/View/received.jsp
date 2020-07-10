<%@ page import="ge.edu.freeuni.Models.User" %>
<%@ page import="ge.edu.freeuni.DAO.ChallengesDAO" %>
<%@ page import="ge.edu.freeuni.Models.Challenge" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/10/2020
  Time: 4:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Received Challenges</title>
</head>
<body>
    <h1>Received Challenges</h1>
    <%
        User user = (User)session.getAttribute("user");
        String username = user.getUsername();
        ChallengesDAO dao = (ChallengesDAO) request.getServletContext().getAttribute("challenges");
        ArrayList<Challenge> allReceived = (ArrayList<Challenge>) dao.getAllReceived(username);
    %>
    <ul>
        <%
            for(Challenge ch:allReceived){%>
                <li>
                    <form action = "/recChallenges" method = "POST">
                        <p>From:<%=ch.getFromUser()%>, Time:<%=ch.getTime()%>:00, Computer ID:<%=ch.getComputerID()%> ==></p>
                        <input type="hidden" name = "hiddenID" value="<%=ch.getId()%>"/>
                        <button type = "submit" name = "Button" value = "Accept">Accept</button>
                        <button type = "submit" name = "Button" value = "Reject">Reject</button>
                    </form>
                </li>
            <%}%>
    </ul>


</body>
</html>

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
<div id="wrapper">
<body>
    <form action="/admin" method="post">
    <div class="sendmailbox">
        <h1>Send Mail</h1>
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
    </div>
        <div class="timetable">
            <table class="table table-stripped table-bordered">
                <tr style="background-color: blueviolet; color: white" >
                    <th>Time</th>
                    <th>Comp0</th>
                    <th>Comp1</th>
                    <th>Comp2</th>
                    <th>Comp3</th>
                    <th>Comp4</th>
                    <th>Comp5</th>
                    <th>Comp6</th>
                    <th>Comp7</th>
                    <th>Comp8</th>
                    <th>Comp9</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">10:00-11:00</th>
                    <th id="c10" style="background-color: green">Free</th>
                    <th id="c11" style="background-color: green">Free</th>
                    <th id="c12" style="background-color: green">Free</th>
                    <th id="c13" style="background-color: green">Free</th>
                    <th id="c14" style="background-color: green">Free</th>
                    <th id="c15" style="background-color: green">Free</th>
                    <th id="c16" style="background-color: green">Free</th>
                    <th id="c17" style="background-color: green">Free</th>
                    <th id="c18" style="background-color: green">Free</th>
                    <th id="c19" style="background-color: green">Free</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">11:00-12:00</th>
                    <th id="c20" style="background-color: green">Free</th>
                    <th id="c21" style="background-color: green">Free</th>
                    <th id="c22" style="background-color: green">Free</th>
                    <th id="c23" style="background-color: green">Free</th>
                    <th id="c24" style="background-color: green">Free</th>
                    <th id="c25" style="background-color: green">Free</th>
                    <th id="c26" style="background-color: green">Free</th>
                    <th id="c27" style="background-color: green">Free</th>
                    <th id="c28" style="background-color: green">Free</th>
                    <th id="c29" style="background-color: green">Free</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">11:00-12:00</th>
                    <th id="c30" style="background-color: green">Free</th>
                    <th id="c31" style="background-color: green">Free</th>
                    <th id="c32" style="background-color: green">Free</th>
                    <th id="c33" style="background-color: green">Free</th>
                    <th id="c34" style="background-color: green">Free</th>
                    <th id="c35" style="background-color: green">Free</th>
                    <th id="c36" style="background-color: green">Free</th>
                    <th id="c37" style="background-color: green">Free</th>
                    <th id="c38" style="background-color: green">Free</th>
                    <th id="c39" style="background-color: green">Free</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">11:00-12:00</th>
                    <th id="c40" style="background-color: green">Free</th>
                    <th id="c41" style="background-color: green">Free</th>
                    <th id="c42" style="background-color: green">Free</th>
                    <th id="c43" style="background-color: green">Free</th>
                    <th id="c44" style="background-color: green">Free</th>
                    <th id="c45" style="background-color: green">Free</th>
                    <th id="c46" style="background-color: green">Free</th>
                    <th id="c47" style="background-color: green">Free</th>
                    <th id="c48" style="background-color: green">Free</th>
                    <th id="c49" style="background-color: green">Free</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">11:00-12:00</th>
                    <th id="c50" style="background-color: green">Free</th>
                    <th id="c51" style="background-color: green">Free</th>
                    <th id="c52" style="background-color: green">Free</th>
                    <th id="c53" style="background-color: green">Free</th>
                    <th id="c54" style="background-color: green">Free</th>
                    <th id="c55" style="background-color: green">Free</th>
                    <th id="c56" style="background-color: green">Free</th>
                    <th id="c57" style="background-color: green">Free</th>
                    <th id="c58" style="background-color: green">Free</th>
                    <th id="c59" style="background-color: green">Free</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">11:00-12:00</th>
                    <th id="c60" style="background-color: green">Free</th>
                    <th id="c61" style="background-color: green">Free</th>
                    <th id="c62" style="background-color: green">Free</th>
                    <th id="c63" style="background-color: green">Free</th>
                    <th id="c64" style="background-color: green">Free</th>
                    <th id="c65" style="background-color: green">Free</th>
                    <th id="c66" style="background-color: green">Free</th>
                    <th id="c67" style="background-color: green">Free</th>
                    <th id="c68" style="background-color: green">Free</th>
                    <th id="c69" style="background-color: green">Free</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">11:00-12:00</th>
                    <th id="c70" style="background-color: green">Free</th>
                    <th id="c71" style="background-color: green">Free</th>
                    <th id="c72" style="background-color: green">Free</th>
                    <th id="c73" style="background-color: green">Free</th>
                    <th id="c74" style="background-color: green">Free</th>
                    <th id="c75" style="background-color: green">Free</th>
                    <th id="c76" style="background-color: green">Free</th>
                    <th id="c77" style="background-color: green">Free</th>
                    <th id="c78" style="background-color: green">Free</th>
                    <th id="c79" style="background-color: green">Free</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">11:00-12:00</th>
                    <th id="c80" style="background-color: green">Free</th>
                    <th id="c81" style="background-color: green">Free</th>
                    <th id="c82" style="background-color: green">Free</th>
                    <th id="c83" style="background-color: green">Free</th>
                    <th id="c84" style="background-color: green">Free</th>
                    <th id="c85" style="background-color: green">Free</th>
                    <th id="c86" style="background-color: green">Free</th>
                    <th id="c87" style="background-color: green">Free</th>
                    <th id="c88" style="background-color: green">Free</th>
                    <th id="c89" style="background-color: green">Free</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">11:00-12:00</th>
                    <th id="c90" style="background-color: green">Free</th>
                    <th id="c91" style="background-color: green">Free</th>
                    <th id="c92" style="background-color: green">Free</th>
                    <th id="c93" style="background-color: green">Free</th>
                    <th id="c94" style="background-color: green">Free</th>
                    <th id="c95" style="background-color: green">Free</th>
                    <th id="c96" style="background-color: green">Free</th>
                    <th id="c97" style="background-color: green">Free</th>
                    <th id="c98" style="background-color: green">Free</th>
                    <th id="c99" style="background-color: green">Free</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">11:00-12:00</th>
                    <th id="c100" style="background-color: green">Free</th>
                    <th id="c101" style="background-color: green">Free</th>
                    <th id="c102" style="background-color: green">Free</th>
                    <th id="c103" style="background-color: green">Free</th>
                    <th id="c104" style="background-color: green">Free</th>
                    <th id="c105" style="background-color: green">Free</th>
                    <th id="c106" style="background-color: green">Free</th>
                    <th id="c107" style="background-color: green">Free</th>
                    <th id="c108" style="background-color: green">Free</th>
                    <th id="c109" style="background-color: green">Free</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">11:00-12:00</th>
                    <th id="c110" style="background-color: green">Free</th>
                    <th id="c111" style="background-color: green">Free</th>
                    <th id="c112" style="background-color: green">Free</th>
                    <th id="c113" style="background-color: green">Free</th>
                    <th id="c114" style="background-color: green">Free</th>
                    <th id="c115" style="background-color: green">Free</th>
                    <th id="c116" style="background-color: green">Free</th>
                    <th id="c117" style="background-color: green">Free</th>
                    <th id="c118" style="background-color: green">Free</th>
                    <th id="c119" style="background-color: green">Free</th>
                </tr>
                <tr style="background-color: white; color: white">
                    <th style="background-color: blueviolet">11:00-12:00</th>
                    <th id="c120" style="background-color: green">Free</th>
                    <th id="c121" style="background-color: green">Free</th>
                    <th id="c122" style="background-color: green">Free</th>
                    <th id="c123" style="background-color: green">Free</th>
                    <th id="c124" style="background-color: green">Free</th>
                    <th id="c125" style="background-color: green">Free</th>
                    <th id="c126" style="background-color: green">Free</th>
                    <th id="c127" style="background-color: green">Free</th>
                    <th id="c128" style="background-color: green">Free</th>
                    <th id="c129" style="background-color: green">Free</th>
                </tr>
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
                <% BlacklistDAO dao = (BlacklistDAO)request.getServletContext().getAttribute("blacklist");
                   List<String> blacklist = dao.getAll();
                   for (String username: blacklist) {%>
                       <li><span class="username" name="<%=username%>"><%=username%></span></li>
                   <%}%>
            </ul>
        </nav>
    </div>
    </form>
    <div class="logout">
        <a href="">Log Out</a>
    </div>
</body>
</div>
</html>

<%-- 
    Document   : home.jsp
    Created on : Sep 24, 2015, 6:47:02 PM
    Author     : xl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/includes/header.jsp" />
<c:import url="/includes/sidebar.jsp" />

<!-- box for inputing tweets into the system. needs servlet to utilize this form-->
<div id ="tweet">
    <form action = "tweet" method="post">
        <input type="hidden" name="action" value="tweet">
        <textarea rows="4" cols="70" value="text" required></textarea>
        <p></p>
        <input type="submit" value="Tweet" class="margin_left" align="right">
    </form>
</div>

<!-- section for user to view all available tweets. currently not functional. 

    <c:forEach var = "i" items="${tweets}">
        <div id="viewTweets">
            <h1${i.email} ${i.date}</h1>
            <p>${i.text}</p>
        </div>
    </c:forEach>
-->
<c:import url="/includes/footer.jsp" />

<%-- 
    Document   : home.jsp
    Created on : Sep 24, 2015, 6:47:02 PM
    Author     : xl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    
    <style>
        #twit{
            line-height:30px;
            background-color:#FFFFFF;
            height:150px;
            width:700px;
            padding:5px;
            top: 220px;
            border-radius: 1em;
            left: 50%;
            margin-left: auto;
            margin-right: auto;
        }
        #twits{
            line-height:30px;
            background-color:#FFFFFF;
            height:100px;
            width:700px;
            padding:5px;
            top: 220px;
            border-radius: 1em;
            left: 50%;
            margin-left: auto;
            margin-right: auto;
        }
        textArea {
            margin-left: auto;
            margin-right: auto;
            left:50%;
            
        }
    </style>
    <div>
        <c:import url="/includes/header.jsp" />
        <c:import url="/includes/sidebar.jsp" />
        <c:import url="/includes/whoToFollow.jsp" />

        <!-- box for inputing twits into the system. needs servlet to utilize this form-->
        <div>
            <div id ="twit">
                <form action="twit" method="post">
                    <input type="hidden" name="action" value="twit">
                    <textarea rows="4" cols="70" name="content" required></textarea>
                    <p></p>
                    <input type="submit" name="Twit" class="margin_left" align="right">
                </form>
            </div>

            <p>Here are all of the twits</p>
            <p>${user}</p>
            <c:forEach var = "i" items="${twits}">
                <p>after this comes a twit</p>
                <div id="viewTwits">
                    <p>Here comes a twit</p>
                    
                    <p>${i.userId} ${i.postedDate} ${i.content}</p>
                </div>
            </c:forEach>
        </div>
        <c:import url="/includes/footer.jsp" />
    </div>
</html>
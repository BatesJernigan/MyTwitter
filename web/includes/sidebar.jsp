<%-- 
    Document   : sidebar
    Created on : Oct 25, 2015, 1:17:48 PM
    Author     : Garrick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p></p>
<div id ="nav">
    <div id="user">
        <p></p>
        <h1>${user.fullName}</h1>
        <p>@${user.nickname}</p>
        <img src="uploadFiles/${user.profilePicture}" height="160" width="160">
    </div>
        <p> </p>
    <div id="trending">  
        <h1>Trending</h1>
        <c:forEach var = "twit" items="${trendingTwits}">
            <div class="smallTwit">
                <p>[@${twit.nickname}]: ${twit.postedDate}</p>
                <p>${twit.content}</p>
            </div>
        </c:forEach>
    </div>
</div>

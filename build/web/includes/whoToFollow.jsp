<%-- 
    Document   : whoToFollow
    Created on : Oct 29, 2015, 8:59:31 PM
    Author     : Garrick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <style>
        #follow {
            line-height:30px;
            background-color:#FFFFFF;
            height:500px;
            width:200px;
            float:right;
            padding:5px;	      
        }        
    </style>
    <div id="follow">
        <h1>Who to follow</h1>
        <c:forEach var = "i" items="${tweets}">
        <div id="viewTweets">
            <h1${i.email} ${i.date}</h1>
            <p>${i.text}</p>
        </div>
        </c:forEach>

    </div>
</html>

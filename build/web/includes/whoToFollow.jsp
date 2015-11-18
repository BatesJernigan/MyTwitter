<%-- 
    Document   : whoToFollow
    Created on : Oct 29, 2015, 8:59:31 PM
    Author     : Garrick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <c:forEach var = "user" items="${users}">
        <div>
            <p>
                ${user.fullName}<br>
                @${user.nickname}
            </p>

        </div>
    </c:forEach>

    </div>
</html>

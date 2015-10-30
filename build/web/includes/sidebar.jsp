<%-- 
    Document   : sidebar
    Created on : Oct 25, 2015, 1:17:48 PM
    Author     : Garrick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <style>
        #nav {
            line-height:30px;
            background-color:#FFFFFF;
            height:500px;
            width:200px;
            float:left;
            padding:5px;	      
        }    
    </style>
    <p></p>
    <div id="nav">
        <h1>${session.getAttribute(user.fullName)}</h1>
        <p>${session.getAttribute(user.nickname)}</p>
        
        <h1>Trending</h1>
        <p>some interesting information will eventually go here.</p>
    </div>

</html>

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
            float:left;	
            border-radius: 1em;
        }
        #user {
            line-height:30px;
            background-color:#FFFFFF;
            height:200px;
            width:200px;
            padding:5px;
   
        }
        #trending {
            line-height:30px;
            background-color:#FFFFFF;
            height:500px;
            width:200px;
            padding:5px;
            
        }
    </style>
    <p></p>
    <div id ="nav">
        <div id="user">
            <p></p>
            <img src=${picture} alt="User image" style="width:100px;height:100px;">
            <h1>${user.fullName}</h1>
            <p>@${user.nickname}</p>
        </div>
            <p> </p>
        <div id="trending">
            <h1>Trending</h1>
            <p>some interesting information will eventually go here.</p>
        </div>
    </div>
</html>

<!DOCTYPE html>
<%-- 
    Document   : header.jsp
    Created on : Sep 24, 2015, 6:44:58 PM
    Author     : xl
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Twitter</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
        <script src="includes/main.js" type="javascript"></script>
    </head>
    
    <!-- code to display menu dependent on what page user is currently on -->
    <c:if test="${true}">
        <div id="header">
            <ul>
                <li><a href="home.jsp">Home</a></li>
                <li><a href="#news">Notifications</a></li>
                <li><a href="signup.jsp">Profile</a></li>
            </ul>
        </div>
    </c:if>

    <c:if test="${user.nickname != null}">
        <span>${user.nickname}</span>
    </c:if>
    <body>

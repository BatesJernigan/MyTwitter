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
        <title>MyTwitter</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head> 

    <c:if test="${user.nickname != null}">
        <span>${user.nickname}</span>
    </c:if>
</html>
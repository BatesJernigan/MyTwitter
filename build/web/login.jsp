<%-- 
    Document   : login.jsp
    Created on : Sep 24, 2015, 6:44:58 PM
    Author     : xl
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head> 
    <body>
        <h1>Welcome!</h1>
        <h2>Log in below</h2>
        <c:if test="${message != null}">
            <span>${message}</span>
        </c:if>
        <form action="membership" method="post">
            <input type="hidden" name="action" value="authenticate">
            <label class="pad_top">Email:</label>
            <input type="email" name="email" value="${user.email}"><br>
            <label class="pad_top">Password:</label>
            <input type="password" name="password"><br>
            <input type="submit" value="Log in" class="margin_left">
            <a href='./forgotpassword.jsp'>Forgot password?</a>

            <p>
                New? <a href='./signup.jsp'>Sign up now!</a>
            </p>
        </form>
    </body>
<c:import url="/includes/footer.jsp" />

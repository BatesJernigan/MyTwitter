<%-- 
    Document   : login.jsp
    Created on : Sep 24, 2015, 6:44:58 PM
    Author     : xl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="login" method="post">
            <input type="hidden" name="action" value="add">
            <label class="pad_top">Email:</label>
            <input type="email" name="user" value="${user.emailAddress}"><br>
            <label class="pad_top">Password:</label>
            <input type="text" name="user" value="${user.password}"><br>

            <input type="submit" value="Log in" class="margin_left">
            <a href='./forgotpassword.jsp'>Forgot password?</a>
        </form>
    </body>
</html>

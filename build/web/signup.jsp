<%-- 
    Document   : signup.jsp
    Created on : Sep 24, 2015, 6:44:47 PM
    Author     : xl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign-up Page</title>
    </head>
    <body>
        <h1>Welcome to My Twitter Website!</h1>
        <p>
            Please sign up and then feel free to look around that site! :)
        </p>
        <jsp:useBean id="user" scope="session" class="business.User"/>    
        <form action="signup" method="post">
            <input type="hidden" name="action" value="add">
            <label class="pad_top">Full Name:</label>
            <input type="text" name="user" value="${user.fullName}"><br>
            <label class="pad_top">Email:</label>
            <input type="email" name="user" value="${user.emailAddress}"><br>
            <label class="pad_top">Nickname:</label>
            <input type="text" name="user" value="${user.nickname}"><br>
            <label class="pad_top">Password:</label>
            <input type="text" name="user" value="${user.password}"><br>
            <select>
                <option>Month</option>
                <%
                    for(int i = 1; i <= 12; i++) {
                %>
                <option value=${i}><%= i+"" %></option>
                <% } %>
            </select>
            <select>
                <option>Day</option>
                <%
                    for(int i = 1; i <= 31; i++) {
                %>
                <option value=${i}><%= i+"" %></option>
                <% } %>
            </select>
            <select>
                <option>Year</option>
                <%
                    for(int i = 1980; i <= 2016; i++) {
                %>
                <option value=${i}><%= i+"" %></option>
                <% } %>
            </select>
                
            </option>
            <label>&nbsp;</label>
            <p>
                <input type="submit" value="Click Me!" class="margin_left">
                <input type="reset" value="No don't Go!" class="margin_left">
            </p>
        </form>
    </body>
</html>

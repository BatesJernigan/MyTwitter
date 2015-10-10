<%-- 
    Document   : signup.jsp
    Created on : Sep 24, 2015, 6:44:47 PM
    Author     : xl
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/includes/header.jsp" />
<h1>Welcome to My Twitter Website!</h1>
<p>
    Please sign up and then feel free to look around that site! :)
</p>
<jsp:useBean id="user" scope="session" class="business.User"/>    
<form action="signup" method="post">
    <input type="hidden" name="action" value="add">
    <label class="pad_top">Full Name:</label>
    <input type="text" name="fullName" value="${user.fullName}" required><br>
    <label class="pad_top">Email:</label>
    <input type="email" name="email" value="${user.email}" required><br>
    <label class="pad_top">Nickname:</label>
    <input type="text" name="nickname" value="${user.nickname}" required><br>
    <label class="pad_top">Password:</label>
    <input type="text" name="password" value="${user.password}" required><br>
    <select type='number' name='month' required>
        <option>Month</option>
        <%
            for(int i = 1; i <= 12; i++) {
        %>
        <option><%= i %></option>
        <% } %>
    </select>
    <select type='number' name='day' required>
        <option>Day</option>
        <%
            for(int i = 1; i <= 31; i++) {
        %>
        <option><%= i %></option>
        <% } %>
    </select>
    <select type='number' name='year' required>
        <option>Year</option>
        <%
            for(int i = 1980; i <= 2016; i++) {
        %>
        <option><%= i %></option>
        <% } %>
    </select>
    
    <label>&nbsp;</label>
    <p>
        <input type="submit" value="Submit" class="margin_left">
        <input type="reset" value="Clear Fields" class="margin_left">
    </p>
</form>
<c:import url="/includes/footer.jsp" />

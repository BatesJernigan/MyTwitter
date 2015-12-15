<%-- 
    Document   : whoToFollow
    Created on : Oct 29, 2015, 8:59:31 PM
    Author     : Garrick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="follow">
    <h1>Who to follow</h1>
    <c:forEach var = "notFollowedUser" items="${notFollowingList}">
        <p>${notFollowedUser.fullName} [@${notFollowedUser.nickname}]
        <form>
            <input type ="submit" name=action" value ="follow">
            <input type ="hidden" name="action" value = "follow">
            <input type ="hidden" name="followed" value = "${notFollowedUser.id}">
            <input type ="hidden" name ="user" value ="${notFollowedUser.id}">
        </form>
    </c:forEach>
    <c:forEach var = "followedUser" items="${followingList}">
        <p>${followedUser.fullName} [@${followedUser.nickname}]
        <form>
            <input type="submit" name="action" value="unfollow">
            <input type ="hidden" name="action" value = "unfollow">
            <input type ="hidden" name="followed" value = "${followedUser.id}">
            <input type ="hidden" name ="user" value ="${followedUser.id}">
        </form>
    </c:forEach>
</div>

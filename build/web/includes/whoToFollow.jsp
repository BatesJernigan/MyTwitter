<%-- 
    Document   : whoToFollow
    Created on : Oct 29, 2015, 8:59:31 PM
    Author     : Garrick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="follow">
    <h1>Who to follow</h1>
    <c:forEach var = "users" items="${users}">
        <div>
            <p>${users.fullName} [@${users.nickname}]
            <form>
                <c:choose>
                    <c:forEach var = "followed" items="${follows}">
                        <c:when test="${followed.followed} == ${users.id}">
                           <input type="submit" name="action" value="unfollow">
                        </c:when>
                    </c:forEach>
                           
                    <c:otherwise>
                        <input type ="submit" name=action" value ="follow">
                    </c:otherwise>
                </c:choose>
                <input type ="hidden" name="followed" value = "${users.id}">
                <input type ="hidden" name ="user" value ="${user.id}">
            </form>
            </p>
        </div>
    </c:forEach>
</div>

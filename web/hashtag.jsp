<%-- 
    Document   : hashtag
    Created on : Dec 12, 2015, 10:38:29 AM
    Author     : batesjernigan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
    <c:import url="/includes/header.jsp" />
    <c:import url="/includes/sidebar.jsp" />
    <c:import url="/includes/whoToFollow.jsp" />

    <!-- box for inputing twits into the system. needs servlet to utilize this form-->
    <h2>All twits talking about ${hashtagContent}</h2>

    <p>
    <c:forEach var = "twit" items="${twits}">
        <form action="twit" method="post" class="twits">
            <input type="hidden" name="twitId" value="${twit.twitId}">
            <input type="hidden" name="userId" value="${twit.userId}">
            <p>[@${twit.nickname}]: ${twit.postedDate} 
                <c:if test = "${user.id == twit.userId}">
                    <input type="submit" name="action" value="Delete">
                </c:if>
            </p>
            <p>${twit.content}</p>
            
        <!--<input type="hidden" name="action" value="logout">-->
        
        </form>
        <p/>
    </c:forEach>
    <c:import url="/includes/footer.jsp" />
</body>

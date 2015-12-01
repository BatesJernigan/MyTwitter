<%-- 
    Document   : home.jsp
    Created on : Sep 24, 2015, 6:47:02 PM
    Author     : xl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
    <c:import url="/includes/header.jsp" />
    <c:import url="/includes/sidebar.jsp" />
    <c:import url="/includes/whoToFollow.jsp" />

    <!-- box for inputing twits into the system. needs servlet to utilize this form-->
    <div class="twits">
        <form action="twit" method="post">
            <input type="hidden" name="action" value="twit">
            <textarea rows="4" cols="70" name="content" required maxlength="200"></textarea>
            <p></p>
            <input type="submit" name="Twit" class="margin_left" align="right">
        </form>
    </div>

    <p/>
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
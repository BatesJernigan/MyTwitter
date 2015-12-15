<%-- 
    Document   : notifications
    Created on : Dec 15, 2015, 8:02:26 AM
    Author     : Garrick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
    <c:import url="/includes/header.jsp" />
    <c:import url="/includes/sidebar.jsp" />
    <c:import url="/includes/whoToFollow.jsp" />
    
    
        
        
    <c:forEach var = "twit" items="${twits}">
        <c:if test ="${twit.postedDate}.after(${lastlogin})">
            <form action="twit" method="post" class="twits">
                <input type="hidden" name="twitId" value="${twit.twitId}">
                <input type="hidden" name="userId" value="${twit.userId}">
                <p>[@${twit.nickname}]: ${twit.postedDate} 
                    <c:if test = "${user.id == twit.userId}">
                        <input type="submit" name="action" value="Delete">
                    </c:if>
                </p>
                <p>${twit.content}</p>
            </form>
            <p/>
        </c:if>
    </c:forEach>
    <c:import url="/includes/footer.jsp" />
</body>

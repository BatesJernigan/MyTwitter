<%-- 
    Document   : forgotpassword
    Created on : Oct 8, 2015, 11:31:43 PM
    Author     : batesjernigan
--%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head> 
    <body>
        <h1>Forgot your password?</h1>
        <p>Enter your Email below, an email will be sent to change your password</p>
        <c:if test="${message != null}">
            <span>${message}</span>
        </c:if>
        <form action="membership" method="post">
            <input type="hidden" name="action" value="password">
            <label class="pad_top">Email:</label>
            <input type="email" name="email" value="${user.email}"><br>
            <input type="submit" value="Reset Password" class="margin_left">
        </form>
    </body>
<c:import url="/includes/footer.jsp" />
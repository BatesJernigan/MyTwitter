<%@ page import="java.util.GregorianCalendar, java.util.Calendar" %>
<%  
    GregorianCalendar currentDate = new GregorianCalendar();
    int currentYear = currentDate.get(Calendar.YEAR);
%>
<div id ="footer">
    <p>&copy; Copyright <%= currentYear %> Bates Jernigan &amp; Garrick White </p>
</div>
</html>
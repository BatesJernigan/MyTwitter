<%@ page import="java.util.GregorianCalendar, java.util.Calendar" %>
<%  
    GregorianCalendar currentDate = new GregorianCalendar();
    int currentYear = currentDate.get(Calendar.YEAR);
%>

<html>
    <style>
        #footer {
            background-color:white;
            color:black;
            text-align:center;
            padding:5px;
            
            bottom: 0;
        }
        
    </style>
    <div id ="footer">
        <p>&copy; Copyright <%= currentYear %> Bates Jernigan &amp; Garrick White </p>
    </div>
</html>
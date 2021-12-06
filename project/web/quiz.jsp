<%-- 
    Document   : quiz
    Created on : 2021-nov-25, 22:02:46
    Author     : alcaj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Quiz page!</h1>
        
        <form name="question-form" method="POST">
        <%  
            Question[] questions = (Question[])session.getAttribute("questions");
            for(int i = 0; i < questions.length; i++) {
                
                    
        %>
            
            <%= i+1 + ": " + questions[i].getText()%> <br>
            
        <%
            String[] options = questions[i].getOptions();
                for(int j = 0; j < options.length; j++) {
        %>
        <input type="checkbox" name=<%="question"+i+"box"+j%> value="ON" /> <%= options[j] %>  <br>
            
        <%      
                }
                out.println("<br>");
            }
        %>
        
        
            <input type="hidden" name="action" value="submitquiz"/>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>

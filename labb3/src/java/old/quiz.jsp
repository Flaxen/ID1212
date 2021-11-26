<%-- 
    Document   : quiz
    Created on : 2021-nov-24, 13:55:48
    Author     : alcaj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Quizzes</h1>
        Quiz ID, Results, RETAKE
        <jsp:useBean id="quizBean" scope="session" class="test.Quiz" />
        <br>
        <%
            for(int i = 0; i < quizBean.getQuizAmount(); i++) {
                out.println("Quiz: " + quizBean.getQuizId() + " " + quizBean.getQuizName()
                + " Score: " + quizBean.getScore());
            }
        %>
        
        
        
        
    </body>
</html>

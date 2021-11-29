<%-- 
    Document   : home
    Created on : 2021-nov-24, 21:45:44
    Author     : alcaj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controller.*" %>
<%@page import="logic.*" %>
<%@page import="model.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <div style="float:right">
            

            Currently logged in as 
            <%
                User u = ((User)session.getAttribute("user"));
                out.println(u.getUsername());
            %>
            
            <form style="float:right" name="logout form" method="POST">
                <input type="submit" value="logout" name="logout" />
                <input type="hidden" value="logout" name="action" />
            </form>
            
            
        </div>
        
        <h1>Quiz menu</h1>
                
        <br> Available quizzes: <br><br>
            <%
                Quiz[] quizzes = (Quiz[])application.getAttribute("quizzes");
                Result[] cast = (Result[])session.getAttribute("userresults");
                int i = 0;
                for (Quiz q : quizzes) {    
                    out.println(q.getSubject());
                    Result res = cast[i++];
                    if(res == null || res.getQuizId() != q.getId()) {
                        out.println("| Score: 0");
                        i--;
                    } else {
                        out.println("| Score: " + res.getScore());
                    }


            %>
       
        
        <form name="logout form" method="POST">
                <input type="submit" value="Take quiz" name="button" />
                <input type="hidden" value=
                       <%= "takequiz:" + q.getId() %>
                       name="action" />
        </form>
        <br>
        
        <%
            }
        %>
        
        
    </body>
</html>

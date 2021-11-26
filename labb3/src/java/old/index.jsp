<%-- 
    Document   : index
    Created on : 19-Nov-2021, 14:46:38
    Author     : Flaxen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Quiz page</h1>
        <form name="Name Input Form" method="POST">
            username:
            <input type="text" name="username" /><br>
            password:
            <input type="password" name="password" />

            <input type="submit" name="login" value="login" />
            <p style="color:red"> 
                <% 
                    if(request.getAttribute("login") == null) {
                        out.println("");
                    } else {
                        out.println("Wrong username or password");
                    }
                %> 
            </p>
        </form>
        
    </body>
</html>
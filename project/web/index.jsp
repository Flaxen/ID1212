<%-- 
    Document   : index
    Created on : 2021-nov-24, 16:04:41
    Author     : alcaj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Login</h1>
        <form method="post" action="Controller">
            Username <input type="text" name="username"><br>
            Password <input type="password" name="password"><br>
            <input type="hidden" name="action" value="login">
            <input type="submit" value="Login">
            
            <p style="color:red">
                <% 
                    if("false".equals(request.getAttribute("loginState"))) {
                        out.println("Wrong username or password");
                    }
                %>
            </p>
            
        </form>
    </body>
</html>


<%-- 
    Document   : response
    Created on : 19-Nov-2021, 15:03:29
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
        <form style="float:right" name="logout form" method="POST">
            <input type="submit" value="logout" name="logout" />
        </form>
        

        <jsp:useBean id="mybean" scope="session" class="test.NameHandler" />
        <% mybean.setUserID(request.getAttribute("userID").toString()); %>
        <!--jsp:setProperty name="mybean" property="userID" value=""/-->
        <h1>Hello user <jsp:getProperty name="mybean" property="userID" />!</h1>
    </body>
</html>
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
                User u = ((User) session.getAttribute("user"));
                out.println(u.getUsername());
            %>

            <form style="float:right" name="logout form" method="POST">
                <input type="submit" value="logout" name="logout" />
                <input type="hidden" value="logout" name="action" />
            </form>


        </div>

        <h1>Queues</h1>
        <table border="0">

            <tbody>
                <tr>


            <br> Available queues: <br><br>
            <%
                Room[] rooms = (Room[]) session.getAttribute("rooms");
                for (Room r : rooms) {
                    out.println("<td>"+ r.getCourseCode() + "</td><td> - </td><td>" +  r.getName() +
                            "</td><td> | </td><td> In queue: " + r.getLength() + "</td>");


            %>

            <td>
            <form name="logout form" method="POST">
                <input type="submit" value="View queue" name="button" />
                <input type="hidden" value=
                       <%= "viewqueue:" + r.getId()%>
                       name="action" />
            </form>
            </td>
        </tr>




<%
    }
%>

    </tbody>
</table>
</body>
</html>

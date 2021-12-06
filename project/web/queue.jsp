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
        <%
        Room room = (Room)session.getAttribute("room");
        %>
        <h1><%=room.getCourseCode()%> <%=room.getName()%></h1>
        <div style="width:50%; float:left">
            <table border="1">
                <thead>
                    <tr>
                        <th>User</th>
                        <th>Location</th>
                        <th>Comment</th>
                        <th>Action</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>



                    <%
                        Queue[] queue = (Queue[]) session.getAttribute("queue");
                        for (int i = 0; i < queue.length; i++) {


                    %>

                    <tr>
                        <td><%= queue[i].getUsername()%></td>
                        <td><%= queue[i].getLocation()%></td>
                        <td><%= queue[i].getSubject()%></td>
                        <td><%= queue[i].getAction()%></td>
                        <td><%= queue[i].getStatus()%></td>
                        <%
                        if("admin".equals(session.getAttribute("role"))) {
                        %>
                        <td>
                            <form method="POST">
                                <input type="hidden" value="togglehelp:<%=queue[i].getUsername()%>" name="action"/>
                                <input type="submit" value="Toggle Help" />
                            </form>
                        <td>
                        <td>
                            <form method="POST">
                                <input type="hidden" value="leavequeue:<%=queue[i].getUsername()%>" name="action"/>
                                <input type="submit" value="Remove from queue" />
                            </form>
                        <td>
                        <%    
                        }
                        %>
                    </tr>

                    <%
                        }
                    %>



                </tbody>
            </table> 
        </div>

        <div style="width:50%; float:left">
            <%
                if ((int)session.getAttribute("userInQueue") == 2) {
                } else if("admin".equals(session.getAttribute("role"))) {
                    
            %>
            
            
            
            <%
                    
                } else if((int)session.getAttribute("userInQueue") == 1){
            %>

            <form method="post" name="join queue form">
                <input type="hidden" name="action" value="leavequeue:<%=u.getUsername()%>"><br>
                <input type="submit" value="Leave Queue">
            </form>

            <%
                } else {

            %>
            
            <form method="post" name="join queue form">
                Location <input type="text" name="location"><br>
                Subject <input type="text" name="subject"><br>
                Present 
                <input type="radio" name="presenthelp" value="present" />
                Help
                <input type="radio" name="presenthelp" value="help" />
                
                <input type="hidden" name="action" value="join"><br>
                <input type="submit" value="Join Queue">
            </form>
            
            <%
                }
            %>

        </div>
    </body>
</html>

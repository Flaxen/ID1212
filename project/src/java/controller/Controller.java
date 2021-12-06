package controller;

import model.*;
import logic.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    DbHandler db = new DbHandler();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Creating the session object has been moved to getpost (compared to the lecture)
        HttpSession session = request.getSession(true);
        //User u;

        System.out.println(session.isNew() + " " + session.getAttribute("user"));
        if (session.isNew() || session.getAttribute("user") == null) {
            System.out.println("uninlogged get:" + session + " created " + session.getCreationTime() + " " + java.lang.System.currentTimeMillis());
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);

        } else {
            // retrieve the already existring user
            //u = (User) session.getAttribute("user");
            DbHandler dbh = new DbHandler();
            Room[] rooms = dbh.getRooms();
            session.setAttribute("rooms", rooms);
            RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
            rd.forward(request, response);

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        User u;
        if (session.isNew() || session.getAttribute("user") == null) {
            System.out.println("in post new session");
            u = new User();
            u.setUsername(request.getParameter("username"));
            u.setPassword(request.getParameter("password"));
            session.setAttribute("user", u);
            System.out.println("no user, creating user " + session.getAttribute("user"));
        } else {

            // retrieve the already existring user
            u = (User) session.getAttribute("user");
            System.out.println("already logged in, user already " + u);

        }
        ServletContext application = request.getServletContext();
        DbHandler dbh = (DbHandler) application.getAttribute("dbh");
        if (dbh == null) {
            dbh = new DbHandler();
        }
        User[] users = dbh.getUsers();
        application.setAttribute("users", users);

        // control url flow
        // check if user is authorized with the "dbh" object
        if ("login".equals(request.getParameter("action"))) {

            if (dbh.validate(u)) {

                
                Room[] rooms = dbh.getRooms();
                session.setAttribute("rooms", rooms);
                
                RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
                rd.forward(request, response);
            } else {

                request.setAttribute("loginState", "false");
                session.setAttribute("user", null);
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            }

        } else if ("logout".equals(request.getParameter("action"))) {
            //request.setAttribute("loginState", "false");
            System.out.println("logout ongoing pog");
            session.invalidate();
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);

        } else if (request.getParameter("action") != null && request.getParameter("action")
                .startsWith("viewqueue:")) {
            int qid = Integer.parseInt(request.getParameter("action").split(":")[1]);
            session.setAttribute("queueId", qid);
            // users is set every time no need for it here
            Queue[] queue = dbh.getQueue(qid);
            String role = dbh.getRole(u, qid);
            int userInQueue = dbh.userInQueue(u, qid);
            Room room = dbh.getRoom(qid);
            
            session.setAttribute("room", room);
            session.setAttribute("userInQueue", userInQueue);
            session.setAttribute("role", role);
            session.setAttribute("queue", queue);
            session.setAttribute("queuename", qid);
            
            
            RequestDispatcher rd = request.getRequestDispatcher("/queue.jsp");
            rd.forward(request, response);
        } else if ("join".equals(request.getParameter("action"))) {
            
            dbh.addUserToQueue(u, (int)session.getAttribute("queuename"), request);
            //System.out.println("test delay");
            RequestDispatcher rd = request.getRequestDispatcher("/queue.jsp");
            rd.forward(request, response);
        } else if(request.getParameter("action") != null && request.getParameter("action")
                .startsWith("leavequeue:")) {
            
            dbh.removeFromQueue(request.getParameter("action").split(":")[1]);
            RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
            rd.forward(request, response);
        } else if(request.getParameter("action") != null && request.getParameter("action")
                .startsWith("togglehelp:")) {
            
            dbh.toggleHelp(request.getParameter("action").split(":")[1]);
            
        } else {
            out.println("post request error, unknown action<br>");
            out.println(request.getParameter("action"));
            out.println(request.getParameter("presenthelp"));
        }

    }

}

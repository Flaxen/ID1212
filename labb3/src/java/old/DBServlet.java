package old;

import old.Logic;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.Cookie;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import old.Logic;

@WebServlet(name = "DBServlet", urlPatterns = {"/DBServlet"})
public class DBServlet extends HttpServlet {
    
    private Logic logic = new Logic();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("in get");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("Connecting to DB. <br>");
        try {
            Context initContext = new InitialContext();
            Context envContext
                    = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
            Connection conn = ds.getConnection();

//            Cookie[] cookies = request.getCookies();
//            for (int i = 0; i < cookies.length; i++) {
//
//                out.println(cookies[i].getName() + "<br>");
//            }
//
//            PrintWriter newnew = response.getWriter();
//            response.setContentType("text/html;charset=UTF-8");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.println("Finished.");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("in post");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("Connecting to DB. <br>");

        if (request.getParameter("login") != null && request.getAttribute("userID") == null) {
            System.out.println("login is: " + request.getParameter("login") + " userID: " + request.getAttribute("userID"));

            int res = logic.login(request);
            if (res != -1) {
                request.setAttribute("userID", res);
                logic.redirectUrl(request, response, "response.jsp");
            } else {
                request.setAttribute("login", "wrong");
                request.setAttribute("userID", null);
                logic.redirectUrl(request, response, "index.jsp");


            }

        } else if (request.getParameter("logout") != null) {
            System.out.println("logout is: " + request.getParameter("logout"));
            request.setAttribute("userID", null);
            request.setAttribute("password", "");
            logic.redirectUrl(request, response, "index.jsp");


        } else if (request.getParameter("temp") != null) {
            logic.redirectUrl(request, response, "index.jsp");

        } else {
            out.println("error lost if else");
        }

        out.println("Finished.");
    }
}

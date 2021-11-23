package test;

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

@WebServlet(name = "DBServlet", urlPatterns = {"/DBServlet"})
public class DBServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            System.out.println("in get");

        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.println("Finished.");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("Connecting to DB. <br>");
        try {
            Context initContext = new InitialContext();
            Context envContext
                    = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
            Connection conn = ds.getConnection();

            System.out.println("in post");
            RequestDispatcher rd;
            
            if(request.getParameter("login") != null) {
                int res = Login.login(request, conn);
                if (res != -1) {
                    request.setAttribute("userID", res);
                    rd = request.getRequestDispatcher("response.jsp");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("login", "wrong");
                    request.setAttribute("userID", null);
                    rd = request.getRequestDispatcher("index.jsp");
                    rd.forward(request, response);

                }
                
            } else if(request.getParameter("logout") != null) {
                request.setAttribute("userID", null);
                request.setAttribute("password", "");
                rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            } else {
                out.println("error lost if else");
            }


        } catch (Exception e) {
            out.println("exception " + e);
        }
        out.println("Finished.");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.Cookie;
import java.util.Enumeration;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author alcaj
 */
public class Logic {

    Connection conn;
    Statement stmt;

    public Logic() {
        try {
            Context initContext = new InitialContext();
            Context envContext
                    = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
            conn = ds.getConnection();
            stmt = conn.createStatement();

        } catch (Exception e) {
            System.out.println("init DBCaller " + e);
        }
    }

    public void redirectUrl(HttpServletRequest request, HttpServletResponse response, String url) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);

        } catch (Exception e) {
            System.out.println("redirURL DBcaller " + e);
        }
    }

    public int login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {

            String sql1 = "select * from users where username='" + username + "' and password = md5('" + password + "')";
            ResultSet rs = stmt.executeQuery(sql1);

            if (rs.next()) {
                return rs.getInt("id");
            }

            return -1;

        } catch (Exception e) {
            System.out.println("eroeoroeroer");
        }
        return -1;
    }

}

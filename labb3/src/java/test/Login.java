/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.sql.Connection;
import javax.servlet.http.Cookie;
import java.sql.Statement;
import java.sql.ResultSet;


/**
 *
 * @author alcaj
 */
public class Login {
    
    private String login = "";
    
    public Login() {
        System.out.println("creating login");
        login = "";
    }
    
    
    public static int login(HttpServletRequest request, Connection conn) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            
            Statement stmt = conn.createStatement();        
            String sql1 = "select * from users where username='" + username + "' and password = md5('" + password + "')";
            ResultSet rs = stmt.executeQuery(sql1);
            
            if(rs.next()) {
                return rs.getInt("id");
            }
            
            return -1;
        
        } catch(Exception e) {
            System.out.println("eroeoroeroer");
        }
        return -1;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        if (login.equals("false")) {
            return "<p style='color:red'> Wrong username or password </p>";
        }
        return "";
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        System.out.println("setting login to: " + login);
        this.login = login;
    }
    
}

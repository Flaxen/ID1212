/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import model.*;
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

public class DbHandler {

    Statement stmt;

    public DbHandler() {

        try {
            Context initContext = new InitialContext();
            Context envContext
                    = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");

            Connection conn = ds.getConnection();
            stmt = conn.createStatement();

        } catch (Exception e) {
            System.out.println("fufufuf: " + e);
        }

    }

    public User[] getUsers() {
        User[] users = null;
        try {

            String sql = "SELECT COUNT(*) FROM users";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            users = new User[rs.getInt(1)];
            //System.out.println(users.length);
            sql = "SELECT * FROM users";
            rs = stmt.executeQuery(sql);

            int i = 0;
            while (rs.next()) {
                User tempUser = new User();

                tempUser.setUsername(rs.getString("username"));
                tempUser.setPassword(rs.getString("password"));

                users[i++] = tempUser;
            }

        } catch (Exception e) {
            System.out.println("eroeoroeroer usado " + e);
        }

        return users;
    }

    public boolean validate(User u) {

        try {

            String sql = "SELECT COUNT(*) FROM users WHERE username='" + u.getUsername() + "' AND password=md5('"
                    + u.getPassword() + "')";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            int temp = rs.getInt(1);
            //System.out.println("uname: " + u.getUsername() + " password: " + u.getPassword() + " res: " + temp);
            if (temp > 0) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("asdasd val " + e);
        }

        return false;
    }

    public Quiz[] getQuizzes() {

        Quiz[] quizzes = null;
        try {

            String sql = "SELECT COUNT(*) FROM quizzes";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            quizzes = new Quiz[rs.getInt(1)];
            //System.out.println(users.length);
            sql = "SELECT * FROM quizzes";
            rs = stmt.executeQuery(sql);

            int i = 0;
            while (rs.next()) {
                Quiz temp = new Quiz();
                
                temp.setId(rs.getInt("id"));
                temp.setSubject(rs.getString("subject"));

                quizzes[i++] = temp;
            }

        } catch (Exception e) {
            System.out.println("eroeoroeroer quizzes " + e);
        }

        return quizzes;
    }
    
    public Question[] getQuestions(int quizId) {
        
        Question[] questions = null;
        try {
            String sql = "SELECT COUNT(*) FROM questions\n" +
                            "INNER JOIN\n" +
                            "selector ON questions.id = selector.question_id\n" + 
                            "WHERE quiz_id = " + quizId;
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            questions = new Question[rs.getInt(1)];
            //System.out.println(users.length);
            sql = "SELECT * FROM questions\n" +
                        "INNER JOIN\n" +
                        "selector ON questions.id = selector.question_id\n" +
                        "WHERE quiz_id = " + quizId;
            rs = stmt.executeQuery(sql);

            int i = 0;
            while (rs.next()) {
                Question temp = new Question();
                
                temp.setText(rs.getString("text"));
                temp.setOptions(rs.getString("options"));
                temp.setAnswer(rs.getString("answer"));

                questions[i++] = temp;
            }

        } catch (Exception e) {
            System.out.println("eroeoroeroer questions " + e);
        }

        return questions;
    }
}

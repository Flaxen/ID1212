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
import javax.servlet.http.HttpSession;
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
                System.out.println("whiler1 wowowo");
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
                System.out.println("whiler2 wowowo");

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
            String sql = "SELECT COUNT(*) FROM questions\n"
                    + "INNER JOIN\n"
                    + "selector ON questions.id = selector.question_id\n"
                    + "WHERE quiz_id = " + quizId;
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            questions = new Question[rs.getInt(1)];
            //System.out.println(users.length);
            sql = "SELECT * FROM questions\n"
                    + "INNER JOIN\n"
                    + "selector ON questions.id = selector.question_id\n"
                    + "WHERE quiz_id = " + quizId;
            rs = stmt.executeQuery(sql);

            int i = 0;
            while (rs.next()) {
                System.out.println("whiler3 wowowo");

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

    public void validateQuiz(HttpServletRequest request, HttpSession session) {
        System.out.println(request.getParameter("question0box0"));
        Question[] questions = (Question[]) session.getAttribute("questions");

        int score = 0;

        for (int i = 0; i < questions.length; i++) {
            String[] answers = questions[i].getAnswer();
            for (int j = 0; j < answers.length; j++) {
                if ("ON".equals(request.getParameter("question" + i + "box" + j)) && answers[j].equals("1")) {
                    //System.out.println("added score");
                    score++;
                } else {
                    //System.out.println("wrong");
                }
            }
        }
        updateScore((User) session.getAttribute("user"), score, (int) session.getAttribute("quizname"), (int) session.getAttribute("quizId"));
    }

    private void updateScore(User u, int score, int quizId, int qid) {
        try {

            String sql = "SELECT id FROM users WHERE username='" + u.getUsername() + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int userId = rs.getInt("id");

            sql = "SELECT COUNT(*) FROM results WHERE user_id='" + userId + "' AND quiz_id='" + qid + "'";
            rs = stmt.executeQuery(sql);
            rs.next();
            if (rs.getInt(1) == 0) {
                sql = "INSERT INTO results(user_id, quiz_id, score) VALUES(" + userId + "," + qid + "," + score + ")";
                System.out.println("inserting new res");
                stmt.executeUpdate(sql);

            } else {
                sql = "UPDATE results SET score=" + score + " WHERE user_id='" + userId + "' AND quiz_id='" + qid + "'";
                System.out.println("updating res");
                stmt.executeUpdate(sql);

            }

        } catch (Exception e) {
            System.out.println("eroeroeroe updateScore " + e);
        }
    }

    public Result[] getLatestResult(User u) {
        Result[] results = null;
        try {

            String sql = "SELECT id FROM users WHERE username='" + u.getUsername() + "'";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            int userId = rs.getInt("id");

            sql = "SELECT COUNT(*) FROM quizzes";
            rs = stmt.executeQuery(sql);

            rs.next();
            results = new Result[rs.getInt(1)];

            sql = "SELECT * FROM results WHERE user_id=" + userId + " ORDER BY quiz_id";
            rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                System.out.println("whiler4 wowowo");

                Result temp = new Result();
                temp.setQuizId(rs.getInt("quiz_id"));
                temp.setScore(rs.getInt("score"));

                results[i++] = temp;
            }

        } catch (Exception e) {
            System.out.println("brbrbr bestres " + e);
        }

        return results;
    }
}

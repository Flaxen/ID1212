/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author alcaj
 */
public class Quiz {

    private int quizAmount;
    private String userId = null;
    private Connection conn;

    private int quizId;
    private String quizName;
    private int score;

    public Quiz() {
        try {
            Context initContext = new InitialContext();
            Context envContext
                    = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
            conn = ds.getConnection();
        } catch (Exception e) {
            System.out.println("error quiz java thing");

        }
    }

    /**
     * @return the quizAmount
     */
    public int getQuizAmount() {
        try {
            Statement stmt = conn.createStatement();
            String sql1 = "select * from quizzes";
            ResultSet rs = stmt.executeQuery(sql1);

            int counter = 0;
            while (rs.next()) {
                counter++;
            }

            return counter;

        } catch (Exception e) {
            System.out.println("eroero " + e);
        }
        return 0;
    }

    /**
     * @param quizAmount the quizAmount to set
     */
    public void setQuizAmount(int quizAmount) {
        this.quizAmount = quizAmount;
    }

    /**
     * @return the quizID
     */
    public int getQuizId() {
        return quizId;
    }

    /**
     * @param quizID the quizID to set
     */
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    /**
     * @return the quizName
     */
    public String getQuizName() {
        return quizName;
    }

    /**
     * @param quizName the quizName to set
     */
    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setScore() {
        try {
        Statement stmt = conn.createStatement();
        String sql1 = "select score from results where quiz_id='" + quizId + "'";
        ResultSet rs = stmt.executeQuery(sql1);
            
        } catch(Exception e) {
            System.out.println("brbrbrbrb " + e);
        }
    }

    public int getScore() {
        return score;
    }
}

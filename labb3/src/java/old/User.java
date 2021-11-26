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
 * @author Flaxen
 */
public class User {

    private String name;
    private String userID;

    public User() {
        name = null;
        userID = null;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name + " from java";
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return this.userID = userID + ", id fetched from db using java";
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getQuiz() {
        System.out.println("in get quiz");
        try {
            System.out.println("in get quiz try");

            Context initContext = new InitialContext();
            Context envContext
                    = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
            Connection conn = ds.getConnection();
            
            
            
            Statement stmt = conn.createStatement();
            String sql1 = "select * from results where user_id='" + userID + "'";
            ResultSet rs = stmt.executeQuery(sql1);

            System.out.println("in get preif");

            if (rs.next()) {
                System.out.println("in if");

                return "" + rs.getInt("score");
            }
            System.out.println("in get postif");


        } catch (Exception e) {
            System.out.println("eroeoreoreor2 " + e);
        }
        
        return "";
    }
}

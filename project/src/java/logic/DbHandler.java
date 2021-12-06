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
    
    public Room[] getRooms() {
        
        Room[] rooms = null;
        try {

            String sql = "SELECT COUNT(*) FROM rooms";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            rooms = new Room[rs.getInt(1)];
            //System.out.println(users.length);
            sql = "SELECT * FROM rooms";
            rs = stmt.executeQuery(sql);

            int i = 0;
            while (rs.next()) {
                //System.out.println("whiler1 wowowo");
                Room tempRoom = new Room();

                tempRoom.setId(rs.getInt("id"));
                tempRoom.setName(rs.getString("name"));
                tempRoom.setCourseCode(rs.getString("course_code"));


                rooms[i++] = tempRoom;
            }
            for(i = 0; i < rooms.length; i++) {
                sql = "SELECT COUNT(*) FROM selector2 WHERE room_id = " + rooms[i].getId();
                rs = stmt.executeQuery(sql);
                rs.next();
                rooms[i].setLength(rs.getInt(1));
            }


        } catch (Exception e) {
            System.out.println("eroeoroeroer rooms " + e);
        }

        return rooms;
    }
    
    public int userInQueue(User u, int queueId) {
        int inQ = 0;
        try {
            
            String sql = "SELECT * FROM users WHERE username='" + u.getUsername() + "'"; 
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int userId = rs.getInt("id");
            
            
            sql = "SELECT COUNT(*) FROM selector2 WHERE user_id=" + userId + " AND room_id=" + queueId;
            rs = stmt.executeQuery(sql);
            rs.next();
            int num = rs.getInt(1);
            //System.out.println("user count in queue: " + num);
            if(num > 0) {
                inQ = 1;
            } else {
                sql = "SELECT COUNT(*) FROM selector2 WHERE user_id=" + userId;
                rs = stmt.executeQuery(sql);
                rs.next();
                num = rs.getInt(1);
                //System.out.println("user count in queue: " + num);
                if(num > 0) {
                    inQ = 2;
                }
            }
            
            
        } catch(Exception e) {
            System.out.println("erer userInQueuee " + e);
        }
        
        return inQ;
    }
    
    public void removeFromQueue(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username='" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int userId = rs.getInt("id");
            
            sql = "DELETE FROM queue WHERE user_id=" + userId;
            stmt.executeUpdate(sql);
            
            sql = "DELETE FROM selector2 WHERE user_id=" + userId;
            stmt.executeUpdate(sql);
            
            
        } catch(Exception e) {
            System.out.println("brbrbr removeFromqueueueue " + e);
        }
    }
    
  
    
    public Queue[] getQueue(int qid) {
        Queue[] queue = null;
        try {

            String sql = "SELECT COUNT(*) FROM queue INNER JOIN users ON queue.user_id = users.id INNER JOIN selector2 ON users.id = selector2.user_id WHERE room_id = "+ qid +" ORDER BY queue.id ASC";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            queue = new Queue[rs.getInt(1)];
            //System.out.println(users.length);
            sql = "SELECT * FROM queue INNER JOIN users ON queue.user_id = users.id INNER JOIN selector2 ON users.id = selector2.user_id WHERE room_id = "+ qid +" ORDER BY queue.id ASC";
            rs = stmt.executeQuery(sql);

            int i = 0;
            while (rs.next()) {
                //System.out.println("whiler1 wowowo");
                Queue tempQueueUser = new Queue();

                tempQueueUser.setUsername(rs.getString("username"));
                tempQueueUser.setStatus(rs.getString("status"));
                tempQueueUser.setLocation(rs.getString("location"));
                tempQueueUser.setAction(rs.getString("action"));
                tempQueueUser.setSubject(rs.getString("subject"));



                queue[i++] = tempQueueUser;
            }

        } catch (Exception e) {
            System.out.println("eroeoroeroer usado in queue " + e);
        }

        return queue;
    }
    
    public void toggleHelp(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username='" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int userId = rs.getInt("id");
            
            sql = "SELECT * FROM queue WHERE user_id=" + userId;
            rs = stmt.executeQuery(sql);
            rs.next();
            String status = rs.getString("status");
            
            if("waiting".equals(status)) {
                sql = "UPDATE queue SET status='Getting help' WHERE user_id=" + userId;
            } else {
                sql = "UPDATE queue SET status='waiting' WHERE user_id=" + userId;
            }
            
            stmt.executeUpdate(sql);
            
            
        } catch(Exception e) {
            System.out.println("OOOO togglehelp " + e);
        }
    }
    
    public String getRole(User u, int qid) {
        String role = null;
        try {
            String sql = "SELECT * FROM users WHERE username= '"+ u.getUsername() + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int userId = rs.getInt("id");
            
            
            sql = "SELECT * FROM roles WHERE user_id= "+ userId + " AND room_id =" + qid;
            rs = stmt.executeQuery(sql);

            rs.next();
            role = rs.getString("role");
            

        } catch (Exception e) {
            System.out.println("eroeoroeroer usado in queue " + e);
        }
        
        return role;
    } 
    
    public void addUserToQueue(User u, int queueId, HttpServletRequest request) {
        try {
        String sql = "SELECT * FROM users WHERE username='" + u.getUsername() + "'";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int userId = rs.getInt(1);
        
        
        sql = "INSERT INTO queue(user_id, status, location, subject, action) "
                + "VALUES("+ userId +", '" + "waiting" + "', '" + request.getParameter("location") 
                + "', '" + request.getParameter("subject") + "', '" + request.getParameter("presenthelp") + "')";
        
        stmt.executeUpdate(sql);
        
        sql = "INSERT INTO selector2 VALUES(" + userId + ", " + queueId + ")";
        
        stmt.executeUpdate(sql);
        
            
        } catch(Exception e) {
            System.out.println("eroero addUserToQueue " + e);
        }
    }
    
    
}

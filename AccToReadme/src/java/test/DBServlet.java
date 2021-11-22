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
import java.sql.Statement;
import java.sql.ResultSet;

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
            
            Statement stmt = conn.createStatement();
            //String sql1 = "insert into user_data (name, password) values ('obobobbo', 'prprs')";
            String sql2 = "select * from users";
            //stmt.executeUpdate(sql1);
            ResultSet rs = stmt.executeQuery(sql2);
            
            while(rs.next()) {
                out.println("\n ID:" + rs.getInt("id"));
                out.println(", Name: " + rs.getString("username"));
                out.println(", Password: " + rs.getString("password") + "<br>");
            }
            
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.println("Finished.");
    }
}

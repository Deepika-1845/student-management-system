package servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
    "jdbc:mysql://switchback.proxy.rlwy.net:29337/railway",
    "root",
    "wDUYKvGHyCrbOGjdsPHANMPYfexSkibU"
);
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?");

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // correct login
                response.sendRedirect("index.html");
            } else {
                // wrong login
                response.sendRedirect("login.html?msg=error");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
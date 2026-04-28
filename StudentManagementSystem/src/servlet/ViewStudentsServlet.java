package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewStudentsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String search = request.getParameter("search");

        out.println("<html><head><style>");
        out.println("body{font-family:Segoe UI;background:#F3F0FF;margin:0;}");
        out.println(".navbar{background:#6A5ACD;padding:15px;color:white;text-align:center;}");
        out.println(".navbar a{color:white;margin:0 15px;text-decoration:none;font-weight:bold;}");

        out.println("table{border-collapse:collapse;width:85%;margin:auto;background:white;}");
        out.println("th,td{padding:12px;border-bottom:1px solid #ddd;text-align:center;}");
        out.println("th{background:#6A5ACD;color:white;}");
        out.println("tr:nth-child(even){background:#f9f9f9;}");

        out.println("button{padding:7px;border:none;border-radius:5px;cursor:pointer;}");
        out.println(".edit{background:#3498DB;color:white;}");
        out.println(".delete{background:#E74C3C;color:white;}");

        out.println("</style></head><body>");

        out.println("<div class='navbar'>Student Management System | ");
        out.println("<a href='index.html'>Home</a>");
        out.println("<a href='view'>View Students</a>");
        out.println("<a href='login.html'>Logout</a></div>");

        out.println("<h2 style='text-align:center;'>Student List</h2>");

        // SEARCH
        out.println("<div style='text-align:center;margin-bottom:20px;'>");
        out.println("<form method='get' action='view'>");
        out.println("<input type='text' name='search' placeholder='Search name/email'>");
        out.println("<button type='submit'>Search</button>");
        out.println("</form></div>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
    "jdbc:mysql://switchback.proxy.rlwy.net:29337/railway",
    "root",
    "wDUYKvGHyCrbOGjdsPHANMPYfexSkibU"
);

            PreparedStatement ps;

            // ✅ SORT BY USN
            if (search != null && !search.isEmpty()) {
                ps = con.prepareStatement(
                        "SELECT * FROM students WHERE name LIKE ? OR email LIKE ? ORDER BY usn ASC");
                ps.setString(1, "%" + search + "%");
                ps.setString(2, "%" + search + "%");
            } else {
                ps = con.prepareStatement("SELECT * FROM students ORDER BY usn ASC");
            }

            ResultSet rs = ps.executeQuery();

            out.println("<table>");
            out.println("<tr><th>SL No</th><th>USN</th><th>Name</th><th>Age</th><th>Email</th><th>Address</th><th>Edit</th><th>Delete</th></tr>");

            int sl = 1;

            while (rs.next()) {

                int id = rs.getInt("id");

                out.println("<tr>");
                out.println("<td>" + sl++ + "</td>");
                out.println("<td>" + rs.getString("usn") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getInt("age") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getString("address") + "</td>");

                // EDIT
                out.println("<td><a href='index.html?id=" + id +
                        "&usn=" + rs.getString("usn") +
                        "&name=" + rs.getString("name") +
                        "&age=" + rs.getInt("age") +
                        "&phone=" + rs.getString("phone") +
                        "&email=" + rs.getString("email") +
                        "&address=" + rs.getString("address") + "'>");
                out.println("<button class='edit'>Edit</button></a></td>");

                // DELETE
                out.println("<td><form method='post' action='hello'>");
                out.println("<input type='hidden' name='delete' value='" + id + "'>");
                out.println("<button class='delete'>Delete</button>");
                out.println("</form></td>");

                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch (Exception e) {
            out.println(e.getMessage());
        }

        out.println("</body></html>");
    }
}
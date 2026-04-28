package servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String usn = request.getParameter("usn");
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
    "jdbc:mysql://switchback.proxy.rlwy.net:29337/railway",
    "root",
    "wDUYKvGHyCrbOGjdsPHANMPYfexSkibU"
);

            // DELETE
            String deleteId = request.getParameter("delete");
            if (deleteId != null) {
                PreparedStatement ps = con.prepareStatement("DELETE FROM students WHERE id=?");
                ps.setInt(1, Integer.parseInt(deleteId));
                ps.executeUpdate();
                response.sendRedirect("view?msg=deleted");
                return;
            }

            // UPDATE
            if (id != null && !id.isEmpty()) {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE students SET usn=?, name=?, age=?, phone=?, email=?, address=? WHERE id=?");

                ps.setString(1, usn);
                ps.setString(2, name);
                ps.setInt(3, Integer.parseInt(age));
                ps.setString(4, phone);
                ps.setString(5, email);
                ps.setString(6, address);
                ps.setInt(7, Integer.parseInt(id));

                ps.executeUpdate();
                response.sendRedirect("index.html?msg=updated");
                return;
            }

            // DUPLICATE CHECK (USN)
            PreparedStatement check = con.prepareStatement(
                    "SELECT * FROM students WHERE usn=?");
            check.setString(1, usn);

            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                response.sendRedirect("index.html?msg=exists");
            } else {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO students(usn,name,age,phone,email,address) VALUES (?,?,?,?,?,?)");

                ps.setString(1, usn);
                ps.setString(2, name);
                ps.setInt(3, Integer.parseInt(age));
                ps.setString(4, phone);
                ps.setString(5, email);
                ps.setString(6, address);

                ps.executeUpdate();
                response.sendRedirect("index.html?msg=saved");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
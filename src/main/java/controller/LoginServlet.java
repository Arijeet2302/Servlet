package controller;

import jakarta.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DB.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.getWriter().println("Hello from Login");
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                List<User> users = new ArrayList<>();
                String getAllUsersSQL = "SELECT * FROM users";
                PreparedStatement stmtAllUsers = conn.prepareStatement(getAllUsersSQL);
                ResultSet rsAllUsers = stmtAllUsers.executeQuery();

                while (rsAllUsers.next()) {
                    String dbUsername = rsAllUsers.getString("username");
                    int id = rsAllUsers.getInt("id");
                    users.add(new User(id, dbUsername));
                }

                request.setAttribute("users", users);

                request.getRequestDispatcher("welcome.jsp").forward(request, response);            
                } else {
                response.getWriter().println("Invalid credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: Unable to login");
        }	}

}

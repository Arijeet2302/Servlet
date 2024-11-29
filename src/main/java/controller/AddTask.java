package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Task;
import model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DB.DBConnection;

@WebServlet("/addTask")
public class AddTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddTask() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("taskName");
		String desc = request.getParameter("taskDesc");
		
		try(Connection conn = DBConnection.getConnection()) {
			String sql = "INSERT INTO tasks (task_name, task_desc) VALUES (?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, desc);
            int rowsAffected = stmt.executeUpdate(); 
            
            
            if (rowsAffected > 0) {
                List<Task> tasks = ShowTaskList.showTasks();
                request.getSession().setAttribute("tasks", tasks);
                request.getSession().setAttribute("successMessage", "New task Created");
                } else {
                	request.getSession().setAttribute("failureMessage", "failed To Create Task");
                }
		}catch(Exception e) {
			e.printStackTrace();
            request.getSession().setAttribute("failureMessage", "failed To Create Task");
        }
		
        response.sendRedirect("Crud.jsp");            

	}

}

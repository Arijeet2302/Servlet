package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import DB.DBConnection;


@WebServlet("/delete")
public class Deletetask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Deletetask() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		
		try(Connection conn = DBConnection.getConnection()) {
			String sql = "DELETE FROM tasks WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, taskId);
            int rowsAffected = stmt.executeUpdate(); 
            
            
            if (rowsAffected > 0) {
                List<Task> tasks = ShowTaskList.showTasks();
                request.getSession().setAttribute("tasks", tasks);
                request.getSession().setAttribute("successMessage", "Task Deleted");
                } else {
                	request.getSession().setAttribute("failureMessage", "failed To Delete Task");
                }
		}catch(Exception e) {
			e.printStackTrace();
            request.getSession().setAttribute("failureMessage", "failed To Delete Task");
        }
		
        response.sendRedirect("Crud.jsp"); 
	}

}

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


@WebServlet("/update")
public class EditTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditTask() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("taskName");
		String desc = request.getParameter("taskDesc");
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		
		try(Connection conn = DBConnection.getConnection()) {
			String sql = "UPDATE tasks SET task_name = ?, task_desc = ? WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, desc);
			stmt.setInt(3, taskId);
            int rowsAffected = stmt.executeUpdate(); 
            
            
            if (rowsAffected > 0) {
                List<Task> tasks = ShowTaskList.showTasks();
                request.getSession().setAttribute("tasks", tasks);
                request.getSession().setAttribute("successMessage", "Task Updated");
                } else {
                	request.getSession().setAttribute("failureMessage", "failed To Update Task");
                }
		}catch(Exception e) {
			e.printStackTrace();
            request.getSession().setAttribute("failureMessage", "failed To Update Task");
        }
		
        response.sendRedirect("Crud.jsp"); 
	}

}

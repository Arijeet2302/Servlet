package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DB.DBConnection;
import model.Task;

public class ShowTaskList {
	
	public static List<Task> showTasks() {
		try(Connection conn = DBConnection.getConnection()){
            List<Task> tasks = new ArrayList<>();
            String getAllUsersSQL = "SELECT * FROM tasks";
            PreparedStatement stmtAllUsers = conn.prepareStatement(getAllUsersSQL);
            ResultSet rsAllUsers = stmtAllUsers.executeQuery();

            while (rsAllUsers.next()) {
                String taskname = rsAllUsers.getString("task_name");
                String task_desc = rsAllUsers.getString("task_desc");
                int id = rsAllUsers.getInt("id");
                tasks.add(new Task(id, taskname, task_desc));
            }
            
            return tasks;
            
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

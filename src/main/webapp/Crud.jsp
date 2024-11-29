<%@page import="model.Task"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Task Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        .form-container {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <h1>Task Management</h1>
    
        <!-- Display Success or Failure Message -->
    <div style="color: green;" id="successMessage">
        <% 
            String successMessage = (String) session.getAttribute("successMessage");
            if (successMessage != null) { 
        %>
            <p><%= successMessage %></p>
        <% } %>
    </div>

    <div style="color: red;" id="failureMessage">
        <% 
            String failureMessage = (String) session.getAttribute("failureMessage");
            if (failureMessage != null) { 
        %>
            <p><%= failureMessage %></p>
        <% } %>
    </div>

    <!-- Create/Update Form -->
    <div class="form-container">
        <form action="addTask" method="post">
            <input type="hidden" name="action" value="create"> <!-- change to "update" for updating -->
            <input type="hidden" name="taskId" id="taskId"> <!-- For update, task ID will be populated -->

            <label for="taskName">Task Name:</label>
            <input type="text" id="taskName" name="taskName" required>
            
            <label for="taskDesc">Task Description:</label>
            <input type="text" id="taskDesc" name="taskDesc" required>
            
            <button type="submit" id="submitButton">Add Task</button>
        </form>
    </div>

    <!-- Task List -->
        <% 
            // Get the list of users from the request attribute
            List<Task> tasks = (List<model.Task>) session.getAttribute("tasks");

            if (tasks != null && !tasks.isEmpty()) {
        %>
            <table border="1">
                <tr>
                	<th>ID</th>
                    <th>Task name</th>
                    <th>Email</th>
                    <th>Action</th>
                </tr>
                <% 
                    // Iterate over the list of users and display their details
                    for (model.Task task : tasks) {
                %>
                <tr>
                    <td><%= task.getId() %></td>
                    <td><%= task.getTaskname() %></td>
					<td><%= task.getDesc() %></td>
					<td>
                 <button onclick="editTask(<%= task.getId() %>, '<%= task.getTaskname().replace("'", "\\'") %>', '<%= task.getDesc().replace("'", "\\'") %>')">Edit</button>
                <form action="delete" method="post" style="display:inline;">
                    <input type="hidden" name="taskId" value="<%= task.getId() %>">
                    <button type="submit">Delete</button>
                </form>
					</td>
                    
                </tr>
                <% 
                    } 
                %>
            </table>
        <% 
            } else {
        %>
            <p>No tasks Yet.</p>
        <% 
            }
        %>

    <script>
	    function editTask(id, name, desc) {
	        document.getElementById('taskId').value = id;
	        document.getElementById('taskName').value = name;
	        document.getElementById('taskDesc').value = desc;
	        document.getElementById('submitButton').innerText = 'Update Task';
	        document.querySelector('form').action = 'update';
	    }
    </script>
</body>
</html>

<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    <% 
        // Get the username from the session
        String username = (String) session.getAttribute("username");

        if (username != null) {
    %>
        <h2>Welcome, <%= username %>!</h2>
        <p>You have successfully logged in.</p>
        <h3>All Users:</h3>
        
        <% 
            // Get the list of users from the request attribute
            List<model.User> users = (List<model.User>) request.getAttribute("users");

            if (users != null && !users.isEmpty()) {
        %>
            <table border="1">
                <tr>
                    <th>User name</th>
                    <th>Email</th>
                </tr>
                <% 
                    // Iterate over the list of users and display their details
                    for (model.User user : users) {
                %>
                <tr>
                    <td><%= user.getId() %></td>
                    <td><%= user.getUsername() %></td>
                </tr>
                <% 
                    } 
                %>
            </table>
        <% 
            } else {
        %>
            <p>No users found.</p>
        <% 
            }
        %>

        <form action="logout" method="POST">
            <button type="submit">Logout</button>
        </form>
    <% 
        } else {
    %>
        <p>You are not logged in. Please <a href="login.html">login</a>.</p>
    <% 
        }
    %>
</body>
</html>

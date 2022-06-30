package com.example.dao;

import com.example.models.Task;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
// finds file database.properties in resources folder and allows use it
@PropertySource("classpath:database.properties")
public class TaskDAO {
    private Connection connection;

    private TaskDAO(Environment environment) {
        // use Environment to get values from file database.properties
        String url = environment.getProperty("url_db");
        String username = environment.getProperty("username_db");
        String password = environment.getProperty("password_db");

        try {
            Class.forName(environment.getProperty("driver_db"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM tasks";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setText(resultSet.getString("text"));

                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public Task get(int id) {
        Task task = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM tasks WHERE id=?");

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            task = new Task();
            task.setId(resultSet.getInt("id"));
            task.setText(resultSet.getString("text"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return task;
    }

    public void save(Task task) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO tasks(text) VALUES(?)");
            preparedStatement.setString(1, task.getText());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Task task) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE tasks SET text=? WHERE id=?");
            preparedStatement.setString(1, task.getText());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM tasks WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


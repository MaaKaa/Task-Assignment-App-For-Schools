package com.company.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Solution {
    private int id;
    private Timestamp created; //zamieniłam z LocalDateTime, ponieważ nie wiem, w jaki sposób do preparedStatement dodać setLocalDateTime?
    private Timestamp updated;
    private String description;
    private Object exercise;
    private Object user;

    public void saveSolutionToDB(Connection connection){
        try {
            if (this.id == 0) {
                String sql = "INSERT INTO solution (created, updated, description, exercise_id, users_id) VALUES (?, ?, ?, ?, ?);";
                String[] generatedColumns = { "ID" };
                PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);
                preparedStatement.setTimestamp(1, this.created);
                preparedStatement.setTimestamp(2, this.updated);
                preparedStatement.setString(3, this.description);
                preparedStatement.setObject(4, this.exercise);
                preparedStatement.setObject(5, this.user);
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    this.id = resultSet.getInt(1);
                } else {
                    String sql2 = "UPDATE solution SET created=?, updated=?, description=?, exercise_id=?, users_id=? where id = ?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql);
                    preparedStatement.setTimestamp(1, this.created);
                    preparedStatement.setTimestamp(2, this.updated);
                    preparedStatement.setString(3, this.description);
                    preparedStatement.setObject(4, this.exercise);
                    preparedStatement.setObject(5, this.user);
                    preparedStatement.setInt(6, this.id);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Solution loadSolutionById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM solution where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = resultSet.getObject("exercise_id");
            loadedSolution.user = resultSet.getObject("user_id");
            return loadedSolution;}
        return null;
    }

    public static Solution[] loadAllSolutions(Connection connection) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = resultSet.getObject("exercise_id");
            loadedSolution.user = resultSet.getObject("user_id");
            solutions.add(loadedSolution);}
        Solution[] uArray = new Solution[solutions.size()]; uArray = solutions.toArray(uArray);
        return uArray;
    }

    public static Solution[] loadAllByUserId(Connection connection, int id) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution where user_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = resultSet.getObject("exercise_id");
            loadedSolution.user = resultSet.getObject("user_id");
            solutions.add(loadedSolution);}
        Solution[] uArray = new Solution[solutions.size()]; uArray = solutions.toArray(uArray);
        return uArray;
    }

    public static Solution[] loadAllByExerciseId(Connection connection, int id) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution where exercise_id=? ORDER BY created DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = resultSet.getObject("exercise_id");
            loadedSolution.user = resultSet.getObject("user_id");
            solutions.add(loadedSolution);}
        Solution[] uArray = new Solution[solutions.size()]; uArray = solutions.toArray(uArray);
        return uArray;
    }

    public void deleteSolution(Connection connection) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }
}

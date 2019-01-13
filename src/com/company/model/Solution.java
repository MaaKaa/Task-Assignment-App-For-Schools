package com.company.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Solution {
    private int id;
    private Timestamp created;
    private Timestamp updated;
    private String description;
    private Object exercise;
    private Object user;

    public static void main(String[] args) {

    }

    public Solution(){}

    public Solution(String description, Object exercise, Object user) {
        this.created = Timestamp.valueOf(LocalDateTime.now());
        this.updated = null;
        this.description = description;
        this.exercise = exercise;
        this.user = user;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExercise(Object exercise) {
        this.exercise = exercise;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public void  setUpdated(Timestamp updated){
        this.updated = updated;
    }

    public String getCreated() {
        return created.toString();
    }

    public String getUpdated() {
        return updated.toString();
    }

    public String getDescription() {
        return description;
    }

    public Object getExercise() {
        return exercise;
    }

    public Object getUser() {
        return user;
    }

    public void saveToDB(Connection connection) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO solution (created, updated, description, exercise_id, users_id) VALUES (?, ?, ?, ?);";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);
            preparedStatement.setTimestamp(1, this.created);
            preparedStatement.setString(2, this.description);
            preparedStatement.setObject(3, this.exercise);
            preparedStatement.setObject(4, this.user);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                this.id = resultSet.getInt(1);
            }
        } else {
            String sql2 = "UPDATE solution SET updated=?, description=?, exercise_id=?, users_id=? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            this.updated = Timestamp.valueOf(LocalDateTime.now());
            preparedStatement.setTimestamp(1, this.updated);
            preparedStatement.setString(2, this.description);
            preparedStatement.setObject(3, this.exercise);
            preparedStatement.setObject(4, this.user);
            preparedStatement.setInt(5, this.id);
            preparedStatement.executeUpdate();
        }
    }

    public static Solution loadById(Connection connection, int id) throws SQLException {
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
            loadedSolution.user = resultSet.getObject("users_id");
            return loadedSolution;}
        return null;
    }

    public static Solution[] loadAll(Connection connection) throws SQLException {
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
            loadedSolution.user = resultSet.getObject("users_id");
            solutions.add(loadedSolution);}
        Solution[] uArray = new Solution[solutions.size()]; uArray = solutions.toArray(uArray);
        return uArray;
    }

    public static Solution[] loadAllByUserId(Connection connection, int id) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution where users_id=?";
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
            loadedSolution.user = resultSet.getObject("users_id");
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
            loadedSolution.user = resultSet.getObject("users_id");
            solutions.add(loadedSolution);}
        Solution[] uArray = new Solution[solutions.size()]; uArray = solutions.toArray(uArray);
        return uArray;
    }

    public void delete(Connection connection) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", description='" + description + '\'' +
                ", exercise=" + exercise +
                ", user=" + user +
                '}'  + "\n";
    }
}


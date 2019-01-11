package com.company.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Solution {
    private int id;
    private java.sql.Date created; //zamieniłam z LocalDateTime, ponieważ nie wiem, w jaki sposób do preparedStatement dodać setLocalDateTime?
    private java.sql.Date updated;
    private String description;
    private Object exercise;
    private Object user;

    public Solution(){}

    public Solution(java.sql.Date created, java.sql.Date updated, String description, Object exercise, Object user) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise = exercise;
        this.user = user;
    }

    public void setCreated(java.sql.Date created) {
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

    public void  setUpdated(java.sql.Date updated){
        this.updated = updated;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public java.util.Date getUpdated() {
        return updated;
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
            String sql = "INSERT INTO solution (created, updated, description, exercise_id, users_id) VALUES (?, ?, ?, ?, ?);";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);
/*            preparedStatement.setTimestamp(1, this.created);
            preparedStatement.setTimestamp(2, this.updated);*/
            preparedStatement.setDate(1, this.created);
            preparedStatement.setDate(2, updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setObject(4, this.exercise);
            preparedStatement.setObject(5, this.user);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                this.id = resultSet.getInt(1);
            }
        } else {
            String sql2 = "UPDATE solution SET created=?, updated=?, description=?, exercise_id=?, users_id=? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setDate(1, this.created);
            preparedStatement.setDate(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setObject(4, this.exercise);
            preparedStatement.setObject(5, this.user);
            preparedStatement.setInt(6, this.id);
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
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
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
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
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
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
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
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
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
                '}';
    }
}


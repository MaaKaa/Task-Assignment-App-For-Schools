package com.company.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {
    private int id;
    private String name;

    public Group() {}

    public Group(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void saveToDB(Connection connection) throws SQLException{

        if (this.id == 0) {
            String sql = "INSERT INTO user_group (name) VALUES (?);";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.name);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                this.id = resultSet.getInt(1);
            }
        } else {
            String sql2 = "UPDATE user_group SET name=? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setString(1, this.name);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        }

    }

    public static Group loadById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM user_group where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Group loadedGroup = new Group();
            loadedGroup.id = resultSet.getInt("id");
            loadedGroup.name = resultSet.getString("name");
            return loadedGroup;}
        return null;
    }

    public static Group[] loadAll(Connection connection) throws SQLException {
        ArrayList<Group> groups = new ArrayList<Group>();
        String sql = "SELECT * FROM user_group";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Group loadedGroup = new Group();
            loadedGroup.id = resultSet.getInt("id");
            loadedGroup.name = resultSet.getString("name");
            groups.add(loadedGroup);}
        Group[] uArray = new Group[groups.size()]; uArray = groups.toArray(uArray);
        return uArray;
    }

    public void delete(Connection connection, int id) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM user_group WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}' + "\n";
    }
}



/*
Dla każdej klasy utwórz odpowiednie metody:
loadAll
loadById
delete
saveToDB
 */

package com.company.model;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email;
    private int id;
    private Group group;

    public User() {} //Tworząc obiekt za pomocą konstruktora bezparametrowego jego atrybutom zostaną nadane wartości domyślne: dla int 0, dla String null.

    public User(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.setPassword(password);
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password = BCrypt.hashpw(password, BCrypt.gensalt()); //implementacja algorytmu Blowfish - JB Crypt
    }

    public String getPassword(){
        return password;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setGroup(Group group){
        this.group = group;
    }

    public Group getGroup(){
        return group;
    }

    public void saveUserToDB(Connection connection){ //Zapisywanie nowego obiektu do bazy danych LUB modyfikacja obiektu
        try {
            if (this.id == 0) { //sprawdzamy, czy takiego obiektu już przypadkiem nie ma w naszej bazie. Jeśli jego ID =0, to znaczy, że nie ma.
                //String sql = "INSERT INTO users (username, email, password, user_group_id) VALUES (?, ?, ?, ?);";
                String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?);";
                String[] generatedColumns = { "ID" };
                PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);
                preparedStatement.setString(1, this.username);
                preparedStatement.setString(2, this.email);
                preparedStatement.setString(3, this.password);
                //preparedStatement.setObject(4, this.group); //Czy przy tworzeniu użytkownika powinniśmy go od razu dodawać do grupy?
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    this.id = resultSet.getInt(1); //pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu.
                } else { //Kod aktualizujący dane znajdujące się w bazie.
                    //String sql2 = "UPDATE users SET username=?, email=?, password=?, user_group_id where id = ?";
                    String sql2 = "UPDATE users SET username=?, email=?, password=? where id = ?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql);
                    preparedStatement.setString(1, this.username);
                    preparedStatement.setString(2, this.email);
                    preparedStatement.setString(3, this.password);
                    preparedStatement.setInt(4, this.id);
                    //preparedStatement.setObject(4, this.group);
                    //preparedStatement.setInt(5, this.id);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User loadUserById(Connection connection, int id) throws SQLException { //Wczytywanie obiektu z bazy danych.
        String sql = "SELECT * FROM users where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User loadedUser = new User(); //tworzymy obiekt typu User i ustawiamy mu parametry.
            loadedUser.id = resultSet.getInt("id");
            loadedUser.username = resultSet.getString("username");
            loadedUser.password = resultSet.getString("password");
            loadedUser.email = resultSet.getString("email");
            return loadedUser;}
        return null; //zwracamy obiekt użytkownika albo null.
    }

    public static User[] loadAllUsers(Connection connection) throws SQLException { //Wczytywanie wielu obiektów.
        ArrayList<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.username = resultSet.getString("username");
            loadedUser.password = resultSet.getString("password");
            loadedUser.email = resultSet.getString("email");
            users.add(loadedUser);}
        User[] uArray = new User[users.size()]; uArray = users.toArray(uArray);
        return uArray;
    }

    public static User[] loadAllByGroupId(Connection connection, int id) throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users where user_group_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.username = resultSet.getString("username");
            loadedUser.password = resultSet.getString("password");
            loadedUser.email = resultSet.getString("email");
            users.add(loadedUser);}
        User[] uArray = new User[users.size()]; uArray = users.toArray(uArray);
        return uArray;
    }

    public void deleteUser(Connection connection) throws SQLException { //Nie jest statyczna, bo musi być wywołana na obiekcie, który jest w bazie danych.
        if (this.id != 0) { //Sprawdzmy, czy obiekt jest już zapisany w bazie danych. Jeśli id jest różne od 0, to znaczy, że jest w bazie.
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0; //Usunęliśmy obiekt, więc zmieniamy jego id na 0.
        }
    }

}

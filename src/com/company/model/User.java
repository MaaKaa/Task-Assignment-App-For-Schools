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
    private int id;
    private String username;
    private String password;
    private String email;
    private Group group;

    //Tworząc obiekt za pomocą konstruktora bezparametrowego jego atrybutom zostaną nadane wartości domyślne: dla int 0, dla String null.
    public User() {}

    public User(String username, String password, String email, int groupId) {
        this.username = username;
        this.email = email;
        this.setPassword(password);
        this.group = group;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        //implementacja algorytmu Blowfish - JB Crypt:
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
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

    //Zapisywanie nowego obiektu do bazy danych LUB modyfikacja obiektu
    public void saveToDB(Connection connection) throws SQLException{
            //sprawdzamy, czy takiego obiektu już przypadkiem nie ma w naszej bazie. Jeśli jego ID =0, to znaczy, że nie ma.
            if (this.id == 0) {
                //String sql = "INSERT INTO users (username, email, password, user_group_id) VALUES (?, ?, ?, ?);";
                String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?);";
                String[] generatedColumns = { "ID" };
                PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);
                preparedStatement.setString(1, this.username);
                preparedStatement.setString(2, this.email);
                preparedStatement.setString(3, this.password);
                //Czy przy tworzeniu użytkownika powinniśmy go od razu dodawać do grupy?
                //preparedStatement.setObject(4, this.group);
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    //pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu.
                    this.id = resultSet.getInt(1);
                }
                //Kod aktualizujący dane znajdujące się w bazie.
                } else {
                    //String sql2 = "UPDATE users SET username=?, email=?, password=?, user_group_id where id = ? ";
                    String sql1 = "UPDATE users SET username=?, email=?, password=? where id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                    preparedStatement.setString(1, this.username);
                    preparedStatement.setString(2, this.email);
                    preparedStatement.setString(3, this.password);
                    preparedStatement.setInt(4, this.id);
                    //preparedStatement.setObject(5, this.group);
                    preparedStatement.executeUpdate();
                }
            }

    //Wczytywanie obiektu z bazy danych:
    public static User loadById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM users where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            //tworzymy obiekt typu User i ustawiamy mu parametry:
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.username = resultSet.getString("username");
            loadedUser.password = resultSet.getString("password");
            loadedUser.email = resultSet.getString("email");
            return loadedUser;}
        //zwracamy obiekt użytkownika albo null:
        return null;
    }

    //Wczytywanie wielu obiektów:
    public static User[] loadAll(Connection connection) throws SQLException {
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
        User[] uArray = new User[users.size()];
        uArray = users.toArray(uArray);
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

    //Nie jest statyczna, bo musi być wywołana na obiekcie, który jest w bazie danych:
    public void delete(Connection connection, int id) throws SQLException {
        //Sprawdzmy, czy obiekt jest już zapisany w bazie danych. Jeśli id jest różne od 0, to znaczy, że jest w bazie:
        if (this.id != 0) {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            //Usunęliśmy obiekt, więc zmieniamy jego id na 0.
            this.id = 0;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + ". Nazwa użytkownika: " + username + ". E-mail: " + email + ". Hasło: " + password + "\n";
    }

}

package com.company;
import com.company.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        User user = new User();
        //DatabaseConnection databaseConnection = new DatabaseConnection(); //Jak użyć klasy DatabaseConnection?
        /*try{ //Udało mi się dodać użytkownika do bazy, ale kiedy w Workbenchu wywołuję SELECT*FROM users, wyświetla mi się tabela wypełniona nullami...
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/szkolaProgramowania?useSSL=false&characterEncoding=utf8", "root", "coderslab");
            user.setUsername("Zbigniew Nowak");
            user.setEmail("zbigniew@nowak.pl");
            user.saveToDB(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/szkolaProgramowania?useSSL=false&characterEncoding=utf8", "root", "coderslab");
            user.loadUserById(connection, 10);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}

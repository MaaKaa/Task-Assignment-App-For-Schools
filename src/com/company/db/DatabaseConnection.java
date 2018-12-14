package com.company.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;
    public static Connection getConnection() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/szkolaProgramowania?useSSL=false&characterEncoding=utf8", "root", "coderslab")) {
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

/*
BEZPIECZNIEJSZA METODA:
public static getEfficientConnection(){
if(openConnection == null) || openConnection.isClosed()) {
        try (Connection newConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/szkolaProgramowania?useSSL=false&characterEncoding=utf8", "root", "coderslab")) {
            openConnection = newConnection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return openConnection;
    }
 */
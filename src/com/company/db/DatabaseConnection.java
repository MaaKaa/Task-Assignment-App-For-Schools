package com.company.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {


    private static Connection openConnection = null;


    public static Connection getConnection() {
        try (Connection newConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/szkolaProgramowania", "root", "coderslab")) {
            return newConnection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getEfficientConnection() throws SQLException {

        if (openConnection == null || openConnection.isClosed()) {

            openConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/szkolaProgramowania", "root", "coderslab");

        }
        return openConnection;

    }
}
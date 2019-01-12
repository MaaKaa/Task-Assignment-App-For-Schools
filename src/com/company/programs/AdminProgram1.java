package com.company.programs;

import com.company.model.User;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.db.DatabaseConnection.getEfficientConnection;
import static com.company.programs.AdminWelcome.printAdminMainMenu;

public class AdminProgram1 {
    public static void adminProgram1() {
        User user = new User();

        System.out.println("All users:");
        try {
            System.out.println(Arrays.toString(user.loadAll(getEfficientConnection())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        printAdminProgram1Menu();

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        int choice = 0;

        while(!quit) {
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    System.out.println("ADD USER:");
                    System.out.println("Enter username: ");
                    user.setUsername(scanner.nextLine());
                    System.out.println("Enter password: ");
                    user.setPassword(scanner.nextLine());
                    System.out.println("Enter email: ");
                    user.setEmail(scanner.nextLine());
                    try {
                        user.saveToDB(getEfficientConnection());
                        System.out.println("User has been saved.");
                        System.out.println(Arrays.toString(user.loadAll(getEfficientConnection())));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        printAdminProgram1Menu();
                    }
                    break;
                case 2:
                    System.out.println("EDIT USER:");
                    System.out.println("Press user ID: ");
                    try {
                        user = user.loadById(getEfficientConnection(), scanner.nextInt());
                        System.out.println("Enter new username: ");
                        user.setUsername(scanner.nextLine());
                        System.out.println("Enter new password: ");
                        user.setPassword(scanner.nextLine());
                        System.out.println("Enter new email: ");
                        user.setEmail(scanner.nextLine());
                        user.saveToDB(getEfficientConnection());
                        System.out.println("Changes has been saved.");
                        System.out.println(Arrays.toString(user.loadAll(getEfficientConnection())));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        printAdminProgram1Menu();
                    }
                    break;
                case 3:
                    System.out.println("DELETE USER:");
                    System.out.println("Enter the ID of the user you want to remove: ");
                    try {
                        int id = scanner.nextInt();
                        user = user.loadById(getEfficientConnection(), id);
                        user.delete(getEfficientConnection(), id);
                        System.out.println("User has been removed.");
                        System.out.println(Arrays.toString(user.loadAll(getEfficientConnection())));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        printAdminProgram1Menu();
                    }
                    break;
                case 4:
                    quit = true;
                    printAdminMainMenu();
                    break;
            }
        }
    }

    public static void printAdminProgram1Menu() {
        System.out.println("Press:");
        System.out.println("1 - Add user");
        System.out.println("2 - Edit user");
        System.out.println("3 - Delete user");
        System.out.println("4 - Quit the program and display the Main Menu.");
    }
}

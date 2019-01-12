package com.company.programs;

import com.company.model.Group;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.db.DatabaseConnection.getEfficientConnection;
import static com.company.programs.AdminWelcome.printAdminMainMenu;

public class AdminProgram3 {
    public static void adminProgram3(){
        Group group = new Group();

        System.out.println("All groups:");
        try {
            System.out.println(Arrays.toString(group.loadAll(getEfficientConnection())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        printAdminProgram3Menu();

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        int choice = 0;

        while(!quit) {
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    System.out.println("ADD GROUP:");
                    System.out.println("Enter name: ");
                    group.setName(scanner.nextLine());
                    try {
                        group.saveToDB(getEfficientConnection());
                        System.out.println("Group has been saved.");
                        System.out.println(Arrays.toString(group.loadAll(getEfficientConnection())));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        printAdminProgram3Menu();
                    }
                    break;
                case 2:
                    System.out.println("EDIT GROUP:");
                    System.out.println("Press group ID: ");
                    try {
                        group = group.loadById(getEfficientConnection(), scanner.nextInt());
                        System.out.println("Enter new name: ");
                        group.setName(scanner.nextLine());
                        group.saveToDB(getEfficientConnection());
                        System.out.println("Changes has been saved.");
                        System.out.println(Arrays.toString(group.loadAll(getEfficientConnection())));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        printAdminProgram3Menu();
                    }
                    break;
                case 3:
                    System.out.println("DELETE GROUP:");
                    System.out.println("Enter the ID of the group you want to remove: ");
                    try {
                        int id = scanner.nextInt();
                        group = group.loadById(getEfficientConnection(), id);
                        group.delete(getEfficientConnection(), id);
                        System.out.println("Group has been removed.");
                        System.out.println(Arrays.toString(group.loadAll(getEfficientConnection())));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        printAdminProgram3Menu();
                    }
                    break;
                case 4:
                    quit = true;
                    printAdminMainMenu();
                    break;
            }
        }
    }
    public static void printAdminProgram3Menu() {
        System.out.println("Press:");
        System.out.println("1 - Add group");
        System.out.println("2 - Edit group");
        System.out.println("3 - Delete group");
        System.out.println("4 - Quit the program and display the Main Menu.");
    }
}

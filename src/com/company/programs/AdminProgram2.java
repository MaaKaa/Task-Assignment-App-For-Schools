package com.company.programs;

import com.company.model.Exercise;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.db.DatabaseConnection.getEfficientConnection;
import static com.company.programs.AdminWelcome.printAdminMainMenu;

public class AdminProgram2 {
    public static void adminProgram2(){
        Exercise exercise = new Exercise();

        System.out.println("All exercises:");
        try {
            System.out.println(Arrays.toString(exercise.loadAll(getEfficientConnection())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        printAdminProgram2Menu();

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        int choice = 0;

        while(!quit) {
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    System.out.println("ADD EXERCISE:");
                    System.out.println("Enter title: ");
                    exercise.setTitle(scanner.nextLine());
                    System.out.println("Enter description: ");
                    exercise.setDescription(scanner.nextLine());
                    try {
                        exercise.saveToDB(getEfficientConnection());
                        System.out.println("Exercise has been saved.");
                        System.out.println(Arrays.toString(exercise.loadAll(getEfficientConnection())));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        printAdminProgram2Menu();
                    }
                    break;
                case 2:
                    System.out.println("EDIT EXERCISE:");
                    System.out.println("Press exercise ID: ");
                    try {
                        exercise = exercise.loadById(getEfficientConnection(), scanner.nextInt());
                        System.out.println("Enter new title: ");
                        exercise.setTitle(scanner.nextLine());
                        System.out.println("Enter new description: ");
                        exercise.setDescription(scanner.nextLine());
                        exercise.saveToDB(getEfficientConnection());
                        System.out.println("Changes has been saved.");
                        System.out.println(Arrays.toString(exercise.loadAll(getEfficientConnection())));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        printAdminProgram2Menu();
                    }
                    break;
                case 3:
                    System.out.println("DELETE EXERCISE:");
                    System.out.println("Enter the ID of the exercise you want to remove: ");
                    try {
                        int id = scanner.nextInt();
                        exercise = exercise.loadById(getEfficientConnection(), id);
                        exercise.delete(getEfficientConnection(), id);
                        System.out.println("Exercise has been removed.");
                        System.out.println(Arrays.toString(exercise.loadAll(getEfficientConnection())));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        printAdminProgram2Menu();;
                    }
                    break;
                case 4:
                    quit = true;
                    printAdminMainMenu();
                    break;
            }
        }
    }
    public static void printAdminProgram2Menu() {
        System.out.println("Press:");
        System.out.println("1 - Add exercise");
        System.out.println("2 - Edit exercise");
        System.out.println("3 - Delete exercise");
        System.out.println("4 - Quit the program and display the Main Menu.");
    }
}

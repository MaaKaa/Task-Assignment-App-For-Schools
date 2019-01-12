package com.company.programs;

import com.company.model.Exercise;
import com.company.model.Solution;
import com.company.model.User;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.db.DatabaseConnection.getEfficientConnection;
import static com.company.programs.AdminWelcome.printAdminMainMenu;

public class AdminProgram4 {
    public static void adminProgram4() {
        User user = new User();
        Exercise exercise = new Exercise();
        Solution solution = new Solution();

        System.out.println("All solutions:");
        try {
            System.out.println(Arrays.toString(solution.loadAll(getEfficientConnection())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        printAdminProgram4Menu();

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        int choice = 0;

        while (!quit) {
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        System.out.println("ASSIGN AN EXERCISE:");
                        System.out.println(Arrays.toString(user.loadAll(getEfficientConnection())));
                        System.out.println("Choose a user: enter user ID: ");
                        int userId = scanner.nextInt();
                        System.out.println(Arrays.toString(exercise.loadAll(getEfficientConnection())));
                        System.out.println("Choose an exercise: enter ID: ");
                        int exerciseId = scanner.nextInt();
                        solution.setUser(userId);
                        solution.setExercise(exerciseId);
                        solution.saveToDB(getEfficientConnection());
                        System.out.println("The exercise has been assigned.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        printAdminProgram4Menu();
                    }
                    break;
                case 2:
                    System.out.println("VIEW USER'S SOLUTIONS:");
                    System.out.println("Press user ID: ");
                    try {
                        int userId = scanner.nextInt();
                        System.out.println(Arrays.toString(solution.loadAllByUserId(getEfficientConnection(), userId)));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        printAdminProgram4Menu();
                    }
                    break;
                case 3:
                    quit = true;
                    printAdminMainMenu();
                    break;
            }
        }
    }

    public static void printAdminProgram4Menu() {
        System.out.println("Press:");
        System.out.println("1 - Add: assign an exercise to a user ");
        System.out.println("2 - View: display solutions of a particular user");
        System.out.println("3 - Quit the program and display the Main Menu.");
    }
}

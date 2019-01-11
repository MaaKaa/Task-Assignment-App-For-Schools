package com.company;
import com.company.db.DatabaseConnection;
import com.company.model.Exercise;
import com.company.model.Group;
import com.company.model.Solution;
import com.company.model.User;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.db.DatabaseConnection.getEfficientConnection;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        User user = new User();

        boolean quit = false;
        int choice = 0;

        printInstructions();

        while(!quit) {
            System.out.println("Choose program: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 0:
                    printInstructions();
                    break;
                    //
                case 1:
                    program1();
                    break;
                case 2:
                    program2();
                    break;
                case 3:
                    program3();
                    break;
                case 4:
                    program4();
                    break;
                case 5:
                    //usersPanel();
                    break;
                case 6:
                    quit = true;
                    break;
            }
        }

    }

    public static void printInstructions() {
        System.out.println("\nPress: ");
        System.out.println("\t 0 - To display the Main Menu.");
        System.out.println("\t 1 - Program 1: manage users.");
        System.out.println("\t 2 - Program 2: manage exercises.");
        System.out.println("\t 3 - Program 3: manage groups.");
        System.out.println("\t 4 - Program 4: assign exercises.");
        System.out.println("\t 5 - To quit the application.");
    }

    public static void printInstructionsProgram1() {
        System.out.println("Press:");
        System.out.println("1 - Add user");
        System.out.println("2 - Edit user");
        System.out.println("3 - Delete user");
        System.out.println("4 - Quit the program and display the Main Menu.");
    }

    public static void printInstructionsProgram2() {
        System.out.println("Press:");
        System.out.println("1 - Add exercise");
        System.out.println("2 - Edit exercise");
        System.out.println("3 - Delete exercise");
        System.out.println("4 - Quit the program and display the Main Menu.");
    }

    public static void printInstructionsProgram3() {
        System.out.println("Press:");
        System.out.println("1 - Add group");
        System.out.println("2 - Edit group");
        System.out.println("3 - Delete group");
        System.out.println("4 - Quit the program and display the Main Menu.");
    }

    public static void printInstructionsProgram4() {
        System.out.println("Press:");
        System.out.println("1 - Add: assign an exercise to a user ");
        System.out.println("2 - View: display solutions of a particular user");
        System.out.println("3 - Quit the program and display the Main Menu.");
    }

    public static void program1() {
        User user = new User();

        System.out.println("All users:");
        try {
            System.out.println(Arrays.toString(user.loadAll(getEfficientConnection())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        printInstructionsProgram1();

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
                        printInstructionsProgram1();
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
                        printInstructionsProgram1();
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
                        printInstructionsProgram1();
                    }
                    break;
                case 4:
                    quit = true;
                    printInstructions();
                    break;
            }
        }
    }

    public static void program2(){
        Exercise exercise = new Exercise();

        System.out.println("All exercises:");
        try {
            System.out.println(Arrays.toString(exercise.loadAll(getEfficientConnection())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        printInstructionsProgram2();

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
                        printInstructionsProgram2();
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
                        printInstructionsProgram2();
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
                        printInstructionsProgram2();
                    }
                    break;
                case 4:
                    quit = true;
                    printInstructions();
                    break;
            }
        }
    }
    public static void program3(){
        Group group = new Group();

        System.out.println("All groups:");
        try {
            System.out.println(Arrays.toString(group.loadAll(getEfficientConnection())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        printInstructionsProgram3();

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
                        printInstructionsProgram3();
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
                        printInstructionsProgram3();
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
                        printInstructionsProgram3();
                    }
                    break;
                case 4:
                    quit = true;
                    printInstructions();
                    break;
            }
        }
    }
    public static void program4(){
        User user = new User();
        Exercise exercise = new Exercise();
        Solution solution = new Solution();

        System.out.println("All solutions:");
        try {
            System.out.println(Arrays.toString(solution.loadAll(getEfficientConnection())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        printInstructionsProgram4();

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        int choice = 0;

        while(!quit) {
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
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
                        printInstructionsProgram4();
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
                        printInstructionsProgram4();
                    }
                    break;
                case 3:
                    quit = true;
                    printInstructions();
                    break;
            }
        }
    }

}

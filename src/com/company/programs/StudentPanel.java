package com.company.programs;

import com.company.model.Exercise;
import com.company.model.Solution;
import com.company.model.User;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.db.DatabaseConnection.getEfficientConnection;

public class StudentPanel {

    private static int currentUserId = 0;

    public static void main(String[] args) {
        studentWelcome();
    }

    public static void studentWelcome(){
        System.out.println("Welcome to the Student Panel.");
        login();
    }

    public static void login(){
        Scanner scanner = new Scanner(System.in);
        User user = new User();

        System.out.println("To log in, enter your ID:");

        while(!scanner.hasNextInt()){
            scanner.next();
            System.out.println("Wrong ID. Try again.");
        }

        currentUserId = scanner.nextInt();

        try {
            if (user.loadById(getEfficientConnection(), currentUserId) == null){
                System.out.println("Login unsuccessful. No user with such ID in our database.");
                studentWelcome();
            } else {
                System.out.println("Logged in as:");
                System.out.println(user.loadById(getEfficientConnection(), currentUserId));
                printStudentMainMenu();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void printStudentMainMenu() {
        System.out.println("\nPress: ");
        System.out.println("\t 0 - Main Menu.");
        System.out.println("\t 1 - Add a solution.");
        System.out.println("\t 2 - Display your solutions.");
        System.out.println("\t 3 - Quit the application.");

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        int choice = 0;

        while(!quit) {
            System.out.println("Choose program: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 0:
                    printStudentMainMenu();
                    break;
                case 1:
                    addSolution();
                    break;
                case 2:
                    viewSolutions();
                    break;
                case 3:
                    quit = true;
                    break;
            }
        }
    }

    public static void addSolution() {
        /*
        po wybraniu add – program wyświetli listę zadań, do których Użytkownik nie dodał jeszcze rozwiązania,
        a następnie odpyta o id zadania, do którego ma zostać dodane rozwiązanie.
        Pole created zostanie wypełnione automatycznie, więc Użytkownik zostanie odpytany jeszcze tylko o rozwiązanie zadania.
        Dla uproszczenia przyjmujemy, że dodanego rozwiązania nie możemy usuwać, ani edytować.
        W przypadku próby dodania rozwiązania, które już istnieje, program ma wyświetlić odpowiedni komunikat.
         */
        Exercise exercise = new Exercise();
        Solution solution = new Solution();
        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("All your exercises without solutions:");
            //do uzupełnienia

            System.out.println("Enter an ID of the exercise to which you want to add a solution:");
            System.out.println(exercise.loadById(getEfficientConnection(), scanner.nextInt()));
            System.out.println("Enter your solution and press Enter:");
            solution.setDescription(scanner.next());
            solution.saveToDB(getEfficientConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void viewSolutions() {
        Solution solution = new Solution();

        try {
            System.out.println(Arrays.toString(solution.loadAllByUserId(getEfficientConnection(), currentUserId)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

package com.company.programs;

import java.util.Scanner;

import static com.company.programs.AssignExercises.assignExercises;
import static com.company.programs.ManageExercises.manageExercises;
import static com.company.programs.ManageGroups.manageGroups;
import static com.company.programs.ManageUsers.manageUsers;

public class AdminPanel {

    public static void main(String[] args) {
        adminWelcome();
    }

    public static void adminWelcome(){
        System.out.println("Welcome to the Admin Panel.");
        printAdminMainMenu();
    }

    public static void printAdminMainMenu() {
        System.out.println("\nPress: ");
        System.out.println("\t 0 - Main Menu.");
        System.out.println("\t 1 - Manage users.");
        System.out.println("\t 2 - Manage exercises.");
        System.out.println("\t 3 - Manage groups.");
        System.out.println("\t 4 - Assign exercises.");
        System.out.println("\t 5 - Quit the application.");

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        int choice = 0;

        while(!quit) {
            System.out.println("Choose program:");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 0:
                    printAdminMainMenu();
                    break;
                case 1:
                    manageUsers();
                    break;
                case 2:
                    manageExercises();
                    break;
                case 3:
                    manageGroups();
                    break;
                case 4:
                    assignExercises();
                    break;
                case 5:
                    quit = true;
                    break;
            }
        }
    }
}


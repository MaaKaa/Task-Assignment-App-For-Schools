package com.company.programs;

import java.util.Scanner;

import static com.company.programs.AdminProgram1.adminProgram1;
import static com.company.programs.AdminProgram2.adminProgram2;
import static com.company.programs.AdminProgram3.adminProgram3;
import static com.company.programs.AdminProgram4.adminProgram4;

public class AdminWelcome {
    public static void main(String[] args) {
        adminWelcome();
    }

    public static void adminWelcome(){
        Scanner scanner = new Scanner(System.in);
        printAdminMainMenu();

        boolean quit = false;
        int choice = 0;

        while(!quit) {
            System.out.println("Choose program: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 0:
                    printAdminMainMenu();
                    break;
                //
                case 1:
                    adminProgram1();
                    break;
                case 2:
                    adminProgram2();
                    break;
                case 3:
                    adminProgram3();
                    break;
                case 4:
                    adminProgram4();
                    break;
                case 5:
                    quit = true;
                    break;
            }
        }
    }

    public static void printAdminMainMenu() {
        System.out.println("\nPress: ");
        System.out.println("\t 0 - To display the Main Menu.");
        System.out.println("\t 1 - Program 1: manage users.");
        System.out.println("\t 2 - Program 2: manage exercises.");
        System.out.println("\t 3 - Program 3: manage groups.");
        System.out.println("\t 4 - Program 4: assign exercises.");
        System.out.println("\t 5 - To quit the application.");
    }
}


package com.company.programs;

import java.util.Scanner;

import static com.company.programs.StudentProgram1.studentProgram1;
import static com.company.programs.StudentProgram2.studentProgram2;

public class StudentWelcome {

    public static void studentWelcome(){

        Scanner scanner = new Scanner(System.in);
        printStudentMainMenu();

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
                    studentProgram1();
                    break;
                case 2:
                    studentProgram2();
                    break;
                case 3:
                    quit = true;
                    break;
            }
        }
    }
    public static void printStudentMainMenu() {
        System.out.println("\nPress: ");
        System.out.println("\t 0 - To display the Main Menu.");
        System.out.println("\t 1 - To add a solution.");
        System.out.println("\t 2 - To display your solutions.");
        System.out.println("\t 3 - To quit the application.");
    }
}

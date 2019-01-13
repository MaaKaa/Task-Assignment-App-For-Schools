package com.company.programs;

import com.company.model.Group;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.db.DatabaseConnection.getEfficientConnection;
import static com.company.programs.AdminPanel.printAdminMainMenu;

public class ManageGroups {

    public static void manageGroups(){
        Group group = new Group();

        System.out.println("All groups:");
        try {
            System.out.println(Arrays.toString(group.loadAll(getEfficientConnection())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printManageGroupsMenu();
    }

    public static void printManageGroupsMenu() {
        System.out.println("Press:");
        System.out.println("1 - Add group");
        System.out.println("2 - Edit group");
        System.out.println("3 - Delete group");
        System.out.println("4 - Quit the program and display the Main Menu.");

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        int choice = 0;

        while(!quit) {
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    addGroup();
                    break;
                case 2:
                    editGroup();
                    break;
                case 3:
                    deleteGroup();
                    break;
                case 4:
                    quit = true;
                    printAdminMainMenu();
                    break;
            }
        }
    }

    public static void addGroup(){
        Scanner scanner = new Scanner(System.in);
        Group group = new Group();
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
            printManageGroupsMenu();
        }
    }

    public static void editGroup(){
        Scanner scanner = new Scanner(System.in);
        Group group = new Group();
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
            printManageGroupsMenu();
        }
    }

    public static void deleteGroup(){
        Scanner scanner = new Scanner(System.in);
        Group group = new Group();
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
            printManageGroupsMenu();
        }
    }
}

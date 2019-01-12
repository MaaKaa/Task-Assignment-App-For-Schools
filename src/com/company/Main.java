package com.company;
import com.company.db.DatabaseConnection;
import com.company.model.Exercise;
import com.company.model.Group;
import com.company.model.Solution;
import com.company.model.User;
import com.company.programs.AdminWelcome;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.db.DatabaseConnection.getEfficientConnection;
import static com.company.programs.AdminWelcome.*;
import static com.company.programs.StudentWelcome.studentWelcome;

public class Main {

    public static void main(String[] args) {
        //Command line comments: Run -> Edit configuration -> Program arguments
        /*for (int i=0; i<args.length; i++){
            System.out.println(args[i]);
        }*/

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("admin")) {
                adminWelcome();
            } else {
                studentWelcome();
            }
        }
    }

}

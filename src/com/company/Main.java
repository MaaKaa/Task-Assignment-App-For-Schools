package com.company;

import static com.company.programs.AdminPanel.*;
import static com.company.programs.StudentPanel.studentWelcome;

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

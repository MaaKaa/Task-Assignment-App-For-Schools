package com.company.programs;

import com.company.model.Solution;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.db.DatabaseConnection.getEfficientConnection;

public class StudentProgram2 {

    public static void studentProgram2() {
        Solution solution = new Solution();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter your ID:");
            System.out.println(Arrays.toString(solution.loadAllByUserId(getEfficientConnection(), scanner.nextInt())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

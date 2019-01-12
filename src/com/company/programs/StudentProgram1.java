package com.company.programs;

import com.company.model.Exercise;
import com.company.model.Solution;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

import static com.company.db.DatabaseConnection.getEfficientConnection;

public class StudentProgram1 {

    public static void studentProgram1() {
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
            System.out.println("Enter your ID:");
            int id = scanner.nextInt();

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
}

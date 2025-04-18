package ap.exercises.ex2;

import java.util.Scanner;

public class Main_EX2_PM_1_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // get input number from user
        System.out.print("Please enter the size of square: ");

        int k = scanner.nextInt();
        int i = k + 2;
        //defining a 2D array with i rows and columns
        char [][] square = new char[i][i];

        if (k <= 0) {
            System.out.print("Invalid number");
        }
        else {
            for (int r = 0; r < i; r++) {//for row
                for (int c = 0; c < i; c++) {//for column
                    if (r == 0 || r == i - 1 || c == 0 || c == i - 1) {
                        square[r][c] = '*';
                    } else {
                        square[r][c] = ' ';
                    }
                }
            }
            //printing array
            for (int r = 0; r < i; r++) {
                for (int c = 0; c < i; c++) {
                    System.out.print(square[r][c]);
                }
                System.out.println();//after each row is done, move to the nest line
            }
            scanner.close();
        }
    }
}
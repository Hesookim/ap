package ap.exercises.ex2;

import java.util.Scanner;
import java.util.Random;

public class Main_EX2_PM_1_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // get input number from user
        System.out.print("Please enter the size of square: ");
        int k = scanner.nextInt();

        // the size of the square is at least 1
        if (k < 1) {
            System.out.println("Invalid size!");
            return;
        }

        System.out.print("Please enter the number of dots: ");
        int a = scanner.nextInt();

        int maxDots = k * k;//dots can not be more than the size of square
        while (a > maxDots || a < 1) {
            System.out.println("ERROR! Dots must be between 1 and " + maxDots);
            System.out.print("Try again please: ");
            a = scanner.nextInt();
        }

        int i = k + 2;
        char[][] square = new char[i][i];

        for (int r = 0; r < i; r++) {
            for (int c = 0; c < i; c++) {
                if (r == 0 || r == i - 1 || c == 0 || c == i - 1) {
                    square[r][c] = '*';
                } else {
                    square[r][c] = ' ';
                }
            }
        }

        //to place dots randomly
        int placeOfDots = 0;
        while (placeOfDots < a) {
            int row = 1 + random.nextInt(k); //the dots can be placed from 1 to k in the border
            int col = 1 + random.nextInt(k);
            if (square[row][col] == ' ') {
                square[row][col] = '.';
                placeOfDots++;
            }
        }

        //printing the square with dots
        for (int row = 0; row < i; row++) {
            for (int col = 0; col < i; col++) {
                System.out.print(square[row][col] + " ");
            }
            System.out.println();
        }
    }
}

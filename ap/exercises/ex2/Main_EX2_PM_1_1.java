package ap.exercises.ex2;

import java.util.Scanner;
public class Main_EX2_PM_1_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // get input number from user
        System.out.print("Please enter the size of square: ");

        int k = scanner.nextInt();
        int i = k + 2;
        if (k <= 0) {
            System.out.println("Invalid number");
        } else {
            for (int r = 1; r <= i; r++) {//for row
                for (int c = 1; c <= i; c++) {//for column
                    if (r == 1 || r == i || c == 1 || c == i) {
                        System.out.print("*");
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.println();//after each row is done, move to the nest line
            }
            scanner.close();
        }
    }
}
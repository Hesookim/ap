package Studio.exercises;

import java.util.InputMismatchException;
import java.util.Scanner;

public class master {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a number: ");
        int a = scanner.nextInt();

        try {
            System.out.println("Result is : " + a);
        } catch (InputMismatchException e) {
            System.out.println("Not possible! " + e.getMessage());
        }
    }
}
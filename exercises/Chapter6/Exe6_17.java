package exercises.Chapter6;
/*Question:
Write a program that reads an integer and
displays, using asterisks, a filled and hollow
square, placed next to each other. For example,
if the side length is 5, the program
should display like:
***** *****
***** *   *
***** *   *
***** *   *
***** *****
*/

import java.util.Scanner;

public class Exe6_17 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter a number(should be integer) :  ");
        int num = scanner.nextInt();

        for (int i = 0; i < num; i++) {
            //loop for filled square
            for (int j = 0; j < num; j++) {
                System.out.print("*");
            }

            //space between squares for them not to coolide
            System.out.print(" ");

            //loop for hollow square
            for (int j = 0; j < num; j++) {
                if (i == 0 || i == num - 1 || j == 0 || j == num - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}

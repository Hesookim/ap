package exercises.Chapter6;
/*Question:
Write a program that reads an integer and
displays, using asterisks, a filled diamond
of the given side length. For example, if the
side length is 4, the program should
display like:
   *
  ***
 *****
*******
 *****
  ***
   *
 */

import java.util.Scanner;

public class Exe6_18 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter a number(should be integer) :  ");
        int num = scanner.nextInt();
        scanner.close();

        for (int i = 1; i <= num; i++) {
            for (int j = 1; j <= num - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

        for (int i = num - 1; i >= 1; i--) {
            for (int j = 1; j <= num - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
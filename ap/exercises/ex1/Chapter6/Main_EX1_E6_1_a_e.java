package ap.exercises.ex1.Chapter6;
/*Question 6.1{a, e} :
Write programs with loops that compute.
 a)The sum of all even numbers between 2 and 100 (inclusive).
 e)The sum of all odd digits of an input.
      (For example, if the input is 32677, the sum would be 3 + 7 + 7 = 17.)
 */

import java.util.Scanner;

public class Main_EX1_E6_1_a_e {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //a)
        int sum = 0;

        for (int i = 2; i <= 100; i += 2) {
            sum += i;
        }

        System.out.println("The sum of all even numbers between 2 and 100 is: " + sum);
        System.out.println();

            //e)
            System.out.print("Enter a number: ");
        int number = scanner.nextInt();

            int sumodd = 0;

            while (number > 0) {
                int digit = number % 10;
                if (digit % 2 != 0) {
                    sumodd += digit;
                }
                number /= 10;
            }

            System.out.println("The sum of all odd digits is: " + sumodd);
        }
    }
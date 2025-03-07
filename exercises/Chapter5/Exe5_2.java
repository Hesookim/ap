package exercises.Chapter5;
/* Question E5.2 :
Write a program that reads a floating-point number.
prints “zero” if the number is zero.
Otherwise, print “positive” or “negative”.
Add “small” if the absolute value of the number is less than 1,
or “large” if it exceeds 1,000,000.
 */
import java.util.Scanner;

public class Exe5_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter a a floating-point number: ");
        float num = scanner.nextFloat();

        if (num == 0) {
            System.out.println("Zero");
        }
        else {
            if (num > 0) {
                System.out.print("Positive");
            }
            else {
                System.out.print("Negative");
            }

            double absNum = Math.abs(num);
            if (absNum < 1) {
                System.out.println(" Small");
            }
            else if (absNum > 1_000_000) {
                System.out.println(" Large");
            }

        }
    }
}

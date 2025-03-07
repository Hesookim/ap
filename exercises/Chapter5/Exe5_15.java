package exercises.Chapter5;
/*Question 5.15 :
The original U.S. income tax of 1913 was quite simple.
The tax was • 1 percent on the first $50,000. • 2 percent on the amount over $50,000 up to $75,000
• 3 percent on the amount over $75,000 up to $100,000.• 4 percent on the amount over $100,000 up to $250,000
• 5 percent on the amount over $250,000 up to $500,000.• 6 percent on the amount over $500,000.
There was no separate schedule for single or married taxpayers.
*/
import java.util.Scanner;

public class Exe5_15 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your income: ");

        double income = scanner.nextDouble();

        double tax = 0;

        if (income > 500000) {
            tax += (income - 500000) * 0.06;
            income = 500000;
        }
        if (income > 250000) {
            tax += (income - 250000) * 0.05;
            income = 250000;
        }
        if (income > 100000) {
            tax += (income - 100000) * 0.04;
            income = 100000;
        }
        if (income > 75000) {
            tax += (income - 75000) * 0.03;
            income = 75000;
        }
        if (income > 50000) {
            tax += (income - 50000) * 0.02;
            income = 50000;
        }
        if (income > 0) {
            tax += income * 0.01;
        }

        System.out.printf("Your total income tax is: %.2f\n", tax);
    }
}

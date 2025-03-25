package ap.exercises.ex1.Chapter6;

import java.util.Scanner;

public class Main_EX1_E6_5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter numbers separated by spaces:");
        String input = scanner.nextLine();
        String[] numbers = input.split(" ");

        double sum = 0;
        double smallest = Double.POSITIVE_INFINITY;
        double largest = Double.NEGATIVE_INFINITY;
        int count = 0;

        for (String numStr : numbers) {
            try {
                double num = Double.parseDouble(numStr);
                sum += num;
                count++;

                if (num < smallest) {
                    smallest = num;
                }

                if (num > largest) {
                    largest = num;
                }
            } catch (NumberFormatException e) {
                System.out.println("Skipping invalid number: " + numStr);
            }
        }

        if (count > 0) {
            double average = sum / count;
            double range = largest - smallest;

            System.out.println("\nResults:");
            System.out.println("Average: " + average);
            System.out.println("Smallest: " + smallest);
            System.out.println("Largest: " + largest);
            System.out.println("Range: " + range);
        } else {
            System.out.println("No valid numbers entered!");
        }

        scanner.close();
    }
}

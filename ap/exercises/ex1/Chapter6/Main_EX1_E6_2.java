package ap.exercises.ex1.Chapter6;

import java.util.Scanner;
public class Main_EX1_E6_2 {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        int evenNumberCount = 0;
        int oddNumberCount = 0;
        int runningTotal = 0;
        int lastNumber = Integer.MIN_VALUE;
        boolean isDuplicatePrinted = false;
        boolean hasAdjacentDuplicates = false;

        System.out.println("Please enter numbers (you can type done when you are finished) : ");
        while (true) {
            String userInput = inputScanner.next();
            if (userInput.equalsIgnoreCase("done")) {
                break;
            }

            int currentNumber;
            try {
                currentNumber = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!!! Enter a valid number or type done to finish the inputing, please.");
                continue;
            }

            if (currentNumber < minValue)
                minValue = currentNumber;
            if (currentNumber > maxValue)
                maxValue = currentNumber;

            if (currentNumber % 2 == 0)
                evenNumberCount++;
            else
                oddNumberCount++;

            runningTotal += currentNumber;

            if ((lastNumber != Integer.MIN_VALUE) && (currentNumber == lastNumber && !isDuplicatePrinted)) {
                if (!hasAdjacentDuplicates) {
                    System.out.print("Adjacent duplicates : ");
                    hasAdjacentDuplicates = true;
                }
                System.out.print(currentNumber + " ");
                isDuplicatePrinted = true;
            } else if (currentNumber != lastNumber) {
                isDuplicatePrinted = false;
            }
            lastNumber = currentNumber;
        }

        System.out.println("\nSmallest = " + minValue + " - Largest = " + maxValue);
        System.out.println("Even count = " + evenNumberCount + " - Odd count = " + oddNumberCount);
        System.out.println("Cumulative total = " + runningTotal);

        if (!hasAdjacentDuplicates) {
            System.out.println(" No adjacent duplicates found.");
        }
    }
}
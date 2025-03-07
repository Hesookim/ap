package exercises.Chapter6;

import java.util.Scanner;
public class Exe6_13 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter a number :  ");
        int number = scanner.nextInt();
        scanner.close();
        while (number != 0) {
            int remainder = number % 2;
            System.out.println(remainder);
            number /= 2;
        }
    }
}
package exercises.Chapter6;
/*Write programs that read a line of input as a string and print.
a)Only the uppercase letters in the string.
b)Every second letter of the string.
c)The string, with all vowels replaced by an underscore.
d)The number of vowels in the string.
e)The positions of all vowels in the string.
 */

import java.util.Scanner;

public class Exe6_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("enter a string : ");
        String input = scanner.nextLine();

        boolean hasUppercase = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                hasUppercase = true;
                break;
            }
        }
        if (hasUppercase) {
            System.out.println("Uppercase letters are : ");
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    System.out.println(c);
                }
            }
            System.out.println();
        } else {
            System.out.println("No uppercase found in the string!!");
        }

        System.out.println();

        System.out.println("every second letter in the string : ");
        for (int i = 1; i < input.length(); i += 2) {
            System.out.print(input.charAt(i));
        }
        System.out.println();

        System.out.println("string with vowels replaced by underscore : ");
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U' ||
                    ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                System.out.print('_');
            } else {
                System.out.print(ch);
            }
        }
        System.out.println();

        int vowelCount = 0;
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U' ||
                    ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                vowelCount++;
            }
        }
        System.out.println("Number of vowels: " + vowelCount);

        System.out.println("Positions of vowels:");
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U' ||
                    ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}
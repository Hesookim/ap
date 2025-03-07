package exercises.Chapter6;

import java.util.Scanner;
public class Exe6_9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter a word : ");
        String word = scanner.nextLine();
        String reversedWord = " ";
        for (int i = word.length() - 1; i >= 0; i--) {
            reversedWord += word.charAt(i);
        }
        System.out.println("reversed word : " + reversedWord);
    }
}
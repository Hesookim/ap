package exercises.Chapter6;

import java.util.Scanner;
public class Exe6_8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter a word : ");
        String word = scanner.nextLine();

        for (int i = 0; i < word.length(); i++) {
            System.out.println(word.charAt(i));
        }
    }
}
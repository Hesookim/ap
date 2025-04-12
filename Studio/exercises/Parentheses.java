package Studio.exercises;

import java.util.Scanner;
import java.util.Stack;

public class Parentheses {
    public static boolean checkParentheses(String input) {
        Stack<Character> stack = new Stack<>();

        for (char p : input.toCharArray()) {
            if (p == '(') {
                stack.push(p);
            }
            else if (p == ')') {
                if (stack.isEmpty()) {
                    return false;
                }

                char last = stack.pop();
                if (!isMatching(last, p)) {
                    return false;
                }
            }
        }

        // If stack is empty, all brackets were matched so isbalanced is true, if not, it returns false
        return stack.isEmpty();
    }

    private static boolean isMatching(char open, char close) {
        return (open == '(' && close == ')');
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a set of string with open and closed Parentheses and press q to exit the program: ");

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                break;
            }

            boolean isBalanced = checkParentheses(input);
            System.out.println("You asked if " + input + " is balanced? " + isBalanced);
        }

        System.out.println("Program exited! Goodbye\uD83D\uDC4B");
        scanner.close();
    }
}
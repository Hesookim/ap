package ap.exercises.ex2;

import java.util.Scanner;
import java.util.Random;

public class Main_EX2_PM_2_2 {

    private static int score = 0;//score of the user
    private static int remainingDots;//to keep track of remaining dots
    private static long startTime;//so that screen starts time relapse

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        startTime = System.currentTimeMillis();

        //create game board
        char[][] square = createGameBoard(scanner, random);

        //placing X at starting position
        int x = 1, y = 1;
        square[x][y] = 'X';

        printGameState(square);
        System.out.println("Use W for up, A for left, S for down, D for right to move. Press Q to quit.");

        while (true) {
            System.out.print("Enter your move (W/A/S/D/Q): ");
            String input = scanner.next().toUpperCase();

            if (input.equals("Q")) {
                endGame(scanner, false);
                break;
            }

            int dir = 0;
            switch (input) {
                case "W":
                    dir = 1;
                    break;
                case "A":
                    dir = 2;
                    break;
                case "S":
                    dir = 3;
                    break;
                case "D":
                    dir = 4;
                    break;
                default:
                    System.out.println("Invalid input. Use W, A, S, D or Q, please.");
                    continue;
            }

            printDirection(dir);
            boolean moved = moveCharacter(square, x, y, dir);

            if (!moved) {
                System.out.println("Can't move - hit a wall!");
            } else {
                int newX = x, newY = y;
                switch (dir) {
                    case 1:
                        newX--;
                        break;
                    case 2:
                        newY--;
                        break;
                    case 3:
                        newX++;
                        break;
                    case 4:
                        newY++;
                        break;
                }
                x = newX;
                y = newY;

                printGameState(square);

                if (remainingDots == 0) {
                    endGame(scanner, true);
                    break;
                }
            }
        }
    }

    private static char[][] createGameBoard(Scanner scanner, Random random) {
        System.out.print("Please enter the size of square: ");
        int k = scanner.nextInt();

        if (k < 1) {
            System.out.println("Invalid size!");
            System.exit(0);
        }

        System.out.print("Please enter the number of dots: ");
        int a = scanner.nextInt();

        int maxDots = (k * k) - 1;
        while (a > maxDots || a < 1) {
            System.out.println("ERROR! Dots must be between 1 and " + maxDots);
            System.out.print("Try again please: ");
            a = scanner.nextInt();
        }

        remainingDots = a;
        int i = k + 2;
        char[][] square = new char[i][i];

        for (int r = 0; r < i; r++) {
            for (int c = 0; c < i; c++) {
                square[r][c] = (r == 0 || r == i - 1 || c == 0 || c == i - 1) ? '*' : ' ';
            }
        }

        int placeOfDots = 0;
        while (placeOfDots < a) {
            int row = 1 + random.nextInt(k);
            int col = 1 + random.nextInt(k);

            if (!(row == 1 && col == 1) && square[row][col] == ' ') {//the [1][1] position should not be available
                square[row][col] = '.';
                placeOfDots++;
            }
        }

        return square;
    }

    private static void endGame(Scanner scanner, boolean won) {
        long finishTime = System.currentTimeMillis();
        long timeElapsed = (finishTime - startTime) / 1000;

        clearScreen();
        if (won) {
            System.out.println("\n\n\n\t\tCongratulations!! You Won!!");
        }
        System.out.println("\t\tYour Score: " + score);
        System.out.println("\t\tTime Played: " + timeElapsed + " seconds");
        scanner.close();
        System.exit(0);
    }

    private static void printGameState(char[][] matrix) {
        clearScreen();
        System.out.println("Score: " + score + "\n");
        for (char[] row : matrix) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void printDirection(int direction) {
        String[] directions = {"", "up", "left", "down", "right"};
        System.out.println("Moving: " + directions[direction]);
    }

    private static boolean moveCharacter(char[][] matrix, int x, int y, int direction) {
        int newX = x, newY = y;

        switch (direction) {
            case 1:
                newX--;
                break;
            case 2:
                newY--;
                break;
            case 3:
                newX++;
                break;
            case 4:
                newY++;
                break;
        }

        if (matrix[newX][newY] == '*') {
            return false;
        }

        if (matrix[newX][newY] == '.') {
            score++;
            remainingDots--;
        }

        matrix[x][y] = ' ';
        matrix[newX][newY] = 'X';
        return true;
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
package ap.exercises.ex2;

import java.util.Scanner;
import java.util.Random;
import java.util.*;
import java.io.*;

public class Main_EX2_PM_2_3 {
    private static int score = 0;
    private static int remainingDots;
    private static long startTime;
    private static final String saveFile = "game_save.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        char[][] square = null;
        int x = 1, y = 1;

        System.out.print("Do you want to continue previous game? (Y/N) : ");
        String continueChoice = scanner.next().toUpperCase();

        if (continueChoice.equals("Y")) {
            square = loadGame();
            if (square != null) {
                System.out.println("Game loaded successfully!");
            } else {
                System.out.println("No saved game found. Starting new game.");
            }
        }

        if (square == null) {
            startTime = System.currentTimeMillis();
            square = createGameBoard(scanner, random);
            x = 1; y = 1;
            square[x][y] = 'X';
        } else {
            boolean found = false;
            for (int i = 0; i < square.length; i++) {
                for (int j = 0; j < square[i].length; j++) {
                    if (square[i][j] == 'X') {
                        x = i;
                        y = j;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            if (!found) {
                x = 1; y = 1;
                square[x][y] = 'X';
            }
        }

        printGameState(square);
        System.out.println("Use W for up, A for left, S for down, D for right to move. Press L to save & Q to quit.");

        while (true) {
            System.out.print("Enter your move (W/A/S/D/L/Q): ");
            String input = scanner.next().toUpperCase();

            if (input.equals("Q")) {
                endGame(scanner, false);
                break;
            }

            if (input.equals("L")) {
                saveGame(square, x, y);
                continue;
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
                    System.out.println("Invalid input. Use W, A, S, D or Q, L, please.");
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

    private static void saveGame(char[][] square, int x, int y) {
        try (PrintWriter writer = new PrintWriter(saveFile)) {
            writer.println(score);
            writer.println(remainingDots);
            writer.println(System.currentTimeMillis() - startTime);
            writer.println(x + " " + y);

            for (char[] row : square) {
                writer.println(new String(row));
            }
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    private static char[][] loadGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            score = Integer.parseInt(reader.readLine());
            remainingDots = Integer.parseInt(reader.readLine());
            startTime = System.currentTimeMillis() - Long.parseLong(reader.readLine());

            reader.readLine();

            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            char[][] square = new char[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                square[i] = lines.get(i).toCharArray();
            }

            return square;
        } catch (Exception e) {
            return null;
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

            if (!(row == 1 && col == 1) && square[row][col] == ' ') {
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
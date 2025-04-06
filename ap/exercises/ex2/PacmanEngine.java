package ap.exercises.ex2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class PacmanEngine {
    private int score;
    private int remainingDots;
    private long startTime;
    private char[][] board;
    private int x, y;
    private Random random;

    public PacmanEngine(int size, int dots) {
        if (size < 1) { throw new IllegalArgumentException("Board size must be ≥1"); }
        if (dots == 0 || dots > size * size - 1) {
            throw new IllegalArgumentException("Dots must be from 1 to" + (size * size - 1));}

        this.score = 0;
        this.startTime = System.currentTimeMillis();
        this.random = new Random();
        this.board = new char [size + 2][size + 2];

        for (int i = 0; i < size + 2; i++) {
            for (int j = 0; j < size + 2; j++) {
                board[i][j] = (i == 0 || i == size+1 || j == 0 || j == size+1) ? '*' : ' ';// Using Conditional combination
            }
        }

        this.remainingDots = dots;
        int placeOfDots = 0;
        while (placeOfDots < dots) {
            int row = 1 + random.nextInt(size);
            int col = 1 + random.nextInt(size);

            // Forbid placing dot on player's starting position which is (1,1)
            if (!(row == 1 && col == 1) && board[row][col] == ' ') {
                board[row][col] = '.';
                placeOfDots++;
            }
        }

        this.x = 1;
        this.y = 1;
        board[x][y] = 'X';
    }

    // Switching from letters to numbers
    public void move(int direction) {
        // New place of X
        int newX = x;
        int newY = y;

        switch (direction) {
            case 0: newX--; break; // up
            case 1: newY--; break; // left
            case 2: newX++; break; // down
            case 3: newY++; break; // right
            default:
                throw new IllegalArgumentException("Invalid direction. Use 0-3 or W/A/S/D buttons: ");
        }

        if (board[newX][newY] == '*') { return;} // Hitting wall handling

        if (board[newX][newY] == '.') { // Hitting dot handling
            score++;
            remainingDots--;
        }

        // Updating X position
        board[x][y] = ' ';
        x = newX;
        y = newY;
        board[x][y] = 'X';
    }

    void printMatrix() {
        this.clearScreen();
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public void printScore() {
        System.out.println("Score: " + score);
    }

    public void clearScreen() {
                System.out.print("\033[H\033[2J"); // Unix fallback if windows fails
                System.out.flush();
    }

    public void printRemainTime() {
        long currentTime = System.currentTimeMillis();
        System.out.println("Time: " + (currentTime - startTime)/1000 + "s");// Changing from ms to s
    }

    void save() {
        try {
            PrintWriter writer = new PrintWriter("Pacman_save.txt");

            // Saving info (score, dots, time)
            writer.println("Score:" + score);
            writer.println("Remaining dots:" + remainingDots);
            writer.println("Time:" + (System.currentTimeMillis() - startTime)/1000 + "s");

            // Save the game board
            writer.println("\nGame Board:");
            for (char[] row : board) {
                writer.println(new String(row));
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Oops! Couldn't save: " + e.getMessage());
        }
    }
        public boolean isGameOver() {
            return remainingDots == 0;
        }
    }

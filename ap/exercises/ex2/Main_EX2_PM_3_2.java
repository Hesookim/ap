package ap.exercises.ex2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main_EX2_PM_3_2 extends JFrame implements KeyListener {
    private static int score = 0;
    private static int remainingDots;
    private static long startTime;
    private static final String saveFile = "game_save.txt";
    private static char[][] square = null;
    private static int x = 1, y = 1;
    private static final int boxSize = 12;
    private static boolean gameOver = false;
    private static final long maxTime = 35000; // 35 seconds
    private static int direction = 1;
    private static final int LEFT = 1, RIGHT = 2, TOP = 3, BOTTOM = 4;

    public Main_EX2_PM_3_2() {
        addKeyListener(this);
        // Calculate window size with margins so not to cross borders
        int marginX = 20;
        int marginY = 50;
        setSize(square[0].length * boxSize + marginX * 2,
                square.length * boxSize + marginY * 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g.clearRect(0, 0, getWidth(), getHeight());

        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - startTime;

        if (!gameOver) {
            if (remainingDots == 0) {
                g2D.setColor(Color.BLACK);
                g2D.drawString("You won! Score: " + score, getWidth()/2 - 60, getHeight()/2);
                gameOver = true;
            } else if (timeElapsed >= maxTime) {
                g2D.setColor(Color.RED);
                g2D.drawString("Time's up! Score: " + score, getWidth()/2 - 60, getHeight()/2);
                gameOver = true;
            } else {
                drawGameBoard(g2D);
            }
        }

        drawScore(g2D);
    }

    private void drawGameBoard(Graphics2D g2d) {
        int marginX = 20;
        int marginY = 50;

        // Draw walls
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                if (square[i][j] == '*') {
                    int wallSize = boxSize/2;
                    g2d.fillRect(j * boxSize + marginX, i * boxSize + marginY, wallSize, wallSize);
                }
            }
        }

        // Draw dots
        g2d.setColor(Color.RED);
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                if (square[i][j] == '.') {
                    g2d.fillRect(j * boxSize + marginX, i * boxSize + marginY, boxSize, boxSize);
                }
            }
        }

        // Draw player
        g2d.setColor(Color.BLUE);
        g2d.fillRect(y * boxSize + marginX, x * boxSize + marginY, boxSize, boxSize);
    }

    private void drawScore(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawString("Score: " + score, 20, 30);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                direction = TOP;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                direction = LEFT;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                direction = BOTTOM;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                direction = RIGHT;
                break;
            case KeyEvent.VK_L:
                saveGame();
                return;
            case KeyEvent.VK_Q:
                System.exit(0);
                return;
            default:
                return;
        }

        boolean moved = moveCharacter(direction);
        if (moved) {
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private boolean moveCharacter(int direction) {
        int newX = x, newY = y;

        switch (direction) {
            case TOP:
                newX--;
                break;
            case LEFT:
                newY--;
                break;
            case BOTTOM:
                newX++;
                break;
            case RIGHT:
                newY++;
                break;
        }

        if (square[newX][newY] == '*') {
            return false;
        }

        if (square[newX][newY] == '.') {
            score++;
            remainingDots--;
        }

        square[x][y] = ' ';
        square[newX][newY] = 'X';
        x = newX;
        y = newY;
        return true;
    }

    private void saveGame() {
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
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    private static char[][] loadGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            score = Integer.parseInt(reader.readLine());
            remainingDots = Integer.parseInt(reader.readLine());
            startTime = System.currentTimeMillis() - Long.parseLong(reader.readLine());

            String[] pos = reader.readLine().split(" ");
            x = Integer.parseInt(pos[0]);
            y = Integer.parseInt(pos[1]);

            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            char[][] loadedSquare = new char[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                loadedSquare[i] = lines.get(i).toCharArray();
            }

            return loadedSquare;
        } catch (Exception e) {
            return null;
        }
    }

    private static char[][] createGameBoard() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

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
        char[][] newSquare = new char[i][i];

        for (int r = 0; r < i; r++) {
            for (int c = 0; c < i; c++) {
                newSquare[r][c] = (r == 0 || r == i - 1 || c == 0 || c == i - 1) ? '*' : ' ';
            }
        }

        int placeOfDots = 0;
        while (placeOfDots < a) {
            int row = 1 + random.nextInt(k);
            int col = 1 + random.nextInt(k);

            if (!(row == 1 && col == 1) && newSquare[row][col] == ' ') {
                newSquare[row][col] = '.';
                placeOfDots++;
            }
        }

        newSquare[1][1] = 'X';
        startTime = System.currentTimeMillis();
        return newSquare;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to continue previous game? (Y/N): ");
        String continueChoice = scanner.nextLine().toUpperCase();

        if (continueChoice.equals("Y")) {
            square = loadGame();
            if (square != null) {
                System.out.println("Game loaded successfully!");
            } else {
                System.out.println("No saved game found. Starting new game.");
                square = createGameBoard();
            }
        } else {
            square = createGameBoard();
        }

        Main_EX2_PM_3_2 frame = new Main_EX2_PM_3_2();
        frame.setVisible(true);
    }
}
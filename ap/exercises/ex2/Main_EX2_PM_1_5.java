package ap.exercises.ex2;

import java.util.Scanner;
import java.util.Random;

public class Main_EX2_PM_1_5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // get input number from user
        System.out.print("Please enter the size of square: ");

        int k = scanner.nextInt();
        int i = k + 2;

        if (k <= 0) {
            System.out.print("Invalid number");
            scanner.close();
            return;
        }

        //defining a 2D array with i rows and columns
        char[][] square = new char[i][i];

        for (int r = 0; r < i; r++) {
            for (int c = 0; c < i; c++) {
                if (r == 0 || r == i - 1 || c == 0 || c == i - 1) {
                    square[r][c] = '*';
                }
                else {
                    square[r][c] = ' ';
                }
            }
        }

        //to place X in one of the corners (inside the borders/ can not be on borders)
        int x = 1;//pos: position
        int y = 1;
        square[x][y] = 'X';

        //printing the array
        printMatrix(square);

        Random random = new Random();
        scanner.close();

        //infinite loop for dot eater movements
        while (true) {
            //step 1 : Random direction (1-4)
            int dir = random.nextInt(4) + 1;
            System.out.print("Moving ");
            switch(dir) {
                case 1: System.out.println("up"); break;
                case 2: System.out.println("right"); break;
                case 3: System.out.println("down"); break;
                case 4: System.out.println("left"); break;
            }
            //step 2: Move character
            boolean moved = moveCharacter(square, x, y, dir);

            if (!moved) {
                System.out.println("Can't move - hit a wall!");
            }
            else {
                // Update position
                switch (dir) {
                    case 1: x--; break; //up
                    case 2: y++; break; //right
                    case 3: x++; break; //down
                    case 4: y--; break; //left
                }
                printMatrix(square);
            }

            //step 3: 1 second delay
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printMatrix(char[][] matrix) {
        for (char[] row : matrix) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void printDirection(int direction) {
        String[] directions = {"", "up", "right", "down", "left"};
        System.out.println("Moving: " + directions[direction]);
    }

    private static boolean moveCharacter(char[][] matrix, int x, int y, int direction) {
        int newX = x, newY = y;

        switch (direction) {
            case 1: newX--; break; //up
            case 2: newY++; break; //right
            case 3: newX++; break; //down
            case 4: newY--; break; //left
        }

        //checking if the new position is valid(not on borders)
        if (matrix[newX][newY] == '*') {
            return false;
        }

        matrix[x][y] = ' ';
        matrix[newX][newY] = 'X';
        return true;
    }
}
//this program will not finish until the user stops it by hand
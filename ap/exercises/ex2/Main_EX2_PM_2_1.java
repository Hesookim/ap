package ap.exercises.ex2;

import java.util.Scanner;

public class Main_EX2_PM_2_1 {
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

        //to place X in one of the corners (inside the borders)
        int x = 1;
        int y = 1;
        square[x][y] = 'X';

        //printing the initial array
        printMatrix(square);

        System.out.println("Use W for up, A for left, S for down, D for right to move. You can press Q, if you want to quit.");

        while (true) {
            System.out.print("Enter your move (W/A/S/D/Q): ");
            String input = scanner.next().toUpperCase();//getting input as string

            if (input.equals("Q")) {
                System.out.println("End of the game!!");
                break;
            }

            int dir = 0;
            switch(input) {
                case "W": dir = 1; //up
                      break;
                case "A": dir = 2;//left
                      break;
                case "S": dir = 3;//down
                      break;
                case "D": dir = 4;//right
                      break;
                default:
                    System.out.println("Invalid input. Use W, A, S, D or Q, please.");
                    continue;
            }

            printDirection(dir);
            boolean moved = moveCharacter(square, x, y, dir);//for changing directions

            if (!moved) {
                System.out.println("Can't move - hit a wall!");
            }
            else {
                //updating the position of X
                switch (dir) {
                    case 1: x--;//up
                        break;
                    case 2: y--;//left
                        break;
                    case 3: x++;//down
                        break;
                    case 4: y++;//right
                        break;
                }
                printMatrix(square);
            }
        }
        scanner.close();
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
        String[] directions = {"", "up", "left", "down", "right"};
        System.out.println("Moving: " + directions[direction]);
    }

    private static boolean moveCharacter(char[][] matrix, int x, int y, int direction) {
        int newX = x, newY = y;

        switch (direction) {
            case 1: newX--;//up
                break;
            case 2: newY--;//left
                break;
            case 3: newX++;//down
                break;
            case 4: newY++;//right
                break;
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
package ap.exercises.Project;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String LIB_NAME = "Zanjan University Central Library";

    public static void main(String[] args) throws IOException {

        FileHandler fh = new FileHandler();
        Library lib;

        try {
            lib = fh.loadAll(LIB_NAME);
        } catch (IOException e) {
            System.err.println("Could not load data: " + e.getMessage());
            lib = new Library(LIB_NAME);

            Manager mgr = new Manager("Admin", "Asghari", 999999, "admin@123", lib);
            lib.setManager(mgr);
            fh.setCurrentManager(mgr);
        }

        Menu mainMenu = new Menu(lib,fh);
        Scanner input = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            mainMenu.showMenu();
            System.out.print("\nExit program? (y/n): ");
            if (input.nextLine().equalsIgnoreCase("y")) {
                quit = true;
            }
        }

            fh.saveAll(lib);
    }
}

package ap.exercises.Project;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Menu {
    private Scanner scanner;
    private Library library;

    public Menu(Library library) {
        this.scanner = new Scanner(System.in);
        this.library = library;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nLibrary Management");
            System.out.println("=========================");
            System.out.println("1. Sign in/ Sign up");
            System.out.println("2. Exit");
            System.out.println("=========================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showSignInMenu();
                    break;

                case 2:
                    library.signOut();
                    System.out.println("you signed out. Good bye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter again!");
            }
        }
    }

    public void showSignInMenu() {
        while (true) {
            System.out.println("===========================");
            System.out.println("1. Sign in as student");
            System.out.println("2. Sign in as manager");
            System.out.println("3. Sign in as librarian");
            System.out.println("4. return");
            System.out.println("===========================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    authenticateStudent();
                    break;

                case 2:
                    authenticateManager();
                    break;

                case 3:
                    authenticateLibrarian();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("invalid choice! Please enter again!");
            }
        }
    }

    private void authenticateLibrarian() {
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        Librarian librarian = library.findLibrarianByName(firstName, lastName);

        if (librarian == null) {
            System.out.println("Librarian not found. Please contact the manager to be added!");
            return;
        }

        if (!librarian.isRegistered()) {
            System.out.println("You are not fully registered yet.");
            System.out.print("Would you like to complete registration? (y/n): ");
            String complete = scanner.nextLine();

            if (complete.equalsIgnoreCase("y")) {
                System.out.print("Set your employee ID: ");
                int employeeId = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Set your password: ");
                String password = scanner.nextLine();

                try {
                    librarian.completeRegistration(employeeId, password);
                    System.out.println("Registration completed successfully!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        System.out.print("Enter your employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (librarian.getId() == id && librarian.authenticate(password)) {
            System.out.println("Librarian signed in successfully!");
            showLibrarianMenu();
        } else {
            System.out.println("Invalid ID or password.");
        }
    }

    private void showLibrarianMenu() {
        while (true) {
            System.out.println("Librarian Menu:");
            System.out.println("==========================");
            System.out.println("1. Add book");
            System.out.println("2. Return to main menu");
            System.out.println("==========================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBookAsLibrarian();
                    break;

                case 2:
                    return;

                default:
                    System.out.println("Invalid choice! Please try again!");
            }
        }
    }

    private void addBookAsLibrarian() {
        if (!(library.getCurrentUser() instanceof Librarian librarian)) {
            System.out.println("Only librarians can add books!");
            return;
        }

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Pages: ");
        int pages = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Copies: ");
        int copies = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        try {
            Book newBook = new Book(title, author, year, pages, copies, isbn);
            library.addBook(newBook, librarian);
            System.out.println("Book added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    private void authenticateStudent() {
        System.out.print("Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (library.signIn(id, password)) {
            showStudentMenu();
            return;
        }

        System.out.println("No student found. Would you like to sign up? (y/n)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("y")) {
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Major: ");
            String major = scanner.nextLine();

            Student newStudent = new Student(firstName, lastName, id, major, password);
            library.registerStudent(newStudent);
            System.out.println("Registration successful!");
        }
    }

    private void authenticateManager() {
        System.out.println("MangerId: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (library.signIn(id, password)) {
            showManagerMenu();
        }
    }

    private void showStudentMenu() {
        while (true) {
            System.out.println("Students Menu: ");
            System.out.println("==========================");
            System.out.println("1. Search for a book");
            System.out.println("===========================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 :
                    searchBookMenu();
                    break;

                default:
                    System.out.println("invalid choice! please try again!");
            }
        }
    }

    private void searchBookMenu() {
        System.out.println("Search for a Book");
        System.out.println("1. By Title");
        System.out.println("2. By Author");
        System.out.print("Choose an option from above: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter your search term: ");
        String search = scanner.nextLine().toLowerCase();

        List<Book> found = new ArrayList<>();

        for (Book book : library.getBookList()) {
            if (option == 1 && book.getTitle().toLowerCase().contains(search)) {
                found.add(book);
            } else if (option == 2 && book.getAuthor().toLowerCase().contains(search)) {
                found.add(book);
            }
        }

        if (found.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Books found:");
            for (Book book : found) {
                System.out.println("Title: " + book.getTitle() +
                        ", Author: " + book.getAuthor() +
                        ", Year: " + book.getYear() +
                        ", Pages: " + book.getPages() +
                        ", Copies: " + book.getCopies() +
                        ", ISBN: " + book.getISBN());
            }
        }
    }


    public void showManagerMenu() {
        while (true) {
            System.out.println("Managers Menu: ");
            System.out.println("==========================");
            System.out.println("1. Add librarian");
            System.out.println("===========================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (!(library.getCurrentUser() instanceof Manager manager)) {
                        System.out.println("You must be signed in as a manager to add librarians!");
                        return;
                    }

                        System.out.print("First name: ");
                        String firstName = scanner.nextLine();

                        System.out.print("Last name: ");
                        String lastName = scanner.nextLine();

                        try {
                            manager.addLibrarian(firstName, lastName);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    break;

                default :
                    System.out.println("invalid choice! please try again!");
            }
        }
    }
}
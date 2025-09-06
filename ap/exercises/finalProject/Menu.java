package ap.exercises.finalProject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private Library library;
    private final FileHandler fh;

    public Menu(Library library, FileHandler fh) {
        this.library = library;
        this.fh = fh;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nLibrary Management");
            System.out.println("=========================");
            System.out.println("1. Sign in/ Sign up");// <-- Sign in
            System.out.println("2. Guest Menu");// <-- Guest
            System.out.println("3. Exit");
            System.out.println("=========================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showSignInMenu();
                    break;

                case 2:
                    showGuestMenu();
                    break;

                case 3:
                    library.signOut();
                    try {
                        fh.saveAll(library);
                    } catch (IOException e) {
                        System.out.println("Error saving library data: " + e.getMessage());
                    }
                    System.out.println("you signed out. Good bye!");
                    return;

                default:
                    System.out.println("Invalid choice! Please enter again!");
            }
        }
    }

    public void showSignInMenu() {
        while (true) {
            System.out.println("===========================");
            System.out.println("1. Sign in as student");// <--
            System.out.println("2. Sign in as manager");
            System.out.println("3. Sign in as operator");
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
                    authenticateOperator();
                    break;

                case 4:
                    try {
                        fh.saveAll(library);
                    } catch (Exception e) {
                        System.err.println("Error saving library data: " + e.getMessage());
                    }
                    return;

                default:
                    System.out.println("invalid choice! Please enter again!");
            }
        }
    }

    private void authenticateStudent() {
        System.out.print("Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (library.signIn(id, password)) {
            showStudentMenu((Student) library.getCurrentUser());
            System.out.println("Welcome!");
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

            Student st = new Student(firstName, lastName, id, major, password);
            library.registerStudent(st);
            System.out.println("Registration successful!");
        }
    }

    private void authenticateManager() {
        System.out.print("MangerId: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (library.signInAsManager(id, password)) {
            showManagerMenu();
        } else {
            System.out.println("invalid manager!");
        }
    }

    private void authenticateOperator() {
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        Operator operator = library.findOperatorByName(firstName, lastName);

        if (operator == null) {
            System.out.println("Operator not found. Please contact the manager to be added!");
            return;
        }

        if (!operator.isRegistered()) {
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
                    operator.completeRegistration(employeeId, password);
                    System.out.println("Registration completed successfully!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                return;
            }
        }

        System.out.print("Enter your employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (operator.getId() == id && operator.authenticate(password)) {
            System.out.println("Operator signed in successfully!");
            showOperatorMenu(operator);
        } else {
            System.out.println("Invalid ID or password.");
        }
    }

    private void showOperatorMenu(Operator operator) {
        while (true) {
            System.out.println("Operator Menu:");
            System.out.println("==========================");
            System.out.println("1. Add book");
            System.out.println("2. Confirm borrow");
            System.out.println("3. Confirm return");
            System.out.println("4. Get students loan history");
            System.out.println("5. Get all handed loans");
            System.out.println("6. Return to main menu");
            System.out.println("==========================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBookAsOperator();
                    break;

                case 2:
                    System.out.print("Pending-borrow index: ");
                    int idx = scanner.nextInt();
                    library.confirmBorrow(idx, operator);
                    break;

                case 3:
                    System.out.print("Pending-return index: ");
                    int rIdx = scanner.nextInt();
                    library.confirmReturn(rIdx, operator);
                    break;

                case 4:
                    System.out.println("Enter the students ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    List<BorrowBook> loans = library.getLoansOfStudent(id);
                    listLoans(loans);
                    break;

                case 5:
                    library.getLoansHandledByLibrarian(operator.getId());
                    break;

                case 6:
                    try {
                        fh.saveAll(library);
                    } catch (Exception e) {
                        System.err.println("Error saving library data: " + e.getMessage());
                    }
                    return;

                default:
                    System.out.println("Invalid choice! Please try again!");
            }
        }
    }

    private void addBookAsOperator() {
        if (!(library.getCurrentUser() instanceof Operator operator)) {
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
            library.addBook(newBook, operator);
            System.out.println("Book added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    private void showGuestMenu() {
        while (true) {
            System.out.println("Guest Menu:");
            System.out.println("1. View student count"); // <---
            System.out.println("2. Search books by title"); // <---
            System.out.println("3. View library statistics");
            System.out.println("4. Back to main menu");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Total students: " + library.getStudentList().size());
                    break;
                case 2:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    List<Book> results = library.searchBooks(title, null, null, null);

                    if (results.isEmpty()) {
                        System.out.println("No books found with title: '" + title + "'");
                    } else {
                        System.out.println("Found " + results.size() + " book(s):");
                        for (int i = 0; i < results.size(); i++) {
                            Book book = results.get(i);
                            System.out.println((i + 1) + ". " + book.getTitle() +
                                    " by " + book.getAuthor() +
                                    " (" + book.getYear() + ")");
                        }
                    }

                    break;
                case 3:
                    library.showLibraryStats();// <------
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void showStudentMenu(Student st) {
        while (true) {
            System.out.println("Students Menu: ");
            System.out.println("1) Search book");// <--
            System.out.println("2) Request borrow");
            System.out.println("3) Request return");
            System.out.println("4) My active loans");
            System.out.println("5) My loan history");
            System.out.println("6) Sign-out");
            System.out.println("===========================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchBookMenu();
                    break;

                case 2:
                    System.out.println("Enter isbn of the book you want to borrow: ");
                    String isbn = scanner.nextLine();

                    LocalDate startDate = null;

                    while (startDate == null) {
                        System.out.println("Enter start borrow date: ");
                        String start = scanner.nextLine();
                        try {
                            startDate = LocalDate.parse(start);
                        } catch (Exception e) {
                            System.out.println("Invalid date format! Please try again.");
                        }
                    }

                    LocalDate endDate = null;
                    while (endDate == null) {
                        System.out.print("Enter end date: ");
                        String end = scanner.nextLine();
                        try {
                            endDate = LocalDate.parse(end);
                        } catch (Exception e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }

                    library.requestBorrow(st.getId(), isbn);
                    break;

                case 3:
                    System.out.println("Enter isbn of the book you want to return: ");
                    String rIsbn = scanner.nextLine();
                    library.requestReturn(st.getId(), rIsbn);
                    break;

                case 4:
                    listLoans(library.getActiveLoansForStudent(st.getId()));
                    break;

                case 5:
                    listLoans(library.getLoanHistoryForStudent(st.getId()));
                    break;

                case 6:
                    try {
                        fh.saveAll(library);
                    } catch (Exception e) {
                        System.err.println("Error saving library data: " + e.getMessage());
                    }
                    return;

                default:
                    System.out.println("Invalid choice! Please try again!");
            }
        }
    }

    private void searchBookMenu() {// <-----
        System.out.println("Search for a Book (leave blank to skip a field)");

        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) title = null;

        System.out.print("Author: ");
        String author = scanner.nextLine().trim();
        if (author.isEmpty()) author = null;

        System.out.print("Year of publication: ");
        String yearInput = scanner.nextLine().trim();
        Integer year = null;
        if (!yearInput.isEmpty()) {
            try {
                year = Integer.parseInt(yearInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid year input. Ignoring year filter.");
            }
        }

        System.out.print("Only available books? (y/n, leave blank for any): ");
        String availInput = scanner.nextLine().trim();
        Boolean available = null;
        if (availInput.equalsIgnoreCase("y")) available = true;
        else if (availInput.equalsIgnoreCase("n")) available = false;

        List<Book> found = library.searchBooks(title, author, year, available);

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
                        ", ISBN: " + book.getISBN() +
                        ", Available: " + (book.isAvailable() ? "Yes" : "No"));
            }
        }
    }

    public void showManagerMenu() {
        while (true) {
            System.out.println("Managers Menu: ");
            System.out.println("==========================");
            System.out.println("1. Add operator");
            System.out.println("2. Get overdue loans ");
            System.out.println("3. Get Issue Count For Operator");
            System.out.println("4. Get Return Count For Operator");
            System.out.println("5. Return");
            System.out.println("===========================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (!(library.getCurrentUser() instanceof Manager manager)) {
                        System.out.println("You must be signed in as a manager to add operators!");
                        return;
                    }

                    System.out.print("First name: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Last name: ");
                    String lastName = scanner.nextLine();

                    try {
                        manager.addOperator(firstName, lastName);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    library.getOverdueLoans();
                    break;

                case 3:
                    System.out.println("Which operator would you like to get issue counts for? (write the operators ID)");
                    int indexIn = scanner.nextInt();
                    scanner.nextLine();
                    int issueCount = library.getIssueCountForOperator(indexIn);
                    System.out.println("Issue count for operator " + indexIn + ": " + issueCount);
                    break;


                case 4:
                    System.out.println("Which operator would you like to get returned counts for? (write the operator's ID)");
                    int rIndex = scanner.nextInt();
                    library.getReturnCountForOperator(rIndex);
                    int returnCount = library.getReturnCountForOperator(rIndex);
                    System.out.println("Return count for operator " + rIndex + ": " + returnCount);
                    break;


                case 5:
                    try {
                        fh.saveAll(library);
                    } catch (Exception e) {
                        System.err.println("Error saving library data: " + e.getMessage());
                    }
                    return;

                default:
                    System.out.println("invalid choice! please try again!");
            }
        }
    }

    private void listLoans(List<BorrowBook> list) {
        if (list.isEmpty()) System.out.println("null");
        else list.forEach(System.out::println);
    }
}
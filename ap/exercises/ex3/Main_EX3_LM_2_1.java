package ap.exercises.ex3;

/* Changes:
1. New Class LibraryBooks( same as before Book but with new feature named copies, that has the number of the copies of the said book)
2. New methods added so that the user is able to borrow book
*/
import java.util.Scanner;
import java.io.FileWriter;// Added for file handling
import java.io.IOException;// Added for file handling
import java.io.FileReader;// Added for file loading and reading
import java.io.BufferedReader;// Added for file handling
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main_EX3_LM_2_1 {
    private static final int maxBooks = 5000;  // Maximum number of books
    private static final int maxStudents = 5000; // Maximum number of students
    private static final String SaveLocation = "ap/exercises/ex3/"; // To be sure the files are saved in exactly this location, regardless of which computer runs the code
    private static final String Library = SaveLocation + "library.txt";
    private static final String StudentsData = SaveLocation + "students.txt";
    private static final String borrowBooks = SaveLocation + "borrowRecords.txt";
    // Permanent books
    private static final LibraryBooks defaultBook1 = new LibraryBooks("The Lord of the Rings", "J.R.R. Tolkien", 1954, 1178, 1);
    private static final LibraryBooks defaultBook2 = new LibraryBooks("Harry Potter and the Philosopher's Stone", " J. K. Rowling", 1997, 223, 4);

    // Permanent students
    private static final Student defaultStudent1 = new Student("Minoo", "Namdar", 403463154, "Computer engineering");
    private static final Student defaultStudent2 = new Student("Sevda", "Akhbari", 403496170, "MBA");

    private static final LibraryBooks[] books = new LibraryBooks[maxBooks];
    private static final Student[] students = new Student[maxStudents];
    private static int bookCount = 0;
    private static int studentCount = 0;

    // Create directory if it doesn't exist in the computers (has changed)
    static {
        try {
            Files.createDirectories(Paths.get(SaveLocation));
        } catch (IOException e) {
            System.err.println("ERROR: Failed to create directory '" + SaveLocation + "': " + e.getMessage());
        }
    }

    static {
        // Add permanent books
        books[bookCount++] = defaultBook1;
        books[bookCount++] = defaultBook2;

        // Add permanent students
        students[studentCount++] = defaultStudent1;
        students[studentCount++] = defaultStudent2;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management");
            System.out.println("=============================");
            System.out.println("1. Add a book");
            System.out.println("2. Add a student");
            System.out.println("3. View all books");
            System.out.println("4. View all students");
            System.out.println("5. Search for student by name");
            System.out.println("6. Save data to files");
            System.out.println("7. Load data from files");
            System.out.println("8. Borrow a book");
            System.out.println("9. Exit");
            System.out.println("=============================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1 :// Add a book
                    if (bookCount >= maxBooks) {
                        System.out.println("ERROR! Maximum number of books reached!");
                        break;
                    }
                    System.out.print("Enter book name: ");
                    String bookName = scanner.nextLine();
                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter publication year: ");
                    int year = scanner.nextInt();
                    System.out.print("Enter number of pages: ");
                    int pages = scanner.nextInt();
                    System.out.print("Enter number of copies: ");
                    int copies = scanner.nextInt();
                    scanner.nextLine();

                    books[bookCount++] = new LibraryBooks(bookName, author, year, pages, copies);
                    System.out.println("Book added successfully!");
                    break;

                case 2 :// Add a student
                    if (studentCount >= maxStudents) {
                        System.out.println("ERROR! Maximum number of students reached!");
                        break;
                    }

                    System.out.print("Enter students first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter students last name: ");
                    String lastName = scanner.nextLine();

                    // Check for duplicate SIN
                    int sin;
                    boolean isDuplicate;
                    do {
                        System.out.print("Enter student identification number (SIN): ");
                        sin = scanner.nextInt();
                        isDuplicate = checkForDuplicateSin(sin);
                        if (isDuplicate) {
                            System.out.println("ERROR! A student with this SIN already exists. Please enter a different SIN.");
                        }
                    }
                    while (isDuplicate); // Keep asking until a unique SIN is entered

                    System.out.print("Enter students major: ");
                    scanner.nextLine();
                    String major = scanner.nextLine();
                    students[studentCount++] = new Student(firstName, lastName, sin, major);
                    System.out.println("Student added successfully!");
                    break;

                case 3 :// Show all added books
                    System.out.println("\nAll books in the library: ");
                    if (bookCount == 0){
                        System.out.println("No books in the library!");
                    }
                    else {
                        for (int i = 0; i < bookCount; i++){
                            LibraryBooks book = books[i];
                            System.out.println("Book " + (i + 1));
                            System.out.println("Title: " + book.getBookName());
                            System.out.println("Author: " + book.getBookAuthor());
                            System.out.println("Year: " + book.getBookYear());
                            System.out.println("Pages: " + book.getBookPages());
                            System.out.println("Copies: " + book.getCopies());
                            System.out.println();
                        }
                    }
                    break;

                case 4 :// Show all registered students
                    System.out.println("\nAll students registered in the library: ");
                    if (studentCount == 0){
                        System.out.println("No student has registered in the library!");
                    }
                    else {
                        for (int i = 0; i < studentCount; i++){
                            Student student = students[i ];
                            System.out.println("Student " + (i + 1));
                            System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
                            System.out.println("SIN: " + student.getSin());
                            System.out.println("Major: " + student.getMajor());
                            System.out.println();
                        }
                    }
                    break;

                case 5 :// Search student by name
                    searchByName();
                    break;

                case 6 :// Save files
                    saveBook();
                    saveStudents();
                    break;

                case 7 :// Load files
                    loadBooks();
                    loadStudents();
                    break;

                case 8 :// Borrow a book
                    borrowBookMenu();
                    break;

                case 9 :// Exist
                    System.out.println("Existing the program.... ");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!!! Please enter a number (1-6): ");
            }
        }
    }

    // Method to save books data to file
    private static void saveBook() {
        try (FileWriter  writer = new FileWriter(Library)) {
            for (int i = 0; i < bookCount; i++) {
                LibraryBooks book = books[i];
                writer.write(book.getBookName() + ",");
                writer.write(book.getBookAuthor() + ",");
                writer.write(book.getBookYear() + ",");
                writer.write(book.getBookPages() + " ");
                writer.write(book.getCopies() + "\n");
            }
            System.out.println("Books saved to file " + Library);
        } catch (IOException e) {
            System.out.println("Error saving books data to file: " + e.getMessage());
        }
    }
    // Method to save students data to file
    private static void saveStudents () {
        try (FileWriter writer = new FileWriter(StudentsData)) {
            for (int i = 0; i < studentCount; i++) {
                Student student = students[i];
                writer.write(student.getFirstName() +" " + student.getLastName() + ","); // Saving first and last name of the student together
                writer.write(student.getSin() + ",");
                writer.write(student.getMajor() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving students data to file:" + e.getMessage());
        }
    }
    // Method to load books from a file
    private static void loadBooks() {
        try (BufferedReader reader =  new BufferedReader(new FileReader(Library))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");// As in saving method, we have split the data's with ","
                String name = parts[0];
                String author = parts[1];
                int year = Integer.parseInt(parts[2]);
                int pages = Integer.parseInt(parts[3]);
                int copies = Integer.parseInt(parts[4]);
                books[bookCount] = new LibraryBooks(name, author, year, pages, copies);
                bookCount++;
            }
            reader.close();
            System.out.println("Books loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }
    // Method to load students from a file
    private static void loadStudents() {
        try (BufferedReader reader =  new BufferedReader(new FileReader(StudentsData))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String[] nameParts = parts[0].split(" ");
                String firstName = nameParts[0];
                String lastName = nameParts[1];
                int sin = Integer.parseInt(parts[1]);
                String major = parts[2];
                students [studentCount] = new Student(firstName, lastName, sin, major);
                studentCount++;
            }
            reader.close();
            System.out.println("Students loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }
    // Checking to see if two different students have same SIN
    public static boolean checkForDuplicateSin(int sin) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getSin() == sin) {
                return true;
            }
        }
        return false;
    }
    // Method to search name of student
    private static void searchByName() {
        if (studentCount == 0) {
            System.out.println(" No students available.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter students name (if it is first name start with uppercase, if it is lastname all should be lowercase): ");
        String searchName = scanner.nextLine().trim().toLowerCase();

        for (int i = 0; i < studentCount; i++) {
            Student student = students[i];// Check both first and last name (case-insensitive)
            if (student.getFirstName().toLowerCase().equalsIgnoreCase(searchName) ||
                    student.getLastName().toLowerCase().equalsIgnoreCase(searchName)) {

                System.out.println("Student Found!");
                System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
                System.out.println("SIN: " + student.getSin());
                System.out.println("Major: " + student.getMajor());
                System.out.println();
            }
        }

        if (searchName.isEmpty()) {
            System.out.println("Error: Empty search term.");
        }
    }
    // Method to search student by SIN
    private static Student findStudentBySin(int sin) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getSin() == sin) {
                return students[i];
            }
        }
        return null;
    }
    // Method to search book by name
    private static LibraryBooks findBook(String identifier) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getBookName().equalsIgnoreCase(identifier)) {
                return books[i];
            }
        }
        return null;
    }

    private static boolean processBorrowing(Student student, LibraryBooks book) {
        // Check book availability
        if (book.getCopies() <= 0) {
            System.out.println("No copies left for: " + book.getBookName());
            return false;
        }

        // Update book copies by decreasing by one
        book.setCopies(book.getCopies() - 1);

        // Save borrower
        try (FileWriter writer = new FileWriter(borrowBooks, true)) {
            writer.write(student.getSin() + "," + book.getBookName() + "\n");
            System.out.println(student.getFirstName() + " "+ student.getLastName() +
                    " borrowed" + " " + book.getBookName());
            return true;
        } catch (IOException e) {
            System.err.println("Error saving record: " + e.getMessage());
            book.setCopies(book.getCopies() + 1);
            return false;
        }
    }
    // Method to borrow book
    private static void borrowBookMenu() {
        if (studentCount == 0 || bookCount == 0) {
            System.out.println("No students or books available for borrowing.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // Get student
        System.out.print("Enter student SIN: ");
        int sin = scanner.nextInt();
        scanner.nextLine();

        Student student = findStudentBySin(sin);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        // Get book
        System.out.print("Enter book name: ");
        String bookName = scanner.nextLine();

        LibraryBooks book = findBook(bookName);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        // Process borrowing
        if (processBorrowing(student, book)) {
            saveBook(); // Update book file
        }
    }
}

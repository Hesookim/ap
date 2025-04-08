package ap.exercises.ex3;

import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;// Added for file handling
import java.io.IOException;// Added for file handling

public class Main_EX3_LM_1_2_A {
    private static final int maxBooks = 5000;  // Maximum number of books
    private static final int maxStudents = 5000; // Maximum number of students
    private static final String DATA_DIR = "ap/exercises/ex3/"; // To be sure the files are saved in exactly this location, regardless of which computer runs the code
    private static final String booksFile = DATA_DIR + "books.txt";
    private static final String studentsFiles = DATA_DIR + "students.txt";
    // Permanent books
    private static final Book defaultBook1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, 1178);
    private static final Book defaultBook2 = new Book("Harry Potter and the Philosopher's Stone", " J. K. Rowling", 1997, 223);

    // Permanent students
    private static final Student defaultStudent1 = new Student("Minoo", "Namdar", 403463154, "Computer engineering");
    private static final Student defaultStudent2 = new Student("Sevda", "Akhbari", 403496170, "MBA");

    private static Book[] books = new Book[maxBooks];
    private static Student[] students = new Student[maxStudents];
    private static int bookCount = 0;
    private static int studentCount = 0;

    // Create directory if it doesn't exist in the computers
    static {
        new File(DATA_DIR).mkdirs(); // Creates all necessary parent directories to save the files
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
            System.out.println("5. Save data to files");
            System.out.println("6. Exit");
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
                    scanner.nextLine();

                    books[bookCount++] = new Book(bookName, author, year, pages);
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
                    System.out.print("Enter student identification number: ");
                    int sin = scanner.nextInt();
                    System.out.print("Enter students major: ");
                    scanner.nextLine();  // Add this to consume the previous newline
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
                            Book book = books[i];
                            System.out.println("Book " + (i + 1));
                            System.out.println("Title: " + book.getName());
                            System.out.println("Author: " + book.getAuthor());
                            System.out.println("Year: " + book.getYear());
                            System.out.println("Pages: " + book.getPages());
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

                case 5 :// Save files
                    savebook();
                    saveStudents();
                    break;

                case 6 :// Exist
                    System.out.println("Existing the program.... ");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!!! Please enter a number (1-6): ");
            }
        }
    }

    // Method to save books data to file
    private static void savebook () {
        try (FileWriter  writer = new FileWriter(booksFile)) {
            for (int i = 0; i < bookCount; i++) {
            Book book = books[i];
                writer.write(book.getName() + ",");
                writer.write(book.getAuthor() + ",");
                writer.write(Integer.toString(book.getYear()) + ","); // Changing integer to string to be able to use correct form of number
                writer.write(Integer.toString(book.getPages()) + "\n");// Changing integer to string to be able to use correct form of number
            }
            System.out.println("Books saved to file " + booksFile);
        } catch (IOException e) {
            System.out.println("Error saving books data to file: " + e.getMessage());
        }
    }
    // Method to save students data to file
    private static void saveStudents () {
        try (FileWriter writer = new FileWriter(studentsFiles)) {
            for (int i = 0; i < studentCount; i++) {
                Student student = students[i];
                writer.write(student.getFirstName() +" " + student.getLastName() + ","); // Saving first and last name of the student together
                writer.write(Integer.toString(student.getSin()) + ",");// Changing integer to string to be able to use correct form of number
                writer.write(student.getMajor() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving students data to file:" + e.getMessage());
        }
    }
}
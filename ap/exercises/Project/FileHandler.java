package ap.exercises.Project;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private final String booksFile = "books.txt";
    private final String studentsFile = "students.txt";
    private final String librariansFile = "librarians.txt";
    private final String managerFile = "manager.txt";

    private Manager currentManager;

    public void saveAll(Library library) throws IOException {
        saveBooks(library.getBookList());
        saveStudents(library.getStudentList());
        saveLibrarians(library.getLibrarianList());
        saveManager(currentManager);
    }

    private void saveBooks(List<Book> books) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(booksFile))) {
            for (Book book : books) {
                writer.println(
                        book.getTitle() + "," +
                                book.getAuthor() + "," +
                                book.getYear() + "," +
                                book.getPages() + "," +
                                book.getCopies() + "," +
                                book.getISBN()
                );
            }
        }
    }

    private void saveStudents(List<Student> students) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(studentsFile))) {
            for (Student student : students) {
                writer.println(
                        student.getFirstName() + "," +
                                student.getLastName() + "," +
                                student.getId() + "," +
                                student.getMajor() + "," +
                                student.getPassword()
                );
            }
        }
    }

    private void saveLibrarians(List<Librarian> librarians) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(librariansFile))) {
            for (Librarian librarian : librarians) {
                writer.println(
                        librarian.getFirstName() + "," +
                                librarian.getLastName() + "," +
                                librarian.getId() + "," +
                                librarian.getPassword() + "," +
                                librarian.isRegistered()
                );
            }
        }
    }

    private void saveManager(Manager manager) throws IOException {
        if (manager == null) return;

        try (PrintWriter writer = new PrintWriter(new FileWriter(managerFile))) {
            writer.println(
                    manager.getFirstName() + "," +
                            manager.getLastName() + "," +
                            manager.getId() + "," +
                            manager.getPassword()
            );
        }
    }

    public Library loadAll(String libraryName) throws IOException {
        Library library = new Library(libraryName);

        loadBooks(library);
        loadStudents(library);
        loadLibrarians(library);
        loadManager(library);
        return library;
    }

    private void loadBooks(Library library) throws IOException {
        File file = new File(booksFile);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Book book = new Book(
                        parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        parts[5]
                );
                library.addBook(book, library.getRandomLibrarian());
            }
        }
    }

    private void loadStudents(Library library) throws IOException {
        File file = new File(studentsFile);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Student student = new Student(
                        parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        parts[3], parts[4]
                );
                library.registerStudent(student);
            }
        }
    }

    private void loadLibrarians(Library library) throws IOException {
        File file = new File(librariansFile);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Librarian librarian = new Librarian(
                        parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        parts[3]
                );
                if (Boolean.parseBoolean(parts[4])) {
                    librarian.completeRegistration(
                            Integer.parseInt(parts[2]),
                            parts[3]
                    );
                }
                library.addLibrarian(librarian);
            }
        }
    }

    private void loadManager(Library library) throws IOException {
        File file = new File(managerFile);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                Manager manager = new Manager(
                        parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        parts[3],
                        library
                );
                library.setManager(manager);
                this.currentManager = manager;
            }
        }
    }

    private Book findBookByISBN(Library library, String isbn) {
        for (Book book : library.getBookList()) {
            if (book.getISBN().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    private Student findStudentById(Library library, int id) {
        for (Student student : library.getStudentList()) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
}
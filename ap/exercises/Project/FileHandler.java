package ap.exercises.Project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class FileHandler {

    private static final String SAVE_LOCATION     = "ap/exercises/project/"; // <─ base folder
    private static final String BOOKS_FILE        = "books.txt";
    private static final String STUDENTS_FILE     = "students.txt";
    private static final String LIBRARIANS_FILE   = "librarians.txt";
    private static final String MANAGER_FILE      = "manager.txt";
    private static final String BORROWINGS_FILE   = "borrowings.txt";

    private static Path resolve(String file) {
        return Paths.get(SAVE_LOCATION).resolve(file);
    }

    private Manager currentManager;

    {
        try {
            Files.createDirectories(Paths.get(SAVE_LOCATION));
        } catch (IOException e) {
            System.err.println("ERROR: Failed to create directory '" +
                    SAVE_LOCATION + "': " + e.getMessage());
        }
    }

    public void saveAll(Library lib) throws IOException {
        saveBooks(lib.getBookList());
        saveStudents(lib.getStudentList());
        saveLibrarians(lib.getLibrarianList());
        saveManager(currentManager);
        saveBorrowings(lib.getBorrowBookList());
    }

    public Library loadAll(String libraryName) throws IOException {
        Library lib = new Library(libraryName);
        loadBooks(lib);
        loadStudents(lib);
        loadLibrarians(lib);
        loadManager(lib);
        loadBorrowings(lib);
        return lib;
    }

    private void saveBooks(List<Book> list) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(resolve(BOOKS_FILE));
             PrintWriter pw   = new PrintWriter(bw)) {

            for (Book b : list) {
                pw.println(b.getTitle() + "," + b.getAuthor() + "," + b.getYear() +
                        "," + b.getPages() + "," + b.getCopies() + "," + b.getISBN());
            }
        }
    }

    private void loadBooks(Library lib) throws IOException {
        Path path = resolve(BOOKS_FILE);
        if (Files.notExists(path)) return;

        for (String ln : Files.readAllLines(path)) {
            String[] p = ln.split(",", 6);
            Book b = new Book(p[0], p[1], Integer.parseInt(p[2]),
                    Integer.parseInt(p[3]), Integer.parseInt(p[4]), p[5]);
            lib.addBook(b, lib.getRandomLibrarian());
        }
    }

    private void saveStudents(List<Student> list) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(resolve(STUDENTS_FILE));
             PrintWriter pw   = new PrintWriter(bw)) {

            for (Student s : list) {
                pw.println(s.getFirstName() + "," + s.getLastName() + "," + s.getId() +
                        "," + s.getMajor() + "," + s.getPassword());
            }
        }
    }

    private void loadStudents(Library lib) throws IOException {
        Path path = resolve(STUDENTS_FILE);
        if (Files.notExists(path)) return;

        for (String ln : Files.readAllLines(path)) {
            String[] p = ln.split(",", 5);
            Student s = new Student(p[0], p[1], Integer.parseInt(p[2]), p[3], p[4]);
            lib.registerStudent(s);
        }
    }

    private void saveLibrarians(List<Librarian> list) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(resolve(LIBRARIANS_FILE));
             PrintWriter pw   = new PrintWriter(bw)) {

            for (Librarian l : list) {
                pw.println(l.getFirstName() + "," + l.getLastName() + "," +
                        l.getId() + "," + l.getPassword() + "," + l.isRegistered());
            }
        }
    }

    private void loadLibrarians(Library lib) throws IOException {
        Path path = resolve(LIBRARIANS_FILE);
        if (Files.notExists(path)) return;

        for (String ln : Files.readAllLines(path)) {
            String[] p = ln.split(",", 5);
            Librarian l = new Librarian(p[0], p[1], Integer.parseInt(p[2]), p[3]);
            if (Boolean.parseBoolean(p[4])) l.completeRegistration(l.getId(), p[3]);
            lib.addLibrarian(l);
        }
    }

    private void saveManager(Manager m) throws IOException {
        if (m == null) return;
        try (BufferedWriter bw = Files.newBufferedWriter(resolve(MANAGER_FILE));
             PrintWriter pw   = new PrintWriter(bw)) {
            pw.println(m);
        }
    }

    private void loadManager(Library lib) throws IOException {
        Path path = resolve(MANAGER_FILE);
        if (Files.notExists(path)) return;

        String ln = Files.readString(path).trim();
        if (ln.isEmpty()) return;

        String[] p = ln.split(",", 5);
        Manager m = new Manager(p[0], p[1], Integer.parseInt(p[2]), p[3], lib);
        lib.setManager(m);
        this.currentManager = m;
    }


    private void saveBorrowings(List<BorrowBook> list) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(resolve(BORROWINGS_FILE));
             PrintWriter pw   = new PrintWriter(bw)) {

            for (BorrowBook bb : list) {
                pw.println(bb.getBook().getISBN() + "|" +
                        bb.getStudent().getId() + "|" +
                        bb.getLendingLibrarian().getId() + "|" +
                        bb.getBorrowDate() + "|" +
                        bb.getDueDate() + "|" +
                        (bb.isReturned() ? bb.getReturnedDate() : "null") + "|" +
                        (bb.isReturned() && bb.getReceivingLibrarian() != null
                                ? bb.getReceivingLibrarian().getId() : "0"));
            }
        }
    }

    private void loadBorrowings(Library lib) throws IOException {
        Path path = resolve(BORROWINGS_FILE);
        if (Files.notExists(path)) return;

        for (String ln : Files.readAllLines(path)) {
            String[] p = ln.split("\\|", 8);

            Book      bk  = lib.findBook(p[0]);
            Student   st  = lib.findStudent(Integer.parseInt(p[1]));
            Librarian lend= lib.findLibrarian(Integer.parseInt(p[2]));
            LocalDate bor = LocalDate.parse(p[3]);
            LocalDate due = LocalDate.parse(p[4]);
            boolean   ret = !"null".equals(p[5]);
            LocalDate rDt = !"null".equals(p[5]) ? LocalDate.parse(p[5]) : null;
            Librarian rec = Integer.parseInt(p[6]) != 0
                    ? lib.findLibrarian(Integer.parseInt(p[6])) : null;

            BorrowBook bb = new BorrowBook(bk, st, lend, bor, due, rec);
            if (ret) bb.setReturnInfo(rDt, rec);
            lib.getBorrowBookList().add(bb);
        }
    }

    public void setCurrentManager(Manager m) {
        this.currentManager = m;
    }
}

package ap.exercises.Project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class FileHandler {

    private static final String BOOKS_FILE       = "books.txt";
    private static final String STUDENTS_FILE    = "students.txt";
    private static final String LIBRARIANS_FILE  = "librarians.txt";
    private static final String MANAGER_FILE     = "manager.txt";
    private static final String BORROWINGS_FILE  = "borrowings.txt";
    private Manager currentManager;

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
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book b : list) {
                pw.println(
                        b.getTitle() + "," +
                                b.getAuthor() + "," +
                                b.getYear() + "," +
                                b.getPages() + "," +
                                b.getCopies() + "," +
                                b.getISBN()
                );
            }
        }
    }

    private void loadBooks(Library lib) throws IOException {
        File f = new File(BOOKS_FILE);
        if (!f.exists()) return;
        for (String ln : Files.readAllLines(Path.of(BOOKS_FILE))) {
            String[] p = ln.split(",",6);
            Book b = new Book(p[0],p[1],Integer.parseInt(p[2]),
                    Integer.parseInt(p[3]),Integer.parseInt(p[4]),p[5]);
            lib.addBook(b, lib.getRandomLibrarian());
        }
    }

    private void saveStudents(List<Student> list) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student s : list) {
                pw.println(
                        s.getFirstName() + "," +
                                s.getLastName() + "," +
                                s.getId() + "," +
                                s.getMajor() + "," +
                                s.getPassword()
                );
            }
        }
    }

    private void loadStudents(Library lib) throws IOException {
        File f = new File(STUDENTS_FILE);
        if (!f.exists()) return;
        for (String ln : Files.readAllLines(Path.of(STUDENTS_FILE))) {
            String[] p = ln.split(",",5);
            Student s = new Student(p[0],p[1],Integer.parseInt(p[2]),p[3],p[4]);
            lib.registerStudent(s);
        }
    }

    private void saveLibrarians(List<Librarian> list) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LIBRARIANS_FILE))) {
            for (Librarian l : list) {
                pw.println(
                        l.getFirstName() + "," +
                                l.getLastName() + "," +
                                l.getId() + "," +
                                l.getPassword() + "," +
                                l.isRegistered()
                );
            }
        }
    }

    private void loadLibrarians(Library lib) throws IOException {
        File f = new File(LIBRARIANS_FILE);
        if (!f.exists()) return;
        for (String ln : Files.readAllLines(Path.of(LIBRARIANS_FILE))) {
            String[] p = ln.split(",",5);
            Librarian l = new Librarian(p[0],p[1],Integer.parseInt(p[2]),p[3]);
            if (Boolean.parseBoolean(p[4])) l.completeRegistration(l.getId(), p[3]);
            lib.addLibrarian(l);
        }
    }

    private void saveManager(Manager m) throws IOException {
        if (m == null) return;
        try (PrintWriter pw = new PrintWriter(new FileWriter(MANAGER_FILE))) {
            pw.println(m);
        }
    }

    private void loadManager(Library lib) throws IOException {
        File f = new File(MANAGER_FILE);
        if (!f.exists()) return;
        String ln = Files.readString(Path.of(MANAGER_FILE));
        if (ln.isEmpty()) return;
        String[] p = ln.split(",",4);
        Manager m = new Manager(p[0],p[1],Integer.parseInt(p[2]),p[3],lib);
        lib.setManager(m);
                this.currentManager = m;
            }


    private void saveBorrowings(List<BorrowBook> list) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BORROWINGS_FILE))) {
            for (BorrowBook bb : list) {
                pw.println(
                        bb.getBook().getISBN() + "|" +
                                bb.getStudent().getId() + "|" +
                                bb.getLendingLibrarian().getId() + "|" +
                                bb.getBorrowDate() + "|" +
                                bb.getDueDate() + "|" +
                                (bb.isReturned() ? bb.getReturnedDate() : "null") + "|" +
                                (bb.isReturned() && bb.getReceivingLibrarian() != null
                                        ? bb.getReceivingLibrarian().getId() : "0")
                );
            }
        }
    }

    private void loadBorrowings(Library lib) throws IOException {
        File f = new File(BORROWINGS_FILE);
        if (!f.exists()) return;

        for (String ln : Files.readAllLines(Path.of(BORROWINGS_FILE))) {
            String[] p = ln.split("\\|",8);
            Book bk   = lib.findBook(p[0]);
            Student st   = lib.findStudent(Integer.parseInt(p[1]));
            Librarian lend = lib.findLibrarian(Integer.parseInt(p[2]));
            LocalDate bor = LocalDate.parse(p[3]);
            LocalDate due = LocalDate.parse(p[4]);
            boolean ret  = Boolean.parseBoolean(p[5]);
            LocalDate rDt = !"null".equals(p[6]) ? LocalDate.parse(p[6]) : null;
            Librarian rec  = Integer.parseInt(p[7])!=0 ? lib.findLibrarian(Integer.parseInt(p[7])) : null;

            BorrowBook bb = new BorrowBook(bk,st,lend,bor,due,rec);
            if (ret) bb.setReturnInfo(rDt, rec);
            lib.getBorrowBookList().add(bb);
        }
    }

    public void setCurrentManager(Manager m) {
        this.currentManager = m;
    }
}

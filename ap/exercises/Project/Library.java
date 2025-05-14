package ap.exercises.Project;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class Library {
    private final String name;
    private List<Book> bookList;
    private List<Student>  studentList;
    private List<Librarian>  librarianList;
    private Random random = new Random();
    private final Librarian librarian1;
    private final Librarian librarian2;
    private List<BorrowBook>  borrowBookList;
    private Manager manager;
    private User currentUser;

    Library(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Library name cannot be null or empty!");
        }

        this.name = name;
        bookList = new ArrayList<>();
        studentList = new ArrayList<>();
        borrowBookList = new ArrayList<>();
        this.librarian1 = new Librarian("Librarian", "One", 39876453, "@hduekfkf");
        this.librarian2 = new Librarian("Librarian", "Two", 39876454, "hwjc&kkpqo");
        this.librarianList = List.of(librarian1, librarian2);
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Librarian getRandomLibrarian() {
        return librarianList.get(random.nextInt(librarianList.size()));
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void addBook(Book book, User user) {
        if (!(user instanceof Librarian)) {
            throw new SecurityException("Only librarians can add books!");
        }

        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null!");
        }

        bookList.add(book);
    }


    public void registerStudent(Student student) {
        if (student == null) throw new IllegalArgumentException("Student cannot be null!");
        if (studentList.contains(student)) throw new IllegalStateException("Student already registered!");
        studentList.add(student);
    }

    public boolean signIn(int userId, String password) {
        for (Student student : studentList) {
            if (student.getId() == userId && student.authenticate(password)) {
                currentUser = student;
                return true;
            }
        }

        if (manager != null && manager.getId() == userId && manager.authenticate(password) && manager.verifyManager()) {
                currentUser = manager;
                return true;
        }

        return false;
    }

    public void signOut() {
        currentUser = null;
    }


    public Librarian findLibrarianByName(String firstName, String lastName) {
        for (Librarian l : librarianList) {
            if (l.getFirstName().equalsIgnoreCase(firstName) && l.getLastName().equalsIgnoreCase(lastName)) {
                return l;
            }
        }
        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void addLibrarian(Librarian librarian) {
        if (librarian == null) {
            throw new IllegalArgumentException("Librarian cannot be null!");
        }

        if (!(librarianList instanceof ArrayList)) {
            librarianList = new ArrayList<>(librarianList);
        }

        for (Librarian newLibrarian : librarianList) {
            if (newLibrarian.getId() == librarian.getId()) {
                throw new IllegalArgumentException("Librarian already registered!");
            }
        }

        librarianList.add(librarian);
        System.out.println("Librarian " + librarian.getFirstName() + " " + librarian.getLastName() + " added successfully!");
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getTitle().contains(query) || book.getAuthor().contains(query)) {
                results.add(book);
            }
        }
        return results;
    }
}

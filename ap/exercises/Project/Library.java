package ap.exercises.Project;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class Library {
    private List<Book> bookList;
    private List<Student> studentList;
    private List<Librarian> librarianList;
    private final Librarian librarian1;
    private final Librarian librarian2;
    private List<BorrowBook> borrowBookList;
    private Manager manager;
    private User currentUser;

     public Library(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Library name cannot be null or empty!");
        }

        bookList = new ArrayList<>();
        studentList = new ArrayList<>();
        borrowBookList = new ArrayList<>();
        this.librarian1 = new Librarian("Librarian", "One", 39876453, "@hduekfkf");
        this.librarian2 = new Librarian("Librarian", "Two", 39876454, "hwjc&kkpqo");
        this.librarianList = new ArrayList<>();
         this.librarianList.add(librarian1);
         this.librarianList.add(librarian2);
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Librarian getRandomLibrarian() {
        Random random = new Random();
        return librarianList.get(random.nextInt(librarianList.size()));
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public  List<Student> getStudentList() {
        return studentList;
    }

    public List<Librarian> getLibrarianList() {
        return librarianList;
    }


    public void addBook(Book book, User user) {
        if (!(user instanceof Librarian)) {
            System.out.println("Only librarians can add books!");
            return;
        }

        if (book == null) {
            System.out.println("Book cannot be null!");
            return;
        }

        bookList.add(book);
        System.out.println("Book added successfully.");
    }


    public void registerStudent(Student student) {
        if (student == null) {
            System.out.println("Student cannot be null!");
            return;
        }

        if (studentList.contains(student)) {
            System.out.println("Student already registered!");
            return;
        }

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
            System.out.println("Librarian cannot be null!");
            return;
        }

        if (!(librarianList instanceof ArrayList)) {
            librarianList = new ArrayList<>(librarianList);
        }

        for (Librarian existing : librarianList) {
            if (existing.getId() == librarian.getId()) {
                System.out.println("Librarian already registered!");
                return;
            }
        }

        librarianList.add(librarian);
        System.out.println("Librarian " + librarian.getFirstName() + " " + librarian.getLastName() + " added successfully!");
    }


    private static final int STANDARD_LOAN_DAYS = 14;// Default borrowed days are 14

    private final List<BorrowBook> pendingBorrow = new ArrayList<>();

    private final List<BorrowBook> pendingReturn = new ArrayList<>();

    public void requestBorrow(int studentId, String isbn) {
        Student s = findStudent(studentId);
        Book    b = findBook(isbn);
        if (b == null || !b.isAvailable())
            System.out.println("Book not available!");
        BorrowBook loan = new BorrowBook(
                b, s, null,
                LocalDate.now(),
                LocalDate.now().plusDays(STANDARD_LOAN_DAYS),
                null);

        pendingBorrow.add(loan);
        System.out.println("Request stored. A librarian must approve it.");
    }

    public void confirmBorrow(int pendingIndex, Librarian confirmer) {
        BorrowBook loan = pendingBorrow.remove(pendingIndex);

        if (loan.getBook().getCopies() <= 0)
            System.out.println("No copies of the wanted book is left!");

        loan.setLendingLibrarian(confirmer);
        loan.getBook().setCopies(loan.getBook().getCopies() - 1);
        borrowBookList.add(loan);

        System.out.println("Loan confirmed : " + loan);
    }

    public void requestReturn(int studentId, String isbn) {
        BorrowBook loan = findActiveLoan(studentId, isbn);
        pendingReturn.add(loan);
        System.out.println("Return stored. A librarian must confirm it.");
    }

    public void confirmReturn(int pendingIndex, Librarian receiver) {
        BorrowBook loan = pendingReturn.remove(pendingIndex);

        loan.setReturnInfo(LocalDate.now(), receiver);
        loan.getBook().setCopies(loan.getBook().getCopies() + 1);

        System.out.println("Return confirmed -> " + loan);
    }

    public Student findStudent(int id) {
        for (Student s : studentList) {
            if (s.getId() == id) {
                return s;
            }
        }
        System.out.println("Student not found");
        return null;
    }

    public Book findBook(String isbn) {
        for (Book b : bookList) {
            if (b.getISBN().equals(isbn)) {
                return b;
            }
        }
        return null;
    }

    private BorrowBook findActiveLoan(int studentId, String isbn) {
        for (BorrowBook l : borrowBookList) {
            if (!l.isReturned() && l.getStudent().getId() == studentId && l.getBook().getISBN().equals(isbn)) {
                return l;
            }
        }
        System.out.println("Active loan not found");
        return null;
    }

    public List<BorrowBook> getActiveLoansForStudent(int studentId) {
        List<BorrowBook> result = new ArrayList<>();
        for (BorrowBook b : borrowBookList) {
            if (!b.isReturned() && b.getStudent().getId() == studentId) {
                result.add(b);
            }
        }
        return result;
    }

    public List<BorrowBook> getLoanHistoryForStudent(int studentId) {
        List<BorrowBook> result = new ArrayList<>();
        for (BorrowBook b : borrowBookList) {
            if (b.getStudent().getId() == studentId) {
                result.add(b);
            }
        }
        return result;
    }

    public List<BorrowBook> getOverdueLoans() {
        List<BorrowBook> overdue = new ArrayList<>();
        for (BorrowBook bb : borrowBookList) {
            if (bb.isOverdue()) {
                overdue.add(bb);
            }
        }
        return overdue;
    }

    public int getIssueCountForLibrarian(int librarianId) {
        int count = 0;
        for (BorrowBook bb : borrowBookList) {
            if (bb.getLendingLibrarian() != null && bb.getLendingLibrarian().getId() == librarianId) {
                count++;
            }
        }
        return count;
    }

    public int getReturnCountForLibrarian(int librarianId) {
        int count = 0;
        for (BorrowBook bb : borrowBookList) {
            if (bb.isReturned() && bb.getReceivingLibrarian() != null && bb.getReceivingLibrarian().getId() == librarianId) {
                count++;
            }
        }
        return count;
    }

    public List<Book> getTopBorrowedBooksLastYear(int topN) {
        int currentYear = Year.now().getValue();
        List<Book> books = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        for (BorrowBook bb : borrowBookList) {
            if (bb.getBorrowDate().getYear() >= currentYear - 1) {
                Book bk = bb.getBook();
                int idx = books.indexOf(bk);
                if (idx == -1) {
                    books.add(bk);
                    counts.add(1);
                } else {
                    counts.set(idx, counts.get(idx) + 1);
                }
            }
        }

        List<Book> result = new ArrayList<>();
        for (int k = 0; k < topN && !books.isEmpty(); k++) {
            int maxIdx = 0;
            for (int i = 1; i < counts.size(); i++) {
                if (counts.get(i) > counts.get(maxIdx)) maxIdx = i;
            }
            result.add(books.get(maxIdx));
            books.remove(maxIdx);
            counts.remove(maxIdx);
        }
        return result;
    }

    public List<BorrowBook> getLoansHandledByLibrarian(int librarianId) {
        List<BorrowBook> list = new ArrayList<>();
        for (BorrowBook bb : borrowBookList) {
            if (bb.getLendingLibrarian().getId() == librarianId ||
                    (bb.isReturned() && bb.getReceivingLibrarian() != null &&
                            bb.getReceivingLibrarian().getId() == librarianId)) {
                list.add(bb);
            }
        }
        return list;
    }

    public List<BorrowBook> getLoansOfStudent(int studentId) {
        return getLoanHistoryForStudent(studentId);
    }

    Librarian findLibrarian(int id) {
        for (Librarian l : librarianList) if (l.getId()==id) return l;
        return null;
    }

    public List<BorrowBook> getBorrowBookList() {
        return borrowBookList;
    }
}
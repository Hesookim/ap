package ap.exercises.finalProject;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> bookList;
    private List<Student> studentList;
    private List<Operator> operatorList;
    private List<BorrowBook> borrowBookList;
    @JsonManagedReference
    private Manager manager;
    private User currentUser;

    private static final int STANDARD_LOAN_DAYS = 14; // Default loan days
    private final List<BorrowBook> pendingBorrow = new ArrayList<>();
    private final List<BorrowBook> pendingReturn = new ArrayList<>();

    public Library(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Library name cannot be null or empty!");
        }

        bookList = new ArrayList<>();
        studentList = new ArrayList<>();
        borrowBookList = new ArrayList<>();
        operatorList = new ArrayList<>();
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public boolean signIn(int userId, String password) {
        for (Student student : studentList) {
            if (student.getId() == userId && student.authenticate(password)) {
                currentUser = student;
                return true;
            }
        }
        return false;
    }

    public boolean signInAsManager(int userId, String password){
        if (manager != null && manager.getId() == userId && manager.authenticate(password)) {
            currentUser = manager;
            return true;
        }
        return false;
    }

    public void signOut() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void addBook(Book book, User user) {
        if (!(user instanceof Operator)) {
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

    public Book findBook(String isbn) {
        for (Book b : bookList) if (b.getISBN().equals(isbn)) return b;
        return null;
    }

    public Student findStudent(int id) {
        for (Student s : studentList) if (s.getId() == id) return s;
        System.out.println("Student not found");
        return null;
    }

    public void addOperator(Operator operator) {
        if (operator == null) {
            System.out.println("Operator cannot be null!");
            return;
        }
        for (Operator existing : operatorList) {
            if (existing.getId() == operator.getId()) {
                System.out.println("Operator already registered!");
                return;
            }
        }
        operatorList.add(operator);
        System.out.println("Operator " + operator.getFullName() + " added successfully!");
    }

    public Operator getRandomOperator() {
        if (operatorList.isEmpty()) return null;
        int idx = (int) (Math.random() * operatorList.size());
        return operatorList.get(idx);
    }

    public void requestBorrow(int studentId, String isbn) { // <-----
        Student s = findStudent(studentId);
        Book b = findBook(isbn);
        if (b == null || !b.isAvailable()) {
            System.out.println("Book not available!");
            return;
        }
        BorrowBook loan = new BorrowBook(b, s, LocalDate.now(), LocalDate.now().plusDays(STANDARD_LOAN_DAYS));
        pendingBorrow.add(loan);
        System.out.println("Request stored. A operator must approve it.");
    }

    public void confirmBorrow(int pendingIndex, Operator confirmer) {
        BorrowBook loan = pendingBorrow.remove(pendingIndex);

        if (loan.getBook().getCopies() <= 0) {
            System.out.println("No copies left!");
            return;
        }

        if (confirmer == null) confirmer = getRandomOperator();
        loan.confirmBorrow(confirmer);

        loan.getBook().setCopies(loan.getBook().getCopies() - 1);
        borrowBookList.add(loan);

        System.out.println("Loan confirmed: " + loan);
    }

    public void requestReturn(int studentId, String isbn) { // <----
        BorrowBook loan = findActiveLoan(studentId, isbn);
        if (loan == null) return;
        pendingReturn.add(loan);
        System.out.println("Return stored. A operator must confirm it.");
    }

    public void returnBook(BorrowBook borrowBook, Operator operator) {
        if (borrowBook.isReturned()) {
            System.out.println("This book has already been returned!");
            return;
        }
        borrowBook.confirmReturn(operator, LocalDate.now());
    }

    public void confirmReturn(int pendingIndex, Operator receiver) {
        BorrowBook loan = pendingReturn.remove(pendingIndex);
        if (receiver == null) receiver = getRandomOperator();
        loan.confirmReturn(receiver, LocalDate.now());
        loan.getBook().setCopies(loan.getBook().getCopies() + 1);
        System.out.println("Return confirmed: " + loan);
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
        for (BorrowBook b : borrowBookList) if (!b.isReturned() && b.getStudent().getId() == studentId) result.add(b);
        return result;
    }

    public List<BorrowBook> getLoanHistoryForStudent(int studentId) {
        List<BorrowBook> result = new ArrayList<>();
        for (BorrowBook b : borrowBookList) result.add(b);
        return result;
    }

    public List<BorrowBook> getLoansOfStudent(int studentId) {
        return getLoanHistoryForStudent(studentId);
    }

    public List<BorrowBook> getLoansHandledByLibrarian(int librarianId) {
        List<BorrowBook> result = new ArrayList<>();
        for (BorrowBook loan : borrowBookList) {
            if (loan.getLendingOperator() != null &&
                    loan.getLendingOperator().getId() == librarianId) {
                result.add(loan);
            }
        }
        return result;
    }

    public List<BorrowBook> getOverdueLoans() {
        List<BorrowBook> overdue = new ArrayList<>();
        for (BorrowBook loan : borrowBookList) {
            if (loan.isOverdue() && !loan.isReturned()) {
                overdue.add(loan);
            }
        }
        return overdue;
    }

    public int getIssueCountForOperator(int librarianId) {
        int count = 0;
        for (BorrowBook loan : borrowBookList) {
            if (loan.getLendingOperator() != null &&
                    loan.getLendingOperator().getId() == librarianId) {
                count++;
            }
        }
        return count;
    }

    public int getReturnCountForOperator(int operatorId) {
        int count = 0;
        for (BorrowBook loan : borrowBookList) {
            if (loan.getReceivingOperator() != null &&
                    loan.getReceivingOperator().getId() == operatorId) {
                count++;
            }
        }
        return count;
    }

    public List<Book> getBookList() { return bookList; }
    public List<Student> getStudentList() { return studentList; } // <---
    public List<Operator> getOperatorList() { return operatorList; }
    public List<BorrowBook> getBorrowBookList() { return borrowBookList; }

    public Operator findOperator(int operatorId) {
        for (Operator op : operatorList) {
            if (op.getId() == operatorId) return op;
        }
        return null;
    }

    public List<Book> searchBooks(String title, String author, Integer year, Boolean available) {// <---- for guest
        List<Book> results = new ArrayList<>();
        for (Book book : bookList) {
            boolean matches = true;

            if (title != null && !title.isEmpty()) {
                matches = book.getTitle().toLowerCase().contains(title.toLowerCase());
            }

            if (matches && author != null && !author.isEmpty()) {
                matches = book.getAuthor().toLowerCase().contains(author.toLowerCase());
            }

            if (matches && year != null) {
                matches = book.getYear() == year;
            }

            if (matches && available != null) {
                matches = book.isAvailable() == available;
            }

            if (matches) {
                results.add(book);
            }
        }
        return results;
    }

    public void showLibraryStats() {// <------- for guest
        System.out.println("Total Students: " + studentList.size());
        System.out.println("Total Books: " + bookList.size());
        System.out.println("Total Loans: " + borrowBookList.size());

        int activeLoans = 0;
        for (BorrowBook loan : borrowBookList) {
            if (!loan.isReturned()) activeLoans++;
        }
        System.out.println("Active Loans: " + activeLoans);
    }

    public void showStudentStats(int studentId) {
        Student student = findStudent(studentId);
        if (student == null) return;

        List<BorrowBook> studentLoans = getLoanHistoryForStudent(studentId);
        int total = studentLoans.size();
        int notReturned = 0;
        int delayed = 0;

        for (BorrowBook loan : studentLoans) {
            if (!loan.isReturned()) notReturned++;
            if (loan.isOverdue()) delayed++;
        }

        System.out.println("Student: " + student.getFullName());
        System.out.println("Total Loans: " + total);
        System.out.println("Not Returned: " + notReturned);
        System.out.println("Delayed Returns: " + delayed);
    }

    public Operator findOperatorByName(String firstName, String lastName) {
        for (Operator op : operatorList) {
            if (op.getFirstName().equalsIgnoreCase(firstName) &&
                    op.getLastName().equalsIgnoreCase(lastName)) {
                return op;
            }
        }
        return null;
    }

    public int getTotalBorrowedByStudent(Student s) {
        return (int) borrowBookList.stream()
                .filter(bb -> bb.getStudent().equals(s))
                .count();
    }

    public int getTotalLateReturnsByStudent(Student s) {
        return (int) borrowBookList.stream()
                .filter(bb -> bb.getStudent().equals(s))
                .filter(BorrowBook::isReturned)
                .filter(bb -> bb.getReturnedDate().isAfter(bb.getDueDate()))
                .count();
    }

    public List<Student> getTop10LateStudents() {
        return studentList.stream()
                .sorted((s1, s2) -> Integer.compare(
                        getTotalLateReturnsByStudent(s2),
                        getTotalLateReturnsByStudent(s1)
                ))
                .limit(10)
                .toList();
    }

    public void requestBorrow(int studentId, String isbn, LocalDate startDate, LocalDate endDate) {
        Student s = findStudent(studentId);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }
        if (!s.isActive()) {
            System.out.println("Student account is deactivated! Cannot borrow books.");
            return;
        }

        Book b = findBook(isbn);
        if (b == null || !b.isAvailable()) {
            System.out.println("Book not available!");
            return;
        }

        BorrowBook loan = new BorrowBook(b, s, startDate, endDate);
        pendingBorrow.add(loan);
        System.out.println("Request stored. A librarian must approve it.");
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
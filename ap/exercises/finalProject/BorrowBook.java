package ap.exercises.finalProject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowBook {
    private Book book;
    private Student student;
    private Operator lendingOperator;
    private Operator receivingOperator;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public BorrowBook(Book book, Student student, LocalDate borrowDate, LocalDate dueDate) {
        if (book == null || student == null || borrowDate == null || dueDate == null) {
            throw new IllegalArgumentException("Book, student, borrowDate, and dueDate cannot be null!");
        }
        if (dueDate.isBefore(borrowDate)) {
            throw new IllegalArgumentException("Due date cannot be before borrow date!");
        }

        this.book = book;
        this.student = student;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.lendingOperator = null;
        this.receivingOperator = null;
        this.returnedDate = null;
    }

    public Book getBook() { return book; }
    public Student getStudent() { return student; }
    public Operator getLendingOperator() { return lendingOperator; }
    public Operator getReceivingOperator() { return receivingOperator; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnedDate() { return returnedDate; }

    public void confirmBorrow(Operator operator) {
        if (operator == null) throw new IllegalArgumentException("Lending operator cannot be null");
        this.lendingOperator = operator;
    }

    public void confirmReturn(Operator operator, LocalDate returnedDate) {
        if (operator == null || returnedDate == null) {
            throw new IllegalArgumentException("Receiving operator and returned date cannot be null");
        }
        this.receivingOperator = operator;
        this.returnedDate = returnedDate;
    }

    public boolean isReturned() {
        return returnedDate != null;
    }

    public boolean isOverdue() {
        if (!isReturned()) {
            return LocalDate.now().isAfter(dueDate);
        }
        return returnedDate.isAfter(dueDate);
    }

    public long getDaysOverdue() {
        if (!isOverdue()) return 0;
        LocalDate ref = isReturned() ? returnedDate : LocalDate.now();
        return ChronoUnit.DAYS.between(dueDate, ref);
    }

    public long getLoanDuration() {
        LocalDate end = isReturned() ? returnedDate : LocalDate.now();
        return ChronoUnit.DAYS.between(borrowDate, end);
    }

    public void requestBorrow(int studentId, String isbn, LocalDate startDate, LocalDate endDate) {
    }

    @Override
    public String toString() {
        String status;
        if (isReturned()) {
            status = "Returned on " + returnedDate +
                    " by " + (receivingOperator != null ? receivingOperator.getFullName() : "N/A");
        } else {
            status = "Due on " + dueDate + (isOverdue() ? " (OVERDUE)" : "") +
                    ", Lent by " + (lendingOperator != null ? lendingOperator.getFullName() : "Pending");
        }

        return "Borrow[Book='" + book.getTitle() +
                "', Student='" + student.getFullName() +
                "', LoanDate=" + borrowDate +
                ", " + status + ']';
    }
}

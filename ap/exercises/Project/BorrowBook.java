package ap.exercises.Project;

import java.time.LocalDate;

public class BorrowBook {
    private Book book;
    private Student student;
    private Librarian lendingLibrarian;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private Librarian receivingLibrarian;

    public  BorrowBook(Book book, Student student, Librarian giverLibrarian, LocalDate borrowDate, LocalDate dueDate, Librarian receivingLibrarian) {
        if (book == null || student == null || receivingLibrarian == null ||
                borrowDate == null || dueDate == null) {
            throw new IllegalArgumentException("Can not be null!");
        }

        this.book = book;
        this.student = student;
        this.lendingLibrarian = giverLibrarian;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public Book getBook() {
        return book;
    }

    public Student getStudent() {
        return student;
    }

    public Librarian getLendingLibrarian() {
        return lendingLibrarian;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate  getReturnedDate()      {
        return returnedDate;
    }

    public Librarian getReceivingLibrarian() {
        return lendingLibrarian;
    }

    public void setReturnInfo(LocalDate returnedDate, Librarian receiver){
        this.returnedDate = returnedDate;
        this.receivingLibrarian = receiver;
    }

    public void setLendingLibrarian(Librarian l){
        this.lendingLibrarian = l;
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

    public long getDaysOverdue(){
        if(!isOverdue()) return 0;
        LocalDate ref = isReturned()? returnedDate : LocalDate.now();
        return dueDate.until(ref).getDays();
    }

    @Override
    public String toString(){
        String status = isReturned()
                ? "Returned on " + returnedDate
                : "Due on " + dueDate + (isOverdue()? " (OVERDUE)" : "");
        return "Borrow[Book='" + book.getTitle() +
                "', Student='" + student.getFullName() +
                "', LoanDate=" + borrowDate +
                ", " + status + ']';
    }
}
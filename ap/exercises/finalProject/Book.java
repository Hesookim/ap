package ap.exercises.finalProject;

public class Book {
    private String title;
    private String author;
    private int year;
    private int pages;
    private int copies;
    private String ISBN;
    private boolean isAvailable;

    public Book(String title, String author, int year, int pages, int copies, String ISBN){
        validBookData(title, author, year, pages, copies, ISBN);
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
        this.copies = copies;
        this.ISBN = ISBN;
        updateAvailability();
    }

    public Book() {
        this.title = "";
        this.author = "";
        this.year = 0;
        this.pages = 0;
        this.copies = 0;
        this.ISBN = "";
        this.isAvailable = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getPages() {
        return pages;
    }

    public int getCopies() {
        return copies;
    }

    public String getISBN() { return ISBN; }

    public void setTitle(String title) {
        validTitle(title);
        this.title = title;
    }

    public void setAuthor(String author) {
        validAuthor(author);
        this.author = author;
    }

    public void setYear(int year) {
        validYear(year);
        this.year = year;
    }

    public void setPages(int pages) {
        validPages(pages);
        this.pages = pages;
    }

    public void setCopies(int copies) {
        validCopies(copies);
        this.copies = copies;
        updateAvailability();
    }

    public void setISBN(String ISBN) {
        validISBN(ISBN);
        this.ISBN = ISBN;
    }

    private void validBookData(String title, String author, int year, int pages, int copies,  String ISBN) {
        validTitle(title);
        validAuthor(author);
        validYear(year);
        validPages(pages);
        validCopies(copies);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void validTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("title cannot be null or empty!");
        }
    }

    public void validAuthor(String author) {
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("author cannot be null or empty!");
        }
    }

    private void validYear(int year) {
        int currentYear = java.time.Year.now().getValue();
        if (year < 1 || year > currentYear) {
            throw new IllegalArgumentException("Year must be between 1 and " + currentYear);
        }
    }

    public void validPages(int pages) {
        if (pages <= 0 ){
            throw new IllegalArgumentException("pages must be greater than 0!");
        }
    }

    public void validCopies(int copies) {
        if (copies < 0){
            throw new IllegalArgumentException("copies must be greater than 0!");
        }
    }

    public void validISBN(String ISBN) {
        if (ISBN == null || ISBN.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty!");
        }

        else if (ISBN.length() < 13) { // Based on current international standards
            throw new IllegalArgumentException("ISBN must be greater than 13 characters!");
        }
    }

    private void updateAvailability() {
        this.isAvailable = copies > 0;
    }
}
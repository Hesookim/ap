package ap.exercises.Project;

public class Manager extends User {
    private Library library;

    public Manager(String firstname, String lastname, int managerId, String password, Library library) {
        super(firstname, lastname, managerId, password);
        this.library = library;
    }

    public boolean verifyManager() {
        return library.getCurrentUser() != null && library.getCurrentUser().getId() == this.getId();
    }

    public void addLibrarian(String firstName, String lastName) {
        if (!verifyManager()) {
            throw new SecurityException("Only manager can add librarian!");
        }

        Librarian newLibrarian = new Librarian(firstName, lastName);
        library.addLibrarian(newLibrarian);
    }
}
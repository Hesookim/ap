package ap.exercises.Project;

public class Manager extends User {
    private Library library;

    public Manager(String firstname, String lastname, int managerId, String password, Library library) {
        super(firstname, lastname, managerId, password);
        this.library = library;
    }

    public boolean verifyManager() {
        User currentUser = library.getCurrentUser();
        return currentUser != null && currentUser.getId() == this.getId() && currentUser instanceof Manager;
    }


    public void addLibrarian(String firstName, String lastName) {
        if (!verifyManager()) {
            throw new SecurityException("Only manager can add librarian!");
        }

        Librarian newLibrarian = new Librarian(firstName, lastName);
        library.addLibrarian(newLibrarian);
    }

    @Override
    public String toString() {
        return "Manager," + getFirstName() + "," + getLastName() + "," + getId() + "," + getPassword();
    }
}
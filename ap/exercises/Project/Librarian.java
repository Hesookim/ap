package ap.exercises.Project;

public class Librarian extends User {
    private boolean registered = false;

    public Librarian(String firstName, String lastName) {
        super(firstName, lastName, 0, "12345678");
    }

    public Librarian(String firstName, String lastName, int employeeId, String password) {
        super(firstName, lastName, employeeId, password);
        this.registered = true;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void completeRegistration(int employeeId, String password) {
        if (this.registered) {
            return;
        }

        validId(employeeId);
        validPassword(password);

        super.setId(employeeId);
        this.setRealPassword(password);
        this.registered = true;
    }

    private void setRealPassword(String password) {
        super.validPassword(password);
        super.setNewPassword(password);
    }

    public void setLendingLibrarian(Librarian lendingLibrarian) {
        this.registered = lendingLibrarian.isRegistered();
    }


    @Override
    public String toString() {
        return "Librarian," + getFirstName() + "," + getLastName() + "," + getId() + "," + getPassword();
    }
}
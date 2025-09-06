package ap.exercises.finalProject;

import java.time.LocalDate;

public class Operator extends User {
    private boolean registered = false;

    public Operator(String firstName, String lastName) {
        super(firstName, lastName, 12345678, "12345678");
    }

    public Operator(String firstName, String lastName, int employeeId, String password) {
        super(firstName, lastName, employeeId, password);
        this.registered = true;
    }

    public Operator() {
        super("", "", 0, "");
        this.registered = false;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public void completeRegistration(int employeeId, String password) {
        validId(employeeId);
        validPassword(password);

        super.setId(employeeId);
        this.setRealPassword(password);
        this.registered = true;
    }

    private void setRealPassword(String password) {// <-------
        super.validPassword(password);
        super.setNewPassword(password);
    }

    public void setStudentActiveStatus(Student student, boolean active) {
        if (!this.isRegistered()) {
            throw new SecurityException("Operator must be registered to change student status!");
        }
        student.setActive(active);
    }

    public void receiveBook(BorrowBook borrowBook) {
        borrowBook.confirmReturn(this, LocalDate.now());
    }


    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        return "Operator," + getFirstName() + "," + getLastName() + "," + getId();
    }
}
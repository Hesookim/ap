package ap.exercises.finalProject;

import java.time.LocalDate;

public class Student extends User {
    private String major;
    private LocalDate joinDate;
    private boolean active = true;

    public Student(String firstName, String lastName, int sin, String major, String password) {
        super(firstName, lastName, sin, password);
        validMajor(major);
        this.major = major;
        this.joinDate = LocalDate.now();
    }

    public Student() {
        super();
        this.major = "";
        this.joinDate = LocalDate.now();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        validMajor(major);
        this.major = major;
    }

    private void validMajor(String major) {
        if (major == null || major.isEmpty()) {
            throw new IllegalArgumentException("Major cannot be null or empty!!!");
        }
    }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        return "Student [" + "firstName: '" + getFirstName() + ", lastName: '" + getLastName() + ", sin: " + getId() + ", major: '" + major  + ", joinDate: " + joinDate + ']';
    }
}

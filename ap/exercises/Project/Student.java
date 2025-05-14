package ap.exercises.Project;

import java.time.LocalDate;

public class Student extends User {
    private String major;
    private final LocalDate joinDate;

    public Student(String firstName, String lastName, int sin, String major, String password) {
        super(firstName, lastName, sin, password);
        validMajor(major);
        this.major = major;
        this.joinDate = LocalDate.now();
    }

    public String getMajor() {
        return major;
    }

    public LocalDate getJoinDate() {
        return joinDate;
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

    public boolean verifyPassword(String inputPassword) {
        return getPassword().equals(inputPassword);
    }

    @Override
    public String toString() {
        return "Student [" + "firstName: '" + getFirstName() + ", lastName: '" + getLastName() + ", sin: " + getId() + ", major: '" + major + ", " + ", joinDate: " + joinDate + ']';
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
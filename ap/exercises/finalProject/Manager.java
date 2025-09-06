package ap.exercises.finalProject;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

public class Manager extends User {
    @JsonBackReference
    private Library library;

    public Manager(String firstname, String lastname, int managerId, String password, Library library) {
        super(firstname, lastname, managerId, password);
        this.library = library;
    }

    public Manager() {
        super("", "", 0, "");
        this.library = null;
    }

    public boolean verifyManager() {
        return this.library != null
                && this.library.getManager() != null
                && this.library.getManager().getId() == this.getId();
    }

    public void addOperator(String firstName, String lastName) {
        if (!verifyManager()) {
            throw new SecurityException("Only manager can add Operator!");
        }

        Operator newOperator = new Operator(firstName, lastName);

        int defaultId = 12345678;
        String defaultPassword = "12345678";

        newOperator.completeRegistration(defaultId, defaultPassword);
        library.addOperator(newOperator);
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public void printStudentStatistics(Library lib) {
        System.out.println("===== Student Statistics =====");
        for (Student s : lib.getStudentList()) {
            int borrowed = lib.getTotalBorrowedByStudent(s);
            int late = lib.getTotalLateReturnsByStudent(s);
            System.out.println(s.getFullName() + " | Borrowed: " + borrowed + " | Late Returns: " + late);
        }

        System.out.println("\nTop 10 students with most late returns:");
        List<Student> top10 = lib.getTop10LateStudents();
        for (int i = 0; i < top10.size(); i++) {
            Student s = top10.get(i);
            System.out.println((i + 1) + ". " + s.getFullName() + " | Late Returns: " +
                    lib.getTotalLateReturnsByStudent(s));
        }
    }

    @Override
    public String toString() {
        return "Manager," + getFirstName() + "," + getLastName() + "," + getId();
    }
}
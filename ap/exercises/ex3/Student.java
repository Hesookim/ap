package ap.exercises.ex3;

class Student {
    private String firstName;
    private String lastName;
    private int sin;// student identification number
    private String major;

    public Student(String firstName, String lastName, int sin, String major) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sin = sin;
        this.major = major;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSin() {
        return sin;
    }

    public String getMajor() {
        return major;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
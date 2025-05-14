package ap.exercises.Project;

public abstract class User {
    private String firstName;
    private String lastName;
    private int id;
    private String password;

    protected User(String firstName, String lastName, int id, String password) {
        validName(firstName, lastName);
        validId(id);
        validPassword(password);

        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    protected String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        validName(firstName, this.lastName);
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        validName(this.firstName, lastName);
        this.lastName = lastName;
    }

    public void setPassword(String currentPassword, String newPassword) {
        if (!authenticate(currentPassword)) {
            throw new IllegalArgumentException("Current password doesn't match!");
        } else if (!newPassword.equals(currentPassword)) {
            throw new IllegalArgumentException("New password is equal to current password!");
        }
        validPassword(newPassword);
        this.password = newPassword;
    }

    protected final void validPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty!");
        }
        else if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters!");
        }
        else if (password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain spaces!");
        }
    }

    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    protected final void validName(String firstName, String lastName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty!");
        }

        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty!");
        }
    }

    protected final void validId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number!");
        }
    }

    protected void setId(int id) {
        validId(id);
        this.id = id;
    }

    protected void setNewPassword(String password) {
        this.password = password;
    }
}
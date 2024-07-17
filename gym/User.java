package gym;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void createAccount() {
        // Logic to create a user account
    }

    public void updateAccount(String name, String email) {
        this.name = name;
        this.email = email;
        // Logic to update the account in the database
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        // Logic to change the password in the database
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters can be added if needed
}

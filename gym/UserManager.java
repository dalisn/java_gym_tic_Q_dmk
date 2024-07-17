package gym;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String USER_FILE = "users.txt";
    private Map<String, User> users;

    public UserManager() {
        users = new HashMap<>();
        loadUsers();
    }

    public void signUpUser(String name, String email, String password) {
        if (users.containsKey(email)) {
            System.out.println("User already exists with this email.");
            return;
        }
        User newUser = new User(users.size() + 1, name, email, password);
        users.put(email, newUser);
        saveUsers();
        System.out.println("User signed up successfully.");
    }

    public boolean loginUser(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("User logged in successfully.");
            return true;
        } else {
            System.out.println("Invalid email or password.");
            return false;
        }
    }

    public User getUserByEmail(String email) {
        return users.get(email);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    private void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    String password = parts[3];
                    users.put(email, new User(id, name, email, password));
                }
            }
        } catch (IOException e) {
            System.out.println("No users file found. Starting fresh.");
        }
    }

    private void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users.values()) {
                bw.write(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + user.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

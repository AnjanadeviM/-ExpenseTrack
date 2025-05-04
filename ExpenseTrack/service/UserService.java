package service;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final String USER_FILE = "data/users.txt";
    private List<User> users;

    public UserService() {
        users = new ArrayList<>();
        loadUsers();
    }

    private void loadUsers() {
        List<String> lines = FileService.loadFromFile(USER_FILE);
        for (String line : lines) {
            users.add(User.fromString(line));
        }
    }

    private void saveUsers() {
        List<String> data = new ArrayList<>();
        for (User user : users) {
            data.add(user.toString());
        }
        FileService.saveToFile(USER_FILE, data);
    }

    public boolean registerUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return false; // User already exists
            }
        }
        users.add(new User(username, password));
        saveUsers();
        return true;
    }

    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.checkPassword(password)) {
                return user;
            }
        }
        return null;
    }
}

package services;

import java.io.*;
import java.util.*;
import utilities.HashUtil;
import model.User;

public class UserService {
    private static final String USERS_FILE = "users.txt";

    public static boolean registerUser(String username, String password) {
        String hashedPassword = HashUtil.hashPassword(password);
        User user = new User(username, hashedPassword);
        return saveUser(user);
    }

    private static boolean saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(user.getUsername() + ":" + user.getHashedPassword());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader((new FileReader(USERS_FILE)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":");
                if (data.length != 2) {
                    users.add(new User(data[0], data[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static boolean loginUser(String username, String password) {
        String hashedPassword = HashUtil.hashPassword(password);
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getHashedPassword().equals(hashedPassword)) {
                return true;
            }
        }
        return false;
    }
}

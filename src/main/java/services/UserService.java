package services;

import java.io.*;
import java.util.*;
import utilities.HashUtil;
import model.User;

public class UserService {
    private static final String USERS_FILE = "users.txt";

    static public boolean registerUser(String username, String password) {
        String hashedPassword = HashUtil.hashPassword(password);
        User user = new User(username, hashedPassword);
        return saveUser(user);
    }

    static private boolean saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(user.getUsername() + ":" + user.getHashedPassword());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

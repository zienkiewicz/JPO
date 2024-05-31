package services;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utilities.HashUtil;
import model.User;

public class UserService {
    private static final String USERS_FILE = "users.txt";

    public enum errCode
    {
        NO_ERROR,
        USERNAME_OR_PASSWORD_IS_EMPTY,
        USERNAME_IS_TAKEN,
        CANNOT_SAVE_USER,
    }

    public static errCode registerUser(String username, String password) throws Exception{

        if(username.isEmpty() || password.isEmpty())
        {
            return errCode.USERNAME_OR_PASSWORD_IS_EMPTY;
        }

        List<User> users = loadUsers();
        for (User user : users)
        {
            if(user.getUsername().equals(username))
                return errCode.USERNAME_IS_TAKEN;
        }

        String hashedPassword = HashUtil.hashPassword(password);
        User user = new User(username, hashedPassword);
        return saveUser(user);
    }

    private static errCode saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(user.getUsername() + ":" + user.getHashedPassword());
            writer.newLine();
            return errCode.NO_ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            return errCode.CANNOT_SAVE_USER;
        }
    }

    private static List<User> loadUsers() throws Exception{
        List<User> users = new ArrayList<>();
        BufferedReader reader = new BufferedReader((new FileReader(USERS_FILE)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":");
                if (data.length == 2) {
                    users.add(new User(data[0], data[1]));
                }
            }

        return users;
    }

    public static boolean loginUser(String username, String password) throws Exception{
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

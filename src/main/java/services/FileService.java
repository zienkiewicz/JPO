package services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileService {
    private static final String CONFIG_FILE = "config.txt";

    public static String getProperty(String property) {
        Properties prop = new Properties();
        try (InputStream in = new FileInputStream(CONFIG_FILE)) {
            prop.load(in);
            return prop.get(property).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

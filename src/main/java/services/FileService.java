package services;

import java.io.*;
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

    public static void appendResponse(String filePath, String data) {
        BufferedWriter bw = null;
        try {
            File file = new File(filePath);

            if (!file.exists())
                file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

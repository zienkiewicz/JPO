package services;

import java.io.*;
import java.util.Properties;

public class FileService {
    private static final String CONFIG_FILE = "config.txt";

    public static String getProperty(String property) throws Exception {
        Properties prop = new Properties();
        InputStream in = new FileInputStream(CONFIG_FILE);
            prop.load(in);
            return prop.get(property).toString();
    }

    public static void appendResponse(String filePath, String data) throws Exception {
        BufferedWriter bw = null;
            File file = new File(filePath);

            if (!file.exists())
                file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(data);
            bw.newLine();
            bw.close();


    }
}

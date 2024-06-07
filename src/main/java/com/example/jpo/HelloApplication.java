package com.example.jpo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.File;
import model.User;
import model.WeatherData;
import utilities.HashUtil;
import services.UserService;
import services.WeatherService;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Hello");
        stage.setScene(scene);
        stage.show();
    }

    private static void checkAndCreateFile(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println(fileName + " created successfully.");
                } else {
                    System.out.println("Failed to create " + fileName + ".");
                }
            } catch (IOException e) {
                System.err.println("An error occurred while creating " + fileName + ": " + e.getMessage());
            }
        } else {
            System.out.println(fileName + " already exists.");
        }
    }

    public static void main(String[] args) { checkAndCreateFile("users.txt"); launch();}
}
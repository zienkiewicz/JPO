package com.example.jpo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

    public static void main(String[] args) {

        WeatherService weatherService = new WeatherService();
        WeatherData currentWeather = weatherService.getCurrentWeather();
        launch();
    }
}
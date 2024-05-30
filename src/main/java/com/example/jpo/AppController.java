package com.example.jpo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import model.WeatherData;
import services.ErrorHandler;
import services.WeatherService;

public class AppController
{
    @FXML
    private Label date;
    @FXML
    private Label temp;
    @FXML
    private Label condition;

    @FXML
    private TextField currencySymbol;
    @FXML
    private DatePicker currencyDate;


    private final Timeline timeline;
    public AppController()
    {
        timeline = new Timeline(new KeyFrame(Duration.seconds(5*60), e -> updateWeatherData()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
        timeline.jumpTo(Duration.seconds((5*60)-0.01));
    }

    @FXML
    private void updateWeatherData()
    {
        try
        {
            WeatherService weatherService = new WeatherService();
            WeatherData currentWeather = weatherService.getCurrentWeather();
            date.setText(currentWeather.getDateTime());
            double t = currentWeather.getTemperatue();
            temp.setText(String.format("%.2f\u00B0C", t));
            condition.setText(currentWeather.getCondition());
        }
        catch (Exception ex)
        {
            ErrorHandler error = new ErrorHandler(Alert.AlertType.ERROR);
            error.fromException(ex);
            error.show();

            timeline.stop();
        }
    }

    @FXML
    protected void onSendButtonClick()
    {

    }
    @FXML
    protected void onShowDataButtonClick()
    {

    }

}

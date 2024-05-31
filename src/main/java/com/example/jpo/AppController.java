package com.example.jpo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.CurrencyRate;
import model.CurrencyRateTable;
import model.WeatherData;
import services.CurrencyService;
import services.MessageBox;
import services.WeatherService;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
            MessageBox error = new MessageBox(Alert.AlertType.ERROR);
            error.fromException(ex);
            error.show();

            timeline.stop();
        }
    }

    @FXML
    protected void onSendButtonClick()
    {
        try
        {

            LocalDate localDate = currencyDate.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            CurrencyService service = new CurrencyService();
            CurrencyRateTable table = service.getCurrencyRateTable(currencySymbol.getText(),sdf.format(new Date()));
        }
        catch (Exception ex)
        {
            MessageBox error = new MessageBox(Alert.AlertType.ERROR);
            error.fromException(ex);
            error.show();
        }
    }
    @FXML
    protected void onShowDataButtonClick()
    {
        String symbol = currencySymbol.getText();
        if(symbol.isEmpty())
        {
            MessageBox error = new MessageBox(Alert.AlertType.WARNING);
            error.setTitle("Błąd");
            error.setMessage("Nie podano symbolu");
            error.show();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(symbol.toUpperCase());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        TableView table = new TableView();
        TableColumn<CurrencyRate,String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("effectiveDate"));
        TableColumn<CurrencyRate,String> bidColumn = new TableColumn<>("Bid");
        bidColumn.setCellValueFactory(new PropertyValueFactory<>("bid"));
        TableColumn<CurrencyRate,String> askColumn = new TableColumn<>("Ask");
        askColumn.setCellValueFactory(new PropertyValueFactory<>("ask"));
        table.getColumns().add(dateColumn);
        table.getColumns().add(bidColumn);
        table.getColumns().add(askColumn);

        try
        {
            CurrencyService service = new CurrencyService();
            CurrencyRateTable currencyRateTable = service.getCurrencyRateTable(currencySymbol.getText());

            for(CurrencyRate currencyRate : currencyRateTable.getRates())
            {
                table.getItems().add(currencyRate);
            }
        }
        catch (Exception ex)
        {
            MessageBox error = new MessageBox(Alert.AlertType.ERROR);
            error.fromException(ex);
            error.show();
            return;
        }


        grid.add(table, 0, 0);
        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.showAndWait();

    }

}

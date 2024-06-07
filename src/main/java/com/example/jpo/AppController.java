package com.example.jpo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import model.CurrencyRate;
import model.CurrencyRateTable;
import model.WeatherData;
import services.*;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

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

    @FXML
    private CheckBox uniqueEntries;

    @FXML
    private Label cityLabel;

    private final Timeline timeline;
    public AppController()
    {
        timeline = new Timeline(new KeyFrame(Duration.seconds(5*60), e -> updateWeatherData()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
        timeline.jumpTo(Duration.seconds((5*60)-0.1));
    }

    @FXML
    protected void initialize()
    {
        currencyDate.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty)
            {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 || (date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY));
            }
        });

        try
        {
            new WeatherService();
        }
        catch (Exception e)
        {
            updateConfigData();
        }
    }

    @FXML
    protected void updateConfigData()
    {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("");
        dialog.setHeaderText("Zaktualizuj dane");

        ButtonType updateConfigButtonType = new ButtonType("Zaktualizuj dane", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateConfigButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        String configApiKey = new String();
        String configCity = new String();

        try
        {
            configApiKey = FileService.getProperty("weather_api_key");
        }catch (Exception ex){}
        try
        {
            configCity = FileService.getProperty("weather_city");
        }catch (Exception ex){}

        TextField apiKey = new TextField();
        apiKey.setText(configApiKey);
        TextField city = new TextField();
        city.setText(configCity);

        grid.add(new Label("Klucz:"), 0, 0);
        grid.add(apiKey, 1, 0);
        grid.add(new Label("Miasto:"), 0, 1);
        grid.add(city, 1, 1);

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> apiKey.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateConfigButtonType) {
                return new Pair<>(apiKey.getText(), city.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(configData -> {
            try
            {
                FileService.setProperty("weather_api_key",configData.getKey());
                FileService.setProperty("weather_city",configData.getValue());

                timeline.playFromStart();
                timeline.jumpTo(Duration.seconds((5*60)-0.1));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
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
            cityLabel.setText(weatherService.getCity());
        }
        catch (Exception ex)
        {
            timeline.stop();
        }
    }

    @FXML
    protected void onSendButtonClick()
    {
        try
        {
            LocalDate localDate = currencyDate.getValue();
            String currencySymbolText = currencySymbol.getText();
            if(currencySymbolText.isEmpty())
            {
                MessageBox warning = new MessageBox(Alert.AlertType.WARNING);
                warning.setTitle("Nieprawidłowy symbol");
                warning.setMessage("Nie podano symbolu");
                warning.show();
                return;
            }
            else if(localDate == null)
            {
                MessageBox warning = new MessageBox(Alert.AlertType.WARNING);
                warning.setTitle("Nieprawidłowa data");
                warning.setMessage("Proszę podać datę");
                warning.show();
                return;
            }
            else if(localDate.isAfter(LocalDate.now(ZoneId.systemDefault())))
            {
                MessageBox warning = new MessageBox(Alert.AlertType.WARNING);
                warning.setTitle("Nieprawidłowa data");
                warning.setMessage("");
                warning.show();
                return;
            }
            else if(localDate.getDayOfWeek() == DayOfWeek.SUNDAY || localDate.getDayOfWeek() == DayOfWeek.SATURDAY)
            {
                MessageBox warning = new MessageBox(Alert.AlertType.WARNING);
                warning.setTitle("Nieprawidłowa data");
                warning.setMessage("");
                warning.show();
                return;
            }


            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            CurrencyService service = new CurrencyService();
            try
            {
                service.getCurrencyRateTable(currencySymbolText,sdf.format(date));
            }
            catch (CurrencyServiceBadRequestException ex)
            {
                MessageBox warning = new MessageBox(Alert.AlertType.WARNING);
                warning.setTitle("Niepowodzenie");
                warning.setMessage("Podano nieprawidłowy symbol");
                warning.show();
                return;
            }
            catch (CurrencyServiceNoDataFoundException ex)
            {
                MessageBox warning = new MessageBox(Alert.AlertType.WARNING);
                warning.setTitle("Niepowodzenie");
                warning.setMessage("Brak danych dla tego przedziału czasowego");
                warning.show();
                return;
            }
        }
        catch (Exception ex)
        {
            MessageBox error = new MessageBox(Alert.AlertType.ERROR);
            error.fromException(ex);
            error.show();
            return;
        }
        MessageBox success = new MessageBox(Alert.AlertType.INFORMATION);
        success.setTitle("Sukces");
        success.setMessage("Zapytanie przebiegło pomyślnie");
        success.show();
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
        table.getColumns().clear();
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
            CurrencyRateTable currencyRateTable;
            try
            {
                currencyRateTable = service.getCurrencyRateTable(currencySymbol.getText());
            }
            catch (CurrencyServiceNoDataFoundException ex)
            {
                MessageBox warning = new MessageBox(Alert.AlertType.WARNING);
                warning.setTitle("Niepowodzenie");
                warning.setMessage("Nie znaleziono danych dla podanego symbolu");
                warning.show();
                return;
            }
            Collection<CurrencyRate> rates;
            if(uniqueEntries.isSelected())
            {
                Set<CurrencyRate> uniqueRates = new LinkedHashSet<CurrencyRate>();
                for(CurrencyRate currencyRate : currencyRateTable.getRates())
                {
                    uniqueRates.add(currencyRate);
                }
                rates = uniqueRates;
            }
            else
            {
                rates = currencyRateTable.getRates();
            }

            for(CurrencyRate currencyRate : rates)
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

    @FXML
    protected void onLogoutClick()
    {
        try
        {
            MessageBox confirm = new MessageBox(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("");
            confirm.setMessage("Czy napewno chcesz się wylogować?");
            confirm.show();

            if(confirm.getResponse() == ButtonType.OK)
            {
                SceneChanger wnd = new SceneChanger((Stage) date.getScene().getWindow());
                wnd.loadScene(getClass().getResource("hello-view.fxml"));
            }
        }
        catch (Exception ex)
        {
            MessageBox error = new MessageBox(Alert.AlertType.ERROR);
            error.fromException(ex);
            error.show();
        }
    }
}

package com.example.jpo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import kotlin.jvm.internal.Ref;
import services.ErrorHandler;
import services.Window;

import java.io.IOException;
import java.lang.ref.Reference;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onRegisterNewAccountButtonClick()
    {
        try
        {
            Window wnd = new Window((Stage) welcomeText.getScene().getWindow());
            wnd.loadScene(getClass().getResource("register-view.fxml"));
        }
        catch(Exception ex)
        {
            ErrorHandler err = new ErrorHandler(Alert.AlertType.ERROR);
            err.fromException(ex);
            err.show();

            System.exit(0);
        }

    }
}
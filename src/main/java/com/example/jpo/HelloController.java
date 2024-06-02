package com.example.jpo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.MessageBox;
import services.SceneChanger;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onLoginButtonClick()
    {
        try
        {
            SceneChanger wnd = new SceneChanger((Stage) welcomeText.getScene().getWindow());
            wnd.loadScene(getClass().getResource("login-view.fxml"));
        }
        catch(Exception ex)
        {
            MessageBox err = new MessageBox(Alert.AlertType.ERROR);
            err.fromException(ex);
            err.show();

            System.exit(0);
        }
    }

    @FXML
    protected void onRegisterNewAccountButtonClick()
    {
        try
        {
            SceneChanger wnd = new SceneChanger((Stage) welcomeText.getScene().getWindow());
            wnd.loadScene(getClass().getResource("register-view.fxml"));
        }
        catch(Exception ex)
        {
            MessageBox err = new MessageBox(Alert.AlertType.ERROR);
            err.fromException(ex);
            err.show();

            System.exit(0);
        }

    }
}
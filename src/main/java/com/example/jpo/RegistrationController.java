package com.example.jpo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    protected void onRegisterButtonClick() {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        boolean result = UserService.registerUser(username, password);
        if (result) {
            messageLabel.setText("User successfully registered!");
            System.out.println("User registered successfully");

            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            try
            {
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        else {
            messageLabel.setText("User could not be registered!");
            System.out.println("User could not be registered. Please try again");
        }
    }
}

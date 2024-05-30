package com.example.jpo;

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
        }
        else {
            messageLabel.setText("User could not be registered!");
            System.out.println("User could not be registered. Please try again");
        }
    }
}

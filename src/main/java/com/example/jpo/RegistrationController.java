package com.example.jpo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.User;
import services.ErrorHandler;
import services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.Window;

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
        UserService.errCode result = UserService.registerUser(username, password);
        if (result == UserService.errCode.NO_ERROR) {
            //messageLabel.setText("User successfully registered!");
            System.out.println("User registered successfully");

            ErrorHandler success = new ErrorHandler(Alert.AlertType.INFORMATION);
            success.setTitle("Sukces");
            success.setMessage("Rejestracja zakończona sukcesem");
            success.show();

            try
            {
                Window wnd = new Window((Stage) usernameField.getScene().getWindow());
                wnd.loadScene(getClass().getResource("hello-view.fxml"));
            }
            catch (Exception ex)
            {
                ErrorHandler err = new ErrorHandler(Alert.AlertType.ERROR);
                err.fromException(ex);
                err.show();

                System.exit(0);
            }
        }
        else {
            //messageLabel.setText("User could not be registered!");
            System.out.println("User could not be registered. Please try again");

            ErrorHandler failure = new ErrorHandler(Alert.AlertType.WARNING);
            failure.setTitle("Niepowodzenie");
            failure.setMessage("Rejestracja nie powiodła się");
            switch (result)
            {
                case USERNAME_IS_TAKEN:
                {
                    failure.setContent("Nazwa użytkownika jest zajęta");
                }break;
                case USERNAME_OR_PASSWORD_IS_EMPTY:
                {
                    failure.setContent("Nazwa użytkownika i hasło nie mogą być puste");
                }break;
            }

            failure.show();
        }
    }
}

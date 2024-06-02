package com.example.jpo;

import javafx.scene.control.*;
import javafx.stage.Stage;
import services.MessageBox;
import services.UserService;
import javafx.fxml.FXML;
import services.SceneChanger;

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
        try
        {
            UserService.errCode result = UserService.registerUser(username, password);
            if (result == UserService.errCode.NO_ERROR) {
                //messageLabel.setText("User successfully registered!");
                System.out.println("User registered successfully");

                MessageBox success = new MessageBox(Alert.AlertType.INFORMATION);
                success.setTitle("Sukces");
                success.setMessage("Rejestracja zakończona sukcesem");
                success.show();

                try
                {
                    SceneChanger wnd = new SceneChanger((Stage) usernameField.getScene().getWindow());
                    wnd.loadScene(getClass().getResource("hello-view.fxml"));
                }
                catch (Exception ex)
                {
                    MessageBox err = new MessageBox(Alert.AlertType.ERROR);
                    err.fromException(ex);
                    err.show();

                    System.exit(0);
                }
            }
            else {
                //messageLabel.setText("User could not be registered!");
                System.out.println("User could not be registered. Please try again");

                MessageBox failure = new MessageBox(Alert.AlertType.WARNING);
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
        catch (Exception ex)
        {
            MessageBox err = new MessageBox(Alert.AlertType.ERROR);
            err.fromException(ex);
            err.show();
        }
    }
    @FXML
    protected void onReturnButtonClick()
    {
        try
        {
            SceneChanger wnd = new SceneChanger((Stage) usernameField.getScene().getWindow());
            wnd.loadScene(getClass().getResource("hello-view.fxml"));
        }
        catch (Exception ex)
        {
            MessageBox error = new MessageBox(Alert.AlertType.ERROR);
            error.fromException(ex);
            error.show();
        }
    }
}

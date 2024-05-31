package com.example.jpo;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import services.ErrorHandler;
import services.UserService;
import services.Window;

public class LoginController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    @FXML
    protected void onLoginButtonClick()
    {
        String username = login.getText();
        String passwordText = password.getText();

        boolean result = UserService.loginUser(username,passwordText);

        if(result)
        {
            ErrorHandler success = new ErrorHandler(Alert.AlertType.INFORMATION);
            success.setTitle("Sukces");
            success.setMessage("Logowanie powiodło się");
            success.show();

            try
            {
                Window wnd = new Window((Stage) login.getScene().getWindow());
                wnd.loadScene(getClass().getResource("application-view.fxml"));
            }
            catch (Exception ex)
            {
                ErrorHandler err = new ErrorHandler(Alert.AlertType.ERROR);
                err.fromException(ex);
                err.show();

                System.exit(0);
            }

        }
        else
        {
            ErrorHandler err = new ErrorHandler(Alert.AlertType.WARNING);
            err.setTitle("Niepowodzenie");
            err.setMessage("Logowanie nie powiodło się");
            err.show();
        }

    }

}

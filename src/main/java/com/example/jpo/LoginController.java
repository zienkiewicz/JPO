package com.example.jpo;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.MessageBox;
import services.UserService;
import services.SceneChanger;

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

        try
        {
            boolean result = UserService.loginUser(username,passwordText);

            if(result)
            {
                MessageBox success = new MessageBox(Alert.AlertType.INFORMATION);
                success.setTitle("Sukces");
                success.setMessage("Logowanie powiodło się");
                success.show();

                try
                {
                    SceneChanger wnd = new SceneChanger((Stage) login.getScene().getWindow());
                    wnd.loadScene(getClass().getResource("application-view.fxml"));
                }
                catch (Exception ex)
                {
                    MessageBox err = new MessageBox(Alert.AlertType.ERROR);
                    err.fromException(ex);
                    err.show();

                    System.exit(0);
                }

            }
            else
            {
                MessageBox err = new MessageBox(Alert.AlertType.WARNING);
                err.setTitle("Niepowodzenie");
                err.setMessage("Logowanie nie powiodło się");
                err.show();
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
            SceneChanger wnd = new SceneChanger((Stage) login.getScene().getWindow());
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

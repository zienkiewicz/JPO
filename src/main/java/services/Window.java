package services;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.ref.Reference;

import java.net.URL;

public class Window {
    @FXML
    private Stage stage = null;
    public Window() {}
    public Window (Stage st)
    {
        stage = st;
    }
    @FXML
    public  void setStage(Stage st)
    {
        stage = st;
    }

    @FXML
    public void loadScene(URL resource) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

}

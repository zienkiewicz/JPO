package services;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class SceneChanger {
    @FXML
    private Stage stage = null;
    public SceneChanger() {}
    public SceneChanger(Stage st)
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

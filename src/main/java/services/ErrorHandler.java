package services;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;


import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Optional;

public class ErrorHandler {
    Alert alert;
    Optional<ButtonType> result = null;

    public ErrorHandler(Alert.AlertType type)
    {
        alert = new Alert(type);
    }

    public void fromException(Exception ex)
    {
        alert.setTitle("Error");
        alert.setHeaderText(ex.getMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);

        TextArea area = new TextArea(sw.toString());
        area.setWrapText(true);
        area.setEditable(false);

        alert.getDialogPane().setContent(area);
    }

    public void setTitle(String title)
    {
        alert.setTitle(title);
    }
    public void setMessage(String message)
    {
        alert.setHeaderText(message);
    }
    public void setContent(String content)
    {
        alert.setContentText(content);
    }
    public void show()
    {
       result = alert.showAndWait();
    }

    public ButtonType getResponse()
    {
        return result.orElse(null);
    }

}

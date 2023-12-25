package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utils
{
    // Methods
    public static boolean stringNullOrEmpty(String string)
    {
        return string == null || string.equals("");
    }

    public static void showAlert(String body, String head, AlertType type)
    {
        Alert alert = new Alert(type);
        alert.setTitle(head);
        alert.setHeaderText(body);
        alert.show();
    }
}

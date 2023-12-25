package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
import java.util.Locale;

public class Program extends Application
{
    // Fields
    private static Scene scene;
    private static Stage stage;

    // Properties
    public static Scene getScene() {
        return  scene;
    }

    public static Stage getStage() {
        return stage;
    }

    // Methods
    public static void main(String[] args)
    {
        Locale.setDefault(Locale.US);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        Program.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Program.scene = scene;
        stage.setScene(scene);
        stage.setTitle("Contador de Materiais");
        stage.show();
    }
}

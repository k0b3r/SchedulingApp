package schrader.schedulingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import schrader.schedulingapp.database.JDBC;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public void start(Stage stage) throws IOException {
        JDBC.openConnection();
        JDBC.closeConnection();
        Parent root = FXMLLoader.load(getClass().getResource("view/LoginForm.fxml"));
        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main() {
        launch();
    }
}
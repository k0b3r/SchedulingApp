package schrader.schedulingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import schrader.schedulingapp.helper.JDBC;

import java.io.IOException;

public class Main extends Application {

    public void start(Stage stage) throws IOException {
        JDBC.openConnection();
        Parent root = FXMLLoader.load(getClass().getResource("view/LoginForm.fxml"));
        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main() {
        launch();
        JDBC.closeConnection();
    }
}
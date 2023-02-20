package schrader.schedulingapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAppointmentController {
    public void createStage(ActionEvent event, String resource, String stageTitle) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(resource));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }
    public void onSaveClick(ActionEvent event) throws IOException {
        // TODO implement functionality
        createStage(event, "/schrader/schedulingapp/view/AppointmentSchedule.fxml", "Appointment Schedule");
    }
    public void initialize() {
    }
}

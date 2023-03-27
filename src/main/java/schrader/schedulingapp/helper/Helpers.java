package schrader.schedulingapp.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;

/**
 * This class contains helper methods that are used across controllers.
 */

/**
 * @author Karoline Schrader
 */
public class Helpers {
    /**
     * This method sets up the stage/scene and is used to display each screen.
     * @param event
     * @param resource
     * @param stageTitle
     * @throws IOException
     */
    public static void createStage(ActionEvent event, String resource, String stageTitle) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Helpers.class.getResource(resource)));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method populates a list with time selections starting at the min value, incrementing by 15 minutes, until the
     * max time is reached. This list is used to populate appointment time combo boxes. s
     * @param min
     * @param max
     * @return timeSlots
     */
    public static ObservableList<LocalTime> setTimeSlots(LocalTime min, LocalTime max) {
        ObservableList<LocalTime> timeSlots = FXCollections.observableArrayList();
        while (min.isBefore(max.plusSeconds(1))) {
            timeSlots.add(min);
            min = min.plusMinutes(15);
        }
        return timeSlots;
    }
}

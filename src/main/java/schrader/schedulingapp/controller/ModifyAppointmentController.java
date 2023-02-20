package schrader.schedulingapp.controller;

/**
 * Modify Appointment Controller
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import schrader.schedulingapp.Utilities.ContactDAO;
import schrader.schedulingapp.Utilities.CustomerDAO;
import schrader.schedulingapp.model.Appointment;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author Karoline Schrader
 */
public class ModifyAppointmentController {
    private static Appointment appointment = null;
    public TextField appointmentId;
    public ComboBox contact;
    public TextField type;
    public DatePicker startDateTime;
    public DatePicker endDateTime;
    public TextField userId;
    public TextField title;
    public TextArea description;
    public TextField local;
    public TextField customerId;

    public static void loadSelectedAppointment(Appointment selectedAppointment) {
        appointment = selectedAppointment;
    }

    public void createStage(ActionEvent event, String resource, String stageTitle) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(resource));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }

    public void onSaveClick(ActionEvent event) throws IOException {
        // TODO build out functionality
        createStage(event, "/schrader/schedulingapp/view/AppointmentSchedule.fxml", "Appointment Schedule");
    }

    public void populateAppointmentValues() throws SQLException {
        // TODO add labels to fields for modify forms to make it more readable
        appointmentId.setText(String.valueOf(appointment.getAppointmentId()));
        title.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
        local.setText(appointment.getLocation());
        contact.setItems(ContactDAO.getAllContactNames());
        contact.setValue(appointment.getContactName());
        type.setText(appointment.getType());
        startDateTime.setValue(appointment.getStartDate().toLocalDate());
        endDateTime.setValue(appointment.getEndDate().toLocalDate());
        customerId.setText(String.valueOf(appointment.getCustomerId()));
        userId.setText(String.valueOf(appointment.getUserId()));

    }

    public void initialize() throws SQLException {
        populateAppointmentValues();
    }
}

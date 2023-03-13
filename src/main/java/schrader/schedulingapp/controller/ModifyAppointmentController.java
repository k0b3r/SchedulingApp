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
import schrader.schedulingapp.Utilities.AppointmentDAO;
import schrader.schedulingapp.Utilities.ContactDAO;
import schrader.schedulingapp.Utilities.CustomerDAO;
import schrader.schedulingapp.Utilities.UserDAO;
import schrader.schedulingapp.model.Appointment;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

/**
 * @author Karoline Schrader
 */
public class ModifyAppointmentController {
    private static Appointment appointment = null;
    public TextField appointmentId;
    public ComboBox contact;
    public TextField type;
    public DatePicker startDate;
    public DatePicker endDate;
    public ComboBox user;
    public TextField title;
    public TextArea description;
    public TextField local;
    public ComboBox customer;
    public ComboBox startTime;
    public ComboBox endTime;

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

    public void setEndDateToStartDate() {
        endDate.setValue(startDate.getValue());
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
        LocalDateTime startDateTime = appointment.getStartDate();
        LocalDate startDateVal = startDateTime.toLocalDate();
        LocalTime startTimeVal = startDateTime.toLocalTime();
        startDate.setValue(startDateVal);
        startTime.setValue(startTimeVal);
        LocalDateTime endDateTime = appointment.getEndDate();
        LocalDate endDateVal = endDateTime.toLocalDate();
        LocalTime endTimeVal = endDateTime.toLocalTime();
        endDate.setValue(endDateVal);
        endTime.setValue(endTimeVal);
        customer.setItems(CustomerDAO.getCustomerNames());
        customer.setValue(String.valueOf(CustomerDAO.getCustomerName(appointment.getCustomerId())));
        user.setItems(UserDAO.getUserNames());
        user.setValue(UserDAO.getUserName(appointment.getUserId()));

    }

    public void onSaveClick(ActionEvent event) throws SQLException, IOException {
        if (title.getText().isEmpty() || description.getText().isEmpty() || local.getText().isEmpty() || type.getText().isEmpty()
                || startDate.getValue() == null || startTime.getSelectionModel().getSelectedItem() == null || endDate.getValue() == null
                || endTime.getSelectionModel().getSelectedItem() == null || customer.getSelectionModel().getSelectedItem() == null || user.getSelectionModel().getSelectedItem() == null || contact.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Appointment");
            alert.setHeaderText("Incomplete Fields");
            alert.setContentText("All fields must be complete to add an appointment.");
            alert.show();
        } else {
            Integer appId = appointment.getAppointmentId();
            String titleVal = title.getText();
            String descVal = description.getText();
            String locationVal = local.getText();
            String typeVal = type.getText();

            LocalDateTime startDateTime = LocalDateTime.of(startDate.getValue(), LocalTime.parse(startTime.getSelectionModel().getSelectedItem().toString()));
            LocalDateTime endDateTime = LocalDateTime.of(endDate.getValue(), LocalTime.parse(endTime.getSelectionModel().getSelectedItem().toString()));

            ZonedDateTime startDateConvert = startDateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime startDateUTC = startDateConvert.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime startAdd = startDateUTC.toLocalDateTime(); // FINAL TIME VALUE

            ZonedDateTime endDateConvert = endDateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime endDateUTC = endDateConvert.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime endAdd = endDateUTC.toLocalDateTime(); // FINAL TIME VALUE

            Timestamp currentDateTime = Timestamp.valueOf(LocalDateTime.now());
            String createdBy = LoginFormController.currentUser.getUsername();
            Integer custId = CustomerDAO.getCustomerID(customer.getSelectionModel().getSelectedItem().toString());
            Integer userId = UserDAO.getUserId(user.getSelectionModel().getSelectedItem().toString());
            Integer contactId = ContactDAO.getContactId(contact.getSelectionModel().getSelectedItem().toString());

            AppointmentDAO.updateAppointment(appId, titleVal, descVal, locationVal, typeVal, startAdd,
                    endAdd, currentDateTime, createdBy, currentDateTime, createdBy, custId, userId, contactId);
            // TODO Update appointment list or does this happen already on the DB query?
            createStage(event, "/schrader/schedulingapp/view/AppointmentSchedule.fxml", "Appointment Schedule");

        }
    }

    public void initialize() throws SQLException {
        populateAppointmentValues();
    }
}

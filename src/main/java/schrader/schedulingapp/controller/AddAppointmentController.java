package schrader.schedulingapp.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import schrader.schedulingapp.DAO.AppointmentDAO;
import schrader.schedulingapp.DAO.ContactDAO;
import schrader.schedulingapp.DAO.CustomerDAO;
import schrader.schedulingapp.DAO.UserDAO;
import schrader.schedulingapp.helper.Helpers;
import schrader.schedulingapp.model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ResourceBundle;

/**
 * This class contains methods that are run when associated actions are taken on the Add Appointment form.
 */

/**
 * @author Karoline Schrader
 */
public class AddAppointmentController implements Initializable {
    public TextField appointmentId;
    public ComboBox <String> contact;
    public TextField type;
    public Button saveButton;
    public DatePicker startDate;
    public DatePicker endDate;
    public ComboBox <String> customer;
    public ComboBox <String> user;
    public ComboBox <LocalTime> startTime;
    public ComboBox <LocalTime> endTime;
    public TextField title;
    public TextArea description;
    public TextField local;

    /**
     * This method sets the end date to the start date when the user selects the start date (the user can still alter
     * the end date, but will get an alert if the start/end date aren't the same).
     */
    public void setEndDateToStartDate() {
        endDate.setValue(startDate.getValue());
    }

    /**
     * This method checks if the selected appointment start/end dates/times overlap with existing appointment start/end dates/times.
     * @param start
     * @param end
     * @return appointmentInfo
     * @throws SQLException
     */
    public StringBuilder checkForOverlappingAppointments(LocalDateTime start, LocalDateTime end) throws SQLException {
        StringBuilder appointmentInfo = new StringBuilder();
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
        for (Appointment a : allAppointments) {
            LocalDateTime appStart = a.getStartDate();
            LocalDateTime appEnd = a.getEndDate();
            String startDateFormatted = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Timestamp.valueOf(appStart));
            String endDateFormatted = new SimpleDateFormat("HH:mm").format(Timestamp.valueOf(appEnd));

            if ((start.isBefore(appStart) || start.isAfter(appStart) || start.equals(appStart)) && ((end.isAfter(appStart) && end.isBefore(appEnd)) || end.equals(appEnd))) {
                appointmentInfo.append(a.getAppointmentId()).append(", ").append(startDateFormatted).append(" - ").append(endDateFormatted).append("\n");
            }
            else if ((start.equals(appStart) || (start.isAfter(appStart) && start.isBefore(appEnd))) && ((end.isAfter(appStart) && end.isBefore(appEnd)) || end.equals(appEnd) || end.isAfter(appEnd))) {
                appointmentInfo.append(a.getAppointmentId()).append(", ").append(startDateFormatted).append(" - ").append(endDateFormatted).append("\n");
            }
        }
        return appointmentInfo;
    }

    /**
     * This method handles creating an appointment, and all the validation needed to pass in order for that to happen.
     * This method checks for empty appointment values, if the appointment start/end dates are the same, if the appointment start time
     * precedes the appointment end time, if the appointment times fall within business hours, and if the appointment dates/times
     * overlap with existing appointment dates/times (see checkForOverlappingAppointments). Users are redirected to the Appointment
     * screen afterward.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void onSaveClick(ActionEvent event) throws IOException, SQLException {
        // check if any fields are empty first
        if (title.getText().isEmpty() || description.getText().isEmpty() || local.getText().isEmpty() || type.getText().isEmpty()
                || startDate.getValue() == null || startTime.getSelectionModel().getSelectedItem() == null || endDate.getValue() == null
                || endTime.getSelectionModel().getSelectedItem() == null || customer.getSelectionModel().getSelectedItem() == null
                || user.getSelectionModel().getSelectedItem() == null || contact.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Appointment");
            alert.setHeaderText("Incomplete Fields");
            alert.setContentText("All fields must be complete to add an appointment.");
            alert.showAndWait();
        }
        else {

            try {
                Integer appId = AppointmentDAO.generateAppointmentId();
                String titleVal = title.getText();
                String descVal = description.getText();
                String locationVal = local.getText();
                String typeVal = type.getText();

                LocalDateTime startDateTime = LocalDateTime.of(startDate.getValue(), LocalTime.parse(startTime.getSelectionModel().getSelectedItem().toString()));
                ZonedDateTime startZdt = startDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                ZonedDateTime startEtzZdt = startZdt.withZoneSameInstant(ZoneId.of("America/New_York"));
                LocalDateTime startEtzLdt = startEtzZdt.toLocalDateTime();

                LocalDateTime endDateTime = LocalDateTime.of(endDate.getValue(), LocalTime.parse(endTime.getSelectionModel().getSelectedItem().toString()));
                ZonedDateTime endZdt = endDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                ZonedDateTime endEtzZdt = endZdt.withZoneSameInstant(ZoneId.of("America/New_York"));
                LocalDateTime endEtzLdt = endEtzZdt.toLocalDateTime();

                LocalDateTime businessStart = LocalDateTime.of(startDate.getValue(), LocalTime.of(8, 0));
                LocalDateTime businessEnd = LocalDateTime.of(endDate.getValue(), LocalTime.of(22, 0));

                Timestamp currentDateTime = Timestamp.valueOf(LocalDateTime.now());
                String createdBy = LoginFormController.currentUser.getUsername();
                Integer custId = CustomerDAO.getCustomerID(customer.getSelectionModel().getSelectedItem());
                Integer userId = UserDAO.getUserId(user.getSelectionModel().getSelectedItem());
                Integer contactId = ContactDAO.getContactId(contact.getSelectionModel().getSelectedItem());
                StringBuilder overlappingAppInfo = checkForOverlappingAppointments(startDateTime, endDateTime);

                if (!startDate.getValue().equals(endDate.getValue())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Add Appointment");
                    alert.setHeaderText("Invalid Dates");
                    alert.setContentText("Start and End Dates must be the same day.");
                    alert.showAndWait();
                } else if ((startEtzLdt.isBefore(businessStart) || startEtzLdt.isAfter(businessEnd)) || (endEtzLdt.isBefore(businessStart) || endEtzLdt.isAfter(businessEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Add Appointment");
                    alert.setHeaderText("Invalid Start and/or End Time");
                    alert.setContentText("Appointment start and end times must be within the following business hours: 8:00 A.M. - 10:00 P.M. EST");
                    alert.showAndWait();
                } else if (startDateTime.isAfter(endDateTime) || startDateTime.equals(endDateTime)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Add Appointment");
                    alert.setHeaderText("Invalid Start/End Time");
                    alert.setContentText("Appointment start time must take place prior to end time.");
                    alert.showAndWait();
                } else if (!overlappingAppInfo.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Add Appointment");
                    alert.setHeaderText("Appointment times overlap with the following existing appointments.");
                    alert.setContentText("Appointment ID, Appointment Date/Time: \n" + "\n" + overlappingAppInfo);
                    alert.showAndWait();
                } else {
                    AppointmentDAO.insertAppointment(appId, titleVal, descVal, locationVal, typeVal, startDateTime,
                            endDateTime, currentDateTime, createdBy, currentDateTime, createdBy, custId, userId, contactId);
                    Helpers.createStage(event, "/schrader/schedulingapp/view/Appointments.fxml", "Appointment Schedule");
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }
    }

    /**
     * This method redirects the user back to the Appointments screen (see Helpers.createStage for additional detail).
     * @param event
     * @throws IOException
     */
    public void onCancelClick(ActionEvent event) throws IOException {
        Helpers.createStage(event, "/schrader/schedulingapp/view/Appointments.fxml", "Appointments");
    }

    /**
     * This method populates the contact, customer, user, start, and end time combo boxes with values for user selection.
     * @throws SQLException
     */
    public void populateDropDownValues() throws SQLException {
        contact.setItems(ContactDAO.getAllContactNames());
        customer.setItems(CustomerDAO.getCustomerNames());
        user.setItems(UserDAO.getUserNames());

        // TODO - Not sure about these hours
        LocalTime startMin = LocalTime.of(5,0);
        LocalTime startMax = LocalTime.of(21,45);
        startTime.setItems(Helpers.setTimeSlots(startMin,startMax));

        LocalTime endMin = LocalTime.of(5,15);
        LocalTime endMax = LocalTime.of(22,0);
        endTime.setItems(Helpers.setTimeSlots(endMin, endMax));
    }

    /**
     * This method sets up the Add Appointment screen to be populated with all expected values (see populateDropDownValues).
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateDropDownValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

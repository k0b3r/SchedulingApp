package schrader.schedulingapp.controller;

import javafx.collections.FXCollections;
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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * This class contains methods that are run when associated actions are taken on the Modify Appointments form.
 */

/**
 * @author Karoline Schrader
 */
public class ModifyAppointmentController implements Initializable {
    private static Appointment appointment = null;
    public TextField appointmentId;
    public ComboBox <String> contact;
    public TextField type;
    public DatePicker startDate;
    public DatePicker endDate;
    public ComboBox <String> user;
    public TextField title;
    public TextArea description;
    public TextField local;
    public ComboBox <String> customer;
    public Spinner<Integer> startTimeHour;
    public Spinner<Integer> startTimeMin;
    public Spinner<Integer> endTimeHour;
    public Spinner<Integer> endTimeMin;
    public ComboBox<String> startAMPM;
    public ComboBox<String> endAMPM;

    /**
     * This method is used to pass the selected appointment from the Appointment screen, in order to load its values on the Modify Appointment form.
     * @param selectedAppointment
     */
    public static void loadSelectedAppointment(Appointment selectedAppointment) {
        appointment = selectedAppointment;
    }

    /**
     * This method sets the end date to the start date when the user selects the start date (the user can still alter
     * the end date, but will get an alert if the start/end date aren't the same).
     */
    public void setEndDateToStartDate() {
        endDate.setValue(startDate.getValue());
    }

    /**
     * This method populates the contact, customer, user, start, and end time combo boxes with values for user selection.
     * @throws SQLException
     */
    public void populateDropDownValues() throws SQLException {
        contact.setItems(ContactDAO.getAllContactNames());
        customer.setItems(CustomerDAO.getCustomerNames());
        user.setItems(UserDAO.getUserNames());

        ObservableList<String> amPm = FXCollections.observableArrayList();
        amPm.add("AM");
        amPm.add("PM");
        startAMPM.setItems(amPm);
        endAMPM.setItems(amPm);
    }

    /**
     * This method populates the Appointment object values (selected on the Appointment screen) on the Modify
     * Appointment form.
     * @throws SQLException
     */
    public void populateAppointmentValues() throws SQLException {
        appointmentId.setText(String.valueOf(appointment.getAppointmentId()));
        title.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
        local.setText(appointment.getLocation());
        contact.setValue(appointment.getContactName());
        type.setText(appointment.getType());

        DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("a");
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("hh");

        LocalDateTime startDateTime = appointment.getStartDate();
        LocalDate startDateVal = startDateTime.toLocalDate();
        Integer startHour = Integer.valueOf(startDateTime.format(hourFormatter));
        Integer startMin = startDateTime.getMinute();

        LocalDateTime endDateTime = appointment.getEndDate();
        LocalDate endDateVal = endDateTime.toLocalDate();
        Integer endHour = Integer.valueOf(endDateTime.format(hourFormatter));
        Integer endMin = endDateTime.getMinute();


        startDate.setValue(startDateVal);
        startTimeHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, startHour));
        startTimeMin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, startMin));
        startAMPM.setValue(startDateTime.format(ampmFormatter));


        endDate.setValue(endDateVal);
        endTimeHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, endHour));
        endTimeMin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, endMin));
        endAMPM.setValue(endDateTime.format(ampmFormatter));

        customer.setValue(String.valueOf(CustomerDAO.getCustomerName(appointment.getCustomerId())));
        user.setValue(UserDAO.getUserName(appointment.getUserId()));

    }

    /**
     * This method checks if the selected appointment start/end dates/times overlap with existing appointment
     * start/end dates/times for the given customerId (excluding the appointment dates/time of the appointment being modified)
     * @param appointmentId
     * @param customerId
     * @param start
     * @param end
     * @return appointmentInfo
     * @throws SQLException
     */
    public StringBuilder checkForOverlappingAppointments(Integer appointmentId, Integer customerId, LocalDateTime start, LocalDateTime end) throws SQLException {
        StringBuilder appointmentInfo = new StringBuilder();
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
        for (Appointment a : allAppointments) {
            if (appointmentId.equals(a.getAppointmentId())) {
                continue;
            }
                LocalDateTime appStart = a.getStartDate();
                LocalDateTime appEnd = a.getEndDate();
                String startDateFormatted = new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(Timestamp.valueOf(appStart));
                String endDateFormatted = new SimpleDateFormat("hh:mm a").format(Timestamp.valueOf(appEnd));

            if (customerId == a.getCustomerId() && a.getAppointmentId() != appointmentId) {
                if ((start.isBefore(appStart) && end.isAfter(appEnd)) || (start.isAfter(appStart) && start.isBefore(appEnd)) || (end.isAfter(appStart) && end.isBefore(appEnd)) || (start.equals(appStart) || end.equals(appEnd))) {
                    appointmentInfo.append(a.getAppointmentId()).append(", ").append(startDateFormatted).append(" - ").append(endDateFormatted).append("\n");
                }
            }
        }
        return appointmentInfo;
    }

    /**
     * This method handles updating an appointment, and all the validation needed to pass in order for that to happen.
     * This method checks for empty appointment values, if the appointment start/end dates are the same, if the appointment start time
     * precedes the appointment end time, if the appointment times fall within business hours, and if the appointment dates/times
     * overlap with existing appointment dates/times (see checkForOverlappingAppointments). Users are redirected to the Appointment
     * screen afterward.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    public void onSaveClick(ActionEvent event) throws SQLException, IOException {
        if (title.getText().isEmpty() || description.getText().isEmpty() || local.getText().isEmpty() || type.getText().isEmpty()
                || startDate.getValue() == null || startTimeHour == null || startTimeMin == null|| endDate.getValue() == null
                || endTimeHour == null || endTimeMin == null || customer.getSelectionModel().getSelectedItem() == null || user.getSelectionModel().getSelectedItem() == null || contact.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Appointment");
            alert.setHeaderText("Incomplete Fields");
            alert.setContentText("All fields must be complete to modify an appointment.");
            alert.showAndWait();
        }
        try {
            Integer appId = appointment.getAppointmentId();
            String titleVal = title.getText();
            String descVal = description.getText();
            String locationVal = local.getText();
            String typeVal = type.getText();
            Integer startHour = startTimeHour.getValue();
            Integer endHour = endTimeHour.getValue();

            if (startAMPM.getSelectionModel().getSelectedItem().equals("AM") && startHour == 12) {
                startHour = 0;
            }
            else if (startAMPM.getSelectionModel().getSelectedItem().equals("PM") && startTimeHour.getValue() != 12) {
                startHour += 12;
            }

            if (endAMPM.getSelectionModel().getSelectedItem().equals("AM") && endHour == 12) {
                endHour = 0;
            }
            else if (endAMPM.getSelectionModel().getSelectedItem().equals("PM") && endTimeHour.getValue() != 12) {
                endHour += 12;
            }

            LocalDateTime startDateTime = LocalDateTime.of(startDate.getValue(), LocalTime.of(startHour, startTimeMin.getValue()));
            ZonedDateTime startZdt = startDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
            ZonedDateTime startEtzZdt = startZdt.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalDateTime startEtzLdt = startEtzZdt.toLocalDateTime();
            LocalTime startEtzLt = LocalTime.of(startEtzLdt.getHour(), startEtzLdt.getMinute());

            LocalDateTime endDateTime = LocalDateTime.of(endDate.getValue(), LocalTime.of(endHour, endTimeMin.getValue()));
            ZonedDateTime endZdt = endDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
            ZonedDateTime endEtzZdt = endZdt.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalDateTime endEtzLdt = endEtzZdt.toLocalDateTime();
            LocalTime endEtzLt = LocalTime.of(endEtzLdt.getHour(), endEtzLdt.getMinute());

            LocalTime businessStart = LocalTime.of(8, 0);
            LocalTime businessEnd = LocalTime.of(22, 0);

            Timestamp currentDateTime = Timestamp.valueOf(LocalDateTime.now());
            String createdBy = LoginFormController.currentUser.getUsername();
            Integer custId = CustomerDAO.getCustomerID(customer.getSelectionModel().getSelectedItem());
            Integer userId = UserDAO.getUserId(user.getSelectionModel().getSelectedItem());
            Integer contactId = ContactDAO.getContactId(contact.getSelectionModel().getSelectedItem());
            StringBuilder overlappingAppInfo = checkForOverlappingAppointments(appId, custId, startDateTime, endDateTime);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

            if (!startDate.getValue().equals(endDate.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modify Appointment");
                alert.setHeaderText("Invalid Dates");
                alert.setContentText("Start and End Dates must be the same day.");
                alert.showAndWait();
            }
            else if ((!startDateTime.format(formatter).equals(appointment.getStartDate().format(formatter)) || !endDateTime.format(formatter).equals(appointment.getEndDate().format(formatter))) && ((startEtzLt.isBefore(businessStart) || startEtzLt.isAfter(businessEnd)) || (endEtzLt.isBefore(businessStart) || endEtzLt.isAfter(businessEnd)))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modify Appointment");
                alert.setHeaderText("Invalid Start and/or End Time");
                alert.setContentText("Appointment start and end times must be within the following business hours: 8:00 A.M. - 10:00 P.M. EST");
                alert.showAndWait();
            }
            else if (startDateTime.isAfter(endDateTime) || startDateTime.equals(endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modify Appointment");
                alert.setHeaderText("Invalid Start/End Time");
                alert.setContentText("Appointment start time must take place prior to end time.");
                alert.showAndWait();
            }
            else if (!overlappingAppInfo.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modify Appointment");
                alert.setHeaderText("Appointment times overlap with the following existing appointments.");
                alert.setContentText("Appointment ID, Appointment Date/Time: \n" + "\n" + overlappingAppInfo);
                alert.showAndWait();
            }
            else {
                AppointmentDAO.updateAppointment(appId, titleVal, descVal, locationVal, typeVal, startDateTime,
                        endDateTime, currentDateTime, createdBy, custId, userId, contactId);
                Helpers.createStage(event, "/schrader/schedulingapp/view/Appointments.fxml", "Appointment Schedule");

            }

        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        }
    }

    /**
     * This method redirects the user back to the Appointments screen when the Cancel button is clicked.
     * @param event
     * @throws IOException
     */
    public void onCancelClick(ActionEvent event) throws IOException {
        Helpers.createStage(event, "/schrader/schedulingapp/view/Appointments.fxml", "Appointments");
    }

    /**
     * This method sets up the Modify Appointment screen to be populated with all expected values (see populateDropDownValues and populateAppointmentValues).
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateDropDownValues();
            populateAppointmentValues();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

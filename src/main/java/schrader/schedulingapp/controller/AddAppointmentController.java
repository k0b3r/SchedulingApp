package schrader.schedulingapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AddAppointmentController {
    public TextField appointmentId;
    public ComboBox contact;
    public TextField type;
    public Button saveButton;
    public DatePicker startDate;
    public DatePicker endDate;
    public ComboBox customer;
    public ComboBox user;
    public ComboBox startTime;
    public ComboBox endTime;
    public TextField title;
    public TextArea description;
    public TextField local;

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

    public ObservableList<LocalTime> setTimeSlots(LocalTime min, LocalTime max) {
        ObservableList<LocalTime> timeSlots = FXCollections.observableArrayList();
        while (min.isBefore(max.plusSeconds(1))) {
            timeSlots.add(min);
            min = min.plusMinutes(15);
        }
        return timeSlots;
    }
    public void onSaveClick(ActionEvent event) throws IOException, SQLException {


        if (title.getText().isEmpty() || description.getText().isEmpty() || local.getText().isEmpty() || type.getText().isEmpty()
                || startDate.getValue() == null || startTime.getSelectionModel().getSelectedItem() == null || endDate.getValue() == null
                || endTime.getSelectionModel().getSelectedItem() == null || customer.getSelectionModel().getSelectedItem() == null || user.getSelectionModel().getSelectedItem() == null || contact.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Appointment");
            alert.setHeaderText("Incomplete Fields");
            alert.setContentText("All fields must be complete to add an appointment.");
            alert.show();
        } else {
            Integer appId = AppointmentDAO.generateAppointmentId();
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

            AppointmentDAO.insertAppointment(appId, titleVal, descVal, locationVal, typeVal, startAdd,
                    endAdd, currentDateTime, createdBy, currentDateTime, createdBy, custId, userId, contactId);

            createStage(event, "/schrader/schedulingapp/view/AppointmentSchedule.fxml", "Appointment Schedule");
        }
    }
    public void initialize() throws SQLException {
        try {
            contact.setItems(ContactDAO.getAllContactNames());
            customer.setItems(CustomerDAO.getCustomerNames());
            user.setItems(UserDAO.getUserNames());

            LocalTime startMin = LocalTime.of(0,0);
            LocalTime startMax = LocalTime.of(23,00);
            startTime.setItems(setTimeSlots(startMin,startMax));

            LocalTime endMin = LocalTime.of(0,0);
            LocalTime endMax = LocalTime.of(23,0);
            endTime.setItems(setTimeSlots(endMin, endMax));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

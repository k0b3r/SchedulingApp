package schrader.schedulingapp.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import schrader.schedulingapp.DAO.AppointmentDAO;
import schrader.schedulingapp.DAO.CustomerDAO;
import schrader.schedulingapp.DAO.UserDAO;
import schrader.schedulingapp.helper.Helpers;
import schrader.schedulingapp.model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * This class contains methods that are run when associated actions are taken on the Reports screen.
 */

/**
 * @author Karoline Schrader
 */
public class ReportsController implements Initializable {
    public ComboBox <String> customerCombo;
    public Label appointmentTotal;
    public ComboBox <String> userCombo;
    public ComboBox <Month> monthCombo;
    public ComboBox <String> typeCombo;
    public Button backButton;
    public TableView <Appointment> customerAppointments;
    public ObservableList<Appointment> customerApps = FXCollections.observableArrayList();
    public TableColumn <Appointment, Integer> apptId;
    public TableColumn <Appointment, String> title;
    public TableColumn <Appointment, String> description;
    public TableColumn <Appointment, String> type;
    public TableColumn <Appointment, String> startDate;
    public TableColumn <Appointment, String> endDate;
    public TableColumn <Appointment, Integer> customerId;
    public Label userAppointmentTotal;
    public ComboBox <Month> monthCombo2;

    /**
     * This method redirects the user to the Appointments screen on click of the Back button.
     * @param event
     * @throws IOException
     */
    public void onBackClick(ActionEvent event) throws IOException {
        Helpers.createStage(event, "/schrader/schedulingapp/view/Appointments.fxml", "Appointments");
    }

    /**
     * This method populates the Reports Appointment table based on the Customer selected.
     * @throws SQLException
     */
    public void populateCustomerAppointmentTable() throws SQLException {
        customerApps.setAll(AppointmentDAO.getAppointmentsByCustomer(CustomerDAO.getCustomerID(customerCombo.getSelectionModel().getSelectedItem())));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        apptId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDate.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getStartDate().format(formatter)));
        endDate.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getEndDate().format(formatter)));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerAppointments.setItems(customerApps);
    }

    /**
     * This method generates/displays the total number of appointments based on type and/or month selected.
     * @throws SQLException
     */
    public void generateTypeMonthTotal() throws SQLException {
        String total;
        if (!typeCombo.getSelectionModel().isEmpty() && monthCombo.getSelectionModel().isEmpty()) {
            total = String.valueOf(AppointmentDAO.getTotalAppointmentsByType(typeCombo.getSelectionModel().getSelectedItem()));
            appointmentTotal.setText(total);
        }
        else if (typeCombo.getSelectionModel().isEmpty() && !monthCombo.getSelectionModel().isEmpty()) {
            total = String.valueOf(AppointmentDAO.getTotalAppointmentsByMonth(Month.valueOf(monthCombo.getSelectionModel().getSelectedItem().toString()).getValue()));
            appointmentTotal.setText(total);
        }
        else {
            total = String.valueOf(AppointmentDAO.getTotalAppointmentsByTypeAndMonth(typeCombo.getSelectionModel().getSelectedItem(), Month.valueOf(monthCombo.getSelectionModel().getSelectedItem().toString()).getValue()));
            appointmentTotal.setText(total);
        }
    }

    /**
     * This method generates/displays the total number of appointments scheduled by the selected user in the selected month.
     * @throws SQLException
     */
    public void generateUserTotal() throws SQLException {
        if (!userCombo.getSelectionModel().isEmpty() && !monthCombo2.getSelectionModel().isEmpty()) {
            userAppointmentTotal.setText(String.valueOf(AppointmentDAO.getTotalAppointmentsByUserAndMonth(UserDAO.getUserId(userCombo.getSelectionModel().getSelectedItem()), Month.valueOf(monthCombo2.getSelectionModel().getSelectedItem().toString()).getValue())));
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reports");
            alert.setHeaderText("User and month selection are required.");
            alert.setContentText("Both the user and month are required to generate the number of appointments scheduled.");
            alert.showAndWait();
        }
    }

    /**
     * This method sets up the Reports screen to be populated with all the expected values (specifically the user, customer,
     * type, and month combo box values).
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userCombo.setItems(UserDAO.getUserNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            customerCombo.setItems(CustomerDAO.getCustomerNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            typeCombo.setItems(AppointmentDAO.getAppointmentTypes());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<Month> months = FXCollections.observableArrayList();
        Collections.addAll(months, Month.values());
        monthCombo.setItems(months);
        monthCombo2.setItems(months);
    }
}

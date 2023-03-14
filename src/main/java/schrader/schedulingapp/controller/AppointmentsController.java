package schrader.schedulingapp.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import schrader.schedulingapp.DAO.AppointmentDAO;
import schrader.schedulingapp.helper.Helpers;
import schrader.schedulingapp.model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;

/**
 * This class contains methods that are run when associated actions are taken on the Appointments screen.
 */

/**
 * @author Karoline Schrader
 */
public class AppointmentsController implements Initializable {
    public TableView <Appointment> appointmentTable;
    public TableColumn <Appointment, Integer> apptId;
    public TableColumn <Appointment, String> title;
    public TableColumn <Appointment, String> description;
    public TableColumn <Appointment, String> contact;
    public TableColumn <Appointment, String> type;
    public TableColumn <Appointment, String> startDate;
    public TableColumn <Appointment, String> endDate;
    public TableColumn <Appointment, Integer> customerId;
    public TableColumn <Appointment, Integer> userId;

    public TableColumn <Appointment, String> local;
    public ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public GridPane gridePane;
    public Label screenLabel;
    public Button deleteButton;
    public RadioButton byWeek;
    public RadioButton byMonth;
    public RadioButton viewAll;
    public Button viewCustomerButton;

    /**
     * This method redirects the user to the Login screen, as well as clearing the current user object (to be reset on next login)
     * when the Logout button is clicked.
     * @param event
     * @throws IOException
     */
    public void onLogoutClick(ActionEvent event) throws IOException {
        Helpers.createStage(event, "/schrader/schedulingapp/view/LoginForm.fxml", "Login");
        LoginFormController.currentUser = null;
    }

    /**
     * This method redirects users to the Add Appointment screen when the Add button is clicked.
     * @param event
     * @throws IOException
     */
    public void onAddClick(ActionEvent event) throws IOException {
        Helpers.createStage(event, "/schrader/schedulingapp/view/AddAppointment.fxml", "Add Appointment");
    }

    /**
     * This method redirects the user to the Modify Appointment screen if an appointment is selected, if not they get an alert
     * prompting them to select an appointment.
     * @param event
     * @throws IOException
     */
    public void onModifyClick(ActionEvent event) throws IOException {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            ModifyAppointmentController.loadSelectedAppointment(appointmentTable.getSelectionModel().getSelectedItem());
            Helpers.createStage(event, "/schrader/schedulingapp/view/ModifyAppointment.fxml", "Modify Appointment");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Schedule");
            alert.setHeaderText("Please select an appointment");
            alert.setContentText("An appointment must be selected in order to modify.");
            alert.showAndWait();
        }
    }

    /**
     * This method deletes an appointment if a selection if made, if not the user is prompted to select an appointment.
     * Alerts are also given to ask if they are sure, to confirm the delete was successful, or to let the user know it was unsuccessful.
     * @throws SQLException
     */
    public void onDelete() throws SQLException {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            Appointment selectedApp = appointmentTable.getSelectionModel().getSelectedItem();
            Alert appDelete = new Alert(Alert.AlertType.CONFIRMATION);
            appDelete.setTitle("Delete Appointment");
            appDelete.setHeaderText("Confirm Delete");
            appDelete.setContentText("Are you sure you want to delete the following appointment? \n" + "Appointment ID: "
                    + allAppointments.get(allAppointments.indexOf(appointmentTable.getSelectionModel().getSelectedItem())).getAppointmentId()
                    + "\nAppointment Type: " + allAppointments.get(allAppointments.indexOf(appointmentTable.getSelectionModel().getSelectedItem())).getType());
            appDelete.showAndWait();

            if (appDelete.getResult().getText().equals("OK")) {
                if (allAppointments.contains(appointmentTable.getSelectionModel().getSelectedItem())) {
                    Integer result = AppointmentDAO.deleteAppointment(selectedApp.getAppointmentId());
                    if (result > 0) {
                        allAppointments.remove(appointmentTable.getSelectionModel().getSelectedItem());
                        Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                        deleteSuccess.setTitle("Delete Appointment");
                        deleteSuccess.setHeaderText("Appointment Deleted");
                        deleteSuccess.setContentText("The appointment was successfully deleted.");
                        deleteSuccess.showAndWait();
                    }
                    else {
                        Alert deleteFailed = new Alert(Alert.AlertType.INFORMATION);
                        deleteFailed.setTitle("Delete Appointment");
                        deleteFailed.setHeaderText("Delete Failed");
                        deleteFailed.setContentText("The appointment was not be deleted. Please try again.");
                        deleteFailed.showAndWait();
                    }
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Schedule");
            alert.setHeaderText("Please select an appointment");
            alert.setContentText("An appointment must be selected in order to delete.");
            alert.showAndWait();
        }
    }

    /**
     * This method redirects the user to the Customers screen when they click the Customers button.
     * @param event
     * @throws IOException
     */
    public void onCustomerClick(ActionEvent event) throws IOException {
        Helpers.createStage(event, "/schrader/schedulingapp/view/Customers.fxml", "Customers");
    }

    /**
     * This method redirects the user to the Reports screen when they click the Reports button.
     * @param event
     * @throws IOException
     */
    public void onReportClick(ActionEvent event) throws IOException {
        Helpers.createStage(event, "/schrader/schedulingapp/view/Reports.fxml", "Reports");
    }

    /**
     * This method sets the appointment table to show only appointments within the current calendar week. When selected
     * the other radio button choices are set to false to prevent multiple selections.
     */
    public void filterByWeek() {
        ObservableList<Appointment> appointmentsByWeek = FXCollections.observableArrayList();
        viewAll.setSelected(false);
        byMonth.setSelected(false);
        // implementing a lambda expression here saved me from writing an additional SQL function to filter by month
        allAppointments.forEach((appointment -> {
            int appWeekOfYear = appointment.getStartDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            int currWeekOfYear = LocalDateTime.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            if (appWeekOfYear == currWeekOfYear) {
                appointmentsByWeek.add(appointment);
            }
        }));
        appointmentTable.setItems(appointmentsByWeek);
    }

    /**
     * This method sets the appointment table to show only appointments within the current month. When selected
     * the other radio button choices are set to false to prevent multiple selections.
     */
    public void filterByMonth() {
        ObservableList<Appointment> appointmentsByMonth = FXCollections.observableArrayList();
        viewAll.setSelected(false);
        byWeek.setSelected(false);
        // implementing a lambda expression here saved me from writing an additional SQL function to filter by month
        allAppointments.forEach((appointment -> {
            if (appointment.getStartDate().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                appointmentsByMonth.add(appointment);
            }}));
        appointmentTable.setItems(appointmentsByMonth);
    }

    /**
     * This method sets the appointment table to show all appointments. Then sets the by week and by month radio buttons
     * to false so only one selection is made at a time.
     */
    public void showAllAppointments() {
        byWeek.setSelected(false);
        byMonth.setSelected(false);

        appointmentTable.setItems(allAppointments);
    }

    /**
     * This method populates the Appointments table with existing appointments (start and end date are only displayed as Strings
     * for formatting purposes, the objects themselves use LocalDateTime and are stored as such)
     * @throws SQLException
     */
    public void populateAppointmentTable() throws SQLException {
        allAppointments.setAll(AppointmentDAO.getAppointments());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        apptId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        local.setCellValueFactory(new PropertyValueFactory<>("location"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDate.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getStartDate().format(formatter)));
        endDate.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getEndDate().format(formatter)));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentTable.setItems(allAppointments);
    }

    /**
     * This method sets up the Appointment screen to be populated with all existing appointments (see populateAppointmentTable).
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateAppointmentTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

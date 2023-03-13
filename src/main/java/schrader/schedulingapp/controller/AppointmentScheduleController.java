package schrader.schedulingapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import schrader.schedulingapp.Utilities.AppointmentDAO;
import schrader.schedulingapp.Utilities.CustomerDAO;
import schrader.schedulingapp.model.Appointment;
import schrader.schedulingapp.model.Customer;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentScheduleController {
    public TableView appointmentTable;
    public TableColumn apptId;
    public TableColumn title;
    public TableColumn description;
    public TableColumn contact;
    public TableColumn type;
    public TableColumn startDate;
    public TableColumn endDate;
    public TableColumn customerId;
    public TableColumn userId;

    public TableColumn local;
    public ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public GridPane gridePane;
    public Label screenLabel;
    public Button deleteButton;
    public RadioButton byWeek;
    public RadioButton byMonth;
    public RadioButton viewAll;

    /**
     *
     * @param event
     * @param resource
     * @param stageTitle
     * @throws IOException
     */
    public void createStage(ActionEvent event, String resource, String stageTitle) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(resource));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void onLogoutClick(ActionEvent event) throws IOException {
        createStage(event, "/schrader/schedulingapp/view/LoginForm.fxml", "Login");
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void onAddClick(ActionEvent event) throws IOException {
        if (apptId.getText().equals("Appt ID")) {
            createStage(event, "/schrader/schedulingapp/view/AddAppointment.fxml", "Add Appointment");
        }
        else {
            createStage(event, "/schrader/schedulingapp/view/AddCustomer.fxml", "Add Customer");
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void onModifyClick(ActionEvent event) throws IOException {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            if (apptId.getText().equals("Appt ID")) {
                ModifyAppointmentController.loadSelectedAppointment((Appointment) appointmentTable.getSelectionModel().getSelectedItem());
                createStage(event, "/schrader/schedulingapp/view/ModifyAppointment.fxml", "Modify Appointment");
            }
            else {
                ModifyCustomerController.loadSelectedCustomer(allCustomers.get(allCustomers.indexOf(appointmentTable.getSelectionModel().getSelectedItem())));
                createStage(event, "/schrader/schedulingapp/view/ModifyCustomer.fxml", "Modify Customer");
            }

        }

    }
    public void onDelete() throws SQLException {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            if (apptId.getText().equals("Appt ID")) {
                Alert appDelete = new Alert(Alert.AlertType.CONFIRMATION);
                appDelete.setTitle("Delete Appointment");
                appDelete.setHeaderText("Confirm Delete");
                appDelete.setContentText("Are you sure you want to delete the following appointment? \n" +
                        "Appointment ID: " + allAppointments.get(allAppointments.indexOf(appointmentTable.getSelectionModel().getSelectedItem())).getAppointmentId()
                + "\nAppointment Type: " + allAppointments.get(allAppointments.indexOf(appointmentTable.getSelectionModel().getSelectedItem())).getType());
                appDelete.showAndWait();

                if (appDelete.getResult().getText().equals("OK")) {
                    if (allAppointments.contains(appointmentTable.getSelectionModel().getSelectedItem())) {
                        allAppointments.remove(appointmentTable.getSelectionModel().getSelectedItem());
                        // TODO remove from DB
                        // TODO do all the checks for appointments
                        // TODO maybe need to implement a post delete message including appointmentID/type
                    }
                }

            } else {
                Alert customerDelete = new Alert(Alert.AlertType.CONFIRMATION);
                customerDelete.setTitle("Delete Customer");
                customerDelete.setHeaderText("Confirm Delete");
                customerDelete.setContentText("Are you sure you want to delete the following customer? \n" + allCustomers.get(allCustomers.indexOf(appointmentTable.getSelectionModel().getSelectedItem())).getCustomerName());
                customerDelete.showAndWait();

                if (customerDelete.getResult().getText().equals("OK")) {
                    if (allCustomers.contains(appointmentTable.getSelectionModel().getSelectedItem())) {
                        Boolean deleteResults = CustomerDAO.removeCustomer(allCustomers.get(allCustomers.indexOf(appointmentTable.getSelectionModel().getSelectedItem())).getCustomerId());
                        if (deleteResults == false) {
                            Alert deleteFailed = new Alert(Alert.AlertType.INFORMATION);
                            deleteFailed.setTitle("Delete Customer");
                            deleteFailed.setHeaderText("Failed Delete");
                            deleteFailed.setContentText("This customer can't be deleted while it has associated appointments.");
                            deleteFailed.show();
                        } else {
                            allCustomers.remove(appointmentTable.getSelectionModel().getSelectedItem());
                            Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                            deleteSuccess.setTitle("Delete Customer");
                            deleteSuccess.setHeaderText("Success");
                            deleteSuccess.setContentText("This customer was successfully deleted");
                            deleteSuccess.show();
                        }
                        // TODO do all the checks for customers
                    }
                }
            }
        }
    }

    public void onCustomerClick() throws SQLException {
        // repurpose Appointment TableView to be used for Customers - Setting text to match customer columns/resizing columns
        appointmentTable.resize(950, 400);
        apptId.setText("Customer ID");
        apptId.setMinWidth(73);
        apptId.setMaxWidth(73);

        title.setText("Name");
        title.setMinWidth(110);
        title.setMaxWidth(110);

        description.setText("Address");
        description.setMinWidth(125);
        description.setMaxWidth(125);

        local.setText("Postal");
        local.setMinWidth(55);
        local.setMaxWidth(55);

        type.setText("Phone");
        type.setMinWidth(110);
        type.setMaxWidth(110);

        startDate.setText("Create Date");
        startDate.setMinWidth(140);
        startDate.setMaxWidth(140);

        endDate.setText("Created By");
        endDate.setMinWidth(75);
        endDate.setMaxWidth(75);

        customerId.setText("Last Updated");
        customerId.setMinWidth(145);
        customerId.setMaxWidth(145);

        userId.setText("Updated By");
        userId.setMinWidth(75);
        userId.setMaxWidth(75);

        contact.setText("Division ID");
        contact.setMinWidth(70);
        contact.setMaxWidth(70);

        // TODO - added clear to solve the duplicating customer issue
        allCustomers.clear();
        allCustomers.addAll(CustomerDAO.getCustomers());
        apptId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        title.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        description.setCellValueFactory(new PropertyValueFactory<>("address"));
        local.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        type.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        userId.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        contact.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        appointmentTable.setItems(allCustomers);
    }
    /**
     *
     * @throws SQLException
     */
    public void populateAppointmentTable() throws SQLException {
        allAppointments.setAll(AppointmentDAO.getAppointments());
        convertAppointmentsToLTZ();
        apptId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        local.setCellValueFactory(new PropertyValueFactory<>("location"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentTable.setItems(allAppointments);
    }

    public void convertAppointmentsToLTZ() {
        for (Appointment a : allAppointments) {
            LocalDateTime startLdt = a.getStartDate();
            ZonedDateTime startZdt = startLdt.atZone(ZoneId.of("UTC"));
            ZonedDateTime startZdt2 = startZdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));

            // TODO - find out if time needs to be displayed formatted or if okay as is
            LocalDateTime startFinal = startZdt2.toLocalDateTime();
            a.setStartDate(startFinal);

            LocalDateTime endLdt = a.getEndDate();
            ZonedDateTime endZdt = endLdt.atZone(ZoneId.of("UTC"));
            ZonedDateTime endZdt2 = endZdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
            LocalDateTime endFinal = endZdt2.toLocalDateTime();
            a.setEndDate(endFinal);
        }
    }

    /**
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        populateAppointmentTable();
    }

    public void showAll(ActionEvent event) {
        // TODO test scenarios, do I need to run the query for this or just refilter?
        byWeek.setSelected(false);
        byMonth.setSelected(false);
        appointmentTable.setItems(allAppointments);
    }

    public void filterByMonth(ActionEvent event) throws SQLException {
        ObservableList<Appointment> byMonthAppointments = FXCollections.observableArrayList();
        viewAll.setSelected(false);
        byWeek.setSelected(false);
        byMonthAppointments.addAll(AppointmentDAO.getAppointmentsByMonth());
        appointmentTable.setItems(byMonthAppointments);
    }

    public void filterByWeek(ActionEvent event) throws SQLException {
        ObservableList<Appointment> byWeekAppointments = FXCollections.observableArrayList();
        viewAll.setSelected(false);
        byMonth.setSelected(false);
        byWeekAppointments.addAll(AppointmentDAO.getAppointmentsByWeek());
        appointmentTable.setItems(byWeekAppointments);
    }
}

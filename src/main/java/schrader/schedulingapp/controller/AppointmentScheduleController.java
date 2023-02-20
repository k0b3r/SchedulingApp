package schrader.schedulingapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import schrader.schedulingapp.Utilities.AppointmentDAO;
import schrader.schedulingapp.model.Appointment;
import schrader.schedulingapp.model.Customer;

import java.io.IOException;
import java.sql.SQLException;

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
    public ObservableList<Appointment> allApps = FXCollections.observableArrayList();

    public ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public GridPane gridePane;
    public Label screenLabel;

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
                ModifyCustomerController.loadSelectedCustomer((Customer) appointmentTable.getSelectionModel().getSelectedItem());
                createStage(event, "/schrader/schedulingapp/view/ModifyCustomer.fxml", "Modify Customer");
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

        allCustomers.addAll(AppointmentDAO.getCustomers());
        allApps.addAll(AppointmentDAO.getAppointments());
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
        allApps.addAll(AppointmentDAO.getAppointments());
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
        appointmentTable.setItems(allApps);
    }

    /**
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        populateAppointmentTable();
    }
}

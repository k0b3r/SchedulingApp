package schrader.schedulingapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import schrader.schedulingapp.Utilities.AppointmentDAO;
import schrader.schedulingapp.model.Appointment;

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

    public void onLogoutClick(ActionEvent event) throws IOException {
        createStage(event, "/schrader/schedulingapp/view/LoginForm.fxml", "Login");
    }

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
        contact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appointmentTable.setItems(allApps);
    }
    public void initialize() throws SQLException {
        populateAppointmentTable();
    }
}

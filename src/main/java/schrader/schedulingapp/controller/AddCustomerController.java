package schrader.schedulingapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schrader.schedulingapp.Utilities.CountryDAO;
import schrader.schedulingapp.Utilities.CustomerDAO;
import schrader.schedulingapp.Utilities.DivisionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AddCustomerController {
    public TextField customerName;
    public TextField phoneNumber;
    public TextField streetAddress;
    public TextField postalCode;
    public ComboBox country;
    public ComboBox state;

    public void createStage(ActionEvent event, String resource, String stageTitle) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(resource));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }
    public void onSaveClick(ActionEvent event) throws IOException, SQLException {
        Timestamp currentDateTime = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
        Integer divisionId = DivisionDAO.getDivisionId(country.getSelectionModel().getSelectedItem().toString(), state.getSelectionModel().getSelectedItem().toString());
        CustomerDAO.insertCustomer(CustomerDAO.generateCustomerId(), customerName.getText(), streetAddress.getText(), postalCode.getText(), phoneNumber.getText(), currentDateTime, LoginFormController.currentUser.getUsername(), currentDateTime, LoginFormController.currentUser.getUsername(), divisionId);
        createStage(event, "/schrader/schedulingapp/view/AppointmentSchedule.fxml", "Appointment Schedule");
    }

    public void populateDropdownFields() throws SQLException {
        country.setItems(CountryDAO.getAllCountries());
        state.setItems(DivisionDAO.getAllStates());
    }

    public void initialize() throws SQLException {
        populateDropdownFields();
    }
}

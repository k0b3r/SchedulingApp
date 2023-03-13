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
import schrader.schedulingapp.model.Customer;

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
        Integer customerId = CustomerDAO.generateCustomerId();
        String name = customerName.getText();
        String address1 = streetAddress.getText();
        String postal = postalCode.getText();
        String phone = phoneNumber.getText();

        // TODO - Dig into how to do time and if customers need to be in UTC as well
        Timestamp currentDateTime = Timestamp.valueOf(LocalDateTime.now());

        String user = LoginFormController.currentUser.getUsername();
        Integer divisionId = DivisionDAO.getDivisionId(country.getSelectionModel().getSelectedItem().toString(), state.getSelectionModel().getSelectedItem().toString());

        // TODO do something with returned value from query?
        CustomerDAO.insertCustomer(customerId, name, address1, postal, phone, currentDateTime, user, currentDateTime, user, divisionId);
        createStage(event, "/schrader/schedulingapp/view/AppointmentSchedule.fxml", "Appointment Schedule");
    }

    public void populateDropdownFields() throws SQLException {
        country.setItems(CountryDAO.getAllCountries());
        state.setItems(DivisionDAO.getAllStates());
    }

    public void initialize() throws SQLException {
        populateDropdownFields();
    }

    public void filterDivisionsByCountry(ActionEvent event) throws SQLException {
        state.setItems(DivisionDAO.filterDivisionsByCountry(CountryDAO.getCountryId(country.getSelectionModel().getSelectedItem().toString())));
    }
}

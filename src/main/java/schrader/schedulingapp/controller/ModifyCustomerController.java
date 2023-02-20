package schrader.schedulingapp.controller;
/**
 * Modify Customer Controller
 */
/**
 * @author Karoline Schrader
 */

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

public class ModifyCustomerController {

    private static Customer customer = null;
    public TextField customerId;
    public ComboBox country;
    public ComboBox state;
    public TextField postalCode;
    public TextField name;
    public TextField phoneNumber;
    public TextField streetAddress;

    /**
     *
     * @param selectedCustomer
     */
    public static void loadSelectedCustomer(Customer selectedCustomer) {
        customer = selectedCustomer;
    }

    public void createStage(ActionEvent event, String resource, String stageTitle) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(resource));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }

    public void onSaveClick(ActionEvent event) throws IOException, SQLException {
        // TODO do I just update the DB and not the object? Or both?
        CustomerDAO.updateCustomer(customer.getCustomerId(), name.getText(), phoneNumber.getText(), streetAddress.getText(), postalCode.getText(), state.getSelectionModel().getSelectedItem().toString(), country.getSelectionModel().getSelectedItem().toString());
        createStage(event, "/schrader/schedulingapp/view/AppointmentSchedule.fxml", "Appointment Schedule");
    }
    public void populateCustomerValues() throws SQLException {
        customerId.setText(String.valueOf(customer.getCustomerId()));
        name.setText(customer.getCustomerName());
        phoneNumber.setText(customer.getPhoneNumber());
        streetAddress.setText(customer.getAddress());
        country.setItems(CountryDAO.getAllCountries());
        country.setValue(CountryDAO.getCountry(customer.getDivisionId()));
        state.setItems(DivisionDAO.getAllStates());
        state.setValue(DivisionDAO.getState(customer.getDivisionId()));
        postalCode.setText(customer.getPostalCode());

    }
    public void initialize() throws SQLException {
        populateCustomerValues();
    }
}

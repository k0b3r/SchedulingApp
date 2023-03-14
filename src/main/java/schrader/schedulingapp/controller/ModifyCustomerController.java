package schrader.schedulingapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import schrader.schedulingapp.DAO.CountryDAO;
import schrader.schedulingapp.DAO.CustomerDAO;
import schrader.schedulingapp.DAO.DivisionDAO;
import schrader.schedulingapp.helper.Helpers;
import schrader.schedulingapp.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This class contains methods that are run when associated actions are taken on the Modify Customer form.
 */

/**
 * @author Karoline Schrader
 */

public class ModifyCustomerController implements Initializable {

    private static Customer customer = null;
    public TextField customerId;
    public ComboBox <String> country;
    public ComboBox <String> state;
    public TextField postalCode;
    public TextField name;
    public TextField phoneNumber;
    public TextField streetAddress;

    /**
     * This method handles passing the selected Customer object/values from the Customer screen for use on the Modify
     * Customer screen.
     * @param selectedCustomer
     */
    public static void loadSelectedCustomer(Customer selectedCustomer) {
        customer = selectedCustomer;
    }

    /**
     * This method modifies a Customer to updated values, and includes validation for empty values preventing users from
     * updating.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void onSaveClick(ActionEvent event) throws IOException, SQLException {
        if (name.getText().isEmpty() || streetAddress.getText().isEmpty() || postalCode.getText().isEmpty() ||
                phoneNumber.getText().isEmpty() || country.getSelectionModel().getSelectedItem() == null || state.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Customer");
            alert.setHeaderText("Incomplete Fields");
            alert.setContentText("All fields must be complete to modify a customer.");
            alert.showAndWait();
        } else {
            String customerName = name.getText();
            String address1 = streetAddress.getText();
            String postal = postalCode.getText();
            String phone = phoneNumber.getText();

            Timestamp currentDateTime = Timestamp.valueOf(LocalDateTime.now());

            String user = LoginFormController.currentUser.getUsername();
            Integer divisionId = DivisionDAO.getDivisionId(country.getSelectionModel().getSelectedItem(), state.getSelectionModel().getSelectedItem());

            CustomerDAO.updateCustomer(customer.getCustomerId(), customerName, phone, address1, postal, currentDateTime, user, divisionId);
            Helpers.createStage(event, "/schrader/schedulingapp/view/Customers.fxml", "Customers");
        }
    }

    /**
     * This method redirects the user to the Customer screen on click of the Cancel button.
     * @param event
     * @throws IOException
     */
    public void onCancelClick(ActionEvent event) throws IOException {
        Helpers.createStage(event, "/schrader/schedulingapp/view/Customers.fxml", "Customers");
    }

    /**
     * This method populates the Modify Customer form with values from the previously selected Customer.
     * @throws SQLException
     */
    public void populateCustomerValues() throws SQLException {
        customerId.setText(String.valueOf(customer.getCustomerId()));
        name.setText(customer.getCustomerName());
        phoneNumber.setText(customer.getPhoneNumber());
        streetAddress.setText(customer.getAddress());
        country.setItems(CountryDAO.getAllCountries());
        country.setValue(CountryDAO.getCountry(customer.getDivisionId()));
        state.setItems(DivisionDAO.filterDivisionsByCountry(CountryDAO.getCountryId(country.getSelectionModel().getSelectedItem())));
        state.setValue(DivisionDAO.getState(customer.getDivisionId()));
        postalCode.setText(customer.getPostalCode());

    }

    /**
     * This method populates the state combo box with values for user selection (instead of listing all possible options
     * across countries, the options are filtered based on the country the user selects).
     * @throws SQLException
     */
    public void filterDivisionByCountry() throws SQLException {
        state.setItems(DivisionDAO.filterDivisionsByCountry(CountryDAO.getCountryId(country.getSelectionModel().getSelectedItem())));
    }

    /**
     * This method sets up the Modify Customer screen to be populated with all expected values (see populateCustomerValues).
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateCustomerValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

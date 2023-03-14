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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This class contains methods that are run when associated actions are taken on the Add Customer form.
 */

/**
 * @author Karoline Schrader
 */
public class AddCustomerController implements Initializable {
    public TextField customerName;
    public TextField phoneNumber;
    public TextField streetAddress;
    public TextField postalCode;
    public ComboBox <String> country;
    public ComboBox <String> state;

    /**
     * This method handles creating a Customer. User input is checked for empty values which prevents Customer creation
     * if empty values are found. The user is redirected to the Customers screen after.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void onSaveClick(ActionEvent event) throws IOException, SQLException {
        if (customerName.getText().isEmpty() || streetAddress.getText().isEmpty() || postalCode.getText().isEmpty() ||
                phoneNumber.getText().isEmpty() || country.getSelectionModel().getSelectedItem() == null || state.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Customer");
            alert.setHeaderText("Incomplete Fields");
            alert.setContentText("All fields must be complete to add a customer.");
            alert.showAndWait();
        }
        else {
            try {
                Integer customerId = CustomerDAO.generateCustomerId();
                String name = customerName.getText();
                String address1 = streetAddress.getText();
                String postal = postalCode.getText();
                String phone = phoneNumber.getText();
                Timestamp currentDateTime = Timestamp.valueOf(LocalDateTime.now());
                String user = LoginFormController.currentUser.getUsername();
                Integer divisionId = DivisionDAO.getDivisionId(country.getSelectionModel().getSelectedItem(), state.getSelectionModel().getSelectedItem());

                CustomerDAO.insertCustomer(customerId, name, address1, postal, phone, currentDateTime, user, currentDateTime, user, divisionId);
                Helpers.createStage(event, "/schrader/schedulingapp/view/Customers.fxml", "Customers");
            } catch(NullPointerException npe) {
                npe.printStackTrace();
            }
        }
    }

    /**
     * This method redirects the user to the Customers screen after clicking the 'Cancel' button.
     * @param event
     * @throws IOException
     */
    public void onCancelClick(ActionEvent event) throws IOException {
        Helpers.createStage(event, "/schrader/schedulingapp/view/Customers.fxml", "Customers");
    }

    /**
     * This method populates the country and state combo boxes with values for user selection.
     * @throws SQLException
     */
    public void populateDropdownFields() throws SQLException {
        country.setItems(CountryDAO.getAllCountries());
        state.setItems(DivisionDAO.getAllStates());
    }

    /**
     * This method populates the state combo box with values for user selection (instead of listing all possible options
     * across countries, the options are filtered based on the country the user selects).
     * @param event
     * @throws SQLException
     */
    public void filterDivisionsByCountry(ActionEvent event) throws SQLException {
        state.setItems(DivisionDAO.filterDivisionsByCountry(CountryDAO.getCountryId(country.getSelectionModel().getSelectedItem())));
    }

    /**
     * This method sets up the Add Customer screen to be populated with all expected values (see populateDropDownFields).
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateDropdownFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

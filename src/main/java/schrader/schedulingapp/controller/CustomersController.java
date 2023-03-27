package schrader.schedulingapp.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import schrader.schedulingapp.DAO.CustomerDAO;
import schrader.schedulingapp.DAO.DivisionDAO;
import schrader.schedulingapp.helper.Helpers;
import schrader.schedulingapp.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * This class contains methods that are run when associated actions are taken on the Customers screen.
 */

/**
 * @author Karoline Schrader
 */
public class CustomersController implements Initializable {
    public TableView <Customer> customerTable;
    public TableColumn <Customer, Integer> customerId;
    public TableColumn <Customer, String> name;
    public TableColumn <Customer, String> address;
    public TableColumn <Customer, String> postalCode;
    public TableColumn <Customer, String> phoneNumber;
    public TableColumn <Customer, String> createDate;
    public TableColumn <Customer, String> createdBy;
    public TableColumn <Customer, String> lastUpdated;
    public TableColumn <Customer, String> lastUpdatedBy;
    public TableColumn <Customer, String> division;
    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * This method takes users to the Add Customers screen when the Add button is clicked.
     * @param event
     * @throws IOException
     */
    public void onAddClick(ActionEvent event) throws IOException {
        Helpers.createStage(event, "/schrader/schedulingapp/view/AddCustomer.fxml", "Add Customer");
    }

    /**
     * This method takes users to the Modify Customer screen when a customer is selected and the Modify button is clicked.
     * Customer data will be populated on the form. If there isn't a customer selected they will see an alert prompting
     * them to select one.
     * @param event
     * @throws IOException
     */
    public void onModifyClick(ActionEvent event) throws IOException {
        if (!customerTable.getSelectionModel().isEmpty()) {
            ModifyCustomerController.loadSelectedCustomer(allCustomers.get(allCustomers.indexOf(customerTable.getSelectionModel().getSelectedItem())));
            Helpers.createStage(event, "/schrader/schedulingapp/view/ModifyCustomer.fxml", "Modify Customer");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customers");
            alert.setHeaderText("Please select a customer");
            alert.setContentText("A customer must be selected in order to modify.");
            alert.showAndWait();
        }
    }

    /**
     * This method handles deletion of a customer when a customer is selected and the Delete button is selected. If a customer
     * is not selected they will be prompted to select one. When a customer is selected they will be prompted to confirm
     * the deletion. An alert modal is also given to confirm the customer was successfully deleted or not.
     * @throws SQLException
     */
    public void onDelete() throws SQLException {
        if (!customerTable.getSelectionModel().isEmpty()) {
            Alert customerDelete = new Alert(Alert.AlertType.CONFIRMATION);
            customerDelete.setTitle("Delete Customer");
            customerDelete.setHeaderText("Confirm Delete");
            customerDelete.setContentText("Are you sure you want to delete the following customer? \n" + allCustomers.get(allCustomers.indexOf(customerTable.getSelectionModel().getSelectedItem())).getCustomerName());
            customerDelete.showAndWait();
            if (customerDelete.getResult().getText().equals("OK")) {
                if (allCustomers.contains(customerTable.getSelectionModel().getSelectedItem())) {
                    Boolean deleteResults = CustomerDAO.removeCustomer(allCustomers.get(allCustomers.indexOf(customerTable.getSelectionModel().getSelectedItem())).getCustomerId());
                    if (!deleteResults) {
                        Alert deleteFailed = new Alert(Alert.AlertType.INFORMATION);
                        deleteFailed.setTitle("Delete Customer");
                        deleteFailed.setHeaderText("Failed Delete");
                        deleteFailed.setContentText("This customer can't be deleted while it has associated appointments.");
                        deleteFailed.showAndWait();
                    } else {
                        allCustomers.remove(customerTable.getSelectionModel().getSelectedItem());
                        Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                        deleteSuccess.setTitle("Delete Customer");
                        deleteSuccess.setHeaderText("Success");
                        deleteSuccess.setContentText("This customer was successfully deleted");
                        deleteSuccess.showAndWait();
                    }
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer");
            alert.setHeaderText("Please select a customer.");
            alert.setContentText("A customer must be selected in order to delete.");
            alert.showAndWait();
        }
    }

    /**
     * This method takes users to the Appointments screen when the Appointments button is clicked.
     * @param event
     * @throws IOException
     */
    public void onAppointmentClick(ActionEvent event) throws IOException {
        Helpers.createStage(event, "/schrader/schedulingapp/view/Appointments.fxml", "Appointments");
    }

    /**
     * This method redirects the user to the Login screen, as well as clearing the current user object (to be reset on next login)
     * when the Logout button is clicked.
     * @param event
     * @throws IOException
     */
    public void onLogoutClick(ActionEvent event) throws IOException {
        LoginFormController.currentUser = null;
        Helpers.createStage(event, "/schrader/schedulingapp/view/LoginForm.fxml", "Login");
    }

    /**
     * This method populates the Customers table with all customers (Create Date and Last Updated are only displayed as Strings
     * for formatting purposes, the objects themselves use LocalDateTime and are stored as such)
     * @throws SQLException
     */
    public void populateCustomersTable() throws SQLException {
        allCustomers.addAll(CustomerDAO.getCustomers());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        createDate.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getCreatedDate().format(formatter)));
        createdBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdated.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getLastUpdated().format(formatter)));
        lastUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        division.setCellValueFactory(customer -> {
            try {
                return new SimpleStringProperty(DivisionDAO.getState(customer.getValue().getDivisionId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        customerTable.setItems(allCustomers);
    }

    /**
     * This method sets up the Customer screen to be populated with all customers (see populateCustomersTable).
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateCustomersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

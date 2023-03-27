package schrader.schedulingapp.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schrader.schedulingapp.helper.JDBC;
import schrader.schedulingapp.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

/**
 * This class includes methods that query the Customer database table and are used throughout the application.
 */

/**
 * @author Karoline Schrader
 */
public class CustomerDAO {
    /**
     * This method gets the largest Customer_ID, increments that value, then returns it.
     * @return customerId
     * @throws SQLException
     */
    public static Integer generateCustomerId() throws SQLException {
        String sql = "SELECT MAX(Customer_ID) AS Customer_ID FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer customerId = rs.getInt("Customer_ID") + 1;
        return customerId;
    }

    /**
     * This method gets/returns the customerID for the provided customer name.
     * @param name
     * @return customerId
     * @throws SQLException
     */
    public static Integer getCustomerID(String name) throws SQLException {
        String sql = "SELECT Customer_ID FROM client_schedule.customers WHERE Customer_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer customerId = rs.getInt("Customer_ID");
        return customerId;
    }

    /**
     * This method gets/returns the customer name for the associated customerId.
     * @param customerId
     * @return name
     * @throws SQLException
     */
    public static String getCustomerName(Integer customerId) throws SQLException {
        String sql = "SELECT Customer_Name FROM client_schedule.customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, String.valueOf(customerId));
        ResultSet rs = ps.executeQuery();
        rs.next();
        String name = rs.getString("Customer_Name");
        return name;
    }

    /**
     * This method gets all customer names and returns them in a list.
     * @return names
     * @throws SQLException
     */
    public static ObservableList<String> getCustomerNames() throws SQLException {
        ObservableList<String> names = FXCollections.observableArrayList();
        String sql = "SELECT Customer_Name from client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String customerName = rs.getString("Customer_Name");
            names.add(customerName);
        }
        return names;
    }

    /**
     * This method gets all customer rows from the Customer table, uses those values to create Customer objects, then
     * returns those objects in a list.
     * @return customers
     * @throws SQLException
     */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Integer customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            Integer divisionId = rs.getInt("Division_ID");

            Customer customer = new Customer(customerId, customerName, address, postalCode, phone, createDate, createdBy,
                    lastUpdate, lastUpdatedBy, divisionId);
            customers.add(customer);
        }
        return customers;
    }

    /**
     * This method inserts a row into the Customer table with the provided values.
     * @param customerId
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionId
     * @throws SQLException
     */
    public static void insertCustomer(Integer customerId, String customerName, String address, String postalCode, String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, Integer divisionId) throws SQLException {
        String sql = "INSERT INTO client_schedule.customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.setString(2, customerName);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setTimestamp(6, createDate);
        ps.setString(7, createdBy);
        ps.setTimestamp(8, lastUpdate);
        ps.setString(9, lastUpdatedBy);
        ps.setInt(10, divisionId);
        ps.executeUpdate();
    }

    /**
     * This method updates the row for the associated customerId with the provided values.
     * @param customerId
     * @param customerName
     * @param phoneNumber
     * @param address
     * @param postalCode
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionId
     * @throws SQLException
     */
    public static void updateCustomer(Integer customerId, String customerName, String phoneNumber, String address,
                                         String postalCode, Timestamp lastUpdate, String lastUpdatedBy, Integer divisionId) throws SQLException {

        String sql = "UPDATE client_schedule.customers " +
                "SET Customer_Name = ?, Phone = ?, Address = ?, Postal_Code = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, customerName);
        ps.setString(2, phoneNumber);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setTimestamp(5, lastUpdate);
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, divisionId);
        ps.setInt(8, customerId);
        ps.executeUpdate();
    }

    /**
     * This method removes the row associated with the provided customerId from the Customer table.
     * @param customerId
     * @return results
     * @throws SQLException
     */
    public static Boolean removeCustomer(Integer customerId) throws SQLException {
        String sql = "SELECT COUNT(*) AS Appointments FROM client_schedule.appointments WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        Boolean results;
        rs.next();
        if (rs.getInt("Appointments") > 0) {
            results = false;
            return results;
        } else {
            String deleteCustomer = "DELETE FROM client_schedule.customers WHERE CUSTOMER_ID = ?";
            PreparedStatement ps2 = JDBC.connection.prepareStatement(deleteCustomer);
            ps2.setInt(1, customerId);
            ps2.executeUpdate();
            results = true;
            return results;
        }
    }
}

package schrader.schedulingapp.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schrader.schedulingapp.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class CustomerDAO {

    public static Integer generateCustomerId() throws SQLException {
        String sql = "SELECT MAX(Customer_ID) AS Customer_ID FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer customerId = rs.getInt("Customer_ID") + 1;
        return customerId;
    }

    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LocalDate date = rs.getTimestamp("Create_Date").toLocalDateTime().toLocalDate();
            LocalTime time = rs.getTimestamp("Create_Date").toLocalDateTime().toLocalTime();
            LocalDateTime convertedTime = rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.of(ZoneId.systemDefault().toString())).toLocalDateTime();

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

    public static Integer insertCustomer(Integer customerId, String customerName, String address, String postalCode, String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, Integer divisionId) throws SQLException {
        String insertCustomerQuery = "INSERT INTO client_schedule.customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // TODO do I need to also create this customer as an object/insert to list?
        PreparedStatement ps = JDBC.connection.prepareStatement(insertCustomerQuery);
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
        Integer result = ps.executeUpdate();
        return result;
    }

    public static Integer updateCustomer(Integer customerId, String customerName, String phoneNumber, String address, String postalCode, LocalDateTime lastUpdate, String lastUpdatedBy, String state, String country) throws SQLException {

        String updateCustomerQuery = "UPDATE client_schedule.customers " +
                "SET Customer_Name = ?, Phone = ?, Address = ?, Postal_Code = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = (SELECT Division_ID from client_schedule.first_level_divisions where Country_ID = (SELECT Country_ID from client_schedule.countries where Country = ?) AND Division = ?) WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(updateCustomerQuery);
        Timestamp lastUpdated = Timestamp.valueOf(lastUpdate);

        ps.setString(1, customerName);
        ps.setString(2, phoneNumber);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setTimestamp(5, lastUpdated);
        ps.setString(6, lastUpdatedBy);
        ps.setString(7, country);
        ps.setString(8, state);
        ps.setInt(9, customerId);
        Integer rowsEffected = ps.executeUpdate();
        return rowsEffected;
    }

    public static Boolean removeCustomer(Integer customerId) throws SQLException {
        String sql = "SELECT COUNT(*) AS Appointments FROM client_schedule.appointments WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        if (rs.getInt("Appointments") > 0) {
            return false;
        } else {
            String deleteCustomer = "DELETE FROM client_schedule.customers WHERE CUSTOMER_ID = ?";
            PreparedStatement ps2 = JDBC.connection.prepareStatement(deleteCustomer);
            ps2.setInt(1, customerId);
            ps2.executeUpdate();
            return true;
        }
    }
}

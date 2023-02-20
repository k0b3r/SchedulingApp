package schrader.schedulingapp.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomerDAO {

    public static Integer generateCustomerId() throws SQLException {
        String sql = "SELECT MAX(Customer_ID) AS Customer_ID FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer customerId = rs.getInt("Customer_ID") + 1;
        return customerId;
    }
    public static Integer insertCustomer(Integer customerId, String customerName, String address, String postalCode, String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, Integer divisionId) throws SQLException {
        String insertCustomerQuery = "INSERT INTO client_schedule.customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(insertCustomerQuery);
        ps.setString(1, String.valueOf(customerId));
        ps.setString(2, customerName);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setString(6, String.valueOf(createDate));
        ps.setString(7, createdBy);
        ps.setString(8, String.valueOf(lastUpdate));
        ps.setString(9, lastUpdatedBy);
        ps.setString(10, String.valueOf(divisionId));
        Integer result = ps.executeUpdate();
        return result;
    }
    public static Integer updateCustomer(Integer customerId, String customerName, String phoneNumber, String address, String postalCode, String state, String country) throws SQLException {

        String updateCustomerQuery = "UPDATE client_schedule.customers " +
                "SET Customer_Name = ?, Phone = ?, Address = ?, Postal_Code = ?, Division_ID = (SELECT Division_ID from client_schedule.first_level_divisions where Country_ID = (SELECT Country_ID from client_schedule.countries where Country = ?) AND Division = ?) WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(updateCustomerQuery);
        ps.setString(1, customerName);
        ps.setString(2, phoneNumber);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, country);
        ps.setString(6, state);
        ps.setString(7, String.valueOf(customerId));
        Integer rowsEffected = ps.executeUpdate();
        return rowsEffected;
    }
}

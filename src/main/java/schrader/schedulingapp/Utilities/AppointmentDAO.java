package schrader.schedulingapp.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schrader.schedulingapp.model.Appointment;
import schrader.schedulingapp.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAO {
    /**
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, appointments.Created_By, appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, appointments.User_ID, contacts.Contact_Name\n" +
                "FROM client_schedule.appointments \n" +
                "INNER JOIN client_schedule.contacts ON appointments.Contact_ID = contacts.Contact_ID;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"),
                        rs.getString("Location"), rs.getString("Type"), rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(), rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"), rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getString("Contact_Name"));
            appointments.add(app);
        }
        return appointments;
    }

    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Customer customer = new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"),
                    rs.getString("Postal_Code"), rs.getString("Phone"), rs.getTimestamp("Create_Date").toLocalDateTime(),
                    rs.getString("Created_By"), rs.getTimestamp("Last_Update"),
                    rs.getString("Last_Updated_By"), rs.getInt("Division_ID"));
            customers.add(customer);
        }
        return customers;
    }
}

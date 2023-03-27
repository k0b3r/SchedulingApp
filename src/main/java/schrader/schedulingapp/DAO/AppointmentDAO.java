package schrader.schedulingapp.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schrader.schedulingapp.helper.JDBC;
import schrader.schedulingapp.model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class includes methods that query the Appointments database table and are used throughout the application.
 */

/**
 * @author Karoline Schrader
 */
public class AppointmentDAO {
    /**
     * This method gets the largest Appointment_ID in the Appointments table, increments that number, and returns it.
     * @return appId
     * @throws SQLException
     */
    public static Integer generateAppointmentId() throws SQLException {
        String sql = "SELECT MAX(Appointment_ID) AS Appointment_ID FROM client_schedule.appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer appId = rs.getInt("Appointment_ID") + 1;
        return appId;
    }

    /**
     * This method gets all appointments from the Appointments table, uses it to create Appointment objects, and returns
     * those objects in a list.
     * @return appointments
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, " +
                "appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, appointments.Created_By, " +
                "appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, appointments.User_ID, contacts.Contact_Name " +
                "FROM client_schedule.appointments \n" + "INNER JOIN client_schedule.contacts ON appointments.Contact_ID " +
                "= contacts.Contact_ID ORDER BY appointments.Appointment_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"),
                        rs.getString("Location"), rs.getString("Type"), rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(), rs.getTimestamp("Create_Date"),
                        rs.getString("Created_By"), rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getString("Contact_Name"));
            appointments.add(app);
        }
        return appointments;
    }

    /**
     * This method gets the appointments associated with the customer id provided, creates objects with it, and returns
     * those objects in a list.
     * @param id
     * @return appointmentsByCustomer
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentsByCustomer(Integer id) throws SQLException {
        ObservableList<Appointment> appointmentsByCustomer = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"),
                    rs.getString("Location"), rs.getString("Type"), rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(), rs.getTimestamp("Create_Date"),
                    rs.getString("Created_By"), rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getString("Contact_ID"));
            appointmentsByCustomer.add(app);
        }
        return  appointmentsByCustomer;
    }

    /**
     * This method gets the total number of appointments with the type value provided and that take place in the month provided.
     * @param type
     * @param month
     * @return total
     * @throws SQLException
     */
    public static Integer getTotalAppointmentsByTypeAndMonth(String type, Integer month) throws SQLException {
        String sql = "SELECT COUNT(Appointment_ID) FROM client_schedule.appointments WHERE Type = ? AND MONTH(Start) = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, type);
        ps.setInt(2, month);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer total = rs.getInt(1);
        return total;
    }

    /**
     * This method gets the total number of appointments created by the user provided and created in the month provided.
     * @param userId
     * @param month
     * @return total
     * @throws SQLException
     */
    public static Integer getTotalAppointmentsByUserAndMonth(Integer userId, Integer month) throws SQLException {
        String sql = "SELECT COUNT(Appointment_ID) FROM client_schedule.appointments WHERE User_ID = ? AND MONTH(Create_Date) = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, month);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer total = rs.getInt(1);
        return total;
    }

    /**
     * This method returns a list of all appointment types without duplicates.
     * @return types
     * @throws SQLException
     */
    public static ObservableList<String> getAppointmentTypes() throws SQLException {
        ObservableList<String> types = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT Type FROM client_schedule.appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            types.add(rs.getString("Type"));
        }
        return types;
    }

    /**
     * This method inserts a row into the Appointment table with the provided values.
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDateTime
     * @param endDateTime
     * @param createDate
     * @param createdBy
     * @param lastUpdated
     * @param lastUpdatedBy
     * @param customerId
     * @param userId
     * @param contact
     * @return result
     * @throws SQLException
     */
    public static Integer insertAppointment(Integer appointmentId, String title, String description, String location,
                                            String type, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                            Timestamp createDate, String createdBy, Timestamp lastUpdated,
                                            String lastUpdatedBy, Integer customerId, Integer userId, Integer contact) throws SQLException {
        String sql = "INSERT INTO client_schedule.appointments (Appointment_ID, Title, Description, Location, Type, Start," +
                "End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setTimestamp(6, Timestamp.valueOf(startDateTime));
        ps.setTimestamp(7, Timestamp.valueOf(endDateTime));
        ps.setTimestamp(8, createDate);
        ps.setString(9, createdBy);
        ps.setTimestamp(10, lastUpdated);
        ps.setString(11, lastUpdatedBy);
        ps.setInt(12, customerId);
        ps.setInt(13, userId);
        ps.setInt(14, contact);
        Integer result = ps.executeUpdate();
        return result;
    }

    /**
     * This method updates the provided Appointment with the given values in the Appointments table.
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDateTime
     * @param endDateTime
     * @param lastUpdated
     * @param lastUpdatedBy
     * @param customerId
     * @param userId
     * @param contact
     * @return result
     * @throws SQLException
     */
    public static Integer updateAppointment(Integer appointmentId, String title, String description, String location,
                                            String type, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                            Timestamp lastUpdated, String lastUpdatedBy, Integer customerId, Integer userId, Integer contact) throws SQLException {
        String sql = "UPDATE client_schedule.appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
        ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
        ps.setTimestamp(7, lastUpdated);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contact);
        ps.setInt(12, appointmentId);
        Integer result = ps.executeUpdate();
        return result;
    }

    /**
     * This method deletes the provided appointment from the Appointments table.
     * @param appointmentId
     * @return result
     * @throws SQLException
     */
    public static Integer deleteAppointment(Integer appointmentId) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        Integer result = ps.executeUpdate();
        return result;
    }

}

package schrader.schedulingapp.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schrader.schedulingapp.model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentDAO {

    public static Integer generateAppointmentId() throws SQLException {
        String sql = "SELECT MAX(Appointment_ID) AS Appointment_ID FROM client_schedule.appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer appId = rs.getInt("Appointment_ID") + 1;
        return appId;
    }
    /**
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, appointments.Created_By, appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, appointments.User_ID, contacts.Contact_Name\n" +
                "FROM client_schedule.appointments \n" +
                "INNER JOIN client_schedule.contacts ON appointments.Contact_ID = contacts.Contact_ID ORDER BY appointments.Appointment_ID";
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

    public static ObservableList<Appointment> getAppointmentsByMonth() throws SQLException {
        ObservableList<Appointment> appointmentsByMonth = FXCollections.observableArrayList();
        String sql = "select * from Appointments where MONTH(Start) = MONTH(CURDATE())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"),
                    rs.getString("Location"), rs.getString("Type"), rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(), rs.getTimestamp("Create_Date"),
                    rs.getString("Created_By"), rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getString("Contact_ID"));
            appointmentsByMonth.add(app);
        }
        return appointmentsByMonth;
    }

    public static ObservableList<Appointment> getAppointmentsByWeek() throws SQLException {
        ObservableList<Appointment> appointmentsByWeek = FXCollections.observableArrayList();
        String sql = "select * from Appointments where WEEK(Start) = WEEK(CURDATE())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"),
                    rs.getString("Location"), rs.getString("Type"), rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(), rs.getTimestamp("Create_Date"),
                    rs.getString("Created_By"), rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getString("Contact_ID"));
            appointmentsByWeek.add(app);
        }
        return appointmentsByWeek;
    }

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

    public static Integer updateAppointment(Integer appointmentId, String title, String description, String location,
                                            String type, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                            Timestamp createDate, String createdBy, Timestamp lastUpdated,
                                            String lastUpdatedBy, Integer customerId, Integer userId, Integer contact) throws SQLException {
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
}

package schrader.schedulingapp.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {
    public static String getContactName(int contactId) throws SQLException {
        String contactNameQuery = "SELECT Contact_Name FROM client_schedule.contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(contactNameQuery);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String contactName = rs.getString("Contact_Name");
        return contactName;
    }

    public static ObservableList<String> getAllContactNames() throws SQLException {
        ObservableList<String> allContacts = FXCollections.observableArrayList();
        String allContactNamesQuery = "SELECT Contact_Name FROM client_schedule.contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(allContactNamesQuery);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            allContacts.add(rs.getString("Contact_Name"));
        }
        return allContacts;
    }
}

package schrader.schedulingapp.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schrader.schedulingapp.helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {
    public static Integer getContactId(String contactId) throws SQLException {
        String sql = "SELECT Contact_ID fROM client_schedule.contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer contact = rs.getInt("Contact_ID");
        return contact;
    }

    public static ObservableList<String> getAllContactNames() throws SQLException {
        ObservableList<String> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT Contact_Name FROM client_schedule.contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            allContacts.add(rs.getString("Contact_Name"));
        }
        return allContacts;
    }
}

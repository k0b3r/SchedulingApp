package schrader.schedulingapp.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schrader.schedulingapp.helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class includes methods that query the User database table and are used throughout the application.
 */

/**
 * @author Karoline Schrader
 */
public abstract class UserDAO {

    /**
     * This method gets the row associated with the provided username and password. Returned is the number of rows.
     * @param username
     * @param password
     * @return count
     * @throws SQLException
     */
    public static int getUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM client_schedule.users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        int count = 0;
        while (rs.next()) {
            count++;
        }
        return count;
    }

    /**
     * This method gets/returns the row for the user specified from the Users table.
     * @param username
     * @return rs
     * @throws SQLException
     */
    public static ResultSet getUser(String username) throws SQLException {
        String sql = "SELECT * FROM client_schedule.users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * This method gets all user names from the Users table and returns them in a list.
     * @return users
     * @throws SQLException
     */
    public static ObservableList<String> getUserNames() throws SQLException {
        ObservableList<String> users = FXCollections.observableArrayList();
        String sql = "SELECT User_Name FROM client_schedule.users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String userName = rs.getString("User_Name");
            users.add(userName);
        }
        return users;
    }

    /**
     * This method gets/returns the userId associated with the name provided.
     * @param name
     * @return userId
     * @throws SQLException
     */
    public static Integer getUserId(String name) throws SQLException {
        String sql = "SELECT User_ID FROM client_schedule.users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer userId = rs.getInt("User_ID");
        return userId;
    }

    /**
     * This method gets/returns the user name associated with the user ID provided.
     * @param id
     * @return name
     * @throws SQLException
     */
    public static String getUserName(Integer id) throws SQLException {
        String sql = "SELECT User_Name from client_schedule.users WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String name = rs.getString("User_Name");
        return name;
    }
}

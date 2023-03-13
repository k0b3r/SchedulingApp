package schrader.schedulingapp.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDAO {
    public static int select(String username, String password) throws SQLException {
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

    public static ResultSet getUser(String username) throws SQLException {
        String sql = "SELECT * FROM client_schedule.users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

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

    public static Integer getUserId(String name) throws SQLException {
        String sql = "SELECT User_ID FROM client_schedule.users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer userId = rs.getInt("User_ID");
        return userId;
    }

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

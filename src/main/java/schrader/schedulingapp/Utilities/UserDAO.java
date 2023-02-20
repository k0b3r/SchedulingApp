package schrader.schedulingapp.Utilities;

import schrader.schedulingapp.model.User;

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

    public static ResultSet createUser(String username) throws SQLException {
        String sql = "SELECT * FROM client_schedule.users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}

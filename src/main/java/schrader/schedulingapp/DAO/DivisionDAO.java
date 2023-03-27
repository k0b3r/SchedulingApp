package schrader.schedulingapp.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schrader.schedulingapp.helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class includes methods that query the Division database table and are used throughout the application.
 */

/**
 * @author Karoline Schrader
 */
public class DivisionDAO {
    /**
     * This method gets/returns the state for the provided divisionId.
     * @param divisionId
     * @return state
     * @throws SQLException
     */
    public static String getState(Integer divisionId) throws SQLException {
        String sql = "SELECT Division FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String state = rs.getString("Division");
        return state;
    }

    /**
     * This method gets/returns the divisionId based on the country and division name provided.
     * @param country
     * @param division
     * @return divisionId
     * @throws SQLException
     */
    public static Integer getDivisionId(String country, String division) throws SQLException {
        String sql = "SELECT Division_ID FROM client_schedule.first_level_divisions WHERE Country_ID = (SELECT Country_ID FROM client_schedule.countries WHERE Country = ?) AND Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, country);
        ps.setString(2, division);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer divisionId = rs.getInt("Division_ID");
        return divisionId;
    }

    /**
     * This method gets all states and returns them in a list of strings.
     * @return allStates
     * @throws SQLException
     */
    public static ObservableList<String> getAllStates() throws SQLException {
        ObservableList<String> allStates = FXCollections.observableArrayList();
        String sql = "SELECT Division FROM client_schedule.first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            allStates.add(rs.getString("Division"));
        }
        return allStates;
    }

    /**
     * This method gets/returns divisions associated with the provided countryId, then returns them in a list.
     * @param countryId
     * @return filteredDivisions
     * @throws SQLException
     */
    public static ObservableList<String> filterDivisionsByCountry(Integer countryId) throws SQLException {
        ObservableList<String> filteredDivisions = FXCollections.observableArrayList();
        String sql = "SELECT Division FROM client_schedule.first_level_divisions where COUNTRY_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, countryId.toString());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            filteredDivisions.add(rs.getString("Division"));
        }
        return filteredDivisions;
    }
}

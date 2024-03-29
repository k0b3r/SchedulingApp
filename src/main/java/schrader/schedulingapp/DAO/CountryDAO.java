package schrader.schedulingapp.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schrader.schedulingapp.helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class includes methods that query the Country database table and are used throughout the application.
 */

/**
 * @author Karoline Schrader
 */
public class CountryDAO {

    /**
     * This method gets the country name associated with the provided divisionId.
     * @param divisionId
     * @return country
     * @throws SQLException
     */
    public static String getCountry(Integer divisionId) throws SQLException {
        String sql = "SELECT Country FROM client_schedule.countries WHERE Country_ID = (SELECT COUNTRY_ID FROM client_schedule.first_level_divisions WHERE Division_ID = ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String country = rs.getString("Country");
        return country;
    }

    /**
     * This method gets all country names and returns them in a list.
     * @return countries
     * @throws SQLException
     */
    public static ObservableList<String> getAllCountries() throws SQLException {
        ObservableList<String> countries = FXCollections.observableArrayList();
        String sql = "SELECT COUNTRY from client_schedule.countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            countries.add(rs.getString("Country"));
        }
        return countries;
    }

    /**
     * This method gets/returns the countryId for the provided country name.
     * @param country
     * @return countryId
     * @throws SQLException
     */
    public static Integer getCountryId(String country) throws SQLException {
        String sql = "SELECT COUNTRY_ID FROM client_schedule.countries WHERE Country = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, country);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer countryId = rs.getInt("Country_ID");
        return countryId;
    }
}

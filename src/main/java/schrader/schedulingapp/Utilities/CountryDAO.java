package schrader.schedulingapp.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schrader.schedulingapp.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
    public static String getCountry(Integer divisionId) throws SQLException {
        String countryNameQuery = "SELECT Country FROM client_schedule.countries WHERE Country_ID = (SELECT COUNTRY_ID FROM client_schedule.first_level_divisions WHERE Division_ID = ?)";
        // String countryIdQuery = "SELECT COUNTRY_ID FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(countryNameQuery);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String country = rs.getString("Country");
        return country;
    }

    public static ObservableList<String> getAllCountries() throws SQLException {
        ObservableList<String> countries = FXCollections.observableArrayList();
        String countriesQuery = "SELECT COUNTRY from client_schedule.countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(countriesQuery);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            countries.add(rs.getString("Country"));
        }
        return countries;
    }

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

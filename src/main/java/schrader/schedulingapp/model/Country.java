package schrader.schedulingapp.model;

import java.security.Timestamp;
import java.time.LocalDateTime;

/**
 * Country.java class
 */

/**
 * @author Karoline Schrader
 */
public class Country {
    int countryId;
    String country;
    LocalDateTime createDate;
    String createdBy;
    Timestamp lastUpdated;
    String lastUpdatedBy;

    public Country(int countryId, String country, LocalDateTime createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method gets the countryId for the Country.
     * @return countryId
     */
    public int getCountryId() {return countryId;}

    /**
     * This method sets the countryId for the Country.
     * @param countryId
     */
    public void setCountryId(int countryId) {this.countryId = countryId;}

    /**
     * This method gets the country name for the Country.
     * @return country
     */
    public String getCountry() {return country;}

    /**
     * This method sets the country name for the Country.
     * @param country
     */
    public void setCountry(String country) {this.country = country;}

    /**
     * This method gets the create date for the Country.
     * @return createDate
     */
    public LocalDateTime getCreateDate() {return createDate;}

    /**
     * This method sets the create date for the Country.
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {this.createDate = createDate;}

    /**
     * This method gets the created by value for the Country.
     * @return createdBy
     */
    public String getCreatedBy() {return createdBy;}

    /**
     * This method sets the created by value for the Country.
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    /**
     * This method gets the last updated value for the Country.
     * @return lastUpdated
     */
    public Timestamp getLastUpdated() {return lastUpdated;}

    /**
     * This method sets the last updated value for the Country.
     * @param lastUpdated
     */
    public void setLastUpdated(Timestamp lastUpdated) {this.lastUpdated = lastUpdated;}

    /**
     * This method gets the last updated by value for the Country.
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {return lastUpdatedBy;}

    /**
     * This method sets the last updated by value for the Country.
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {this.lastUpdatedBy = lastUpdatedBy;}
}

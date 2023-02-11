package schrader.schedulingapp.model;

/**
 * Country.java class
 */

import java.security.Timestamp;
import java.time.LocalDateTime;

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


    /**
     *
     * @param countryId
     * @param country
     * @param createDate
     * @param createdBy
     * @param lastUpdated
     * @param lastUpdatedBy
     */
    public Country(int countryId, String country, LocalDateTime createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return
     */
    public int getCountryId() {return countryId;}

    /**
     *
     * @param country
     */
    public void setCountryId(int countryId) {this.countryId = countryId;}

    /**
     *
     * @return
     */
    public String getCountry() {return country;}

    /**
     *
     * @param country
     */
    public void setCountry(String country) {this.country = country;}

    /**
     *
     * @return
     */
    public LocalDateTime getCreateDate() {return createDate;}

    /**
     *
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {this.createDate = createDate;}

    /**
     *
     * @return
     */
    public String getCreatedBy() {return createdBy;}

    /**
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    /**
     *
     * @return
     */
    public Timestamp getLastUpdated() {return lastUpdated;}

    /**
     *
     * @param lastUpdated
     */
    public void setLastUpdated(Timestamp lastUpdated) {this.lastUpdated = lastUpdated;}

    /**
     *
     * @return
     */
    public String getLastUpdatedBy() {return lastUpdatedBy;}

    /**
     *
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {this.lastUpdatedBy = lastUpdatedBy;}
}

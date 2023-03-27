package schrader.schedulingapp.model;

import java.security.Timestamp;
import java.time.LocalDateTime;

/**
 * Division.java class
 */

/**
 * @author Karoline Schrader
 */
public class Division {
    int divisionId;
    String division;
    LocalDateTime createDate;
    String createdBy;
    Timestamp lastUpdated;
    String lastUpdatedBy;
    int countryId;

    public Division(int divisionId, String division, LocalDateTime createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    /**
     * This method gets the divisionId for the Division.
     * @return divisionId
     */
    public int getDivisionId() {return divisionId;}

    /**
     * This method sets the divisionId for the Division.
     * @param divisionId
     */
   public void setDivisionId(int divisionId) {this.divisionId = divisionId;}

    /**
     * This method gets the division name for the Division.
     * @return division
     */
    public String getDivision() {return division;}

    /**
     * This method sets the division name for the Division.
     * @param division
     */
    public void setDivision(String division) {this.division = division;}

    /**
     * This method gets the create date for the Division.
     * @return createDate
     */
    public LocalDateTime getCreateDate() {return createDate;}

    /**
     * This method sets the create date for the Division.
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {this.createDate = createDate;}

    /**
     * This method gets the created by value for the Division.
     * @return createdBy
     */
    public String getCreatedBy() {return createdBy;}

    /**
     * This method sets the created by for the Division.
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    /**
     * This method gets the last updated value for the Division.
     * @return lastUpdated
     */
    public Timestamp getLastUpdated() {return lastUpdated;}

    /**
     * This method sets the last updated value for the Division.
     * @param lastUpdated
     */
    public void setLastUpdated(Timestamp lastUpdated) {this.lastUpdated = lastUpdated;}

    /**
     * This method gets the last updated by value for the Division.
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {return lastUpdatedBy;}

    /**
     * This method sets the last updated by value for the Division.
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {this.lastUpdatedBy = lastUpdatedBy;}

    /**
     * This method gets the countryId for the Division.
     * @return countryId
     */
    public int getCountryId() {return countryId;}

    /**
     * This method sets the countryId for the Division.
     */
    public void setCountryId() {this.countryId = countryId;}
}

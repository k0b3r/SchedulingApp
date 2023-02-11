package schrader.schedulingapp.model;

/**
 * Division.java class
 */

import java.security.Timestamp;
import java.time.LocalDateTime;

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

    /**
     *
     * @param divisionId
     * @param division
     * @param createDate
     * @param createdBy
     * @param lastUpdated
     * @param lastUpdatedBy
     * @param countryId
     */
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
     *
     * @return
     */
    public int getDivisionId() {return divisionId;}

    /**
     *
     * @param divisionId
     */
   public void setDivisionId(int divisionId) {this.divisionId = divisionId;}

    /**
     *
     * @return
     */
    public String getDivision() {return division;}

    /**
     *
     * @param division
     */
    public void setDivision(String division) {this.division = division;}

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

    /**
     *
     * @return
     */
    public int getCountryId() {return countryId;}

    /**
     *
     */
    public void setCountryId() {this.countryId = countryId;}
}

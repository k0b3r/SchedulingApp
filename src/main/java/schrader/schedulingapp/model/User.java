package schrader.schedulingapp.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * User.java class
 */

/**
 * @author Karoline Schrader
 */


public class User {
    public int userId;
    public String username;
    public String password;
    public LocalDateTime createDate;
    public String createdBy;
    public Timestamp lastUpdated;
    public String lastUpdatedBy;

    public User(int userId, String username, String password, LocalDateTime createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;

    }

    /**
     * This method gets the userId for the User.
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This method sets the userId for the User.
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * This method gets the username for the User.
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method sets the username for the User.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method gets the password for the User.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method sets the password for the user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method gets the create date for the User.
     * @return createDate
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method sets the create date for the User.
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method gets the created by value for the User.
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method sets the created by value for the User.
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method gets the last updated value for the User.
     * @return lastUpdated
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**
     * This method sets the last updated value for the User.
     * @param lastUpdated
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * This method gets the last updated by value for the User.
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method sets the last updated by value for the User.
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}

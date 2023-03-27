package schrader.schedulingapp.model;
/**
 * Appointment.java class
 */

/**
 *
 * @author Karoline Schrader
 */

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;

    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime startDate, LocalDateTime endDate, Timestamp createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime startDate, LocalDateTime endDate, Timestamp createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int customerId, int userId, String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactName = contactName;
    }

    /**
     * This method gets the appointmentId from the Appointment.
     * @return appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * This method sets the appointmentId value for the Appointment.
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * This method gets the title for the Appointment.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method sets the title for the Appointment.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method gets the description for the Appointment.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method sets the description for the Appointment.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method gets the location for the Appointment.
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method sets the location for the Appointment.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This method gets the type for the Appointment.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * This method sets the type for the Appointment.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method gets the start date for the Appointment.
     * @return startDate
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * This method gets the start date for the Appointment.
     * @param startDate
     */
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate;}

    /**
     * This method gets the end date for the Appointment.
     * @return endDate
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * This method sets the end date for the Appointment.
     * @param endDate
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * This method gets the create date for the Appointment.
     * @return createDate
     */
    public Timestamp getCreateDate() {return createDate;}

    /**
     * This method sets the create date for the Appointment.
     * @param createDate
     */
    public void setCreateDate(Timestamp createDate) {this.createDate = createDate;}

    /**
     * This method gets the created by value for the Appointment.
     * @return createdBy
     */
    public String getCreatedBy() {return createdBy;}

    /**
     * This method sets the created by value for the Appointment.
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    /**
     * This method gets the last updated value for the Appointment.
     * @return lastUpdated
     */
    public Timestamp getLastUpdated() {return lastUpdated;}

    /**
     * This method sets the last updated value for the Appointment.
     * @param lastUpdated
     */
    public void setLastUpdated(Timestamp lastUpdated) {this.lastUpdated = lastUpdated;}

    /**
     * This method gets the last updated by value for the Appointment.
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {return lastUpdatedBy;}

    /**
     * This method sets the last updated by value for the Appointment.
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {this.lastUpdatedBy = lastUpdatedBy;}

    /**
     * This method gets the customerId for the Appointment.
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * This method sets the customerId for the Appointment.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * This method gets the userId for the Appointment.
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This method sets the userId for the Appointment.
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * This method gets the contactId for the Appointment.
     * @return contactId
     */
    public int getContactId() {return contactId;}

    /**
     * This method sets the contactId for the Appointment.
     * @param contactId
     */
    public void setContactId(int contactId) {this.contactId = contactId;}

    /**
     * This method gets the contactName for the Appointment.
     * @return contactName
     */
    public String getContactName() {return contactName;}

    /**
     * This method sets the contactName for the Appointment.
     * @param contactName
     */
    public void setContactName(String contactName) {this.contactName = contactName;}
}

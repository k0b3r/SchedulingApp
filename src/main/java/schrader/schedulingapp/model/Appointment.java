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

    /**
     *
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDate
     * @param endDate
     * @param createDate
     * @param createdBy
     * @param lastUpdated
     * @param lastUpdatedBy
     * @param customerId
     * @param userId
     * @param contactId
     */

    // TODO - Not sure if the appointment times should be in timestamp or localdatetime ?
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
     *
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     *
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     *
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     */
    public String getType() {
        return type;
    }

    /**
     *
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     *
     */
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate;}

    /**
     *
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     *
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     */
    public Timestamp getCreateDate() {return createDate;}

    /**
     *
     * @param createDate
     */
    public void setCreateDate(Timestamp createDate) {this.createDate = createDate;}

    /**
     *
     * @return
     */
    public String getCreatedBy() {return createdBy;}

    /**
     *
     */
    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    /**
     *
     */
    public Timestamp getLastUpdated() {return lastUpdated;}

    /**
     *
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
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public int getContactId() {return contactId;}

    /**
     *
     * @param contactId
     */
    public void setContactId(int contactId) {this.contactId = contactId;}

    /**
     *
     * @return
     */
    public String getContactName() {return contactName;}

    /**
     *
     * @param contactName
     */
    public void setContactName(String contactName) {this.contactName = contactName;}
}

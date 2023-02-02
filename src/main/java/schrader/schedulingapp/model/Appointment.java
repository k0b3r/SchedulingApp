package schrader.schedulingapp.model;
/**
 * Supplied class Part.java
 */

/**
 *
 * @author Place Your Name Here
 */
import java.time.LocalTime;
import java.util.Date;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private Date startDate;
    private long startTime;
    private Date endDate;
    private long endTime;
    private int customerId;
    private int userId;

    public Appointment(int id, String title, String description, String location, String contact, String type, Date startDate, long startTime, Date endDate, long endTime, int customerId, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
    }

    /**
     *
     */
    public int getId() {
        return id;
    }

    /**
     *
     */
    public void setId(int id) {
        this.id = id;
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
    public String getContact() {
        return location;
    }

    /**
     *
     */
    public void setContact(String contact) {
        this.contact = contact;
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
    public Date getStartDate() {
        return startDate;
    }

    /**
     *
     */
    public void setStartDate(Date startDate) {
        this.startTime = startTime;
    }

    /**
     *
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     *
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     *
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     *
     */
    public void setEndDate(Date endDate) {
        this.endTime = endTime;
    }

    /**
     *
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     *
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

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
}

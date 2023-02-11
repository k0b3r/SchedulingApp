package schrader.schedulingapp.model;

/**
 * Customer.java class
 */

import java.security.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Karoline Schrader
 */

public class Customer {
    private int customerId;
    private String customerName;
    private String country;
    private String address;
    private String postalCode;
    private String state;
    private String phoneNumber;
    private LocalDateTime createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;


    /**
     *
     * @param customerId
     * @param customerName
     * @param country
     * @param address
     * @param postalCode
     * @param state
     * @param phoneNumber
     * @param createdDate
     * @param createdBy
     * @param lastUpdated
     * @param lastUpdatedBy
     */
    public Customer(int customerId, String customerName, String country, String address, String postalCode, String state, String phoneNumber, LocalDateTime createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.country = country;
        this.address = address;
        this.postalCode = postalCode;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     *
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     */
    public String getState() {
        return state;
    }

    /**
     *
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public String getPhoneNumber() {return phoneNumber;}

    /**
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    /**
     *
     * @return
     */
    public LocalDateTime getCreatedDate() {return createdDate;}

    /**
     *
     * @param createdDate
     */
    public void setCreatedDate(LocalDateTime createdDate) {this.createdDate = createdDate;}

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

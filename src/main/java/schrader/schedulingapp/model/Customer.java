package schrader.schedulingapp.model;

import java.time.LocalDateTime;

/**
 * Customer.java class
 */

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
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;
    private Integer divisionId;

    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy, Integer divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    /**
     * This method gets the customerId for the Customer.
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * This method sets the customerId for the Customer.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * This method gets the name for the Customer.
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * This method sets the name for the Customer.
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * This method gets the country for the Customer.
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method sets the country for the Customer.
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * This method gets the address for the Customer.
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method sets the address for the Customer.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method gets the postal code for the Customer.
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * This method sets the postal code for the Customer.
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * This method gets the state for the Customer.
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * This method sets the state for the Customer.
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * This method gets the phone number for the Customer.
     * @return phoneNumber
     */
    public String getPhoneNumber() {return phoneNumber;}

    /**
     * This method sets the phone number for the Customer.
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    /**
     * This method gets the create date value for the Customer.
     * @return createdDate
     */
    public LocalDateTime getCreatedDate() {return createdDate;}

    /**
     * This method sets the create date value for the Customer.
     * @param createdDate
     */
    public void setCreatedDate(LocalDateTime createdDate) {this.createdDate = createdDate;}

    /**
     * This method gets the created by value for the Customer.
     * @return createdBy
     */
    public String getCreatedBy() {return createdBy;}

    /**
     * This method sets the created by value for the Customer.
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    /**
     * This method gets the last updated value for the Customer.
     * @return lastUpdated
     */
    public LocalDateTime getLastUpdated() {return lastUpdated;}

    /**
     * This method sets the last updated value for the Customer.
     * @param lastUpdated
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {this.lastUpdated = lastUpdated;}

    /**
     * This method gets the last updated by value for the Customer.
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {return lastUpdatedBy;}

    /**
     * This method gets the last updated by for the Customer.
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {this.lastUpdatedBy = lastUpdatedBy;}

    /**
     * This method gets the divisionId for the Customer.
     * @return divisionId
     */
    public Integer getDivisionId() {return divisionId;}

    /**
     * This method sets the divisionId for the Customer.
     * @param divisionId
     */
    public void setDivisionId(Integer divisionId) {this.divisionId = divisionId;}
}

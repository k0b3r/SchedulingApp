package schrader.schedulingapp.model;

/**
 * Supplied class Part.java
 */

/**
 *
 * @author Place Your Name Here
 */

public class Customer {
    private int id;
    private String name;
    private String phoneNumber;
    private String country;
    private String streetAddress1;
    // TODO build uml and remove uneccsary lines
    private String streetAddress2;
    private String postalCode;
    private String state;
    private String city;

    // TODO define values to pass in
    public Customer(int id, String name, String country, String streetAddress1, String streetAddress2, String postalCode, String state, String city) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.postalCode = postalCode;
        this.state = state;
        this.city = city;
    }

    /**
     *
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
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
    public String getStreetAddress1() {
        return streetAddress1;
    }

    /**
     *
     */
    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    /**
     *
     * @return
     */
    public String getStreetAddress2() {
        return streetAddress2;
    }

    /**
     *
     */
    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
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
    public String getCity() {
        return city;
    }

    /**
     *
     */
    public void setCity(String city) {
        this.city = city;
    }

}

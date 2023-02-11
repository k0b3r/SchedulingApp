package schrader.schedulingapp.model;

/**
 * Contact.java class
 */

/**
 * @author Karoline Schrader
 */
public class Contact {
    private int contactId;
    private String name;
    private String email;

    /**
     *
     * @param contactId
     * @param name
     * @param email
     */
    public Contact(int contactId, String name, String email) {
        this.contactId = contactId;
        this.name = name;
        this.email = email;
    }

    /**
     *
     * @return
     */
    public int getContactId() {return contactId;}

    /**
     *
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     *
     * @return
     */
    public String getName() {return name;}

    /**
     *
     * @param name
     */
    public void setName(String name) {this.name = name;}

    /**
     *
     * @return
     */
    public String getEmail() {return email;}

    /**
     *
     * @param email
     */
    public void setEmail(String email) {this.email = email;}
}

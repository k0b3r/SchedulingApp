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

    public Contact(int contactId, String name, String email) {
        this.contactId = contactId;
        this.name = name;
        this.email = email;
    }

    /**
     * This method gets the contactId for the Contact.
     * @return contactId
     */
    public int getContactId() {return contactId;}

    /**
     * This method sets the contactId for the Contact.
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * This method gets the name for the Contact.
     * @return name
     */
    public String getName() {return name;}

    /**
     * This method sets the name for the Contact.
     * @param name
     */
    public void setName(String name) {this.name = name;}

    /**
     * This method gets the email for the Contact.
     * @return email
     */
    public String getEmail() {return email;}

    /**
     * This method sets the email for the Contact.
     * @param email
     */
    public void setEmail(String email) {this.email = email;}
}

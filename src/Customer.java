/* Student Number: 25882262 */

/**
 * Represents a Customer (Student) within the Smart Path Tutoring System.*/
public class Customer {

     private String customerId; 
     private String firstName;
     private String lastName;
     private String email;
     private String phoneNumber;

    /** Static counter used for auto-generating unique customer IDs. */
    private static int nextId = 1001;

    /**
     * Constructor for creating a customer with a specific ID.
      * @param customerId The unique ID.
     * @param firstName The first name.
     * @param lastName The last name.
     * @param email The email address.
     * @param phoneNumber The phone number.
     */
    public Customer(String customerId, String firstName, String lastName, String email, String phoneNumber) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

  
    public Customer(String firstName, String lastName, String email, String phoneNumber) {
        this("C" + nextId++, firstName, lastName, email, phoneNumber);
    }

    /** @return The unique customer ID. */
    public String getCustomerId() { return customerId; }

    /** @return The customer's first name. */
    public String getFirstName() { return firstName; }

    /** @return The customer's last name. */
    public String getLastName() { return lastName; }

    /** @return The customer's email address. */
    public String getEmail() { return email; }

    /** @return The customer's phone number. */
    public String getPhoneNumber() { return phoneNumber; }

    /** @param firstName Updates the customer's first name. */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /** @param lastName Updates the customer's last name. */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /** @param email Updates the customer's email address. */
    public void setEmail(String email) { this.email = email; }

    /** @param phoneNumber Updates the customer's phone number. */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /**
     * @return A formatted summary of customer details.
     */
    @Override
    public String toString() {
        return "Customer ID: " + customerId +
                " | Name: " + firstName + " " + lastName +
                " | Email: " + email +
                " | Phone: " + phoneNumber;
    }
}
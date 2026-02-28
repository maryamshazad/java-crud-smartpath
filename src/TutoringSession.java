 /* Student Number: 25882262 */

/*Represents a specific tutoring session in the system.*/

public class TutoringSession {

     private String sessionId;
     private String subject;
     private String tutorName;
     private double price;
     private int stockLevel;
     private String dateTime;

    /**
     * Constructs a new TutoringSession with the specified details.
     * @param sessionId the unique session ID 
     * @param subject the topic of the session
     * @param tutorName the name of the tutor
     * @param price the cost of the session
     * @param stockLevel the initial number of available slots 
     * @param dateTime the date and time of the session
     */
    public TutoringSession(String sessionId, String subject, String tutorName, double price, int stockLevel, String dateTime) {
        this.sessionId = sessionId;
        this.subject = subject;
        this.tutorName = tutorName;
        this.price = price;
        this.stockLevel = stockLevel;
        this.dateTime = dateTime;
    }

    /** @return the unique session identifier. */
    public String getSessionId() {
        return sessionId;
    }

    /** @return the subject of the session. */
    public String getSubject() {
        return subject;
    }

    /** @return the name of the assigned tutor. */
    public String getTutorName() {
        return tutorName;
    }

    /** @return the price of the session. */
    public double getPrice() {
        return price;
    }

    /** @return the current number of available slots. */
    public int getStockLevel() {
        return stockLevel;
    }

    /** @return the scheduled date and time. */
    public String getDateTime() {
        return dateTime;
    }

    /** @param subject updates the session subject. */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /** @param tutorName updates the tutor's name. */
    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    /** @param price updates the session price. */
    public void setPrice(double price) {
        this.price = price;
    }

    /** @param stockLevel updates the number of available slots. */
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    /** @param dateTime updates the scheduled date and time. */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Returns a formatted string representation of the tutoring session details.
     * @return a summary of the session attributes.
     */
    @Override
    public String toString() {
        return "Session ID: " + sessionId +
                " | Subject: " + subject +
                " | Tutor: " + tutorName +
                " | Price: £" + String.format("%.2f", price) + 
                " | Available Slots: " + stockLevel +
                " | Date/Time: " + dateTime;
    }
}
/* Student Number: 25882262 */

/**
 * This class is for tutoring sessions held online.
 * It builds on the main TutoringSession class but adds details 
 * about which video software is being used.
 */
public class OnlineSession extends TutoringSession {
    
    // The name of the software used, like Zoom or Teams
    private String platform; 

    /**
     * Sets up a new online session.
     * It sends the main details (like ID and price) to the parent class 
     * and saves the platform name here.
     */
    public OnlineSession(String sessionId, String subject, String tutorName, double price, int stockLevel, String dateTime, String platform) {
        super(sessionId, subject, tutorName, price, stockLevel, dateTime);
        this.platform = platform;
    }

    /**
     * Returns the platform name.
     */
    public String getPlatform() { 
        return platform; 
    }
    
    /**
     * Updates the platform name.
     */
    public void setPlatform(String platform) { 
        this.platform = platform; 
    }

    /**
     * Returns a text description of the session.
     * It takes the standard session info and adds the online platform at the end.
     */
    @Override
    public String toString() {
        return super.toString() + " | Type: Online (" + platform + ")";
    }
}
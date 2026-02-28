/* Student Number: 25882262 */

/**
 * This class is for tutoring sessions that happen inperson.
 * It extends the main TutoringSession class by adding a room location.
 */
public class InPersonSession extends TutoringSession {
    
    // The specific room or building for the lesson
    private String roomLocation; 

    /**
     * Sets up a new in-person session.
     * It uses the parent constructor for the main details and saves 
     * the room location here.
     */
    public InPersonSession(String sessionId, String subject, String tutorName, double price, int stockLevel, String dateTime, String roomLocation) {
        super(sessionId, subject, tutorName, price, stockLevel, dateTime);
        this.roomLocation = roomLocation;
    }

    /**
     * Gets the room location.
     */
    public String getRoomLocation() { 
        return roomLocation; 
    }
    
    /**
     * Updates the room location.
     */
    public void setRoomLocation(String roomLocation) { 
        this.roomLocation = roomLocation; 
    }

    /**
     * Returns a text description of the session.
     * It shows the standard session info plus the physical location.
     */
    @Override
    public String toString() {
        return super.toString() + " | Type: In-Person (Location: " + roomLocation + ")";
    }
}
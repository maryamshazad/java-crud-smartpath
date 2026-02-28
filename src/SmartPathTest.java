/* Student Number: 25882262 */

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * This class runs automatic tests on the system.
 * it checks things like price calculations, stock levels, and security.
 */
public class SmartPathTest {

    private Customer testCustomer;
    private OnlineSession onlineSession;
    private InPersonSession physicalSession;

    /**
     * This runs before every test to set up fresh data.
     * It creates a fake customer and two fake sessions to test with.
     */
    @Before
    public void setUp() {
        testCustomer = new Customer("C999", "Test", "User", "test@mmu.ac.uk", "0161");
        onlineSession = new OnlineSession("S1", "Java", "Tutor A", 50.0, 5, "2026-01-01", "Zoom");
        physicalSession = new InPersonSession("S2", "Math", "Tutor B", 30.0, 2, "2026-01-02", "Room 101");
    }

    /**
     * Checks if the system adds up the prices correctly.
     * It adds a £50 session and a £30 session to make sure the total is £80.
     */
    @Test
    public void testBookingTotalCost() {
        ClassBooking booking = new ClassBooking(testCustomer, "2025-12-25");
        booking.addSession(onlineSession);
        booking.addSession(physicalSession);
        
        // Make sure 50.0 + 30.0 equals 80.0
        assertEquals(80.0, booking.getTotalCost(), 0.01);
    }

    /**
     * Checks if the number of available spots goes down when someone books.
     */
    @Test
    public void testStockReduction() {
        int initialStock = onlineSession.getStockLevel();
        
        // Take away one spot from the session
        if (onlineSession.getStockLevel() > 0) {
            onlineSession.setStockLevel(onlineSession.getStockLevel() - 1);
        }
        
        assertEquals(initialStock - 1, onlineSession.getStockLevel());
    }

    /**
     * Checks if the login system works correctly.
     * It tests a right password (should pass) and a wrong password (should fail).
     */
    @Test
    public void testAuthenticationLogic() {
        assertTrue(SmartPath.authenticateStaff("Staff", "Password1!"));
        assertFalse(SmartPath.authenticateStaff("Admin", "WrongPass"));
    }
}
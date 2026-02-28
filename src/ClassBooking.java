/* Student Number: 25882262 */

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a tutoring booking.
 * This class links multiple TutoringSession objects for a single Customer,
 * satisfying the requirement to store details about an order consisting of 
 * many products/services.
 */
public class ClassBooking {

     private String bookingId; 
     private Customer customer; 
     private List<TutoringSession> bookedSessions; 
     private String bookingDate; 
     private double totalCost;

    /** Static counter to ensure unique auto-generated booking IDs. */
    private static int nextBookingId = 5001;

    /**
     * Constructor for creating new bookings with an auto-generated ID.
     * @param customer The Customer placing the order.
     * @param bookingDate The date of the transaction.
     */
    public ClassBooking(Customer customer, String bookingDate) {
        this.bookingId = "BKG" + nextBookingId++; 
        this.customer = customer;
        this.bookedSessions = new ArrayList<>();
        this.bookingDate = bookingDate;
        this.totalCost = 0.0;
    }

    /**
     * Constructor used when loading existing records from persistent storage.
     * @param bookingId The existing ID from the file.
     * @param customer The Customer associated with the record.
     * @param bookingDate The original date of the booking.
     */
    public ClassBooking(String bookingId, Customer customer, String bookingDate) {
        this.bookingId = bookingId; 
        this.customer = customer;
        this.bookedSessions = new ArrayList<>();
        this.bookingDate = bookingDate;
        this.totalCost = 0.0;
    }

    /**
     * Adds a tutoring session to the current booking and triggers a cost recalculation.
     * @param session The TutoringSession (product) to be added to the order.
     */
    public void addSession(TutoringSession session) {
        if (session != null) {
            this.bookedSessions.add(session);
            calculateTotalCost(); 
        }
    }

    /**
     * Logic for calculating the total cost of the order.
     * Sums the individual prices of all TutoringSession objects in the list.
     */
    public void calculateTotalCost() {
        double currentTotal = 0.0;
        for (TutoringSession session : bookedSessions) {
            currentTotal += session.getPrice();
        }
        this.totalCost = currentTotal;
    }

    /** @return The unique booking ID. */
    public String getBookingId() { return bookingId; }

    /** @return The Customer object assigned to this booking. */
    public Customer getCustomer() { return customer; }

    /** @return The list of sessions (products) in this order. */
    public List<TutoringSession> getBookedSessions() { return bookedSessions; }

    /** @return The booking date string. */
    public String getBookingDate() { return bookingDate; }

    /** @return The total cost of the booking in GBP. */
    public double getTotalCost() { return totalCost; }

    /** @param bookingDate Sets a new date for the booking. */
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    /** @param customer Assigns a different customer to the booking. */
    public void setCustomer(Customer customer) { this.customer = customer; }

    /** * Updates the sessions in the booking and recalculates the total cost.
     * @param bookedSessions The new list of sessions.
     */
    public void setBookedSessions(List<TutoringSession> bookedSessions) {
        this.bookedSessions = bookedSessions;
        calculateTotalCost(); 
    }

    /**
     * Returns a string representation of the booking for list views.
     * @return A formatted summary of the order.
     */
    @Override
    public String toString() {
        String customerName = this.customer != null ? this.customer.getFirstName() + " " + this.customer.getLastName() : "N/A";
        int itemCount = bookedSessions.size();

        return String.format("Booking ID: %s | Customer: %s (%s) | Date: %s | Sessions: %d | Total Cost: £%.2f",
            bookingId, customerName, customer.getCustomerId(), bookingDate, itemCount, totalCost);
    }
}
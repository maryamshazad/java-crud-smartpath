/* Student Number: 25882262 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

/**
 * The main logic for the tutoring system. 
 * It stores and manages sessions, customers, and bookings.
 */
public class SmartPath {
    // Lists to hold all our data while the program is open
    private static ArrayList<TutoringSession> sessionList = new ArrayList<>();
    private static ArrayList<Customer> customerList = new ArrayList<>();
    private static ArrayList<ClassBooking> bookingList = new ArrayList<>();

    /**
     * Runs the program. Loads data from files and shows the menu.
     */
    public static void main(String[] args) {
        // Load saved data when the app starts
        loadDataFromFiles(); 
        
        System.out.println("Welcome to Smart Path Tutoring System");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add Session");
            System.out.println("2. Edit Session");
            System.out.println("3. Delete Session");
            System.out.println("4. Search Sessions");
            System.out.println("5. List All Sessions");
            System.out.println("6. Add Customer");
            System.out.println("7. Edit Customer");
            System.out.println("8. Delete Customer");
            System.out.println("9. List All Customers");
            System.out.println("10. Add New Booking");
            System.out.println("11. Edit Booking");
            System.out.println("12. Delete Booking");
            System.out.println("13. List All Bookings");
            System.out.println("14. Save Data to Files");
            System.out.println("15. Exit");
            System.out.print("Enter choice: ");

            // Make sure the user enters a number
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the scanner buffer

            switch(choice) {
                case 15:
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                case 14:
                    System.out.println(saveDataToFiles());
                    break;
                
                default:
                    System.out.println("CLI Menu option selected. Redirecting to system logic...");
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Checks if staff login details are correct.
     */
    public static boolean authenticateStaff(String username, String password) {
        return username.equals("Staff") && password.equals("Password1!");
    }

    /**
     * Formats session data so it can be shown in the table.
     */
    public static Object[][] getSessionDataForTable() {
        Object[][] data = new Object[sessionList.size()][6];
        for (int i = 0; i < sessionList.size(); i++) {
            TutoringSession s = sessionList.get(i);
            data[i][0] = s.getSessionId();
            data[i][1] = s.getSubject();
            data[i][2] = s.getTutorName();
            data[i][3] = String.format("£%.2f", s.getPrice());
            data[i][4] = s.getStockLevel();
            data[i][5] = s.getDateTime();
        }
        return data;
    }

    /**
     * Formats customer data for the table.
     */
    public static Object[][] getCustomerDataForTable() {
        Object[][] data = new Object[customerList.size()][5];
        for (int i = 0; i < customerList.size(); i++) {
            Customer c = customerList.get(i);
            data[i][0] = c.getCustomerId();
            data[i][1] = c.getFirstName();
            data[i][2] = c.getLastName();
            data[i][3] = c.getEmail();
            data[i][4] = c.getPhoneNumber();
        }
        return data;
    }

    /**
     * Formats booking data for the table.
     */
    public static Object[][] getBookingDataForTable() {
        Object[][] data = new Object[bookingList.size()][5];
        for (int i = 0; i < bookingList.size(); i++) {
            ClassBooking b = bookingList.get(i);
            data[i][0] = b.getBookingId();
            data[i][1] = b.getCustomer().getCustomerId();
            data[i][2] = b.getCustomer().getFirstName() + " " + b.getCustomer().getLastName();
            data[i][3] = b.getBookingDate();
            data[i][4] = String.format("£%.2f", b.getTotalCost());
        }
        return data;
    }

    /**
     * Adds a session as either Online or In-Person.
     */
    public static String addNewSubclassedSessionGUI(String type, String id, String sub, String tutor, double price, int stock, String dt, String extra) {
        if (findSessionById(id) != null) {
            return "Error: Duplicate Session ID found.";
        }
        
        TutoringSession newSession;
        if (type.equalsIgnoreCase("Online")) {
            newSession = new OnlineSession(id, sub, tutor, price, stock, dt, extra);
        } else {
            newSession = new InPersonSession(id, sub, tutor, price, stock, dt, extra);
        }
        
        sessionList.add(newSession);
        return "SUCCESS: " + type + " session added to the system.";
    }

    /**
     * Removes a session by its ID.
     */
    public static String deleteSessionGUI(String id) {
        TutoringSession s = findSessionById(id);
        if (s != null) {
            sessionList.remove(s);
            return "SUCCESS: Session " + id + " has been removed.";
        }
        return "ERROR: Session ID not found.";
    }

    /**
     * Adds a new customer to the list.
     */
    public static String addNewCustomerGUI(String id, String f, String l, String e, String p) {
        if (findCustomerById(id) != null) {
            return "ERROR: Customer ID already exists.";
        }
        customerList.add(new Customer(id, f, l, e, p));
        return "SUCCESS: Customer added.";
    }

    /**
     * Deletes a customer by their ID.
     */
    public static String deleteCustomerGUI(String id) {
        Customer c = findCustomerById(id);
        if (c != null) {
            customerList.remove(c);
            return "SUCCESS: Customer " + id + " removed.";
        }
        return "ERROR: Customer not found.";
    }

    /**
     * Updates an existing customer's information.
     */
    public static String editCustomerGUI(String id, int fieldIndex, String value) {
        Customer c = findCustomerById(id);
        if (c != null) {
            if (fieldIndex == 1) c.setFirstName(value);
            else if (fieldIndex == 2) c.setLastName(value);
            else if (fieldIndex == 3) c.setEmail(value);
            else if (fieldIndex == 4) c.setPhoneNumber(value);
            return "SUCCESS: Customer details updated.";
        }
        return "ERROR: Update failed.";
    }

    /**
     * Changes the price or stock of a session.
     */
    public static String editSession(String id, int field, double value) {
        TutoringSession s = findSessionById(id);
        if (s != null) {
            if (field == 1) s.setPrice(value);
            else s.setStockLevel((int)value);
            return "SUCCESS: Session updated.";
        }
        return "ERROR: Session not found.";
    }

    /**
     * Makes a new booking and reduces the availablity.
     */
    public static ClassBooking addNewBooking(String cid, String date, List<String> sids) {
        Customer c = findCustomerById(cid);
        if (c == null) return null;
        
        ClassBooking b = new ClassBooking(c, date);
        for (String infoLine : sids) {
            String id = infoLine.split(" ")[0].trim();
            TutoringSession s = findSessionById(id);
            
            if (s != null && s.getStockLevel() > 0) {
                b.addSession(s);
                s.setStockLevel(s.getStockLevel() - 1);
            }
        }
        bookingList.add(b);
        return b;
    }

    /**
     * Updates a booking and adjusts the session stock levels.
     */
    public static String editBookingSessionsAndCustomer(String bid, String cid, List<String> sids) {
        ClassBooking b = findBookingById(bid);
        Customer c = findCustomerById(cid);
        
        if (b != null && c != null) {
            // Put back stock for the previous sessions
            for (TutoringSession oldS : b.getBookedSessions()) {
                oldS.setStockLevel(oldS.getStockLevel() + 1);
            }
            
            b.setCustomer(c);
            b.getBookedSessions().clear();
            
            // Take stock for the new sessions
            for (String sidLine : sids) {
                String id = sidLine.split(" ")[0].trim();
                TutoringSession newS = findSessionById(id);
                if (newS != null && newS.getStockLevel() > 0) {
                    b.addSession(newS);
                    newS.setStockLevel(newS.getStockLevel() - 1);
                }
            }
            return "SUCCESS: Booking " + bid + " updated.";
        }
        return "ERROR: Could not find booking or customer.";
    }

    /**
     * Deletes a booking and restores the session availability.
     */
    public static String deleteBooking(String id) {
        ClassBooking b = findBookingById(id);
        if (b != null) {
            for (TutoringSession s : b.getBookedSessions()) {
                s.setStockLevel(s.getStockLevel() + 1);
            }
            bookingList.remove(b);
            return "SUCCESS: Booking deleted and stock levels restored.";
        }
        return "ERROR: Booking not found.";
    }

    /**
     * Searches for sessions by subject or tutor.
     */
    public static ArrayList<TutoringSession> searchSessions(String k) {
        ArrayList<TutoringSession> results = new ArrayList<>();
        for (TutoringSession s : sessionList) {
            if (s.getSubject().toLowerCase().contains(k.toLowerCase()) || 
                s.getTutorName().toLowerCase().contains(k.toLowerCase())) {
                results.add(s);
            }
        }
        return results; 
    }

    /**
     * Saves sessions and customers to CSV files.
     */
    public static String saveDataToFiles() {
        try {
            PrintWriter sessionWriter = new PrintWriter(new FileWriter("sessions.csv"));
            for (TutoringSession s : sessionList) {
                String type = (s instanceof OnlineSession) ? "Online" : "InPerson";
                String extra = (s instanceof OnlineSession) ? 
                               ((OnlineSession)s).getPlatform() : ((InPersonSession)s).getRoomLocation();
                
                sessionWriter.println(type + "," + s.getSessionId() + "," + s.getSubject() + "," + 
                                     s.getTutorName() + "," + s.getPrice() + "," + 
                                     s.getStockLevel() + "," + s.getDateTime() + "," + extra);
            }
            sessionWriter.close();

            PrintWriter customerWriter = new PrintWriter(new FileWriter("customers.csv"));
            for (Customer c : customerList) {
                customerWriter.println(c.getCustomerId() + "," + c.getFirstName() + "," + 
                                      c.getLastName() + "," + c.getEmail() + "," + c.getPhoneNumber());
            }
            customerWriter.close();

            return "SUCCESS: Data saved to sessions.csv and customers.csv";
        } catch (IOException e) {
            return "ERROR: Could not save data. " + e.getMessage();
        }
    }

    /**
     * Reads the data back from the CSV files.
     */
    public static void loadDataFromFiles() {
        try {
            File sFile = new File("sessions.csv");
            if (sFile.exists()) {
                Scanner sReader = new Scanner(sFile);
                while (sReader.hasNextLine()) {
                    String line = sReader.nextLine();
                    String[] data = line.split(",");
                    if (data.length >= 8) {
                        try {
                            String type = data[0];
                            String id = data[1];
                            String sub = data[2];
                            String tutor = data[3];
                            double price = Double.parseDouble(data[4]);
                            int stock = Integer.parseInt(data[5]);
                            String dt = data[6];
                            String extra = data[7];
                            addNewSubclassedSessionGUI(type, id, sub, tutor, price, stock, dt, extra);
                        } catch (NumberFormatException e) {
                            System.out.println("Skipping bad row: " + line);
                        }
                    }
                }
                sReader.close();
            }

            File cFile = new File("customers.csv");
            if (cFile.exists()) {
                Scanner cReader = new Scanner(cFile);
                while (cReader.hasNextLine()) {
                    String line = cReader.nextLine();
                    String[] data = line.split(",");
                    if (data.length >= 5) {
                        customerList.add(new Customer(data[0], data[1], data[2], data[3], data[4]));
                    }
                }
                cReader.close();
            }
        } catch (Exception e) {
            System.out.println("Error loading files: " + e.getMessage());
        }
    }

    /**
     * Looks up a session by ID.
     */
    public static TutoringSession findSessionById(String id) {
        for (TutoringSession s : sessionList) {
            if (s.getSessionId().equalsIgnoreCase(id.trim())) return s;
        }
        return null;
    }

    /**
     * Looks up a customer by ID.
     */
    public static Customer findCustomerById(String id) {
        for (Customer c : customerList) {
            if (c.getCustomerId().equalsIgnoreCase(id.trim())) return c;
        }
        return null;
    }

    /**
     * Looks up a booking by ID.
     */
    public static ClassBooking findBookingById(String id) {
        for (ClassBooking b : bookingList) {
            if (b.getBookingId().equalsIgnoreCase(id.trim())) return b;
        }
        return null;
    }
}
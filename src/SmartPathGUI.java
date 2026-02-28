 /* Student Number: 25882262 */

import javax.swing.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the visual window for the Tutoring System.
 * It creates a tabbed layout so users can easily click between 
 * sessions, students, and bookings.
 */
public class SmartPathGUI extends JFrame {

    private JTabbedPane tabbedPane;

    // These hold the data and the actual visual tables
    private DefaultTableModel sessionTableModel;
    private DefaultTableModel customerTableModel;
    private DefaultTableModel bookingTableModel;

    private JTable sessionTable;
    private JTable customerTable;
    private JTable bookingTable;

    /**
     * This sets up the main window size, title, and the colored tabs.
     */
    public SmartPathGUI() {
        super("Smart Path Tutoring System (GUI) - Task 13");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        // The title text at the very top
        JLabel titleLabel = new JLabel("Smart Path Tutoring System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Setting up the tabs with a green background
        tabbedPane = new JTabbedPane();
        Color mutedGreen = new Color(140, 155, 105);
        tabbedPane.setBackground(mutedGreen);
        tabbedPane.setForeground(Color.WHITE);

        tabbedPane.addTab("Sessions", createSessionPanel());
        tabbedPane.addTab("Students", createCustomerPanel());
        tabbedPane.addTab("Bookings", createBookingPanel());
        tabbedPane.addTab("System", createSystemPanel());

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // ---------------------------
    // Functions to update the tables
    // ---------------------------
    
    /**
     * Clears the session table and refills it with the latest data.
     */
    private void refreshSessionTable() {
        Object[][] data = SmartPath.getSessionDataForTable();
        sessionTableModel.setRowCount(0);
        for (Object[] row : data) sessionTableModel.addRow(row);
    }

    /**
     * Clears the customer table and refills it with the latest data.
     */
    private void refreshCustomerTable() {
        Object[][] data = SmartPath.getCustomerDataForTable();
        customerTableModel.setRowCount(0);
        for (Object[] row : data) customerTableModel.addRow(row);
    }

    /**
     * Clears the booking table and refills it with the latest data.
     */
    private void refreshBookingTable() {
        Object[][] data = SmartPath.getBookingDataForTable();
        bookingTableModel.setRowCount(0);
        for (Object[] row : data) bookingTableModel.addRow(row);
    }

    // ---------------------------
    // Layout for each tab
    // ---------------------------

    /**
     * Creates the "Sessions" tab with the table and action buttons.
     */
    private JPanel createSessionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Subject", "Tutor", "Price", "Availability", "Date"};
        Object[][] data = SmartPath.getSessionDataForTable();
        sessionTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        sessionTable = new JTable(sessionTableModel);
        JScrollPane scrollPane = new JScrollPane(sessionTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Yellow styling for the table headers
        Color headerYellow = new Color(234, 212, 122);
        sessionTable.getTableHeader().setBackground(headerYellow);
        sessionTable.getTableHeader().setForeground(Color.WHITE);
        sessionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addButton = styledButton("Add New Session (C1.5a)");
        addButton.addActionListener(e -> showAddSessionDialog());

        JButton editButton = styledButton("Edit Selected Session (C1.5c)");
        editButton.addActionListener(e -> showEditSessionDialog(sessionTable));

        JButton deleteButton = styledButton("Delete Selected Session (C1.5d)");
        deleteButton.addActionListener(e -> deleteSelectedSession(sessionTable));

        JButton searchButton = styledButton("Search Sessions (C1.14)");
        searchButton.addActionListener(e -> showSearchSessionDialog());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Creates the "Students" tab for managing customer info.
     */
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "First Name", "Last Name", "Email", "Phone"};
        Object[][] data = SmartPath.getCustomerDataForTable();
        customerTableModel = new DefaultTableModel(data, columnNames) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        customerTable = new JTable(customerTableModel);
        panel.add(new JScrollPane(customerTable), BorderLayout.CENTER);

        Color headerYellow = new Color(234, 212, 122);
        customerTable.getTableHeader().setBackground(headerYellow);
        customerTable.getTableHeader().setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = styledButton("Add New Student (C1.8a)");
        addButton.addActionListener(e -> showAddCustomerDialog());

        JButton editButton = styledButton("Edit Selected (C1.8d)");
        editButton.addActionListener(e -> showEditCustomerDialog(customerTable));

        JButton deleteButton = styledButton("Delete Selected (C1.8e)");
        deleteButton.addActionListener(e -> deleteSelectedCustomer(customerTable));

        buttonPanel.add(addButton); buttonPanel.add(editButton); buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Creates the "Bookings" tab for processing orders.
     */
    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Customer ID", "Customer Name", "Date", "Total Cost"};
        Object[][] data = SmartPath.getBookingDataForTable();
        bookingTableModel = new DefaultTableModel(data, columnNames) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        bookingTable = new JTable(bookingTableModel);
        panel.add(new JScrollPane(bookingTable), BorderLayout.CENTER);

        Color headerYellow = new Color(234, 212, 122);
        bookingTable.getTableHeader().setBackground(headerYellow);
        bookingTable.getTableHeader().setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = styledButton("Add New Booking (C1.10a)");
        addButton.addActionListener(e -> showAddBookingDialog());

        JButton editButton = styledButton("Edit Selected (C1.15)");
        editButton.addActionListener(e -> showEditBookingDialog(bookingTable));

        JButton deleteButton = styledButton("Delete Selected (C1.10e)");
        deleteButton.addActionListener(e -> deleteSelectedBooking(bookingTable));

        buttonPanel.add(addButton); buttonPanel.add(editButton); buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Creates the "System" tab for saving data to files.
     */
    private JPanel createSystemPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton saveButton = styledButton("Save All Data to Files (C1.11)");
        saveButton.addActionListener(e -> {
            String feedback = SmartPath.saveDataToFiles();
            JOptionPane.showMessageDialog(this, feedback, "System Save", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(saveButton);
        return panel;
    }

    
    private void showAddSessionDialog() {
        // 1. AUTO-GENERATE UNIQUE ID
        String autoId = "SMARTPATH-" + (SmartPath.getSessionDataForTable().length + 101);

        // 2. Initialize fields and set the ID field to the auto-generated value
        JTextField idField = new JTextField(autoId, 15);
        idField.setEditable(false); 
        
        JTextField subjectField = new JTextField(15);
        JTextField tutorField = new JTextField(15);
        JTextField priceField = new JTextField(15);
        JTextField stockField = new JTextField(15);
        
         SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);
        
        String[] types = {"Online", "In-Person"};
        JComboBox<String> typeBox = new JComboBox<>(types);

        String[] platforms = {"Zoom", "Teams", "Google Meet"};
        JComboBox<String> platformBox = new JComboBox<>(platforms);
        
        String[] rooms = {"Room 1", "Room 2", "Room 3", "Room 4"};
        JComboBox<String> roomBox = new JComboBox<>(rooms);
        
        JLabel extraLabel = new JLabel("Platform:");
        
        // Dynamic panel for switching between Platform and Room 
        JPanel dynamicExtraPanel = new JPanel(new CardLayout());
        dynamicExtraPanel.add(platformBox, "Online");
        dynamicExtraPanel.add(roomBox, "In-Person");
        CardLayout cl = (CardLayout) dynamicExtraPanel.getLayout();

        typeBox.addActionListener(e -> {
            if (typeBox.getSelectedItem().equals("Online")) {
                extraLabel.setText("Platform:");
                cl.show(dynamicExtraPanel, "Online");
            } else {
                extraLabel.setText("Room Location:");
                cl.show(dynamicExtraPanel, "In-Person");
            }
        });

        // 5. Build the visual input panel 
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.add(new JLabel("Generated ID:")); inputPanel.add(idField);
        inputPanel.add(new JLabel("Session Type:")); inputPanel.add(typeBox);
        inputPanel.add(new JLabel("Subject:")); inputPanel.add(subjectField);
        inputPanel.add(new JLabel("Tutor:")); inputPanel.add(tutorField);
        inputPanel.add(new JLabel("Price (£):")); inputPanel.add(priceField);
        inputPanel.add(new JLabel("Availability:")); inputPanel.add(stockField);
        inputPanel.add(new JLabel("Select Date:")); inputPanel.add(dateSpinner);
        inputPanel.add(extraLabel); 
        inputPanel.add(dynamicExtraPanel); 

        // 6. Display the Dialog
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Add New Session", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Formatting for persistence
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                String dateString = sdf.format(dateSpinner.getValue());
                
                String extraValue = typeBox.getSelectedItem().equals("Online") ? 
                                    (String)platformBox.getSelectedItem() : (String)roomBox.getSelectedItem();

                // Format the Tutor and Subject names automatically
                String formattedTutor = formatName(tutorField.getText().trim());
                String formattedSubject = formatName(subjectField.getText().trim());
                
                // Final submission to logic
                String feedback = SmartPath.addNewSubclassedSessionGUI(
                        (String)typeBox.getSelectedItem(), 
                        idField.getText().trim(), 
                        formattedSubject, // Capitalised Subject
                        formattedTutor,   // Capitalised Tutor
                        Double.parseDouble(priceField.getText()), 
                        Integer.parseInt(stockField.getText()), 
                        dateString, 
                        extraValue
                    );
                
                JOptionPane.showMessageDialog(this, feedback);
                refreshSessionTable(); 
                
            } catch (NumberFormatException ex) {
                // Defensive programming
                JOptionPane.showMessageDialog(this, "Error: Invalid numerical input.");
            }
        }
    }
    /**
     * Deletes the row currently selected in the session table.
     */
    private void deleteSelectedSession(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;
        String sessionId = String.valueOf(table.getValueAt(selectedRow, 0));
        if (JOptionPane.showConfirmDialog(this, "Delete Session " + sessionId + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, SmartPath.deleteSessionGUI(sessionId));
            refreshSessionTable();
        }
    }

    /**
     * Asks for a keyword and shows a list of matching sessions.
     */
    private void showSearchSessionDialog() {
        String keyword = JOptionPane.showInputDialog(this, "Enter keyword (Subject or Tutor):");
        if (keyword != null && !keyword.trim().isEmpty()) {
            ArrayList<TutoringSession> results = SmartPath.searchSessions(keyword);
            
            if (!results.isEmpty()) { 
                StringBuilder sb = new StringBuilder("Results Found:\n\n");
                for (TutoringSession s : results) sb.append(s.toString()).append("\n");
                JOptionPane.showMessageDialog(this, sb.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No matches found for: " + keyword);
            }
        }
    }

    /**
     * Opens a window to change the price or stock for a session.
     */
    private void showEditSessionDialog(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) return;
        String sessionId = table.getValueAt(row, 0).toString();
        TutoringSession s = SmartPath.findSessionById(sessionId);

        JTextField priceField = new JTextField(String.valueOf(s.getPrice()), 15);
        JTextField stockField = new JTextField(String.valueOf(s.getStockLevel()), 15);

        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.add(new JLabel("New Price (£):")); p.add(priceField);
        p.add(new JLabel("New Availability:")); p.add(stockField);

        if (JOptionPane.showConfirmDialog(this, p, "Edit Session", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                SmartPath.editSession(sessionId, 1, Double.parseDouble(priceField.getText()));
                SmartPath.editSession(sessionId, 2, Double.parseDouble(stockField.getText()));
                refreshSessionTable();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Input error."); }
        }
    }

    /**
     * Opens a window to add a new student to the list.
     */
    /**
     * Opens a window to add a new student to the list.
     * Includes automatic ID generation and validation for email and phone numbers.
     */
    private void showAddCustomerDialog() {
        // 1. AUTO-GENERATE UNIQUE ID (Task 8a logic)
        // Matches the style used in your bookings and sessions
        String autoId = "STUDENT-" + (SmartPath.getCustomerDataForTable().length + 1001);

        JTextField idF = new JTextField(autoId);
        idF.setEditable(false); // Prevents manual editing of the unique ID
        
        JTextField fF = new JTextField(), lF = new JTextField();
        JTextField eF = new JTextField(), phF = new JTextField();

        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.add(new JLabel("Generated ID:")); p.add(idF);
        p.add(new JLabel("First Name:")); p.add(fF);
        p.add(new JLabel("Last Name:")); p.add(lF);
        p.add(new JLabel("Email Address:")); p.add(eF);
        p.add(new JLabel("Phone Number:")); p.add(phF);
        
        int result = JOptionPane.showConfirmDialog(this, p, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String email = eF.getText().trim().toLowerCase();
            String phone = phF.getText().trim();
            
            // --- VERIFICATION & VALIDATION LOGIC ---
            
            // 2. Email Validation (@ and .com or .co.uk)
            boolean validEmail = email.contains("@") && (email.endsWith(".com") || email.endsWith(".co.uk"));
            
            // 3. Phone Number Validation (Starts with 07 and length of 11)
            boolean validPhone = phone.startsWith("07") && phone.length() == 11;

            if (!validEmail) {
                JOptionPane.showMessageDialog(this, "ERROR: Invalid Email. Must contain '@' and end with '.com' or '.co.uk'", "Validation Error", JOptionPane.ERROR_MESSAGE);
            } else if (!validPhone) {
                JOptionPane.showMessageDialog(this, "ERROR: Invalid Phone. Must be 11 digits and start with '07'", "Validation Error", JOptionPane.ERROR_MESSAGE);
            } else if (fF.getText().isEmpty() || lF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: First and Last names cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // 4. Formatting - Capitalise names before saving
                String fName = formatName(fF.getText().trim());
                String lName = formatName(lF.getText().trim());
                
                // Final submission to system logic
                String feedback = SmartPath.addNewCustomerGUI(idF.getText(), fName, lName, email, phone);
                JOptionPane.showMessageDialog(this, feedback);
                refreshCustomerTable();
            }
        }
    }

    /**
     * Deletes the selected student.
     */
    private void deleteSelectedCustomer(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) return;
        String id = table.getValueAt(row, 0).toString();
        if (JOptionPane.showConfirmDialog(this, "Delete Customer " + id + "?") == JOptionPane.YES_OPTION) {
            SmartPath.deleteCustomerGUI(id);
            refreshCustomerTable();
        }
    }

    /**
     * Opens a window to update a student's contact info.
     */
    private void showEditCustomerDialog(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) return;
        String id = table.getValueAt(row, 0).toString();
        Customer c = SmartPath.findCustomerById(id);

        JTextField fF = new JTextField(c.getFirstName()), lF = new JTextField(c.getLastName()), eF = new JTextField(c.getEmail()), pF = new JTextField(c.getPhoneNumber());
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.add(new JLabel("First:")); p.add(fF); p.add(new JLabel("Last:")); p.add(lF);
        p.add(new JLabel("Email:")); p.add(eF); p.add(new JLabel("Phone:")); p.add(pF);

        if (JOptionPane.showConfirmDialog(this, p, "Edit Customer", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            SmartPath.editCustomerGUI(id, 1, fF.getText());
            SmartPath.editCustomerGUI(id, 2, lF.getText());
            SmartPath.editCustomerGUI(id, 3, eF.getText());
            SmartPath.editCustomerGUI(id, 4, pF.getText());
            refreshCustomerTable();
        }
    }

    /**
     * Lets you pick a student and multiple sessions to create a booking.
     */
    private void showAddBookingDialog() {
        String[] custs = customerListToIdArray();
        String[] sess = sessionListToInfoArray();
        if (custs.length == 0 || sess.length == 0) {
            JOptionPane.showMessageDialog(this, "Please ensure students and sessions exist.");
            return;
        }

        JComboBox<String> cb = new JComboBox<>(custs);
        JList<String> list = new JList<>(sess);
        String todayDate = java.time.LocalDate.now().toString(); 

        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel("Customer:"), BorderLayout.NORTH); 
        p.add(cb, BorderLayout.CENTER);
        p.add(new JLabel("Select Sessions (Hold Ctrl):"), BorderLayout.SOUTH); 
        p.add(new JScrollPane(list), BorderLayout.SOUTH);

        if (JOptionPane.showConfirmDialog(this, p, "New Booking for " + todayDate, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String cid = cb.getSelectedItem().toString().split(" - ")[0];
            SmartPath.addNewBooking(cid, todayDate, list.getSelectedValuesList());
            refreshBookingTable(); 
            refreshSessionTable();
        }
    }

    /**
     * Change who is in a booking or what sessions are included.
     */
    private void showEditBookingDialog(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) return;
        String bid = table.getValueAt(row, 0).toString();
        ClassBooking b = SmartPath.findBookingById(bid);

        JComboBox<String> cb = new JComboBox<>(customerListToIdArray());
        JList<String> list = new JList<>(sessionListToInfoArray());
        JPanel p = new JPanel(new BorderLayout());
        p.add(cb, BorderLayout.NORTH); p.add(new JScrollPane(list), BorderLayout.CENTER);

        if (JOptionPane.showConfirmDialog(this, p, "Edit Booking", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String cid = cb.getSelectedItem().toString().split(" - ")[0];
            SmartPath.editBookingSessionsAndCustomer(bid, cid, list.getSelectedValuesList());
            refreshBookingTable(); refreshSessionTable();
        }
    }

    /**
     * Removes a booking from the system.
     */
    private void deleteSelectedBooking(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) return;
        String id = table.getValueAt(row, 0).toString();
        SmartPath.deleteBooking(id);
        refreshBookingTable(); refreshSessionTable();
    }

    // --- Helpers to convert lists into text for the drop-down boxes ---
    private String[] customerListToIdArray() {
        Object[][] data = SmartPath.getCustomerDataForTable();
        String[] arr = new String[data.length];
        for(int i=0; i<data.length; i++) arr[i] = data[i][0] + " - " + data[i][1];
        return arr;
    }

    private String[] sessionListToInfoArray() {
        Object[][] data = SmartPath.getSessionDataForTable();
        String[] arr = new String[data.length];
        for(int i=0; i<data.length; i++) arr[i] = data[i][0] + " (" + data[i][1] + ")";
        return arr;
    }

    /**
     * A helper to make all buttons look consistent and pink.
     */
    private JButton styledButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(255, 153, 153));
        b.setForeground(Color.WHITE);
        b.setOpaque(true); b.setBorderPainted(false);
        return b;
    }

    /**
     * Shows the login box before the main program opens.
     */
    private static boolean showLoginDialog() {
        JTextField u = new JTextField(); JPasswordField p = new JPasswordField();
        Object[] msg = {"Username:", u, "Password:", p};
        if (JOptionPane.showConfirmDialog(null, msg, "Login", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            return SmartPath.authenticateStaff(u.getText(), new String(p.getPassword()));
        }
        return false;
    }

    /**
     * The starting point for the GUI. Loads data, asks for login, then opens the window.
     */
    public static void main(String[] args) {
        SmartPath.loadDataFromFiles();
        if (showLoginDialog()) SwingUtilities.invokeLater(() -> new SmartPathGUI());
        else System.exit(0);
    }
    
    /**
     * Formats names and subjects so they have a capital first letter.
     */
    private String formatName(String input) {
        if (input == null || input.isEmpty()) return "";
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
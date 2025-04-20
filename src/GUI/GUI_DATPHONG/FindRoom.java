package GUI_DATPHONG;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import GUI_DANGNHAP_DANGKY.RoundedPanel;

public class FindRoom extends JPanel {
    private JLabel checkInDateLabel;
    private JLabel checkOutDateLabel;
    private JPanel selectedGuestOption = null;
    private String selectedOptionTitle = "Đi một mình";
    private final RoomTablePanel roomTablePanel;

    private final UIComponents uiComponents;
    private final EventHandler eventHandler;

    public FindRoom() {
        uiComponents = new UIComponents();
        eventHandler = new EventHandler(this, uiComponents);

        setLayout(new BorderLayout(10, 10));

        // Create top panel for search UI
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        topPanel.setBackground(new Color(240, 245, 245));

        Font boldFont = new Font("SansSerif", Font.BOLD, 16);
        Font normalFont = new Font("SansSerif", Font.PLAIN, 14);

        // Location Panel
        RoundedPanel locationPanel = uiComponents.createLocationPanel(boldFont);
        locationPanel.setPreferredSize(new Dimension(240, 60));

        // Date Panel
        RoundedPanel datePanel = uiComponents.createDatePanel(boldFont, eventHandler);
        datePanel.setPreferredSize(new Dimension(350, 60));

        // Guest Panel
        RoundedPanel guestPanel = uiComponents.createGuestPanel(boldFont, normalFont, eventHandler);
        guestPanel.setPreferredSize(new Dimension(250, 60));

        // Search Button
        RoundedButton searchButton = uiComponents.createSearchButton(boldFont);
        searchButton.setPreferredSize(new Dimension(60, 60));
        searchButton.addActionListener(e -> showRoomTable());

        // Reset Button
        RoundedButton resetButton = uiComponents.createResetButton(boldFont);
        resetButton.setPreferredSize(new Dimension(60, 60));
        resetButton.addActionListener(e -> resetUI());

        // Add components to top panel
        topPanel.add(locationPanel);
        topPanel.add(datePanel);
        topPanel.add(guestPanel);
        topPanel.add(searchButton);
        topPanel.add(resetButton);

        // Add top panel to panel
        add(topPanel, BorderLayout.NORTH);

        // Initialize date labels before using them
        checkInDateLabel = uiComponents.getCheckInDateLabel();
        checkOutDateLabel = uiComponents.getCheckOutDateLabel();

        // Set default dates on UI (already set in EventHandler, just update labels)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        eventHandler.getCheckInDateLabel().setText(sdf.format(eventHandler.getCheckInDate()));
        eventHandler.getCheckOutDateLabel().setText(sdf.format(eventHandler.getCheckOutDate()));

        // Initialize RoomTablePanel and load all available rooms immediately
        roomTablePanel = new RoomTablePanel(eventHandler, this);
        roomTablePanel.loadAllAvailableRooms();
        add(roomTablePanel, BorderLayout.CENTER);
    }

    private void showRoomTable() {
        // Refresh the table data when Search is clicked
        roomTablePanel.loadRoomData();
    }

    private void resetUI() {
        Font boldFont = new Font("SansSerif", Font.BOLD, 16); // Re-declare boldFont for use in reset
    
        // Reset Dates
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + 24 * 60 * 60 * 1000L);
        eventHandler.setCheckInDate(today);
        eventHandler.setCheckOutDate(tomorrow);
        checkInDateLabel.setText(sdf.format(today));
        checkOutDateLabel.setText(sdf.format(tomorrow));
    
        // Reset Table Data
        roomTablePanel.loadAllAvailableRooms();
    }    

    // Getters for date labels
    public JLabel getCheckInDateLabel() {
        return checkInDateLabel;
    }

    public JLabel getCheckOutDateLabel() {
        return checkOutDateLabel;
    }

    // Getters and setters for guest selection
    public JPanel getSelectedGuestOption() {
        return selectedGuestOption;
    }

    public void setSelectedGuestOption(JPanel selectedGuestOption) {
        this.selectedGuestOption = selectedGuestOption;
    }

    public String getSelectedOptionTitle() {
        return selectedOptionTitle;
    }

    public void setSelectedOptionTitle(String selectedOptionTitle) {
        this.selectedOptionTitle = selectedOptionTitle;
    }
}

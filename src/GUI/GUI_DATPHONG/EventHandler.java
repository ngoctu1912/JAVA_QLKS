package GUI_DATPHONG;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class EventHandler {
    private final FindRoom findRoom;
    private final UIComponents uiComponents;
    private Date checkInDate;
    private Date checkOutDate;
    private final BUS.PhongBUS phongBUS;

    public EventHandler(FindRoom findRoom, UIComponents uiComponents) {
        this.findRoom = findRoom;
        this.uiComponents = uiComponents;
        this.phongBUS = new BUS.PhongBUS();
        // Gán giá trị mặc định cho checkInDate và checkOutDate
        this.checkInDate = new Date(); // Today
        this.checkOutDate = new Date(checkInDate.getTime() + 24 * 60 * 60 * 1000L); // Tomorrow
    }

    public MouseAdapter createCheckInMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CalendarPopup(findRoom, e.getComponent(), true, EventHandler.this);
            }
        };
    }

    public MouseAdapter createCheckOutMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CalendarPopup(findRoom, e.getComponent(), false, EventHandler.this);
            }
        };
    }

    public MouseAdapter createGuestPanelMouseListener(JLabel guestLabel, JLabel roomLabel) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showGuestPopup(e.getComponent(), guestLabel, roomLabel);
            }
        };
    }

    private void showGuestPopup(Component parent, JLabel guestLabel, JLabel roomLabel) {
        JPopupMenu popup = new JPopupMenu();
        popup.setLayout(new BorderLayout());

        findRoom.setSelectedGuestOption(null);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(235, 250));

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(140, 250));

        int[] popupOffsetX = { 0 }; // lưu x-offset để điều chỉnh khi show popup

        addGuestOption(leftPanel, "Đi một mình", "1 phòng, 1 người lớn", guestLabel, roomLabel,
                "1 người lớn", "1 phòng", popup, rightPanel, () -> {
                    findRoom.setSelectedOptionTitle("Đi một mình");
                    popup.setPreferredSize(new Dimension(235, 250));
                    popup.remove(rightPanel);
                    popup.revalidate();
                    popup.repaint();
                    guestLabel.setText("1 người lớn");
                    roomLabel.setText("1 phòng");
                    popupOffsetX[0] = 0;
                    popup.setVisible(false);
                    popup.show(parent, popupOffsetX[0], parent.getHeight());
                });

        addGuestOption(leftPanel, "Cặp đôi/2 người", "1 phòng, 2 người lớn", guestLabel, roomLabel,
                "2 người lớn", "1 phòng", popup, rightPanel, () -> {
                    findRoom.setSelectedOptionTitle("Cặp đôi/2 người");
                    popup.setPreferredSize(new Dimension(235, 250));
                    popup.remove(rightPanel);
                    popup.revalidate();
                    popup.repaint();
                    guestLabel.setText("2 người lớn");
                    roomLabel.setText("1 phòng");
                    popupOffsetX[0] = 0;
                    popup.setVisible(false);
                    popup.show(parent, popupOffsetX[0], parent.getHeight());
                });

        addGuestOption(leftPanel, "Theo gia đình", "", guestLabel, roomLabel, "", "", popup, rightPanel, () -> {
            findRoom.setSelectedOptionTitle("Theo gia đình");
            rightPanel.removeAll();
            rightPanel.add(createFamilyDetailPanel(guestLabel, roomLabel, checkInDate, checkOutDate),
                    BorderLayout.CENTER);
            popup.setPreferredSize(new Dimension(370, 250));
            popupOffsetX[0] = -30;
            if (!popup.isAncestorOf(rightPanel)) {
                popup.add(rightPanel, BorderLayout.CENTER);
            }
            popup.revalidate();
            popup.repaint();
            popup.pack();
            popup.setVisible(false);
            popup.show(parent, popupOffsetX[0], parent.getHeight());
        });

        addGuestOption(leftPanel, "Theo đoàn/nhóm", "", guestLabel, roomLabel, "", "", popup, rightPanel, () -> {
            findRoom.setSelectedOptionTitle("Theo đoàn/nhóm");
            rightPanel.removeAll();
            rightPanel.add(createGroupDetailPanel(guestLabel, roomLabel, checkInDate, checkOutDate),
                    BorderLayout.CENTER);
            popup.setPreferredSize(new Dimension(370, 250));
            popupOffsetX[0] = -30;
            if (!popup.isAncestorOf(rightPanel)) {
                popup.add(rightPanel, BorderLayout.CENTER);
            }
            popup.revalidate();
            popup.repaint();
            popup.pack();
            popup.setVisible(false);
            popup.show(parent, popupOffsetX[0], parent.getHeight());
        });

        popup.add(leftPanel, BorderLayout.WEST);
        popup.show(parent, popupOffsetX[0], parent.getHeight());
    }

    private void addGuestOption(JPanel container, String title, String description,
            JLabel guestLabel, JLabel roomLabel,
            String guestText, String roomText,
            JPopupMenu popup, JPanel rightPanel,
            Runnable onSelect) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setPreferredSize(new Dimension(250, 45));
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        titleLabel.setBorder(new EmptyBorder(0, 10, 0, 0));

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descLabel.setForeground(Color.GRAY);
        descLabel.setBorder(new EmptyBorder(0, 0, 0, 10));

        itemPanel.add(titleLabel, BorderLayout.WEST);

        if (title.equals("Theo gia đình") || title.equals("Theo đoàn/nhóm")) {
            JLabel arrowLabel = new JLabel("\u25B6");
            arrowLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            arrowLabel.setForeground(Color.DARK_GRAY);
            arrowLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
            itemPanel.add(arrowLabel, BorderLayout.EAST);
            JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            centerPanel.setOpaque(false);
            centerPanel.add(descLabel);
            itemPanel.add(centerPanel, BorderLayout.CENTER);
        } else {
            itemPanel.add(descLabel, BorderLayout.EAST);
        }

        if (title.equals(findRoom.getSelectedOptionTitle())) {
            itemPanel.setBackground(new Color(200, 220, 255));
            findRoom.setSelectedGuestOption(itemPanel);
        }

        itemPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (itemPanel != findRoom.getSelectedGuestOption()) {
                    itemPanel.setBackground(new Color(245, 245, 255));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (itemPanel != findRoom.getSelectedGuestOption()) {
                    itemPanel.setBackground(Color.WHITE);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (findRoom.getSelectedGuestOption() != null && findRoom.getSelectedGuestOption() != itemPanel) {
                    findRoom.getSelectedGuestOption().setBackground(Color.WHITE);
                }
                findRoom.setSelectedGuestOption(itemPanel);
                itemPanel.setBackground(new Color(200, 220, 255));

                if (onSelect != null) {
                    onSelect.run();
                }
            }
        });

        container.add(itemPanel);
    }

    private JPanel createFamilyDetailPanel(JLabel guestLabel, JLabel roomLabel, Date checkInDate, Date checkOutDate) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        int roomCount = parseRoomCount(roomLabel.getText());
        int adultCount = parseAdultCount(guestLabel.getText());
        int childCount = parseChildCount(guestLabel.getText());

        // Count available rooms for "Theo gia đình"
        int maxRooms = phongBUS.countAvailableRooms(checkInDate, checkOutDate, "Theo gia đình");
        roomCount = Math.min(roomCount, maxRooms); // Adjust initial room count to not exceed max

        JPanel roomSpinner = uiComponents.createSpinner(roomCount);
        JPanel adultSpinner = uiComponents.createSpinner(adultCount);
        JPanel childSpinner = uiComponents.createSpinner(childCount);

        // Get the "+" button (index 0 is "-", index 1 is label, index 2 is "+")
        JButton incrementButton = (JButton) roomSpinner.getComponent(2);
        JLabel roomLabelSpinner = (JLabel) roomSpinner.getComponent(1);

        // Update the "+" button state based on current room count
        incrementButton.setEnabled(Integer.parseInt(roomLabelSpinner.getText()) < maxRooms);

        // Add listeners to update labels and button state
        ((JButton) roomSpinner.getComponent(0)).addActionListener(e -> {
            updateLabels(roomSpinner, adultSpinner, childSpinner, guestLabel, roomLabel);
            incrementButton.setEnabled(Integer.parseInt(roomLabelSpinner.getText()) < maxRooms);
        });
        ((JButton) roomSpinner.getComponent(2)).addActionListener(e -> {
            updateLabels(roomSpinner, adultSpinner, childSpinner, guestLabel, roomLabel);
            incrementButton.setEnabled(Integer.parseInt(roomLabelSpinner.getText()) < maxRooms);
        });
        ((JButton) adultSpinner.getComponent(0))
                .addActionListener(e -> updateLabels(roomSpinner, adultSpinner, childSpinner, guestLabel, roomLabel));
        ((JButton) adultSpinner.getComponent(2))
                .addActionListener(e -> updateLabels(roomSpinner, adultSpinner, childSpinner, guestLabel, roomLabel));
        ((JButton) childSpinner.getComponent(0))
                .addActionListener(e -> updateLabels(roomSpinner, adultSpinner, childSpinner, guestLabel, roomLabel));
        ((JButton) childSpinner.getComponent(2))
                .addActionListener(e -> updateLabels(roomSpinner, adultSpinner, childSpinner, guestLabel, roomLabel));

        panel.add(uiComponents.createRow("Phòng", roomSpinner));
        panel.add(uiComponents.createRow("Người lớn", adultSpinner));
        panel.add(uiComponents.createRow("Trẻ em", childSpinner));

        return panel;
    }

    private void updateLabels(JPanel roomSpinner, JPanel adultSpinner, JPanel childSpinner, JLabel guestLabel,
            JLabel roomLabel) {
        int rooms = Integer.parseInt(((JLabel) roomSpinner.getComponent(1)).getText());
        int adults = Integer.parseInt(((JLabel) adultSpinner.getComponent(1)).getText());
        int children = Integer.parseInt(((JLabel) childSpinner.getComponent(1)).getText());
        roomLabel.setText(rooms + " phòng");
        guestLabel.setText(adults + " người lớn" + (children > 0 ? ", " + children + " trẻ em" : ""));
        findRoom.setSelectedOptionTitle("Theo gia đình");
    }

    private JPanel createGroupDetailPanel(JLabel guestLabel, JLabel roomLabel, Date checkInDate, Date checkOutDate) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        int roomCount = parseRoomCount(roomLabel.getText());
        int adultCount = parseAdultCount(guestLabel.getText());

        // Count available rooms for "Theo đoàn/nhóm"
        int maxRooms = phongBUS.countAvailableRooms(checkInDate, checkOutDate, "Theo đoàn/nhóm");
        roomCount = Math.min(roomCount, maxRooms); // Adjust initial room count to not exceed max

        JPanel roomSpinner = uiComponents.createSpinner(roomCount);
        JPanel adultSpinner = uiComponents.createSpinner(adultCount);

        // Get the "+" button (index 0 is "-", index 1 is label, index 2 is "+")
        JButton incrementButton = (JButton) roomSpinner.getComponent(2);
        JLabel roomLabelSpinner = (JLabel) roomSpinner.getComponent(1);

        // Update the "+" button state based on current room count
        incrementButton.setEnabled(Integer.parseInt(roomLabelSpinner.getText()) < maxRooms);

        // Add listeners to update labels and button state
        ((JButton) roomSpinner.getComponent(0)).addActionListener(e -> {
            updateGroupLabels(roomSpinner, adultSpinner, guestLabel, roomLabel);
            incrementButton.setEnabled(Integer.parseInt(roomLabelSpinner.getText()) < maxRooms);
        });
        ((JButton) roomSpinner.getComponent(2)).addActionListener(e -> {
            updateGroupLabels(roomSpinner, adultSpinner, guestLabel, roomLabel);
            incrementButton.setEnabled(Integer.parseInt(roomLabelSpinner.getText()) < maxRooms);
        });
        ((JButton) adultSpinner.getComponent(0))
                .addActionListener(e -> updateGroupLabels(roomSpinner, adultSpinner, guestLabel, roomLabel));
        ((JButton) adultSpinner.getComponent(2))
                .addActionListener(e -> updateGroupLabels(roomSpinner, adultSpinner, guestLabel, roomLabel));

        panel.add(uiComponents.createRow("Phòng", roomSpinner));
        panel.add(uiComponents.createRow("Người lớn", adultSpinner));

        return panel;
    }

    private void updateGroupLabels(JPanel roomSpinner, JPanel adultSpinner, JLabel guestLabel, JLabel roomLabel) {
        int rooms = Integer.parseInt(((JLabel) roomSpinner.getComponent(1)).getText());
        int adults = Integer.parseInt(((JLabel) adultSpinner.getComponent(1)).getText());
        roomLabel.setText(rooms + " phòng");
        guestLabel.setText(adults + " người lớn");
        findRoom.setSelectedOptionTitle("Theo đoàn/nhóm");
    }

    private int parseRoomCount(String roomLabelText) {
        try {
            return Integer.parseInt(roomLabelText.replace(" phòng", "").trim());
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private int parseAdultCount(String guestLabelText) {
        try {
            String[] parts = guestLabelText.split(",");
            String adultPart = parts[0].replace(" người lớn", "").trim();
            return Integer.parseInt(adultPart);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return 1;
        }
    }

    private int parseChildCount(String guestLabelText) {
        try {
            String[] parts = guestLabelText.split(",");
            if (parts.length > 1) {
                String childPart = parts[1].replace(" trẻ em", "").trim();
                return Integer.parseInt(childPart);
            }
            return 0;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    // Getters and setters for dates
    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    // Getters for date labels via FindRoom
    public JLabel getCheckInDateLabel() {
        return findRoom.getCheckInDateLabel();
    }

    public JLabel getCheckOutDateLabel() {
        return findRoom.getCheckOutDateLabel();
    }
}
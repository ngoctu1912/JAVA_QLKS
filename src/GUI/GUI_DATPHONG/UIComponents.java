package GUI_DATPHONG;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import GUI_DANGNHAP_DANGKY.RoundedPanel;

public class UIComponents {
    private JLabel checkInDateLabel;
    private JLabel checkOutDateLabel;

    public RoundedPanel createLocationPanel(Font boldFont) {
        RoundedPanel locationPanel = new RoundedPanel(30);
        locationPanel.setBackground(Color.WHITE);
        locationPanel.setLayout(new GridBagLayout());

        FlatSVGIcon locationIcon = new FlatSVGIcon("icons/location.svg", 20, 20);
        JLabel locationIconLabel = new JLabel(locationIcon);

        JLabel locationField = new JLabel("Thành phố Hồ Chí Minh");
        locationField.setFont(boldFont);
        locationField.setHorizontalAlignment(JTextField.CENTER);

        JPanel locationContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        locationContent.setOpaque(false);
        locationContent.add(locationIconLabel);
        locationContent.add(locationField);

        locationPanel.add(locationContent);
        return locationPanel;
    }

    public RoundedPanel createDatePanel(Font boldFont, EventHandler eventHandler) {
        RoundedPanel datePanel = new RoundedPanel(30);
        datePanel.setBackground(Color.WHITE);
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));

        JPanel checkInPanel = createDatePanelWithLabel("Nhận phòng", boldFont, "icons/schedule.svg", true);
        checkInPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        checkInPanel.addMouseListener(eventHandler.createCheckInMouseListener());

        JPanel separator = new JPanel();
        separator.setBackground(new Color(209, 207, 207));
        separator.setPreferredSize(new Dimension(1, 30));
        separator.setMaximumSize(new Dimension(1, Integer.MAX_VALUE));

        JPanel checkOutPanel = createDatePanelWithLabel("Trả phòng", boldFont, "icons/schedule.svg", false);
        checkOutPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        checkOutPanel.addMouseListener(eventHandler.createCheckOutMouseListener());

        datePanel.add(checkInPanel);
        datePanel.add(Box.createHorizontalGlue());
        datePanel.add(separator);
        datePanel.add(Box.createHorizontalGlue());
        datePanel.add(checkOutPanel);

        return datePanel;
    }

    public RoundedPanel createGuestPanel(Font boldFont, Font normalFont, EventHandler eventHandler) {
        RoundedPanel guestPanel = new RoundedPanel(30);
        guestPanel.setBackground(Color.WHITE);
        guestPanel.setLayout(new BorderLayout(10, 10));

        FlatSVGIcon guestIcon = new FlatSVGIcon("icons/group.svg", 20, 20);
        JLabel guestIconLabel = new JLabel(guestIcon);

        JPanel guestTextPanel = new JPanel(new GridLayout(2, 1));
        guestTextPanel.setOpaque(false);
        JLabel guestLabel = new JLabel("1 người lớn");
        guestLabel.setFont(boldFont);
        JLabel roomLabel = new JLabel("1 phòng");
        roomLabel.setFont(normalFont);
        guestTextPanel.add(guestLabel);
        guestTextPanel.add(roomLabel);

        JPanel leftContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftContent.setOpaque(false);
        leftContent.add(guestIconLabel);
        leftContent.add(guestTextPanel);

        JLabel arrowLabel = new JLabel("\u25BC");
        arrowLabel.setBorder(new EmptyBorder(0, 0, 0, 10));

        guestPanel.add(leftContent, BorderLayout.WEST);
        guestPanel.add(arrowLabel, BorderLayout.EAST);

        guestPanel.addMouseListener(eventHandler.createGuestPanelMouseListener(guestLabel, roomLabel));
        return guestPanel;
    }

    public RoundedButton createSearchButton(Font boldFont) {
        RoundedButton searchButton = new RoundedButton("", 30);
        searchButton.setBackground(new Color(113, 162, 245));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(boldFont);
        searchButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        FlatSVGIcon search = new FlatSVGIcon("icons/search.svg", 20, 20);
        searchButton.setIcon(search);
        return searchButton;
    }

    public RoundedButton createResetButton(Font boldFont) {
        RoundedButton resetButton = new RoundedButton("", 30);
        resetButton.setBackground(new Color(232, 233, 235));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(boldFont);
        resetButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        FlatSVGIcon reset = new FlatSVGIcon("icons/reset.svg", 20, 20);
        resetButton.setIcon(reset);
        return resetButton;
    }

    private JPanel createDatePanelWithLabel(String labelText, Font boldFont, String iconPath, boolean isCheckIn) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(null);
        wrapper.setPreferredSize(new Dimension(150, 60));
        wrapper.setOpaque(false);

        JLabel iconLabel = new JLabel(new FlatSVGIcon(iconPath, 20, 20));
        iconLabel.setBounds(10, 20, 20, 20);

        JLabel label = new JLabel(labelText);
        label.setFont(boldFont);
        label.setBounds(40, 20, 100, 20);

        wrapper.add(iconLabel);
        wrapper.add(label);

        if (isCheckIn) {
            checkInDateLabel = new JLabel("");
            checkInDateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            checkInDateLabel.setForeground(Color.GRAY);
            checkInDateLabel.setBounds(40, 40, 100, 20);
            wrapper.add(checkInDateLabel);
        } else {
            checkOutDateLabel = new JLabel("");
            checkOutDateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            checkOutDateLabel.setForeground(Color.GRAY);
            checkOutDateLabel.setBounds(40, 40, 100, 20);
            wrapper.add(checkOutDateLabel);
        }

        return wrapper;
    }

    public JPanel createSpinner(int defaultValue) {
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        spinnerPanel.setBackground(Color.WHITE);

        JLabel valueLabel = new JLabel(String.valueOf(defaultValue));
        valueLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        valueLabel.setPreferredSize(new Dimension(30, 25));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton minusButton = new JButton("-");
        minusButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        minusButton.setPreferredSize(new Dimension(25, 25));
        minusButton.setBackground(Color.WHITE);
        minusButton.setForeground(new Color(66, 133, 244));
        minusButton.setBorder(BorderFactory.createEmptyBorder());
        minusButton.setFocusable(false);

        JButton plusButton = new JButton("+");
        plusButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        plusButton.setPreferredSize(new Dimension(25, 25));
        plusButton.setBackground(Color.WHITE);
        plusButton.setForeground(new Color(66, 133, 244));
        plusButton.setBorder(BorderFactory.createEmptyBorder());
        plusButton.setFocusable(false);

        minusButton.addActionListener(e -> {
            int value = Integer.parseInt(valueLabel.getText());
            if (value > 0) {
                value--;
                valueLabel.setText(String.valueOf(value));
            }
        });

        plusButton.addActionListener(e -> {
            int value = Integer.parseInt(valueLabel.getText());
            if (value < 20) {
                value++;
                valueLabel.setText(String.valueOf(value));
            }
        });

        spinnerPanel.add(minusButton);
        spinnerPanel.add(valueLabel);
        spinnerPanel.add(plusButton);

        return spinnerPanel;
    }

    public JPanel createRow(String label, JPanel spinnerPanel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.add(new JLabel(label));
        row.add(Box.createHorizontalStrut(10));
        row.add(spinnerPanel);
        return row;
    }

    public JLabel getCheckInDateLabel() {
        return checkInDateLabel;
    }

    public JLabel getCheckOutDateLabel() {
        return checkOutDateLabel;
    }
}
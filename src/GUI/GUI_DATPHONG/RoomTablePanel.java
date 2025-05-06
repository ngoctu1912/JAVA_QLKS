package GUI_DATPHONG;

import BUS.PhongBUS;
import BUS.DichVuBUS;
import DTO.PhongDTO;
import GUI_DANGNHAP_DANGKY.CustomMessage;
import GUI_TRANGCHU.HomeFrame;
import GUI_DATPHONG.DatPhong.DatPhongFrame;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class RoomTablePanel extends JPanel {
    private final JPanel roomContainer;
    private final EventHandler eventHandler;
    private final FindRoom findRoom;
    private final PhongBUS phongBUS;
    private final DichVuBUS dichVuBUS;
    private final List<JCheckBox> roomCheckBoxes;
    private final JButton bookButton;

    public RoomTablePanel(EventHandler eventHandler, FindRoom findRoom) {
        this.eventHandler = eventHandler;
        this.findRoom = findRoom;
        this.phongBUS = new PhongBUS();
        this.dichVuBUS = new DichVuBUS();
        this.roomCheckBoxes = new ArrayList<>();

        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 245));

        // Create scrollable container for room cards
        roomContainer = new JPanel();
        roomContainer.setLayout(new BoxLayout(roomContainer, BoxLayout.Y_AXIS));
        roomContainer.setBackground(new Color(240, 245, 245));

        JScrollPane scrollPane = new JScrollPane(roomContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scrollPane.getViewport().setBackground(new Color(240, 245, 245));
        scrollPane.setBackground(new Color(240, 245, 245));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Customize vertical scrollbar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(209, 207, 207);
                this.trackColor = new Color(245, 245, 245);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(thumbColor);
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                // Minimal track painting
            }
        });
        verticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));

        add(scrollPane, BorderLayout.CENTER);

        // Book Button at the bottom
        bookButton = new JButton("Đặt phòng");
        bookButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        bookButton.setBackground(new Color(95, 195, 245));
        bookButton.setForeground(Color.WHITE);
        bookButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bookButton.setFocusPainted(false);
        FlatSVGIcon bookIcon = new FlatSVGIcon("icons/booking.svg", 20, 20);
        bookButton.setIcon(bookIcon);
        bookButton.addActionListener(e -> {
            StringBuilder selectedRooms = new StringBuilder();
            List<String> selectedRoomIds = new ArrayList<>();
            for (JCheckBox checkBox : roomCheckBoxes) {
                if (checkBox.isSelected()) {
                    String roomInfo = checkBox.getToolTipText();
                    selectedRooms.append(roomInfo).append("\n");
                    selectedRoomIds.add(roomInfo.split(" - ")[0]); // Extract maP
                }
            }

            if (selectedRooms.length() == 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn ít nhất một phòng!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra ngữ cảnh: admin (DatPhongFrame) hay khách hàng (HomeFrame)
            HomeFrame homeFrame = (HomeFrame) SwingUtilities.getAncestorOfClass(HomeFrame.class, this);
            DatPhongFrame datPhongFrame = (DatPhongFrame) SwingUtilities.getAncestorOfClass(DatPhongFrame.class, this);
            boolean isAdminContext = datPhongFrame != null;

            if (!isAdminContext && homeFrame != null && homeFrame.getAccountInfo() == null) {
                // Khách hàng chưa đăng nhập
                CustomMessage customMessage = new CustomMessage();
                customMessage.showSuccessMessage(
                        "Yêu cầu đăng nhập",
                        "Vui lòng đăng nhập hoặc đăng ký để đặt phòng!",
                        e1 -> homeFrame.showLoginFrame()
                );
                return;
            }

            // Kiểm tra ngày nhận và trả phòng
            java.util.Date checkInDate = eventHandler.getCheckInDate();
            java.util.Date checkOutDate = eventHandler.getCheckOutDate();
            if (checkInDate == null || checkOutDate == null || checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate)) {
                JOptionPane.showMessageDialog(this,
                        "Ngày nhận phòng và trả phòng không hợp lệ!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mở BookingDialog với cờ isAdminContext
            new BookingDialog(this, selectedRoomIds, selectedRooms.toString(), checkInDate, checkOutDate, isAdminContext).showDialog();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bottomPanel.add(bookButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Load all available rooms without filtering
    public void loadAllAvailableRooms() {
        roomContainer.removeAll();
        roomCheckBoxes.clear();

        try {
            List<PhongDTO> rooms = phongBUS.getAllAvailableRooms();
            populateRoomCards(rooms);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách phòng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        roomContainer.revalidate();
        roomContainer.repaint();
    }

    // Load filtered rooms based on dates and guest type
    public void loadRoomData() {
        roomContainer.removeAll();
        roomCheckBoxes.clear();

        try {
            java.util.Date checkInDate = eventHandler.getCheckInDate();
            java.util.Date checkOutDate = eventHandler.getCheckOutDate();
            String guestType = findRoom.getSelectedOptionTitle();
            if (checkInDate == null || checkOutDate == null || guestType == null) {
                throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin tìm kiếm (ngày nhận phòng, ngày trả phòng, loại khách)!");
            }
            List<PhongDTO> rooms = phongBUS.getAvailableRooms(checkInDate, checkOutDate, guestType);
            populateRoomCards(rooms);
        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm phòng: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }

        roomContainer.revalidate();
        roomContainer.repaint();
    }

    private void populateRoomCards(List<PhongDTO> rooms) {
        for (PhongDTO room : rooms) {
            JPanel roomCard = createRoomCard(room);
            roomContainer.add(roomCard);
            roomContainer.add(Box.createVerticalStrut(10)); // Spacing between cards
        }
    }

    private JPanel createRoomCard(PhongDTO room) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Left: Image
        JLabel imageLabel = new JLabel();
        try {
            String hinhAnh = room.getHinhAnh();
            if (hinhAnh != null && !hinhAnh.isEmpty()) {
                ImageIcon icon = new ImageIcon(hinhAnh);
                Image scaledImage = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                imageLabel.setText("No Image");
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            imageLabel.setText("No Image");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            e.printStackTrace();
        }
        imageLabel.setPreferredSize(new Dimension(120, 120));
        card.add(imageLabel, BorderLayout.WEST);

        // Center: Room Details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);

        JLabel roomIdLabel = new JLabel("Số phòng: " + room.getMaP());
        roomIdLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel roomNameLabel = new JLabel("Tên phòng: " + room.getTenP());
        roomNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel priceLabel = new JLabel("Giá: " + room.getGiaP() + "đ");
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        detailsPanel.add(roomIdLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(roomNameLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(priceLabel);

        card.add(detailsPanel, BorderLayout.CENTER);

        // Right: Detail Button and Select Checkbox
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Detail Button
        JPanel detailButtonWrapper = new JPanel(new BorderLayout());
        detailButtonWrapper.setOpaque(false);
        detailButtonWrapper.setMaximumSize(new Dimension(120, 40));
        JButton detailButton = new JButton("Chi tiết");
        detailButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        detailButton.setBackground(Color.WHITE);
        detailButton.setForeground(new Color(66, 133, 244));
        detailButton.setBorder(BorderFactory.createLineBorder(new Color(66, 133, 244), 1));
        detailButton.setFocusPainted(false);
        detailButton.setPreferredSize(new Dimension(120, 40));
        FlatSVGIcon detailIcon = new FlatSVGIcon("icons/detail.svg", 20, 20);
        detailButton.setIcon(detailIcon);
        detailButton.addActionListener(e -> new RoomDetailsDialog().showDialog(room, this));
        detailButtonWrapper.add(detailButton, BorderLayout.CENTER);

        // Select Checkbox with a styled container
        JPanel checkBoxWrapper = new JPanel(new BorderLayout());
        checkBoxWrapper.setOpaque(false);
        checkBoxWrapper.setMaximumSize(new Dimension(120, 40));
        JCheckBox selectCheckBox = new JCheckBox("Chọn phòng");
        selectCheckBox.setFont(new Font("SansSerif", Font.BOLD, 14));
        selectCheckBox.setForeground(new Color(66, 133, 244));
        selectCheckBox.setFocusPainted(false);
        selectCheckBox.setOpaque(false);
        selectCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        selectCheckBox.setToolTipText(room.getMaP() + " - " + room.getTenP());
        JPanel checkBoxContainer = new JPanel(new BorderLayout());
        checkBoxContainer.setBackground(Color.WHITE);
        checkBoxContainer.setBorder(BorderFactory.createLineBorder(new Color(66, 133, 244), 1));
        checkBoxContainer.setPreferredSize(new Dimension(120, 40));
        checkBoxContainer.add(selectCheckBox, BorderLayout.CENTER);
        checkBoxWrapper.add(checkBoxContainer, BorderLayout.CENTER);

        roomCheckBoxes.add(selectCheckBox);

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(detailButtonWrapper);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(checkBoxWrapper);
        buttonPanel.add(Box.createVerticalGlue());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rightPanel.setOpaque(false);
        rightPanel.add(buttonPanel);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }
}
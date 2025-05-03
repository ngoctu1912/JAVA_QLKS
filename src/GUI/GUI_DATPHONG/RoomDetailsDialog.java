package GUI_DATPHONG;

import DTO.PhongDTO;
import DTO.ChiTietTienIchDTO;
import DTO.TienIchDTO;
import BUS.ChiTietTienIchBUS;
import BUS.TienIchBUS;
import config.ConnectDB;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class RoomDetailsDialog {
    private final ChiTietTienIchBUS chiTietTienIchBUS;
    private final TienIchBUS tienIchBUS;

    public RoomDetailsDialog() {
        Connection conn = ConnectDB.getConnection(); // Lấy Connection
        this.chiTietTienIchBUS = new ChiTietTienIchBUS(); // Giả sử cũng cần Connection
        this.tienIchBUS = new TienIchBUS();
    }

    public void showDialog(PhongDTO room, JComponent parent) {
        JDialog dialog = new JDialog((Frame) null, "Xem chi tiết phòng", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(900, 500);
        dialog.setLocationRelativeTo(parent);

        // Header
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(new Color(149, 213, 178));
        headerPanel.setPreferredSize(new Dimension(900, 50));
        JLabel headerLabel = new JLabel("XEM CHI TIẾT PHÒNG");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        headerPanel.add(headerLabel, new GridBagConstraints());
        dialog.add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Left: Image
        JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(270, 280));
        imageLabel.setBorder(BorderFactory.createLineBorder(new Color(61, 172, 120), 3));
        try {
            ImageIcon icon = new ImageIcon(room.getHinhAnh());
            Image scaledImage = icon.getImage().getScaledInstance(240, 290, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            imageLabel.setText("No Image");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        mainPanel.add(imageLabel, BorderLayout.WEST);

        // Right: Room Info Grid
        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

        infoPanel.add(createInfoPanel("Tên phòng", room.getTenP()));
        infoPanel.add(createInfoPanel("Loại phòng", room.getLoaiP()));
        infoPanel.add(createInfoPanel("Giá phòng", room.getGiaP() + " đ"));
        infoPanel.add(createInfoPanel("Chi tiết loại phòng", room.getChiTietLoaiPhong()));

        List<ChiTietTienIchDTO> amenities = chiTietTienIchBUS.getAmenitiesByRoomId(room.getMaP());
        String tenTienIch = amenities.stream()
                .map(ti -> {
                    try {
                        TienIchDTO tienIch = tienIchBUS.selectById(ti.getMaTI());
                        return tienIch != null ? tienIch.getTenTI() : "Unknown (" + ti.getMaTI() + ")";
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return "Error (" + ti.getMaTI() + ")";
                    }
                })
                .collect(Collectors.joining(", "));
        infoPanel.add(createInfoPanel("Tiện ích", tenTienIch.isEmpty() ? "Không có tiện ích" : tenTienIch));

        mainPanel.add(infoPanel, BorderLayout.CENTER);
        dialog.add(mainPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        JButton closeButton = new JButton("Huỷ bỏ");
        closeButton.setContentAreaFilled(true);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setOpaque(true);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.setBackground(new Color(247, 99, 99));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dialog.dispose());
        footerPanel.add(closeButton);
        dialog.add(footerPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel createInfoPanel(String labelText, String valueText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(0, 40));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setPreferredSize(new Dimension(0, 20));
        panel.add(label, BorderLayout.NORTH);

        JTextField textField = new JTextField(valueText);
        textField.setEditable(false);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(0, 40));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panel.add(textField, BorderLayout.CENTER);

        return panel;
    }
}
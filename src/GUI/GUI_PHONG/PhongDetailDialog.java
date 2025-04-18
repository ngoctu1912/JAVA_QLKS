package GUI_PHONG;

import DTO.PhongDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PhongDetailDialog extends JDialog {
    private PhongDTO room;

    public PhongDetailDialog(JFrame parent, PhongDTO room) {
        super(parent, "Chi Tiết Phòng", true);
        this.room = room;
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Background với gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(173, 220, 230), // Xanh dương nhạt
                        getWidth(), getHeight(), Color.WHITE);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        // Panel hình ảnh (bên trái)
        JPanel imagePanel = new JPanel();
        imagePanel.setOpaque(false);
        imagePanel.setLayout(new BorderLayout());
        try {
            // Tải hình ảnh dựa trên mã phòng (ví dụ: ./src/images/P101.jpg)
            String imagePath = "./images/" + room.getMaP() + ".jpg";
            ImageIcon roomImage = new ImageIcon(imagePath);
            if (roomImage.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image scaledImage = roomImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
                JLabel lblImage = new JLabel(new ImageIcon(scaledImage));
                lblImage.setHorizontalAlignment(SwingConstants.CENTER);
                imagePanel.add(lblImage, BorderLayout.CENTER);
            } else {
                throw new Exception("Hình ảnh không tải được");
            }
        } catch (Exception e) {
            JLabel lblPlaceholder = new JLabel("Hình ảnh không tìm thấy", SwingConstants.CENTER);
            lblPlaceholder.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            lblPlaceholder.setForeground(Color.RED);
            imagePanel.add(lblPlaceholder, BorderLayout.CENTER);
        }
        mainPanel.add(imagePanel, BorderLayout.WEST);

        // Panel thông tin (bên phải)
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        infoPanel.setLayout(new BorderLayout());

        // Tiêu đề
        JLabel lblTitle = new JLabel("Thông Tin Phòng", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 26));
        lblTitle.setForeground(new Color(0, 102, 153));
        infoPanel.add(lblTitle, BorderLayout.NORTH);

        // Panel chi tiết
        JPanel detailPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        detailPanel.setOpaque(false);

        Font labelFont = new Font("Times New Roman", Font.BOLD, 20);
        Color labelColor = new Color(0, 102, 153);
        Font valueFont = new Font("Times New Roman", Font.PLAIN, 20);
        Color valueColor = new Color(51, 51, 51);

        JLabel lblMaPhong = new JLabel("Mã Phòng:");
        lblMaPhong.setFont(labelFont);
        lblMaPhong.setForeground(labelColor);
        detailPanel.add(lblMaPhong);

        JLabel valMaPhong = new JLabel(room.getMaP());
        valMaPhong.setFont(valueFont);
        valMaPhong.setForeground(valueColor);
        detailPanel.add(valMaPhong);

        JLabel lblTenPhong = new JLabel("Tên Phòng:");
        lblTenPhong.setFont(labelFont);
        lblTenPhong.setForeground(labelColor);
        detailPanel.add(lblTenPhong);

        JLabel valTenPhong = new JLabel(room.getTenP());
        valTenPhong.setFont(valueFont);
        valTenPhong.setForeground(valueColor);
        detailPanel.add(valTenPhong);

        JLabel lblLoaiPhong = new JLabel("Loại Phòng:");
        lblLoaiPhong.setFont(labelFont);
        lblLoaiPhong.setForeground(labelColor);
        detailPanel.add(lblLoaiPhong);

        JLabel valLoaiPhong = new JLabel(room.getLoaiP());
        valLoaiPhong.setFont(valueFont);
        valLoaiPhong.setForeground(valueColor);
        detailPanel.add(valLoaiPhong);

        JLabel lblGiaPhong = new JLabel("Giá Phòng:");
        lblGiaPhong.setFont(labelFont);
        lblGiaPhong.setForeground(labelColor);
        detailPanel.add(lblGiaPhong);

        JLabel valGiaPhong = new JLabel(String.valueOf(room.getGiaP()) + " VNĐ");
        valGiaPhong.setFont(valueFont);
        valGiaPhong.setForeground(valueColor);
        detailPanel.add(valGiaPhong);

        JLabel lblChiTietLoaiPhong = new JLabel("Chi Tiết Loại Phòng:");
        lblChiTietLoaiPhong.setFont(labelFont);
        lblChiTietLoaiPhong.setForeground(labelColor);
        detailPanel.add(lblChiTietLoaiPhong);

        JLabel valChiTietLoaiPhong = new JLabel(room.getChiTietLoaiPhong());
        valChiTietLoaiPhong.setFont(valueFont);
        valChiTietLoaiPhong.setForeground(valueColor);
        detailPanel.add(valChiTietLoaiPhong);

        JLabel lblTinhTrang = new JLabel("Tình Trạng:");
        lblTinhTrang.setFont(labelFont);
        lblTinhTrang.setForeground(labelColor);
        detailPanel.add(lblTinhTrang);

        JLabel valTinhTrang = new JLabel(room.getTinhTrang() == 1 ? "Đã đặt" : "Trống");
        valTinhTrang.setFont(valueFont);
        valTinhTrang.setForeground(room.getTinhTrang() == 1 ? Color.RED : new Color(0, 128, 0));
        detailPanel.add(valTinhTrang);

        infoPanel.add(detailPanel, BorderLayout.CENTER);

        // Nút Đóng
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton btnClose = new JButton("Đóng");
        btnClose.setBackground(new Color(0, 153, 204));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnClose.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);

        infoPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(infoPanel, BorderLayout.CENTER);
    }

    public PhongDTO getRoom() { return room; }
}
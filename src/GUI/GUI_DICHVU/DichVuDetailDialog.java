// package GUI_DICHVU;

// import DTO.DichVuDTO;

// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;

// public class DichVuDetailDialog extends JDialog {
//     private DichVuDTO dichVu;

//     public DichVuDetailDialog(JFrame parent, DichVuDTO dichVu) {
//         super(parent, "Chi Tiết Dịch Vụ", true);
//         this.dichVu = dichVu;
//         setSize(700, 500);
//         setLocationRelativeTo(parent);
//         setLayout(new BorderLayout());

//         // Background với gradient
//         JPanel mainPanel = new JPanel() {
//             @Override
//             protected void paintComponent(Graphics g) {
//                 super.paintComponent(g);
//                 Graphics2D g2d = (Graphics2D) g;
//                 GradientPaint gradient = new GradientPaint(
//                         0, 0, new Color(173, 220, 230), // Xanh dương nhạt, match PhongDetailDialog
//                         getWidth(), getHeight(), Color.WHITE);
//                 g2d.setPaint(gradient);
//                 g2d.fillRect(0, 0, getWidth(), getHeight());
//             }
//         };
//         mainPanel.setLayout(new BorderLayout(10, 10));
//         mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
//         add(mainPanel);

//         // Panel hình ảnh (bên trái)
//         JPanel imagePanel = new JPanel();
//         imagePanel.setOpaque(false);
//         imagePanel.setLayout(new BorderLayout());
//         try {
//             // Tải hình ảnh dựa trên mã dịch vụ (ví dụ: ./images/DV001.jpg)
//             String imagePath = "./images/" + dichVu.getMaDV() + ".jpg";
//             ImageIcon dichVuImage = new ImageIcon(imagePath);
//             if (dichVuImage.getImageLoadStatus() == MediaTracker.COMPLETE) {
//                 Image scaledImage = dichVuImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
//                 JLabel lblImage = new JLabel(new ImageIcon(scaledImage));
//                 lblImage.setHorizontalAlignment(SwingConstants.CENTER);
//                 imagePanel.add(lblImage, BorderLayout.CENTER);
//             } else {
//                 throw new Exception("Hình ảnh không tải được");
//             }
//         } catch (Exception e) {
//             JLabel lblPlaceholder = new JLabel("Hình ảnh không tìm thấy", SwingConstants.CENTER);
//             lblPlaceholder.setFont(new Font("Times New Roman", Font.PLAIN, 20));
//             lblPlaceholder.setForeground(Color.RED);
//             imagePanel.add(lblPlaceholder, BorderLayout.CENTER);
//         }
//         mainPanel.add(imagePanel, BorderLayout.WEST);

//         // Panel thông tin (bên phải)
//         JPanel infoPanel = new JPanel();
//         infoPanel.setBackground(Color.WHITE);
//         infoPanel.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
//             BorderFactory.createEmptyBorder(15, 15, 15, 15)
//         ));
//         infoPanel.setLayout(new BorderLayout());

//         // Tiêu đề
//         JLabel lblTitle = new JLabel("Thông Tin Dịch Vụ", SwingConstants.CENTER);
//         lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 26));
//         lblTitle.setForeground(new Color(0, 102, 153));
//         infoPanel.add(lblTitle, BorderLayout.NORTH);

//         // Panel chi tiết
//         JPanel detailPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // 5 rows for 5 fields
//         detailPanel.setOpaque(false);

//         Font labelFont = new Font("Times New Roman", Font.BOLD, 20);
//         Color labelColor = new Color(0, 102, 153);
//         Font valueFont = new Font("Times New Roman", Font.PLAIN, 20);
//         Color valueColor = new Color(51, 51, 51);

//         JLabel lblMaDichVu = new JLabel("Mã Dịch Vụ:");
//         lblMaDichVu.setFont(labelFont);
//         lblMaDichVu.setForeground(labelColor);
//         detailPanel.add(lblMaDichVu);

//         JLabel valMaDichVu = new JLabel(dichVu.getMaDV());
//         valMaDichVu.setFont(valueFont);
//         valMaDichVu.setForeground(valueColor);
//         detailPanel.add(valMaDichVu);

//         JLabel lblTenDichVu = new JLabel("Tên Dịch Vụ:");
//         lblTenDichVu.setFont(labelFont);
//         lblTenDichVu.setForeground(labelColor);
//         detailPanel.add(lblTenDichVu);

//         JLabel valTenDichVu = new JLabel(dichVu.getTenDV());
//         valTenDichVu.setFont(valueFont);
//         valTenDichVu.setForeground(valueColor);
//         detailPanel.add(valTenDichVu);

//         JLabel lblLoaiDichVu = new JLabel("Loại Dịch Vụ:");
//         lblLoaiDichVu.setFont(labelFont);
//         lblLoaiDichVu.setForeground(labelColor);
//         detailPanel.add(lblLoaiDichVu);

//         JLabel valLoaiDichVu = new JLabel(dichVu.getLoaiDV());
//         valLoaiDichVu.setFont(valueFont);
//         valLoaiDichVu.setForeground(valueColor);
//         detailPanel.add(valLoaiDichVu);

//         JLabel lblSoLuong = new JLabel("Số Lượng:");
//         lblSoLuong.setFont(labelFont);
//         lblSoLuong.setForeground(labelColor);
//         detailPanel.add(lblSoLuong);

//         JLabel valSoLuong = new JLabel(String.valueOf(dichVu.getSoLuong()));
//         valSoLuong.setFont(valueFont);
//         valSoLuong.setForeground(valueColor);
//         detailPanel.add(valSoLuong);

//         JLabel lblGiaDichVu = new JLabel("Giá Dịch Vụ:");
//         lblGiaDichVu.setFont(labelFont);
//         lblGiaDichVu.setForeground(labelColor);
//         detailPanel.add(lblGiaDichVu);

//         JLabel valGiaDichVu = new JLabel(String.valueOf(dichVu.getGiaDV()) + " VNĐ");
//         valGiaDichVu.setFont(valueFont);
//         valGiaDichVu.setForeground(valueColor);
//         detailPanel.add(valGiaDichVu);

//         infoPanel.add(detailPanel, BorderLayout.CENTER);

//         // Nút Đóng
//         JPanel buttonPanel = new JPanel();
//         buttonPanel.setOpaque(false);
//         buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

//         JButton btnClose = new JButton("Đóng");
//         btnClose.setBackground(new Color(0, 153, 204));
//         btnClose.setForeground(Color.WHITE);
//         btnClose.setFont(new Font("Times New Roman", Font.BOLD, 20));
//         btnClose.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
//         btnClose.setFocusPainted(false);
//         btnClose.addActionListener(e -> dispose());
//         buttonPanel.add(btnClose);

//         infoPanel.add(buttonPanel, BorderLayout.SOUTH);

//         mainPanel.add(infoPanel, BorderLayout.CENTER);
//     }

//     public DichVuDTO getDichVu() { return dichVu; }
// }

package GUI_DICHVU;

import DTO.DichVuDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DichVuDetailDialog extends JDialog {
    private DichVuDTO dichVu;

    public DichVuDetailDialog(Component parentComponent, DichVuDTO dichVu) { // Đổi từ JFrame thành Component
        super((JFrame) SwingUtilities.getWindowAncestor(parentComponent), "Chi Tiết Dịch Vụ", true); // Lấy JFrame từ parentComponent
        this.dichVu = dichVu;
        setSize(700, 500);
        setLocationRelativeTo(parentComponent);
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
            String imagePath = "./images/" + dichVu.getMaDV() + ".jpg";
            ImageIcon dichVuImage = new ImageIcon(imagePath);
            if (dichVuImage.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image scaledImage = dichVuImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
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
        JLabel lblTitle = new JLabel("Thông Tin Dịch Vụ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 26));
        lblTitle.setForeground(new Color(0, 102, 153));
        infoPanel.add(lblTitle, BorderLayout.NORTH);

        // Panel chi tiết
        JPanel detailPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        detailPanel.setOpaque(false);

        Font labelFont = new Font("Times New Roman", Font.BOLD, 20);
        Color labelColor = new Color(0, 102, 153);
        Font valueFont = new Font("Times New Roman", Font.PLAIN, 20);
        Color valueColor = new Color(51, 51, 51);

        JLabel lblMaDichVu = new JLabel("Mã Dịch Vụ:");
        lblMaDichVu.setFont(labelFont);
        lblMaDichVu.setForeground(labelColor);
        detailPanel.add(lblMaDichVu);

        JLabel valMaDichVu = new JLabel(dichVu.getMaDV());
        valMaDichVu.setFont(valueFont);
        valMaDichVu.setForeground(valueColor);
        detailPanel.add(valMaDichVu);

        JLabel lblTenDichVu = new JLabel("Tên Dịch Vụ:");
        lblTenDichVu.setFont(labelFont);
        lblTenDichVu.setForeground(labelColor);
        detailPanel.add(lblTenDichVu);

        JLabel valTenDichVu = new JLabel(dichVu.getTenDV());
        valTenDichVu.setFont(valueFont);
        valTenDichVu.setForeground(valueColor);
        detailPanel.add(valTenDichVu);

        JLabel lblLoaiDichVu = new JLabel("Loại Dịch Vụ:");
        lblLoaiDichVu.setFont(labelFont);
        lblLoaiDichVu.setForeground(labelColor);
        detailPanel.add(lblLoaiDichVu);

        JLabel valLoaiDichVu = new JLabel(dichVu.getLoaiDV());
        valLoaiDichVu.setFont(valueFont);
        valLoaiDichVu.setForeground(valueColor);
        detailPanel.add(valLoaiDichVu);

        JLabel lblSoLuong = new JLabel("Số Lượng:");
        lblSoLuong.setFont(labelFont);
        lblSoLuong.setForeground(labelColor);
        detailPanel.add(lblSoLuong);

        JLabel valSoLuong = new JLabel(String.valueOf(dichVu.getSoLuong()));
        valSoLuong.setFont(valueFont);
        valSoLuong.setForeground(valueColor);
        detailPanel.add(valSoLuong);

        JLabel lblGiaDichVu = new JLabel("Giá Dịch Vụ:");
        lblGiaDichVu.setFont(labelFont);
        lblGiaDichVu.setForeground(labelColor);
        detailPanel.add(lblGiaDichVu);

        JLabel valGiaDichVu = new JLabel(String.valueOf(dichVu.getGiaDV()) + " VNĐ");
        valGiaDichVu.setFont(valueFont);
        valGiaDichVu.setForeground(valueColor);
        detailPanel.add(valGiaDichVu);

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

    public DichVuDTO getDichVu() { return dichVu; }
}
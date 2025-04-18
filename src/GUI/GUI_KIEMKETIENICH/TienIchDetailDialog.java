package GUI_KIEMKETIENICH;

import DTO.TienIchDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TienIchDetailDialog extends JDialog {
    private TienIchDTO tienIch;

    public TienIchDetailDialog(JFrame parent, TienIchDTO tienIch) {
        super(parent, "Chi Tiết Tiện Ích", true);
        this.tienIch = tienIch;
        setSize(500, 400);
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

        // Panel thông tin
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        infoPanel.setLayout(new BorderLayout());

        // Tiêu đề
        JLabel lblTitle = new JLabel("Thông Tin Tiện Ích", SwingConstants.CENTER);
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

        // Mã tiện ích
        JLabel lblMaTI = new JLabel("Mã Tiện Ích:");
        lblMaTI.setFont(labelFont);
        lblMaTI.setForeground(labelColor);
        detailPanel.add(lblMaTI);

        JLabel valMaTI = new JLabel(tienIch.getMaTI());
        valMaTI.setFont(valueFont);
        valMaTI.setForeground(valueColor);
        detailPanel.add(valMaTI);

        // Tên tiện ích
        JLabel lblTenTI = new JLabel("Tên Tiện Ích:");
        lblTenTI.setFont(labelFont);
        lblTenTI.setForeground(labelColor);
        detailPanel.add(lblTenTI);

        JLabel valTenTI = new JLabel(tienIch.getTenTI());
        valTenTI.setFont(valueFont);
        valTenTI.setForeground(valueColor);
        detailPanel.add(valTenTI);

        // Số lượng tổng
        JLabel lblTotalQuantity = new JLabel("Số Lượng Tổng:");
        lblTotalQuantity.setFont(labelFont);
        lblTotalQuantity.setForeground(labelColor);
        detailPanel.add(lblTotalQuantity);

        JLabel valTotalQuantity = new JLabel(String.valueOf(tienIch.getTotalQuantity()));
        valTotalQuantity.setFont(valueFont);
        valTotalQuantity.setForeground(valueColor);
        detailPanel.add(valTotalQuantity);

        // Số lượng còn lại
        JLabel lblRemainingQuantity = new JLabel("Số Lượng Còn Lại:");
        lblRemainingQuantity.setFont(labelFont);
        lblRemainingQuantity.setForeground(labelColor);
        detailPanel.add(lblRemainingQuantity);

        JLabel valRemainingQuantity = new JLabel(String.valueOf(tienIch.getRemainingQuantity()));
        valRemainingQuantity.setFont(valueFont);
        valRemainingQuantity.setForeground(valueColor);
        detailPanel.add(valRemainingQuantity);

        // Trạng thái
        JLabel lblXuLy = new JLabel("Trạng Thái:");
        lblXuLy.setFont(labelFont);
        lblXuLy.setForeground(labelColor);
        detailPanel.add(lblXuLy);

        JLabel valXuLy = new JLabel(tienIch.getXuLy() == 1 ? "Hoạt động" : "Ngừng");
        valXuLy.setFont(valueFont);
        valXuLy.setForeground(tienIch.getXuLy() == 1 ? new Color(0, 128, 0) : Color.RED);
        detailPanel.add(valXuLy);

        infoPanel.add(detailPanel, BorderLayout.CENTER);

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton btnClose = new JButton("Đóng");
        btnClose.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnClose.setBackground(new Color(255, 102, 102));
        btnClose.setForeground(Color.WHITE);
        btnClose.setPreferredSize(new Dimension(100, 35));
        btnClose.setBorder(BorderFactory.createEmptyBorder());
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
}
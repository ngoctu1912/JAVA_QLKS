package GUI_MENU;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class MenuViewComponent {
    private static final Color COLOR_ACTIVE = new Color(220, 245, 218);
    private static final Color COLOR_DEFAULT = Color.WHITE;

    private JPanel headerPanel;
    private JLabel helloLabel;
    private JSeparator separator;
    private JButton btnTrangChu;
    private JButton btnDatPhong;
    private JButton btnDangNhap;

    public MenuViewComponent(MenuViewFrame menuFrame) {
        initComponents();
    }

    private void initComponents() {
        // Header: Avatar và lời chào
        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        FlatSVGIcon avatarIcon = new FlatSVGIcon("icons/avatar.svg", 42, 42);
        JLabel avatarLabel = new JLabel(avatarIcon);
        avatarLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        helloLabel = new JLabel("Xin chào quý khách!");
        helloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        textPanel.add(helloLabel);

        JLabel modeLabel = new JLabel("Chế độ xem");
        modeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        modeLabel.setForeground(Color.GRAY);
        textPanel.add(modeLabel);

        headerPanel.add(avatarLabel);
        headerPanel.add(textPanel);

        // Đường phân cách
        separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(new Color(220, 220, 220)); // Xám nhẹ hơn

        // Các nút điều hướng
        btnTrangChu = createButton("Trang chủ", "icons/home.svg", true);
        btnDatPhong = createButton("Đặt phòng", "icons/datphong.svg", false);
        btnDangNhap = createButton("Đăng nhập", "icons/login.svg", false);
    }

    private JButton createButton(String text, String iconPath, boolean isActive) {
        FlatSVGIcon icon = new FlatSVGIcon(iconPath, 30, 30);

        // JButton button = new JButton(text, icon);
        RoundedMenuButton button = new RoundedMenuButton(text, icon);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setPreferredSize(new Dimension(250, 50));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Font to và hiện đại hơn
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setForeground(Color.BLACK);
        button.setSelected(isActive);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        // Căn trái icon + chữ + khoảng cách hợp lý
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(15); // ← Khoảng cách icon - chữ

        return button;
    }

    // Getter để truy cập các thành phần
    public Component getVerticalStrut(int height) {
        return Box.createVerticalStrut(height);
    }

    public Component getVerticalGlue() {
        return Box.createVerticalGlue();
    }

    public JPanel getHeaderPanel() {
        return headerPanel;
    }

    public JLabel getHelloLabel() {
        return helloLabel;
    }

    public JSeparator getSeparator() {
        return separator;
    }

    public JButton getBtnTrangChu() {
        return btnTrangChu;
    }

    public JButton getBtnDatPhong() {
        return btnDatPhong;
    }

    public JButton getBtnDangNhap() {
        return btnDangNhap;
    }

    // Getter cho màu sắc để sử dụng trong sự kiện
    public Color getColorActive() {
        return COLOR_ACTIVE;
    }

    public Color getColorDefault() {
        return COLOR_DEFAULT;
    }
}
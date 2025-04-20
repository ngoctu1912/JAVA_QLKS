package GUI_TRANGCHU;

import GUI_MENU.RoundedMenuButton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrangChuKHComponent extends JPanel {
    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color FONT_COLOR = Color.BLACK;
    private static final Color HOVER_COLOR = Color.decode("#edeff0");
    private static final Color SELECTED_COLOR = new Color(220, 245, 218);

    private final String[][] menuItems = {
            {"Trang chủ", "home.svg", "TrangChuGUI"},
            {"Phòng", "room.svg", "PhongGUI"},
            {"Khách hàng", "khachhang.svg", "KhachHangGUI"},
            {"Hóa đơn", "hoadon.svg", "HoaDonGUI"},
            {"Đăng xuất", "logout.svg", "Logout"}
    };

    private String username;
    private JPanel headerPanel;
    private JLabel lblHello, lblRole;
    private JSeparator separator;
    private List<JButton> menuButtons;

    public TrangChuKHComponent(String username) {
        this.username = username;
        this.menuButtons = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(DEFAULT_COLOR);
        setPreferredSize(new Dimension(250, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.LIGHT_GRAY));
        initComponents();
    }

    private void initComponents() {
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.setBackground(DEFAULT_COLOR);
        wrapperPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        createHeaderPanel();
        separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(new Color(220, 220, 220));

        wrapperPanel.add(Box.createVerticalStrut(10));
        wrapperPanel.add(headerPanel);
        wrapperPanel.add(Box.createVerticalStrut(10));
        wrapperPanel.add(separator);
        wrapperPanel.add(Box.createVerticalStrut(10));

        for (int i = 0; i < menuItems.length - 1; i++) {
            String[] item = menuItems[i];
            JButton button = createMenuButton(item[0], "icons/" + item[1], item[2], item[0].equals("Trang chủ"));
            wrapperPanel.add(button);
            wrapperPanel.add(Box.createVerticalStrut(10));
            menuButtons.add(button);
        }

        wrapperPanel.add(Box.createVerticalGlue());

        String[] logoutItem = menuItems[menuItems.length - 1];
        JButton logoutButton = createMenuButton(logoutItem[0], "icons/" + logoutItem[1], logoutItem[2], false);
        wrapperPanel.add(logoutButton);
        menuButtons.add(logoutButton);

        add(wrapperPanel);
    }

    private void createHeaderPanel() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(DEFAULT_COLOR);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel avatarLabel = new JLabel();
        avatarLabel.setIcon(loadIcon("icons/avatar.svg"));
        avatarLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(DEFAULT_COLOR);

        lblHello = new JLabel("Xin chào, " + username + "!");
        lblHello.setFont(new Font("Arial", Font.BOLD, 16));
        textPanel.add(lblHello);

        lblRole = new JLabel("Khách hàng");
        lblRole.setFont(new Font("Arial", Font.PLAIN, 13));
        lblRole.setForeground(Color.GRAY);
        textPanel.add(lblRole);

        headerPanel.add(avatarLabel);
        headerPanel.add(textPanel);
    }

    private JButton createMenuButton(String text, String iconPath, String actionCommand, boolean isActive) {
        Icon icon = loadIcon(iconPath);
        RoundedMenuButton button = new RoundedMenuButton(text, icon);
        button.setActionCommand(actionCommand);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setPreferredSize(new Dimension(220, 50));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setForeground(FONT_COLOR);
        button.setSelected(isActive);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(15);
        return button;
    }

    private Icon loadIcon(String path) {
        try {
            FlatSVGIcon icon = new FlatSVGIcon(path);
            icon = new FlatSVGIcon(path, 30, 30);
            if (icon != null) {
                return icon;
            }
        } catch (Exception e) {
            System.err.println("Lỗi: Không thể load icon tại " + path + ": " + e.getMessage());
        }
        try {
            FlatSVGIcon defaultIcon = new FlatSVGIcon("icons/avatar.svg", 30, 30);
            return defaultIcon;
        } catch (Exception e) {
            System.err.println("Lỗi: Không thể load icon mặc định: " + e.getMessage());
            return null;
        }
    }

    public List<JButton> getMenuButtons() {
        return menuButtons;
    }

    public Color getDefaultColor() {
        return DEFAULT_COLOR;
    }

    public Color getHoverColor() {
        return HOVER_COLOR;
    }

    public Color getSelectedColor() {
        return SELECTED_COLOR;
    }

    public JLabel getLblHello() {
        return lblHello;
    }

    public JLabel getLblRole() {
        return lblRole;
    }

    public JSeparator getSeparator() {
        return separator;
    }
}
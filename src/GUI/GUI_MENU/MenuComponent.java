package GUI_MENU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;

public class MenuComponent extends JPanel {
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private JLabel lblAvatar;
    private JLabel lblUsername;
    private JLabel lblRole;
    private final ArrayList<JButton> menuButtons = new ArrayList<>();
    private Color defaultColor = new Color(255, 255, 255);
    private Color fontColor = Color.BLACK;
    private Color hoverColor = new Color(237, 239, 240);
    private Color selectedColor = new Color(220, 245, 218);
    private AccountInfo accountInfo;

    private final String[][] menuItems = {
            { "Trang chủ", "home.svg", "TrangChuGUI" },
            { "Phòng", "room.svg", "PhongGUI" },
            { "Đặt phòng", "datphong.svg", "DatPhongGUI" },
            { "Khách hàng", "khachhang.svg", "KhachHangGUI" },
            { "Dịch vụ", "dichvu.svg", "DichVuGUI" },
            { "Nhân viên", "nhanvien.svg", "NhanVienGUI" },
            { "Hóa đơn", "hoadon.svg", "HoaDonGUI" },
            { "Kiểm kê tiện ích", "tienich.svg", "KiemKeTienIchGUI" },
            { "Phân quyền", "nhomquyen.svg", "PhanQuyenGUI" },
            { "Tài khoản", "taikhoan.svg", "TaiKhoanGUI" },
            { "Tài khoản khách hàng", "client_acc.svg", "TaiKhoanKHGUI" },
            { "Thống kê", "thongke.svg", "ThongKeGUI" },
            { "Đăng xuất", "logout.svg", "Logout" }
    };

    public MenuComponent(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
        String username = (accountInfo != null && accountInfo.getFullName() != null) ? accountInfo.getFullName() : "unknown";
        initComponents(username);
    }

    private void initComponents(String username) {
        setLayout(new BorderLayout());

        // Top panel (avatar, greeting, role)
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setPreferredSize(new Dimension(250, 74));
        topPanel.setBackground(Color.WHITE); 
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Avatar icon
        FlatSVGIcon avatarIcon = new FlatSVGIcon("icons/avatar.svg", 42, 42);
        lblAvatar = new JLabel(avatarIcon);
        lblAvatar.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10)); 

        // Text panel (greeting, role)
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE); // Match MenuViewComponent

        lblUsername = new JLabel(username);
        lblUsername.setFont(new Font("Arial", Font.BOLD, 16)); // Match MenuViewComponent
        lblUsername.setForeground(Color.BLACK);
        textPanel.add(lblUsername);

        lblRole = new JLabel((accountInfo != null && accountInfo.getRole() != null) ? accountInfo.getRole() : "unknown trading account");
        lblRole.setFont(new Font("Arial", Font.PLAIN, 13)); // Match MenuViewComponent
        lblRole.setForeground(Color.GRAY); // Match MenuViewComponent
        textPanel.add(lblRole);

        topPanel.add(lblAvatar);
        topPanel.add(textPanel); // No extra strut to match MenuViewComponent

        // Center panel (menu items)
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(defaultColor);
        centerPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Bottom panel (logout)
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(250, 60));
        bottomPanel.setBackground(defaultColor);
        bottomPanel.setBorder(new EmptyBorder(5, 10, 10, 10));

        // Add panels to layout
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public Icon loadIcon(String path, int width, int height) {
        try {
            FlatSVGIcon svgIcon = new FlatSVGIcon(path);
            return svgIcon.derive(width, height);
        } catch (Exception e) {
            System.err.println("Warning: Failed to load SVG icon at " + path + ": " + e.getMessage());
        }
        try {
            FlatSVGIcon defaultIcon = new FlatSVGIcon("icons/avatar.svg");
            return defaultIcon.derive(width, height);
        } catch (Exception e) {
            System.err.println("Warning: Failed to load default SVG icon: " + e.getMessage());
            return new ImageIcon();
        }
    }

    public JButton addMenuItem(String text, String iconPath, String actionCommand, JPanel parent) {
        RoundedMenuButton menuItem = new RoundedMenuButton(text);
        menuItem.setIcon(loadIcon(iconPath, 30, 30));
        menuItem.setActionCommand(actionCommand);
        menuItem.setHorizontalAlignment(SwingConstants.LEFT);
        menuItem.setIconTextGap(15);
        menuItem.setBorder(new EmptyBorder(8, 15, 8, 15));
        menuItem.setBackground(defaultColor);
        menuItem.setForeground(fontColor);
        menuItem.setFocusPainted(false);
        menuItem.setPreferredSize(new Dimension(250, 50));
        menuItem.setMaximumSize(new Dimension(250, 50));
        menuItem.setMinimumSize(new Dimension(250, 50));
        menuItem.setFont(new Font("Arial", Font.PLAIN, 16));

        parent.add(menuItem);
        if (parent == centerPanel) {
            parent.add(Box.createVerticalStrut(10));
        }
        menuButtons.add(menuItem);
        return menuItem;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public JLabel getLblAvatar() {
        return lblAvatar;
    }

    public JLabel getLblUsername() {
        return lblUsername;
    }

    public JLabel getLblRole() {
        return lblRole;
    }

    public ArrayList<JButton> getMenuButtons() {
        return menuButtons;
    }

    public String[][] getMenuItems() {
        return menuItems;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public Color getFontColor() {
        return fontColor;
    }
}


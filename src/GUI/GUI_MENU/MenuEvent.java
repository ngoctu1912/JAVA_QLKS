package GUI_MENU;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Cursor;
import java.util.ArrayList;
import DTO.DanhMucChucNangDTO;
import BUS.NhomQuyenBUS;
import DAO.TaiKhoanDAO;
import DAO.TaiKhoanKHDAO;
import DTO.TaiKhoanDTO;
import DTO.TaiKhoanKHDTO;
import GUI_TRANGCHU.TrangChu;
import GUI_TRANGCHU.HomeFrame;
import GUI_DANGNHAP_DANGKY.CustomMessage;
import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;

public class MenuEvent {
    private MenuGUI menuGUI;
    private MenuComponent components;
    private String currentUser;
    private AccountInfo accountInfo;
    private int maNhomQuyen;
    private ArrayList<DanhMucChucNangDTO> danhSachChucNang;

    public MenuEvent(MenuGUI menuGUI, MenuComponent components, AccountInfo accountInfo) {
        this.menuGUI = menuGUI;
        this.components = components;
        this.accountInfo = accountInfo;
        this.currentUser = menuGUI.getMenuComponents().getLblUsername().getText().isEmpty()
                ? "unknown"
                : menuGUI.getMenuComponents().getLblUsername().getText();
        initEvents();
        loadUserInfo();
    }

    private void initEvents() {
        // Sự kiện cho các menu item sẽ được thêm trong createMenuItems
    }

    private void loadUserInfo() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (accountInfo != null) {
                    SwingUtilities.invokeLater(() -> {
                        components.getLblUsername().setText(accountInfo.getFullName());
                        components.getLblRole().setText(accountInfo.getRole());
                        components.getLblAvatar().setIcon(components.loadIcon("icons/avatar.svg", 42, 42));
                    });

                    System.out.println("AccountInfo: id=" + accountInfo.getAccountId() + ", type=" + accountInfo.getAccountType());

                    // Lấy mã nhóm quyền từ tài khoản
                    if (accountInfo.getAccountType().equals("QUANLY")) {
                        TaiKhoanDTO tk = TaiKhoanDAO.getInstance()
                                .selectById(String.valueOf(accountInfo.getAccountId()));
                        if (tk != null) {
                            maNhomQuyen = tk.getMaNhomQuyen();
                            System.out.println("maNhomQuyen (QUANLY): " + maNhomQuyen);
                        } else {
                            System.err.println("Error: TaiKhoanDTO is null for accountId: " + accountInfo.getAccountId());
                        }
                    } else if (accountInfo.getAccountType().equals("KHACHHANG")) {
                        TaiKhoanKHDTO tkKH = TaiKhoanKHDAO.getInstance()
                                .selectById(String.valueOf(accountInfo.getAccountId()));
                        if (tkKH != null) {
                            maNhomQuyen = tkKH.getMaNhomQuyen();
                            System.out.println("maNhomQuyen (KHACHHANG): " + maNhomQuyen);
                        } else {
                            System.err.println("Error: TaiKhoanKHDTO is null for accountId: " + accountInfo.getAccountId());
                        }
                    } else {
                        System.err.println("Error: Unknown account type: " + accountInfo.getAccountType());
                    }
                } else {
                    System.err.println("Error: accountInfo is null");
                }
                return null;
            }

            @Override
            protected void done() {
                System.out.println("Starting createMenuItems with maNhomQuyen: " + maNhomQuyen);
                createMenuItems();
            }
        }.execute();
    }

    private void createMenuItems() {
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        System.out.println("Creating menu items...");

        String[][] menuItems = components.getMenuItems();
        String[] mcnMapping = new String[menuItems.length];
        for (int i = 0; i < menuItems.length; i++) {
            switch (menuItems[i][0]) {
                case "Trang chủ":
                    mcnMapping[i] = "0";
                    break;
                case "Phòng":
                    mcnMapping[i] = "1";
                    break;
                case "Đặt phòng":
                    mcnMapping[i] = "2";
                    break;
                case "Khách hàng":
                    mcnMapping[i] = "3";
                    break;
                case "Dịch vụ":
                    mcnMapping[i] = "4";
                    break;
                case "Nhân viên":
                    mcnMapping[i] = "5";
                    break;
                case "Hóa đơn":
                    mcnMapping[i] = "8";
                    break;
                case "Kiểm kê tiện ích":
                    mcnMapping[i] = "10";
                    break;
                case "Phân quyền":
                    mcnMapping[i] = "11";
                    break;
                case "Tài khoản":
                    mcnMapping[i] = "12";
                    break;
                case "Thống kê":
                    mcnMapping[i] = "14";
                    break;
                case "Đăng xuất":
                    mcnMapping[i] = "0";
                    break;
                default:
                    mcnMapping[i] = "0";
            }
        }

        for (int i = 0; i < menuItems.length; i++) {
            String menuText = menuItems[i][0];
            String iconPath = "icons/" + menuItems[i][1];
            String actionCommand = menuItems[i][2];
            String mcn = mcnMapping[i];

            boolean hasPermission = mcn.equals("0");
            if (!hasPermission) {
                hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, mcn, "view");
                System.out.println("Checking permission: maNhomQuyen=" + maNhomQuyen + ", mcn=" + mcn + ", menu=" + menuText + ", hasPermission=" + hasPermission);
            }

            if (!hasPermission) {
                System.out.println("Skipping menu item: " + menuText + " due to no permission");
                continue;
            }

            JPanel parent = actionCommand.equals("Logout")
                    ? components.getBottomPanel()
                    : components.getCenterPanel();

            JButton menuItem = components.addMenuItem(menuText, iconPath, actionCommand, parent);
            System.out.println("Added menu item: " + menuText);

            menuItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!menuItem.getBackground().equals(components.getSelectedColor())) {
                        menuItem.setBackground(components.getHoverColor());
                    }
                    menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!menuItem.getBackground().equals(components.getSelectedColor())) {
                        menuItem.setBackground(components.getDefaultColor());
                    }
                    menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            });

            menuItem.addActionListener(e -> {
                for (JButton btn : components.getMenuButtons()) {
                    if (btn instanceof RoundedMenuButton rbtn) {
                        rbtn.setSelected(false);
                        rbtn.setForeground(components.getFontColor());
                    }
                }

                ((RoundedMenuButton) menuItem).setSelected(true);
                menuItem.setBackground(components.getSelectedColor());

                if (actionCommand.equals("Logout")) {
                    logout();
                } else {
                    openModule(actionCommand);
                }
            });
        }

        if (danhSachChucNang != null) {
            for (DanhMucChucNangDTO cn : danhSachChucNang) {
                boolean exists = false;
                for (String[] item : components.getMenuItems()) {
                    if (item[2].equals(cn.getMCN() + "GUI")) {
                        exists = true;
                        break;
                    }
                }
                if (!exists && nhomQuyenBUS.checkPermission(maNhomQuyen, String.valueOf(cn.getMCN()), "view")) {
                    components.addMenuItem(cn.getTENCN(), "icons/avatar.svg", cn.getMCN() + "GUI",
                            components.getCenterPanel());
                    System.out.println("Added dynamic menu item: " + cn.getTENCN());
                }
            }
        }

        if (!components.getMenuButtons().isEmpty()) {
            JButton firstBtn = components.getMenuButtons().get(0);
            if (firstBtn instanceof RoundedMenuButton rbtn) {
                rbtn.setSelected(true);
                firstBtn.setBackground(components.getSelectedColor());
            }
        }

        System.out.println("Menu items added, revalidating centerPanel...");
        components.getCenterPanel().revalidate();
        components.getCenterPanel().repaint();
        components.getBottomPanel().revalidate();
        components.getBottomPanel().repaint();
    }

    private void openModule(String moduleName) {
        TrangChu homeFrame = menuGUI.getTrangChu();
        if (homeFrame != null) {
            homeFrame.showPanel(moduleName);
        } else {
            System.err.println("Error: HomeFrame is null in MenuEvent.openModule");
        }
    }

    private void logout() {
        CustomMessage customMessage = new CustomMessage();
        int confirm = customMessage.showConfirmMessage(
                "Xác nhận đăng xuất",
                "Bạn có chắc chắn muốn đăng xuất?");

        if (confirm == JOptionPane.YES_OPTION) {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(menuGUI);
            if (currentFrame != null) {
                currentFrame.dispose();
            }

            components.getLblUsername().setText("");
            components.getLblRole().setText("");
            components.getLblAvatar().setIcon(null);
            currentUser = "unknown";
            maNhomQuyen = 0;
            danhSachChucNang = null;

            new HomeFrame().setVisible(true);
        }
    }
}
package GUI_TRANGCHU;

import javax.swing.*;
import java.awt.*;

import GUI_DANGNHAP_DANGKY.DNDKFrame;
import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;
import GUI_DATPHONG.FindRoom;
import GUI_MENU.MenuViewFrame;

public class HomeFrame extends JFrame {
    private JPanel currentMenuPanel; // Lưu panel hiện tại (MenuViewFrame hoặc MenuGUI)
    private JPanel currentContentPanel; // Lưu panel nội dung hiện tại
    private AccountInfo accountInfo; // Lưu thông tin tài khoản sau khi đăng nhập

    public HomeFrame() {
        initFrame();
    }

    private void initFrame() {
        setTitle("Quản lý khách sạn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Hiển thị giao diện khách (MenuViewFrame) mặc định
        currentMenuPanel = new MenuViewFrame(this);
        add(currentMenuPanel, BorderLayout.WEST);

        // Nội dung mặc định (trang chủ khách)
        currentContentPanel = new MainContentFrame();
        add(currentContentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void showPanel(String panelName) {
        // Xóa nội dung hiện tại
        if (currentContentPanel != null) {
            remove(currentContentPanel);
        }

        // Chọn panel nội dung dựa trên panelName
        switch (panelName) {
            case "TrangChu":
                currentContentPanel = new MainContentFrame();
                break;
            case "DatPhong":
                currentContentPanel = new JPanel(new BorderLayout());
                FindRoom findRoomPanel = new FindRoom();
                currentContentPanel.add(findRoomPanel, BorderLayout.CENTER);
                break;
            default:
                currentContentPanel = new JPanel(new BorderLayout());
                currentContentPanel.add(new JLabel("Chức năng chưa triển khai", SwingConstants.CENTER));
                break;
        }

        add(currentContentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showLoginFrame() {
        DNDKFrame loginFrame = new DNDKFrame(accountInfo -> {
            // Lưu thông tin tài khoản sau khi đăng nhập
            this.accountInfo = accountInfo;
            // Sau khi đăng nhập thành công, chuyển sang TrangChu
            getContentPane().removeAll(); // Xóa MenuViewFrame và nội dung cũ
            TrangChu trangChu = new TrangChu(accountInfo);
            add(trangChu, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        loginFrame.setVisible(true);
    }

    // Getter cho AccountInfo
    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeFrame());
    }
}
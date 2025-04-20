package GUI_TRANGCHU;

import javax.swing.*;
import java.awt.*;

public class TrangChuKHFrame extends JFrame {
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private TrangChuKHComponent components;
    private String username;

    public TrangChuKHFrame(String username) {
        this.username = username;
        initComponents();
    }

    private void initComponents() {
        setTitle("Khách Hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        components = new TrangChuKHComponent(username);

        mainContentPanel = new JPanel();
        cardLayout = new CardLayout();
        mainContentPanel.setLayout(cardLayout);

        mainContentPanel.add(new MainContentFrame(), "TrangChuGUI");
        JPanel phongPanel = new JPanel();
        mainContentPanel.add(phongPanel, "PhongGUI");
        JPanel khachHangPanel = new JPanel();
        mainContentPanel.add(khachHangPanel, "KhachHangGUI");
        JPanel hoaDonPanel = new JPanel();
        mainContentPanel.add(hoaDonPanel, "HoaDonGUI");

        add(components, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);

        new TrangChuKHEvent(this, components);

        showPanel("TrangChuGUI");
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainContentPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TrangChuKHFrame("KhachHang").setVisible(true);
        });
    }
}
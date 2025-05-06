package GUI_THONGKE;

import BUS.ThongKeBUS;
import javax.swing.*;

public class TongQuanTKFrame extends JFrame {
    private ThongKeBUS thongKeBUS;
    private TongQuanTKComponent component;

    public TongQuanTKFrame() {
        thongKeBUS = new ThongKeBUS();
        component = new TongQuanTKComponent(thongKeBUS);
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản lý khách sạn - Thống kê");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        add(component);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // thongKeBUS.closeConnection();
            }
        });
    }
}
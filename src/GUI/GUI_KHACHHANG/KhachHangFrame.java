package GUI_KHACHHANG;

import javax.swing.*;
import java.awt.*;

public class KhachHangFrame extends JFrame {
    private final KhachHangComponent khachHangComponent;

    public KhachHangFrame() {
        khachHangComponent = new KhachHangComponent();
        setTitle("Quản lý khách hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);

        // Thêm component vào frame
        add(khachHangComponent);

        setLocationRelativeTo(null);
        setVisible(true);

        // Gọi sự kiện
        KhachHangEvent eventHandler = new KhachHangEvent(this, khachHangComponent);
        eventHandler.setupEvents();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KhachHangFrame::new);
    }
}
package GUI_THONGKE;

import javax.swing.*;
import java.awt.*;

public class KhachHangFrame extends JFrame {
    public KhachHangFrame() {
        setTitle("Thống Kê Khách Hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Thêm KhachHangComponent vào JFrame
        KhachHangComponent khachHangComponent = new KhachHangComponent(null);
        add(khachHangComponent, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KhachHangFrame());
    }
}
package GUI_THONGKE;

import javax.swing.*;
import java.awt.*;

public class DoanhThuTheoThangFrame extends JFrame {
    public DoanhThuTheoThangFrame() {
        setTitle("Thống Kê Doanh Thu Theo Tháng Trong Năm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Thêm DoanhThuTheoThangComponent vào JFrame
        DoanhThuTheoThangComponent doanhThuTheoThangComponent = new DoanhThuTheoThangComponent(null);
        add(doanhThuTheoThangComponent, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DoanhThuTheoThangFrame());
    }
}
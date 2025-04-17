package GUI_THONGKE;

import javax.swing.*;
import java.awt.*;

public class DoanhThuTheoNgayFrame extends JFrame {
    public DoanhThuTheoNgayFrame() {
        setTitle("Thống Kê Doanh Thu Theo Ngày Trong Tháng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Thêm DoanhThuTheoNgayComponent vào JFrame
        DoanhThuTheoNgayComponent doanhThuTheoNgayComponent = new DoanhThuTheoNgayComponent(null);
        add(doanhThuTheoNgayComponent, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DoanhThuTheoNgayFrame());
    }
}
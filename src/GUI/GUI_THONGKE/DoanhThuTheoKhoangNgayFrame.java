package GUI_THONGKE;

import javax.swing.*;
import java.awt.*;

public class DoanhThuTheoKhoangNgayFrame extends JFrame {
    public DoanhThuTheoKhoangNgayFrame() {
        setTitle("Thống Kê Doanh Thu Theo Khoảng Ngày");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Thêm DoanhThuTheoKhoangNgayComponent vào JFrame
        DoanhThuTheoKhoangNgayComponent doanhThuTheoKhoangNgayComponent = new DoanhThuTheoKhoangNgayComponent(null);
        add(doanhThuTheoKhoangNgayComponent, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DoanhThuTheoKhoangNgayFrame());
    }
}
package GUI_THONGKE;

import BUS.ThongKeBUS;
import javax.swing.*;
import java.awt.*;

public class DoanhThuFrame extends JFrame {
    public DoanhThuFrame() {
        setTitle("Thống Kê Doanh Thu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Khởi tạo ThongKeBUS
        ThongKeBUS thongKeBUS = new ThongKeBUS();

        // Thêm DoanhThuComponent với ThongKeBUS
        DoanhThuComponent doanhThuComponent = new DoanhThuComponent(thongKeBUS);
        add(doanhThuComponent, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DoanhThuFrame());
    }
}
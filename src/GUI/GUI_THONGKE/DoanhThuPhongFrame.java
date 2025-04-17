package GUI_THONGKE;

import javax.swing.*;
import java.awt.*;

public class DoanhThuPhongFrame extends JFrame {
    public DoanhThuPhongFrame() {
        setTitle("Thống Kê Doanh Thu Theo Loại Phòng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Thêm DoanhThuPhongComponent vào JFrame
        DoanhThuPhongFrame doanhThuPhongComponent = new DoanhThuPhongFrame();
        add(doanhThuPhongComponent, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DoanhThuPhongFrame());
    }
}
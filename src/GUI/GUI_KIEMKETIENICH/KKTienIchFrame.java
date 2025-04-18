package GUI_KIEMKETIENICH;

import javax.swing.*;
import java.awt.*;

public class KKTienIchFrame extends JFrame {
    public KKTienIchFrame() {
        setTitle("Kiểm Kê Tiện Ích");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        KKTienIchComponent kiemKeComponent = new KKTienIchComponent(null); // Không cần mainScrollPane khi chạy độc lập
        new KKTienIchEvent(kiemKeComponent);
        add(kiemKeComponent);

        setVisible(true);
    }

    public static JPanel createKiemKePanel(JScrollPane mainScrollPane) {
        KKTienIchComponent kiemKeComponent = new KKTienIchComponent(mainScrollPane);
        new KKTienIchEvent(kiemKeComponent);
        return kiemKeComponent;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KKTienIchFrame::new);
    }
}
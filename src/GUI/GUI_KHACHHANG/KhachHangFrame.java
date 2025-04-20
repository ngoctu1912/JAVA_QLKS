package GUI_KHACHHANG;

import javax.swing.*;
import java.awt.*;

public class KhachHangFrame extends JFrame {
    private final KhachHangComponent khachHangComponent;

    public KhachHangFrame() {
        khachHangComponent = new KhachHangComponent();
        // Thêm component vào frame
        add(khachHangComponent);

        setLocationRelativeTo(null);
        setVisible(true);

        // Gọi sự kiện
        KhachHangEvent eventHandler = new KhachHangEvent(this, khachHangComponent);
        eventHandler.setupEvents();
    }

    // Getter for KhachHangComponent
    public KhachHangComponent getKhachHangComponent() {
        return khachHangComponent;
    }
}
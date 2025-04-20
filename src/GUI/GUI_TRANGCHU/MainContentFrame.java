package GUI_TRANGCHU;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.BoxLayout;

public class MainContentFrame extends JPanel {
    private MainContentComponents components;

    public MainContentFrame() {
        initFrame();
    }

    private void initFrame() {
        // Đảm bảo layout được thiết lập đúng
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        // Khởi tạo components
        components = new MainContentComponents();

        // Thêm các thành phần
        add(components.getHeaderPanel());
        add(components.getFeaturesPanel());
    }
}
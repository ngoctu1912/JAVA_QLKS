package GUI_MENU;

import GUI_TRANGCHU.HomeFrame;
import javax.swing.*;
import java.awt.*;

public class MenuViewFrame extends JPanel {
    private HomeFrame parentPanel;
    private MenuViewComponent components;
    private MenuViewEvent eventHandler;

    public MenuViewFrame(HomeFrame parent) {
        this.parentPanel = parent;
        initFrame();
    }

    private void initFrame() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(270, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.LIGHT_GRAY));

        // Khởi tạo components và events
        components = new MenuViewComponent(this);
        eventHandler = new MenuViewEvent(components, parentPanel);

        // Panel chứa toàn bộ nội dung, cách lề trái/phải 10px
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // 👈 cách trái/phải 10px

        // Thêm các thành phần vào wrapper
        wrapperPanel.add(components.getVerticalStrut(10));
        wrapperPanel.add(components.getHeaderPanel());
        wrapperPanel.add(components.getVerticalStrut(10));
        wrapperPanel.add(components.getSeparator());
        wrapperPanel.add(components.getVerticalStrut(10));
        wrapperPanel.add(components.getBtnTrangChu());
        wrapperPanel.add(components.getVerticalStrut(10));
        wrapperPanel.add(components.getBtnDatPhong());
        wrapperPanel.add(components.getVerticalGlue());
        wrapperPanel.add(components.getBtnDangNhap());
        wrapperPanel.add(components.getVerticalStrut(10));

        // Thêm wrapper vào MenuFrame
        add(wrapperPanel);

        // Gắn sự kiện
        eventHandler.setupEvents();
    }

    // Phương thức để TrangChuKHGUI gọi showPanel
    public void showPanel(String panelName) {
        parentPanel.showPanel(panelName);
    }
}

package GUI_MENU;

import GUI_TRANGCHU.HomeFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuViewEvent {
    private MenuViewComponent components;
    private HomeFrame parentPanel;
    private static final Color COLOR_HOVER = Color.decode("#edeff0");

    public MenuViewEvent(MenuViewComponent components, HomeFrame parentPanel) {
        this.components = components;
        this.parentPanel = parentPanel;
    }

    public void setupEvents() {
        // Sự kiện cho btnTrangChu
        components.getBtnTrangChu().addActionListener(e -> {
            parentPanel.showPanel("TrangChu");
            setActiveButton(components.getBtnTrangChu(), components.getBtnDatPhong(), components.getBtnDangNhap());
        });

        // Sự kiện cho btnDatPhong
        components.getBtnDatPhong().addActionListener(e -> {
            parentPanel.showPanel("DatPhong");
            setActiveButton(components.getBtnDatPhong(), components.getBtnTrangChu(), components.getBtnDangNhap());
        });

        // Sự kiện cho btnDangNhap
        components.getBtnDangNhap().addActionListener(e -> {
            // Gọi trực tiếp phương thức showLoginFrame của HomeFrame
            try {
                java.lang.reflect.Method showLoginFrame = HomeFrame.class.getDeclaredMethod("showLoginFrame");
                showLoginFrame.setAccessible(true);
                showLoginFrame.invoke(parentPanel);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(components.getBtnDangNhap(),
                        "Lỗi khi mở form đăng nhập: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Thêm hiệu ứng hover
        addHoverEffect(components.getBtnTrangChu());
        addHoverEffect(components.getBtnDatPhong());
        addHoverEffect(components.getBtnDangNhap());
    }

    private void setActiveButton(JButton activeButton, JButton... inactiveButtons) {
        if (activeButton instanceof RoundedMenuButton rbtnActive) {
            rbtnActive.setSelected(true);
        } else {
            activeButton.setBackground(components.getColorActive());
        }

        for (JButton inactiveButton : inactiveButtons) {
            if (inactiveButton instanceof RoundedMenuButton rbtnInactive) {
                rbtnInactive.setSelected(false);
            } else {
                inactiveButton.setBackground(components.getColorDefault());
            }
        }
    }

    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (button.getBackground() != components.getColorActive()) {
                    button.setBackground(COLOR_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (button.getBackground() != components.getColorActive()) {
                    button.setBackground(components.getColorDefault());
                }
            }
        });
    }
}
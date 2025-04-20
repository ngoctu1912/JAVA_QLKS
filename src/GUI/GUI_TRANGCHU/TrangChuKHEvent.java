package GUI_TRANGCHU;

import GUI_MENU.RoundedMenuButton;
import GUI_DANGNHAP_DANGKY.CustomMessage;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TrangChuKHEvent {
    private TrangChuKHFrame frame;
    private TrangChuKHComponent components;

    public TrangChuKHEvent(TrangChuKHFrame frame, TrangChuKHComponent components) {
        this.frame = frame;
        this.components = components;
        initEvents();
    }

    public void initEvents() {
        List<JButton> menuButtons = components.getMenuButtons();

        for (JButton button : menuButtons) {
            button.addActionListener(e -> {
                setActiveButton(button);
                String action = button.getActionCommand();
                if (!action.equals("Logout")) {
                    frame.showPanel(action);
                } else {
                    logout();
                }
            });
            addHoverEffect(button);
        }
    }

    private void setActiveButton(JButton activeButton) {
        for (JButton button : components.getMenuButtons()) {
            if (button instanceof RoundedMenuButton rbtn) {
                rbtn.setSelected(button == activeButton);
            } else {
                button.setBackground(
                        button == activeButton ? components.getSelectedColor() : components.getDefaultColor());
            }
        }
    }

    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (button.getBackground() != components.getSelectedColor()) {
                    button.setBackground(components.getHoverColor());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (button.getBackground() != components.getSelectedColor()) {
                    button.setBackground(components.getDefaultColor());
                }
            }
        });
    }

    private void logout() {
        CustomMessage customMessage = new CustomMessage();
        int confirm = customMessage.showConfirmMessage(
                "Xác nhận đăng xuất",
                "Bạn có chắc chắn muốn đăng xuất?");

        if (confirm == JOptionPane.YES_OPTION) {
            frame.dispose();
            new HomeFrame().setVisible(true);
        } else {
            System.out.println("Cancel logout");
        }
    }
}
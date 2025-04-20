package GUI_MENU;

import javax.swing.*;
import java.awt.*;

public class RoundedMenuButton extends JButton {
    private boolean isSelected = false;

    public RoundedMenuButton(String text) {
        super(text);
        init();
    }

    public RoundedMenuButton(String text, Icon icon) {
        super(text, icon);
        init();
    }

    private void init() {
        setContentAreaFilled(false); // Tắt nền mặc định
        setFocusPainted(false);
        setBorderPainted(false);
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        int arc = 20;
        Color bgColor;

        if (isSelected) {
            bgColor = new Color(220, 245, 218); // Màu được chọn
        } else if (getModel().isRollover()) {
            bgColor = new Color(237, 239, 240); // Hover
        } else {
            bgColor = Color.WHITE; // Mặc định
        }

        g2.setColor(bgColor);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.dispose();
        super.paintComponent(g);
    }
}

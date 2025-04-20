package GUI_DATPHONG;

import java.awt.*;
import javax.swing.*;

// Lớp RoundedButton để bo góc nút Tìm kiếm
public class RoundedButton extends JButton {
    private int arc;

    public RoundedButton(String text, int arc) {
        super(text);
        this.arc = arc;
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        g2.dispose();
        super.paintComponent(g);
    }
}

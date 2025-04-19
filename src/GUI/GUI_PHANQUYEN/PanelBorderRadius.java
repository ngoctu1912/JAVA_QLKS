package GUI_PHANQUYEN;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PanelBorderRadius extends JPanel {
    private int shadowSize = 3;
    private Color backgroundColor = new Color(255, 255, 255); // Đồng bộ với bảng

    public PanelBorderRadius() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        createShadow(grphcs);
    }

    private void createShadow(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        int size = shadowSize * 2;
        int width = getWidth() - size;
        int height = getHeight() - size;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(backgroundColor);
        g.fillRoundRect(0, 0, width, height, 15, 15);
        g.dispose();

        g2.drawImage(img, shadowSize, shadowSize, null);
    }
}


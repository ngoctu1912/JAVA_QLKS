package GUI_MENU;

import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;
import GUI_TRANGCHU.TrangChu;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class MenuGUI extends JPanel {
    private MenuComponent components;
    private TrangChu trangChu;

    public MenuGUI(AccountInfo accountInfo, TrangChu trangChu) {
        this.trangChu = trangChu;
        initComponents(accountInfo);
    }

    private void initComponents(AccountInfo accountInfo) {
        // Use standard BorderLayout without BoxLayout.Y_AXIS
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(270, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.LIGHT_GRAY));

        // Initialize MenuComponent
        components = new MenuComponent(accountInfo);

        // Scroll pane for center panel
        JScrollPane scrollPane = new JScrollPane(components.getCenterPanel());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Customize vertical scroll bar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));
        verticalScrollBar.setUnitIncrement(20);
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(209, 207, 207);
                this.trackColor = new Color(245, 245, 245);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                return button;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(thumbColor);
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                // Track is light or not painted
            }
        });

        // Top wrapper for top panel and separator
        JPanel topWrapper = new JPanel();
        topWrapper.setLayout(new BorderLayout());
        topWrapper.setBackground(Color.WHITE);
        topWrapper.add(components.getTopPanel(), BorderLayout.CENTER);

        // Separator styled to match MenuViewComponent
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(new Color(220, 220, 220)); // Match MenuViewComponent
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); // Match MenuViewComponent
        topWrapper.add(separator, BorderLayout.SOUTH);

        // Add components to layout
        add(topWrapper, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(components.getBottomPanel(), BorderLayout.SOUTH);

        // Initialize event handling
        new MenuEvent(this, components, accountInfo);
    }

    public MenuComponent getMenuComponents() {
        return components;
    }

    public TrangChu getTrangChu() {
        return trangChu;
    }
}


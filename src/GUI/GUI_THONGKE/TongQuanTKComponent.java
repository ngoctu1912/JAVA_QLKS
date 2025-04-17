package GUI_THONGKE;

import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.util.ArrayList;

public class TongQuanTKComponent extends JPanel {
    private ThongKeBUS thongKeBUS;
    private JLabel lblChartTitle;
    private JTable doanhThuTable;
    private JPanel chartPanel;
    private ArrayList<ThongKeDTO> danhSachDoanhThu;
    private JPanel contentPanel;
    private JScrollPane chartScrollPane, tableScrollPane, mainScrollPane;
    private CardLayout cardLayout;
    private JPanel tongQuanContentPanel;
    private JPanel navPanel;
    private TongQuanTKEvent event;

    public TongQuanTKComponent(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS;
        this.event = new TongQuanTKEvent(this, thongKeBUS);
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 245));
        setPreferredSize(new Dimension(1000, 650));
        initComponents();
        event.loadDataAsync();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(240, 245, 245));

        tongQuanContentPanel = new JPanel();
        tongQuanContentPanel.setBackground(new Color(240, 245, 245));
        tongQuanContentPanel.setLayout(new BoxLayout(tongQuanContentPanel, BoxLayout.Y_AXIS));
        // BỎ: Không đặt PreferredSize cố định để tránh cuộn quá đà

        JPanel doanhThuPanel = new DoanhThuComponent(thongKeBUS);
        JPanel khachHangPanel = new KhachHangComponent(thongKeBUS);

        contentPanel.add(tongQuanContentPanel, "Tổng quan");
        contentPanel.add(doanhThuPanel, "Doanh thu");
        contentPanel.add(khachHangPanel, "Khách hàng");

        navPanel = new JPanel();
        navPanel.setBackground(Color.decode("#E6F4F1"));
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        navPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        navPanel.setPreferredSize(new Dimension(1000, 40));

        String[] tabs = {"Tổng quan", "Doanh thu", "Khách hàng"};
        for (String tab : tabs) {
            JButton tabButton = new JButton(tab);
            tabButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            tabButton.setBackground(tab.equals("Tổng quan") ? Color.WHITE : Color.decode("#E6F4F1"));
            tabButton.setForeground(Color.BLACK);
            tabButton.setBorderPainted(false);
            tabButton.setFocusPainted(false);
            tabButton.addActionListener(e -> event.switchTab(tab));
            navPanel.add(tabButton);
        }

        tongQuanContentPanel.add(Box.createVerticalStrut(5));

        lblChartTitle = new JLabel("Thống kê doanh thu 8 ngày gần nhất", SwingConstants.CENTER);
        lblChartTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblChartTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        tongQuanContentPanel.add(lblChartTitle);
        tongQuanContentPanel.add(Box.createVerticalStrut(10));

        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                event.drawLineChart(g);
            }

            @Override
            public Dimension getPreferredSize() {
                int numPoints = (danhSachDoanhThu != null && !danhSachDoanhThu.isEmpty()) ? danhSachDoanhThu.size() : 1;
                int minWidth = numPoints * 80;
                return new Dimension(Math.max(minWidth, getParent().getWidth() - 40), 400);
            }
        };
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        chartScrollPane = new JScrollPane(chartPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chartScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        chartScrollPane.setPreferredSize(new Dimension(960, 400));
        chartScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        chartScrollPane.getViewport().setBackground(new Color(240, 245, 245));
        tongQuanContentPanel.add(chartScrollPane);
        tongQuanContentPanel.add(Box.createVerticalStrut(20));

        String[] columns = {"Ngày", "Doanh thu phòng", "Doanh thu dịch vụ", "Tổng doanh thu"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        doanhThuTable = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(new Color(183, 228, 199));
                c.setForeground(Color.BLACK);
                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }

            @Override
            public JTableHeader getTableHeader() {
                JTableHeader header = super.getTableHeader();
                header.setPreferredSize(new Dimension(0, 40));
                return header;
            }
        };

        doanhThuTable.setRowHeight(40);
        doanhThuTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        doanhThuTable.setShowVerticalLines(false);
        doanhThuTable.setShowHorizontalLines(false);
        doanhThuTable.setIntercellSpacing(new Dimension(0, 0));
        doanhThuTable.setBorder(BorderFactory.createEmptyBorder());

        JTableHeader header = doanhThuTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setOpaque(false);

        UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());

        doanhThuTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        doanhThuTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        doanhThuTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        doanhThuTable.getColumnModel().getColumn(3).setPreferredWidth(150);

        tableScrollPane = new JScrollPane(doanhThuTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tableScrollPane.getViewport().setBackground(new Color(240, 245, 245));
        tableScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tableScrollPane.getVerticalScrollBar().setBlockIncrement(50);

        // Tùy chỉnh thanh cuộn
        JScrollBar verticalScrollBar = tableScrollPane.getVerticalScrollBar();
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
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(trackColor);
                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                g2.dispose();
            }
        });
        verticalScrollBar.setPreferredSize(new Dimension(12, Integer.MAX_VALUE));

        tongQuanContentPanel.add(tableScrollPane);
        tongQuanContentPanel.add(Box.createVerticalStrut(10));

        // Bao quanh contentPanel bằng mainScrollPane
        mainScrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainScrollPane.getViewport().setBackground(new Color(240, 245, 245));
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainScrollPane.getVerticalScrollBar().setBlockIncrement(50);

        // Tùy chỉnh thanh cuộn cho mainScrollPane
        JScrollBar mainVerticalScrollBar = mainScrollPane.getVerticalScrollBar();
        mainVerticalScrollBar.setUI(new BasicScrollBarUI() {
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
            protected void paintTrack(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(trackColor);
                g2.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
                g2.dispose();
            }
        });
        mainVerticalScrollBar.setPreferredSize(new Dimension(12, Integer.MAX_VALUE));

        add(navPanel, BorderLayout.NORTH);
        add(mainScrollPane, BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                adjustTableSize();
            }
        });

        cardLayout.show(contentPanel, "Tổng quan");
        revalidate();
        repaint();
        adjustTableSize();
    }

    private void adjustTableSize() {
        int width = getWidth();
        int tableHeight = doanhThuTable.getRowCount() * doanhThuTable.getRowHeight() + doanhThuTable.getTableHeader().getHeight() + 40; // Tính chiều cao động
        tableScrollPane.setPreferredSize(new Dimension(width - 40, Math.min(tableHeight, 300))); // Giới hạn chiều cao tối đa
        tableScrollPane.revalidate();
        tableScrollPane.repaint();
    }

    public void updateNavButtons(String activeTab) {
        for (Component comp : navPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                btn.setBackground(btn.getText().equals(activeTab) ? Color.WHITE : Color.decode("#E6F4F1"));
            }
        }
        navPanel.revalidate();
        navPanel.repaint();
    }

    public JLabel getLblChartTitle() {
        return lblChartTitle;
    }

    public JTable getDoanhThuTable() {
        return doanhThuTable;
    }

    public JPanel getChartPanel() {
        return chartPanel;
    }

    public ArrayList<ThongKeDTO> getDanhSachDoanhThu() {
        return danhSachDoanhThu;
    }

    public void setDanhSachDoanhThu(ArrayList<ThongKeDTO> danhSachDoanhThu) {
        this.danhSachDoanhThu = danhSachDoanhThu;
    }

    public JScrollPane getMainScrollPane() {
        return mainScrollPane;
    }

    public JPanel getNavPanel() {
        return navPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
package GUI_THONGKE;

import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class DoanhThuTheoNgayComponent extends JPanel {
    private ThongKeBUS thongKeBUS;
    private JLabel lblChartTitle;
    private JTable doanhThuTable;
    private JPanel chartPanel;
    private ArrayList<ThongKeDTO> danhSachDoanhThu;
    private ArrayList<ThongKeDTO> dailyData;
    private JTextField txtYear, txtMonth;
    private JPanel contentPanel;
    private RoundedPanel filterPanel;
    private JScrollPane chartScrollPane, tableScrollPane, mainScrollPane;
    private JButton btnExport, btnFilter, btnReset;
    private JPanel subTabPanel;
    private DoanhThuTheoNgayEvent eventHandler;
    private static int sharedYear = 2025;

    public DoanhThuTheoNgayComponent(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS != null ? thongKeBUS : new ThongKeBUS();
        eventHandler = new DoanhThuTheoNgayEvent(this, this.thongKeBUS);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(960, 600));
        initComponents();
        eventHandler.loadData();
    }

    private void initComponents() {
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setDoubleBuffered(true);

        // Thanh tab phụ
        subTabPanel = new JPanel();
        subTabPanel.setBackground(Color.decode("#E6F4F1"));
        subTabPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        subTabPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        subTabPanel.setPreferredSize(new Dimension(960, 40));

        String[] subTabs = {
            "Thống kê theo năm",
            "Thống kê từng tháng trong năm",
            "Thống kê theo loại phòng",
            "Thống kê từng ngày trong tháng",
            "Thống kê từ ngày đến ngày"
        };
        for (String subTab : subTabs) {
            JButton subTabButton = new JButton(subTab);
            subTabButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            subTabButton.setBackground(subTab.equals("Thống kê từng ngày trong tháng") ? Color.WHITE : Color.decode("#E6F4F1"));
            subTabButton.setForeground(Color.BLACK);
            subTabButton.setBorderPainted(false);
            subTabButton.setFocusPainted(false);
            subTabButton.addActionListener(e -> eventHandler.switchTab(subTab));
            subTabPanel.add(subTabButton);
        }
        contentPanel.add(subTabPanel);
        contentPanel.add(Box.createVerticalStrut(10));

        // Panel Bộ Lọc
        filterPanel = new RoundedPanel(20);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setLayout(null);
        filterPanel.setPreferredSize(new Dimension(960, 60));
        filterPanel.setMaximumSize(new Dimension(960, 60));
        filterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblYear = new JLabel("Năm:");
        lblYear.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblYear.setBounds(20, 15, 60, 30);
        filterPanel.add(lblYear);

        txtYear = new JTextField();
        txtYear.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtYear.setBounds(80, 15, 80, 30);
        txtYear.setText(String.valueOf(sharedYear));
        filterPanel.add(txtYear);

        JLabel lblMonth = new JLabel("Tháng:");
        lblMonth.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblMonth.setBounds(180, 15, 60, 30);
        filterPanel.add(lblMonth);

        txtMonth = new JTextField();
        txtMonth.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtMonth.setBounds(240, 15, 80, 30);
        txtMonth.setText("1");
        filterPanel.add(txtMonth);

        btnFilter = createRoundedButton("Thống kê");
        btnFilter.setBounds(340, 15, 100, 30);
        btnFilter.addActionListener(e -> eventHandler.loadDataByFilter());
        filterPanel.add(btnFilter);

        btnReset = createRoundedButton("Làm mới");
        btnReset.setBounds(450, 15, 100, 30);
        btnReset.addActionListener(e -> eventHandler.loadData());
        filterPanel.add(btnReset);

        btnExport = createRoundedButton("Xuất Excel");
        btnExport.setBounds(560, 15, 100, 30);
        btnExport.addActionListener(e -> eventHandler.xuatExcel());
        filterPanel.add(btnExport);

        contentPanel.add(filterPanel);
        contentPanel.add(Box.createVerticalStrut(10));

        // Tiêu đề biểu đồ
        lblChartTitle = new JLabel("", SwingConstants.CENTER);
        lblChartTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblChartTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblChartTitle);
        contentPanel.add(Box.createVerticalStrut(10));

        // Biểu đồ
        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                eventHandler.drawHistogram(g);
            }

            @Override
            public Dimension getPreferredSize() {
                int numDays = dailyData != null ? dailyData.size() : 1;
                int minWidth = Math.min(numDays * 50, 900); // Giới hạn chiều rộng tối đa 900
                return new Dimension(minWidth, 400);
            }
        };
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        chartPanel.setDoubleBuffered(true);

        chartScrollPane = new JScrollPane(chartPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chartScrollPane.setPreferredSize(new Dimension(940, 400));
        chartScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(chartScrollPane);
        contentPanel.add(Box.createVerticalStrut(20));

        // Bảng doanh thu
        String[] columns = {"Ngày", "Doanh thu phòng", "Doanh thu dịch vụ", "Tổng doanh thu"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        doanhThuTable = new JTable(model) {
            @Override
            public JTableHeader getTableHeader() {
                JTableHeader header = super.getTableHeader();
                header.setPreferredSize(new Dimension(0, 40));
                return header;
            }

            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(900, getPreferredSize().height); // Giới hạn chiều rộng bảng
            }
        };
        doanhThuTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        doanhThuTable.setShowVerticalLines(false);
        doanhThuTable.setShowHorizontalLines(false);
        doanhThuTable.setIntercellSpacing(new Dimension(0, 0));
        doanhThuTable.setBorder(BorderFactory.createEmptyBorder());
        doanhThuTable.getTableHeader().setReorderingAllowed(false);
        doanhThuTable.getTableHeader().setOpaque(false);
        styleTable(doanhThuTable);

        tableScrollPane = new JScrollPane(doanhThuTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        tableScrollPane.setPreferredSize(new Dimension(940, 100));
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tableScrollPane.getViewport().setBackground(Color.WHITE);
        contentPanel.add(tableScrollPane);
        contentPanel.add(Box.createVerticalStrut(10));

        // Tùy chỉnh thanh cuộn mảnh & đẹp cho tableScrollPane
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
                // Không vẽ track hoặc để lại mặc định nhẹ nhàng
            }
        });
        verticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));

        UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());

        mainScrollPane = new JScrollPane(contentPanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainScrollPane.getVerticalScrollBar().setBlockIncrement(50);
        mainScrollPane.setBackground(Color.WHITE);
        mainScrollPane.getViewport().setBackground(Color.WHITE);

        // Chuyển sự kiện cuộn từ các thành phần con lên mainScrollPane (trừ tableScrollPane và doanhThuTable)
        forwardMouseWheelEvents(chartScrollPane);
        forwardMouseWheelEvents(subTabPanel);
        forwardMouseWheelEvents(filterPanel);
        forwardMouseWheelEvents(chartPanel);
        forwardMouseWheelEvents(lblChartTitle);
        forwardMouseWheelEvents(txtYear);
        forwardMouseWheelEvents(txtMonth);
        forwardMouseWheelEvents(btnFilter);
        forwardMouseWheelEvents(btnReset);
        forwardMouseWheelEvents(btnExport);

        add(mainScrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void forwardMouseWheelEvents(JComponent component) {
        component.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                mainScrollPane.dispatchEvent(new MouseWheelEvent(
                    mainScrollPane,
                    e.getID(),
                    e.getWhen(),
                    e.getModifiers(),
                    e.getX(),
                    e.getY(),
                    e.getClickCount(),
                    e.isPopupTrigger(),
                    e.getScrollType(),
                    e.getScrollAmount(),
                    e.getWheelRotation()
                ));
                e.consume();
            }
        });
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#B7E4C7"));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();

                int width = b.getWidth();
                int height = b.getHeight();
                int arc = 20;

                if (model.isPressed()) {
                    g2.setColor(Color.decode("#2eb82e"));
                } else if (model.isRollover()) {
                    g2.setColor(Color.decode("#3cdc3c"));
                } else {
                    g2.setColor(Color.decode("#B7E4C7"));
                }

                g2.fillRoundRect(0, 0, width, height, arc, arc);

                g2.setColor(Color.WHITE);
                g2.setFont(b.getFont());
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(b.getText());
                int textHeight = fm.getHeight();
                int x = (width - textWidth) / 2;
                int y = (height - textHeight) / 2 + fm.getAscent();
                g2.drawString(b.getText(), x, y);

                g2.dispose();
            }
        });
        return button;
    }

    private void styleTable(JTable table) {
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));

        table.setRowHeight(40);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(183, 228, 199));
                c.setForeground(Color.BLACK);
                c.setFont(new Font("SansSerif", Font.PLAIN, 14));
                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    // Lớp RoundedPanel tích hợp trực tiếp
    class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.dispose();
        }
    }

    public void updateTabButtons(String activeTab) {
        for (Component comp : subTabPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setBackground(((JButton) comp).getText().equals(activeTab) ? Color.WHITE : Color.decode("#E6F4F1"));
            }
        }
        subTabPanel.revalidate();
        subTabPanel.repaint();
    }

    // Getters và Setters
    public JLabel getLblChartTitle() { return lblChartTitle; }
    public JTable getDoanhThuTable() { return doanhThuTable; }
    public JPanel getChartPanel() { return chartPanel; }
    public ArrayList<ThongKeDTO> getDanhSachDoanhThu() { return danhSachDoanhThu; }
    public void setDanhSachDoanhThu(ArrayList<ThongKeDTO> danhSachDoanhThu) { this.danhSachDoanhThu = danhSachDoanhThu; }
    public ArrayList<ThongKeDTO> getDailyData() { return dailyData; }
    public void setDailyData(ArrayList<ThongKeDTO> dailyData) { this.dailyData = dailyData; }
    public JTextField getTxtYear() { return txtYear; }
    public JTextField getTxtMonth() { return txtMonth; }
    public JPanel getSubTabPanel() { return subTabPanel; }
    public JScrollPane getMainScrollPane() { return mainScrollPane; }
    public static int getSharedYear() { return sharedYear; }
    public static void setSharedYear(int year) { sharedYear = year; }
}
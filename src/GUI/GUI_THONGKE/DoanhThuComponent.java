package GUI_THONGKE;

import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class DoanhThuComponent extends JPanel {
    private ThongKeBUS thongKeBUS;
    private JLabel lblChartTitle;
    private JTable doanhThuTable;
    private JPanel chartPanel;
    private ArrayList<ThongKeDTO> danhSachDoanhThu;
    private ArrayList<ThongKeDTO> yearlyData;
    private JTextField txtStartYear, txtEndYear;
    private JPanel contentPanel;
    private RoundedPanel filterPanel;
    private JScrollPane chartScrollPane, tableScrollPane, mainScrollPane;
    private JButton btnExport, btnFilter, btnReset;
    private JPanel subTabPanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DoanhThuEvent event;
    private static int sharedStartYear = 2020;
    private static int sharedEndYear = LocalDate.now().getYear();

    public DoanhThuComponent(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS;
        this.event = new DoanhThuEvent(this, thongKeBUS);
        initComponents();
        event.loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(960, 600));
        setBackground(Color.decode("#F5F5F5")); // Đổi màu nền thành xám nhạt

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.decode("#F5F5F5"));

        contentPanel = new JPanel();
        contentPanel.setBackground(Color.decode("#F5F5F5"));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setDoubleBuffered(true);
        contentPanel.setPreferredSize(new Dimension(960, 900));

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
            subTabButton.setBackground(subTab.equals("Thống kê theo năm") ? Color.WHITE : Color.decode("#E6F4F1"));
            subTabButton.setForeground(Color.BLACK);
            subTabButton.setBorderPainted(false);
            subTabButton.setFocusPainted(false);
            subTabButton.addActionListener(e -> event.switchTab(subTab));
            subTabPanel.add(subTabButton);
        }
        contentPanel.add(subTabPanel);
        contentPanel.add(Box.createVerticalStrut(10));

        filterPanel = new RoundedPanel(20);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15)); // Sử dụng FlowLayout thay vì null
        filterPanel.setPreferredSize(new Dimension(960, 60));
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        filterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblStartYear = new JLabel("Từ năm:");
        lblStartYear.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        filterPanel.add(lblStartYear);

        txtStartYear = new JTextField(5);
        txtStartYear.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtStartYear.setText(String.valueOf(sharedStartYear));
        filterPanel.add(txtStartYear);

        JLabel lblEndYear = new JLabel("Đến năm:");
        lblEndYear.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        filterPanel.add(lblEndYear);

        txtEndYear = new JTextField(5);
        txtEndYear.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtEndYear.setText(String.valueOf(sharedEndYear));
        filterPanel.add(txtEndYear);

        btnFilter = createRoundedButton("Thống kê");
        btnFilter.addActionListener(e -> event.loadDataByFilter());
        filterPanel.add(btnFilter);

        btnReset = createRoundedButton("Làm mới");
        btnReset.addActionListener(e -> event.loadData());
        filterPanel.add(btnReset);

        btnExport = createRoundedButton("Xuất Excel");
        btnExport.addActionListener(e -> event.xuatExcel());
        filterPanel.add(btnExport);

        contentPanel.add(filterPanel);
        contentPanel.add(Box.createVerticalStrut(10));

        lblChartTitle = new JLabel("", SwingConstants.CENTER);
        lblChartTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblChartTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblChartTitle);
        contentPanel.add(Box.createVerticalStrut(10));

        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                event.drawHistogram(g);
            }

            @Override
            public Dimension getPreferredSize() {
                int numYears = yearlyData != null ? yearlyData.size() : 1;
                int minWidth = Math.min(numYears * 150, 900);
                return new Dimension(minWidth, 400);
            }
        };
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        chartPanel.setDoubleBuffered(true);

        chartScrollPane = new JScrollPane(chartPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chartScrollPane.setPreferredSize(new Dimension(940, 400));
        chartScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        chartScrollPane.getViewport().setBackground(Color.WHITE);

        JScrollBar chartHorizontalScrollBar = chartScrollPane.getHorizontalScrollBar();
        chartHorizontalScrollBar.setUI(new BasicScrollBarUI() {
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
                // Không vẽ track
            }
        });
        chartHorizontalScrollBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 6));

        contentPanel.add(chartScrollPane);
        contentPanel.add(Box.createVerticalStrut(20));

        String[] columns = {"Năm", "Doanh thu phòng", "Doanh thu dịch vụ", "Tổng doanh thu"};
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

            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(900, 200);
            }
        };

        doanhThuTable.setRowHeight(40);
        doanhThuTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        doanhThuTable.setShowVerticalLines(false);
        doanhThuTable.setShowHorizontalLines(false);
        doanhThuTable.setIntercellSpacing(new Dimension(0, 0));
        doanhThuTable.setBorder(BorderFactory.createEmptyBorder());
        doanhThuTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        doanhThuTable.getTableHeader().setReorderingAllowed(false);
        doanhThuTable.getTableHeader().setOpaque(false);

        JTableHeader header = doanhThuTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setOpaque(false);

        UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());

        tableScrollPane = new JScrollPane(doanhThuTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tableScrollPane.setPreferredSize(new Dimension(940, 200));
        tableScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tableScrollPane.getViewport().setBackground(Color.WHITE);
        tableScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tableScrollPane.getVerticalScrollBar().setBlockIncrement(50);

        JScrollBar tableVerticalScrollBar = tableScrollPane.getVerticalScrollBar();
        tableVerticalScrollBar.setUI(new BasicScrollBarUI() {
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
                // Không vẽ track
            }
        });
        tableVerticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));

        contentPanel.add(tableScrollPane);
        contentPanel.add(Box.createVerticalStrut(10));

        mainScrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.setBackground(Color.decode("#F5F5F5"));
        mainScrollPane.getViewport().setBackground(Color.decode("#F5F5F5"));
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainScrollPane.getVerticalScrollBar().setBlockIncrement(50);

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
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                // Không vẽ track
            }
        });
        mainVerticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));

        cardPanel.add(mainScrollPane, "Yearly");
        add(cardPanel, BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                adjustTableSize();
            }
        });

        forwardMouseWheelEvents(chartScrollPane);
        forwardMouseWheelEvents(subTabPanel);
        forwardMouseWheelEvents(filterPanel);
        forwardMouseWheelEvents(chartPanel);
        forwardMouseWheelEvents(lblChartTitle);
        forwardMouseWheelEvents(txtStartYear);
        forwardMouseWheelEvents(txtEndYear);
        forwardMouseWheelEvents(btnFilter);
        forwardMouseWheelEvents(btnReset);
        forwardMouseWheelEvents(btnExport);

        revalidate();
        repaint();
        adjustTableSize();
    }

    private void forwardMouseWheelEvents(JComponent component) {
        component.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                mainScrollPane.dispatchEvent(new MouseWheelEvent(
                    mainScrollPane,
                    e.getID(),
                    e.getWhen(),
                    e.getModifiersEx(),
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

    private void adjustTableSize() {
        int width = getWidth() - 40;
        int tableHeight = doanhThuTable.getRowCount() * doanhThuTable.getRowHeight() + doanhThuTable.getTableHeader().getHeight() + 40;
        tableScrollPane.setPreferredSize(new Dimension(width, Math.min(tableHeight, 200)));
        tableScrollPane.revalidate();
        tableScrollPane.repaint();
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

    public void switchTab(String tabName) {
        event.switchTab(tabName);
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
        adjustTableSize();
    }

    public ArrayList<ThongKeDTO> getYearlyData() {
        return yearlyData;
    }

    public void setYearlyData(ArrayList<ThongKeDTO> yearlyData) {
        this.yearlyData = yearlyData;
        adjustTableSize();
    }

    public JTextField getTxtStartYear() {
        return txtStartYear;
    }

    public JTextField getTxtEndYear() {
        return txtEndYear;
    }

    public JScrollPane getMainScrollPane() {
        return mainScrollPane;
    }

    public JPanel getSubTabPanel() {
        return subTabPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public static int getSharedStartYear() {
        return sharedStartYear;
    }

    public static void setSharedStartYear(int year) {
        sharedStartYear = year;
    }

    public static int getSharedEndYear() {
        return sharedEndYear;
    }

    public static void setSharedEndYear(int year) {
        sharedEndYear = year;
    }
}

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
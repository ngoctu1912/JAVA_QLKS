package GUI_THONGKE;

import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;

public class DoanhThuPhongComponent extends JPanel {
    private ThongKeBUS thongKeBUS;
    private JLabel lblChartTitle;
    private JTable doanhThuTable;
    private JPanel chartPanel;
    private ArrayList<ThongKeDTO> danhSachDoanhThu;
    private ArrayList<ThongKeDTO> typeData;
    private JComboBox<String> cbStartDay, cbStartMonth, cbStartYear, cbEndDay, cbEndMonth, cbEndYear;
    private JPanel contentPanel;
    private RoundedPanel filterPanel;
    private JScrollPane chartScrollPane, tableScrollPane, mainScrollPane;
    private JButton btnExport;
    private JPanel subTabPanel;
    private DoanhThuPhongEvent event;
    private static LocalDate sharedStartDate = LocalDate.now().minusMonths(1);
    private static LocalDate sharedEndDate = LocalDate.now();

    public DoanhThuPhongComponent(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS;
        this.event = new DoanhThuPhongEvent(this, thongKeBUS);
        initComponents();
        event.loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(960, 600));

        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setDoubleBuffered(true);
        contentPanel.setPreferredSize(new Dimension(960, 1000)); // Tăng chiều cao để kích hoạt thanh cuộn

        subTabPanel = new JPanel();
        subTabPanel.setBackground(Color.decode("#F5F5F5"));
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
            subTabButton.setBackground(subTab.equals("Thống kê theo loại phòng") ? Color.WHITE : Color.decode("#E6F4F1"));
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
        filterPanel.setLayout(null);
        filterPanel.setPreferredSize(new Dimension(960, 60));
        filterPanel.setMaximumSize(new Dimension(960, 60));
        filterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblStartDate = new JLabel("Từ ngày:");
        lblStartDate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblStartDate.setBounds(20, 15, 60, 30);
        filterPanel.add(lblStartDate);

        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) days[i-1] = String.valueOf(i);
        String[] months = new String[12];
        for (int i = 1; i <= 12; i++) months[i-1] = String.valueOf(i);
        String[] years = new String[126];
        for (int i = 1900; i <= 2025; i++) years[i-1900] = String.valueOf(i);

        cbStartDay = new JComboBox<>(days);
        cbStartDay.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbStartDay.setBounds(80, 15, 60, 30);
        cbStartDay.setBackground(Color.WHITE);
        cbStartDay.setUI(new CustomComboBoxUI());
        cbStartDay.setSelectedItem(String.valueOf(sharedStartDate.getDayOfMonth()));
        cbStartDay.addActionListener(e -> event.loadDataByFilter());
        filterPanel.add(cbStartDay);

        cbStartMonth = new JComboBox<>(months);
        cbStartMonth.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbStartMonth.setBounds(150, 15, 60, 30);
        cbStartMonth.setBackground(Color.WHITE);
        cbStartMonth.setUI(new CustomComboBoxUI());
        cbStartMonth.setSelectedItem(String.valueOf(sharedStartDate.getMonthValue()));
        cbStartMonth.addActionListener(e -> event.loadDataByFilter());
        filterPanel.add(cbStartMonth);

        cbStartYear = new JComboBox<>(years);
        cbStartYear.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbStartYear.setBounds(220, 15, 80, 30);
        cbStartYear.setBackground(Color.WHITE);
        cbStartYear.setUI(new CustomComboBoxUI());
        cbStartYear.setSelectedItem(String.valueOf(sharedStartDate.getYear()));
        cbStartYear.addActionListener(e -> event.loadDataByFilter());
        filterPanel.add(cbStartYear);

        JLabel lblEndDate = new JLabel("Đến ngày:");
        lblEndDate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblEndDate.setBounds(320, 15, 70, 30);
        filterPanel.add(lblEndDate);

        cbEndDay = new JComboBox<>(days);
        cbEndDay.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbEndDay.setBounds(390, 15, 60, 30);
        cbEndDay.setBackground(Color.WHITE);
        cbEndDay.setUI(new CustomComboBoxUI());
        cbEndDay.setSelectedItem(String.valueOf(sharedEndDate.getDayOfMonth()));
        cbEndDay.addActionListener(e -> event.loadDataByFilter());
        filterPanel.add(cbEndDay);

        cbEndMonth = new JComboBox<>(months);
        cbEndMonth.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbEndMonth.setBounds(460, 15, 60, 30);
        cbEndMonth.setBackground(Color.WHITE);
        cbEndMonth.setUI(new CustomComboBoxUI());
        cbEndMonth.setSelectedItem(String.valueOf(sharedEndDate.getMonthValue()));
        cbEndMonth.addActionListener(e -> event.loadDataByFilter());
        filterPanel.add(cbEndMonth);

        cbEndYear = new JComboBox<>(years);
        cbEndYear.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbEndYear.setBounds(530, 15, 80, 30);
        cbEndYear.setBackground(Color.WHITE);
        cbEndYear.setUI(new CustomComboBoxUI());
        cbEndYear.setSelectedItem(String.valueOf(sharedEndDate.getYear()));
        cbEndYear.addActionListener(e -> event.loadDataByFilter());
        filterPanel.add(cbEndYear);

        JButton btnFilter = createRoundedButton("Thống kê");
        btnFilter.setBounds(630, 15, 100, 30);
        btnFilter.addActionListener(e -> event.loadDataByFilter());
        filterPanel.add(btnFilter);

        JButton btnReset = createRoundedButton("Làm mới");
        btnReset.setBounds(740, 15, 100, 30);
        btnReset.addActionListener(e -> event.loadData());
        filterPanel.add(btnReset);

        btnExport = createRoundedButton("Xuất Excel");
        btnExport.setBounds(850, 15, 100, 30);
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
                int numTypes = typeData != null ? typeData.size() : 1;
                int minWidth = numTypes * 150;
                return new Dimension(Math.max(minWidth, getParent().getWidth() - 40), 400);
            }
        };
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        chartPanel.setDoubleBuffered(true);

        chartScrollPane = new JScrollPane(chartPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chartScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        chartScrollPane.setPreferredSize(new Dimension(960, 400));
        chartScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        chartScrollPane.getViewport().setBackground(Color.WHITE);

        contentPanel.add(chartScrollPane);
        contentPanel.add(Box.createVerticalStrut(20));

        String[] columns = {"Loại phòng", "Doanh thu phòng", "Doanh thu dịch vụ", "Số lần đặt", "Tổng doanh thu"};
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
                return new Dimension(960, 200); // Giảm chiều cao để kích hoạt thanh cuộn
            }
        };
        doanhThuTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        doanhThuTable.setShowVerticalLines(false);
        doanhThuTable.setShowHorizontalLines(false);
        doanhThuTable.setIntercellSpacing(new Dimension(0, 0));
        doanhThuTable.setBorder(BorderFactory.createEmptyBorder());
        doanhThuTable.getTableHeader().setReorderingAllowed(false);
        doanhThuTable.getTableHeader().setOpaque(false);
        doanhThuTable.setRowHeight(30); // Giảm chiều cao hàng để hiển thị nhiều hàng hơn
        styleTable(doanhThuTable);

        // Thêm dữ liệu giả để đảm bảo thanh cuộn dọc xuất hiện
        for (int i = 0; i < 50; i++) {
            model.addRow(new Object[]{"Loại " + i, 1000 * i, 500 * i, i + 1, 1500 * i});
        }

        tableScrollPane = new JScrollPane(doanhThuTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tableScrollPane.setPreferredSize(new Dimension(960, 300));
        tableScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        tableScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        tableScrollPane.getViewport().setBackground(Color.WHITE);

        // Tùy chỉnh thanh cuộn dọc cho tableScrollPane
        JScrollBar tableVerticalScrollBar = tableScrollPane.getVerticalScrollBar();
        tableVerticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(209, 207, 207); // #D1CFCF
                this.trackColor = new Color(245, 245, 245); // #F5F5F5
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
                // Không vẽ track để giao diện mảnh
            }
        });
        tableVerticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));

        contentPanel.add(tableScrollPane);
        contentPanel.add(Box.createVerticalStrut(10));

        mainScrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainScrollPane.getViewport().setBackground(Color.WHITE);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainScrollPane.getVerticalScrollBar().setBlockIncrement(50);

        // Tùy chỉnh thanh cuộn dọc cho mainScrollPane
        JScrollBar mainVerticalScrollBar = mainScrollPane.getVerticalScrollBar();
        mainVerticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(209, 207, 207); // #D1CFCF
                this.trackColor = new Color(245, 245, 245); // #F5F5F5
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
                // Không vẽ track để giao diện mảnh
            }
        });
        mainVerticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));

        UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());

        // Chuyển sự kiện cuộn chuột
        MouseWheelListener scrollListener = new MouseWheelListener() {
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
        };

        contentPanel.addMouseWheelListener(scrollListener);
        chartScrollPane.addMouseWheelListener(scrollListener);
        subTabPanel.addMouseWheelListener(scrollListener);
        filterPanel.addMouseWheelListener(scrollListener);
        chartPanel.addMouseWheelListener(scrollListener);
        lblChartTitle.addMouseWheelListener(scrollListener);
        cbStartDay.addMouseWheelListener(scrollListener);
        cbStartMonth.addMouseWheelListener(scrollListener);
        cbStartYear.addMouseWheelListener(scrollListener);
        cbEndDay.addMouseWheelListener(scrollListener);
        cbEndMonth.addMouseWheelListener(scrollListener);
        cbEndYear.addMouseWheelListener(scrollListener);

        add(mainScrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
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

        table.setRowHeight(30);
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

    private class CustomComboBoxUI extends BasicComboBoxUI {
        private boolean isPressed = false;

        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton() {
                @Override
                public void paint(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    int width = getWidth();
                    int height = getHeight();
                    g2.setColor(Color.decode("#B7E4C7"));
                    g2.fillRect(0, 0, width, height);
                    g2.setColor(Color.WHITE);
                    int[] xPoints = {width / 2 - 4, width / 2 + 4, width / 2};
                    int[] yPoints = {height / 2 - 2, height / 2 - 2, height / 2 + 2};
                    g2.fillPolygon(xPoints, yPoints, 3);
                    g2.dispose();
                }
            };
            button.setPreferredSize(new Dimension(20, 30));
            button.setBackground(Color.decode("#B7E4C7"));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    comboBox.repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    comboBox.repaint();
                }
            });
            return button;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = c.getWidth();
            int height = c.getHeight();
            int arc = 10;

            if (isPressed) {
                g2.setColor(Color.decode("#2eb82e"));
            } else if (c.getMousePosition() != null) {
                g2.setColor(Color.decode("#3cdc3c"));
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.fillRoundRect(0, 0, width - 20, height, arc, arc);
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(0, 0, width - 20, height - 1, arc, arc);

            String selectedText = comboBox.getSelectedItem() != null ? comboBox.getSelectedItem().toString() : "";
            g2.setColor(Color.BLACK);
            g2.setFont(comboBox.getFont());
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(selectedText);
            int textHeight = fm.getHeight();
            int x = 5;
            int y = (height - textHeight) / 2 + fm.getAscent();
            g2.drawString(selectedText, x, y);

            g2.dispose();
        }

        @Override
        protected void installDefaults() {
            super.installDefaults();
            comboBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            comboBox.setOpaque(false);
        }

        @Override
        protected void installListeners() {
            super.installListeners();
            comboBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    comboBox.repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    comboBox.repaint();
                }
            });
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

    public ArrayList<ThongKeDTO> getTypeData() {
        return typeData;
    }

    public void setTypeData(ArrayList<ThongKeDTO> typeData) {
        this.typeData = typeData;
    }

    public JComboBox<String> getCbStartDay() {
        return cbStartDay;
    }

    public JComboBox<String> getCbStartMonth() {
        return cbStartMonth;
    }

    public JComboBox<String> getCbStartYear() {
        return cbStartYear;
    }

    public JComboBox<String> getCbEndDay() {
        return cbEndDay;
    }

    public JComboBox<String> getCbEndMonth() {
        return cbEndMonth;
    }

    public JComboBox<String> getCbEndYear() {
        return cbEndYear;
    }

    public JScrollPane getMainScrollPane() {
        return mainScrollPane;
    }

    public JPanel getSubTabPanel() {
        return subTabPanel;
    }

    public static LocalDate getSharedStartDate() {
        return sharedStartDate;
    }

    public static void setSharedStartDate(LocalDate date) {
        sharedStartDate = date;
    }

    public static LocalDate getSharedEndDate() {
        return sharedEndDate;
    }

    public static void setSharedEndDate(LocalDate date) {
        sharedEndDate = date;
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
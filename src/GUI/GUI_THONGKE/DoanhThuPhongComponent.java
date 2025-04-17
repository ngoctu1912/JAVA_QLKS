package GUI_THONGKE;

import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        filterPanel.setPreferredSize(new Dimension(960, 50));
        filterPanel.setMaximumSize(new Dimension(960, 50));
        filterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblStartDate = new JLabel("Từ ngày:");
        lblStartDate.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblStartDate.setBounds(10, 10, 60, 30);
        filterPanel.add(lblStartDate);

        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) days[i-1] = String.valueOf(i);
        String[] months = new String[12];
        for (int i = 1; i <= 12; i++) months[i-1] = String.valueOf(i);
        String[] years = new String[126];
        for (int i = 1900; i <= 2025; i++) years[i-1900] = String.valueOf(i);

        cbStartDay = new JComboBox<>(days);
        cbStartDay.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbStartDay.setBounds(70, 10, 50, 30);
        cbStartDay.setBackground(Color.WHITE);
        cbStartDay.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        filterPanel.add(cbStartDay);

        cbStartMonth = new JComboBox<>(months);
        cbStartMonth.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbStartMonth.setBounds(125, 10, 50, 30);
        cbStartMonth.setBackground(Color.WHITE);
        cbStartMonth.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        filterPanel.add(cbStartMonth);

        cbStartYear = new JComboBox<>(years);
        cbStartYear.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbStartYear.setBounds(180, 10, 70, 30);
        cbStartYear.setBackground(Color.WHITE);
        cbStartYear.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        filterPanel.add(cbStartYear);

        JLabel lblEndDate = new JLabel("Đến ngày:");
        lblEndDate.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblEndDate.setBounds(260, 10, 70, 30);
        filterPanel.add(lblEndDate);

        cbEndDay = new JComboBox<>(days);
        cbEndDay.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbEndDay.setBounds(330, 10, 50, 30);
        cbEndDay.setBackground(Color.WHITE);
        cbEndDay.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        filterPanel.add(cbEndDay);

        cbEndMonth = new JComboBox<>(months);
        cbEndMonth.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbEndMonth.setBounds(385, 10, 50, 30);
        cbEndMonth.setBackground(Color.WHITE);
        cbEndMonth.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        filterPanel.add(cbEndMonth);

        cbEndYear = new JComboBox<>(years);
        cbEndYear.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbEndYear.setBounds(440, 10, 70, 30);
        cbEndYear.setBackground(Color.WHITE);
        cbEndYear.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        filterPanel.add(cbEndYear);

        JButton btnFilter = createRoundedButton("Thống kê");
        btnFilter.setBounds(520, 10, 90, 30);
        btnFilter.addActionListener(e -> event.loadDataByFilter());
        filterPanel.add(btnFilter);

        JButton btnReset = createRoundedButton("Làm mới");
        btnReset.setBounds(620, 10, 90, 30);
        btnReset.addActionListener(e -> event.loadData());
        filterPanel.add(btnReset);

        btnExport = createRoundedButton("Xuất Excel");
        btnExport.setBounds(720, 10, 90, 30);
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

        chartScrollPane = new JScrollPane(chartPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chartScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        chartScrollPane.setPreferredSize(new Dimension(960, 400));
        chartScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        tableScrollPane.setPreferredSize(new Dimension(960, 300));
        tableScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        tableScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(tableScrollPane);
        contentPanel.add(Box.createVerticalStrut(10));

        UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());

        mainScrollPane = new JScrollPane(contentPanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainScrollPane.getVerticalScrollBar().setBlockIncrement(50);
        mainScrollPane.setBackground(Color.WHITE);
        mainScrollPane.getViewport().setBackground(Color.WHITE);
        add(mainScrollPane, BorderLayout.CENTER);

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
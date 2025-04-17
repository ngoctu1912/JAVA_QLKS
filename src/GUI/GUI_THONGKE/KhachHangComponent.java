package GUI_THONGKE;

import BUS.ThongKeBUS;
import DTO.KhachHangThongKeDTO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI; // THÊM
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class KhachHangComponent extends JPanel {
    private ThongKeBUS thongKeBUS;
    private JTable khachHangTable;
    private ArrayList<KhachHangThongKeDTO> danhSachKhachHang;
    private JTextField txtStartDate, txtEndDate;
    private JPanel contentPanel;
    private RoundedPanel filterPanel;
    private JScrollPane tableScrollPane;
    private JButton btnExport, btnFilter, btnReset, btnTopKhachHang;
    private DateTimeFormatter dateFormatter;
    private JLabel lblTableTitle;
    private KhachHangEvent eventHandler;

    private static LocalDate sharedStartDate = LocalDate.now().minusDays(30);
    private static LocalDate sharedEndDate = LocalDate.now();

    public KhachHangComponent(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS != null ? thongKeBUS : new ThongKeBUS();
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        eventHandler = new KhachHangEvent(this, this.thongKeBUS, dateFormatter);
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 245)); // SỬA: Màu nền giống RoomTablePanel
        setPreferredSize(new Dimension(960, 600));
        initComponents();
        eventHandler.loadData();
    }

    private void initComponents() {
        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(240, 245, 245)); // SỬA: Màu nền giống RoomTablePanel
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setDoubleBuffered(true);

        // Panel Bộ Lọc
        filterPanel = new RoundedPanel(20);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setLayout(null);
        filterPanel.setPreferredSize(new Dimension(960, 60));
        filterPanel.setMaximumSize(new Dimension(960, 60));
        filterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblStartDate = new JLabel("Từ ngày:");
        lblStartDate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblStartDate.setBounds(20, 15, 80, 30);
        filterPanel.add(lblStartDate);

        txtStartDate = new JTextField();
        txtStartDate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtStartDate.setBounds(100, 15, 120, 30);
        filterPanel.add(txtStartDate);

        JLabel lblEndDate = new JLabel("Đến ngày:");
        lblEndDate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblEndDate.setBounds(240, 15, 80, 30);
        filterPanel.add(lblEndDate);

        txtEndDate = new JTextField();
        txtEndDate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtEndDate.setBounds(320, 15, 120, 30);
        filterPanel.add(txtEndDate);

        btnFilter = createRoundedButton("Thống kê");
        btnFilter.setBounds(460, 15, 100, 30);
        btnFilter.addActionListener(e -> eventHandler.loadDataByFilter());
        filterPanel.add(btnFilter);

        btnReset = createRoundedButton("Làm mới");
        btnReset.setBounds(570, 15, 100, 30);
        btnReset.addActionListener(e -> eventHandler.loadData());
        filterPanel.add(btnReset);

        btnExport = createRoundedButton("Xuất Excel");
        btnExport.setBounds(680, 15, 100, 30);
        btnExport.addActionListener(e -> eventHandler.xuatExcel());
        filterPanel.add(btnExport);

        btnTopKhachHang = createRoundedButton("Top KH");
        btnTopKhachHang.setBounds(790, 15, 100, 30);
        btnTopKhachHang.addActionListener(e -> eventHandler.hienThiTop3KhachHangDatPhong());
        filterPanel.add(btnTopKhachHang);

        contentPanel.add(filterPanel);
        contentPanel.add(Box.createVerticalStrut(10));

        // Tiêu đề bảng
        lblTableTitle = new JLabel("Thống kê khách hàng", SwingConstants.CENTER);
        lblTableTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblTableTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblTableTitle);
        contentPanel.add(Box.createVerticalStrut(10));

        // Bảng khách hàng
        String[] columns = {"STT", "Mã khách hàng", "Tên khách hàng", "Số lần đặt phòng", "Tổng số tiền"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // THÊM: Không cho chỉnh sửa ô
            }
        };
        khachHangTable = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(new Color(183, 228, 199)); // SỬA: Màu nền giống RoomTablePanel
                c.setForeground(Color.BLACK);
                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER); // SỬA: Căn giữa
                return c;
            }

            @Override
            public JTableHeader getTableHeader() {
                JTableHeader header = super.getTableHeader();
                header.setPreferredSize(new Dimension(0, 40)); // SỬA: Chiều cao header
                return header;
            }
        };

        khachHangTable.setRowHeight(40); // SỬA: Chiều cao hàng
        khachHangTable.setFont(new Font("SansSerif", Font.PLAIN, 14)); // SỬA: Font giống RoomTablePanel
        khachHangTable.setShowVerticalLines(false); // SỬA: Không hiển thị đường dọc
        khachHangTable.setShowHorizontalLines(false); // SỬA: Không hiển thị đường ngang
        khachHangTable.setIntercellSpacing(new Dimension(0, 0)); // SỬA: Không có khoảng cách ô
        khachHangTable.setBorder(BorderFactory.createEmptyBorder()); // SỬA: Không viền

        // Header style
        JTableHeader header = khachHangTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15)); // SỬA: Font giống RoomTablePanel
        header.setBackground(Color.WHITE); // SỬA: Màu nền trắng
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false); // SỬA: Không cho phép sắp xếp lại cột
        header.setBorder(BorderFactory.createEmptyBorder()); // SỬA: Không viền
        header.setOpaque(false); // SỬA: Trong suốt

        // Make header "flat"
        UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder()); // THÊM

        // Column widths
        khachHangTable.getColumnModel().getColumn(0).setPreferredWidth(50); // STT
        khachHangTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Mã khách hàng
        khachHangTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Tên khách hàng
        khachHangTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Số lần đặt phòng
        khachHangTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Tổng số tiền

        // ScrollPane with 20px margin
        tableScrollPane = new JScrollPane(khachHangTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // THÊM: Margin 20px
        tableScrollPane.getViewport().setBackground(new Color(240, 245, 245)); // SỬA: Màu nền giống RoomTablePanel
        tableScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tableScrollPane.getVerticalScrollBar().setBlockIncrement(50);

        // Tuỳ chỉnh thanh cuộn mảnh & đẹp
        JScrollBar verticalScrollBar = tableScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(209, 207, 207); // SỬA: Màu thumb
                this.trackColor = new Color(245, 245, 245); // SỬA: Màu track
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
        verticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE)); // SỬA: Thanh cuộn mảnh

        contentPanel.add(tableScrollPane);
        contentPanel.add(Box.createVerticalStrut(10));

        add(contentPanel, BorderLayout.CENTER);

        // Thêm listener để điều chỉnh kích thước khi cửa sổ thay đổi
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                adjustTableSize();
            }
        });

        revalidate();
        repaint();
        adjustTableSize(); // THÊM: Gọi để điều chỉnh kích thước ban đầu
    }

    // THÊM: Phương thức điều chỉnh kích thước bảng
    private void adjustTableSize() {
        int width = getWidth();
        tableScrollPane.setPreferredSize(new Dimension(width - 20, Math.max(100, getHeight() - 150))); // 150 là khoảng cách cho filterPanel và padding
        tableScrollPane.revalidate();
        tableScrollPane.repaint();
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(text.equals("Làm mới") ? Color.RED : Color.decode("#B7E4C7"));
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
                    g2.setColor(text.equals("Làm mới") ? Color.decode("#cc0000") : Color.decode("#2eb82e"));
                } else if (model.isRollover()) {
                    g2.setColor(text.equals("Làm mới") ? Color.decode("#ff3333") : Color.decode("#3cdc3c"));
                } else {
                    g2.setColor(text.equals("Làm mới") ? Color.RED : Color.decode("#B7E4C7"));
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
        // Bỏ phương thức này vì đã tích hợp trực tiếp vào initComponents
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

    public JTable getKhachHangTable() { return khachHangTable; }
    public ArrayList<KhachHangThongKeDTO> getDanhSachKhachHang() { return danhSachKhachHang; }
    public void setDanhSachKhachHang(ArrayList<KhachHangThongKeDTO> danhSachKhachHang) { this.danhSachKhachHang = danhSachKhachHang; }
    public JTextField getTxtStartDate() { return txtStartDate; }
    public JTextField getTxtEndDate() { return txtEndDate; }
    public JLabel getLblTableTitle() { return lblTableTitle; }
    public JScrollPane getMainScrollPane() { return tableScrollPane; } // SỬA: Trả về tableScrollPane
    public KhachHangEvent getEventHandler() { return eventHandler; }
    public static LocalDate getSharedStartDate() { return sharedStartDate; }
    public static void setSharedStartDate(LocalDate startDate) { sharedStartDate = startDate; }
    public static LocalDate getSharedEndDate() { return sharedEndDate; }
    public static void setSharedEndDate(LocalDate endDate) { sharedEndDate = endDate; }
}
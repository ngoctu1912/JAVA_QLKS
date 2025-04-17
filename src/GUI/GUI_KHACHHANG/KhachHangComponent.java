package GUI_KHACHHANG;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class KhachHangComponent extends JPanel {
    private final JTable customerTable;
    private final DefaultTableModel tableModel;
    private final KhachHangBUS khachHangBUS;
    private final JPanel buttonPanel;
    private final JTextField searchField;
    private final JButton searchButton;
    private final JButton refreshButton;

    public KhachHangComponent() {
        this.khachHangBUS = new KhachHangBUS();

        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 245));

        // Panel chứa các nút
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(new Color(240, 245, 245));
        String[] buttonLabels = {"Thêm", "Sửa", "Xóa", "Chi tiết", "Nhập Excel", "Xuất Excel"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("SansSerif", Font.BOLD, 14));
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            buttonPanel.add(button);
        }

        // Thêm trường tìm kiếm
        searchField = new JTextField(20);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchButton = new JButton("Nhập nội dung tìm kiếm...");
        searchButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);

        // Thêm nút "Làm mới"
        refreshButton = new JButton("Làm mới");
        refreshButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(refreshButton);

        // Tạo bảng khách hàng
        String[] columnNames = {"STT", "Mã khách hàng", "Tên khách hàng", "Giới tính", "CCCD", "Địa chỉ", "Số điện thoại", "Email", "Trạng thái", "Ngày sinh"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        customerTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(new Color(183, 228, 199)); // Màu nền xanh nhạt
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

        customerTable.setRowHeight(40);
        customerTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        customerTable.setShowVerticalLines(false);
        customerTable.setShowHorizontalLines(false);
        customerTable.setIntercellSpacing(new Dimension(0, 0));
        customerTable.setBorder(BorderFactory.createEmptyBorder());

        // Định dạng tiêu đề bảng
        JTableHeader header = customerTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createEmptyBorder());

        UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());
        header.setOpaque(false);

        // Đặt độ rộng cột
        customerTable.getColumnModel().getColumn(0).setPreferredWidth(50); // STT
        customerTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Mã khách hàng
        customerTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Tên khách hàng
        customerTable.getColumnModel().getColumn(3).setPreferredWidth(80); // Giới tính
        customerTable.getColumnModel().getColumn(4).setPreferredWidth(100); // CCCD
        customerTable.getColumnModel().getColumn(5).setPreferredWidth(200); // Địa chỉ
        customerTable.getColumnModel().getColumn(6).setPreferredWidth(120); // Số điện thoại
        customerTable.getColumnModel().getColumn(7).setPreferredWidth(150); // Email
        customerTable.getColumnModel().getColumn(8).setPreferredWidth(80); // Trạng thái
        customerTable.getColumnModel().getColumn(9).setPreferredWidth(100); // Ngày sinh

        // Tạo thanh cuộn
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scrollPane.getViewport().setBackground(new Color(240, 245, 245));

        // Tùy chỉnh thanh cuộn
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
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
                // Không vẽ track
            }
        });
        verticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));

        // Thêm các thành phần vào panel chính
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Tải dữ liệu khách hàng
        loadAllCustomers();
    }

    public void loadAllCustomers() {
        tableModel.setRowCount(0);

        try {
            List<KhachHangDTO> customers = khachHangBUS.getAllCustomers();
            int rowCount = 0;
            for (KhachHangDTO customer : customers) {
                rowCount++;
                // Chuyển đổi giới tính và trạng thái thành chuỗi dễ đọc
                String gioiTinh = (customer.getGT() == 1) ? "Nam" : "Nữ";
                String trangThai = (customer.getTT() == 1) ? "Hoạt động" : "Ngưng hoạt động";
                tableModel.addRow(new Object[] {
                    rowCount,
                    customer.getMKH(),
                    customer.getTKH(),
                    gioiTinh,
                    customer.getCCCD(),
                    customer.getDIACHI(),
                    customer.getSDT(),
                    customer.getEMAIL(),
                    trangThai,
                    customer.getNgayThamGia()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Getter để truy cập các thành phần
    public JTable getCustomerTable() { return customerTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JPanel getButtonPanel() { return buttonPanel; }
    public JTextField getSearchField() { return searchField; }
    public JButton getSearchButton() { return searchButton; }
    public JButton getRefreshButton() { return refreshButton; }
}
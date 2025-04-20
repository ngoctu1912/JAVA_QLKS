package GUI_HOADON;

import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import helper.Formater;
import helper.JTableExporter;
import Component.ButtonToolBar;
import Component.InputDate;
import Component.NumericDocumentFilter;
import Component.SidebarPanel;
import Component.TableSorter;
import Component.PanelBorderRadius;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.PlainDocument;

public final class HoaDon extends JPanel implements ActionListener, KeyListener, PropertyChangeListener, ItemListener {
    private PanelBorderRadius main, functionBar, box;
    private JPanel contentCenter, pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4;
    private JFrame owner;
    private JTable tableHoaDon;
    private JScrollPane scrollTableHoaDon;
    private DefaultTableModel tblModel;
    private SelectForm cbxKhachHang, cbxNhanVien;
    private InputDate dateStart, dateEnd;
    private JTextField moneyMin, moneyMax;
    private HoaDonBUS hdBUS = new HoaDonBUS();
    private ArrayList<HoaDonDTO> listHD = hdBUS.getAll();
    private final Color BackgroundColor = new Color(193, 237, 220);
    private KhachHangBUS khachHangBUS = new KhachHangBUS();
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private SidebarPanel sidebarPanel;
    private static final Logger LOGGER = Logger.getLogger(HoaDon.class.getName());

    public HoaDon() {
        initComponent();
        if (listHD == null || listHD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách hóa đơn. Vui lòng kiểm tra cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            loadDataTable(listHD);
        }
    }

    private void initComponent() {
        setBackground(BackgroundColor);
        setLayout(new BorderLayout(0, 0));

        // Initialize padding
        initPadding();

        // Initialize content center
        contentCenter = new JPanel(new BorderLayout(10, 10));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setBorder(null);
        add(contentCenter, BorderLayout.CENTER);

        // Initialize function bar
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new BorderLayout());
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Initialize SidebarPanel with search options
        String[] searchOptions = {"Tất cả", "Mã HD", "Mã CTT", "Hình thức TT"};
        try {
            sidebarPanel = new SidebarPanel(
                e -> openCreateDialog(), // THÊM
                e -> openUpdateDialog(), // SỬA
                e -> cancelHoaDon(), // XÓA
                e -> openDetailDialog(), // CHI TIẾT
                e -> exportExcel(), // XUẤT EXCEL
                e -> resetForm(), // Làm mới
                null, // Không cần DocumentListener
                searchOptions // Truyền danh sách tùy chọn tìm kiếm
            );
            sidebarPanel.getSearchField().addKeyListener(this);
            sidebarPanel.getCbxChoose().addItemListener(this);
            functionBar.add(sidebarPanel, BorderLayout.CENTER);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing SidebarPanel: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Lỗi khi khởi tạo thanh công cụ: " + e.getMessage());
        }

        contentCenter.add(functionBar, BorderLayout.NORTH);

        // Initialize left filter panel
        leftFunc();

        // Initialize main panel
        main = new PanelBorderRadius();
        main.setLayout(new BorderLayout());
        main.setBorder(null);
        contentCenter.add(main, BorderLayout.CENTER);

        // Initialize table
        tableHoaDon = new JTable();
        tableHoaDon.setBackground(new Color(0xA1D6E2));
        tableHoaDon.setRowHeight(35);
        tableHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableHoaDon.setShowHorizontalLines(true);
        tableHoaDon.setShowVerticalLines(false);
        tableHoaDon.setGridColor(Color.WHITE);
        scrollTableHoaDon = new JScrollPane(tableHoaDon);
        tblModel = new DefaultTableModel();
        String[] header = { "Mã HD", "Mã CTT", "Tiền phòng", "Tiền DV", "Giảm giá", "Phụ thu", "Tổng tiền",
                "Ngày thanh toán", "Hình thức TT", "Xử lý" };
        tblModel.setColumnIdentifiers(header);
        tableHoaDon.setModel(tblModel);
        scrollTableHoaDon.setBorder(null);
        scrollTableHoaDon.setViewportBorder(null);

        // Customize scroll bar
        JScrollBar verticalScrollBar = scrollTableHoaDon.getVerticalScrollBar();
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

        // Customize table header
        JTableHeader headerTable = tableHoaDon.getTableHeader();
        headerTable.setBackground(Color.WHITE);
        headerTable.setBorder(BorderFactory.createEmptyBorder());
        headerTable.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerTable.setPreferredSize(new Dimension(headerTable.getWidth(), 35));
        headerTable.setReorderingAllowed(false);
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) headerTable.getDefaultRenderer();
        headerRenderer.setBorder(null);
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setBackground(Color.WHITE);

        // Center align cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        TableColumnModel columnModel = tableHoaDon.getColumnModel();
        for (int i = 0; i < header.length; i++) {
            if (i != 8) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        columnModel.getColumn(7).setPreferredWidth(200);
        columnModel.getColumn(8).setPreferredWidth(80);
        columnModel.getColumn(9).setPreferredWidth(80);
        tableHoaDon.setFocusable(false);
        tableHoaDon.setAutoCreateRowSorter(true);

        // Configure sorters
        TableSorter.configureTableColumnSorter(tableHoaDon, 2, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableHoaDon, 3, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableHoaDon, 4, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableHoaDon, 5, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableHoaDon, 6, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableHoaDon, 7, TableSorter.DATE_COMPARATOR);
        tableHoaDon.setDefaultEditor(Object.class, null);

        main.add(scrollTableHoaDon, BorderLayout.CENTER);
    }

    private void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        add(pnlBorder4, BorderLayout.WEST);
    }

    public void leftFunc() {
        box = new PanelBorderRadius();
        box.setPreferredSize(new Dimension(250, 0));
        box.setLayout(new GridLayout(6, 1, 10, 0));
        box.setBorder(new EmptyBorder(0, 5, 150, 5));
        contentCenter.add(box, BorderLayout.WEST);

        // Lấy danh sách tên khách hàng từ KhachHangBUS
        List<KhachHangDTO> khachHangList = khachHangBUS.getAllCustomers();
        String[] listKh = khachHangList == null || khachHangList.isEmpty() 
            ? new String[] { "Tất cả" }
            : Stream.concat(
                Stream.of("Tất cả"), 
                khachHangList.stream().map(KhachHangDTO::getTenKhachHang)
            ).toArray(String[]::new);

        String[] listNv = nvBUS.getArrTenNhanVien();
        listNv = listNv == null || listNv.length == 0 
            ? new String[] { "Tất cả" }
            : Stream.concat(Stream.of("Tất cả"), Arrays.stream(listNv)).toArray(String[]::new);

        cbxKhachHang = new SelectForm("Khách hàng", listKh);
        cbxNhanVien = new SelectForm("Nhân viên", listNv);
        dateStart = new InputDate("Từ ngày");
        dateEnd = new InputDate("Đến ngày");

        JLabel lblMoneyMin = new JLabel("Từ số tiền (VND)");
        moneyMin = new JTextField();
        moneyMin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        PlainDocument docMin = (PlainDocument) moneyMin.getDocument();
        docMin.setDocumentFilter(new NumericDocumentFilter());

        JLabel lblMoneyMax = new JLabel("Đến số tiền (VND)");
        moneyMax = new JTextField();
        moneyMax.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        PlainDocument docMax = (PlainDocument) moneyMax.getDocument();
        docMax.setDocumentFilter(new NumericDocumentFilter());

        cbxKhachHang.getCbb().addItemListener(this);
        cbxNhanVien.getCbb().addItemListener(this);
        dateStart.getDateChooser().addPropertyChangeListener(this);
        dateEnd.getDateChooser().addPropertyChangeListener(this);
        moneyMin.addKeyListener(this);
        moneyMax.addKeyListener(this);

        box.add(cbxKhachHang);
        box.add(cbxNhanVien);
        box.add(dateStart);
        box.add(dateEnd);
        JPanel minPanel = new JPanel(new GridLayout(2, 1));
        minPanel.add(lblMoneyMin);
        minPanel.add(moneyMin);
        box.add(minPanel);
        JPanel maxPanel = new JPanel(new GridLayout(2, 1));
        maxPanel.add(lblMoneyMax);
        maxPanel.add(moneyMax);
        box.add(maxPanel);
    }

    public void loadDataTable(ArrayList<HoaDonDTO> result) {
        tblModel.setRowCount(0);
        for (HoaDonDTO hd : result) {
            tblModel.addRow(new Object[] {
                    hd.getMaHD() != null ? hd.getMaHD() : "",
                    hd.getMaCTT() != null ? hd.getMaCTT() : "",
                    Formater.FormatVND(hd.getTienP()),
                    Formater.FormatVND(hd.getTienDV()),
                    Formater.FormatVND(hd.getGiamGia()),
                    Formater.FormatVND(hd.getPhuThu()),
                    Formater.FormatVND(hd.getTongTien()),
                    hd.getNgayThanhToan() != null ? Formater.FormatTime(hd.getNgayThanhToan()) : "",
                    hd.getHinhThucThanhToan() != null ? hd.getHinhThucThanhToan() : "",
                    hd.getXuLy() == 1 ? "Đã xử lý" : "Đã hủy"
            });
        }
    }

    public int getRowSelected() {
        int index = tableHoaDon.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn trong bảng",
                    "Chưa chọn hóa đơn", JOptionPane.WARNING_MESSAGE);
        }
        return index;
    }

    public void filter() throws ParseException {
        if (validateSelectDate()) {
            String searchType = (String) sidebarPanel.getCbxChoose().getSelectedItem();
            int type;
            switch (searchType) {
                case "Mã HD":
                    type = 1;
                    break;
                case "Mã CTT":
                    type = 2;
                    break;
                case "Hình thức TT":
                    type = 3;
                    break;
                default:
                    type = 0; // Tất cả
                    break;
            }

            String maKH = null;
            if (cbxKhachHang.getSelectedIndex() > 0) {
                List<KhachHangDTO> khachHangList = khachHangBUS.getAllCustomers();
                if (khachHangList != null && cbxKhachHang.getSelectedIndex() - 1 < khachHangList.size()) {
                    KhachHangDTO kh = khachHangList.get(cbxKhachHang.getSelectedIndex() - 1);
                    maKH = kh != null ? kh.getMaKhachHang() : null;
                }
            }

            int maNV = 0;
            if (cbxNhanVien.getSelectedIndex() > 0) {
                NhanVienDTO nv = nvBUS.getByIndex(cbxNhanVien.getSelectedIndex() - 1);
                maNV = (nv != null) ? nv.getMaNhanVien() : 0;
            }

            String input = sidebarPanel.getSearchField().getText() != null ? sidebarPanel.getSearchField().getText().trim() : "";
            if (input.equals("Nhập nội dung tìm kiếm...")) {
                input = "";
            }
            Date timeStart = dateStart.getDate() != null ? dateStart.getDate() : new Date(0);
            Date timeEnd = dateEnd.getDate() != null ? dateEnd.getDate() : new Date(System.currentTimeMillis());
            String minPrice = moneyMin.getText().trim();
            String maxPrice = moneyMax.getText().trim();

            try {
                this.listHD = hdBUS.filterHoaDon(type, input, maKH, maNV, timeStart, timeEnd, minPrice, maxPrice);
                if (listHD == null) {
                    LOGGER.warning("filterHoaDon returned null");
                    listHD = new ArrayList<>();
                }
                loadDataTable(listHD);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error filtering invoices: " + ex.getMessage(), ex);
                JOptionPane.showMessageDialog(this, "Lỗi khi lọc hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void resetForm() {
        cbxKhachHang.setSelectedIndex(0);
        cbxNhanVien.setSelectedIndex(0);
        sidebarPanel.getSearchField().setText("");
        sidebarPanel.getCbxChoose().setSelectedIndex(0);
        moneyMin.setText("");
        moneyMax.setText("");
        dateStart.getDateChooser().setCalendar(null);
        dateEnd.getDateChooser().setCalendar(null);
        this.listHD = hdBUS.getAll();
        loadDataTable(listHD);
    }

    public boolean validateSelectDate() throws ParseException {
        Date timeStart = dateStart.getDate();
        Date timeEnd = dateEnd.getDate();
        Date currentDate = new Date();
        if (timeStart != null && timeStart.after(currentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày hiện tại", "Lỗi!",
                    JOptionPane.ERROR_MESSAGE);
            dateStart.getDateChooser().setCalendar(null);
            return false;
        }
        if (timeEnd != null && timeEnd.after(currentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được lớn hơn ngày hiện tại", "Lỗi!",
                    JOptionPane.ERROR_MESSAGE);
            dateEnd.getDateChooser().setCalendar(null);
            return false;
        }
        if (timeStart != null && timeEnd != null && timeStart.after(timeEnd)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi!",
                    JOptionPane.ERROR_MESSAGE);
            dateEnd.getDateChooser().setCalendar(null);
            return false;
        }
        return true;
    }

    private void openCreateDialog() {
        if (owner == null) {
            initializeOwner();
        }
        new HoaDonDialog(this, owner, "Thêm hóa đơn mới", true, "create").setVisible(true);
    }

    private void openUpdateDialog() {
        if (owner == null) {
            initializeOwner();
        }
        int index = getRowSelected();
        if (index != -1) {
            new HoaDonDialog(this, owner, "Cập nhật hóa đơn", true, "update", listHD.get(index)).setVisible(true);
        }
    }

    private void openDetailDialog() {
        if (owner == null) {
            initializeOwner();
        }
        int index = getRowSelected();
        if (index != -1) {
            new HoaDonDialog(this, owner, "Xem chi tiết hóa đơn", true, "view", listHD.get(index)).setVisible(true);
        }
    }

    private void cancelHoaDon() {
        if (owner == null) {
            initializeOwner();
        }
        int index = getRowSelected();
        if (index != -1) {
            int input = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn hủy hóa đơn này?",
                    "Hủy hóa đơn",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (input == JOptionPane.OK_OPTION) {
                hdBUS.cancel(listHD.get(index).getMaHD());
                listHD = hdBUS.getAll();
                loadDataTable(listHD);
                JOptionPane.showMessageDialog(this, "Hủy hóa đơn thành công!");
            }
        }
    }

    private void exportExcel() {
        if (owner == null) {
            initializeOwner();
        }
        try {
            if (tableHoaDon.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Bảng hóa đơn rỗng. Không có dữ liệu để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean success = JTableExporter.exportJTableToExcel(tableHoaDon);
            if (success) {
                JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Đã hủy xuất Excel.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất Excel: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: JTableExporter không khả dụng.");
        }
    }

    private void initializeOwner() {
        owner = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (owner == null) {
            owner = new JFrame("Quản lý hóa đơn");
            owner.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = Math.min(1200, screenSize.width - 50);
            int height = Math.min(600, screenSize.height - 50);
            owner.setSize(width, height);
            owner.add(this);
            owner.setLocationRelativeTo(null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Sự kiện được xử lý trực tiếp trong SidebarPanel
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            filter();
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, "Error filtering data: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            filter();
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, "Error processing property change: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            try {
                filter();
            } catch (ParseException ex) {
                LOGGER.log(Level.SEVERE, "Error processing item state change: " + ex.getMessage(), ex);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error setting look and feel: " + e.getMessage(), e);
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý hóa đơn");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = Math.min(1200, screenSize.width - 50);
            int height = Math.min(600, screenSize.height - 50);
            frame.setSize(width, height);
            frame.add(new HoaDon());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
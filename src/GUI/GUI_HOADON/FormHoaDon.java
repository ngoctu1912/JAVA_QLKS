package GUI_HOADON;

import BUS.HoaDonBUS;
import DTO.HoaDonDTO;
import Component.IntegratedSearch;
import Component.PanelBorderRadius;
import Component.SidebarPanel;
import Component.TableSorter;
import helper.JTableExporter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FormHoaDon extends JPanel {
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JFrame owner;
    private JTable tableHoaDon;
    private JScrollPane scrollTableHoaDon;
    private SidebarPanel sidebarPanel;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private ArrayList<HoaDonDTO> listHoaDon;
    private String maKH; // Biến maKH để lọc hóa đơn
    private Color BackgroundColor = new Color(240, 245, 245);
    private HoaDonEventHandler eventHandler;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // Constructor cho tài khoản quản lý (hiển thị tất cả hóa đơn)
    public FormHoaDon(JFrame parentFrame, int manhomquyen, String chucnang) {
        this(parentFrame, manhomquyen, chucnang, null);
    }

    // Constructor cho tài khoản khách hàng (lọc theo maKH)
    public FormHoaDon(JFrame parentFrame, int manhomquyen, String chucnang, String maKH) {
        this.owner = parentFrame;
        this.maKH = maKH;
        System.out.println("FormHoaDon initialized with maKH: " + maKH);
        initComponent(manhomquyen, chucnang);
        this.eventHandler = new HoaDonEventHandler(this);
        loadTableData();
    }

    public JFrame getOwner() {
        return owner;
    }

    public JTable getTable() {
        return tableHoaDon;
    }

    public HoaDonBUS getHoaDonBUS() {
        return hoaDonBUS;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public void loadTableData() {
        System.out.println("Loading table data for maKH: " + maKH);
        try {
            if (maKH != null && !maKH.isEmpty()) {
                // Lọc hóa đơn theo maKH
                listHoaDon = hoaDonBUS.filterHoaDon(0, "", maKH, 0, new java.util.Date(0), new java.util.Date(), "", "");
                System.out.println("Filtered invoices for maKH=" + maKH + ": " + (listHoaDon != null ? listHoaDon.size() : 0) + " invoices");
                if (listHoaDon.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Không tìm thấy hóa đơn nào cho khách hàng này. Vui lòng kiểm tra dữ liệu đặt phòng hoặc liên hệ quản lý!", 
                        "Thông báo", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // Lấy tất cả hóa đơn
                listHoaDon = hoaDonBUS.getAll();
                System.out.println("Loaded all invoices: " + (listHoaDon != null ? listHoaDon.size() : 0) + " invoices");
            }
            loadDataTable(listHoaDon);
        } catch (Exception e) {
            System.err.println("Error loading table data: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponent(int manhomquyen, String chucnang) {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Khởi tạo bảng
        tableHoaDon = new JTable();
        tableHoaDon.setBackground(new Color(0xA1D6E2));
        scrollTableHoaDon = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "STT", "Mã Hóa Đơn", "Mã Chi Tiết Thuê", "Tiền Phòng", "Tiền Dịch Vụ",
                "Giảm Giá", "Phụ Thu", "Tổng Tiền", "Ngày Thanh Toán", "Hình Thức Thanh Toán", "Xử Lý" };
        tblModel.setColumnIdentifiers(header);
        tableHoaDon.setModel(tblModel);
        scrollTableHoaDon.setViewportView(tableHoaDon);

        // Căn giữa các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableHoaDon.getColumnModel();
        for (int i = 0; i < header.length; i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }

        // Thiết lập kích thước cột
        tableHoaDon.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableHoaDon.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableHoaDon.getColumnModel().getColumn(8).setPreferredWidth(100);
        tableHoaDon.setFocusable(false);
        tableHoaDon.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableHoaDon, 3, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableHoaDon, 4, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableHoaDon, 5, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableHoaDon, 6, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableHoaDon, 7, TableSorter.VND_CURRENCY_COMPARATOR);
        tableHoaDon.setDefaultEditor(Object.class, null);

        // Tùy chỉnh chiều cao dòng và đường phân cách
        tableHoaDon.setRowHeight(30);
        tableHoaDon.getTableHeader().setPreferredSize(new Dimension(tableHoaDon.getTableHeader().getPreferredSize().width, 30));
        tableHoaDon.setShowHorizontalLines(true);
        tableHoaDon.setShowVerticalLines(false);
        tableHoaDon.setGridColor(Color.WHITE);
        tableHoaDon.setIntercellSpacing(new Dimension(0, 1));
        tableHoaDon.setFocusable(false);
        tableHoaDon.setAutoCreateRowSorter(true);
        tableHoaDon.setDefaultEditor(Object.class, null);

        // Tùy chỉnh tiêu đề
        tableHoaDon.getTableHeader().setBackground(Color.WHITE);
        tableHoaDon.getTableHeader().setForeground(Color.BLACK);
        tableHoaDon.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        ((DefaultTableCellRenderer) tableHoaDon.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        // Nền bảng
        scrollTableHoaDon.setBackground(Color.WHITE);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // Thanh chức năng bo góc với PanelBorderRadius
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] actions = maKH != null ? new String[] { "detail" } : new String[] { "create", "update", "delete", "detail", "export" };
        sidebarPanel = new SidebarPanel(manhomquyen, chucnang, actions);
        for (String action : actions) {
            JButton button = sidebarPanel.btn.get(action);
            if (button != null) {
                button.setPreferredSize(new Dimension(70, 50));
            }
        }
        functionBar.add(sidebarPanel);

        // Cập nhật IntegratedSearch với các tùy chọn tìm kiếm
        search = new IntegratedSearch(new String[] { "Tất cả", "Mã Hóa Đơn", "Mã Chi Tiết Thuê", "Hình Thức Thanh Toán", "Khách hàng" });
        search.txtSearchForm.setPreferredSize(new Dimension(100, search.txtSearchForm.getPreferredSize().height));
        search.btnReset.setPreferredSize(new Dimension(120, 25));
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                search();
            }
        });

        search.btnReset.addActionListener(e -> {
            search.txtSearchForm.setText("");
            search.cbxChoose.setSelectedIndex(0);
            loadTableData();
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        // Phần main chứa bảng
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableHoaDon);
    }

    private void loadDataTable(ArrayList<HoaDonDTO> result) {
        tblModel.setRowCount(0);
        if (result == null) {
            System.out.println("loadDataTable: result is null");
            return;
        }
        int stt = 1;
        for (HoaDonDTO hd : result) {
            String xuLy = hd.getXuLy() == 1 ? "Đã xử lý" : "Đã hủy";
            tblModel.addRow(new Object[] {
                    stt++,
                    hd.getMaHD(),
                    hd.getMaCTT(),
                    hd.getTienP() + "đ",
                    hd.getTienDV() + "đ",
                    hd.getGiamGia() + "đ",
                    hd.getPhuThu() + "đ",
                    hd.getTongTien() + "đ",
                    dateFormat.format(hd.getNgayThanhToan()),
                    hd.getHinhThucThanhToan(),
                    xuLy
            });
        }
        System.out.println("loadDataTable: Loaded " + result.size() + " rows");
    }

    private void search() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String type = (String) search.cbxChoose.getSelectedItem();
        ArrayList<HoaDonDTO> result;
        try {
            // Chuyển type sang giá trị số tương ứng với filterHoaDon
            int searchType;
            switch (type) {
                case "Mã Hóa Đơn":
                    searchType = 1;
                    break;
                case "Mã Chi Tiết Thuê":
                    searchType = 2;
                    break;
                case "Hình Thức Thanh Toán":
                    searchType = 3;
                    break;
                case "Khách hàng":
                    searchType = 0; // Sử dụng filterHoaDon với keyword để tìm khách hàng
                    break;
                default:
                    searchType = 0; // Tất cả
                    break;
            }
            
            // Gọi filterHoaDon để tìm kiếm, tích hợp cả maKH nếu có
            result = hoaDonBUS.filterHoaDon(
                searchType, 
                keyword, 
                maKH != null && !maKH.isEmpty() ? maKH : "", 
                0, 
                new java.util.Date(0), 
                new java.util.Date(), 
                "", 
                ""
            );
            
            System.out.println("Search result for type=" + type + ", keyword=" + keyword + ", maKH=" + maKH + ": " + 
                (result != null ? result.size() : 0) + " invoices");
            
            if (result.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Không tìm thấy hóa đơn phù hợp với tiêu chí tìm kiếm!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            loadDataTable(result);
        } catch (Exception e) {
            System.err.println("Error during search: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tìm kiếm hóa đơn: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }
}
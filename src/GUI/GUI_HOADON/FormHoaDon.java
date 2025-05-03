// package GUI_HOADON;

// import BUS.HoaDonBUS;
// import DTO.HoaDonDTO;
// import Component.IntegratedSearch;
// import Component.PanelBorderRadius;
// import Component.SidebarPanel;
// import Component.TableSorter;
// import helper.JTableExporter;

// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import javax.swing.table.DefaultTableCellRenderer;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.table.TableColumnModel;
// import java.awt.*;
// import java.awt.event.KeyAdapter;
// import java.awt.event.KeyEvent;
// import java.io.IOException;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;

// public final class FormHoaDon extends JPanel {
//     private PanelBorderRadius main, functionBar;
//     private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
//     private JFrame owner;
//     private JTable tableHoaDon;
//     private JScrollPane scrollTableHoaDon;
//     private SidebarPanel sidebarPanel;
//     private IntegratedSearch search;
//     private DefaultTableModel tblModel;
//     private HoaDonBUS hoaDonBUS = new HoaDonBUS();
//     private ArrayList<HoaDonDTO> listHoaDon = hoaDonBUS.getAll();
//     private Color BackgroundColor = new Color(240, 245, 245);
//     private HoaDonEventHandler eventHandler;
//     private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

//     public FormHoaDon(JFrame parentFrame, int manhomquyen, String chucnang) {
//         this.owner = parentFrame;
//         initComponent(manhomquyen, chucnang);
//         this.eventHandler = new HoaDonEventHandler(this);
//         loadTableData();
//     }

//     public JFrame getOwner() {
//         return owner;
//     }

//     public JTable getTable() {
//         return tableHoaDon;
//     }

//     public HoaDonBUS getHoaDonBUS() {
//         return hoaDonBUS;
//     }

//     public SidebarPanel getSidebarPanel() {
//         return sidebarPanel;
//     }

//     public void loadTableData() {
//         listHoaDon = hoaDonBUS.getAll();
//         loadDataTable(listHoaDon);
//     }

//     private void initComponent(int manhomquyen, String chucnang) {
//         this.setBackground(BackgroundColor);
//         this.setLayout(new BorderLayout(0, 0));
//         this.setOpaque(true);

//         // Khởi tạo bảng
//         tableHoaDon = new JTable();
//         tableHoaDon.setBackground(new Color(0xA1D6E2));
//         scrollTableHoaDon = new JScrollPane();
//         tblModel = new DefaultTableModel();
//         String[] header = new String[] { "STT", "Mã Hóa Đơn", "Mã Chi Tiết Thuê", "Tiền Phòng", "Tiền Dịch Vụ",
//                 "Giảm Giá", "Phụ Thu", "Tổng Tiền", "Ngày Thanh Toán", "Hình Thức Thanh Toán", "Xử Lý" };
//         tblModel.setColumnIdentifiers(header);
//         tableHoaDon.setModel(tblModel);
//         scrollTableHoaDon.setViewportView(tableHoaDon);

//         // Căn giữa các cột
//         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//         centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//         TableColumnModel columnModel = tableHoaDon.getColumnModel();
//         for (int i = 0; i < header.length; i++) {
//             columnModel.getColumn(i).setCellRenderer(centerRenderer);
//         }

//         // Thiết lập kích thước cột
//         tableHoaDon.getColumnModel().getColumn(1).setPreferredWidth(100);
//         tableHoaDon.getColumnModel().getColumn(2).setPreferredWidth(120);
//         tableHoaDon.getColumnModel().getColumn(8).setPreferredWidth(100);
//         tableHoaDon.setFocusable(false);
//         tableHoaDon.setAutoCreateRowSorter(true);
//         TableSorter.configureTableColumnSorter(tableHoaDon, 3, TableSorter.VND_CURRENCY_COMPARATOR);
//         TableSorter.configureTableColumnSorter(tableHoaDon, 4, TableSorter.VND_CURRENCY_COMPARATOR);
//         TableSorter.configureTableColumnSorter(tableHoaDon, 5, TableSorter.VND_CURRENCY_COMPARATOR);
//         TableSorter.configureTableColumnSorter(tableHoaDon, 6, TableSorter.VND_CURRENCY_COMPARATOR);
//         TableSorter.configureTableColumnSorter(tableHoaDon, 7, TableSorter.VND_CURRENCY_COMPARATOR);
//         tableHoaDon.setDefaultEditor(Object.class, null);

//         // Tùy chỉnh chiều cao dòng và đường phân cách
//         tableHoaDon.setRowHeight(30);
//         tableHoaDon.getTableHeader().setPreferredSize(new Dimension(tableHoaDon.getTableHeader().getPreferredSize().width, 30));
//         tableHoaDon.setShowHorizontalLines(true);
//         tableHoaDon.setShowVerticalLines(false);
//         tableHoaDon.setGridColor(Color.WHITE);
//         tableHoaDon.setIntercellSpacing(new Dimension(0, 1));
//         tableHoaDon.setFocusable(false);
//         tableHoaDon.setAutoCreateRowSorter(true);
//         tableHoaDon.setDefaultEditor(Object.class, null);

//         // Tùy chỉnh tiêu đề
//         tableHoaDon.getTableHeader().setBackground(Color.WHITE);
//         tableHoaDon.getTableHeader().setForeground(Color.BLACK);
//         tableHoaDon.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
//         ((DefaultTableCellRenderer) tableHoaDon.getTableHeader().getDefaultRenderer())
//                 .setHorizontalAlignment(JLabel.CENTER);

//         // Nền bảng
//         scrollTableHoaDon.setBackground(Color.WHITE);

//         initPadding();

//         contentCenter = new JPanel();
//         contentCenter.setBackground(BackgroundColor);
//         contentCenter.setLayout(new BorderLayout(10, 10));
//         this.add(contentCenter, BorderLayout.CENTER);

//         // Thanh chức năng bo góc với PanelBorderRadius
//         functionBar = new PanelBorderRadius();
//         functionBar.setPreferredSize(new Dimension(0, 100));
//         functionBar.setLayout(new GridLayout(1, 2, 50, 0));
//         functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

//         String[] actions = { "create", "update", "delete", "detail", "export" };
//         sidebarPanel = new SidebarPanel(manhomquyen, chucnang, actions);
//         for (String action : actions) {
//             JButton button = sidebarPanel.btn.get(action);
//             if (button != null) {
//                 button.setPreferredSize(new Dimension(70, 50));
//             }
//         }
//         functionBar.add(sidebarPanel);

//         // Cập nhật IntegratedSearch với các tùy chọn tìm kiếm
//         search = new IntegratedSearch(new String[] { "Tất cả", "Mã Hóa Đơn", "Mã Chi Tiết Thuê", "Hình Thức Thanh Toán" });
//         search.txtSearchForm.setPreferredSize(new Dimension(100, search.txtSearchForm.getPreferredSize().height));
//         search.btnReset.setPreferredSize(new Dimension(120, 25));
//         search.txtSearchForm.addKeyListener(new KeyAdapter() {
//             @Override
//             public void keyReleased(KeyEvent e) {
//                 search();
//             }
//         });

//         search.btnReset.addActionListener(e -> {
//             search.txtSearchForm.setText("");
//             search.cbxChoose.setSelectedIndex(0);
//             listHoaDon = hoaDonBUS.getAll();
//             loadTableData();
//         });
//         functionBar.add(search);

//         contentCenter.add(functionBar, BorderLayout.NORTH);

//         // Phần main chứa bảng
//         main = new PanelBorderRadius();
//         BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
//         main.setLayout(boxly);
//         main.setBorder(new EmptyBorder(0, 0, 0, 0));
//         contentCenter.add(main, BorderLayout.CENTER);
//         main.add(scrollTableHoaDon);
//     }

//     private void loadDataTable(ArrayList<HoaDonDTO> result) {
//         tblModel.setRowCount(0);
//         int stt = 1;
//         for (HoaDonDTO hd : result) {
//             String xuLy = hd.getXuLy() == 1 ? "Đã xử lý" : "Đã hủy";
//             tblModel.addRow(new Object[] {
//                     stt++,
//                     hd.getMaHD(),
//                     hd.getMaCTT(),
//                     hd.getTienP() + "đ",
//                     hd.getTienDV() + "đ",
//                     hd.getGiamGia() + "đ",
//                     hd.getPhuThu() + "đ",
//                     hd.getTongTien() + "đ",
//                     dateFormat.format(hd.getNgayThanhToan()),
//                     hd.getHinhThucThanhToan(),
//                     xuLy
//             });
//         }
//     }

//     private void search() {
//         String keyword = search.txtSearchForm.getText().trim().toLowerCase();
//         String type = (String) search.cbxChoose.getSelectedItem();
//         ArrayList<HoaDonDTO> result = hoaDonBUS.search(keyword, type);
//         loadDataTable(result);
//     }

//     private void initPadding() {
//         pnlBorder1 = new JPanel();
//         pnlBorder1.setPreferredSize(new Dimension(0, 10));
//         pnlBorder1.setBackground(BackgroundColor);
//         this.add(pnlBorder1, BorderLayout.NORTH);

//         pnlBorder2 = new JPanel();
//         pnlBorder2.setPreferredSize(new Dimension(0, 10));
//         pnlBorder2.setBackground(BackgroundColor);
//         this.add(pnlBorder2, BorderLayout.SOUTH);

//         pnlBorder3 = new JPanel();
//         pnlBorder3.setPreferredSize(new Dimension(10, 0));
//         pnlBorder3.setBackground(BackgroundColor);
//         this.add(pnlBorder3, BorderLayout.EAST);

//         pnlBorder4 = new JPanel();
//         pnlBorder4.setPreferredSize(new Dimension(10, 0));
//         pnlBorder4.setBackground(BackgroundColor);
//         this.add(pnlBorder4, BorderLayout.WEST);
//     }
// }

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
    private String maKH; // Thêm biến maKH để lọc hóa đơn
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
        if (maKH != null && !maKH.isEmpty()) {
            // Lọc hóa đơn theo maKH
            listHoaDon = hoaDonBUS.filterHoaDon(0, "", maKH, 0, new java.util.Date(0), new java.util.Date(), "", "");
        } else {
            // Lấy tất cả hóa đơn
            listHoaDon = hoaDonBUS.getAll();
        }
        loadDataTable(listHoaDon);
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
        search = new IntegratedSearch(new String[] { "Tất cả", "Mã Hóa Đơn", "Mã Chi Tiết Thuê", "Hình Thức Thanh Toán" });
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
    }

    private void search() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String type = (String) search.cbxChoose.getSelectedItem();
        ArrayList<HoaDonDTO> result = hoaDonBUS.search(keyword, type);
        if (maKH != null && !maKH.isEmpty()) {
            // Lọc thêm theo maKH
            result = hoaDonBUS.filterHoaDon(0, keyword, maKH, 0, new java.util.Date(0), new java.util.Date(), "", "");
        }
        loadDataTable(result);
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
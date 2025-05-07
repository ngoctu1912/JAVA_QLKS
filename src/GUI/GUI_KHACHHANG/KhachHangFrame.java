package GUI_KHACHHANG;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
import Component.IntegratedSearch;
import Component.PanelBorderRadius;
import Component.SidebarPanel;
import Component.TableSorter;
import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class KhachHangFrame extends JPanel {
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JFrame owner;
    private JTable tableKhachHang;
    private JScrollPane scrollTableKhachHang;
    private SidebarPanel sidebarPanel;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private KhachHangBUS khachHangBUS = new KhachHangBUS();
    private ArrayList<KhachHangDTO> listKhachHang;
    private Color BackgroundColor = new Color(240, 245, 245);
    private KhachHangEvent eventHandler;
    private AccountInfo accountInfo; // Thêm biến lưu thông tin tài khoản

    public KhachHangFrame(JFrame parentFrame, int manhomquyen, String chucnang, AccountInfo accountInfo) {
        this.owner = parentFrame;
        this.accountInfo = accountInfo; // Lưu thông tin tài khoản
        if (accountInfo != null && "KHACHHANG".equals(accountInfo.getAccountType())) {
            // Nếu là tài khoản khách hàng, chỉ lấy thông tin của khách hàng đó
            KhachHangDTO khachHang = khachHangBUS.selectById(String.valueOf(accountInfo.getAccountId()));
            this.listKhachHang = new ArrayList<>();
            if (khachHang != null) {
                this.listKhachHang.add(khachHang);
            }
        } else {
            // Nếu không phải tài khoản khách hàng, lấy toàn bộ danh sách
            this.listKhachHang = khachHangBUS.selectAll();
        }
        initComponent(manhomquyen, chucnang);
        this.eventHandler = new KhachHangEvent(this);
        loadTableData();
    }

    public JFrame getOwner() {
        return owner;
    }

    public JTable getTable() {
        return tableKhachHang;
    }

    public KhachHangBUS getKhachHangBUS() {
        return khachHangBUS;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void loadTableData() {
        if (accountInfo != null && "KHACHHANG".equals(accountInfo.getAccountType())) {
            // Nếu là tài khoản khách hàng, chỉ hiển thị thông tin của khách hàng đó
            KhachHangDTO khachHang = khachHangBUS.selectById(String.valueOf(accountInfo.getAccountId()));
            listKhachHang = new ArrayList<>();
            if (khachHang != null) {
                listKhachHang.add(khachHang);
            }
        } else {
            // Nếu không phải tài khoản khách hàng, lấy toàn bộ danh sách
            listKhachHang = khachHangBUS.selectAll();
        }
        loadDataTable(listKhachHang);
    }

    private void initComponent(int manhomquyen, String chucnang) {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Khởi tạo bảng
        tableKhachHang = new JTable();
        tableKhachHang.setBackground(new Color(0xA1D6E2));
        scrollTableKhachHang = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "STT", "Mã KH", "Tên KH", "Giới Tính", "CCCD", "Địa Chỉ", "SĐT", "Email", "Trạng Thái", "Ngày Sinh" };
        tblModel.setColumnIdentifiers(header);
        tableKhachHang.setModel(tblModel);
        scrollTableKhachHang.setViewportView(tableKhachHang);

        // Căn giữa các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableKhachHang.getColumnModel();
        for (int i = 0; i < header.length; i++) {
            if (i != 2 && i != 5 && i != 7) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        // Thiết lập kích thước cột
        tableKhachHang.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableKhachHang.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableKhachHang.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableKhachHang.getColumnModel().getColumn(3).setPreferredWidth(80);
        tableKhachHang.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableKhachHang.getColumnModel().getColumn(5).setPreferredWidth(120);
        tableKhachHang.getColumnModel().getColumn(6).setPreferredWidth(120);
        tableKhachHang.getColumnModel().getColumn(7).setPreferredWidth(200);
        tableKhachHang.getColumnModel().getColumn(8).setPreferredWidth(80);
        tableKhachHang.getColumnModel().getColumn(9).setPreferredWidth(100);

        tableKhachHang.setFocusable(false);
        tableKhachHang.setAutoCreateRowSorter(true);
        tableKhachHang.setDefaultEditor(Object.class, null);

        // Tùy chỉnh chiều cao dòng và đường phân cách
        tableKhachHang.setRowHeight(30);
        tableKhachHang.getTableHeader().setPreferredSize(new Dimension(tableKhachHang.getTableHeader().getPreferredSize().width, 30));
        tableKhachHang.setShowHorizontalLines(true);
        tableKhachHang.setShowVerticalLines(false);
        tableKhachHang.setGridColor(Color.WHITE);
        tableKhachHang.setIntercellSpacing(new Dimension(0, 1));

        // Tùy chỉnh tiêu đề
        tableKhachHang.getTableHeader().setBackground(Color.WHITE);
        tableKhachHang.getTableHeader().setForeground(Color.BLACK);
        tableKhachHang.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        ((DefaultTableCellRenderer) tableKhachHang.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        // Nền bảng
        scrollTableKhachHang.setBackground(Color.WHITE);

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

        String[] actions = (accountInfo != null && "KHACHHANG".equals(accountInfo.getAccountType()))
                ? new String[] { "update", "detail" }
                : new String[] { "create", "update", "delete", "detail", "export" };
        sidebarPanel = new SidebarPanel(manhomquyen, chucnang, actions);
        for (String action : actions) {
            JButton button = sidebarPanel.btn.get(action);
            if (button != null) {
                button.setPreferredSize(new Dimension(70, 50));
            }
        }
        functionBar.add(sidebarPanel);

        // Cập nhật IntegratedSearch
        search = new IntegratedSearch(new String[] { "Tất cả", "Mã KH", "Tên KH", "SĐT", "CCCD", "Trạng Thái" });
        search.txtSearchForm.setPreferredSize(new Dimension(100, search.txtSearchForm.getPreferredSize().height));
        search.btnReset.setPreferredSize(new Dimension(120, 25));
        search.txtSearchForm.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
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
        main.add(scrollTableKhachHang);
    }

    private void loadDataTable(ArrayList<KhachHangDTO> result) {
        tblModel.setRowCount(0);
        int stt = 1;
        for (KhachHangDTO khachHang : result) {
            String gioiTinh = khachHang.getGioiTinh() == 1 ? "Nam" : "Nữ";
            String trangThai = khachHang.getTrangThai() == 1 ? "Hoạt động" : "Ngưng hoạt động";
            tblModel.addRow(new Object[] {
                    stt++,
                    khachHang.getMaKhachHang(),
                    khachHang.getTenKhachHang(),
                    gioiTinh,
                    khachHang.getCccd(),
                    khachHang.getDiaChi(),
                    khachHang.getSoDienThoai(),
                    khachHang.getEmail(),
                    trangThai,
                    khachHang.getNgaySinh()
            });
        }
    }

    private void search() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String type = (String) search.cbxChoose.getSelectedItem();
        ArrayList<KhachHangDTO> result = new ArrayList<>();

        if (accountInfo != null && "KHACHHANG".equals(accountInfo.getAccountType())) {
            // Nếu là tài khoản khách hàng, chỉ tìm kiếm trong thông tin của khách hàng đó
            KhachHangDTO khachHang = khachHangBUS.selectById(String.valueOf(accountInfo.getAccountId()));
            if (khachHang != null) {
                String trangThai = khachHang.getTrangThai() == 1 ? "hoạt động" : "ngưng hoạt động";
                boolean match = false;
                if (type.equals("Tất cả")) {
                    match = String.valueOf(khachHang.getMaKhachHang()).contains(keyword) ||
                            khachHang.getTenKhachHang().toLowerCase().contains(keyword) ||
                            khachHang.getSoDienThoai().contains(keyword) ||
                            String.valueOf(khachHang.getCccd()).contains(keyword) ||
                            trangThai.contains(keyword);
                } else if (type.equals("Mã KH")) {
                    match = String.valueOf(khachHang.getMaKhachHang()).contains(keyword);
                } else if (type.equals("Tên KH")) {
                    match = khachHang.getTenKhachHang().toLowerCase().contains(keyword);
                } else if (type.equals("SĐT")) {
                    match = khachHang.getSoDienThoai().contains(keyword);
                } else if (type.equals("CCCD")) {
                    match = String.valueOf(khachHang.getCccd()).contains(keyword);
                } else if (type.equals("Trạng Thái")) {
                    match = trangThai.contains(keyword);
                }
                if (match) {
                    result.add(khachHang);
                }
            }
        } else {
            // Nếu không phải tài khoản khách hàng, tìm kiếm như bình thường
            result = khachHangBUS.selectAll();
            result.removeIf(kh -> {
                String trangThai = kh.getTrangThai() == 1 ? "hoạt động" : "ngưng hoạt động";
                return !String.valueOf(kh.getMaKhachHang()).contains(keyword) &&
                        !kh.getTenKhachHang().toLowerCase().contains(keyword) &&
                        !kh.getSoDienThoai().contains(keyword) &&
                        !String.valueOf(kh.getCccd()).contains(keyword) &&
                        !trangThai.contains(keyword);
            });
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
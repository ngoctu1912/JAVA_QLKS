package GUI_TAIKHOAN;

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import DTO.NhomQuyenDTO;
import Component.SidebarPanel;
import Component.PanelBorderRadius;
import com.formdev.flatlaf.FlatLightLaf;
import config.ConnectDB;
import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.Connection;

public class TaiKhoanGUI extends JPanel {
    private TaiKhoanBUS taiKhoanBus;
    private JTable taiKhoanTable;
    private DefaultTableModel tableModel;
    private SidebarPanel sidebarPanel;
    private TaiKhoanEvent eventHandler;
    private Connection conn;
    private AccountInfo accountInfo;

    public TaiKhoanGUI() {
        this(null);
    }

    public TaiKhoanGUI(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
        try {
            conn = ConnectDB.getConnection();
            if (conn == null) {
                throw new Exception("Không thể kết nối cơ sở dữ liệu");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UIManager.put("TextField.preferredSize", new Dimension(200, 30));
            UIManager.put("Button.preferredSize", new Dimension(120, 30));
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        taiKhoanBus = new TaiKhoanBUS();
        initComponents();
        loadTaiKhoanData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(220, 245, 218));

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(new Color(220, 245, 218));
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 10));
        wrapperPanel.setLayout(new BorderLayout());

        sidebarPanel = new SidebarPanel(
            e -> eventHandler.openAddDialog(),
            e -> eventHandler.openEditDialog(),
            e -> eventHandler.deleteTaiKhoan(),
            e -> eventHandler.showDetails(),
            e -> eventHandler.exportToExcel(),
            e -> eventHandler.refreshTable(),
            null
        );

        int maNhomQuyen = 0;
        if (accountInfo != null) {
            System.out.println("AccountInfo: ID=" + accountInfo.getAccountId() + ", Type=" + accountInfo.getAccountType());
            int index = taiKhoanBus.getTaiKhoanByMaNV(accountInfo.getAccountId());
            System.out.println("MaNV: " + accountInfo.getAccountId() + ", Index: " + index);
            if (index != -1) {
                TaiKhoanDTO tk = taiKhoanBus.getTaiKhoan(index);
                if (tk != null) {
                    maNhomQuyen = tk.getMaNhomQuyen();
                    System.out.println("TaiKhoanDTO: TenDangNhap=" + tk.getTenDangNhap() + ", MaNV=" + tk.getMaNV() + ", MaNhomQuyen=" + maNhomQuyen + ", TrangThai=" + tk.getTrangThai());
                } else {
                    System.out.println("TaiKhoanDTO not found for index: " + index);
                }
            } else {
                System.out.println("Account not found in TAIKHOAN for MaNV: " + accountInfo.getAccountId());
            }
        } else {
            System.out.println("AccountInfo is null - no user logged in");
        }
        System.out.println("Final maNhomQuyen: " + maNhomQuyen);
        eventHandler = new TaiKhoanEvent(this, taiKhoanBus, sidebarPanel.getSearchField(), maNhomQuyen);
        sidebarPanel.getSearchField().getDocument().addDocumentListener(eventHandler.getSearchListener());

        wrapperPanel.add(sidebarPanel, BorderLayout.CENTER);
        add(wrapperPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"MNV", "Tên đăng nhập", "Nhóm quyền", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        taiKhoanTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(new Color(193, 237, 220));
                }
                ((JComponent) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE));
                if (c instanceof JLabel) {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                }
                return c;
            }
        };
        taiKhoanTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taiKhoanTable.setGridColor(Color.WHITE);
        taiKhoanTable.setShowGrid(true);
        taiKhoanTable.setRowHeight(30);
        taiKhoanTable.setBackground(new Color(193, 237, 220));
        taiKhoanTable.setSelectionBackground(new Color(192, 192, 192));
        taiKhoanTable.setSelectionForeground(Color.BLACK);

        JTableHeader tableHeader = taiKhoanTable.getTableHeader();
        tableHeader.setBackground(Color.WHITE);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 40));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tableHeader.setDefaultRenderer(headerRenderer);

        PanelBorderRadius tablePanel = new PanelBorderRadius();
        tablePanel.setBackground(new Color(220, 245, 218));
        tablePanel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(taiKhoanTable);
        scrollPane.setBackground(new Color(193, 237, 220));
        scrollPane.getViewport().setBackground(new Color(193, 237, 220));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel tableWrapper = new JPanel();
        tableWrapper.setBackground(new Color(220, 245, 218));
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        tableWrapper.setLayout(new BorderLayout());
        tableWrapper.add(tablePanel, BorderLayout.CENTER);

        add(tableWrapper, BorderLayout.CENTER);
    }

    public void loadTaiKhoanData() {
        tableModel.setRowCount(0);
        for (TaiKhoanDTO tk : taiKhoanBus.getTaiKhoanAll()) {
            String trangthaiString = switch (tk.getTrangThai()) {
                case 1 -> "Hoạt động";
                case 0 -> "Ngưng hoạt động";
                case 2 -> "Chờ xử lý";
                default -> "Không xác định";
            };
            NhomQuyenDTO nhomQuyen = taiKhoanBus.getNhomQuyenDTO(tk.getMaNhomQuyen());
            String tenNhomQuyen = nhomQuyen != null ? nhomQuyen.getTEN() : "Không xác định";
            tableModel.addRow(new Object[]{
                tk.getMaNV(),
                tk.getTenDangNhap(),
                tenNhomQuyen,
                trangthaiString
            });
        }
    }

    public TaiKhoanDTO getSelectedTaiKhoan() {
        int selectedRow = taiKhoanTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int index = taiKhoanBus.getTaiKhoanByMaNV(id);
        return index != -1 ? taiKhoanBus.getTaiKhoan(index) : null;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTaiKhoanTable() {
        return taiKhoanTable;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public TaiKhoanBUS getTaiKhoanBus() {
        return taiKhoanBus;
    }
}
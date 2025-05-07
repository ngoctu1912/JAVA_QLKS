package GUI_TAIKHOAN;

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import DTO.NhomQuyenDTO;
import Component.IntegratedSearch;
import Component.PanelBorderRadius;
import Component.SidebarPanel;
import com.formdev.flatlaf.FlatLightLaf;
import config.ConnectDB;
import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.ArrayList;

public class TaiKhoanGUI extends JPanel {
    private TaiKhoanBUS taiKhoanBus;
    private JTable taiKhoanTable;
    private DefaultTableModel tableModel;
    private SidebarPanel sidebarPanel;
    private IntegratedSearch search;
    private TaiKhoanEvent eventHandler;
    private Connection conn;
    private AccountInfo accountInfo;
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private Color BackgroundColor = new Color(240, 245, 245);
    private ArrayList<TaiKhoanDTO> listTaiKhoan;

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
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        taiKhoanBus = new TaiKhoanBUS();
        listTaiKhoan = taiKhoanBus.getTaiKhoanAll();
        initComponents();
        loadTaiKhoanData();
    }

    private void initComponents() {
        setBackground(BackgroundColor);
        setLayout(new BorderLayout(0, 0));
        setOpaque(true);

        tableModel = new DefaultTableModel(new String[]{"STT", "Mã NV", "Tên đăng nhập", "Nhóm quyền", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        taiKhoanTable = new JTable(tableModel);
        taiKhoanTable.setBackground(new Color(0xA1D6E2));
        JScrollPane scrollPane = new JScrollPane(taiKhoanTable);
        scrollPane.setBackground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = taiKhoanTable.getColumnModel();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }

        taiKhoanTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        taiKhoanTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        taiKhoanTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        taiKhoanTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        taiKhoanTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        taiKhoanTable.setRowHeight(30);
        taiKhoanTable.getTableHeader().setPreferredSize(new Dimension(taiKhoanTable.getTableHeader().getPreferredSize().width, 30));
        taiKhoanTable.setShowHorizontalLines(true);
        taiKhoanTable.setShowVerticalLines(false);
        taiKhoanTable.setGridColor(Color.WHITE);
        taiKhoanTable.setIntercellSpacing(new Dimension(0, 1));
        taiKhoanTable.setFocusable(false);
        taiKhoanTable.setAutoCreateRowSorter(true);
        taiKhoanTable.setDefaultEditor(Object.class, null);

        taiKhoanTable.getTableHeader().setBackground(Color.WHITE);
        taiKhoanTable.getTableHeader().setForeground(Color.BLACK);
        taiKhoanTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        ((DefaultTableCellRenderer) taiKhoanTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] actions = {"create", "update", "delete", "detail", "export"};
        int maNhomQuyen = 0;
        if (accountInfo != null) {
            int index = taiKhoanBus.getTaiKhoanByMaNV(accountInfo.getAccountId());
            if (index != -1) {
                TaiKhoanDTO tk = taiKhoanBus.getTaiKhoan(index);
                if (tk != null) {
                    maNhomQuyen = tk.getMaNhomQuyen();
                }
            }
        }
        sidebarPanel = new SidebarPanel(maNhomQuyen, "12", actions);
        for (String action : actions) {
            JButton button = sidebarPanel.btn.get(action);
            if (button != null) {
                button.setPreferredSize(new Dimension(70, 50));
            }
        }
        functionBar.add(sidebarPanel);

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã NV", "Tên đăng nhập", "Nhóm quyền", "Trạng thái"});
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
            loadTaiKhoanData();
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollPane);

        eventHandler = new TaiKhoanEvent(this, taiKhoanBus, maNhomQuyen);
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

    public void loadTaiKhoanData() {
        listTaiKhoan = taiKhoanBus.getTaiKhoanAll();
        loadDataTable(listTaiKhoan);
    }

    private void loadDataTable(ArrayList<TaiKhoanDTO> result) {
        tableModel.setRowCount(0);
        int stt = 1;
        for (TaiKhoanDTO tk : result) {
            String trangthaiString = switch (tk.getTrangThai()) {
                case 1 -> "Hoạt động";
                case 0 -> "Ngưng hoạt động";
                case 2 -> "Chờ xử lý";
                default -> "Không xác định";
            };
            NhomQuyenDTO nhomQuyen = taiKhoanBus.getNhomQuyenDTO(tk.getMaNhomQuyen());
            String tenNhomQuyen = nhomQuyen != null ? nhomQuyen.getTEN() : "Không xác định";
            tableModel.addRow(new Object[]{
                stt++,
                tk.getMaNV(),
                tk.getTenDangNhap(),
                tenNhomQuyen,
                trangthaiString
            });
        }
    }

    private void search() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String type = (String) search.cbxChoose.getSelectedItem();
        ArrayList<TaiKhoanDTO> result = new ArrayList<>();

        if (type.equals("Tất cả")) {
            result = taiKhoanBus.getTaiKhoanAll();
            result.removeIf(tk -> {
                NhomQuyenDTO nhomQuyen = taiKhoanBus.getNhomQuyenDTO(tk.getMaNhomQuyen());
                String tenNhomQuyen = nhomQuyen != null ? nhomQuyen.getTEN().toLowerCase() : "";
                String trangThai = switch (tk.getTrangThai()) {
                    case 1 -> "hoạt động";
                    case 0 -> "ngưng hoạt động";
                    case 2 -> "chờ xử lý";
                    default -> "không xác định";
                };
                return !String.valueOf(tk.getMaNV()).contains(keyword) &&
                       !tk.getTenDangNhap().toLowerCase().contains(keyword) &&
                       !tenNhomQuyen.contains(keyword) &&
                       !trangThai.contains(keyword);
            });
        } else if (type.equals("Mã NV")) {
            result = taiKhoanBus.getTaiKhoanAll();
            result.removeIf(tk -> !String.valueOf(tk.getMaNV()).contains(keyword));
        } else if (type.equals("Tên đăng nhập")) {
            result = taiKhoanBus.getTaiKhoanAll();
            result.removeIf(tk -> !tk.getTenDangNhap().toLowerCase().contains(keyword));
        } else if (type.equals("Nhóm quyền")) {
            result = taiKhoanBus.getTaiKhoanAll();
            result.removeIf(tk -> {
                NhomQuyenDTO nhomQuyen = taiKhoanBus.getNhomQuyenDTO(tk.getMaNhomQuyen());
                String tenNhomQuyen = nhomQuyen != null ? nhomQuyen.getTEN().toLowerCase() : "";
                return !tenNhomQuyen.contains(keyword);
            });
        } else if (type.equals("Trạng thái")) {
            result = taiKhoanBus.getTaiKhoanAll();
            if (keyword.contains("hoạt động")) {
                result.removeIf(tk -> tk.getTrangThai() != 1);
            } else if (keyword.contains("ngưng")) {
                result.removeIf(tk -> tk.getTrangThai() != 0);
            } else if (keyword.contains("chờ")) {
                result.removeIf(tk -> tk.getTrangThai() != 2);
            } else {
                result = taiKhoanBus.getTaiKhoanAll();
            }
        }

        loadDataTable(result);
    }

    public TaiKhoanDTO getSelectedTaiKhoan() {
        int selectedRow = taiKhoanTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 1);
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
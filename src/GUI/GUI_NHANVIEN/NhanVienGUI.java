package GUI_NHANVIEN;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
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

public class NhanVienGUI extends JPanel {
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JFrame owner;
    private JTable nhanVienTable;
    private JScrollPane scrollTableNhanVien;
    private SidebarPanel sidebarPanel;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private NhanVienBUS nhanVienBUS;
    private ArrayList<NhanVienDTO> listNhanVien;
    private Color BackgroundColor = new Color(240, 245, 245);
    private NhanVienEvent eventHandler;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public NhanVienGUI(JFrame parentFrame, int manhomquyen, String chucnang) {
        this.owner = parentFrame;
        this.nhanVienBUS = new NhanVienBUS(this);
        this.listNhanVien = nhanVienBUS.getAll();
        initComponent(manhomquyen, chucnang);
        this.eventHandler = new NhanVienEvent(this);
        loadTableData();
    }

    public JFrame getOwner() {
        return owner;
    }

    public JTable getNhanVienTable() {
        return nhanVienTable;
    }

    public NhanVienBUS getNhanVienBUS() {
        return nhanVienBUS;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public void loadTableData() {
        listNhanVien = nhanVienBUS.getAll();
        loadDataTable(listNhanVien);
    }

    public void loadNhanVienData() {
        loadTableData();
    }

    private void initComponent(int manhomquyen, String chucnang) {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        nhanVienTable = new JTable();
        nhanVienTable.setBackground(new Color(0xA1D6E2));
        scrollTableNhanVien = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "STT", "Mã NV", "Họ Tên", "Giới Tính", "Ngày Sinh", "SĐT", "Email", "Trạng Thái" };
        tblModel.setColumnIdentifiers(header);
        nhanVienTable.setModel(tblModel);
        scrollTableNhanVien.setViewportView(nhanVienTable);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = nhanVienTable.getColumnModel();
        for (int i = 0; i < header.length; i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        nhanVienTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        nhanVienTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        nhanVienTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        nhanVienTable.setFocusable(false);
        nhanVienTable.setAutoCreateRowSorter(true);
        nhanVienTable.setDefaultEditor(Object.class, null);

        nhanVienTable.setRowHeight(30);
        nhanVienTable.getTableHeader().setPreferredSize(new Dimension(nhanVienTable.getTableHeader().getPreferredSize().width, 30));
        nhanVienTable.setShowHorizontalLines(true);
        nhanVienTable.setShowVerticalLines(false);
        nhanVienTable.setGridColor(Color.WHITE);
        nhanVienTable.setIntercellSpacing(new Dimension(0, 1));

        nhanVienTable.getTableHeader().setBackground(Color.WHITE);
        nhanVienTable.getTableHeader().setForeground(Color.BLACK);
        nhanVienTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        ((DefaultTableCellRenderer) nhanVienTable.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        scrollTableNhanVien.setBackground(Color.WHITE);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] actions = { "create", "update", "delete", "detail", "export" };
        sidebarPanel = new SidebarPanel(manhomquyen, chucnang, actions);
        for (String action : actions) {
            JButton button = sidebarPanel.btn.get(action);
            if (button != null) {
                button.setPreferredSize(new Dimension(70, 50));
            }
        }
        functionBar.add(sidebarPanel);

        search = new IntegratedSearch(new String[] { "Tất cả", "Mã NV", "Họ Tên", "Giới Tính", "SĐT", "Email", "Trạng Thái" });
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
            listNhanVien = nhanVienBUS.getAll();
            loadTableData();
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableNhanVien);
    }

    private void loadDataTable(ArrayList<NhanVienDTO> result) {
        tblModel.setRowCount(0);
        int stt = 1;
        for (NhanVienDTO nv : result) {
            String gioitinh = nv.getGIOITINH() == 1 ? "Nam" : "Nữ";
            String trangThai = nv.getTT() == 1 ? "Đang làm việc" : "Nghỉ làm việc";
            tblModel.addRow(new Object[] {
                    stt++,
                    "NV" + String.format("%03d", nv.getMNV()),
                    nv.getHOTEN(),
                    gioitinh,
                    nv.getNGAYSINH() != null ? dateFormat.format(nv.getNGAYSINH()) : "",
                    nv.getSDT(),
                    nv.getEMAIL(),
                    trangThai
            });
        }
    }

    private void search() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String type = (String) search.cbxChoose.getSelectedItem();
        ArrayList<NhanVienDTO> result;

        if (type.equals("Tất cả")) {
            result = nhanVienBUS.search(keyword);
        } else {
            result = new ArrayList<>();
            for (NhanVienDTO nv : nhanVienBUS.getAll()) {
                String gioitinh = nv.getGIOITINH() == 1 ? "nam" : "nữ";
                String trangThai = nv.getTT() == 1 ? "Đang làm việc" : "Nghỉ làm việc";
                boolean matches = false;
                switch (type) {
                    case "Mã NV":
                        matches = String.valueOf(nv.getMNV()).contains(keyword);
                        break;
                    case "Họ Tên":
                        matches = nv.getHOTEN().toLowerCase().contains(keyword);
                        break;
                    case "Giới Tính":
                        matches = gioitinh.contains(keyword);
                        break;
                    case "SĐT":
                        matches = nv.getSDT().toLowerCase().contains(keyword);
                        break;
                    case "Email":
                        matches = nv.getEMAIL().toLowerCase().contains(keyword);
                        break;
                    case "Trạng Thái":
                        matches = trangThai.contains(keyword);
                        break;
                }
                if (matches) {
                    result.add(nv);
                }
            }
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

    public NhanVienDTO getSelectedNhanVien() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        String mnvStr = (String) tblModel.getValueAt(selectedRow, 1);
        int mnv = Integer.parseInt(mnvStr.replace("NV", ""));
        return nhanVienBUS.getAll().stream()
                .filter(nv -> nv.getMNV() == mnv)
                .findFirst().orElse(null);
    }

    public DefaultTableModel getTableModel() {
        return tblModel;
    }
}
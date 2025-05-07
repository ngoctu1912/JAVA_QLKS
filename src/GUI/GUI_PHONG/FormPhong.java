package GUI_PHONG;

import BUS.PhongBUS;
import DTO.PhongDTO;
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
import java.util.ArrayList;

public final class FormPhong extends JPanel {
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JFrame owner;
    private JTable tablePhong;
    private JScrollPane scrollTablePhong;
    private SidebarPanel sidebarPanel;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private PhongBUS phongBUS = new PhongBUS();
    private ArrayList<PhongDTO> listPhong = phongBUS.getAllPhong();
    private Color BackgroundColor = new Color(240, 245, 245);
    private PhongEventHandler eventHandler;

    public FormPhong(JFrame parentFrame, int manhomquyen, String chucnang) {
        this.owner = parentFrame;
        initComponent(manhomquyen, chucnang);
        this.eventHandler = new PhongEventHandler(this);
        loadTableData();
    }

    public JFrame getOwner() {
        return owner;
    }

    public JTable getTable() {
        return tablePhong;
    }

    public PhongBUS getPhongBUS() {
        return phongBUS;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public void loadTableData() {
        listPhong = phongBUS.getAllPhong();
        loadDataTable(listPhong);
    }

    private void initComponent(int manhomquyen, String chucnang) {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Khởi tạo bảng
        tablePhong = new JTable();
        tablePhong.setBackground(new Color(0xA1D6E2));
        scrollTablePhong = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "STT", "Mã Phòng", "Tên Phòng", "Loại Phòng", "Giá Phòng",
                "Chi Tiết Loại Phòng" }; // Removed "Tình Trạng"
        tblModel.setColumnIdentifiers(header);
        tablePhong.setModel(tblModel);
        scrollTablePhong.setViewportView(tablePhong);

        // Căn giữa các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tablePhong.getColumnModel();
        for (int i = 0; i < header.length; i++) {
            if (i != 2) { // Không căn giữa cột "Tên Phòng"
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        // Thiết lập kích thước cột
        tablePhong.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablePhong.getColumnModel().getColumn(5).setPreferredWidth(200);
        tablePhong.setFocusable(false);
        tablePhong.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tablePhong, 4, TableSorter.VND_CURRENCY_COMPARATOR);
        tablePhong.setDefaultEditor(Object.class, null);

        // Tùy chỉnh chiều cao dòng và đường phân cách
        tablePhong.setRowHeight(30);
        tablePhong.getTableHeader().setPreferredSize(new Dimension(tablePhong.getTableHeader().getPreferredSize().width, 30));
        tablePhong.setShowHorizontalLines(true);
        tablePhong.setShowVerticalLines(false);
        tablePhong.setGridColor(Color.WHITE);
        tablePhong.setIntercellSpacing(new Dimension(0, 1));
        tablePhong.setFocusable(false);
        tablePhong.setAutoCreateRowSorter(true);
        tablePhong.setDefaultEditor(Object.class, null);

        // Tùy chỉnh tiêu đề
        tablePhong.getTableHeader().setBackground(Color.WHITE);
        tablePhong.getTableHeader().setForeground(Color.BLACK);
        tablePhong.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        ((DefaultTableCellRenderer) tablePhong.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        // Nền bảng
        scrollTablePhong.setBackground(Color.WHITE);

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

        String[] actions = { "create", "update", "delete", "detail", "export" };
        sidebarPanel = new SidebarPanel(manhomquyen, chucnang, actions);
        for (String action : actions) {
            JButton button = sidebarPanel.btn.get(action);
            if (button != null) {
                button.setPreferredSize(new Dimension(70, 50));
            }
        }
        functionBar.add(sidebarPanel);

        // Cập nhật IntegratedSearch với các tùy chọn tìm kiếm mới
        search = new IntegratedSearch(new String[] { "Tất cả", "Mã Phòng", "Tên Phòng", "Loại Phòng", "Chi Tiết Loại Phòng" }); // Removed "Tình Trạng"
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
            listPhong = phongBUS.getAllPhong();
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
        main.add(scrollTablePhong);
    }

    private void loadDataTable(ArrayList<PhongDTO> result) {
        tblModel.setRowCount(0);
        int stt = 1;
        for (PhongDTO phong : result) {
            tblModel.addRow(new Object[] {
                    stt++,
                    phong.getMaP(),
                    phong.getTenP(),
                    phong.getLoaiP(),
                    phong.getGiaP() + "đ",
                    phong.getChiTietLoaiPhong()
            }); // Removed "status"
        }
    }

    private void search() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String type = (String) search.cbxChoose.getSelectedItem();
        ArrayList<PhongDTO> result = new ArrayList<>();

        if (type.equals("Tất cả")) {
            result = phongBUS.getAllPhong();
            result.removeIf(phong -> {
                return !phong.getMaP().toLowerCase().contains(keyword) &&
                        !phong.getTenP().toLowerCase().contains(keyword) &&
                        !phong.getLoaiP().toLowerCase().contains(keyword) &&
                        !phong.getChiTietLoaiPhong().toLowerCase().contains(keyword);
            });
        } else if (type.equals("Mã Phòng")) {
            result = phongBUS.getAllPhong();
            result.removeIf(phong -> !phong.getMaP().toLowerCase().contains(keyword));
        } else if (type.equals("Tên Phòng")) {
            result = phongBUS.getAllPhong();
            result.removeIf(phong -> !phong.getTenP().toLowerCase().contains(keyword));
        } else if (type.equals("Loại Phòng")) {
            result = phongBUS.getAllPhong();
            result.removeIf(phong -> !phong.getLoaiP().toLowerCase().contains(keyword));
        } else if (type.equals("Chi Tiết Loại Phòng")) {
            result = phongBUS.getAllPhong();
            result.removeIf(phong -> !phong.getChiTietLoaiPhong().toLowerCase().contains(keyword));
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
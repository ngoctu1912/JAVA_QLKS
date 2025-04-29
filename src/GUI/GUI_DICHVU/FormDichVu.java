package GUI_DICHVU;

import BUS.DichVuBUS;
import DTO.DichVuDTO;
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

public class FormDichVu extends JPanel {
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JFrame owner;
    private JTable tableDichVu;
    private JScrollPane scrollTableDichVu;
    private SidebarPanel sidebarPanel;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private DichVuBUS dichVuBUS = new DichVuBUS();
    private ArrayList<DichVuDTO> listDichVu = dichVuBUS.getAllDichVu();
    private Color BackgroundColor = new Color(240, 245, 245);
    private DichVuEventHandler eventHandler;

    public FormDichVu(JFrame parentFrame, int manhomquyen, String chucnang) {
        this.owner = parentFrame;
        initComponent(manhomquyen, chucnang);
        this.eventHandler = new DichVuEventHandler(this);
        loadTableData();
    }

    public JFrame getOwner() {
        return owner;
    }

    public JTable getTable() {
        return tableDichVu;
    }

    public DichVuBUS getDichVuBUS() {
        return dichVuBUS;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public void loadTableData() {
        listDichVu = dichVuBUS.getAllDichVu();
        loadDataTable(listDichVu);
    }

    private void initComponent(int manhomquyen, String chucnang) {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Khởi tạo bảng
        tableDichVu = new JTable();
        tableDichVu.setBackground(new Color(0xA1D6E2));
        scrollTableDichVu = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "STT", "Mã Dịch Vụ", "Tên Dịch Vụ", "Loại Dịch Vụ", "Số Lượng", "Giá Dịch Vụ", "Xử Lý" };
        tblModel.setColumnIdentifiers(header);
        tableDichVu.setModel(tblModel);
        scrollTableDichVu.setViewportView(tableDichVu);

        // Căn giữa các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableDichVu.getColumnModel();
        for (int i = 0; i < header.length; i++) {
            if (i != 2) { // Không căn giữa cột "Tên Dịch Vụ"
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        // Thiết lập kích thước cột
        tableDichVu.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableDichVu.getColumnModel().getColumn(5).setPreferredWidth(100);
        tableDichVu.getColumnModel().getColumn(6).setPreferredWidth(100);
        tableDichVu.setFocusable(false);
        tableDichVu.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableDichVu, 5, TableSorter.VND_CURRENCY_COMPARATOR);
        tableDichVu.setDefaultEditor(Object.class, null);

        // Tùy chỉnh chiều cao dòng và đường phân cách
        tableDichVu.setRowHeight(30);
        tableDichVu.getTableHeader().setPreferredSize(new Dimension(tableDichVu.getTableHeader().getPreferredSize().width, 30));
        tableDichVu.setShowHorizontalLines(true);
        tableDichVu.setShowVerticalLines(false);
        tableDichVu.setGridColor(Color.WHITE);
        tableDichVu.setIntercellSpacing(new Dimension(0, 1));
        tableDichVu.setFocusable(false);
        tableDichVu.setAutoCreateRowSorter(true);
        tableDichVu.setDefaultEditor(Object.class, null);

        // Tùy chỉnh tiêu đề
        tableDichVu.getTableHeader().setBackground(Color.WHITE);
        tableDichVu.getTableHeader().setForeground(Color.BLACK);
        tableDichVu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        ((DefaultTableCellRenderer) tableDichVu.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        // Nền bảng
        scrollTableDichVu.setBackground(Color.WHITE);

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
        search = new IntegratedSearch(new String[] { "Tất cả", "Mã Dịch Vụ", "Tên Dịch Vụ", "Loại Dịch Vụ", "Xử Lý" });
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
            listDichVu = dichVuBUS.getAllDichVu();
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
        main.add(scrollTableDichVu);
    }

    private void loadDataTable(ArrayList<DichVuDTO> result) {
        tblModel.setRowCount(0);
        int stt = 1;
        for (DichVuDTO dichVu : result) {
            String xuLy = dichVu.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý";
            tblModel.addRow(new Object[] {
                    stt++,
                    dichVu.getMaDV(),
                    dichVu.getTenDV(),
                    dichVu.getLoaiDV(),
                    dichVu.getSoLuong(),
                    dichVu.getGiaDV() + "đ",
                    xuLy
            });
        }
    }

    private void search() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String type = (String) search.cbxChoose.getSelectedItem();
        ArrayList<DichVuDTO> result = new ArrayList<>();

        if (type.equals("Tất cả")) {
            result = dichVuBUS.getAllDichVu();
            result.removeIf(dichVu -> {
                String xuLy = dichVu.getXuLy() == 1 ? "đã xử lý" : "chưa xử lý";
                return !dichVu.getMaDV().toLowerCase().contains(keyword) &&
                        !dichVu.getTenDV().toLowerCase().contains(keyword) &&
                        !dichVu.getLoaiDV().toLowerCase().contains(keyword) &&
                        !xuLy.contains(keyword);
            });
        } else if (type.equals("Mã Dịch Vụ")) {
            result = dichVuBUS.getAllDichVu();
            result.removeIf(dichVu -> !dichVu.getMaDV().toLowerCase().contains(keyword));
        } else if (type.equals("Tên Dịch Vụ")) {
            result = dichVuBUS.getAllDichVu();
            result.removeIf(dichVu -> !dichVu.getTenDV().toLowerCase().contains(keyword));
        } else if (type.equals("Loại Dịch Vụ")) {
            result = dichVuBUS.getAllDichVu();
            result.removeIf(dichVu -> !dichVu.getLoaiDV().toLowerCase().contains(keyword));
        } else if (type.equals("Xử Lý")) {
            result = dichVuBUS.getAllDichVu();
            if (keyword.contains("đã xử lý")) {
                result.removeIf(dichVu -> dichVu.getXuLy() != 1);
            } else if (keyword.contains("chưa xử lý")) {
                result.removeIf(dichVu -> dichVu.getXuLy() != 0);
            } else {
                result = dichVuBUS.getAllDichVu();
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
}
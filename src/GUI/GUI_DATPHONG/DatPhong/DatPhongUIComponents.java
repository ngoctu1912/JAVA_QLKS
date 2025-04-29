package GUI_DATPHONG.DatPhong;

import Component.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class DatPhongUIComponents {
    private DatPhongFrame frame;

    public DatPhongUIComponents(DatPhongFrame frame) {
        this.frame = frame;
    }

    public JTable createTable() {
        JTable table = new JTable();
        table.setBackground(new Color(0xA1D6E2));
        DefaultTableModel tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã Đặt Phòng", "Tên Khách Hàng", "Ngày Lập Phiếu", "Tiền Đặt Cọc", "Tình Trạng Xử Lý", "Xử Lý"};
        tblModel.setColumnIdentifiers(header);
        table.setModel(tblModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < header.length; i++) {
            if (i != 2) { // Cột "Tên Khách Hàng" không căn giữa
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);

        table.setFocusable(false);
        table.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(table, 1, TableSorter.STRING_COMPARATOR);
        TableSorter.configureTableColumnSorter(table, 2, TableSorter.STRING_COMPARATOR); // Tên Khách Hàng là chuỗi
        TableSorter.configureTableColumnSorter(table, 3, TableSorter.DATE_COMPARATOR);
        TableSorter.configureTableColumnSorter(table, 4, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(table, 5, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(table, 6, TableSorter.INTEGER_COMPARATOR);
        table.setDefaultEditor(Object.class, null);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 5 || column == 6) {
                    c.setForeground(value.toString().equals("Đã xử lý") ? Color.RED : value.toString().equals("Chưa xử lý") ? new Color(0, 128, 0) : Color.BLACK);
                } else {
                    c.setForeground(Color.BLACK);
                }
                ((JLabel) c).setHorizontalAlignment(column == 2 ? JLabel.LEFT : JLabel.CENTER);
                return c;
            }
        });

        table.setRowHeight(30);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width, 30));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(Color.WHITE);
        table.setIntercellSpacing(new Dimension(0, 1));

        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        return table;
    }

    public SidebarPanel createSidebarPanel(DatPhongEventHandler eventHandler) {
        String[] actions = {"create", "update", "delete", "detail", "export"};
        SidebarPanel sidebarPanel = new SidebarPanel(1, "2", actions);
        for (String action : actions) {
            JButton button = sidebarPanel.btn.get(action);
            if (button != null) {
                button.setPreferredSize(new Dimension(70, 50));
            }
        }

        sidebarPanel.btn.get("create").addActionListener(e -> eventHandler.handleAdd());
        sidebarPanel.btn.get("update").addActionListener(e -> eventHandler.handleEdit());
        sidebarPanel.btn.get("delete").addActionListener(e -> eventHandler.handleDelete());
        sidebarPanel.btn.get("detail").addActionListener(e -> eventHandler.handleDetails());
        sidebarPanel.btn.get("export").addActionListener(e -> eventHandler.handleExport());

        return sidebarPanel;
    }

    public IntegratedSearch createSearchComponent(DatPhongEventHandler eventHandler) {
        IntegratedSearch search = new IntegratedSearch(new String[]{"Tất cả", "Mã Đặt Phòng", "Tên Khách Hàng", "Ngày Lập Phiếu", "Tiền Đặt Cọc", "Tình Trạng Xử Lý", "Xử Lý"});
        search.txtSearchForm.setPreferredSize(new Dimension(100, search.txtSearchForm.getPreferredSize().height));
        search.btnReset.setPreferredSize(new Dimension(120, 25));
        search.btnReset.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Giống FormPhong
        search.txtSearchForm.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                eventHandler.search();
            }
        });

        search.btnReset.addActionListener(e -> eventHandler.resetSearch());
        return search;
    }
}
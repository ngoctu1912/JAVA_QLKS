package GUI_NHANVIEN;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import Component.SidebarPanel;
import Component.PanelBorderRadius;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class NhanVienGUI extends JFrame {
    private NhanVienBUS nhanVienBUS;
    private JTable nhanVienTable;
    private DefaultTableModel tableModel;
    private SidebarPanel sidebarPanel;
    private NhanVienEvent eventHandler;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public NhanVienGUI() {
        try {
            UIManager.put("TextField.preferredSize", new Dimension(200, 30));
            UIManager.put("TextField.minimumSize", new Dimension(200, 30));
            UIManager.put("TextField.maximumSize", new Dimension(200, 30));
            UIManager.put("Button.preferredSize", new Dimension(120, 30));
            UIManager.put("Button.minimumSize", new Dimension(120, 30));
            UIManager.put("Button.maximumSize", new Dimension(120, 30));
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        nhanVienBUS = new NhanVienBUS(this);
        initComponents();
        loadNhanVienData();
    }

    private void initComponents() {
        setTitle("Quản Lý Nhân Viên");
        setSize(1300, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(220, 245, 218));

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(new Color(220, 245, 218));
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 10));
        wrapperPanel.setLayout(new BorderLayout());

        sidebarPanel = new SidebarPanel(
            e -> eventHandler.openAddDialog(),
            e -> eventHandler.openEditDialog(),
            e -> eventHandler.deleteNhanVien(),
            e -> eventHandler.showDetails(),
            e -> eventHandler.exportToExcel(),
            e -> eventHandler.refreshTable(),
            null
        );

        // Instantiate eventHandler with the searchField from SidebarPanel
        eventHandler = new NhanVienEvent(this, nhanVienBUS, sidebarPanel.getSearchField());
        sidebarPanel.getSearchField().getDocument().addDocumentListener(eventHandler.getSearchListener());
        System.out.println("DocumentListener attached to search field");

        wrapperPanel.add(sidebarPanel, BorderLayout.CENTER);
        add(wrapperPanel, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"Mã NV", "Họ Tên", "Giới Tính", "Ngày Sinh", "SĐT", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        nhanVienTable = new JTable(tableModel) {
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
        nhanVienTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nhanVienTable.setGridColor(Color.WHITE);
        nhanVienTable.setShowGrid(true);
        nhanVienTable.setRowHeight(30);
        nhanVienTable.setIntercellSpacing(new Dimension(0, 0));
        nhanVienTable.setBackground(new Color(193, 237, 220));
        nhanVienTable.setSelectionBackground(new Color(192, 192, 192));
        nhanVienTable.setSelectionForeground(Color.BLACK);

        JTableHeader tableHeader = nhanVienTable.getTableHeader();
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

        JScrollPane scrollPane = new JScrollPane(nhanVienTable);
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

    public void loadNhanVienData() {
        tableModel.setRowCount(0);
        for (NhanVienDTO nv : nhanVienBUS.getAll()) {
            tableModel.addRow(new Object[]{
                nv.getMNV(),
                nv.getHOTEN(),
                nv.getGIOITINH() == 1 ? "Nam" : "Nữ",
                nv.getNGAYSINH() != null ? dateFormat.format(nv.getNGAYSINH()) : "",
                nv.getSDT(),
                nv.getEMAIL()
            });
        }
    }

    public NhanVienDTO getSelectedNhanVien() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên!");
            return null;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        return nhanVienBUS.getAll().stream()
                .filter(nv -> nv.getMNV() == id)
                .findFirst().orElse(null);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getNhanVienTable() {
        return nhanVienTable;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NhanVienGUI().setVisible(true));
    }
}
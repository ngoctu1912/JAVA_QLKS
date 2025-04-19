package GUI_PHANQUYEN;

import BUS.NhomQuyenBUS;
import DTO.NhomQuyenDTO;
import DTO.DanhMucChucNangDTO;
import DAO.DanhMucChucNangDAO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import com.formdev.flatlaf.FlatLightLaf;

public class PhanQuyenGUI extends JFrame {
    private NhomQuyenBUS nhomQuyenBUS;
    private JTable nhomQuyenTable;
    private DefaultTableModel tableModel;
    private ArrayList<DanhMucChucNangDTO> danhMucChucNangList;
    private PhanQuyenEvent eventHandler;
    private SidebarPanel sidebarPanel;

    public PhanQuyenGUI() {
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

        nhomQuyenBUS = new NhomQuyenBUS();
        danhMucChucNangList = DanhMucChucNangDAO.getInstance().selectAll();
        initComponents();
        loadNhomQuyenData();
    }

    private void initComponents() {
        setTitle("Chức Năng Phân Quyền");
        setSize(1300, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(220, 245, 218));

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(new Color(220, 245, 218));
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 10));
        wrapperPanel.setLayout(new BorderLayout());

        // Create SidebarPanel first
        sidebarPanel = new SidebarPanel(
            e -> eventHandler.openAddDialog(),
            e -> eventHandler.openEditDialog(),
            e -> eventHandler.deleteNhomQuyen(),
            e -> eventHandler.showDetails(),
            e -> eventHandler.exportToExcel(),
            e -> eventHandler.refreshTable(),
            null
        );

        // Instantiate eventHandler with the searchField from SidebarPanel
        eventHandler = new PhanQuyenEvent(this, nhomQuyenBUS, danhMucChucNangList, sidebarPanel.getSearchField());

        // Update SidebarPanel’s search listener
        sidebarPanel.getSearchField().getDocument().addDocumentListener(eventHandler.getSearchListener());
        System.out.println("DocumentListener attached to search field");

        wrapperPanel.add(sidebarPanel, BorderLayout.CENTER);
        add(wrapperPanel, BorderLayout.NORTH);

        // Rest of the table setup
        tableModel = new DefaultTableModel(new String[]{"Mã nhóm quyền", "Tên nhóm quyền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        nhomQuyenTable = new JTable(tableModel) {
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
        nhomQuyenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nhomQuyenTable.setGridColor(Color.WHITE);
        nhomQuyenTable.setShowGrid(true);
        nhomQuyenTable.setRowHeight(30);
        nhomQuyenTable.setIntercellSpacing(new Dimension(0, 0));
        nhomQuyenTable.setBackground(new Color(193, 237, 220));
        nhomQuyenTable.setSelectionBackground(new Color(192, 192, 192));
        nhomQuyenTable.setSelectionForeground(Color.BLACK);

        JTableHeader tableHeader = nhomQuyenTable.getTableHeader();
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

        JScrollPane scrollPane = new JScrollPane(nhomQuyenTable);
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

    public void loadNhomQuyenData() {
        tableModel.setRowCount(0);
        for (NhomQuyenDTO nq : nhomQuyenBUS.getAll()) {
            tableModel.addRow(new Object[]{nq.getMNQ(), nq.getTEN()});
        }
    }

    public NhomQuyenDTO getSelectedNhomQuyen() {
        int selectedRow = nhomQuyenTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhóm quyền!");
            return null;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        return nhomQuyenBUS.getAll().stream()
                .filter(nq -> nq.getMNQ() == id)
                .findFirst().orElse(null);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getNhomQuyenTable() {
        return nhomQuyenTable;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PhanQuyenGUI().setVisible(true));
    }
}
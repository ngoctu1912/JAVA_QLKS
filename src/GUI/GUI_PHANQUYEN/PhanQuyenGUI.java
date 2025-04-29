package GUI_PHANQUYEN;

import BUS.NhomQuyenBUS;
import Component.IntegratedSearch;
import Component.PanelBorderRadius;
import Component.SidebarPanel;
import DTO.NhomQuyenDTO;
import DAO.DanhMucChucNangDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PhanQuyenGUI extends JPanel {
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JFrame owner;
    private JTable nhomQuyenTable;
    private JScrollPane scrollTableNhomQuyen;
    private SidebarPanel sidebarPanel;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private NhomQuyenBUS nhomQuyenBUS;
    private ArrayList<NhomQuyenDTO> listNhomQuyen;
    private Color BackgroundColor = new Color(240, 245, 245);
    private PhanQuyenEvent eventHandler;

    public PhanQuyenGUI(JFrame parentFrame, int manhomquyen, String chucnang) {
        this.owner = parentFrame;
        this.nhomQuyenBUS = new NhomQuyenBUS();
        this.listNhomQuyen = nhomQuyenBUS.getAll();
        initComponents(manhomquyen, chucnang);
        this.eventHandler = new PhanQuyenEvent(this); // Moved after initComponents
        loadNhomQuyenData();
    }

    public JFrame getOwner() {
        return owner;
    }

    public JTable getNhomQuyenTable() {
        return nhomQuyenTable;
    }

    public NhomQuyenBUS getNhomQuyenBUS() {
        return nhomQuyenBUS;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public void loadNhomQuyenData() {
        listNhomQuyen = nhomQuyenBUS.getAll();
        loadDataTable(listNhomQuyen);
    }

    private void initComponents(int manhomquyen, String chucnang) {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        nhomQuyenTable = new JTable();
        nhomQuyenTable.setBackground(new Color(0xA1D6E2));
        scrollTableNhomQuyen = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "STT", "Mã Nhóm Quyền", "Tên Nhóm Quyền" };
        tblModel.setColumnIdentifiers(header);
        nhomQuyenTable.setModel(tblModel);
        scrollTableNhomQuyen.setViewportView(nhomQuyenTable);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        nhomQuyenTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        nhomQuyenTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        nhomQuyenTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        nhomQuyenTable.setFocusable(false);
        nhomQuyenTable.setAutoCreateRowSorter(true);
        nhomQuyenTable.setDefaultEditor(Object.class, null);

        nhomQuyenTable.setRowHeight(30);
        nhomQuyenTable.getTableHeader().setPreferredSize(new Dimension(nhomQuyenTable.getTableHeader().getPreferredSize().width, 30));
        nhomQuyenTable.setShowHorizontalLines(true);
        nhomQuyenTable.setShowVerticalLines(false);
        nhomQuyenTable.setGridColor(Color.WHITE);
        nhomQuyenTable.setIntercellSpacing(new Dimension(0, 1));

        nhomQuyenTable.getTableHeader().setBackground(Color.WHITE);
        nhomQuyenTable.getTableHeader().setForeground(Color.BLACK);
        nhomQuyenTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        ((DefaultTableCellRenderer) nhomQuyenTable.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        scrollTableNhomQuyen.setBackground(Color.WHITE);

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

        search = new IntegratedSearch(new String[] { "Tất cả", "Mã Nhóm Quyền", "Tên Nhóm Quyền" });
        search.txtSearchForm.setPreferredSize(new Dimension(100, search.txtSearchForm.getPreferredSize().height));
        search.btnReset.setPreferredSize(new Dimension(120, 25));
        functionBar.add(search);

        // Attach search listener here
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                eventHandler.search();
            }
        });
        search.btnReset.addActionListener(e -> {
            search.txtSearchForm.setText("");
            search.cbxChoose.setSelectedIndex(0);
            loadNhomQuyenData();
        });

        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableNhomQuyen);
    }

    private void loadDataTable(ArrayList<NhomQuyenDTO> result) {
        tblModel.setRowCount(0);
        int stt = 1;
        for (NhomQuyenDTO nq : result) {
            tblModel.addRow(new Object[] {
                    stt++,
                    "NQ" + String.format("%03d", nq.getMNQ()),
                    nq.getTEN()
            });
        }
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

    public NhomQuyenDTO getSelectedNhomQuyen() {
        int selectedRow = nhomQuyenTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhóm quyền!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        String mnqStr = (String) tblModel.getValueAt(selectedRow, 1);
        int mnq = Integer.parseInt(mnqStr.replace("NQ", ""));
        return nhomQuyenBUS.getAll().stream()
                .filter(nq -> nq.getMNQ() == mnq)
                .findFirst().orElse(null);
    }

    public DefaultTableModel getTableModel() {
        return tblModel;
    }

    public IntegratedSearch getSearch() {
        return search;
    }
}
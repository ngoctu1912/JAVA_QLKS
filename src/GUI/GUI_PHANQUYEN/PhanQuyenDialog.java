package GUI_PHANQUYEN;

import BUS.NhomQuyenBUS;
import Component.ButtonCustom;
import Component.HeaderTitle;
import Component.InputForm;
import DTO.ChiTietQuyenDTO;
import DTO.DanhMucChucNangDTO;
import DTO.NhomQuyenDTO;
import DAO.DanhMucChucNangDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class PhanQuyenDialog extends JDialog {
    private NhomQuyenBUS nhomQuyenBUS;
    private PhanQuyenGUI gui;
    private ArrayList<DanhMucChucNangDTO> danhMucChucNangList;
    private NhomQuyenDTO nhomQuyen;
    private boolean isEditMode;
    private boolean isViewMode;
    private HeaderTitle titlePage;
    private JPanel pnCenter, pnInfoNhomQuyen, pnBottom;
    private InputForm txtTenNhomQuyen;
    private DefaultTableModel permissionTableModel;
    private JTable permissionTable;

    public PhanQuyenDialog(PhanQuyenGUI gui, NhomQuyenDTO nhomQuyen, boolean isEditMode, boolean isViewMode) {
        super(gui.getOwner(), isViewMode ? "CHI TIẾT NHÓM QUYỀN" : (isEditMode ? "SỬA NHÓM QUYỀN" : "THÊM NHÓM QUYỀN MỚI"), true);
        this.nhomQuyenBUS = gui.getNhomQuyenBUS();
        this.gui = gui;
        this.danhMucChucNangList = DanhMucChucNangDAO.getInstance().selectAll();
        this.nhomQuyen = nhomQuyen;
        this.isEditMode = isEditMode;
        this.isViewMode = isViewMode;
        initComponents();
        if (isEditMode || isViewMode) {
            if (nhomQuyen != null) {
                setInfo(nhomQuyen);
            } else {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu nhóm quyền để hiển thị!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } else {
            initCreate();
        }
        if (isViewMode) {
            initView();
        }
        setLocationRelativeTo(gui.getOwner());
        setVisible(true);
    }

    private void initComponents() {
        setSize(new Dimension(1000, 550));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(isViewMode ? "CHI TIẾT NHÓM QUYỀN" : (isEditMode ? "SỬA NHÓM QUYỀN" : "THÊM NHÓM QUYỀN MỚI"));
        titlePage.setColor("167AC6");
        add(titlePage, BorderLayout.NORTH);

        pnCenter = new JPanel(new BorderLayout());
        pnCenter.setBackground(Color.WHITE);
        add(pnCenter, BorderLayout.CENTER);

        pnInfoNhomQuyen = new JPanel(new BorderLayout());
        pnInfoNhomQuyen.setBackground(Color.WHITE);
        pnInfoNhomQuyen.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnCenter.add(pnInfoNhomQuyen, BorderLayout.NORTH);

        txtTenNhomQuyen = new InputForm("Tên Nhóm Quyền");
        pnInfoNhomQuyen.add(txtTenNhomQuyen, BorderLayout.CENTER);

        String[] columnNames = { "Danh mục chức năng", "Xem", "Tạo mới", "Cập nhật", "Xóa" };
        permissionTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? String.class : Boolean.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && !isViewMode;
            }
        };

        if (danhMucChucNangList != null) {
            for (DanhMucChucNangDTO dm : danhMucChucNangList) {
                permissionTableModel.addRow(new Object[] { dm.getTENCN(), false, false, false, false });
            }
        }

        if (nhomQuyen != null) {
            ArrayList<ChiTietQuyenDTO> chiTietQuyenList = nhomQuyenBUS.getChiTietQuyen(String.valueOf(nhomQuyen.getMNQ()));
            for (ChiTietQuyenDTO ctq : chiTietQuyenList) {
                int mcn = ctq.getMCN();
                String hanhDong = ctq.getHANHDONG();
                for (int i = 0; i < permissionTableModel.getRowCount(); i++) {
                    String chucNang = (String) permissionTableModel.getValueAt(i, 0);
                    int rowMcn = danhMucChucNangList.stream()
                            .filter(dm -> dm.getTENCN().equals(chucNang))
                            .findFirst().map(DanhMucChucNangDTO::getMCN).orElse(-1);
                    if (rowMcn == mcn) {
                        switch (hanhDong.toLowerCase()) {
                            case "view":
                                permissionTableModel.setValueAt(true, i, 1);
                                break;
                            case "create":
                                permissionTableModel.setValueAt(true, i, 2);
                                break;
                            case "update":
                                permissionTableModel.setValueAt(true, i, 3);
                                break;
                            case "delete":
                                permissionTableModel.setValueAt(true, i, 4);
                                break;
                        }
                    }
                }
            }
        }

        permissionTable = new JTable(permissionTableModel);
        permissionTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        for (int i = 1; i < permissionTable.getColumnCount(); i++) {
            permissionTable.getColumnModel().getColumn(i).setPreferredWidth(80);
        }
        permissionTable.setRowHeight(25);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        permissionTable.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);

        permissionTable.setShowGrid(false);
        permissionTable.setIntercellSpacing(new Dimension(0, 0));
        permissionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        permissionTable.setFocusable(false);

        JTableHeader header = permissionTable.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        JScrollPane permissionScroll = new JScrollPane(permissionTable);
        permissionScroll.setBackground(Color.WHITE);
        pnCenter.add(permissionScroll, BorderLayout.CENTER);

        pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
        pnBottom.setBackground(Color.WHITE);
        pnCenter.add(pnBottom, BorderLayout.SOUTH);

        if (!isViewMode) {
            ButtonCustom btnSave = new ButtonCustom(isEditMode ? "Lưu thông tin" : "Thêm nhóm quyền", "success", 14);
            btnSave.addActionListener(e -> saveNhomQuyen());
            pnBottom.add(btnSave);
        }

        ButtonCustom btnClose = new ButtonCustom(isViewMode ? "Đóng" : "Hủy bỏ", "danger", 14);
        btnClose.addActionListener(e -> dispose());
        pnBottom.add(btnClose);
    }

    private void initCreate() {
        txtTenNhomQuyen.setText("");
    }

    private void initView() {
        txtTenNhomQuyen.setEditable(false);
    }

    private void setInfo(NhomQuyenDTO nhomQuyen) {
        txtTenNhomQuyen.setText(nhomQuyen.getTEN() != null ? nhomQuyen.getTEN() : "");
    }

    private void saveNhomQuyen() {
        String ten = txtTenNhomQuyen.getText().trim();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhóm quyền!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<ChiTietQuyenDTO> chiTietQuyenList = new ArrayList<>();
        for (int i = 0; i < permissionTableModel.getRowCount(); i++) {
            String chucNang = (String) permissionTableModel.getValueAt(i, 0);
            int mcn = danhMucChucNangList.stream()
                    .filter(dm -> dm.getTENCN().equals(chucNang))
                    .findFirst().map(DanhMucChucNangDTO::getMCN).orElse(0);

            if ((Boolean) permissionTableModel.getValueAt(i, 1)) {
                chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "view"));
            }
            if ((Boolean) permissionTableModel.getValueAt(i, 2)) {
                chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "create"));
            }
            if ((Boolean) permissionTableModel.getValueAt(i, 3)) {
                chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "update"));
            }
            if ((Boolean) permissionTableModel.getValueAt(i, 4)) {
                chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "delete"));
            }
        }

        try {
            if (isEditMode) {
                nhomQuyen.setTEN(ten);
                nhomQuyenBUS.update(nhomQuyen, chiTietQuyenList, nhomQuyenBUS.getAll().indexOf(nhomQuyen));
                JOptionPane.showMessageDialog(this, "Cập nhật nhóm quyền thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                nhomQuyenBUS.add(ten, chiTietQuyenList);
                JOptionPane.showMessageDialog(this, "Thêm nhóm quyền thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
            gui.loadNhomQuyenData();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
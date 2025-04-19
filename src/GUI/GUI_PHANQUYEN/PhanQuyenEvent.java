package GUI_PHANQUYEN;

import BUS.NhomQuyenBUS;
import DTO.NhomQuyenDTO;
import DTO.DanhMucChucNangDTO;
import helper.JTableExporter; // Thêm import
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import java.util.ArrayList;
import java.io.IOException;

public class PhanQuyenEvent {
    private PhanQuyenGUI gui;
    private NhomQuyenBUS nhomQuyenBUS;
    private ArrayList<DanhMucChucNangDTO> danhMucChucNangList;
    private JTextField searchField;

    public PhanQuyenEvent(PhanQuyenGUI gui, NhomQuyenBUS nhomQuyenBUS, ArrayList<DanhMucChucNangDTO> danhMucChucNangList, JTextField searchField) {
        this.gui = gui;
        this.nhomQuyenBUS = nhomQuyenBUS;
        this.danhMucChucNangList = danhMucChucNangList;
        this.searchField = searchField;
    }

    public void openAddDialog() {
        String type = "create";
        PhanQuyenDialog dialog = new PhanQuyenDialog(nhomQuyenBUS, gui, gui, "Thêm nhóm quyền", true, type, null);
        dialog.setVisible(true);
        gui.loadNhomQuyenData();
    }

    public void openEditDialog() {
        NhomQuyenDTO selected = gui.getSelectedNhomQuyen();
        if (selected == null) {
            // Thông báo đã được hiển thị trong getSelectedNhomQuyen, không cần làm gì thêm
            return;
        }
        String type = "update";
        PhanQuyenDialog dialog = new PhanQuyenDialog(nhomQuyenBUS, gui, gui, "Chỉnh sửa nhóm quyền", true, type, selected);
        dialog.setVisible(true);
        gui.loadNhomQuyenData();
    }

    public void deleteNhomQuyen() {
        NhomQuyenDTO selected = gui.getSelectedNhomQuyen();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(gui, "Bạn có chắc chắn muốn xóa nhóm quyền này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                nhomQuyenBUS.delete(selected);
                gui.loadNhomQuyenData();
            }
        }
    }

    public void showDetails() {
        NhomQuyenDTO selected = gui.getSelectedNhomQuyen();
        if (selected != null) {
            PhanQuyenDialog dialog = new PhanQuyenDialog(nhomQuyenBUS, gui, gui, "Chi tiết nhóm quyền", true, "view", selected);
            dialog.setVisible(true);
        }
    }

    public void exportToExcel() {
        try {
            JTableExporter.exportJTableToExcel(gui.getNhomQuyenTable());
            JOptionPane.showMessageDialog(gui, "Xuất file Excel thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void refreshTable() {
        gui.loadNhomQuyenData();
        searchField.setText("");
    }

    public DocumentListener getSearchListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }

            private void search() {
                String searchText = searchField.getText();
                ArrayList<NhomQuyenDTO> searchResults = nhomQuyenBUS.search(searchText);
                DefaultTableModel tableModel = gui.getTableModel();
                tableModel.setRowCount(0);
                for (NhomQuyenDTO nq : searchResults) {
                    tableModel.addRow(new Object[]{nq.getMNQ(), nq.getTEN()});
                }
            }
        };
    }
}
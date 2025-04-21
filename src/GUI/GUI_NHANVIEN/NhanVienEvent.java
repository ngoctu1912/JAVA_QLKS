package GUI_NHANVIEN;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import helper.JTableExporter;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import java.io.IOException;
import java.util.ArrayList;

public class NhanVienEvent {
    private NhanVienGUI gui;
    private NhanVienBUS nhanVienBUS;
    private JTextField searchField;

    public NhanVienEvent(NhanVienGUI gui, NhanVienBUS nhanVienBUS, JTextField searchField) {
        this.gui = gui;
        this.nhanVienBUS = nhanVienBUS;
        this.searchField = searchField;
    }

    public void openAddDialog() {
        NhanVienDialog dialog = new NhanVienDialog(nhanVienBUS, gui, gui, "Thêm nhân viên", true, "create", null);
        dialog.setVisible(true);
    }

    public void openEditDialog() {
        NhanVienDTO selected = gui.getSelectedNhanVien();
        if (selected == null) {
            return;
        }
        NhanVienDialog dialog = new NhanVienDialog(nhanVienBUS, gui, gui, "Sửa nhân viên", true, "update", selected);
        dialog.setVisible(true);
    }

    public void deleteNhanVien() {
        NhanVienDTO selected = gui.getSelectedNhanVien();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(gui, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                nhanVienBUS.deleteNv(selected);
                gui.loadNhanVienData();
            }
        }
    }

    public void showDetails() {
        NhanVienDTO selected = gui.getSelectedNhanVien();
        if (selected != null) {
            NhanVienDialog dialog = new NhanVienDialog(nhanVienBUS, gui, gui, "Chi tiết nhân viên", true, "view", selected);
            dialog.setVisible(true);
        }
    }

    public void exportToExcel() {
        try {
            JTableExporter.exportJTableToExcel(gui.getNhanVienTable());
            JOptionPane.showMessageDialog(gui, "Xuất file Excel thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void importExcel() {
        nhanVienBUS.importExcel();
        gui.loadNhanVienData();
    }

    public void refreshTable() {
        gui.loadNhanVienData();
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
                ArrayList<NhanVienDTO> searchResults = nhanVienBUS.search(searchText);
                DefaultTableModel tableModel = gui.getTableModel();
                tableModel.setRowCount(0);
                for (NhanVienDTO nv : searchResults) {
                    tableModel.addRow(new Object[]{
                            nv.getMNV(),
                            nv.getHOTEN(),
                            nv.getGIOITINH() == 1 ? "Nam" : "Nữ",
                            nv.getNGAYSINH(),
                            nv.getSDT(),
                            nv.getEMAIL()
                    });
                }
            }
        };
    }
}
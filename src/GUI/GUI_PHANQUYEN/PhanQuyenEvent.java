package GUI_PHANQUYEN;

import BUS.NhomQuyenBUS;
import Component.IntegratedSearch;
import DTO.NhomQuyenDTO;
import DTO.DanhMucChucNangDTO;
import helper.JTableExporter;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import java.util.ArrayList;
import java.io.IOException;
import java.awt.Component;

public class PhanQuyenEvent {
    private PhanQuyenGUI gui;
    private NhomQuyenBUS nhomQuyenBUS;
    private ArrayList<DanhMucChucNangDTO> danhMucChucNangList;
    private IntegratedSearch searchPanel;

    public PhanQuyenEvent(PhanQuyenGUI gui, NhomQuyenBUS nhomQuyenBUS, ArrayList<DanhMucChucNangDTO> danhMucChucNangList, IntegratedSearch searchPanel) {
        this.gui = gui;
        this.nhomQuyenBUS = nhomQuyenBUS;
        this.danhMucChucNangList = danhMucChucNangList;
        this.searchPanel = searchPanel;
    }

    public void openAddDialog() {
        String type = "create";
        JFrame parentFrame = getParentFrame(gui);
        PhanQuyenDialog dialog = new PhanQuyenDialog(nhomQuyenBUS, gui, parentFrame, "Thêm nhóm quyền", true, type, null);
        dialog.setVisible(true);
        gui.loadNhomQuyenData();
    }

    public void openEditDialog() {
        NhomQuyenDTO selected = gui.getSelectedNhomQuyen();
        if (selected == null) {
            return;
        }
        String type = "update";
        JFrame parentFrame = getParentFrame(gui);
        PhanQuyenDialog dialog = new PhanQuyenDialog(nhomQuyenBUS, gui, parentFrame, "Chỉnh sửa nhóm quyền", true, type, selected);
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
            JFrame parentFrame = getParentFrame(gui);
            PhanQuyenDialog dialog = new PhanQuyenDialog(nhomQuyenBUS, gui, parentFrame, "Chi tiết nhóm quyền", true, "view", selected);
            dialog.setVisible(true);
        }
    }

    public void exportToExcel() {
        try {
            boolean success = JTableExporter.exportJTableToExcel(gui.getNhomQuyenTable());
            if (success) {
                JOptionPane.showMessageDialog(gui, "Xuất file Excel thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void refreshTable() {
        gui.loadNhomQuyenData();
        if (searchPanel != null) {
            searchPanel.getSearchField().setText("");
        }
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
                if (searchPanel == null) {
                    return;
                }
                String searchText = searchPanel.getSearchField().getText();
                ArrayList<NhomQuyenDTO> searchResults = nhomQuyenBUS.search(searchText);
                DefaultTableModel tableModel = gui.getTableModel();
                tableModel.setRowCount(0);
                for (NhomQuyenDTO nq : searchResults) {
                    tableModel.addRow(new Object[]{nq.getMNQ(), nq.getTEN()});
                }
            }
        };
    }

    private JFrame getParentFrame(Component component) {
        while (component != null && !(component instanceof JFrame)) {
            component = component.getParent();
        }
        return (JFrame) component;
    }
}
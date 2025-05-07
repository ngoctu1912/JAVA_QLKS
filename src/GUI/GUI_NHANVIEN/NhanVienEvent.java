package GUI_NHANVIEN;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import helper.JTableExporter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class NhanVienEvent {
    private NhanVienGUI gui;
    private NhanVienBUS nhanVienBUS;

    public NhanVienEvent(NhanVienGUI gui) {
        this.gui = gui;
        this.nhanVienBUS = gui.getNhanVienBUS();

        // Attach double-click event to table
        gui.getNhanVienTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = gui.getNhanVienTable().getSelectedRow();
                    if (row >= 0) {
                        NhanVienDTO nv = gui.getSelectedNhanVien();
                        if (nv != null) {
                            new NhanVienDialog(gui, nv, false, true);
                        } else {
                            JOptionPane.showMessageDialog(gui, "Không tìm thấy nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // Attach events to sidebar buttons
        gui.getSidebarPanel().btn.get("create").addActionListener(e -> openAddDialog());
        gui.getSidebarPanel().btn.get("update").addActionListener(e -> openEditDialog());
        gui.getSidebarPanel().btn.get("delete").addActionListener(e -> deleteNhanVien());
        gui.getSidebarPanel().btn.get("detail").addActionListener(e -> openDetailDialog());
        gui.getSidebarPanel().btn.get("export").addActionListener(e -> exportToExcel());
    }

    public void openAddDialog() {
        new NhanVienDialog(gui, null, false, false);
        gui.loadTableData();
    }

    public void openEditDialog() {
        NhanVienDTO selected = gui.getSelectedNhanVien();
        if (selected == null) {
            return;
        }
        new NhanVienDialog(gui, selected, true, false);
        gui.loadTableData();
    }

    public void deleteNhanVien() {
        NhanVienDTO selected = gui.getSelectedNhanVien();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(gui, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                nhanVienBUS.deleteNv(selected);
                gui.loadTableData();
                JOptionPane.showMessageDialog(gui, "Xóa nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void openDetailDialog() {
        NhanVienDTO selected = gui.getSelectedNhanVien();
        if (selected != null) {
            new NhanVienDialog(gui, selected, false, true);
        }
    }

    public void exportToExcel() {
        try {
            boolean success = JTableExporter.exportJTableToExcel(gui.getNhanVienTable());
            if (success) {
                JOptionPane.showMessageDialog(gui, "Xuất file Excel thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
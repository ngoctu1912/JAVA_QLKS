package GUI_PHONG;

import BUS.PhongBUS;
import DTO.PhongDTO;
import helper.JTableExporter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class PhongEventHandler {
    private FormPhong form;
    private PhongBUS phongBUS;

    public PhongEventHandler(FormPhong form) {
        this.form = form;
        this.phongBUS = form.getPhongBUS();
        // System.out.println("PhongEventHandler initialized"); // Debug log

        // Gắn sự kiện bấm đúp vào bảng
        form.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = form.getTable().getSelectedRow();
                    if (row >= 0) {
                        String maP = form.getTable().getValueAt(row, 1).toString();
                        PhongDTO room = phongBUS.getPhongById(maP);
                        // System.out.println("Double-click: maP=" + maP + ", room=" + (room != null ? room.getMaP() : "null")); // Debug log
                        if (room != null) {
                            new PhongDialog(form.getOwner(), room, false, true);
                        } else {
                            JOptionPane.showMessageDialog(form, "Không tìm thấy phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // Gắn sự kiện cho các nút trong SidebarPanel
        form.getSidebarPanel().btn.get("create").addActionListener(e -> openAddDialog());
        form.getSidebarPanel().btn.get("update").addActionListener(e -> openEditDialog());
        form.getSidebarPanel().btn.get("delete").addActionListener(e -> deleteRoom());
        form.getSidebarPanel().btn.get("detail").addActionListener(e -> openDetailDialog());
        form.getSidebarPanel().btn.get("export").addActionListener(e -> exportToExcel());
    }

    public void openAddDialog() {
        // System.out.println("openAddDialog called"); // Debug log
        new PhongDialog(form.getOwner(), null, false, false);
        form.loadTableData();
    }

    public void openEditDialog() {
        int row = form.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(form, "Vui lòng chọn một phòng để chỉnh sửa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maP = (String) form.getTable().getValueAt(row, 1);
        PhongDTO room = phongBUS.getPhongById(maP);
        // System.out.println("openEditDialog: maP=" + maP + ", room=" + (room != null ? room.getMaP() : "null")); // Debug log
        if (room == null) {
            JOptionPane.showMessageDialog(form, "Không tìm thấy phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new PhongDialog(form.getOwner(), room, true, false);
        form.loadTableData();
    }

    public void deleteRoom() {
        int row = form.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(form, "Vui lòng chọn một phòng để xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(form, "Bạn có chắc chắn muốn xóa phòng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String maP = (String) form.getTable().getValueAt(row, 1);
            try {
                int rowsAffected = phongBUS.deletePhong(maP);
                if (rowsAffected > 0) {
                    form.loadTableData();
                    JOptionPane.showMessageDialog(form, "Xóa phòng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(form, "Xóa phòng thất bại! Phòng không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(form, "Lỗi khi xóa phòng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void openDetailDialog() {
        int row = form.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(form, "Vui lòng chọn một phòng để xem chi tiết!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maP = (String) form.getTable().getValueAt(row, 1);
        PhongDTO room = phongBUS.getPhongById(maP);
        // System.out.println("openDetailDialog: maP=" + maP + ", room=" + (room != null ? room.getMaP() : "null")); // Debug log
        if (room != null) {
            new PhongDialog(form.getOwner(), room, false, true);
        } else {
            JOptionPane.showMessageDialog(form, "Không tìm thấy phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exportToExcel() {
        try {
            boolean success = JTableExporter.exportJTableToExcel(form.getTable());
            if (success) {
                JOptionPane.showMessageDialog(form, "Xuất file Excel thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(form, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
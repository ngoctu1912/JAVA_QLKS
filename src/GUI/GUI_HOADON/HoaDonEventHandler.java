package GUI_HOADON;

import BUS.HoaDonBUS;
import DTO.HoaDonDTO;
import helper.JTableExporter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class HoaDonEventHandler {
    private FormHoaDon form;
    private HoaDonBUS hoaDonBUS;

    public HoaDonEventHandler(FormHoaDon form) {
        this.form = form;
        this.hoaDonBUS = form.getHoaDonBUS();
        System.out.println("HoaDonEventHandler initialized");

        // Gắn sự kiện bấm đúp vào bảng
        form.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = form.getTable().getSelectedRow();
                    if (row >= 0) {
                        String maHD = form.getTable().getValueAt(row, 1).toString();
                        HoaDonDTO hoaDon = hoaDonBUS.selectById(maHD);
                        System.out.println("Double-click: maHD=" + maHD + ", hoaDon=" + (hoaDon != null ? hoaDon.getMaHD() : "null"));
                        if (hoaDon != null) {
                            new HoaDonDialog(form.getOwner(), hoaDon, false, true);
                        } else {
                            JOptionPane.showMessageDialog(form, "Không tìm thấy hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // Gắn sự kiện cho các nút trong SidebarPanel
        form.getSidebarPanel().btn.get("create").addActionListener(e -> openAddDialog());
        form.getSidebarPanel().btn.get("update").addActionListener(e -> openEditDialog());
        form.getSidebarPanel().btn.get("delete").addActionListener(e -> deleteHoaDon());
        form.getSidebarPanel().btn.get("detail").addActionListener(e -> openDetailDialog());
        form.getSidebarPanel().btn.get("export").addActionListener(e -> exportToExcel());
    }

    public void openAddDialog() {
        System.out.println("openAddDialog called");
        new HoaDonDialog(form.getOwner(), null, false, false);
        form.loadTableData();
    }

    public void openEditDialog() {
        int row = form.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(form, "Vui lòng chọn một hóa đơn để chỉnh sửa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maHD = (String) form.getTable().getValueAt(row, 1);
        HoaDonDTO hoaDon = hoaDonBUS.selectById(maHD);
        System.out.println("openEditDialog: maHD=" + maHD + ", hoaDon=" + (hoaDon != null ? hoaDon.getMaHD() : "null"));
        if (hoaDon == null) {
            JOptionPane.showMessageDialog(form, "Không tìm thấy hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new HoaDonDialog(form.getOwner(), hoaDon, true, false);
        form.loadTableData();
    }

    public void deleteHoaDon() {
        int row = form.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(form, "Vui lòng chọn một hóa đơn để xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(form, "Bạn có chắc chắn muốn xóa hóa đơn này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String maHD = (String) form.getTable().getValueAt(row, 1);
            try {
                HoaDonDTO hoaDon = new HoaDonDTO();
                hoaDon.setMaHD(maHD);
                int rowsAffected = hoaDonBUS.delete(hoaDon);
                if (rowsAffected > 0) {
                    form.loadTableData();
                    JOptionPane.showMessageDialog(form, "Xóa hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(form, "Xóa hóa đơn thất bại! Hóa đơn không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(form, "Lỗi khi xóa hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void openDetailDialog() {
        int row = form.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(form, "Vui lòng chọn một hóa đơn để xem chi tiết!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maHD = (String) form.getTable().getValueAt(row, 1);
        HoaDonDTO hoaDon = hoaDonBUS.selectById(maHD);
        System.out.println("openDetailDialog: maHD=" + maHD + ", hoaDon=" + (hoaDon != null ? hoaDon.getMaHD() : "null"));
        if (hoaDon != null) {
            new HoaDonDialog(form.getOwner(), hoaDon, false, true);
        } else {
            JOptionPane.showMessageDialog(form, "Không tìm thấy hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
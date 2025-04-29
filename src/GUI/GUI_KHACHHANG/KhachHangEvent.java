package GUI_KHACHHANG;

import BUS.KhachHangBUS;
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import helper.JTableExporter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class KhachHangEvent {
    private KhachHangFrame frame;
    private KhachHangBUS khachHangBUS;

    public KhachHangEvent(KhachHangFrame frame) {
        this.frame = frame;
        this.khachHangBUS = frame.getKhachHangBUS();
        setupEvents();
    }

    private void setupEvents() {
        // Gắn sự kiện bấm đúp vào bảng
        frame.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = frame.getTable().getSelectedRow();
                    if (row >= 0) {
                        String maKH = String.valueOf(frame.getTable().getValueAt(row, 1)); // Chuyển đổi thành String
                        KhachHangDTO khachHang = khachHangBUS.selectById(maKH);
                        if (khachHang != null) {
                            new KhachHangDialog(frame.getOwner(), khachHang, false, true);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Không tìm thấy khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // Gắn sự kiện cho các nút trong SidebarPanel
        frame.getSidebarPanel().btn.get("create").addActionListener(e -> openAddDialog());
        frame.getSidebarPanel().btn.get("update").addActionListener(e -> openEditDialog());
        frame.getSidebarPanel().btn.get("delete").addActionListener(e -> deleteCustomer());
        frame.getSidebarPanel().btn.get("detail").addActionListener(e -> openDetailDialog());
        frame.getSidebarPanel().btn.get("export").addActionListener(e -> exportToExcel());
    }

    private void openAddDialog() {
        new KhachHangDialog(frame.getOwner(), null, false, false);
        frame.loadTableData();
    }

    private void openEditDialog() {
        int row = frame.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn một khách hàng để chỉnh sửa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maKH = String.valueOf(frame.getTable().getValueAt(row, 1)); // Chuyển đổi thành String
        KhachHangDTO khachHang = khachHangBUS.selectById(maKH);
        if (khachHang == null) {
            JOptionPane.showMessageDialog(frame, "Không tìm thấy khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new KhachHangDialog(frame.getOwner(), khachHang, true, false);
        frame.loadTableData();
    }

    private void deleteCustomer() {
        int row = frame.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn một khách hàng để xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String maKH = String.valueOf(frame.getTable().getValueAt(row, 1)); // Chuyển đổi thành String
            try {
                int rowsAffected = KhachHangDAO.getInstance().delete(maKH);
                if (rowsAffected > 0) {
                    frame.loadTableData();
                    JOptionPane.showMessageDialog(frame, "Xóa khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Xóa khách hàng thất bại! Khách hàng không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Lỗi khi xóa khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void openDetailDialog() {
        int row = frame.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn một khách hàng để xem chi tiết!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maKH = String.valueOf(frame.getTable().getValueAt(row, 1)); // Chuyển đổi thành String
        KhachHangDTO khachHang = khachHangBUS.selectById(maKH);
        if (khachHang != null) {
            new KhachHangDialog(frame.getOwner(), khachHang, false, true);
        } else {
            JOptionPane.showMessageDialog(frame, "Không tìm thấy khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportToExcel() {
        try {
            boolean success = JTableExporter.exportJTableToExcel(frame.getTable());
            if (success) {
                JOptionPane.showMessageDialog(frame, "Xuất file Excel thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}

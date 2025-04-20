// package GUI_PHONG;

// import BUS.PhongBUS;
// import DTO.PhongDTO;
// import helper.JTableExporter;

// import javax.swing.*;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import java.io.IOException;

// public class PhongEventHandler {
//     private FormPhong form;
//     private PhongBUS phongBUS;

//     public PhongEventHandler(FormPhong form) {
//         this.form = form;
//         this.phongBUS = form.getPhongBUS();

//         // Gắn sự kiện bấm đúp vào bảng để mở chi tiết phòng
//         form.getTable().addMouseListener(new MouseAdapter() {
//             @Override
//             public void mouseClicked(MouseEvent evt) {
//                 if (evt.getClickCount() == 2) { // Bấm đúp
//                     int row = form.getTable().getSelectedRow();
//                     if (row >= 0) {
//                         String maP = form.getTable().getValueAt(row, 1).toString();
//                         PhongDTO room = phongBUS.getPhongById(maP);
//                         if (room != null) {
//                             // Open PhongDialog in view mode
//                             new PhongDialog((JFrame) SwingUtilities.getWindowAncestor(form.getTable()), room, false, true).setVisible(true);
//                         }
//                     }
//                 }
//             }
//         });
//     }

//     public void openAddDialog() {
//         // Open PhongDialog in add mode (isEditMode = false, isViewMode = false)
//         PhongDialog dialog = new PhongDialog((JFrame) SwingUtilities.getWindowAncestor(form.getTable()), null, false, false);
//         dialog.setVisible(true);
//         // Refresh table after adding
//         form.loadTableData();
//     }

//     public void openEditDialog() {
//         int row = form.getTable().getSelectedRow();
//         if (row < 0) {
//             JOptionPane.showMessageDialog(form, "Vui lòng chọn một phòng để chỉnh sửa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//             return;
//         }

//         String maP = (String) form.getTable().getValueAt(row, 1);
//         PhongDTO room = phongBUS.getPhongById(maP);
//         if (room == null) {
//             JOptionPane.showMessageDialog(form, "Không tìm thấy phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         // Open PhongDialog in edit mode (isEditMode = true, isViewMode = false)
//         PhongDialog dialog = new PhongDialog((JFrame) SwingUtilities.getWindowAncestor(form.getTable()), room, true, false);
//         dialog.setVisible(true);
//         // Refresh table after editing
//         form.loadTableData();
//     }

//     public void deleteRoom() {
//         int row = form.getTable().getSelectedRow();
//         if (row < 0) {
//             JOptionPane.showMessageDialog(form, "Vui lòng chọn một phòng để xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//             return;
//         }
    
//         int confirm = JOptionPane.showConfirmDialog(form, "Bạn có chắc chắn muốn xóa phòng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
//         if (confirm == JOptionPane.YES_OPTION) {
//             String maP = (String) form.getTable().getValueAt(row, 1);
//             phongBUS.deletePhong(maP);
//             form.loadTableData();
//             JOptionPane.showMessageDialog(form, "Xóa phòng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//         }
//     }

//     public void exportToExcel() {
//         try {
//             boolean success = JTableExporter.exportJTableToExcel(form.getTable());
//             if (success) {
//                 JOptionPane.showMessageDialog(form, "Xuất file Excel thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//             }
//         } catch (IOException ex) {
//             JOptionPane.showMessageDialog(form, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//             ex.printStackTrace();
//         }
//     }
// }

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

        // Gắn sự kiện bấm đúp vào bảng để mở chi tiết phòng
        form.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Bấm đúp
                    int row = form.getTable().getSelectedRow();
                    if (row >= 0) {
                        String maP = form.getTable().getValueAt(row, 1).toString();
                        PhongDTO room = phongBUS.getPhongById(maP);
                        if (room != null) {
                            // Open PhongDialog in view mode
                            new PhongDialog(SwingUtilities.getWindowAncestor(form), room, false, true).setVisible(true);
                        }
                    }
                }
            }
        });
    }

    public void openAddDialog() {
        // Open PhongDialog in add mode (isEditMode = false, isViewMode = false)
        PhongDialog dialog = new PhongDialog(SwingUtilities.getWindowAncestor(form), null, false, false);
        dialog.setVisible(true);
        // Refresh table after adding
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
        if (room == null) {
            JOptionPane.showMessageDialog(form, "Không tìm thấy phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Open PhongDialog in edit mode (isEditMode = true, isViewMode = false)
        PhongDialog dialog = new PhongDialog(SwingUtilities.getWindowAncestor(form), room, true, false);
        dialog.setVisible(true);
        // Refresh table after editing
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
            System.out.println("Attempting to delete room: " + maP);
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
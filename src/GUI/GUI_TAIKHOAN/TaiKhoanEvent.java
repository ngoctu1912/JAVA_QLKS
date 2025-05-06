// package GUI_TAIKHOAN;

// import BUS.TaiKhoanBUS;
// import BUS.NhomQuyenBUS;
// import DTO.TaiKhoanDTO;
// import DTO.NhomQuyenDTO;
// import DTO.ChiTietQuyenDTO;
// import helper.JTableExporter;
// import javax.swing.*;
// import javax.swing.event.DocumentListener;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.event.DocumentEvent;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import java.io.IOException;
// import java.util.List;

// public class TaiKhoanEvent {
//     private TaiKhoanGUI gui;
//     private TaiKhoanBUS taiKhoanBUS;
//     private int maNhomQuyen;

//     public TaiKhoanEvent(TaiKhoanGUI gui, TaiKhoanBUS taiKhoanBUS, int maNhomQuyen) {
//         this.gui = gui;
//         this.taiKhoanBUS = taiKhoanBUS;
//         this.maNhomQuyen = maNhomQuyen;
//         System.out.println("TaiKhoanEvent initialized with maNhomQuyen: " + maNhomQuyen);
//         initEvents();
//     }

//     private void initEvents() {
//         // Sự kiện bấm đúp vào bảng
//         gui.getTaiKhoanTable().addMouseListener(new MouseAdapter() {
//             @Override
//             public void mouseClicked(MouseEvent evt) {
//                 if (evt.getClickCount() == 2) {
//                     showDetails();
//                 }
//             }
//         });

//         // Sự kiện cho các nút
//         gui.getSidebarPanel().btn.get("create").addActionListener(e -> openAddDialog());
//         gui.getSidebarPanel().btn.get("update").addActionListener(e -> openEditDialog());
//         gui.getSidebarPanel().btn.get("delete").addActionListener(e -> deleteTaiKhoan());
//         gui.getSidebarPanel().btn.get("detail").addActionListener(e -> showDetails());
//         gui.getSidebarPanel().btn.get("export").addActionListener(e -> exportToExcel());
//     }

//     public void openAddDialog() {
//         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
//         boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "add");
//         if (maNhomQuyen == 0 || !hasPermission) {
//             JOptionPane.showMessageDialog(gui, "Bạn không có quyền thêm tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//             return;
//         }
//         TaiKhoanDialog dialog = new TaiKhoanDialog(taiKhoanBUS, gui, gui, "THÊM TÀI KHOẢN", true, "create", null, maNhomQuyen);
//     }

//     public void openEditDialog() {
//         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
//         boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "edit");
//         if (maNhomQuyen == 0 || !hasPermission) {
//             JOptionPane.showMessageDialog(gui, "Bạn không có quyền sửa tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//             return;
//         }
//         TaiKhoanDTO selected = gui.getSelectedTaiKhoan();
//         if (selected == null) {
//             return;
//         }
//         TaiKhoanDialog dialog = new TaiKhoanDialog(taiKhoanBUS, gui, gui, "SỬA TÀI KHOẢN", true, "update", selected, maNhomQuyen);
//     }

//     public void deleteTaiKhoan() {
//         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
//         boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "delete");
//         if (maNhomQuyen == 0 || !hasPermission) {
//             JOptionPane.showMessageDialog(gui, "Bạn không có quyền xóa tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//             return;
//         }
//         TaiKhoanDTO selected = gui.getSelectedTaiKhoan();
//         if (selected != null) {
//             int confirm = JOptionPane.showConfirmDialog(gui, "Bạn có chắc chắn muốn xóa tài khoản này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
//             if (confirm == JOptionPane.YES_OPTION) {
//                 boolean success = taiKhoanBUS.deleteAcc(selected.getMaNV());
//                 if (success) {
//                     gui.loadTaiKhoanData();
//                     JOptionPane.showMessageDialog(gui, "Xóa tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                 } else {
//                     JOptionPane.showMessageDialog(gui, "Xóa tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 }
//             }
//         }
//     }

//     public void showDetails() {
//         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
//         boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "view");
//         if (maNhomQuyen == 0 || !hasPermission) {
//             JOptionPane.showMessageDialog(gui, "Bạn không có quyền xem chi tiết tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//             return;
//         }
//         TaiKhoanDTO selected = gui.getSelectedTaiKhoan();
//         if (selected != null) {
//             TaiKhoanDialog dialog = new TaiKhoanDialog(taiKhoanBUS, gui, gui, "CHI TIẾT TÀI KHOẢN", true, "view", selected, maNhomQuyen);
//         }
//     }

//     public void exportToExcel() {
//         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
//         boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "export");
//         if (maNhomQuyen == 0 || !hasPermission) {
//             JOptionPane.showMessageDialog(gui, "Bạn không có quyền xuất dữ liệu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//             return;
//         }
//         try {
//             JTableExporter.exportJTableToExcel(gui.getTaiKhoanTable());
//             JOptionPane.showMessageDialog(gui, "Xuất file Excel thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//         } catch (IOException ex) {
//             JOptionPane.showMessageDialog(gui, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//             ex.printStackTrace();
//         }
//     }
// }

package GUI_TAIKHOAN;

import BUS.TaiKhoanBUS;
import BUS.NhomQuyenBUS;
import DTO.TaiKhoanDTO;
import DTO.NhomQuyenDTO;
import DTO.ChiTietQuyenDTO;
import helper.JTableExporter;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class TaiKhoanEvent {
    private TaiKhoanGUI gui;
    private TaiKhoanBUS taiKhoanBUS;
    private int maNhomQuyen;

    public TaiKhoanEvent(TaiKhoanGUI gui, TaiKhoanBUS taiKhoanBUS, int maNhomQuyen) {
        this.gui = gui;
        this.taiKhoanBUS = taiKhoanBUS;
        this.maNhomQuyen = maNhomQuyen;
        System.out.println("TaiKhoanEvent initialized with maNhomQuyen: " + maNhomQuyen);
        initEvents();
    }

    private void initEvents() {
        gui.getTaiKhoanTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    showDetails();
                }
            }
        });

        gui.getSidebarPanel().btn.get("create").addActionListener(e -> openAddDialog());
        gui.getSidebarPanel().btn.get("update").addActionListener(e -> openEditDialog());
        gui.getSidebarPanel().btn.get("delete").addActionListener(e -> deleteTaiKhoan());
        gui.getSidebarPanel().btn.get("detail").addActionListener(e -> showDetails());
        gui.getSidebarPanel().btn.get("export").addActionListener(e -> exportToExcel());
    }

    public void openAddDialog() {
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "create"); // Changed from "add"
        if (maNhomQuyen == 0 || !hasPermission) {
            JOptionPane.showMessageDialog(gui, "Bạn không có quyền thêm tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        TaiKhoanDialog dialog = new TaiKhoanDialog(taiKhoanBUS, gui, gui, "THÊM TÀI KHOẢN", true, "create", null, maNhomQuyen);
    }

    public void openEditDialog() {
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "update"); // Changed from "edit"
        if (maNhomQuyen == 0 || !hasPermission) {
            JOptionPane.showMessageDialog(gui, "Bạn không có quyền sửa tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        TaiKhoanDTO selected = gui.getSelectedTaiKhoan();
        if (selected == null) {
            return;
        }
        TaiKhoanDialog dialog = new TaiKhoanDialog(taiKhoanBUS, gui, gui, "SỬA TÀI KHOẢN", true, "update", selected, maNhomQuyen);
    }

    public void deleteTaiKhoan() {
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "delete");
        if (maNhomQuyen == 0 || !hasPermission) {
            JOptionPane.showMessageDialog(gui, "Bạn không có quyền xóa tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        TaiKhoanDTO selected = gui.getSelectedTaiKhoan();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(gui, "Bạn có chắc chắn muốn xóa tài khoản này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = taiKhoanBUS.deleteAcc(selected.getMaNV());
                if (success) {
                    gui.loadTaiKhoanData();
                    JOptionPane.showMessageDialog(gui, "Xóa tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(gui, "Xóa tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void showDetails() {
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "view");
        if (maNhomQuyen == 0 || !hasPermission) {
            JOptionPane.showMessageDialog(gui, "Bạn không có quyền xem chi tiết tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        TaiKhoanDTO selected = gui.getSelectedTaiKhoan();
        if (selected != null) {
            TaiKhoanDialog dialog = new TaiKhoanDialog(taiKhoanBUS, gui, gui, "CHI TIẾT TÀI KHOẢN", true, "view", selected, maNhomQuyen);
        }
    }

    public void exportToExcel() {
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "export");
        if (maNhomQuyen == 0 || !hasPermission) {
            JOptionPane.showMessageDialog(gui, "Bạn không có quyền xuất dữ liệu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            JTableExporter.exportJTableToExcel(gui.getTaiKhoanTable());
            JOptionPane.showMessageDialog(gui, "Xuất file Excel thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
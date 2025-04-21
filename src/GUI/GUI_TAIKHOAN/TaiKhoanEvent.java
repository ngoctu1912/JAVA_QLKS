package GUI_TAIKHOAN;

import BUS.TaiKhoanBUS;
import BUS.NhomQuyenBUS;
import DTO.TaiKhoanDTO;
import DTO.NhomQuyenDTO;
import DTO.ChiTietQuyenDTO;
import helper.JTableExporter;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import java.io.IOException;
import java.util.List;

public class TaiKhoanEvent {
    private TaiKhoanGUI gui;
    private TaiKhoanBUS taiKhoanBUS;
    private JTextField searchField;
    private NhomQuyenBUS nhomQuyenBUS;
    private int maNhomQuyen;

    public TaiKhoanEvent(TaiKhoanGUI gui, TaiKhoanBUS taiKhoanBUS, JTextField searchField, int maNhomQuyen) {
        this.gui = gui;
        this.taiKhoanBUS = taiKhoanBUS;
        this.searchField = searchField;
        this.nhomQuyenBUS = new NhomQuyenBUS();
        this.maNhomQuyen = maNhomQuyen;
        System.out.println("TaiKhoanEvent initialized with maNhomQuyen: " + maNhomQuyen);
    }

    public void openAddDialog() {
        System.out.println("Checking permission for add, maNhomQuyen: " + maNhomQuyen + ", MCN: 12, Action: add");
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "add");
        if (!hasPermission) {
            List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
            System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
        }
        System.out.println("Permission check result: " + hasPermission);
        if (maNhomQuyen == 0 || !hasPermission) {
            String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền thêm tài khoản! Kiểm tra CHITIETQUYEN cho MNQ=" + maNhomQuyen + ", MCN=12, HANHDONG=add.";
            JOptionPane.showMessageDialog(gui, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
            return;
        }
        TaiKhoanDialog dialog = new TaiKhoanDialog(taiKhoanBUS, gui, gui, "Thêm tài khoản", true, "create", null, maNhomQuyen);
        dialog.setVisible(true);
    }

    public void openEditDialog() {
        System.out.println("Checking permission for edit, maNhomQuyen: " + maNhomQuyen + ", MCN: 12, Action: edit");
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "edit");
        if (!hasPermission) {
            List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
            System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
        }
        System.out.println("Permission check result: " + hasPermission);
        if (maNhomQuyen == 0 || !hasPermission) {
            String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền sửa tài khoản! Kiểm tra CHITIETQUYEN cho MNQ=" + maNhomQuyen + ", MCN=12, HANHDONG=edit.";
            JOptionPane.showMessageDialog(gui, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
            return;
        }
        TaiKhoanDTO selected = gui.getSelectedTaiKhoan();
        if (selected == null) {
            return;
        }
        TaiKhoanDialog dialog = new TaiKhoanDialog(taiKhoanBUS, gui, gui, "Sửa tài khoản", true, "update", selected, maNhomQuyen);
        dialog.setVisible(true);
    }

    public void deleteTaiKhoan() {
        System.out.println("Checking permission for delete, maNhomQuyen: " + maNhomQuyen + ", MCN: 12, Action: delete");
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "delete");
        if (!hasPermission) {
            List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
            System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
        }
        System.out.println("Permission check result: " + hasPermission);
        if (maNhomQuyen == 0 || !hasPermission) {
            String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền xóa tài khoản! Kiểm tra CHITIETQUYEN cho MNQ=" + maNhomQuyen + ", MCN=12, HANHDONG=delete.";
            JOptionPane.showMessageDialog(gui, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
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
        System.out.println("Checking permission for view, maNhomQuyen: " + maNhomQuyen + ", MCN: 12, Action: view");
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "view");
        if (!hasPermission) {
            List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
            System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
        }
        System.out.println("Permission check result: " + hasPermission);
        if (maNhomQuyen == 0 || !hasPermission) {
            String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền xem chi tiết tài khoản! Kiểm tra CHITIETQUYEN cho MNQ=" + maNhomQuyen + ", MCN=12, HANHDONG=view.";
            JOptionPane.showMessageDialog(gui, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
            return;
        }
        TaiKhoanDTO selected = gui.getSelectedTaiKhoan();
        if (selected != null) {
            TaiKhoanDialog dialog = new TaiKhoanDialog(taiKhoanBUS, gui, gui, "Chi tiết tài khoản", true, "view", selected, maNhomQuyen);
            dialog.setVisible(true);
        }
    }

    public void exportToExcel() {
        System.out.println("Checking permission for export, maNhomQuyen: " + maNhomQuyen + ", MCN: 12, Action: export");
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", "export");
        if (!hasPermission) {
            List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
            System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
        }
        System.out.println("Permission check result: " + hasPermission);
        if (maNhomQuyen == 0 || !hasPermission) {
            String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền xuất dữ liệu! Kiểm tra CHITIETQUYEN cho MNQ=" + maNhomQuyen + ", MCN=12, HANHDONG=export.";
            JOptionPane.showMessageDialog(gui, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
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

    public void refreshTable() {
        gui.loadTaiKhoanData();
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
                String searchText = searchField.getText().trim();
                List<TaiKhoanDTO> searchResults = taiKhoanBUS.search(searchText, "Tất cả");
                DefaultTableModel tableModel = gui.getTableModel();
                tableModel.setRowCount(0);
                for (TaiKhoanDTO tk : searchResults) {
                    String trangthaiString = switch (tk.getTrangThai()) {
                        case 1 -> "Hoạt động";
                        case 0 -> "Ngưng hoạt động";
                        case 2 -> "Chờ xử lý";
                        default -> "Không xác định";
                    };
                    NhomQuyenDTO nhomQuyen = taiKhoanBUS.getNhomQuyenDTO(tk.getMaNhomQuyen());
                    String tenNhomQuyen = nhomQuyen != null ? nhomQuyen.getTEN() : "Không xác định";
                    tableModel.addRow(new Object[]{
                        tk.getMaNV(),
                        tk.getTenDangNhap(),
                        tenNhomQuyen,
                        trangthaiString
                    });
                }
            }
        };
    }
}
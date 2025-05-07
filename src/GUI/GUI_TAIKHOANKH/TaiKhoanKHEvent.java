// package GUI_TAIKHOANKH;

// import BUS.TaiKhoanBUS;
// import DTO.NhomQuyenDTO;
// import DTO.TaiKhoanKHDTO;
// import Component.ButtonToolBar; 
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.io.File;
// import java.io.FileOutputStream;
// import java.util.ArrayList;
// import javax.swing.JFileChooser;
// import javax.swing.JOptionPane;
// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.ss.usermodel.Sheet;
// import org.apache.poi.ss.usermodel.Workbook;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// public class TaiKhoanKHEvent {
//     private TaiKhoanKHGUI gui;
//     private TaiKhoanBUS taiKhoanBus;
//     private int maNhomQuyen;

//     public TaiKhoanKHEvent(TaiKhoanKHGUI gui, TaiKhoanBUS taiKhoanBus, int maNhomQuyen) {
//         this.gui = gui;
//         this.taiKhoanBus = taiKhoanBus;
//         this.maNhomQuyen = maNhomQuyen;
//         initEvent();
//     }

//     private void initEvent() {
//         ButtonToolBar btnCreate = gui.getSidebarPanel().btn.get("create"); // Sửa thành ButtonToolBar
//         ButtonToolBar btnUpdate = gui.getSidebarPanel().btn.get("update");
//         ButtonToolBar btnDelete = gui.getSidebarPanel().btn.get("delete");
//         ButtonToolBar btnDetail = gui.getSidebarPanel().btn.get("detail");
//         ButtonToolBar btnExport = gui.getSidebarPanel().btn.get("export");

//         if (btnCreate != null) {
//             btnCreate.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     handleCreate();
//                 }
//             });
//         }

//         if (btnUpdate != null) {
//             btnUpdate.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     handleUpdate();
//                 }
//             });
//         }

//         if (btnDelete != null) {
//             btnDelete.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     handleDelete();
//                 }
//             });
//         }

//         if (btnDetail != null) {
//             btnDetail.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     handleDetail();
//                 }
//             });
//         }

//         if (btnExport != null) {
//             btnExport.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     handleExport();
//                 }
//             });
//         }
//     }

//     private void handleCreate() {
//         TaiKhoanKHDialog dialog = new TaiKhoanKHDialog(taiKhoanBus, gui, gui, "Thêm tài khoản khách hàng", true, "create", null, maNhomQuyen);
//         dialog.setVisible(true);
//         gui.loadTaiKhoanKHData();
//     }

//     private void handleUpdate() {
//         TaiKhoanKHDTO selectedTaiKhoan = gui.getSelectedTaiKhoanKH();
//         if (selectedTaiKhoan != null) {
//             TaiKhoanKHDialog dialog = new TaiKhoanKHDialog(taiKhoanBus, gui, gui, "Cập nhật tài khoản khách hàng", true, "update", selectedTaiKhoan, maNhomQuyen);
//             dialog.setVisible(true);
//             gui.loadTaiKhoanKHData();
//         }
//     }

//     private void handleDelete() {
//         TaiKhoanKHDTO selectedTaiKhoan = gui.getSelectedTaiKhoanKH();
//         if (selectedTaiKhoan != null) {
//             int confirm = JOptionPane.showConfirmDialog(gui, "Bạn có chắc chắn muốn xóa tài khoản khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
//             if (confirm == JOptionPane.YES_OPTION) {
//                 boolean success = taiKhoanBus.deleteAccKH(selectedTaiKhoan.getMaKhachHang());
//                 if (success) {
//                     JOptionPane.showMessageDialog(gui, "Xóa tài khoản khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                     gui.loadTaiKhoanKHData();
//                 } else {
//                     JOptionPane.showMessageDialog(gui, "Xóa tài khoản khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 }
//             }
//         }
//     }

//     private void handleDetail() {
//         TaiKhoanKHDTO selectedTaiKhoan = gui.getSelectedTaiKhoanKH();
//         if (selectedTaiKhoan != null) {
//             TaiKhoanKHDialog dialog = new TaiKhoanKHDialog(taiKhoanBus, gui, gui, "Chi tiết tài khoản khách hàng", true, "view", selectedTaiKhoan, maNhomQuyen);
//             dialog.setVisible(true);
//         }
//     }

//     private void handleExport() {
//         JFileChooser fileChooser = new JFileChooser();
//         fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
//         fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//         fileChooser.setAcceptAllFileFilterUsed(false);
//         fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));

//         if (fileChooser.showSaveDialog(gui) == JFileChooser.APPROVE_OPTION) {
//             File file = fileChooser.getSelectedFile();
//             if (!file.getAbsolutePath().endsWith(".xlsx")) {
//                 file = new File(file.getAbsolutePath() + ".xlsx");
//             }

//             try (Workbook workbook = new XSSFWorkbook()) {
//                 Sheet sheet = workbook.createSheet("TaiKhoanKH");
//                 Row headerRow = sheet.createRow(0);
//                 String[] headers = {"STT", "Mã KH", "Tên đăng nhập", "Nhóm quyền", "Trạng thái"};
//                 for (int i = 0; i < headers.length; i++) {
//                     headerRow.createCell(i).setCellValue(headers[i]);
//                 }

//                 ArrayList<TaiKhoanKHDTO> list = taiKhoanBus.getTaiKhoanAllKH();
//                 int rowNum = 1;
//                 for (TaiKhoanKHDTO tk : list) {
//                     Row row = sheet.createRow(rowNum++);
//                     row.createCell(0).setCellValue(rowNum - 1);
//                     row.createCell(1).setCellValue(tk.getMaKhachHang());
//                     row.createCell(2).setCellValue(tk.getTenDangNhap());
//                     NhomQuyenDTO nhomQuyen = taiKhoanBus.getNhomQuyenDTO(tk.getMaNhomQuyen());
//                     row.createCell(3).setCellValue(nhomQuyen != null ? nhomQuyen.getTEN() : "Không xác định");
//                     String trangThai = switch (tk.getTrangThai()) {
//                         case 1 -> "Hoạt động";
//                         case 0 -> "Ngưng hoạt động";
//                         case 2 -> "Chờ xử lý";
//                         default -> "Không xác định";
//                     };
//                     row.createCell(4).setCellValue(trangThai);
//                 }

//                 for (int i = 0; i < headers.length; i++) {
//                     sheet.autoSizeColumn(i);
//                 }

//                 try (FileOutputStream fileOut = new FileOutputStream(file)) {
//                     workbook.write(fileOut);
//                     JOptionPane.showMessageDialog(gui, "Xuất file Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                 }
//             } catch (Exception e) {
//                 e.printStackTrace();
//                 JOptionPane.showMessageDialog(gui, "Xuất file Excel thất bại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//             }
//         }
//     }
// }

package GUI_TAIKHOANKH;

import BUS.NhomQuyenBUS;
import BUS.TaiKhoanBUS;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanKHDTO;
import Component.ButtonToolBar; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TaiKhoanKHEvent {
    private TaiKhoanKHGUI gui;
    private TaiKhoanBUS taiKhoanBus;
    private int maNhomQuyen;
    private TaiKhoanKHDialog currentDialog = null; // Theo dõi dialog hiện tại

    public TaiKhoanKHEvent(TaiKhoanKHGUI gui, TaiKhoanBUS taiKhoanBus, int maNhomQuyen) {
        this.gui = gui;
        this.taiKhoanBus = taiKhoanBus;
        this.maNhomQuyen = maNhomQuyen;
        initEvent();
    }

    private void initEvent() {
        ButtonToolBar btnCreate = gui.getSidebarPanel().btn.get("create");
        ButtonToolBar btnUpdate = gui.getSidebarPanel().btn.get("update");
        ButtonToolBar btnDelete = gui.getSidebarPanel().btn.get("delete");
        ButtonToolBar btnDetail = gui.getSidebarPanel().btn.get("detail");
        ButtonToolBar btnExport = gui.getSidebarPanel().btn.get("export");

        if (btnCreate != null) {
            btnCreate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleCreate();
                }
            });
        }

        if (btnUpdate != null) {
            btnUpdate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleUpdate();
                }
            });
        }

        if (btnDelete != null) {
            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleDelete();
                }
            });
        }

        if (btnDetail != null) {
            btnDetail.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleDetail();
                }
            });
        }

        if (btnExport != null) {
            btnExport.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleExport();
                }
            });
        }
    }

    private void handleCreate() {
        if (currentDialog != null && currentDialog.isVisible()) {
            return; // Ngăn tạo dialog mới
        }
        currentDialog = new TaiKhoanKHDialog(taiKhoanBus, gui, gui, "Thêm tài khoản khách hàng", true, "create", null, maNhomQuyen);
        gui.getSidebarPanel().btn.get("create").setEnabled(false);
        currentDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                gui.getSidebarPanel().btn.get("create").setEnabled(true);
                currentDialog = null;
                gui.loadTaiKhoanKHData();
            }
        });
        currentDialog.setVisible(true);
    }

    private void handleUpdate() {
        if (currentDialog != null && currentDialog.isVisible()) {
            return; // Ngăn tạo dialog mới
        }
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        if (!nhomQuyenBUS.checkPermission(maNhomQuyen, "13", "update")) {
            JOptionPane.showMessageDialog(gui, "Bạn không có quyền sửa tài khoản khách hàng!", "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
            return;
        }
        TaiKhoanKHDTO selectedTaiKhoan = gui.getSelectedTaiKhoanKH();
        if (selectedTaiKhoan != null) {
            currentDialog = new TaiKhoanKHDialog(taiKhoanBus, gui, gui, "Cập nhật tài khoản khách hàng", true, "update", selectedTaiKhoan, maNhomQuyen);
            gui.getSidebarPanel().btn.get("update").setEnabled(false);
            currentDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    gui.getSidebarPanel().btn.get("update").setEnabled(true);
                    currentDialog = null;
                    gui.loadTaiKhoanKHData();
                }
            });
            currentDialog.setVisible(true);
        }
    }

    private void handleDelete() {
        TaiKhoanKHDTO selectedTaiKhoan = gui.getSelectedTaiKhoanKH();
        if (selectedTaiKhoan != null) {
            NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
            if (!nhomQuyenBUS.checkPermission(maNhomQuyen, "13", "delete")) {
                JOptionPane.showMessageDialog(gui, "Bạn không có quyền xóa tài khoản khách hàng!", "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(gui, "Bạn có chắc chắn muốn xóa tài khoản khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = taiKhoanBus.deleteAccKH(selectedTaiKhoan.getMaKhachHang());
                if (success) {
                    JOptionPane.showMessageDialog(gui, "Xóa tài khoản khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    gui.loadTaiKhoanKHData();
                } else {
                    JOptionPane.showMessageDialog(gui, "Xóa tài khoản khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void handleDetail() {
        if (currentDialog != null && currentDialog.isVisible()) {
            return; // Ngăn tạo dialog mới
        }
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        if (!nhomQuyenBUS.checkPermission(maNhomQuyen, "13", "view")) {
            JOptionPane.showMessageDialog(gui, "Bạn không có quyền xem chi tiết tài khoản khách hàng!", "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
            return;
        }
        TaiKhoanKHDTO selectedTaiKhoan = gui.getSelectedTaiKhoanKH();
        if (selectedTaiKhoan != null) {
            currentDialog = new TaiKhoanKHDialog(taiKhoanBus, gui, gui, "Chi tiết tài khoản khách hàng", true, "view", selectedTaiKhoan, maNhomQuyen);
            gui.getSidebarPanel().btn.get("detail").setEnabled(false);
            currentDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    gui.getSidebarPanel().btn.get("detail").setEnabled(true);
                    currentDialog = null;
                }
            });
            currentDialog.setVisible(true);
        }
    }

    private void handleExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));

        if (fileChooser.showSaveDialog(gui) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getAbsolutePath().endsWith(".xlsx")) {
                file = new File(file.getAbsolutePath() + ".xlsx");
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("TaiKhoanKH");
                Row headerRow = sheet.createRow(0);
                String[] headers = {"STT", "Mã KH", "Tên đăng nhập", "Nhóm quyền", "Trạng thái"};
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }

                ArrayList<TaiKhoanKHDTO> list = taiKhoanBus.getTaiKhoanAllKH();
                int rowNum = 1;
                for (TaiKhoanKHDTO tk : list) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(rowNum - 1);
                    row.createCell(1).setCellValue(tk.getMaKhachHang());
                    row.createCell(2).setCellValue(tk.getTenDangNhap());
                    NhomQuyenDTO nhomQuyen = taiKhoanBus.getNhomQuyenDTO(tk.getMaNhomQuyen());
                    row.createCell(3).setCellValue(nhomQuyen != null ? nhomQuyen.getTEN() : "Không xác định");
                    String trangThai = switch (tk.getTrangThai()) {
                        case 1 -> "Hoạt động";
                        case 0 -> "Ngưng hoạt động";
                        case 2 -> "Chờ xử lý";
                        default -> "Không xác định";
                    };
                    row.createCell(4).setCellValue(trangThai);
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(gui, "Xuất file Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(gui, "Xuất file Excel thất bại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
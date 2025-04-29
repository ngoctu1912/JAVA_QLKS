// package GUI_DATPHONG.DatPhong;

// import BUS.DatPhongBUS;
// import Component.*;
// import DTO.DatPhongDTO;
// import javax.swing.*;
// import javax.swing.event.DocumentEvent;
// import javax.swing.event.DocumentListener;
// import javax.swing.table.DefaultTableModel;

// import java.awt.*;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import java.io.IOException;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.logging.Level;
// import java.util.logging.Logger;
// import helper.JTableExporter;

// public class DatPhongEventHandler {
//     private DatPhongFrame frame;
//     private DatPhongBUS datPhongBUS;
//     private ArrayList<DatPhongDTO> listDP;

//     public DatPhongEventHandler(DatPhongFrame frame, DatPhongBUS datPhongBUS, ArrayList<DatPhongDTO> listDP) {
//         this.frame = frame;
//         this.datPhongBUS = datPhongBUS;
//         this.listDP = listDP;
//     }

//     public void search() {
//         String keyword = frame.getSearch().txtSearchForm.getText().trim().toLowerCase();
//         String type = (String) frame.getSearch().cbxChoose.getSelectedItem();
//         ArrayList<DatPhongDTO> result = new ArrayList<>();

//         if (type.equals("Tất cả")) {
//             result = datPhongBUS.selectAll();
//             SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//             result.removeIf(dp -> {
//                 String maDP = dp.getMaDP().toLowerCase();
//                 String maKH = String.valueOf(dp.getMaKH());
//                 String ngayLapPhieu = dp.getNgayLapPhieu() != null ? dateFormat.format(dp.getNgayLapPhieu()).toLowerCase() : "";
//                 String tienDatCoc = String.valueOf(dp.getTienDatCoc());
//                 String tinhTrangXuLy = (dp.getTinhTrangXuLy() == 1 ? "đã xử lý" : "chưa xử lý").toLowerCase();
//                 String xuLy = (dp.getXuLy() == 1 ? "đã xử lý" : "chưa xử lý").toLowerCase();

//                 return !maDP.contains(keyword) &&
//                        !maKH.contains(keyword) &&
//                        !ngayLapPhieu.contains(keyword) &&
//                        !tienDatCoc.contains(keyword) &&
//                        !tinhTrangXuLy.contains(keyword) &&
//                        !xuLy.contains(keyword);
//             });
//         } else if (type.equals("Mã Đặt Phòng")) {
//             result = datPhongBUS.selectAll();
//             result.removeIf(dp -> !dp.getMaDP().toLowerCase().contains(keyword));
//         } else if (type.equals("Mã Khách Hàng")) {
//             result = datPhongBUS.selectAll();
//             result.removeIf(dp -> !String.valueOf(dp.getMaKH()).contains(keyword));
//         } else if (type.equals("Ngày Lập Phiếu")) {
//             result = datPhongBUS.selectAll();
//             SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//             result.removeIf(dp -> dp.getNgayLapPhieu() == null || !dateFormat.format(dp.getNgayLapPhieu()).toLowerCase().contains(keyword));
//         } else if (type.equals("Tiền Đặt Cọc")) {
//             result = datPhongBUS.selectAll();
//             result.removeIf(dp -> !String.valueOf(dp.getTienDatCoc()).contains(keyword));
//         } else if (type.equals("Tình Trạng Xử Lý")) {
//             result = datPhongBUS.selectAll();
//             if (keyword.contains("đã xử lý")) {
//                 result.removeIf(dp -> dp.getTinhTrangXuLy() != 1);
//             } else if (keyword.contains("chưa xử lý")) {
//                 result.removeIf(dp -> dp.getTinhTrangXuLy() != 0);
//             } else {
//                 result = datPhongBUS.selectAll();
//             }
//         } else if (type.equals("Xử Lý")) {
//             result = datPhongBUS.selectAll();
//             if (keyword.contains("đã xử lý")) {
//                 result.removeIf(dp -> dp.getXuLy() != 1);
//             } else if (keyword.contains("chưa xử lý")) {
//                 result.removeIf(dp -> dp.getXuLy() != 0);
//             } else {
//                 result = datPhongBUS.selectAll();
//             }
//         }

//         loadDataTable(result);
//     }

//     public void resetSearch() {
//         frame.getSearch().txtSearchForm.setText("");
//         frame.getSearch().cbxChoose.setSelectedIndex(0);
//         listDP = datPhongBUS.selectAll();
//         loadDataTable(listDP);
//     }

//     public void loadDataTable(ArrayList<DatPhongDTO> result) {
//         DefaultTableModel tblModel = frame.getTableModel();
//         tblModel.setRowCount(0);
//         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//         int stt = 1; // Thêm STT giống FormPhong
//         for (DatPhongDTO dp : result) {
//             tblModel.addRow(new Object[]{
//                     stt++, // STT
//                     dp.getMaDP(),
//                     dp.getMaKH(),
//                     dp.getNgayLapPhieu() != null ? dateFormat.format(dp.getNgayLapPhieu()) : "",
//                     dp.getTienDatCoc(),
//                     dp.getTinhTrangXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý",
//                     dp.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý"
//             });
//         }
//     }

//     public void handleAdd() {
//         JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(frame);
//         JDialog dialog = new JDialog(owner, "Thêm Đặt Phòng", true);
//         dialog.setSize(400, 400);
//         dialog.setLocationRelativeTo(owner);
//         dialog.setLayout(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);
//         gbc.fill = GridBagConstraints.HORIZONTAL;

//         // Input fields
//         InputForm maDPForm = new InputForm("Mã Đặt Phòng");
//         InputForm maKHForm = new InputForm("Mã Khách Hàng");
//         InputForm tienDatCocForm = new InputForm("Tiền Đặt Cọc");
//         SelectForm tinhTrangXuLyForm = new SelectForm("Tình Trạng Xử Lý", new String[]{"Chưa xử lý", "Đã xử lý"});
//         SelectForm xuLyForm = new SelectForm("Xử Lý", new String[]{"Chưa xử lý", "Đã xử lý"});

//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         dialog.add(maDPForm, gbc);
//         gbc.gridy = 1;
//         dialog.add(maKHForm, gbc);
//         gbc.gridy = 2;
//         dialog.add(tienDatCocForm, gbc);
//         gbc.gridy = 3;
//         dialog.add(tinhTrangXuLyForm, gbc);
//         gbc.gridy = 4;
//         dialog.add(xuLyForm, gbc);

//         // Buttons
//         ButtonCustom saveButton = new ButtonCustom("Lưu", "success", 14);
//         ButtonCustom cancelButton = new ButtonCustom("Hủy", "danger", 14);
//         JPanel buttonPanel = new JPanel(new FlowLayout());
//         buttonPanel.add(saveButton);
//         buttonPanel.add(cancelButton);
//         gbc.gridy = 5;
//         dialog.add(buttonPanel, gbc);

//         saveButton.addActionListener(e -> {
//             try {
//                 DatPhongDTO dp = new DatPhongDTO();
//                 dp.setMaDP(maDPForm.getText());
//                 dp.setMaKH(Integer.parseInt(maKHForm.getText()));
//                 dp.setTienDatCoc(Integer.parseInt(tienDatCocForm.getText()));
//                 dp.setTinhTrangXuLy(tinhTrangXuLyForm.getValue().equals("Đã xử lý") ? 1 : 0);
//                 dp.setXuLy(xuLyForm.getValue().equals("Đã xử lý") ? 1 : 0);
//                 dp.setNgayLapPhieu(new java.util.Date());

//                 if (datPhongBUS.checkExists(dp.getMaDP())) {
//                     JOptionPane.showMessageDialog(dialog, "Mã đặt phòng đã tồn tại!");
//                     return;
//                 }

//                 int result = datPhongBUS.add(dp);
//                 if (result > 0) {
//                     JOptionPane.showMessageDialog(dialog, "Thêm đặt phòng thành công!");
//                     listDP = datPhongBUS.selectAll();
//                     frame.setListDP(listDP);
//                     loadDataTable(listDP);
//                     dialog.dispose();
//                 } else {
//                     JOptionPane.showMessageDialog(dialog, "Thêm đặt phòng thất bại!");
//                 }
//             } catch (Exception ex) {
//                 JOptionPane.showMessageDialog(dialog, "Dữ liệu không hợp lệ: " + ex.getMessage());
//             }
//         });

//         cancelButton.addActionListener(e -> dialog.dispose());
//         dialog.setVisible(true);
//     }

//     public void handleEdit() {
//         int index = getRowSelected();
//         if (index == -1) return;

//         DatPhongDTO dp = listDP.get(index);
//         JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(frame);
//         JDialog dialog = new JDialog(owner, "Sửa Đặt Phòng", true);
//         dialog.setSize(400, 400);
//         dialog.setLocationRelativeTo(owner);
//         dialog.setLayout(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);
//         gbc.fill = GridBagConstraints.HORIZONTAL;

//         // Input fields
//         InputForm maDPForm = new InputForm("Mã Đặt Phòng");
//         maDPForm.setText(dp.getMaDP());
//         maDPForm.setEditable(false);
//         InputForm maKHForm = new InputForm("Mã Khách Hàng");
//         maKHForm.setText(String.valueOf(dp.getMaKH()));
//         InputForm tienDatCocForm = new InputForm("Tiền Đặt Cọc");
//         tienDatCocForm.setText(String.valueOf(dp.getTienDatCoc()));
//         SelectForm tinhTrangXuLyForm = new SelectForm("Tình Trạng Xử Lý", new String[]{"Chưa xử lý", "Đã xử lý"});
//         tinhTrangXuLyForm.setSelectedItem(dp.getTinhTrangXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý");
//         SelectForm xuLyForm = new SelectForm("Xử Lý", new String[]{"Chưa xử lý", "Đã xử lý"});
//         xuLyForm.setSelectedItem(dp.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý");

//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         dialog.add(maDPForm, gbc);
//         gbc.gridy = 1;
//         dialog.add(maKHForm, gbc);
//         gbc.gridy = 2;
//         dialog.add(tienDatCocForm, gbc);
//         gbc.gridy = 3;
//         dialog.add(tinhTrangXuLyForm, gbc);
//         gbc.gridy = 4;
//         dialog.add(xuLyForm, gbc);

//         // Buttons
//         ButtonCustom saveButton = new ButtonCustom("Lưu", "success", 14);
//         ButtonCustom cancelButton = new ButtonCustom("Hủy", "danger", 14);
//         JPanel buttonPanel = new JPanel(new FlowLayout());
//         buttonPanel.add(saveButton);
//         buttonPanel.add(cancelButton);
//         gbc.gridy = 5;
//         dialog.add(buttonPanel, gbc);

//         saveButton.addActionListener(e -> {
//             try {
//                 dp.setMaKH(Integer.parseInt(maKHForm.getText()));
//                 dp.setTienDatCoc(Integer.parseInt(tienDatCocForm.getText()));
//                 dp.setTinhTrangXuLy(tinhTrangXuLyForm.getValue().equals("Đã xử lý") ? 1 : 0);
//                 dp.setXuLy(xuLyForm.getValue().equals("Đã xử lý") ? 1 : 0);

//                 int result = datPhongBUS.update(dp);
//                 if (result > 0) {
//                     JOptionPane.showMessageDialog(dialog, "Sửa đặt phòng thành công!");
//                     listDP = datPhongBUS.selectAll();
//                     frame.setListDP(listDP);
//                     loadDataTable(listDP);
//                     dialog.dispose();
//                 } else {
//                     JOptionPane.showMessageDialog(dialog, "Sửa đặt phòng thất bại!");
//                 }
//             } catch (Exception ex) {
//                 JOptionPane.showMessageDialog(dialog, "Dữ liệu không hợp lệ: " + ex.getMessage());
//             }
//         });

//         cancelButton.addActionListener(e -> dialog.dispose());
//         dialog.setVisible(true);
//     }

//     public void handleDelete() {
//         int index = getRowSelected();
//         if (index == -1) return;

//         DatPhongDTO dp = listDP.get(index);
//         int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa đặt phòng " + dp.getMaDP() + "?",
//                 "Xóa đặt phòng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
//         if (input == 0) {
//             int result = datPhongBUS.delete(dp.getMaDP());
//             if (result > 0) {
//                 JOptionPane.showMessageDialog(frame, "Xóa đặt phòng thành công!");
//                 listDP = datPhongBUS.selectAll();
//                 frame.setListDP(listDP);
//                 loadDataTable(listDP);
//             } else {
//                 JOptionPane.showMessageDialog(frame, "Xóa đặt phòng thất bại!");
//             }
//         }
//     }

//     public void handleDetails() {
//         int index = getRowSelected();
//         if (index == -1) return;

//         DatPhongDTO dp = listDP.get(index);
//         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

//         String details = String.format(
//                 "Mã Đặt Phòng: %s\nMã Khách Hàng: %d\nNgày Lập Phiếu: %s\nTiền Đặt Cọc: %d\nTình Trạng Xử Lý: %s\nXử Lý: %s",
//                 dp.getMaDP(),
//                 dp.getMaKH(),
//                 dp.getNgayLapPhieu() != null ? dateFormat.format(dp.getNgayLapPhieu()) : "N/A",
//                 dp.getTienDatCoc(),
//                 dp.getTinhTrangXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý",
//                 dp.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý");

//         JOptionPane.showMessageDialog(frame, details, "Chi Tiết Đặt Phòng", JOptionPane.INFORMATION_MESSAGE);
//     }

//     public void handleExport() {
//         try {
//             JTableExporter.exportJTableToExcel(frame.getTableDatPhong());
//         } catch (IOException ex) {
//             Logger.getLogger(DatPhongFrame.class.getName()).log(Level.SEVERE, null, ex);
//         }
//     }

//     public int getRowSelected() {
//         int index = frame.getTableDatPhong().getSelectedRow();
//         if (index == -1) {
//             JOptionPane.showMessageDialog(frame, "Vui lòng chọn đặt phòng");
//         }
//         return index;
//     }

//     public MouseAdapter getDoubleClickListener() {
//         return new MouseAdapter() {
//             @Override
//             public void mouseClicked(MouseEvent e) {
//                 int row = frame.getTableDatPhong().rowAtPoint(e.getPoint());
//                 if (row >= 0) {
//                     frame.getTableDatPhong().setRowSelectionInterval(row, row);
//                     if (e.getClickCount() == 2) {
//                         handleDetails();
//                     }
//                 }
//             }
//         };
//     }
// }

package GUI_DATPHONG.DatPhong;

import BUS.DatPhongBUS;
import BUS.KhachHangBUS; // Thêm import
import Component.*;
import DTO.DatPhongDTO;
import DTO.KhachHangDTO; // Thêm import
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import helper.JTableExporter;

public class DatPhongEventHandler {
    private DatPhongFrame frame;
    private DatPhongBUS datPhongBUS;
    private KhachHangBUS khachHangBUS; // Thêm để lấy tenKH
    private ArrayList<DatPhongDTO> listDP;

    public DatPhongEventHandler(DatPhongFrame frame, DatPhongBUS datPhongBUS, ArrayList<DatPhongDTO> listDP) {
        this.frame = frame;
        this.datPhongBUS = datPhongBUS;
        this.khachHangBUS = new KhachHangBUS(); // Khởi tạo
        this.listDP = listDP;
    }

    public void search() {
        String keyword = frame.getSearch().txtSearchForm.getText().trim().toLowerCase();
        String type = (String) frame.getSearch().cbxChoose.getSelectedItem();
        ArrayList<DatPhongDTO> result = new ArrayList<>();

        if (type.equals("Tất cả")) {
            result = datPhongBUS.selectAll();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            result.removeIf(dp -> {
                String maDP = dp.getMaDP().toLowerCase();
                KhachHangDTO kh = khachHangBUS.selectById(String.valueOf(dp.getMaKH()));
                String tenKH = kh != null ? kh.getTenKhachHang().toLowerCase() : "";
                String ngayLapPhieu = dp.getNgayLapPhieu() != null ? dateFormat.format(dp.getNgayLapPhieu()).toLowerCase() : "";
                String tienDatCoc = String.valueOf(dp.getTienDatCoc());
                String tinhTrangXuLy = (dp.getTinhTrangXuLy() == 1 ? "đã xử lý" : "chưa xử lý").toLowerCase();
                String xuLy = (dp.getXuLy() == 1 ? "đã xử lý" : "chưa xử lý").toLowerCase();

                return !maDP.contains(keyword) &&
                       !tenKH.contains(keyword) &&
                       !ngayLapPhieu.contains(keyword) &&
                       !tienDatCoc.contains(keyword) &&
                       !tinhTrangXuLy.contains(keyword) &&
                       !xuLy.contains(keyword);
            });
        } else if (type.equals("Mã Đặt Phòng")) {
            result = datPhongBUS.selectAll();
            result.removeIf(dp -> !dp.getMaDP().toLowerCase().contains(keyword));
        } else if (type.equals("Tên Khách Hàng")) {
            result = datPhongBUS.selectAll();
            result.removeIf(dp -> {
                KhachHangDTO kh = khachHangBUS.selectById(String.valueOf(dp.getMaKH()));
                return kh == null || !kh.getTenKhachHang().toLowerCase().contains(keyword);
            });
        } else if (type.equals("Ngày Lập Phiếu")) {
            result = datPhongBUS.selectAll();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            result.removeIf(dp -> dp.getNgayLapPhieu() == null || !dateFormat.format(dp.getNgayLapPhieu()).toLowerCase().contains(keyword));
        } else if (type.equals("Tiền Đặt Cọc")) {
            result = datPhongBUS.selectAll();
            result.removeIf(dp -> !String.valueOf(dp.getTienDatCoc()).contains(keyword));
        } else if (type.equals("Tình Trạng Xử Lý")) {
            result = datPhongBUS.selectAll();
            if (keyword.contains("đã xử lý")) {
                result.removeIf(dp -> dp.getTinhTrangXuLy() != 1);
            } else if (keyword.contains("chưa xử lý")) {
                result.removeIf(dp -> dp.getTinhTrangXuLy() != 0);
            } else {
                result = datPhongBUS.selectAll();
            }
        } else if (type.equals("Xử Lý")) {
            result = datPhongBUS.selectAll();
            if (keyword.contains("đã xử lý")) {
                result.removeIf(dp -> dp.getXuLy() != 1);
            } else if (keyword.contains("chưa xử lý")) {
                result.removeIf(dp -> dp.getXuLy() != 0);
            } else {
                result = datPhongBUS.selectAll();
            }
        }

        loadDataTable(result);
    }

    public void resetSearch() {
        frame.getSearch().txtSearchForm.setText("");
        frame.getSearch().cbxChoose.setSelectedIndex(0);
        listDP = datPhongBUS.selectAll();
        loadDataTable(listDP);
    }

    public void loadDataTable(ArrayList<DatPhongDTO> result) {
        DefaultTableModel tblModel = frame.getTableModel();
        tblModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        int stt = 1;
        for (DatPhongDTO dp : result) {
            KhachHangDTO kh = khachHangBUS.selectById(String.valueOf(dp.getMaKH()));
            String tenKH = kh != null ? kh.getTenKhachHang() : "N/A";
            System.out.println("Debug: Displaying maDP=" + dp.getMaDP() + ", tenKH=" + tenKH); // Debug log
            tblModel.addRow(new Object[]{
                    stt++,
                    dp.getMaDP(),
                    tenKH, // Hiển thị tenKH thay vì maKH
                    dp.getNgayLapPhieu() != null ? dateFormat.format(dp.getNgayLapPhieu()) : "",
                    dp.getTienDatCoc(),
                    dp.getTinhTrangXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý",
                    dp.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý"
            });
        }
    }

    public void handleAdd() {
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(frame);
        JDialog dialog = new JDialog(owner, "Thêm Đặt Phòng", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(owner);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        InputForm maDPForm = new InputForm("Mã Đặt Phòng");
        InputForm maKHForm = new InputForm("Mã Khách Hàng");
        InputForm tienDatCocForm = new InputForm("Tiền Đặt Cọc");
        SelectForm tinhTrangXuLyForm = new SelectForm("Tình Trạng Xử Lý", new String[]{"Chưa xử lý", "Đã xử lý"});
        SelectForm xuLyForm = new SelectForm("Xử Lý", new String[]{"Chưa xử lý", "Đã xử lý"});

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(maDPForm, gbc);
        gbc.gridy = 1;
        dialog.add(maKHForm, gbc);
        gbc.gridy = 2;
        dialog.add(tienDatCocForm, gbc);
        gbc.gridy = 3;
        dialog.add(tinhTrangXuLyForm, gbc);
        gbc.gridy = 4;
        dialog.add(xuLyForm, gbc);

        ButtonCustom saveButton = new ButtonCustom("Lưu", "success", 14);
        ButtonCustom cancelButton = new ButtonCustom("Hủy", "danger", 14);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        gbc.gridy = 5;
        dialog.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            try {
                DatPhongDTO dp = new DatPhongDTO();
                dp.setMaDP(maDPForm.getText());
                dp.setMaKH(Integer.parseInt(maKHForm.getText()));
                dp.setTienDatCoc(Integer.parseInt(tienDatCocForm.getText()));
                dp.setTinhTrangXuLy(tinhTrangXuLyForm.getValue().equals("Đã xử lý") ? 1 : 0);
                dp.setXuLy(xuLyForm.getValue().equals("Đã xử lý") ? 1 : 0);
                dp.setNgayLapPhieu(new java.util.Date());

                if (datPhongBUS.checkExists(dp.getMaDP())) {
                    JOptionPane.showMessageDialog(dialog, "Mã đặt phòng đã tồn tại!");
                    return;
                }

                int result = datPhongBUS.add(dp);
                if (result > 0) {
                    JOptionPane.showMessageDialog(dialog, "Thêm đặt phòng thành công!");
                    listDP = datPhongBUS.selectAll();
                    frame.setListDP(listDP);
                    loadDataTable(listDP);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm đặt phòng thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Dữ liệu không hợp lệ: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public void handleEdit() {
        int index = getRowSelected();
        if (index == -1) return;

        DatPhongDTO dp = listDP.get(index);
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(frame);
        JDialog dialog = new JDialog(owner, "Sửa Đặt Phòng", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(owner);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        InputForm maDPForm = new InputForm("Mã Đặt Phòng");
        maDPForm.setText(dp.getMaDP());
        maDPForm.setEditable(false);
        InputForm maKHForm = new InputForm("Mã Khách Hàng");
        maKHForm.setText(String.valueOf(dp.getMaKH()));
        InputForm tienDatCocForm = new InputForm("Tiền Đặt Cọc");
        tienDatCocForm.setText(String.valueOf(dp.getTienDatCoc()));
        SelectForm tinhTrangXuLyForm = new SelectForm("Tình Trạng Xử Lý", new String[]{"Chưa xử lý", "Đã xử lý"});
        tinhTrangXuLyForm.setSelectedItem(dp.getTinhTrangXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý");
        SelectForm xuLyForm = new SelectForm("Xử Lý", new String[]{"Chưa xử lý", "Đã xử lý"});
        xuLyForm.setSelectedItem(dp.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý");

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(maDPForm, gbc);
        gbc.gridy = 1;
        dialog.add(maKHForm, gbc);
        gbc.gridy = 2;
        dialog.add(tienDatCocForm, gbc);
        gbc.gridy = 3;
        dialog.add(tinhTrangXuLyForm, gbc);
        gbc.gridy = 4;
        dialog.add(xuLyForm, gbc);

        ButtonCustom saveButton = new ButtonCustom("Lưu", "success", 14);
        ButtonCustom cancelButton = new ButtonCustom("Hủy", "danger", 14);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        gbc.gridy = 5;
        dialog.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            try {
                dp.setMaKH(Integer.parseInt(maKHForm.getText()));
                dp.setTienDatCoc(Integer.parseInt(tienDatCocForm.getText()));
                dp.setTinhTrangXuLy(tinhTrangXuLyForm.getValue().equals("Đã xử lý") ? 1 : 0);
                dp.setXuLy(xuLyForm.getValue().equals("Đã xử lý") ? 1 : 0);

                int result = datPhongBUS.update(dp);
                if (result > 0) {
                    JOptionPane.showMessageDialog(dialog, "Sửa đặt phòng thành công!");
                    listDP = datPhongBUS.selectAll();
                    frame.setListDP(listDP);
                    loadDataTable(listDP);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Sửa đặt phòng thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Dữ liệu không hợp lệ: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public void handleDelete() {
        int index = getRowSelected();
        if (index == -1) return;

        DatPhongDTO dp = listDP.get(index);
        int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa đặt phòng " + dp.getMaDP() + "?",
                "Xóa đặt phòng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            int result = datPhongBUS.delete(dp.getMaDP());
            if (result > 0) {
                JOptionPane.showMessageDialog(frame, "Xóa đặt phòng thành công!");
                listDP = datPhongBUS.selectAll();
                frame.setListDP(listDP);
                loadDataTable(listDP);
            } else {
                JOptionPane.showMessageDialog(frame, "Xóa đặt phòng thất bại!");
            }
        }
    }

    public void handleDetails() {
        int index = getRowSelected();
        if (index == -1) return;

        DatPhongDTO dp = listDP.get(index);
        KhachHangDTO kh = khachHangBUS.selectById(String.valueOf(dp.getMaKH()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String details = String.format(
                "Mã Đặt Phòng: %s\nTên Khách Hàng: %s\nNgày Lập Phiếu: %s\nTiền Đặt Cọc: %d\nTình Trạng Xử Lý: %s\nXử Lý: %s",
                dp.getMaDP(),
                kh != null ? kh.getTenKhachHang() : "N/A", // Hiển thị tenKH
                dp.getNgayLapPhieu() != null ? dateFormat.format(dp.getNgayLapPhieu()) : "N/A",
                dp.getTienDatCoc(),
                dp.getTinhTrangXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý",
                dp.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý");

        JOptionPane.showMessageDialog(frame, details, "Chi Tiết Đặt Phòng", JOptionPane.INFORMATION_MESSAGE);
    }

    public void handleExport() {
        try {
            JTableExporter.exportJTableToExcel(frame.getTableDatPhong());
        } catch (IOException ex) {
            Logger.getLogger(DatPhongFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getRowSelected() {
        int index = frame.getTableDatPhong().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn đặt phòng");
        }
        return index;
    }

    public MouseAdapter getDoubleClickListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = frame.getTableDatPhong().rowAtPoint(e.getPoint());
                if (row >= 0) {
                    frame.getTableDatPhong().setRowSelectionInterval(row, row);
                    if (e.getClickCount() == 2) {
                        handleDetails();
                    }
                }
            }
        };
    }
}
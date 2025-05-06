package GUI_DATPHONG.DatPhong;

import BUS.DatPhongBUS;
import BUS.KhachHangBUS;
import Component.*;
import DTO.DatPhongDTO;
import DTO.KhachHangDTO;
import GUI_DATPHONG.FindRoom;

import javax.swing.*;
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
    private KhachHangBUS khachHangBUS;
    private ArrayList<DatPhongDTO> listDP;

    public DatPhongEventHandler(DatPhongFrame frame, DatPhongBUS datPhongBUS, ArrayList<DatPhongDTO> listDP) {
        this.frame = frame;
        this.datPhongBUS = datPhongBUS;
        this.khachHangBUS = new KhachHangBUS();
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
                String ngayLapPhieu = dp.getNgayLapPhieu() != null
                        ? dateFormat.format(dp.getNgayLapPhieu()).toLowerCase()
                        : "";
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
            result.removeIf(dp -> dp.getNgayLapPhieu() == null
                    || !dateFormat.format(dp.getNgayLapPhieu()).toLowerCase().contains(keyword));
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
            tblModel.addRow(new Object[]{
                    stt++,
                    dp.getMaDP(),
                    tenKH,
                    dp.getNgayLapPhieu() != null ? dateFormat.format(dp.getNgayLapPhieu()) : "",
                    dp.getTienDatCoc(),
                    dp.getTinhTrangXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý",
                    dp.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý"
            });
        }
    }

    public void handleAdd() {
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(frame);
        JDialog dialog = new JDialog(owner, "Tìm Phòng", true);
        dialog.setSize(1150, 600);
        dialog.setLocationRelativeTo(owner);
        dialog.setLayout(new BorderLayout());

        // Create and add FindRoom panel
        FindRoom findRoomPanel = new FindRoom();
        dialog.add(findRoomPanel, BorderLayout.CENTER);

        // Add a close button at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ButtonCustom closeButton = new ButtonCustom("Đóng", "danger", 14);
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    public void handleEdit() {
        int index = getRowSelected();
        if (index == -1) return;

        DatPhongDTO dp = listDP.get(index);
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(frame);
        DatPhongDialog dialog = new DatPhongDialog(owner, dp, true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                listDP = datPhongBUS.selectAll();
                frame.setListDP(listDP);
                loadDataTable(listDP);
            }
        });
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
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(frame);
        new DatPhongDialog(owner, dp, false);
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
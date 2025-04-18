package GUI_KIEMKETIENICH;

import BUS.TienIchBUS;
import DTO.TienIchDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class KKTienIchEvent {
    private KKTienIchComponent component;
    private TienIchBUS tienIchBUS;

    public KKTienIchEvent(KKTienIchComponent component) {
        this.component = component;
        this.tienIchBUS = new TienIchBUS();
        initEvents();
        loadTienIchData();
    }

    private void initEvents() {
        component.btnThem.addActionListener(e -> themTienIch());
        component.btnCapNhat.addActionListener(e -> capNhatTienIch());
        component.btnXoa.addActionListener(e -> xoaTienIch());
        component.btnLamMoi.addActionListener(e -> loadTienIchData());
        component.btnChiTiet.addActionListener(e -> showChiTietTienIch());
        component.btnQuayLai.addActionListener(e -> showTienIchTable());
        component.btnTimKiemPhong.addActionListener(e -> timKiemPhong());
        component.btnLamMoiChiTiet.addActionListener(e -> lamMoiChiTiet());

        component.getTxtMaPhieu().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    kiemKe();
                }
            }
        });

        component.getTxtMaPhong().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKiemPhong();
                }
            }
        });
    }

    private void loadTienIchData() {
        DefaultTableModel model = (DefaultTableModel) component.getTienIchTable().getModel();
        model.setRowCount(0);
        try {
            List<TienIchDTO> tienIchList = tienIchBUS.getAll();
            for (TienIchDTO ti : tienIchList) {
                model.addRow(new Object[]{
                    ti.getMaTI(),
                    ti.getTenTI(),
                    ti.getTotalQuantity(),
                    ti.getRemainingQuantity(),
                    ti.getXuLy() == 1 ? "Hoạt động" : "Ngừng"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(component, "Lỗi tải dữ liệu tiện ích: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void themTienIch() {
        JTextField maTIField = new JTextField();
        JTextField tenTIField = new JTextField();
        JTextField totalQuantityField = new JTextField();
        Object[] fields = {
            "Mã tiện ích:", maTIField,
            "Tên tiện ích:", tenTIField,
            "Số lượng tổng:", totalQuantityField
        };
        int option = JOptionPane.showConfirmDialog(component, fields, "Thêm tiện ích", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String maTI = maTIField.getText();
            String tenTI = tenTIField.getText();
            int totalQuantity;
            try {
                totalQuantity = Integer.parseInt(totalQuantityField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(component, "Số lượng phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            TienIchDTO ti = new TienIchDTO(maTI, tenTI, totalQuantity, 1);
            try {
                tienIchBUS.add(ti);
                loadTienIchData();
                JOptionPane.showMessageDialog(component, "Thêm tiện ích thành công!");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(component, "Lỗi thêm tiện ích: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void capNhatTienIch() {
        int selectedRow = component.getTienIchTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(component, "Vui lòng chọn một tiện ích!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maTI = component.getTienIchTable().getValueAt(selectedRow, 0).toString();
        String tenTI = component.getTienIchTable().getValueAt(selectedRow, 1).toString();
        String totalQuantityStr = component.getTienIchTable().getValueAt(selectedRow, 2).toString();
        String xuLyStr = component.getTienIchTable().getValueAt(selectedRow, 4).toString();

        JTextField tenTIField = new JTextField(tenTI);
        JTextField totalQuantityField = new JTextField(totalQuantityStr);
        JComboBox<String> xuLyCombo = new JComboBox<>(new String[]{"Hoạt động", "Ngừng"});
        xuLyCombo.setSelectedItem(xuLyStr);
        Object[] fields = {
            "Tên tiện ích:", tenTIField,
            "Số lượng tổng:", totalQuantityField,
            "Trạng thái:", xuLyCombo
        };
        int option = JOptionPane.showConfirmDialog(component, fields, "Cập nhật tiện ích", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newTenTI = tenTIField.getText();
            int newTotalQuantity;
            try {
                newTotalQuantity = Integer.parseInt(totalQuantityField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(component, "Số lượng phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int newXuLy = xuLyCombo.getSelectedItem().equals("Hoạt động") ? 1 : 0;
            TienIchDTO ti = new TienIchDTO(maTI, newTenTI, newTotalQuantity, newXuLy);
            try {
                tienIchBUS.update(ti);
                loadTienIchData();
                JOptionPane.showMessageDialog(component, "Cập nhật tiện ích thành công!");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(component, "Lỗi cập nhật tiện ích: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xoaTienIch() {
        int selectedRow = component.getTienIchTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(component, "Vui lòng chọn một tiện ích!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maTI = component.getTienIchTable().getValueAt(selectedRow, 0).toString();
        int option = JOptionPane.showConfirmDialog(component, "Bạn có chắc muốn xóa tiện ích " + maTI + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                tienIchBUS.delete(maTI);
                loadTienIchData();
                JOptionPane.showMessageDialog(component, "Xóa tiện ích thành công!");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(component, "Lỗi xóa tiện ích: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void kiemKe() {
        String maTI = component.getTxtMaPhieu().getText();
        if (maTI == null || maTI.trim().isEmpty()) {
            JOptionPane.showMessageDialog(component, "Vui lòng nhập mã tiện ích!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            TienIchDTO ti = tienIchBUS.getById(maTI);
            if (ti == null) {
                JOptionPane.showMessageDialog(component, "Tiện ích không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Hiển thị dialog chi tiết tiện ích
            TienIchDetailDialog dialog = new TienIchDetailDialog(null, ti);
            dialog.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(component, "Lỗi kiểm kê: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showChiTietTienIch() {
        try {
            // Ẩn bảng tiện ích và hiển thị bảng chi tiết
            component.getTienIchScrollPane().setVisible(false);
            component.getChiTietScrollPane().setVisible(true);
            component.getLblTableTitle().setText("Chi tiết tiện ích theo phòng");
            component.btnThem.setVisible(false);
            component.btnCapNhat.setVisible(false);
            component.btnXoa.setVisible(false);
            component.btnChiTiet.setVisible(false);
            component.btnQuayLai.setVisible(true);
            component.btnLamMoi.setVisible(false);
            component.btnTimKiemPhong.setVisible(true);
            component.btnLamMoiChiTiet.setVisible(true);
            component.getTxtMaPhieu().setVisible(false);
            component.getTxtMaPhong().setVisible(true);
            component.filterPanel.getComponent(0).setVisible(false); // Ẩn JLabel "Mã tiện ích"
            component.filterPanel.getComponent(2).setVisible(true); // Hiển thị JLabel "Mã phòng"

            // Load dữ liệu vào bảng chi tiết
            loadChiTietData(null);

            component.revalidate();
            component.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(component, "Lỗi tải chi tiết tiện ích: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showTienIchTable() {
        // Hiển thị lại bảng tiện ích và ẩn bảng chi tiết
        component.getTienIchScrollPane().setVisible(true);
        component.getChiTietScrollPane().setVisible(false);
        component.getLblTableTitle().setText("Tiện ích");
        component.btnThem.setVisible(true);
        component.btnCapNhat.setVisible(true);
        component.btnXoa.setVisible(true);
        component.btnChiTiet.setVisible(true);
        component.btnQuayLai.setVisible(false);
        component.btnLamMoi.setVisible(true);
        component.btnTimKiemPhong.setVisible(false);
        component.btnLamMoiChiTiet.setVisible(false);
        component.getTxtMaPhieu().setVisible(true);
        component.getTxtMaPhong().setVisible(false);
        component.filterPanel.getComponent(0).setVisible(true); // Hiển thị JLabel "Mã tiện ích"
        component.filterPanel.getComponent(2).setVisible(false); // Ẩn JLabel "Mã phòng"

        loadTienIchData();
        component.revalidate();
        component.repaint();
    }

    private void timKiemPhong() {
        String maPhong = component.getTxtMaPhong().getText().trim();
        if (maPhong.isEmpty()) {
            JOptionPane.showMessageDialog(component, "Vui lòng nhập mã phòng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            loadChiTietData(maPhong);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(component, "Lỗi tìm kiếm phòng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lamMoiChiTiet() {
        try {
            component.getTxtMaPhong().setText("");
            loadChiTietData(null);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(component, "Lỗi làm mới bảng chi tiết: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadChiTietData(String maPhong) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) component.getChiTietTable().getModel();
        model.setRowCount(0);
        List<Object[]> details = maPhong == null ? tienIchBUS.getTienIchDetailsByRoom() : tienIchBUS.getTienIchDetailsByRoom(maPhong);
        for (Object[] row : details) {
            model.addRow(row);
        }
        if (details.isEmpty() && maPhong != null) {
            JOptionPane.showMessageDialog(component, "Không tìm thấy phòng hoặc phòng không có tiện ích!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
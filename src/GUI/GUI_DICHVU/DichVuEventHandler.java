package GUI_DICHVU;

import BUS.DichVuBUS;
import DTO.DichVuDTO;
import helper.JTableExporter;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class DichVuEventHandler {
    private FormDichVu form;
    private DichVuBUS dichVuBUS;

    public DichVuEventHandler(FormDichVu form) {
        this.form = form;
        this.dichVuBUS = form.getDichVuBUS();
        // System.out.println("DichVuEventHandler initialized");

        form.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = form.getTable().getSelectedRow();
                    if (row >= 0) {
                        String maDV = form.getTable().getValueAt(row, 1).toString();
                        DichVuDTO dichVu = dichVuBUS.getDichVuById(maDV);
                        // System.out.println("Double-click: maDV=" + maDV + ", dichVu=" + (dichVu != null ? dichVu.getMaDV() : "null"));
                        if (dichVu != null) {
                            openDetailDialog(dichVu); // Gọi hàm chi tiết (sẽ xử lý sau)
                        } else {
                            JOptionPane.showMessageDialog(form, "Không tìm thấy dịch vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        form.getSidebarPanel().btn.get("create").addActionListener(e -> openAddDialog());
        form.getSidebarPanel().btn.get("update").addActionListener(e -> openEditDialog());
        form.getSidebarPanel().btn.get("delete").addActionListener(e -> deleteDichVu());
        form.getSidebarPanel().btn.get("detail").addActionListener(e -> {
            int row = form.getTable().getSelectedRow();
            if (row >= 0) {
                String maDV = (String) form.getTable().getValueAt(row, 1);
                DichVuDTO dichVu = dichVuBUS.getDichVuById(maDV);
                if (dichVu != null) {
                    openDetailDialog(dichVu);
                }
            }
        });
        form.getSidebarPanel().btn.get("export").addActionListener(e -> exportToExcel());
    }

    public void openAddDialog() {
        // System.out.println("openAddDialog called");
        DichVuComponents components = new DichVuComponents(form.getOwner());
        components.getTxtMaDV().setText("DV" + String.format("%03d", dichVuBUS.getAutoIncrement()));
        components.getBtnOK().addActionListener(e -> {
            if (validateInput(components)) {
                DichVuDTO newDichVu = getDichVuFromComponents(components);
                if (dichVuBUS.addDichVu(newDichVu) > 0) {
                    JOptionPane.showMessageDialog(form, "Thêm dịch vụ thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    form.loadTableData();
                    components.getDialog().dispose();
                } else {
                    JOptionPane.showMessageDialog(form, "Thêm dịch vụ thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        components.showDialog();
    }

    public void openEditDialog() {
        int row = form.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(form, "Vui lòng chọn một dịch vụ để chỉnh sửa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maDV = (String) form.getTable().getValueAt(row, 1);
        DichVuDTO dichVu = dichVuBUS.getDichVuById(maDV);
        // System.out.println("openEditDialog: maDV=" + maDV + ", dichVu=" + (dichVu != null ? dichVu.getMaDV() : "null"));
        if (dichVu == null) {
            JOptionPane.showMessageDialog(form, "Không tìm thấy dịch vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DichVuComponents components = new DichVuComponents(form.getOwner());
        components.getTxtMaDV().setText(dichVu.getMaDV());
        components.getTxtTenDV().setText(dichVu.getTenDV());
        components.getTxtLoaiDV().setText(dichVu.getLoaiDV());
        components.getTxtSoLuong().setText(String.valueOf(dichVu.getSoLuong()));
        components.getTxtGiaDV().setText(String.valueOf(dichVu.getGiaDV()));
        components.getCbXuLy().setSelectedItem(dichVu.getXuLy() == 1 ? "1 - Đã xử lý" : "0 - Chưa xử lý");

        components.getBtnOK().addActionListener(e -> {
            if (validateInput(components)) {
                DichVuDTO updatedDichVu = getDichVuFromComponents(components);
                if (dichVuBUS.updateDichVu(updatedDichVu) > 0) {
                    JOptionPane.showMessageDialog(form, "Cập nhật dịch vụ thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    form.loadTableData();
                    components.getDialog().dispose();
                } else {
                    JOptionPane.showMessageDialog(form, "Cập nhật dịch vụ thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        components.showDialog();
    }

    private boolean validateInput(DichVuComponents components) {
        if (components.getTxtTenDV().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(form, "Tên dịch vụ không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (components.getTxtLoaiDV().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(form, "Loại dịch vụ không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int soLuong = Integer.parseInt(components.getTxtSoLuong().getText());
            if (soLuong < 0) {
                JOptionPane.showMessageDialog(form, "Số lượng phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(form, "Số lượng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int giaDV = Integer.parseInt(components.getTxtGiaDV().getText());
            if (giaDV < 0) {
                JOptionPane.showMessageDialog(form, "Giá dịch vụ phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(form, "Giá dịch vụ phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private DichVuDTO getDichVuFromComponents(DichVuComponents components) {
        String maDV = components.getTxtMaDV().getText();
        String tenDV = components.getTxtTenDV().getText();
        String loaiDV = components.getTxtLoaiDV().getText();
        int soLuong = Integer.parseInt(components.getTxtSoLuong().getText());
        int giaDV = Integer.parseInt(components.getTxtGiaDV().getText());
        int xuLy = components.getCbXuLy().getSelectedItem().equals("1 - Đã xử lý") ? 1 : 0;
        return new DichVuDTO(maDV, tenDV, loaiDV, soLuong, giaDV, xuLy);
    }

    public void deleteDichVu() {
        int row = form.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(form, "Vui lòng chọn một dịch vụ để xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(form, "Bạn có chắc chắn muốn xóa dịch vụ này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String maDV = (String) form.getTable().getValueAt(row, 1);
            try {
                int rowsAffected = dichVuBUS.deleteDichVu(maDV);
                if (rowsAffected > 0) {
                    form.loadTableData();
                    JOptionPane.showMessageDialog(form, "Xóa dịch vụ thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(form, "Xóa dịch vụ thất bại! Dịch vụ không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(form, "Lỗi khi xóa dịch vụ: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void openDetailDialog(DichVuDTO dichVu) {
        // Tạm thời hiển thị thông tin bằng JOptionPane để tránh lỗi với DichVuDetailDialog
        String info = String.format("Mã DV: %s\nTên DV: %s\nLoại DV: %s\nSố lượng: %d\nGiá DV: %d\nXử lý: %s",
                dichVu.getMaDV(), dichVu.getTenDV(), dichVu.getLoaiDV(), dichVu.getSoLuong(), dichVu.getGiaDV(),
                dichVu.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý");
        JOptionPane.showMessageDialog(form, info, "Chi tiết dịch vụ", JOptionPane.INFORMATION_MESSAGE);
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
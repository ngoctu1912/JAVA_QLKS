package GUI_DICHVU;

import java.awt.*;
import javax.swing.*;

public class DichVuComponents {
    private JTextField txtMaDV, txtTenDV, txtLoaiDV, txtSoLuong, txtGiaDV;
    private JComboBox<String> cbXuLy;
    private JDialog dialog;

    public DichVuComponents(JFrame parent) {
        dialog = new JDialog(parent, "Nhập Thông Tin Dịch Vụ", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new GridLayout(6, 2));

        txtMaDV = new JTextField();
        txtTenDV = new JTextField();
        txtLoaiDV = new JTextField();
        txtSoLuong = new JTextField();
        txtGiaDV = new JTextField();
        cbXuLy = new JComboBox<>(new String[]{"1", "0"});

        dialog.add(new JLabel("Mã DV:"));
        dialog.add(txtMaDV);
        dialog.add(new JLabel("Tên DV:"));
        dialog.add(txtTenDV);
        dialog.add(new JLabel("Loại DV:"));
        dialog.add(txtLoaiDV);
        dialog.add(new JLabel("Số Lượng:"));
        dialog.add(txtSoLuong);
        dialog.add(new JLabel("Giá DV:"));
        dialog.add(txtGiaDV);
        dialog.add(new JLabel("Xử Lý:"));
        dialog.add(cbXuLy);
    }

    public void showDialog() {
        dialog.setVisible(true);
    }

    public JTextField getTxtMaDV() { return txtMaDV; }
    public JTextField getTxtTenDV() { return txtTenDV; }
    public JTextField getTxtLoaiDV() { return txtLoaiDV; }
    public JTextField getTxtSoLuong() { return txtSoLuong; }
    public JTextField getTxtGiaDV() { return txtGiaDV; }
    public JComboBox<String> getCbXuLy() { return cbXuLy; }
}
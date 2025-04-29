package GUI_KHACHHANG;

import DTO.KhachHangDTO;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KhachHangComponent {
    private JTextField txtMaKH, txtTenKH, txtCCCD, txtDiaChi, txtSoDienThoai, txtEmail;
    private JComboBox<String> cbGioiTinh, cbTrangThai;
    private JFormattedTextField txtNgaySinh;
    private JDialog dialog;
    private JButton btnOK, btnCancel;

    public KhachHangComponent(JFrame parent) {
        dialog = new JDialog(parent, "Nhập Thông Tin Khách Hàng", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtMaKH = new JTextField();
        txtTenKH = new JTextField();
        txtCCCD = new JTextField();
        txtDiaChi = new JTextField();
        txtSoDienThoai = new JTextField();
        txtEmail = new JTextField();
        cbGioiTinh = new JComboBox<>(new String[] { "Nữ", "Nam" });
        cbTrangThai = new JComboBox<>(new String[] { "Hoạt động", "Ngưng hoạt động" });

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtNgaySinh = new JFormattedTextField(dateFormat);
        txtNgaySinh.setValue(new Date());

        inputPanel.add(new JLabel("Mã KH:"));
        inputPanel.add(txtMaKH);
        inputPanel.add(new JLabel("Tên KH:"));
        inputPanel.add(txtTenKH);
        inputPanel.add(new JLabel("Giới Tính:"));
        inputPanel.add(cbGioiTinh);
        inputPanel.add(new JLabel("CCCD:"));
        inputPanel.add(txtCCCD);
        inputPanel.add(new JLabel("Địa Chỉ:"));
        inputPanel.add(txtDiaChi);
        inputPanel.add(new JLabel("SĐT:"));
        inputPanel.add(txtSoDienThoai);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(txtEmail);
        inputPanel.add(new JLabel("Trạng Thái:"));
        inputPanel.add(cbTrangThai);
        inputPanel.add(new JLabel("Ngày Sinh:"));
        inputPanel.add(txtNgaySinh);

        JPanel buttonPanel = new JPanel();
        btnOK = new JButton("OK");
        btnCancel = new JButton("Cancel");
        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> dialog.dispose());
    }

    public void showDialog() {
        dialog.setVisible(true);
    }

    public JTextField getTxtMaKH() {
        return txtMaKH;
    }

    public JTextField getTxtTenKH() {
        return txtTenKH;
    }

    public JTextField getTxtCCCD() {
        return txtCCCD;
    }

    public JTextField getTxtDiaChi() {
        return txtDiaChi;
    }

    public JTextField getTxtSoDienThoai() {
        return txtSoDienThoai;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JComboBox<String> getCbGioiTinh() {
        return cbGioiTinh;
    }

    public JComboBox<String> getCbTrangThai() {
        return cbTrangThai;
    }

    public JFormattedTextField getTxtNgaySinh() {
        return txtNgaySinh;
    }

    public JButton getBtnOK() {
        return btnOK;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public JDialog getDialog() {
        return dialog;
    }
}
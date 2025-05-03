package GUI_HOADON;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class HoaDonComponents {
    private JTextField txtMaHD, txtMaCTT, txtTienP, txtTienDV, txtGiamGia, txtPhuThu, txtTongTien, txtHinhThucThanhToan;
    private JFormattedTextField txtNgayThanhToan;
    private JComboBox<String> cbXuLy;
    private JDialog dialog;
    private JButton btnOK, btnCancel;

    public HoaDonComponents(JFrame parent) {
        dialog = new JDialog(parent, "Nhập Thông Tin Hóa Đơn", true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtMaHD = new JTextField();
        txtMaCTT = new JTextField();
        txtTienP = new JTextField();
        txtTienDV = new JTextField();
        txtGiamGia = new JTextField();
        txtPhuThu = new JTextField();
        txtTongTien = new JTextField();
        txtNgayThanhToan = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        txtHinhThucThanhToan = new JTextField();
        cbXuLy = new JComboBox<>(new String[] { "1 - Đã xử lý", "0 - Đã hủy" });

        inputPanel.add(new JLabel("Mã Hóa Đơn:"));
        inputPanel.add(txtMaHD);
        inputPanel.add(new JLabel("Mã Chi Tiết Thuê:"));
        inputPanel.add(txtMaCTT);
        inputPanel.add(new JLabel("Tiền Phòng:"));
        inputPanel.add(txtTienP);
        inputPanel.add(new JLabel("Tiền Dịch Vụ:"));
        inputPanel.add(txtTienDV);
        inputPanel.add(new JLabel("Giảm Giá:"));
        inputPanel.add(txtGiamGia);
        inputPanel.add(new JLabel("Phụ Thu:"));
        inputPanel.add(txtPhuThu);
        inputPanel.add(new JLabel("Tổng Tiền:"));
        inputPanel.add(txtTongTien);
        inputPanel.add(new JLabel("Ngày Thanh Toán:"));
        inputPanel.add(txtNgayThanhToan);
        inputPanel.add(new JLabel("Hình Thức Thanh Toán:"));
        inputPanel.add(txtHinhThucThanhToan);
        inputPanel.add(new JLabel("Xử Lý:"));
        inputPanel.add(cbXuLy);

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

    public JTextField getTxtMaHD() {
        return txtMaHD;
    }

    public JTextField getTxtMaCTT() {
        return txtMaCTT;
    }

    public JTextField getTxtTienP() {
        return txtTienP;
    }

    public JTextField getTxtTienDV() {
        return txtTienDV;
    }

    public JTextField getTxtGiamGia() {
        return txtGiamGia;
    }

    public JTextField getTxtPhuThu() {
        return txtPhuThu;
    }

    public JTextField getTxtTongTien() {
        return txtTongTien;
    }

    public JFormattedTextField getTxtNgayThanhToan() {
        return txtNgayThanhToan;
    }

    public JTextField getTxtHinhThucThanhToan() {
        return txtHinhThucThanhToan;
    }

    public JComboBox<String> getCbXuLy() {
        return cbXuLy;
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
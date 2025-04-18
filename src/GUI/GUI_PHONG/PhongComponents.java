package GUI_PHONG;

import javax.swing.*;
import java.awt.*;

public class PhongComponents {
    private JTextField txtMaP, txtTenP, txtLoaiP, txtHinhAnh, txtGiaP, txtChiTietLoaiPhong;
    private JComboBox<String> cbTinhTrang;
    private JDialog dialog;
    private JButton btnOK, btnCancel;

    public PhongComponents(JFrame parent) {
        dialog = new JDialog(parent, "Nhập Thông Tin Phòng", true);
        dialog.setSize(400, 350); // Giảm chiều cao vì bỏ 1 trường
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10)); // Giảm từ 8 xuống 7 hàng
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtMaP = new JTextField();
        txtTenP = new JTextField();
        txtLoaiP = new JTextField();
        txtHinhAnh = new JTextField();
        txtGiaP = new JTextField();
        txtChiTietLoaiPhong = new JTextField();
        cbTinhTrang = new JComboBox<>(new String[]{"1", "0"});

        inputPanel.add(new JLabel("Mã Phòng:"));
        inputPanel.add(txtMaP);
        inputPanel.add(new JLabel("Tên Phòng:"));
        inputPanel.add(txtTenP);
        inputPanel.add(new JLabel("Loại Phòng:"));
        inputPanel.add(txtLoaiP);
        inputPanel.add(new JLabel("Hình Ảnh:"));
        inputPanel.add(txtHinhAnh);
        inputPanel.add(new JLabel("Giá Phòng:"));
        inputPanel.add(txtGiaP);
        inputPanel.add(new JLabel("Chi Tiết Loại Phòng:"));
        inputPanel.add(txtChiTietLoaiPhong);
        inputPanel.add(new JLabel("Tình Trạng:"));
        inputPanel.add(cbTinhTrang);

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

    public JTextField getTxtMaP() { return txtMaP; }
    public JTextField getTxtTenP() { return txtTenP; }
    public JTextField getTxtLoaiP() { return txtLoaiP; }
    public JTextField getTxtHinhAnh() { return txtHinhAnh; }
    public JTextField getTxtGiaP() { return txtGiaP; }
    public JTextField getTxtChiTietLoaiPhong() { return txtChiTietLoaiPhong; }
    public JComboBox<String> getCbTinhTrang() { return cbTinhTrang; }
    public JButton getBtnOK() { return btnOK; }
    public JDialog getDialog() { return dialog; }
}
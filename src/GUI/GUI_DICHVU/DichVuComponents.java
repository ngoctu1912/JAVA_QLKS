package GUI_DICHVU;

import javax.swing.*;
import java.awt.*;

public class DichVuComponents {
    private JTextField txtMaDV, txtTenDV, txtLoaiDV, txtSoLuong, txtGiaDV;
    private JComboBox<String> cbXuLy;
    private JDialog dialog;
    private JButton btnOK, btnCancel;

    public DichVuComponents(JFrame parent) {
        dialog = new JDialog(parent, "Nhập Thông Tin Dịch Vụ", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtMaDV = new JTextField();
        txtTenDV = new JTextField();
        txtLoaiDV = new JTextField();
        txtSoLuong = new JTextField();
        txtGiaDV = new JTextField();
        cbXuLy = new JComboBox<>(new String[] { "1 - Đã xử lý", "0 - Chưa xử lý" });

        inputPanel.add(new JLabel("Mã Dịch Vụ:"));
        inputPanel.add(txtMaDV);
        inputPanel.add(new JLabel("Tên Dịch Vụ:"));
        inputPanel.add(txtTenDV);
        inputPanel.add(new JLabel("Loại Dịch Vụ:"));
        inputPanel.add(txtLoaiDV);
        inputPanel.add(new JLabel("Số Lượng:"));
        inputPanel.add(txtSoLuong);
        inputPanel.add(new JLabel("Giá Dịch Vụ:"));
        inputPanel.add(txtGiaDV);
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

    public JTextField getTxtMaDV() {
        return txtMaDV;
    }

    public JTextField getTxtTenDV() {
        return txtTenDV;
    }

    public JTextField getTxtLoaiDV() {
        return txtLoaiDV;
    }

    public JTextField getTxtSoLuong() {
        return txtSoLuong;
    }

    public JTextField getTxtGiaDV() {
        return txtGiaDV;
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
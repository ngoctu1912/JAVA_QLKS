package GUI_NHANVIEN;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import helper.Validation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NhanVienDialog extends JDialog {
    private NhanVienBUS nhanVienBUS;
    private NhanVienGUI gui;
    private NhanVienDTO nhanVien;
    private String type;

    private JTextField hotenField;
    private JComboBox<String> gioitinhCombo;
    private JTextField ngaysinhField;
    private JTextField sdtField;
    private JTextField emailField;
    private JTextField snpField;
    private JTextField lnField;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public NhanVienDialog(NhanVienBUS nhanVienBUS, NhanVienGUI parent, Component owner, String title, boolean modal, String type, NhanVienDTO nhanVien) {
        super(parent, title, modal);
        this.nhanVienBUS = nhanVienBUS;
        this.gui = parent;
        this.nhanVien = nhanVien;
        this.type = type;
        initComponents();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        try {
            setSize(600, 400);
            setLayout(new BorderLayout());
            getContentPane().setBackground(Color.WHITE);

            // Main panel with padding
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            mainPanel.setBackground(Color.WHITE);

            // Form panel
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Họ tên
            gbc.gridx = 0;
            gbc.gridy = 0;
            formPanel.add(new JLabel("Họ tên:"), gbc);
            gbc.gridx = 1;
            hotenField = new JTextField(20);
            hotenField.setText(nhanVien != null ? nhanVien.getHOTEN() : "");
            formPanel.add(hotenField, gbc);

            // Giới tính
            gbc.gridx = 0;
            gbc.gridy = 1;
            formPanel.add(new JLabel("Giới tính:"), gbc);
            gbc.gridx = 1;
            gioitinhCombo = new JComboBox<>(new String[]{"Nam", "Nữ"});
            gioitinhCombo.setSelectedIndex(nhanVien != null ? (nhanVien.getGIOITINH() == 1 ? 0 : 1) : 0);
            formPanel.add(gioitinhCombo, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            formPanel.add(new JLabel("Ngày sinh (yyyy-MM-dd):"), gbc); // Cập nhật nhãn
            gbc.gridx = 1;
            ngaysinhField = new JTextField(20);
            ngaysinhField.setText(nhanVien != null && nhanVien.getNGAYSINH() != null ?
                    dateFormat.format(nhanVien.getNGAYSINH()) : "");
            formPanel.add(ngaysinhField, gbc);

            // Số điện thoại
            gbc.gridx = 0;
            gbc.gridy = 3;
            formPanel.add(new JLabel("Số điện thoại:"), gbc);
            gbc.gridx = 1;
            sdtField = new JTextField(20);
            sdtField.setText(nhanVien != null ? nhanVien.getSDT() : "");
            formPanel.add(sdtField, gbc);

            // Email
            gbc.gridx = 0;
            gbc.gridy = 4;
            formPanel.add(new JLabel("Email:"), gbc);
            gbc.gridx = 1;
            emailField = new JTextField(20);
            emailField.setText(nhanVien != null ? nhanVien.getEMAIL() : "");
            formPanel.add(emailField, gbc);

            // Số ngày phép
            gbc.gridx = 0;
            gbc.gridy = 5;
            formPanel.add(new JLabel("Số ngày phép:"), gbc);
            gbc.gridx = 1;
            snpField = new JTextField(20);
            snpField.setText(nhanVien != null ? String.valueOf(nhanVien.getSNP()) : "0");
            formPanel.add(snpField, gbc);

            // Lương
            gbc.gridx = 0;
            gbc.gridy = 6;
            formPanel.add(new JLabel("Lương:"), gbc);
            gbc.gridx = 1;
            lnField = new JTextField(20);
            lnField.setText(nhanVien != null ? String.valueOf(nhanVien.getLN()) : "0");
            formPanel.add(lnField, gbc);

            // Set read-only for view mode
            if (type.equals("view")) {
                hotenField.setEditable(false);
                gioitinhCombo.setEnabled(false);
                ngaysinhField.setEditable(false);
                sdtField.setEditable(false);
                emailField.setEditable(false);
                snpField.setEditable(false);
                lnField.setEditable(false);
            }

            mainPanel.add(formPanel, BorderLayout.CENTER);

            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(Color.WHITE);

            if (!type.equals("view")) {
                JButton saveButton = new JButton(type.equals("create") ? "Thêm nhân viên" : "Cập nhật nhân viên");
                saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
                saveButton.setBackground(new Color(66, 139, 202));
                saveButton.setForeground(Color.WHITE);
                saveButton.setBorderPainted(false);
                saveButton.addActionListener(e -> saveNhanVien());
                buttonPanel.add(saveButton);
            }

            JButton closeButton = new JButton(type.equals("view") ? "Đóng" : "Hủy bỏ");
            closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
            closeButton.setBackground(new Color(217, 83, 79));
            closeButton.setForeground(Color.WHITE);
            closeButton.setBorderPainted(false);
            closeButton.addActionListener(e -> dispose());
            buttonPanel.add(closeButton);

            add(mainPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi khởi tạo dialog: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveNhanVien() {
        // Validate input
        String hoten = hotenField.getText().trim();
        if (Validation.isEmpty(hoten)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!");
            return;
        }

        String sdt = sdtField.getText().trim();
        if (!Validation.isPhoneNumber(sdt)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Phải là 10 chữ số.");
            return;
        }

        String email = emailField.getText().trim();
        if (!Validation.isEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!");
            return;
        }

        Date ngaysinh = null;
        if (!Validation.isEmpty(ngaysinhField.getText().trim())) {
            try {
                ngaysinh = dateFormat.parse(ngaysinhField.getText().trim());
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ! Định dạng: yyyy-MM-dd");
                return;
            }
        }

        String snpText = snpField.getText().trim();
        int snp;
        if (Validation.isEmpty(snpText)) {
            snp = 0; // Default value
        } else if (!Validation.isNumber(snpText)) {
            JOptionPane.showMessageDialog(this, "Số ngày phép phải là số không âm!");
            return;
        } else {
            snp = Integer.parseInt(snpText);
        }

        String lnText = lnField.getText().trim();
        int ln;
        if (Validation.isEmpty(lnText)) {
            ln = 0; // Default value
        } else if (!Validation.isNumber(lnText)) {
            JOptionPane.showMessageDialog(this, "Lương phải là số không âm!");
            return;
        } else {
            ln = Integer.parseInt(lnText);
        }

        int gioitinh = gioitinhCombo.getSelectedIndex() == 0 ? 1 : 0; // 0 (Nam) -> 1, 1 (Nữ) -> 0

        // Create or update NhanVienDTO
        NhanVienDTO nv;
        if (type.equals("create")) {
            nv = new NhanVienDTO(
                    0, // MNV will be auto-incremented
                    hoten,
                    gioitinh,
                    ngaysinh,
                    sdt,
                    1, // TT: Active by default
                    email,
                    snp,
                    new java.sql.Date(System.currentTimeMillis()), // NVL: Current date
                    ln
            );
            nhanVienBUS.insertNv(nv);
        } else {
            nv = new NhanVienDTO(
                    nhanVien.getMNV(),
                    hoten,
                    gioitinh,
                    ngaysinh,
                    sdt,
                    nhanVien.getTT(),
                    email,
                    snp,
                    nhanVien.getNVL(),
                    ln
            );
            int index = nhanVienBUS.getIndexById(nhanVien.getMNV());
            nhanVienBUS.updateNv(index, nv);
        }

        gui.loadNhanVienData();
        dispose();
    }
}



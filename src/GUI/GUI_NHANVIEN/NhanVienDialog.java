package GUI_NHANVIEN;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import Component.ButtonCustom;
import Component.HeaderTitle;
import Component.InputDate;
import Component.InputForm;
import Component.NumericDocumentFilter;
import helper.Validation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NhanVienDialog extends JDialog implements ActionListener {
    private NhanVienBUS nhanVienBUS;
    private NhanVienGUI gui;
    private NhanVienDTO nhanVien;
    private boolean isEditMode;
    private boolean isViewMode;
    private HeaderTitle titlePage;
    private JPanel pnCenter, pnInfoNhanVien, pnBottom, gioitinhPanel;
    private InputForm txtMNV, txtHoTen, txtSDT, txtEmail, txtSNP, txtLN;
    private InputDate txtNgaysinh; // Changed to InputDate
    private JComboBox<String> cbGioiTinh;
    private ButtonCustom btnSave, btnCancel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public NhanVienDialog(NhanVienGUI gui, NhanVienDTO nhanVien, boolean isEditMode, boolean isViewMode) {
        super(gui.getOwner(), isViewMode ? "CHI TIẾT NHÂN VIÊN" : (isEditMode ? "SỬA NHÂN VIÊN" : "THÊM NHÂN VIÊN MỚI"), true);
        this.nhanVienBUS = new NhanVienBUS(gui);
        this.gui = gui;
        this.nhanVien = nhanVien;
        this.isEditMode = isEditMode;
        this.isViewMode = isViewMode;

        initComponents(isViewMode ? "CHI TIẾT NHÂN VIÊN" : (isEditMode ? "SỬA NHÂN VIÊN" : "THÊM NHÂN VIÊN MỚI"));
        if (isEditMode || isViewMode) {
            if (nhanVien != null) {
                setInfo(nhanVien);
            } else {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu nhân viên để hiển thị!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } else {
            initCreate();
        }

        if (isViewMode) {
            initView();
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(String title) {
        setSize(new Dimension(800, 550));
        setLayout(new BorderLayout(0, 0));

        // Title
        titlePage = new HeaderTitle(title.toUpperCase());
        titlePage.setColor("167AC6");
        add(titlePage, BorderLayout.NORTH);

        // Main panel
        pnCenter = new JPanel(new BorderLayout());
        pnCenter.setBackground(Color.WHITE);
        add(pnCenter, BorderLayout.CENTER);

        // Employee info panel
        pnInfoNhanVien = new JPanel(new GridLayout(4, 2, 15, 15));
        pnInfoNhanVien.setBackground(Color.WHITE);
        pnInfoNhanVien.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnCenter.add(pnInfoNhanVien, BorderLayout.CENTER);

        // Initialize input fields
        txtMNV = new InputForm("Mã NV");
        txtMNV.setEditable(false);
        txtHoTen = new InputForm("Họ Tên");
        txtNgaysinh = new InputDate("Ngày Sinh"); // Changed to InputDate
        txtSDT = new InputForm("Số Điện Thoại");
        txtEmail = new InputForm("Email");
        // txtSNP = new InputForm("Số Ngày Phép");
        // PlainDocument snpDoc = (PlainDocument) txtSNP.getTxtForm().getDocument();
        // snpDoc.setDocumentFilter(new NumericDocumentFilter());
        // txtLN = new InputForm("Lương");
        // PlainDocument lnDoc = (PlainDocument) txtLN.getTxtForm().getDocument();
        // lnDoc.setDocumentFilter(new NumericDocumentFilter());

        // Gender ComboBox
        cbGioiTinh = new JComboBox<>(new String[] { "Nam", "Nữ" });

        // Custom panel for Gender
        gioitinhPanel = new JPanel(new GridBagLayout());
        gioitinhPanel.setBackground(Color.WHITE);
        gioitinhPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Gender label
        JLabel lblGioiTinh = new JLabel("Giới Tính:");
        Font timesNewRomanBold = Font.getFont("Times New Roman");
        if (timesNewRomanBold != null) {
            lblGioiTinh.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblGioiTinh.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        }
        lblGioiTinh.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gioitinhPanel.add(lblGioiTinh, gbc);

        // Gender ComboBox
        Font timesNewRomanPlain = Font.getFont("Times New Roman");
        if (timesNewRomanPlain != null) {
            cbGioiTinh.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            cbGioiTinh.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gioitinhPanel.add(cbGioiTinh, gbc);

        // Add fields to panel
        pnInfoNhanVien.add(txtMNV);
        pnInfoNhanVien.add(txtHoTen);
        pnInfoNhanVien.add(gioitinhPanel);
        pnInfoNhanVien.add(txtNgaysinh);
        pnInfoNhanVien.add(txtSDT);
        pnInfoNhanVien.add(txtEmail);
        // pnInfoNhanVien.add(txtSNP);
        // pnInfoNhanVien.add(txtLN);

        // Button panel
        pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
        pnBottom.setBackground(Color.WHITE);
        pnCenter.add(pnBottom, BorderLayout.SOUTH);

        if (!isViewMode) {
            btnSave = new ButtonCustom(isEditMode ? "Lưu thông tin" : "Thêm nhân viên", "success", 14);
            btnSave.addActionListener(this);
            pnBottom.add(btnSave);
        }

        btnCancel = new ButtonCustom(isViewMode ? "Đóng" : "Hủy bỏ", "danger", 14);
        btnCancel.addActionListener(this);
        pnBottom.add(btnCancel);
    }

    private void initCreate() {
        // Generate auto-incremented MNV starting from NV001
        ArrayList<NhanVienDTO> listNV = nhanVienBUS.getAll();
        int nextId = 1;
        if (!listNV.isEmpty()) {
            int maxId = 0;
            for (NhanVienDTO nv : listNV) {
                int id = nv.getMNV();
                if (id > maxId) {
                    maxId = id;
                }
            }
            nextId = maxId + 1;
        }
        txtMNV.setText("NV" + String.format("%03d", nextId));

        txtHoTen.setText("");
        cbGioiTinh.setSelectedItem("Nam");
        txtNgaysinh.setDate((java.util.Date) null);
        txtSDT.setText("");
        txtEmail.setText("");
        // txtSNP.setText("0");
        // txtLN.setText("0");
    }

    private void initView() {
        txtMNV.setEditable(false);
        txtHoTen.setEditable(false);
        cbGioiTinh.setEnabled(false);
        txtNgaysinh.setDisable(); // Disable date picker
        txtSDT.setEditable(false);
        txtEmail.setEditable(false);
        // txtSNP.setEditable(false);
        // txtLN.setEditable(false);
    }

    private void setInfo(NhanVienDTO nhanVien) {
        txtMNV.setText("NV" + String.format("%03d", nhanVien.getMNV()));
        txtHoTen.setText(nhanVien.getHOTEN() != null ? nhanVien.getHOTEN() : "");
        cbGioiTinh.setSelectedItem(nhanVien.getGIOITINH() == 1 ? "Nam" : "Nữ");
        txtNgaysinh.setDate(nhanVien.getNGAYSINH()); // Set date in InputDate
        txtSDT.setText(nhanVien.getSDT() != null ? nhanVien.getSDT() : "");
        txtEmail.setText(nhanVien.getEMAIL() != null ? nhanVien.getEMAIL() : "");
    }

    private NhanVienDTO getInfo() {
        String mnvStr = txtMNV.getText();
        int mnv = mnvStr.startsWith("NV") ? Integer.parseInt(mnvStr.substring(2)) : 0;
        String hoten = txtHoTen.getText();
        int gioitinh = cbGioiTinh.getSelectedItem().equals("Nam") ? 1 : 0;
        Date ngaysinh = null;
        try {
            ngaysinh = txtNgaysinh.getDate(); // Get date from InputDate
        } catch (ParseException e) {
            // Handled in validation
        }
        String sdt = txtSDT.getText();
        String email = txtEmail.getText();
        // int snp = txtSNP.getText().isEmpty() ? 0 : Integer.parseInt(txtSNP.getText());
        // int ln = txtLN.getText().isEmpty() ? 0 : Integer.parseInt(txtLN.getText());
        return new NhanVienDTO(mnv, hoten, gioitinh, ngaysinh, sdt, 1, email);
    }

    private boolean checkInput() {
        if (Validation.isEmpty(txtHoTen.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!Validation.isPhoneNumber(txtSDT.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Phải là 10 chữ số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!Validation.isEmail(txtEmail.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Date date = txtNgaysinh.getDate(); // Check if date is valid
            if (date == null && !isEditMode) { // Require date for new employees
                JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ! Định dạng: dd/MM/yyyy", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // if (!txtSNP.getText().trim().isEmpty() && !Validation.isNumber(txtSNP.getText().trim())) {
        //     JOptionPane.showMessageDialog(this, "Số ngày phép phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        //     return false;
        // }
        // if (!txtLN.getText().trim().isEmpty() && !Validation.isNumber(txtLN.getText().trim())) {
        //     JOptionPane.showMessageDialog(this, "Lương phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        //     return false;
        // }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnCancel) {
            dispose();
        } else if (source == btnSave && checkInput()) {
            NhanVienDTO newNV = getInfo();
            try {
                if (isEditMode) {
                    int index = nhanVienBUS.getIndexById(newNV.getMNV());
                    nhanVienBUS.updateNv(index, newNV);
                    JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    nhanVienBUS.insertNv(newNV);
                    JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
                gui.loadTableData();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
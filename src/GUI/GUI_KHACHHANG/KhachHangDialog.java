
package GUI_KHACHHANG;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
import Component.ButtonCustom;
import Component.HeaderTitle;
import Component.InputForm;
import Component.InputDate;
import DAO.KhachHangDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import Component.NumericDocumentFilter;

public class KhachHangDialog extends JDialog implements ActionListener {
    private KhachHangBUS khachHangBUS;
    private KhachHangDTO khachHang;
    private boolean isEditMode;
    private boolean isViewMode;
    private HeaderTitle titlePage;
    private JPanel pnCenter, pnInfoKhachHang, pnBottom, gioiTinhPanel, trangThaiPanel;
    private InputForm txtMaKH, txtTenKH, txtCCCD, txtDiaChi, txtSoDienThoai, txtEmail;
    private InputDate txtNgaySinh;
    private JComboBox<String> cbGioiTinh, cbTrangThai;
    private ButtonCustom btnSave, btnCancel;

    public KhachHangDialog(JFrame parent, KhachHangDTO khachHang, boolean isEditMode, boolean isViewMode) {
        super(parent, isViewMode ? "CHI TIẾT KHÁCH HÀNG" : (isEditMode ? "SỬA KHÁCH HÀNG" : "THÊM KHÁCH HÀNG"), true);
        this.khachHangBUS = new KhachHangBUS();
        this.khachHang = khachHang;
        this.isEditMode = isEditMode;
        this.isViewMode = isViewMode;

        initComponents(isViewMode ? "CHI TIẾT KHÁCH HÀNG" : (isEditMode ? "SỬA KHÁCH HÀNG" : "THÊM KHÁCH HÀNG"));
        if (isEditMode || isViewMode) {
            if (khachHang != null) {
                setInfo(khachHang);
            } else {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu khách hàng để hiển thị!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        setSize(new Dimension(800, 600));
        setLayout(new BorderLayout(0, 0));

        // Tiêu đề
        titlePage = new HeaderTitle(title.toUpperCase());
        titlePage.setColor("167AC6");
        add(titlePage, BorderLayout.NORTH);

        // Phần chính
        pnCenter = new JPanel(new BorderLayout());
        pnCenter.setBackground(Color.WHITE);
        add(pnCenter, BorderLayout.CENTER);

        // Phần thông tin khách hàng
        pnInfoKhachHang = new JPanel(new GridLayout(5, 2, 15, 15));
        pnInfoKhachHang.setBackground(Color.WHITE);
        pnInfoKhachHang.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnCenter.add(pnInfoKhachHang, BorderLayout.CENTER);

        // Khởi tạo các trường nhập liệu
        txtMaKH = new InputForm("Mã Khách Hàng");
        txtMaKH.setEditable(false);
        txtTenKH = new InputForm("Tên Khách Hàng");
        txtCCCD = new InputForm("CCCD");
        PlainDocument cccdDoc = (PlainDocument) txtCCCD.getTxtForm().getDocument();
        cccdDoc.setDocumentFilter(new NumericDocumentFilter());
        txtDiaChi = new InputForm("Địa Chỉ");
        txtSoDienThoai = new InputForm("Số Điện Thoại");
        PlainDocument sdtDoc = (PlainDocument) txtSoDienThoai.getTxtForm().getDocument();
        sdtDoc.setDocumentFilter(new NumericDocumentFilter());
        txtEmail = new InputForm("Email");
        txtNgaySinh = new InputDate("Ngày Sinh");
        cbGioiTinh = new JComboBox<>(new String[] { "Nữ", "Nam" });
        cbTrangThai = new JComboBox<>(new String[] { "Ngưng hoạt động", "Hoạt động" });

        // Panel cho Giới Tính
        gioiTinhPanel = new JPanel(new GridBagLayout());
        gioiTinhPanel.setBackground(Color.WHITE);
        gioiTinhPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

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
        gioiTinhPanel.add(lblGioiTinh, gbc);

        Font timesNewRomanPlain = Font.getFont("Times New Roman");
        if (timesNewRomanPlain != null) {
            cbGioiTinh.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            cbGioiTinh.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gioiTinhPanel.add(cbGioiTinh, gbc);

        // Panel cho Trạng Thái
        trangThaiPanel = new JPanel(new GridBagLayout());
        trangThaiPanel.setBackground(Color.WHITE);
        trangThaiPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbcTrangThai = new GridBagConstraints();
        gbcTrangThai.insets = new Insets(5, 5, 5, 5);
        gbcTrangThai.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTrangThai = new JLabel("Trạng Thái:");
        if (timesNewRomanBold != null) {
            lblTrangThai.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblTrangThai.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        }
        lblTrangThai.setForeground(new Color(0, 102, 153));
        gbcTrangThai.gridx = 0;
        gbcTrangThai.gridy = 0;
        gbcTrangThai.weightx = 0.3;
        trangThaiPanel.add(lblTrangThai, gbcTrangThai);

        if (timesNewRomanPlain != null) {
            cbTrangThai.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            cbTrangThai.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        gbcTrangThai.gridx = 1;
        gbcTrangThai.gridy = 0;
        gbcTrangThai.weightx = 0.7;
        trangThaiPanel.add(cbTrangThai, gbcTrangThai);

        // Thêm các trường vào panel
        pnInfoKhachHang.add(txtMaKH);
        pnInfoKhachHang.add(txtTenKH);
        pnInfoKhachHang.add(gioiTinhPanel);
        pnInfoKhachHang.add(txtCCCD);
        pnInfoKhachHang.add(txtDiaChi);
        pnInfoKhachHang.add(txtSoDienThoai);
        pnInfoKhachHang.add(txtEmail);
        pnInfoKhachHang.add(txtNgaySinh);
        pnInfoKhachHang.add(trangThaiPanel);

        // Phần nút (căn giữa)
        pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
        pnBottom.setBackground(Color.WHITE);
        pnCenter.add(pnBottom, BorderLayout.SOUTH);

        if (!isViewMode) {
            btnSave = new ButtonCustom(isEditMode ? "Lưu thông tin" : "Thêm khách hàng", "success", 14);
            btnSave.addActionListener(this);
            pnBottom.add(btnSave);
        }

        btnCancel = new ButtonCustom(isViewMode ? "Đóng" : "Hủy bỏ", "danger", 14);
        btnCancel.addActionListener(this);
        pnBottom.add(btnCancel);
    }

    private void initCreate() {
        int nextId = KhachHangDAO.getInstance().getAutoIncrement();
        // Kiểm tra dự phòng: Lấy MAX(MKH) + 1 nếu AUTO_INCREMENT nhỏ bất thường
        int maxId = getMaxMaKH();
        if (nextId <= maxId) {
            nextId = maxId + 1;
        }
        txtMaKH.setText(String.valueOf(nextId));
        txtTenKH.setText("");
        cbGioiTinh.setSelectedItem("Nữ");
        txtCCCD.setText("");
        txtDiaChi.setText("");
        txtSoDienThoai.setText("");
        txtEmail.setText("");
        cbTrangThai.setSelectedItem("Hoạt động");
        txtNgaySinh.getDateChooser().setDate(null); // Để trống ngày sinh
    }

    private int getMaxMaKH() {
        ArrayList<KhachHangDTO> list = KhachHangDAO.getInstance().selectAll();
        int maxId = 0;
        for (KhachHangDTO kh : list) {
            if (kh.getMaKhachHang() > maxId) {
                maxId = kh.getMaKhachHang();
            }
        }
        return maxId;
    }

    private void initView() {
        txtMaKH.setEditable(false);
        txtTenKH.setEditable(false);
        cbGioiTinh.setEnabled(false);
        txtCCCD.setEditable(false);
        txtDiaChi.setEditable(false);
        txtSoDienThoai.setEditable(false);
        txtEmail.setEditable(false);
        cbTrangThai.setEnabled(false);
        txtNgaySinh.setDisable(); // Vô hiệu hóa JDateChooser
    }

    private void setInfo(KhachHangDTO khachHang) {
        txtMaKH.setText(String.valueOf(khachHang.getMaKhachHang()));
        txtTenKH.setText(khachHang.getTenKhachHang());
        cbGioiTinh.setSelectedIndex(khachHang.getGioiTinh());
        txtCCCD.setText(String.valueOf(khachHang.getCccd()));
        txtDiaChi.setText(khachHang.getDiaChi());
        txtSoDienThoai.setText(khachHang.getSoDienThoai());
        txtEmail.setText(khachHang.getEmail());
        cbTrangThai.setSelectedIndex(khachHang.getTrangThai() == 1 ? 1 : 0); // 1: Hoạt động, 0: Ngưng hoạt động
        txtNgaySinh.getDateChooser().setDate(khachHang.getNgaySinh()); // Gọi trực tiếp JDateChooser.setDate
    }

    private KhachHangDTO getInfo() {
        String maKH = txtMaKH.getText();
        String tenKH = txtTenKH.getText();
        int gioiTinh = cbGioiTinh.getSelectedIndex();
        long cccd = txtCCCD.getText().isEmpty() ? 0 : Long.parseLong(txtCCCD.getText());
        String diaChi = txtDiaChi.getText();
        String soDienThoai = txtSoDienThoai.getText();
        String email = txtEmail.getText();
        int trangThai = cbTrangThai.getSelectedIndex() == 1 ? 1 : 0; // 1: Hoạt động, 0: Ngưng hoạt động
        Date ngaySinh = null;
        try {
            ngaySinh = txtNgaySinh.getDate();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy ngày sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return new KhachHangDTO(Integer.parseInt(maKH), tenKH, gioiTinh, cccd, diaChi, soDienThoai, email, trangThai, ngaySinh);
    }

    private boolean checkInput() {
        if (txtTenKH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtCCCD.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "CCCD không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            long cccd = Long.parseLong(txtCCCD.getText());
            if (cccd <= 0) {
                JOptionPane.showMessageDialog(this, "CCCD phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (cccd > 9223372036854775807L) {
                JOptionPane.showMessageDialog(this, "CCCD vượt quá giới hạn cho phép!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "CCCD phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtSoDienThoai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtSoDienThoai.getText().matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải có 10-11 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Kiểm tra trùng lặp số điện thoại
        String soDienThoai = txtSoDienThoai.getText().trim();
        int maKhachHang = isEditMode ? Integer.parseInt(txtMaKH.getText()) : -1; // -1 cho trường hợp thêm mới
        if (khachHangBUS.isPhoneNumberExists(soDienThoai, maKhachHang)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            if (txtNgaySinh.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi kiểm tra ngày sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnCancel) {
            dispose();
        } else if (source == btnSave && checkInput()) {
            KhachHangDTO newKhachHang = getInfo();
            try {
                if (isEditMode) {
                    khachHangBUS.update(newKhachHang);
                    JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    khachHangBUS.add(newKhachHang);
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
                dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu khách hàng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
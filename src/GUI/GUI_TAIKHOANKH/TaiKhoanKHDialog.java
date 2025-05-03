package GUI_TAIKHOANKH;

import BUS.TaiKhoanBUS;
import BUS.NhomQuyenBUS;
import DTO.TaiKhoanKHDTO;
import DTO.NhomQuyenDTO;
import Component.ButtonCustom;
import Component.HeaderTitle;
import Component.InputForm;
import helper.Validation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TaiKhoanKHDialog extends JDialog {
    private TaiKhoanBUS taiKhoanBus;
    private TaiKhoanKHGUI gui;
    private TaiKhoanKHDTO taiKhoanKH;
    private String type;
    private int maNhomQuyen;
    private HeaderTitle titlePage;
    private JPanel pnCenter, pnBottom, nhomQuyenPanel, trangThaiPanel, matKhauPanel, confirmMatKhauPanel;
    private InputForm maKHField, tenDangNhapField;
    private JPasswordField matKhauField, confirmMatKhauField;
    private JComboBox<String> nhomQuyenCombo, trangThaiCombo;
    private ButtonCustom btnSave, btnCancel;

    public TaiKhoanKHDialog(TaiKhoanBUS taiKhoanBus, TaiKhoanKHGUI parent, Component owner, String title, boolean modal, String type, TaiKhoanKHDTO taiKhoanKH, int maNhomQuyen) {
        super(JOptionPane.getFrameForComponent(owner), title, modal);
        this.taiKhoanBus = taiKhoanBus;
        this.gui = parent;
        this.taiKhoanKH = taiKhoanKH;
        this.type = type;
        this.maNhomQuyen = maNhomQuyen;
        initComponents(title);
        setLocationRelativeTo(owner);
    }

    private void initComponents(String title) {
        setSize(600, 500);
        setLayout(new BorderLayout());

        // Tiêu đề
        titlePage = new HeaderTitle(title.toUpperCase());
        titlePage.setColor("167AC6");
        add(titlePage, BorderLayout.NORTH);

        // Phần chính
        pnCenter = new JPanel(new BorderLayout());
        pnCenter.setBackground(Color.WHITE);
        add(pnCenter, BorderLayout.CENTER);

        // Phần thông tin tài khoản
        JPanel pnInfo = new JPanel(new GridLayout(6, 1, 15, 15));
        pnInfo.setBackground(Color.WHITE);
        pnInfo.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnCenter.add(pnInfo, BorderLayout.CENTER);

        // Khởi tạo các trường nhập liệu
        maKHField = new InputForm("Mã KH");
        maKHField.setText(taiKhoanKH != null ? String.valueOf(taiKhoanKH.getMaKhachHang()) : String.valueOf(taiKhoanBus.layMaKhachHangTuDong()));
        maKHField.setEditable(type.equals("create"));

        tenDangNhapField = new InputForm("Tên đăng nhập");
        tenDangNhapField.setText(taiKhoanKH != null ? taiKhoanKH.getTenDangNhap() : "");

        // Trường mật khẩu
        matKhauPanel = new JPanel(new GridBagLayout());
        matKhauPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        Font timesNewRomanBold = Font.getFont("Times New Roman");
        if (timesNewRomanBold != null) {
            lblMatKhau.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblMatKhau.setFont(new Font("Segoe UI", Font.BOLD, 20));
        }
        lblMatKhau.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        matKhauPanel.add(lblMatKhau, gbc);
        matKhauField = new JPasswordField();
        Font timesNewRomanPlain = Font.getFont("Times New Roman");
        if (timesNewRomanPlain != null) {
            matKhauField.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            matKhauField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        }
        matKhauField.setForeground(new Color(51, 51, 51));
        matKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        matKhauField.setBackground(Color.WHITE);
        matKhauField.setPreferredSize(new Dimension(200, 32));
        matKhauField.setText(taiKhoanKH != null ? "" : "");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        matKhauPanel.add(matKhauField, gbc);

        // Trường xác nhận mật khẩu
        confirmMatKhauPanel = new JPanel(new GridBagLayout());
        confirmMatKhauPanel.setBackground(Color.WHITE);
        JLabel lblConfirmMatKhau = new JLabel("Xác nhận mật khẩu:");
        if (timesNewRomanBold != null) {
            lblConfirmMatKhau.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblConfirmMatKhau.setFont(new Font("Segoe UI", Font.BOLD, 20));
        }
        lblConfirmMatKhau.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        confirmMatKhauPanel.add(lblConfirmMatKhau, gbc);
        confirmMatKhauField = new JPasswordField();
        if (timesNewRomanPlain != null) {
            confirmMatKhauField.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            confirmMatKhauField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        }
        confirmMatKhauField.setForeground(new Color(51, 51, 51));
        confirmMatKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        confirmMatKhauField.setBackground(Color.WHITE);
        confirmMatKhauField.setPreferredSize(new Dimension(200, 32));
        confirmMatKhauField.setText(taiKhoanKH != null ? "" : "");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        confirmMatKhauPanel.add(confirmMatKhauField, gbc);

        // Nhóm quyền
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUS.getAll();
        String[] nhomQuyenNames = nhomQuyenList.stream().map(NhomQuyenDTO::getTEN).toArray(String[]::new);
        nhomQuyenCombo = new JComboBox<>(nhomQuyenNames);
        if (taiKhoanKH != null) {
            for (int i = 0; i < nhomQuyenList.size(); i++) {
                if (nhomQuyenList.get(i).getMNQ() == taiKhoanKH.getMaNhomQuyen()) {
                    nhomQuyenCombo.setSelectedIndex(i);
                    break;
                }
            }
        }
        nhomQuyenPanel = new JPanel(new GridBagLayout());
        nhomQuyenPanel.setBackground(Color.WHITE);
        JLabel lblNhomQuyen = new JLabel("Nhóm quyền:");
        if (timesNewRomanBold != null) {
            lblNhomQuyen.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblNhomQuyen.setFont(new Font("Segoe UI", Font.BOLD, 20));
        }
        lblNhomQuyen.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        nhomQuyenPanel.add(lblNhomQuyen, gbc);
        if (timesNewRomanPlain != null) {
            nhomQuyenCombo.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            nhomQuyenCombo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        nhomQuyenPanel.add(nhomQuyenCombo, gbc);

        // Trạng thái
        trangThaiCombo = new JComboBox<>(new String[]{"Hoạt động", "Ngưng hoạt động", "Chờ xử lý"});
        trangThaiCombo.setSelectedIndex(taiKhoanKH != null ? taiKhoanKH.getTrangThai() : 0);
        trangThaiPanel = new JPanel(new GridBagLayout());
        trangThaiPanel.setBackground(Color.WHITE);
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        if (timesNewRomanBold != null) {
            lblTrangThai.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 20));
        }
        lblTrangThai.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        trangThaiPanel.add(lblTrangThai, gbc);
        if (timesNewRomanPlain != null) {
            trangThaiCombo.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            trangThaiCombo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        trangThaiPanel.add(trangThaiCombo, gbc);

        // Thêm vào panel
        pnInfo.add(maKHField);
        pnInfo.add(tenDangNhapField);
        pnInfo.add(matKhauPanel);
        pnInfo.add(confirmMatKhauPanel);
        pnInfo.add(nhomQuyenPanel);
        pnInfo.add(trangThaiPanel);

        // Phần nút
        pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
        pnBottom.setBackground(Color.WHITE);
        pnCenter.add(pnBottom, BorderLayout.SOUTH);

        if (!type.equals("view")) {
            btnSave = new ButtonCustom(type.equals("create") ? "Thêm tài khoản" : "Cập nhật tài khoản", "success", 14);
            btnSave.addActionListener(e -> saveTaiKhoanKH());
            pnBottom.add(btnSave);
        }

        btnCancel = new ButtonCustom(type.equals("view") ? "Đóng" : "Hủy bỏ", "danger", 14);
        btnCancel.addActionListener(e -> dispose());
        pnBottom.add(btnCancel);

        if (type.equals("view")) {
            maKHField.setEditable(false);
            tenDangNhapField.setEditable(false);
            matKhauField.setEditable(false);
            confirmMatKhauField.setEditable(false);
            nhomQuyenCombo.setEnabled(false);
            trangThaiCombo.setEnabled(false);
        }
    }

    private void saveTaiKhoanKH() {
        // Validate input
        String maKHText = maKHField.getText().trim();
        if (Validation.isEmpty(maKHText) || !Validation.isNumber(maKHText)) {
            JOptionPane.showMessageDialog(this, "Mã KH phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tenDangNhap = tenDangNhapField.getText().trim();
        if (Validation.isEmpty(tenDangNhap)) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (type.equals("create") && !taiKhoanBus.checkTDN(tenDangNhap)) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String matKhau = new String(matKhauField.getPassword()).trim();
        String confirmMatKhau = new String(confirmMatKhauField.getPassword()).trim();
        if (Validation.isEmpty(matKhau) || matKhau.length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!matKhau.equals(confirmMatKhau)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUS.getAll();
        int maNhomQuyen = nhomQuyenList.get(nhomQuyenCombo.getSelectedIndex()).getMNQ();

        int trangThai = switch ((String) trangThaiCombo.getSelectedItem()) {
            case "Hoạt động" -> 1;
            case "Ngưng hoạt động" -> 0;
            case "Chờ xử lý" -> 2;
            default -> 0;
        };

        // Tạo hoặc cập nhật tài khoản
        TaiKhoanKHDTO tk = new TaiKhoanKHDTO();
        tk.setMaKhachHang(Integer.parseInt(maKHText));
        tk.setTenDangNhap(tenDangNhap);
        tk.setMatKhau(matKhau);
        tk.setMaNhomQuyen(maNhomQuyen);
        tk.setTrangThai(trangThai);

        boolean success;
        if (type.equals("create")) {
            success = taiKhoanBus.addAccKH(tk);
            if (success) {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            success = taiKhoanBus.updateAccKH(tk);
            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (success) {
            gui.loadTaiKhoanKHData();
            dispose();
        }
    }
}
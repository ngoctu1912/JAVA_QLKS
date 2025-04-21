package GUI_TAIKHOAN;

import BUS.TaiKhoanBUS;
import BUS.NhanVienBUS;
import BUS.NhomQuyenBUS;
import DTO.TaiKhoanDTO;
import DTO.NhomQuyenDTO;
import DTO.ChiTietQuyenDTO;
import helper.Validation;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TaiKhoanDialog extends JDialog {
    private TaiKhoanBUS taiKhoanBUS;
    private TaiKhoanGUI gui;
    private TaiKhoanDTO taiKhoan;
    private String type;
    private int maNhomQuyen;

    private JTextField maNVField;
    private JTextField tenDangNhapField;
    private JPasswordField matKhauField;
    private JPasswordField confirmMatKhauField;
    private JComboBox<String> nhomQuyenCombo;
    private JComboBox<String> trangThaiCombo;

    public TaiKhoanDialog(TaiKhoanBUS taiKhoanBUS, TaiKhoanGUI parent, Component owner, String title, boolean modal, String type, TaiKhoanDTO taiKhoan, int maNhomQuyen) {
        super(JOptionPane.getFrameForComponent(owner), title, modal);
        this.taiKhoanBUS = taiKhoanBUS;
        this.gui = parent;
        this.taiKhoan = taiKhoan;
        this.type = type;
        this.maNhomQuyen = maNhomQuyen;
        System.out.println("TaiKhoanDialog initialized with maNhomQuyen: " + maNhomQuyen);
        initComponents();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        try {
            setSize(600, 450);
            setLayout(new BorderLayout());
            getContentPane().setBackground(Color.WHITE);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            mainPanel.setBackground(Color.WHITE);

            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.gridy = 0;
            formPanel.add(new JLabel("Mã NV:"), gbc);
            gbc.gridx = 1;
            maNVField = new JTextField(20);
            maNVField.setText(taiKhoan != null ? String.valueOf(taiKhoan.getMaNV()) : "");
            maNVField.setEditable(type.equals("create"));
            formPanel.add(maNVField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            formPanel.add(new JLabel("Tên đăng nhập:"), gbc);
            gbc.gridx = 1;
            tenDangNhapField = new JTextField(20);
            tenDangNhapField.setText(taiKhoan != null ? taiKhoan.getTenDangNhap() : "");
            formPanel.add(tenDangNhapField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            formPanel.add(new JLabel("Mật khẩu:"), gbc);
            gbc.gridx = 1;
            matKhauField = new JPasswordField(20);
            matKhauField.setText(taiKhoan != null ? "" : "");
            formPanel.add(matKhauField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            formPanel.add(new JLabel("Xác nhận mật khẩu:"), gbc);
            gbc.gridx = 1;
            confirmMatKhauField = new JPasswordField(20);
            confirmMatKhauField.setText(taiKhoan != null ? "" : "");
            formPanel.add(confirmMatKhauField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            formPanel.add(new JLabel("Nhóm quyền:"), gbc);
            gbc.gridx = 1;
            NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
            List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUS.getAll();
            String[] nhomQuyenNames = nhomQuyenList.stream().map(NhomQuyenDTO::getTEN).toArray(String[]::new);
            nhomQuyenCombo = new JComboBox<>(nhomQuyenNames);
            if (taiKhoan != null) {
                for (int i = 0; i < nhomQuyenList.size(); i++) {
                    if (nhomQuyenList.get(i).getMNQ() == taiKhoan.getMaNhomQuyen()) {
                        nhomQuyenCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }
            formPanel.add(nhomQuyenCombo, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            formPanel.add(new JLabel("Trạng thái:"), gbc);
            gbc.gridx = 1;
            trangThaiCombo = new JComboBox<>(new String[]{"Hoạt động", "Ngưng hoạt động", "Chờ xử lý"});
            trangThaiCombo.setSelectedIndex(taiKhoan != null ? taiKhoan.getTrangThai() : 0);
            formPanel.add(trangThaiCombo, gbc);

            if (type.equals("view")) {
                maNVField.setEditable(false);
                tenDangNhapField.setEditable(false);
                matKhauField.setEditable(false);
                confirmMatKhauField.setEditable(false);
                nhomQuyenCombo.setEnabled(false);
                trangThaiCombo.setEnabled(false);
            }

            mainPanel.add(formPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(Color.WHITE);

            if (!type.equals("view")) {
                JButton saveButton = new JButton(type.equals("create") ? "Thêm tài khoản" : "Cập nhật tài khoản");
                saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
                saveButton.setBackground(new Color(66, 139, 202));
                saveButton.setForeground(Color.WHITE);
                saveButton.setBorderPainted(false);
                saveButton.addActionListener(e -> saveTaiKhoan());
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

    private void saveTaiKhoan() {
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        String action = type.equals("create") ? "add" : "edit";
        System.out.println("Checking permission in saveTaiKhoan, maNhomQuyen: " + maNhomQuyen + ", MCN: 12, Action: " + action);
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", action);
        if (!hasPermission) {
            List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
            System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
        }
        System.out.println("Permission check result: " + hasPermission);
        if (maNhomQuyen == 0 || !hasPermission) {
            String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền " + (type.equals("create") ? "thêm" : "sửa") + " tài khoản! Kiểm tra CHITIETQUYEN cho MNQ=" + maNhomQuyen + ", MCN=12, HANHDONG=" + action + ".";
            JOptionPane.showMessageDialog(this, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maNVText = maNVField.getText().trim();
        if (Validation.isEmpty(maNVText) || !Validation.isNumber(maNVText)) {
            JOptionPane.showMessageDialog(this, "Mã NV phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int maNV = Integer.parseInt(maNVText);

        NhanVienBUS nhanVienBUS = new NhanVienBUS(null);
        if (type.equals("create") && nhanVienBUS.getByIndex(nhanVienBUS.getIndexById(maNV)) == null) {
            JOptionPane.showMessageDialog(this, "Mã NV không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tenDangNhap = tenDangNhapField.getText().trim();
        if (Validation.isEmpty(tenDangNhap)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (type.equals("create") && !taiKhoanBUS.checkTDN(tenDangNhap)) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String matKhau = new String(matKhauField.getPassword()).trim();
        String confirmMatKhau = new String(confirmMatKhauField.getPassword()).trim();
        if (type.equals("create") && Validation.isEmpty(matKhau)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!matKhau.equals(confirmMatKhau)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        NhomQuyenBUS nhomQuyenBUSForCombo = new NhomQuyenBUS();
        List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUSForCombo.getAll();
        int selectedMaNhomQuyen = nhomQuyenList.get(nhomQuyenCombo.getSelectedIndex()).getMNQ();
        int trangThai = trangThaiCombo.getSelectedIndex();

        TaiKhoanDTO tk = new TaiKhoanDTO(maNV, tenDangNhap, matKhau, selectedMaNhomQuyen, trangThai);
        boolean success;
        if (type.equals("create")) {
            String hashedPassword = BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12));
            tk.setMatKhau(hashedPassword);
            success = taiKhoanBUS.getTaiKhoanAll().add(tk);
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công (lưu tạm trong bộ nhớ)!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("New account added to in-memory list: " + tk.getTenDangNhap());
        } else {
            if (!matKhau.isEmpty()) {
                tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
            } else {
                tk.setMatKhau(taiKhoan.getMatKhau());
            }
            success = taiKhoanBUS.updateAcc(tk);
            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        gui.loadTaiKhoanData();
        dispose();
    }
}
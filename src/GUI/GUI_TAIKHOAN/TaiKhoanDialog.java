// package GUI_TAIKHOAN;

// import BUS.TaiKhoanBUS;
// import BUS.NhanVienBUS;
// import BUS.NhomQuyenBUS;
// import DTO.TaiKhoanDTO;
// import DTO.NhomQuyenDTO;
// import DTO.ChiTietQuyenDTO;
// import Component.ButtonCustom;
// import Component.HeaderTitle;
// import Component.InputForm;
// import helper.Validation;
// import org.mindrot.jbcrypt.BCrypt;

// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;
// import java.util.List;

// public class TaiKhoanDialog extends JDialog {
//     private TaiKhoanBUS taiKhoanBUS;
//     private TaiKhoanGUI gui;
//     private TaiKhoanDTO taiKhoan;
//     private String type;
//     private int maNhomQuyen;
//     private HeaderTitle titlePage;
//     private JPanel pnCenter, pnBottom, nhomQuyenPanel, trangThaiPanel, matKhauPanel, confirmMatKhauPanel;
//     private InputForm maNVField, tenDangNhapField;
//     private JPasswordField matKhauField, confirmMatKhauField;
//     private JComboBox<String> nhomQuyenCombo, trangThaiCombo;
//     private ButtonCustom btnSave, btnCancel;

//     public TaiKhoanDialog(TaiKhoanBUS taiKhoanBUS, TaiKhoanGUI parent, Component owner, String title, boolean modal, String type, TaiKhoanDTO taiKhoan, int maNhomQuyen) {
//         super(JOptionPane.getFrameForComponent(owner), title, modal);
//         this.taiKhoanBUS = taiKhoanBUS;
//         this.gui = parent;
//         this.taiKhoan = taiKhoan;
//         this.type = type;
//         this.maNhomQuyen = maNhomQuyen;
//         System.out.println("TaiKhoanDialog initialized with maNhomQuyen: " + maNhomQuyen);
//         initComponents(title);
//         setLocationRelativeTo(owner);
//         setVisible(true);
//     }

//     private void initComponents(String title) {
//         setSize(600, 500);
//         setLayout(new BorderLayout());

//         // Tiêu đề
//         titlePage = new HeaderTitle(title.toUpperCase());
//         titlePage.setColor("167AC6");
//         add(titlePage, BorderLayout.NORTH);

//         // Phần chính
//         pnCenter = new JPanel(new BorderLayout());
//         pnCenter.setBackground(Color.WHITE);
//         add(pnCenter, BorderLayout.CENTER);

//         // Phần thông tin tài khoản
//         JPanel pnInfo = new JPanel(new GridLayout(6, 1, 15, 15));
//         pnInfo.setBackground(Color.WHITE);
//         pnInfo.setBorder(new EmptyBorder(20, 20, 20, 20));
//         pnCenter.add(pnInfo, BorderLayout.CENTER);

//         // Khởi tạo các trường nhập liệu
//         maNVField = new InputForm("Mã NV");
//         maNVField.setText(taiKhoan != null ? String.valueOf(taiKhoan.getMaNV()) : "");
//         maNVField.setEditable(type.equals("create"));

//         tenDangNhapField = new InputForm("Tên đăng nhập");
//         tenDangNhapField.setText(taiKhoan != null ? taiKhoan.getTenDangNhap() : "");

//         // Trường mật khẩu
//         matKhauPanel = new JPanel(new GridBagLayout());
//         matKhauPanel.setBackground(Color.WHITE);
//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(5, 5, 5, 5);
//         gbc.fill = GridBagConstraints.HORIZONTAL;
//         JLabel lblMatKhau = new JLabel("Mật khẩu:");
//         Font timesNewRomanBold = Font.getFont("Times New Roman");
//         if (timesNewRomanBold != null) {
//             lblMatKhau.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
//         } else {
//             lblMatKhau.setFont(new Font(Font.SERIF, Font.BOLD, 20));
//         }
//         lblMatKhau.setForeground(new Color(0, 102, 153));
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.weightx = 0.3;
//         matKhauPanel.add(lblMatKhau, gbc);
//         matKhauField = new JPasswordField();
//         Font timesNewRomanPlain = Font.getFont("Times New Roman");
//         if (timesNewRomanPlain != null) {
//             matKhauField.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
//         } else {
//             matKhauField.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
//         }
//         matKhauField.setForeground(new Color(51, 51, 51));
//         matKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
//         matKhauField.setBackground(Color.WHITE);
//         matKhauField.setPreferredSize(new Dimension(200, 32));
//         matKhauField.setText(taiKhoan != null ? "" : "");
//         gbc.gridx = 1;
//         gbc.gridy = 0;
//         gbc.weightx = 0.7;
//         matKhauPanel.add(matKhauField, gbc);

//         // Trường xác nhận mật khẩu
//         confirmMatKhauPanel = new JPanel(new GridBagLayout());
//         confirmMatKhauPanel.setBackground(Color.WHITE);
//         JLabel lblConfirmMatKhau = new JLabel("Xác nhận mật khẩu:");
//         if (timesNewRomanBold != null) {
//             lblConfirmMatKhau.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
//         } else {
//             lblConfirmMatKhau.setFont(new Font(Font.SERIF, Font.BOLD, 20));
//         }
//         lblConfirmMatKhau.setForeground(new Color(0, 102, 153));
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.weightx = 0.3;
//         confirmMatKhauPanel.add(lblConfirmMatKhau, gbc);
//         confirmMatKhauField = new JPasswordField();
//         if (timesNewRomanPlain != null) {
//             confirmMatKhauField.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
//         } else {
//             confirmMatKhauField.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
//         }
//         confirmMatKhauField.setForeground(new Color(51, 51, 51));
//         confirmMatKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
//         confirmMatKhauField.setBackground(Color.WHITE);
//         confirmMatKhauField.setPreferredSize(new Dimension(200, 32));
//         confirmMatKhauField.setText(taiKhoan != null ? "" : "");
//         gbc.gridx = 1;
//         gbc.gridy = 0;
//         gbc.weightx = 0.7;
//         confirmMatKhauPanel.add(confirmMatKhauField, gbc);

//         // Nhóm quyền
//         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
//         List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUS.getAll();
//         String[] nhomQuyenNames = nhomQuyenList.stream().map(NhomQuyenDTO::getTEN).toArray(String[]::new);
//         nhomQuyenCombo = new JComboBox<>(nhomQuyenNames);
//         if (taiKhoan != null) {
//             for (int i = 0; i < nhomQuyenList.size(); i++) {
//                 if (nhomQuyenList.get(i).getMNQ() == taiKhoan.getMaNhomQuyen()) {
//                     nhomQuyenCombo.setSelectedIndex(i);
//                     break;
//                 }
//             }
//         }
//         nhomQuyenPanel = new JPanel(new GridBagLayout());
//         nhomQuyenPanel.setBackground(Color.WHITE);
//         JLabel lblNhomQuyen = new JLabel("Nhóm quyền:");
//         if (timesNewRomanBold != null) {
//             lblNhomQuyen.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
//         } else {
//             lblNhomQuyen.setFont(new Font(Font.SERIF, Font.BOLD, 20));
//         }
//         lblNhomQuyen.setForeground(new Color(0, 102, 153));
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.weightx = 0.3;
//         nhomQuyenPanel.add(lblNhomQuyen, gbc);
//         if (timesNewRomanPlain != null) {
//             nhomQuyenCombo.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
//         } else {
//             nhomQuyenCombo.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
//         }
//         gbc.gridx = 1;
//         gbc.gridy = 0;
//         gbc.weightx = 0.7;
//         nhomQuyenPanel.add(nhomQuyenCombo, gbc);

//         // Trạng thái
//         trangThaiCombo = new JComboBox<>(new String[]{"Hoạt động", "Ngưng hoạt động", "Chờ xử lý"});
//         trangThaiCombo.setSelectedIndex(taiKhoan != null ? taiKhoan.getTrangThai() : 0);
//         trangThaiPanel = new JPanel(new GridBagLayout());
//         trangThaiPanel.setBackground(Color.WHITE);
//         JLabel lblTrangThai = new JLabel("Trạng thái:");
//         if (timesNewRomanBold != null) {
//             lblTrangThai.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
//         } else {
//             lblTrangThai.setFont(new Font(Font.SERIF, Font.BOLD, 20));
//         }
//         lblTrangThai.setForeground(new Color(0, 102, 153));
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.weightx = 0.3;
//         trangThaiPanel.add(lblTrangThai, gbc);
//         if (timesNewRomanPlain != null) {
//             trangThaiCombo.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
//         } else {
//             trangThaiCombo.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
//         }
//         gbc.gridx = 1;
//         gbc.gridy = 0;
//         gbc.weightx = 0.7;
//         trangThaiPanel.add(trangThaiCombo, gbc);

//         // Thêm vào panel
//         pnInfo.add(maNVField);
//         pnInfo.add(tenDangNhapField);
//         pnInfo.add(matKhauPanel);
//         pnInfo.add(confirmMatKhauPanel);
//         pnInfo.add(nhomQuyenPanel);
//         pnInfo.add(trangThaiPanel);

//         // Phần nút
//         pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         pnBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
//         pnBottom.setBackground(Color.WHITE);
//         pnCenter.add(pnBottom, BorderLayout.SOUTH);

//         if (!type.equals("view")) {
//             btnSave = new ButtonCustom(type.equals("create") ? "Thêm tài khoản" : "Cập nhật tài khoản", "success", 14);
//             btnSave.addActionListener(e -> saveTaiKhoan());
//             pnBottom.add(btnSave);
//         }

//         btnCancel = new ButtonCustom(type.equals("view") ? "Đóng" : "Hủy bỏ", "danger", 14);
//         btnCancel.addActionListener(e -> dispose());
//         pnBottom.add(btnCancel);

//         if (type.equals("view")) {
//             maNVField.setEditable(false);
//             tenDangNhapField.setEditable(false);
//             matKhauField.setEditable(false);
//             confirmMatKhauField.setEditable(false);
//             nhomQuyenCombo.setEnabled(false);
//             trangThaiCombo.setEnabled(false);
//         }
//     }

//     private boolean checkInput() {
//         String maNVText = maNVField.getText().trim();
//         if (Validation.isEmpty(maNVText) || !Validation.isNumber(maNVText)) {
//             JOptionPane.showMessageDialog(this, "Mã NV phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return false;
//         }

//         String tenDangNhap = tenDangNhapField.getText().trim();
//         if (Validation.isEmpty(tenDangNhap)) {
//             JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return false;
//         }

//         String matKhau = new String(matKhauField.getPassword()).trim();
//         String confirmMatKhau = new String(confirmMatKhauField.getPassword()).trim();
//         if (type.equals("create") && Validation.isEmpty(matKhau)) {
//             JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return false;
//         }
//         if (!matKhau.equals(confirmMatKhau)) {
//             JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return false;
//         }

//         return true;
//     }

//     private void saveTaiKhoan() {
//         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
//         String action = type.equals("create") ? "add" : "edit";
//         boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", action);
//         if (!hasPermission) {
//             List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
//             System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
//         }
//         System.out.println("Permission check result: " + hasPermission);
//         if (maNhomQuyen == 0 || !hasPermission) {
//             String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền " + (type.equals("create") ? "thêm" : "sửa") + " tài khoản!";
//             JOptionPane.showMessageDialog(this, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         if (!checkInput()) {
//             return;
//         }

//         int maNV = Integer.parseInt(maNVField.getText().trim());
//         NhanVienBUS nhanVienBUS = new NhanVienBUS(null);
//         if (type.equals("create") && nhanVienBUS.getByIndex(nhanVienBUS.getIndexById(maNV)) == null) {
//             JOptionPane.showMessageDialog(this, "Mã NV không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         String tenDangNhap = tenDangNhapField.getText().trim();
//         if (type.equals("create") && !taiKhoanBUS.checkTDN(tenDangNhap)) {
//             JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         String matKhau = new String(matKhauField.getPassword()).trim();
//         NhomQuyenBUS nhomQuyenBUSForCombo = new NhomQuyenBUS();
//         List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUSForCombo.getAll();
//         int selectedMaNhomQuyen = nhomQuyenList.get(nhomQuyenCombo.getSelectedIndex()).getMNQ();
//         int trangThai = trangThaiCombo.getSelectedIndex();

//         TaiKhoanDTO tk = new TaiKhoanDTO(maNV, tenDangNhap, matKhau, selectedMaNhomQuyen, trangThai);
//         boolean success;
//         if (type.equals("create")) {
//             String hashedPassword = BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12));
//             tk.setMatKhau(hashedPassword);
//             success = taiKhoanBUS.getTaiKhoanAll().add(tk);
//             JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//         } else {
//             if (!matKhau.isEmpty()) {
//                 tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
//             } else {
//                 tk.setMatKhau(taiKhoan.getMatKhau());
//             }
//             success = taiKhoanBUS.updateAcc(tk);
//             JOptionPane.showMessageDialog(this, success ? "Cập nhật tài khoản thành công!" : "Cập nhật tài khoản thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
//         }

//         if (success) {
//             gui.loadTaiKhoanData();
//             dispose();
//         }
//     }
// }

package GUI_TAIKHOAN;

import BUS.TaiKhoanBUS;
import BUS.NhanVienBUS;
import BUS.NhomQuyenBUS;
import DTO.TaiKhoanDTO;
import DTO.NhomQuyenDTO;
import DTO.ChiTietQuyenDTO;
import Component.ButtonCustom;
import Component.HeaderTitle;
import Component.InputForm;
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
    private HeaderTitle titlePage;
    private JPanel pnCenter, pnBottom, nhomQuyenPanel, trangThaiPanel, matKhauPanel, confirmMatKhauPanel;
    private InputForm maNVField, tenDangNhapField;
    private JPasswordField matKhauField, confirmMatKhauField;
    private JComboBox<String> nhomQuyenCombo, trangThaiCombo;
    private ButtonCustom btnSave, btnCancel;

    public TaiKhoanDialog(TaiKhoanBUS taiKhoanBUS, TaiKhoanGUI parent, Component owner, String title, boolean modal, String type, TaiKhoanDTO taiKhoan, int maNhomQuyen) {
        super(JOptionPane.getFrameForComponent(owner), title, modal);
        this.taiKhoanBUS = taiKhoanBUS;
        this.gui = parent;
        this.taiKhoan = taiKhoan;
        this.type = type;
        this.maNhomQuyen = maNhomQuyen;
        System.out.println("TaiKhoanDialog initialized with maNhomQuyen: " + maNhomQuyen);
        initComponents(title);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    private void initComponents(String title) {
        setSize(600, 500);
        setLayout(new BorderLayout());

        titlePage = new HeaderTitle(title.toUpperCase());
        titlePage.setColor("167AC6");
        add(titlePage, BorderLayout.NORTH);

        pnCenter = new JPanel(new BorderLayout());
        pnCenter.setBackground(Color.WHITE);
        add(pnCenter, BorderLayout.CENTER);

        JPanel pnInfo = new JPanel(new GridLayout(6, 1, 15, 15));
        pnInfo.setBackground(Color.WHITE);
        pnInfo.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnCenter.add(pnInfo, BorderLayout.CENTER);

        maNVField = new InputForm("Mã NV");
        maNVField.setText(taiKhoan != null ? String.valueOf(taiKhoan.getMaNV()) : "");
        maNVField.setEditable(type.equals("create"));

        tenDangNhapField = new InputForm("Tên đăng nhập");
        tenDangNhapField.setText(taiKhoan != null ? taiKhoan.getTenDangNhap() : "");

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
            lblMatKhau.setFont(new Font(Font.SERIF, Font.BOLD, 20));
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
            matKhauField.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        matKhauField.setForeground(new Color(51, 51, 51));
        matKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        matKhauField.setBackground(Color.WHITE);
        matKhauField.setPreferredSize(new Dimension(200, 32));
        matKhauField.setText(taiKhoan != null ? "" : "");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        matKhauPanel.add(matKhauField, gbc);

        confirmMatKhauPanel = new JPanel(new GridBagLayout());
        confirmMatKhauPanel.setBackground(Color.WHITE);
        JLabel lblConfirmMatKhau = new JLabel("Xác nhận mật khẩu:");
        if (timesNewRomanBold != null) {
            lblConfirmMatKhau.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblConfirmMatKhau.setFont(new Font(Font.SERIF, Font.BOLD, 20));
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
            confirmMatKhauField.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        confirmMatKhauField.setForeground(new Color(51, 51, 51));
        confirmMatKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        confirmMatKhauField.setBackground(Color.WHITE);
        confirmMatKhauField.setPreferredSize(new Dimension(200, 32));
        confirmMatKhauField.setText(taiKhoan != null ? "" : "");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        confirmMatKhauPanel.add(confirmMatKhauField, gbc);

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
        nhomQuyenPanel = new JPanel(new GridBagLayout());
        nhomQuyenPanel.setBackground(Color.WHITE);
        JLabel lblNhomQuyen = new JLabel("Nhóm quyền:");
        if (timesNewRomanBold != null) {
            lblNhomQuyen.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblNhomQuyen.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        }
        lblNhomQuyen.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        nhomQuyenPanel.add(lblNhomQuyen, gbc);
        if (timesNewRomanPlain != null) {
            nhomQuyenCombo.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            nhomQuyenCombo.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        nhomQuyenPanel.add(nhomQuyenCombo, gbc);

        trangThaiCombo = new JComboBox<>(new String[]{"Hoạt động", "Ngưng hoạt động", "Chờ xử lý"});
        trangThaiCombo.setSelectedIndex(taiKhoan != null ? taiKhoan.getTrangThai() : 0);
        trangThaiPanel = new JPanel(new GridBagLayout());
        trangThaiPanel.setBackground(Color.WHITE);
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        if (timesNewRomanBold != null) {
            lblTrangThai.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblTrangThai.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        }
        lblTrangThai.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        trangThaiPanel.add(lblTrangThai, gbc);
        if (timesNewRomanPlain != null) {
            trangThaiCombo.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            trangThaiCombo.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        trangThaiPanel.add(trangThaiCombo, gbc);

        pnInfo.add(maNVField);
        pnInfo.add(tenDangNhapField);
        pnInfo.add(matKhauPanel);
        pnInfo.add(confirmMatKhauPanel);
        pnInfo.add(nhomQuyenPanel);
        pnInfo.add(trangThaiPanel);

        pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
        pnBottom.setBackground(Color.WHITE);
        pnCenter.add(pnBottom, BorderLayout.SOUTH);

        if (!type.equals("view")) {
            btnSave = new ButtonCustom(type.equals("create") ? "Thêm tài khoản" : "Cập nhật tài khoản", "success", 14);
            btnSave.addActionListener(e -> saveTaiKhoan());
            pnBottom.add(btnSave);
        }

        btnCancel = new ButtonCustom(type.equals("view") ? "Đóng" : "Hủy bỏ", "danger", 14);
        btnCancel.addActionListener(e -> dispose());
        pnBottom.add(btnCancel);

        if (type.equals("view")) {
            maNVField.setEditable(false);
            tenDangNhapField.setEditable(false);
            matKhauField.setEditable(false);
            confirmMatKhauField.setEditable(false);
            nhomQuyenCombo.setEnabled(false);
            trangThaiCombo.setEnabled(false);
        }
    }

    private boolean checkInput() {
        String maNVText = maNVField.getText().trim();
        if (Validation.isEmpty(maNVText) || !Validation.isNumber(maNVText)) {
            JOptionPane.showMessageDialog(this, "Mã NV phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String tenDangNhap = tenDangNhapField.getText().trim();
        if (Validation.isEmpty(tenDangNhap)) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String matKhau = new String(matKhauField.getPassword()).trim();
        String confirmMatKhau = new String(confirmMatKhauField.getPassword()).trim();
        if (type.equals("create") && Validation.isEmpty(matKhau)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!matKhau.equals(confirmMatKhau)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // private void saveTaiKhoan() {
    //     NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
    //     String action = type.equals("create") ? "create" : "update"; // Changed from "add"/"edit"
    //     boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", action);
    //     if (!hasPermission) {
    //         List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
    //         System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
    //     }
    //     System.out.println("Permission check result: " + hasPermission);
    //     if (maNhomQuyen == 0 || !hasPermission) {
    //         String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền " + (type.equals("create") ? "thêm" : "sửa") + " tài khoản!";
    //         JOptionPane.showMessageDialog(this, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
    //         return;
    //     }

    //     if (!checkInput()) {
    //         return;
    //     }

    //     int maNV = Integer.parseInt(maNVField.getText().trim());
    //     NhanVienBUS nhanVienBUS = new NhanVienBUS(null);
    //     if (type.equals("create") && nhanVienBUS.getByIndex(nhanVienBUS.getIndexById(maNV)) == null) {
    //         JOptionPane.showMessageDialog(this, "Mã NV không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    //         return;
    //     }

    //     String tenDangNhap = tenDangNhapField.getText().trim();
    //     if (type.equals("create") && !taiKhoanBUS.checkTDN(tenDangNhap)) {
    //         JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    //         return;
    //     }

    //     String matKhau = new String(matKhauField.getPassword()).trim();
    //     NhomQuyenBUS nhomQuyenBUSForCombo = new NhomQuyenBUS();
    //     List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUSForCombo.getAll();
    //     int selectedMaNhomQuyen = nhomQuyenList.get(nhomQuyenCombo.getSelectedIndex()).getMNQ();
    //     int trangThai = trangThaiCombo.getSelectedIndex();

    //     TaiKhoanDTO tk = new TaiKhoanDTO(maNV, tenDangNhap, matKhau, selectedMaNhomQuyen, trangThai);
    //     boolean success;
    //     if (type.equals("create")) {
    //         String hashedPassword = BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12));
    //         tk.setMatKhau(hashedPassword);
    //         success = taiKhoanBUS.getTaiKhoanAll().add(tk);
    //         JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
    //     } else {
    //         if (!matKhau.isEmpty()) {
    //             tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
    //         } else {
    //             tk.setMatKhau(taiKhoan.getMatKhau());
    //         }
    //         success = taiKhoanBUS.updateAcc(tk);
    //         JOptionPane.showMessageDialog(this, success ? "Cập nhật tài khoản thành công!" : "Cập nhật tài khoản thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    //     }

    //     if (success) {
    //         gui.loadTaiKhoanData();
    //         dispose();
    //     }
    // }
    private void saveTaiKhoan() {
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        String action = type.equals("create") ? "create" : "update";
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", action);
        if (!hasPermission) {
            List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
            System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
        }
        System.out.println("Permission check result: " + hasPermission);
        if (maNhomQuyen == 0 || !hasPermission) {
            String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền " + (type.equals("create") ? "thêm" : "sửa") + " tài khoản!";
            JOptionPane.showMessageDialog(this, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (!checkInput()) {
            return;
        }
    
        int maNV = Integer.parseInt(maNVField.getText().trim());
        NhanVienBUS nhanVienBUS = new NhanVienBUS(null);
        if (type.equals("create") && nhanVienBUS.getByIndex(nhanVienBUS.getIndexById(maNV)) == null) {
            JOptionPane.showMessageDialog(this, "Mã NV không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String tenDangNhap = tenDangNhapField.getText().trim();
        if (type.equals("create") && !taiKhoanBUS.checkTDN(tenDangNhap)) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String matKhau = new String(matKhauField.getPassword()).trim();
        NhomQuyenBUS nhomQuyenBUSForCombo = new NhomQuyenBUS();
        List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUSForCombo.getAll();
        int selectedMaNhomQuyen = nhomQuyenList.get(nhomQuyenCombo.getSelectedIndex()).getMNQ();
        int trangThai = trangThaiCombo.getSelectedIndex();
    
        TaiKhoanDTO tk = new TaiKhoanDTO(maNV, tenDangNhap, matKhau, selectedMaNhomQuyen, trangThai);
        boolean success;
        if (type.equals("create")) {
            // Gọi phương thức addAcc để thêm tài khoản vào database
            success = taiKhoanBUS.addAcc(tk);
            JOptionPane.showMessageDialog(this, success ? "Thêm tài khoản thành công!" : "Thêm tài khoản thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        } else {
            // Gọi phương thức updateAcc để cập nhật tài khoản
            if (!matKhau.isEmpty()) {
                tk.setMatKhau(matKhau); // Mật khẩu sẽ được mã hóa trong updateAcc
            } else {
                tk.setMatKhau(taiKhoan.getMatKhau());
            }
            success = taiKhoanBUS.updateAcc(tk);
            JOptionPane.showMessageDialog(this, success ? "Cập nhật tài khoản thành công!" : "Cập nhật tài khoản thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        }
    
        if (success) {
            // Làm mới danh sách tài khoản từ database
            taiKhoanBUS.getTaiKhoanAll(); // Đảm bảo listTaiKhoan được cập nhật
            gui.loadTaiKhoanData(); // Tải lại dữ liệu vào bảng
            dispose();
        }
    }
}
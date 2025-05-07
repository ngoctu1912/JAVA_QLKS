// // package GUI_TAIKHOAN;

// // import BUS.TaiKhoanBUS;
// // import BUS.NhanVienBUS;
// // import BUS.NhomQuyenBUS;
// // import DTO.TaiKhoanDTO;
// // import DTO.NhomQuyenDTO;
// // import DTO.ChiTietQuyenDTO;
// // import DTO.NhanVienDTO;
// // import Component.ButtonCustom;
// // import Component.HeaderTitle;
// // import Component.InputForm;
// // import helper.Validation;
// // import org.mindrot.jbcrypt.BCrypt;

// // import javax.swing.*;
// // import javax.swing.border.EmptyBorder;
// // import java.awt.*;
// // import java.util.ArrayList;
// // import java.util.List;

// // public class TaiKhoanDialog extends JDialog {
// //     private TaiKhoanBUS taiKhoanBUS;
// //     private TaiKhoanGUI gui;
// //     private TaiKhoanDTO taiKhoan;
// //     private String type;
// //     private int maNhomQuyen;
// //     private HeaderTitle titlePage;
// //     private JPanel pnCenter, pnBottom, nhomQuyenPanel, trangThaiPanel, matKhauPanel, confirmMatKhauPanel, maNVPanel;
// //     private InputForm maNVField, tenDangNhapField;
// //     private JPasswordField matKhauField, confirmMatKhauField;
// //     private JComboBox<String> nhomQuyenCombo, trangThaiCombo, maNVCombo;
// //     private ButtonCustom btnSave, btnCancel;

// //     public TaiKhoanDialog(TaiKhoanBUS taiKhoanBUS, TaiKhoanGUI parent, Component owner, String title, boolean modal, String type, TaiKhoanDTO taiKhoan, int maNhomQuyen) {
// //         super(JOptionPane.getFrameForComponent(owner), title, modal);
// //         this.taiKhoanBUS = taiKhoanBUS;
// //         this.gui = parent;
// //         this.taiKhoan = taiKhoan;
// //         this.type = type;
// //         this.maNhomQuyen = maNhomQuyen;
// //         System.out.println("TaiKhoanDialog initialized with maNhomQuyen: " + maNhomQuyen);
// //         initComponents(title);
// //         setLocationRelativeTo(owner);
// //         setVisible(true);
// //     }

// //     private void initComponents(String title) {
// //         setSize(600, 500);
// //         setLayout(new BorderLayout());

// //         titlePage = new HeaderTitle(title.toUpperCase());
// //         titlePage.setColor("167AC6");
// //         add(titlePage, BorderLayout.NORTH);

// //         pnCenter = new JPanel(new BorderLayout());
// //         pnCenter.setBackground(Color.WHITE);
// //         add(pnCenter, BorderLayout.CENTER);

// //         JPanel pnInfo = new JPanel(new GridLayout(6, 1, 15, 15));
// //         pnInfo.setBackground(Color.WHITE);
// //         pnInfo.setBorder(new EmptyBorder(20, 20, 20, 20));
// //         pnCenter.add(pnInfo, BorderLayout.CENTER);

// //         if (type.equals("create")) {
// //             maNVPanel = new JPanel(new GridBagLayout());
// //             maNVPanel.setBackground(Color.WHITE);
// //             GridBagConstraints gbc = new GridBagConstraints();
// //             gbc.insets = new Insets(5, 5, 5, 5);
// //             gbc.fill = GridBagConstraints.HORIZONTAL;
// //             JLabel lblMaNV = new JLabel("Mã NV:");
// //             lblMaNV.setFont(new Font("Arial", Font.BOLD, 20));
// //             lblMaNV.setForeground(new Color(0, 102, 153));
// //             gbc.gridx = 0;
// //             gbc.gridy = 0;
// //             gbc.weightx = 0.3;
// //             maNVPanel.add(lblMaNV, gbc);

// //             NhanVienBUS nhanVienBUS = new NhanVienBUS(null);
// //             ArrayList<NhanVienDTO> allNhanVien = nhanVienBUS.getAll();
// //             ArrayList<TaiKhoanDTO> allTaiKhoan;
// //             try {
// //                 allTaiKhoan = taiKhoanBUS.getTaiKhoanAll();
// //             } catch (Exception e) {
// //                 JOptionPane.showMessageDialog(this, "Lỗi tải danh sách tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 dispose();
// //                 return;
// //             }
// //             List<String> nhanVienWithoutTaiKhoan = new ArrayList<>();
// //             for (NhanVienDTO nv : allNhanVien) {
// //                 boolean hasTaiKhoan = false;
// //                 for (TaiKhoanDTO tk : allTaiKhoan) {
// //                     if (tk.getMaNV() == nv.getMNV()) {
// //                         hasTaiKhoan = true;
// //                         break;
// //                     }
// //                 }
// //                 if (!hasTaiKhoan) {
// //                     nhanVienWithoutTaiKhoan.add(nv.getMNV() + " - " + nv.getHOTEN());
// //                 }
// //             }

// //             if (nhanVienWithoutTaiKhoan.isEmpty()) {
// //                 JOptionPane.showMessageDialog(this, "Không còn nhân viên nào chưa có tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
// //                 dispose();
// //                 return;
// //             }

// //             maNVCombo = new JComboBox<>(nhanVienWithoutTaiKhoan.toArray(new String[0]));
// //             maNVCombo.setFont(new Font("Arial", Font.PLAIN, 20));
// //             maNVCombo.setPreferredSize(new Dimension(200, 32));
// //             gbc.gridx = 1;
// //             gbc.gridy = 0;
// //             gbc.weightx = 0.7;
// //             maNVPanel.add(maNVCombo, gbc);
// //         } else {
// //             maNVField = new InputForm("Mã NV");
// //             maNVField.setText(taiKhoan != null ? String.valueOf(taiKhoan.getMaNV()) : "");
// //             maNVField.setEditable(false);
// //         }

// //         tenDangNhapField = new InputForm("Tên đăng nhập");
// //         tenDangNhapField.setText(taiKhoan != null ? taiKhoan.getTenDangNhap() : "");
// //         if (type.equals("view")) {
// //             tenDangNhapField.setEditable(false);
// //         }

// //         matKhauPanel = new JPanel(new GridBagLayout());
// //         matKhauPanel.setBackground(Color.WHITE);
// //         GridBagConstraints gbc = new GridBagConstraints();
// //         gbc.insets = new Insets(5, 5, 5, 5);
// //         gbc.fill = GridBagConstraints.HORIZONTAL;
// //         JLabel lblMatKhau = new JLabel("Mật khẩu:");
// //         lblMatKhau.setFont(new Font("Arial", Font.BOLD, 20));
// //         lblMatKhau.setForeground(new Color(0, 102, 153));
// //         gbc.gridx = 0;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.3;
// //         matKhauPanel.add(lblMatKhau, gbc);
// //         matKhauField = new JPasswordField();
// //         matKhauField.setFont(new Font("Arial", Font.PLAIN, 20));
// //         matKhauField.setForeground(new Color(51, 51, 51));
// //         matKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
// //         matKhauField.setBackground(Color.WHITE);
// //         matKhauField.setPreferredSize(new Dimension(200, 32));
// //         matKhauField.setText("********"); // Placeholder for view mode
// //         gbc.gridx = 1;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.7;
// //         matKhauPanel.add(matKhauField, gbc);
// //         if (type.equals("update")) {
// //             JLabel lblNote = new JLabel("(Để trống để giữ mật khẩu hiện tại)");
// //             lblNote.setFont(new Font("Arial", Font.PLAIN, 12));
// //             lblNote.setForeground(Color.GRAY);
// //             gbc.gridx = 1;
// //             gbc.gridy = 1;
// //             gbc.weightx = 0.7;
// //             matKhauPanel.add(lblNote, gbc);
// //         }

// //         confirmMatKhauPanel = new JPanel(new GridBagLayout());
// //         confirmMatKhauPanel.setBackground(Color.WHITE);
// //         JLabel lblConfirmMatKhau = new JLabel("Xác nhận mật khẩu:");
// //         lblConfirmMatKhau.setFont(new Font("Arial", Font.BOLD, 20));
// //         lblConfirmMatKhau.setForeground(new Color(0, 102, 153));
// //         gbc.gridx = 0;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.3;
// //         confirmMatKhauPanel.add(lblConfirmMatKhau, gbc);
// //         confirmMatKhauField = new JPasswordField();
// //         confirmMatKhauField.setFont(new Font("Arial", Font.PLAIN, 20));
// //         confirmMatKhauField.setForeground(new Color(51, 51, 51));
// //         confirmMatKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
// //         confirmMatKhauField.setBackground(Color.WHITE);
// //         confirmMatKhauField.setPreferredSize(new Dimension(200, 32));
// //         confirmMatKhauField.setText("********"); // Placeholder for view mode
// //         gbc.gridx = 1;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.7;
// //         confirmMatKhauPanel.add(confirmMatKhauField, gbc);

// //         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
// //         List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUS.getAll();
// //         String[] nhomQuyenNames = nhomQuyenList.stream().map(NhomQuyenDTO::getTEN).toArray(String[]::new);
// //         nhomQuyenCombo = new JComboBox<>(nhomQuyenNames);
// //         if (taiKhoan != null) {
// //             for (int i = 0; i < nhomQuyenList.size(); i++) {
// //                 if (nhomQuyenList.get(i).getMNQ() == taiKhoan.getMaNhomQuyen()) {
// //                     nhomQuyenCombo.setSelectedIndex(i);
// //                     break;
// //                 }
// //             }
// //         }
// //         nhomQuyenPanel = new JPanel(new GridBagLayout());
// //         nhomQuyenPanel.setBackground(Color.WHITE);
// //         JLabel lblNhomQuyen = new JLabel("Nhóm quyền:");
// //         lblNhomQuyen.setFont(new Font("Arial", Font.BOLD, 20));
// //         lblNhomQuyen.setForeground(new Color(0, 102, 153));
// //         gbc.gridx = 0;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.3;
// //         nhomQuyenPanel.add(lblNhomQuyen, gbc);
// //         nhomQuyenCombo.setFont(new Font("Arial", Font.PLAIN, 20));
// //         gbc.gridx = 1;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.7;
// //         nhomQuyenPanel.add(nhomQuyenCombo, gbc);

// //         String[] trangThaiOptions = type.equals("create") ? new String[]{"Hoạt động", "Ngưng hoạt động"} : new String[]{"Hoạt động", "Ngưng hoạt động", "Chờ xử lý"};
// //         trangThaiCombo = new JComboBox<>(trangThaiOptions);
// //         if (taiKhoan != null) {
// //             switch (taiKhoan.getTrangThai()) {
// //                 case 1: trangThaiCombo.setSelectedIndex(0); break; // Hoạt động
// //                 case 0: trangThaiCombo.setSelectedIndex(1); break; // Ngưng hoạt động
// //                 case 2: trangThaiCombo.setSelectedIndex(2); break; // Chờ xử lý
// //                 default: trangThaiCombo.setSelectedIndex(0);
// //             }
// //         } else {
// //             trangThaiCombo.setSelectedIndex(0);
// //         }
// //         trangThaiPanel = new JPanel(new GridBagLayout());
// //         trangThaiPanel.setBackground(Color.WHITE);
// //         JLabel lblTrangThai = new JLabel("Trạng thái:");
// //         lblTrangThai.setFont(new Font("Arial", Font.BOLD, 20));
// //         lblTrangThai.setForeground(new Color(0, 102, 153));
// //         gbc.gridx = 0;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.3;
// //         trangThaiPanel.add(lblTrangThai, gbc);
// //         trangThaiCombo.setFont(new Font("Arial", Font.PLAIN, 20));
// //         gbc.gridx = 1;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.7;
// //         trangThaiPanel.add(trangThaiCombo, gbc);

// //         if (type.equals("create")) {
// //             pnInfo.add(maNVPanel);
// //         } else {
// //             pnInfo.add(maNVField);
// //         }
// //         pnInfo.add(tenDangNhapField);
// //         pnInfo.add(matKhauPanel);
// //         pnInfo.add(confirmMatKhauPanel);
// //         pnInfo.add(nhomQuyenPanel);
// //         pnInfo.add(trangThaiPanel);

// //         pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
// //         pnBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
// //         pnBottom.setBackground(Color.WHITE);
// //         pnCenter.add(pnBottom, BorderLayout.SOUTH);

// //         if (!type.equals("view")) {
// //             btnSave = new ButtonCustom(type.equals("create") ? "Thêm tài khoản" : "Cập nhật tài khoản", "success", 14);
// //             btnSave.addActionListener(e -> saveTaiKhoan());
// //             pnBottom.add(btnSave);
// //         }

// //         btnCancel = new ButtonCustom(type.equals("view") ? "Đóng" : "Hủy bỏ", "danger", 14);
// //         btnCancel.addActionListener(e -> dispose());
// //         pnBottom.add(btnCancel);

// //         // Ensure read-only for view mode
// //         if (type.equals("view")) {
// //             if (maNVField != null) {
// //                 maNVField.setEditable(false);
// //                 maNVField.setEnabled(false); // Disable to prevent any interaction
// //             }
// //             tenDangNhapField.setEditable(false);
// //             tenDangNhapField.setEnabled(false);
// //             matKhauField.setEditable(false);
// //             matKhauField.setEnabled(false);
// //             confirmMatKhauField.setEditable(false);
// //             confirmMatKhauField.setEnabled(false);
// //             nhomQuyenCombo.setEnabled(false);
// //             trangThaiCombo.setEnabled(false);
// //         }
// //     }

// //     private boolean checkInput() {
// //         String maNVText;
// //         if (type.equals("create")) {
// //             if (maNVCombo.getSelectedItem() == null) {
// //                 JOptionPane.showMessageDialog(this, "Vui lòng chọn mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return false;
// //             }
// //             maNVText = maNVCombo.getSelectedItem().toString().split(" - ")[0];
// //         } else {
// //             maNVText = maNVField.getText().trim();
// //         }
// //         if (Validation.isEmpty(maNVText) || !Validation.isNumber(maNVText)) {
// //             JOptionPane.showMessageDialog(this, "Mã NV phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //             return false;
// //         }

// //         String tenDangNhap = tenDangNhapField.getText().trim();
// //         if (Validation.isEmpty(tenDangNhap)) {
// //             JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //             return false;
// //         }

// //         String matKhau = new String(matKhauField.getPassword()).trim();
// //         String confirmMatKhau = new String(confirmMatKhauField.getPassword()).trim();
// //         if (type.equals("create")) {
// //             if (Validation.isEmpty(matKhau)) {
// //                 JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return false;
// //             }
// //             if (!matKhau.equals(confirmMatKhau)) {
// //                 JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return false;
// //             }
// //         } else if (!matKhau.isEmpty() || !confirmMatKhau.isEmpty()) {
// //             // If user enters a password during update, validate it
// //             if (!matKhau.equals(confirmMatKhau)) {
// //                 JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return false;
// //             }
// //         }

// //         return true;
// //     }

// //     private void saveTaiKhoan() {
// //         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
// //         String action = type.equals("create") ? "create" : "update";
// //         boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "12", action);
// //         if (!hasPermission) {
// //             List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
// //             System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
// //         }
// //         System.out.println("Permission check result: " + hasPermission);
// //         if (maNhomQuyen == 0 || !hasPermission) {
// //             String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền " + (type.equals("create") ? "thêm" : "sửa") + " tài khoản!";
// //             JOptionPane.showMessageDialog(this, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
// //             return;
// //         }

// //         if (!checkInput()) {
// //             return;
// //         }

// //         int maNV;
// //         if (type.equals("create")) {
// //             if (maNVCombo.getSelectedItem() == null) {
// //                 JOptionPane.showMessageDialog(this, "Vui lòng chọn mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return;
// //             }
// //             maNV = Integer.parseInt(maNVCombo.getSelectedItem().toString().split(" - ")[0]);
// //         } else {
// //             maNV = Integer.parseInt(maNVField.getText().trim());
// //         }

// //         NhanVienBUS nhanVienBUS = new NhanVienBUS(null);
// //         if (type.equals("create") && nhanVienBUS.getByIndex(nhanVienBUS.getIndexById(maNV)) == null) {
// //             JOptionPane.showMessageDialog(this, "Mã NV không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //             return;
// //         }

// //         String tenDangNhap = tenDangNhapField.getText().trim();
// //         if (type.equals("create") && !taiKhoanBUS.checkTDN(tenDangNhap)) {
// //             JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //             return;
// //         }

// //         String matKhau = new String(matKhauField.getPassword()).trim();
// //         NhomQuyenBUS nhomQuyenBUSForCombo = new NhomQuyenBUS();
// //         List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUSForCombo.getAll();
// //         int selectedMaNhomQuyen = nhomQuyenList.get(nhomQuyenCombo.getSelectedIndex()).getMNQ();
// //         int trangThai;
// //         switch (trangThaiCombo.getSelectedIndex()) {
// //             case 0: trangThai = 1; break; // Hoạt động
// //             case 1: trangThai = 0; break; // Ngưng hoạt động
// //             case 2: trangThai = 2; break; // Chờ xử lý
// //             default:
// //                 JOptionPane.showMessageDialog(this, "Trạng thái không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return;
// //         }

// //         TaiKhoanDTO tk = new TaiKhoanDTO(maNV, tenDangNhap, matKhau, selectedMaNhomQuyen, trangThai);
// //         boolean success;
// //         if (type.equals("create")) {
// //             System.out.println("Adding new account: " + tenDangNhap + ", Raw Password: " + matKhau);
// //             tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
// //             success = taiKhoanBUS.addAcc(tk);
// //             System.out.println("AddAcc result: " + success);
// //             JOptionPane.showMessageDialog(this, success ? "Thêm tài khoản thành công!" : "Thêm tài khoản thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
// //         } else {
// //             System.out.println("Updating account: " + tenDangNhap + ", Raw Password: " + (matKhau.isEmpty() ? "unchanged" : matKhau));
// //             if (!matKhau.isEmpty()) {
// //                 tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
// //             } else {
// //                 tk.setMatKhau(taiKhoan.getMatKhau());
// //             }
// //             success = taiKhoanBUS.updateAcc(tk);
// //             System.out.println("UpdateAcc result: " + success);
// //             JOptionPane.showMessageDialog(this, success ? "Cập nhật tài khoản thành công!" : "Cập nhật tài khoản thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
// //         }

// //         if (success) {
// //             taiKhoanBUS.getTaiKhoanAll();
// //             gui.loadTaiKhoanData();
// //             dispose();
// //         }
// //     }
// // }

// package GUI_TAIKHOAN;

// import BUS.TaiKhoanBUS;
// import BUS.NhanVienBUS;
// import BUS.NhomQuyenBUS;
// import DTO.TaiKhoanDTO;
// import DTO.NhomQuyenDTO;
// import DTO.ChiTietQuyenDTO;
// import DTO.NhanVienDTO;
// import Component.ButtonCustom;
// import Component.HeaderTitle;
// import Component.InputForm;
// import helper.Validation;
// import org.mindrot.jbcrypt.BCrypt;

// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;
// import java.util.ArrayList;
// import java.util.List;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import javax.swing.ImageIcon;

// public class TaiKhoanDialog extends JDialog {
//     private TaiKhoanBUS taiKhoanBUS;
//     private TaiKhoanGUI gui;
//     private TaiKhoanDTO taiKhoan;
//     private String type;
//     private int maNhomQuyen;
//     private HeaderTitle titlePage;
//     private JPanel pnCenter, pnBottom, nhomQuyenPanel, trangThaiPanel, matKhauPanel, confirmMatKhauPanel, maNVPanel;
//     private InputForm maNVField, tenDangNhapField;
//     private JPasswordField matKhauField, confirmMatKhauField;
//     private JComboBox<String> nhomQuyenCombo, trangThaiCombo, maNVCombo;
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

//         titlePage = new HeaderTitle(title.toUpperCase());
//         titlePage.setColor("167AC6");
//         add(titlePage, BorderLayout.NORTH);

//         pnCenter = new JPanel(new BorderLayout());
//         pnCenter.setBackground(Color.WHITE);
//         add(pnCenter, BorderLayout.CENTER);

//         JPanel pnInfo = new JPanel(new GridLayout(6, 1, 15, 15));
//         pnInfo.setBackground(Color.WHITE);
//         pnInfo.setBorder(new EmptyBorder(20, 20, 20, 20));
//         pnCenter.add(pnInfo, BorderLayout.CENTER);

//         if (type.equals("create")) {
//             maNVPanel = new JPanel(new GridBagLayout());
//             maNVPanel.setBackground(Color.WHITE);
//             GridBagConstraints gbc = new GridBagConstraints();
//             gbc.insets = new Insets(5, 5, 5, 5);
//             gbc.fill = GridBagConstraints.HORIZONTAL;
//             JLabel lblMaNV = new JLabel("Mã NV:");
//             lblMaNV.setFont(new Font("Arial", Font.BOLD, 20));
//             lblMaNV.setForeground(new Color(0, 102, 153));
//             gbc.gridx = 0;
//             gbc.gridy = 0;
//             gbc.weightx = 0.3;
//             maNVPanel.add(lblMaNV, gbc);

//             NhanVienBUS nhanVienBUS = new NhanVienBUS(null);
//             ArrayList<NhanVienDTO> allNhanVien = nhanVienBUS.getAll();
//             ArrayList<TaiKhoanDTO> allTaiKhoan;
//             try {
//                 allTaiKhoan = taiKhoanBUS.getTaiKhoanAll();
//             } catch (Exception e) {
//                 JOptionPane.showMessageDialog(this, "Lỗi tải danh sách tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 dispose();
//                 return;
//             }
//             List<String> nhanVienWithoutTaiKhoan = new ArrayList<>();
//             for (NhanVienDTO nv : allNhanVien) {
//                 boolean hasTaiKhoan = false;
//                 for (TaiKhoanDTO tk : allTaiKhoan) {
//                     if (tk.getMaNV() == nv.getMNV()) {
//                         hasTaiKhoan = true;
//                         break;
//                     }
//                 }
//                 if (!hasTaiKhoan) {
//                     nhanVienWithoutTaiKhoan.add(nv.getMNV() + " - " + nv.getHOTEN());
//                 }
//             }

//             if (nhanVienWithoutTaiKhoan.isEmpty()) {
//                 JOptionPane.showMessageDialog(this, "Không còn nhân viên nào chưa có tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//                 dispose();
//                 return;
//             }

//             maNVCombo = new JComboBox<>(nhanVienWithoutTaiKhoan.toArray(new String[0]));
//             maNVCombo.setFont(new Font("Arial", Font.PLAIN, 20));
//             maNVCombo.setPreferredSize(new Dimension(200, 32));
//             gbc.gridx = 1;
//             gbc.gridy = 0;
//             gbc.weightx = 0.7;
//             maNVPanel.add(maNVCombo, gbc);
//         } else {
//             maNVField = new InputForm("Mã NV");
//             maNVField.setText(taiKhoan != null ? String.valueOf(taiKhoan.getMaNV()) : "");
//             maNVField.setEditable(false);
//         }

//         tenDangNhapField = new InputForm("Tên đăng nhập");
//         tenDangNhapField.setText(taiKhoan != null ? taiKhoan.getTenDangNhap() : "");
//         if (type.equals("view")) {
//             tenDangNhapField.setEditable(false);
//         }

//         // Khởi tạo matKhauPanel với biểu tượng mắt
//         matKhauPanel = new JPanel(new GridBagLayout());
//         matKhauPanel.setBackground(Color.WHITE);
//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(5, 5, 5, 5);
//         gbc.fill = GridBagConstraints.HORIZONTAL;

//         JLabel lblMatKhau = new JLabel("Mật khẩu:");
//         lblMatKhau.setFont(new Font("Arial", Font.BOLD, 20));
//         lblMatKhau.setForeground(new Color(0, 102, 153));
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.weightx = 0.3;
//         matKhauPanel.add(lblMatKhau, gbc);

//         matKhauField = new JPasswordField();
//         matKhauField.setFont(new Font("Arial", Font.PLAIN, 20));
//         matKhauField.setForeground(new Color(51, 51, 51));
//         matKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
//         matKhauField.setBackground(Color.WHITE);
//         matKhauField.setPreferredSize(new Dimension(200, 32));
//         if (type.equals("view")) {
//             matKhauField.setText("********");
//             matKhauField.setEditable(false);
//             matKhauField.setEnabled(false);
//         }
//         gbc.gridx = 1;
//         gbc.gridy = 0;
//         gbc.weightx = 0.7;
//         matKhauPanel.add(matKhauField, gbc);

//         // Thêm biểu tượng mắt cho matKhauField
//         ImageIcon openEyeIcon = new ImageIcon("src/icons/openeye.png");
//         ImageIcon closeEyeIcon = new ImageIcon("src/icons/closeeye.png");
//         JLabel eyeLabelMatKhau = new JLabel(closeEyeIcon);
//         eyeLabelMatKhau.setCursor(new Cursor(Cursor.HAND_CURSOR));
//         boolean[] isPasswordVisibleMatKhau = {false};
//         eyeLabelMatKhau.addMouseListener(new MouseAdapter() {
//             @Override
//             public void mouseClicked(MouseEvent e) {
//                 if (type.equals("view")) return;
//                 isPasswordVisibleMatKhau[0] = !isPasswordVisibleMatKhau[0];
//                 if (isPasswordVisibleMatKhau[0]) {
//                     matKhauField.setEchoChar((char) 0);
//                     eyeLabelMatKhau.setIcon(openEyeIcon);
//                 } else {
//                     matKhauField.setEchoChar('\u2022');
//                     eyeLabelMatKhau.setIcon(closeEyeIcon);
//                 }
//             }
//         });
//         gbc.gridx = 2;
//         gbc.gridy = 0;
//         gbc.weightx = 0.0;
//         gbc.fill = GridBagConstraints.NONE;
//         matKhauPanel.add(eyeLabelMatKhau, gbc);

//         if (type.equals("update")) {
//             JLabel lblNote = new JLabel("(Để trống để giữ mật khẩu hiện tại)");
//             lblNote.setFont(new Font("Arial", Font.PLAIN, 12));
//             lblNote.setForeground(Color.GRAY);
//             gbc.gridx = 1;
//             gbc.gridy = 1;
//             gbc.weightx = 0.7;
//             gbc.gridwidth = 2;
//             matKhauPanel.add(lblNote, gbc);
//         }

//         // Khởi tạo confirmMatKhauPanel với biểu tượng mắt
//         confirmMatKhauPanel = new JPanel(new GridBagLayout());
//         confirmMatKhauPanel.setBackground(Color.WHITE);
//         gbc = new GridBagConstraints();
//         gbc.insets = new Insets(5, 5, 5, 5);
//         gbc.fill = GridBagConstraints.HORIZONTAL;

//         JLabel lblConfirmMatKhau = new JLabel("Xác nhận mật khẩu:");
//         lblConfirmMatKhau.setFont(new Font("Arial", Font.BOLD, 20));
//         lblConfirmMatKhau.setForeground(new Color(0, 102, 153));
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.weightx = 0.3;
//         confirmMatKhauPanel.add(lblConfirmMatKhau, gbc);

//         confirmMatKhauField = new JPasswordField();
//         confirmMatKhauField.setFont(new Font("Arial", Font.PLAIN, 20));
//         confirmMatKhauField.setForeground(new Color(51, 51, 51));
//         confirmMatKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
//         confirmMatKhauField.setBackground(Color.WHITE);
//         confirmMatKhauField.setPreferredSize(new Dimension(200, 32));
//         if (type.equals("view")) {
//             confirmMatKhauField.setText("********");
//             confirmMatKhauField.setEditable(false);
//             confirmMatKhauField.setEnabled(false);
//         }
//         gbc.gridx = 1;
//         gbc.gridy = 0;
//         gbc.weightx = 0.7;
//         confirmMatKhauPanel.add(confirmMatKhauField, gbc);

//         // Thêm biểu tượng mắt cho confirmMatKhauField
//         JLabel eyeLabelConfirmMatKhau = new JLabel(closeEyeIcon);
//         eyeLabelConfirmMatKhau.setCursor(new Cursor(Cursor.HAND_CURSOR));
//         boolean[] isPasswordVisibleConfirm = {false};
//         eyeLabelConfirmMatKhau.addMouseListener(new MouseAdapter() {
//             @Override
//             public void mouseClicked(MouseEvent e) {
//                 if (type.equals("view")) return;
//                 isPasswordVisibleConfirm[0] = !isPasswordVisibleConfirm[0];
//                 if (isPasswordVisibleConfirm[0]) {
//                     confirmMatKhauField.setEchoChar((char) 0);
//                     eyeLabelConfirmMatKhau.setIcon(openEyeIcon);
//                 } else {
//                     confirmMatKhauField.setEchoChar('\u2022');
//                     eyeLabelConfirmMatKhau.setIcon(closeEyeIcon);
//                 }
//             }
//         });
//         gbc.gridx = 2;
//         gbc.gridy = 0;
//         gbc.weightx = 0.0;
//         gbc.fill = GridBagConstraints.NONE;
//         confirmMatKhauPanel.add(eyeLabelConfirmMatKhau, gbc);

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
//         lblNhomQuyen.setFont(new Font("Arial", Font.BOLD, 20));
//         lblNhomQuyen.setForeground(new Color(0, 102, 153));
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.weightx = 0.3;
//         nhomQuyenPanel.add(lblNhomQuyen, gbc);
//         nhomQuyenCombo.setFont(new Font("Arial", Font.PLAIN, 20));
//         gbc.gridx = 1;
//         gbc.gridy = 0;
//         gbc.weightx = 0.7;
//         nhomQuyenPanel.add(nhomQuyenCombo, gbc);

//         String[] trangThaiOptions = type.equals("create") ? new String[]{"Hoạt động", "Ngưng hoạt động"} : new String[]{"Hoạt động", "Ngưng hoạt động", "Chờ xử lý"};
//         trangThaiCombo = new JComboBox<>(trangThaiOptions);
//         if (taiKhoan != null) {
//             switch (taiKhoan.getTrangThai()) {
//                 case 1: trangThaiCombo.setSelectedIndex(0); break;
//                 case 0: trangThaiCombo.setSelectedIndex(1); break;
//                 case 2: trangThaiCombo.setSelectedIndex(2); break;
//                 default: trangThaiCombo.setSelectedIndex(0);
//             }
//         } else {
//             trangThaiCombo.setSelectedIndex(0);
//         }
//         trangThaiPanel = new JPanel(new GridBagLayout());
//         trangThaiPanel.setBackground(Color.WHITE);
//         JLabel lblTrangThai = new JLabel("Trạng thái:");
//         lblTrangThai.setFont(new Font("Arial", Font.BOLD, 20));
//         lblTrangThai.setForeground(new Color(0, 102, 153));
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.weightx = 0.3;
//         trangThaiPanel.add(lblTrangThai, gbc);
//         trangThaiCombo.setFont(new Font("Arial", Font.PLAIN, 20));
//         gbc.gridx = 1;
//         gbc.gridy = 0;
//         gbc.weightx = 0.7;
//         trangThaiPanel.add(trangThaiCombo, gbc);

//         if (type.equals("create")) {
//             pnInfo.add(maNVPanel);
//         } else {
//             pnInfo.add(maNVField);
//         }
//         pnInfo.add(tenDangNhapField);
//         pnInfo.add(matKhauPanel);
//         pnInfo.add(confirmMatKhauPanel);
//         pnInfo.add(nhomQuyenPanel);
//         pnInfo.add(trangThaiPanel);

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
//             if (maNVField != null) {
//                 maNVField.setEditable(false);
//                 maNVField.setEnabled(false);
//             }
//             tenDangNhapField.setEditable(false);
//             tenDangNhapField.setEnabled(false);
//             matKhauField.setEditable(false);
//             matKhauField.setEnabled(false);
//             confirmMatKhauField.setEditable(false);
//             confirmMatKhauField.setEnabled(false);
//             nhomQuyenCombo.setEnabled(false);
//             trangThaiCombo.setEnabled(false);
//         }
//     }

//     private boolean checkInput() {
//         String maNVText;
//         if (type.equals("create")) {
//             if (maNVCombo.getSelectedItem() == null) {
//                 JOptionPane.showMessageDialog(this, "Vui lòng chọn mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return false;
//             }
//             maNVText = maNVCombo.getSelectedItem().toString().split(" - ")[0];
//         } else {
//             maNVText = maNVField.getText().trim();
//         }
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
//         if (type.equals("create")) {
//             if (Validation.isEmpty(matKhau)) {
//                 JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return false;
//             }
//             if (!matKhau.equals(confirmMatKhau)) {
//                 JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return false;
//             }
//         } else if (!matKhau.isEmpty() || !confirmMatKhau.isEmpty()) {
//             if (!matKhau.equals(confirmMatKhau)) {
//                 JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return false;
//             }
//         }

//         return true;
//     }

//     private void saveTaiKhoan() {
//         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
//         String action = type.equals("create") ? "create" : "update";
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

//         int maNV;
//         if (type.equals("create")) {
//             if (maNVCombo.getSelectedItem() == null) {
//                 JOptionPane.showMessageDialog(this, "Vui lòng chọn mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }
//             maNV = Integer.parseInt(maNVCombo.getSelectedItem().toString().split(" - ")[0]);
//         } else {
//             maNV = Integer.parseInt(maNVField.getText().trim());
//         }

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
//         int trangThai;
//         switch (trangThaiCombo.getSelectedIndex()) {
//             case 0: trangThai = 1; break;
//             case 1: trangThai = 0; break;
//             case 2: trangThai = 2; break;
//             default:
//                 JOptionPane.showMessageDialog(this, "Trạng thái không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return;
//         }

//         TaiKhoanDTO tk = new TaiKhoanDTO(maNV, tenDangNhap, matKhau, selectedMaNhomQuyen, trangThai);
//         boolean success;
//         if (type.equals("create")) {
//             System.out.println("Adding new account: " + tenDangNhap + ", Raw Password: " + matKhau);
//             tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
//             success = taiKhoanBUS.addAcc(tk);
//             System.out.println("AddAcc result: " + success);
//             JOptionPane.showMessageDialog(this, success ? "Thêm tài khoản thành công!" : "Thêm tài khoản thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
//         } else {
//             System.out.println("Updating account: " + tenDangNhap + ", Raw Password: " + (matKhau.isEmpty() ? "unchanged" : matKhau));
//             if (!matKhau.isEmpty()) {
//                 tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
//             } else {
//                 tk.setMatKhau(taiKhoan.getMatKhau());
//             }
//             success = taiKhoanBUS.updateAcc(tk);
//             System.out.println("UpdateAcc result: " + success);
//             JOptionPane.showMessageDialog(this, success ? "Cập nhật tài khoản thành công!" : "Cập nhật tài khoản thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
//         }

//         if (success) {
//             taiKhoanBUS.getTaiKhoanAll();
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
import DTO.NhanVienDTO;
import Component.ButtonCustom;
import Component.HeaderTitle;
import Component.InputForm;
import helper.Validation;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class TaiKhoanDialog extends JDialog {
    private TaiKhoanBUS taiKhoanBUS;
    private TaiKhoanGUI gui;
    private TaiKhoanDTO taiKhoan;
    private String type;
    private int maNhomQuyen;
    private HeaderTitle titlePage;
    private JPanel pnCenter, pnBottom, nhomQuyenPanel, trangThaiPanel, matKhauPanel, confirmMatKhauPanel, maNVPanel;
    private InputForm maNVField, tenDangNhapField;
    private JPasswordField matKhauField, confirmMatKhauField;
    private JComboBox<String> nhomQuyenCombo, trangThaiCombo, maNVCombo;
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

        if (type.equals("create")) {
            maNVPanel = new JPanel(new GridBagLayout());
            maNVPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            JLabel lblMaNV = new JLabel("Mã NV:");
            lblMaNV.setFont(new Font("Arial", Font.BOLD, 20));
            lblMaNV.setForeground(new Color(0, 102, 153));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.3;
            maNVPanel.add(lblMaNV, gbc);

            NhanVienBUS nhanVienBUS = new NhanVienBUS(null);
            ArrayList<NhanVienDTO> allNhanVien = nhanVienBUS.getAll();
            ArrayList<TaiKhoanDTO> allTaiKhoan;
            try {
                allTaiKhoan = taiKhoanBUS.getTaiKhoanAll();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi tải danh sách tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
            }
            List<String> nhanVienWithoutTaiKhoan = new ArrayList<>();
            for (NhanVienDTO nv : allNhanVien) {
                boolean hasTaiKhoan = false;
                for (TaiKhoanDTO tk : allTaiKhoan) {
                    if (tk.getMaNV() == nv.getMNV()) {
                        hasTaiKhoan = true;
                        break;
                    }
                }
                if (!hasTaiKhoan) {
                    nhanVienWithoutTaiKhoan.add(nv.getMNV() + " - " + nv.getHOTEN());
                }
            }

            if (nhanVienWithoutTaiKhoan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không còn nhân viên nào chưa có tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                dispose();
                return;
            }

            maNVCombo = new JComboBox<>(nhanVienWithoutTaiKhoan.toArray(new String[0]));
            maNVCombo.setFont(new Font("Arial", Font.PLAIN, 20));
            maNVCombo.setPreferredSize(new Dimension(200, 32));
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 0.7;
            maNVPanel.add(maNVCombo, gbc);
        } else {
            maNVField = new InputForm("Mã NV");
            maNVField.setText(taiKhoan != null ? String.valueOf(taiKhoan.getMaNV()) : "");
            maNVField.setEditable(false);
        }

        tenDangNhapField = new InputForm("Tên đăng nhập");
        tenDangNhapField.setText(taiKhoan != null ? taiKhoan.getTenDangNhap() : "");
        if (type.equals("view")) {
            tenDangNhapField.setEditable(false);
        }

        // Khởi tạo matKhauPanel với biểu tượng mắt
        matKhauPanel = new JPanel(new GridBagLayout());
        matKhauPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setFont(new Font("Arial", Font.BOLD, 20));
        lblMatKhau.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        matKhauPanel.add(lblMatKhau, gbc);

        matKhauField = new JPasswordField();
        matKhauField.setFont(new Font("Arial", Font.PLAIN, 20));
        matKhauField.setForeground(new Color(51, 51, 51));
        matKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        matKhauField.setBackground(Color.WHITE);
        matKhauField.setPreferredSize(new Dimension(200, 32));
        if (type.equals("view")) {
            matKhauField.setText("********");
            matKhauField.setEditable(false);
            matKhauField.setEnabled(false);
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        matKhauPanel.add(matKhauField, gbc);

        // Thêm biểu tượng mắt cho matKhauField
        ImageIcon openEyeIcon = new ImageIcon("src/icons/openeye.png");
        ImageIcon closeEyeIcon = new ImageIcon("src/icons/closeeye.png");
        JLabel eyeLabelMatKhau = new JLabel(closeEyeIcon);
        eyeLabelMatKhau.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boolean[] isPasswordVisibleMatKhau = {false};
        eyeLabelMatKhau.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (type.equals("view")) return;
                isPasswordVisibleMatKhau[0] = !isPasswordVisibleMatKhau[0];
                if (isPasswordVisibleMatKhau[0]) {
                    matKhauField.setEchoChar((char) 0);
                    eyeLabelMatKhau.setIcon(openEyeIcon);
                } else {
                    matKhauField.setEchoChar('\u2022');
                    eyeLabelMatKhau.setIcon(closeEyeIcon);
                }
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        matKhauPanel.add(eyeLabelMatKhau, gbc);

        // Khởi tạo confirmMatKhauPanel với biểu tượng mắt
        confirmMatKhauPanel = new JPanel(new GridBagLayout());
        confirmMatKhauPanel.setBackground(Color.WHITE);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblConfirmMatKhau = new JLabel("Xác nhận mật khẩu:");
        lblConfirmMatKhau.setFont(new Font("Arial", Font.BOLD, 20));
        lblConfirmMatKhau.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        confirmMatKhauPanel.add(lblConfirmMatKhau, gbc);

        confirmMatKhauField = new JPasswordField();
        confirmMatKhauField.setFont(new Font("Arial", Font.PLAIN, 20));
        confirmMatKhauField.setForeground(new Color(51, 51, 51));
        confirmMatKhauField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        confirmMatKhauField.setBackground(Color.WHITE);
        confirmMatKhauField.setPreferredSize(new Dimension(200, 32));
        if (type.equals("view")) {
            confirmMatKhauField.setText("********");
            confirmMatKhauField.setEditable(false);
            confirmMatKhauField.setEnabled(false);
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        confirmMatKhauPanel.add(confirmMatKhauField, gbc);

        // Thêm biểu tượng mắt cho confirmMatKhauField
        JLabel eyeLabelConfirmMatKhau = new JLabel(closeEyeIcon);
        eyeLabelConfirmMatKhau.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boolean[] isPasswordVisibleConfirm = {false};
        eyeLabelConfirmMatKhau.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (type.equals("view")) return;
                isPasswordVisibleConfirm[0] = !isPasswordVisibleConfirm[0];
                if (isPasswordVisibleConfirm[0]) {
                    confirmMatKhauField.setEchoChar((char) 0);
                    eyeLabelConfirmMatKhau.setIcon(openEyeIcon);
                } else {
                    confirmMatKhauField.setEchoChar('\u2022');
                    eyeLabelConfirmMatKhau.setIcon(closeEyeIcon);
                }
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        confirmMatKhauPanel.add(eyeLabelConfirmMatKhau, gbc);

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
        lblNhomQuyen.setFont(new Font("Arial", Font.BOLD, 20));
        lblNhomQuyen.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        nhomQuyenPanel.add(lblNhomQuyen, gbc);
        nhomQuyenCombo.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        nhomQuyenPanel.add(nhomQuyenCombo, gbc);

        String[] trangThaiOptions = type.equals("create") ? new String[]{"Hoạt động", "Ngưng hoạt động"} : new String[]{"Hoạt động", "Ngưng hoạt động", "Chờ xử lý"};
        trangThaiCombo = new JComboBox<>(trangThaiOptions);
        if (taiKhoan != null) {
            switch (taiKhoan.getTrangThai()) {
                case 1: trangThaiCombo.setSelectedIndex(0); break;
                case 0: trangThaiCombo.setSelectedIndex(1); break;
                case 2: trangThaiCombo.setSelectedIndex(2); break;
                default: trangThaiCombo.setSelectedIndex(0);
            }
        } else {
            trangThaiCombo.setSelectedIndex(0);
        }
        trangThaiPanel = new JPanel(new GridBagLayout());
        trangThaiPanel.setBackground(Color.WHITE);
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Arial", Font.BOLD, 20));
        lblTrangThai.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        trangThaiPanel.add(lblTrangThai, gbc);
        trangThaiCombo.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        trangThaiPanel.add(trangThaiCombo, gbc);

        if (type.equals("create")) {
            pnInfo.add(maNVPanel);
        } else {
            pnInfo.add(maNVField);
        }
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
            if (maNVField != null) {
                maNVField.setEditable(false);
                maNVField.setEnabled(false);
            }
            tenDangNhapField.setEditable(false);
            tenDangNhapField.setEnabled(false);
            matKhauField.setEditable(false);
            matKhauField.setEnabled(false);
            confirmMatKhauField.setEditable(false);
            confirmMatKhauField.setEnabled(false);
            nhomQuyenCombo.setEnabled(false);
            trangThaiCombo.setEnabled(false);
        }
    }

    private boolean checkInput() {
        String maNVText;
        if (type.equals("create")) {
            if (maNVCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            maNVText = maNVCombo.getSelectedItem().toString().split(" - ")[0];
        } else {
            maNVText = maNVField.getText().trim();
        }
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
        if (type.equals("create")) {
            if (Validation.isEmpty(matKhau)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!matKhau.equals(confirmMatKhau)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else if (!matKhau.isEmpty() || !confirmMatKhau.isEmpty()) {
            if (!matKhau.equals(confirmMatKhau)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

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

        int maNV;
        if (type.equals("create")) {
            if (maNVCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            maNV = Integer.parseInt(maNVCombo.getSelectedItem().toString().split(" - ")[0]);
        } else {
            maNV = Integer.parseInt(maNVField.getText().trim());
        }

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
        int trangThai;
        switch (trangThaiCombo.getSelectedIndex()) {
            case 0: trangThai = 1; break;
            case 1: trangThai = 0; break;
            case 2: trangThai = 2; break;
            default:
                JOptionPane.showMessageDialog(this, "Trạng thái không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
        }

        TaiKhoanDTO tk = new TaiKhoanDTO(maNV, tenDangNhap, matKhau, selectedMaNhomQuyen, trangThai);
        boolean success;
        if (type.equals("create")) {
            System.out.println("Adding new account: " + tenDangNhap + ", Raw Password: " + matKhau);
            tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
            success = taiKhoanBUS.addAcc(tk);
            System.out.println("AddAcc result: " + success);
            JOptionPane.showMessageDialog(this, success ? "Thêm tài khoản thành công!" : "Thêm tài khoản thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Updating account: " + tenDangNhap + ", Raw Password: " + (matKhau.isEmpty() ? "unchanged" : matKhau));
            if (!matKhau.isEmpty()) {
                tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
            } else {
                tk.setMatKhau(taiKhoan.getMatKhau());
            }
            success = taiKhoanBUS.updateAcc(tk);
            System.out.println("UpdateAcc result: " + success);
            JOptionPane.showMessageDialog(this, success ? "Cập nhật tài khoản thành công!" : "Cập nhật tài khoản thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        }

        if (success) {
            taiKhoanBUS.getTaiKhoanAll();
            gui.loadTaiKhoanData();
            dispose();
        }
    }
}
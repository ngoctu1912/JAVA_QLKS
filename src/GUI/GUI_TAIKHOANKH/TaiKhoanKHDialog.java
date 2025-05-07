
// // package GUI_TAIKHOANKH;

// // import BUS.TaiKhoanBUS;
// // import BUS.KhachHangBUS;
// // import BUS.NhomQuyenBUS;
// // import DTO.TaiKhoanKHDTO;
// // import DTO.NhomQuyenDTO;
// // import DTO.ChiTietQuyenDTO;
// // import DTO.KhachHangDTO;
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
// // import java.awt.event.MouseAdapter;
// // import java.awt.event.MouseEvent;
// // import java.awt.event.WindowAdapter;
// // import java.awt.event.WindowEvent;
// // import javax.swing.ImageIcon;

// // public class TaiKhoanKHDialog extends JDialog {
// //     private TaiKhoanBUS taiKhoanBus;
// //     private TaiKhoanKHGUI gui;
// //     private TaiKhoanKHDTO taiKhoanKH;
// //     private String type;
// //     private int maNhomQuyen;
// //     private HeaderTitle titlePage;
// //     private JPanel pnCenter, pnBottom, nhomQuyenPanel, trangThaiPanel, matKhauPanel, confirmMatKhauPanel, maKHPanel;
// //     private InputForm maKHField, tenDangNhapField;
// //     private JPasswordField matKhauField, confirmMatKhauField;
// //     private JComboBox<String> nhomQuyenCombo, trangThaiCombo, maKHCombo;
// //     private ButtonCustom btnSave, btnCancel;
// //     private boolean isDisposed = false;

// //     public TaiKhoanKHDialog(TaiKhoanBUS taiKhoanBus, TaiKhoanKHGUI parent, Component owner, String title, boolean modal, String type, TaiKhoanKHDTO taiKhoanKH, int maNhomQuyen) {
// //         super(JOptionPane.getFrameForComponent(owner), title, modal);
// //         this.taiKhoanBus = taiKhoanBus;
// //         this.gui = parent;
// //         this.taiKhoanKH = taiKhoanKH;
// //         this.type = type;
// //         this.maNhomQuyen = maNhomQuyen;
// //         System.out.println("TaiKhoanKHDialog initialized with maNhomQuyen: " + maNhomQuyen);
// //         initComponents(title);
// //         setLocationRelativeTo(owner);
// //         // Ngăn chặn tạo nhiều dialog bằng cách kiểm tra trạng thái
// //         addWindowListener(new WindowAdapter() {
// //             @Override
// //             public void windowClosing(WindowEvent e) {
// //                 safeDispose();
// //             }
// //         });
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
// //             maKHPanel = new JPanel(new GridBagLayout());
// //             maKHPanel.setBackground(Color.WHITE);
// //             GridBagConstraints gbc = new GridBagConstraints();
// //             gbc.insets = new Insets(5, 5, 5, 5);
// //             gbc.fill = GridBagConstraints.HORIZONTAL;
// //             JLabel lblMaKH = new JLabel("Mã KH:");
// //             lblMaKH.setFont(new Font("Arial", Font.BOLD, 20));
// //             lblMaKH.setForeground(new Color(0, 102, 153));
// //             gbc.gridx = 0;
// //             gbc.gridy = 0;
// //             gbc.weightx = 0.3;
// //             maKHPanel.add(lblMaKH, gbc);

// //             KhachHangBUS khachHangBUS = new KhachHangBUS();
// //             ArrayList<KhachHangDTO> allKhachHang = khachHangBUS.selectAll();
// //             ArrayList<TaiKhoanKHDTO> allTaiKhoanKH;
// //             try {
// //                 allTaiKhoanKH = taiKhoanBus.getTaiKhoanAllKH();
// //             } catch (Exception e) {
// //                 JOptionPane.showMessageDialog(this, "Lỗi tải danh sách tài khoản khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 safeDispose();
// //                 return;
// //             }
// //             List<String> khachHangWithoutTaiKhoan = new ArrayList<>();
// //             for (KhachHangDTO kh : allKhachHang) {
// //                 boolean hasTaiKhoan = false;
// //                 for (TaiKhoanKHDTO tk : allTaiKhoanKH) {
// //                     if (tk.getMaKhachHang() == kh.getMaKhachHang()) {
// //                         hasTaiKhoan = true;
// //                         break;
// //                     }
// //                 }
// //                 if (!hasTaiKhoan) {
// //                     khachHangWithoutTaiKhoan.add(kh.getMaKhachHang() + " - " + kh.getTenKhachHang());
// //                 }
// //             }

// //             if (khachHangWithoutTaiKhoan.isEmpty()) {
// //                 JOptionPane.showMessageDialog(this, "Không còn khách hàng nào chưa có tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
// //                 safeDispose();
// //                 return;
// //             }

// //             maKHCombo = new JComboBox<>(khachHangWithoutTaiKhoan.toArray(new String[0]));
// //             maKHCombo.setFont(new Font("Arial", Font.PLAIN, 20));
// //             maKHCombo.setPreferredSize(new Dimension(200, 32));
// //             gbc.gridx = 1;
// //             gbc.gridy = 0;
// //             gbc.weightx = 0.7;
// //             maKHPanel.add(maKHCombo, gbc);
// //         } else {
// //             maKHField = new InputForm("Mã KH");
// //             maKHField.setText(taiKhoanKH != null ? String.valueOf(taiKhoanKH.getMaKhachHang()) : "");
// //             maKHField.setEditable(false);
// //         }

// //         tenDangNhapField = new InputForm("Tên đăng nhập");
// //         tenDangNhapField.setText(taiKhoanKH != null ? taiKhoanKH.getTenDangNhap() : "");
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
// //         if (type.equals("view")) {
// //             matKhauField.setText("********");
// //             matKhauField.setEditable(false);
// //             matKhauField.setEnabled(false);
// //         }
// //         gbc.gridx = 1;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.7;
// //         matKhauPanel.add(matKhauField, gbc);

// //         ImageIcon openEyeIcon = new ImageIcon("src/icons/openeye.png");
// //         ImageIcon closeEyeIcon = new ImageIcon("src/icons/closeeye.png");
// //         JLabel eyeLabelMatKhau = new JLabel(closeEyeIcon);
// //         eyeLabelMatKhau.setCursor(new Cursor(Cursor.HAND_CURSOR));
// //         boolean[] isPasswordVisibleMatKhau = {false};
// //         eyeLabelMatKhau.addMouseListener(new MouseAdapter() {
// //             @Override
// //             public void mouseClicked(MouseEvent e) {
// //                 if (type.equals("view")) return;
// //                 isPasswordVisibleMatKhau[0] = !isPasswordVisibleMatKhau[0];
// //                 if (isPasswordVisibleMatKhau[0]) {
// //                     matKhauField.setEchoChar((char) 0);
// //                     eyeLabelMatKhau.setIcon(openEyeIcon);
// //                 } else {
// //                     matKhauField.setEchoChar('\u2022');
// //                     eyeLabelMatKhau.setIcon(closeEyeIcon);
// //                 }
// //             }
// //         });
// //         gbc.gridx = 2;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.0;
// //         gbc.fill = GridBagConstraints.NONE;
// //         matKhauPanel.add(eyeLabelMatKhau, gbc);

// //         if (type.equals("update")) {
// //             JLabel lblNote = new JLabel("(Để trống để giữ mật khẩu hiện tại)");
// //             lblNote.setFont(new Font("Arial", Font.PLAIN, 12));
// //             lblNote.setForeground(Color.GRAY);
// //             gbc.gridx = 1;
// //             gbc.gridy = 1;
// //             gbc.weightx = 0.7;
// //             gbc.gridwidth = 2;
// //             matKhauPanel.add(lblNote, gbc);
// //         }

// //         confirmMatKhauPanel = new JPanel(new GridBagLayout());
// //         confirmMatKhauPanel.setBackground(Color.WHITE);
// //         gbc = new GridBagConstraints();
// //         gbc.insets = new Insets(5, 5, 5, 5);
// //         gbc.fill = GridBagConstraints.HORIZONTAL;

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
// //         if (type.equals("view")) {
// //             confirmMatKhauField.setText("********");
// //             confirmMatKhauField.setEditable(false);
// //             confirmMatKhauField.setEnabled(false);
// //         }
// //         gbc.gridx = 1;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.7;
// //         confirmMatKhauPanel.add(confirmMatKhauField, gbc);

// //         JLabel eyeLabelConfirmMatKhau = new JLabel(closeEyeIcon);
// //         eyeLabelConfirmMatKhau.setCursor(new Cursor(Cursor.HAND_CURSOR));
// //         boolean[] isPasswordVisibleConfirm = {false};
// //         eyeLabelConfirmMatKhau.addMouseListener(new MouseAdapter() {
// //             @Override
// //             public void mouseClicked(MouseEvent e) {
// //                 if (type.equals("view")) return;
// //                 isPasswordVisibleConfirm[0] = !isPasswordVisibleConfirm[0];
// //                 if (isPasswordVisibleConfirm[0]) {
// //                     confirmMatKhauField.setEchoChar((char) 0);
// //                     eyeLabelConfirmMatKhau.setIcon(openEyeIcon);
// //                 } else {
// //                     confirmMatKhauField.setEchoChar('\u2022');
// //                     eyeLabelConfirmMatKhau.setIcon(closeEyeIcon);
// //                 }
// //             }
// //         });
// //         gbc.gridx = 2;
// //         gbc.gridy = 0;
// //         gbc.weightx = 0.0;
// //         gbc.fill = GridBagConstraints.NONE;
// //         confirmMatKhauPanel.add(eyeLabelConfirmMatKhau, gbc);

// //         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
// //         List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUS.getAll();
// //         String[] nhomQuyenNames = nhomQuyenList.stream().map(NhomQuyenDTO::getTEN).toArray(String[]::new);
// //         nhomQuyenCombo = new JComboBox<>(nhomQuyenNames);
// //         if (taiKhoanKH != null) {
// //             for (int i = 0; i < nhomQuyenList.size(); i++) {
// //                 if (nhomQuyenList.get(i).getMNQ() == taiKhoanKH.getMaNhomQuyen()) {
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
// //         if (taiKhoanKH != null) {
// //             switch (taiKhoanKH.getTrangThai()) {
// //                 case 1: trangThaiCombo.setSelectedIndex(0); break;
// //                 case 0: trangThaiCombo.setSelectedIndex(1); break;
// //                 case 2: trangThaiCombo.setSelectedIndex(2); break;
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
// //             pnInfo.add(maKHPanel);
// //         } else {
// //             pnInfo.add(maKHField);
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
// //             btnSave.addActionListener(e -> saveTaiKhoanKH());
// //             pnBottom.add(btnSave);
// //         }

// //         btnCancel = new ButtonCustom(type.equals("view") ? "Đóng" : "Hủy bỏ", "danger", 14);
// //         btnCancel.addActionListener(e -> safeDispose());
// //         pnBottom.add(btnCancel);

// //         if (type.equals("view")) {
// //             if (maKHField != null) {
// //                 maKHField.setEditable(false);
// //                 maKHField.setEnabled(false);
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
// //         String maKHText;
// //         if (type.equals("create")) {
// //             if (maKHCombo.getSelectedItem() == null) {
// //                 JOptionPane.showMessageDialog(this, "Vui lòng chọn mã khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return false;
// //             }
// //             maKHText = maKHCombo.getSelectedItem().toString().split(" - ")[0];
// //         } else {
// //             maKHText = maKHField.getText().trim();
// //         }
// //         if (Validation.isEmpty(maKHText) || !Validation.isNumber(maKHText)) {
// //             JOptionPane.showMessageDialog(this, "Mã KH phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
// //             if (matKhau.length() < 6) {
// //                 JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return false;
// //             }
// //         } else if (!matKhau.isEmpty() || !confirmMatKhau.isEmpty()) {
// //             if (!matKhau.equals(confirmMatKhau)) {
// //                 JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return false;
// //             }
// //             if (!matKhau.isEmpty() && matKhau.length() < 6) {
// //                 JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return false;
// //             }
// //         }

// //         return true;
// //     }

// //     private void safeDispose() {
// //         if (!isDisposed) {
// //             isDisposed = true;
// //             System.out.println("Closing TaiKhoanKHDialog");
// //             dispose();
// //         }
// //     }

// //     private void saveTaiKhoanKH() {
// //         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
// //         String action = type.equals("create") ? "create" : "update";
// //         boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "13", action);
// //         if (!hasPermission) {
// //             List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
// //             System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
// //         }
// //         System.out.println("Permission check result: " + hasPermission);
// //         if (maNhomQuyen == 0 || !hasPermission) {
// //             String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền " + (type.equals("create") ? "thêm" : "sửa") + " tài khoản khách hàng!";
// //             JOptionPane.showMessageDialog(this, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
// //             return;
// //         }

// //         if (!checkInput()) {
// //             return;
// //         }

// //         int maKH;
// //         if (type.equals("create")) {
// //             if (maKHCombo.getSelectedItem() == null) {
// //                 JOptionPane.showMessageDialog(this, "Vui lòng chọn mã khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return;
// //             }
// //             maKH = Integer.parseInt(maKHCombo.getSelectedItem().toString().split(" - ")[0]);
// //         } else {
// //             maKH = Integer.parseInt(maKHField.getText().trim());
// //         }

// //         KhachHangBUS khachHangBUS = new KhachHangBUS();
// //         if (type.equals("create") && khachHangBUS.selectById(String.valueOf(maKH)) == null) {
// //             JOptionPane.showMessageDialog(this, "Mã KH không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //             return;
// //         }

// //         String tenDangNhap = tenDangNhapField.getText().trim();
// //         if (type.equals("create") && !taiKhoanBus.checkTDN(tenDangNhap)) {
// //             JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //             return;
// //         }

// //         String matKhau = new String(matKhauField.getPassword()).trim();
// //         NhomQuyenBUS nhomQuyenBUSForCombo = new NhomQuyenBUS();
// //         List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUSForCombo.getAll();
// //         int selectedMaNhomQuyen = nhomQuyenList.get(nhomQuyenCombo.getSelectedIndex()).getMNQ();
// //         int trangThai;
// //         switch (trangThaiCombo.getSelectedIndex()) {
// //             case 0: trangThai = 1; break;
// //             case 1: trangThai = 0; break;
// //             case 2: trangThai = 2; break;
// //             default:
// //                 JOptionPane.showMessageDialog(this, "Trạng thái không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
// //                 return;
// //         }

// //         TaiKhoanKHDTO tk = new TaiKhoanKHDTO(maKH, tenDangNhap, matKhau, selectedMaNhomQuyen, trangThai);
// //         boolean success;
// //         if (type.equals("create")) {
// //             System.out.println("Adding new KH account: " + tenDangNhap + ", Raw Password: " + matKhau);
// //             tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
// //             success = taiKhoanBus.addAccKH(tk);
// //             System.out.println("AddAccKH result: " + success);
// //             JOptionPane.showMessageDialog(this, success ? "Thêm tài khoản khách hàng thành công!" : "Thêm tài khoản khách hàng thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
// //         } else {
// //             System.out.println("Updating KH account: " + tenDangNhap + ", Raw Password: " + (matKhau.isEmpty() ? "unchanged" : matKhau));
// //             if (!matKhau.isEmpty()) {
// //                 tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
// //             } else {
// //                 tk.setMatKhau(taiKhoanKH.getMatKhau());
// //             }
// //             success = taiKhoanBus.updateAccKH(tk);
// //             System.out.println("UpdateAccKH result: " + success);
// //             JOptionPane.showMessageDialog(this, success ? "Cập nhật tài khoản khách hàng thành công!" : "Cập nhật tài khoản khách hàng thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
// //         }

// //         if (success) {
// //             try {
// //                 taiKhoanBus.getTaiKhoanAllKH();
// //                 gui.loadTaiKhoanKHData();
// //             } catch (Exception e) {
// //                 System.err.println("Lỗi khi làm mới dữ liệu sau cập nhật: " + e.getMessage());
// //                 JOptionPane.showMessageDialog(this, "Lỗi khi làm mới danh sách tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
// //             }
// //             safeDispose();
// //         }
// //     }
// // }

// package GUI_TAIKHOANKH;

// import BUS.TaiKhoanBUS;
// import BUS.KhachHangBUS;
// import BUS.NhomQuyenBUS;
// import DTO.TaiKhoanKHDTO;
// import DTO.NhomQuyenDTO;
// import DTO.ChiTietQuyenDTO;
// import DTO.KhachHangDTO;
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
// import javax.swing.SwingWorker;

// public class TaiKhoanKHDialog extends JDialog {
//     private TaiKhoanBUS taiKhoanBus;
//     private TaiKhoanKHGUI gui;
//     private TaiKhoanKHDTO taiKhoanKH;
//     private String type;
//     private int maNhomQuyen;
//     private HeaderTitle titlePage;
//     private JPanel pnCenter, pnBottom, nhomQuyenPanel, trangThaiPanel, matKhauPanel, confirmMatKhauPanel, maKHPanel;
//     private InputForm maKHField, tenDangNhapField;
//     private JPasswordField matKhauField, confirmMatKhauField;
//     private JComboBox<String> nhomQuyenCombo, trangThaiCombo, maKHCombo;
//     private ButtonCustom btnSave, btnCancel;

//     public TaiKhoanKHDialog(TaiKhoanBUS taiKhoanBus, TaiKhoanKHGUI parent, Component owner, String title, boolean modal, String type, TaiKhoanKHDTO taiKhoanKH, int maNhomQuyen) {
//         super(JOptionPane.getFrameForComponent(owner), title, modal);
//         this.taiKhoanBus = taiKhoanBus;
//         this.gui = parent;
//         this.taiKhoanKH = taiKhoanKH;
//         this.type = type;
//         this.maNhomQuyen = maNhomQuyen;
//         System.out.println("TaiKhoanKHDialog initialized with maNhomQuyen: " + maNhomQuyen);
//         initComponents(title);
//         setLocationRelativeTo(owner);
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
//             maKHPanel = new JPanel(new GridBagLayout());
//             maKHPanel.setBackground(Color.WHITE);
//             GridBagConstraints gbc = new GridBagConstraints();
//             gbc.insets = new Insets(5, 5, 5, 5);
//             gbc.fill = GridBagConstraints.HORIZONTAL;
//             JLabel lblMaKH = new JLabel("Mã KH:");
//             lblMaKH.setFont(new Font("Arial", Font.BOLD, 20));
//             lblMaKH.setForeground(new Color(0, 102, 153));
//             gbc.gridx = 0;
//             gbc.gridy = 0;
//             gbc.weightx = 0.3;
//             maKHPanel.add(lblMaKH, gbc);

//             // Tải danh sách khách hàng trong luồng riêng
//             SwingWorker<List<String>, Void> worker = new SwingWorker<>() {
//                 @Override
//                 protected List<String> doInBackground() {
//                     KhachHangBUS khachHangBUS = new KhachHangBUS();
//                     ArrayList<KhachHangDTO> allKhachHang = khachHangBUS.selectAll();
//                     ArrayList<TaiKhoanKHDTO> allTaiKhoanKH;
//                     try {
//                         allTaiKhoanKH = taiKhoanBus.getTaiKhoanAllKH();
//                     } catch (Exception e) {
//                         return null;
//                     }
//                     List<String> khachHangWithoutTaiKhoan = new ArrayList<>();
//                     for (KhachHangDTO kh : allKhachHang) {
//                         boolean hasTaiKhoan = false;
//                         for (TaiKhoanKHDTO tk : allTaiKhoanKH) {
//                             if (tk.getMaKhachHang() == kh.getMaKhachHang()) {
//                                 hasTaiKhoan = true;
//                                 break;
//                             }
//                         }
//                         if (!hasTaiKhoan) {
//                             khachHangWithoutTaiKhoan.add(kh.getMaKhachHang() + " - " + kh.getTenKhachHang());
//                         }
//                     }
//                     return khachHangWithoutTaiKhoan;
//                 }

//                 @Override
//                 protected void done() {
//                     try {
//                         List<String> khachHangWithoutTaiKhoan = get();
//                         if (khachHangWithoutTaiKhoan == null) {
//                             JOptionPane.showMessageDialog(TaiKhoanKHDialog.this, "Lỗi tải danh sách tài khoản khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                             dispose();
//                             return;
//                         }
//                         if (khachHangWithoutTaiKhoan.isEmpty()) {
//                             JOptionPane.showMessageDialog(TaiKhoanKHDialog.this, "Không còn khách hàng nào chưa có tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//                             dispose();
//                             return;
//                         }
//                         maKHCombo = new JComboBox<>(khachHangWithoutTaiKhoan.toArray(new String[0]));
//                         maKHCombo.setFont(new Font("Arial", Font.PLAIN, 20));
//                         maKHCombo.setPreferredSize(new Dimension(200, 32));
//                         GridBagConstraints gbc = new GridBagConstraints();
//                         gbc.insets = new Insets(5, 5, 5, 5);
//                         gbc.fill = GridBagConstraints.HORIZONTAL;
//                         gbc.gridx = 1;
//                         gbc.gridy = 0;
//                         gbc.weightx = 0.7;
//                         maKHPanel.add(maKHCombo, gbc);
//                         maKHPanel.revalidate();
//                         maKHPanel.repaint();
//                         pnInfo.add(maKHPanel);
//                         pnInfo.revalidate();
//                         pnInfo.repaint();
//                     } catch (Exception e) {
//                         JOptionPane.showMessageDialog(TaiKhoanKHDialog.this, "Lỗi tải danh sách khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//                         dispose();
//                     }
//                 }
//             };
//             worker.execute();
//         } else {
//             maKHField = new InputForm("Mã KH");
//             maKHField.setText(taiKhoanKH != null ? String.valueOf(taiKhoanKH.getMaKhachHang()) : "");
//             maKHField.setEditable(false);
//             pnInfo.add(maKHField);
//         }

//         tenDangNhapField = new InputForm("Tên đăng nhập");
//         tenDangNhapField.setText(taiKhoanKH != null ? taiKhoanKH.getTenDangNhap() : "");
//         if (type.equals("view")) {
//             tenDangNhapField.setEditable(false);
//         }
//         pnInfo.add(tenDangNhapField);

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
//         pnInfo.add(matKhauPanel);

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
//         pnInfo.add(confirmMatKhauPanel);

//         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
//         List<NhomQuyenDTO> nhomQuyenList = nhomQuyenBUS.getAll();
//         String[] nhomQuyenNames = nhomQuyenList.stream().map(NhomQuyenDTO::getTEN).toArray(String[]::new);
//         nhomQuyenCombo = new JComboBox<>(nhomQuyenNames);
//         if (taiKhoanKH != null) {
//             for (int i = 0; i < nhomQuyenList.size(); i++) {
//                 if (nhomQuyenList.get(i).getMNQ() == taiKhoanKH.getMaNhomQuyen()) {
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
//         pnInfo.add(nhomQuyenPanel);

//         String[] trangThaiOptions = type.equals("create") ? new String[]{"Hoạt động", "Ngưng hoạt động"} : new String[]{"Hoạt động", "Ngưng hoạt động", "Chờ xử lý"};
//         trangThaiCombo = new JComboBox<>(trangThaiOptions);
//         if (taiKhoanKH != null) {
//             switch (taiKhoanKH.getTrangThai()) {
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
//         pnInfo.add(trangThaiPanel);

//         pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         pnBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
//         pnBottom.setBackground(Color.WHITE);
//         pnCenter.add(pnBottom, BorderLayout.SOUTH);

//         if (!type.equals("view")) {
//             btnSave = new ButtonCustom(type.equals("create") ? "Thêm tài khoản" : "Cập nhật tài khoản", "success", 14);
//             btnSave.addActionListener(e -> saveTaiKhoanKH());
//             pnBottom.add(btnSave);
//         }

//         btnCancel = new ButtonCustom(type.equals("view") ? "Đóng" : "Hủy bỏ", "danger", 14);
//         btnCancel.addActionListener(e -> dispose());
//         pnBottom.add(btnCancel);

//         if (type.equals("view")) {
//             if (maKHField != null) {
//                 maKHField.setEditable(false);
//                 maKHField.setEnabled(false);
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
//         String maKHText;
//         if (type.equals("create")) {
//             if (maKHCombo.getSelectedItem() == null) {
//                 JOptionPane.showMessageDialog(this, "Vui lòng chọn mã khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return false;
//             }
//             maKHText = maKHCombo.getSelectedItem().toString().split(" - ")[0];
//         } else {
//             maKHText = maKHField.getText().trim();
//         }
//         if (Validation.isEmpty(maKHText) || !Validation.isNumber(maKHText)) {
//             JOptionPane.showMessageDialog(this, "Mã KH phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
//             if (matKhau.length() < 6) {
//                 JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return false;
//             }
//         } else if (!matKhau.isEmpty() || !confirmMatKhau.isEmpty()) {
//             if (!matKhau.equals(confirmMatKhau)) {
//                 JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return false;
//             }
//             if (!matKhau.isEmpty() && matKhau.length() < 6) {
//                 JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return false;
//             }
//         }

//         return true;
//     }

//     private void saveTaiKhoanKH() {
//         NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
//         String action = type.equals("create") ? "create" : "update";
//         boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "13", action);
//         if (!hasPermission) {
//             List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
//             System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
//         }
//         System.out.println("Permission check result: " + hasPermission);
//         if (maNhomQuyen == 0 || !hasPermission) {
//             String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền " + (type.equals("create") ? "thêm" : "sửa") + " tài khoản khách hàng!";
//             JOptionPane.showMessageDialog(this, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         if (!checkInput()) {
//             return;
//         }

//         int maKH;
//         if (type.equals("create")) {
//             if (maKHCombo.getSelectedItem() == null) {
//                 JOptionPane.showMessageDialog(this, "Vui lòng chọn mã khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }
//             maKH = Integer.parseInt(maKHCombo.getSelectedItem().toString().split(" - ")[0]);
//         } else {
//             maKH = Integer.parseInt(maKHField.getText().trim());
//         }

//         KhachHangBUS khachHangBUS = new KhachHangBUS();
//         if (type.equals("create") && khachHangBUS.selectById(String.valueOf(maKH)) == null) {
//             JOptionPane.showMessageDialog(this, "Mã KH không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         String tenDangNhap = tenDangNhapField.getText().trim();
//         if (type.equals("create") && !taiKhoanBus.checkTDN(tenDangNhap)) {
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

//         TaiKhoanKHDTO tk = new TaiKhoanKHDTO(maKH, tenDangNhap, matKhau, selectedMaNhomQuyen, trangThai);
//         boolean success;
//         if (type.equals("create")) {
//             System.out.println("Adding new KH account: " + tenDangNhap + ", Raw Password: " + matKhau);
//             tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
//             success = taiKhoanBus.addAccKH(tk);
//             System.out.println("AddAccKH result: " + success);
//             JOptionPane.showMessageDialog(this, success ? "Thêm tài khoản khách hàng thành công!" : "Thêm tài khoản khách hàng thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
//         } else {
//             System.out.println("Updating KH account: " + tenDangNhap + ", Raw Password: " + (matKhau.isEmpty() ? "unchanged" : matKhau));
//             if (!matKhau.isEmpty()) {
//                 tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
//             } else {
//                 tk.setMatKhau(taiKhoanKH.getMatKhau());
//             }
//             success = taiKhoanBus.updateAccKH(tk);
//             System.out.println("UpdateAccKH result: " + success);
//             JOptionPane.showMessageDialog(this, success ? "Cập nhật tài khoản khách hàng thành công!" : "Cập nhật tài khoản khách hàng thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
//         }

//         if (success) {
//             try {
//                 taiKhoanBus.getTaiKhoanAllKH();
//                 gui.loadTaiKhoanKHData();
//             } catch (Exception e) {
//                 System.err.println("Lỗi khi làm mới dữ liệu sau cập nhật: " + e.getMessage());
//                 JOptionPane.showMessageDialog(this, "Lỗi khi làm mới danh sách tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//             }
//             dispose();
//         }
//     }
// }

package GUI_TAIKHOANKH;

import BUS.TaiKhoanBUS;
import BUS.KhachHangBUS;
import BUS.NhomQuyenBUS;
import DTO.TaiKhoanKHDTO;
import DTO.NhomQuyenDTO;
import DTO.ChiTietQuyenDTO;
import DTO.KhachHangDTO;
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
import javax.swing.SwingWorker;

public class TaiKhoanKHDialog extends JDialog {
    private TaiKhoanBUS taiKhoanBus;
    private TaiKhoanKHGUI gui;
    private TaiKhoanKHDTO taiKhoanKH;
    private String type;
    private int maNhomQuyen;
    private HeaderTitle titlePage;
    private JPanel pnCenter, pnBottom, nhomQuyenPanel, trangThaiPanel, matKhauPanel, confirmMatKhauPanel, maKHPanel;
    private InputForm maKHField, tenDangNhapField;
    private JPasswordField matKhauField, confirmMatKhauField;
    private JComboBox<String> nhomQuyenCombo, trangThaiCombo, maKHCombo;
    private ButtonCustom btnSave, btnCancel;

    public TaiKhoanKHDialog(TaiKhoanBUS taiKhoanBus, TaiKhoanKHGUI parent, Component owner, String title, boolean modal, String type, TaiKhoanKHDTO taiKhoanKH, int maNhomQuyen) {
        super(JOptionPane.getFrameForComponent(owner), title, modal);
        this.taiKhoanBus = taiKhoanBus;
        this.gui = parent;
        this.taiKhoanKH = taiKhoanKH;
        this.type = type;
        this.maNhomQuyen = maNhomQuyen;
        System.out.println("TaiKhoanKHDialog initialized with maNhomQuyen: " + maNhomQuyen);
        initComponents(title);
        setLocationRelativeTo(owner);
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
            maKHPanel = new JPanel(new GridBagLayout());
            maKHPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            JLabel lblMaKH = new JLabel("Mã KH:");
            lblMaKH.setFont(new Font("Arial", Font.BOLD, 20));
            lblMaKH.setForeground(new Color(0, 102, 153));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.3;
            maKHPanel.add(lblMaKH, gbc);

            // Tải danh sách khách hàng trong luồng riêng
            SwingWorker<List<String>, Void> worker = new SwingWorker<>() {
                @Override
                protected List<String> doInBackground() {
                    KhachHangBUS khachHangBUS = new KhachHangBUS();
                    ArrayList<KhachHangDTO> allKhachHang = khachHangBUS.selectAll();
                    ArrayList<TaiKhoanKHDTO> allTaiKhoanKH;
                    try {
                        allTaiKhoanKH = taiKhoanBus.getTaiKhoanAllKH();
                    } catch (Exception e) {
                        return null;
                    }
                    List<String> khachHangWithoutTaiKhoan = new ArrayList<>();
                    for (KhachHangDTO kh : allKhachHang) {
                        boolean hasTaiKhoan = false;
                        for (TaiKhoanKHDTO tk : allTaiKhoanKH) {
                            if (tk.getMaKhachHang() == kh.getMaKhachHang()) {
                                hasTaiKhoan = true;
                                break;
                            }
                        }
                        if (!hasTaiKhoan) {
                            khachHangWithoutTaiKhoan.add(kh.getMaKhachHang() + " - " + kh.getTenKhachHang());
                        }
                    }
                    return khachHangWithoutTaiKhoan;
                }

                @Override
                protected void done() {
                    try {
                        List<String> khachHangWithoutTaiKhoan = get();
                        if (khachHangWithoutTaiKhoan == null) {
                            JOptionPane.showMessageDialog(TaiKhoanKHDialog.this, "Lỗi tải danh sách tài khoản khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            dispose();
                            return;
                        }
                        if (khachHangWithoutTaiKhoan.isEmpty()) {
                            JOptionPane.showMessageDialog(TaiKhoanKHDialog.this, "Không còn khách hàng nào chưa có tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                            dispose();
                            return;
                        }
                        maKHCombo = new JComboBox<>(khachHangWithoutTaiKhoan.toArray(new String[0]));
                        maKHCombo.setFont(new Font("Arial", Font.PLAIN, 20));
                        maKHCombo.setPreferredSize(new Dimension(200, 32));
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 5, 5, 5);
                        gbc.fill = GridBagConstraints.HORIZONTAL;
                        gbc.gridx = 1;
                        gbc.gridy = 0;
                        gbc.weightx = 0.7;
                        maKHPanel.add(maKHCombo, gbc);
                        maKHPanel.revalidate();
                        maKHPanel.repaint();
                        pnInfo.add(maKHPanel);
                        pnInfo.revalidate();
                        pnInfo.repaint();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(TaiKhoanKHDialog.this, "Lỗi tải danh sách khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
            };
            worker.execute();
        } else {
            maKHField = new InputForm("Mã KH");
            maKHField.setText(taiKhoanKH != null ? String.valueOf(taiKhoanKH.getMaKhachHang()) : "");
            maKHField.setEditable(false);
            pnInfo.add(maKHField);
        }

        tenDangNhapField = new InputForm("Tên đăng nhập");
        tenDangNhapField.setText(taiKhoanKH != null ? taiKhoanKH.getTenDangNhap() : "");
        if (type.equals("view")) {
            tenDangNhapField.setEditable(false);
        }
        pnInfo.add(tenDangNhapField);

        // Panel Mật khẩu
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

        pnInfo.add(matKhauPanel);

        // Panel Xác nhận mật khẩu
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
        pnInfo.add(confirmMatKhauPanel);

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
        lblNhomQuyen.setFont(new Font("Arial", Font.BOLD, 20));
        lblNhomQuyen.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        nhomQuyenPanel.add(lblNhomQuyen, gbc);
        nhomQuyenCombo.setFont(new Font("Arial", Font.PLAIN, 20));
        nhomQuyenCombo.setPreferredSize(new Dimension(200, 32)); // Đồng bộ kích thước
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        nhomQuyenPanel.add(nhomQuyenCombo, gbc);
        pnInfo.add(nhomQuyenPanel);

        // Trạng thái
        String[] trangThaiOptions = type.equals("create") ? new String[]{"Hoạt động", "Ngưng hoạt động"} : new String[]{"Hoạt động", "Ngưng hoạt động", "Chờ xử lý"};
        trangThaiCombo = new JComboBox<>(trangThaiOptions);
        if (taiKhoanKH != null) {
            switch (taiKhoanKH.getTrangThai()) {
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
        trangThaiCombo.setPreferredSize(new Dimension(200, 32)); // Đồng bộ kích thước
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        trangThaiPanel.add(trangThaiCombo, gbc);
        pnInfo.add(trangThaiPanel);

        // Nút thao tác
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

        // Vô hiệu hóa các trường trong chế độ view
        if (type.equals("view")) {
            if (maKHField != null) {
                maKHField.setEditable(false);
                maKHField.setEnabled(false);
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
        String maKHText;
        if (type.equals("create")) {
            if (maKHCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            maKHText = maKHCombo.getSelectedItem().toString().split(" - ")[0];
        } else {
            maKHText = maKHField.getText().trim();
        }
        if (Validation.isEmpty(maKHText) || !Validation.isNumber(maKHText)) {
            JOptionPane.showMessageDialog(this, "Mã KH phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            if (matKhau.length() < 6) {
                JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else if (!matKhau.isEmpty() || !confirmMatKhau.isEmpty()) {
            if (!matKhau.equals(confirmMatKhau)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!matKhau.isEmpty() && matKhau.length() < 6) {
                JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private void saveTaiKhoanKH() {
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        String action = type.equals("create") ? "create" : "update";
        boolean hasPermission = nhomQuyenBUS.checkPermission(maNhomQuyen, "13", action);
        if (!hasPermission) {
            List<ChiTietQuyenDTO> chiTietQuyen = nhomQuyenBUS.getChiTietQuyen(String.valueOf(maNhomQuyen));
            System.out.println("ChiTietQuyen for MNQ=" + maNhomQuyen + ": " + chiTietQuyen);
        }
        System.out.println("Permission check result: " + hasPermission);
        if (maNhomQuyen == 0 || !hasPermission) {
            String errorMsg = maNhomQuyen == 0 ? "Vui lòng đăng nhập với tài khoản quản lý (MNQ phải khác 0)!" : "Bạn không có quyền " + (type.equals("create") ? "thêm" : "sửa") + " tài khoản khách hàng!";
            JOptionPane.showMessageDialog(this, errorMsg, "Lỗi Quyền Truy Cập", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!checkInput()) {
            return;
        }

        int maKH;
        if (type.equals("create")) {
            if (maKHCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            maKH = Integer.parseInt(maKHCombo.getSelectedItem().toString().split(" - ")[0]);
        } else {
            maKH = Integer.parseInt(maKHField.getText().trim());
        }

        KhachHangBUS khachHangBUS = new KhachHangBUS();
        if (type.equals("create") && khachHangBUS.selectById(String.valueOf(maKH)) == null) {
            JOptionPane.showMessageDialog(this, "Mã KH không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tenDangNhap = tenDangNhapField.getText().trim();
        if (type.equals("create") && !taiKhoanBus.checkTDN(tenDangNhap)) {
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

        TaiKhoanKHDTO tk = new TaiKhoanKHDTO(maKH, tenDangNhap, matKhau, selectedMaNhomQuyen, trangThai);
        boolean success;
        if (type.equals("create")) {
            System.out.println("Adding new KH account: " + tenDangNhap + ", Raw Password: " + matKhau);
            tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
            success = taiKhoanBus.addAccKH(tk);
            System.out.println("AddAccKH result: " + success);
            JOptionPane.showMessageDialog(this, success ? "Thêm tài khoản khách hàng thành công!" : "Thêm tài khoản khách hàng thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Updating KH account: " + tenDangNhap + ", Raw Password: " + (matKhau.isEmpty() ? "unchanged" : matKhau));
            if (!matKhau.isEmpty()) {
                tk.setMatKhau(BCrypt.hashpw(matKhau, BCrypt.gensalt(12)));
            } else {
                tk.setMatKhau(taiKhoanKH.getMatKhau());
            }
            success = taiKhoanBus.updateAccKH(tk);
            System.out.println("UpdateAccKH result: " + success);
            JOptionPane.showMessageDialog(this, success ? "Cập nhật tài khoản khách hàng thành công!" : "Cập nhật tài khoản khách hàng thất bại!", "Thông báo", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        }

        if (success) {
            try {
                taiKhoanBus.getTaiKhoanAllKH();
                gui.loadTaiKhoanKHData();
            } catch (Exception e) {
                System.err.println("Lỗi khi làm mới dữ liệu sau cập nhật: " + e.getMessage());
                JOptionPane.showMessageDialog(this, "Lỗi khi làm mới danh sách tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        }
    }
}
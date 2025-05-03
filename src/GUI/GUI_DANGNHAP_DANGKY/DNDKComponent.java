package GUI_DANGNHAP_DANGKY;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;

import BUS.TaiKhoanBUS;
import DAO.KhachHangDAO;
import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import DAO.TaiKhoanKHDAO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import DTO.TaiKhoanKHDTO;

public class DNDKComponent {
    JPanel contentP;
    private final TaiKhoanBUS taiKhoanBUS;
    boolean isLoginMode = true;

    public JLabel lblImg;
    public JLabel lblTitle;
    public JTextField txtUser;
    public JPasswordField txtPass;
    public JPasswordField txtConfirmPass;
    public JToggleButton eyeTogglePass;
    public JToggleButton eyeToggleConfirm;
    public JButton btnAction;
    public JLabel lblErrorUser;
    public JLabel lblErrorPass;
    public JLabel lblErrorConfirm;
    public JLabel lblErrorAction;
    public JLabel lblSwitchMode;
    public JLabel lblForgot;
    public RoundedPanel userPanel;
    public RoundedPanel passwordPanel;
    public RoundedPanel confirmPanel;

    public final ImageIcon openEyeIcon;
    public final ImageIcon closeEyeIcon;

    public DNDKComponent(JPanel contentP, TaiKhoanBUS taiKhoanBUS) {
        this.contentP = contentP;
        this.taiKhoanBUS = taiKhoanBUS;

        ImageIcon tempOpenEyeIcon = new ImageIcon("./src/icons/openeye.png");
        openEyeIcon = new ImageIcon(tempOpenEyeIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));
        ImageIcon tempCloseEyeIcon = new ImageIcon("./src/icons/closeeye.png");
        closeEyeIcon = new ImageIcon(tempCloseEyeIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));

        initComponents();
    }

    private void initComponents() {
        lblImg = new JLabel();
        lblImg.setBounds(60, 40, 450, 450);
        lblImg.setOpaque(true);
        lblImg.setBackground(Color.WHITE);
        try {
            ImageIcon icon = new ImageIcon("./src/icons/login.jpg");
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(450, 450, Image.SCALE_SMOOTH);
            lblImg.setIcon(new ImageIcon(scaledImage));
            lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            lblImg.setText("Image not found");
        }
        contentP.add(lblImg);

        lblTitle = new JLabel("Đăng Nhập", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 28));
        lblTitle.setBounds(530, 50, 400, 40);
        contentP.add(lblTitle);

        ImageIcon userIcon = new ImageIcon("./src/icons/user.png");
        Image img = userIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        userIcon = new ImageIcon(img);

        userPanel = new RoundedPanel(50);
        userPanel.setBounds(580, 150, 300, 50);
        userPanel.setBackground(Color.decode("#f0f0f0"));
        userPanel.setOpaque(false);
        userPanel.setLayout(null);

        JLabel iconLabel = new JLabel(userIcon);
        iconLabel.setBounds(15, 15, 20, 20);
        userPanel.add(iconLabel);

        txtUser = new JTextField("Tên Đăng Nhập");
        txtUser.setBounds(50, 12, 230, 25);
        txtUser.setBorder(null);
        txtUser.setForeground(Color.GRAY);
        txtUser.setBackground(Color.decode("#f0f0f0"));
        txtUser.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        userPanel.add(txtUser);
        contentP.add(userPanel);

        ImageIcon passwordIcon = new ImageIcon("./src/icons/lock.png");
        Image passImg = passwordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        passwordIcon = new ImageIcon(passImg);

        passwordPanel = new RoundedPanel(50);
        passwordPanel.setBounds(580, 230, 300, 50);
        passwordPanel.setBackground(Color.decode("#f0f0f0"));
        passwordPanel.setOpaque(false);
        passwordPanel.setLayout(null);

        JLabel passIconLabel = new JLabel(passwordIcon);
        passIconLabel.setBounds(15, 15, 20, 20);
        passwordPanel.add(passIconLabel);

        txtPass = new JPasswordField("Mật Khẩu");
        txtPass.setBounds(50, 12, 200, 25);
        txtPass.setBorder(null);
        txtPass.setForeground(Color.GRAY);
        txtPass.setBackground(Color.decode("#f0f0f0"));
        txtPass.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        txtPass.setEchoChar((char) 0);
        passwordPanel.add(txtPass);

        eyeTogglePass = new JToggleButton(closeEyeIcon);
        eyeTogglePass.setBounds(255, 16, 20, 20);
        eyeTogglePass.setBorder(null);
        eyeTogglePass.setContentAreaFilled(false);
        eyeTogglePass.setFocusPainted(false);
        passwordPanel.add(eyeTogglePass);
        contentP.add(passwordPanel);

        confirmPanel = new RoundedPanel(50);
        confirmPanel.setBounds(580, 310, 300, 50);
        confirmPanel.setBackground(Color.decode("#f0f0f0"));
        confirmPanel.setOpaque(false);
        confirmPanel.setLayout(null);

        JLabel confirmIconLabel = new JLabel(passwordIcon);
        confirmIconLabel.setBounds(15, 15, 20, 20);
        confirmPanel.add(confirmIconLabel);

        txtConfirmPass = new JPasswordField("Xác Nhận Mật Khẩu");
        txtConfirmPass.setBounds(50, 12, 200, 25);
        txtConfirmPass.setBorder(null);
        txtConfirmPass.setForeground(Color.GRAY);
        txtConfirmPass.setBackground(Color.decode("#f0f0f0"));
        txtConfirmPass.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        txtConfirmPass.setEchoChar((char) 0);
        confirmPanel.add(txtConfirmPass);

        eyeToggleConfirm = new JToggleButton(closeEyeIcon);
        eyeToggleConfirm.setBounds(255, 16, 20, 20);
        eyeToggleConfirm.setBorder(null);
        eyeToggleConfirm.setContentAreaFilled(false);
        eyeToggleConfirm.setFocusPainted(false);
        confirmPanel.add(eyeToggleConfirm);
        contentP.add(confirmPanel);

        btnAction = new JButton("Đăng Nhập");
        btnAction.setBounds(580, 330, 300, 50);
        btnAction.setFont(new Font("Times New Roman", Font.BOLD, 18));
        btnAction.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAction.setBorder(BorderFactory.createEmptyBorder());
        btnAction.setFocusPainted(false);
        btnAction.setBorderPainted(false);
        btnAction.setContentAreaFilled(false);
        btnAction.setOpaque(false);

        btnAction.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                javax.swing.AbstractButton b = (javax.swing.AbstractButton) c;
                javax.swing.ButtonModel model = b.getModel();

                int width = b.getWidth();
                int height = b.getHeight();
                int arc = 50;

                if (model.isPressed()) {
                    g2.setColor(Color.decode("#2eb82e"));
                } else if (model.isRollover()) {
                    g2.setColor(Color.decode("#3cdc3c"));
                } else {
                    g2.setColor(Color.decode("#32cd32"));
                }

                g2.fillRoundRect(0, 0, width, height, arc, arc);

                g2.setColor(Color.WHITE);
                g2.setFont(b.getFont());
                java.awt.FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(b.getText());
                int textHeight = fm.getHeight();
                int x = (width - textWidth) / 2;
                int y = (height - textHeight) / 2 + fm.getAscent();
                g2.drawString(b.getText(), x, y);

                g2.dispose();
            }
        });
        contentP.add(btnAction);

        lblErrorUser = new JLabel("");
        lblErrorUser.setBounds(595, 200, 270, 20);
        lblErrorUser.setForeground(Color.RED);
        lblErrorUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        contentP.add(lblErrorUser);

        lblErrorPass = new JLabel("");
        lblErrorPass.setBounds(595, 280, 270, 20);
        lblErrorPass.setForeground(Color.RED);
        lblErrorPass.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        contentP.add(lblErrorPass);

        lblErrorConfirm = new JLabel("");
        lblErrorConfirm.setBounds(595, 360, 270, 20);
        lblErrorConfirm.setForeground(Color.RED);
        lblErrorConfirm.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        contentP.add(lblErrorConfirm);

        lblErrorAction = new JLabel("");
        lblErrorAction.setBounds(595, 380, 270, 20);
        lblErrorAction.setForeground(Color.RED);
        lblErrorAction.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        contentP.add(lblErrorAction);

        lblSwitchMode = new JLabel("Chưa có tài khoản? Đăng ký");
        lblSwitchMode.setForeground(Color.GRAY);
        lblSwitchMode.setBounds(660, 440, 200, 20);
        lblSwitchMode.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        lblSwitchMode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contentP.add(lblSwitchMode);

        lblForgot = new JLabel("Quên mật khẩu?");
        lblForgot.setForeground(Color.GRAY);
        lblForgot.setBounds(600, 383, 100, 20);
        lblForgot.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        lblForgot.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contentP.add(lblForgot);

        updateUIMode();
    }

    public void setLoginMode(boolean isLoginMode) {
        this.isLoginMode = isLoginMode;
        updateUIMode();
    }

    private void updateUIMode() {
        if (isLoginMode) {
            lblTitle.setText("Đăng Nhập");
            btnAction.setText("Đăng Nhập");
            btnAction.setBounds(580, 330, 300, 50);
            lblErrorAction.setBounds(595, 380, 270, 20);
            lblSwitchMode.setText("Chưa có tài khoản? Đăng ký");
            lblSwitchMode.setBounds(660, 440, 200, 20);
            confirmPanel.setVisible(false);
            lblErrorConfirm.setVisible(false);
            lblForgot.setVisible(true);
            try {
                ImageIcon icon = new ImageIcon("./src/icons/login.jpg");
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(450, 450, Image.SCALE_SMOOTH);
                lblImg.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                e.printStackTrace();
                lblImg.setText("Login image not found");
            }
        } else {
            lblTitle.setText("Đăng Ký");
            btnAction.setText("Đăng Ký");
            btnAction.setBounds(580, 410, 300, 50);
            lblErrorAction.setBounds(595, 460, 270, 20);
            lblSwitchMode.setText("Đã có tài khoản? Đăng nhập");
            lblSwitchMode.setBounds(660, 460, 200, 20);
            confirmPanel.setVisible(true);
            lblErrorConfirm.setVisible(true);
            lblForgot.setVisible(false);
            try {
                ImageIcon icon = new ImageIcon("./src/icons/signup.jpg");
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(550, 470, Image.SCALE_SMOOTH);
                lblImg.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                e.printStackTrace();
                lblImg.setText("Signup image not found");
            }
        }
    }

    // public AccountInfo checkAccount() {
    //     String username = txtUser.getText().trim();
    //     if (username.isEmpty() || username.equals("Tên Đăng Nhập")) {
    //         lblErrorUser.setText("Vui lòng nhập tên đăng nhập!");
    //         txtUser.requestFocus();
    //         return null;
    //     }
    
    //     // Kiểm tra tài khoản nhân viên
    //     TaiKhoanDTO tkNV = TaiKhoanDAO.getInstance().selectByUser(username);
    //     if (tkNV != null && tkNV.getTrangThai() == 1) {
    //         NhanVienDTO nv = NhanVienDAO.getInstance().selectById(String.valueOf(tkNV.getMaNV()));
    //         if (nv != null) {
    //             String fullName = nv.getHOTEN();
    //             String role;
    //             switch (tkNV.getMaNhomQuyen()) {
    //                 case 1:
    //                     role = "Quản lý khách sạn";
    //                     break;
    //                 case 2:
    //                     role = "Nhân viên Lễ tân";
    //                     break;
    //                 case 3:
    //                     role = "Nhân viên Quản lý kho";
    //                     break;
    //                 default:
    //                     role = "Nhân viên";
    //             }
    //             return new AccountInfo(fullName, role, "QUANLY", tkNV.getMaNV());
    //         }
    //     }
    
    //     // Kiểm tra tài khoản khách hàng
    //     TaiKhoanKHDTO tkKH = TaiKhoanKHDAO.getInstance().selectByUser(username);
    //     if (tkKH != null && tkKH.getTrangThai() == 1) {
    //         KhachHangDTO kh = KhachHangDAO.getInstance().selectById(String.valueOf(tkKH.getMaKhachHang()));
    //         String fullName = (kh != null && !kh.getTenKhachHang().isEmpty()) ? kh.getTenKhachHang() : "Khách hàng mới";
    //         String role = "Khách hàng";
    //         return new AccountInfo(fullName, role, "KHACHHANG", tkKH.getMaKhachHang());
    //     }
    
    //     lblErrorUser.setText("Tên đăng nhập không tồn tại hoặc tài khoản bị khóa!");
    //     txtUser.requestFocus();
    //     return null;
    // }
    public AccountInfo checkAccount() {
        String username = txtUser.getText().trim();
        if (username.isEmpty() || username.equals("Tên Đăng Nhập")) {
            lblErrorUser.setText("Vui lòng nhập tên đăng nhập!");
            txtUser.requestFocus();
            return null;
        }
    
        // Kiểm tra tài khoản nhân viên
        TaiKhoanDTO tkNV = TaiKhoanDAO.getInstance().selectByUser(username);
        if (tkNV != null && tkNV.getTrangThai() == 1) {
            NhanVienDTO nv = NhanVienDAO.getInstance().selectById(String.valueOf(tkNV.getMaNV()));
            if (nv != null) {
                if (nv.getTT() != 1) { // Kiểm tra trạng thái nhân viên
                    lblErrorUser.setText("Tài khoản đã khóa!");
                    txtUser.requestFocus();
                    return null;
                }
                String fullName = nv.getHOTEN();
                String role;
                switch (tkNV.getMaNhomQuyen()) {
                    case 1:
                        role = "Quản lý khách sạn";
                        break;
                    case 2:
                        role = "Nhân viên Lễ tân";
                        break;
                    case 3:
                        role = "Nhân viên Quản lý kho";
                        break;
                    default:
                        role = "Nhân viên";
                }
                return new AccountInfo(fullName, role, "QUANLY", tkNV.getMaNV());
            }
        }
    
        // Kiểm tra tài khoản khách hàng
        TaiKhoanKHDTO tkKH = TaiKhoanKHDAO.getInstance().selectByUser(username);
        if (tkKH != null && tkKH.getTrangThai() == 1) {
            KhachHangDTO kh = KhachHangDAO.getInstance().selectById(String.valueOf(tkKH.getMaKhachHang()));
            if (kh != null) {
                if (kh.getTrangThai() != 1) { // Kiểm tra trạng thái khách hàng
                    lblErrorUser.setText("Tài khoản đã khóa!");
                    txtUser.requestFocus();
                    return null;
                }
                String fullName = (!kh.getTenKhachHang().isEmpty()) ? kh.getTenKhachHang() : "Khách hàng mới";
                String role = "Khách hàng";
                return new AccountInfo(fullName, role, "KHACHHANG", tkKH.getMaKhachHang());
            }
        }
    
        lblErrorUser.setText("Tên đăng nhập không tồn tại hoặc tài khoản bị khóa!");
        txtUser.requestFocus();
        return null;
    }

    public static class AccountInfo {
        private final String fullName;
        private final String role;
        private final String accountType;
        private final int accountId;

        public AccountInfo(String fullName, String role, String accountType, int accountId) {
            this.fullName = fullName;
            this.role = role;
            this.accountType = accountType;
            this.accountId = accountId;
        }

        public String getFullName() {
            return fullName;
        }

        public String getRole() {
            return role;
        }

        public String getAccountType() {
            return accountType;
        }

        public int getAccountId() {
            return accountId;
        }
    }

    // Getters
    public JTextField getTxtUser() {
        return txtUser;
    }

    public JPasswordField getTxtPass() {
        return txtPass;
    }

    public JPasswordField getTxtConfirmPass() {
        return txtConfirmPass;
    }

    public JToggleButton getEyeTogglePass() {
        return eyeTogglePass;
    }

    public JToggleButton getEyeToggleConfirm() {
        return eyeToggleConfirm;
    }

    public JButton getBtnAction() {
        return btnAction;
    }

    public JLabel getLblErrorUser() {
        return lblErrorUser;
    }

    public JLabel getLblErrorPass() {
        return lblErrorPass;
    }

    public JLabel getLblErrorConfirm() {
        return lblErrorConfirm;
    }

    public JLabel getLblErrorAction() {
        return lblErrorAction;
    }

    public JLabel getLblSwitchMode() {
        return lblSwitchMode;
    }

    public JLabel getLblForgot() {
        return lblForgot;
    }

    public JPanel getContentP() {
        return contentP;
    }
}
package GUI_DANGNHAP;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicButtonUI;

import BUS.TaiKhoanBUS;
import DAO.ConnectDB;
import DTO.TaiKhoanDTO;

public class FormLogin extends JFrame {
    private TaiKhoanBUS taiKhoanBUS;

    public FormLogin(TaiKhoanBUS taiKhoanBUS) {
        this.taiKhoanBUS = taiKhoanBUS;
        initComponents();
    }

    private void initComponents() {
        // JFrame
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0,
                        Color.decode("#8a2be2"),
                        getWidth(),
                        getHeight(),
                        Color.decode("#ff69b4"));

                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBounds(0, 0, 1000, 600);
        panel.setLayout(null);
        add(panel);

        // Panel nội dung chính
        JPanel contentP = new JPanel();
        contentP.setBackground(Color.WHITE);
        contentP.setBounds(100, 50, 800, 450);
        contentP.setLayout(null);
        contentP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(contentP);

        // Ảnh
        JLabel lblImg = new JLabel();
        lblImg.setBounds(40, 50, 360, 350);
        lblImg.setOpaque(true);
        lblImg.setBackground(Color.WHITE);

        // Tải hình ảnh và điều chỉnh kích thước
        try {
            ImageIcon icon = new ImageIcon("./src/icons/login.jpg");
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(330, 330, Image.SCALE_SMOOTH);
            lblImg.setIcon(new ImageIcon(scaledImage));
            lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            lblImg.setText("Image not found");
        }

        contentP.add(lblImg);

        // Tiêu đề "Đăng Nhập"
        JLabel lblTitle = new JLabel("Đăng Nhập", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTitle.setBounds(380, 30, 400, 30);
        contentP.add(lblTitle);

        // Email Icon
        ImageIcon userIcon = new ImageIcon("./src/icons/user.png");
        Image img = userIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        userIcon = new ImageIcon(img);

        // corners
        RoundedPanel userPanel = new RoundedPanel(50);
        userPanel.setBounds(450, 100, 270, 50);
        userPanel.setBackground(Color.decode("#f0f0f0"));
        userPanel.setOpaque(false);
        userPanel.setLayout(null);

        // Label icon
        JLabel iconLabel = new JLabel(userIcon);
        iconLabel.setBounds(15, 15, 20, 20);
        userPanel.add(iconLabel);

        // TextField
        JTextField txtUser = new JTextField("Tên Đăng Nhập");
        txtUser.setBounds(50, 12, 200, 25);
        txtUser.setBorder(null);
        txtUser.setForeground(Color.GRAY);
        txtUser.setBackground(Color.decode("#f0f0f0"));
        txtUser.setFont(new Font("Times New Roman", Font.PLAIN, 15));

        // Add FocusListener for placeholder behavior
        txtUser.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtUser.getText().equals("Tên Đăng Nhập")) {
                    txtUser.setText("");
                    txtUser.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtUser.getText().isEmpty()) {
                    txtUser.setText("Tên Đăng Nhập");
                    txtUser.setForeground(Color.GRAY);
                }
            }
        });

        userPanel.add(txtUser);

        // Add to content panel
        contentP.add(userPanel);

        // Ensure txtUser doesn't automatically focus on startup
        SwingUtilities.invokeLater(() -> contentP.requestFocusInWindow());

        // Password Icon
        ImageIcon passwordIcon = new ImageIcon("./src/icons/lock.png");
        Image passImg = passwordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        passwordIcon = new ImageIcon(passImg);

        // Eye Icons for password visibility
        final ImageIcon openEyeIcon = new ImageIcon("./src/icons/openeye.png");
        Image openEyeImg = openEyeIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        final ImageIcon finalOpenEyeIcon = new ImageIcon(openEyeImg);

        final ImageIcon closeEyeIcon = new ImageIcon("./src/icons/closeeye.png");
        Image closeEyeImg = closeEyeIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        final ImageIcon finalCloseEyeIcon = new ImageIcon(closeEyeImg);

        // RoundedPanel for password icon + field
        RoundedPanel passwordPanel = new RoundedPanel(50);
        passwordPanel.setBounds(450, 175, 270, 50);
        passwordPanel.setBackground(Color.decode("#f0f0f0"));
        passwordPanel.setOpaque(false);
        passwordPanel.setLayout(null);

        // Label icon
        JLabel passIconLabel = new JLabel(passwordIcon);
        passIconLabel.setBounds(15, 15, 20, 20);
        passwordPanel.add(passIconLabel);

        // Password Field
        JPasswordField txtPass = new JPasswordField("Mật Khẩu");
        txtPass.setBounds(50, 12, 170, 25);
        txtPass.setBorder(null);
        txtPass.setForeground(Color.GRAY);
        txtPass.setBackground(Color.decode("#f0f0f0"));
        txtPass.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        txtPass.setEchoChar((char) 0); // Initially show the placeholder text

        // Add FocusListener for placeholder behavior
        txtPass.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtPass.getPassword()).equals("Mật Khẩu")) {
                    txtPass.setText("");
                    txtPass.setEchoChar('*');
                    txtPass.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtPass.getPassword()).isEmpty()) {
                    txtPass.setEchoChar((char) 0);
                    txtPass.setText("Mật Khẩu");
                    txtPass.setForeground(Color.GRAY);
                }
            }
        });

        // Eye toggle button
        JToggleButton eyeToggle = new JToggleButton(finalCloseEyeIcon);
        eyeToggle.setBounds(225, 16, 20, 20);
        eyeToggle.setBorder(null);
        eyeToggle.setContentAreaFilled(false);
        eyeToggle.setFocusPainted(false);

        // Add action listener to toggle password visibility
        eyeToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eyeToggle.isSelected()) {
                    eyeToggle.setIcon(finalOpenEyeIcon);
                    if (!String.valueOf(txtPass.getPassword()).equals("Mật Khẩu")) {
                        txtPass.setEchoChar((char) 0);
                    }
                } else {
                    eyeToggle.setIcon(finalCloseEyeIcon);
                    if (!String.valueOf(txtPass.getPassword()).equals("Mật Khẩu")) {
                        txtPass.setEchoChar('*');
                    }
                }
            }
        });

        passwordPanel.add(txtPass);
        passwordPanel.add(eyeToggle);

        // Add to content panel
        contentP.add(passwordPanel);

        // Nút Login with rounded corners
        JButton btnLogin = new JButton("Đăng Nhập");
        btnLogin.setBackground(new Color(50, 205, 50));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBounds(450, 260, 270, 45);
        btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnLogin.setBorder(BorderFactory.createEmptyBorder());
        btnLogin.setFocusPainted(false);

        // Create rounded corners
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setOpaque(false);

        // Custom painting for rounded corners
        btnLogin.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();

                int width = b.getWidth();
                int height = b.getHeight();
                int arc = 50;

                // Draw rounded background
                if (model.isPressed()) {
                    g2.setColor(Color.decode("#2eb82e"));
                } else if (model.isRollover()) {
                    g2.setColor(Color.decode("#3cdc3c"));
                } else {
                    g2.setColor(Color.decode("#32cd32"));
                }

                g2.fillRoundRect(0, 0, width, height, arc, arc);

                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(b.getFont());
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(b.getText());
                int textHeight = fm.getHeight();
                int x = (width - textWidth) / 2;
                int y = (height - textHeight) / 2 + fm.getAscent();
                g2.drawString(b.getText(), x, y);

                g2.dispose();
            }
        });

        // Khai báo các JLabel để hiển thị thông báo lỗi
        JLabel lblErrorUser = new JLabel("");
        JLabel lblErrorPass = new JLabel("");
        JLabel lblErrorLogin = new JLabel("");

        // Thêm lblErrorUser ngay dưới userPanel
        lblErrorUser.setBounds(475, 150, 270, 20); // Đặt ngay dưới userPanel
        lblErrorUser.setForeground(Color.RED);
        lblErrorUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        contentP.add(lblErrorUser);

        // Thêm lblErrorPass ngay dưới passwordPanel
        lblErrorPass.setBounds(475, 225, 270, 20); // Đặt ngay dưới passwordPanel
        lblErrorPass.setForeground(Color.RED);
        lblErrorPass.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        contentP.add(lblErrorPass);

        // Thêm lblErrorLogin ngay dưới btnLogin (cho thông báo đăng nhập thất bại)
        lblErrorLogin.setBounds(450, 290, 270, 20); // Đặt ngay dưới btnLogin
        lblErrorLogin.setForeground(Color.RED);
        lblErrorLogin.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        contentP.add(lblErrorLogin);

        // Sửa lại ActionListener của btnLogin
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xóa các thông báo lỗi trước đó
                lblErrorUser.setText("");
                lblErrorPass.setText("");
                lblErrorLogin.setText("");

                String email = txtUser.getText().trim();
                String password = new String(txtPass.getPassword()).trim();

                // Class để hiển thị thông báo tùy chỉnh
                class CustomMessage {
                    // Thông báo thành công
                    private void showSuccessMessage(String message, String description) {
                        JPanel panel = new JPanel();
                        panel.setLayout(null);
                        panel.setBackground(Color.WHITE);
                        panel.setPreferredSize(new java.awt.Dimension(300, 150));

                        // Biểu tượng dấu tích
                        ImageIcon icon = new ImageIcon("./src/icons/success.png");
                        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
                        iconLabel.setBounds(130, 10, 40, 40); // Căn giữa theo chiều ngang
                        panel.add(iconLabel);

                        // Tiêu đề "Đăng nhập thành công!"
                        JLabel titleLabel = new JLabel(message);
                        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        titleLabel.setForeground(Color.BLACK);
                        titleLabel.setBounds(0, 60, 300, 20);
                        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        panel.add(titleLabel);

                        // Mô tả
                        JLabel descLabel = new JLabel(description);
                        descLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
                        descLabel.setForeground(Color.GRAY);
                        descLabel.setBounds(0, 85, 300, 20);
                        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        panel.add(descLabel);

                        // Nút OK
                        JButton okButton = new JButton("OK");
                        okButton.setBackground(new Color(66, 133, 244)); // Màu xanh dương giống Google
                        okButton.setForeground(Color.WHITE);
                        okButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                        okButton.setBounds(110, 115, 80, 30);
                        okButton.setFocusPainted(false);
                        okButton.setBorderPainted(false);
                        okButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Đóng hộp thoại khi nhấn OK
                                Window dialog = SwingUtilities.windowForComponent(okButton);
                                dialog.dispose();
                            }
                        });
                        panel.add(okButton);

                        // Hiển thị hộp thoại
                        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.DEFAULT_OPTION, null, new Object[] {});
                        JDialog dialog = optionPane.createDialog(null, "Thành công");
                        dialog.setVisible(true);
                    }

                    // Thông báo lỗi
                    private void showErrorMessage(String message, String description) {
                        JPanel panel = new JPanel();
                        panel.setLayout(null);
                        panel.setBackground(Color.WHITE);
                        panel.setPreferredSize(new java.awt.Dimension(300, 150));

                        // Biểu tượng lỗi (dấu chéo đỏ)
                        ImageIcon icon = new ImageIcon("./src/icons/error.png");
                        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
                        iconLabel.setBounds(130, 10, 40, 40); // Căn giữa theo chiều ngang
                        panel.add(iconLabel);

                        // Tiêu đề "Đăng nhập thất bại!"
                        JLabel titleLabel = new JLabel(message);
                        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        titleLabel.setForeground(Color.BLACK);
                        titleLabel.setBounds(0, 60, 300, 20);
                        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        panel.add(titleLabel);

                        // Mô tả
                        JLabel descLabel = new JLabel(description);
                        descLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
                        descLabel.setForeground(Color.GRAY);
                        descLabel.setBounds(0, 85, 300, 20);
                        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        panel.add(descLabel);

                        // Nút OK
                        JButton okButton = new JButton("OK");
                        okButton.setBackground(new Color(66, 133, 244)); // Màu xanh dương giống Google
                        okButton.setForeground(Color.WHITE);
                        okButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                        okButton.setBounds(110, 115, 80, 30);
                        okButton.setFocusPainted(false);
                        okButton.setBorderPainted(false);
                        okButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Đóng hộp thoại khi nhấn OK
                                Window dialog = SwingUtilities.windowForComponent(okButton);
                                dialog.dispose();
                            }
                        });
                        panel.add(okButton);

                        // Hiển thị hộp thoại
                        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.DEFAULT_OPTION, null, new Object[] {});
                        JDialog dialog = optionPane.createDialog(null, "Lỗi");
                        dialog.setVisible(true);
                    }
                }

                CustomMessage msg = new CustomMessage();

                // Kiểm tra trường trống theo thứ tự ưu tiên
                if (email.isEmpty() || email.equals("Tên Đăng Nhập")) {
                    lblErrorUser.setText("Vui lòng nhập tên đăng nhập!");
                    txtUser.requestFocus();
                    return;
                }

                if (password.isEmpty() || password.equals("Mật Khẩu")) {
                    lblErrorPass.setText("Vui lòng nhập mật khẩu!");
                    txtPass.requestFocus();
                    return;
                }

                // Nếu cả hai trường đều được điền thì kiểm tra đăng nhập
                if (taiKhoanBUS.kiemTraDangNhap(email, password)) {
                    msg.showSuccessMessage("Đăng nhập thành công!", "Bạn đã đăng nhập thành công vào hệ thống!");
                } else {
                    msg.showErrorMessage("Đăng nhập thất bại!", "Tên đăng nhập hoặc mật khẩu không đúng!");
                }
            }
        });

        contentP.add(btnLogin);

        // Link Forgot Username/Password
        JLabel lblForgot = new JLabel("Quên mật khẩu?");
        lblForgot.setForeground(Color.GRAY);
        lblForgot.setBounds(460, 305, 200, 20);
        lblForgot.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        // Thêm hiệu ứng con trỏ chuột để trông giống một liên kết
        lblForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Thêm MouseListener để xử lý sự kiện nhấn chuột
        lblForgot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xóa các thông báo lỗi trước đó
                lblErrorUser.setText("");
                lblErrorPass.setText("");
                lblErrorLogin.setText("");

                String email = txtUser.getText().trim();

                // Kiểm tra trường trống
                if (email.isEmpty() || email.equals("Tên Đăng Nhập")) {
                    lblErrorUser.setText("Vui lòng nhập tên đăng nhập!");
                    txtUser.requestFocus();
                    return;
                }

                // Kiểm tra xem tên đăng nhập có tồn tại không
                TaiKhoanDTO taiKhoan = taiKhoanBUS.layTaiKhoanTheoTenDangNhap(email);
                if (taiKhoan == null) {
                    lblErrorUser.setText("Tên đăng nhập không tồn tại!");
                    txtUser.requestFocus();
                    return;
                }

                // Nếu tên đăng nhập tồn tại, mở FormChangePassword với maNV tương ứng
                new FormChangePassword(taiKhoanBUS, taiKhoan.getMaNV()).setVisible(true);
            }

            // Hover
            @Override
            public void mouseEntered(MouseEvent e) {
                lblForgot.setForeground(Color.decode("#60a5fa"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblForgot.setForeground(Color.GRAY);
            }
        });

        contentP.add(lblForgot);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Lấy Connection từ ConnectDB
            Connection conn = ConnectDB.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Khởi tạo TaiKhoanBUS với Connection
            TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS(conn);
            new FormLogin(taiKhoanBUS).setVisible(true);
        });
    }
}
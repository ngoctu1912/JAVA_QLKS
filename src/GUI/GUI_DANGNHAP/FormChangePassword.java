package GUI_DANGNHAP;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;
import java.awt.event.*;
import BUS.TaiKhoanBUS;

public class FormChangePassword extends JFrame {
    private TaiKhoanBUS taiKhoanBUS;
    private int maNV;

    public FormChangePassword(TaiKhoanBUS taiKhoanBUS, int maNV) {
        this.taiKhoanBUS = taiKhoanBUS;
        this.maNV = maNV;
        initComponents();
    }

    private void initComponents() {
        setTitle("Đổi Mật Khẩu");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

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
                        Color.decode("#ff69b4")
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBounds(0, 0, 800, 500);
        panel.setLayout(null);
        add(panel);

        JPanel contentP = new JPanel();
        contentP.setBackground(Color.WHITE);
        contentP.setBounds(50, 30, 700, 420);
        contentP.setLayout(null);
        contentP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(contentP);

        JLabel lblTitle = new JLabel("Đổi Mật Khẩu", SwingConstants.CENTER);
        lblTitle.setBounds(250, 30, 200, 30);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTitle.setForeground(Color.BLACK);
        contentP.add(lblTitle);

        ImageIcon passwordIcon = new ImageIcon("./src/icons/lock.png");
        Image passImg = passwordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        passwordIcon = new ImageIcon(passImg);

        final ImageIcon openEyeIcon = new ImageIcon("./src/icons/openeye.png");
        Image openEyeImg = openEyeIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        final ImageIcon finalOpenEyeIcon = new ImageIcon(openEyeImg);

        final ImageIcon closeEyeIcon = new ImageIcon("./src/icons/closeeye.png");
        Image closeEyeImg = closeEyeIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        final ImageIcon finalCloseEyeIcon = new ImageIcon(closeEyeImg);

        RoundedPanel newPassPanel = new RoundedPanel(50);
        newPassPanel.setBounds(215, 100, 270, 50);
        newPassPanel.setBackground(new Color(240, 240, 240));
        newPassPanel.setOpaque(false);
        newPassPanel.setLayout(null);

        JLabel newPassIconLabel = new JLabel(passwordIcon);
        newPassIconLabel.setBounds(15, 15, 20, 20);
        newPassPanel.add(newPassIconLabel);

        JPasswordField txtNewPass = new JPasswordField("Mật Khẩu Mới");
        txtNewPass.setBounds(50, 12, 170, 25);
        txtNewPass.setBorder(null);
        txtNewPass.setForeground(Color.GRAY);
        txtNewPass.setBackground(new Color(240, 240, 240));
        txtNewPass.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txtNewPass.setEchoChar((char) 0);

        txtNewPass.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtNewPass.getPassword()).equals("Mật Khẩu Mới")) {
                    txtNewPass.setText("");
                    txtNewPass.setEchoChar('*');
                    txtNewPass.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtNewPass.getPassword()).isEmpty()) {
                    txtNewPass.setEchoChar((char) 0);
                    txtNewPass.setText("Mật Khẩu Mới");
                    txtNewPass.setForeground(Color.GRAY);
                }
            }
        });

        JToggleButton newPassEyeToggle = new JToggleButton(finalCloseEyeIcon);
        newPassEyeToggle.setBounds(225, 16, 20, 20);
        newPassEyeToggle.setBorder(null);
        newPassEyeToggle.setContentAreaFilled(false);
        newPassEyeToggle.setFocusPainted(false);

        newPassEyeToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newPassEyeToggle.isSelected()) {
                    newPassEyeToggle.setIcon(finalOpenEyeIcon);
                    if (!String.valueOf(txtNewPass.getPassword()).equals("Mật Khẩu Mới")) {
                        txtNewPass.setEchoChar((char) 0);
                    }
                } else {
                    newPassEyeToggle.setIcon(finalCloseEyeIcon);
                    if (!String.valueOf(txtNewPass.getPassword()).equals("Mật Khẩu Mới")) {
                        txtNewPass.setEchoChar('*');
                    }
                }
            }
        });

        newPassPanel.add(txtNewPass);
        newPassPanel.add(newPassEyeToggle);
        contentP.add(newPassPanel);

        JLabel lblErrorNewPass = new JLabel("");
        lblErrorNewPass.setBounds(240, 150, 270, 20);
        lblErrorNewPass.setForeground(Color.RED);
        lblErrorNewPass.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        contentP.add(lblErrorNewPass);

        RoundedPanel confirmPassPanel = new RoundedPanel(50);
        confirmPassPanel.setBounds(215, 180, 270, 50);
        confirmPassPanel.setBackground(new Color(240, 240, 240));
        confirmPassPanel.setOpaque(false);
        confirmPassPanel.setLayout(null);

        JLabel confirmPassIconLabel = new JLabel(passwordIcon);
        confirmPassIconLabel.setBounds(15, 15, 20, 20);
        confirmPassPanel.add(confirmPassIconLabel);

        JPasswordField txtConfirmPass = new JPasswordField("Xác Nhận Mật Khẩu");
        txtConfirmPass.setBounds(50, 12, 170, 25);
        txtConfirmPass.setBorder(null);
        txtConfirmPass.setForeground(Color.GRAY);
        txtConfirmPass.setBackground(new Color(240, 240, 240));
        txtConfirmPass.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txtConfirmPass.setEchoChar((char) 0);

        txtConfirmPass.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtConfirmPass.getPassword()).equals("Xác Nhận Mật Khẩu")) {
                    txtConfirmPass.setText("");
                    txtConfirmPass.setEchoChar('*');
                    txtConfirmPass.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtConfirmPass.getPassword()).isEmpty()) {
                    txtConfirmPass.setEchoChar((char) 0);
                    txtConfirmPass.setText("Xác Nhận Mật Khẩu");
                    txtConfirmPass.setForeground(Color.GRAY);
                }
            }
        });

        JToggleButton confirmPassEyeToggle = new JToggleButton(finalCloseEyeIcon);
        confirmPassEyeToggle.setBounds(225, 16, 20, 20);
        confirmPassEyeToggle.setBorder(null);
        confirmPassEyeToggle.setContentAreaFilled(false);
        confirmPassEyeToggle.setFocusPainted(false);

        confirmPassEyeToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (confirmPassEyeToggle.isSelected()) {
                    confirmPassEyeToggle.setIcon(finalOpenEyeIcon);
                    if (!String.valueOf(txtConfirmPass.getPassword()).equals("Xác Nhận Mật Khẩu")) {
                        txtConfirmPass.setEchoChar((char) 0);
                    }
                } else {
                    confirmPassEyeToggle.setIcon(finalCloseEyeIcon);
                    if (!String.valueOf(txtConfirmPass.getPassword()).equals("Xác Nhận Mật Khẩu")) {
                        txtConfirmPass.setEchoChar('*');
                    }
                }
            }
        });

        confirmPassPanel.add(txtConfirmPass);
        confirmPassPanel.add(confirmPassEyeToggle);
        contentP.add(confirmPassPanel);

        JLabel lblErrorConfirmPass = new JLabel("");
        lblErrorConfirmPass.setBounds(240, 230, 270, 20);
        lblErrorConfirmPass.setForeground(Color.RED);
        lblErrorConfirmPass.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        contentP.add(lblErrorConfirmPass);

        JButton btnChangePass = new JButton("Xác Nhận");
        btnChangePass.setBackground(new Color(50, 205, 50));
        btnChangePass.setForeground(Color.WHITE);
        btnChangePass.setBounds(215, 270, 270, 45);
        btnChangePass.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnChangePass.setBorder(BorderFactory.createEmptyBorder());
        btnChangePass.setFocusPainted(false);

        btnChangePass.setBorderPainted(false);
        btnChangePass.setContentAreaFilled(false);
        btnChangePass.setOpaque(false);

        btnChangePass.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();

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
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(b.getText());
                int textHeight = fm.getHeight();
                int x = (width - textWidth) / 2;
                int y = (height - textHeight) / 2 + fm.getAscent();
                g2.drawString(b.getText(), x, y);

                g2.dispose();
            }
        });

        btnChangePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblErrorNewPass.setText("");
                lblErrorConfirmPass.setText("");

                String newPass = String.valueOf(txtNewPass.getPassword()).trim();
                String confirmPass = String.valueOf(txtConfirmPass.getPassword()).trim();

                class CustomMessage {
                    private void showSuccessMessage(String message, String description) {
                        JPanel panel = new JPanel();
                        panel.setLayout(null);
                        panel.setBackground(Color.WHITE);
                        panel.setPreferredSize(new java.awt.Dimension(300, 150));

                        ImageIcon icon = new ImageIcon("./src/icons/success.png");
                        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
                        iconLabel.setBounds(130, 10, 40, 40);
                        panel.add(iconLabel);

                        JLabel titleLabel = new JLabel(message);
                        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        titleLabel.setForeground(Color.BLACK);
                        titleLabel.setBounds(0, 60, 300, 20);
                        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        panel.add(titleLabel);

                        JLabel descLabel = new JLabel(description);
                        descLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
                        descLabel.setForeground(Color.GRAY);
                        descLabel.setBounds(0, 85, 300, 20);
                        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        panel.add(descLabel);

                        JButton okButton = new JButton("OK");
                        okButton.setBackground(new Color(66, 133, 244));
                        okButton.setForeground(Color.WHITE);
                        okButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                        okButton.setBounds(110, 115, 80, 30);
                        okButton.setFocusPainted(false);
                        okButton.setBorderPainted(false);
                        okButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Window dialog = SwingUtilities.windowForComponent(okButton);
                                dialog.dispose();
                                dispose();
                                SwingUtilities.invokeLater(() -> {
                                    new FormLogin(taiKhoanBUS).setVisible(true);
                                });
                            }
                        });
                        panel.add(okButton);

                        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
                        JDialog dialog = optionPane.createDialog(null, "Thành công");
                        dialog.setVisible(true);
                    }

                    private void showErrorMessage(String message, String description) {
                        JPanel panel = new JPanel();
                        panel.setLayout(null);
                        panel.setBackground(Color.WHITE);
                        panel.setPreferredSize(new java.awt.Dimension(300, 150));

                        ImageIcon icon = new ImageIcon("src/GUI/icons/error.png");
                        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
                        iconLabel.setBounds(130, 10, 40, 40);
                        panel.add(iconLabel);

                        JLabel titleLabel = new JLabel(message);
                        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        titleLabel.setForeground(Color.BLACK);
                        titleLabel.setBounds(0, 60, 300, 20);
                        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        panel.add(titleLabel);

                        JLabel descLabel = new JLabel(description);
                        descLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
                        descLabel.setForeground(Color.GRAY);
                        descLabel.setBounds(0, 85, 300, 20);
                        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        panel.add(descLabel);

                        JButton okButton = new JButton("OK");
                        okButton.setBackground(new Color(66, 133, 244));
                        okButton.setForeground(Color.WHITE);
                        okButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                        okButton.setBounds(110, 115, 80, 30);
                        okButton.setFocusPainted(false);
                        okButton.setBorderPainted(false);
                        okButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Window dialog = SwingUtilities.windowForComponent(okButton);
                                dialog.dispose();
                            }
                        });
                        panel.add(okButton);

                        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
                        JDialog dialog = optionPane.createDialog(null, "Lỗi");
                        dialog.setVisible(true);
                    }
                }

                CustomMessage msg = new CustomMessage();

                if (newPass.isEmpty() || newPass.equals("Mật Khẩu Mới")) {
                    lblErrorNewPass.setText("Vui lòng nhập mật khẩu mới!");
                    txtNewPass.requestFocus();
                    return;
                }

                if (confirmPass.isEmpty() || confirmPass.equals("Xác Nhận Mật Khẩu")) {
                    lblErrorConfirmPass.setText("Vui lòng xác nhận mật khẩu!");
                    txtConfirmPass.requestFocus();
                    return;
                }

                if (!newPass.equals(confirmPass)) {
                    lblErrorConfirmPass.setText("Mật khẩu xác nhận không khớp!");
                    txtConfirmPass.requestFocus();
                    return;
                }

                boolean updateSuccess = taiKhoanBUS.capNhatMatKhau(maNV, newPass);
                if (updateSuccess) {
                    msg.showSuccessMessage("Đổi mật khẩu thành công!", "Mật khẩu của bạn đã được cập nhật!");
                } else {
                    msg.showErrorMessage("Đổi mật khẩu thất bại!", "Không thể cập nhật mật khẩu. Vui lòng thử lại!");
                }
            }
        });

        contentP.add(btnChangePass);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS(null);
            new FormChangePassword(taiKhoanBUS, 1).setVisible(true);
        });
    }
}
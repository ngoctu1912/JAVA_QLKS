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
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;
import BUS.TaiKhoanBUS;

public class DoiMatKhauComponent {
    private JPanel contentP;
    private TaiKhoanBUS taiKhoanBUS;
    private int id;

    // Các thành phần giao diện
    public JLabel lblTitle;
    public JPasswordField txtNewPass;
    public JPasswordField txtConfirmPass;
    public JToggleButton newPassEyeToggle;
    public JToggleButton confirmPassEyeToggle;
    public JButton btnChangePass;
    public JLabel lblErrorNewPass;
    public JLabel lblErrorConfirmPass;
    public RoundedPanel newPassPanel;
    public RoundedPanel confirmPassPanel;

    public DoiMatKhauComponent(JPanel contentP, TaiKhoanBUS taiKhoanBUS, int id) {
        this.contentP = contentP;
        this.taiKhoanBUS = taiKhoanBUS;
        this.id = id;
        initComponents();
    }

    private void initComponents() {
        lblTitle = new JLabel("Đổi Mật Khẩu", SwingConstants.CENTER);
        lblTitle.setBounds(300, 30, 200, 30);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTitle.setForeground(Color.BLACK);
        contentP.add(lblTitle);

        ImageIcon passwordIcon = new ImageIcon("./src/icons/lock.png");
        Image passImg = passwordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        passwordIcon = new ImageIcon(passImg);

        ImageIcon openEyeIcon = new ImageIcon("./src/icons/openeye.png");
        Image openEyeImg = openEyeIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        ImageIcon finalOpenEyeIcon = new ImageIcon(openEyeImg);

        ImageIcon closeEyeIcon = new ImageIcon("./src/icons/closeeye.png");
        Image closeEyeImg = closeEyeIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        ImageIcon finalCloseEyeIcon = new ImageIcon(closeEyeImg);

        // New Password Field
        newPassPanel = new RoundedPanel(50);
        newPassPanel.setBounds(265, 100, 270, 50);
        newPassPanel.setBackground(Color.decode("#f0f0f0"));
        newPassPanel.setOpaque(false);
        newPassPanel.setLayout(null);

        JLabel newPassIconLabel = new JLabel(passwordIcon);
        newPassIconLabel.setBounds(15, 15, 20, 20);
        newPassPanel.add(newPassIconLabel);

        txtNewPass = new JPasswordField("Mật Khẩu Mới");
        txtNewPass.setBounds(50, 12, 170, 25);
        txtNewPass.setBorder(null);
        txtNewPass.setForeground(Color.GRAY);
        txtNewPass.setBackground(Color.decode("#f0f0f0"));
        txtNewPass.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txtNewPass.setEchoChar((char) 0);
        newPassPanel.add(txtNewPass);

        newPassEyeToggle = new JToggleButton(finalCloseEyeIcon);
        newPassEyeToggle.setBounds(225, 16, 20, 20);
        newPassEyeToggle.setBorder(null);
        newPassEyeToggle.setContentAreaFilled(false);
        newPassEyeToggle.setFocusPainted(false);
        newPassPanel.add(newPassEyeToggle);
        contentP.add(newPassPanel);

        lblErrorNewPass = new JLabel("");
        lblErrorNewPass.setBounds(290, 150, 270, 20);
        lblErrorNewPass.setForeground(Color.RED);
        lblErrorNewPass.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        contentP.add(lblErrorNewPass);

        // Confirm Password Field
        confirmPassPanel = new RoundedPanel(50);
        confirmPassPanel.setBounds(265, 180, 270, 50);
        confirmPassPanel.setBackground(Color.decode("#f0f0f0"));
        confirmPassPanel.setOpaque(false);
        confirmPassPanel.setLayout(null);

        JLabel confirmPassIconLabel = new JLabel(passwordIcon);
        confirmPassIconLabel.setBounds(15, 15, 20, 20);
        confirmPassPanel.add(confirmPassIconLabel);

        txtConfirmPass = new JPasswordField("Xác Nhận Mật Khẩu");
        txtConfirmPass.setBounds(50, 12, 170, 25);
        txtConfirmPass.setBorder(null);
        txtConfirmPass.setForeground(Color.GRAY);
        txtConfirmPass.setBackground(Color.decode("#f0f0f0"));
        txtConfirmPass.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txtConfirmPass.setEchoChar((char) 0);
        confirmPassPanel.add(txtConfirmPass);

        confirmPassEyeToggle = new JToggleButton(finalCloseEyeIcon);
        confirmPassEyeToggle.setBounds(225, 16, 20, 20);
        confirmPassEyeToggle.setBorder(null);
        confirmPassEyeToggle.setContentAreaFilled(false);
        confirmPassEyeToggle.setFocusPainted(false);
        confirmPassPanel.add(confirmPassEyeToggle);
        contentP.add(confirmPassPanel);

        lblErrorConfirmPass = new JLabel("");
        lblErrorConfirmPass.setBounds(290, 230, 270, 20);
        lblErrorConfirmPass.setForeground(Color.RED);
        lblErrorConfirmPass.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        contentP.add(lblErrorConfirmPass);

        // Submit Button
        btnChangePass = new JButton("Xác Nhận");
        btnChangePass.setBackground(Color.decode("#32cd32"));
        btnChangePass.setForeground(Color.WHITE);
        btnChangePass.setBounds(265, 270, 270, 45);
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
        contentP.add(btnChangePass);
    }

    // Getter để truy cập các thành phần
    public JPasswordField getTxtNewPass() { return txtNewPass; }
    public JPasswordField getTxtConfirmPass() { return txtConfirmPass; }
    public JToggleButton getNewPassEyeToggle() { return newPassEyeToggle; }
    public JToggleButton getConfirmPassEyeToggle() { return confirmPassEyeToggle; }
    public JButton getBtnChangePass() { return btnChangePass; }
    public JLabel getLblErrorNewPass() { return lblErrorNewPass; }
    public JLabel getLblErrorConfirmPass() { return lblErrorConfirmPass; }
}
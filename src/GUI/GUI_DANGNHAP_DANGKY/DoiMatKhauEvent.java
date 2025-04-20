package GUI_DANGNHAP_DANGKY;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import BUS.TaiKhoanBUS;

public class DoiMatKhauEvent {
    private DoiMatKhauComponent components;
    private TaiKhoanBUS taiKhoanBUS;
    private int id;
    private String accountType;

    // Eye Icons
    private final ImageIcon openEyeIcon;
    private final ImageIcon closeEyeIcon;

    public DoiMatKhauEvent(DoiMatKhauComponent components, TaiKhoanBUS taiKhoanBUS, 
                                     int id, String accountType) {
        this.components = components;
        this.taiKhoanBUS = taiKhoanBUS;
        this.id = id;
        this.accountType = accountType;

        // Initialize eye icons
        ImageIcon tempOpenEyeIcon = new ImageIcon("./src/icons/openeye.png");
        openEyeIcon = new ImageIcon(tempOpenEyeIcon.getImage().getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH));

        ImageIcon tempCloseEyeIcon = new ImageIcon("./src/icons/closeeye.png");
        closeEyeIcon = new ImageIcon(tempCloseEyeIcon.getImage().getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH));
    }

    public void setupEvents() {
        // FocusListener for txtNewPass
        components.getTxtNewPass().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(components.getTxtNewPass().getPassword()).equals("Mật Khẩu Mới")) {
                    components.getTxtNewPass().setText("");
                    components.getTxtNewPass().setEchoChar('*');
                    components.getTxtNewPass().setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(components.getTxtNewPass().getPassword()).isEmpty()) {
                    components.getTxtNewPass().setEchoChar((char) 0);
                    components.getTxtNewPass().setText("Mật Khẩu Mới");
                    components.getTxtNewPass().setForeground(Color.GRAY);
                }
            }
        });

        // FocusListener for txtConfirmPass
        components.getTxtConfirmPass().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(components.getTxtConfirmPass().getPassword()).equals("Xác Nhận Mật Khẩu")) {
                    components.getTxtConfirmPass().setText("");
                    components.getTxtConfirmPass().setEchoChar('*');
                    components.getTxtConfirmPass().setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(components.getTxtConfirmPass().getPassword()).isEmpty()) {
                    components.getTxtConfirmPass().setEchoChar((char) 0);
                    components.getTxtConfirmPass().setText("Xác Nhận Mật Khẩu");
                    components.getTxtConfirmPass().setForeground(Color.GRAY);
                }
            }
        });

        // ActionListener for newPassEyeToggle
        components.getNewPassEyeToggle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (components.getNewPassEyeToggle().isSelected()) {
                    components.getNewPassEyeToggle().setIcon(openEyeIcon);
                    if (!String.valueOf(components.getTxtNewPass().getPassword()).equals("Mật Khẩu Mới")) {
                        components.getTxtNewPass().setEchoChar((char) 0);
                    }
                } else {
                    components.getNewPassEyeToggle().setIcon(closeEyeIcon);
                    if (!String.valueOf(components.getTxtNewPass().getPassword()).equals("Mật Khẩu Mới")) {
                        components.getTxtNewPass().setEchoChar('*');
                    }
                }
            }
        });

        // ActionListener for confirmPassEyeToggle
        components.getConfirmPassEyeToggle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (components.getConfirmPassEyeToggle().isSelected()) {
                    components.getConfirmPassEyeToggle().setIcon(openEyeIcon);
                    if (!String.valueOf(components.getTxtConfirmPass().getPassword()).equals("Xác Nhận Mật Khẩu")) {
                        components.getTxtConfirmPass().setEchoChar((char) 0);
                    }
                } else {
                    components.getConfirmPassEyeToggle().setIcon(closeEyeIcon);
                    if (!String.valueOf(components.getTxtConfirmPass().getPassword()).equals("Xác Nhận Mật Khẩu")) {
                        components.getTxtConfirmPass().setEchoChar('*');
                    }
                }
            }
        });

        // Real-time validation for txtNewPass
        components.getTxtNewPass().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateNewPass();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateNewPass();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateNewPass();
            }

            private void validateNewPass() {
                String newPass = String.valueOf(components.getTxtNewPass().getPassword()).trim();
                if (newPass.isEmpty() || newPass.equals("Mật Khẩu Mới")) {
                    components.getLblErrorNewPass().setText("");
                } else if (newPass.length() < 6) {
                    components.getLblErrorNewPass().setText("Mật khẩu phải có ít nhất 6 ký tự!");
                } else {
                    components.getLblErrorNewPass().setText("");
                }
            }
        });

        // Real-time validation for txtConfirmPass
        components.getTxtConfirmPass().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateConfirmPass();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateConfirmPass();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateConfirmPass();
            }

            private void validateConfirmPass() {
                String newPass = String.valueOf(components.getTxtNewPass().getPassword()).trim();
                String confirmPass = String.valueOf(components.getTxtConfirmPass().getPassword()).trim();
                if (confirmPass.isEmpty() || confirmPass.equals("Xác Nhận Mật Khẩu")) {
                    components.getLblErrorConfirmPass().setText("");
                } else if (!newPass.equals("Mật Khẩu Mới") && !newPass.isEmpty() && !confirmPass.equals(newPass)) {
                    components.getLblErrorConfirmPass().setText("Mật khẩu không trùng khớp!");
                } else {
                    components.getLblErrorConfirmPass().setText("");
                }
            }
        });

        // ActionListener for btnChangePass
        components.getBtnChangePass().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPass = String.valueOf(components.getTxtNewPass().getPassword()).trim();
                String confirmPass = String.valueOf(components.getTxtConfirmPass().getPassword()).trim();

                if (newPass.isEmpty() || newPass.equals("Mật Khẩu Mới")) {
                    components.getLblErrorNewPass().setText("Vui lòng nhập mật khẩu mới!");
                    return;
                } else {
                    components.getLblErrorNewPass().setText("");
                }

                if (newPass.length() < 6) {
                    components.getLblErrorNewPass().setText("Mật khẩu phải có ít nhất 6 ký tự!");
                    return;
                } else {
                    components.getLblErrorNewPass().setText("");
                }

                if (confirmPass.isEmpty() || confirmPass.equals("Xác Nhận Mật Khẩu")) {
                    components.getLblErrorConfirmPass().setText("Vui lòng xác nhận mật khẩu!");
                    return;
                } else {
                    components.getLblErrorConfirmPass().setText("");
                }

                if (!newPass.equals(confirmPass)) {
                    components.getLblErrorConfirmPass().setText("Mật khẩu không trùng khớp!");
                    return;
                } else {
                    components.getLblErrorConfirmPass().setText("");
                }

                CustomMessage msg = new CustomMessage();
                boolean updateSuccess = false;
                if ("QUANLY".equals(accountType)) {
                    updateSuccess = taiKhoanBUS.capNhatMatKhau(id, newPass);
                } else if ("KHACHHANG".equals(accountType)) {
                    updateSuccess = taiKhoanBUS.capNhatMatKhau(id, newPass);
                }

                if (updateSuccess) {
                    msg.showSuccessMessage("Đổi mật khẩu thành công!", "Mật khẩu của bạn đã được cập nhật!", null);
                    components.getTxtNewPass().getTopLevelAncestor().setVisible(false);
                    SwingUtilities.invokeLater(() -> new DNDKFrame(null).setVisible(true));
                } else {
                    msg.showErrorMessage("Đổi mật khẩu thất bại!", "Không thể cập nhật mật khẩu. Vui lòng thử lại!");
                }
            }
        });
    }
}
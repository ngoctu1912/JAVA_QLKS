package GUI_DANGNHAP_DANGKY;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import java.util.function.Consumer;

import BUS.KhachHangBUS;
import BUS.TaiKhoanBUS;
import DAO.KhachHangDAO;
import DAO.TaiKhoanDAO;
import DAO.TaiKhoanKHDAO;
import DTO.KhachHangDTO;
import DTO.TaiKhoanDTO;
import DTO.TaiKhoanKHDTO;

public class DNDKEvent {
    private final DNDKComponent components;
    private final TaiKhoanBUS taiKhoanBUS;
    private final KhachHangBUS khachHangBUS;
    private final Consumer<DNDKComponent.AccountInfo> loginCallback;

    public DNDKEvent(DNDKComponent components, TaiKhoanBUS taiKhoanBUS,
            Consumer<DNDKComponent.AccountInfo> loginCallback) {
        this.components = components;
        this.taiKhoanBUS = taiKhoanBUS;
        this.khachHangBUS = new KhachHangBUS();
        this.loginCallback = loginCallback;
    }

    public void setupEvents() {
        components.getTxtUser().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (components.getTxtUser().getText().equals("Tên Đăng Nhập")) {
                    components.getTxtUser().setText("");
                    components.getTxtUser().setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (components.getTxtUser().getText().isEmpty()) {
                    components.getTxtUser().setText("Tên Đăng Nhập");
                    components.getTxtUser().setForeground(Color.GRAY);
                }
            }
        });

        components.getTxtPass().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(components.getTxtPass().getPassword()).equals("Mật Khẩu")) {
                    components.getTxtPass().setText("");
                    components.getTxtPass().setEchoChar('*');
                    components.getTxtPass().setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(components.getTxtPass().getPassword()).isEmpty()) {
                    components.getTxtPass().setEchoChar((char) 0);
                    components.getTxtPass().setText("Mật Khẩu");
                    components.getTxtPass().setForeground(Color.GRAY);
                }
            }
        });

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

        components.getEyeTogglePass().addActionListener(e -> {
            if (components.getEyeTogglePass().isSelected()) {
                components.getEyeTogglePass().setIcon(components.openEyeIcon);
                if (!String.valueOf(components.getTxtPass().getPassword()).equals("Mật Khẩu")) {
                    components.getTxtPass().setEchoChar((char) 0);
                }
            } else {
                components.getEyeTogglePass().setIcon(components.closeEyeIcon);
                if (!String.valueOf(components.getTxtPass().getPassword()).equals("Mật Khẩu")) {
                    components.getTxtPass().setEchoChar('*');
                }
            }
        });

        components.getEyeToggleConfirm().addActionListener(e -> {
            if (components.getEyeToggleConfirm().isSelected()) {
                components.getEyeToggleConfirm().setIcon(components.openEyeIcon);
                if (!String.valueOf(components.getTxtConfirmPass().getPassword()).equals("Xác Nhận Mật Khẩu")) {
                    components.getTxtConfirmPass().setEchoChar((char) 0);
                }
            } else {
                components.getEyeToggleConfirm().setIcon(components.closeEyeIcon);
                if (!String.valueOf(components.getTxtConfirmPass().getPassword()).equals("Xác Nhận Mật Khẩu")) {
                    components.getTxtConfirmPass().setEchoChar('*');
                }
            }
        });

        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Enter key pressed in " + (components.isLoginMode ? "Login" : "Register") + " mode");
                    components.getBtnAction().doClick();
                }
            }
        };

        components.getTxtUser().addKeyListener(enterKeyListener);
        components.getTxtPass().addKeyListener(enterKeyListener);
        components.getTxtConfirmPass().addKeyListener(enterKeyListener);

        components.getBtnAction().addActionListener(e -> {
            components.getLblErrorUser().setText("");
            components.getLblErrorPass().setText("");
            components.getLblErrorConfirm().setText("");
            components.getLblErrorAction().setText("");

            String username = components.getTxtUser().getText().trim();
            String password = new String(components.getTxtPass().getPassword()).trim();

            if (components.isLoginMode) {
                // Kiểm tra tài khoản
                DNDKComponent.AccountInfo accountInfo = components.checkAccount();
                if (accountInfo == null) {
                    return; // Đã hiển thị lỗi trong checkAccount
                }

                // Kiểm tra mật khẩu
                if (!taiKhoanBUS.kiemTraDangNhap(username, password)) {
                    components.getLblErrorPass().setText("Mật khẩu không đúng!");
                    return;
                }

                // Thực hiện các hành động sau khi đăng nhập thành công
                System.out.println("Login successful for username: " + username);
                SwingUtilities.getWindowAncestor(components.getBtnAction()).dispose();
                if (loginCallback != null) {
                    loginCallback.accept(accountInfo); // Truyền AccountInfo
                }
            } else {
                String confirmPassword = new String(components.getTxtConfirmPass().getPassword()).trim();

                if (username.isEmpty() || username.equals("Tên Đăng Nhập")) {
                    components.getLblErrorUser().setText("Vui lòng nhập tên đăng nhập!");
                    components.getTxtUser().requestFocus();
                    return;
                }

                if (password.isEmpty() || password.equals("Mật Khẩu")) {
                    components.getLblErrorPass().setText("Vui lòng nhập mật khẩu!");
                    components.getTxtPass().requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    components.getLblErrorPass().setText("Mật khẩu phải có ít nhất 6 ký tự!");
                    components.getTxtPass().requestFocus();
                    return;
                }

                if (confirmPassword.isEmpty() || confirmPassword.equals("Xác Nhận Mật Khẩu")) {
                    components.getLblErrorConfirm().setText("Vui lòng xác nhận mật khẩu!");
                    components.getTxtConfirmPass().requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    components.getLblErrorConfirm().setText("Mật khẩu không khớp!");
                    components.getTxtConfirmPass().requestFocus();
                    return;
                }

                if (!taiKhoanBUS.checkTDN(username)) {
                    components.getLblErrorUser().setText("Tên đăng nhập đã tồn tại!");
                    components.getTxtUser().requestFocus();
                    return;
                }

                int maKhachHang = taiKhoanBUS.layMaKhachHangTuDong();
                TaiKhoanKHDTO taiKhoan = new TaiKhoanKHDTO();
                taiKhoan.setTenDangNhap(username);
                taiKhoan.setMatKhau(password);
                taiKhoan.setMaKhachHang(maKhachHang);
                taiKhoan.setMaNhomQuyen(4);
                taiKhoan.setTrangThai(1);

                CustomMessage msg = new CustomMessage();
                if (taiKhoanBUS.addAccKH(taiKhoan)) {
                    msg.showSuccessMessage("Đăng ký thành công!",
                            "Tài khoản khách hàng của bạn đã được tạo!",
                            new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    components.setLoginMode(true);
                                    components.getTxtUser().setText("Tên Đăng Nhập");
                                    components.getTxtUser().setForeground(Color.GRAY);
                                    components.getTxtPass().setText("Mật Khẩu");
                                    components.getTxtPass().setEchoChar((char) 0);
                                    components.getTxtPass().setForeground(Color.GRAY);
                                    components.getTxtConfirmPass().setText("Xác Nhận Mật Khẩu");
                                    components.getTxtConfirmPass().setEchoChar((char) 0);
                                    components.getTxtConfirmPass().setForeground(Color.GRAY);
                                }
                            });
                } else {
                    msg.showErrorMessage("Lỗi đăng ký", "Đăng ký tài khoản thất bại! Vui lòng thử lại.");
                }
            }
        });

        components.getLblSwitchMode().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                components.setLoginMode(!components.isLoginMode);
                components.getLblErrorUser().setText("");
                components.getLblErrorPass().setText("");
                components.getLblErrorConfirm().setText("");
                components.getLblErrorAction().setText("");
                components.getTxtUser().setText("Tên Đăng Nhập");
                components.getTxtUser().setForeground(Color.GRAY);
                components.getTxtPass().setText("Mật Khẩu");
                components.getTxtPass().setEchoChar((char) 0);
                components.getTxtPass().setForeground(Color.GRAY);
                components.getTxtConfirmPass().setText("Xác Nhận Mật Khẩu");
                components.getTxtConfirmPass().setEchoChar((char) 0);
                components.getTxtConfirmPass().setForeground(Color.GRAY);
                components.getTxtUser().setFocusable(false);
                components.getTxtPass().setFocusable(false);
                components.getTxtConfirmPass().setFocusable(false);
                SwingUtilities.invokeLater(() -> {
                    components.getContentP().requestFocusInWindow();
                    components.getTxtUser().setFocusable(true);
                    components.getTxtPass().setFocusable(true);
                    components.getTxtConfirmPass().setFocusable(true);
                });
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                components.getLblSwitchMode().setForeground(Color.decode("#60a5fa"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                components.getLblSwitchMode().setForeground(Color.GRAY);
            }
        });

        components.getLblForgot().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                components.getLblErrorUser().setText("");
                components.getLblErrorPass().setText("");
                components.getLblErrorAction().setText("");

                String username = components.getTxtUser().getText().trim();

                if (username.isEmpty() || username.equals("Tên Đăng Nhập")) {
                    components.getLblErrorUser().setText("Vui lòng nhập tên đăng nhập!");
                    components.getTxtUser().requestFocus();
                    return;
                }

                TaiKhoanKHDTO taiKhoanKH = TaiKhoanKHDAO.getInstance().selectByUser(username);
                if (taiKhoanKH != null && taiKhoanKH.getTrangThai() == 1) {
                    try {
                        DoiMatKhauFrame changePassFrame = new DoiMatKhauFrame(
                                taiKhoanBUS, null, taiKhoanKH.getMaKhachHang(), "KHACHHANG");
                        changePassFrame.setVisible(true);
                    } catch (Exception ex) {
                        CustomMessage msg = new CustomMessage();
                        msg.showErrorMessage("Lỗi", "Không thể mở form đổi mật khẩu: " + ex.getMessage());
                    }
                    return;
                }

                TaiKhoanDTO taiKhoanNV = TaiKhoanDAO.getInstance().selectByUser(username);
                if (taiKhoanNV != null && taiKhoanNV.getTrangThai() == 1) {
                    try {
                        DoiMatKhauFrame changePassFrame = new DoiMatKhauFrame(
                                taiKhoanBUS, null, taiKhoanNV.getMaNV(), "QUANLY");
                        changePassFrame.setVisible(true);
                    } catch (Exception ex) {
                        CustomMessage msg = new CustomMessage();
                        msg.showErrorMessage("Lỗi", "Không thể mở form đổi mật khẩu: " + ex.getMessage());
                    }
                    return;
                }

                components.getLblErrorUser().setText("Tên đăng nhập không tồn tại hoặc tài khoản bị khóa!");
                components.getTxtUser().requestFocus();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                components.getLblForgot().setForeground(Color.decode("#60a5fa"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                components.getLblForgot().setForeground(Color.GRAY);
            }
        });

        SwingUtilities.invokeLater(() -> components.getContentP().requestFocusInWindow());
    }
}
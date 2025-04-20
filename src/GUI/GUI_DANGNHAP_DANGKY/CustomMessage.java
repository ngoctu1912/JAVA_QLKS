package GUI_DANGNHAP_DANGKY;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class CustomMessage {
    private static final int YES_OPTION = JOptionPane.YES_OPTION;
    private static final int NO_OPTION = JOptionPane.NO_OPTION;
    private int result = NO_OPTION; // Biến lưu kết quả lựa chọn

    // Phương thức hiển thị thông báo thành công (giữ nguyên)
    public void showSuccessMessage(String message, String description, ActionListener onOkAction) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 150));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Biểu tượng dấu tích
        ImageIcon icon;
        try {
            icon = new FlatSVGIcon("icons/success.svg");
            if (icon.getIconWidth() == -1) {
                throw new Exception("Icon không tồn tại");
            }
        } catch (Exception e) {
            System.err.println("Không thể tải icon success.png: " + e.getMessage());
            icon = new ImageIcon();
        }
        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(iconLabel, gbc);

        // Tiêu đề
        JLabel titleLabel = new JLabel(message);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(titleLabel, gbc);

        // Mô tả
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        descLabel.setForeground(Color.GRAY);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(descLabel, gbc);

        // Nút OK
        JButton okButton = new JButton("OK");
        okButton.setBackground(Color.decode("#4286f4"));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        okButton.setPreferredSize(new Dimension(80, 30));
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.addActionListener(e -> {
            Window dialog = SwingUtilities.windowForComponent(okButton);
            dialog.dispose();
            if (onOkAction != null) {
                onOkAction.actionPerformed(e);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(okButton, gbc);

        // Hiển thị hộp thoại
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        JDialog dialog = optionPane.createDialog(null, "Thành công");
        dialog.setVisible(true);
    }

    // Phương thức hiển thị thông báo lỗi (giữ nguyên)
    public void showErrorMessage(String message, String description) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 150));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Biểu tượng lỗi
        ImageIcon icon;
        try {
            icon = new FlatSVGIcon("icons/error.svg");
            if (icon.getIconWidth() == -1) {
                throw new Exception("Icon không tồn tại");
            }
        } catch (Exception e) {
            System.err.println("Không thể tải icon error.png: " + e.getMessage());
            icon = new ImageIcon();
        }
        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(iconLabel, gbc);

        // Tiêu đề
        JLabel titleLabel = new JLabel(message);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(titleLabel, gbc);

        // Mô tả
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        descLabel.setForeground(Color.GRAY);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(descLabel, gbc);

        // Nút OK
        JButton okButton = new JButton("OK");
        okButton.setBackground(Color.decode("#4286f4"));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        okButton.setPreferredSize(new Dimension(80, 30));
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.addActionListener(e -> {
            Window dialog = SwingUtilities.windowForComponent(okButton);
            dialog.dispose();
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(okButton, gbc);

        // Hiển thị hộp thoại
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        JDialog dialog = optionPane.createDialog(null, "Lỗi");
        dialog.setVisible(true);
    }

    // Phương thức mới: Hiển thị hộp thoại xác nhận tùy chỉnh
    public int showConfirmMessage(String title, String description) {
        result = NO_OPTION; // Đặt mặc định là NO

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 150));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Biểu tượng câu hỏi (có thể thay bằng icon khác nếu muốn)
        ImageIcon icon;
        try {
            icon = new FlatSVGIcon("icons/logout.svg"); // Đổi thành icon phù hợp
            if (icon.getIconWidth() == -1) {
                throw new Exception("Icon không tồn tại");
            }
        } catch (Exception e) {
            System.err.println("Không thể tải icon " + e.getMessage());
            icon = new ImageIcon();
        }
        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(iconLabel, gbc);

        // Tiêu đề
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Mô tả
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        descLabel.setForeground(Color.GRAY);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(descLabel, gbc);

        // Nút Yes
        JButton yesButton = new JButton("Yes");
        yesButton.setBackground(Color.decode("#4286f4"));
        yesButton.setForeground(Color.WHITE);
        yesButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        yesButton.setPreferredSize(new Dimension(80, 30));
        yesButton.setFocusPainted(false);
        yesButton.setBorderPainted(false);
        yesButton.addActionListener(e -> {
            result = YES_OPTION;
            Window dialog = SwingUtilities.windowForComponent(yesButton);
            dialog.dispose();
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(yesButton, gbc);

        // Nút No
        JButton noButton = new JButton("No");
        noButton.setBackground(Color.decode("#d9534f")); // Màu đỏ cho nút No
        noButton.setForeground(Color.WHITE);
        noButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        noButton.setPreferredSize(new Dimension(80, 30));
        noButton.setFocusPainted(false);
        noButton.setBorderPainted(false);
        noButton.addActionListener(e -> {
            result = NO_OPTION;
            Window dialog = SwingUtilities.windowForComponent(noButton);
            dialog.dispose();
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(noButton, gbc);

        // Hiển thị hộp thoại
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        JDialog dialog = optionPane.createDialog(null, "Xác nhận");
        dialog.setVisible(true);

        return result;
    }
}


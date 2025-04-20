package GUI_TRANGCHU;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class MainContentComponents {
    private Image backgroundImage;
    private JPanel headerPanel;
    private JPanel featuresPanel;

    public MainContentComponents() {
        initComponents();
    }

    private void initComponents() {
        // Tải hình nền
        backgroundImage = new ImageIcon("./src/icons/banner.jpg").getImage();

        // Panel tiêu đề với hình nền
        headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), 250, this);
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(0, 250));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("VỀ KHÁCH SẠN CHÚNG TÔI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setForeground(Color.decode("#3a5a40"));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(260, 0, 0, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel nội dung đặc điểm
        featuresPanel = new JPanel();
        featuresPanel.setLayout(new GridLayout(2, 2, 30, 30));
        featuresPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        featuresPanel.setOpaque(false);

        featuresPanel.add(createFeaturePanel("icons/convenient.svg", "Tiện ích đầy đủ",
                "Khách sạn cung cấp đầy đủ các tiện nghi hiện đại như điều hòa, truyền hình cáp, wifi."));
        featuresPanel.add(createFeaturePanel("icons/security.svg", "An ninh nghiêm ngặt",
                "Hệ thống camera 24/7, thẻ từ ra vào và đội ngũ bảo vệ chuyên nghiệp."));
        featuresPanel.add(createFeaturePanel("icons/privacy.svg", "Bảo mật thông tin",
                "Cam kết bảo mật tuyệt đối thông tin cá nhân và giao dịch của khách hàng."));
        featuresPanel.add(createFeaturePanel("icons/service.svg", "Dịch vụ chuyên nghiệp",
                "Đội ngũ nhân viên được đào tạo bài bản, phục vụ tận tình."));
    }

    private JPanel createFeaturePanel(String iconPath, String title, String description) {
        JPanel featurePanel = new JPanel(new BorderLayout(10, 10));
        featurePanel.setBackground(Color.WHITE);
        featurePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        FlatSVGIcon icon = new FlatSVGIcon(iconPath);
        Image scaledIcon = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 102, 102));

        JTextArea descText = new JTextArea(description);
        descText.setFont(new Font("Arial", Font.PLAIN, 14));
        descText.setLineWrap(true);
        descText.setWrapStyleWord(true);
        descText.setEditable(false);
        descText.setOpaque(false);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(descText, BorderLayout.CENTER);

        featurePanel.add(iconLabel, BorderLayout.WEST);
        featurePanel.add(textPanel, BorderLayout.CENTER);

        return featurePanel;
    }

    // Getter để truy cập các thành phần
    public JPanel getHeaderPanel() {
        return headerPanel;
    }

    public JPanel getFeaturesPanel() {
        return featuresPanel;
    }
}
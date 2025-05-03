package GUI_DATPHONG;

import BUS.DichVuBUS;
import BUS.KhachHangBUS;
import BUS.PhongBUS;
import DTO.DichVuDTO;
import DTO.KhachHangDTO;
import DTO.PhongDTO;
import GUI_HOADON.HoaDonDialog;
import GUI_TRANGCHU.HomeFrame;
import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

// ... (phần đầu giữ nguyên)

public class BookingDialog {
    private final JComponent parent;
    private final DichVuBUS dichVuBUS;
    private final KhachHangBUS khachHangBUS;
    private final PhongBUS phongBUS;
    private final List<String> selectedRoomIds;
    private final String selectedRoomsText;
    private final Date checkInDate;
    private final Date checkOutDate;
    private final AccountInfo accountInfo;

    public BookingDialog(JComponent parent, List<String> selectedRoomIds, String selectedRoomsText, 
                         Date checkInDate, Date checkOutDate) {
        this.parent = parent;
        this.dichVuBUS = new DichVuBUS();
        this.khachHangBUS = new KhachHangBUS();
        this.phongBUS = new PhongBUS();
        this.selectedRoomIds = selectedRoomIds;
        this.selectedRoomsText = selectedRoomsText;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

        // Get account info from HomeFrame
        HomeFrame homeFrame = (HomeFrame) SwingUtilities.getAncestorOfClass(HomeFrame.class, parent);
        this.accountInfo = homeFrame != null ? homeFrame.getAccountInfo() : null;
    }

    public void showDialog() {
        // Validate dates
        if (checkInDate == null || checkOutDate == null) {
            JOptionPane.showMessageDialog(parent, "Ngày nhận phòng và trả phòng không được để trống!", 
                                         "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate)) {
            JOptionPane.showMessageDialog(parent, "Ngày trả phòng phải sau ngày nhận phòng!", 
                                         "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate login status
        if (accountInfo == null || !"KHACHHANG".equals(accountInfo.getAccountType())) {
            JOptionPane.showMessageDialog(parent, "Vui lòng đăng nhập tài khoản khách hàng để đặt phòng!", 
                                         "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) null, "Xác nhận đặt phòng", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(parent);

        // Header
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(new Color(149, 213, 178));
        headerPanel.setPreferredSize(new Dimension(500, 50));
        JLabel headerLabel = new JLabel("XÁC NHẬN ĐẶT PHÒNG");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerPanel.add(headerLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Phòng đã chọn
        JLabel roomTitle = new JLabel("Phòng đã chọn", SwingConstants.CENTER);
        roomTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        roomTitle.setForeground(new Color(66, 133, 244));
        roomTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(roomTitle);
        contentPanel.add(Box.createVerticalStrut(10));

        JTextPane roomTextPane = new JTextPane();
        roomTextPane.setContentType("text/html");
        roomTextPane.setText(
                "<html><div style='text-align: center; font-family: SansSerif; font-size: 12pt;'>" +
                        selectedRoomsText.replace("\n", "<br>") +
                        "</div></html>");
        roomTextPane.setEditable(false);
        roomTextPane.setBackground(new Color(248, 248, 248));
        roomTextPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane roomScrollPane = new JScrollPane(roomTextPane);
        roomScrollPane.setPreferredSize(new Dimension(400, 100));
        roomScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        roomScrollPane.getViewport().setBackground(new Color(248, 248, 248));
        contentPanel.add(roomScrollPane);
        contentPanel.add(Box.createVerticalStrut(20));

        // Tiêu đề dịch vụ
        JLabel serviceTitle = new JLabel("Các dịch vụ tại khách sạn", SwingConstants.CENTER);
        serviceTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        serviceTitle.setForeground(new Color(66, 133, 244));
        serviceTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        serviceTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(serviceTitle);

        // Lấy danh sách dịch vụ
        List<DichVuDTO> services = dichVuBUS.getAllDichVu(); // Sửa từ selectAll()
        int numCols = 2;
        int numRows = (int) Math.ceil((double) services.size() / numCols);

        JPanel servicePanel = new JPanel(new GridLayout(numRows, numCols, 10, 10));
        servicePanel.setBackground(Color.WHITE);

        List<JCheckBox> serviceCheckBoxes = new ArrayList<>();
        for (DichVuDTO service : services) {
            JCheckBox checkBox = new JCheckBox(service.getTenDV() + " (" + service.getGiaDV() + "đ)");
            checkBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
            checkBox.setBackground(Color.WHITE);
            checkBox.setToolTipText(service.getMaDV());
            checkBox.setFocusPainted(false);
            checkBox.setBorder(new EmptyBorder(5, 10, 5, 10));
            serviceCheckBoxes.add(checkBox);
            servicePanel.add(checkBox);
        }

        JScrollPane serviceScrollPane = new JScrollPane(servicePanel);
        serviceScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        serviceScrollPane.setPreferredSize(new Dimension(400, 150));
        serviceScrollPane.getViewport().setBackground(Color.WHITE);
        contentPanel.add(serviceScrollPane);

        dialog.add(contentPanel, BorderLayout.CENTER);

        // Footer - nút xác nhận
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);
        JButton confirmButton = new JButton("Xác nhận");
        confirmButton.setBackground(new Color(95, 195, 245));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        confirmButton.setPreferredSize(new Dimension(120, 40));
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirmButton.addActionListener(e -> {
            // Fetch KhachHangDTO from database using accountInfo
            KhachHangDTO khachHang = khachHangBUS.selectById(String.valueOf(accountInfo.getAccountId()));
            if (khachHang == null) {
                JOptionPane.showMessageDialog(parent, "Không tìm thấy thông tin khách hàng!", 
                                             "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate the number of days
            long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diffInDays == 0) diffInDays = 1; // Minimum 1 day

            // Calculate room cost
            int tienPhong = 0;
            for (String maP : selectedRoomIds) {
                PhongDTO phong = phongBUS.getPhongById(maP); // Sửa từ selectById thành getPhongById
                if (phong != null) {
                    tienPhong += phong.getGiaP() * (int) diffInDays;
                }
            }

            // Calculate service cost and get selected services
            int tienDichVu = 0;
            List<String> selectedServiceIds = new ArrayList<>();
            StringBuilder selectedServicesText = new StringBuilder();
            for (JCheckBox checkBox : serviceCheckBoxes) {
                if (checkBox.isSelected()) {
                    String maDV = checkBox.getToolTipText();
                    selectedServiceIds.add(maDV);
                    DichVuDTO service = dichVuBUS.getDichVuById(maDV); // Sửa từ selectById()
                    if (service != null) {
                        tienDichVu += service.getGiaDV();
                        selectedServicesText.append(service.getTenDV()).append(" (").append(service.getGiaDV()).append("đ)\n");
                    }
                }
            }
            String servicesText = selectedServicesText.length() > 0 ? selectedServicesText.toString() : "Không có dịch vụ nào được chọn.";

            // Generate maCTT (placeholder)
            String maCTT = "DP" + String.format("%03d", new Random().nextInt(1000));

            // Show HoaDonDialog with fetched customer info
            // new HoaDonDialog(parent, khachHang, selectedRoomsText, selectedRoomIds, 
            //                 tienPhong, tienDichVu, maCTT, servicesText).showDialog();

            dialog.dispose();
        });

        footerPanel.add(confirmButton);
        dialog.add(footerPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
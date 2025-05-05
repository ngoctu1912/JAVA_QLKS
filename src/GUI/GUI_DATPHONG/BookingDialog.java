// package GUI_DATPHONG;

// import BUS.DichVuBUS;
// import BUS.KhachHangBUS;
// import BUS.PhongBUS;
// import BUS.DatPhongBUS;
// import BUS.ChiTietDatPhongBUS;
// import DTO.DichVuDTO;
// import DTO.KhachHangDTO;
// import DTO.PhongDTO;
// import DTO.DatPhongDTO;
// import DTO.ChiTietDatPhongDTO;
// import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;
// import GUI_TRANGCHU.HomeFrame;
// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;
// import java.text.DecimalFormat;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
// import java.util.concurrent.TimeUnit;

// public class BookingDialog {
//     private final JComponent parent;
//     private final DichVuBUS dichVuBUS;
//     private final KhachHangBUS khachHangBUS;
//     private final PhongBUS phongBUS;
//     private final DatPhongBUS datPhongBUS;
//     private final ChiTietDatPhongBUS chiTietDatPhongBUS;
//     private final List<String> selectedRoomIds;
//     private final String selectedRoomsText;
//     private final Date checkInDate;
//     private final Date checkOutDate;
//     private final AccountInfo accountInfo;
//     private JTextField nameField; // Trường nhập họ và tên
//     private JTextField phoneField; // Trường nhập số điện thoại
//     private List<String> selectedServiceIds; // Danh sách dịch vụ đã chọn

//     public BookingDialog(JComponent parent, List<String> selectedRoomIds, String selectedRoomsText, 
//                          Date checkInDate, Date checkOutDate) {
//         this.parent = parent;
//         this.dichVuBUS = new DichVuBUS();
//         this.khachHangBUS = new KhachHangBUS();
//         this.phongBUS = new PhongBUS();
//         this.datPhongBUS = new DatPhongBUS();
//         this.chiTietDatPhongBUS = new ChiTietDatPhongBUS();
//         this.selectedRoomIds = selectedRoomIds;
//         this.selectedRoomsText = selectedRoomsText;
//         this.checkInDate = checkInDate;
//         this.checkOutDate = checkOutDate;
//         this.selectedServiceIds = new ArrayList<>();

//         // Get account info from HomeFrame
//         HomeFrame homeFrame = (HomeFrame) SwingUtilities.getAncestorOfClass(HomeFrame.class, parent);
//         this.accountInfo = homeFrame != null ? homeFrame.getAccountInfo() : null;
//     }

//     public void showDialog() {
//         // Validate dates
//         if (checkInDate == null || checkOutDate == null) {
//             JOptionPane.showMessageDialog(parent, "Ngày nhận phòng và trả phòng không được để trống!", 
//                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return;
//         }
//         if (checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate)) {
//             JOptionPane.showMessageDialog(parent, "Ngày trả phòng phải sau ngày nhận phòng!", 
//                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         // Validate login status
//         if (accountInfo == null || !"KHACHHANG".equals(accountInfo.getAccountType())) {
//             JOptionPane.showMessageDialog(parent, "Vui lòng đăng nhập tài khoản khách hàng để đặt phòng!", 
//                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         JDialog dialog = new JDialog((Frame) null, "Xác nhận đặt phòng", true);
//         dialog.setLayout(new BorderLayout());
//         dialog.setSize(500, 650);
//         // Căn giữa dialog so với HomeFrame
//         HomeFrame homeFrame = (HomeFrame) SwingUtilities.getAncestorOfClass(HomeFrame.class, parent);
//         if (homeFrame != null) {
//             dialog.setLocationRelativeTo(homeFrame);
//         } else {
//             dialog.setLocationRelativeTo(null); // Căn giữa màn hình nếu không tìm thấy HomeFrame
//         }

//         // Header
//         JPanel headerPanel = new JPanel(new GridBagLayout());
//         headerPanel.setBackground(new Color(149, 213, 178));
//         headerPanel.setPreferredSize(new Dimension(500, 50));
//         JLabel headerLabel = new JLabel("XÁC NHẬN ĐẶT PHÒNG");
//         headerLabel.setForeground(Color.WHITE);
//         headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
//         headerPanel.add(headerLabel);
//         dialog.add(headerPanel, BorderLayout.NORTH);

//         // Content Panel
//         JPanel contentPanel = new JPanel();
//         contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
//         contentPanel.setBackground(Color.WHITE);
//         contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

//         // Phòng đã chọn
//         JLabel roomTitle = new JLabel("Phòng đã chọn", SwingConstants.CENTER);
//         roomTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
//         roomTitle.setForeground(new Color(66, 133, 244));
//         roomTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
//         contentPanel.add(roomTitle);
//         contentPanel.add(Box.createVerticalStrut(10));

//         JTextPane roomTextPane = new JTextPane();
//         roomTextPane.setContentType("text/html");
//         roomTextPane.setText(
//                 "<html><div style='text-align: center; font-family: SansSerif; font-size: 12pt;'>" +
//                         selectedRoomsText.replace("\n", "<br>") +
//                         "</div></html>");
//         roomTextPane.setEditable(false);
//         roomTextPane.setBackground(new Color(248, 248, 248));
//         roomTextPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//         JScrollPane roomScrollPane = new JScrollPane(roomTextPane);
//         roomScrollPane.setPreferredSize(new Dimension(400, 100));
//         roomScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
//         roomScrollPane.getViewport().setBackground(new Color(248, 248, 248));
//         contentPanel.add(roomScrollPane);
//         contentPanel.add(Box.createVerticalStrut(20));

//         // Tính tổng tiền phòng
//         final int tongTienPhong;
//         final long diffInDays;
//         {
//             long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
//             long tempDiffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//             diffInDays = tempDiffInDays == 0 ? 1 : tempDiffInDays; // Gán giá trị một lần

//             int tempTongTienPhong = 0;
//             for (String maPhong : selectedRoomIds) {
//                 PhongDTO phong = phongBUS.getPhongById(maPhong);
//                 if (phong == null) {
//                     JOptionPane.showMessageDialog(parent, "Phòng " + maPhong + " không tồn tại!", 
//                                                  "Lỗi", JOptionPane.ERROR_MESSAGE);
//                     return;
//                 }
//                 tempTongTienPhong += phong.getGiaP() * (int) diffInDays;
//             }
//             tongTienPhong = tempTongTienPhong;
//         }

//         // Tạo danh sách dịch vụ để tính tiền dịch vụ khi chọn
//         List<DichVuDTO> services = dichVuBUS.getAllDichVu();
//         List<JCheckBox> serviceCheckBoxes = new ArrayList<>();
//         int[] tongTienDichVu = {0}; // Mảng để cập nhật tổng tiền dịch vụ trong ActionListener

//         // Hiển thị tiền đặt cọc
//         JLabel depositTitle = new JLabel("Tiền đặt cọc (30%)", SwingConstants.CENTER);
//         depositTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
//         depositTitle.setForeground(new Color(66, 133, 244));
//         depositTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
//         contentPanel.add(depositTitle);
//         contentPanel.add(Box.createVerticalStrut(10));

//         DecimalFormat df = new DecimalFormat("#,### đ");
//         JTextField depositField = new JTextField(df.format((int) ((tongTienPhong + tongTienDichVu[0]) * 0.3)));
//         depositField.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         depositField.setEditable(false);
//         depositField.setHorizontalAlignment(JTextField.CENTER);
//         depositField.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(new Color(200, 200, 200)),
//                 BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//         contentPanel.add(depositField);
//         contentPanel.add(Box.createVerticalStrut(20));

//         // Thông tin khách hàng
//         JLabel customerTitle = new JLabel("Thông tin khách hàng", SwingConstants.CENTER);
//         customerTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
//         customerTitle.setForeground(new Color(66, 133, 244));
//         customerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
//         contentPanel.add(customerTitle);
//         contentPanel.add(Box.createVerticalStrut(10));

//         JPanel customerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
//         customerPanel.setBackground(Color.WHITE);

//         // Họ và tên
//         JLabel nameLabel = new JLabel("Họ và tên:");
//         nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         nameField = new JTextField();
//         nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         nameField.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(new Color(200, 200, 200)),
//                 BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//         customerPanel.add(nameLabel);
//         customerPanel.add(nameField);

//         // Số điện thoại
//         JLabel phoneLabel = new JLabel("Số điện thoại:");
//         phoneLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         phoneField = new JTextField();
//         phoneField.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         phoneField.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(new Color(200, 200, 200)),
//                 BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//         customerPanel.add(phoneLabel);
//         customerPanel.add(phoneField);

//         contentPanel.add(customerPanel);
//         contentPanel.add(Box.createVerticalStrut(20));

//         // Tiêu đề dịch vụ
//         JLabel serviceTitle = new JLabel("Các dịch vụ tại khách sạn", SwingConstants.CENTER);
//         serviceTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
//         serviceTitle.setForeground(new Color(66, 133, 244));
//         serviceTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
//         serviceTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
//         contentPanel.add(serviceTitle);

//         // Danh sách dịch vụ
//         int numCols = 2;
//         int numRows = (int) Math.ceil((double) services.size() / numCols);

//         JPanel servicePanel = new JPanel(new GridLayout(numRows, numCols, 10, 10));
//         servicePanel.setBackground(Color.WHITE);

//         for (DichVuDTO service : services) {
//             JCheckBox checkBox = new JCheckBox(service.getTenDV() + " (" + service.getGiaDV() + "đ)");
//             checkBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
//             checkBox.setBackground(Color.WHITE);
//             checkBox.setToolTipText(service.getMaDV());
//             checkBox.setFocusPainted(false);
//             checkBox.setBorder(new EmptyBorder(5, 10, 5, 10));
//             checkBox.addActionListener(e -> {
//                 // Cập nhật tổng tiền dịch vụ và tiền đặt cọc khi checkbox thay đổi
//                 tongTienDichVu[0] = 0;
//                 for (JCheckBox cb : serviceCheckBoxes) {
//                     if (cb.isSelected()) {
//                         String maDV = cb.getToolTipText();
//                         DichVuDTO dv = dichVuBUS.getDichVuById(maDV);
//                         if (dv != null) {
//                             tongTienDichVu[0] += dv.getGiaDV();
//                         }
//                     }
//                 }
//                 depositField.setText(df.format((int) ((tongTienPhong + tongTienDichVu[0]) * 0.3)));
//             });
//             serviceCheckBoxes.add(checkBox);
//             servicePanel.add(checkBox);
//         }

//         JScrollPane serviceScrollPane = new JScrollPane(servicePanel);
//         serviceScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
//         serviceScrollPane.setPreferredSize(new Dimension(400, 150));
//         serviceScrollPane.getViewport().setBackground(Color.WHITE);
//         contentPanel.add(serviceScrollPane);

//         dialog.add(contentPanel, BorderLayout.CENTER);

//         // Footer - nút xác nhận
//         JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         footerPanel.setBackground(Color.WHITE);
//         JButton confirmButton = new JButton("Xác nhận");
//         confirmButton.setBackground(new Color(95, 195, 245));
//         confirmButton.setForeground(Color.WHITE);
//         confirmButton.setFont(new Font("SansSerif", Font.BOLD, 14));
//         confirmButton.setPreferredSize(new Dimension(120, 40));
//         confirmButton.setFocusPainted(false);
//         confirmButton.setBorderPainted(false);
//         confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//         confirmButton.addActionListener(e -> {
//             // Validate customer information
//             String name = nameField.getText().trim();
//             String phone = phoneField.getText().trim();

//             if (name.isEmpty() || phone.isEmpty()) {
//                 JOptionPane.showMessageDialog(parent, "Vui lòng nhập đầy đủ họ và tên và số điện thoại!", 
//                                              "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             // Validate phone number (10-11 chữ số)
//             if (!phone.matches("\\d{10,11}")) {
//                 JOptionPane.showMessageDialog(parent, "Số điện thoại phải là số và có 10-11 chữ số!", 
//                                              "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             // Fetch KhachHangDTO from database
//             KhachHangDTO khachHang = khachHangBUS.selectById(String.valueOf(accountInfo.getAccountId()));
//             if (khachHang == null) {
//                 JOptionPane.showMessageDialog(parent, "Không tìm thấy thông tin khách hàng!", 
//                                              "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             // Update customer information
//             khachHang.setTenKhachHang(name);
//             khachHang.setSoDienThoai(phone);
//             try {
//                 khachHangBUS.update(khachHang); // Cập nhật thông tin khách hàng
//             } catch (IllegalArgumentException ex) {
//                 JOptionPane.showMessageDialog(parent, ex.getMessage(), 
//                                              "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             // Create DatPhongDTO
//             DatPhongDTO datPhong = new DatPhongDTO();
//             String maDP = "DP" + String.format("%03d", datPhongBUS.getAutoIncrement());
//             datPhong.setMaDP(maDP);
//             datPhong.setMaKH(khachHang.getMaKhachHang());
//             datPhong.setNgayLapPhieu(new Date());
//             datPhong.setTienDatCoc((int) ((tongTienPhong + tongTienDichVu[0]) * 0.3)); // Tiền đặt cọc 30% tổng tiền
//             datPhong.setTinhTrangXuLy(0); // Chưa xử lý
//             datPhong.setXuLy(1); // Hợp lệ

//             // Get selected services
//             selectedServiceIds.clear();
//             for (JCheckBox checkBox : serviceCheckBoxes) {
//                 if (checkBox.isSelected()) {
//                     selectedServiceIds.add(checkBox.getToolTipText());
//                 }
//             }

//             // Save to DATPHONG table
//             try {
//                 int result = datPhongBUS.add(datPhong);
//                 if (result > 0) {
//                     // Lưu chi tiết đặt phòng (phòng)
//                     int ctdpCounter = chiTietDatPhongBUS.getAutoIncrement(); // Lấy giá trị ban đầu
//                     for (String maPhong : selectedRoomIds) {
//                         PhongDTO phong = phongBUS.getPhongById(maPhong);
//                         if (phong == null) {
//                             JOptionPane.showMessageDialog(parent, "Phòng " + maPhong + " không tồn tại!", 
//                                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
//                             return;
//                         }
//                         ChiTietDatPhongDTO chiTiet = new ChiTietDatPhongDTO();
//                         chiTiet.setMaCTDP("CTDP" + String.format("%03d", ctdpCounter++));
//                         chiTiet.setMaP(maPhong);
//                         chiTiet.setNgayThue(checkInDate);
//                         chiTiet.setNgayTra(checkOutDate);
//                         chiTiet.setNgayCheckOut(null); // Chưa check out
//                         chiTiet.setLoaiHinhThue(0); // Thuê theo ngày
//                         chiTiet.setGiaThue(phong.getGiaP() * (int) diffInDays); // Giá thuê phòng
//                         chiTiet.setTinhTrang(0); // Chưa xử lý
//                         chiTietDatPhongBUS.add(chiTiet);
//                     }
//                     // Lưu chi tiết đặt phòng (dịch vụ)
//                     for (String maDV : selectedServiceIds) {
//                         DichVuDTO dv = dichVuBUS.getDichVuById(maDV);
//                         if (dv == null) {
//                             JOptionPane.showMessageDialog(parent, "Dịch vụ " + maDV + " không tồn tại!", 
//                                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
//                             return;
//                         }
//                         ChiTietDatPhongDTO chiTiet = new ChiTietDatPhongDTO();
//                         chiTiet.setMaCTDP("CTDP" + String.format("%03d", ctdpCounter++));

//                         chiTiet.setMaP(selectedRoomIds.get(0)); // Gán dịch vụ cho phòng đầu tiên
//                         chiTiet.setNgayThue(checkInDate);
//                         chiTiet.setNgayTra(checkOutDate);
//                         chiTiet.setNgayCheckOut(null);
//                         chiTiet.setLoaiHinhThue(1); // Giả sử 1 là dịch vụ
//                         chiTiet.setGiaThue(dv.getGiaDV()); // Giá dịch vụ
//                         chiTiet.setTinhTrang(0);
//                         chiTietDatPhongBUS.add(chiTiet);
//                     }

//                     JOptionPane.showMessageDialog(parent, "Đặt phòng thành công! Mã đặt phòng: " + maDP, 
//                                                  "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                     dialog.dispose();
//                 } else {
//                     JOptionPane.showMessageDialog(parent, "Đặt phòng thất bại!", 
//                                                  "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 }
//             } catch (RuntimeException ex) {
//                 JOptionPane.showMessageDialog(parent, "Lỗi khi lưu đặt phòng: " + ex.getMessage(), 
//                                              "Lỗi", JOptionPane.ERROR_MESSAGE);
//             }
//         });

//         footerPanel.add(confirmButton);
//         dialog.add(footerPanel, BorderLayout.SOUTH);

//         // Auto-fill customer information if available
//         KhachHangDTO khachHang = khachHangBUS.selectById(String.valueOf(accountInfo.getAccountId()));
//         if (khachHang != null) {
//             nameField.setText(khachHang.getTenKhachHang() != null ? khachHang.getTenKhachHang() : "");
//             phoneField.setText(khachHang.getSoDienThoai() != null ? khachHang.getSoDienThoai() : "");
//         }

//         dialog.setVisible(true);
//     }
// }

package GUI_DATPHONG;

import BUS.DichVuBUS;
import BUS.KhachHangBUS;
import BUS.PhongBUS;
import BUS.DatPhongBUS;
import BUS.ChiTietDatPhongBUS;
import DTO.DichVuDTO;
import DTO.KhachHangDTO;
import DTO.PhongDTO;
import DTO.DatPhongDTO;
import DTO.ChiTietDatPhongDTO;
import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;
import GUI_TRANGCHU.HomeFrame;
import config.ConnectDB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BookingDialog {
    private final JComponent parent;
    private final DichVuBUS dichVuBUS;
    private final KhachHangBUS khachHangBUS;
    private final PhongBUS phongBUS;
    private final DatPhongBUS datPhongBUS;
    private final ChiTietDatPhongBUS chiTietDatPhongBUS;
    private final List<String> selectedRoomIds;
    private final String selectedRoomsText;
    private final Date checkInDate;
    private final Date checkOutDate;
    private final AccountInfo accountInfo;
    private JTextField nameField; // Trường nhập họ và tên
    private JTextField phoneField; // Trường nhập số điện thoại
    private List<String> selectedServiceIds; // Danh sách dịch vụ đã chọn

    public BookingDialog(JComponent parent, List<String> selectedRoomIds, String selectedRoomsText,
            Date checkInDate, Date checkOutDate) {
        this.parent = parent;
        this.dichVuBUS = new DichVuBUS();
        this.khachHangBUS = new KhachHangBUS();
        this.phongBUS = new PhongBUS();
        this.datPhongBUS = new DatPhongBUS();
        this.chiTietDatPhongBUS = new ChiTietDatPhongBUS();
        this.selectedRoomIds = selectedRoomIds;
        this.selectedRoomsText = selectedRoomsText;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.selectedServiceIds = new ArrayList<>();

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
        dialog.setSize(500, 650);
        // Căn giữa dialog so với HomeFrame
        HomeFrame homeFrame = (HomeFrame) SwingUtilities.getAncestorOfClass(HomeFrame.class, parent);
        if (homeFrame != null) {
            dialog.setLocationRelativeTo(homeFrame);
        } else {
            dialog.setLocationRelativeTo(null); // Căn giữa màn hình nếu không tìm thấy HomeFrame
        }

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

        // Tính tổng tiền phòng
        final int tongTienPhong;
        final long diffInDays;
        {
            long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
            long tempDiffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            diffInDays = tempDiffInDays == 0 ? 1 : tempDiffInDays; // Gán giá trị một lần

            int tempTongTienPhong = 0;
            for (String maPhong : selectedRoomIds) {
                PhongDTO phong = phongBUS.getPhongById(maPhong);
                if (phong == null) {
                    JOptionPane.showMessageDialog(parent, "Phòng " + maPhong + " không tồn tại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                tempTongTienPhong += phong.getGiaP() * (int) diffInDays;
            }
            tongTienPhong = tempTongTienPhong;
        }

        // Tạo danh sách dịch vụ để tính tiền dịch vụ khi chọn
        List<DichVuDTO> services = dichVuBUS.getAllDichVu();
        List<JCheckBox> serviceCheckBoxes = new ArrayList<>();
        int[] tongTienDichVu = { 0 }; // Mảng để cập nhật tổng tiền dịch vụ trong ActionListener

        // Hiển thị tiền đặt cọc
        JLabel depositTitle = new JLabel("Tiền đặt cọc (30%)", SwingConstants.CENTER);
        depositTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        depositTitle.setForeground(new Color(66, 133, 244));
        depositTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(depositTitle);
        contentPanel.add(Box.createVerticalStrut(10));

        DecimalFormat df = new DecimalFormat("#,### đ");
        JTextField depositField = new JTextField(df.format((int) ((tongTienPhong + tongTienDichVu[0]) * 0.3)));
        depositField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        depositField.setEditable(false);
        depositField.setHorizontalAlignment(JTextField.CENTER);
        depositField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        contentPanel.add(depositField);
        contentPanel.add(Box.createVerticalStrut(20));

        // Thông tin khách hàng
        JLabel customerTitle = new JLabel("Thông tin khách hàng", SwingConstants.CENTER);
        customerTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        customerTitle.setForeground(new Color(66, 133, 244));
        customerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(customerTitle);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel customerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        customerPanel.setBackground(Color.WHITE);

        // Họ và tên
        JLabel nameLabel = new JLabel("Họ và tên:");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField = new JTextField();
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        customerPanel.add(nameLabel);
        customerPanel.add(nameField);

        // Số điện thoại
        JLabel phoneLabel = new JLabel("Số điện thoại:");
        phoneLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        phoneField = new JTextField();
        phoneField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        phoneField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        customerPanel.add(phoneLabel);
        customerPanel.add(phoneField);

        contentPanel.add(customerPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Tiêu đề dịch vụ
        JLabel serviceTitle = new JLabel("Các dịch vụ tại khách sạn", SwingConstants.CENTER);
        serviceTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        serviceTitle.setForeground(new Color(66, 133, 244));
        serviceTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        serviceTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(serviceTitle);

        // Danh sách dịch vụ
        int numCols = 2;
        int numRows = (int) Math.ceil((double) services.size() / numCols);

        JPanel servicePanel = new JPanel(new GridLayout(numRows, numCols, 10, 10));
        servicePanel.setBackground(Color.WHITE);

        for (DichVuDTO service : services) {
            JCheckBox checkBox = new JCheckBox(service.getTenDV() + " (" + service.getGiaDV() + "đ)");
            checkBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
            checkBox.setBackground(Color.WHITE);
            checkBox.setToolTipText(service.getMaDV());
            checkBox.setFocusPainted(false);
            checkBox.setBorder(new EmptyBorder(5, 10, 5, 10));
            checkBox.addActionListener(e -> {
                // Cập nhật tổng tiền dịch vụ và tiền đặt cọc khi checkbox thay đổi
                tongTienDichVu[0] = 0;
                for (JCheckBox cb : serviceCheckBoxes) {
                    if (cb.isSelected()) {
                        String maDV = cb.getToolTipText();
                        DichVuDTO dv = dichVuBUS.getDichVuById(maDV);
                        if (dv != null) {
                            tongTienDichVu[0] += dv.getGiaDV();
                        }
                    }
                }
                depositField.setText(df.format((int) ((tongTienPhong + tongTienDichVu[0]) * 0.3)));
            });
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
            // Validate customer information
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Vui lòng nhập đầy đủ họ và tên và số điện thoại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate phone number (10-11 chữ số)
            if (!phone.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(parent, "Số điện thoại phải là số và có 10-11 chữ số!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Fetch KhachHangDTO from database
            KhachHangDTO khachHang = khachHangBUS.selectById(String.valueOf(accountInfo.getAccountId()));
            if (khachHang == null) {
                JOptionPane.showMessageDialog(parent, "Không tìm thấy thông tin khách hàng!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update customer information
            khachHang.setTenKhachHang(name);
            khachHang.setSoDienThoai(phone);
            try {
                khachHangBUS.update(khachHang); // Cập nhật thông tin khách hàng
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create DatPhongDTO
            DatPhongDTO datPhong = new DatPhongDTO();
            String maDP = "DP" + String.format("%03d", datPhongBUS.getAutoIncrement());
            datPhong.setMaDP(maDP);
            datPhong.setMaKH(khachHang.getMaKhachHang());
            datPhong.setNgayLapPhieu(new Date());
            datPhong.setTienDatCoc((int) ((tongTienPhong + tongTienDichVu[0]) * 0.3)); // Tiền đặt cọc 30% tổng tiền
            datPhong.setTinhTrangXuLy(0); // Chưa xử lý
            datPhong.setXuLy(1); // Hợp lệ

            // Log DatPhongDTO
            System.out.println("DatPhongDTO: maDP=" + datPhong.getMaDP() + ", maKH=" + datPhong.getMaKH() +
                    ", tienDatCoc=" + datPhong.getTienDatCoc() + ", ngayLapPhieu=" + datPhong.getNgayLapPhieu());

            // Check if maDP already exists
            if (datPhongBUS.checkExists(maDP)) {
                JOptionPane.showMessageDialog(parent, "Mã đặt phòng " + maDP + " đã tồn tại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get selected services
            selectedServiceIds.clear();
            for (JCheckBox checkBox : serviceCheckBoxes) {
                if (checkBox.isSelected()) {
                    selectedServiceIds.add(checkBox.getToolTipText());
                }
            }

            // Save to DATPHONG table with transaction
            try (Connection conn = ConnectDB.getConnection()) {
                conn.setAutoCommit(false); // Bắt đầu giao dịch
                int result = datPhongBUS.add(datPhong);
                System.out.println("Result of datPhongBUS.add: " + result);
                if (result > 0) {
                    // Lưu chi tiết đặt phòng (phòng)
                    int ctdpCounter = chiTietDatPhongBUS.getAutoIncrement();
                    for (String maPhong : selectedRoomIds) {
                        PhongDTO phong = phongBUS.getPhongById(maPhong);
                        if (phong == null) {
                            conn.rollback();
                            JOptionPane.showMessageDialog(parent, "Phòng " + maPhong + " không tồn tại!",
                                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        ChiTietDatPhongDTO chiTiet = new ChiTietDatPhongDTO();
                        chiTiet.setMaCTDP("CTDP" + String.format("%03d", ctdpCounter++));
                        chiTiet.setMaP(maPhong);
                        chiTiet.setNgayThue(checkInDate);
                        chiTiet.setNgayTra(checkOutDate);
                        chiTiet.setNgayCheckOut(null);
                        chiTiet.setLoaiHinhThue(0); // Thuê theo ngày
                        chiTiet.setGiaThue(phong.getGiaP() * (int) diffInDays);
                        chiTiet.setTinhTrang(0);
                        chiTietDatPhongBUS.add(chiTiet);
                    }

                    // Note: Services are not saved to CHITIETDATPHONG as per requirement.
                    conn.commit(); // Hoàn tất giao dịch
                    JOptionPane.showMessageDialog(parent, "Đặt phòng thành công! Mã đặt phòng: " + maDP,
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } else {
                    conn.rollback();
                    JOptionPane.showMessageDialog(parent, "Đặt phòng thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                System.err.println("SQLException in saving booking: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parent, "Lỗi khi lưu đặt phòng: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        footerPanel.add(confirmButton);
        dialog.add(footerPanel, BorderLayout.SOUTH);

        // Auto-fill customer information if available
        KhachHangDTO khachHang = khachHangBUS.selectById(String.valueOf(accountInfo.getAccountId()));
        if (khachHang != null) {
            nameField.setText(khachHang.getTenKhachHang() != null ? khachHang.getTenKhachHang() : "");
            phoneField.setText(khachHang.getSoDienThoai() != null ? khachHang.getSoDienThoai() : "");
        }

        dialog.setVisible(true);
    }
}
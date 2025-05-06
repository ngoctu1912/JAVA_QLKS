// package GUI_DATPHONG;

// import BUS.DichVuBUS;
// import BUS.KhachHangBUS;
// import BUS.PhongBUS;
// import BUS.DatPhongBUS;
// import BUS.ChiTietDatPhongBUS;
// import BUS.ChiTietThueDichVuBUS;
// import DTO.DichVuDTO;
// import DTO.KhachHangDTO;
// import DTO.PhongDTO;
// import DTO.DatPhongDTO;
// import DTO.ChiTietDatPhongDTO;
// import DTO.ChiTietThueDichVuDTO;
// import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;
// import GUI_TRANGCHU.HomeFrame;
// import config.ConnectDB;

// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;
// import java.sql.Connection;
// import java.sql.SQLException;
// import java.text.DecimalFormat;
// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.TimeUnit;

// public class BookingDialog {
//     private final JComponent parent;
//     private final DichVuBUS dichVuBUS;
//     private final KhachHangBUS khachHangBUS;
//     private final PhongBUS phongBUS;
//     private final DatPhongBUS datPhongBUS;
//     private final ChiTietDatPhongBUS chiTietDatPhongBUS;
//     private final ChiTietThueDichVuBUS chiTietThueDichVuBUS;
//     private final List<String> selectedRoomIds;
//     private final String selectedRoomsText;
//     private final Date checkInDate;
//     private final Date checkOutDate;
//     private final AccountInfo accountInfo;
//     private JTextField nameField;
//     private JTextField phoneField;
//     private List<String> selectedServiceIds;
//     private List<JCheckBox> serviceCheckBoxes;

//     public BookingDialog(JComponent parent, List<String> selectedRoomIds, String selectedRoomsText,
//             Date checkInDate, Date checkOutDate) {
//         this.parent = parent;
//         this.dichVuBUS = new DichVuBUS();
//         this.khachHangBUS = new KhachHangBUS();
//         this.phongBUS = new PhongBUS();
//         this.datPhongBUS = new DatPhongBUS();
//         this.chiTietDatPhongBUS = new ChiTietDatPhongBUS();
//         this.chiTietThueDichVuBUS = new ChiTietThueDichVuBUS();
//         this.selectedRoomIds = selectedRoomIds;
//         this.selectedRoomsText = selectedRoomsText;
//         this.checkInDate = checkInDate;
//         this.checkOutDate = checkOutDate;
//         this.selectedServiceIds = new ArrayList<>();
//         this.serviceCheckBoxes = new ArrayList<>();

//         HomeFrame homeFrame = (HomeFrame) SwingUtilities.getAncestorOfClass(HomeFrame.class, parent);
//         if (homeFrame == null || homeFrame.getAccountInfo() == null) {
//             throw new IllegalStateException("Không thể khởi tạo BookingDialog: HomeFrame hoặc thông tin tài khoản không khả dụng.");
//         }
//         this.accountInfo = homeFrame.getAccountInfo();
//     }

//     public void showDialog() {
//         if (checkInDate == null || checkOutDate == null) {
//             JOptionPane.showMessageDialog(parent, "Ngày nhận phòng và trả phòng không được để trống!",
//                     "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return;
//         }
//         if (checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate)) {
//             JOptionPane.showMessageDialog(parent, "Ngày trả phòng phải sau ngày nhận phòng!",
//                     "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         if (!"KHACHHANG".equals(accountInfo.getAccountType())) {
//             JOptionPane.showMessageDialog(parent, "Vui lòng đăng nhập tài khoản khách hàng để đặt phòng!",
//                     "Lỗi", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         JDialog dialog = new JDialog((Frame) null, "Xác nhận đặt phòng", true);
//         dialog.setLayout(new BorderLayout());
//         dialog.setSize(500, 650);
//         HomeFrame homeFrame = (HomeFrame) SwingUtilities.getAncestorOfClass(HomeFrame.class, parent);
//         if (homeFrame != null) {
//             dialog.setLocationRelativeTo(homeFrame);
//         } else {
//             dialog.setLocationRelativeTo(null);
//         }

//         JPanel headerPanel = new JPanel(new GridBagLayout());
//         headerPanel.setBackground(new Color(149, 213, 178));
//         headerPanel.setPreferredSize(new Dimension(500, 50));
//         JLabel headerLabel = new JLabel("XÁC NHẬN ĐẶT PHÒNG");
//         headerLabel.setForeground(Color.WHITE);
//         headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
//         headerPanel.add(headerLabel);
//         dialog.add(headerPanel, BorderLayout.NORTH);

//         JPanel contentPanel = new JPanel();
//         contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
//         contentPanel.setBackground(Color.WHITE);
//         contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

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

//         final int tongTienPhong;
//         final long diffInDays;
//         {
//             long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
//             long tempDiffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//             diffInDays = tempDiffInDays == 0 ? 1 : tempDiffInDays;

//             int tempTongTienPhong = 0;
//             for (String maPhong : selectedRoomIds) {
//                 PhongDTO phong = phongBUS.getPhongById(maPhong);
//                 if (phong == null) {
//                     JOptionPane.showMessageDialog(parent, "Phòng " + maPhong + " không tồn tại!",
//                             "Lỗi", JOptionPane.ERROR_MESSAGE);
//                     return;
//                 }
//                 tempTongTienPhong += phong.getGiaP() * (int) diffInDays;
//             }
//             tongTienPhong = tempTongTienPhong;
//         }

//         List<DichVuDTO> services = dichVuBUS.getAllDichVu();
//         int[] tongTienDichVu = { 0 };

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

//         JLabel customerTitle = new JLabel("Thông tin khách hàng", SwingConstants.CENTER);
//         customerTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
//         customerTitle.setForeground(new Color(66, 133, 244));
//         customerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
//         contentPanel.add(customerTitle);
//         contentPanel.add(Box.createVerticalStrut(10));

//         JPanel customerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
//         customerPanel.setBackground(Color.WHITE);

//         JLabel nameLabel = new JLabel("Họ và tên:");
//         nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         nameField = new JTextField();
//         nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         nameField.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(new Color(200, 200, 200)),
//                 BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//         customerPanel.add(nameLabel);
//         customerPanel.add(nameField);

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

//         JLabel serviceTitle = new JLabel("Các dịch vụ tại khách sạn", SwingConstants.CENTER);
//         serviceTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
//         serviceTitle.setForeground(new Color(66, 133, 244));
//         serviceTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
//         serviceTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
//         contentPanel.add(serviceTitle);

//         int numCols = 2;
//         int numRows = (int) Math.ceil((double) services.size() / numCols);

//         JPanel servicePanel = new JPanel(new GridLayout(numRows, numCols, 10, 10));
//         servicePanel.setBackground(Color.WHITE);

//         for (DichVuDTO service : services) {
//             JCheckBox checkBox = new JCheckBox(service.getTenDV() + " (" + df.format(service.getGiaDV()) + ")");
//             checkBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
//             checkBox.setBackground(Color.WHITE);
//             checkBox.setToolTipText(service.getMaDV());
//             checkBox.setFocusPainted(false);
//             checkBox.setBorder(new EmptyBorder(5, 10, 5, 10));
//             checkBox.addActionListener(e -> {
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
//             String name = nameField.getText().trim();
//             String phone = phoneField.getText().trim();

//             if (name.isEmpty() || phone.isEmpty()) {
//                 JOptionPane.showMessageDialog(parent, "Vui lòng nhập đầy đủ họ và tên và số điện thoại!",
//                         "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             if (!phone.matches("\\d{10,11}")) {
//                 JOptionPane.showMessageDialog(parent, "Số điện thoại phải là số và có 10-11 chữ số!",
//                         "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             KhachHangDTO khachHang = khachHangBUS.selectById(String.valueOf(accountInfo.getAccountId()));
//             if (khachHang == null) {
//                 JOptionPane.showMessageDialog(parent, "Không tìm thấy thông tin khách hàng!",
//                         "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             khachHang.setTenKhachHang(name);
//             khachHang.setSoDienThoai(phone);
//             try {
//                 khachHangBUS.update(khachHang);
//             } catch (IllegalArgumentException ex) {
//                 JOptionPane.showMessageDialog(parent, "Lỗi cập nhật thông tin khách hàng: " + ex.getMessage(),
//                         "Lỗi", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             DatPhongDTO datPhong = new DatPhongDTO();
//             String maDP = "DP" + String.format("%03d", datPhongBUS.getAutoIncrement());
//             datPhong.setMaDP(maDP);
//             datPhong.setMaKH(khachHang.getMaKhachHang());
//             datPhong.setNgayLapPhieu(new Date());
//             datPhong.setTienDatCoc((int) ((tongTienPhong + tongTienDichVu[0]) * 0.3));
//             datPhong.setTinhTrangXuLy(0);
//             datPhong.setXuLy(1);

//             selectedServiceIds.clear();
//             for (JCheckBox cb : serviceCheckBoxes) {
//                 if (cb.isSelected()) {
//                     selectedServiceIds.add(cb.getToolTipText());
//                 }
//             }

//             Map<String, PhongDTO> phongMap = new HashMap<>();
//             for (String maPhong : selectedRoomIds) {
//                 PhongDTO phong = phongBUS.getPhongById(maPhong);
//                 if (phong == null) {
//                     JOptionPane.showMessageDialog(parent, "Phòng " + maPhong + " không tồn tại!",
//                             "Lỗi", JOptionPane.ERROR_MESSAGE);
//                     return;
//                 }
//                 phongMap.put(maPhong, phong);
//             }

//             Map<String, DichVuDTO> dichVuMap = new HashMap<>();
//             for (String maDV : selectedServiceIds) {
//                 DichVuDTO service = dichVuBUS.getDichVuById(maDV);
//                 if (service == null) {
//                     JOptionPane.showMessageDialog(parent, "Dịch vụ " + maDV + " không tồn tại!",
//                             "Lỗi", JOptionPane.ERROR_MESSAGE);
//                     return;
//                 }
//                 dichVuMap.put(maDV, service);
//             }

//             // Retry logic for transaction
//             int maxRetries = 3;
//             int attempt = 0;
//             boolean success = false;
//             Connection conn = null;

//             while (attempt < maxRetries && !success) {
//                 try {
//                     conn = ConnectDB.getConnection();
//                     if (conn == null) {
//                         JOptionPane.showMessageDialog(parent,
//                                 "Không thể kết nối đến cơ sở dữ liệu. Vui lòng kiểm tra cấu hình!",
//                                 "Lỗi", JOptionPane.ERROR_MESSAGE);
//                         return;
//                     }
//                     conn.setAutoCommit(false);

//                     int result = datPhongBUS.add(datPhong, conn);
//                     if (result == 0) {
//                         conn.rollback();
//                         JOptionPane.showMessageDialog(parent, "Đặt phòng thất bại! Vui lòng thử lại.",
//                                 "Lỗi", JOptionPane.ERROR_MESSAGE);
//                         return;
//                     }

//                     for (String maPhong : selectedRoomIds) {
//                         ChiTietDatPhongDTO chiTiet = new ChiTietDatPhongDTO();
//                         chiTiet.setMaCTDP(maDP);
//                         chiTiet.setMaP(maPhong);

//                         Calendar cal = Calendar.getInstance();
//                         cal.setTime(checkInDate);
//                         cal.set(Calendar.HOUR_OF_DAY, 14);
//                         cal.set(Calendar.MINUTE, 0);
//                         cal.set(Calendar.SECOND, 0);
//                         cal.set(Calendar.MILLISECOND, 0);
//                         chiTiet.setNgayThue(cal.getTime());

//                         cal.setTime(checkOutDate);
//                         cal.set(Calendar.HOUR_OF_DAY, 12);
//                         cal.set(Calendar.MINUTE, 0);
//                         cal.set(Calendar.SECOND, 0);
//                         cal.set(Calendar.MILLISECOND, 0);
//                         chiTiet.setNgayTra(cal.getTime());

//                         chiTiet.setNgayCheckOut(null);
//                         chiTiet.setLoaiHinhThue(0);
//                         chiTiet.setGiaThue(phongMap.get(maPhong).getGiaP() * (int) diffInDays);
//                         chiTiet.setTinhTrang(0);
//                         chiTietDatPhongBUS.add(chiTiet, conn);
//                     }

//                     for (String maDV : selectedServiceIds) {
//                         ChiTietThueDichVuDTO chiTietDV = new ChiTietThueDichVuDTO();
//                         chiTietDV.setMaCTT(maDP);
//                         chiTietDV.setMaDV(maDV);
//                         Calendar cal = Calendar.getInstance();
//                         cal.setTime(checkInDate);
//                         cal.set(Calendar.HOUR_OF_DAY, 8);
//                         cal.set(Calendar.MINUTE, 0);
//                         cal.set(Calendar.SECOND, 0);
//                         cal.set(Calendar.MILLISECOND, 0);
//                         chiTietDV.setNgaySuDung(cal.getTime());
//                         chiTietDV.setSoLuong(1);
//                         chiTietDV.setGiaDV(dichVuMap.get(maDV).getGiaDV());
//                         chiTietThueDichVuBUS.add(chiTietDV, conn);
//                     }

//                     conn.commit();
//                     success = true;
//                     JOptionPane.showMessageDialog(parent, "Đặt phòng thành công! Mã đặt phòng: " + maDP,
//                             "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                     dialog.dispose();
//                 } catch (SQLException ex) {
//                     attempt++;
//                     try {
//                         if (conn != null && !conn.isClosed()) {
//                             conn.rollback();
//                         }
//                     } catch (SQLException rollbackEx) {
//                         rollbackEx.printStackTrace();
//                     }

//                     if (ex.getMessage().contains("Lock wait timeout exceeded") && attempt < maxRetries) {
//                         try {
//                             Thread.sleep(100 * attempt); // Wait before retry
//                         } catch (InterruptedException ie) {
//                             ie.printStackTrace();
//                         }
//                         continue;
//                     }

//                     String errorMessage = "Lỗi cơ sở dữ liệu: " + ex.getMessage();
//                     if (ex.getMessage().contains("Access denied")) {
//                         errorMessage = "Tài khoản hoặc mật khẩu cơ sở dữ liệu không đúng!";
//                     } else if (ex.getMessage().contains("Unknown database")) {
//                         errorMessage = "Cơ sở dữ liệu không tồn tại! Vui lòng kiểm tra tên cơ sở dữ liệu.";
//                     } else if (ex.getMessage().contains("Connection refused")) {
//                         errorMessage = "Không thể kết nối đến server MySQL. Vui lòng kiểm tra server!";
//                     } else if (ex.getMessage().contains("Duplicate entry")) {
//                         errorMessage = "Lỗi trùng khóa chính hoặc khóa ngoại. Vui lòng kiểm tra dữ liệu!";
//                     }
//                     JOptionPane.showMessageDialog(parent, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
//                     return;
//                 } finally {
//                     try {
//                         if (conn != null && !conn.isClosed()) {
//                             conn.setAutoCommit(true);
//                             conn.close();
//                         }
//                     } catch (SQLException closeEx) {
//                         closeEx.printStackTrace();
//                     }
//                 }
//             }

//             if (!success) {
//                 JOptionPane.showMessageDialog(parent, "Đặt phòng thất bại sau " + maxRetries + " lần thử. Vui lòng thử lại sau!",
//                         "Lỗi", JOptionPane.ERROR_MESSAGE);
//             }
//         });

//         footerPanel.add(confirmButton);
//         dialog.add(footerPanel, BorderLayout.SOUTH);

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
import DAO.HoaDonDAO;
import BUS.DatPhongBUS;
import BUS.ChiTietDatPhongBUS;
import BUS.ChiTietThueDichVuBUS;
import BUS.HoaDonBUS;
import DTO.DichVuDTO;
import DTO.KhachHangDTO;
import DTO.PhongDTO;
import DTO.DatPhongDTO;
import DTO.ChiTietDatPhongDTO;
import DTO.ChiTietThueDichVuDTO;
import DTO.HoaDonDTO;
import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;
import GUI_HOADON.FormHoaDon;
import GUI_TRANGCHU.HomeFrame;
import config.ConnectDB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BookingDialog {
    private final JComponent parent;
    private final DichVuBUS dichVuBUS;
    private final KhachHangBUS khachHangBUS;
    private final PhongBUS phongBUS;
    private final DatPhongBUS datPhongBUS;
    private final ChiTietDatPhongBUS chiTietDatPhongBUS;
    private final ChiTietThueDichVuBUS chiTietThueDichVuBUS;
    private final HoaDonBUS hoaDonBUS;
    private final List<String> selectedRoomIds;
    private final String selectedRoomsText;
    private final Date checkInDate;
    private final Date checkOutDate;
    private final AccountInfo accountInfo;
    private JTextField nameField;
    private JTextField phoneField;
    private List<String> selectedServiceIds;
    private List<JCheckBox> serviceCheckBoxes;

    public BookingDialog(JComponent parent, List<String> selectedRoomIds, String selectedRoomsText,
            Date checkInDate, Date checkOutDate) {
        this.parent = parent;
        this.dichVuBUS = new DichVuBUS();
        this.khachHangBUS = new KhachHangBUS();
        this.phongBUS = new PhongBUS();
        this.datPhongBUS = new DatPhongBUS();
        this.chiTietDatPhongBUS = new ChiTietDatPhongBUS();
        this.chiTietThueDichVuBUS = new ChiTietThueDichVuBUS();
        this.hoaDonBUS = new HoaDonBUS();
        this.selectedRoomIds = selectedRoomIds;
        this.selectedRoomsText = selectedRoomsText;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.selectedServiceIds = new ArrayList<>();
        this.serviceCheckBoxes = new ArrayList<>();

        HomeFrame homeFrame = (HomeFrame) SwingUtilities.getAncestorOfClass(HomeFrame.class, parent);
        if (homeFrame == null || homeFrame.getAccountInfo() == null) {
            throw new IllegalStateException(
                    "Không thể khởi tạo BookingDialog: HomeFrame hoặc thông tin tài khoản không khả dụng.");
        }
        this.accountInfo = homeFrame.getAccountInfo();
    }

    public void showDialog() {
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

        if (!"KHACHHANG".equals(accountInfo.getAccountType())) {
            JOptionPane.showMessageDialog(parent, "Vui lòng đăng nhập tài khoản khách hàng để đặt phòng!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) null, "Xác nhận đặt phòng", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 650);
        HomeFrame homeFrame = (HomeFrame) SwingUtilities.getAncestorOfClass(HomeFrame.class, parent);
        if (homeFrame != null) {
            dialog.setLocationRelativeTo(homeFrame);
        } else {
            dialog.setLocationRelativeTo(null);
        }

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(new Color(149, 213, 178));
        headerPanel.setPreferredSize(new Dimension(500, 50));
        JLabel headerLabel = new JLabel("XÁC NHẬN ĐẶT PHÒNG");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerPanel.add(headerLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

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

        final int tongTienPhong;
        final long diffInDays;
        {
            long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
            long tempDiffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            diffInDays = tempDiffInDays == 0 ? 1 : tempDiffInDays;

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

        List<DichVuDTO> services = dichVuBUS.getAllDichVu();
        int[] tongTienDichVu = { 0 };

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

        JLabel customerTitle = new JLabel("Thông tin khách hàng", SwingConstants.CENTER);
        customerTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        customerTitle.setForeground(new Color(66, 133, 244));
        customerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(customerTitle);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel customerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        customerPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Họ và tên:");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField = new JTextField();
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        customerPanel.add(nameLabel);
        customerPanel.add(nameField);

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

        JLabel serviceTitle = new JLabel("Các dịch vụ tại khách sạn", SwingConstants.CENTER);
        serviceTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        serviceTitle.setForeground(new Color(66, 133, 244));
        serviceTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        serviceTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(serviceTitle);

        int numCols = 2;
        int numRows = (int) Math.ceil((double) services.size() / numCols);

        JPanel servicePanel = new JPanel(new GridLayout(numRows, numCols, 10, 10));
        servicePanel.setBackground(Color.WHITE);

        for (DichVuDTO service : services) {
            JCheckBox checkBox = new JCheckBox(service.getTenDV() + " (" + df.format(service.getGiaDV()) + ")");
            checkBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
            checkBox.setBackground(Color.WHITE);
            checkBox.setToolTipText(service.getMaDV());
            checkBox.setFocusPainted(false);
            checkBox.setBorder(new EmptyBorder(5, 10, 5, 10));
            checkBox.addActionListener(e -> {
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
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Vui lòng nhập đầy đủ họ và tên và số điện thoại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!phone.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(parent, "Số điện thoại phải là số và có 10-11 chữ số!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KhachHangDTO khachHang = khachHangBUS.selectById(String.valueOf(accountInfo.getAccountId()));
            if (khachHang == null) {
                JOptionPane.showMessageDialog(parent, "Không tìm thấy thông tin khách hàng!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            khachHang.setTenKhachHang(name);
            khachHang.setSoDienThoai(phone);
            try {
                khachHangBUS.update(khachHang);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(parent, "Lỗi cập nhật thông tin khách hàng: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DatPhongDTO datPhong = new DatPhongDTO();
            String maDP = "DP" + String.format("%03d", datPhongBUS.getAutoIncrement());
            datPhong.setMaDP(maDP);
            datPhong.setMaKH(khachHang.getMaKhachHang());
            datPhong.setNgayLapPhieu(new Date());
            datPhong.setTienDatCoc((int) ((tongTienPhong + tongTienDichVu[0]) * 0.3));
            datPhong.setTinhTrangXuLy(0);
            datPhong.setXuLy(1);

            selectedServiceIds.clear();
            for (JCheckBox cb : serviceCheckBoxes) {
                if (cb.isSelected()) {
                    selectedServiceIds.add(cb.getToolTipText());
                }
            }

            Map<String, PhongDTO> phongMap = new HashMap<>();
            for (String maPhong : selectedRoomIds) {
                PhongDTO phong = phongBUS.getPhongById(maPhong);
                if (phong == null) {
                    JOptionPane.showMessageDialog(parent, "Phòng " + maPhong + " không tồn tại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                phongMap.put(maPhong, phong);
            }

            Map<String, DichVuDTO> dichVuMap = new HashMap<>();
            for (String maDV : selectedServiceIds) {
                DichVuDTO service = dichVuBUS.getDichVuById(maDV);
                if (service == null) {
                    JOptionPane.showMessageDialog(parent, "Dịch vụ " + maDV + " không tồn tại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dichVuMap.put(maDV, service);
            }

            // Retry logic for transaction
            int maxRetries = 5;
            int attempt = 0;
            boolean success = false;
            Connection conn = null;

            while (attempt < maxRetries && !success) {
                try {
                    conn = ConnectDB.getConnection();
                    if (conn == null) {
                        JOptionPane.showMessageDialog(parent,
                                "Không thể kết nối đến cơ sở dữ liệu. Vui lòng kiểm tra cấu hình!",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    conn.setAutoCommit(false);

                    int result = datPhongBUS.add(datPhong, conn);
                    if (result == 0) {
                        conn.rollback();
                        JOptionPane.showMessageDialog(parent, "Đặt phòng thất bại! Vui lòng thử lại.",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    for (String maPhong : selectedRoomIds) {
                        ChiTietDatPhongDTO chiTiet = new ChiTietDatPhongDTO();
                        chiTiet.setMaCTDP(maDP);
                        chiTiet.setMaP(maPhong);

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(checkInDate);
                        cal.set(Calendar.HOUR_OF_DAY, 14);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        chiTiet.setNgayThue(cal.getTime());

                        cal.setTime(checkOutDate);
                        cal.set(Calendar.HOUR_OF_DAY, 12);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        chiTiet.setNgayTra(cal.getTime());

                        chiTiet.setNgayCheckOut(null);
                        chiTiet.setLoaiHinhThue(0);
                        chiTiet.setGiaThue(phongMap.get(maPhong).getGiaP() * (int) diffInDays);
                        chiTiet.setTinhTrang(0);
                        chiTietDatPhongBUS.add(chiTiet, conn);
                    }

                    for (String maDV : selectedServiceIds) {
                        ChiTietThueDichVuDTO chiTietDV = new ChiTietThueDichVuDTO();
                        chiTietDV.setMaCTT(maDP);
                        chiTietDV.setMaDV(maDV);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(checkInDate);
                        cal.set(Calendar.HOUR_OF_DAY, 8);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        chiTietDV.setNgaySuDung(cal.getTime());
                        chiTietDV.setSoLuong(1);
                        chiTietDV.setGiaDV(dichVuMap.get(maDV).getGiaDV());
                        chiTietThueDichVuBUS.add(chiTietDV, conn);
                    }

                    // Tạo hóa đơn
                    HoaDonDTO hoaDon = new HoaDonDTO();
                    String maHD = "HD" + String.format("%03d", HoaDonDAO.getInstance().getAutoIncrement());
                    hoaDon.setMaHD(maHD);
                    hoaDon.setMaCTT(maDP);
                    hoaDon.setTienP(tongTienPhong);
                    hoaDon.setTienDV(tongTienDichVu[0]);
                    hoaDon.setGiamGia(0);
                    hoaDon.setPhuThu(0);
                    hoaDon.setTongTien(tongTienPhong + tongTienDichVu[0]);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(checkOutDate);
                    cal.set(Calendar.HOUR_OF_DAY, 12);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    hoaDon.setNgayThanhToan(cal.getTime());
                    hoaDon.setHinhThucThanhToan("Chuyển khoản"); // Đặt mặc định là "Chuyển khoản"
                    hoaDon.setXuLy(0); // Chưa xử lý

                    int hoaDonResult = hoaDonBUS.add(hoaDon, conn);
                    if (hoaDonResult == 0) {
                        conn.rollback();
                        JOptionPane.showMessageDialog(parent, "Tạo hóa đơn thất bại! Vui lòng thử lại.",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    conn.commit();
                    success = true;
                    JOptionPane.showMessageDialog(parent,
                            "Đặt phòng thành công! Mã đặt phòng: " + maDP + "\nMã hóa đơn: " + maHD,
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Làm mới FormHoaDon nếu đang mở
                    HomeFrame parentFrame = (HomeFrame) SwingUtilities.getAncestorOfClass(HomeFrame.class, parent);
                    if (homeFrame != null) {
                        Component[] components = homeFrame.getContentPane().getComponents();
                        for (Component comp : components) {
                            if (comp instanceof FormHoaDon) {
                                ((FormHoaDon) comp).loadTableData();
                                System.out.println("Refreshed FormHoaDon for maKH=" + khachHang.getMaKhachHang());
                                break;
                            }
                        }
                    }
                    dialog.dispose();
                } catch (SQLException ex) {
                    attempt++;
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.rollback();
                        }
                    } catch (SQLException rollbackEx) {
                        rollbackEx.printStackTrace();
                    }

                    if (ex.getMessage().contains("Lock wait timeout exceeded") && attempt < maxRetries) {
                        try {
                            Thread.sleep(200 * attempt);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                        continue;
                    }

                    String errorMessage = "Lỗi cơ sở dữ liệu: " + ex.getMessage();
                    if (ex.getMessage().contains("Access denied")) {
                        errorMessage = "Tài khoản hoặc mật khẩu cơ sở dữ liệu không đúng!";
                    } else if (ex.getMessage().contains("Unknown database")) {
                        errorMessage = "Cơ sở dữ liệu không tồn tại! Vui lòng kiểm tra tên cơ sở dữ liệu.";
                    } else if (ex.getMessage().contains("Connection refused")) {
                        errorMessage = "Không thể kết nối đến server MySQL. Vui lòng kiểm tra server!";
                    } else if (ex.getMessage().contains("Duplicate entry")) {
                        errorMessage = "Lỗi trùng khóa chính hoặc khóa ngoại. Vui lòng kiểm tra dữ liệu!";
                    } else if (ex.getMessage().contains("Lock wait timeout exceeded")) {
                        errorMessage = "Hệ thống đang bận, vui lòng thử lại sau vài giây!";
                    }
                    JOptionPane.showMessageDialog(parent, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                } finally {
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.setAutoCommit(true);
                            conn.close();
                        }
                    } catch (SQLException closeEx) {
                        closeEx.printStackTrace();
                    }
                }
            }

            if (!success) {
                JOptionPane.showMessageDialog(parent,
                        "Đặt phòng thất bại sau " + maxRetries + " lần thử. Vui lòng thử lại sau!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        footerPanel.add(confirmButton);
        dialog.add(footerPanel, BorderLayout.SOUTH);

        KhachHangDTO khachHang = khachHangBUS.selectById(String.valueOf(accountInfo.getAccountId()));
        if (khachHang != null) {
            nameField.setText(khachHang.getTenKhachHang() != null ? khachHang.getTenKhachHang() : "");
            phoneField.setText(khachHang.getSoDienThoai() != null ? khachHang.getSoDienThoai() : "");
        }

        dialog.setVisible(true);
    }
}
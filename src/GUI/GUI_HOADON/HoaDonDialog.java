package GUI_HOADON;

import BUS.HoaDonBUS;
import DTO.HoaDonDTO;
import Component.ButtonCustom;
import Component.HeaderTitle;
import Component.InputDate;
import Component.InputForm;
import Component.NumericDocumentFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

public class HoaDonDialog extends JDialog implements ActionListener {
    private HoaDonBUS hoaDonBUS;
    private HoaDonDTO hoaDon;
    private boolean isEditMode;
    private boolean isViewMode;
    private HeaderTitle titlePage;
    private JPanel pnCenter, pnInfoHoaDon, pnBottom, xuLyPanel, hinhThucThanhToanPanel;
    private InputDate txtNgayThanhToan; // Changed to InputDate
    private InputForm txtMaHD, txtMaCTT, txtTienP, txtTienDV, txtGiamGia, txtPhuThu, txtTongTien;
    private JComboBox<String> cbXuLy, cbHinhThucThanhToan; // Added JComboBox for payment method
    private ButtonCustom btnSave, btnCancel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public HoaDonDialog(JFrame parent, HoaDonDTO hoaDon, boolean isEditMode, boolean isViewMode) {
        super(parent, isViewMode ? "CHI TIẾT HÓA ĐƠN" : (isEditMode ? "SỬA HÓA ĐƠN" : "THÊM HÓA ĐƠN MỚI"), true);
        this.hoaDonBUS = new HoaDonBUS();
        this.hoaDon = hoaDon;
        this.isEditMode = isEditMode;
        this.isViewMode = isViewMode;

        System.out.println("HoaDonDialog initialized: isEditMode=" + isEditMode + ", isViewMode=" + isViewMode
                + ", hoaDon=" + (hoaDon != null ? hoaDon.getMaHD() : "null"));

        initComponents(isViewMode ? "CHI TIẾT HÓA ĐƠN" : (isEditMode ? "SỬA HÓA ĐƠN" : "THÊM HÓA ĐƠN MỚI"));
        if (isEditMode || isViewMode) {
            if (hoaDon != null) {
                setInfo(hoaDon);
            } else {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu hóa đơn để hiển thị!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } else {
            initCreate();
        }

        if (isViewMode) {
            initView();
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(String title) {
        setSize(new Dimension(800, 600));
        setLayout(new BorderLayout(0, 0));

        // Tiêu đề
        titlePage = new HeaderTitle(title.toUpperCase());
        titlePage.setColor("167AC6");
        add(titlePage, BorderLayout.NORTH);

        // Thêm nút "Xác nhận thanh toán" trong chế độ xem
        if (isViewMode && hoaDon != null && hoaDon.getXuLy() == 0) {
            ButtonCustom btnConfirmPayment = new ButtonCustom("Xác nhận thanh toán", "success", 14);
            btnConfirmPayment.addActionListener(e -> confirmPayment());
            pnBottom.add(btnConfirmPayment);
        }

        // Phần chính
        pnCenter = new JPanel(new BorderLayout());
        pnCenter.setBackground(Color.WHITE);
        add(pnCenter, BorderLayout.CENTER);

        // Phần thông tin hóa đơn
        pnInfoHoaDon = new JPanel(new GridLayout(5, 2, 15, 15));
        pnInfoHoaDon.setBackground(Color.WHITE);
        pnInfoHoaDon.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnCenter.add(pnInfoHoaDon, BorderLayout.CENTER);

        // Khởi tạo các trường nhập liệu
        txtMaHD = new InputForm("Mã Hóa Đơn");
        txtMaHD.setEditable(false);
        txtMaCTT = new InputForm("Mã Chi Tiết Thuê");
        txtTienP = new InputForm("Tiền Phòng");
        PlainDocument tienPDoc = (PlainDocument) txtTienP.getTxtForm().getDocument();
        tienPDoc.setDocumentFilter(new NumericDocumentFilter());
        txtTienDV = new InputForm("Tiền Dịch Vụ");
        PlainDocument tienDVDoc = (PlainDocument) txtTienDV.getTxtForm().getDocument();
        tienDVDoc.setDocumentFilter(new NumericDocumentFilter());
        txtGiamGia = new InputForm("Giảm Giá");
        PlainDocument giamGiaDoc = (PlainDocument) txtGiamGia.getTxtForm().getDocument();
        giamGiaDoc.setDocumentFilter(new NumericDocumentFilter());
        txtPhuThu = new InputForm("Phụ Thu");
        PlainDocument phuThuDoc = (PlainDocument) txtPhuThu.getTxtForm().getDocument();
        phuThuDoc.setDocumentFilter(new NumericDocumentFilter());
        txtTongTien = new InputForm("Tổng Tiền");
        PlainDocument tongTienDoc = (PlainDocument) txtTongTien.getTxtForm().getDocument();
        tongTienDoc.setDocumentFilter(new NumericDocumentFilter());
        txtNgayThanhToan = new InputDate("Ngày Thanh Toán"); // Use InputDate
        cbHinhThucThanhToan = new JComboBox<>(new String[] { "Tiền mặt", "Chuyển khoản", "Thẻ tín dụng" });

        // Tạo panel tùy chỉnh cho "Hình Thức Thanh Toán"
        hinhThucThanhToanPanel = new JPanel(new GridBagLayout());
        hinhThucThanhToanPanel.setBackground(Color.WHITE);
        hinhThucThanhToanPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbcHinhThuc = new GridBagConstraints();
        gbcHinhThuc.insets = new Insets(5, 5, 5, 5);
        gbcHinhThuc.fill = GridBagConstraints.HORIZONTAL;

        // Nhãn "Hình Thức Thanh Toán"
        JLabel lblHinhThuc = new JLabel("Hình Thức Thanh Toán:");
        Font timesNewRomanBold = Font.getFont("Times New Roman");
        if (timesNewRomanBold != null) {
            lblHinhThuc.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblHinhThuc.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        }
        lblHinhThuc.setForeground(new Color(0, 102, 153));
        gbcHinhThuc.gridx = 0;
        gbcHinhThuc.gridy = 0;
        gbcHinhThuc.weightx = 0.3;
        hinhThucThanhToanPanel.add(lblHinhThuc, gbcHinhThuc);

        // JComboBox
        Font timesNewRomanPlain = Font.getFont("Times New Roman");
        if (timesNewRomanPlain != null) {
            cbHinhThucThanhToan.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            cbHinhThucThanhToan.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        gbcHinhThuc.gridx = 1;
        gbcHinhThuc.gridy = 0;
        gbcHinhThuc.weightx = 0.7;
        hinhThucThanhToanPanel.add(cbHinhThucThanhToan, gbcHinhThuc);

        // Tạo JComboBox cho "Xử Lý"
        cbXuLy = new JComboBox<>(new String[] { "Đã xử lý", "Chưa xử lý" });

        // Tạo panel tùy chỉnh cho "Xử Lý"
        xuLyPanel = new JPanel(new GridBagLayout());
        xuLyPanel.setBackground(Color.WHITE);
        xuLyPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbcXuLy = new GridBagConstraints();
        gbcXuLy.insets = new Insets(5, 5, 5, 5);
        gbcXuLy.fill = GridBagConstraints.HORIZONTAL;

        // Nhãn "Xử Lý"
        JLabel lblXuLy = new JLabel("Xử Lý:");
        if (timesNewRomanBold != null) {
            lblXuLy.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblXuLy.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        }
        lblXuLy.setForeground(new Color(0, 102, 153));
        gbcXuLy.gridx = 0;
        gbcXuLy.gridy = 0;
        gbcXuLy.weightx = 0.3;
        xuLyPanel.add(lblXuLy, gbcXuLy);

        // JComboBox
        if (timesNewRomanPlain != null) {
            cbXuLy.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            cbXuLy.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        gbcXuLy.gridx = 1;
        gbcXuLy.gridy = 0;
        gbcXuLy.weightx = 0.7;
        xuLyPanel.add(cbXuLy, gbcXuLy);

        // Thêm các trường vào panel
        pnInfoHoaDon.add(txtMaHD);
        pnInfoHoaDon.add(txtMaCTT);
        pnInfoHoaDon.add(txtTienP);
        pnInfoHoaDon.add(txtTienDV);
        pnInfoHoaDon.add(txtGiamGia);
        pnInfoHoaDon.add(txtPhuThu);
        pnInfoHoaDon.add(txtTongTien);
        pnInfoHoaDon.add(txtNgayThanhToan);
        pnInfoHoaDon.add(hinhThucThanhToanPanel); // Add JComboBox panel
        pnInfoHoaDon.add(xuLyPanel);

        // Phần nút (căn giữa)
        pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
        pnBottom.setBackground(Color.WHITE);
        pnCenter.add(pnBottom, BorderLayout.SOUTH);

        if (!isViewMode) {
            btnSave = new ButtonCustom(isEditMode ? "Lưu thông tin" : "Thêm hóa đơn", "success", 14);
            btnSave.addActionListener(this);
            pnBottom.add(btnSave);
        }

        btnCancel = new ButtonCustom(isViewMode ? "Đóng" : "Hủy bỏ", "danger", 14);
        btnCancel.addActionListener(this);
        pnBottom.add(btnCancel);
    }

    private void initCreate() {
        // Tạo mã hóa đơn tăng dần từ HD001
        ArrayList<HoaDonDTO> listHoaDon = hoaDonBUS.getAll();
        int nextId = 1;
        if (!listHoaDon.isEmpty()) {
            int maxId = 0;
            for (HoaDonDTO hd : listHoaDon) {
                String maHD = hd.getMaHD();
                if (maHD != null && maHD.startsWith("HD")) {
                    try {
                        int id = Integer.parseInt(maHD.substring(2));
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Bỏ qua nếu mã hóa đơn không đúng định dạng
                    }
                }
            }
            nextId = maxId + 1;
        }
        txtMaHD.setText("HD" + String.format("%03d", nextId));

        txtMaCTT.setText("");
        txtTienP.setText("");
        txtTienDV.setText("");
        txtGiamGia.setText("");
        txtPhuThu.setText("");
        txtTongTien.setText("");
        txtNgayThanhToan.setDate(new java.util.Date()); // Set current date
        cbHinhThucThanhToan.setSelectedIndex(0); // Default to "Tiền mặt"
        cbXuLy.setSelectedItem("Đã xử lý");
        System.out.println("initCreate called: maHD=" + txtMaHD.getText());
    }

    private void initView() {
        txtMaHD.setEditable(false);
        txtMaCTT.setEditable(false);
        txtTienP.setEditable(false);
        txtTienDV.setEditable(false);
        txtGiamGia.setEditable(false);
        txtPhuThu.setEditable(false);
        txtTongTien.setEditable(false);
        txtNgayThanhToan.setDisable(); // Disable InputDate
        cbHinhThucThanhToan.setEnabled(false); // Disable JComboBox
        cbXuLy.setEnabled(false);
    }

    private void setInfo(HoaDonDTO hoaDon) {
        txtMaHD.setText(hoaDon.getMaHD() != null ? hoaDon.getMaHD() : "");
        txtMaCTT.setText(hoaDon.getMaCTT() != null ? hoaDon.getMaCTT() : "");
        txtTienP.setText(hoaDon.getTienP() > 0 ? String.valueOf(hoaDon.getTienP()) : "");
        txtTienDV.setText(hoaDon.getTienDV() > 0 ? String.valueOf(hoaDon.getTienDV()) : "");
        txtGiamGia.setText(hoaDon.getGiamGia() > 0 ? String.valueOf(hoaDon.getGiamGia()) : "");
        txtPhuThu.setText(hoaDon.getPhuThu() > 0 ? String.valueOf(hoaDon.getPhuThu()) : "");
        txtTongTien.setText(hoaDon.getTongTien() > 0 ? String.valueOf(hoaDon.getTongTien()) : "");
        txtNgayThanhToan.setDate(hoaDon.getNgayThanhToan()); // Set date
        String hinhThuc = hoaDon.getHinhThucThanhToan();
        if (hinhThuc != null && !hinhThuc.isEmpty()) {
            cbHinhThucThanhToan.setSelectedItem(hinhThuc);
        } else {
            cbHinhThucThanhToan.setSelectedIndex(0); // Default to first option
        }
        cbXuLy.setSelectedItem(hoaDon.getXuLy() == 0 ? "Chưa xử lý" : "Đã xử lý");
    }

    private HoaDonDTO getInfo() {
        String maHD = txtMaHD.getText();
        String maCTT = txtMaCTT.getText();
        int tienP = txtTienP.getText().isEmpty() ? 0 : Integer.parseInt(txtTienP.getText());
        int tienDV = txtTienDV.getText().isEmpty() ? 0 : Integer.parseInt(txtTienDV.getText());
        int giamGia = txtGiamGia.getText().isEmpty() ? 0 : Integer.parseInt(txtGiamGia.getText());
        int phuThu = txtPhuThu.getText().isEmpty() ? 0 : Integer.parseInt(txtPhuThu.getText());
        int tongTien = txtTongTien.getText().isEmpty() ? 0 : Integer.parseInt(txtTongTien.getText());
        java.util.Date ngayThanhToan = null;
        try {
            ngayThanhToan = txtNgayThanhToan.getDate(); // Get date from InputDate
        } catch (ParseException e) {
            ngayThanhToan = new java.util.Date();
        }
        String hinhThucThanhToan = (String) cbHinhThucThanhToan.getSelectedItem(); // Get selected payment method
        int xuLy = cbXuLy.getSelectedItem().equals("Đã xử lý") ? 1 : 0;
        return new HoaDonDTO(maHD, maCTT, tienP, tienDV, giamGia, phuThu, tongTien, ngayThanhToan, hinhThucThanhToan, xuLy);
    }

    private boolean checkInput() {
        if (txtMaCTT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã chi tiết thuê không được để trống!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtTienP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tiền phòng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int tienP = Integer.parseInt(txtTienP.getText());
            if (tienP < 0) {
                JOptionPane.showMessageDialog(this, "Tiền phòng phải là số nguyên dương!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tiền phòng phải là số nguyên dương!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtTongTien.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tổng tiền không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int tongTien = Integer.parseInt(txtTongTien.getText());
            if (tongTien < 0) {
                JOptionPane.showMessageDialog(this, "Tổng tiền phải là số nguyên dương!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tổng tiền phải là số nguyên dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            if (txtNgayThanhToan.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Ngày thanh toán không được để trống!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày thanh toán không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbHinhThucThanhToan.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hình thức thanh toán không được để trống!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnCancel) {
            dispose();
        } else if (source == btnSave && checkInput()) {
            HoaDonDTO newHoaDon = getInfo();
            try {
                if (isEditMode) {
                    hoaDonBUS.update(newHoaDon);
                    JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    hoaDonBUS.add(newHoaDon);
                    JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void confirmPayment() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Xác nhận khách hàng đã thanh toán số tiền còn lại?", 
            "Xác nhận thanh toán", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                hoaDon.setXuLy(1);
                int result = hoaDonBUS.update(hoaDon);
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Xác nhận thanh toán thành công! Hóa đơn đã được xử lý.", 
                        "Thành công", 
                        JOptionPane.INFORMATION_MESSAGE);
                    cbXuLy.setSelectedItem("Đã xử lý");
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Xác nhận thanh toán thất bại!", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi xác nhận thanh toán: " + ex.getMessage(), 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
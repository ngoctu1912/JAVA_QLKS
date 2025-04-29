package GUI_PHONG;

import BUS.PhongBUS;
import DTO.PhongDTO;
import Component.ButtonCustom;
import Component.HeaderTitle;
import Component.InputForm;
import Component.InputImage;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

public class PhongDialog extends JDialog implements ActionListener {
    private static final String IMAGE_DIR = "images";
    private PhongBUS phongBUS;
    private PhongDTO phong;
    private boolean isEditMode;
    private boolean isViewMode;
    private HeaderTitle titlePage;
    private JPanel pnCenter, pnInfoPhong, pnInfoPhongRight, pnBottom, tinhTrangPanel, loaiPhongPanel;
    private InputForm txtMaP, txtTenP, txtGiaP, txtChiTietLoaiPhong;
    private JComboBox<String> cbLoaiP; // Thay txtLoaiP bằng JComboBox
    private JComboBox<String> cbTinhTrang;
    private InputImage hinhAnh;
    private ButtonCustom btnSave, btnCancel;

    public PhongDialog(JFrame parent, PhongDTO phong, boolean isEditMode, boolean isViewMode) {
        super(parent, isViewMode ? "CHI TIẾT PHÒNG" : (isEditMode ? "SỬA PHÒNG" : "THÊM PHÒNG MỚI"), true);
        this.phongBUS = new PhongBUS();
        this.phong = phong;
        this.isEditMode = isEditMode;
        this.isViewMode = isViewMode;

        File imageDir = new File(IMAGE_DIR);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }

        System.out.println("PhongDialog initialized: isEditMode=" + isEditMode + ", isViewMode=" + isViewMode + ", phong=" + (phong != null ? phong.getMaP() : "null"));

        initComponents(isViewMode ? "CHI TIẾT PHÒNG" : (isEditMode ? "SỬA PHÒNG" : "THÊM PHÒNG MỚI"));
        if (isEditMode || isViewMode) {
            if (phong != null) {
                setInfo(phong);
            } else {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu phòng để hiển thị!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        setSize(new Dimension(1200, 550));
        setLayout(new BorderLayout(0, 0));

        // Tiêu đề
        titlePage = new HeaderTitle(title.toUpperCase());
        titlePage.setColor("167AC6");
        add(titlePage, BorderLayout.NORTH);

        // Phần chính
        pnCenter = new JPanel(new BorderLayout());
        pnCenter.setBackground(Color.WHITE);
        add(pnCenter, BorderLayout.CENTER);

        // Phần thông tin phòng
        pnInfoPhong = new JPanel(new GridLayout(3, 2, 15, 15));
        pnInfoPhong.setBackground(Color.WHITE);
        pnInfoPhong.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnCenter.add(pnInfoPhong, BorderLayout.CENTER);

        // Phần hình ảnh (bên trái)
        pnInfoPhongRight = new JPanel();
        pnInfoPhongRight.setBackground(Color.WHITE);
        pnInfoPhongRight.setPreferredSize(new Dimension(350, 600));
        pnInfoPhongRight.setBorder(new EmptyBorder(0, 20, 0, 20));
        pnCenter.add(pnInfoPhongRight, BorderLayout.WEST);

        // Khởi tạo các trường nhập liệu
        txtMaP = new InputForm("Mã Phòng");
        txtMaP.setEditable(false);
        txtTenP = new InputForm("Tên Phòng");
        
        // Tạo JComboBox cho "Loại Phòng"
        cbLoaiP = new JComboBox<>(new String[] { "Đơn", "Đôi", "Gia đình", "VIP", "Suite" });

        // Tạo panel tùy chỉnh cho "Loại Phòng"
        loaiPhongPanel = new JPanel(new GridBagLayout());
        loaiPhongPanel.setBackground(Color.WHITE);
        loaiPhongPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nhãn "Loại Phòng"
        JLabel lblLoaiP = new JLabel("Loại Phòng:");
        Font timesNewRomanBold = Font.getFont("Times New Roman");
        if (timesNewRomanBold != null) {
            lblLoaiP.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblLoaiP.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        }
        lblLoaiP.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        loaiPhongPanel.add(lblLoaiP, gbc);

        // JComboBox
        Font timesNewRomanPlain = Font.getFont("Times New Roman");
        if (timesNewRomanPlain != null) {
            cbLoaiP.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            cbLoaiP.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        loaiPhongPanel.add(cbLoaiP, gbc);

        // Thêm ActionListener để tự động điền "Chi Tiết Loại Phòng"
        cbLoaiP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiP = (String) cbLoaiP.getSelectedItem();
                switch (selectedLoaiP) {
                    case "Đơn":
                        txtChiTietLoaiPhong.setText("1 giường đơn");
                        break;
                    case "Đôi":
                        txtChiTietLoaiPhong.setText("1 giường đôi");
                        break;
                    case "Gia đình":
                        txtChiTietLoaiPhong.setText("2 giường đôi");
                        break;
                    case "VIP":
                        txtChiTietLoaiPhong.setText("1 giường king");
                        break;
                    case "Suite":
                        txtChiTietLoaiPhong.setText("1 giường king + sofa");
                        break;
                    default:
                        txtChiTietLoaiPhong.setText("");
                        break;
                }
            }
        });

        txtGiaP = new InputForm("Giá Phòng");
        PlainDocument giaDoc = (PlainDocument) txtGiaP.getTxtForm().getDocument();
        giaDoc.setDocumentFilter(new NumericDocumentFilter());
        txtChiTietLoaiPhong = new InputForm("Chi Tiết Loại Phòng");

        // Tạo JComboBox cho "Tình Trạng"
        cbTinhTrang = new JComboBox<>(new String[] { "0 - Trống", "1 - Đã đặt" });

        // Tạo panel tùy chỉnh cho "Tình Trạng"
        tinhTrangPanel = new JPanel(new GridBagLayout());
        tinhTrangPanel.setBackground(Color.WHITE);
        tinhTrangPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbcTinhTrang = new GridBagConstraints();
        gbcTinhTrang.insets = new Insets(5, 5, 5, 5);
        gbcTinhTrang.fill = GridBagConstraints.HORIZONTAL;

        // Nhãn "Tình Trạng"
        JLabel lblTinhTrang = new JLabel("Tình Trạng:");
        if (timesNewRomanBold != null) {
            lblTinhTrang.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblTinhTrang.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        }
        lblTinhTrang.setForeground(new Color(0, 102, 153));
        gbcTinhTrang.gridx = 0;
        gbcTinhTrang.gridy = 0;
        gbcTinhTrang.weightx = 0.3;
        tinhTrangPanel.add(lblTinhTrang, gbcTinhTrang);

        // JComboBox
        if (timesNewRomanPlain != null) {
            cbTinhTrang.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            cbTinhTrang.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        gbcTinhTrang.gridx = 1;
        gbcTinhTrang.gridy = 0;
        gbcTinhTrang.weightx = 0.7;
        tinhTrangPanel.add(cbTinhTrang, gbcTinhTrang);

        hinhAnh = new InputImage("Hình Ảnh");

        // Thêm các trường vào panel
        pnInfoPhong.add(txtMaP);
        pnInfoPhong.add(txtTenP);
        pnInfoPhong.add(loaiPhongPanel); // Thay txtLoaiP bằng loaiPhongPanel
        pnInfoPhong.add(txtGiaP);
        pnInfoPhong.add(txtChiTietLoaiPhong);
        pnInfoPhong.add(tinhTrangPanel);
        pnInfoPhongRight.add(hinhAnh);

        // Phần nút (căn giữa)
        pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
        pnBottom.setBackground(Color.WHITE);
        pnCenter.add(pnBottom, BorderLayout.SOUTH);

        if (!isViewMode) {
            btnSave = new ButtonCustom(isEditMode ? "Lưu thông tin" : "Thêm phòng", "success", 14);
            btnSave.addActionListener(this);
            pnBottom.add(btnSave);
        }

        btnCancel = new ButtonCustom(isViewMode ? "Đóng" : "Hủy bỏ", "danger", 14);
        btnCancel.addActionListener(this);
        pnBottom.add(btnCancel);
    }

    private void initCreate() {
        // Tạo mã phòng tăng dần từ P601
        ArrayList<PhongDTO> listPhong = phongBUS.getAllPhong();
        int nextId = 601; // Bắt đầu từ P601
        if (!listPhong.isEmpty()) {
            // Tìm mã phòng lớn nhất
            int maxId = 0;
            for (PhongDTO p : listPhong) {
                String maP = p.getMaP();
                if (maP != null && maP.startsWith("P")) {
                    try {
                        int id = Integer.parseInt(maP.substring(1));
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Bỏ qua nếu mã phòng không đúng định dạng
                    }
                }
            }
            nextId = maxId + 1; // Tăng lên 1
        }
        txtMaP.setText("P" + nextId);

        txtTenP.setText("");
        cbLoaiP.setSelectedItem("Đơn"); // Mặc định chọn "Đơn"
        txtGiaP.setText("");
        txtChiTietLoaiPhong.setText("1 giường đơn"); // Tương ứng với "Đơn"
        cbTinhTrang.setSelectedItem("0 - Trống");
        hinhAnh.setUrl_img(null);
        System.out.println("initCreate called: maP=" + txtMaP.getText());
    }

    private void initView() {
        txtMaP.setEditable(false);
        txtTenP.setEditable(false);
        cbLoaiP.setEnabled(false); // Vô hiệu hóa JComboBox
        txtGiaP.setEditable(false);
        txtChiTietLoaiPhong.setEditable(false);
        cbTinhTrang.setEnabled(false);
        System.out.println("initView called");
    }

    private void setInfo(PhongDTO phong) {
        txtMaP.setText(phong.getMaP() != null ? phong.getMaP() : "");
        txtTenP.setText(phong.getTenP() != null ? phong.getTenP() : "");
        cbLoaiP.setSelectedItem(phong.getLoaiP() != null ? phong.getLoaiP() : "Đơn");
        txtGiaP.setText(phong.getGiaP() > 0 ? String.valueOf(phong.getGiaP()) : "");
        txtChiTietLoaiPhong.setText(phong.getChiTietLoaiPhong() != null ? phong.getChiTietLoaiPhong() : "");
        cbTinhTrang.setSelectedItem(phong.getTinhTrang() == 0 ? "0 - Trống" : "1 - Đã đặt");
        hinhAnh.setUrl_img(phong.getHinhAnh() != null && !phong.getHinhAnh().isEmpty() ? phong.getHinhAnh() : null);
        System.out.println("setInfo called: maP=" + phong.getMaP() + ", tenP=" + phong.getTenP());
    }

    private PhongDTO getInfo() {
        String maP = txtMaP.getText();
        String tenP = txtTenP.getText();
        String loaiP = (String) cbLoaiP.getSelectedItem(); // Lấy giá trị từ JComboBox
        int giaP = txtGiaP.getText().isEmpty() ? 0 : Integer.parseInt(txtGiaP.getText());
        String chiTietLoaiPhong = txtChiTietLoaiPhong.getText();
        int tinhTrang = Integer.parseInt(((String) cbTinhTrang.getSelectedItem()).split(" - ")[0]);
        String hinhAnhPath = hinhAnh.getUrl_img() != null ? hinhAnh.getUrl_img() : "";
        return new PhongDTO(maP, tenP, loaiP, hinhAnhPath, giaP, chiTietLoaiPhong, tinhTrang);
    }

    private String saveImage(String maP, String sourceImagePath) throws IOException {
        if (sourceImagePath == null || sourceImagePath.trim().isEmpty()) {
            return "";
        }
        String destImagePath = Paths.get(IMAGE_DIR, maP + ".jpg").toString();
        File sourceFile = new File(sourceImagePath);
        File destFile = new File(destImagePath);
        if (sourceFile.exists()) {
            Files.copy(sourceFile.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        return destImagePath;
    }

    private boolean checkInput() {
        if (txtTenP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên phòng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtGiaP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá phòng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int giaP = Integer.parseInt(txtGiaP.getText());
            if (giaP < 0) {
                JOptionPane.showMessageDialog(this, "Giá phòng phải là số nguyên dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá phòng phải là số nguyên dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (hinhAnh.getUrl_img() == null || hinhAnh.getUrl_img().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hình ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            PhongDTO newPhong = getInfo();
            try {
                String imagePath = saveImage(newPhong.getMaP(), newPhong.getHinhAnh());
                newPhong.setHinhAnh(imagePath);
                if (isEditMode) {
                    phongBUS.updatePhong(newPhong);
                    JOptionPane.showMessageDialog(this, "Cập nhật phòng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    phongBUS.addPhong(newPhong);
                    JOptionPane.showMessageDialog(this, "Thêm phòng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu hình ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
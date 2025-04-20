package GUI_PHONG;

import BUS.PhongBUS;
import Component.ButtonCustom;
import Component.HeaderTitle;
import Component.InputForm;
import Component.InputImage;
import DTO.PhongDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;

public class PhongDialog extends JDialog {
    private static final String IMAGE_DIR = "images"; // Relative path to images╚
    private PhongBUS phongBUS;
    private PhongDTO phong;
    private boolean isEditMode;
    private boolean isViewMode;

    private HeaderTitle titlePage;
    private JPanel pnmain, pnCenter, pnbottom, pninfosanpham, pninfosanphamright;
    private InputForm maPhong, tenPhong, giaPhong, chiTietLoaiPhong;
    private JComboBox<String> loaiPhong, tinhTrang;
    private InputImage hinhanh;
    private ButtonCustom btnThem, btnHuyBo;

    public PhongDialog(JFrame parent, PhongDTO phong, boolean isEditMode, boolean isViewMode) {
        super(parent, isViewMode ? "CHI TIẾT PHÒNG" : (isEditMode ? "SỬA PHÒNG" : "THÊM PHÒNG MỚI"), true);
        this.phongBUS = new PhongBUS();
        this.phong = phong;
        this.isEditMode = isEditMode;
        this.isViewMode = isViewMode;

        // Ensure the images directory exists
        File imageDir = new File(IMAGE_DIR);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }

        initComponents();
        if (isEditMode || isViewMode) {
            loadData(phong);
        } else {
            autoGenerateMaPhong();
        }

        if (isViewMode) {
            setFieldsEditable(false);
            btnThem.setVisible(false);
            btnHuyBo.setText("Đóng");
        }
    }

    private void initComponents() {
        setSize(new Dimension(1150, 510)); // Increased height by 30px (480 -> 510)
        setLayout(new BorderLayout(0, 0));
        setLocationRelativeTo(getOwner());

        titlePage = new HeaderTitle(isViewMode ? "CHI TIẾT PHÒNG" : (isEditMode ? "SỬA PHÒNG" : "THÊM PHÒNG MỚI"));
        add(titlePage, BorderLayout.NORTH);

        pnmain = new JPanel(new CardLayout());
        pnCenter = new JPanel(new BorderLayout());
        pnmain.add(pnCenter);
        add(pnmain, BorderLayout.CENTER);

        pninfosanpham = new JPanel(new GridLayout(3, 2, 10, 10));
        pninfosanpham.setBackground(Color.WHITE);
        pninfosanpham.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnCenter.add(pninfosanpham, BorderLayout.CENTER);

        pninfosanphamright = new JPanel();
        pninfosanphamright.setBackground(Color.WHITE);
        pninfosanphamright.setPreferredSize(new Dimension(300, 600));
        pninfosanphamright.setBorder(new EmptyBorder(0, 10, 0, 10));
        pnCenter.add(pninfosanphamright, BorderLayout.WEST);

        maPhong = new InputForm("Mã Phòng");
        maPhong.setEditable(false);
        tenPhong = new InputForm("Tên Phòng");
        loaiPhong = new JComboBox<>(new String[]{"Đơn", "Đôi", "Gia đình", "VIP", "Suite"});
        loaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        loaiPhong.setBackground(Color.WHITE);
        loaiPhong.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        giaPhong = new InputForm("Giá Phòng");
        chiTietLoaiPhong = new InputForm("Chi Tiết Loại Phòng");
        tinhTrang = new JComboBox<>(new String[]{"Trống", "Đã đặt"});
        tinhTrang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        tinhTrang.setBackground(Color.WHITE);
        tinhTrang.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Add ActionListener to loaiPhong to update chiTietLoaiPhong
        loaiPhong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiPhong = (String) loaiPhong.getSelectedItem();
                switch (selectedLoaiPhong) {
                    case "Đơn":
                        chiTietLoaiPhong.setText("1 giường đơn");
                        break;
                    case "Đôi":
                        chiTietLoaiPhong.setText("1 giường đôi");
                        break;
                    case "Gia đình":
                        chiTietLoaiPhong.setText("2 giường đôi");
                        break;
                    case "VIP":
                        chiTietLoaiPhong.setText("1 giường king");
                        break;
                    case "Suite":
                        chiTietLoaiPhong.setText("1 giường king + sofa");
                        break;
                    default:
                        chiTietLoaiPhong.setText("");
                }
            }
        });

        pninfosanpham.add(maPhong);
        pninfosanpham.add(tenPhong);
        pninfosanpham.add(createComboBoxPanel("Loại Phòng", loaiPhong));
        pninfosanpham.add(giaPhong);
        pninfosanpham.add(chiTietLoaiPhong);
        pninfosanpham.add(createComboBoxPanel("Tình Trạng", tinhTrang));

        hinhanh = new InputImage("Hình minh họa");
        pninfosanphamright.add(hinhanh);

        pnbottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnbottom.setBorder(new EmptyBorder(20, 0, 10, 0));
        pnbottom.setBackground(Color.WHITE);
        pnCenter.add(pnbottom, BorderLayout.SOUTH);

        btnThem = new ButtonCustom(isEditMode ? "Cập nhật" : "Thêm phòng", "success", 14);
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePhong();
            }
        });
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnHuyBo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        pnbottom.add(btnThem);
        pnbottom.add(btnHuyBo);
    }

    private JPanel createComboBoxPanel(String label, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbl = new JLabel(label + ":");
        lbl.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        panel.add(comboBox, gbc);

        return panel;
    }

    private void setFieldsEditable(boolean editable) {
        tenPhong.setEditable(editable);
        loaiPhong.setEnabled(editable);
        giaPhong.setEditable(editable);
        chiTietLoaiPhong.setEditable(editable);
        tinhTrang.setEnabled(editable);
        hinhanh.setEnabled(editable);
    }

    private void autoGenerateMaPhong() {
        int nextId = phongBUS.getAutoIncrement();
        maPhong.setText("P" + String.format("%03d", nextId));
    }

    private void loadData(PhongDTO phong) {
        maPhong.setText(phong.getMaP());
        tenPhong.setText(phong.getTenP());
        loaiPhong.setSelectedItem(phong.getLoaiP());
        giaPhong.setText(String.valueOf(phong.getGiaP()));
        chiTietLoaiPhong.setText(phong.getChiTietLoaiPhong());
        tinhTrang.setSelectedItem(phong.getTinhTrang() == 1 ? "Đã đặt" : "Trống");
        hinhanh.setUrl_img(phong.getHinhAnh()); // Relative path (e.g., "images/P001.jpg")
    }

    private String saveImage(String maP, String sourceImagePath) throws IOException {
        if (sourceImagePath == null || sourceImagePath.trim().isEmpty() || !sourceImagePath.endsWith("temp.jpg")) {
            return "";
        }

        // Destination path: images/<maP>.jpg
        String destImagePath = Paths.get(IMAGE_DIR, maP + ".jpg").toString();
        File tempFile = new File(sourceImagePath);
        File destFile = new File(destImagePath);

        // Rename temp.jpg to <maP>.jpg
        if (tempFile.exists()) {
            Files.move(tempFile.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        // Return the relative path to store in PhongDTO.hinhAnh
        return destImagePath;
    }

    private void savePhong() {
        if (tenPhong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên phòng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String giaText = giaPhong.getText().trim();
        if (giaText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá phòng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int giaP;
        try {
            giaP = NumberFormat.getNumberInstance().parse(giaText).intValue();
            if (giaP < 0) {
                throw new NumberFormatException("Giá phòng phải lớn hơn hoặc bằng 0");
            }
        } catch (ParseException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá phòng phải là số nguyên dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PhongDTO newPhong = new PhongDTO();
        newPhong.setMaP(maPhong.getText());
        newPhong.setTenP(tenPhong.getText());
        newPhong.setLoaiP((String) loaiPhong.getSelectedItem());
        newPhong.setGiaP(giaP);
        newPhong.setChiTietLoaiPhong(chiTietLoaiPhong.getText());
        newPhong.setTinhTrang(tinhTrang.getSelectedItem().equals("Đã đặt") ? 1 : 0);

        try {
            String imagePath = saveImage(newPhong.getMaP(), hinhanh.getUrl_img());
            newPhong.setHinhAnh(imagePath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hình ảnh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (isEditMode) {
                phongBUS.updatePhong(newPhong);
                JOptionPane.showMessageDialog(this, "Cập nhật phòng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                phongBUS.addPhong(newPhong);
                JOptionPane.showMessageDialog(this, "Thêm phòng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
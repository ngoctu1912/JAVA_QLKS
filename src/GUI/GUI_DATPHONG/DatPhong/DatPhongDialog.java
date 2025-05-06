package GUI_DATPHONG.DatPhong;

import BUS.DatPhongBUS;
import BUS.KhachHangBUS;
import DTO.DatPhongDTO;
import DTO.KhachHangDTO;
import Component.ButtonCustom;
import Component.HeaderTitle;
import Component.InputForm;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DatPhongDialog extends JDialog implements ActionListener {
    private DatPhongBUS datPhongBUS;
    private KhachHangBUS khachHangBUS;
    private DatPhongDTO datPhong;
    private boolean isEditMode;
    private HeaderTitle titlePage;
    private JPanel pnCenter, pnInfoDatPhong, pnBottom;
    private InputForm txtMaDP, txtTenKH, txtNgayLapPhieu, txtTienDatCoc;
    private JComboBox<String> cbTinhTrangXuLy, cbXuLy;
    private ButtonCustom btnSave, btnCancel;

    public DatPhongDialog(JFrame parent, DatPhongDTO datPhong, boolean isEditMode) {
        super(parent, isEditMode ? "SỬA ĐẶT PHÒNG" : "CHI TIẾT ĐẶT PHÒNG", true);
        this.datPhongBUS = new DatPhongBUS();
        this.khachHangBUS = new KhachHangBUS();
        this.datPhong = datPhong;
        this.isEditMode = isEditMode;

        initComponents(isEditMode ? "SỬA ĐẶT PHÒNG" : "CHI TIẾT ĐẶT PHÒNG");
        if (datPhong != null) {
            setInfo(datPhong);
        } else {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu đặt phòng để hiển thị!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
        }

        if (!isEditMode) {
            initView();
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(String title) {
        setSize(new Dimension(900, 400)); // Kích thước vừa đủ
        setLayout(new BorderLayout(0, 0));

        // Tiêu đề
        titlePage = new HeaderTitle(title.toUpperCase());
        titlePage.setColor("167AC6");
        add(titlePage, BorderLayout.NORTH);

        // Phần chính
        pnCenter = new JPanel(new BorderLayout());
        pnCenter.setBackground(Color.WHITE);
        add(pnCenter, BorderLayout.CENTER);

        // Phần thông tin đặt phòng - Sử dụng GridBagLayout
        pnInfoDatPhong = new JPanel(new GridBagLayout());
        pnInfoDatPhong.setBackground(Color.WHITE);
        pnInfoDatPhong.setBorder(new EmptyBorder(20, 20, 20, 20)); // Giảm padding trái và phải từ 40 xuống 20
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
        gbc.weightx = 1.0; // Đảm bảo ô text trải dài

        // Khởi tạo các trường nhập liệu
        txtMaDP = new InputForm("Mã Đặt Phòng");
        txtMaDP.setEditable(false);
        txtMaDP.setPreferredSize(new Dimension(450, 50)); // Tăng chiều rộng lên 450
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnInfoDatPhong.add(txtMaDP, gbc);

        txtTenKH = new InputForm("Tên Khách Hàng");
        txtTenKH.setEditable(false);
        txtTenKH.setPreferredSize(new Dimension(450, 50));
        gbc.gridx = 1;
        gbc.gridy = 0;
        pnInfoDatPhong.add(txtTenKH, gbc);

        txtNgayLapPhieu = new InputForm("Ngày Lập Phiếu");
        txtNgayLapPhieu.setEditable(false);
        txtNgayLapPhieu.setPreferredSize(new Dimension(450, 50));
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnInfoDatPhong.add(txtNgayLapPhieu, gbc);

        txtTienDatCoc = new InputForm("Tiền Đặt Cọc");
        txtTienDatCoc.setPreferredSize(new Dimension(450, 50));
        gbc.gridx = 1;
        gbc.gridy = 1;
        pnInfoDatPhong.add(txtTienDatCoc, gbc);

        cbTinhTrangXuLy = new JComboBox<>(new String[]{"Chưa xử lý", "Đã xử lý"});
        JPanel tinhTrangXuLyPanel = createComboBoxPanel("Tình Trạng Xử Lý", cbTinhTrangXuLy);
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnInfoDatPhong.add(tinhTrangXuLyPanel, gbc);

        cbXuLy = new JComboBox<>(new String[]{"Chưa xử lý", "Đã xử lý"});
        JPanel xuLyPanel = createComboBoxPanel("Xử Lý", cbXuLy);
        gbc.gridx = 1;
        gbc.gridy = 2;
        pnInfoDatPhong.add(xuLyPanel, gbc);

        pnCenter.add(pnInfoDatPhong, BorderLayout.CENTER);

        // Phần nút (căn giữa)
        pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnBottom.setBorder(new EmptyBorder(10, 0, 10, 0)); // Padding nhỏ
        pnBottom.setBackground(Color.WHITE);
        pnCenter.add(pnBottom, BorderLayout.SOUTH);

        if (isEditMode) {
            btnSave = new ButtonCustom("Lưu thông tin", "success", 14);
            btnSave.addActionListener(this);
            btnSave.setPreferredSize(new Dimension(150, 40));
            pnBottom.add(btnSave);
        }

        btnCancel = new ButtonCustom(isEditMode ? "Hủy bỏ" : "Đóng", "danger", 14);
        btnCancel.addActionListener(this);
        btnCancel.setPreferredSize(new Dimension(150, 40));
        pnBottom.add(btnCancel);
    }

    private JPanel createComboBoxPanel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setPreferredSize(new Dimension(450, 50)); // Tăng chiều rộng lên 450
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel(labelText + ":");
        Font timesNewRomanBold = Font.getFont("Times New Roman");
        if (timesNewRomanBold != null) {
            label.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 18));
        } else {
            label.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        }
        label.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        Font timesNewRomanPlain = Font.getFont("Times New Roman");
        if (timesNewRomanPlain != null) {
            comboBox.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 18));
        } else {
            comboBox.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        }
        comboBox.setPreferredSize(new Dimension(300, 40)); // Tăng chiều rộng của JComboBox lên 300
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        panel.add(comboBox, gbc);

        return panel;
    }

    private void initView() {
        txtTienDatCoc.setEditable(false);
        cbTinhTrangXuLy.setEnabled(false);
        cbXuLy.setEnabled(false);
    }

    private void setInfo(DatPhongDTO datPhong) {
        txtMaDP.setText(datPhong.getMaDP() != null ? datPhong.getMaDP() : "");
        KhachHangDTO kh = khachHangBUS.selectById(String.valueOf(datPhong.getMaKH()));
        txtTenKH.setText(kh != null ? kh.getTenKhachHang() : "N/A");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        txtNgayLapPhieu.setText(datPhong.getNgayLapPhieu() != null ? dateFormat.format(datPhong.getNgayLapPhieu()) : "");
        txtTienDatCoc.setText(datPhong.getTienDatCoc() >= 0 ? String.valueOf(datPhong.getTienDatCoc()) : "");
        cbTinhTrangXuLy.setSelectedItem(datPhong.getTinhTrangXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý");
        cbXuLy.setSelectedItem(datPhong.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý");
    }

    private DatPhongDTO getInfo() {
        String maDP = txtMaDP.getText();
        int maKH = datPhong.getMaKH();
        int tienDatCoc = txtTienDatCoc.getText().isEmpty() ? 0 : Integer.parseInt(txtTienDatCoc.getText());
        int tinhTrangXuLy = cbTinhTrangXuLy.getSelectedItem().equals("Đã xử lý") ? 1 : 0;
        int xuLy = cbXuLy.getSelectedItem().equals("Đã xử lý") ? 1 : 0;
        DatPhongDTO newDatPhong = new DatPhongDTO();
        newDatPhong.setMaDP(maDP);
        newDatPhong.setMaKH(maKH);
        newDatPhong.setNgayLapPhieu(datPhong.getNgayLapPhieu());
        newDatPhong.setTienDatCoc(tienDatCoc);
        newDatPhong.setTinhTrangXuLy(tinhTrangXuLy);
        newDatPhong.setXuLy(xuLy);
        return newDatPhong;
    }

    private boolean checkInput() {
        if (txtTienDatCoc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tiền đặt cọc không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int tienDatCoc = Integer.parseInt(txtTienDatCoc.getText());
            if (tienDatCoc < 0) {
                JOptionPane.showMessageDialog(this, "Tiền đặt cọc phải là số nguyên không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tiền đặt cọc phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            DatPhongDTO newDatPhong = getInfo();
            try {
                int result = datPhongBUS.update(newDatPhong);
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật đặt phòng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật đặt phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
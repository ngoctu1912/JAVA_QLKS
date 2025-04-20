package GUI_HOADON;

import BUS.HoaDonBUS;
import DTO.HoaDonDTO;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class HoaDonDialog extends JDialog {
    private HoaDon parent;
    private JTextField txtMaHD, txtMaCTT, txtTienP, txtTienDV, txtGiamGia, txtPhuThu, txtTongTien, txtHinhThucTT;
    private JTextField txtNgayThanhToan;
    private JComboBox<String> cbxXuLy;
    private JButton btnSave, btnCancel;
    private String type;
    private HoaDonDTO hd;
    private HoaDonBUS hdBUS = new HoaDonBUS();

    public HoaDonDialog(HoaDon parent, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.parent = parent;
        this.type = type;
        initComponents();
    }

    public HoaDonDialog(HoaDon parent, JFrame owner, String title, boolean modal, String type, HoaDonDTO hd) {
        this(parent, owner, title, modal, type);
        this.hd = hd;
        populateFields();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        formPanel.setBackground(Color.WHITE);

        Font fieldFont = new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13);

        formPanel.add(createLabel("Mã HD:"));
        txtMaHD = createTextField(fieldFont);
        formPanel.add(txtMaHD);

        formPanel.add(createLabel("Mã CTT:"));
        txtMaCTT = createTextField(fieldFont);
        formPanel.add(txtMaCTT);

        formPanel.add(createLabel("Tiền phòng:"));
        txtTienP = createTextField(fieldFont);
        formPanel.add(txtTienP);

        formPanel.add(createLabel("Tiền DV:"));
        txtTienDV = createTextField(fieldFont);
        formPanel.add(txtTienDV);

        formPanel.add(createLabel("Giảm giá:"));
        txtGiamGia = createTextField(fieldFont);
        formPanel.add(txtGiamGia);

        formPanel.add(createLabel("Phụ thu:"));
        txtPhuThu = createTextField(fieldFont);
        formPanel.add(txtPhuThu);

        formPanel.add(createLabel("Tổng tiền:"));
        txtTongTien = createTextField(fieldFont);
        formPanel.add(txtTongTien);

        formPanel.add(createLabel("Ngày thanh toán:"));
        txtNgayThanhToan = createTextField(fieldFont);
        txtNgayThanhToan.setToolTipText("dd/MM/yyyy HH:mm");
        formPanel.add(txtNgayThanhToan);

        formPanel.add(createLabel("Hình thức TT:"));
        txtHinhThucTT = createTextField(fieldFont);
        formPanel.add(txtHinhThucTT);

        formPanel.add(createLabel("Xử lý:"));
        cbxXuLy = new JComboBox<>(new String[]{"Đã xử lý", "Chưa xử lý"});
        cbxXuLy.setFont(fieldFont);
        formPanel.add(cbxXuLy);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        btnSave = new JButton("Lưu");
        btnSave.setFont(fieldFont);
        btnSave.addActionListener(e -> saveHoaDon());
        btnCancel = new JButton("Hủy");
        btnCancel.setFont(fieldFont);
        btnCancel.addActionListener(e -> dispose());
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        if (type.equals("view")) {
            setFieldsEditable(false);
            btnSave.setVisible(false);
        }

        setSize(450, 450);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(Color.WHITE);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        return label;
    }

    private JTextField createTextField(Font font) {
        JTextField field = new JTextField();
        field.setFont(font);
        return field;
    }

    private void populateFields() {
        if (hd != null) {
            txtMaHD.setText(hd.getMaHD() != null ? hd.getMaHD() : "");
            txtMaCTT.setText(hd.getMaCTT() != null ? hd.getMaCTT() : "");
            txtTienP.setText(String.valueOf(hd.getTienP()));
            txtTienDV.setText(String.valueOf(hd.getTienDV()));
            txtGiamGia.setText(String.valueOf(hd.getGiamGia()));
            txtPhuThu.setText(String.valueOf(hd.getPhuThu()));
            txtTongTien.setText(String.valueOf(hd.getTongTien()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            txtNgayThanhToan.setText(hd.getNgayThanhToan() != null ? sdf.format(hd.getNgayThanhToan()) : "");
            txtHinhThucTT.setText(hd.getHinhThucThanhToan() != null ? hd.getHinhThucThanhToan() : "");
            cbxXuLy.setSelectedIndex(hd.getXuLy() == 1 ? 0 : 1);
        } else {
            txtTienP.setText("0");
            txtTienDV.setText("0");
            txtGiamGia.setText("0");
            txtPhuThu.setText("0");
            txtTongTien.setText("0");
            txtHinhThucTT.setText("Tiền mặt");
            cbxXuLy.setSelectedIndex(1); // Chưa xử lý
        }
    }

    private void setFieldsEditable(boolean editable) {
        txtMaHD.setEditable(editable);
        txtMaCTT.setEditable(editable);
        txtTienP.setEditable(editable);
        txtTienDV.setEditable(editable);
        txtGiamGia.setEditable(editable);
        txtPhuThu.setEditable(editable);
        txtTongTien.setEditable(editable);
        txtNgayThanhToan.setEditable(editable);
        txtHinhThucTT.setEditable(editable);
        cbxXuLy.setEnabled(editable);
    }

    private void saveHoaDon() {
        try {
            // Validate inputs
            if (txtMaHD.getText().trim().isEmpty() || txtMaCTT.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã HD và Mã CTT không được để trống");
                return;
            }

            if (txtHinhThucTT.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hình thức thanh toán không được để trống");
                return;
            }

            // Validate MaCTT existence (giả sử có CTThueDAO)
            /*
            if (!CTThueDAO.getInstance().exists(txtMaCTT.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Mã CTT không tồn tại trong hệ thống");
                return;
            }
            */

            HoaDonDTO newHd = new HoaDonDTO();
            newHd.setMaHD(txtMaHD.getText().trim());
            newHd.setMaCTT(txtMaCTT.getText().trim());

            // Validate numeric fields
            try {
                int tienP = Integer.parseInt(txtTienP.getText().trim());
                int tienDV = Integer.parseInt(txtTienDV.getText().trim());
                int giamGia = Integer.parseInt(txtGiamGia.getText().trim());
                int phuThu = Integer.parseInt(txtPhuThu.getText().trim());
                int tongTien = Integer.parseInt(txtTongTien.getText().trim());

                if (tienP < 0 || tienDV < 0 || giamGia < 0 || phuThu < 0 || tongTien < 0) {
                    JOptionPane.showMessageDialog(this, "Các trường số (Tiền phòng, Tiền DV, Giảm giá, Phụ thu, Tổng tiền) không được âm");
                    return;
                }

                newHd.setTienP(tienP);
                newHd.setTienDV(tienDV);
                newHd.setGiamGia(giamGia);
                newHd.setPhuThu(phuThu);
                newHd.setTongTien(tongTien);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Các trường số (Tiền phòng, Tiền DV, Giảm giá, Phụ thu, Tổng tiền) phải là số nguyên hợp lệ");
                return;
            }

            // Validate date
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                sdf.setLenient(false);
                String dateText = txtNgayThanhToan.getText().trim();
                if (dateText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ngày thanh toán không được để trống");
                    return;
                }
                newHd.setNgayThanhToan(sdf.parse(dateText));
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Ngày thanh toán phải có định dạng dd/MM/yyyy HH:mm");
                return;
            }

            newHd.setHinhThucThanhToan(txtHinhThucTT.getText().trim());
            newHd.setXuLy(cbxXuLy.getSelectedIndex() == 0 ? 1 : 0);

            // Perform database operation
            int result = 0;
            if (type.equals("create")) {
                result = hdBUS.add(newHd);
            } else if (type.equals("update")) {
                result = hdBUS.update(newHd);
            }

            if (result > 0) {
                parent.listHD = hdBUS.getAll();
                parent.loadDataTable(parent.listHD);
                JOptionPane.showMessageDialog(this, "Lưu hóa đơn thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lưu hóa đơn thất bại. Vui lòng kiểm tra lại dữ liệu hoặc kết nối cơ sở dữ liệu.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
package GUI_DICHVU;

import DTO.DichVuDTO;
import Component.ButtonCustom;
import Component.HeaderTitle;
import Component.InputForm;
import Component.InputImage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.nio.file.Paths;

public class DichVuDetailDialog extends JDialog {
    private static final String IMAGE_DIR = "images";
    private DichVuDTO dichVu;
    private HeaderTitle titlePage;
    private JPanel pnCenter, pnInfoDichVu, pnInfoDichVuRight;
    private InputForm txtMaDV, txtTenDV, txtLoaiDV, txtSoLuong, txtGiaDV, txtXuLy;
    private InputImage hinhAnh;

    public DichVuDetailDialog(JFrame parent, DichVuDTO dichVu) {
        super(parent, "CHI TIẾT DỊCH VỤ", true);
        this.dichVu = dichVu;
        initComponents();
        setInfo(dichVu);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void initComponents() {
        setSize(new Dimension(1200, 550));
        setLayout(new BorderLayout(0, 0));

        // Tiêu đề
        titlePage = new HeaderTitle("CHI TIẾT DỊCH VỤ");
        titlePage.setColor("167AC6");
        add(titlePage, BorderLayout.NORTH);

        // Phần chính
        pnCenter = new JPanel(new BorderLayout());
        pnCenter.setBackground(Color.WHITE);
        add(pnCenter, BorderLayout.CENTER);

        // Phần thông tin dịch vụ
        pnInfoDichVu = new JPanel(new GridLayout(3, 2, 15, 15));
        pnInfoDichVu.setBackground(Color.WHITE);
        pnInfoDichVu.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnCenter.add(pnInfoDichVu, BorderLayout.CENTER);

        // Phần hình ảnh (bên trái)
        pnInfoDichVuRight = new JPanel();
        pnInfoDichVuRight.setBackground(Color.WHITE);
        pnInfoDichVuRight.setPreferredSize(new Dimension(350, 600));
        pnInfoDichVuRight.setBorder(new EmptyBorder(0, 20, 0, 20));
        pnCenter.add(pnInfoDichVuRight, BorderLayout.WEST);

        // Khởi tạo các trường nhập liệu
        txtMaDV = new InputForm("Mã Dịch Vụ");
        txtMaDV.setEditable(false);
        txtTenDV = new InputForm("Tên Dịch Vụ");
        txtTenDV.setEditable(false);
        txtLoaiDV = new InputForm("Loại Dịch Vụ");
        txtLoaiDV.setEditable(false);
        txtSoLuong = new InputForm("Số Lượng");
        txtSoLuong.setEditable(false);
        txtGiaDV = new InputForm("Giá Dịch Vụ");
        txtGiaDV.setEditable(false);
        txtXuLy = new InputForm("Xử Lý");
        txtXuLy.setEditable(false);

        hinhAnh = new InputImage("Hình Ảnh");
        hinhAnh.setEnabled(false);

        // Thêm các trường vào panel
        pnInfoDichVu.add(txtMaDV);
        pnInfoDichVu.add(txtTenDV);
        pnInfoDichVu.add(txtLoaiDV);
        pnInfoDichVu.add(txtSoLuong);
        pnInfoDichVu.add(txtGiaDV);
        pnInfoDichVu.add(txtXuLy);
        pnInfoDichVuRight.add(hinhAnh);

        // Phần nút (căn giữa)
        JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnBottom.setBorder(new EmptyBorder(20, 0, 20, 0));
        pnBottom.setBackground(Color.WHITE);
        pnCenter.add(pnBottom, BorderLayout.SOUTH);

        ButtonCustom btnClose = new ButtonCustom("Đóng", "danger", 14);
        btnClose.addActionListener(e -> dispose());
        pnBottom.add(btnClose);
    }

    private String getImagePath(String maDV) {
        return Paths.get(IMAGE_DIR, maDV + ".jpg").toString();
    }

    private void setInfo(DichVuDTO dichVu) {
        txtMaDV.setText(dichVu.getMaDV() != null ? dichVu.getMaDV() : "");
        txtTenDV.setText(dichVu.getTenDV() != null ? dichVu.getTenDV() : "");
        txtLoaiDV.setText(dichVu.getLoaiDV() != null ? dichVu.getLoaiDV() : "");
        txtSoLuong.setText(dichVu.getSoLuong() >= 0 ? String.valueOf(dichVu.getSoLuong()) : "");
        txtGiaDV.setText(dichVu.getGiaDV() >= 0 ? String.valueOf(dichVu.getGiaDV()) : "");
        txtXuLy.setText(dichVu.getXuLy() == 0 ? "Chưa xử lý" : "Đã xử lý");
        hinhAnh.setUrl_img(getImagePath(dichVu.getMaDV()));
        System.out.println("setInfo called: maDV=" + dichVu.getMaDV() + ", tenDV=" + dichVu.getTenDV() + ", hinhAnh=" + getImagePath(dichVu.getMaDV()));
    }

    public DichVuDTO getDichVu() {
        return dichVu;
    }
}
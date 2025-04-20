package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

public class KhachHangBUS {
    private KhachHangDAO khachHangDAO;

    // public KhachHangBUS() {
    //     this.khachHangDAO = new KhachHangDAO();
    // }

    public int add(KhachHangDTO khachHang) {
        if (khachHang == null || khachHang.getTenKhachHang() == null || khachHang.getTenKhachHang().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống");
        }
        if (khachHang.getSoDienThoai() == null || !khachHang.getSoDienThoai().matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        }
        if (khachHang.getCccd() <= 0) {
            throw new IllegalArgumentException("CCCD không hợp lệ");
        }
        return khachHangDAO.add(khachHang);
    }

    public int update(KhachHangDTO khachHang) {
        if (khachHang == null || khachHang.getTenKhachHang() == null || khachHang.getTenKhachHang().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống");
        }
        if (khachHang.getSoDienThoai() == null || !khachHang.getSoDienThoai().matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        }
        if (khachHang.getCccd() <= 0) {
            throw new IllegalArgumentException("CCCD không hợp lệ");
        }
        return khachHangDAO.update(khachHang);
    }

    public KhachHangDTO selectById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống");
        }
        return khachHangDAO.selectById(id);
    }
}

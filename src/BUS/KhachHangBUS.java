package BUS;

import java.util.ArrayList;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

public class KhachHangBUS {
    private KhachHangDAO khachHangDAO;

    public KhachHangBUS() {
        this.khachHangDAO = KhachHangDAO.getInstance();
    }

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

    public ArrayList<KhachHangDTO> selectAll() {
        return khachHangDAO.selectAll();
    }

    public boolean isPhoneNumberExists(String soDienThoai, int maKhachHang) {
        ArrayList<KhachHangDTO> list = khachHangDAO.selectAll();
        for (KhachHangDTO kh : list) {
            if (kh.getSoDienThoai().equals(soDienThoai) && kh.getMaKhachHang() != maKhachHang) {
                return true;
            }
        }
        return false;
    }
}

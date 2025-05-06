// package BUS;

// import java.util.ArrayList;

// import DAO.KhachHangDAO;
// import DTO.KhachHangDTO;

// public class KhachHangBUS {
//     private KhachHangDAO khachHangDAO;

//     public KhachHangBUS() {
//         this.khachHangDAO = KhachHangDAO.getInstance();
//     }

//     public int add(KhachHangDTO khachHang) {
//         if (khachHang == null || khachHang.getTenKhachHang() == null || khachHang.getTenKhachHang().trim().isEmpty()) {
//             throw new IllegalArgumentException("Tên khách hàng không được để trống");
//         }
//         if (khachHang.getSoDienThoai() == null || !khachHang.getSoDienThoai().matches("\\d{10,11}")) {
//             throw new IllegalArgumentException("Số điện thoại không hợp lệ");
//         }
//         if (khachHang.getCccd() <= 0) {
//             throw new IllegalArgumentException("CCCD không hợp lệ");
//         }
//         return khachHangDAO.add(khachHang);
//     }

//     public int update(KhachHangDTO khachHang) {
//         if (khachHang == null || khachHang.getTenKhachHang() == null || khachHang.getTenKhachHang().trim().isEmpty()) {
//             throw new IllegalArgumentException("Tên khách hàng không được để trống");
//         }
//         if (khachHang.getSoDienThoai() == null || !khachHang.getSoDienThoai().matches("\\d{10,11}")) {
//             throw new IllegalArgumentException("Số điện thoại không hợp lệ");
//         }
//         if (khachHang.getCccd() <= 0) {
//             throw new IllegalArgumentException("CCCD không hợp lệ");
//         }
//         return khachHangDAO.update(khachHang);
//     }

//     public KhachHangDTO selectById(String id) {
//         if (id == null || id.trim().isEmpty()) {
//             throw new IllegalArgumentException("Mã khách hàng không được để trống");
//         }
//         return khachHangDAO.selectById(id);
//     }

//     public ArrayList<KhachHangDTO> selectAll() {
//         return khachHangDAO.selectAll();
//     }

//     public boolean isPhoneNumberExists(String soDienThoai, int maKhachHang) {
//         ArrayList<KhachHangDTO> list = khachHangDAO.selectAll();
//         for (KhachHangDTO kh : list) {
//             if (kh.getSoDienThoai().equals(soDienThoai) && kh.getMaKhachHang() != maKhachHang) {
//                 return true;
//             }
//         }
//         return false;
//     }
// }

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
        if (khachHang.getSoDienThoai() == null || !khachHang.getSoDienThoai().matches("\\d{8,15}")) {
            throw new IllegalArgumentException("Số điện thoại phải có 8-15 chữ số");
        }
        // Không yêu cầu Cccd bắt buộc
        if (khachHang.getCccd() <= 0) {
            khachHang.setCccd(0); // Giá trị mặc định
        }
        return khachHangDAO.add(khachHang);
    }

    public int update(KhachHangDTO khachHang) {
        if (khachHang == null || khachHang.getTenKhachHang() == null || khachHang.getTenKhachHang().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống");
        }
        if (khachHang.getSoDienThoai() == null || !khachHang.getSoDienThoai().matches("\\d{8,15}")) {
            throw new IllegalArgumentException("Số điện thoại phải có 8-15 chữ số");
        }
        // Không yêu cầu Cccd bắt buộc
        if (khachHang.getCccd() <= 0) {
            khachHang.setCccd(0); // Giá trị mặc định
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

    public KhachHangDTO findByNameAndPhone(String tenKhachHang, String soDienThoai) {
        if (tenKhachHang == null || tenKhachHang.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống");
        }
        if (soDienThoai == null || !soDienThoai.matches("\\d{8,15}")) {
            throw new IllegalArgumentException("Số điện thoại phải có 8-15 chữ số");
        }
        ArrayList<KhachHangDTO> list = khachHangDAO.selectAll();
        for (KhachHangDTO kh : list) {
            if (kh.getTenKhachHang().equalsIgnoreCase(tenKhachHang) && kh.getSoDienThoai().equals(soDienThoai)) {
                return kh;
            }
        }
        return null;
    }
}
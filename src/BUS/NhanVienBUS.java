// package BUS;

// import DTO.NhanVienDTO;

// public class NhanVienBUS {

//     public NhanVienDTO getNhanVienByUsername(String currentUser) {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'getNhanVienByUsername'");
//     }
    
// }
package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import java.util.ArrayList;

public class NhanVienBUS {
    private NhanVienDAO nhanVienDAO;

    public NhanVienBUS() {
        this.nhanVienDAO = NhanVienDAO.getInstance(); // Use singleton pattern
    }

    public NhanVienDTO getNhanVienByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống");
        }
        return nhanVienDAO.selectNhanVienByUsername(username);
    }

    // New method to get array of active employee names
    public String[] getArrTenNhanVien() {
        ArrayList<NhanVienDTO> nhanVienList = nhanVienDAO.selectAllActive();
        if (nhanVienList == null || nhanVienList.isEmpty()) {
            return new String[0]; // Return empty array if no active employees
        }
        String[] tenNhanVien = new String[nhanVienList.size()];
        for (int i = 0; i < nhanVienList.size(); i++) {
            tenNhanVien[i] = nhanVienList.get(i).getHoTen();
        }
        return tenNhanVien;
    }

    // New method to get NhanVienDTO by index (used in filter)
    public NhanVienDTO getByIndex(int index) {
        ArrayList<NhanVienDTO> nhanVienList = nhanVienDAO.selectAllActive();
        if (nhanVienList == null || index < 0 || index >= nhanVienList.size()) {
            return null;
        }
        return nhanVienList.get(index);
    }
}
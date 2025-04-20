package BUS;

import DAO.NhomQuyenDAO;
import DAO.TaiKhoanDAO;
import DAO.TaiKhoanKHDAO;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;
import DTO.TaiKhoanKHDTO;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;

public class TaiKhoanBUS {
    private ArrayList<TaiKhoanDTO> listTaiKhoan;
    private ArrayList<TaiKhoanKHDTO> listTaiKhoanKH;
    private NhomQuyenDAO nhomQuyenDAO = NhomQuyenDAO.getInstance();
    
    public TaiKhoanBUS() {
        this.listTaiKhoan = TaiKhoanDAO.getInstance().selectAll();
        this.listTaiKhoanKH = TaiKhoanKHDAO.getInstance().selectAll();
    }
    
    public ArrayList<TaiKhoanDTO> getTaiKhoanAll() {
        this.listTaiKhoan = TaiKhoanDAO.getInstance().selectAll();
        return listTaiKhoan;
    }

    public ArrayList<TaiKhoanKHDTO> getTaiKhoanAllKH() {
        this.listTaiKhoanKH = TaiKhoanKHDAO.getInstance().selectAll();
        return listTaiKhoanKH;
    }
    
    public TaiKhoanDTO getTaiKhoan(int index) {
        return listTaiKhoan.get(index);
    }
    
    public int getTaiKhoanByMaNV(int manv) {
        for (int i = 0; i < this.listTaiKhoan.size(); i++) {
            if (listTaiKhoan.get(i).getMaNV() == manv) {
                return i;
            }
        }
        return -1;
    }

    public int getTaiKhoanByMaKH(int makh) {
        for (int i = 0; i < this.listTaiKhoanKH.size(); i++) {
            if (listTaiKhoanKH.get(i).getMaKhachHang() == makh) {
                return i;
            }
        }
        return -1;
    }
    
    public NhomQuyenDTO getNhomQuyenDTO(int manhom) {
        return nhomQuyenDAO.selectById(String.valueOf(manhom));
    }
    
    public boolean addAccKH(TaiKhoanKHDTO tk) {
        if (tk == null || TaiKhoanKHDAO.getInstance().selectById(tk.getTenDangNhap()) != null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12));
        tk.setMatKhau(hashedPassword);
        int result = TaiKhoanKHDAO.getInstance().add(tk);
        if (result > 0) {
            listTaiKhoanKH.add(tk);
            return true;
        }
        return false;
    }

    public boolean updateAcc(TaiKhoanDTO tk) {
        if (tk == null || !TaiKhoanDAO.getInstance().isAccountExists(tk.getMaNV())) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12));
        tk.setMatKhau(hashedPassword);
        int result = TaiKhoanDAO.getInstance().update(tk);
        if (result > 0) {
            int index = getTaiKhoanByMaNV(tk.getMaNV());
            if (index != -1) {
                listTaiKhoan.set(index, tk);
            }
            return true;
        }
        return false;
    }

    public boolean updateAccKH(TaiKhoanKHDTO tk) {
        if (tk == null || TaiKhoanKHDAO.getInstance().selectById(tk.getTenDangNhap()) == null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12));
        tk.setMatKhau(hashedPassword);
        int result = TaiKhoanKHDAO.getInstance().update(tk);
        if (result > 0) {
            int index = getTaiKhoanByMaKH(tk.getMaKhachHang());
            if (index != -1) {
                listTaiKhoanKH.set(index, tk);
            }
            return true;
        }
        return false;
    }

    public boolean checkTDN(String TDN) {
        TaiKhoanDTO tk = TaiKhoanDAO.getInstance().selectByUser(TDN);
        if (tk != null) return false;
        TaiKhoanKHDTO tkKH = TaiKhoanKHDAO.getInstance().selectByUser(TDN);
        if (tkKH != null) return false;
        return true;
    }
    
    public boolean deleteAcc(int manv) {
        if (!TaiKhoanDAO.getInstance().isAccountExists(manv)) {
            return false;
        }
        int result = TaiKhoanDAO.getInstance().delete(String.valueOf(manv));
        if (result > 0) {
            int index = getTaiKhoanByMaNV(manv);
            if (index != -1) {
                listTaiKhoan.remove(index);
            }
            return true;
        }
        return false;
    }

    public boolean deleteAccKH(int makh) {
        TaiKhoanKHDTO tk = TaiKhoanKHDAO.getInstance().selectById(String.valueOf(makh));
        if (tk == null) {
            return false;
        }
        int result = TaiKhoanKHDAO.getInstance().delete(tk.getTenDangNhap());
        if (result > 0) {
            int index = getTaiKhoanByMaKH(makh);
            if (index != -1) {
                listTaiKhoanKH.remove(index);
            }
            return true;
        }
        return false;
    }

    public ArrayList<TaiKhoanDTO> search(String txt, String type) {
        ArrayList<TaiKhoanDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
        switch (type) {
            case "Tất cả":
                for (TaiKhoanDTO i : listTaiKhoan) {
                    if (String.valueOf(i.getMaNV()).contains(txt) || i.getTenDangNhap().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
                break;
            case "Mã nhân viên":
                for (TaiKhoanDTO i : listTaiKhoan) {
                    if (String.valueOf(i.getMaNV()).contains(txt)) {
                        result.add(i);
                    }
                }
                break;
            case "Username":
                for (TaiKhoanDTO i : listTaiKhoan) {
                    if (i.getTenDangNhap().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
                break;
        }
        return result;
    }

    public ArrayList<TaiKhoanKHDTO> searchKH(String txt, String type) {
        ArrayList<TaiKhoanKHDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
        switch (type) {
            case "Tất cả":
                for (TaiKhoanKHDTO i : listTaiKhoanKH) {
                    if (String.valueOf(i.getMaKhachHang()).contains(txt) || i.getTenDangNhap().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
                break;
            case "Mã khách hàng":
                for (TaiKhoanKHDTO i : listTaiKhoanKH) {
                    if (String.valueOf(i.getMaKhachHang()).contains(txt)) {
                        result.add(i);
                    }
                }
                break;
            case "Username":
                for (TaiKhoanKHDTO i : listTaiKhoanKH) {
                    if (i.getTenDangNhap().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
                break;
        }
        return result;
    }

    public int layMaKhachHangTuDong() {
        return TaiKhoanKHDAO.getInstance().getAutoIncrement();
    }

    public boolean kiemTraDangNhap(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return false;
        }
        TaiKhoanDTO tkNV = TaiKhoanDAO.getInstance().getByTenDangNhap(username);
        if (tkNV != null && BCrypt.checkpw(password, tkNV.getMatKhau()) && tkNV.getTrangThai() == 1) {
            return true;
        }
        TaiKhoanKHDTO tkKH = TaiKhoanKHDAO.getInstance().selectByUser(username);
        if (tkKH != null && BCrypt.checkpw(password, tkKH.getMatKhau()) && tkKH.getTrangThai() == 1) {
            return true;
        }
        return false;
    }

    public boolean capNhatMatKhau(int maNV, String matKhauMoi) {
        if (matKhauMoi == null || matKhauMoi.trim().isEmpty()) {
            return false;
        }
        // Mã hóa mật khẩu mới bằng BCrypt
        String hashedPassword = BCrypt.hashpw(matKhauMoi, BCrypt.gensalt(12));
        boolean result = TaiKhoanDAO.getInstance().updatePassword(maNV, hashedPassword);
        if (result) {
            // Cập nhật lại danh sách trong bộ nhớ
            int index = getTaiKhoanByMaNV(maNV);
            if (index != -1) {
                listTaiKhoan.get(index).setMatKhau(hashedPassword);
            }
        }
        return result;
    }    

}

// package BUS;

// import DAO.NhomQuyenDAO;
// import DAO.TaiKhoanDAO;
// import DAO.TaiKhoanKHDAO;
// import DAO.KhachHangDAO;
// import DTO.NhomQuyenDTO;
// import DTO.TaiKhoanDTO;
// import DTO.TaiKhoanKHDTO;
// import DTO.KhachHangDTO;
// import java.util.ArrayList;
// import java.util.Date;

// import org.mindrot.jbcrypt.BCrypt;

// public class TaiKhoanBUS {
//     private final TaiKhoanDAO taiKhoanDAO;
//     private final TaiKhoanKHDAO taiKhoanKHDAO;
//     private final NhomQuyenDAO nhomQuyenDAO;
//     private final KhachHangDAO khachHangDAO;

//     public TaiKhoanBUS() {
//         this.taiKhoanDAO = TaiKhoanDAO.getInstance();
//         this.taiKhoanKHDAO = TaiKhoanKHDAO.getInstance();
//         this.nhomQuyenDAO = NhomQuyenDAO.getInstance();
//         this.khachHangDAO = KhachHangDAO.getInstance();
//     }

//     public ArrayList<TaiKhoanDTO> getTaiKhoanAll() {
//         return taiKhoanDAO.selectAll();
//     }

//     public ArrayList<TaiKhoanKHDTO> getTaiKhoanAllKH() {
//         return taiKhoanKHDAO.selectAll();
//     }

//     public TaiKhoanDTO getTaiKhoan(int maNV) {
//         return taiKhoanDAO.selectById(String.valueOf(maNV));
//     }

//     public TaiKhoanKHDTO getTaiKhoanKH(int maKH) {
//         return taiKhoanKHDAO.selectById(String.valueOf(maKH));
//     }

//     public NhomQuyenDTO getNhomQuyenDTO(int maNhom) {
//         return nhomQuyenDAO.selectById(String.valueOf(maNhom));
//     }

//     public boolean addAccKH(TaiKhoanKHDTO tk, KhachHangDTO khachHang) {
//         if (tk == null || khachHang == null || taiKhoanKHDAO.selectByUser(tk.getTenDangNhap()) != null) {
//             return false;
//         }
//         // Thêm thông tin khách hàng trước
//         khachHang.setMaKhachHang(tk.getMaKhachHang());
//         int khResult = khachHangDAO.add(khachHang);
//         if (khResult <= 0) {
//             return false;
//         }
//         // Thêm tài khoản khách hàng
//         int tkResult = taiKhoanKHDAO.add(tk);
//         return tkResult > 0;
//     }

//     public boolean updateAcc(TaiKhoanDTO tk) {
//         if (tk == null || !taiKhoanDAO.isAccountExists(tk.getMaNV())) {
//             return false;
//         }
//         return taiKhoanDAO.update(tk) > 0;
//     }

//     public boolean updateAccKH(TaiKhoanKHDTO tk) {
//         if (tk == null || taiKhoanKHDAO.selectByUser(tk.getTenDangNhap()) == null) {
//             return false;
//         }
//         return taiKhoanKHDAO.update(tk) > 0;
//     }

//     public boolean checkTDN(String tenDangNhap) {
//         return taiKhoanDAO.selectByUser(tenDangNhap) == null && taiKhoanKHDAO.selectByUser(tenDangNhap) == null;
//     }

//     public boolean deleteAcc(int maNV) {
//         if (!taiKhoanDAO.isAccountExists(maNV)) {
//             return false;
//         }
//         return taiKhoanDAO.delete(String.valueOf(maNV)) > 0;
//     }

//     public boolean deleteAccKH(int maKH) {
//         TaiKhoanKHDTO tk = taiKhoanKHDAO.selectById(String.valueOf(maKH));
//         if (tk == null) {
//             return false;
//         }
//         return taiKhoanKHDAO.delete(tk.getTenDangNhap()) > 0;
//     }

//     public ArrayList<TaiKhoanDTO> search(String txt, String type) {
//         ArrayList<TaiKhoanDTO> result = new ArrayList<>();
//         txt = txt.toLowerCase();
//         for (TaiKhoanDTO tk : taiKhoanDAO.selectAll()) {
//             boolean match = false;
//             switch (type) {
//                 case "Tất cả":
//                     match = String.valueOf(tk.getMaNV()).contains(txt) || tk.getTenDangNhap().toLowerCase().contains(txt);
//                     break;
//                 case "Mã nhân viên":
//                     match = String.valueOf(tk.getMaNV()).contains(txt);
//                     break;
//                 case "Username":
//                     match = tk.getTenDangNhap().toLowerCase().contains(txt);
//                     break;
//             }
//             if (match) {
//                 result.add(tk);
//             }
//         }
//         return result;
//     }

//     public ArrayList<TaiKhoanKHDTO> searchKH(String txt, String type) {
//         ArrayList<TaiKhoanKHDTO> result = new ArrayList<>();
//         txt = txt.toLowerCase();
//         for (TaiKhoanKHDTO tk : taiKhoanKHDAO.selectAll()) {
//             boolean match = false;
//             switch (type) {
//                 case "Tất cả":
//                     match = String.valueOf(tk.getMaKhachHang()).contains(txt) || tk.getTenDangNhap().toLowerCase().contains(txt);
//                     break;
//                 case "Mã khách hàng":
//                     match = String.valueOf(tk.getMaKhachHang()).contains(txt);
//                     break;
//                 case "Username":
//                     match = tk.getTenDangNhap().toLowerCase().contains(txt);
//                     break;
//             }
//             if (match) {
//                 result.add(tk);
//             }
//         }
//         return result;
//     }

//     public int layMaKhachHangTuDong() {
//         return taiKhoanKHDAO.getAutoIncrement();
//     }

//     public boolean kiemTraDangNhap(String username, String password) {
//         if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
//             return false;
//         }
//         TaiKhoanDTO tkNV = taiKhoanDAO.getByTenDangNhap(username);
//         if (tkNV != null && tkNV.getTrangThai() == 1) {
//             return taiKhoanDAO.checkLogin(username, password);
//         }
//         TaiKhoanKHDTO tkKH = taiKhoanKHDAO.selectByUser(username);
//         if (tkKH != null && tkKH.getTrangThai() == 1) {
//             return BCrypt.checkpw(password, tkKH.getMatKhau());
//         }
//         return false;
//     }

//     public boolean capNhatMatKhau(int maNV, String matKhauMoi) {
//         if (matKhauMoi == null || matKhauMoi.trim().isEmpty()) {
//             return false;
//         }
//         return taiKhoanDAO.updatePassword(maNV, matKhauMoi);
//     }
// }
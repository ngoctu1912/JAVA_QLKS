// package BUS;

// import DAO.NhomQuyenDAO;
// import DAO.TaiKhoanDAO;
// import DAO.TaiKhoanKHDAO;
// import DTO.NhomQuyenDTO;
// import DTO.TaiKhoanDTO;
// import DTO.TaiKhoanKHDTO;
// import org.mindrot.jbcrypt.BCrypt;
// import java.util.ArrayList;

// public class TaiKhoanBUS {
//     private ArrayList<TaiKhoanDTO> listTaiKhoan;
//     private ArrayList<TaiKhoanKHDTO> listTaiKhoanKH;
//     private NhomQuyenDAO nhomQuyenDAO = NhomQuyenDAO.getInstance();
    
//     public TaiKhoanBUS() {
//         this.listTaiKhoan = TaiKhoanDAO.getInstance().selectAll();
//         this.listTaiKhoanKH = TaiKhoanKHDAO.getInstance().selectAll();
//     }
    
//     public ArrayList<TaiKhoanDTO> getTaiKhoanAll() {
//         this.listTaiKhoan = TaiKhoanDAO.getInstance().selectAll();
//         return listTaiKhoan;
//     }

//     public ArrayList<TaiKhoanKHDTO> getTaiKhoanAllKH() {
//         this.listTaiKhoanKH = TaiKhoanKHDAO.getInstance().selectAll();
//         return listTaiKhoanKH;
//     }
    
//     public TaiKhoanDTO getTaiKhoan(int index) {
//         return listTaiKhoan.get(index);
//     }
    
//     public int getTaiKhoanByMaNV(int manv) {
//         for (int i = 0; i < this.listTaiKhoan.size(); i++) {
//             if (listTaiKhoan.get(i).getMaNV() == manv) {
//                 return i;
//             }
//         }
//         return -1;
//     }

//     public int getTaiKhoanByMaKH(int makh) {
//         for (int i = 0; i < this.listTaiKhoanKH.size(); i++) {
//             if (listTaiKhoanKH.get(i).getMaKhachHang() == makh) {
//                 return i;
//             }
//         }
//         return -1;
//     }
    
//     public NhomQuyenDTO getNhomQuyenDTO(int manhom) {
//         return nhomQuyenDAO.selectById(String.valueOf(manhom));
//     }

//     public boolean addAcc(TaiKhoanDTO tk) {
//         if (tk == null || TaiKhoanDAO.getInstance().selectById(String.valueOf(tk.getMaNV())) != null) {
//             return false;
//         }
//         int result = TaiKhoanDAO.getInstance().add(tk);
//         if (result > 0) {
//             listTaiKhoan.add(tk);
//             return true;
//         }
//         return false;
//     }
    
//     // public boolean addAccKH(TaiKhoanKHDTO tk) {
//     //     if (tk == null || TaiKhoanKHDAO.getInstance().selectById(tk.getTenDangNhap()) != null) {
//     //         return false;
//     //     }
//     //     String hashedPassword = BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12));
//     //     tk.setMatKhau(hashedPassword);
//     //     int result = TaiKhoanKHDAO.getInstance().add(tk);
//     //     if (result > 0) {
//     //         listTaiKhoanKH.add(tk);
//     //         return true;
//     //     }
//     //     return false;
//     // }
//     public boolean addAccKH(TaiKhoanKHDTO tk) {
//         if (tk == null || TaiKhoanKHDAO.getInstance().selectById(String.valueOf(tk.getMaKhachHang())) != null) {
//             return false;
//         }
//         int result = TaiKhoanKHDAO.getInstance().add(tk);
//         if (result > 0) {
//             listTaiKhoanKH.add(tk);
//             return true;
//         }
//         return false;
//     }

//     public boolean updateAcc(TaiKhoanDTO tk) {
//         if (tk == null || !TaiKhoanDAO.getInstance().isAccountExists(tk.getMaNV())) {
//             return false;
//         }
//         int result = TaiKhoanDAO.getInstance().update(tk);
//         if (result > 0) {
//             int index = getTaiKhoanByMaNV(tk.getMaNV());
//             if (index != -1) {
//                 listTaiKhoan.set(index, tk);
//             }
//             return true;
//         }
//         return false;
//     }

//     public boolean updateAccKH(TaiKhoanKHDTO tk) {
//         if (tk == null || TaiKhoanKHDAO.getInstance().selectById(tk.getTenDangNhap()) == null) {
//             return false;
//         }
//         String hashedPassword = BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12));
//         tk.setMatKhau(hashedPassword);
//         int result = TaiKhoanKHDAO.getInstance().update(tk);
//         if (result > 0) {
//             int index = getTaiKhoanByMaKH(tk.getMaKhachHang());
//             if (index != -1) {
//                 listTaiKhoanKH.set(index, tk);
//             }
//             return true;
//         }
//         return false;
//     }

//     public boolean checkTDN(String TDN) {
//         TaiKhoanDTO tk = TaiKhoanDAO.getInstance().selectByUser(TDN);
//         if (tk != null) return false;
//         TaiKhoanKHDTO tkKH = TaiKhoanKHDAO.getInstance().selectByUser(TDN);
//         if (tkKH != null) return false;
//         return true;
//     }
    
//     public boolean deleteAcc(int manv) {
//         if (!TaiKhoanDAO.getInstance().isAccountExists(manv)) {
//             return false;
//         }
//         int result = TaiKhoanDAO.getInstance().delete(String.valueOf(manv));
//         if (result > 0) {
//             listTaiKhoan = TaiKhoanDAO.getInstance().selectAll();
//             return true;
//         }
//         return false;
//     }

//     public boolean deleteAccKH(int makh) {
//         TaiKhoanKHDTO tk = TaiKhoanKHDAO.getInstance().selectById(String.valueOf(makh));
//         if (tk == null) {
//             return false;
//         }
//         int result = TaiKhoanKHDAO.getInstance().delete(tk.getTenDangNhap());
//         if (result > 0) {
//             int index = getTaiKhoanByMaKH(makh);
//             if (index != -1) {
//                 listTaiKhoanKH.remove(index);
//             }
//             return true;
//         }
//         return false;
//     }

//     public ArrayList<TaiKhoanDTO> search(String txt, String type) {
//         ArrayList<TaiKhoanDTO> result = new ArrayList<>();
//         txt = txt.toLowerCase();
//         switch (type) {
//             case "Tất cả":
//                 for (TaiKhoanDTO i : listTaiKhoan) {
//                     if (String.valueOf(i.getMaNV()).contains(txt) || i.getTenDangNhap().toLowerCase().contains(txt)) {
//                         result.add(i);
//                     }
//                 }
//                 break;
//             case "Mã nhân viên":
//                 for (TaiKhoanDTO i : listTaiKhoan) {
//                     if (String.valueOf(i.getMaNV()).contains(txt)) {
//                         result.add(i);
//                     }
//                 }
//                 break;
//             case "Username":
//                 for (TaiKhoanDTO i : listTaiKhoan) {
//                     if (i.getTenDangNhap().toLowerCase().contains(txt)) {
//                         result.add(i);
//                     }
//                 }
//                 break;
//         }
//         return result;
//     }

//     public ArrayList<TaiKhoanKHDTO> searchKH(String txt, String type) {
//         ArrayList<TaiKhoanKHDTO> result = new ArrayList<>();
//         txt = txt.toLowerCase();
//         switch (type) {
//             case "Tất cả":
//                 for (TaiKhoanKHDTO i : listTaiKhoanKH) {
//                     if (String.valueOf(i.getMaKhachHang()).contains(txt) || i.getTenDangNhap().toLowerCase().contains(txt)) {
//                         result.add(i);
//                     }
//                 }
//                 break;
//             case "Mã khách hàng":
//                 for (TaiKhoanKHDTO i : listTaiKhoanKH) {
//                     if (String.valueOf(i.getMaKhachHang()).contains(txt)) {
//                         result.add(i);
//                     }
//                 }
//                 break;
//             case "Username":
//                 for (TaiKhoanKHDTO i : listTaiKhoanKH) {
//                     if (i.getTenDangNhap().toLowerCase().contains(txt)) {
//                         result.add(i);
//                     }
//                 }
//                 break;
//         }
//         return result;
//     }

//     public int layMaKhachHangTuDong() {
//         return TaiKhoanKHDAO.getInstance().getAutoIncrement();
//     }

//     public boolean kiemTraDangNhap(String username, String password) {
//         if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
//             System.out.println("Login failed: Username or password is empty");
//             return false;
//         }
//         TaiKhoanDTO tkNV = TaiKhoanDAO.getInstance().getByTenDangNhap(username);
//         if (tkNV != null && tkNV.getTrangThai() == 1) {
//             boolean passwordMatch = BCrypt.checkpw(password, tkNV.getMatKhau());
//             System.out.println("Login check for NV " + username + ": Password match = " + passwordMatch);
//             return passwordMatch;
//         }
//         TaiKhoanKHDTO tkKH = TaiKhoanKHDAO.getInstance().selectByUser(username);
//         if (tkKH != null && tkKH.getTrangThai() == 1) {
//             boolean passwordMatch = BCrypt.checkpw(password, tkKH.getMatKhau());
//             System.out.println("Login check for KH " + username + ": Password match = " + passwordMatch);
//             return passwordMatch;
//         }
//         System.out.println("Login failed for username: " + username + " - Account not found or not active");
//         return false;
//     }

//     public boolean capNhatMatKhau(int maNV, String matKhauMoi) {
//         if (matKhauMoi == null || matKhauMoi.trim().isEmpty()) {
//             return false;
//         }
//         String hashedPassword = BCrypt.hashpw(matKhauMoi, BCrypt.gensalt(12));
//         boolean result = TaiKhoanDAO.getInstance().updatePassword(maNV, hashedPassword);
//         if (result) {
//             int index = getTaiKhoanByMaNV(maNV);
//             if (index != -1) {
//                 listTaiKhoan.get(index).setMatKhau(hashedPassword);
//             }
//         }
//         return result;
//     }
// }

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

    public boolean addAcc(TaiKhoanDTO tk) {
        if (tk == null || TaiKhoanDAO.getInstance().selectById(String.valueOf(tk.getMaNV())) != null) {
            return false;
        }
        int result = TaiKhoanDAO.getInstance().add(tk);
        if (result > 0) {
            listTaiKhoan.add(tk);
            return true;
        }
        return false;
    }
    
    public boolean addAccKH(TaiKhoanKHDTO tk) {
        if (tk == null || TaiKhoanKHDAO.getInstance().selectById(String.valueOf(tk.getMaKhachHang())) != null) {
            return false;
        }
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
        if (tk == null || TaiKhoanKHDAO.getInstance().selectById(String.valueOf(tk.getMaKhachHang())) == null) {
            return false;
        }
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
            listTaiKhoan = TaiKhoanDAO.getInstance().selectAll();
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
            System.out.println("Login failed: Username or password is empty");
            return false;
        }
        TaiKhoanDTO tkNV = TaiKhoanDAO.getInstance().getByTenDangNhap(username);
        if (tkNV != null && tkNV.getTrangThai() == 1) {
            boolean passwordMatch = BCrypt.checkpw(password, tkNV.getMatKhau());
            System.out.println("Login check for NV " + username + ": Password match = " + passwordMatch);
            return passwordMatch;
        }
        TaiKhoanKHDTO tkKH = TaiKhoanKHDAO.getInstance().selectByUser(username);
        if (tkKH != null && tkKH.getTrangThai() == 1) {
            boolean passwordMatch = BCrypt.checkpw(password, tkKH.getMatKhau());
            System.out.println("Login check for KH " + username + ": Password match = " + passwordMatch);
            return passwordMatch;
        }
        System.out.println("Login failed for username: " + username + " - Account not found or not active");
        return false;
    }

    public boolean capNhatMatKhau(int maNV, String matKhauMoi) {
        if (matKhauMoi == null || matKhauMoi.trim().isEmpty()) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(matKhauMoi, BCrypt.gensalt(12));
        boolean result = TaiKhoanDAO.getInstance().updatePassword(maNV, hashedPassword);
        if (result) {
            int index = getTaiKhoanByMaNV(maNV);
            if (index != -1) {
                listTaiKhoan.get(index).setMatKhau(hashedPassword);
            }
        }
        return result;
    }
}
package DTO;

import java.util.Objects;

public class TaiKhoanKHDTO {
    private int maKhachHang;
    private String matKhau;
    private String tenDangNhap;
    private int maNhomQuyen;
    private int trangThai;

    public TaiKhoanKHDTO() {
    }

    public TaiKhoanKHDTO(int maKhachHang, String matKhau, String tenDangNhap, int maNhomQuyen, int trangThai) {
        this.maKhachHang = maKhachHang;
        this.matKhau = matKhau;
        this.tenDangNhap = tenDangNhap;
        this.maNhomQuyen = maNhomQuyen;
        this.trangThai = trangThai;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public int getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(int maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKhachHang, tenDangNhap, matKhau, maNhomQuyen, trangThai);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TaiKhoanKHDTO other = (TaiKhoanKHDTO) obj;
        return maKhachHang == other.maKhachHang &&
                maNhomQuyen == other.maNhomQuyen &&
                trangThai == other.trangThai &&
                Objects.equals(tenDangNhap, other.tenDangNhap) &&
                Objects.equals(matKhau, other.matKhau);
    }

    @Override
    public String toString() {
        return "TaiKhoanKHDTO{" +
                "maKhachHang=" + maKhachHang +
                ", matKhau='" + matKhau + '\'' +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                ", maNhomQuyen=" + maNhomQuyen +
                ", trangThai=" + trangThai +
                '}';
    }
}

// package DTO;

// import java.util.Objects;

// public class TaiKhoanKHDTO {
//     private int maKhachHang;
//     private String matKhau;
//     private String tenDangNhap;
//     private int maNhomQuyen;
//     private int trangThai;

//     // Constructor mặc định
//     public TaiKhoanKHDTO() {
//     }

//     // Constructor đầy đủ
//     public TaiKhoanKHDTO(int maKhachHang, String matKhau, String tenDangNhap, int maNhomQuyen, int trangThai) {
//         setMaKhachHang(maKhachHang);
//         setMatKhau(matKhau);
//         setTenDangNhap(tenDangNhap);
//         setMaNhomQuyen(maNhomQuyen);
//         setTrangThai(trangThai);
//     }

//     // Getters and Setters
//     public int getMaKhachHang() {
//         return maKhachHang;
//     }

//     public void setMaKhachHang(int maKhachHang) {
//         if (maKhachHang <= 0) {
//             throw new IllegalArgumentException("Mã khách hàng phải lớn hơn 0");
//         }
//         this.maKhachHang = maKhachHang;
//     }

//     public String getMatKhau() {
//         return matKhau;
//     }

//     public void setMatKhau(String matKhau) {
//         if (matKhau == null || matKhau.length() < 6) {
//             throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự");
//         }
//         this.matKhau = matKhau;
//     }

//     public String getTenDangNhap() {
//         return tenDangNhap;
//     }

//     public void setTenDangNhap(String tenDangNhap) {
//         if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
//             throw new IllegalArgumentException("Tên đăng nhập không được rỗng");
//         }
//         this.tenDangNhap = tenDangNhap;
//     }

//     public int getMaNhomQuyen() {
//         return maNhomQuyen;
//     }

//     public void setMaNhomQuyen(int maNhomQuyen) {
//         if (maNhomQuyen <= 0) {
//             throw new IllegalArgumentException("Mã nhóm quyền phải lớn hơn 0");
//         }
//         this.maNhomQuyen = maNhomQuyen;
//     }

//     public int getTrangThai() {
//         return trangThai;
//     }

//     public void setTrangThai(int trangThai) {
//         if (trangThai != 0 && trangThai != 1) {
//             throw new IllegalArgumentException("Trạng thái chỉ được là 0 (khóa) hoặc 1 (hoạt động)");
//         }
//         this.trangThai = trangThai;
//     }

//     @Override
//     public int hashCode() {
//         return Objects.hash(maKhachHang, tenDangNhap, matKhau, maNhomQuyen, trangThai);
//     }

//     @Override
//     public boolean equals(Object obj) {
//         if (this == obj) return true;
//         if (obj == null || getClass() != obj.getClass()) return false;
//         TaiKhoanKHDTO other = (TaiKhoanKHDTO) obj;
//         return maKhachHang == other.maKhachHang &&
//                 maNhomQuyen == other.maNhomQuyen &&
//                 trangThai == other.trangThai &&
//                 Objects.equals(tenDangNhap, other.tenDangNhap) &&
//                 Objects.equals(matKhau, other.matKhau);
//     }

//     @Override
//     public String toString() {
//         return "TaiKhoanKHDTO{" +
//                 "maKhachHang=" + maKhachHang +
//                 ", matKhau='[HIDDEN]'" +
//                 ", tenDangNhap='" + tenDangNhap + '\'' +
//                 ", maNhomQuyen=" + maNhomQuyen +
//                 ", trangThai=" + trangThai +
//                 '}';
//     }
// }
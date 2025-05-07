package DTO;

import java.util.Objects;

public class TaiKhoanDTO {
    private int maNV;
    private String tenDangNhap;
    private String matKhau;
    private int maNhomQuyen;
    private int trangThai;

    // Constructor mặc định
    public TaiKhoanDTO() {
    }

    // Constructor đầy đủ
    public TaiKhoanDTO(int maNV, String tenDangNhap, String matKhau, int maNhomQuyen, int trangThai) {
        setMaNV(maNV);
        setTenDangNhap(tenDangNhap);
        setMatKhau(matKhau);
        setMaNhomQuyen(maNhomQuyen);
        setTrangThai(trangThai);
    }

    // Getters and Setters
    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        if (maNV <= 0) {
            throw new IllegalArgumentException("Mã nhân viên phải lớn hơn 0");
        }
        this.maNV = maNV;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được rỗng");
        }
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        if (matKhau == null || matKhau.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự");
        }
        this.matKhau = matKhau;
    }

    public int getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(int maNhomQuyen) {
        if (maNhomQuyen <= 0) {
            throw new IllegalArgumentException("Mã nhóm quyền phải lớn hơn 0");
        }
        this.maNhomQuyen = maNhomQuyen;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        if (trangThai != 0 && trangThai != 1) {
            throw new IllegalArgumentException("Trạng thái chỉ được là 0 (khóa) hoặc 1 (hoạt động)");
        }
        this.trangThai = trangThai;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNV, tenDangNhap, matKhau, maNhomQuyen, trangThai);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TaiKhoanDTO other = (TaiKhoanDTO) obj;
        return maNV == other.maNV &&
                maNhomQuyen == other.maNhomQuyen &&
                trangThai == other.trangThai &&
                Objects.equals(tenDangNhap, other.tenDangNhap) &&
                Objects.equals(matKhau, other.matKhau);
    }

    @Override
    public String toString() {
        return "TaiKhoanDTO{" +
                "maNV=" + maNV +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                ", matKhau='[HIDDEN]'" +
                ", maNhomQuyen=" + maNhomQuyen +
                ", trangThai=" + trangThai +
                '}';
    }
}
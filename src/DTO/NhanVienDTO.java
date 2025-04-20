package DTO;

import java.util.Date;
import java.util.Objects;

public class NhanVienDTO {
    private int maNhanVien;
    private String hoTen;
    private int gioiTinh;
    private Date ngaySinh;
    private String soDienThoai;
    private String email;
    private int trangThai;
    private int soNgayPhep;
    private Date ngayVaoLam;
    private int luongNgay;

    public NhanVienDTO() {
    }

    public NhanVienDTO(int maNhanVien, String hoTen, int gioiTinh, Date ngaySinh,
            String soDienThoai, String email, int trangThai,
            int soNgayPhep, Date ngayVaoLam, int luongNgay) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.trangThai = trangThai;
        this.soNgayPhep = soNgayPhep;
        this.ngayVaoLam = ngayVaoLam;
        this.luongNgay = luongNgay;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getSoNgayPhep() {
        return soNgayPhep;
    }

    public void setSoNgayPhep(int soNgayPhep) {
        this.soNgayPhep = soNgayPhep;
    }

    public Date getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public int getLuongNgay() {
        return luongNgay;
    }

    public void setLuongNgay(int luongNgay) {
        this.luongNgay = luongNgay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNhanVien, hoTen, gioiTinh, ngaySinh, soDienThoai,
                email, trangThai, soNgayPhep, ngayVaoLam, luongNgay);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NhanVienDTO other = (NhanVienDTO) obj;
        return maNhanVien == other.maNhanVien &&
                gioiTinh == other.gioiTinh &&
                trangThai == other.trangThai &&
                soNgayPhep == other.soNgayPhep &&
                luongNgay == other.luongNgay &&
                Objects.equals(hoTen, other.hoTen) &&
                Objects.equals(ngaySinh, other.ngaySinh) &&
                Objects.equals(soDienThoai, other.soDienThoai) &&
                Objects.equals(email, other.email) &&
                Objects.equals(ngayVaoLam, other.ngayVaoLam);
    }

    @Override
    public String toString() {
        return "NhanVienDTO{" +
                "maNhanVien=" + maNhanVien +
                ", hoTen='" + hoTen + '\'' +
                ", gioiTinh=" + gioiTinh +
                ", ngaySinh=" + ngaySinh +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", email='" + email + '\'' +
                ", trangThai=" + trangThai +
                ", soNgayPhep=" + soNgayPhep +
                ", ngayVaoLam=" + ngayVaoLam +
                ", luongNgay=" + luongNgay +
                '}';
    }
}
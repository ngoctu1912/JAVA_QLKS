package DTO;

import java.util.Date;

public class KhachHangDTO {
    private int maKhachHang;
    private String tenKhachHang;
    private int gioiTinh; // 0: Nữ, 1: Nam
    private long cccd;
    private String diaChi;
    private String soDienThoai;
    private String email;
    private int trangThai;
    private Date ngaySinh;

    // Constructor mặc định
    public KhachHangDTO() {
    }

    // Constructor đầy đủ
    public KhachHangDTO(int maKhachHang, String tenKhachHang, int gioiTinh, long cccd, String diaChi,
            String soDienThoai, String email, int trangThai, Date ngaySinh) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.gioiTinh = gioiTinh;
        this.cccd = cccd;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.trangThai = trangThai;
        this.ngaySinh = ngaySinh;
    }

    // Getters và Setters
    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public long getCccd() {
        return cccd;
    }

    public void setCccd(long cccd) {
        this.cccd = cccd;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + maKhachHang;
        result = prime * result + ((tenKhachHang == null) ? 0 : tenKhachHang.hashCode());
        result = prime * result + gioiTinh;
        result = prime * result + (int) (cccd ^ (cccd >>> 32));
        result = prime * result + ((diaChi == null) ? 0 : diaChi.hashCode());
        result = prime * result + ((soDienThoai == null) ? 0 : soDienThoai.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + trangThai;
        result = prime * result + ((ngaySinh == null) ? 0 : ngaySinh.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KhachHangDTO other = (KhachHangDTO) obj;
        if (maKhachHang != other.maKhachHang)
            return false;
        if (tenKhachHang == null) {
            if (other.tenKhachHang != null)
                return false;
        } else if (!tenKhachHang.equals(other.tenKhachHang))
            return false;
        if (gioiTinh != other.gioiTinh)
            return false;
        if (cccd != other.cccd)
            return false;
        if (diaChi == null) {
            if (other.diaChi != null)
                return false;
        } else if (!diaChi.equals(other.diaChi))
            return false;
        if (soDienThoai == null) {
            if (other.soDienThoai != null)
                return false;
        } else if (!soDienThoai.equals(other.soDienThoai))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (trangThai != other.trangThai)
            return false;
        if (ngaySinh == null) {
            if (other.ngaySinh != null)
                return false;
        } else if (!ngaySinh.equals(other.ngaySinh))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "KhachHangDTO [maKhachHang=" + maKhachHang + ", tenKhachHang=" + tenKhachHang + ", gioiTinh=" + gioiTinh
                + ", cccd=" + cccd + ", diaChi=" + diaChi + ", soDienThoai=" + soDienThoai + ", email=" + email
                + ", trangThai=" + trangThai + ", ngaySinh=" + ngaySinh + "]";
    }
}


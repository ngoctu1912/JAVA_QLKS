package DTO;

public class KhachHangThongKeDTO {
    private int maKH; // Thay đổi kiểu dữ liệu thành int để khớp với cột MKH trong bảng KHACHHANG
    private String tenKH;
    private int soLanDatPhong;
    private long tongTien;

    public KhachHangThongKeDTO(int maKH, String tenKH, int soLanDatPhong, long tongTien) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.soLanDatPhong = soLanDatPhong;
        this.tongTien = tongTien;
    }

    // Getters và setters
    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public int getsoLanDatPhong() {
        return soLanDatPhong;
    }

    public void setsoLanDatPhong(int soLanDatPhong) {
        this.soLanDatPhong = soLanDatPhong;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }
}
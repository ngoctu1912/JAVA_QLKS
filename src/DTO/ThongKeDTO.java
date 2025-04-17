package DTO;

import java.time.LocalDate;

public class ThongKeDTO {
    private String loaiPhong;
    private long doanhThuPhong;
    private long doanhThuDichVu;
    private long tongDoanhThu;
    private int soLanDat;
    private LocalDate ngay; // Thêm thuộc tính này

    // Constructor mặc định
    public ThongKeDTO() {}

    // Constructor cho thống kê theo loại phòng
    public ThongKeDTO(String loaiPhong, long doanhThuPhong, long doanhThuDichVu, long tongDoanhThu, int soLanDat) {
        this.loaiPhong = loaiPhong;
        this.doanhThuPhong = doanhThuPhong;
        this.doanhThuDichVu = doanhThuDichVu;
        this.tongDoanhThu = tongDoanhThu;
        this.soLanDat = soLanDat;
    }

    // Constructor cho thống kê theo ngày
    public ThongKeDTO(LocalDate ngay, long doanhThuPhong, long doanhThuDichVu, long tongDoanhThu) {
        this.ngay = ngay;
        this.doanhThuPhong = doanhThuPhong;
        this.doanhThuDichVu = doanhThuDichVu;
        this.tongDoanhThu = tongDoanhThu;
    }

    // Getters và Setters
    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public long getDoanhThuPhong() {
        return doanhThuPhong;
    }

    public void setDoanhThuPhong(long doanhThuPhong) {
        this.doanhThuPhong = doanhThuPhong;
    }

    public long getDoanhThuDichVu() {
        return doanhThuDichVu;
    }

    public void setDoanhThuDichVu(long doanhThuDichVu) {
        this.doanhThuDichVu = doanhThuDichVu;
    }

    public long getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(long tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public int getSoLanDat() {
        return soLanDat;
    }

    public void setSoLanDat(int soLanDat) {
        this.soLanDat = soLanDat;
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
    }
}
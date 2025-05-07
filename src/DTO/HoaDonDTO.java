package DTO;

import java.util.Date;

public class HoaDonDTO {
    private String maHD;
    private String maCTT;
    private int tienP;
    private int tienDV;
    private int giamGia;
    private int phuThu;
    private int tongTien;
    private Date ngayThanhToan;
    private String hinhThucThanhToan;
    private int xuLy;

    // Default constructor
    public HoaDonDTO() {
    }

    // Constructor for all fields
    public HoaDonDTO(String maHD, String maCTT, int tienP, int tienDV, int giamGia, int phuThu, int tongTien,
                     Date ngayThanhToan, String hinhThucThanhToan, int xuLy) {
        this.maHD = maHD;
        this.maCTT = maCTT;
        this.tienP = tienP;
        this.tienDV = tienDV;
        this.giamGia = giamGia;
        this.phuThu = phuThu;
        this.tongTien = tongTien;
        this.ngayThanhToan = ngayThanhToan;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.xuLy = xuLy;
    }

    // Getters and Setters
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaCTT() {
        return maCTT;
    }

    public void setMaCTT(String maCTT) {
        this.maCTT = maCTT;
    }

    public int getTienP() {
        return tienP;
    }

    public void setTienP(int tienP) {
        this.tienP = tienP;
    }

    public int getTienDV() {
        return tienDV;
    }

    public void setTienDV(int tienDV) {
        this.tienDV = tienDV;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

    public int getPhuThu() {
        return phuThu;
    }

    public void setPhuThu(int phuThu) {
        this.phuThu = phuThu;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public int getXuLy() {
        return xuLy;
    }

    public void setXuLy(int xuLy) {
        this.xuLy = xuLy;
    }
}
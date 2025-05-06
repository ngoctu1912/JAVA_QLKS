package DTO;

import java.util.Date;

public class ChiTietDatPhongDTO {
    private String maCTDP;
    private String maP;
    private Date ngayThue;
    private Date ngayTra;
    private Date ngayCheckOut;
    private int loaiHinhThue;
    private int giaThue;
    private int tinhTrang;

    // Constructor mặc định
    public ChiTietDatPhongDTO() {
    }

    // Constructor đầy đủ
    public ChiTietDatPhongDTO(String maCTDP, String maP, Date ngayThue, Date ngayTra, 
                              Date ngayCheckOut, int loaiHinhThue, int giaThue, int tinhTrang) {
        this.maCTDP = maCTDP;
        this.maP = maP;
        this.ngayThue = ngayThue;
        this.ngayTra = ngayTra;
        this.ngayCheckOut = ngayCheckOut;
        this.loaiHinhThue = loaiHinhThue;
        this.giaThue = giaThue;
        this.tinhTrang = tinhTrang;
    }

    // Getter và Setter
    public String getMaCTDP() {
        return maCTDP;
    }

    public void setMaCTDP(String maCTDP) {
        this.maCTDP = maCTDP;
    }

    public String getMaP() {
        return maP;
    }

    public void setMaP(String maP) {
        this.maP = maP;
    }

    public Date getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(Date ngayThue) {
        this.ngayThue = ngayThue;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public Date getNgayCheckOut() {
        return ngayCheckOut;
    }

    public void setNgayCheckOut(Date ngayCheckOut) {
        this.ngayCheckOut = ngayCheckOut;
    }

    public int getLoaiHinhThue() {
        return loaiHinhThue;
    }

    public void setLoaiHinhThue(int loaiHinhThue) {
        this.loaiHinhThue = loaiHinhThue;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
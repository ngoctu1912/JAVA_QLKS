// package DTO;

// import java.util.Date;

// public class DatPhongDTO {
//     private String maDP;
//     private int maKH;
//     private Date ngayDat;
//     private Date ngayTra;

//     // Getters and Setters
//     public String getMaDP() {
//         return maDP;
//     }

//     public void setMaDP(String maDP) {
//         this.maDP = maDP;
//     }

//     public int getMaKH() {
//         return maKH;
//     }

//     public void setMaKH(int maKH) {
//         this.maKH = maKH;
//     }

//     public Date getNgayDat() {
//         return ngayDat;
//     }

//     public void setNgayDat(Date ngayDat) {
//         this.ngayDat = ngayDat;
//     }

//     public Date getNgayTra() {
//         return ngayTra;
//     }

//     public void setNgayTra(Date ngayTra) {
//         this.ngayTra = ngayTra;
//     }
// }

package DTO;

import java.util.Date;

public class DatPhongDTO {
    private String maDP;
    private int maKH;
    private Date ngayLapPhieu;
    private int tienDatCoc;
    private int tinhTrangXuLy;
    private int xuLy;

    public DatPhongDTO() {}

    public DatPhongDTO(String maDP, int maKH, Date ngayLapPhieu, int tienDatCoc, int tinhTrangXuLy, int xuLy) {
        this.maDP = maDP;
        this.maKH = maKH;
        this.ngayLapPhieu = ngayLapPhieu;
        this.tienDatCoc = tienDatCoc;
        this.tinhTrangXuLy = tinhTrangXuLy;
        this.xuLy = xuLy;
    }

    public String getMaDP() {
        return maDP;
    }

    public void setMaDP(String maDP) {
        this.maDP = maDP;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public Date getNgayLapPhieu() {
        return ngayLapPhieu;
    }

    public void setNgayLapPhieu(Date ngayLapPhieu) {
        this.ngayLapPhieu = ngayLapPhieu;
    }

    public int getTienDatCoc() {
        return tienDatCoc;
    }

    public void setTienDatCoc(int tienDatCoc) {
        this.tienDatCoc = tienDatCoc;
    }

    public int getTinhTrangXuLy() {
        return tinhTrangXuLy;
    }

    public void setTinhTrangXuLy(int tinhTrangXuLy) {
        this.tinhTrangXuLy = tinhTrangXuLy;
    }

    public int getXuLy() {
        return xuLy;
    }

    public void setXuLy(int xuLy) {
        this.xuLy = xuLy;
    }
}
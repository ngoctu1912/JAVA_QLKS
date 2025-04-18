package DTO;

public class PhongDTO {
    private String maP;
    private String tenP;
    private String loaiP;
    private String hinhAnh;
    private int giaP;
    private String chiTietLoaiPhong;
    private int tinhTrang;

    // Constructor
    public PhongDTO(String maP, String tenP, String loaiP, String hinhAnh, int giaP, String chiTietLoaiPhong, int tinhTrang) {
        this.maP = maP;
        this.tenP = tenP;
        this.loaiP = loaiP;
        this.hinhAnh = hinhAnh;
        this.giaP = giaP;
        this.chiTietLoaiPhong = chiTietLoaiPhong;
        this.tinhTrang = tinhTrang;
    }

    // Getters and Setters
    public String getMaP() { return maP; }
    public void setMaP(String maP) { this.maP = maP; }
    public String getTenP() { return tenP; }
    public void setTenP(String tenP) { this.tenP = tenP; }
    public String getLoaiP() { return loaiP; }
    public void setLoaiP(String loaiP) { this.loaiP = loaiP; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    public int getGiaP() { return giaP; }
    public void setGiaP(int giaP) { this.giaP = giaP; }
    public String getChiTietLoaiPhong() { return chiTietLoaiPhong; }
    public void setChiTietLoaiPhong(String chiTietLoaiPhong) { this.chiTietLoaiPhong = chiTietLoaiPhong; }
    public int getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(int tinhTrang) { this.tinhTrang = tinhTrang; }
}
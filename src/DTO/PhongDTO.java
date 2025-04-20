package DTO;

public class PhongDTO {
    private String maP;
    private String tenP;
    private String loaiP;
    private String hinhAnh;
    private int giaP;
    private String chiTietLoaiPhong;
    private int tinhTrang;

    // Constructor mặc định
    public PhongDTO() {
    }

    // Constructor đầy đủ
    public PhongDTO(String maP, String tenP, String loaiP, String hinhAnh, int giaP, String chiTietLoaiPhong, int tinhTrang) {
        setMaP(maP);
        setTenP(tenP);
        setLoaiP(loaiP);
        setHinhAnh(hinhAnh);
        setGiaP(giaP);
        setChiTietLoaiPhong(chiTietLoaiPhong);
        setTinhTrang(tinhTrang);
    }

    // Getters
    public String getMaP() {
        return maP;
    }

    public String getTenP() {
        return tenP;
    }

    public String getLoaiP() {
        return loaiP;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public int getGiaP() {
        return giaP;
    }

    public String getChiTietLoaiPhong() {
        return chiTietLoaiPhong;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    // Setters
    public void setMaP(String maP) {
        if (maP == null || maP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được rỗng");
        }
        this.maP = maP;
    }

    public void setTenP(String tenP) {
        if (tenP == null || tenP.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên phòng không được rỗng");
        }
        this.tenP = tenP;
    }

    public void setLoaiP(String loaiP) {
        if (loaiP == null || loaiP.trim().isEmpty()) {
            throw new IllegalArgumentException("Loại phòng không được rỗng");
        }
        this.loaiP = loaiP;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh == null ? "" : hinhAnh;
    }

    public void setGiaP(int giaP) {
        if (giaP < 0) {
            throw new IllegalArgumentException("Giá phòng phải lớn hơn hoặc bằng 0");
        }
        this.giaP = giaP;
    }

    public void setChiTietLoaiPhong(String chiTietLoaiPhong) {
        this.chiTietLoaiPhong = chiTietLoaiPhong == null ? "" : chiTietLoaiPhong;
    }

    public void setTinhTrang(int tinhTrang) {
        if (tinhTrang != 0 && tinhTrang != 1) {
            throw new IllegalArgumentException("Tình trạng phòng chỉ được là 0 (trống) hoặc 1 (đã đặt)");
        }
        this.tinhTrang = tinhTrang;
    }
}
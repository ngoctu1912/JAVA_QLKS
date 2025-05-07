package DTO;

import java.util.Date;
import java.util.Objects;

public class NhanVienDTO {

    private int MNV;
    private String HOTEN;
    private int GIOITINH;
    private String SDT;
    private Date NGAYSINH;
    private int TT;
    private String EMAIL;

    public NhanVienDTO() {
    }

    public NhanVienDTO(int MNV, String HOTEN, int GIOITINH, Date NGAYSINH, String SDT, int TT, String EMAIL) {
        this.MNV = MNV;
        this.HOTEN = HOTEN;
        this.GIOITINH = GIOITINH;
        this.NGAYSINH = NGAYSINH;
        this.SDT = SDT;
        this.TT = TT;
        this.EMAIL = EMAIL;
    }

    // Getters and Setters
    public int getMNV() { return MNV; }
    public void setMNV(int MNV) { this.MNV = MNV; }

    public String getHOTEN() { return HOTEN; }
    public void setHOTEN(String HOTEN) { this.HOTEN = HOTEN; }

    public int getGIOITINH() { return GIOITINH; }
    public void setGIOITINH(int GIOITINH) { this.GIOITINH = GIOITINH; }

    public Date getNGAYSINH() { return NGAYSINH; }
    public void setNGAYSINH(Date NGAYSINH) { this.NGAYSINH = NGAYSINH; }

    public String getSDT() { return SDT; }
    public void setSDT(String SDT) { this.SDT = SDT; }

    public int getTT() { return TT; }
    public void setTT(int TT) { this.TT = TT; }

    public String getEMAIL() { return EMAIL; }
    public void setEMAIL(String EMAIL) { this.EMAIL = EMAIL; }

    @Override
    public int hashCode() {
        return Objects.hash(MNV, HOTEN, GIOITINH, NGAYSINH, SDT, TT, EMAIL);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NhanVienDTO other = (NhanVienDTO) obj;
        return MNV == other.MNV &&
               Objects.equals(HOTEN, other.HOTEN) &&
               GIOITINH == other.GIOITINH &&
               Objects.equals(NGAYSINH, other.NGAYSINH) &&
               Objects.equals(SDT, other.SDT) &&
               TT == other.TT &&
               Objects.equals(EMAIL, other.EMAIL);
    }

    @Override
    public String toString() {
        return "NhanVienDTO{" +
               "MNV=" + MNV +
               ", HOTEN='" + HOTEN + '\'' +
               ", GIOITINH=" + GIOITINH +
               ", NGAYSINH=" + NGAYSINH +
               ", SDT='" + SDT + '\'' +
               ", TT=" + TT +
               ", EMAIL='" + EMAIL + '\'' +
               '}';
    }

    public int getColumnCount() {
        return getClass().getDeclaredFields().length;
    }
}
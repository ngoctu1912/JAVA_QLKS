package DTO;

import java.util.Date;

public class KhachHangDTO {
    private int MKH;
    private String TKH;
    private String DIACHI;
    private String SDT;
    private Date NS;
    private int TT;
    private int GT;
    private int CCCD;
    private String EMAIL;

    public KhachHangDTO(int MKH, String TKH, String DIACHI, String SDT, Date NS, int TT, int GT, int CCCD, String EMAIL) {
        this.MKH = MKH;
        this.TKH = TKH;
        this.DIACHI = DIACHI;
        this.SDT = SDT;
        this.NS = NS;
        this.TT = TT;
        this.GT = GT;
        this.CCCD = CCCD;
        this.EMAIL = EMAIL;
    }

    // Getters
    public int getMKH() { return MKH; }
    public String getTKH() { return TKH; }
    public String getDIACHI() { return DIACHI; }
    public String getSDT() { return SDT; }
    public String getNgayThamGia() { return new java.text.SimpleDateFormat("yyyy-MM-dd").format(NS); }
    public Date getNS() { return NS; }
    public int getTT() { return TT; }
    public int getGT() { return GT; }
    public int getCCCD() { return CCCD; }
    public String getEMAIL() { return EMAIL; }
}
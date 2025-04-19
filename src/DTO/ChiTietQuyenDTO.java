package DTO;

import java.util.Objects;

public class ChiTietQuyenDTO {
    private int MNQ;
    private int MCN;
    private String HANHDONG;

    public ChiTietQuyenDTO() {
        
    }

    public ChiTietQuyenDTO(int MNQ, int MCN, String HANHDONG) {
        this.MNQ = MNQ;
        this.MCN = MCN;
        this.HANHDONG = HANHDONG;
    }

    public int getMNQ() {
        return MNQ;
    }

    public void setMNQ(int MNQ) {
        this.MNQ = MNQ;
    }

    public int getMCN() {
        return MCN;
    }

    public void setMCN(int MCN) {
        this.MCN = MCN;
    }

    public String getHANHDONG() {
        return HANHDONG;
    }

    public void setHANHDONG(String HANHDONG) {
        this.HANHDONG = HANHDONG;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.MNQ);
        hash = 83 * hash + Objects.hashCode(this.MCN);
        hash = 83 * hash + Objects.hashCode(this.HANHDONG);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChiTietQuyenDTO other = (ChiTietQuyenDTO) obj;
        if (!Objects.equals(this.MNQ, other.MNQ)) {
            return false;
        }
        if (!Objects.equals(this.MCN, other.MCN)) {
            return false;
        }
        return Objects.equals(this.HANHDONG, other.HANHDONG);
    }

    @Override
    public String toString() {
        return "ChiTietQuyen{" + "Ma nhom quyen = " + MNQ + ", Ma chuc nang = " + MCN + ", Hanh dong = " + HANHDONG + '}';
    }

    
    
}

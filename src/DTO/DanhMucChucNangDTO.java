package DTO;

import java.util.Objects;

public class DanhMucChucNangDTO {
    private int MCN;
    private String TENCN;

    public DanhMucChucNangDTO() {
    }

    public DanhMucChucNangDTO(int MCN, String TENCN) {
        this.MCN = MCN;
        this.TENCN = TENCN;
    }

    public int getMCN() {
        return MCN;
    }

    public void setMCN(int MCN) {
        this.MCN = MCN;
    }

    public String getTENCN() {
        return TENCN;
    }

    public void setTENCN(String TENCN) {
        this.TENCN = TENCN;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.MCN;
        hash = 37 * hash + Objects.hashCode(this.TENCN);
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
        final DanhMucChucNangDTO other = (DanhMucChucNangDTO) obj;
        return MCN == other.MCN &&
                Objects.equals(TENCN, other.TENCN);
    }

    @Override
    public String toString() {
        return "DanhMucChucNang{" + "Ma chuc nang = " + MCN + ", Ten chuc nang = " + TENCN + '}';
    }
}

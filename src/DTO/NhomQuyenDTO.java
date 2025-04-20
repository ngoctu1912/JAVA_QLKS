package DTO;

import java.util.Objects;

public class NhomQuyenDTO {

    private int MNQ;
    private String TEN;

    public NhomQuyenDTO(int MNQ, String TEN) {
        this.MNQ = MNQ;
        this.TEN = TEN;
    }

    public int getMNQ() {
        return MNQ;
    }

    public void setMNQ(int MNQ) {
        this.MNQ = MNQ;
    }

    public String getTEN() {
        return TEN;
    }

    public void setTEN(String tEN) {
        this.TEN = tEN;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.MNQ;
        hash = 37 * hash + Objects.hashCode(this.TEN);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        NhomQuyenDTO other = (NhomQuyenDTO) obj;
        return MNQ == other.MNQ &&
                Objects.equals(TEN, other.TEN);
    }

    @Override
    public String toString() {
        return "NhomQuyenDTO{" + "MNQ=" + MNQ + ", TEN='" + TEN + '\'' + '}';
    }
}
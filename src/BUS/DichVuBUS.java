package BUS;

import DAO.DichVuDAO;
import DTO.DichVuDTO;

import java.util.ArrayList;

public class DichVuBUS {
    private DichVuDAO dichVuDAO;

    public DichVuBUS() {
        this.dichVuDAO = new DichVuDAO();
    }

    public ArrayList<DichVuDTO> getAllDichVu() {
        return dichVuDAO.selectAll();
    }

    public DichVuDTO getDichVuById(String maDV) {
        return dichVuDAO.selectById(maDV);
    }

    public int addDichVu(DichVuDTO dv) {
        return dichVuDAO.add(dv);
    }

    public int updateDichVu(DichVuDTO dv) {
        return dichVuDAO.update(dv);
    }

    public int deleteDichVu(String maDV) {
        return dichVuDAO.delete(maDV);
    }

    public int getAutoIncrement() {
        return dichVuDAO.getAutoIncrement();
    }
}
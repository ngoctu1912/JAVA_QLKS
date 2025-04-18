package BUS;

import DAO.PhongDAO;
import DTO.PhongDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhongBUS {
    private PhongDAO phongDAO;

    public PhongBUS() {
        this.phongDAO = new PhongDAO();
    }

    public ArrayList<PhongDTO> getAllPhong() {
        return phongDAO.selectAll();
    }

    public PhongDTO getPhongById(String maP) {
        return phongDAO.selectById(maP);
    }

    public int addPhong(PhongDTO p) {
        return phongDAO.add(p);
    }

    public int updatePhong(PhongDTO p) {
        return phongDAO.update(p);
    }

    public int deletePhong(String maP) {
        return phongDAO.delete(maP);
    }

    public int getAutoIncrement() {
        return phongDAO.getAutoIncrement();
    }

    public List<Date[]> getThoiGianThue(String maP) {
        return phongDAO.getThoiGianThue(maP);
    }
}
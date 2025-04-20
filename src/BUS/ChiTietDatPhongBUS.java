package BUS;

import DAO.ChiTietDatPhongDAO;
import DTO.ChiTietDatPhongDTO;
import java.util.ArrayList;

public class ChiTietDatPhongBUS {
    private ChiTietDatPhongDAO ctdpDAO;

    public ChiTietDatPhongBUS() {
        this.ctdpDAO = new ChiTietDatPhongDAO();
    }

    public int add(ChiTietDatPhongDTO ctdp) {
        if (ctdp == null || ctdp.getMaCTDP() == null || ctdp.getMaP() == null) {
            throw new IllegalArgumentException("Mã chi tiết đặt phòng hoặc mã phòng không được để trống");
        }
        return ctdpDAO.add(ctdp);
    }

    public int update(ChiTietDatPhongDTO ctdp) {
        if (ctdp == null || ctdp.getMaCTDP() == null || ctdp.getMaP() == null) {
            throw new IllegalArgumentException("Mã chi tiết đặt phòng hoặc mã phòng không được để trống");
        }
        return ctdpDAO.update(ctdp);
    }

    public int delete(String maCTDP) {
        if (maCTDP == null || maCTDP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống");
        }
        return ctdpDAO.delete(maCTDP);
    }

    public ArrayList<ChiTietDatPhongDTO> selectAll() {
        return ctdpDAO.selectAll();
    }

    public ChiTietDatPhongDTO selectById(String maCTDP) {
        if (maCTDP == null || maCTDP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống");
        }
        return ctdpDAO.selectById(maCTDP);
    }
}
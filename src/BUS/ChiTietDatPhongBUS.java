package BUS;

import DAO.ChiTietDatPhongDAO;
import DTO.ChiTietDatPhongDTO;
import java.util.ArrayList;

public class ChiTietDatPhongBUS {
    private final ChiTietDatPhongDAO ctdpDAO;

    public ChiTietDatPhongBUS() {
        this.ctdpDAO = ChiTietDatPhongDAO.getInstance();
    }

    public int add(ChiTietDatPhongDTO ctdp) {
        if (ctdp.getMaCTDP() == null || ctdp.getMaCTDP().isEmpty()) {
            throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
        }
        if (ctdp.getMaP() == null || ctdp.getMaP().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được để trống!");
        }
        if (ctdp.getNgayThue() == null) {
            throw new IllegalArgumentException("Ngày thuê không được để trống!");
        }
        if (ctdp.getNgayTra() == null) {
            throw new IllegalArgumentException("Ngày trả không được để trống!");
        }
        return ctdpDAO.add(ctdp);
    }

    public int update(ChiTietDatPhongDTO ctdp) {
        if (ctdp.getMaCTDP() == null || ctdp.getMaCTDP().isEmpty()) {
            throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
        }
        return ctdpDAO.update(ctdp);
    }

    public int delete(String maCTDP) {
        if (maCTDP == null || maCTDP.isEmpty()) {
            throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
        }
        return ctdpDAO.delete(maCTDP);
    }

    public int deleteByMaP(String maP) {
        if (maP == null || maP.isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được để trống!");
        }
        return ctdpDAO.deleteByMaP(maP);
    }

    public ArrayList<ChiTietDatPhongDTO> getAll() {
        return ctdpDAO.selectAll();
    }

    public ChiTietDatPhongDTO getById(String maCTDP) {
        if (maCTDP == null || maCTDP.isEmpty()) {
            throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
        }
        return ctdpDAO.selectById(maCTDP);
    }

    public int getAutoIncrement() {
        return ctdpDAO.getAutoIncrement();
    }
}
package BUS;

import DAO.ChiTietDatPhongDAO;
import DTO.ChiTietDatPhongDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietDatPhongBUS {
    private ChiTietDatPhongDAO chiTietDatPhongDAO;

    public ChiTietDatPhongBUS() {
        this.chiTietDatPhongDAO = new ChiTietDatPhongDAO();
    }

    public int add(ChiTietDatPhongDTO t) {
        return chiTietDatPhongDAO.add(t);
    }

    public int add(ChiTietDatPhongDTO t, Connection conn) throws SQLException {
        return chiTietDatPhongDAO.add(t, conn);
    }

    public int update(ChiTietDatPhongDTO t) {
        return chiTietDatPhongDAO.update(t);
    }

    public int delete(String id) {
        return chiTietDatPhongDAO.delete(id);
    }

    public ArrayList<ChiTietDatPhongDTO> selectAll() {
        return chiTietDatPhongDAO.selectAll();
    }

    public ChiTietDatPhongDTO selectById(String id) {
        return chiTietDatPhongDAO.selectById(id);
    }

    public int getAutoIncrement() {
        return chiTietDatPhongDAO.getAutoIncrement();
    }
}
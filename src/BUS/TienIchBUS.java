package BUS;

import DAO.TienIchDAO;
import DAO.ConnectDB;
import DTO.TienIchDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TienIchBUS {
    private TienIchDAO tienIchDAO;

    public TienIchBUS() {
        Connection conn = ConnectDB.getConnection();
        this.tienIchDAO = new TienIchDAO(conn);
    }

    public List<TienIchDTO> getAll() throws SQLException {
        List<TienIchDTO> list = tienIchDAO.getAll();
        for (TienIchDTO ti : list) {
            int usedQuantity = tienIchDAO.getUsedQuantity(ti.getMaTI());
            ti.setRemainingQuantity(ti.getTotalQuantity() - usedQuantity);
        }
        return list;
    }

    public TienIchDTO getById(String maTI) throws SQLException {
        TienIchDTO ti = tienIchDAO.getById(maTI);
        if (ti != null) {
            int usedQuantity = tienIchDAO.getUsedQuantity(maTI);
            ti.setRemainingQuantity(ti.getTotalQuantity() - usedQuantity);
        }
        return ti;
    }

    public void add(TienIchDTO ti) throws SQLException {
        if (tienIchDAO.getById(ti.getMaTI()) != null) {
            throw new SQLException("Mã tiện ích " + ti.getMaTI() + " đã tồn tại!");
        }
        if (ti.getTotalQuantity() < 0) {
            throw new SQLException("Số lượng không được nhỏ hơn 0!");
        }
        tienIchDAO.add(ti);
    }

    public void update(TienIchDTO ti) throws SQLException {
        if (tienIchDAO.getById(ti.getMaTI()) == null) {
            throw new SQLException("Tiện ích " + ti.getMaTI() + " không tồn tại!");
        }
        if (ti.getTotalQuantity() < 0) {
            throw new SQLException("Số lượng không được nhỏ hơn 0!");
        }
        tienIchDAO.update(ti);
    }

    public void delete(String maTI) throws SQLException {
        if (tienIchDAO.getById(maTI) == null) {
            throw new SQLException("Tiện ích " + maTI + " không tồn tại!");
        }
        tienIchDAO.delete(maTI);
    }

    public List<String> getRoomsUsingTienIch(String maTI) throws SQLException {
        return tienIchDAO.getRoomsUsingTienIch(maTI);
    }

    public List<Object[]> getTienIchDetailsByRoom() throws SQLException {
        return tienIchDAO.getTienIchDetailsByRoom();
    }

    public List<Object[]> getTienIchDetailsByRoom(String maPhong) throws SQLException {
        return tienIchDAO.getTienIchDetailsByRoom(maPhong);
    }
}
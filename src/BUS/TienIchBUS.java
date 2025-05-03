package BUS;

import DAO.TienIchDAO;
import config.ConnectDB; // Giả sử bạn có lớp này để quản lý kết nối
import DTO.TienIchDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TienIchBUS {
    private TienIchDAO tienIchDAO;

    public TienIchBUS() {
        Connection conn = ConnectDB.getConnection(); // Lấy Connection từ ConnectDB
        this.tienIchDAO = new TienIchDAO(conn);
    }

    // Các phương thức khác giữ nguyên
    public boolean isInitialized() {
        return tienIchDAO != null;
    }

    public List<TienIchDTO> getAll() throws SQLException {
        if (!isInitialized()) {
            throw new SQLException("TienIchBUS initialization failed");
        }
        List<TienIchDTO> list = tienIchDAO.getAll();
        for (TienIchDTO ti : list) {
            int usedQuantity = tienIchDAO.getUsedQuantity(ti.getMaTI());
            ti.setRemainingQuantity(ti.getTotalQuantity() - usedQuantity);
        }
        return list;
    }

    public TienIchDTO getById(String maTI) throws SQLException {
        if (!isInitialized()) {
            throw new SQLException("TienIchBUS initialization failed");
        }
        TienIchDTO ti = tienIchDAO.selectById(maTI);
        if (ti != null) {
            int usedQuantity = tienIchDAO.getUsedQuantity(maTI);
            ti.setRemainingQuantity(ti.getTotalQuantity() - usedQuantity);
        }
        return ti;
    }

    public void add(TienIchDTO ti) throws SQLException {
        if (!isInitialized()) {
            throw new SQLException("TienIchBUS initialization failed");
        }
        if (tienIchDAO.selectById(ti.getMaTI()) != null) {
            throw new SQLException("Mã tiện ích " + ti.getMaTI() + " đã tồn tại!");
        }
        if (ti.getTotalQuantity() < 0) {
            throw new SQLException("Số lượng không được nhỏ hơn 0!");
        }
        tienIchDAO.add(ti);
    }

    public void update(TienIchDTO ti) throws SQLException {
        if (!isInitialized()) {
            throw new SQLException("TienIchBUS initialization failed");
        }
        if (tienIchDAO.selectById(ti.getMaTI()) == null) {
            throw new SQLException("Tiện ích " + ti.getMaTI() + " không tồn tại!");
        }
        if (ti.getTotalQuantity() < 0) {
            throw new SQLException("Số lượng không được nhỏ hơn 0!");
        }
        tienIchDAO.update(ti);
    }

    public void delete(String maTI) throws SQLException {
        if (!isInitialized()) {
            throw new SQLException("TienIchBUS initialization failed");
        }
        if (tienIchDAO.selectById(maTI) == null) {
            throw new SQLException("Tiện ích " + maTI + " không tồn tại!");
        }
        tienIchDAO.delete(maTI);
    }

    public List<String> getRoomsUsingTienIch(String maTI) throws SQLException {
        if (!isInitialized()) {
            throw new SQLException("TienIchBUS initialization failed");
        }
        return tienIchDAO.getRoomsUsingTienIch(maTI);
    }

    public List<Object[]> getTienIchDetailsByRoom() throws SQLException {
        if (!isInitialized()) {
            throw new SQLException("TienIchBUS initialization failed");
        }
        return tienIchDAO.getTienIchDetailsByRoom();
    }

    public List<Object[]> getTienIchDetailsByRoom(String maPhong) throws SQLException {
        if (!isInitialized()) {
            throw new SQLException("TienIchBUS initialization failed");
        }
        return tienIchDAO.getTienIchDetailsByRoom(maPhong);
    }

    public TienIchDTO selectById(String maTI) throws SQLException {
        if (!isInitialized()) {
            throw new SQLException("TienIchBUS initialization failed");
        }
        return tienIchDAO.selectById(maTI);
    }
}
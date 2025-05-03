// package DAO;

// import DTO.TienIchDTO;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// public class TienIchDAO {
//     private Connection conn;

//     public TienIchDAO(Connection conn) {
//         this.conn = conn;
//     }

//     public List<TienIchDTO> getAll() throws SQLException {
//         List<TienIchDTO> list = new ArrayList<>();
//         String sql = "SELECT * FROM TIENICH";
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         ResultSet rs = stmt.executeQuery();
//         while (rs.next()) {
//             TienIchDTO ti = new TienIchDTO();
//             ti.setMaTI(rs.getString("maTI"));
//             ti.setTenTI(rs.getString("tenTI"));
//             ti.setTotalQuantity(rs.getInt("soLuong"));
//             ti.setXuLy(rs.getInt("xuLy"));
//             list.add(ti);
//         }
//         rs.close();
//         stmt.close();
//         return list;
//     }

//     public TienIchDTO getById(String maTI) throws SQLException {
//         String sql = "SELECT * FROM TIENICH WHERE maTI = ?";
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         stmt.setString(1, maTI);
//         ResultSet rs = stmt.executeQuery();
//         if (rs.next()) {
//             TienIchDTO ti = new TienIchDTO();
//             ti.setMaTI(rs.getString("maTI"));
//             ti.setTenTI(rs.getString("tenTI"));
//             ti.setTotalQuantity(rs.getInt("soLuong"));
//             ti.setXuLy(rs.getInt("xuLy"));
//             rs.close();
//             stmt.close();
//             return ti;
//         }
//         rs.close();
//         stmt.close();
//         return null;
//     }

//     public void add(TienIchDTO ti) throws SQLException {
//         String sql = "INSERT INTO TIENICH (maTI, tenTI, soLuong, xuLy) VALUES (?, ?, ?, ?)";
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         stmt.setString(1, ti.getMaTI());
//         stmt.setString(2, ti.getTenTI());
//         stmt.setInt(3, ti.getTotalQuantity());
//         stmt.setInt(4, ti.getXuLy());
//         stmt.executeUpdate();
//         stmt.close();
//     }

//     public void update(TienIchDTO ti) throws SQLException {
//         String sql = "UPDATE TIENICH SET tenTI = ?, soLuong = ?, xuLy = ? WHERE maTI = ?";
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         stmt.setString(1, ti.getTenTI());
//         stmt.setInt(2, ti.getTotalQuantity());
//         stmt.setInt(3, ti.getXuLy());
//         stmt.setString(4, ti.getMaTI());
//         stmt.executeUpdate();
//         stmt.close();
//     }

//     public void delete(String maTI) throws SQLException {
//         String sql = "DELETE FROM TIENICH WHERE maTI = ?";
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         stmt.setString(1, maTI);
//         stmt.executeUpdate();
//         stmt.close();
//     }

//     public int getUsedQuantity(String maTI) throws SQLException {
//         String sql = "SELECT SUM(soLuong) FROM CHITIETTIENICH WHERE maTI = ?";
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         stmt.setString(1, maTI);
//         ResultSet rs = stmt.executeQuery();
//         if (rs.next()) {
//             int usedQuantity = rs.getInt(1);
//             rs.close();
//             stmt.close();
//             return usedQuantity;
//         }
//         rs.close();
//         stmt.close();
//         return 0;
//     }

//     public List<String> getRoomsUsingTienIch(String maTI) throws SQLException {
//         List<String> rooms = new ArrayList<>();
//         String sql = "SELECT p.maP FROM PHONG p JOIN CHITIETTIENICH ct ON p.maP = ct.maP WHERE ct.maTI = ?";
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         stmt.setString(1, maTI);
//         ResultSet rs = stmt.executeQuery();
//         while (rs.next()) {
//             rooms.add(rs.getString("maP"));
//         }
//         rs.close();
//         stmt.close();
//         return rooms;
//     }

//     public List<Object[]> getTienIchDetailsByRoom() throws SQLException {
//         List<Object[]> result = new ArrayList<>();
//         String sql = "SELECT p.maP, p.tenP, " +
//                      "SUM(CASE WHEN ct.maTI = 'TI001' THEN ct.soLuong ELSE 0 END) AS wifi, " +
//                      "SUM(CASE WHEN ct.maTI = 'TI002' THEN ct.soLuong ELSE 0 END) AS tv, " +
//                      "SUM(CASE WHEN ct.maTI = 'TI003' THEN ct.soLuong ELSE 0 END) AS mayLanh, " +
//                      "SUM(CASE WHEN ct.maTI = 'TI004' THEN ct.soLuong ELSE 0 END) AS tuLanh, " +
//                      "SUM(CASE WHEN ct.maTI = 'TI005' THEN ct.soLuong ELSE 0 END) AS binhNongLanh " +
//                      "FROM PHONG p " +
//                      "LEFT JOIN CHITIETTIENICH ct ON p.maP = ct.maP " +
//                      "GROUP BY p.maP, p.tenP";
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         ResultSet rs = stmt.executeQuery();
//         while (rs.next()) {
//             Object[] row = new Object[]{
//                 rs.getString("maP"),
//                 rs.getString("tenP"),
//                 rs.getInt("wifi"),
//                 rs.getInt("tv"),
//                 rs.getInt("mayLanh"),
//                 rs.getInt("tuLanh"),
//                 rs.getInt("binhNongLanh")
//             };
//             result.add(row);
//         }
//         rs.close();
//         stmt.close();
//         return result;
//     }

//     public List<Object[]> getTienIchDetailsByRoom(String maPhong) throws SQLException {
//         List<Object[]> result = new ArrayList<>();
//         String sql = "SELECT p.maP, p.tenP, " +
//                      "SUM(CASE WHEN ct.maTI = 'TI001' THEN ct.soLuong ELSE 0 END) AS wifi, " +
//                      "SUM(CASE WHEN ct.maTI = 'TI002' THEN ct.soLuong ELSE 0 END) AS tv, " +
//                      "SUM(CASE WHEN ct.maTI = 'TI003' THEN ct.soLuong ELSE 0 END) AS mayLanh, " +
//                      "SUM(CASE WHEN ct.maTI = 'TI004' THEN ct.soLuong ELSE 0 END) AS tuLanh, " +
//                      "SUM(CASE WHEN ct.maTI = 'TI005' THEN ct.soLuong ELSE 0 END) AS binhNongLanh " +
//                      "FROM PHONG p " +
//                      "LEFT JOIN CHITIETTIENICH ct ON p.maP = ct.maP " +
//                      "WHERE p.maP = ? " +
//                      "GROUP BY p.maP, p.tenP";
//         PreparedStatement stmt = conn.prepareStatement(sql);
//         stmt.setString(1, maPhong);
//         ResultSet rs = stmt.executeQuery();
//         while (rs.next()) {
//             Object[] row = new Object[]{
//                 rs.getString("maP"),
//                 rs.getString("tenP"),
//                 rs.getInt("wifi"),
//                 rs.getInt("tv"),
//                 rs.getInt("mayLanh"),
//                 rs.getInt("tuLanh"),
//                 rs.getInt("binhNongLanh")
//             };
//             result.add(row);
//         }
//         rs.close();
//         stmt.close();
//         return result;
//     }
// }

package DAO;

import DTO.TienIchDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TienIchDAO {
    private Connection conn;

    public TienIchDAO(Connection conn) {
        this.conn = conn;
    }

    public List<TienIchDTO> getAll() throws SQLException {
        List<TienIchDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM TIENICH";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            TienIchDTO ti = new TienIchDTO();
            ti.setMaTI(rs.getString("maTI"));
            ti.setTenTI(rs.getString("tenTI"));
            ti.setTotalQuantity(rs.getInt("soLuong"));
            ti.setXuLy(rs.getInt("xuLy"));
            list.add(ti);
        }
        rs.close();
        stmt.close();
        return list;
    }

    // // Đổi tên từ getById thành selectById
    // public TienIchDTO selectById(String maTI) throws SQLException {
    //     String sql = "SELECT * FROM TIENICH WHERE maTI = ?";
    //     PreparedStatement stmt = conn.prepareStatement(sql);
    //     stmt.setString(1, maTI);
    //     ResultSet rs = stmt.executeQuery();
    //     if (rs.next()) {
    //         TienIchDTO ti = new TienIchDTO();
    //         ti.setMaTI(rs.getString("maTI"));
    //         ti.setTenTI(rs.getString("tenTI"));
    //         ti.setTotalQuantity(rs.getInt("soLuong"));
    //         ti.setXuLy(rs.getInt("xuLy"));
    //         rs.close();
    //         stmt.close();
    //         return ti;
    //     }
    //     rs.close();
    //     stmt.close();
    //     return null;
    // }

    public void add(TienIchDTO ti) throws SQLException {
        String sql = "INSERT INTO TIENICH (maTI, tenTI, soLuong, xuLy) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, ti.getMaTI());
        stmt.setString(2, ti.getTenTI());
        stmt.setInt(3, ti.getTotalQuantity());
        stmt.setInt(4, ti.getXuLy());
        stmt.executeUpdate();
        stmt.close();
    }

    public void update(TienIchDTO ti) throws SQLException {
        String sql = "UPDATE TIENICH SET tenTI = ?, soLuong = ?, xuLy = ? WHERE maTI = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, ti.getTenTI());
        stmt.setInt(2, ti.getTotalQuantity());
        stmt.setInt(3, ti.getXuLy());
        stmt.setString(4, ti.getMaTI());
        stmt.executeUpdate();
        stmt.close();
    }

    public void delete(String maTI) throws SQLException {
        String sql = "DELETE FROM TIENICH WHERE maTI = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, maTI);
        stmt.executeUpdate();
        stmt.close();
    }

    public int getUsedQuantity(String maTI) throws SQLException {
        String sql = "SELECT SUM(soLuong) FROM CHITIETTIENICH WHERE maTI = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, maTI);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int usedQuantity = rs.getInt(1);
            rs.close();
            stmt.close();
            return usedQuantity;
        }
        rs.close();
        stmt.close();
        return 0;
    }

    public List<String> getRoomsUsingTienIch(String maTI) throws SQLException {
        List<String> rooms = new ArrayList<>();
        String sql = "SELECT p.maP FROM PHONG p JOIN CHITIETTIENICH ct ON p.maP = ct.maP WHERE ct.maTI = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, maTI);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            rooms.add(rs.getString("maP"));
        }
        rs.close();
        stmt.close();
        return rooms;
    }

    public List<Object[]> getTienIchDetailsByRoom() throws SQLException {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT p.maP, p.tenP, " +
                     "SUM(CASE WHEN ct.maTI = 'TI001' THEN ct.soLuong ELSE 0 END) AS wifi, " +
                     "SUM(CASE WHEN ct.maTI = 'TI002' THEN ct.soLuong ELSE 0 END) AS tv, " +
                     "SUM(CASE WHEN ct.maTI = 'TI003' THEN ct.soLuong ELSE 0 END) AS mayLanh, " +
                     "SUM(CASE WHEN ct.maTI = 'TI004' THEN ct.soLuong ELSE 0 END) AS tuLanh, " +
                     "SUM(CASE WHEN ct.maTI = 'TI005' THEN ct.soLuong ELSE 0 END) AS binhNongLanh " +
                     "FROM PHONG p " +
                     "LEFT JOIN CHITIETTIENICH ct ON p.maP = ct.maP " +
                     "GROUP BY p.maP, p.tenP";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Object[] row = new Object[]{
                rs.getString("maP"),
                rs.getString("tenP"),
                rs.getInt("wifi"),
                rs.getInt("tv"),
                rs.getInt("mayLanh"),
                rs.getInt("tuLanh"),
                rs.getInt("binhNongLanh")
            };
            result.add(row);
        }
        rs.close();
        stmt.close();
        return result;
    }

    public List<Object[]> getTienIchDetailsByRoom(String maPhong) throws SQLException {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT p.maP, p.tenP, " +
                     "SUM(CASE WHEN ct.maTI = 'TI001' THEN ct.soLuong ELSE 0 END) AS wifi, " +
                     "SUM(CASE WHEN ct.maTI = 'TI002' THEN ct.soLuong ELSE 0 END) AS tv, " +
                     "SUM(CASE WHEN ct.maTI = 'TI003' THEN ct.soLuong ELSE 0 END) AS mayLanh, " +
                     "SUM(CASE WHEN ct.maTI = 'TI004' THEN ct.soLuong ELSE 0 END) AS tuLanh, " +
                     "SUM(CASE WHEN ct.maTI = 'TI005' THEN ct.soLuong ELSE 0 END) AS binhNongLanh " +
                     "FROM PHONG p " +
                     "LEFT JOIN CHITIETTIENICH ct ON p.maP = ct.maP " +
                     "WHERE p.maP = ? " +
                     "GROUP BY p.maP, p.tenP";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, maPhong);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Object[] row = new Object[]{
                rs.getString("maP"),
                rs.getString("tenP"),
                rs.getInt("wifi"),
                rs.getInt("tv"),
                rs.getInt("mayLanh"),
                rs.getInt("tuLanh"),
                rs.getInt("binhNongLanh")
            };
            result.add(row);
        }
        rs.close();
        stmt.close();
        return result;
    }

    public TienIchDTO selectById(String maTI) throws SQLException {
        String sql = "SELECT * FROM TIENICH WHERE maTI = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, maTI);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            TienIchDTO ti = new TienIchDTO();
            ti.setMaTI(rs.getString("maTI"));
            ti.setTenTI(rs.getString("tenTI"));
            ti.setTotalQuantity(rs.getInt("soLuong"));
            ti.setXuLy(rs.getInt("xuLy"));
            rs.close();
            stmt.close();
            return ti;
        }
        rs.close();
        stmt.close();
        return null;
    }
}
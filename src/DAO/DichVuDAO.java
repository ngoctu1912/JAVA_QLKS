// package DAO;

// import DTO.DichVuDTO;
// import config.ConnectDB;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.Statement;
// import java.util.ArrayList;

// public class DichVuDAO implements DAOinterface<DichVuDTO> {

//     public DichVuDAO() {
//         // No connection field needed
//     }

//     @Override
//     public int add(DichVuDTO t) {
//         Connection conn = ConnectDB.getConnection();
//         if (conn == null) return 0;

//         String query = "INSERT INTO DICHVU (maDV, tenDV, loaiDV, soLuong, giaDV, xuLy) VALUES (?, ?, ?, ?, ?, ?)";
//         try {
//             PreparedStatement ps = conn.prepareStatement(query);
//             ps.setString(1, t.getMaDV());
//             ps.setString(2, t.getTenDV());
//             ps.setString(3, t.getLoaiDV());
//             ps.setInt(4, t.getSoLuong());
//             ps.setInt(5, t.getGiaDV());
//             ps.setInt(6, t.getXuLy());
//             int rowsAffected = ps.executeUpdate();
//             return rowsAffected;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return 0;
//         }
//     }

//     @Override
//     public int update(DichVuDTO t) {
//         Connection conn = ConnectDB.getConnection();
//         if (conn == null) return 0;

//         String query = "UPDATE DICHVU SET tenDV = ?, loaiDV = ?, soLuong = ?, giaDV = ?, xuLy = ? WHERE maDV = ?";
//         try {
//             PreparedStatement ps = conn.prepareStatement(query);
//             ps.setString(1, t.getTenDV());
//             ps.setString(2, t.getLoaiDV());
//             ps.setInt(3, t.getSoLuong());
//             ps.setInt(4, t.getGiaDV());
//             ps.setInt(5, t.getXuLy());
//             ps.setString(6, t.getMaDV());
//             int rowsAffected = ps.executeUpdate();
//             return rowsAffected;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return 0;
//         }
//     }

//     @Override
//     public int delete(String id) {
//         Connection conn = ConnectDB.getConnection();
//         if (conn == null) return 0;

//         String query = "DELETE FROM DICHVU WHERE maDV = ?";
//         try {
//             PreparedStatement ps = conn.prepareStatement(query);
//             ps.setString(1, id); // maDV as the primary key
//             int rowsAffected = ps.executeUpdate();
//             return rowsAffected;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return 0;
//         }
//     }

//     @Override
//     public ArrayList<DichVuDTO> selectAll() {
//         Connection conn = ConnectDB.getConnection();
//         if (conn == null) return new ArrayList<>();

//         ArrayList<DichVuDTO> services = new ArrayList<>();
//         String query = "SELECT * FROM DICHVU";
//         try {
//             PreparedStatement ps = conn.prepareStatement(query);
//             ResultSet rs = ps.executeQuery();
//             while (rs.next()) {
//                 DichVuDTO service = new DichVuDTO(
//                     rs.getString("maDV"),
//                     rs.getString("tenDV"),
//                     rs.getString("loaiDV"),
//                     rs.getInt("soLuong"),
//                     rs.getInt("giaDV"),
//                     rs.getInt("xuLy")
//                 );
//                 services.add(service);
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return services;
//     }

//     @Override
//     public DichVuDTO selectById(String id) {
//         Connection conn = ConnectDB.getConnection();
//         if (conn == null) return null;

//         String query = "SELECT * FROM DICHVU WHERE maDV = ?";
//         try {
//             PreparedStatement ps = conn.prepareStatement(query);
//             ps.setString(1, id); // maDV as the primary key
//             ResultSet rs = ps.executeQuery();
//             if (rs.next()) {
//                 return new DichVuDTO(
//                     rs.getString("maDV"),
//                     rs.getString("tenDV"),
//                     rs.getString("loaiDV"),
//                     rs.getInt("soLuong"),
//                     rs.getInt("giaDV"),
//                     rs.getInt("xuLy")
//                 );
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return null;
//     }

//     @Override
//     public int getAutoIncrement() {
//         Connection conn = ConnectDB.getConnection();
//         if (conn == null) return 0;

//         String query = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlykhachsan' AND TABLE_NAME = 'DICHVU'";
//         try {
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query);
//             if (rs.next()) {
//                 return rs.getInt("AUTO_INCREMENT");
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return 0;
//     }
// }

package DAO;

import DTO.DichVuDTO;
import config.ConnectDB;
import config.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DichVuDAO implements DAOinterface<DichVuDTO> {
    @Override
    public int add(DichVuDTO dv) {
        int rowsAffected = 0;
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO DICHVU (maDV, tenDV, loaiDV, soLuong, giaDV, xuLy) VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, dv.getMaDV());
            stmt.setString(2, dv.getTenDV());
            stmt.setString(3, dv.getLoaiDV());
            stmt.setInt(4, dv.getSoLuong());
            stmt.setInt(5, dv.getGiaDV());
            stmt.setInt(6, dv.getXuLy());
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int update(DichVuDTO dv) {
        int rowsAffected = 0;
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE DICHVU SET tenDV = ?, loaiDV = ?, soLuong = ?, giaDV = ?, xuLy = ? WHERE maDV = ?")) {
            stmt.setString(1, dv.getTenDV());
            stmt.setString(2, dv.getLoaiDV());
            stmt.setInt(3, dv.getSoLuong());
            stmt.setInt(4, dv.getGiaDV());
            stmt.setInt(5, dv.getXuLy());
            stmt.setString(6, dv.getMaDV());
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int delete(String maDV) {
        int rowsAffected = 0;
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM DICHVU WHERE maDV = ?")) {
            stmt.setString(1, maDV);
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ArrayList<DichVuDTO> selectAll() {
        ArrayList<DichVuDTO> list = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DICHVU");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new DichVuDTO(
                        rs.getString("maDV"),
                        rs.getString("tenDV"),
                        rs.getString("loaiDV"),
                        rs.getInt("soLuong"),
                        rs.getInt("giaDV"),
                        rs.getInt("xuLy")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public DichVuDTO selectById(String maDV) {
        DichVuDTO dv = null;
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DICHVU WHERE maDV = ?")) {
            stmt.setString(1, maDV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dv = new DichVuDTO(
                        rs.getString("maDV"),
                        rs.getString("tenDV"),
                        rs.getString("loaiDV"),
                        rs.getInt("soLuong"),
                        rs.getInt("giaDV"),
                        rs.getInt("xuLy")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dv;
    }

    @Override
    public int getAutoIncrement() {
        int autoIncrement = 0;
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = 'DICHVU'")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                autoIncrement = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autoIncrement;
    }
}
// package DAO;

// import DTO.ChiTietThueDichVuDTO;
// import config.ConnectDB;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.Date;

// public class ChiTietThueDichVuDAO implements DAOinterface<ChiTietThueDichVuDTO> {

//     public ChiTietThueDichVuDAO() {
//         // No connection field needed
//     }

//     @Override
//     public int add(ChiTietThueDichVuDTO t) {
//         Connection conn = ConnectDB.getConnection();
//         if (conn == null) return 0;

//         String query = "INSERT INTO CHITIETTHUEDICHVU (maCTT, maDV, ngaySuDung, SoLuong, giaDV) VALUES (?, ?, ?, ?, ?)";
//         try {
//             PreparedStatement ps = conn.prepareStatement(query);
//             ps.setString(1, t.getMaCTT());
//             ps.setString(2, t.getMaDV());
//             ps.setDate(3, new java.sql.Date(t.getNgaySuDung().getTime()));
//             ps.setInt(4, t.getSoLuong());
//             ps.setInt(5, t.getGiaDV());
//             int rowsAffected = ps.executeUpdate();
//             return rowsAffected;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return 0;
//         }
//     }

//     @Override
//     public int update(ChiTietThueDichVuDTO t) {
//         Connection conn = ConnectDB.getConnection();
//         if (conn == null) return 0;

//         String query = "UPDATE CHITIETTHUEDICHVU SET SoLuong = ?, giaDV = ? WHERE maCTT = ? AND maDV = ? AND ngaySuDung = ?";
//         try {
//             PreparedStatement ps = conn.prepareStatement(query);
//             ps.setInt(1, t.getSoLuong());
//             ps.setInt(2, t.getGiaDV());
//             ps.setString(3, t.getMaCTT());
//             ps.setString(4, t.getMaDV());
//             ps.setDate(5, new java.sql.Date(t.getNgaySuDung().getTime()));
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

//         String query = "DELETE FROM CHITIETTHUEDICHVU WHERE maCTT = ? AND maDV = ? AND ngaySuDung = ?";
//         try {
//             String[] keys = id.split(",");
//             PreparedStatement ps = conn.prepareStatement(query);
//             ps.setString(1, keys[0]); // maCTT
//             ps.setString(2, keys[1]); // maDV
//             ps.setDate(3, java.sql.Date.valueOf(keys[2])); // ngaySuDung
//             int rowsAffected = ps.executeUpdate();
//             return rowsAffected;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return 0;
//         }
//     }

//     @Override
//     public ArrayList<ChiTietThueDichVuDTO> selectAll() {
//         Connection conn = ConnectDB.getConnection();
//         if (conn == null) return new ArrayList<>();

//         ArrayList<ChiTietThueDichVuDTO> details = new ArrayList<>();
//         String query = "SELECT * FROM CHITIETTHUEDICHVU";
//         try {
//             PreparedStatement ps = conn.prepareStatement(query);
//             ResultSet rs = ps.executeQuery();
//             while (rs.next()) {
//                 ChiTietThueDichVuDTO detail = new ChiTietThueDichVuDTO(
//                     rs.getString("maCTT"),
//                     rs.getString("maDV"),
//                     rs.getDate("ngaySuDung"),
//                     rs.getInt("SoLuong"),
//                     rs.getInt("giaDV")
//                 );
//                 details.add(detail);
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return details;
//     }

//     @Override
//     public ChiTietThueDichVuDTO selectById(String id) {
//         Connection conn = ConnectDB.getConnection();
//         if (conn == null) return null;

//         String query = "SELECT * FROM CHITIETTHUEDICHVU WHERE maCTT = ? AND maDV = ? AND ngaySuDung = ?";
//         try {
//             String[] keys = id.split(",");
//             PreparedStatement ps = conn.prepareStatement(query);
//             ps.setString(1, keys[0]); // maCTT
//             ps.setString(2, keys[1]); // maDV
//             ps.setDate(3, java.sql.Date.valueOf(keys[2])); // ngaySuDung
//             ResultSet rs = ps.executeQuery();
//             if (rs.next()) {
//                 return new ChiTietThueDichVuDTO(
//                     rs.getString("maCTT"),
//                     rs.getString("maDV"),
//                     rs.getDate("ngaySuDung"),
//                     rs.getInt("SoLuong"),
//                     rs.getInt("giaDV")
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

//         String query = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlykhachsan' AND TABLE_NAME = 'CHITIETTHUEDICHVU'";
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

import DTO.ChiTietThueDichVuDTO;
import config.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class ChiTietThueDichVuDAO implements DAOinterface<ChiTietThueDichVuDTO> {

    public ChiTietThueDichVuDAO() {
    }

    @Override
    public int add(ChiTietThueDichVuDTO t) {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return 0;

        try {
            int result = add(t, conn);
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int add(ChiTietThueDichVuDTO t, Connection conn) {
        String query = "INSERT INTO CHITIETTHUEDICHVU (maCTT, maDV, ngaySuDung, SoLuong, giaDV) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, t.getMaCTT());
            ps.setString(2, t.getMaDV());
            ps.setDate(3, new java.sql.Date(t.getNgaySuDung().getTime()));
            ps.setInt(4, t.getSoLuong());
            ps.setInt(5, t.getGiaDV());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(ChiTietThueDichVuDTO t) {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return 0;

        String query = "UPDATE CHITIETTHUEDICHVU SET SoLuong = ?, giaDV = ? WHERE maCTT = ? AND maDV = ? AND ngaySuDung = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, t.getSoLuong());
            ps.setInt(2, t.getGiaDV());
            ps.setString(3, t.getMaCTT());
            ps.setString(4, t.getMaDV());
            ps.setDate(5, new java.sql.Date(t.getNgaySuDung().getTime()));
            int rowsAffected = ps.executeUpdate();
            conn.close();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(String id) {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return 0;

        String query = "DELETE FROM CHITIETTHUEDICHVU WHERE maCTT = ? AND maDV = ? AND ngaySuDung = ?";
        try {
            String[] keys = id.split(",");
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, keys[0]);
            ps.setString(2, keys[1]);
            ps.setDate(3, java.sql.Date.valueOf(keys[2]));
            int rowsAffected = ps.executeUpdate();
            conn.close();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<ChiTietThueDichVuDTO> selectAll() {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return new ArrayList<>();

        ArrayList<ChiTietThueDichVuDTO> details = new ArrayList<>();
        String query = "SELECT * FROM CHITIETTHUEDICHVU";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietThueDichVuDTO detail = new ChiTietThueDichVuDTO(
                    rs.getString("maCTT"),
                    rs.getString("maDV"),
                    rs.getDate("ngaySuDung"),
                    rs.getInt("SoLuong"),
                    rs.getInt("giaDV")
                );
                details.add(detail);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    @Override
    public ChiTietThueDichVuDTO selectById(String id) {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return null;

        String query = "SELECT * FROM CHITIETTHUEDICHVU WHERE maCTT = ? AND maDV = ? AND ngaySuDung = ?";
        try {
            String[] keys = id.split(",");
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, keys[0]);
            ps.setString(2, keys[1]);
            ps.setDate(3, java.sql.Date.valueOf(keys[2]));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ChiTietThueDichVuDTO detail = new ChiTietThueDichVuDTO(
                    rs.getString("maCTT"),
                    rs.getString("maDV"),
                    rs.getDate("ngaySuDung"),
                    rs.getInt("SoLuong"),
                    rs.getInt("giaDV")
                );
                conn.close();
                return detail;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getAutoIncrement() {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return 0;

        String query = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlykhachsan' AND TABLE_NAME = 'CHITIETTHUEDICHVU'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int autoIncrement = rs.getInt("AUTO_INCREMENT");
                conn.close();
                return autoIncrement;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
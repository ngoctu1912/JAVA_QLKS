// // package DAO;

// // import DTO.ChiTietDatPhongDTO;
// // import config.ConnectDB;
// // import java.sql.Connection;
// // import java.sql.PreparedStatement;
// // import java.sql.ResultSet;
// // import java.sql.SQLException;
// // import java.util.ArrayList;

// // public class ChiTietDatPhongDAO implements DAOinterface<ChiTietDatPhongDTO> {

// //     public static ChiTietDatPhongDAO getInstance() {
// //         return new ChiTietDatPhongDAO();
// //     }

// //     @Override
// //     public int add(ChiTietDatPhongDTO ctdp) {
// //         String sql = "INSERT INTO CHITIETDATPHONG (maCTDP, maP, ngayThue, ngayTra, ngayCheckOut, loaiHinhThue, giaThue, tinhTrang) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
// //         try (Connection conn = ConnectDB.getConnection();
// //              PreparedStatement pstmt = conn.prepareStatement(sql)) {
// //             pstmt.setString(1, ctdp.getMaCTDP());
// //             pstmt.setString(2, ctdp.getMaP());
// //             pstmt.setTimestamp(3, ctdp.getNgayThue() != null ? new java.sql.Timestamp(ctdp.getNgayThue().getTime()) : null);
// //             pstmt.setTimestamp(4, ctdp.getNgayTra() != null ? new java.sql.Timestamp(ctdp.getNgayTra().getTime()) : null);
// //             pstmt.setTimestamp(5, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
// //             pstmt.setInt(6, ctdp.getLoaiHinhThue());
// //             pstmt.setInt(7, ctdp.getGiaThue());
// //             pstmt.setInt(8, ctdp.getTinhTrang());
// //             return pstmt.executeUpdate();
// //         } catch (SQLException e) {
// //             throw new RuntimeException("Lỗi khi thêm chi tiết đặt phòng: " + e.getMessage(), e);
// //         }
// //     }

// //     public int add(ChiTietDatPhongDTO ctdp, Connection conn) throws SQLException {
// //         String sql = "INSERT INTO CHITIETDATPHONG (maCTDP, maP, ngayThue, ngayTra, ngayCheckOut, loaiHinhThue, giaThue, tinhTrang) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
// //         try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
// //             pstmt.setString(1, ctdp.getMaCTDP());
// //             pstmt.setString(2, ctdp.getMaP());
// //             pstmt.setTimestamp(3, ctdp.getNgayThue() != null ? new java.sql.Timestamp(ctdp.getNgayThue().getTime()) : null);
// //             pstmt.setTimestamp(4, ctdp.getNgayTra() != null ? new java.sql.Timestamp(ctdp.getNgayTra().getTime()) : null);
// //             pstmt.setTimestamp(5, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
// //             pstmt.setInt(6, ctdp.getLoaiHinhThue());
// //             pstmt.setInt(7, ctdp.getGiaThue());
// //             pstmt.setInt(8, ctdp.getTinhTrang());
// //             return pstmt.executeUpdate();
// //         }
// //     }

// //     @Override
// //     public int update(ChiTietDatPhongDTO ctdp) {
// //         String sql = "UPDATE CHITIETDATPHONG SET maP = ?, ngayThue = ?, ngayTra = ?, ngayCheckOut = ?, loaiHinhThue = ?, giaThue = ?, tinhTrang = ? WHERE maCTDP = ?";
// //         try (Connection conn = ConnectDB.getConnection();
// //              PreparedStatement pstmt = conn.prepareStatement(sql)) {
// //             pstmt.setString(1, ctdp.getMaP());
// //             pstmt.setTimestamp(2, ctdp.getNgayThue() != null ? new java.sql.Timestamp(ctdp.getNgayThue().getTime()) : null);
// //             pstmt.setTimestamp(3, ctdp.getNgayTra() != null ? new java.sql.Timestamp(ctdp.getNgayTra().getTime()) : null);
// //             pstmt.setTimestamp(4, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
// //             pstmt.setInt(5, ctdp.getLoaiHinhThue());
// //             pstmt.setInt(6, ctdp.getGiaThue());
// //             pstmt.setInt(7, ctdp.getTinhTrang());
// //             pstmt.setString(8, ctdp.getMaCTDP());
// //             return pstmt.executeUpdate();
// //         } catch (SQLException e) {
// //             throw new RuntimeException("Lỗi khi cập nhật chi tiết đặt phòng: " + e.getMessage(), e);
// //         }
// //     }

// //     @Override
// //     public int delete(String maCTDP) {
// //         String sql = "DELETE FROM CHITIETDATPHONG WHERE maCTDP = ?";
// //         try (Connection conn = ConnectDB.getConnection();
// //              PreparedStatement pstmt = conn.prepareStatement(sql)) {
// //             pstmt.setString(1, maCTDP);
// //             return pstmt.executeUpdate();
// //         } catch (SQLException e) {
// //             throw new RuntimeException("Lỗi khi xóa chi tiết đặt phòng: " + e.getMessage(), e);
// //         }
// //     }

// //     public int deleteByMaP(String maP) {
// //         String sql = "DELETE FROM CHITIETDATPHONG WHERE maP = ?";
// //         try (Connection conn = ConnectDB.getConnection();
// //              PreparedStatement pstmt = conn.prepareStatement(sql)) {
// //             pstmt.setString(1, maP);
// //             return pstmt.executeUpdate();
// //         } catch (SQLException e) {
// //             throw new RuntimeException("Lỗi khi xóa chi tiết đặt phòng theo maP: " + e.getMessage(), e);
// //         }
// //     }

// //     @Override
// //     public ArrayList<ChiTietDatPhongDTO> selectAll() {
// //         ArrayList<ChiTietDatPhongDTO> ctdpList = new ArrayList<>();
// //         String sql = "SELECT * FROM CHITIETDATPHONG";
// //         try (Connection conn = ConnectDB.getConnection();
// //              PreparedStatement pstmt = conn.prepareStatement(sql);
// //              ResultSet rs = pstmt.executeQuery()) {
// //             while (rs.next()) {
// //                 ChiTietDatPhongDTO ctdp = new ChiTietDatPhongDTO();
// //                 ctdp.setMaCTDP(rs.getString("maCTDP"));
// //                 ctdp.setMaP(rs.getString("maP"));
// //                 ctdp.setNgayThue(rs.getTimestamp("ngayThue"));
// //                 ctdp.setNgayTra(rs.getTimestamp("ngayTra"));
// //                 ctdp.setNgayCheckOut(rs.getTimestamp("ngayCheckOut"));
// //                 ctdp.setLoaiHinhThue(rs.getInt("loaiHinhThue"));
// //                 ctdp.setGiaThue(rs.getInt("giaThue"));
// //                 ctdp.setTinhTrang(rs.getInt("tinhTrang"));
// //                 ctdpList.add(ctdp);
// //             }
// //         } catch (SQLException e) {
// //             throw new RuntimeException("Lỗi khi lấy danh sách chi tiết đặt phòng: " + e.getMessage(), e);
// //         }
// //         return ctdpList;
// //     }

// //     @Override
// //     public ChiTietDatPhongDTO selectById(String maCTDP) {
// //         String sql = "SELECT * FROM CHITIETDATPHONG WHERE maCTDP = ?";
// //         try (Connection conn = ConnectDB.getConnection();
// //              PreparedStatement pstmt = conn.prepareStatement(sql)) {
// //             pstmt.setString(1, maCTDP);
// //             try (ResultSet rs = pstmt.executeQuery()) {
// //                 if (rs.next()) {
// //                     ChiTietDatPhongDTO ctdp = new ChiTietDatPhongDTO();
// //                     ctdp.setMaCTDP(rs.getString("maCTDP"));
// //                     ctdp.setMaP(rs.getString("maP"));
// //                     ctdp.setNgayThue(rs.getTimestamp("ngayThue"));
// //                     ctdp.setNgayTra(rs.getTimestamp("ngayTra"));
// //                     ctdp.setNgayCheckOut(rs.getTimestamp("ngayCheckOut"));
// //                     ctdp.setLoaiHinhThue(rs.getInt("loaiHinhThue"));
// //                     ctdp.setGiaThue(rs.getInt("giaThue"));
// //                     ctdp.setTinhTrang(rs.getInt("tinhTrang"));
// //                     return ctdp;
// //                 }
// //             }
// //         } catch (SQLException e) {
// //             throw new RuntimeException("Lỗi khi lấy chi tiết đặt phòng theo maCTDP: " + e.getMessage(), e);
// //         }
// //         return null;
// //     }

// //     @Override
// //     public int getAutoIncrement() {
// //         String sql = "SELECT MAX(CAST(SUBSTRING(maCTDP, 3) AS UNSIGNED)) AS maxId FROM CHITIETDATPHONG";
// //         try (Connection conn = ConnectDB.getConnection();
// //              PreparedStatement pstmt = conn.prepareStatement(sql)) {
// //             ResultSet rs = pstmt.executeQuery();
// //             if (rs.next()) {
// //                 int maxId = rs.getInt("maxId");
// //                 return maxId + 1;
// //             }
// //         } catch (SQLException e) {
// //             throw new RuntimeException("Lỗi khi lấy mã tự động: " + e.getMessage(), e);
// //         }
// //         return 1;
// //     }
// // }

// package DAO;

// import DTO.ChiTietDatPhongDTO;
// import config.ConnectDB;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;

// public class ChiTietDatPhongDAO implements DAOinterface<ChiTietDatPhongDTO> {

//     public static ChiTietDatPhongDAO getInstance() {
//         return new ChiTietDatPhongDAO();
//     }

//     @Override
//     public int add(ChiTietDatPhongDTO ctdp) {
//         String sql = "INSERT INTO CHITIETDATPHONG (maCTDP, maP, ngayThue, ngayTra, ngayCheckOut, loaiHinhThue, giaThue, tinhTrang) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setString(1, ctdp.getMaCTDP());
//             pstmt.setString(2, ctdp.getMaP());
//             pstmt.setTimestamp(3, ctdp.getNgayThue() != null ? new java.sql.Timestamp(ctdp.getNgayThue().getTime()) : null);
//             pstmt.setTimestamp(4, ctdp.getNgayTra() != null ? new java.sql.Timestamp(ctdp.getNgayTra().getTime()) : null);
//             pstmt.setTimestamp(5, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
//             pstmt.setInt(6, ctdp.getLoaiHinhThue());
//             pstmt.setInt(7, ctdp.getGiaThue());
//             pstmt.setInt(8, ctdp.getTinhTrang());
//             return pstmt.executeUpdate();
//         } catch (SQLException e) {
//             throw new RuntimeException("Lỗi khi thêm chi tiết đặt phòng: " + e.getMessage(), e);
//         }
//     }

//     public int add(ChiTietDatPhongDTO ctdp, Connection conn) throws SQLException {
//         String sql = "INSERT INTO CHITIETDATPHONG (maCTDP, maP, ngayThue, ngayTra, ngayCheckOut, loaiHinhThue, giaThue, tinhTrang) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//         try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setString(1, ctdp.getMaCTDP());
//             pstmt.setString(2, ctdp.getMaP());
//             pstmt.setTimestamp(3, ctdp.getNgayThue() != null ? new java.sql.Timestamp(ctdp.getNgayThue().getTime()) : null);
//             pstmt.setTimestamp(4, ctdp.getNgayTra() != null ? new java.sql.Timestamp(ctdp.getNgayTra().getTime()) : null);
//             pstmt.setTimestamp(5, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
//             pstmt.setInt(6, ctdp.getLoaiHinhThue());
//             pstmt.setInt(7, ctdp.getGiaThue());
//             pstmt.setInt(8, ctdp.getTinhTrang());
//             return pstmt.executeUpdate();
//         }
//     }

//     @Override
//     public int update(ChiTietDatPhongDTO ctdp) {
//         String sql = "UPDATE CHITIETDATPHONG SET maP = ?, ngayThue = ?, ngayTra = ?, ngayCheckOut = ?, loaiHinhThue = ?, giaThue = ?, tinhTrang = ? WHERE maCTDP = ?";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setString(1, ctdp.getMaP());
//             pstmt.setTimestamp(2, ctdp.getNgayThue() != null ? new java.sql.Timestamp(ctdp.getNgayThue().getTime()) : null);
//             pstmt.setTimestamp(3, ctdp.getNgayTra() != null ? new java.sql.Timestamp(ctdp.getNgayTra().getTime()) : null);
//             pstmt.setTimestamp(4, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
//             pstmt.setInt(5, ctdp.getLoaiHinhThue());
//             pstmt.setInt(6, ctdp.getGiaThue());
//             pstmt.setInt(7, ctdp.getTinhTrang());
//             pstmt.setString(8, ctdp.getMaCTDP());
//             return pstmt.executeUpdate();
//         } catch (SQLException e) {
//             throw new RuntimeException("Lỗi khi cập nhật chi tiết đặt phòng: " + e.getMessage(), e);
//         }
//     }

//     @Override
//     public int delete(String maCTDP) {
//         String sql = "DELETE FROM CHITIETDATPHONG WHERE maCTDP = ?";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setString(1, maCTDP);
//             return pstmt.executeUpdate();
//         } catch (SQLException e) {
//             throw new RuntimeException("Lỗi khi xóa chi tiết đặt phòng: " + e.getMessage(), e);
//         }
//     }

//     public int deleteByMaP(String maP) {
//         String sql = "DELETE FROM CHITIETDATPHONG WHERE maP = ?";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setString(1, maP);
//             return pstmt.executeUpdate();
//         } catch (SQLException e) {
//             throw new RuntimeException("Lỗi khi xóa chi tiết đặt phòng theo maP: " + e.getMessage(), e);
//         }
//     }

//     @Override
//     public ArrayList<ChiTietDatPhongDTO> selectAll() {
//         ArrayList<ChiTietDatPhongDTO> ctdpList = new ArrayList<>();
//         String sql = "SELECT * FROM CHITIETDATPHONG";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql);
//              ResultSet rs = pstmt.executeQuery()) {
//             while (rs.next()) {
//                 ChiTietDatPhongDTO ctdp = new ChiTietDatPhongDTO();
//                 ctdp.setMaCTDP(rs.getString("maCTDP"));
//                 ctdp.setMaP(rs.getString("maP"));
//                 ctdp.setNgayThue(rs.getTimestamp("ngayThue"));
//                 ctdp.setNgayTra(rs.getTimestamp("ngayTra"));
//                 ctdp.setNgayCheckOut(rs.getTimestamp("ngayCheckOut"));
//                 ctdp.setLoaiHinhThue(rs.getInt("loaiHinhThue"));
//                 ctdp.setGiaThue(rs.getInt("giaThue"));
//                 ctdp.setTinhTrang(rs.getInt("tinhTrang"));
//                 ctdpList.add(ctdp);
//             }
//         } catch (SQLException e) {
//             throw new RuntimeException("Lỗi khi lấy danh sách chi tiết đặt phòng: " + e.getMessage(), e);
//         }
//         return ctdpList;
//     }

//     @Override
//     public ChiTietDatPhongDTO selectById(String maCTDP) {
//         String sql = "SELECT * FROM CHITIETDATPHONG WHERE maCTDP = ?";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setString(1, maCTDP);
//             try (ResultSet rs = pstmt.executeQuery()) {
//                 if (rs.next()) {
//                     ChiTietDatPhongDTO ctdp = new ChiTietDatPhongDTO();
//                     ctdp.setMaCTDP(rs.getString("maCTDP"));
//                     ctdp.setMaP(rs.getString("maP"));
//                     ctdp.setNgayThue(rs.getTimestamp("ngayThue"));
//                     ctdp.setNgayTra(rs.getTimestamp("ngayTra"));
//                     ctdp.setNgayCheckOut(rs.getTimestamp("ngayCheckOut"));
//                     ctdp.setLoaiHinhThue(rs.getInt("loaiHinhThue"));
//                     ctdp.setGiaThue(rs.getInt("giaThue"));
//                     ctdp.setTinhTrang(rs.getInt("tinhTrang"));
//                     return ctdp;
//                 }
//             }
//         } catch (SQLException e) {
//             throw new RuntimeException("Lỗi khi lấy chi tiết đặt phòng theo maCTDP: " + e.getMessage(), e);
//         }
//         return null;
//     }

//     @Override
//     public int getAutoIncrement() {
//         String sql = "SELECT MAX(CAST(SUBSTRING(maCTDP, 3) AS UNSIGNED)) AS maxId FROM CHITIETDATPHONG";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             ResultSet rs = pstmt.executeQuery();
//             if (rs.next()) {
//                 int maxId = rs.getInt("maxId");
//                 return maxId + 1;
//             }
//         } catch (SQLException e) {
//             throw new RuntimeException("Lỗi khi lấy mã tự động: " + e.getMessage(), e);
//         }
//         return 1;
//     }
// }

package DAO;

import DTO.ChiTietDatPhongDTO;
import config.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChiTietDatPhongDAO implements DAOinterface<ChiTietDatPhongDTO> {

    public ChiTietDatPhongDAO() {
    }

    @Override
    public int add(ChiTietDatPhongDTO ctdp) {
        String sql = "INSERT INTO CHITIETDATPHONG (maCTDP, maP, ngayThue, ngayTra, ngayCheckOut, loaiHinhThue, giaThue, tinhTrang) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ctdp.getMaCTDP());
            pstmt.setString(2, ctdp.getMaP());
            pstmt.setTimestamp(3, ctdp.getNgayThue() != null ? new java.sql.Timestamp(ctdp.getNgayThue().getTime()) : null);
            pstmt.setTimestamp(4, ctdp.getNgayTra() != null ? new java.sql.Timestamp(ctdp.getNgayTra().getTime()) : null);
            pstmt.setTimestamp(5, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
            pstmt.setInt(6, ctdp.getLoaiHinhThue());
            pstmt.setInt(7, ctdp.getGiaThue());
            pstmt.setInt(8, ctdp.getTinhTrang());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm chi tiết đặt phòng: " + e.getMessage(), e);
        }
    }

    public int add(ChiTietDatPhongDTO ctdp, Connection conn) throws SQLException {
        String sql = "INSERT INTO CHITIETDATPHONG (maCTDP, maP, ngayThue, ngayTra, ngayCheckOut, loaiHinhThue, giaThue, tinhTrang) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ctdp.getMaCTDP());
            pstmt.setString(2, ctdp.getMaP());
            pstmt.setTimestamp(3, ctdp.getNgayThue() != null ? new java.sql.Timestamp(ctdp.getNgayThue().getTime()) : null);
            pstmt.setTimestamp(4, ctdp.getNgayTra() != null ? new java.sql.Timestamp(ctdp.getNgayTra().getTime()) : null);
            pstmt.setTimestamp(5, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
            pstmt.setInt(6, ctdp.getLoaiHinhThue());
            pstmt.setInt(7, ctdp.getGiaThue());
            pstmt.setInt(8, ctdp.getTinhTrang());
            return pstmt.executeUpdate();
        }
    }

    @Override
    public int update(ChiTietDatPhongDTO t) {
        Connection conn = ConnectDB.getConnection();
        if (conn == null)
            return 0;
    
        String query = "UPDATE CHITIETDATPHONG SET ngayThue = ?, ngayTra = ?, ngayCheckOut = ?, loaiHinhThue = ?, giaThue = ?, tinhTrang = ? "
                +
                "WHERE maCTDP = ? AND maP = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setTimestamp(1, t.getNgayThue() != null ? new java.sql.Timestamp(t.getNgayThue().getTime()) : null);
            ps.setTimestamp(2, t.getNgayTra() != null ? new java.sql.Timestamp(t.getNgayTra().getTime()) : null);
            ps.setTimestamp(3, t.getNgayCheckOut() != null ? new java.sql.Timestamp(t.getNgayCheckOut().getTime()) : null);
            ps.setInt(4, t.getLoaiHinhThue());
            ps.setInt(5, t.getGiaThue());
            ps.setInt(6, t.getTinhTrang());
            ps.setString(7, t.getMaCTDP());
            ps.setString(8, t.getMaP());
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
        if (conn == null)
            return 0;

        String query = "DELETE FROM CHITIETDATPHONG WHERE maCTDP = ? AND maP = ?";
        try {
            String[] keys = id.split(",");
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, keys[0]);
            ps.setString(2, keys[1]);
            int rowsAffected = ps.executeUpdate();
            conn.close();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<ChiTietDatPhongDTO> selectAll() {
        Connection conn = ConnectDB.getConnection();
        if (conn == null)
            return new ArrayList<>();
    
        ArrayList<ChiTietDatPhongDTO> details = new ArrayList<>();
        String query = "SELECT * FROM CHITIETDATPHONG";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietDatPhongDTO detail = new ChiTietDatPhongDTO(
                        rs.getString("maCTDP"),
                        rs.getString("maP"),
                        rs.getTimestamp("ngayThue"),
                        rs.getTimestamp("ngayTra"),
                        rs.getTimestamp("ngayCheckOut"),
                        rs.getInt("loaiHinhThue"),
                        rs.getInt("giaThue"),
                        rs.getInt("tinhTrang"));
                details.add(detail);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    @Override
    public ChiTietDatPhongDTO selectById(String id) {
        Connection conn = ConnectDB.getConnection();
        if (conn == null)
            return null;
    
        String query = "SELECT * FROM CHITIETDATPHONG WHERE maCTDP = ? AND maP = ?";
        try {
            String[] keys = id.split(",");
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, keys[0]);
            ps.setString(2, keys[1]);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ChiTietDatPhongDTO detail = new ChiTietDatPhongDTO(
                        rs.getString("maCTDP"),
                        rs.getString("maP"),
                        rs.getTimestamp("ngayThue"),
                        rs.getTimestamp("ngayTra"),
                        rs.getTimestamp("ngayCheckOut"),
                        rs.getInt("loaiHinhThue"),
                        rs.getInt("giaThue"),
                        rs.getInt("tinhTrang"));
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
        if (conn == null)
            return 0;

        String query = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlykhachsan' AND TABLE_NAME = 'CHITIETDATPHONG'";
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

    public int deleteByMaP(String maP) {
        String sql = "DELETE FROM CHITIETDATPHONG WHERE maP = ?";
        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maP);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa chi tiết đặt phòng theo maP: " + e.getMessage(), e);
        }
    }
}
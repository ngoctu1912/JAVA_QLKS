// package DAO;

// import DTO.HoaDonDTO;
// import config.ConnectDB;
// import java.sql.*;
// import java.util.ArrayList;

// public class HoaDonDAO implements DAOinterface<HoaDonDTO> {
//     private static HoaDonDAO instance;

//     public static HoaDonDAO getInstance() {
//         if (instance == null) {
//             instance = new HoaDonDAO();
//         }
//         return instance;
//     }

//     @Override
//     public int add(HoaDonDTO hd) {
//         String sql = "INSERT INTO HOADON (maHD, maCTT, tienP, tienDV, giamGia, phuThu, tongTien, ngayThanhToan, hinhThucThanhToan, xuLy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setString(1, hd.getMaHD());
//             ps.setString(2, hd.getMaCTT());
//             ps.setInt(3, hd.getTienP());
//             ps.setInt(4, hd.getTienDV());
//             ps.setInt(5, hd.getGiamGia());
//             ps.setInt(6, hd.getPhuThu());
//             ps.setInt(7, hd.getTongTien());
//             ps.setTimestamp(8, new Timestamp(hd.getNgayThanhToan().getTime()));
//             ps.setString(9, hd.getHinhThucThanhToan());
//             ps.setInt(10, hd.getXuLy());
//             return ps.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return 0;
//         }
//     }

//     @Override
//     public int update(HoaDonDTO hd) {
//         String sql = "UPDATE HOADON SET maCTT = ?, tienP = ?, tienDV = ?, giamGia = ?, phuThu = ?, tongTien = ?, ngayThanhToan = ?, hinhThucThanhToan = ?, xuLy = ? WHERE maHD = ?";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setString(1, hd.getMaCTT());
//             ps.setInt(2, hd.getTienP());
//             ps.setInt(3, hd.getTienDV());
//             ps.setInt(4, hd.getGiamGia());
//             ps.setInt(5, hd.getPhuThu());
//             ps.setInt(6, hd.getTongTien());
//             ps.setTimestamp(7, new Timestamp(hd.getNgayThanhToan().getTime()));
//             ps.setString(8, hd.getHinhThucThanhToan());
//             ps.setInt(9, hd.getXuLy());
//             ps.setString(10, hd.getMaHD());
//             return ps.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return 0;
//         }
//     }

//     @Override
//     public int delete(String maHD) {
//         String sql = "DELETE FROM HOADON WHERE maHD = ?";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setString(1, maHD);
//             return ps.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return 0;
//         }
//     }

//     @Override
//     public ArrayList<HoaDonDTO> selectAll() {
//         ArrayList<HoaDonDTO> list = new ArrayList<>();
//         String sql = "SELECT * FROM HOADON";
//         try (Connection conn = ConnectDB.getConnection();
//              Statement stmt = conn.createStatement();
//              ResultSet rs = stmt.executeQuery(sql)) {
//             while (rs.next()) {
//                 HoaDonDTO hd = new HoaDonDTO(
//                     rs.getString("maHD"),
//                     rs.getString("maCTT"),
//                     rs.getInt("tienP"),
//                     rs.getInt("tienDV"),
//                     rs.getInt("giamGia"),
//                     rs.getInt("phuThu"),
//                     rs.getInt("tongTien"),
//                     rs.getTimestamp("ngayThanhToan"),
//                     rs.getString("hinhThucThanhToan"),
//                     rs.getInt("xuLy")
//                 );
//                 list.add(hd);
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return list;
//     }

//     @Override
//     public HoaDonDTO selectById(String maHD) {
//         String sql = "SELECT * FROM HOADON WHERE maHD = ?";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setString(1, maHD);
//             ResultSet rs = ps.executeQuery();
//             if (rs.next()) {
//                 return new HoaDonDTO(
//                     rs.getString("maHD"),
//                     rs.getString("maCTT"),
//                     rs.getInt("tienP"),
//                     rs.getInt("tienDV"),
//                     rs.getInt("giamGia"),
//                     rs.getInt("phuThu"),
//                     rs.getInt("tongTien"),
//                     rs.getTimestamp("ngayThanhToan"),
//                     rs.getString("hinhThucThanhToan"),
//                     rs.getInt("xuLy")
//                 );
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return null;
//     }

//     @Override
//     public int getAutoIncrement() {
//         String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'quanlykhachsan' AND TABLE_NAME = 'HOADON'";
//         try (Connection conn = ConnectDB.getConnection();
//              Statement stmt = conn.createStatement();
//              ResultSet rs = stmt.executeQuery(sql)) {
//             if (rs.next()) {
//                 return rs.getInt("AUTO_INCREMENT");
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return 0;
//     }
    
//     public int cancel(String maHD) {
//         String sql = "UPDATE HOADON SET XULY = 0 WHERE MAHD = ?";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, maHD);
//             return stmt.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return 0;
//         }
//     }
// }

package DAO;

import DTO.HoaDonDTO;
import config.ConnectDB;
import java.sql.*;
import java.util.ArrayList;

public class HoaDonDAO implements DAOinterface<HoaDonDTO> {
    private static HoaDonDAO instance;

    public static HoaDonDAO getInstance() {
        if (instance == null) {
            instance = new HoaDonDAO();
        }
        return instance;
    }

    public int add(HoaDonDTO hd, Connection conn) throws SQLException {
        String sql = "INSERT INTO HOADON (maHD, maCTT, tienP, tienDV, giamGia, phuThu, tongTien, ngayThanhToan, hinhThucThanhToan, xuLy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hd.getMaHD());
            ps.setString(2, hd.getMaCTT());
            ps.setInt(3, hd.getTienP());
            ps.setInt(4, hd.getTienDV());
            ps.setInt(5, hd.getGiamGia());
            ps.setInt(6, hd.getPhuThu());
            ps.setInt(7, hd.getTongTien());
            ps.setTimestamp(8, new Timestamp(hd.getNgayThanhToan().getTime()));
            ps.setString(9, hd.getHinhThucThanhToan());
            ps.setInt(10, hd.getXuLy());
            return ps.executeUpdate();
        }
    }

    @Override
    public int add(HoaDonDTO hd) {
        try (Connection conn = ConnectDB.getConnection()) {
            return add(hd, conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(HoaDonDTO hd) {
        String sql = "UPDATE HOADON SET maCTT = ?, tienP = ?, tienDV = ?, giamGia = ?, phuThu = ?, tongTien = ?, ngayThanhToan = ?, hinhThucThanhToan = ?, xuLy = ? WHERE maHD = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hd.getMaCTT());
            ps.setInt(2, hd.getTienP());
            ps.setInt(3, hd.getTienDV());
            ps.setInt(4, hd.getGiamGia());
            ps.setInt(5, hd.getPhuThu());
            ps.setInt(6, hd.getTongTien());
            ps.setTimestamp(7, new Timestamp(hd.getNgayThanhToan().getTime()));
            ps.setString(8, hd.getHinhThucThanhToan());
            ps.setInt(9, hd.getXuLy());
            ps.setString(10, hd.getMaHD());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(String maHD) {
        String sql = "DELETE FROM HOADON WHERE maHD = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHD);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<HoaDonDTO> selectAll() {
        ArrayList<HoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM HOADON";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO(
                    rs.getString("maHD"),
                    rs.getString("maCTT"),
                    rs.getInt("tienP"),
                    rs.getInt("tienDV"),
                    rs.getInt("giamGia"),
                    rs.getInt("phuThu"),
                    rs.getInt("tongTien"),
                    rs.getTimestamp("ngayThanhToan"),
                    rs.getString("hinhThucThanhToan"),
                    rs.getInt("xuLy")
                );
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public HoaDonDTO selectById(String maHD) {
        String sql = "SELECT * FROM HOADON WHERE maHD = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new HoaDonDTO(
                    rs.getString("maHD"),
                    rs.getString("maCTT"),
                    rs.getInt("tienP"),
                    rs.getInt("tienDV"),
                    rs.getInt("giamGia"),
                    rs.getInt("phuThu"),
                    rs.getInt("tongTien"),
                    rs.getTimestamp("ngayThanhToan"),
                    rs.getString("hinhThucThanhToan"),
                    rs.getInt("xuLy")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getAutoIncrement() {
        String sql = "SELECT MAX(CAST(SUBSTRING(maHD, 3) AS UNSIGNED)) AS max_id FROM HOADON WHERE maHD LIKE 'HD%'";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("max_id") + 1; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    
    public int cancel(String maHD) {
        String sql = "UPDATE HOADON SET XULY = 0 WHERE MAHD = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maHD);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
package DAO;

import DTO.DatPhongDTO;
import config.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatPhongDAO implements DAOinterface<DatPhongDTO> {

    public static DatPhongDAO getInstance() {
        return new DatPhongDAO();
    }

    @Override
    public int add(DatPhongDTO datPhong) {
        String sql = "INSERT INTO DATPHONG (maDP, maKH, ngayLapPhieu, tienDatCoc, tinhTrangXuLy, xuLy) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, datPhong.getMaDP());
            pstmt.setInt(2, datPhong.getMaKH());
            pstmt.setTimestamp(3, datPhong.getNgayLapPhieu() != null ? new java.sql.Timestamp(datPhong.getNgayLapPhieu().getTime()) : null);
            pstmt.setInt(4, datPhong.getTienDatCoc());
            pstmt.setInt(5, datPhong.getTinhTrangXuLy());
            pstmt.setInt(6, datPhong.getXuLy());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(DatPhongDTO datPhong) {
        String sql = "UPDATE DATPHONG SET maKH = ?, ngayLapPhieu = ?, tienDatCoc = ?, tinhTrangXuLy = ?, xuLy = ? WHERE maDP = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, datPhong.getMaKH());
            pstmt.setTimestamp(2, datPhong.getNgayLapPhieu() != null ? new java.sql.Timestamp(datPhong.getNgayLapPhieu().getTime()) : null);
            pstmt.setInt(3, datPhong.getTienDatCoc());
            pstmt.setInt(4, datPhong.getTinhTrangXuLy());
            pstmt.setInt(5, datPhong.getXuLy());
            pstmt.setString(6, datPhong.getMaDP());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(String maDP) {
        String sql = "DELETE FROM DATPHONG WHERE maDP = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maDP);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<DatPhongDTO> selectAll() {
        ArrayList<DatPhongDTO> datPhongList = new ArrayList<>();
        String sql = "SELECT * FROM DATPHONG";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                DatPhongDTO dp = new DatPhongDTO(
                    rs.getString("maDP"),
                    rs.getInt("maKH"),
                    rs.getTimestamp("ngayLapPhieu"),
                    rs.getInt("tienDatCoc"),
                    rs.getInt("tinhTrangXuLy"),
                    rs.getInt("xuLy")
                );
                datPhongList.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datPhongList;
    }

    @Override
    public DatPhongDTO selectById(String maDP) {
        String sql = "SELECT * FROM DATPHONG WHERE maDP = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maDP);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new DatPhongDTO(
                    rs.getString("maDP"),
                    rs.getInt("maKH"),
                    rs.getTimestamp("ngayLapPhieu"),
                    rs.getInt("tienDatCoc"),
                    rs.getInt("tinhTrangXuLy"),
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
        String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = 'DATPHONG'";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean checkExists(String maDP) throws SQLException {
        String query = "SELECT COUNT(*) FROM DATPHONG WHERE maDP = ?";
        try (Connection conn = ConnectDB.getConnection(); // Assumes ConnectDB is your DB utility
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maDP);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}


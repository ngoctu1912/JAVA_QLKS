package DAO;

import DTO.ChiTietDatPhongDTO;
import config.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietDatPhongDAO implements DAOinterface<ChiTietDatPhongDTO> {

    public static ChiTietDatPhongDAO getInstance() {
        return new ChiTietDatPhongDAO();
    }

    @Override
    public int add(ChiTietDatPhongDTO ctdp) {
        String sql = "INSERT INTO CHITIETDATPHONG (maCTDP, maDP, maP, ngayThue, ngayTra, ngayCheckOut, loaiHinhThue, giaThue, tinhTrang) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ctdp.getMaCTDP());
            pstmt.setString(3, ctdp.getMaP());
            pstmt.setTimestamp(4, ctdp.getNgayThue() != null ? new java.sql.Timestamp(ctdp.getNgayThue().getTime()) : null);
            pstmt.setTimestamp(5, ctdp.getNgayTra() != null ? new java.sql.Timestamp(ctdp.getNgayTra().getTime()) : null);
            pstmt.setTimestamp(6, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
            pstmt.setInt(7, ctdp.getLoaiHinhThue());
            pstmt.setInt(8, ctdp.getGiaThue());
            pstmt.setInt(9, ctdp.getTinhTrang());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm chi tiết đặt phòng: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(ChiTietDatPhongDTO ctdp) {
        String sql = "UPDATE CHITIETDATPHONG SET maDP = ?, maP = ?, ngayThue = ?, ngayTra = ?, ngayCheckOut = ?, loaiHinhThue = ?, giaThue = ?, tinhTrang = ? WHERE maCTDP = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(2, ctdp.getMaP());
            pstmt.setTimestamp(3, ctdp.getNgayThue() != null ? new java.sql.Timestamp(ctdp.getNgayThue().getTime()) : null);
            pstmt.setTimestamp(4, ctdp.getNgayTra() != null ? new java.sql.Timestamp(ctdp.getNgayTra().getTime()) : null);
            pstmt.setTimestamp(5, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
            pstmt.setInt(6, ctdp.getLoaiHinhThue());
            pstmt.setInt(7, ctdp.getGiaThue());
            pstmt.setInt(8, ctdp.getTinhTrang());
            pstmt.setString(9, ctdp.getMaCTDP());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật chi tiết đặt phòng: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(String maCTDP) {
        String sql = "DELETE FROM CHITIETDATPHONG WHERE maCTDP = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maCTDP);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa chi tiết đặt phòng: " + e.getMessage(), e);
        }
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

    @Override
    public ArrayList<ChiTietDatPhongDTO> selectAll() {
        ArrayList<ChiTietDatPhongDTO> ctdpList = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETDATPHONG";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ChiTietDatPhongDTO ctdp = new ChiTietDatPhongDTO();
                ctdp.setMaCTDP(rs.getString("maCTDP"));
                ctdp.setMaP(rs.getString("maP"));
                ctdp.setNgayThue(rs.getTimestamp("ngayThue"));
                ctdp.setNgayTra(rs.getTimestamp("ngayTra"));
                ctdp.setNgayCheckOut(rs.getTimestamp("ngayCheckOut"));
                ctdp.setLoaiHinhThue(rs.getInt("loaiHinhThue"));
                ctdp.setGiaThue(rs.getInt("giaThue"));
                ctdp.setTinhTrang(rs.getInt("tinhTrang"));
                ctdpList.add(ctdp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách chi tiết đặt phòng: " + e.getMessage(), e);
        }
        return ctdpList;
    }

    @Override
    public ChiTietDatPhongDTO selectById(String maCTDP) {
        String sql = "SELECT * FROM CHITIETDATPHONG WHERE maCTDP = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maCTDP);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ChiTietDatPhongDTO ctdp = new ChiTietDatPhongDTO();
                    ctdp.setMaCTDP(rs.getString("maCTDP"));
                    ctdp.setMaP(rs.getString("maP"));
                    ctdp.setNgayThue(rs.getTimestamp("ngayThue"));
                    ctdp.setNgayTra(rs.getTimestamp("ngayTra"));
                    ctdp.setNgayCheckOut(rs.getTimestamp("ngayCheckOut"));
                    ctdp.setLoaiHinhThue(rs.getInt("loaiHinhThue"));
                    ctdp.setGiaThue(rs.getInt("giaThue"));
                    ctdp.setTinhTrang(rs.getInt("tinhTrang"));
                    return ctdp;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy chi tiết đặt phòng theo maCTDP: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public int getAutoIncrement() {
        String sql = "SELECT MAX(CAST(SUBSTRING(maCTDP, 5) AS UNSIGNED)) AS maxId FROM CHITIETDATPHONG";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int maxId = rs.getInt("maxId");
                return maxId + 1; // Tăng số thứ tự lên 1
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy mã tự động: " + e.getMessage(), e);
        }
        return 1; // Nếu bảng rỗng, bắt đầu từ 1
    }
}
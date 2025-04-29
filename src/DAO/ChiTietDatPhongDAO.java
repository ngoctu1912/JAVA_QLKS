package DAO;

import DTO.ChiTietDatPhongDTO;
import config.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietDatPhongDAO implements DAOinterface<ChiTietDatPhongDTO> {

    @Override
    public int add(ChiTietDatPhongDTO ctdp) {
        String sql = "INSERT INTO CHITIETDATPHONG (maCTDP, maP, ngayThue, ngayTra, ngayCheckOut, loaiHinhThue, giaThue, tinhTrang) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ctdp.getMaCTDP());
            pstmt.setString(2, ctdp.getMaP());
            pstmt.setTimestamp(3, new java.sql.Timestamp(ctdp.getNgayThue().getTime()));
            pstmt.setTimestamp(4, new java.sql.Timestamp(ctdp.getNgayTra().getTime()));
            pstmt.setTimestamp(5, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
            pstmt.setInt(6, ctdp.getLoaiHinhThue());
            pstmt.setInt(7, ctdp.getGiaThue());
            pstmt.setInt(8, ctdp.getTinhTrang());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm chi tiết đặt phòng: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(ChiTietDatPhongDTO ctdp) {
        String sql = "UPDATE CHITIETDATPHONG SET ngayTra = ?, ngayCheckOut = ?, loaiHinhThue = ?, giaThue = ?, tinhTrang = ? WHERE maCTDP = ? AND maP = ? AND ngayThue = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, new java.sql.Timestamp(ctdp.getNgayTra().getTime()));
            pstmt.setTimestamp(2, ctdp.getNgayCheckOut() != null ? new java.sql.Timestamp(ctdp.getNgayCheckOut().getTime()) : null);
            pstmt.setInt(3, ctdp.getLoaiHinhThue());
            pstmt.setInt(4, ctdp.getGiaThue());
            pstmt.setInt(5, ctdp.getTinhTrang());
            pstmt.setString(6, ctdp.getMaCTDP());
            pstmt.setString(7, ctdp.getMaP());
            pstmt.setTimestamp(8, new java.sql.Timestamp(ctdp.getNgayThue().getTime()));
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

    // Thêm phương thức deleteByMaP để xóa các bản ghi theo maP
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
            ResultSet rs = pstmt.executeQuery();
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
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy chi tiết đặt phòng theo maCTDP: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public int getAutoIncrement() {
        // Nếu cần sinh mã tự động, bạn có thể sửa lại logic tại đây
        return 0;
    }
}
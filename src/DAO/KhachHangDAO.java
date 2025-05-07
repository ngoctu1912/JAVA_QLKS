package DAO;

import DTO.KhachHangDTO;
import config.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KhachHangDAO implements DAOinterface<KhachHangDTO> {

    public static KhachHangDAO getInstance() {
        return new KhachHangDAO();
    }

    @Override
    public int add(KhachHangDTO khachHang) {
        String sql = "INSERT INTO KHACHHANG (MKH, TKH, GT, CCCD, DIACHI, SDT, EMAIL, TT, NS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, khachHang.getMaKhachHang());
            pstmt.setString(2, khachHang.getTenKhachHang());
            pstmt.setInt(3, khachHang.getGioiTinh());
            pstmt.setLong(4, khachHang.getCccd());
            pstmt.setString(5, khachHang.getDiaChi());
            pstmt.setString(6, khachHang.getSoDienThoai());
            pstmt.setString(7, khachHang.getEmail());
            pstmt.setInt(8, khachHang.getTrangThai());
            pstmt.setDate(9, khachHang.getNgaySinh() != null ? new java.sql.Date(khachHang.getNgaySinh().getTime()) : null);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(KhachHangDTO khachHang) {
        String sql = "UPDATE KHACHHANG SET TKH = ?, GT = ?, CCCD = ?, DIACHI = ?, SDT = ?, EMAIL = ?, TT = ?, NS = ? WHERE MKH = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, khachHang.getTenKhachHang());
            pstmt.setInt(2, khachHang.getGioiTinh());
            pstmt.setLong(3, khachHang.getCccd());
            pstmt.setString(4, khachHang.getDiaChi());
            pstmt.setString(5, khachHang.getSoDienThoai());
            pstmt.setString(6, khachHang.getEmail());
            pstmt.setInt(7, khachHang.getTrangThai());
            pstmt.setDate(8, khachHang.getNgaySinh() != null ? new java.sql.Date(khachHang.getNgaySinh().getTime()) : null);
            pstmt.setInt(9, khachHang.getMaKhachHang());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(String maKhachHang) {
        String sql = "DELETE FROM KHACHHANG WHERE MKH = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(maKhachHang));
            return pstmt.executeUpdate();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<KhachHangDTO> selectAll() {
        ArrayList<KhachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KHACHHANG";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO();
                kh.setMaKhachHang(rs.getInt("MKH"));
                kh.setTenKhachHang(rs.getString("TKH"));
                kh.setGioiTinh(rs.getInt("GT"));
                kh.setCccd(rs.getLong("CCCD")); 
                kh.setDiaChi(rs.getString("DIACHI"));
                kh.setSoDienThoai(rs.getString("SDT"));
                kh.setEmail(rs.getString("EMAIL"));
                kh.setTrangThai(rs.getInt("TT"));
                kh.setNgaySinh(rs.getDate("NS"));
                khachHangList.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khachHangList;
    }

    public ArrayList<KhachHangDTO> selectByTTActive() {
        ArrayList<KhachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KHACHHANG WHERE TT = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO();
                kh.setMaKhachHang(rs.getInt("MKH"));
                kh.setTenKhachHang(rs.getString("TKH"));
                kh.setGioiTinh(rs.getInt("GT"));
                kh.setCccd(rs.getLong("CCCD")); 
                kh.setDiaChi(rs.getString("DIACHI"));
                kh.setSoDienThoai(rs.getString("SDT"));
                kh.setEmail(rs.getString("EMAIL"));
                kh.setTrangThai(rs.getInt("TT"));
                kh.setNgaySinh(rs.getDate("NS"));
                khachHangList.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khachHangList;
    }

    @Override
    public KhachHangDTO selectById(String maKhachHang) {
        String sql = "SELECT * FROM KHACHHANG WHERE MKH = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(maKhachHang));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO();
                kh.setMaKhachHang(rs.getInt("MKH"));
                kh.setTenKhachHang(rs.getString("TKH"));
                kh.setGioiTinh(rs.getInt("GT"));
                kh.setCccd(rs.getLong("CCCD"));
                kh.setDiaChi(rs.getString("DIACHI"));
                kh.setSoDienThoai(rs.getString("SDT"));
                kh.setEmail(rs.getString("EMAIL"));
                kh.setTrangThai(rs.getInt("TT"));
                kh.setNgaySinh(rs.getDate("NS"));
                return kh;
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getAutoIncrement() {
        String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = 'KHACHHANG'";
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
}
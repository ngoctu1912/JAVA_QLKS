package DAO;

import DTO.NhanVienDTO;
import config.ConnectDB;
import java.sql.*;
import java.util.ArrayList;

public class NhanVienDAO implements DAOinterface<NhanVienDTO> {

    public static NhanVienDAO getInstance(){
        return new NhanVienDAO();
    }

    @Override
    public int add(NhanVienDTO nv) {
        String sql = "INSERT INTO NHANVIEN (HOTEN, GIOITINH, NGAYSINH, SDT, EMAIL, TT, SNP, NVL, LN) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nv.getHoTen());
            stmt.setInt(2, nv.getGioiTinh());
            stmt.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));
            stmt.setString(4, nv.getSoDienThoai());
            stmt.setString(5, nv.getEmail());
            stmt.setInt(6, nv.getTrangThai());
            stmt.setInt(7, nv.getSoNgayPhep());
            stmt.setTimestamp(8, (Timestamp) nv.getNgayVaoLam());
            stmt.setInt(9, nv.getLuongNgay());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi thêm nhân viên: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int update(NhanVienDTO nv) {
        String sql = "UPDATE NHANVIEN SET HOTEN = ?, GIOITINH = ?, NGAYSINH = ?, SDT = ?, EMAIL = ?, TT = ?, SNP = ?, NVL = ?, LN = ? WHERE MNV = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nv.getHoTen());
            stmt.setInt(2, nv.getGioiTinh());
            stmt.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));
            stmt.setString(4, nv.getSoDienThoai());
            stmt.setString(5, nv.getEmail());
            stmt.setInt(6, nv.getTrangThai());
            stmt.setInt(7, nv.getSoNgayPhep());
            stmt.setTimestamp(8, (Timestamp) nv.getNgayVaoLam());
            stmt.setInt(9, nv.getLuongNgay());
            stmt.setInt(10, nv.getMaNhanVien());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật nhân viên: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int delete(String maNVStr) {
        try {
            int maNV = Integer.parseInt(maNVStr);
            String sql = "DELETE FROM NHANVIEN WHERE MNV = ?";
            try (Connection conn = ConnectDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, maNV);
                return stmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Lỗi xóa nhân viên: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public ArrayList<NhanVienDTO> selectAll() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NHANVIEN";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNhanVien(rs.getInt("MNV"));
                nv.setHoTen(rs.getString("HOTEN"));
                nv.setGioiTinh(rs.getInt("GIOITINH"));
                nv.setNgaySinh(rs.getDate("NGAYSINH"));
                nv.setSoDienThoai(rs.getString("SDT"));
                nv.setEmail(rs.getString("EMAIL"));
                nv.setTrangThai(rs.getInt("TT"));
                nv.setSoNgayPhep(rs.getInt("SNP"));
                nv.setNgayVaoLam(rs.getTimestamp("NVL"));
                nv.setLuongNgay(rs.getInt("LN"));
                list.add(nv);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách nhân viên: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<NhanVienDTO> selectAllActive() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NHANVIEN WHERE TT = 1";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNhanVien(rs.getInt("MNV"));
                nv.setHoTen(rs.getString("HOTEN"));
                nv.setGioiTinh(rs.getInt("GIOITINH"));
                nv.setNgaySinh(rs.getDate("NGAYSINH"));
                nv.setSoDienThoai(rs.getString("SDT"));
                nv.setEmail(rs.getString("EMAIL"));
                nv.setTrangThai(rs.getInt("TT"));
                nv.setSoNgayPhep(rs.getInt("SNP"));
                nv.setNgayVaoLam(rs.getTimestamp("NVL"));
                nv.setLuongNgay(rs.getInt("LN"));
                list.add(nv);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách nhân viên đang hoạt động: " + e.getMessage());
        }
        return list;
    }    

    @Override
    public NhanVienDTO selectById(String maNVStr) {
        try {
            int maNV = Integer.parseInt(maNVStr);
            String sql = "SELECT * FROM NHANVIEN WHERE MNV = ?";
            try (Connection conn = ConnectDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, maNV);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        NhanVienDTO nv = new NhanVienDTO();
                        nv.setMaNhanVien(rs.getInt("MNV"));
                        nv.setHoTen(rs.getString("HOTEN"));
                        nv.setGioiTinh(rs.getInt("GIOITINH"));
                        nv.setNgaySinh(rs.getDate("NGAYSINH"));
                        nv.setSoDienThoai(rs.getString("SDT"));
                        nv.setEmail(rs.getString("EMAIL"));
                        nv.setTrangThai(rs.getInt("TT"));
                        nv.setSoNgayPhep(rs.getInt("SNP"));
                        nv.setNgayVaoLam(rs.getTimestamp("NVL"));
                        nv.setLuongNgay(rs.getInt("LN"));
                        return nv;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi lấy nhân viên theo ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int getAutoIncrement() {
        String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES " +
                     "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'NHANVIEN'";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy AUTO_INCREMENT nhân viên: " + e.getMessage());
        }
        return -1;
    }

    public NhanVienDTO selectNhanVienByUsername(String username) {
        NhanVienDTO result = null;
        String query = "SELECT NV.* FROM NHANVIEN NV " +
                       "JOIN TAIKHOAN TK ON NV.MNV = TK.MNV " +
                       "WHERE TK.TDN = ? AND NV.TT = 1";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = new NhanVienDTO();
                    result.setMaNhanVien(rs.getInt("MNV"));
                    result.setHoTen(rs.getString("HOTEN"));
                    result.setGioiTinh(rs.getInt("GIOITINH"));
                    result.setNgaySinh(rs.getDate("NGAYSINH"));
                    result.setSoDienThoai(rs.getString("SDT"));
                    result.setEmail(rs.getString("EMAIL"));
                    result.setTrangThai(rs.getInt("TT"));
                    result.setSoNgayPhep(rs.getInt("SNP"));
                    result.setNgayVaoLam(rs.getTimestamp("NVL"));
                    result.setLuongNgay(rs.getInt("LN"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy nhân viên theo username: " + e.getMessage());
        }
        return result;
    }
}

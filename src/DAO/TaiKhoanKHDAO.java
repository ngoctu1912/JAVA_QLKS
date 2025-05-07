package DAO;

import DTO.TaiKhoanKHDTO;
import config.ConnectDB;
import java.sql.*;
import java.util.ArrayList;

public class TaiKhoanKHDAO implements DAOinterface<TaiKhoanKHDTO> {

    public static TaiKhoanKHDAO getInstance(){
        return new TaiKhoanKHDAO();
    }

    @Override
    public int add(TaiKhoanKHDTO tk) {
        String sql = "INSERT INTO TAIKHOANKH (MKH, MK, TDN, MNQ, TT) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tk.getMaKhachHang());
            stmt.setString(2, tk.getMatKhau());
            stmt.setString(3, tk.getTenDangNhap());
            stmt.setInt(4, tk.getMaNhomQuyen());
            stmt.setInt(5, tk.getTrangThai());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi thêm tài khoản KH: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int update(TaiKhoanKHDTO tk) {
        String sql = "UPDATE TAIKHOANKH SET MK = ?, MNQ = ?, TT = ? WHERE MKH = ? AND TDN = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tk.getMatKhau());
            stmt.setInt(2, tk.getMaNhomQuyen());
            stmt.setInt(3, tk.getTrangThai());
            stmt.setInt(4, tk.getMaKhachHang());
            stmt.setString(5, tk.getTenDangNhap());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật tài khoản KH: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int delete(String tdn) {
        String sql = "DELETE FROM TAIKHOANKH WHERE TDN = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tdn);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi xóa tài khoản KH: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public ArrayList<TaiKhoanKHDTO> selectAll() {
        ArrayList<TaiKhoanKHDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOANKH";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TaiKhoanKHDTO tk = new TaiKhoanKHDTO(
                        rs.getInt("MKH"),
                        rs.getString("MK"),
                        rs.getString("TDN"),
                        rs.getInt("MNQ"),
                        rs.getInt("TT")
                );
                ds.add(tk);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách tài khoản KH: " + e.getMessage());
        }
        return ds;
    }

    @Override
    public TaiKhoanKHDTO selectById(String maKhachHang) {
        String sql = "SELECT * FROM TAIKHOANKH WHERE MKH = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKhachHang);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TaiKhoanKHDTO(
                            rs.getInt("MKH"),
                            rs.getString("MK"),
                            rs.getString("TDN"),
                            rs.getInt("MNQ"),
                            rs.getInt("TT")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm tài khoản KH theo MKH: " + e.getMessage());
        }
        return null;
    }

    public TaiKhoanKHDTO selectByUser(String tdn) {
        String sql = "SELECT * FROM TAIKHOANKH WHERE TDN = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tdn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TaiKhoanKHDTO(
                            rs.getInt("MKH"),
                            rs.getString("MK"),
                            rs.getString("TDN"),
                            rs.getInt("MNQ"),
                            rs.getInt("TT")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm tài khoản KH theo TDN: " + e.getMessage());
        }
        return null;
    }

    public int getAutoIncrement() {
        String sql = "SELECT MAX(MKH) AS max_id FROM TAIKHOANKH";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return rs.wasNull() ? 1 : maxId + 1;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy mã khách hàng tiếp theo: " + e.getMessage());
        }
        return 1;
    }

}
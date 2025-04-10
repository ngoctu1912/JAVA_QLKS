package DAO;

import DTO.TaiKhoanDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class TaiKhoanDAO {
    private Connection conn;

    public TaiKhoanDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean add(TaiKhoanDTO tk) {
        String sql = "INSERT INTO TAIKHOAN (MNV, MK, TDN, MNQ, TT) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tk.getMaNV());
            stmt.setString(2, tk.getMatKhau());
            stmt.setString(3, tk.getTenDangNhap());
            stmt.setInt(4, tk.getMaNhomQuyen());
            stmt.setInt(5, tk.getTrangThai());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public TaiKhoanDTO getByMaNV(int maNV) {
        String sql = "SELECT * FROM TAIKHOAN WHERE MNV = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TaiKhoanDTO(
                        rs.getInt("MNV"),
                        rs.getString("TDN"),
                        rs.getString("MK"),
                        rs.getInt("MNQ"),
                        rs.getInt("TT")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Phương thức để lấy tài khoản theo tên đăng nhập
    public TaiKhoanDTO getByTenDangNhap(String tenDangNhap) {
        String sql = "SELECT * FROM TAIKHOAN WHERE TDN = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenDangNhap);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TaiKhoanDTO(
                        rs.getInt("MNV"),
                        rs.getString("TDN"),
                        rs.getString("MK"),
                        rs.getInt("MNQ"),
                        rs.getInt("TT")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TaiKhoanDTO> getAllAccounts() {
        List<TaiKhoanDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOAN";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new TaiKhoanDTO(
                        rs.getInt("MNV"),
                        rs.getString("TDN"),
                        rs.getString("MK"),
                        rs.getInt("MNQ"),
                        rs.getInt("TT")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(TaiKhoanDTO tk) {
        String sql = "UPDATE TAIKHOAN SET TDN = ?, MK = ?, MNQ = ?, TT = ? WHERE MNV = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tk.getTenDangNhap());
            stmt.setString(2, tk.getMatKhau());
            stmt.setInt(3, tk.getMaNhomQuyen());
            stmt.setInt(4, tk.getTrangThai());
            stmt.setInt(5, tk.getMaNV());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(int maNV, String matKhauMoi) {
        String sql = "UPDATE TAIKHOAN SET MK = ? WHERE MNV = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matKhauMoi);
            stmt.setInt(2, maNV);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int maNV) {
        String sql = "DELETE FROM TAIKHOAN WHERE MNV = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maNV);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAccountExists(int maNV) {
        String sql = "SELECT 1 FROM TAIKHOAN WHERE MNV = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maNV);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM TAIKHOAN WHERE TDN = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("MK");
                return BCrypt.checkpw(password, hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
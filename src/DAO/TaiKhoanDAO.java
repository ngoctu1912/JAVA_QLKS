// package DAO;

// import DTO.TaiKhoanDTO;
// import config.ConnectDB;
// import org.mindrot.jbcrypt.BCrypt;

// import java.sql.*;
// import java.util.ArrayList;

// public class TaiKhoanDAO implements DAOinterface<TaiKhoanDTO> {

//     public static TaiKhoanDAO getInstance(){
//         return new TaiKhoanDAO();
//     }

//     @Override
//     public int add(TaiKhoanDTO tk) {
//         String sql = "INSERT INTO TAIKHOAN (MNV, MK, TDN, MNQ, TT) VALUES (?, ?, ?, ?, ?)";
//         Connection conn = null;
//         try {
//             conn = ConnectDB.getConnection();
//             if (conn == null) {
//                 throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
//             }
//             try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                 stmt.setInt(1, tk.getMaNV());
//                 stmt.setString(2, tk.getMatKhau());
//                 stmt.setString(3, tk.getTenDangNhap());
//                 stmt.setInt(4, tk.getMaNhomQuyen());
//                 stmt.setInt(5, tk.getTrangThai());
//                 return stmt.executeUpdate();
//             }
//         } catch (SQLException e) {
//             System.err.println("Lỗi khi thêm tài khoản: " + e.getMessage());
//         } finally {
//             ConnectDB.closeConnection();
//         }
//         return 0;
//     }

//     @Override
//     public int update(TaiKhoanDTO tk) {
//         String sql = "UPDATE TAIKHOAN SET TDN = ?, MK = ?, MNQ = ?, TT = ? WHERE MNV = ?";
//         Connection conn = null;
//         try {
//             conn = ConnectDB.getConnection();
//             if (conn == null) {
//                 throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
//             }
//             try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                 stmt.setString(1, tk.getTenDangNhap());
//                 stmt.setString(2, tk.getMatKhau());
//                 stmt.setInt(3, tk.getMaNhomQuyen());
//                 stmt.setInt(4, tk.getTrangThai());
//                 stmt.setInt(5, tk.getMaNV());
//                 return stmt.executeUpdate();
//             }
//         } catch (SQLException e) {
//             System.err.println("Lỗi khi cập nhật tài khoản: " + e.getMessage());
//         } finally {
//             ConnectDB.closeConnection();
//         }
//         return 0;
//     }

//     @Override
//     public int delete(String maNVStr) {
//         Connection conn = null;
//         try {
//             int maNV = Integer.parseInt(maNVStr);
//             String sql = "DELETE FROM TAIKHOAN WHERE MNV = ?";
//             conn = ConnectDB.getConnection();
//             if (conn == null) {
//                 throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
//             }
//             try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                 stmt.setInt(1, maNV);
//                 return stmt.executeUpdate();
//             }
//         } catch (NumberFormatException e) {
//             System.err.println("Mã NV không hợp lệ: " + maNVStr);
//         } catch (SQLException e) {
//             System.err.println("Lỗi khi xóa tài khoản: " + e.getMessage());
//         } finally {
//             ConnectDB.closeConnection();
//         }
//         return 0;
//     }

//     @Override
//     public ArrayList<TaiKhoanDTO> selectAll() {
//         ArrayList<TaiKhoanDTO> list = new ArrayList<>();
//         String sql = "SELECT * FROM TAIKHOAN";
//         Connection conn = null;
//         try {
//             conn = ConnectDB.getConnection();
//             if (conn == null) {
//                 throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
//             }
//             try (Statement stmt = conn.createStatement();
//                  ResultSet rs = stmt.executeQuery(sql)) {
//                 while (rs.next()) {
//                     list.add(new TaiKhoanDTO(
//                             rs.getInt("MNV"),
//                             rs.getString("TDN"),
//                             rs.getString("MK"),
//                             rs.getInt("MNQ"),
//                             rs.getInt("TT")
//                     ));
//                 }
//             }
//         } catch (SQLException e) {
//             System.err.println("Lỗi khi lấy danh sách tài khoản: " + e.getMessage());
//         } finally {
//             ConnectDB.closeConnection();
//         }
//         return list;
//     }

//     @Override
//     public TaiKhoanDTO selectById(String maNVStr) {
//         Connection conn = null;
//         try {
//             int maNV = Integer.parseInt(maNVStr);
//             String sql = "SELECT * FROM TAIKHOAN WHERE MNV = ?";
//             conn = ConnectDB.getConnection();
//             if (conn == null) {
//                 throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
//             }
//             try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                 stmt.setInt(1, maNV);
//                 try (ResultSet rs = stmt.executeQuery()) {
//                     if (rs.next()) {
//                         return new TaiKhoanDTO(
//                                 rs.getInt("MNV"),
//                                 rs.getString("TDN"),
//                                 rs.getString("MK"),
//                                 rs.getInt("MNQ"),
//                                 rs.getInt("TT")
//                         );
//                     }
//                 }
//             }
//         } catch (NumberFormatException e) {
//             System.err.println("Mã NV không hợp lệ: " + maNVStr);
//         } catch (SQLException e) {
//             System.err.println("Lỗi khi lấy tài khoản theo MNV: " + e.getMessage());
//         } finally {
//             ConnectDB.closeConnection();
//         }
//         return null;
//     }

//     public TaiKhoanDTO selectByUser(String username) {
//         Connection conn = null;
//         String sql = "SELECT * FROM TAIKHOAN WHERE TDN = ?";
//         try {
//             conn = ConnectDB.getConnection();
//             if (conn == null) {
//                 throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
//             }
//             try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                 stmt.setString(1, username);
//                 try (ResultSet rs = stmt.executeQuery()) {
//                     if (rs.next()) {
//                         return new TaiKhoanDTO(
//                                 rs.getInt("MNV"),
//                                 rs.getString("TDN"),
//                                 rs.getString("MK"),
//                                 rs.getInt("MNQ"),
//                                 rs.getInt("TT")
//                         );
//                     }
//                 }
//             }
//         } catch (SQLException e) {
//             System.err.println("Lỗi khi lấy tài khoản theo tên đăng nhập: " + e.getMessage());
//         } finally {
//             ConnectDB.closeConnection();
//         }
//         return null;
//     }

//     @Override
//     public int getAutoIncrement() {
//         Connection conn = null;
//         String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES " +
//                 "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'TAIKHOAN'";
//         try {
//             conn = ConnectDB.getConnection();
//             if (conn == null) {
//                 throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
//             }
//             try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
//                 if (rs.next()) {
//                     return rs.getInt("AUTO_INCREMENT");
//                 }
//             }
//         } catch (SQLException e) {
//             System.err.println("Lỗi lấy AUTO_INCREMENT: " + e.getMessage());
//         } finally {
//             ConnectDB.closeConnection();
//         }
//         return -1;
//     }

//     public TaiKhoanDTO selectByEmail(String email) {
//         Connection conn = null;
//         String sql = "SELECT T.* FROM TAIKHOAN T JOIN NHANVIEN N ON T.MNV = N.MNV WHERE N.EMAIL = ?";
//         try {
//             conn = ConnectDB.getConnection();
//             if (conn == null) {
//                 throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
//             }
//             try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                 stmt.setString(1, email);
//                 try (ResultSet rs = stmt.executeQuery()) {
//                     if (rs.next()) {
//                         return new TaiKhoanDTO(
//                                 rs.getInt("MNV"),
//                                 rs.getString("TDN"),
//                                 rs.getString("MK"),
//                                 rs.getInt("MNQ"),
//                                 rs.getInt("TT")
//                         );
//                     }
//                 }
//             }
//         } catch (SQLException e) {
//             System.err.println("Lỗi khi lấy tài khoản theo email: " + e.getMessage());
//         } finally {
//             ConnectDB.closeConnection();
//         }
//         return null;
//     }

//     // ------- Các phương thức mở rộng riêng -------

//     public boolean updatePassword(int maNV, String matKhauMoi) {
//         Connection conn = null;
//         String sql = "UPDATE TAIKHOAN SET MK = ? WHERE MNV = ?";
//         try {
//             conn = ConnectDB.getConnection();
//             if (conn == null) {
//                 throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
//             }
//             try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                 stmt.setString(1, matKhauMoi);
//                 stmt.setInt(2, maNV);
//                 return stmt.executeUpdate() > 0;
//             }
//         } catch (SQLException e) {
//             System.err.println("Lỗi cập nhật mật khẩu: " + e.getMessage());
//         } finally {
//             ConnectDB.closeConnection();
//         }
//         return false;
//     }

//     public TaiKhoanDTO getByTenDangNhap(String username) {
//         Connection conn = null;
//         String sql = "SELECT * FROM TAIKHOAN WHERE TDN = ?";
//         try {
//             conn = ConnectDB.getConnection();
//             if (conn == null) {
//                 throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
//             }
//             try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                 stmt.setString(1, username);
//                 try (ResultSet rs = stmt.executeQuery()) {
//                     if (rs.next()) {
//                         return new TaiKhoanDTO(
//                                 rs.getInt("MNV"),
//                                 rs.getString("TDN"),
//                                 rs.getString("MK"),
//                                 rs.getInt("MNQ"),
//                                 rs.getInt("TT")
//                         );
//                     }
//                 }
//             }
//         } catch (SQLException e) {
//             System.err.println("Lỗi khi lấy tài khoản theo tên đăng nhập: " + e.getMessage());
//         } finally {
//             ConnectDB.closeConnection();
//         }
//         return null;
//     }

//     public boolean isAccountExists(int maNV) {
//         Connection conn = null;
//         String sql = "SELECT 1 FROM TAIKHOAN WHERE MNV = ?";
//         try {
//             conn = ConnectDB.getConnection();
//             if (conn == null) {
//                 throw new SQLException("Không thể kết nối cơ sở dữ liệu.");
//             }
//             try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                 stmt.setInt(1, maNV);
//                 try (ResultSet rs = stmt.executeQuery()) {
//                     return rs.next();
//                 }
//             }
//         } catch (SQLException e) {
//             System.err.println("Lỗi kiểm tra tài khoản tồn tại: " + e.getMessage());
//         } finally {
//             ConnectDB.closeConnection();
//         }
//         return false;
//     }

//     public boolean checkLogin(String username, String password) {
//         TaiKhoanDTO tk = getByTenDangNhap(username);
//         if (tk != null) {
//             return BCrypt.checkpw(password, tk.getMatKhau());
//         }
//         return false;
//     }
// }

package DAO;

import DTO.TaiKhoanDTO;
import config.ConnectDB;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class TaiKhoanDAO implements DAOinterface<TaiKhoanDTO> {

    public static TaiKhoanDAO getInstance() {
        return new TaiKhoanDAO();
    }

    @Override
    public int add(TaiKhoanDTO tk) {
        String sql = "INSERT INTO TAIKHOAN (MNV, MK, TDN, MNQ, TT) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tk.getMaNV());
            stmt.setString(2, BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12)));
            stmt.setString(3, tk.getTenDangNhap());
            stmt.setInt(4, tk.getMaNhomQuyen());
            stmt.setInt(5, tk.getTrangThai());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi thêm tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    @Override
    public int update(TaiKhoanDTO tk) {
        String sql = "UPDATE TAIKHOAN SET TDN = ?, MK = ?, MNQ = ?, TT = ? WHERE MNV = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tk.getTenDangNhap());
            stmt.setString(2, BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12)));
            stmt.setInt(3, tk.getMaNhomQuyen());
            stmt.setInt(4, tk.getTrangThai());
            stmt.setInt(5, tk.getMaNV());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi cập nhật tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    @Override
    public int delete(String maNVStr) {
        String sql = "DELETE FROM TAIKHOAN WHERE MNV = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(maNVStr));
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected;
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Lỗi xóa tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    @Override
    public ArrayList<TaiKhoanDTO> selectAll() {
        ArrayList<TaiKhoanDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOAN WHERE TT = 1";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(createTaiKhoanDTOFromResultSet(rs));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi lấy danh sách tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    @Override
    public TaiKhoanDTO selectById(String maNVStr) {
        String sql = "SELECT * FROM TAIKHOAN WHERE MNV = ? AND TT = 1";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(maNVStr));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createTaiKhoanDTOFromResultSet(rs);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Lỗi tìm tài khoản theo MNV: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public TaiKhoanDTO selectByUser(String username) {
        String sql = "SELECT * FROM TAIKHOAN WHERE TDN = ? AND TT = 1";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createTaiKhoanDTOFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi tìm tài khoản theo TDN: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public int getAutoIncrement() {
        String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = 'TAIKHOAN'";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi lấy AUTO_INCREMENT: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return 1;
    }

    public TaiKhoanDTO selectByEmail(String email) {
        String sql = "SELECT T.* FROM TAIKHOAN T JOIN NHANVIEN N ON T.MNV = N.MNV WHERE N.EMAIL = ? AND T.TT = 1";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createTaiKhoanDTOFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi tìm tài khoản theo email: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public boolean updatePassword(int maNV, String matKhauMoi) {
        String sql = "UPDATE TAIKHOAN SET MK = ? WHERE MNV = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, BCrypt.hashpw(matKhauMoi, BCrypt.gensalt(12)));
            stmt.setInt(2, maNV);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi cập nhật mật khẩu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public TaiKhoanDTO getByTenDangNhap(String username) {
        return selectByUser(username);
    }

    public boolean isAccountExists(int maNV) {
        String sql = "SELECT 1 FROM TAIKHOAN WHERE MNV = ? AND TT = 1";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maNV);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi kiểm tra tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean checkLogin(String username, String password) {
        TaiKhoanDTO tk = getByTenDangNhap(username);
        if (tk != null && tk.getTrangThai() == 1) {
            return BCrypt.checkpw(password, tk.getMatKhau());
        }
        return false;
    }

    private TaiKhoanDTO createTaiKhoanDTOFromResultSet(ResultSet rs) throws SQLException {
        return new TaiKhoanDTO(
                rs.getInt("MNV"),
                rs.getString("TDN"),
                rs.getString("MK"),
                rs.getInt("MNQ"),
                rs.getInt("TT")
        );
    }
}
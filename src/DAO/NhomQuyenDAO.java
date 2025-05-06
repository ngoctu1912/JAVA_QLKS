// package DAO;

// import DTO.NhomQuyenDTO;
// import config.ConnectDB;
// import java.sql.*;
// import java.util.ArrayList;
// import java.util.logging.Level;
// import java.util.logging.Logger;

// public class NhomQuyenDAO implements DAOinterface<NhomQuyenDTO> {

//     public static NhomQuyenDAO getInstance() {
//         return new NhomQuyenDAO();
//     }

//     @Override
//     public int add(NhomQuyenDTO nq) {
//         String sql = "INSERT INTO NHOMQUYEN (MNQ, TEN, TT) VALUES (?, ?, ?)";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setInt(1, nq.getMaNhomQuyen());
//             stmt.setString(2, nq.getTen());
//             stmt.setInt(3, nq.getTrangThai());
//             return stmt.executeUpdate();
//         } catch (SQLException e) {
//             System.err.println("Lỗi thêm nhóm quyền: " + e.getMessage());
//             throw new RuntimeException("Lỗi thêm nhóm quyền: " + e.getMessage(), e);
//         }
//     }

//     @Override
//     public int update(NhomQuyenDTO nq) {
//         String sql = "UPDATE NHOMQUYEN SET TEN = ?, TT = ? WHERE MNQ = ?";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, nq.getTen());
//             stmt.setInt(2, nq.getTrangThai());
//             stmt.setInt(3, nq.getMaNhomQuyen());
//             return stmt.executeUpdate();
//         } catch (SQLException e) {
//             System.err.println("Lỗi cập nhật nhóm quyền: " + e.getMessage());
//             throw new RuntimeException("Lỗi cập nhật nhóm quyền: " + e.getMessage(), e);
//         }
//     }

//     @Override
//     public int delete(String id) {
//         String sql = "DELETE FROM NHOMQUYEN WHERE MNQ = ?";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, id);
//             return stmt.executeUpdate();
//         } catch (SQLException e) {
//             System.err.println("Lỗi xóa nhóm quyền: " + e.getMessage());
//             throw new RuntimeException("Lỗi xóa nhóm quyền: " + e.getMessage(), e);
//         }
//     }

//     @Override
//     public ArrayList<NhomQuyenDTO> selectAll() {
//         ArrayList<NhomQuyenDTO> ds = new ArrayList<>();
//         String sql = "SELECT * FROM NHOMQUYEN";
//         try (Connection conn = ConnectDB.getConnection();
//              Statement stmt = conn.createStatement();
//              ResultSet rs = stmt.executeQuery(sql)) {
//             while (rs.next()) {
//                 NhomQuyenDTO nq = new NhomQuyenDTO();
//                 nq.setMaNhomQuyen(rs.getInt("MNQ"));
//                 nq.setTen(rs.getString("TEN"));
//                 nq.setTrangThai(rs.getInt("TT"));
//                 ds.add(nq);
//             }
//         } catch (SQLException e) {
//             System.err.println("Lỗi lấy danh sách nhóm quyền: " + e.getMessage());
//             throw new RuntimeException("Lỗi lấy danh sách nhóm quyền: " + e.getMessage(), e);
//         }
//         return ds;
//     }

//     @Override
//     public NhomQuyenDTO selectById(String id) {
//         String sql = "SELECT * FROM NHOMQUYEN WHERE MNQ = ?";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, id);
//             try (ResultSet rs = stmt.executeQuery()) {
//                 if (rs.next()) {
//                     NhomQuyenDTO nq = new NhomQuyenDTO();
//                     nq.setMaNhomQuyen(rs.getInt("MNQ"));
//                     nq.setTen(rs.getString("TEN"));
//                     nq.setTrangThai(rs.getInt("TT"));
//                     return nq;
//                 }
//             }
//         } catch (SQLException e) {
//             System.err.println("Lỗi tìm nhóm quyền theo ID: " + e.getMessage());
//             throw new RuntimeException("Lỗi tìm nhóm quyền theo ID: " + e.getMessage(), e);
//         }
//         return null;
//     }

//     @Override
//     public int getAutoIncrement() {
//         int result = -1;
//         String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES " +
//                      "WHERE TABLE_SCHEMA = 'quanlycuahangsach' AND TABLE_NAME = 'NHOMQUYEN'";
//         try (Connection conn = ConnectDB.getConnection();
//              PreparedStatement pst = conn.prepareStatement(sql);
//              ResultSet rs = pst.executeQuery()) {
//             if (rs.next()) {
//                 result = rs.getInt("AUTO_INCREMENT");
//             }
//         } catch (SQLException ex) {
//             Logger.getLogger(NhomQuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
//         }
//         return result;
//     }
// }

package DAO;

import DTO.NhomQuyenDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;
import config.ConnectDB;



public class NhomQuyenDAO implements DAOinterface<NhomQuyenDTO> {

    public static NhomQuyenDAO getInstance() {
        return new NhomQuyenDAO();
    }

    @Override
    public int add(NhomQuyenDTO t) {
        int result = 0;
        try{
            Connection conn = (Connection) ConnectDB.getConnection();
            String sql = "INSERT INTO `NHOMQUYEN`(`TEN`, `TT`) VALUES (?,1)";
            PreparedStatement prest = conn.prepareStatement(sql);
            prest.setString(1, t.getTEN());
            result = prest.executeUpdate();
            // ConnectDB.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(NhomQuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result; 
    }

    @Override
    public int update(NhomQuyenDTO t) {
        int result = 0;
        try {
            Connection conn = (Connection) ConnectDB.getConnection();
            String sql = "UPDATE `NHOMQUYEN` SET `TEN`= ? WHERE `MNQ` = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, t.getTEN());
            pst.setInt(2, t.getMNQ());
            result = pst.executeUpdate();
            // ConnectDB.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(NhomQuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection conn = (Connection) ConnectDB.getConnection();
            String sql = "UPDATE `NHOMQUYEN` SET `TT` = 0 WHERE MNQ = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            // ConnectDB.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(NhomQuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<NhomQuyenDTO> selectAll() {
        ArrayList<NhomQuyenDTO> result = new ArrayList<>();
        try {
            Connection conn = (Connection) ConnectDB.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM NHOMQUYEN WHERE TT = 1");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int MNQ = rs.getInt("MNQ");
                String TEN = rs.getString("TEN");
                NhomQuyenDTO dvt = new NhomQuyenDTO(MNQ, TEN);
                result.add(dvt);
            }
            ConnectDB.getConnection();
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public NhomQuyenDTO selectById(String t) {
        NhomQuyenDTO result = null;
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT * FROM NHOMQUYEN WHERE MNQ = ?";
            PreparedStatement prest = conn.prepareStatement(sql);
            prest.setString(1, t); 
            ResultSet rs = prest.executeQuery();
            if (rs.next()) {
                int MNQ = rs.getInt("MNQ");
                String TEN = rs.getString("TEN");
                result = new NhomQuyenDTO(MNQ, TEN);
            }
            // ConnectDB.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(NhomQuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection conn = (Connection) ConnectDB.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlykhachsan' AND TABLE_NAME = 'NHOMQUYEN'";
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs2 = pst.executeQuery(sql);
            if (!rs2.isBeforeFirst()) {
                System.out.println("No data");
            } else {
                while (rs2.next()) {
                    result = rs2.getInt("AUTO_INCREMENT");

                }
            }
            // ConnectDB.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(NhomQuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
}    

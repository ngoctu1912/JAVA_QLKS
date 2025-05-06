package DAO;

import java.sql.*;
import java.util.logging.*;
import java.util.ArrayList;

import config.ConnectDB;
import DTO.ChiTietQuyenDTO;

public class ChiTietQuyenDAO implements ChiTietInterface<ChiTietQuyenDTO> {

    public static ChiTietQuyenDAO getInstance() {
        return new ChiTietQuyenDAO();
    }

    @Override
    public int insert(ArrayList<ChiTietQuyenDTO> t) {
        int result = 0;
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false);
            String sql = "INSERT INTO `CTQUYEN`(`MNQ`, `MCN`, `HANHDONG`) VALUES (?, ?, ?)";
            String checkSql = "SELECT COUNT(*) FROM `CTQUYEN` WHERE `MNQ` = ? AND `MCN` = ? AND `HANHDONG` = ?";
            PreparedStatement prest = conn.prepareStatement(sql);
            PreparedStatement checkPrest = conn.prepareStatement(checkSql);

            for (ChiTietQuyenDTO ctq : t) {
                // Kiểm tra xem bản ghi đã tồn tại chưa
                checkPrest.setInt(1, ctq.getMNQ());
                checkPrest.setInt(2, ctq.getMCN());
                checkPrest.setString(3, ctq.getHANHDONG());
                ResultSet rs = checkPrest.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) { // Chỉ chèn nếu chưa tồn tại
                    prest.setInt(1, ctq.getMNQ());
                    prest.setInt(2, ctq.getMCN());
                    prest.setString(3, ctq.getHANHDONG());
                    result += prest.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietQuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            // ConnectDB.closeConnection();
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection conn = (Connection) ConnectDB.getConnection();
            String sql = "DELETE FROM CTQUYEN WHERE MNQ = ? ";
            PreparedStatement prest = conn.prepareStatement(sql);
            prest.setString(1, t);
            result = prest.executeUpdate();
            // ConnectDB.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietQuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(ArrayList<ChiTietQuyenDTO> t, String pk) {
        int result = this.delete(pk);
        if(result != 0) result = this.insert(t);
        return result;
    }

    
    @Override
    public ArrayList<ChiTietQuyenDTO> selectAll(String t) {
        ArrayList<ChiTietQuyenDTO> result = new ArrayList<>();
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT * FROM CTQUYEN WHERE MNQ = ?";
            PreparedStatement prest = conn.prepareStatement(sql);
            prest.setString(1, t);
            System.out.println("Executing query: " + sql + " with MNQ = " + t);
            ResultSet rs = prest.executeQuery();
            while (rs.next()) {
                int MNQ = rs.getInt("MNQ");
                int MCN = rs.getInt("MCN");
                String HANHDONG = rs.getString("HANHDONG");
                System.out.println("Found permission: MNQ=" + MNQ + ", MCN=" + MCN + ", HANHDONG=" + HANHDONG);
                ChiTietQuyenDTO dvt = new ChiTietQuyenDTO(MNQ, MCN, HANHDONG);
                result.add(dvt);
            }
            System.out.println("Total permissions found: " + result.size());
            // ConnectDB.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietQuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

        
}

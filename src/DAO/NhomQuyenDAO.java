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
            ConnectDB.closeConnection();
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
            ConnectDB.closeConnection();
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
            ConnectDB.closeConnection();
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
            Connection conn = (Connection) ConnectDB.getConnection();
            String sql = "SELECT * FROM NHOMQUYEN WHERE MNQ=?";
            PreparedStatement prest = conn.prepareStatement(sql);
            prest.setString(1, sql);
            ResultSet rs = (ResultSet) prest.executeQuery();
            while (rs.next()) {
                int MNQ = rs.getInt("MNQ");
                String TEN = rs.getString("TEN");
                result = new NhomQuyenDTO(MNQ, TEN);
            }
            ConnectDB.closeConnection();
        } catch (Exception e) {
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
            ConnectDB.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(NhomQuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
}    

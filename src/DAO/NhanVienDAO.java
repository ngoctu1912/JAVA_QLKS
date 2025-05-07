package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.ConnectDB;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import DTO.NhanVienDTO;

public class NhanVienDAO implements DAOinterface<NhanVienDTO> {
    public static NhanVienDAO getInstance() {
        return new NhanVienDAO();
    }

    @Override  
    public int add(NhanVienDTO t) {
        int result = 0;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet generatedKeys = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO `NHANVIEN`(`HOTEN`, `GIOITINH`, `SDT`, `NGAYSINH`, `TT`, `EMAIL`) VALUES (?,?,?,?,?,?)";
            pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, t.getHOTEN());
            pst.setInt(2, t.getGIOITINH());
            pst.setString(3, t.getSDT());
            pst.setDate(4, t.getNGAYSINH() != null ? new java.sql.Date(t.getNGAYSINH().getTime()) : null);
            pst.setInt(5, t.getTT());
            pst.setString(6, t.getEMAIL());
            result = pst.executeUpdate();

            generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                t.setMNV(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, "SQL Error: " + ex.getMessage(), ex);
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (pst != null) pst.close();
                // if (con != null) ConnectDB.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    // @Override
    // public int update(NhanVienDTO t) {
    //     int result = 0;
    //     Connection con = null;
    //     PreparedStatement pst = null;
    //     try {
    //         con = ConnectDB.getConnection();
    //         String sql = "UPDATE `NHANVIEN` SET `HOTEN`=?, `GIOITINH`=?, `SDT`=?, `NGAYSINH`=?, `TT`=?, `EMAIL`=?, `SNP`=0, `NVL`=CURDATE(), `LN`=0 WHERE `MNV`=?";
    //         pst = con.prepareStatement(sql);
    //         pst.setString(1, t.getHOTEN());
    //         pst.setInt(2, t.getGIOITINH());
    //         pst.setString(3, t.getSDT());
    //         pst.setDate(4, t.getNGAYSINH() != null ? new java.sql.Date(t.getNGAYSINH().getTime()) : null);
    //         pst.setInt(5, t.getTT());
    //         pst.setString(6, t.getEMAIL());
    //         pst.setInt(7, t.getMNV());
    //         result = pst.executeUpdate();
    //     } catch (SQLException ex) {
    //         Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
    //     } finally {
    //         try {
    //             if (pst != null) pst.close();
    //             // if (con != null) ConnectDB.closeConnection();
    //         } catch (SQLException ex) {
    //             Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
    //         }
    //     }
    //     return result;
    // }
    @Override
    public int update(NhanVienDTO t) {
        int result = 0;
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE `NHANVIEN` SET `HOTEN`=?, `GIOITINH`=?, `SDT`=?, `NGAYSINH`=?, `TT`=?, `EMAIL`=? WHERE `MNV`=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, t.getHOTEN());
            pst.setInt(2, t.getGIOITINH());
            pst.setString(3, t.getSDT());
            pst.setDate(4, t.getNGAYSINH() != null ? new java.sql.Date(t.getNGAYSINH().getTime()) : null);
            pst.setInt(5, t.getTT());
            pst.setString(6, t.getEMAIL());
            pst.setInt(7, t.getMNV());
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, "SQL Error: " + ex.getMessage(), ex);
        } finally {
            try {
                if (pst != null) pst.close();
                // if (con != null) ConnectDB.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE NHANVIEN SET `TT` = 1 WHERE MNV = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pst != null) pst.close();
                // if (con != null) ConnectDB.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public ArrayList<NhanVienDTO> selectAll() {
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT `MNV`, `HOTEN`, `GIOITINH`, `SDT`, `NGAYSINH`, `TT`, `EMAIL` FROM NHANVIEN";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int MNV = rs.getInt("MNV");
                String HOTEN = rs.getString("HOTEN");
                int GIOITINH = rs.getInt("GIOITINH");
                Date NGAYSINH = rs.getDate("NGAYSINH");
                String SDT = rs.getString("SDT");
                int TT = rs.getInt("TT");
                String EMAIL = rs.getString("EMAIL");
                NhanVienDTO nv = new NhanVienDTO(MNV, HOTEN, GIOITINH, NGAYSINH, SDT, TT, EMAIL);
                result.add(nv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                // if (con != null) ConnectDB.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public ArrayList<NhanVienDTO> selectAlll() {
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NHANVIEN";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int MNV = rs.getInt("MNV");
                String HOTEN = rs.getString("HOTEN");
                int GIOITINH = rs.getInt("GIOITINH");
                Date NGAYSINH = rs.getDate("NGAYSINH");
                String SDT = rs.getString("SDT");
                int TT = rs.getInt("TT");
                String EMAIL = rs.getString("EMAIL");
                NhanVienDTO nv = new NhanVienDTO(MNV, HOTEN, GIOITINH, NGAYSINH, SDT, TT, EMAIL);
                result.add(nv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                // if (con != null) ConnectDB.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public ArrayList<NhanVienDTO> selectAllNV() {
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NHANVIEN nv WHERE nv.TT = 1 AND NOT EXISTS (SELECT * FROM taikhoan tk WHERE tk.MNV = nv.MNV)";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int MNV = rs.getInt("MNV");
                String HOTEN = rs.getString("HOTEN");
                int GIOITINH = rs.getInt("GIOITINH");
                Date NGAYSINH = rs.getDate("NGAYSINH");
                String SDT = rs.getString("SDT");
                int TT = rs.getInt("TT");
                String EMAIL = rs.getString("EMAIL");
                NhanVienDTO nv = new NhanVienDTO(MNV, HOTEN, GIOITINH, NGAYSINH, SDT, TT, EMAIL);
                result.add(nv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                // if (con != null) ConnectDB.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public NhanVienDTO selectById(String t) {
        NhanVienDTO result = null;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NHANVIEN WHERE MNV = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, t);
            rs = pst.executeQuery();
            if (rs.next()) {
                int MNV = rs.getInt("MNV");
                String HOTEN = rs.getString("HOTEN");
                int GIOITINH = rs.getInt("GIOITINH");
                Date NGAYSINH = rs.getDate("NGAYSINH");
                String SDT = rs.getString("SDT");
                int TT = rs.getInt("TT");
                String EMAIL = rs.getString("EMAIL");
                result = new NhanVienDTO(MNV, HOTEN, GIOITINH, NGAYSINH, SDT, TT, EMAIL);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                // if (con != null) ConnectDB.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public NhanVienDTO selectByEmail(String t) {
        NhanVienDTO result = null;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NHANVIEN WHERE EMAIL = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, t);
            rs = pst.executeQuery();
            if (rs.next()) {
                int MNV = rs.getInt("MNV");
                String HOTEN = rs.getString("HOTEN");
                int GIOITINH = rs.getInt("GIOITINH");
                Date NGAYSINH = rs.getDate("NGAYSINH");
                String SDT = rs.getString("SDT");
                int TT = rs.getInt("TT");
                String EMAIL = rs.getString("EMAIL");
                result = new NhanVienDTO(MNV, HOTEN, GIOITINH, NGAYSINH, SDT, TT, EMAIL);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                // if (con != null) ConnectDB.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlykhachsan' AND TABLE_NAME = 'NHANVIEN'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                // if (con != null) ConnectDB.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
}
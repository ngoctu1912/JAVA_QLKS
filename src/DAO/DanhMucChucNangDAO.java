package DAO;

import DTO.DanhMucChucNangDTO;
import config.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class DanhMucChucNangDAO {

    public static DanhMucChucNangDAO getInstance() {
        return new DanhMucChucNangDAO();
    }

    public ArrayList<DanhMucChucNangDTO> selectAll() {
        ArrayList<DanhMucChucNangDTO> result = new ArrayList<>();
        try {
            Connection conn = (Connection) ConnectDB.getConnection();
            String sql = "SELECT * FROM DANHMUCCHUCNANG";
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int machucnang = rs.getInt("MCN");
                String tenchucnang = rs.getString("TENCN");
                DanhMucChucNangDTO dvt = new DanhMucChucNangDTO(machucnang, tenchucnang);
                result.add(dvt);
            }
            // ConnectDB.closeConnection();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

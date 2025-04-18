package DAO;

import DTO.DichVuDTO;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DichVuDAO implements DAOinterface<DichVuDTO> {
    @Override
    public int add(DichVuDTO dv) {
        int rowsAffected = 0;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO DICHVU (maDV, tenDV, loaiDV, soLuong, giaDV, xuLy) VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, dv.getMaDV());
            stmt.setString(2, dv.getTenDV());
            stmt.setString(3, dv.getLoaiDV());
            stmt.setInt(4, dv.getSoLuong());
            stmt.setInt(5, dv.getGiaDV());
            stmt.setInt(6, dv.getXuLy());
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int update(DichVuDTO dv) {
        int rowsAffected = 0;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE DICHVU SET tenDV = ?, loaiDV = ?, soLuong = ?, giaDV = ?, xuLy = ? WHERE maDV = ?")) {
            stmt.setString(1, dv.getTenDV());
            stmt.setString(2, dv.getLoaiDV());
            stmt.setInt(3, dv.getSoLuong());
            stmt.setInt(4, dv.getGiaDV());
            stmt.setInt(5, dv.getXuLy());
            stmt.setString(6, dv.getMaDV());
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int delete(String maDV) {
        int rowsAffected = 0;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM DICHVU WHERE maDV = ?")) {
            stmt.setString(1, maDV);
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ArrayList<DichVuDTO> selectAll() {
        ArrayList<DichVuDTO> list = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DICHVU");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new DichVuDTO(
                        rs.getString("maDV"),
                        rs.getString("tenDV"),
                        rs.getString("loaiDV"),
                        rs.getInt("soLuong"),
                        rs.getInt("giaDV"),
                        rs.getInt("xuLy")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public DichVuDTO selectById(String maDV) {
        DichVuDTO dv = null;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DICHVU WHERE maDV = ?")) {
            stmt.setString(1, maDV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dv = new DichVuDTO(
                        rs.getString("maDV"),
                        rs.getString("tenDV"),
                        rs.getString("loaiDV"),
                        rs.getInt("soLuong"),
                        rs.getInt("giaDV"),
                        rs.getInt("xuLy")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dv;
    }

    @Override
    public int getAutoIncrement() {
        int autoIncrement = 0;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = 'DICHVU'")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                autoIncrement = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autoIncrement;
    }
}
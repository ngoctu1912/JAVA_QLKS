package DAO;

import DTO.ChiTietTienIchDTO;
import config.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChiTietTienIchDAO implements DAOinterface<ChiTietTienIchDTO> {

    public ChiTietTienIchDAO() {
        // No connection field needed
    }

    @Override
    public int add(ChiTietTienIchDTO t) {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return 0;

        String query = "INSERT INTO CHITIETTIENICH (maP, maTI, soLuong) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, t.getMaP());
            ps.setString(2, t.getMaTI());
            ps.setInt(3, t.getSoLuong());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(ChiTietTienIchDTO t) {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return 0;

        String query = "UPDATE CHITIETTIENICH SET soLuong = ? WHERE maP = ? AND maTI = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, t.getSoLuong());
            ps.setString(2, t.getMaP());
            ps.setString(3, t.getMaTI());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(String id) {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return 0;

        String query = "DELETE FROM CHITIETTIENICH WHERE maP = ? AND maTI = ?";
        try {
            String[] keys = id.split(",");
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, keys[0]); // maP
            ps.setString(2, keys[1]); // maTI
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<ChiTietTienIchDTO> selectAll() {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return new ArrayList<>();

        ArrayList<ChiTietTienIchDTO> amenities = new ArrayList<>();
        String query = "SELECT * FROM CHITIETTIENICH";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietTienIchDTO amenity = new ChiTietTienIchDTO(
                    rs.getString("maP"),
                    rs.getString("maTI"),
                    rs.getInt("soLuong")
                );
                amenities.add(amenity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amenities;
    }

    @Override
    public ChiTietTienIchDTO selectById(String id) {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return null;

        String query = "SELECT * FROM CHITIETTIENICH WHERE maP = ? AND maTI = ?";
        try {
            String[] keys = id.split(",");
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, keys[0]); // maP
            ps.setString(2, keys[1]); // maTI
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ChiTietTienIchDTO(
                    rs.getString("maP"),
                    rs.getString("maTI"),
                    rs.getInt("soLuong")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getAutoIncrement() {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return 0;

        String query = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlykhachsan' AND TABLE_NAME = 'CHITIETTIENICH'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("AUTO_INCREMENT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Helper method to get amenities by room ID (for convenience)
    public List<ChiTietTienIchDTO> getAmenitiesByRoomId(String maP) {
        Connection conn = ConnectDB.getConnection();
        if (conn == null) return new ArrayList<>();

        List<ChiTietTienIchDTO> amenities = new ArrayList<>();
        String query = "SELECT * FROM CHITIETTIENICH WHERE maP = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, maP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietTienIchDTO amenity = new ChiTietTienIchDTO(
                    rs.getString("maP"),
                    rs.getString("maTI"),
                    rs.getInt("soLuong")
                );
                amenities.add(amenity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amenities;
    }
}


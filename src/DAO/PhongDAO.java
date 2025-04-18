package DAO;

import DTO.PhongDTO;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhongDAO implements DAOinterface<PhongDTO> {
    @Override
    public int add(PhongDTO p) {
        int rowsAffected = 0;
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO PHONG (maP, tenP, loaiP, hinhAnh, giaP, chiTietLoaiPhong, tinhTrang) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, p.getMaP());
            stmt.setString(2, p.getTenP());
            stmt.setString(3, p.getLoaiP());
            stmt.setString(4, p.getHinhAnh());
            stmt.setInt(5, p.getGiaP());
            stmt.setString(6, p.getChiTietLoaiPhong());
            stmt.setInt(7, p.getTinhTrang());
            rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // Thêm log
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có
        }
        return rowsAffected;
    }

    @Override
    public int update(PhongDTO p) {
        int rowsAffected = 0;
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE PHONG SET tenP = ?, loaiP = ?, hinhAnh = ?, giaP = ?, chiTietLoaiPhong = ?, tinhTrang = ? WHERE maP = ?")) {
            stmt.setString(1, p.getTenP());
            stmt.setString(2, p.getLoaiP());
            stmt.setString(3, p.getHinhAnh());
            stmt.setInt(4, p.getGiaP());
            stmt.setString(5, p.getChiTietLoaiPhong());
            stmt.setInt(6, p.getTinhTrang());
            stmt.setString(7, p.getMaP());
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int delete(String maP) {
        int rowsAffected = 0;
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM PHONG WHERE maP = ?")) {
            stmt.setString(1, maP);
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ArrayList<PhongDTO> selectAll() {
        ArrayList<PhongDTO> list = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PHONG");
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new PhongDTO(
                        rs.getString("maP"),
                        rs.getString("tenP"),
                        rs.getString("loaiP"),
                        rs.getString("hinhAnh"),
                        rs.getInt("giaP"),
                        rs.getString("chiTietLoaiPhong"),
                        rs.getInt("tinhTrang")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public PhongDTO selectById(String maP) {
        PhongDTO p = null;
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PHONG WHERE maP = ?")) {
            stmt.setString(1, maP);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                p = new PhongDTO(
                        rs.getString("maP"),
                        rs.getString("tenP"),
                        rs.getString("loaiP"),
                        rs.getString("hinhAnh"),
                        rs.getInt("giaP"),
                        rs.getString("chiTietLoaiPhong"),
                        rs.getInt("tinhTrang"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public int getAutoIncrement() {
        int autoIncrement = 0;
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = 'PHONG'")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                autoIncrement = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autoIncrement;
    }

    public List<Date[]> getThoiGianThue(String maP) {
        List<Date[]> thoiGianList = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT ngayThue, ngayTra FROM CHITIETDATPHONG WHERE maP = ?")) {
            stmt.setString(1, maP);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Date[] thoiGian = new Date[2];
                thoiGian[0] = rs.getTimestamp("ngayThue");
                thoiGian[1] = rs.getTimestamp("ngayTra");
                thoiGianList.add(thoiGian);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thoiGianList;
    }
}
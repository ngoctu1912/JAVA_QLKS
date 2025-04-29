package DAO;

import DTO.PhongDTO;
import config.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PhongDAO implements DAOinterface<PhongDTO> {

    @Override
    public int add(PhongDTO p) {
        Objects.requireNonNull(p, "Thông tin phòng không được rỗng");
        String sql = "INSERT INTO PHONG (maP, tenP, loaiP, hinhAnh, giaP, chiTietLoaiPhong, tinhTrang) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getMaP());
            stmt.setString(2, p.getTenP());
            stmt.setString(3, p.getLoaiP());
            stmt.setString(4, p.getHinhAnh());
            stmt.setInt(5, p.getGiaP());
            stmt.setString(6, p.getChiTietLoaiPhong());
            stmt.setInt(7, p.getTinhTrang());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Added room, rows affected: " + rowsAffected);
            return rowsAffected;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm phòng: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(PhongDTO p) {
        Objects.requireNonNull(p, "Thông tin phòng không được rỗng");
        String sql = "UPDATE PHONG SET tenP = ?, loaiP = ?, hinhAnh = ?, giaP = ?, chiTietLoaiPhong = ?, tinhTrang = ? WHERE maP = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getTenP());
            stmt.setString(2, p.getLoaiP());
            stmt.setString(3, p.getHinhAnh());
            stmt.setInt(4, p.getGiaP());
            stmt.setString(5, p.getChiTietLoaiPhong());
            stmt.setInt(6, p.getTinhTrang());
            stmt.setString(7, p.getMaP());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Updated room, rows affected: " + rowsAffected);
            return rowsAffected;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật phòng: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(String maP) {
        if (maP == null || maP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được rỗng");
        }
        String sql = "DELETE FROM PHONG WHERE maP = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maP);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Deleted room, rows affected: " + rowsAffected);
            return rowsAffected;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa phòng: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<PhongDTO> selectAll() {
        ArrayList<PhongDTO> rooms = new ArrayList<>();
        String sql = "SELECT * FROM PHONG";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rooms.add(createPhongDTOFromResultSet(rs));
            }
            return rooms;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách phòng: " + e.getMessage(), e);
        }
    }

    @Override
    public PhongDTO selectById(String maP) {
        if (maP == null || maP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được rỗng");
        }
        String sql = "SELECT * FROM PHONG WHERE maP = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maP);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createPhongDTOFromResultSet(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy phòng theo mã: " + e.getMessage(), e);
        }
    }

    @Override
    public int getAutoIncrement() {
        String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'quanlykhachsan' AND TABLE_NAME = 'PHONG'";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("AUTO_INCREMENT");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy AUTO_INCREMENT: " + e.getMessage(), e);
        }
    }

    public List<Date[]> getThoiGianThue(String maP) {
        if (maP == null || maP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được rỗng");
        }
        List<Date[]> thoiGianList = new ArrayList<>();
        String sql = "SELECT ngayThue, ngayTra FROM CHITIETDATPHONG WHERE maP = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maP);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Date[] thoiGian = new Date[2];
                    thoiGian[0] = rs.getTimestamp("ngayThue");
                    thoiGian[1] = rs.getTimestamp("ngayTra");
                    thoiGianList.add(thoiGian);
                }
                return thoiGianList;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy thời gian thuê: " + e.getMessage(), e);
        }
    }

    public List<PhongDTO> getAllAvailableRooms() throws SQLException {
        List<PhongDTO> rooms = new ArrayList<>();
        String sql = "SELECT maP, tenP, loaiP, hinhAnh, giaP, chiTietLoaiPhong, tinhTrang FROM PHONG WHERE tinhTrang = 0 ORDER BY maP";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rooms.add(createPhongDTOFromResultSet(rs));
            }
            return rooms;
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy danh sách phòng trống: " + e.getMessage(), e);
        }
    }

    public List<PhongDTO> getAvailableRooms(Date checkInDate, Date checkOutDate, List<String> roomTypes)
            throws SQLException {
        Objects.requireNonNull(checkInDate, "Ngày nhận phòng không được rỗng");
        Objects.requireNonNull(checkOutDate, "Ngày trả phòng không được rỗng");
        List<PhongDTO> rooms = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = prepareAvailableRoomsStatement(conn, checkInDate, checkOutDate, roomTypes);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rooms.add(createPhongDTOFromResultSet(rs));
            }
            return rooms;
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy danh sách phòng trống theo thời gian: " + e.getMessage(), e);
        }
    }

    private PreparedStatement prepareAvailableRoomsStatement(Connection conn, Date checkInDate, Date checkOutDate,
                                                            List<String> roomTypes) throws SQLException {
        StringBuilder query = new StringBuilder(
                "SELECT p.maP, p.tenP, p.loaiP, p.hinhAnh, p.giaP, p.chiTietLoaiPhong, p.tinhTrang " +
                        "FROM PHONG p " +
                        "WHERE p.tinhTrang = 0");

        if (roomTypes != null && !roomTypes.isEmpty()) {
            query.append(" AND p.loaiP IN (");
            for (int i = 0; i < roomTypes.size(); i++) {
                query.append("?");
                if (i < roomTypes.size() - 1) {
                    query.append(",");
                }
            }
            query.append(")");
        }

        query.append(
                " AND p.maP NOT IN (" +
                        "SELECT c.maP FROM CHITIETDATPHONG c " +
                        "WHERE c.tinhTrang = 1 AND c.ngayThue <= ? AND c.ngayTra >= ?) " +
                        "ORDER BY p.maP");

        PreparedStatement stmt = conn.prepareStatement(query.toString());

        int paramIndex = 1;
        if (roomTypes != null && !roomTypes.isEmpty()) {
            for (String roomType : roomTypes) {
                stmt.setString(paramIndex++, roomType);
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        stmt.setString(paramIndex++, sdf.format(checkOutDate));
        stmt.setString(paramIndex, sdf.format(checkInDate));

        return stmt;
    }

    private PhongDTO createPhongDTOFromResultSet(ResultSet rs) throws SQLException {
        return new PhongDTO(
                rs.getString("maP"),
                rs.getString("tenP"),
                rs.getString("loaiP"),
                rs.getString("hinhAnh"),
                rs.getInt("giaP"),
                rs.getString("chiTietLoaiPhong"),
                rs.getInt("tinhTrang"));
    }
}
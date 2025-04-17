package DAO;

import DTO.KhachHangDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class KhachHangDAO {
    public List<KhachHangDTO> getAllCustomers() {
        List<KhachHangDTO> customers = new ArrayList<>();
        String query = "SELECT * FROM KHACHHANG WHERE TT = 1"; // Chỉ lấy khách hàng hoạt động

        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                customers.add(new KhachHangDTO(
                    rs.getInt("MKH"),
                    rs.getString("TKH"),
                    rs.getString("DIACHI"),
                    rs.getString("SDT"),
                    rs.getDate("NS"),
                    rs.getInt("TT"),
                    rs.getInt("GT"),
                    rs.getInt("CCCD"),
                    rs.getString("EMAIL")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi truy vấn dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return customers;
    }
}
package DAO;

import DTO.ThongKeDTO;
import DTO.KhachHangThongKeDTO;
import config.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ThongKeDAO implements DAOinterface<Object> {
    private Connection conn;

    public ThongKeDAO() {
        try {
            conn = ConnectDB.getConnection();
            if (conn != null) {
                System.out.println("Kết nối cơ sở dữ liệu thành công!");
            } else {
                System.err.println("Không thể kết nối cơ sở dữ liệu: ConnectDB.getConnection() trả về null");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy kết nối từ ConnectDB: " + e.getMessage());
            e.printStackTrace();
            conn = null;
        }
    }

    public ArrayList<ThongKeDTO> getDoanhThuTheoThangNam(int year, int month) {
        ArrayList<ThongKeDTO> list = new ArrayList<>();
        if (conn == null) {
            System.err.println("Không thể truy vấn do không có kết nối cơ sở dữ liệu!");
            return list;
        }

        LocalDate startDate = LocalDate.of(year, month, 1);
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        LocalDate endDate = startDate.withDayOfMonth(daysInMonth);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            String formattedDate = currentDate.format(formatter);
            String query = "SELECT SUM(tienP) as doanhThuPhong, SUM(tienDV) as doanhThuDichVu, SUM(tongTien) as tongDoanhThu "
                    +
                    "FROM HOADON " +
                    "WHERE DATE(ngayThanhToan) = ? AND xuLy = 1";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, formattedDate);
                ResultSet rs = stmt.executeQuery();
                ThongKeDTO dto = new ThongKeDTO();
                dto.setNgay(currentDate);
                if (rs.next() && rs.getLong("doanhThuPhong") > 0) { // Kiểm tra dữ liệu hợp lệ
                    dto.setDoanhThuPhong(rs.getLong("doanhThuPhong"));
                    dto.setDoanhThuDichVu(rs.getLong("doanhThuDichVu"));
                    dto.setTongDoanhThu(rs.getLong("tongDoanhThu"));
                } else {
                    dto.setDoanhThuPhong(0);
                    dto.setDoanhThuDichVu(0);
                    dto.setTongDoanhThu(0);
                }
                list.add(dto);
            } catch (SQLException e) {
                System.err.println("Lỗi khi truy vấn doanh thu theo ngày " + formattedDate + ": " + e.getMessage());
                e.printStackTrace();
                // Thêm DTO với giá trị 0 nếu có lỗi
                ThongKeDTO dto = new ThongKeDTO();
                dto.setNgay(currentDate);
                dto.setDoanhThuPhong(0);
                dto.setDoanhThuDichVu(0);
                dto.setTongDoanhThu(0);
                list.add(dto);
            }
            currentDate = currentDate.plusDays(1);
        }

        System.out.println("Fetched " + list.size() + " records for " + month + "/" + year);
        return list;
    }

    public ArrayList<ThongKeDTO> getDoanhThuTheoKhoangNgay(LocalDate startDate, LocalDate endDate) {
        ArrayList<ThongKeDTO> list = new ArrayList<>();
        if (conn == null) {
            System.err.println("Không thể truy vấn do không có kết nối cơ sở dữ liệu!");
            return list;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            String formattedDate = currentDate.format(formatter);
            String query = "SELECT SUM(tienP) as doanhThuPhong, SUM(tienDV) as doanhThuDichVu, SUM(tongTien) as tongDoanhThu "
                    +
                    "FROM HOADON " +
                    "WHERE DATE(ngayThanhToan) = ? AND xuLy = 1";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, formattedDate);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    long doanhThuPhong = rs.getLong("doanhThuPhong");
                    long doanhThuDichVu = rs.getLong("doanhThuDichVu");
                    long tongDoanhThu = rs.getLong("tongDoanhThu");

                    ThongKeDTO dto = new ThongKeDTO();
                    dto.setNgay(currentDate);
                    dto.setDoanhThuPhong(doanhThuPhong);
                    dto.setDoanhThuDichVu(doanhThuDichVu);
                    dto.setTongDoanhThu(tongDoanhThu);
                    list.add(dto);
                } else {
                    ThongKeDTO dto = new ThongKeDTO();
                    dto.setNgay(currentDate);
                    dto.setDoanhThuPhong(0);
                    dto.setDoanhThuDichVu(0);
                    dto.setTongDoanhThu(0);
                    list.add(dto);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi truy vấn doanh thu theo ngày " + formattedDate + ": " + e.getMessage());
                e.printStackTrace();
            }
            currentDate = currentDate.plusDays(1);
        }

        return list;
    }

    public ArrayList<ThongKeDTO> getDoanhThuTheoLoaiPhong(LocalDate startDate, LocalDate endDate) {
        ArrayList<ThongKeDTO> list = new ArrayList<>();
        if (conn == null) {
            System.err.println("Không thể truy vấn do không có kết nối cơ sở dữ liệu!");
            return list;
        }

        String query = "SELECT p.loaiP, " +
                "SUM(h.tienP) as doanhThuPhong, " +
                "SUM(h.tienDV) as doanhThuDichVu, " +
                "SUM(h.tongTien) as tongDoanhThu, " +
                "COUNT(DISTINCT dp.maDP) as soLanDat " +
                "FROM HOADON h " +
                "JOIN DATPHONG dp ON h.maCTT = dp.maDP " +
                "JOIN CHITIETDATPHONG ctdp ON dp.maDP = ctdp.maCTDP " +
                "JOIN PHONG p ON ctdp.maP = p.maP " +
                "WHERE DATE(h.ngayThanhToan) BETWEEN ? AND ? AND h.xuLy = 1 " +
                "GROUP BY p.loaiP";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            stmt.setString(2, endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String loaiPhong = rs.getString("loaiP");
                long doanhThuPhong = rs.getLong("doanhThuPhong");
                long doanhThuDichVu = rs.getLong("doanhThuDichVu");
                long tongDoanhThu = rs.getLong("tongDoanhThu");
                int soLanDat = rs.getInt("soLanDat");

                ThongKeDTO dto = new ThongKeDTO(loaiPhong, doanhThuPhong, doanhThuDichVu, tongDoanhThu, soLanDat);
                list.add(dto);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn doanh thu theo loại phòng từ " + startDate + " đến " + endDate + ": "
                    + e.getMessage());
            e.printStackTrace();
        }

        // Đảm bảo có dữ liệu cho tất cả loại phòng (nếu không có dữ liệu thì trả về 0)
        String[] roomTypes = { "Đơn", "Đôi", "Gia đình", "VIP", "Suite" };
        for (String roomType : roomTypes) {
            boolean found = false;
            for (ThongKeDTO dto : list) {
                if (dto.getLoaiPhong().equals(roomType)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                list.add(new ThongKeDTO(roomType, 0, 0, 0, 0));
            }
        }

        return list;
    }

    public ArrayList<KhachHangThongKeDTO> getKhachHangTheoKhoangNgay(LocalDate startDate, LocalDate endDate) {
        ArrayList<KhachHangThongKeDTO> list = new ArrayList<>();
        if (conn == null) {
            System.err.println("Không thể truy vấn do không có kết nối cơ sở dữ liệu!");
            return list;
        }

        String query = "SELECT dp.maKH, k.TKH, COUNT(h.maHD) as soLanDatPhong, SUM(h.tongTien) as tongTien " +
                "FROM HOADON h " +
                "JOIN DATPHONG dp ON h.maCTT = dp.maDP " +
                "JOIN KHACHHANG k ON dp.maKH = k.MKH " +
                "WHERE DATE(h.ngayThanhToan) BETWEEN ? AND ? AND h.xuLy = 1 " +
                "GROUP BY dp.maKH, k.TKH " +
                "ORDER BY tongTien DESC";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            stmt.setString(2, endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int maKH = rs.getInt("maKH");
                String tenKH = rs.getString("TKH");
                int soLanDatPhong = rs.getInt("soLanDatPhong");
                long tongTien = rs.getLong("tongTien");

                KhachHangThongKeDTO dto = new KhachHangThongKeDTO(maKH, tenKH, soLanDatPhong, tongTien);
                list.add(dto);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn thông tin khách hàng từ " + startDate + " đến " + endDate + ": "
                    + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<ThongKeDTO> getDoanhThu8NgayGanNhat() {
        ArrayList<ThongKeDTO> list = new ArrayList<>();
        if (conn == null) {
            System.err.println("Không thể truy vấn do không có kết nối cơ sở dữ liệu!");
            return list;
        }

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(today)) {
            String formattedDate = currentDate.format(formatter);
            String query = "SELECT SUM(tienP) as doanhThuPhong, SUM(tienDV) as doanhThuDichVu, SUM(tongTien) as tongDoanhThu "
                    +
                    "FROM HOADON " +
                    "WHERE DATE(ngayThanhToan) = ? AND xuLy = 1";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, formattedDate);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    long doanhThuPhong = rs.getLong("doanhThuPhong");
                    long doanhThuDichVu = rs.getLong("doanhThuDichVu");
                    long tongDoanhThu = rs.getLong("tongDoanhThu");

                    ThongKeDTO dto = new ThongKeDTO();
                    dto.setNgay(currentDate);
                    dto.setDoanhThuPhong(doanhThuPhong);
                    dto.setDoanhThuDichVu(doanhThuDichVu);
                    dto.setTongDoanhThu(tongDoanhThu);
                    list.add(dto);
                } else {
                    ThongKeDTO dto = new ThongKeDTO();
                    dto.setNgay(currentDate);
                    dto.setDoanhThuPhong(0);
                    dto.setDoanhThuDichVu(0);
                    dto.setTongDoanhThu(0);
                    list.add(dto);
                }
            } catch (SQLException e) {
                System.err.println(
                        "Lỗi khi truy vấn doanh thu 8 ngày gần nhất cho ngày " + formattedDate + ": " + e.getMessage());
                e.printStackTrace();
            }
            currentDate = currentDate.plusDays(1);
        }

        return list;
    }

    public long getTongDoanhThu() {
        if (conn == null) {
            System.err.println("Không thể truy vấn do không có kết nối cơ sở dữ liệu!");
            return 0;
        }

        String query = "SELECT SUM(tongTien) as tongDoanhThu " +
                "FROM HOADON " +
                "WHERE xuLy = 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("tongDoanhThu");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn tổng doanh thu: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public int getTongSoDatPhong() {
        if (conn == null) {
            System.err.println("Không thể truy vấn do không có kết nối cơ sở dữ liệu!");
            return 0;
        }

        String query = "SELECT COUNT(*) as tongSoDatPhong " +
                "FROM DATPHONG " +
                "WHERE xuLy = 1 AND tinhTrangXuLy = 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("tongSoDatPhong");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn tổng số đặt phòng: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public int getKhachHangDangSuDung() {
        if (conn == null) {
            System.err.println("Không thể truy vấn do không có kết nối cơ sở dữ liệu!");
            return 0;
        }

        String query = "SELECT COUNT(DISTINCT maKH) as soKhachHang " +
                "FROM DATPHONG " +
                "WHERE xuLy = 1 AND tinhTrangXuLy = 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("soKhachHang");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn số khách hàng đang sử dụng: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public int getNhanVienDangHoatDong() {
        if (conn == null) {
            System.err.println("Không thể truy vấn do không có kết nối cơ sở dữ liệu!");
            return 0;
        }

        String query = "SELECT COUNT(*) as soNhanVien " +
                "FROM NHANVIEN " +
                "WHERE TT = 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("soNhanVien");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn số nhân viên đang hoạt động: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // public void closeConnection() {
    // ConnectDB.closeConnection(conn);
    // }

    @Override
    public int add(Object t) {
        return 0;
    }

    @Override
    public int update(Object t) {
        return 0;
    }

    @Override
    public int delete(String t) {
        return 0;
    }

    @Override
    public ArrayList<Object> selectAll() {
        return new ArrayList<>();
    }

    @Override
    public Object selectById(String t) {
        return null;
    }

    @Override
    public int getAutoIncrement() {
        return 0;
    }
}
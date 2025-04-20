package BUS;

import DAO.ThongKeDAO;
import DTO.ThongKeDTO;
import DTO.KhachHangThongKeDTO;
import java.time.LocalDate;
import java.util.ArrayList;

public class ThongKeBUS {
    private ThongKeDAO thongKeDAO;

    public ThongKeBUS() {
        thongKeDAO = new ThongKeDAO();
    }

    public ArrayList<ThongKeDTO> getDoanhThuTheoNam(int startYear, int endYear) {
        ArrayList<ThongKeDTO> result = new ArrayList<>();
        for (int year = startYear; year <= endYear; year++) {
            for (int month = 1; month <= 12; month++) {
                ArrayList<ThongKeDTO> monthlyData = getDoanhThuTheoThangNam(year, month);
                result.addAll(monthlyData);
            }
        }
        return result;
    }

    public ArrayList<ThongKeDTO> getDoanhThuTheoThangNam(int year, int month) {
        return thongKeDAO.getDoanhThuTheoThangNam(year, month);
    }

    public ArrayList<ThongKeDTO> getDoanhThuTheoNgayTrongThang(int year, int month) {
        return thongKeDAO.getDoanhThuTheoThangNam(year, month);
    }

    public ArrayList<ThongKeDTO> getDoanhThuTheoKhoangNgay(LocalDate startDate, LocalDate endDate) {
        return thongKeDAO.getDoanhThuTheoKhoangNgay(startDate, endDate);
    }

    // Thêm phương thức: Lấy doanh thu theo loại phòng
    public ArrayList<ThongKeDTO> getDoanhThuTheoLoaiPhong(LocalDate startDate, LocalDate endDate) {
        return thongKeDAO.getDoanhThuTheoLoaiPhong(startDate, endDate);
    }

    // Thêm phương thức: Lấy thông tin khách hàng theo khoảng ngày
    public ArrayList<KhachHangThongKeDTO> getKhachHangTheoKhoangNgay(LocalDate startDate, LocalDate endDate) {
        return thongKeDAO.getKhachHangTheoKhoangNgay(startDate, endDate);
    }

    public ArrayList<ThongKeDTO> getDoanhThuTheoThangTrongNam(int year) {
        ArrayList<ThongKeDTO> result = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            ArrayList<ThongKeDTO> monthlyData = getDoanhThuTheoThangNam(year, month);
            result.addAll(monthlyData);
        }
        return result;
    }

    public ArrayList<ThongKeDTO> getDoanhThu8NgayGanNhat() {
        return thongKeDAO.getDoanhThu8NgayGanNhat();
    }

    public long getTongDoanhThu() {
        return thongKeDAO.getTongDoanhThu();
    }

    public int getTongSoDatPhong() {
        return thongKeDAO.getTongSoDatPhong();
    }

    public int getKhachHangDangSuDung() {
        return thongKeDAO.getKhachHangDangSuDung();
    }

    public int getNhanVienDangHoatDong() {
        return thongKeDAO.getNhanVienDangHoatDong();
    }

    public void closeConnection() {
        config.ConnectDB.closeConnection();
    }
}
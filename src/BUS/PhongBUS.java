package BUS;

import DAO.ChiTietDatPhongDAO;
import DAO.PhongDAO;
import DTO.PhongDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PhongBUS {
    private final PhongDAO phongDAO;
    private final ChiTietDatPhongDAO chiTietDatPhongDAO;

    public PhongBUS() {
        this.phongDAO = new PhongDAO();
        this.chiTietDatPhongDAO = new ChiTietDatPhongDAO();
    }

    public ArrayList<PhongDTO> getAllPhong() {
        return phongDAO.selectAll();
    }

    public PhongDTO getPhongById(String maP) {
        if (maP == null || maP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được rỗng");
        }
        return phongDAO.selectById(maP);
    }

    public int addPhong(PhongDTO p) {
        if (p == null) {
            throw new IllegalArgumentException("Thông tin phòng không được rỗng");
        }
        return phongDAO.add(p);
    }

    public int updatePhong(PhongDTO p) {
        if (p == null) {
            throw new IllegalArgumentException("Thông tin phòng không được rỗng");
        }
        return phongDAO.update(p);
    }

    public int deletePhong(String maP) {
        if (maP == null || maP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được rỗng");
        }
        // Xóa các bản ghi liên quan trong chitietdatphong trước
        chiTietDatPhongDAO.deleteByMaP(maP);
        // Sau đó xóa phòng
        return phongDAO.delete(maP);
    }

    public int getAutoIncrement() {
        return phongDAO.getAutoIncrement();
    }

    public List<Date[]> getThoiGianThue(String maP) {
        if (maP == null || maP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được rỗng");
        }
        return phongDAO.getThoiGianThue(maP);
    }

    public List<PhongDTO> getAllAvailableRooms() throws IllegalStateException {
        try {
            List<PhongDTO> rooms = phongDAO.getAllAvailableRooms();
            if (rooms.isEmpty()) {
                throw new IllegalStateException("Không có phòng trống nào!");
            }
            return rooms;
        } catch (Exception e) {
            throw new IllegalStateException("Lỗi khi lấy danh sách phòng trống: " + e.getMessage(), e);
        }
    }

    public List<PhongDTO> getAvailableRooms(Date checkInDate, Date checkOutDate, String guestType) throws IllegalArgumentException, IllegalStateException {
        // Validate inputs
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Vui lòng chọn ngày nhận phòng và trả phòng!");
        }
        if (checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate)) {
            throw new IllegalArgumentException("Ngày trả phòng phải sau ngày nhận phòng!");
        }

        // Map guestType to room types
        List<String> roomTypes = getRoomTypesByGuestType(guestType);

        // Fetch available rooms
        try {
            List<PhongDTO> rooms = phongDAO.getAvailableRooms(checkInDate, checkOutDate, roomTypes);
            if (rooms.isEmpty()) {
                throw new IllegalStateException("Không tìm thấy phòng trống phù hợp!");
            }
            return rooms;
        } catch (Exception e) {
            throw new IllegalStateException("Lỗi khi lấy danh sách phòng trống: " + e.getMessage(), e);
        }
    }

    public int countAvailableRooms(Date checkInDate, Date checkOutDate, String guestType) {
        try {
            // Map guestType to room types
            List<String> roomTypes = getRoomTypesByGuestType(guestType);

            // If dates are valid, count available rooms within the date range
            if (checkInDate != null && checkOutDate != null && !checkOutDate.before(checkInDate) && !checkOutDate.equals(checkInDate)) {
                List<PhongDTO> rooms = phongDAO.getAvailableRooms(checkInDate, checkOutDate, roomTypes);
                return rooms.size();
            } else {
                // Count all rooms with tinhTrang = 0 for the given room types
                List<PhongDTO> allRooms = phongDAO.getAllAvailableRooms();
                return (int) allRooms.stream()
                        .filter(room -> roomTypes.contains(room.getLoaiP()))
                        .count();
            }
        } catch (Exception e) {
            return 0;
        }
    }

    private List<String> getRoomTypesByGuestType(String guestType) {
        switch (guestType) {
            case "Đi một mình":
                return Arrays.asList("Đơn", "VIP", "Suite");
            case "Cặp đôi/2 người":
                return Arrays.asList("Đôi");
            case "Theo gia đình":
                return Arrays.asList("Gia đình");
            case "Theo đoàn/nhóm":
                return Arrays.asList("Đơn", "Đôi", "Suite", "VIP");
            default:
                throw new IllegalArgumentException("Loại khách không hợp lệ: " + guestType);
        }
    }
}
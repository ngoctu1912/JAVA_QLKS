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
        chiTietDatPhongDAO.deleteByMaP(maP);
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
        if (guestType == null || guestType.trim().isEmpty()) {
            throw new IllegalArgumentException("Loại khách không được rỗng!");
        }

        // Map guestType to room types
        List<String> roomTypes = getRoomTypesByGuestType(guestType);

        // Fetch available rooms
        try {
            List<PhongDTO> rooms = phongDAO.getAvailableRooms(checkInDate, checkOutDate, roomTypes);
            if (rooms.isEmpty()) {
                throw new IllegalStateException("Không tìm thấy phòng trống phù hợp trong khoảng thời gian đã chọn!");
            }
            return rooms;
        } catch (Exception e) {
            throw new IllegalStateException("Lỗi khi lấy danh sách phòng trống: " + e.getMessage(), e);
        }
    }

    public int countAvailableRooms(Date checkInDate, Date checkOutDate, String guestType) {
        try {
            if (checkInDate == null || checkOutDate == null || 
                checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate) ||
                guestType == null || guestType.trim().isEmpty()) {
                return 0; // Invalid inputs, return 0
            }
            List<PhongDTO> rooms = getAvailableRooms(checkInDate, checkOutDate, guestType);
            return rooms.size();
        } catch (IllegalStateException e) {
            return 0; // No available rooms
        } catch (Exception e) {
            return 0; // Other errors
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
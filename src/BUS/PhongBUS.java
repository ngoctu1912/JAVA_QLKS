package BUS;

import DAO.PhongDAO;
import DTO.PhongDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PhongBUS {
    private PhongDAO phongDAO;

    public PhongBUS() {
        this.phongDAO = new PhongDAO();
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

    public List<PhongDTO> getAllAvailableRooms() throws Exception {
        List<PhongDTO> rooms = phongDAO.getAllAvailableRooms();
        if (rooms.isEmpty()) {
            throw new Exception("Không có phòng trống nào!");
        }
        return rooms;
    }

    public List<PhongDTO> getAvailableRooms(Date checkInDate, Date checkOutDate, String guestType) throws Exception {
        // Validate dates
        if (checkInDate == null || checkOutDate == null) {
            throw new Exception("Vui lòng chọn ngày nhận phòng và trả phòng!");
        }
        if (checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate)) {
            throw new Exception("Ngày trả phòng phải sau ngày nhận phòng!");
        }

        // Map guestType to room types
        List<String> roomTypes;
        switch (guestType) {
            case "Đi một mình":
                roomTypes = Arrays.asList("Đơn", "VIP", "Suite");
                break;
            case "Cặp đôi/2 người":
                roomTypes = Arrays.asList("Đôi");
                break;
            case "Theo gia đình":
                roomTypes = Arrays.asList("Gia đình");
                break;
            case "Theo đoàn/nhóm":
                roomTypes = Arrays.asList("Đơn", "Đôi", "Suite", "VIP");
                break;
            default:
                throw new Exception("Loại khách không hợp lệ: " + guestType);
        }

        // Fetch available rooms with the specified room types
        List<PhongDTO> rooms = phongDAO.getAvailableRooms(checkInDate, checkOutDate, roomTypes);

        if (rooms.isEmpty()) {
            throw new Exception("Không tìm thấy phòng trống phù hợp!");
        }

        return rooms;
    }

    public int countAvailableRooms(Date checkInDate, Date checkOutDate, String guestType) {
        try {
            // Map guestType to room types
            List<String> roomTypes;
            switch (guestType) {
                case "Đi một mình":
                    roomTypes = Arrays.asList("Đơn", "VIP", "Suite");
                    break;
                case "Cặp đôi/2 người":
                    roomTypes = Arrays.asList("Đôi");
                    break;
                case "Theo gia đình":
                    roomTypes = Arrays.asList("Gia đình");
                    break;
                case "Theo đoàn/nhóm":
                    roomTypes = Arrays.asList("Đơn", "Đôi", "Suite", "VIP");
                    break;
                default:
                    return 0; // Invalid guest type
            }

            // If dates are valid, count available rooms within the date range
            if (checkInDate != null && checkOutDate != null && !checkOutDate.before(checkInDate) && !checkOutDate.equals(checkInDate)) {
                List<PhongDTO> rooms = phongDAO.getAvailableRooms(checkInDate, checkOutDate, roomTypes);
                return rooms.size();
            } else {
                // Otherwise, count all rooms with tinhTrang = 0 for the given room types
                List<PhongDTO> allRooms = phongDAO.getAllAvailableRooms();
                int count = 0;
                for (PhongDTO room : allRooms) {
                    if (roomTypes.contains(room.getLoaiP())) {
                        count++;
                    }
                }
                return count;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
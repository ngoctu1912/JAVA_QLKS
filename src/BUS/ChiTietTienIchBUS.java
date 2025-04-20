package BUS;

import DAO.ChiTietTienIchDAO;
import DTO.ChiTietTienIchDTO;
import java.util.List;

public class ChiTietTienIchBUS {
    private ChiTietTienIchDAO chiTietTienIchDAO;

    public ChiTietTienIchBUS() {
        this.chiTietTienIchDAO = new ChiTietTienIchDAO();
    }

    public List<ChiTietTienIchDTO> getAmenitiesByRoomId(String maP) {
        return chiTietTienIchDAO.getAmenitiesByRoomId(maP);
    }
}


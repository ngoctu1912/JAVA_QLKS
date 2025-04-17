package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import java.util.List;

public class KhachHangBUS {
    private final KhachHangDAO khachHangDAO;

    public KhachHangBUS() {
        this.khachHangDAO = new KhachHangDAO();
    }

    public List<KhachHangDTO> getAllCustomers() {
        return khachHangDAO.getAllCustomers();
    }
}
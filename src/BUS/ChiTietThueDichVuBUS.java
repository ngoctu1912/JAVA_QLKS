// package BUS;

// import DAO.ChiTietThueDichVuDAO;
// import DTO.ChiTietThueDichVuDTO;
// import java.util.ArrayList;

// public class ChiTietThueDichVuBUS {
//     private ChiTietThueDichVuDAO chiTietThueDichVuDAO;

//     public ChiTietThueDichVuBUS() {
//         this.chiTietThueDichVuDAO = new ChiTietThueDichVuDAO();
//     }

//     public int add(ChiTietThueDichVuDTO t) {
//         return chiTietThueDichVuDAO.add(t);
//     }

//     public int update(ChiTietThueDichVuDTO t) {
//         return chiTietThueDichVuDAO.update(t);
//     }

//     public int delete(String id) {
//         return chiTietThueDichVuDAO.delete(id);
//     }

//     public ArrayList<ChiTietThueDichVuDTO> selectAll() {
//         return chiTietThueDichVuDAO.selectAll();
//     }

//     public ChiTietThueDichVuDTO selectById(String id) {
//         return chiTietThueDichVuDAO.selectById(id);
//     }

//     public int getAutoIncrement() {
//         return chiTietThueDichVuDAO.getAutoIncrement();
//     }
// }

package BUS;

import DAO.ChiTietThueDichVuDAO;
import DTO.ChiTietThueDichVuDTO;
import java.sql.Connection;
import java.util.ArrayList;

public class ChiTietThueDichVuBUS {
    private ChiTietThueDichVuDAO chiTietThueDichVuDAO;

    public ChiTietThueDichVuBUS() {
        this.chiTietThueDichVuDAO = new ChiTietThueDichVuDAO();
    }

    public int add(ChiTietThueDichVuDTO t) {
        return chiTietThueDichVuDAO.add(t);
    }

    public int add(ChiTietThueDichVuDTO t, Connection conn) {
        return chiTietThueDichVuDAO.add(t, conn);
    }

    public int update(ChiTietThueDichVuDTO t) {
        return chiTietThueDichVuDAO.update(t);
    }

    public int delete(String id) {
        return chiTietThueDichVuDAO.delete(id);
    }

    public ArrayList<ChiTietThueDichVuDTO> selectAll() {
        return chiTietThueDichVuDAO.selectAll();
    }

    public ChiTietThueDichVuDTO selectById(String id) {
        return chiTietThueDichVuDAO.selectById(id);
    }

    public int getAutoIncrement() {
        return chiTietThueDichVuDAO.getAutoIncrement();
    }
}
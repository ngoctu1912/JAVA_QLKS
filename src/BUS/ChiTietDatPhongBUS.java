// // package BUS;

// // import DAO.ChiTietDatPhongDAO;
// // import DTO.ChiTietDatPhongDTO;
// // import java.util.ArrayList;

// // public class ChiTietDatPhongBUS {
// //     private final ChiTietDatPhongDAO ctdpDAO;

// //     public ChiTietDatPhongBUS() {
// //         this.ctdpDAO = ChiTietDatPhongDAO.getInstance();
// //     }

// //     public int add(ChiTietDatPhongDTO ctdp) {
// //         if (ctdp.getMaCTDP() == null || ctdp.getMaCTDP().isEmpty()) {
// //             throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
// //         }
// //         if (ctdp.getMaP() == null || ctdp.getMaP().isEmpty()) {
// //             throw new IllegalArgumentException("Mã phòng không được để trống!");
// //         }
// //         if (ctdp.getNgayThue() == null) {
// //             throw new IllegalArgumentException("Ngày thuê không được để trống!");
// //         }
// //         if (ctdp.getNgayTra() == null) {
// //             throw new IllegalArgumentException("Ngày trả không được để trống!");
// //         }
// //         return ctdpDAO.add(ctdp);
// //     }

// //     public int update(ChiTietDatPhongDTO ctdp) {
// //         if (ctdp.getMaCTDP() == null || ctdp.getMaCTDP().isEmpty()) {
// //             throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
// //         }
// //         return ctdpDAO.update(ctdp);
// //     }

// //     public int delete(String maCTDP) {
// //         if (maCTDP == null || maCTDP.isEmpty()) {
// //             throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
// //         }
// //         return ctdpDAO.delete(maCTDP);
// //     }

// //     public int deleteByMaP(String maP) {
// //         if (maP == null || maP.isEmpty()) {
// //             throw new IllegalArgumentException("Mã phòng không được để trống!");
// //         }
// //         return ctdpDAO.deleteByMaP(maP);
// //     }

// //     public ArrayList<ChiTietDatPhongDTO> getAll() {
// //         return ctdpDAO.selectAll();
// //     }

// //     public ChiTietDatPhongDTO getById(String maCTDP) {
// //         if (maCTDP == null || maCTDP.isEmpty()) {
// //             throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
// //         }
// //         return ctdpDAO.selectById(maCTDP);
// //     }

// //     public int getAutoIncrement() {
// //         return ctdpDAO.getAutoIncrement();
// //     }
// // }

// package BUS;

// import DAO.ChiTietDatPhongDAO;
// import DTO.ChiTietDatPhongDTO;
// import java.util.ArrayList;
// import java.sql.Connection;
// import java.sql.SQLException;

// public class ChiTietDatPhongBUS {
//     private final ChiTietDatPhongDAO ctdpDAO;

//     public ChiTietDatPhongBUS() {
//         this.ctdpDAO = ChiTietDatPhongDAO.getInstance();
//     }

//     public int add(ChiTietDatPhongDTO ctdp) {
//         if (ctdp.getMaCTDP() == null || ctdp.getMaCTDP().isEmpty()) {
//             throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
//         }
//         if (ctdp.getMaP() == null || ctdp.getMaP().isEmpty()) {
//             throw new IllegalArgumentException("Mã phòng không được để trống!");
//         }
//         if (ctdp.getNgayThue() == null) {
//             throw new IllegalArgumentException("Ngày thuê không được để trống!");
//         }
//         if (ctdp.getNgayTra() == null) {
//             throw new IllegalArgumentException("Ngày trả không được để trống!");
//         }
//         return ctdpDAO.add(ctdp);
//     }

//     public int add(ChiTietDatPhongDTO ctdp, Connection conn) throws SQLException {
//         if (ctdp.getMaCTDP() == null || ctdp.getMaCTDP().isEmpty()) {
//             throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
//         }
//         if (ctdp.getMaP() == null || ctdp.getMaP().isEmpty()) {
//             throw new IllegalArgumentException("Mã phòng không được để trống!");
//         }
//         if (ctdp.getNgayThue() == null) {
//             throw new IllegalArgumentException("Ngày thuê không được để trống!");
//         }
//         if (ctdp.getNgayTra() == null) {
//             throw new IllegalArgumentException("Ngày trả không được để trống!");
//         }
//         return ctdpDAO.add(ctdp, conn);
//     }

//     public int update(ChiTietDatPhongDTO ctdp) {
//         if (ctdp.getMaCTDP() == null || ctdp.getMaCTDP().isEmpty()) {
//             throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
//         }
//         return ctdpDAO.update(ctdp);
//     }

//     public int delete(String maCTDP) {
//         if (maCTDP == null || maCTDP.isEmpty()) {
//             throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
//         }
//         return ctdpDAO.delete(maCTDP);
//     }

//     public int deleteByMaP(String maP) {
//         if (maP == null || maP.isEmpty()) {
//             throw new IllegalArgumentException("Mã phòng không được để trống!");
//         }
//         return ctdpDAO.deleteByMaP(maP);
//     }

//     public ArrayList<ChiTietDatPhongDTO> getAll() {
//         return ctdpDAO.selectAll();
//     }

//     public ChiTietDatPhongDTO getById(String maCTDP) {
//         if (maCTDP == null || maCTDP.isEmpty()) {
//             throw new IllegalArgumentException("Mã chi tiết đặt phòng không được để trống!");
//         }
//         return ctdpDAO.selectById(maCTDP);
//     }

//     public int getAutoIncrement() {
//         return ctdpDAO.getAutoIncrement();
//     }
// }

package BUS;

import DAO.ChiTietDatPhongDAO;
import DTO.ChiTietDatPhongDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietDatPhongBUS {
    private ChiTietDatPhongDAO chiTietDatPhongDAO;

    public ChiTietDatPhongBUS() {
        this.chiTietDatPhongDAO = new ChiTietDatPhongDAO();
    }

    public int add(ChiTietDatPhongDTO t) {
        return chiTietDatPhongDAO.add(t);
    }

    public int add(ChiTietDatPhongDTO t, Connection conn) throws SQLException {
        return chiTietDatPhongDAO.add(t, conn);
    }

    public int update(ChiTietDatPhongDTO t) {
        return chiTietDatPhongDAO.update(t);
    }

    public int delete(String id) {
        return chiTietDatPhongDAO.delete(id);
    }

    public ArrayList<ChiTietDatPhongDTO> selectAll() {
        return chiTietDatPhongDAO.selectAll();
    }

    public ChiTietDatPhongDTO selectById(String id) {
        return chiTietDatPhongDAO.selectById(id);
    }

    public int getAutoIncrement() {
        return chiTietDatPhongDAO.getAutoIncrement();
    }
}
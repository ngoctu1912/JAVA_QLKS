// // package BUS;

// // import DAO.DatPhongDAO;
// // import DTO.DatPhongDTO;

// // import java.sql.SQLException;
// // import java.util.ArrayList;

// // public class DatPhongBUS {
// //     private DatPhongDAO datPhongDAO;

// //     public DatPhongBUS() {
// //         this.datPhongDAO = DatPhongDAO.getInstance();
// //     }

// //     public int add(DatPhongDTO datPhong) {
// //         if (datPhong == null || datPhong.getMaDP() == null || datPhong.getMaDP().trim().isEmpty()) {
// //             throw new IllegalArgumentException("Mã đặt phòng không được để trống");
// //         }
// //         if (datPhong.getMaKH() == 0) {
// //             throw new IllegalArgumentException("Mã khách hàng không được để trống");
// //         }
// //         return datPhongDAO.add(datPhong);
// //     }

// //     public int update(DatPhongDTO datPhong) {
// //         if (datPhong == null || datPhong.getMaDP() == null || datPhong.getMaDP().trim().isEmpty()) {
// //             throw new IllegalArgumentException("Mã đặt phòng không được để trống");
// //         }
// //         return datPhongDAO.update(datPhong);
// //     }

// //     public int delete(String maDP) {
// //         if (maDP == null || maDP.trim().isEmpty()) {
// //             throw new IllegalArgumentException("Mã đặt phòng không được để trống");
// //         }
// //         return datPhongDAO.delete(maDP);
// //     }

// //     public ArrayList<DatPhongDTO> selectAll() {
// //         return datPhongDAO.selectAll();
// //     }

// //     public DatPhongDTO selectById(String maDP) {
// //         if (maDP == null || maDP.trim().isEmpty()) {
// //             throw new IllegalArgumentException("Mã đặt phòng không được để trống");
// //         }
// //         return datPhongDAO.selectById(maDP);
// //     }

// //     public int getAutoIncrement() {
// //         return datPhongDAO.getAutoIncrement();
// //     }

// //     public boolean checkExists(String maDP) {
// //         try {
// //             return datPhongDAO.checkExists(maDP);
// //         } catch (SQLException e) {
// //             e.printStackTrace();
// //             return false;
// //         }
// //     }
// // }

// package BUS;

// import DAO.DatPhongDAO;
// import DTO.DatPhongDTO;

// import java.sql.Connection;
// import java.sql.SQLException;
// import java.util.ArrayList;

// public class DatPhongBUS {
//     private DatPhongDAO datPhongDAO;

//     public DatPhongBUS() {
//         this.datPhongDAO = DatPhongDAO.getInstance();
//     }

//     public int add(DatPhongDTO datPhong) {
//         if (datPhong == null || datPhong.getMaDP() == null || datPhong.getMaDP().trim().isEmpty()) {
//             throw new IllegalArgumentException("Mã đặt phòng không được để trống");
//         }
//         if (datPhong.getMaKH() == 0) {
//             throw new IllegalArgumentException("Mã khách hàng không được để trống");
//         }
//         return datPhongDAO.add(datPhong);
//     }

//     public int add(DatPhongDTO datPhong, Connection conn) throws SQLException {
//         if (datPhong == null || datPhong.getMaDP() == null || datPhong.getMaDP().trim().isEmpty()) {
//             throw new IllegalArgumentException("Mã đặt phòng không được để trống");
//         }
//         if (datPhong.getMaKH() == 0) {
//             throw new IllegalArgumentException("Mã khách hàng không được để trống");
//         }
//         return datPhongDAO.add(datPhong, conn);
//     }

//     public int update(DatPhongDTO datPhong) {
//         if (datPhong == null || datPhong.getMaDP() == null || datPhong.getMaDP().trim().isEmpty()) {
//             throw new IllegalArgumentException("Mã đặt phòng không được để trống");
//         }
//         return datPhongDAO.update(datPhong);
//     }

//     public int delete(String maDP) {
//         if (maDP == null || maDP.trim().isEmpty()) {
//             throw new IllegalArgumentException("Mã đặt phòng không được để trống");
//         }
//         return datPhongDAO.delete(maDP);
//     }

//     public ArrayList<DatPhongDTO> selectAll() {
//         return datPhongDAO.selectAll();
//     }

//     public DatPhongDTO selectById(String maDP) {
//         if (maDP == null || maDP.trim().isEmpty()) {
//             throw new IllegalArgumentException("Mã đặt phòng không được để trống");
//         }
//         return datPhongDAO.selectById(maDP);
//     }

//     public int getAutoIncrement() {
//         return datPhongDAO.getAutoIncrement();
//     }

//     public boolean checkExists(String maDP) {
//         try {
//             return datPhongDAO.checkExists(maDP);
//         } catch (SQLException e) {
//             throw new RuntimeException("Lỗi khi kiểm tra mã đặt phòng: " + e.getMessage(), e);
//         }
//     }
// }

package BUS;

import DAO.DatPhongDAO;
import DTO.DatPhongDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatPhongBUS {
    private DatPhongDAO datPhongDAO;

    public DatPhongBUS() {
        this.datPhongDAO = DatPhongDAO.getInstance();
    }

    public int add(DatPhongDTO datPhong) {
        if (datPhong == null || datPhong.getMaDP() == null || datPhong.getMaDP().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã đặt phòng không được để trống");
        }
        if (datPhong.getMaKH() == 0) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống");
        }
        return datPhongDAO.add(datPhong);
    }

    public int add(DatPhongDTO datPhong, Connection conn) throws SQLException {
        if (datPhong == null || datPhong.getMaDP() == null || datPhong.getMaDP().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã đặt phòng không được để trống");
        }
        if (datPhong.getMaKH() == 0) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống");
        }
        return datPhongDAO.add(datPhong, conn);
    }

    public int update(DatPhongDTO datPhong) {
        if (datPhong == null || datPhong.getMaDP() == null || datPhong.getMaDP().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã đặt phòng không được để trống");
        }
        return datPhongDAO.update(datPhong);
    }

    public int delete(String maDP) {
        if (maDP == null || maDP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã đặt phòng không được để trống");
        }
        return datPhongDAO.delete(maDP);
    }

    public ArrayList<DatPhongDTO> selectAll() {
        return datPhongDAO.selectAll();
    }

    public DatPhongDTO selectById(String maDP) {
        if (maDP == null || maDP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã đặt phòng không được để trống");
        }
        return datPhongDAO.selectById(maDP);
    }

    public int getAutoIncrement() {
        return datPhongDAO.getAutoIncrement();
    }

    public boolean checkExists(String maDP) {
        try {
            return datPhongDAO.checkExists(maDP);
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi kiểm tra mã đặt phòng: " + e.getMessage(), e);
        }
    }
}
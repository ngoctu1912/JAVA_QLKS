// package BUS;

// import DAO.HoaDonDAO;
// import DTO.HoaDonDTO;
// import java.util.ArrayList;

// public class HoaDonBUS {
//     private ArrayList<HoaDonDTO> listHD;

//     public HoaDonBUS() {
//         listHD = HoaDonDAO.getInstance().selectAll();
//     }

//     public ArrayList<HoaDonDTO> getAll() {
//         this.listHD = HoaDonDAO.getInstance().selectAll();
//         return this.listHD;
//     }

//     public int add(HoaDonDTO hd) {
//         int result = HoaDonDAO.getInstance().add(hd);
//         if (result > 0) {
//             listHD.add(hd);
//         }
//         return result;
//     }

//     public int update(HoaDonDTO hd) {
//         int result = HoaDonDAO.getInstance().update(hd);
//         if (result > 0) {
//             for (int i = 0; i < listHD.size(); i++) {
//                 if (listHD.get(i).getMaHD().equals(hd.getMaHD())) {
//                     listHD.set(i, hd);
//                     break;
//                 }
//             }
//         }
//         return result;
//     }

//     public int delete(HoaDonDTO hd) {
//         int result = HoaDonDAO.getInstance().delete(hd.getMaHD());
//         if (result > 0) {
//             listHD.removeIf(item -> item.getMaHD().equals(hd.getMaHD()));
//         }
//         return result;
//     }

//     public ArrayList<HoaDonDTO> search(String keyword, String type) {
//         ArrayList<HoaDonDTO> result = new ArrayList<>();
//         keyword = keyword.toLowerCase();
//         switch (type) {
//             case "Tất cả":
//                 for (HoaDonDTO hd : listHD) {
//                     if (hd.getMaHD().toLowerCase().contains(keyword) ||
//                         hd.getMaCTT().toLowerCase().contains(keyword) ||
//                         hd.getHinhThucThanhToan().toLowerCase().contains(keyword)) {
//                         result.add(hd);
//                     }
//                 }
//                 break;
//             case "Mã hóa đơn":
//                 for (HoaDonDTO hd : listHD) {
//                     if (hd.getMaHD().toLowerCase().contains(keyword)) {
//                         result.add(hd);
//                     }
//                 }
//                 break;
//             case "Mã chi tiết thuê":
//                 for (HoaDonDTO hd : listHD) {
//                     if (hd.getMaCTT().toLowerCase().contains(keyword)) {
//                         result.add(hd);
//                     }
//                 }
//                 break;
//         }
//         return result;
//     }
// }

package BUS;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;
import config.ConnectDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class HoaDonBUS {
    private ArrayList<HoaDonDTO> listHD;

    public HoaDonBUS() {
        listHD = HoaDonDAO.getInstance().selectAll();
    }

    public ArrayList<HoaDonDTO> getAll() {
        this.listHD = HoaDonDAO.getInstance().selectAll();
        return this.listHD;
    }

    public int add(HoaDonDTO hd) {
        int result = HoaDonDAO.getInstance().add(hd);
        if (result > 0) {
            listHD.add(hd);
        }
        return result;
    }

    public int update(HoaDonDTO hd) {
        int result = HoaDonDAO.getInstance().update(hd);
        if (result > 0) {
            for (int i = 0; i < listHD.size(); i++) {
                if (listHD.get(i).getMaHD().equals(hd.getMaHD())) {
                    listHD.set(i, hd);
                    break;
                }
            }
        }
        return result;
    }

    public int delete(HoaDonDTO hd) {
        int result = HoaDonDAO.getInstance().delete(hd.getMaHD());
        if (result > 0) {
            listHD.removeIf(item -> item.getMaHD().equals(hd.getMaHD()));
        }
        return result;
    }

    public int cancel(String maHD) {
        int result = HoaDonDAO.getInstance().cancel(maHD);
        if (result > 0) {
            for (HoaDonDTO hd : listHD) {
                if (hd.getMaHD().equals(maHD)) {
                    hd.setXuLy(0); // Set xuLy to 0 (canceled)
                    break;
                }
            }
        }
        return result;
    }

    public ArrayList<HoaDonDTO> search(String keyword, String type) {
        ArrayList<HoaDonDTO> result = new ArrayList<>();
        keyword = keyword.toLowerCase();
        switch (type) {
            case "Tất cả":
                for (HoaDonDTO hd : listHD) {
                    if (hd.getMaHD().toLowerCase().contains(keyword) ||
                            hd.getMaCTT().toLowerCase().contains(keyword) ||
                            hd.getHinhThucThanhToan().toLowerCase().contains(keyword)) {
                        result.add(hd);
                    }
                }
                break;
            case "Mã hóa đơn":
                for (HoaDonDTO hd : listHD) {
                    if (hd.getMaHD().toLowerCase().contains(keyword)) {
                        result.add(hd);
                    }
                }
                break;
            case "Mã chi tiết thuê":
                for (HoaDonDTO hd : listHD) {
                    if (hd.getMaCTT().toLowerCase().contains(keyword)) {
                        result.add(hd);
                    }
                }
                break;
            case "Khách hàng":
            case "Nhân viên":
                // Skip for now, as maKH and maNV are not in HOADON
                result = new ArrayList<>(listHD);
                break;
        }
        return result;
    }

    public ArrayList<HoaDonDTO> filterHoaDon(int type, String input, String maKH, int maNV, Date timeStart,
            Date timeEnd, String minPrice, String maxPrice) {
        ArrayList<HoaDonDTO> result = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "SELECT hd.* FROM HoaDon hd LEFT JOIN CTThue ctt ON hd.MaCTT = ctt.MaCTT WHERE 1=1");

        // Add filtering conditions
        if (input != null && !input.isEmpty()) {
            switch (type) {
                case 1: // Mã HD
                    query.append(" AND hd.MaHD LIKE ?");
                    break;
                case 2: // Mã CTT
                    query.append(" AND hd.MaCTT LIKE ?");
                    break;
                case 3: // Hình thức TT
                    query.append(" AND hd.HinhThucThanhToan LIKE ?");
                    break;
                case 0: // Tất cả
                    query.append(" AND (hd.MaHD LIKE ? OR hd.MaCTT LIKE ? OR hd.HinhThucThanhToan LIKE ?)");
                    break;
            }
        }

        if (maKH != null && !maKH.isEmpty()) {
            query.append(" AND ctt.MaKhachHang = ?");
        }

        if (maNV != 0) {
            query.append(" AND ctt.MaNhanVien = ?");
        }

        query.append(" AND hd.NgayThanhToan BETWEEN ? AND ?");

        if (!minPrice.isEmpty()) {
            query.append(" AND hd.TongTien >= ?");
        }

        if (!maxPrice.isEmpty()) {
            query.append(" AND hd.TongTien <= ?");
        }

        try (PreparedStatement stmt = ConnectDB.getConnection()) {
            int paramIndex = 1;

            if (input != null && !input.isEmpty()) {
                String likePattern = "%" + input + "%";
                switch (type) {
                    case 1:
                        stmt.setString(paramIndex++, likePattern);
                        break;
                    case 2:
                        stmt.setString(paramIndex++, likePattern);
                        break;
                    case 3:
                        stmt.setString(paramIndex++, likePattern);
                        break;
                    case 0:
                        stmt.setString(paramIndex++, likePattern);
                        stmt.setString(paramIndex++, likePattern);
                        stmt.setString(paramIndex++, likePattern);
                        break;
                }
            }

            if (maKH != null && !maKH.isEmpty()) {
                stmt.setString(paramIndex++, maKH);
            }

            if (maNV != 0) {
                stmt.setInt(paramIndex++, maNV);
            }

            stmt.setTimestamp(paramIndex++, new java.sql.Timestamp(timeStart.getTime()));
            stmt.setTimestamp(paramIndex++, new java.sql.Timestamp(timeEnd.getTime()));

            if (!minPrice.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(minPrice));
            }

            if (!maxPrice.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(maxPrice));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO();
                hd.setMaHD(rs.getString("MaHD"));
                hd.setMaCTT(rs.getString("MaCTT"));
                hd.setTienP(rs.getInt("TienP"));
                hd.setTienDV(rs.getInt("TienDV"));
                hd.setGiamGia(rs.getInt("GiamGia"));
                hd.setPhuThu(rs.getInt("PhuThu"));
                hd.setTongTien(rs.getInt("TongTien"));
                hd.setNgayThanhToan(rs.getTimestamp("NgayThanhToan"));
                hd.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                hd.setXuLy(rs.getInt("XuLy"));
                result.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
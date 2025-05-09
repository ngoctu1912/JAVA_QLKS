package BUS;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;
import config.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    public HoaDonDTO selectById(String maHD) {
        return HoaDonDAO.getInstance().selectById(maHD);
    }

    public int add(HoaDonDTO hd, Connection conn) throws SQLException {
        int result = HoaDonDAO.getInstance().add(hd, conn);
        if (result > 0) {
            listHD.add(hd);
        }
        return result;
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
                    hd.setXuLy(0);
                    break;
                }
            }
        }
        return result;
    }

    public ArrayList<HoaDonDTO> search(String keyword, String type) {
        ArrayList<HoaDonDTO> result = new ArrayList<>();
        keyword = keyword != null ? keyword.toLowerCase() : "";
        
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
                try (Connection conn = ConnectDB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(
                         "SELECT hd.* FROM HOADON hd " +
                         "INNER JOIN DATPHONG dp ON hd.maCTT = dp.maDP " +
                         "INNER JOIN KHACHHANG k ON dp.maKH = k.MKH " +
                         "WHERE k.MKH LIKE ? OR k.TKH LIKE ?")) {
                    String likePattern = "%" + keyword + "%";
                    stmt.setString(1, likePattern);
                    stmt.setString(2, likePattern);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        HoaDonDTO hd = new HoaDonDTO(
                                rs.getString("MaHD"),
                                rs.getString("MaCTT"),
                                rs.getInt("TienP"),
                                rs.getInt("TienDV"),
                                rs.getInt("GiamGia"),
                                rs.getInt("PhuThu"),
                                rs.getInt("TongTien"),
                                rs.getTimestamp("NgayThanhToan"),
                                rs.getString("HinhThucThanhToan"),
                                rs.getInt("XuLy")
                        );
                        result.add(hd);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "Nhân viên":
                try (Connection conn = ConnectDB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(
                         "SELECT hd.* FROM HOADON hd " +
                         "INNER JOIN DATPHONG dp ON hd.maCTT = dp.maDP " +
                         "WHERE dp.maNV LIKE ?")) {
                    String likePattern = "%" + keyword + "%";
                    stmt.setString(1, likePattern);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        HoaDonDTO hd = new HoaDonDTO(
                                rs.getString("MaHD"),
                                rs.getString("MaCTT"),
                                rs.getInt("TienP"),
                                rs.getInt("TienDV"),
                                rs.getInt("GiamGia"),
                                rs.getInt("PhuThu"),
                                rs.getInt("TongTien"),
                                rs.getTimestamp("NgayThanhToan"),
                                rs.getString("HinhThucThanhToan"),
                                rs.getInt("XuLy")
                        );
                        result.add(hd);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return result;
    }

    // public ArrayList<HoaDonDTO> filterHoaDon(int type, String input, String maKH, int maNV, Date timeStart,
    //         Date timeEnd, String minPrice, String maxPrice) {
    //     ArrayList<HoaDonDTO> result = new ArrayList<>();
    //     StringBuilder query = new StringBuilder(
    //             "SELECT hd.* FROM HOADON hd " +
    //             "INNER JOIN DATPHONG dp ON hd.maCTT = dp.maDP " +
    //             "INNER JOIN KHACHHANG k ON dp.maKH = k.MKH " +
    //             "WHERE 1=1");

    //     ArrayList<Object> params = new ArrayList<>();

    //     if (input != null && !input.isEmpty()) {
    //         String likePattern = "%" + input + "%";
    //         switch (type) {
    //             case 1:
    //                 query.append(" AND hd.maHD LIKE ?");
    //                 params.add(likePattern);
    //                 break;
    //             case 2:
    //                 query.append(" AND hd.maCTT LIKE ?");
    //                 params.add(likePattern);
    //                 break;
    //             case 3:
    //                 query.append(" AND hd.hinhThucThanhToan LIKE ?");
    //                 params.add(likePattern);
    //                 break;
    //             case 0:
    //                 query.append(" AND (hd.maHD LIKE ? OR hd.maCTT LIKE ? OR hd.hinhThucThanhToan LIKE ?)");
    //                 params.add(likePattern);
    //                 params.add(likePattern);
    //                 params.add(likePattern);
    //                 break;
    //         }
    //     }

    //     if (maKH != null && !maKH.isEmpty()) {
    //         query.append(" AND k.MKH = ?");
    //         try {
    //             params.add(Integer.parseInt(maKH));
    //         } catch (NumberFormatException e) {
    //             params.add(maKH);
    //         }
    //     }

    //     if (maNV != 0) {
    //         query.append(" AND dp.maNV = ?");
    //         params.add(maNV);
    //     }

    //     if (timeStart != null) {
    //         query.append(" AND hd.ngayThanhToan >= ?");
    //         params.add(new java.sql.Timestamp(timeStart.getTime()));
    //     }

    //     if (timeEnd != null) {
    //         query.append(" AND hd.ngayThanhToan <= ?");
    //         params.add(new java.sql.Timestamp(timeEnd.getTime()));
    //     }

    //     if (minPrice != null && !minPrice.isEmpty()) {
    //         try {
    //             query.append(" AND hd.tongTien >= ?");
    //             params.add(Integer.parseInt(minPrice));
    //         } catch (NumberFormatException e) {
    //             System.err.println("Invalid minPrice: " + minPrice);
    //         }
    //     }

    //     if (maxPrice != null && !maxPrice.isEmpty()) {
    //         try {
    //             query.append(" AND hd.tongTien <= ?");
    //             params.add(Integer.parseInt(maxPrice));
    //         } catch (NumberFormatException e) {
    //             System.err.println("Invalid maxPrice: " + maxPrice);
    //         }
    //     }

    //     System.out.println("filterHoaDon query: " + query.toString());
    //     System.out.println("filterHoaDon params: " + params);

    //     try (Connection conn = ConnectDB.getConnection();
    //          PreparedStatement stmt = conn.prepareStatement(query.toString())) {
    //         for (int i = 0; i < params.size(); i++) {
    //             stmt.setObject(i + 1, params.get(i));
    //         }

    //         ResultSet rs = stmt.executeQuery();
    //         while (rs.next()) {
    //             HoaDonDTO hd = new HoaDonDTO(
    //                     rs.getString("maHD"),
    //                     rs.getString("maCTT"),
    //                     rs.getInt("tienP"),
    //                     rs.getInt("tienDV"),
    //                     rs.getInt("giamGia"),
    //                     rs.getInt("phuThu"),
    //                     rs.getInt("tongTien"),
    //                     rs.getTimestamp("ngayThanhToan"),
    //                     rs.getString("hinhThucThanhToan"),
    //                     rs.getInt("xuLy")
    //             );
    //             result.add(hd);
    //         }
    //     } catch (SQLException e) {
    //         System.err.println("SQL Error in filterHoaDon: " + e.getMessage());
    //         e.printStackTrace();
    //     }

    //     System.out.println("filterHoaDon result: " + result.size() + " invoices");
    //     return result;
    // }
    public ArrayList<HoaDonDTO> filterHoaDon(int searchType, String keyword, String maKH, int tongTien, Date startDate, Date endDate, String hinhThucThanhToan, String sort) {
        ArrayList<HoaDonDTO> list = new ArrayList<>();
        StringBuilder query = new StringBuilder(
            "SELECT h.* FROM HOADON h " +
            "JOIN DATPHONG d ON h.maCTT = d.maDP " +
            "JOIN KHACHHANG k ON d.maKH = k.MKH " +
            "WHERE 1=1"
        );
        ArrayList<Object> params = new ArrayList<>();
    
        if (maKH != null && !maKH.isEmpty()) {
            query.append(" AND k.MKH = ?");
            params.add(maKH);
        }
    
        // Chỉ lọc xuLy nếu searchType được chỉ định rõ ràng
        if (searchType == 0 || searchType == 1) {
            query.append(" AND h.xuLy = ?");
            params.add(searchType);
        }
    
        if (keyword != null && !keyword.isEmpty()) {
            query.append(" AND (h.maHD LIKE ? OR h.maCTT LIKE ?)");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }
    
        if (tongTien > 0) {
            query.append(" AND h.tongTien = ?");
            params.add(tongTien);
        }
    
        // Chỉ thêm điều kiện ngày nếu được cung cấp hợp lệ
        if (startDate != null && startDate.getTime() > 0 && !startDate.equals(new Date(0))) {
            query.append(" AND h.ngayThanhToan >= ?");
            params.add(new Timestamp(startDate.getTime()));
        }
        if (endDate != null && endDate.getTime() > 0 && !endDate.equals(new Date())) {
            query.append(" AND h.ngayThanhToan <= ?");
            params.add(new Timestamp(endDate.getTime()));
        }
    
        if (sort != null && !sort.isEmpty()) {
            query.append(" ORDER BY ").append(sort);
        }
    
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            System.out.println("filterHoaDon query: " + query.toString());
            System.out.println("filterHoaDon params: " + params);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new HoaDonDTO(
                    rs.getString("maHD"),
                    rs.getString("maCTT"),
                    rs.getInt("tienP"),
                    rs.getInt("tienDV"),
                    rs.getInt("giamGia"),
                    rs.getInt("phuThu"),
                    rs.getInt("tongTien"),
                    rs.getTimestamp("ngayThanhToan"),
                    rs.getString("hinhThucThanhToan"),
                    rs.getInt("xuLy")
                ));
            }
            System.out.println("filterHoaDon result: " + list.size() + " invoices");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
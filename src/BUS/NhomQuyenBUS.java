package BUS;

import DAO.ChiTietQuyenDAO;
import DAO.NhomQuyenDAO;
import DTO.ChiTietQuyenDTO;
import DTO.NhomQuyenDTO;
import config.ConnectDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhomQuyenBUS {

    private final NhomQuyenDAO nhomQuyenDAO = new NhomQuyenDAO();
    private final ChiTietQuyenDAO chiTietQuyenDAO = new ChiTietQuyenDAO();
    private final ArrayList<NhomQuyenDTO> listNhomQuyen;

    public NhomQuyenBUS() {
        this.listNhomQuyen = nhomQuyenDAO.selectAll();
    }

    public ArrayList<NhomQuyenDTO> getAll() {
        return new ArrayList<>(this.listNhomQuyen);
    }

    public NhomQuyenDAO getNhomQuyenDAO() {
        return nhomQuyenDAO;
    }

    public NhomQuyenDTO getByIndex(int index) {
        if (index >= 0 && index < listNhomQuyen.size()) {
            return this.listNhomQuyen.get(index);
        }
        return null;
    }

    public boolean add(String tenNhomQuyen, ArrayList<ChiTietQuyenDTO> chiTietQuyen) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false);

            NhomQuyenDTO nhomQuyen = new NhomQuyenDTO(nhomQuyenDAO.getAutoIncrement(), tenNhomQuyen);
            boolean success = nhomQuyenDAO.add(nhomQuyen) > 0;
            if (success) {
                this.listNhomQuyen.add(nhomQuyen);
                if (chiTietQuyen != null && !chiTietQuyen.isEmpty()) {
                    // Xóa quyền cũ nếu có
                    removeChiTietQuyen(String.valueOf(nhomQuyen.getMNQ()));
                    for (ChiTietQuyenDTO ctq : chiTietQuyen) {
                        ctq.setMNQ(nhomQuyen.getMNQ());
                    }
                    success &= addChiTietQuyen(chiTietQuyen);
                }
            }

            if (success) {
                conn.commit();
            } else {
                conn.rollback();
            }
            return success;
        } catch (SQLException e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean update(NhomQuyenDTO nhomQuyen, ArrayList<ChiTietQuyenDTO> chiTietQuyen, int index) {
        if (index < 0 || index >= listNhomQuyen.size()) {
            return false;
        }
        boolean success = nhomQuyenDAO.update(nhomQuyen) > 0;
        if (success) {
            removeChiTietQuyen(String.valueOf(nhomQuyen.getMNQ()));
            if (chiTietQuyen != null && !chiTietQuyen.isEmpty()) {
                success &= addChiTietQuyen(chiTietQuyen);
            }
            this.listNhomQuyen.set(index, nhomQuyen);
        }
        return success;
    }

    public boolean delete(NhomQuyenDTO nhomQuyen) {
        if (nhomQuyen == null) {
            return false;
        }
        boolean success = nhomQuyenDAO.delete(String.valueOf(nhomQuyen.getMNQ())) > 0;
        if (success) {
            removeChiTietQuyen(String.valueOf(nhomQuyen.getMNQ()));
            this.listNhomQuyen.remove(nhomQuyen);
        }
        return success;
    }

    public ArrayList<ChiTietQuyenDTO> getChiTietQuyen(String maNhomQuyen) {
        return chiTietQuyenDAO.selectAll(maNhomQuyen);
    }

    public boolean addChiTietQuyen(ArrayList<ChiTietQuyenDTO> chiTietQuyen) {
        if (chiTietQuyen == null || chiTietQuyen.isEmpty()) {
            return false;
        }
        return chiTietQuyenDAO.insert(chiTietQuyen) > 0;
    }

    public boolean removeChiTietQuyen(String maNhomQuyen) {
        return chiTietQuyenDAO.delete(maNhomQuyen) > 0;
    }

    public boolean checkPermission(int maQuyen, String chucNang, String hanhDong) {
        ArrayList<ChiTietQuyenDTO> chiTietQuyen = getChiTietQuyen(String.valueOf(maQuyen));
        for (ChiTietQuyenDTO ctq : chiTietQuyen) {
            if (ctq.getMCN() == Integer.parseInt(chucNang) && ctq.getHANHDONG().equals(hanhDong)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<NhomQuyenDTO> search(String text) {
        if (text == null || text.trim().isEmpty()) {
            System.out.println("Search text is empty, returning all: " + listNhomQuyen.size());
            return getAll();
        }
        text = text.toLowerCase();
        System.out.println("Searching with text: " + text + ", list size: " + listNhomQuyen.size());
        ArrayList<NhomQuyenDTO> result = new ArrayList<>();
        for (NhomQuyenDTO nq : listNhomQuyen) {
            if (String.valueOf(nq.getMNQ()).contains(text) || nq.getTEN().toLowerCase().contains(text)) {
                result.add(nq);
            }
        }
        System.out.println("Search results: " + result.size());
        return result;
    }

    public ArrayList<NhomQuyenDTO> searchById(String text) {
        if (text == null || text.trim().isEmpty()) {
            return getAll();
        }
        text = text.toLowerCase();
        ArrayList<NhomQuyenDTO> result = new ArrayList<>();
        for (NhomQuyenDTO nq : listNhomQuyen) {
            if (String.valueOf(nq.getMNQ()).contains(text)) {
                result.add(nq);
            }
        }
        return result;
    }

    public ArrayList<NhomQuyenDTO> searchByName(String text) {
        if (text == null || text.trim().isEmpty()) {
            return getAll();
        }
        text = text.toLowerCase();
        ArrayList<NhomQuyenDTO> result = new ArrayList<>();
        for (NhomQuyenDTO nq : listNhomQuyen) {
            if (nq.getTEN().toLowerCase().contains(text)) {
                result.add(nq);
            }
        }
        return result;
    }
}
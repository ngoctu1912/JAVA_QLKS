package BUS;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import java.sql.Connection;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class TaiKhoanBUS {
    private TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanBUS(Connection conn) {
        this.taiKhoanDAO = new TaiKhoanDAO(conn);
    }

    public boolean themTaiKhoan(TaiKhoanDTO tk) {
        if (taiKhoanDAO.isAccountExists(tk.getMaNV())) {
            return false; // Tài khoản đã tồn tại
        }
        // Mã hóa mật khẩu trước khi thêm
        String hashedPassword = BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12));
        tk.setMatKhau(hashedPassword);
        return taiKhoanDAO.add(tk);
    }

    public TaiKhoanDTO layTaiKhoanTheoMaNV(int maNV) {
        return taiKhoanDAO.getByMaNV(maNV);
    }

    // Thêm phương thức để lấy tài khoản theo tên đăng nhập
    public TaiKhoanDTO layTaiKhoanTheoTenDangNhap(String tenDangNhap) {
        if (tenDangNhap == null || tenDangNhap.isEmpty() || tenDangNhap.equals("Tên Đăng Nhập")) {
            return null;
        }
        return taiKhoanDAO.getByTenDangNhap(tenDangNhap);
    }

    public List<TaiKhoanDTO> layDanhSachTaiKhoan() {
        return taiKhoanDAO.getAllAccounts();
    }

    public boolean capNhatTaiKhoan(TaiKhoanDTO tk) {
        if (!taiKhoanDAO.isAccountExists(tk.getMaNV())) {
            return false; // Tài khoản không tồn tại
        }
        // Mã hóa mật khẩu trước khi cập nhật
        String hashedPassword = BCrypt.hashpw(tk.getMatKhau(), BCrypt.gensalt(12));
        tk.setMatKhau(hashedPassword);
        return taiKhoanDAO.update(tk);
    }

    public boolean capNhatMatKhau(int maNV, String matKhauMoi) {
        if (!taiKhoanDAO.isAccountExists(maNV)) {
            return false; // Tài khoản không tồn tại
        }
        // Mã hóa mật khẩu mới
        String hashedPassword = BCrypt.hashpw(matKhauMoi, BCrypt.gensalt(12));
        return taiKhoanDAO.updatePassword(maNV, hashedPassword);
    }

    public boolean xoaTaiKhoan(int maNV) {
        if (!taiKhoanDAO.isAccountExists(maNV)) {
            return false; // Tài khoản không tồn tại
        }
        return taiKhoanDAO.delete(maNV);
    }

    public boolean kiemTraDangNhap(String username, String password) {
        // Kiểm tra dữ liệu đầu vào
        if (username == null || username.isEmpty() || username.equals("Tên Đăng Nhập")) {
            return false;
        }
        if (password == null || password.isEmpty() || password.equals("Mật Khẩu")) {
            return false;
        }
        // Lấy tài khoản từ cơ sở dữ liệu
        TaiKhoanDTO taiKhoan = taiKhoanDAO.getByTenDangNhap(username);
        if (taiKhoan == null) {
            return false;
        }
        // So sánh mật khẩu đã mã hóa
        return BCrypt.checkpw(password, taiKhoan.getMatKhau());
    }
}
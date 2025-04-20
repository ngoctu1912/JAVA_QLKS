package GUI_DANGNHAP_DANGKY;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import BUS.TaiKhoanBUS;

public class DoiMatKhauFrame extends JFrame {
    private TaiKhoanBUS taiKhoanBUS;
    private int id; // maNV hoặc maKhachHang
    private String accountType; // "QUANLY" hoặc "KHACHHANG"
    private DoiMatKhauComponent components;
    private DoiMatKhauEvent eventHandler;

    // Constructor với 3 tham số (đã có)
    public DoiMatKhauFrame(TaiKhoanBUS taiKhoanBUS, int id, String accountType) {
        this.taiKhoanBUS = taiKhoanBUS;
        this.id = id;
        this.accountType = accountType;
        initFrame();
    }

    // Constructor mới với 4 tham số để tương thích với AuthEventHandler
    public DoiMatKhauFrame(TaiKhoanBUS taiKhoanBUS, Object unused, int id, String accountType) {
        this(taiKhoanBUS, id, accountType); // Gọi constructor với 3 tham số, bỏ qua tham số unused
    }

    private void initFrame() {
        setTitle("Đổi Mật Khẩu");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JPanel contentP = new JPanel();
        contentP.setBackground(Color.WHITE);
        contentP.setBounds(0, 0, 800, 500);
        contentP.setLayout(null);
        contentP.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(contentP);

        components = new DoiMatKhauComponent(contentP, taiKhoanBUS, id);
        eventHandler = new DoiMatKhauEvent(components, taiKhoanBUS, id, accountType);
        eventHandler.setupEvents();

        javax.swing.SwingUtilities.invokeLater(() -> contentP.requestFocusInWindow());
    }
}
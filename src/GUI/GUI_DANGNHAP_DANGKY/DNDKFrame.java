package GUI_DANGNHAP_DANGKY;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.function.Consumer;

import BUS.TaiKhoanBUS;

public class DNDKFrame extends JFrame {
    private final TaiKhoanBUS taiKhoanBUS;
    private DNDKComponent authComponents;
    private DNDKEvent eventHandler;
    private final Consumer<DNDKComponent.AccountInfo> loginCallback;

    public DNDKFrame(Consumer<DNDKComponent.AccountInfo> loginCallback) {
        this.taiKhoanBUS = new TaiKhoanBUS();
        this.loginCallback = loginCallback;
        initFrame();
    }

    private void initFrame() {
        setTitle("Đăng Nhập - Đăng Ký");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel contentP = new JPanel();
        contentP.setBackground(Color.WHITE);
        contentP.setBounds(0, 0, 1000, 600);
        contentP.setLayout(null);
        contentP.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(contentP);

        authComponents = new DNDKComponent(contentP, taiKhoanBUS);
        eventHandler = new DNDKEvent(authComponents, taiKhoanBUS, loginCallback);

        eventHandler.setupEvents();
    }
}
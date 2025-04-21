package GUI_TRANGCHU;

import GUI_MENU.MenuGUI;
import GUI_NHANVIEN.NhanVienGUI;
import GUI_DANGNHAP_DANGKY.DNDKComponent.AccountInfo;
import GUI_DATPHONG.FindRoom;
import GUI_DATPHONG.DatPhong.DatPhongFrame;
import GUI_DICHVU.FormDichVu;
import GUI_HOADON.HoaDon;
import GUI_KHACHHANG.KhachHangComponent;
import GUI_KHACHHANG.KhachHangFrame;
import GUI_KHACHHANG.KhachHangGUI;
import GUI_KIEMKETIENICH.KKTienIchComponent;
import GUI_KIEMKETIENICH.KKTienIchFrame;
import BUS.NhomQuyenBUS;
import BUS.ThongKeBUS;
import DAO.TaiKhoanDAO;
import DAO.TaiKhoanKHDAO;
import DTO.TaiKhoanDTO;
import DTO.TaiKhoanKHDTO;
import GUI_PHANQUYEN.PhanQuyenGUI;
import GUI_PHONG.FormPhong;
import GUI_TAIKHOAN.TaiKhoanGUI;
import GUI_THONGKE.TongQuanTKComponent;

import javax.swing.*;
import java.awt.*;

public class TrangChu extends JPanel {
    private MenuGUI menuPanel;
    private JPanel currentContentPanel;

    public TrangChu(AccountInfo accountInfo) {
        initComponents(accountInfo);
    }

    private void initComponents(AccountInfo accountInfo) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        menuPanel = new MenuGUI(accountInfo, this);
        add(menuPanel, BorderLayout.WEST);

        currentContentPanel = new MainContentFrame();
        add(currentContentPanel, BorderLayout.CENTER);
    }

    public void showPanel(String moduleName) {
        int maNhomQuyen = getMaNhomQuyen();
        String mcn = getMCNFromModule(moduleName);
        NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
        if (!mcn.equals("0") && !nhomQuyenBUS.checkPermission(maNhomQuyen, mcn, "view")) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này!");
            return;
        }

        remove(currentContentPanel);

        switch (moduleName) {
            case "TrangChuGUI":
                currentContentPanel = new MainContentFrame();
                break;
            case "PhongGUI":
                currentContentPanel = new FormPhong();
                break;
            case "DatPhongGUI":
                currentContentPanel = new JPanel(new BorderLayout());
                AccountInfo accountInfo = menuPanel.getMenuComponents().getAccountInfo();
                if (accountInfo != null && accountInfo.getAccountType().equals("KHACHHANG")) {
                    FindRoom findRoomPanel = new FindRoom();
                    currentContentPanel.add(findRoomPanel, BorderLayout.CENTER);
                } else if (accountInfo != null && accountInfo.getAccountType().equals("QUANLY")) {
                    try {
                        DatPhongFrame datPhongFrame = new DatPhongFrame();
                        currentContentPanel.add(datPhongFrame, BorderLayout.CENTER);
                        System.out.println("DatPhongFrame panel added successfully");
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi khi tải DatPhongFrame: " + e.getMessage(), "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    currentContentPanel.add(new JLabel("Không xác định được loại tài khoản", SwingConstants.CENTER));
                }
                break;
            case "KhachHangGUI":
                currentContentPanel = new JPanel(new BorderLayout());
                try {
                    KhachHangFrame khachHangFrame = new KhachHangFrame();
                    // Extract the KhachHangComponent from KhachHangFrame
                    KhachHangComponent khachHangComponent = khachHangFrame.getKhachHangComponent();
                    currentContentPanel.add(khachHangComponent, BorderLayout.CENTER);
                    // Dispose of the frame since we only need its component
                    khachHangFrame.dispose();
                    System.out.println("KhachHangFrame panel added successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi tải KhachHangFrame: " + e.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "DichVuGUI":
                currentContentPanel = new JPanel(new BorderLayout());
                try {
                    FormDichVu dichVuPanel = new FormDichVu();
                    currentContentPanel.add(dichVuPanel, BorderLayout.CENTER);
                    System.out.println("DichVuGUI panel added successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi tải DichVuGUI: " + e.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "NhanVienGUI":
                currentContentPanel = new JPanel(new BorderLayout());
                try {
                    NhanVienGUI nhanVienGUI = new NhanVienGUI();
                    // Lấy content pane của NhanVienGUI
                    Container contentPane = nhanVienGUI.getContentPane();
                    // Thêm content pane vào currentContentPanel
                    currentContentPanel.add(contentPane, BorderLayout.CENTER);
                    // Ẩn JFrame để tránh hiển thị cửa sổ riêng
                    nhanVienGUI.setVisible(false);
                    System.out.println("NhanVienGUI content pane added successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi tải NhanVienGUI: " + e.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "HoaDonGUI":
                currentContentPanel = new JPanel(new BorderLayout());
                HoaDon hoaDonPanel = new HoaDon();
                currentContentPanel.add(hoaDonPanel, BorderLayout.CENTER);
                break;
            case "KiemKeTienIchGUI":
                currentContentPanel = new JPanel(new BorderLayout());
                try {
                    JScrollPane mainScrollPane = new JScrollPane();
                    JPanel kiemKePanel = KKTienIchFrame.createKiemKePanel(mainScrollPane);
                    currentContentPanel.add(kiemKePanel, BorderLayout.CENTER);
                    System.out.println("KiemKeTienIchGUI panel added successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi tải KiemKeTienIchGUI: " + e.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "PhanQuyenGUI":
                currentContentPanel = new JPanel(new BorderLayout());
                currentContentPanel.add(new PhanQuyenGUI(), BorderLayout.CENTER);
                break;
            
            case "TaiKhoanGUI":
                currentContentPanel = new JPanel(new BorderLayout());
                try {
                    TaiKhoanGUI taiKhoanGUI = new TaiKhoanGUI();
                    currentContentPanel.add(taiKhoanGUI, BorderLayout.CENTER);
                    System.out.println("TaiKhoanGUI panel added successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi tải TaiKhoanGUI: " + e.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "ThongKeGUI":
                currentContentPanel = new JPanel(new BorderLayout());
                ThongKeBUS thongKeBUS = new ThongKeBUS();
                TongQuanTKComponent thongKePanel = new TongQuanTKComponent(thongKeBUS);
                currentContentPanel.add(thongKePanel, BorderLayout.CENTER);
                break;
            default:
                currentContentPanel = new JPanel();
                JLabel placeholder = new JLabel("Module " + moduleName + " chưa được triển khai",
                        SwingConstants.CENTER);
                placeholder.setFont(new Font("Arial", Font.PLAIN, 18));
                currentContentPanel.add(placeholder);
                break;
        }

        add(currentContentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private String getMCNFromModule(String moduleName) {
        return switch (moduleName) {
            case "TrangChuGUI" -> "0";
            case "PhongGUI" -> "1";
            case "DatPhongGUI" -> "2";
            case "KhachHangGUI" -> "3";
            case "DichVuGUI" -> "4";
            case "NhanVienGUI" -> "5";
            case "NhapDichVuGUI" -> "7";
            case "HoaDonGUI" -> "8";
            case "LeTanGUI" -> "9";
            case "KiemKeTienIchGUI" -> "10";
            case "PhanQuyenGUI" -> "11";
            case "TaiKhoanGUI" -> "12";
            case "KhuyenMaiGUI" -> "13";
            case "ThongKeGUI" -> "14";
            default -> "0";
        };
    }

    private int getMaNhomQuyen() {
        AccountInfo accountInfo = menuPanel.getMenuComponents().getAccountInfo();
        if (accountInfo == null) {
            return 0;
        }

        if (accountInfo.getAccountType().equals("QUANLY")) {
            TaiKhoanDTO tk = TaiKhoanDAO.getInstance().selectById(String.valueOf(accountInfo.getAccountId()));
            return tk != null ? tk.getMaNhomQuyen() : 0;
        } else if (accountInfo.getAccountType().equals("KHACHHANG")) {
            TaiKhoanKHDTO tkKH = TaiKhoanKHDAO.getInstance().selectById(String.valueOf(accountInfo.getAccountId()));
            return tkKH != null ? tkKH.getMaNhomQuyen() : 0;
        }
        return 0;
    }
}
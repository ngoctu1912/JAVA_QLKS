// package GUI_KHACHHANG;

// import BUS.KhachHangBUS;
// import DTO.KhachHangDTO;
// import javax.swing.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.List;

// public class KhachHangEvent {
//     private final KhachHangFrame frame;
//     private final KhachHangComponent component;

//     public KhachHangEvent(KhachHangFrame frame, KhachHangComponent component) {
//         this.frame = frame;
//         this.component = component;
//     }

//     public void setupEvents() {
//         // Sự kiện cho nút "Làm mới"
//         component.getRefreshButton().addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 component.loadAllCustomers();
//             }
//         });

//         // Sự kiện cho nút tìm kiếm
//         component.getSearchButton().addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 String searchText = component.getSearchField().getText().trim();
//                 if (!searchText.isEmpty()) {
//                     searchCustomers(searchText);
//                 } else {
//                     JOptionPane.showMessageDialog(frame, "Vui lòng nhập nội dung tìm kiếm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                 }
//             }
//         });
//     }

//     private void searchCustomers(String searchText) {
//         component.getTableModel().setRowCount(0);
//         KhachHangBUS khachHangBUS = new KhachHangBUS();
//         List<KhachHangDTO> customers = khachHangBUS.getAllCustomers();
//         int rowCount = 0;

//         for (KhachHangDTO customer : customers) {
//             // Tìm kiếm theo tên, số điện thoại, hoặc mã khách hàng (có thể mở rộng)
//             if (customer.getTKH().toLowerCase().contains(searchText.toLowerCase()) ||
//                 customer.getSDT().contains(searchText) ||
//                 String.valueOf(customer.getMKH()).contains(searchText)) {
//                 rowCount++;
//                 String gioiTinh = (customer.getGT() == 1) ? "Nam" : "Nữ";
//                 String trangThai = (customer.getTT() == 1) ? "Hoạt động" : "Ngưng hoạt động";
//                 component.getTableModel().addRow(new Object[] {
//                     rowCount,
//                     customer.getMKH(),
//                     customer.getTKH(),
//                     gioiTinh,
//                     customer.getCCCD(),
//                     customer.getDIACHI(),
//                     customer.getSDT(),
//                     customer.getEMAIL(),
//                     trangThai,
//                     customer.getNgayThamGia()
//                 });
//             }
//         }

//         if (rowCount == 0) {
//             JOptionPane.showMessageDialog(frame, "Không tìm thấy khách hàng nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//         }
//     }
// }

package GUI_KHACHHANG;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KhachHangEvent {
    private final KhachHangFrame frame;
    private final KhachHangComponent component;

    public KhachHangEvent(KhachHangFrame frame, KhachHangComponent component) {
        this.frame = frame;
        this.component = component;
    }

    public void setupEvents() {
        // Sự kiện cho nút "Làm mới"
        component.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                component.loadAllCustomers();
            }
        });

        // Sự kiện cho nút tìm kiếm
        component.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = component.getSearchField().getText().trim();
                if (!searchText.isEmpty()) {
                    searchCustomers(searchText);
                } else {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập nội dung tìm kiếm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void searchCustomers(String searchText) {
        component.getTableModel().setRowCount(0);
        KhachHangDAO khachHangDAO = KhachHangDAO.getInstance();
        List<KhachHangDTO> customers = khachHangDAO.selectAll();
        int rowCount = 0;

        for (KhachHangDTO customer : customers) {
            // Tìm kiếm theo tên, số điện thoại, hoặc mã khách hàng
            if (customer.getTenKhachHang().toLowerCase().contains(searchText.toLowerCase()) ||
                customer.getSoDienThoai().contains(searchText) ||
                String.valueOf(customer.getMaKhachHang()).contains(searchText)) {
                rowCount++;
                String gioiTinh = (customer.getGioiTinh() == 1) ? "Nam" : "Nữ";
                String trangThai = (customer.getTrangThai() == 1) ? "Hoạt động" : "Ngưng hoạt động";
                component.getTableModel().addRow(new Object[] {
                    rowCount,
                    String.valueOf(customer.getMaKhachHang()), // Convert int to String
                    customer.getTenKhachHang(),
                    gioiTinh,
                    customer.getCccd(),
                    customer.getDiaChi(),
                    customer.getSoDienThoai(),
                    customer.getEmail(),
                    trangThai,
                    customer.getNgaySinh()
                });
            }
        }

        if (rowCount == 0) {
            JOptionPane.showMessageDialog(frame, "Không tìm thấy khách hàng nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
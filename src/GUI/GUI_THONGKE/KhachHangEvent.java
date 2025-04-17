package GUI_THONGKE;

import BUS.ThongKeBUS;
import DTO.KhachHangThongKeDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;

public class KhachHangEvent {
    private KhachHangComponent component;
    private ThongKeBUS thongKeBUS;
    private DateTimeFormatter dateFormatter;

    public KhachHangEvent(KhachHangComponent component, ThongKeBUS thongKeBUS, DateTimeFormatter dateFormatter) {
        this.component = component;
        this.thongKeBUS = thongKeBUS;
        this.dateFormatter = dateFormatter;
    }

    public void loadData() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(30);
        KhachHangComponent.setSharedStartDate(startDate);
        KhachHangComponent.setSharedEndDate(now);
        component.getTxtStartDate().setText(startDate.format(dateFormatter));
        component.getTxtEndDate().setText(now.format(dateFormatter));
        component.setDanhSachKhachHang(thongKeBUS.getKhachHangTheoKhoangNgay(startDate, now));
        component.getLblTableTitle().setText("Thống kê khách hàng");
        updateTable();
    }

    public void loadDataByFilter() {
        try {
            LocalDate startDate = LocalDate.parse(component.getTxtStartDate().getText(), dateFormatter);
            LocalDate endDate = LocalDate.parse(component.getTxtEndDate().getText(), dateFormatter);
            if (startDate.isAfter(endDate)) {
                JOptionPane.showMessageDialog(component, "Ngày bắt đầu không được sau ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (startDate.isAfter(LocalDate.now()) || endDate.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(component, "Ngày không được trong tương lai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            KhachHangComponent.setSharedStartDate(startDate);
            KhachHangComponent.setSharedEndDate(endDate);
            component.setDanhSachKhachHang(thongKeBUS.getKhachHangTheoKhoangNgay(startDate, endDate));
            component.getLblTableTitle().setText("Thống kê khách hàng");
            updateTable();
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(component, "Vui lòng nhập ngày theo định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateTable() {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) component.getKhachHangTable().getModel();
            model.setRowCount(0);
            ArrayList<KhachHangThongKeDTO> danhSachKhachHang = component.getDanhSachKhachHang();
            if (danhSachKhachHang != null && !danhSachKhachHang.isEmpty()) {
                for (int i = 0; i < danhSachKhachHang.size(); i++) {
                    KhachHangThongKeDTO dto = danhSachKhachHang.get(i);
                    model.addRow(new Object[]{
                        i + 1,
                        dto.getMaKH(),
                        dto.getTenKH(),
                        dto.getsoLanDatPhong(),
                        dto.getTongTien()
                    });
                }
            }

            // Cập nhật chiều cao tableScrollPane
            int rowHeight = component.getKhachHangTable().getRowHeight();
            int headerHeight = component.getKhachHangTable().getTableHeader().getHeight();
            int totalHeight = (danhSachKhachHang != null ? danhSachKhachHang.size() : 0) * rowHeight + headerHeight + 5;
            component.getKhachHangTable().getParent().getParent().setPreferredSize(new Dimension(940, Math.min(totalHeight, 400)));

            component.getMainScrollPane().revalidate();
            component.getMainScrollPane().repaint();
        });
    }

    public void xuatExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Thống kê khách hàng");

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < component.getKhachHangTable().getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(component.getKhachHangTable().getColumnName(i));
        }

        for (int i = 0; i < component.getKhachHangTable().getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < component.getKhachHangTable().getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(String.valueOf(component.getKhachHangTable().getValueAt(i, j)));
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream("thongke_khachhang.xlsx")) {
            workbook.write(fileOut);
            showCustomMessage("Xuất file thành công!", "File Excel đã được tạo thành công.", true);
        } catch (IOException e) {
            e.printStackTrace();
            showCustomMessage("Lỗi xuất file!", "Đã có lỗi xảy ra khi xuất file Excel.", false);
        }
    }

    public void hienThiTop3KhachHangDatPhong() {
        ArrayList<KhachHangThongKeDTO> danhSachKhachHang = component.getDanhSachKhachHang();
        if (danhSachKhachHang == null || danhSachKhachHang.isEmpty()) {
            showCustomMessage("Không có dữ liệu!", "Danh sách khách hàng trống.", false);
            return;
        }

        // Sao chép danh sách và sắp xếp theo số lần đặt phòng giảm dần
        ArrayList<KhachHangThongKeDTO> sortedList = new ArrayList<>(danhSachKhachHang);
        sortedList.sort(Comparator.comparingInt(KhachHangThongKeDTO::getsoLanDatPhong).reversed());

        // Lấy tối đa 3 khách hàng
        int limit = Math.min(3, sortedList.size());
        ArrayList<KhachHangThongKeDTO> top3 = new ArrayList<>(sortedList.subList(0, limit));

        // Cập nhật tiêu đề bảng
        component.getLblTableTitle().setText("Khách hàng có tổng chi tiêu cao nhất");

        // Cập nhật bảng chính
        DefaultTableModel model = (DefaultTableModel) component.getKhachHangTable().getModel();
        model.setRowCount(0);
        for (int i = 0; i < top3.size(); i++) {
            KhachHangThongKeDTO dto = top3.get(i);
            model.addRow(new Object[]{
                i + 1,
                dto.getMaKH(),
                dto.getTenKH(),
                dto.getsoLanDatPhong(),
                dto.getTongTien()
            });
        }

        // Cập nhật chiều cao tableScrollPane
        int rowHeight = component.getKhachHangTable().getRowHeight();
        int headerHeight = component.getKhachHangTable().getTableHeader().getHeight();
        int totalHeight = top3.size() * rowHeight + headerHeight + 5;
        component.getKhachHangTable().getParent().getParent().setPreferredSize(new Dimension(940, Math.min(totalHeight, 400)));

        component.getMainScrollPane().revalidate();
        component.getMainScrollPane().repaint();
    }

    private void showCustomMessage(String title, String message, boolean isSuccess) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(component);

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(Color.WHITE);
        messagePanel.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        messagePanel.add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        okButton.setBackground(isSuccess ? Color.decode("#B7E4C7") : Color.RED);
        okButton.setForeground(Color.WHITE);
        okButton.setBorderPainted(false);
        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);

        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
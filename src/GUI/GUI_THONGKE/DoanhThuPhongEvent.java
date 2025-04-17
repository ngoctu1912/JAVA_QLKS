package GUI_THONGKE;

import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DoanhThuPhongEvent {
    private DoanhThuPhongComponent component;
    private ThongKeBUS thongKeBUS;

    public DoanhThuPhongEvent(DoanhThuPhongComponent component, ThongKeBUS thongKeBUS) {
        this.component = component;
        this.thongKeBUS = thongKeBUS;
    }

    public void switchTab(String tabName) {
        SwingUtilities.invokeLater(() -> {
            try {
                Container parent = component.getParent();
                while (!(parent instanceof DoanhThuComponent) && parent != null) {
                    parent = parent.getParent();
                }
                if (parent instanceof DoanhThuComponent) {
                    ((DoanhThuComponent) parent).switchTab(tabName);
                } else {
                    throw new IllegalStateException("Không tìm thấy DoanhThuComponent trong hệ thống phân cấp");
                }

                component.updateTabButtons(tabName);
                component.revalidate();
                component.repaint();
            } catch (Exception e) {
                System.err.println("Lỗi khi chuyển tab: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(component, "Không thể chuyển tab: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void loadData() {
        LocalDate startDate = DoanhThuPhongComponent.getSharedStartDate();
        LocalDate endDate = DoanhThuPhongComponent.getSharedEndDate();

        component.getCbStartDay().setSelectedItem(String.valueOf(startDate.getDayOfMonth()));
        component.getCbStartMonth().setSelectedItem(String.valueOf(startDate.getMonthValue()));
        component.getCbStartYear().setSelectedItem(String.valueOf(startDate.getYear()));
        component.getCbEndDay().setSelectedItem(String.valueOf(endDate.getDayOfMonth()));
        component.getCbEndMonth().setSelectedItem(String.valueOf(endDate.getMonthValue()));
        component.getCbEndYear().setSelectedItem(String.valueOf(endDate.getYear()));

        component.setDanhSachDoanhThu(thongKeBUS.getDoanhThuTheoLoaiPhong(startDate, endDate));
        component.getLblChartTitle().setText("Thống kê doanh thu theo loại phòng từ " + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " đến " + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        updateTableAndChart();
    }

    public void loadDataByFilter() {
        try {
            int startDay = Integer.parseInt((String) component.getCbStartDay().getSelectedItem());
            int startMonth = Integer.parseInt((String) component.getCbStartMonth().getSelectedItem());
            int startYear = Integer.parseInt((String) component.getCbStartYear().getSelectedItem());
            int endDay = Integer.parseInt((String) component.getCbEndDay().getSelectedItem());
            int endMonth = Integer.parseInt((String) component.getCbEndMonth().getSelectedItem());
            int endYear = Integer.parseInt((String) component.getCbEndYear().getSelectedItem());

            LocalDate startDate;
            LocalDate endDate;
            try {
                startDate = LocalDate.of(startYear, startMonth, startDay);
                endDate = LocalDate.of(endYear, endMonth, endDay);
            } catch (Exception e) {
                showCustomMessage("Lỗi", "Ngày không hợp lệ! Vui lòng kiểm tra lại ngày, tháng, năm.", false);
                return;
            }

            if (startDate.isAfter(endDate)) {
                showCustomMessage("Lỗi", "Ngày bắt đầu không được sau ngày kết thúc!", false);
                return;
            }
            if (startDate.isBefore(LocalDate.of(1900, 1, 1)) || endDate.isAfter(LocalDate.now())) {
                showCustomMessage("Lỗi", "Vui lòng chọn khoảng thời gian hợp lệ!", false);
                return;
            }

            DoanhThuPhongComponent.setSharedStartDate(startDate);
            DoanhThuPhongComponent.setSharedEndDate(endDate);
            component.setDanhSachDoanhThu(thongKeBUS.getDoanhThuTheoLoaiPhong(startDate, endDate));
            component.getLblChartTitle().setText("Thống kê doanh thu theo loại phòng từ " + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " đến " + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            updateTableAndChart();
        } catch (NumberFormatException e) {
            showCustomMessage("Lỗi", "Vui lòng chọn đầy đủ ngày, tháng, năm!", false);
        }
    }

    public void updateTableAndChart() {
        SwingUtilities.invokeLater(() -> {
            ArrayList<ThongKeDTO> typeData = new ArrayList<>();
            if (component.getDanhSachDoanhThu() != null && !component.getDanhSachDoanhThu().isEmpty()) {
                typeData.addAll(component.getDanhSachDoanhThu());
            }
            component.setTypeData(typeData);

            DefaultTableModel model = (DefaultTableModel) component.getDoanhThuTable().getModel();
            model.setRowCount(0);
            if (!typeData.isEmpty()) {
                model.setRowCount(typeData.size());
                for (int i = 0; i < typeData.size(); i++) {
                    ThongKeDTO dto = typeData.get(i);
                    model.setValueAt(dto.getLoaiPhong(), i, 0);
                    model.setValueAt(dto.getDoanhThuPhong(), i, 1);
                    model.setValueAt(dto.getDoanhThuDichVu(), i, 2);
                    model.setValueAt(dto.getSoLanDat(), i, 3);
                    model.setValueAt(dto.getTongDoanhThu(), i, 4);
                }
            } else {
                component.getLblChartTitle().setText("Không có dữ liệu cho khoảng thời gian này");
            }

            int rowHeight = component.getDoanhThuTable().getRowHeight();
            int headerHeight = component.getDoanhThuTable().getTableHeader().getHeight();
            int totalHeight = (typeData.size() * rowHeight) + headerHeight + 5;
            component.getDoanhThuTable().getParent().getParent().setPreferredSize(new Dimension(940, Math.min(totalHeight, 400)));

            component.getChartPanel().revalidate();
            component.getChartPanel().repaint();
            component.getMainScrollPane().revalidate();
            component.getMainScrollPane().repaint();
        });
    }

    public void drawHistogram(Graphics g) {
        ArrayList<ThongKeDTO> typeData = component.getTypeData();
        if (typeData == null || typeData.isEmpty()) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            g.drawString("Không có dữ liệu để hiển thị biểu đồ", 50, 100);
            return;
        }

        int width = component.getChartPanel().getWidth();
        int height = component.getChartPanel().getHeight();
        int margin = 80;
        int chartWidth = width - 2 * margin;
        int chartHeight = height - 2 * margin - 30;
        int numTypes = typeData.size();
        int barGroupWidth = chartWidth / numTypes;
        int barWidth = barGroupWidth / 4;

        long maxValue = typeData.stream()
                .mapToLong(dto -> Math.max(dto.getTongDoanhThu(), Math.max(dto.getDoanhThuPhong(), dto.getDoanhThuDichVu())))
                .max()
                .orElse(1);
        if (maxValue == 0) maxValue = 1;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(margin, margin, margin, height - margin - 30);
        g2d.drawLine(margin, height - margin - 30, width - margin, height - margin - 30);

        DecimalFormat df = new DecimalFormat("#,###");
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        int numTicks = 5;
        long step = maxValue / numTicks;
        if (step == 0) step = 1;
        for (int i = 0; i <= numTicks; i++) {
            long value = step * i;
            int y = height - margin - 30 - (int) ((value / (double) maxValue) * chartHeight);
            g2d.drawString(df.format(value), margin - 65, y + 5);
            g2d.drawLine(margin - 5, y, margin, y);
        }

        for (int i = 0; i < numTypes; i++) {
            String roomType = typeData.get(i).getLoaiPhong();
            int xBase = margin + i * barGroupWidth + barGroupWidth / 4;
            g2d.drawString(roomType, xBase + barWidth / 2, height - margin - 10);

            g2d.setColor(new Color(255, 165, 0));
            int doanhThuPhongHeight = (int) ((typeData.get(i).getDoanhThuPhong() / (double) maxValue) * chartHeight);
            g2d.fillRect(xBase, height - margin - 30 - doanhThuPhongHeight, barWidth, doanhThuPhongHeight);

            g2d.setColor(new Color(148, 0, 211));
            int doanhThuDichVuHeight = (int) ((typeData.get(i).getDoanhThuDichVu() / (double) maxValue) * chartHeight);
            g2d.fillRect(xBase + barWidth + 5, height - margin - 30 - doanhThuDichVuHeight, barWidth, doanhThuDichVuHeight);

            g2d.setColor(new Color(0, 191, 255));
            int tongDoanhThuHeight = (int) ((typeData.get(i).getTongDoanhThu() / (double) maxValue) * chartHeight);
            g2d.fillRect(xBase + 2 * (barWidth + 5), height - margin - 30 - tongDoanhThuHeight, barWidth, tongDoanhThuHeight);
        }

        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        int legendY = height - margin + 20;
        g2d.setColor(new Color(0, 191, 255));
        g2d.fillOval(margin, legendY - 10, 10, 10);
        g2d.drawString("Tổng doanh thu", margin + 15, legendY);

        g2d.setColor(new Color(255, 165, 0));
        g2d.fillOval(margin + 150, legendY - 10, 10, 10);
        g2d.drawString("Doanh thu phòng", margin + 165, legendY);

        g2d.setColor(new Color(148, 0, 211));
        g2d.fillOval(margin + 300, legendY - 10, 10, 10);
        g2d.drawString("Doanh thu dịch vụ", margin + 315, legendY);
    }

    public void xuatExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Thống kê doanh thu theo loại phòng");

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < component.getDoanhThuTable().getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(component.getDoanhThuTable().getColumnName(i));
        }

        for (int i = 0; i < component.getTypeData().size(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < component.getDoanhThuTable().getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(String.valueOf(component.getDoanhThuTable().getValueAt(i, j)));
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream("thongke_doanhthu_loaiphong_" + System.currentTimeMillis() + ".xlsx")) {
            workbook.write(fileOut);
            showCustomMessage("Xuất file thành công!", "File Excel đã được tạo thành công.", true);
        } catch (IOException e) {
            showCustomMessage("Lỗi xuất file!", "Không thể ghi file do: " + e.getMessage(), false);
        }
    }

    private void showCustomMessage(String title, String description, boolean isSuccess) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 150));

        ImageIcon icon = new ImageIcon(isSuccess ? "./src/icons/success.png" : "./src/icons/error.png");
        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        iconLabel.setBounds(130, 10, 40, 40);
        panel.add(iconLabel);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 60, 300, 20);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        descLabel.setForeground(Color.GRAY);
        descLabel.setBounds(0, 85, 300, 20);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(descLabel);

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        okButton.setForeground(Color.WHITE);
        okButton.setBackground(Color.decode("#B7E4C7"));
        okButton.setBounds(110, 115, 80, 30);
        okButton.addActionListener(e -> SwingUtilities.windowForComponent(okButton).dispose());
        panel.add(okButton);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        JDialog dialog = optionPane.createDialog(null, isSuccess ? "Thành công" : "Lỗi");
        dialog.setVisible(true);
    }
}
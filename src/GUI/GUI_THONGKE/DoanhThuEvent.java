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
import java.util.ArrayList;

public class DoanhThuEvent {
    private DoanhThuComponent component;
    private ThongKeBUS thongKeBUS;

    public DoanhThuEvent(DoanhThuComponent component, ThongKeBUS thongKeBUS) {
        this.component = component;
        this.thongKeBUS = thongKeBUS;
    }

    public void switchTab(String tabName) {
        SwingUtilities.invokeLater(() -> {
            try {
                Component currentPanel = null;
                switch (tabName) {
                    case "Thống kê từng tháng trong năm":
                        if (!hasPanel("Monthly")) {
                            DoanhThuTheoThangComponent roomPanel = new DoanhThuTheoThangComponent(thongKeBUS);
                            roomPanel.setName("Monthly");
                            component.getCardPanel().add(roomPanel, "Monthly");
                        }
                        component.getCardLayout().show(component.getCardPanel(), "Monthly");
                        currentPanel = getPanel("Monthly");
                        break;

                    case "Thống kê theo loại phòng":
                        if (!hasPanel("Room")) {
                            DoanhThuPhongComponent roomPanel = new DoanhThuPhongComponent(thongKeBUS);
                            roomPanel.setName("Room");
                            component.getCardPanel().add(roomPanel, "Room");
                        }
                        component.getCardLayout().show(component.getCardPanel(), "Room");
                        currentPanel = getPanel("Room");
                        break;

                    case "Thống kê từng ngày trong tháng":
                        if (!hasPanel("Daily")) {
                            DoanhThuTheoNgayComponent roomPanel = new DoanhThuTheoNgayComponent(thongKeBUS);
                            roomPanel.setName("Daily");
                            component.getCardPanel().add(roomPanel, "Daily");
                        }
                        component.getCardLayout().show(component.getCardPanel(), "Daily");
                        currentPanel = getPanel("Daily");
                        break;

                    case "Thống kê từ ngày đến ngày":
                        if (!hasPanel("Range")) {
                            DoanhThuTheoKhoangNgayComponent roomPanel = new DoanhThuTheoKhoangNgayComponent(thongKeBUS);
                            roomPanel.setName("Range");
                            component.getCardPanel().add(roomPanel, "Range");
                        }
                        component.getCardLayout().show(component.getCardPanel(), "Range");
                        currentPanel = getPanel("Range");
                        break;

                    default:
                        component.getCardLayout().show(component.getCardPanel(), "Yearly");
                        loadData();
                        currentPanel = component.getMainScrollPane();
                        break;
                }

                component.updateTabButtons(tabName);
                if (currentPanel instanceof DoanhThuComponent) {
                    ((DoanhThuComponent) currentPanel).updateTabButtons(tabName);
                }

                component.getCardPanel().revalidate();
                component.getCardPanel().repaint();
            } catch (Exception e) {
                System.err.println("Lỗi khi chuyển tab: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(component, "Không thể chuyển tab: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean hasPanel(String panelName) {
        for (Component comp : component.getCardPanel().getComponents()) {
            if (panelName.equals(comp.getName())) {
                return true;
            }
        }
        return false;
    }

    private Component getPanel(String panelName) {
        for (Component comp : component.getCardPanel().getComponents()) {
            if (panelName.equals(comp.getName())) {
                return comp;
            }
        }
        return null;
    }

    public void loadData() {
        int currentYear = LocalDate.now().getYear();
        component.getTxtStartYear().setText("2020");
        component.getTxtEndYear().setText(String.valueOf(currentYear));
        DoanhThuComponent.setSharedStartYear(2020);
        DoanhThuComponent.setSharedEndYear(currentYear);
        component.setDanhSachDoanhThu(thongKeBUS.getDoanhThuTheoNam(2020, currentYear));
        component.getLblChartTitle().setText("Thống kê doanh thu từ năm 2020 đến năm " + currentYear);
        updateTableAndChart();
    }

    public void loadDataByFilter() {
        try {
            int startYear = Integer.parseInt(component.getTxtStartYear().getText());
            int endYear = Integer.parseInt(component.getTxtEndYear().getText());
            if (startYear > endYear) {
                showCustomMessage("Lỗi", "Năm bắt đầu không được lớn hơn năm kết thúc!", false);
                return;
            }
            if (startYear < 1900 || endYear > LocalDate.now().getYear()) {
                showCustomMessage("Lỗi", "Năm phải từ 1900 đến năm hiện tại!", false);
                return;
            }
            DoanhThuComponent.setSharedStartYear(startYear);
            DoanhThuComponent.setSharedEndYear(endYear);
            component.setDanhSachDoanhThu(thongKeBUS.getDoanhThuTheoNam(startYear, endYear));
            component.getLblChartTitle().setText("Thống kê doanh thu từ năm " + startYear + " đến năm " + endYear);
            updateTableAndChart();
        } catch (NumberFormatException e) {
            showCustomMessage("Lỗi", "Vui lòng nhập năm hợp lệ!", false);
        }
    }

    public void updateTableAndChart() {
        SwingUtilities.invokeLater(() -> {
            ArrayList<ThongKeDTO> yearlyData = new ArrayList<>();
            if (component.getDanhSachDoanhThu() != null && !component.getDanhSachDoanhThu().isEmpty()) {
                int startYear = component.getDanhSachDoanhThu().get(0).getNgay().getYear();
                int endYear = component.getDanhSachDoanhThu().get(component.getDanhSachDoanhThu().size() - 1).getNgay().getYear();
                for (int year = startYear; year <= endYear; year++) {
                    long doanhThuPhong = 0, doanhThuDichVu = 0, tongDoanhThu = 0;
                    for (ThongKeDTO dto : component.getDanhSachDoanhThu()) {
                        if (dto.getNgay().getYear() == year) {
                            doanhThuPhong += dto.getDoanhThuPhong();
                            doanhThuDichVu += dto.getDoanhThuDichVu();
                            tongDoanhThu += dto.getTongDoanhThu();
                        }
                    }
                    yearlyData.add(new ThongKeDTO(LocalDate.of(year, 1, 1), doanhThuPhong, doanhThuDichVu, tongDoanhThu));
                }
            }
            component.setYearlyData(yearlyData);

            DefaultTableModel model = (DefaultTableModel) component.getDoanhThuTable().getModel();
            model.setRowCount(0);
            if (!yearlyData.isEmpty()) {
                model.setRowCount(yearlyData.size());
                for (int i = 0; i < yearlyData.size(); i++) {
                    ThongKeDTO dto = yearlyData.get(i);
                    model.setValueAt(dto.getNgay().getYear(), i, 0);
                    model.setValueAt(dto.getDoanhThuPhong(), i, 1);
                    model.setValueAt(dto.getDoanhThuDichVu(), i, 2);
                    model.setValueAt(dto.getTongDoanhThu(), i, 3);
                }
            }

            int rowHeight = component.getDoanhThuTable().getRowHeight();
            int headerHeight = component.getDoanhThuTable().getTableHeader().getHeight();
            int totalHeight = (yearlyData.size() * rowHeight) + headerHeight + 5;
            component.getDoanhThuTable().getParent().getParent().setPreferredSize(new Dimension(940, Math.min(totalHeight, 400)));

            component.getChartPanel().revalidate();
            component.getChartPanel().repaint();
            component.getMainScrollPane().revalidate();
            component.getMainScrollPane().repaint();
        });
    }

    public void drawHistogram(Graphics g) {
        ArrayList<ThongKeDTO> yearlyData = component.getYearlyData();
        if (yearlyData == null || yearlyData.isEmpty()) {
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
        int numYears = yearlyData.size();
        int barGroupWidth = chartWidth / numYears;
        int barWidth = barGroupWidth / 4;

        long maxValue = yearlyData.stream()
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

        for (int i = 0; i < numYears; i++) {
            int year = yearlyData.get(i).getNgay().getYear();
            int xBase = margin + i * barGroupWidth + barGroupWidth / 4;
            g2d.drawString("Năm " + year, xBase + barWidth / 2, height - margin - 10);

            g2d.setColor(new Color(255, 165, 0));
            int doanhThuPhongHeight = (int) ((yearlyData.get(i).getDoanhThuPhong() / (double) maxValue) * chartHeight);
            g2d.fillRect(xBase, height - margin - 30 - doanhThuPhongHeight, barWidth, doanhThuPhongHeight);

            g2d.setColor(new Color(148, 0, 211));
            int doanhThuDichVuHeight = (int) ((yearlyData.get(i).getDoanhThuDichVu() / (double) maxValue) * chartHeight);
            g2d.fillRect(xBase + barWidth + 5, height - margin - 30 - doanhThuDichVuHeight, barWidth, doanhThuDichVuHeight);

            g2d.setColor(new Color(0, 191, 255));
            int tongDoanhThuHeight = (int) ((yearlyData.get(i).getTongDoanhThu() / (double) maxValue) * chartHeight);
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
        Sheet sheet = workbook.createSheet("Thống kê doanh thu");

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < component.getDoanhThuTable().getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(component.getDoanhThuTable().getColumnName(i));
        }

        for (int i = 0; i < component.getYearlyData().size(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < component.getDoanhThuTable().getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(String.valueOf(component.getDoanhThuTable().getValueAt(i, j)));
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream("thongke_doanhthu_" + System.currentTimeMillis() + ".xlsx")) {
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
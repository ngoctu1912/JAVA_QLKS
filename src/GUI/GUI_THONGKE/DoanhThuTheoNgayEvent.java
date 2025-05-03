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
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DoanhThuTheoNgayEvent {
    private DoanhThuTheoNgayComponent component;
    private ThongKeBUS thongKeBUS;

    public DoanhThuTheoNgayEvent(DoanhThuTheoNgayComponent component, ThongKeBUS thongKeBUS) {
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
                }

                updateTabButtons(tabName);

                component.revalidate();
                component.repaint();
            } catch (Exception e) {
                System.err.println("Lỗi khi chuyển tab: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void updateTabButtons(String activeTab) {
        for (Component comp : component.getSubTabPanel().getComponents()) {
            if (comp instanceof JButton) {
                comp.setBackground(
                        ((JButton) comp).getText().equals(activeTab) ? Color.WHITE : Color.decode("#E6F4F1"));
            }
        }
        component.getSubTabPanel().revalidate();
        component.getSubTabPanel().repaint();
    }

    public void loadData() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        component.getTxtYear().setText(String.valueOf(currentYear));
        component.getTxtMonth().setText(String.valueOf(currentMonth));
        component.setDanhSachDoanhThu(thongKeBUS.getDoanhThuTheoThangNam(currentYear, currentMonth));
        component.getLblChartTitle()
                .setText("Thống kê doanh thu các ngày trong tháng " + currentMonth + "/" + currentYear);
        updateTableAndChart();
    }

    public void loadDataByFilter() {
        try {
            int year = Integer.parseInt(component.getTxtYear().getText());
            int month = Integer.parseInt(component.getTxtMonth().getText());
            if (year < 1900 || year > LocalDate.now().getYear()) {
                JOptionPane.showMessageDialog(component, "Vui lòng nhập năm hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (month < 1 || month > 12) {
                JOptionPane.showMessageDialog(component, "Vui lòng nhập tháng hợp lệ (1-12)!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            component.setDanhSachDoanhThu(thongKeBUS.getDoanhThuTheoThangNam(year, month));
            component.getLblChartTitle().setText("Thống kê doanh thu các ngày trong tháng " + month + "/" + year);
            updateTableAndChart();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(component, "Vui lòng nhập năm và tháng hợp lệ!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateTableAndChart() {
        SwingUtilities.invokeLater(() -> {
            ArrayList<ThongKeDTO> dailyData = new ArrayList<>();
            try {
                int year = Integer.parseInt(component.getTxtYear().getText());
                int month = Integer.parseInt(component.getTxtMonth().getText());
                YearMonth yearMonth = YearMonth.of(year, month);
                int daysInMonth = yearMonth.lengthOfMonth();

                boolean hasNonZeroData = false;
                for (int day = 1; day <= daysInMonth; day++) {
                    LocalDate currentDate = LocalDate.of(year, month, day);
                    long doanhThuPhong = 0, doanhThuDichVu = 0, tongDoanhThu = 0;
                    for (ThongKeDTO dto : component.getDanhSachDoanhThu()) {
                        if (dto.getNgay().equals(currentDate)) {
                            doanhThuPhong += dto.getDoanhThuPhong();
                            doanhThuDichVu += dto.getDoanhThuDichVu();
                            tongDoanhThu += dto.getTongDoanhThu();
                        }
                    }
                    if (doanhThuPhong > 0 || doanhThuDichVu > 0 || tongDoanhThu > 0) {
                        hasNonZeroData = true;
                    }
                    dailyData.add(new ThongKeDTO(currentDate, doanhThuPhong, doanhThuDichVu, tongDoanhThu));
                }

                component.setDailyData(dailyData);

                DefaultTableModel model = (DefaultTableModel) component.getDoanhThuTable().getModel();
                model.setRowCount(0);
                for (ThongKeDTO dto : dailyData) {
                    model.addRow(new Object[] {
                            dto.getNgay().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            dto.getDoanhThuPhong(),
                            dto.getDoanhThuDichVu(),
                            dto.getTongDoanhThu()
                    });
                }

                if (!hasNonZeroData) {
                    JOptionPane.showMessageDialog(component,
                            "Không có dữ liệu doanh thu trong tháng " + month + "/" + year + "!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                int rowHeight = component.getDoanhThuTable().getRowHeight();
                int headerHeight = component.getDoanhThuTable().getTableHeader().getPreferredSize().height;
                int maxHeight = rowHeight * 10 + headerHeight;
                int preferredHeight = rowHeight * model.getRowCount() + headerHeight;
                component.getTableScrollPane().setPreferredSize(new Dimension(
                        component.getTableScrollPane().getPreferredSize().width,
                        Math.min(preferredHeight, maxHeight)));
                component.revalidate();
                component.repaint();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(component,
                        "Vui lòng nhập năm và tháng hợp lệ!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void drawHistogram(Graphics g) {
        ArrayList<ThongKeDTO> dailyData = component.getDailyData();
        if (dailyData == null || dailyData.isEmpty()) {
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
        int numDays = dailyData.size();
        int barGroupWidth = chartWidth / Math.max(numDays, 1);
        int barWidth = barGroupWidth / 4;

        long maxValue = dailyData.stream()
                .mapToLong(dto -> Math.max(dto.getTongDoanhThu(), Math.max(dto.getDoanhThuPhong(), dto.getDoanhThuDichVu())))
                .max()
                .orElse(1);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(margin, margin, margin, height - margin - 30);
        g2d.drawLine(margin, height - margin - 30, width - margin, height - margin - 30);

        DecimalFormat df = new DecimalFormat("#,###");
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        int numTicks = 5;
        long step = (long) Math.ceil(maxValue / (double) numTicks);
        for (int i = 0; i <= numTicks; i++) {
            long value = step * i;
            int y = height - margin - 30 - (int) ((value / (double) maxValue) * chartHeight);
            g2d.drawString(df.format(value), margin - 65, y + 5);
            g2d.drawLine(margin - 5, y, margin, y);
        }

        for (int i = 0; i < numDays; i++) {
            int day = dailyData.get(i).getNgay().getDayOfMonth();
            int xBase = margin + i * barGroupWidth + barGroupWidth / 4;
            g2d.drawString("Ngày " + day, xBase + barWidth / 2 - 10, height - margin - 10);

            g2d.setColor(new Color(255, 165, 0));
            int doanhThuPhongHeight = (int) ((dailyData.get(i).getDoanhThuPhong() / (double) maxValue) * chartHeight);
            g2d.fillRect(xBase, height - margin - 30 - doanhThuPhongHeight, barWidth, doanhThuPhongHeight);

            g2d.setColor(new Color(148, 0, 211));
            int doanhThuDichVuHeight = (int) ((dailyData.get(i).getDoanhThuDichVu() / (double) maxValue) * chartHeight);
            g2d.fillRect(xBase + barWidth + 5, height - margin - 30 - doanhThuDichVuHeight, barWidth, doanhThuDichVuHeight);

            g2d.setColor(new Color(0, 191, 255));
            int tongDoanhThuHeight = (int) ((dailyData.get(i).getTongDoanhThu() / (double) maxValue) * chartHeight);
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
        Sheet sheet = workbook.createSheet("Thống kê doanh thu theo ngày");

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < component.getDoanhThuTable().getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(component.getDoanhThuTable().getColumnName(i));
        }

        for (int i = 0; i < component.getDailyData().size(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < component.getDoanhThuTable().getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(String.valueOf(component.getDoanhThuTable().getValueAt(i, j)));
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream("thongke_doanhthu_ngay.xlsx")) {
            workbook.write(fileOut);
            showCustomMessage("Xuất file thành công!", "File Excel đã được tạo thành công.", true);
        } catch (IOException e) {
            e.printStackTrace();
            showCustomMessage("Lỗi xuất file!", "Đã có lỗi xảy ra khi xuất file Excel.", false);
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

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
                new Object[] {});
        JDialog dialog = optionPane.createDialog(null, isSuccess ? "Thành công" : "Lỗi");
        dialog.setVisible(true);
    }
}
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

public class DoanhThuTheoThangEvent {
    private DoanhThuTheoThangComponent component;
    private ThongKeBUS thongKeBUS;

    public DoanhThuTheoThangEvent(DoanhThuTheoThangComponent component, ThongKeBUS thongKeBUS) {
        this.component = component;
        this.thongKeBUS = thongKeBUS;
    }

    public void switchTab(String tabName) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Tìm DoanhThuGUI trong hệ thống phân cấp
                Container parent = component.getParent();
                while (!(parent instanceof DoanhThuComponent) && parent != null) {
                    parent = parent.getParent();
                }
                if (parent instanceof DoanhThuComponent) {
                    ((DoanhThuComponent) parent).switchTab(tabName);
                }

                // Cập nhật trạng thái nút trong subTabPanel
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
                comp.setBackground(((JButton) comp).getText().equals(activeTab) ? Color.WHITE : Color.decode("#E6F4F1"));
            }
        }
        component.getSubTabPanel().revalidate();
        component.getSubTabPanel().repaint();
    }

    public void loadData() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        component.getTxtYear().setText(String.valueOf(currentYear));
        component.setDanhSachDoanhThu(thongKeBUS.getDoanhThuTheoNam(currentYear, currentYear));
        component.getLblChartTitle().setText("Thống kê doanh thu các tháng trong năm " + currentYear);
        updateTableAndChart();
    }

    public void loadDataByFilter() {
        try {
            int year = Integer.parseInt(component.getTxtYear().getText());
            if (year < 1900 || year > LocalDate.now().getYear()) {
                JOptionPane.showMessageDialog(component, "Vui lòng nhập năm hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            component.setDanhSachDoanhThu(thongKeBUS.getDoanhThuTheoNam(year, year));
            component.getLblChartTitle().setText("Thống kê doanh thu các tháng trong năm " + year);
            updateTableAndChart();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(component, "Vui lòng nhập năm hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateTableAndChart() {
        SwingUtilities.invokeLater(() -> {
            ArrayList<ThongKeDTO> monthlyData = new ArrayList<>();
            if (!component.getDanhSachDoanhThu().isEmpty()) {
                int year = Integer.parseInt(component.getTxtYear().getText());
                for (int month = 1; month <= 12; month++) {
                    long doanhThuPhong = 0, doanhThuDichVu = 0, tongDoanhThu = 0;
                    for (ThongKeDTO dto : component.getDanhSachDoanhThu()) {
                        if (dto.getNgay().getMonthValue() == month && dto.getNgay().getYear() == year) {
                            doanhThuPhong += dto.getDoanhThuPhong();
                            doanhThuDichVu += dto.getDoanhThuDichVu();
                            tongDoanhThu += dto.getTongDoanhThu();
                        }
                    }
                    monthlyData.add(new ThongKeDTO(LocalDate.of(year, month, 1), doanhThuPhong, doanhThuDichVu, tongDoanhThu));
                }
            }
            component.setMonthlyData(monthlyData);

            DefaultTableModel model = (DefaultTableModel) component.getDoanhThuTable().getModel();
            model.setRowCount(0);
            model.setRowCount(monthlyData.size());

            for (int i = 0; i < monthlyData.size(); i++) {
                ThongKeDTO dto = monthlyData.get(i);
                model.setValueAt("Tháng " + dto.getNgay().getMonthValue(), i, 0);
                model.setValueAt(dto.getDoanhThuPhong(), i, 1);
                model.setValueAt(dto.getDoanhThuDichVu(), i, 2);
                model.setValueAt(dto.getTongDoanhThu(), i, 3);
            }

            // Cập nhật chiều cao tableScrollPane
            int rowHeight = component.getDoanhThuTable().getRowHeight();
            int headerHeight = component.getDoanhThuTable().getTableHeader().getHeight();
            int totalHeight = (monthlyData.size() * rowHeight) + headerHeight + 5;
            component.getDoanhThuTable().getParent().getParent().setPreferredSize(new Dimension(940, Math.min(totalHeight, 400)));

            component.getChartPanel().revalidate();
            component.getChartPanel().repaint();
            component.getMainScrollPane().revalidate();
            component.getMainScrollPane().repaint();
        });
    }

    public void drawHistogram(Graphics g) {
        ArrayList<ThongKeDTO> monthlyData = component.getMonthlyData();
        if (monthlyData == null || monthlyData.isEmpty()) {
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
        int numMonths = monthlyData.size();
        int barGroupWidth = chartWidth / numMonths;
        int barWidth = barGroupWidth / 4;

        long maxValue = monthlyData.stream()
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
        long step = maxValue / numTicks;
        for (int i = 0; i <= numTicks; i++) {
            long value = step * i;
            int y = height - margin - 30 - (int) ((value / (double) maxValue) * chartHeight);
            g2d.drawString(df.format(value), margin - 65, y + 5);
            g2d.drawLine(margin - 5, y, margin, y);
        }

        for (int i = 0; i < numMonths; i++) {
            int month = monthlyData.get(i).getNgay().getMonthValue();
            int xBase = margin + i * barGroupWidth + barGroupWidth / 4;
            g2d.drawString("Tháng " + month, xBase + barWidth / 2 - 10, height - margin - 10);

            g2d.setColor(new Color(255, 165, 0));
            int doanhThuPhongHeight = (int) ((monthlyData.get(i).getDoanhThuPhong() / (double) maxValue) * chartHeight);
            g2d.fillRect(xBase, height - margin - 30 - doanhThuPhongHeight, barWidth, doanhThuPhongHeight);

            g2d.setColor(new Color(148, 0, 211));
            int doanhThuDichVuHeight = (int) ((monthlyData.get(i).getDoanhThuDichVu() / (double) maxValue) * chartHeight);
            g2d.fillRect(xBase + barWidth + 5, height - margin - 30 - doanhThuDichVuHeight, barWidth, doanhThuDichVuHeight);

            g2d.setColor(new Color(0, 191, 255));
            int tongDoanhThuHeight = (int) ((monthlyData.get(i).getTongDoanhThu() / (double) maxValue) * chartHeight);
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
        Sheet sheet = workbook.createSheet("Thống kê doanh thu theo tháng");

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < component.getDoanhThuTable().getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(component.getDoanhThuTable().getColumnName(i));
        }

        for (int i = 0; i < component.getMonthlyData().size(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < component.getDoanhThuTable().getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(String.valueOf(component.getDoanhThuTable().getValueAt(i, j)));
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream("thongke_doanhthu_thang.xlsx")) {
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

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        JDialog dialog = optionPane.createDialog(null, isSuccess ? "Thành công" : "Lỗi");
        dialog.setVisible(true);
    }
}
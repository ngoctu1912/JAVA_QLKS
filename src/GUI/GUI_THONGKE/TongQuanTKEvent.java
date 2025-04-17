package GUI_THONGKE;

import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TongQuanTKEvent {
    private TongQuanTKComponent component;
    private ThongKeBUS thongKeBUS;

    public TongQuanTKEvent(TongQuanTKComponent component, ThongKeBUS thongKeBUS) {
        this.component = component;
        this.thongKeBUS = thongKeBUS;
    }

    // Phương thức tùy chỉnh thanh cuộn
    private void customizeScrollBar(JScrollPane scrollPane) {
        if (scrollPane == null) {
            System.out.println("Error: scrollPane is null in customizeScrollBar");
            return;
        }
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(209, 207, 207); // Màu của thanh cuộn
                this.trackColor = new Color(245, 245, 245); // Màu của nền
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(thumbColor);
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                // Không vẽ track hoặc để lại mặc định nhẹ nhàng
            }
        });
        verticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));
        System.out.println("Custom scroll bar applied to mainScrollPane");
    }

    public void switchTab(String tabName) {
        SwingUtilities.invokeLater(() -> {
            try {
                component.getCardLayout().show(component.getContentPanel(), tabName);
                component.updateNavButtons(tabName);
                if (component.getMainScrollPane() == null) {
                    System.out.println("Error: mainScrollPane is null in switchTab");
                    return;
                }
                // Đặt AS_NEEDED để tương tự DoanhThuEvent
                component.getMainScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                // Đảm bảo nội dung đủ lớn để kích hoạt thanh cuộn
                component.getContentPanel().setPreferredSize(new Dimension(960, 1000));
                if (tabName.equals("Doanh thu")) {
                    Component currentTab = null;
                    for (Component comp : component.getContentPanel().getComponents()) {
                        if (comp.isVisible()) {
                            currentTab = comp;
                            break;
                        }
                    }
                    if (currentTab instanceof DoanhThuComponent) {
                        ((DoanhThuComponent) currentTab).switchTab("Thống kê theo năm");
                    }
                } else if (tabName.equals("Khách hàng")) {
                    component.getMainScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    Component currentTab = null;
                    for (Component comp : component.getContentPanel().getComponents()) {
                        if (comp.isVisible()) {
                            currentTab = comp;
                            break;
                        }
                    }
                    if (currentTab instanceof KhachHangComponent) {
                        ((KhachHangComponent) currentTab).getEventHandler().loadData();
                    }
                } else {
                    component.getMainScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                }
                // Áp dụng tùy chỉnh thanh cuộn
                customizeScrollBar(component.getMainScrollPane());
                component.getContentPanel().revalidate();
                component.getContentPanel().repaint();
                component.getMainScrollPane().revalidate();
                component.getMainScrollPane().repaint();
                System.out.println("Switched to tab: " + tabName + ", ScrollBarPolicy: " + component.getMainScrollPane().getVerticalScrollBarPolicy());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(component, "Lỗi khi chuyển tab: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void loadDataAsync() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    component.setDanhSachDoanhThu(thongKeBUS.getDoanhThu8NgayGanNhat());
                    component.getLblChartTitle().setText("Thống kê doanh thu 8 ngày gần nhất");
                } catch (Exception e) {
                    throw new RuntimeException("Lỗi tải dữ liệu: " + e.getMessage(), e);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    updateTableAndChart();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    public void updateTableAndChart() {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) component.getDoanhThuTable().getModel();
            model.setRowCount(0);

            if (component.getDanhSachDoanhThu() != null && !component.getDanhSachDoanhThu().isEmpty()) {
                model.setRowCount(component.getDanhSachDoanhThu().size());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                for (int i = 0; i < component.getDanhSachDoanhThu().size(); i++) {
                    ThongKeDTO dto = component.getDanhSachDoanhThu().get(i);
                    model.setValueAt(dto.getNgay().format(formatter), i, 0);
                    model.setValueAt(dto.getDoanhThuPhong(), i, 1);
                    model.setValueAt(dto.getDoanhThuDichVu(), i, 2);
                    model.setValueAt(dto.getTongDoanhThu(), i, 3);
                }

                int rowHeight = component.getDoanhThuTable().getRowHeight();
                int headerHeight = component.getDoanhThuTable().getTableHeader().getHeight();
                int totalHeight = (component.getDanhSachDoanhThu().size() * rowHeight) + headerHeight + 5;
                component.getDoanhThuTable().getParent().getParent().setPreferredSize(new Dimension(960, Math.min(totalHeight, 1000)));
            }

            component.getChartPanel().revalidate();
            component.getChartPanel().repaint();
            component.getMainScrollPane().revalidate();
            component.getMainScrollPane().repaint();
        });
    }

    public void drawLineChart(Graphics g) {
        ArrayList<ThongKeDTO> danhSachDoanhThu = component.getDanhSachDoanhThu();
        if (danhSachDoanhThu == null || danhSachDoanhThu.isEmpty()) {
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
        int numPoints = danhSachDoanhThu.size();
        int xStep = numPoints > 1 ? chartWidth / (numPoints - 1) : chartWidth;

        long maxDoanhThu = danhSachDoanhThu.stream()
                .mapToLong(dto -> Math.max(dto.getTongDoanhThu(), Math.max(dto.getDoanhThuPhong(), dto.getDoanhThuDichVu())))
                .max()
                .orElse(1);
        if (maxDoanhThu == 0) maxDoanhThu = 1;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(margin, margin, margin, height - margin - 30);
        g2d.drawLine(margin, height - margin - 30, width - margin, height - margin - 30);

        DecimalFormat df = new DecimalFormat("#,###");
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        int numTicks = 5;
        long step = maxDoanhThu / numTicks;
        if (step == 0) step = 1;
        for (int i = 0; i <= numTicks; i++) {
            long value = step * i;
            int y = height - margin - 30 - (int) ((value / (double) maxDoanhThu) * chartHeight);
            g2d.drawString(df.format(value), margin - 65, y + 5);
            g2d.drawLine(margin - 5, y, margin, y);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < numPoints; i++) {
            String date = danhSachDoanhThu.get(i).getNgay().format(formatter);
            g2d.drawString(date, margin + i * xStep - 30, height - margin - 10);
        }

        g2d.setStroke(new BasicStroke(2));

        int[] xPoints = new int[numPoints];
        for (int i = 0; i < numPoints; i++) {
            xPoints[i] = margin + i * xStep;
        }

        int[] yPointsPhong = new int[numPoints];
        for (int i = 0; i < numPoints; i++) {
            yPointsPhong[i] = height - margin - 30 - (int) ((danhSachDoanhThu.get(i).getDoanhThuPhong() / (double) maxDoanhThu) * chartHeight);
        }
        g2d.setColor(new Color(255, 165, 0));
        g2d.drawPolyline(xPoints, yPointsPhong, numPoints);

        int[] yPointsDichVu = new int[numPoints];
        for (int i = 0; i < numPoints; i++) {
            yPointsDichVu[i] = height - margin - 30 - (int) ((danhSachDoanhThu.get(i).getDoanhThuDichVu() / (double) maxDoanhThu) * chartHeight);
        }
        g2d.setColor(new Color(148, 0, 211));
        g2d.drawPolyline(xPoints, yPointsDichVu, numPoints);

        int[] yPointsTong = new int[numPoints];
        for (int i = 0; i < numPoints; i++) {
            yPointsTong[i] = height - margin - 30 - (int) ((danhSachDoanhThu.get(i).getTongDoanhThu() / (double) maxDoanhThu) * chartHeight);
        }
        g2d.setColor(new Color(0, 191, 255));
        g2d.drawPolyline(xPoints, yPointsTong, numPoints);

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
}
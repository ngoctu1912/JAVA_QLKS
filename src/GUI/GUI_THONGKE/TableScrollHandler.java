package GUI_THONGKE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class TableScrollHandler {

    /**
     * Gắn sự kiện cuộn chuột cho bảng, đảm bảo bảng cuộn được khi con trỏ chuột nằm trên bảng hoặc JScrollPane chứa bảng.
     *
     * @param table        JTable cần xử lý cuộn
     * @param tableScrollPane JScrollPane chứa JTable
     * @param components   Các thành phần khác trong giao diện cần gắn sự kiện cuộn
     */
    public static void attachScrollHandler(JTable table, JScrollPane tableScrollPane, Component... components) {
        MouseWheelListener scrollListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Chuyển đổi tọa độ con trỏ chuột sang tọa độ của tableScrollPane và table
                Point pScrollPane = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), tableScrollPane);
                Point pTable = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), table);

                // Kiểm tra xem con trỏ chuột có nằm trên tableScrollPane hoặc table không
                if (tableScrollPane.getBounds().contains(pScrollPane) || table.getBounds().contains(pTable)) {
                    // Lấy thanh cuộn dọc của tableScrollPane
                    JScrollBar verticalScrollBar = tableScrollPane.getVerticalScrollBar();
                    if (verticalScrollBar != null && verticalScrollBar.isVisible()) {
                        // Tính toán lượng cuộn dựa trên sự kiện cuộn chuột
                        int scrollAmount = e.getWheelRotation() * verticalScrollBar.getUnitIncrement();
                        int newValue = verticalScrollBar.getValue() + scrollAmount;

                        // Đảm bảo giá trị cuộn nằm trong giới hạn
                        newValue = Math.max(verticalScrollBar.getMinimum(), Math.min(newValue, verticalScrollBar.getMaximum() - verticalScrollBar.getVisibleAmount()));
                        verticalScrollBar.setValue(newValue);
                    }
                }
                e.consume();
            }
        };

        // Gắn sự kiện cuộn cho bảng và JScrollPane
        table.addMouseWheelListener(scrollListener);
        tableScrollPane.addMouseWheelListener(scrollListener);

        // Gắn sự kiện cuộn cho các thành phần khác trong giao diện (nếu có)
        for (Component component : components) {
            component.addMouseWheelListener(scrollListener);
        }
    }
}
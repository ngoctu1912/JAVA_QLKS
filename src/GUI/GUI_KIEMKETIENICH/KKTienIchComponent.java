package GUI_KIEMKETIENICH;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class KKTienIchComponent extends JPanel {
    private JTable tienIchTable, chiTietTable;
    private JScrollPane tienIchScrollPane, chiTietScrollPane;
    public JButton btnThem, btnCapNhat, btnXoa, btnLamMoi, btnChiTiet, btnQuayLai, btnTimKiemPhong, btnLamMoiChiTiet;
    public JTextField txtMaPhieu, txtMaPhong;
    private JScrollPane mainScrollPane;
    private JPanel contentPanel;
    RoundedPanel filterPanel;
    private JLabel lblTableTitle;

    public KKTienIchComponent(JScrollPane mainScrollPane) {
        this.mainScrollPane = mainScrollPane;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 245));
        setPreferredSize(new Dimension(960, 600));
        initComponents();
    }

    private void initComponents() {
        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(240, 245, 245));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setDoubleBuffered(true);

        // Panel Bộ Lọc
        filterPanel = new RoundedPanel(20);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));
        filterPanel.setPreferredSize(new Dimension(960, 60));
        filterPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 60));
        filterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thêm JLabel "Mã tiện ích"
        JLabel lblMaPhieu = new JLabel("Mã tiện ích");
        lblMaPhieu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        filterPanel.add(lblMaPhieu);

        // Thêm JTextField để nhập mã tiện ích
        txtMaPhieu = new JTextField("");
        txtMaPhieu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtMaPhieu.setPreferredSize(new Dimension(200, 30));
        txtMaPhieu.setBorder(BorderFactory.createLineBorder(Color.decode("#A3CFFA"), 1, true));
        txtMaPhieu.setBackground(Color.WHITE);
        filterPanel.add(txtMaPhieu);

        // Thêm JLabel "Mã phòng" (ẩn mặc định)
        JLabel lblMaPhong = new JLabel("Mã phòng");
        lblMaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblMaPhong.setVisible(false);
        filterPanel.add(lblMaPhong);

        // Thêm JTextField để nhập mã phòng
        txtMaPhong = new JTextField("");
        txtMaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtMaPhong.setPreferredSize(new Dimension(200, 30));
        txtMaPhong.setBorder(BorderFactory.createLineBorder(Color.decode("#A3CFFA"), 1, true));
        txtMaPhong.setBackground(Color.WHITE);
        txtMaPhong.setVisible(false);
        filterPanel.add(txtMaPhong);

        // Thêm khoảng cách để đẩy các nút sang bên phải
        filterPanel.add(Box.createHorizontalGlue());

        // Các nút
        btnThem = createRoundedButton("Thêm");
        btnThem.setPreferredSize(new Dimension(80, 30));
        filterPanel.add(btnThem);

        btnCapNhat = createRoundedButton("Cập nhật");
        btnCapNhat.setPreferredSize(new Dimension(80, 30));
        filterPanel.add(btnCapNhat);

        btnXoa = createRoundedButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(80, 30));
        filterPanel.add(btnXoa);

        btnChiTiet = createRoundedButton("Chi tiết");
        btnChiTiet.setPreferredSize(new Dimension(80, 30));
        filterPanel.add(btnChiTiet);

        btnQuayLai = createRoundedButton("Quay lại");
        btnQuayLai.setPreferredSize(new Dimension(80, 30));
        btnQuayLai.setBackground(Color.RED);
        btnQuayLai.setVisible(false);
        filterPanel.add(btnQuayLai);

        btnLamMoi = createRoundedButton("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(80, 30));
        btnLamMoi.setBackground(Color.RED);
        filterPanel.add(btnLamMoi);

        btnTimKiemPhong = createRoundedButton("Tìm kiếm");
        btnTimKiemPhong.setPreferredSize(new Dimension(80, 30));
        btnTimKiemPhong.setVisible(false);
        filterPanel.add(btnTimKiemPhong);

        btnLamMoiChiTiet = createRoundedButton("Làm mới");
        btnLamMoiChiTiet.setPreferredSize(new Dimension(80, 30));
        btnLamMoiChiTiet.setBackground(Color.RED);
        btnLamMoiChiTiet.setVisible(false);
        filterPanel.add(btnLamMoiChiTiet);

        contentPanel.add(filterPanel);
        contentPanel.add(Box.createVerticalStrut(10));

        // Tiêu đề bảng
        lblTableTitle = new JLabel("Tiện ích", SwingConstants.CENTER);
        lblTableTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblTableTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblTableTitle);
        contentPanel.add(Box.createVerticalStrut(10));

        // Bảng tiện ích
        String[] columns = {"Mã tiện ích", "Tên tiện ích", "Số lượng tổng", "Số lượng còn lại", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tienIchTable = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(new Color(183, 228, 199));
                c.setForeground(Color.BLACK);
                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }

            @Override
            public JTableHeader getTableHeader() {
                JTableHeader header = super.getTableHeader();
                header.setPreferredSize(new Dimension(0, 40));
                return header;
            }
        };

        tienIchTable.setRowHeight(40);
        tienIchTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tienIchTable.setShowVerticalLines(false);
        tienIchTable.setShowHorizontalLines(false);
        tienIchTable.setIntercellSpacing(new Dimension(0, 0));
        tienIchTable.setBorder(BorderFactory.createEmptyBorder());

        // Header style
        JTableHeader header = tienIchTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setOpaque(false);

        UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());

        // Column widths
        tienIchTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        tienIchTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        tienIchTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        tienIchTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        tienIchTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        // ScrollPane for tienIchTable
        tienIchScrollPane = new JScrollPane(tienIchTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tienIchScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tienIchScrollPane.getViewport().setBackground(new Color(240, 245, 245));
        tienIchScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tienIchScrollPane.getVerticalScrollBar().setBlockIncrement(50);
        customizeScrollBar(tienIchScrollPane.getVerticalScrollBar());

        // Bảng chi tiết tiện ích
        String[] chiTietColumns = {"Mã phòng", "Tên phòng", "Wifi", "TV", "Máy lạnh", "Tủ lạnh", "Bình nóng lạnh"};
        DefaultTableModel chiTietModel = new DefaultTableModel(chiTietColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        chiTietTable = new JTable(chiTietModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(new Color(183, 228, 199));
                c.setForeground(Color.BLACK);
                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }

            @Override
            public JTableHeader getTableHeader() {
                JTableHeader header = super.getTableHeader();
                header.setPreferredSize(new Dimension(0, 40));
                return header;
            }
        };

        chiTietTable.setRowHeight(40);
        chiTietTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        chiTietTable.setShowVerticalLines(false);
        chiTietTable.setShowHorizontalLines(false);
        chiTietTable.setIntercellSpacing(new Dimension(0, 0));
        chiTietTable.setBorder(BorderFactory.createEmptyBorder());

        // Header style for chiTietTable
        JTableHeader chiTietHeader = chiTietTable.getTableHeader();
        chiTietHeader.setFont(new Font("SansSerif", Font.BOLD, 15));
        chiTietHeader.setBackground(Color.WHITE);
        chiTietHeader.setForeground(Color.BLACK);
        chiTietHeader.setReorderingAllowed(false);
        chiTietHeader.setBorder(BorderFactory.createEmptyBorder());
        chiTietHeader.setOpaque(false);

        // Column widths for chiTietTable
        chiTietTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        chiTietTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        chiTietTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        chiTietTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        chiTietTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        chiTietTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        chiTietTable.getColumnModel().getColumn(6).setPreferredWidth(100);

        // ScrollPane for chiTietTable
        chiTietScrollPane = new JScrollPane(chiTietTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chiTietScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        chiTietScrollPane.getViewport().setBackground(new Color(240, 245, 245));
        chiTietScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        chiTietScrollPane.getVerticalScrollBar().setBlockIncrement(50);
        customizeScrollBar(chiTietScrollPane.getVerticalScrollBar());
        chiTietScrollPane.setVisible(false);

        // Thêm MouseWheelListener cho cả hai ScrollPane
        tienIchScrollPane.addMouseWheelListener(e -> {
            if (mainScrollPane != null) {
                if (e.getWheelRotation() < 0) {
                    mainScrollPane.getVerticalScrollBar().setValue(
                        mainScrollPane.getVerticalScrollBar().getValue() - e.getScrollAmount() * 10
                    );
                } else {
                    mainScrollPane.getVerticalScrollBar().setValue(
                        mainScrollPane.getVerticalScrollBar().getValue() + e.getScrollAmount() * 10
                    );
                }
            }
        });

        chiTietScrollPane.addMouseWheelListener(e -> {
            if (mainScrollPane != null) {
                if (e.getWheelRotation() < 0) {
                    mainScrollPane.getVerticalScrollBar().setValue(
                        mainScrollPane.getVerticalScrollBar().getValue() - e.getScrollAmount() * 10
                    );
                } else {
                    mainScrollPane.getVerticalScrollBar().setValue(
                        mainScrollPane.getVerticalScrollBar().getValue() + e.getScrollAmount() * 10
                    );
                }
            }
        });

        contentPanel.add(tienIchScrollPane);
        contentPanel.add(chiTietScrollPane);
        contentPanel.add(Box.createVerticalStrut(10));

        add(contentPanel, BorderLayout.CENTER);

        // Thêm listener để điều chỉnh kích thước khi cửa sổ thay đổi
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                adjustTableSize();
            }
        });

        revalidate();
        repaint();
        adjustTableSize();
    }

    private void adjustTableSize() {
        int width = getWidth();
        tienIchScrollPane.setPreferredSize(new Dimension(width - 20, Math.max(100, getHeight() - 150)));
        chiTietScrollPane.setPreferredSize(new Dimension(width - 20, Math.max(100, getHeight() - 150)));
        tienIchScrollPane.revalidate();
        chiTietScrollPane.revalidate();
        tienIchScrollPane.repaint();
        chiTietScrollPane.repaint();
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(text.equals("Làm mới") || text.equals("Quay lại") ? Color.RED : Color.decode("#B7E4C7"));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();

                int width = b.getWidth();
                int height = b.getHeight();
                int arc = 20;

                if (model.isPressed()) {
                    g2.setColor(text.equals("Làm mới") || text.equals("Quay lại") ? Color.decode("#cc0000") : Color.decode("#2eb82e"));
                } else if (model.isRollover()) {
                    g2.setColor(text.equals("Làm mới") || text.equals("Quay lại") ? Color.decode("#ff3333") : Color.decode("#3cdc3c"));
                } else {
                    g2.setColor(text.equals("Làm mới") || text.equals("Quay lại") ? Color.RED : Color.decode("#B7E4C7"));
                }

                g2.fillRoundRect(0, 0, width, height, arc, arc);

                g2.setColor(Color.WHITE);
                g2.setFont(b.getFont());
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(b.getText());
                int textHeight = fm.getHeight();
                int x = (width - textWidth) / 2;
                int y = (height - textHeight) / 2 + fm.getAscent();
                g2.drawString(b.getText(), x, y);

                g2.dispose();
            }
        });
        return button;
    }

    private void customizeScrollBar(JScrollBar scrollBar) {
        scrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(209, 207, 207);
                this.trackColor = new Color(245, 245, 245);
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
        scrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));
    }

    class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.dispose();
        }
    }

    public JTable getTienIchTable() {
        return tienIchTable;
    }

    public JTable getChiTietTable() {
        return chiTietTable;
    }

    public JScrollPane getTienIchScrollPane() {
        return tienIchScrollPane;
    }

    public JScrollPane getChiTietScrollPane() {
        return chiTietScrollPane;
    }

    public JLabel getLblTableTitle() {
        return lblTableTitle;
    }

    public JTextField getTxtMaPhieu() {
        return txtMaPhieu;
    }

    public JTextField getTxtMaPhong() {
        return txtMaPhong;
    }

    public JButton getBtnLamMoiChiTiet() {
        return btnLamMoiChiTiet;
    }
}
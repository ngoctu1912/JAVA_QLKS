package GUI_DICHVU;

import DTO.DichVuDTO;
import BUS.DichVuBUS;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FormDichVu extends JFrame {
    private JTable table;
    private DichVuBUS dichVuBUS;
    private JTextField txtSearch;
    private JComboBox<String> cbLoaiDichVu;
    private ArrayList<DichVuDTO> filteredDichVus;
    private ArrayList<DichVuDTO> searchedDichVus;
    private DefaultTableCellRenderer centerRenderer;
    private DocumentListener searchListener;
    private java.awt.event.ActionListener loaiDichVuListener;

    public FormDichVu() {
        setTitle("Quản lý dịch vụ");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 245));

        dichVuBUS = new DichVuBUS();
        filteredDichVus = dichVuBUS.getAllDichVu();
        searchedDichVus = new ArrayList<>();

        if (filteredDichVus == null) {
            System.out.println("Error: dichVuBUS.getAllDichVu() returned null in constructor.");
            filteredDichVus = new ArrayList<>();
        } else if (filteredDichVus.isEmpty()) {
            System.out.println("Warning: No services loaded from the database in constructor.");
        } else {
            System.out.println("Successfully loaded " + filteredDichVus.size() + " services from the database in constructor.");
            for (DichVuDTO dv : filteredDichVus) {
                System.out.println("Service: " + dv.getMaDV() + ", " + dv.getTenDV() + ", " + dv.getLoaiDV() + ", " + dv.getGiaDV());
            }
        }

        // Thanh menu trên cùng
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(240, 245, 245));
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Panel for left side (Refresh button, Info button, and search field)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(240, 245, 245));
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        // Panel for "Làm mới" with icon
        JPanel refreshPanel = new JPanel();
        refreshPanel.setBackground(new Color(240, 245, 245));
        refreshPanel.setLayout(new BoxLayout(refreshPanel, BoxLayout.Y_AXIS));

        JButton btnRefresh = new JButton();
        try {
            ImageIcon icon = new ImageIcon("./src/icons/reload.png");
            Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            btnRefresh.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnRefresh.setBackground(new Color(240, 245, 245));
        btnRefresh.setBorder(BorderFactory.createEmptyBorder());
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(e -> loadTableData());
        btnRefresh.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshPanel.add(btnRefresh);

        JLabel lblLamMoi = new JLabel("LÀM MỚI");
        lblLamMoi.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblLamMoi.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshPanel.add(lblLamMoi);

        leftPanel.add(refreshPanel);

        // Panel for "CHI TIẾT" with icon
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(240, 245, 245));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JButton btnInfo = new JButton();
        try {
            ImageIcon icon = new ImageIcon("./src/icons/file-circle-info.png");
            Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            btnInfo.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnInfo.setBackground(new Color(240, 245, 245));
        btnInfo.setBorder(BorderFactory.createEmptyBorder());
        btnInfo.setFocusPainted(false);
        btnInfo.setEnabled(false);
        btnInfo.addActionListener(e -> openDetailDialog());
        btnInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(btnInfo);

        JLabel lblChiTiet = new JLabel("CHI TIẾT");
        lblChiTiet.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblChiTiet.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(lblChiTiet);

        leftPanel.add(infoPanel);

        txtSearch = new JTextField(30);
        txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtSearch.setText("Nhập nội dung tìm kiếm...");
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 207, 207)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtSearch.getText().equals("Nhập nội dung tìm kiếm...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Nhập nội dung tìm kiếm...");
                    txtSearch.setForeground(Color.GRAY);
                }
            }
        });
        searchListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
                applyFilters();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
                applyFilters();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
                applyFilters();
            }
        };
        txtSearch.getDocument().addDocumentListener(searchListener);
        txtSearch.addActionListener(e -> {
            performSearch();
            applyFilters();
        });
        leftPanel.add(txtSearch);

        menuPanel.add(leftPanel, BorderLayout.WEST);

        // Panel for right side (Filter components)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(240, 245, 245));
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton btnFilter = new JButton();
        try {
            ImageIcon icon = new ImageIcon("./src/icons/filter.png");
            Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            btnFilter.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnFilter.setBackground(new Color(240, 245, 245));
        btnFilter.setBorder(BorderFactory.createEmptyBorder());
        btnFilter.setFocusPainted(false);
        rightPanel.add(btnFilter);

        JLabel lblLoaiDichVu = new JLabel("Loại Dịch Vụ: ");
        lblLoaiDichVu.setFont(new Font("SansSerif", Font.BOLD, 16)); // Bolder to match FormPhong
        rightPanel.add(lblLoaiDichVu);

        cbLoaiDichVu = new JComboBox<>();
        cbLoaiDichVu.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cbLoaiDichVu.setPreferredSize(new Dimension(150, 30)); // Match FormPhong
        cbLoaiDichVu.setToolTipText("Chọn loại dịch vụ để lọc");
        cbLoaiDichVu.setUI(new ModernComboBoxUI());
        cbLoaiDichVu.setRenderer(new CustomComboBoxRenderer());
        loaiDichVuListener = e -> applyFilters();
        cbLoaiDichVu.addActionListener(loaiDichVuListener);
        rightPanel.add(cbLoaiDichVu);

        menuPanel.add(rightPanel, BorderLayout.EAST);

        add(menuPanel, BorderLayout.NORTH);

        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 245, 245));

        // Panel nội dung chính
        JPanel contentP = new JPanel(new BorderLayout());
        contentP.setBackground(new Color(240, 245, 245));
        contentP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"STT", "Mã DV", "Tên DV", "Loại DV", "Số Lượng", "Giá DV"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
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

        table.setRowHeight(40);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBorder(BorderFactory.createEmptyBorder());

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 17));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createEmptyBorder());
        UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());
        header.setOpaque(false);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(240, 245, 245));

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new BasicScrollBarUI() {
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
            }
        });
        verticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));

        contentP.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(contentP, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        loadTableData();

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    table.setRowSelectionInterval(row, row);
                    btnInfo.setEnabled(true);
                }
            }
        });

        new DichVuEventHandler(this);
    }

    private void loadLoaiDichVu() {
        Set<String> loaiDichVuSet = new HashSet<>();
        for (DichVuDTO dv : searchedDichVus) {
            if (dv != null && dv.getLoaiDV() != null) {
                loaiDichVuSet.add(dv.getLoaiDV());
            }
        }
        cbLoaiDichVu.removeAllItems();
        cbLoaiDichVu.addItem("Tất cả");
        for (String loai : loaiDichVuSet) {
            cbLoaiDichVu.addItem(loai);
        }
        cbLoaiDichVu.setSelectedItem("Tất cả");
    }

    private void performSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.equals("Nhập nội dung tìm kiếm...")) {
            keyword = "";
        }

        ArrayList<DichVuDTO> allDichVus = dichVuBUS.getAllDichVu();
        if (allDichVus == null) {
            System.out.println("Error: dichVuBUS.getAllDichVu() returned null in performSearch.");
            allDichVus = new ArrayList<>();
        }

        searchedDichVus.clear();
        for (DichVuDTO dv : allDichVus) {
            if (dv == null) continue;

            boolean matchesKeyword = keyword.isEmpty() ||
                (dv.getMaDV() != null && dv.getMaDV().toLowerCase().contains(keyword)) ||
                (dv.getTenDV() != null && dv.getTenDV().toLowerCase().contains(keyword)) ||
                (dv.getLoaiDV() != null && dv.getLoaiDV().toLowerCase().contains(keyword)) ||
                String.valueOf(dv.getSoLuong()).contains(keyword) ||
                String.valueOf(dv.getGiaDV()).contains(keyword);

            if (matchesKeyword) {
                searchedDichVus.add(dv);
            }
        }

        System.out.println("Searched services (Keyword: " + keyword + "): " + searchedDichVus.size());
    }

    private void applyFilters() {
        String selectedLoaiDichVu = cbLoaiDichVu.getSelectedItem() != null ? cbLoaiDichVu.getSelectedItem().toString() : "Tất cả";

        filteredDichVus = new ArrayList<>();
        for (DichVuDTO dv : searchedDichVus) {
            if (dv == null) continue;

            boolean matchesLoaiDichVu = selectedLoaiDichVu.equals("Tất cả") || (dv.getLoaiDV() != null && dv.getLoaiDV().equals(selectedLoaiDichVu));

            if (matchesLoaiDichVu) {
                filteredDichVus.add(dv);
            }
        }

        System.out.println("Filtered services (Loại Dịch Vụ: " + selectedLoaiDichVu + "): " + filteredDichVus.size());
        updateTable();
    }

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (filteredDichVus == null) {
            System.out.println("Error: filteredDichVus is null in updateTable.");
            return;
        }

        int stt = 1;
        for (DichVuDTO dv : filteredDichVus) {
            if (dv != null) {
                model.addRow(new Object[]{
                    stt++,
                    dv.getMaDV(),
                    dv.getTenDV(),
                    dv.getLoaiDV(),
                    dv.getSoLuong(),
                    dv.getGiaDV() + "đ"
                });
            }
        }
        System.out.println("Table updated with " + (stt - 1) + " rows.");
    }

    private void loadTableData() {
        ArrayList<DichVuDTO> allDichVus = dichVuBUS.getAllDichVu();
        if (allDichVus == null) {
            System.out.println("Error: dichVuBUS.getAllDichVu() returned null in loadTableData.");
            allDichVus = new ArrayList<>();
        } else if (allDichVus.isEmpty()) {
            System.out.println("Warning: No services loaded from the database in loadTableData.");
        } else {
            System.out.println("Successfully loaded " + allDichVus.size() + " services from the database in loadTableData.");
            for (DichVuDTO dv : allDichVus) {
                System.out.println("Service: " + dv.getMaDV() + ", " + dv.getTenDV() + ", " + dv.getLoaiDV() + ", " + dv.getGiaDV());
            }
        }

        searchedDichVus.clear();
        searchedDichVus.addAll(allDichVus);
        filteredDichVus.clear();
        filteredDichVus.addAll(allDichVus);

        txtSearch.getDocument().removeDocumentListener(searchListener);
        txtSearch.setText("Nhập nội dung tìm kiếm...");
        txtSearch.setForeground(Color.GRAY);
        txtSearch.getDocument().addDocumentListener(searchListener);

        cbLoaiDichVu.removeActionListener(loaiDichVuListener);
        updateTable();
        loadLoaiDichVu();
        cbLoaiDichVu.addActionListener(loaiDichVuListener);
    }

    private void openDetailDialog() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String maDV = (String) table.getValueAt(row, 1);
            DichVuDTO dichVu = dichVuBUS.getDichVuById(maDV);
            if (dichVu != null) {
                new DichVuDetailDialog(this, dichVu).setVisible(true);
            }
        }
    }

    public JTable getTable() { return table; }

    // Custom ComboBox UI
    private static class ModernComboBoxUI extends BasicComboBoxUI {
        private Border defaultBorder = BorderFactory.createLineBorder(new Color(200, 200, 200), 1);
        private Border hoverBorder = BorderFactory.createLineBorder(new Color(100, 150, 255), 1);

        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton() {
                @Override
                public void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(100, 100, 100));
                    int[] xPoints = {getWidth() / 2 - 4, getWidth() / 2 + 4, getWidth() / 2};
                    int[] yPoints = {getHeight() / 2 - 2, getHeight() / 2 - 2, getHeight() / 2 + 4};
                    g2.fillPolygon(xPoints, yPoints, 3);
                    g2.dispose();
                }
            };
            button.setPreferredSize(new Dimension(20, 20));
            button.setBackground(new Color(245, 245, 245));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setFocusPainted(false);
            return button;
        }

        @Override
        protected void installComponents() {
            super.installComponents();
            comboBox.setBackground(new Color(245, 245, 245));
            comboBox.setBorder(defaultBorder);
            comboBox.setOpaque(false);

            // Hover effect
            comboBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    comboBox.setBorder(hoverBorder);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    comboBox.setBorder(defaultBorder);
                }
            });
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(255, 255, 255),
                0, c.getHeight(), new Color(245, 245, 245)
            );
            g2.setPaint(gradient);
            g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 10, 10);
            g2.dispose();
            super.paint(g, c);
        }
    }

    // Custom ComboBox Renderer
    private static class CustomComboBoxRenderer extends JLabel implements ListCellRenderer<String> {
        public CustomComboBoxRenderer() {
            setOpaque(true);
            setFont(new Font("SansSerif", Font.PLAIN, 16));
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText(value);

            // Icon based on service type
            if ("Ăn uống".equals(value)) {
                setIcon(new Icon() {
                    @Override
                    public void paintIcon(Component c, Graphics g, int x, int y) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setColor(Color.BLUE);
                        g2.fillRect(x + 5, y + 2, 8, 2); // Fork handle
                        g2.fillRect(x + 7, y, 4, 2); // Fork prongs
                        g2.dispose();
                    }
                    @Override
                    public int getIconWidth() { return 15; }
                    @Override
                    public int getIconHeight() { return 10; }
                });
            } else if ("Giặt là".equals(value)) {
                setIcon(new Icon() {
                    @Override
                    public void paintIcon(Component c, Graphics g, int x, int y) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setColor(Color.GREEN);
                        g2.fillOval(x + 5, y, 8, 8); // Washer drum
                        g2.setColor(Color.WHITE);
                        g2.fillOval(x + 7, y + 2, 4, 4); // Inner circle
                        g2.dispose();
                    }
                    @Override
                    public int getIconWidth() { return 15; }
                    @Override
                    public int getIconHeight() { return 10; }
                });
            } else {
                setIcon(new Icon() {
                    @Override
                    public void paintIcon(Component c, Graphics g, int x, int y) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setColor(new Color(100, 100, 100));
                        g2.fillOval(x + 5, y, 5, 5); // Circle
                        g2.dispose();
                    }
                    @Override
                    public int getIconWidth() { return 10; }
                    @Override
                    public int getIconHeight() { return 10; }
                });
            }

            if (isSelected) {
                setBackground(new Color(100, 150, 255));
                setForeground(Color.WHITE);
                setFont(new Font("SansSerif", Font.BOLD, 16));
            } else {
                setBackground(index >= 0 && list.getSelectionBackground().equals(getBackground()) ?
                    new Color(220, 230, 255) : new Color(245, 245, 245));
                setForeground(Color.BLACK);
                setFont(new Font("SansSerif", Font.PLAIN, 16));
            }

            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormDichVu().setVisible(true));
    }
}
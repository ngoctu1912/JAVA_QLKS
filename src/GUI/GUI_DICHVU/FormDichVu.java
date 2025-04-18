package GUI_DICHVU;

import DTO.DichVuDTO;
import BUS.DichVuBUS;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FormDichVu extends JFrame {
    private JTable table;
    private DichVuBUS dichVuBUS;
    private JTextField txtSearch;
    private JComboBox<String> cbLoaiDichVu;
    private JComboBox<String> cbGiaDichVu;
    private ArrayList<DichVuDTO> filteredDichVus;
    private ArrayList<DichVuDTO> searchedDichVus; // List to store search results
    private DefaultTableCellRenderer centerRenderer;
    private DocumentListener searchListener; // To control search listener
    private java.awt.event.ActionListener loaiDichVuListener; // To control LoaiDichVu listener
    private java.awt.event.ActionListener giaDichVuListener; // To control GiaDichVu listener

    public FormDichVu() {
        setTitle("Quản lý dịch vụ");
        setSize(1000, 600); // Match FormPhong's adjusted size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Match FormPhong
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 245)); // Match FormPhong's background

        dichVuBUS = new DichVuBUS();
        filteredDichVus = dichVuBUS.getAllDichVu();
        searchedDichVus = new ArrayList<>(); // Initialize searchedDichVus

        // Debug: Check initial data load
        if (filteredDichVus == null) {
            System.out.println("Error: dichVuBUS.getAllDichVu() returned null in constructor. Check DichVuBUS implementation.");
            filteredDichVus = new ArrayList<>();
        } else if (filteredDichVus.isEmpty()) {
            System.out.println("Warning: No services loaded from the database in constructor. Check your database connection or data.");
        } else {
            System.out.println("Successfully loaded " + filteredDichVus.size() + " services from the database in constructor.");
            for (DichVuDTO dv : filteredDichVus) {
                System.out.println("Service: " + dv.getMaDV() + ", " + dv.getTenDV() + ", " + dv.getLoaiDV() + ", " + dv.getGiaDV());
            }
        }

        // Thanh menu trên cùng
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(240, 245, 245)); // Match FormPhong
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Match FormPhong

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
        lblLamMoi.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Match FormPhong
        lblLamMoi.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshPanel.add(lblLamMoi);

        leftPanel.add(refreshPanel); // Add "Làm mới" button first

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

        leftPanel.add(infoPanel); // Add "CHI TIẾT" button after "Làm mới"

        txtSearch = new JTextField(30); // Match FormPhong
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
        // Define the search listener but don't add it yet
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
        // Add ActionListener for Enter key press
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
            ImageIcon icon = new ImageIcon("./src/icons/filter-list.png");
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
        lblLoaiDichVu.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rightPanel.add(lblLoaiDichVu);

        cbLoaiDichVu = new JComboBox<>();
        cbLoaiDichVu.setFont(new Font("SansSerif", Font.PLAIN, 16));
        // Define the listener but don't add it yet
        loaiDichVuListener = e -> {
            updateGiaDichVu();
            applyFilters();
        };
        rightPanel.add(cbLoaiDichVu);

        JLabel lblGiaDichVu = new JLabel("Giá Dịch Vụ: ");
        lblGiaDichVu.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rightPanel.add(lblGiaDichVu);

        cbGiaDichVu = new JComboBox<>();
        cbGiaDichVu.setFont(new Font("SansSerif", Font.PLAIN, 16));
        // Define the listener but don't add it yet
        giaDichVuListener = e -> applyFilters();
        rightPanel.add(cbGiaDichVu);

        menuPanel.add(rightPanel, BorderLayout.EAST);

        add(menuPanel, BorderLayout.NORTH);

        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 245, 245)); // Match FormPhong

        // Panel nội dung chính
        JPanel contentP = new JPanel(new BorderLayout());
        contentP.setBackground(new Color(240, 245, 245));
        contentP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Khởi tạo renderer căn giữa
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Table model
        DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"STT", "Mã DV", "Tên DV", "Loại DV", "Số Lượng", "Giá DV"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // JTable
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(new Color(183, 228, 199)); // Match FormPhong
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

        // Header style
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 17));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createEmptyBorder());
        UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());
        header.setOpaque(false);

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(240, 245, 245));

        // Custom scroll bar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(209, 207, 207); // Match FormPhong
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
                // Minimal track
            }
        });
        verticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));

        contentP.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(contentP, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Load initial table data
        loadTableData();

        // Sự kiện nhấp chuột để chọn hàng
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
        for (DichVuDTO dv : searchedDichVus) { // Use searchedDichVus
            if (dv != null && dv.getLoaiDV() != null) {
                loaiDichVuSet.add(dv.getLoaiDV());
            }
        }
        cbLoaiDichVu.removeAllItems();
        cbLoaiDichVu.addItem("Tất cả");
        for (String loai : loaiDichVuSet) {
            cbLoaiDichVu.addItem(loai);
        }
        cbLoaiDichVu.setSelectedItem("Tất cả"); // Ensure default selection
    }

    private void updateGiaDichVu() {
        cbGiaDichVu.removeAllItems();
        String selectedLoaiDichVu = cbLoaiDichVu.getSelectedItem() != null ? cbLoaiDichVu.getSelectedItem().toString() : "Tất cả";
        ArrayList<DichVuDTO> dichVus = new ArrayList<>();
        if (selectedLoaiDichVu.equals("Tất cả")) {
            dichVus.addAll(searchedDichVus);
        } else {
            for (DichVuDTO dv : searchedDichVus) {
                if (dv != null && dv.getLoaiDV() != null && dv.getLoaiDV().equals(selectedLoaiDichVu)) {
                    dichVus.add(dv);
                }
            }
        }
        System.out.println("Services for price filtering (Loại Dịch Vụ: " + selectedLoaiDichVu + "): " + dichVus.size());
        if (!dichVus.isEmpty()) {
            cbGiaDichVu.addItem("Tất cả");
            int minPrice = Integer.MAX_VALUE;
            int maxPrice = Integer.MIN_VALUE;
            for (DichVuDTO dv : dichVus) {
                if (dv != null) {
                    if (dv.getGiaDV() < minPrice) minPrice = dv.getGiaDV();
                    if (dv.getGiaDV() > maxPrice) maxPrice = dv.getGiaDV();
                }
            }
            int range = (maxPrice - minPrice) / 3;
            if (range > 0) {
                cbGiaDichVu.addItem("< " + (minPrice + range));
                cbGiaDichVu.addItem((minPrice + range) + " - " + (minPrice + 2 * range));
                cbGiaDichVu.addItem("> " + (minPrice + 2 * range));
            } else {
                cbGiaDichVu.addItem("= " + minPrice);
            }
        }
        cbGiaDichVu.setSelectedItem("Tất cả"); // Ensure default selection
    }

    private void performSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.equals("Nhập nội dung tìm kiếm...")) {
            keyword = "";
        }

        ArrayList<DichVuDTO> allDichVus = dichVuBUS.getAllDichVu();
        if (allDichVus == null) {
            System.out.println("Error: dichVuBUS.getAllDichVu() returned null in performSearch. Check DichVuBUS implementation.");
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
        String selectedGiaDichVu = cbGiaDichVu.getSelectedItem() != null ? cbGiaDichVu.getSelectedItem().toString() : "Tất cả";

        filteredDichVus = new ArrayList<>();
        for (DichVuDTO dv : searchedDichVus) {
            if (dv == null) continue;

            // Service type filter
            boolean matchesLoaiDichVu = selectedLoaiDichVu.equals("Tất cả") || (dv.getLoaiDV() != null && dv.getLoaiDV().equals(selectedLoaiDichVu));

            // Price filter
            boolean matchesGiaDichVu = true;
            if (!selectedGiaDichVu.equals("Tất cả")) {
                if (selectedGiaDichVu.startsWith("< ")) {
                    int price = Integer.parseInt(selectedGiaDichVu.replace("< ", ""));
                    matchesGiaDichVu = dv.getGiaDV() < price;
                } else if (selectedGiaDichVu.startsWith("> ")) {
                    int price = Integer.parseInt(selectedGiaDichVu.replace("> ", ""));
                    matchesGiaDichVu = dv.getGiaDV() > price;
                } else if (selectedGiaDichVu.startsWith("= ")) {
                    int price = Integer.parseInt(selectedGiaDichVu.replace("= ", ""));
                    matchesGiaDichVu = dv.getGiaDV() == price;
                } else {
                    String[] range = selectedGiaDichVu.split(" - ");
                    int minPrice = Integer.parseInt(range[0]);
                    int maxPrice = Integer.parseInt(range[1]);
                    matchesGiaDichVu = dv.getGiaDV() >= minPrice && dv.getGiaDV() <= maxPrice;
                }
            }

            if (matchesLoaiDichVu && matchesGiaDichVu) {
                filteredDichVus.add(dv);
            }
        }

        System.out.println("Filtered services (Loại Dịch Vụ: " + selectedLoaiDichVu + ", Giá Dịch Vụ: " + selectedGiaDichVu + "): " + filteredDichVus.size());
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
        // Load all services
        ArrayList<DichVuDTO> allDichVus = dichVuBUS.getAllDichVu();
        if (allDichVus == null) {
            System.out.println("Error: dichVuBUS.getAllDichVu() returned null in loadTableData. Check DichVuBUS implementation.");
            allDichVus = new ArrayList<>();
        } else if (allDichVus.isEmpty()) {
            System.out.println("Warning: No services loaded from the database in loadTableData. Check your database connection or data.");
        } else {
            System.out.println("Successfully loaded " + allDichVus.size() + " services from the database in loadTableData.");
            for (DichVuDTO dv : allDichVus) {
                System.out.println("Service: " + dv.getMaDV() + ", " + dv.getTenDV() + ", " + dv.getLoaiDV() + ", " + dv.getGiaDV());
            }
        }

        // Set both searchedDichVus and filteredDichVus to all services
        searchedDichVus.clear();
        searchedDichVus.addAll(allDichVus);
        filteredDichVus.clear();
        filteredDichVus.addAll(allDichVus);

        // Reset search field
        txtSearch.getDocument().removeDocumentListener(searchListener);
        txtSearch.setText("Nhập nội dung tìm kiếm...");
        txtSearch.setForeground(Color.GRAY);
        txtSearch.getDocument().addDocumentListener(searchListener);

        // Reset dropdowns without triggering listeners
        cbLoaiDichVu.removeActionListener(loaiDichVuListener);
        cbGiaDichVu.removeActionListener(giaDichVuListener);

        // Update table with all data
        updateTable();

        // Populate and reset dropdowns
        loadLoaiDichVu();
        updateGiaDichVu();

        // Re-add listeners
        cbLoaiDichVu.addActionListener(loaiDichVuListener);
        cbGiaDichVu.addActionListener(giaDichVuListener);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormDichVu().setVisible(true));
    }
}
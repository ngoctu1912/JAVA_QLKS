// package GUI_PHONG;

// import DTO.PhongDTO;
// import BUS.PhongBUS;

// import javax.swing.*;
// import javax.swing.plaf.basic.BasicScrollBarUI;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.table.JTableHeader;
// import javax.swing.table.DefaultTableCellRenderer;
// import java.awt.*;
// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.Set;

// public class FormPhong extends JFrame {
//     private JTable table;
//     private PhongBUS phongBUS;
//     private JTextField txtSearch;
//     private JComboBox<String> cbLoaiPhong;
//     private JComboBox<String> cbGiaPhong;
//     private ArrayList<PhongDTO> filteredRooms;
//     private DefaultTableCellRenderer centerRenderer;

//     public FormPhong() {
//         setTitle("Quản lý phòng");
//         setSize(1000, 600);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setLayout(new BorderLayout());
//         setBackground(new Color(240, 245, 245));

//         phongBUS = new PhongBUS();
//         filteredRooms = phongBUS.getAllPhong();

//         // Debug: Check initial data load
//         if (filteredRooms == null) {
//             System.out.println("Error: phongBUS.getAllPhong() returned null in constructor. Check PhongBUS implementation.");
//             filteredRooms = new ArrayList<>();
//         } else if (filteredRooms.isEmpty()) {
//             System.out.println("Warning: No rooms loaded from the database in constructor. Check your database connection or data.");
//         } else {
//             System.out.println("Successfully loaded " + filteredRooms.size() + " rooms from the database in constructor.");
//             for (PhongDTO p : filteredRooms) {
//                 System.out.println("Room: " + p.getMaP() + ", " + p.getTenP() + ", " + p.getLoaiP() + ", " + p.getGiaP());
//             }
//         }

//         // Thanh menu trên cùng
//         JPanel menuPanel = new JPanel();
//         menuPanel.setBackground(new Color(240, 245, 245));
//         menuPanel.setLayout(new BorderLayout());
//         menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

//         // Panel for left side (Info button and search field)
//         JPanel leftPanel = new JPanel();
//         leftPanel.setBackground(new Color(240, 245, 245));
//         leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

//         // Panel for "CHI TIẾT" with icon
//         JPanel infoPanel = new JPanel();
//         infoPanel.setBackground(new Color(240, 245, 245));
//         infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

//         JButton btnInfo = new JButton();
//         try {
//             ImageIcon icon = new ImageIcon("./src/icons/info.png");
//             Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
//             btnInfo.setIcon(new ImageIcon(scaledImage));
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         btnInfo.setBackground(new Color(240, 245, 245));
//         btnInfo.setBorder(BorderFactory.createEmptyBorder());
//         btnInfo.setFocusPainted(false);
//         btnInfo.setEnabled(false);
//         btnInfo.addActionListener(e -> openDetailDialog());
//         btnInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
//         infoPanel.add(btnInfo);

//         JLabel lblChiTiet = new JLabel("CHI TIẾT");
//         lblChiTiet.setFont(new Font("SansSerif", Font.PLAIN, 12));
//         lblChiTiet.setAlignmentX(Component.CENTER_ALIGNMENT);
//         infoPanel.add(lblChiTiet);

//         leftPanel.add(infoPanel);

//         txtSearch = new JTextField(20);
//         txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         txtSearch.setText("Nhập nội dung tìm kiếm...");
//         txtSearch.setForeground(Color.GRAY);
//         txtSearch.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(new Color(209, 207, 207)),
//             BorderFactory.createEmptyBorder(5, 10, 5, 10)
//         ));
//         txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
//             @Override
//             public void focusGained(java.awt.event.FocusEvent e) {
//                 if (txtSearch.getText().equals("Nhập nội dung tìm kiếm...")) {
//                     txtSearch.setText("");
//                     txtSearch.setForeground(Color.BLACK);
//                 }
//             }
//             @Override
//             public void focusLost(java.awt.event.FocusEvent e) {
//                 if (txtSearch.getText().isEmpty()) {
//                     txtSearch.setText("Nhập nội dung tìm kiếm...");
//                     txtSearch.setForeground(Color.GRAY);
//                 }
//             }
//         });
//         leftPanel.add(txtSearch);

//         menuPanel.add(leftPanel, BorderLayout.WEST);

//         // Panel for right side (Refresh and filter components)
//         JPanel rightPanel = new JPanel();
//         rightPanel.setBackground(new Color(240, 245, 245));
//         rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));

//         // Panel for "Làm mới" with icon
//         JPanel refreshPanel = new JPanel();
//         refreshPanel.setBackground(new Color(240, 245, 245));
//         refreshPanel.setLayout(new BoxLayout(refreshPanel, BoxLayout.Y_AXIS));

//         JButton btnRefresh = new JButton();
//         try {
//             ImageIcon icon = new ImageIcon("./src/icons/reload.png");
//             Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
//             btnRefresh.setIcon(new ImageIcon(scaledImage));
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         btnRefresh.setBackground(new Color(240, 245, 245));
//         btnRefresh.setBorder(BorderFactory.createEmptyBorder());
//         btnRefresh.setFocusPainted(false);
//         btnRefresh.addActionListener(e -> loadTableData());
//         btnRefresh.setAlignmentX(Component.CENTER_ALIGNMENT);
//         refreshPanel.add(btnRefresh);

//         JLabel lblLamMoi = new JLabel("Làm mới");
//         lblLamMoi.setFont(new Font("SansSerif", Font.PLAIN, 12));
//         lblLamMoi.setAlignmentX(Component.CENTER_ALIGNMENT);
//         refreshPanel.add(lblLamMoi);

//         rightPanel.add(refreshPanel);

//         JButton btnFilter = new JButton();
//         try {
//             ImageIcon icon = new ImageIcon("./src/icons/filter.png");
//             Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
//             btnFilter.setIcon(new ImageIcon(scaledImage));
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         btnFilter.setBackground(new Color(240, 245, 245));
//         btnFilter.setBorder(BorderFactory.createEmptyBorder());
//         btnFilter.setFocusPainted(false);
//         rightPanel.add(btnFilter);

//         JLabel lblLoaiPhong = new JLabel("Loại Phòng: ");
//         lblLoaiPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         rightPanel.add(lblLoaiPhong);

//         cbLoaiPhong = new JComboBox<>();
//         cbLoaiPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         loadLoaiPhong();
//         cbLoaiPhong.addActionListener(e -> {
//             updateGiaPhong();
//             applyFilters();
//         });
//         rightPanel.add(cbLoaiPhong);

//         JLabel lblGiaPhong = new JLabel("Giá Phòng: ");
//         lblGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         rightPanel.add(lblGiaPhong);

//         cbGiaPhong = new JComboBox<>();
//         cbGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         updateGiaPhong();
//         cbGiaPhong.addActionListener(e -> applyFilters());
//         rightPanel.add(cbGiaPhong);

//         menuPanel.add(rightPanel, BorderLayout.EAST);

//         add(menuPanel, BorderLayout.NORTH);

//         // Panel chính
//         JPanel mainPanel = new JPanel(new BorderLayout());
//         mainPanel.setBackground(new Color(240, 245, 245));

//         // Panel nội dung chính
//         JPanel contentP = new JPanel(new BorderLayout());
//         contentP.setBackground(new Color(240, 245, 245));
//         contentP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

//         // Khởi tạo renderer căn giữa
//         centerRenderer = new DefaultTableCellRenderer();
//         centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

//         // Table model
//         DefaultTableModel tableModel = new DefaultTableModel(
//             new String[]{"STT", "Mã Phòng", "Tên Phòng", "Loại Phòng", "Giá Phòng", "Chi Tiết Loại Phòng", "Tình Trạng"}, 0
//         ) {
//             @Override
//             public boolean isCellEditable(int row, int column) {
//                 return false;
//             }
//         };

//         // JTable
//         table = new JTable(tableModel) {
//             @Override
//             public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
//                 Component c = super.prepareRenderer(renderer, row, column);
//                 c.setBackground(new Color(183, 228, 199));
//                 c.setForeground(column == 6 && getValueAt(row, column).toString().equals("Đã đặt") ? Color.RED :
//                     column == 6 && getValueAt(row, column).toString().equals("Trống") ? new Color(0, 128, 0) : Color.BLACK);
//                 ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
//                 return c;
//             }

//             @Override
//             public JTableHeader getTableHeader() {
//                 JTableHeader header = super.getTableHeader();
//                 header.setPreferredSize(new Dimension(0, 40));
//                 return header;
//             }
//         };

//         table.setRowHeight(40);
//         table.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         table.setShowVerticalLines(false);
//         table.setShowHorizontalLines(false);
//         table.setIntercellSpacing(new Dimension(0, 0));
//         table.setBorder(BorderFactory.createEmptyBorder());

//         // Header style
//         JTableHeader header = table.getTableHeader();
//         header.setFont(new Font("SansSerif", Font.BOLD, 15));
//         header.setBackground(Color.WHITE);
//         header.setForeground(Color.BLACK);
//         header.setReorderingAllowed(false);
//         header.setBorder(BorderFactory.createEmptyBorder());
//         UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());
//         header.setOpaque(false);

//         // Column widths
//         table.getColumnModel().getColumn(0).setPreferredWidth(50);
//         table.getColumnModel().getColumn(1).setPreferredWidth(100);
//         table.getColumnModel().getColumn(2).setPreferredWidth(150);
//         table.getColumnModel().getColumn(3).setPreferredWidth(100);
//         table.getColumnModel().getColumn(4).setPreferredWidth(100);
//         table.getColumnModel().getColumn(5).setPreferredWidth(200);
//         table.getColumnModel().getColumn(6).setPreferredWidth(100);

//         // ScrollPane
//         JScrollPane scrollPane = new JScrollPane(table);
//         scrollPane.setBorder(BorderFactory.createEmptyBorder());
//         scrollPane.getViewport().setBackground(new Color(240, 245, 245));

//         // Custom scroll bar
//         JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
//         verticalScrollBar.setUI(new BasicScrollBarUI() {
//             @Override
//             protected void configureScrollBarColors() {
//                 this.thumbColor = new Color(209, 207, 207);
//                 this.trackColor = new Color(245, 245, 245);
//             }

//             @Override
//             protected JButton createDecreaseButton(int orientation) {
//                 return createZeroButton();
//             }

//             @Override
//             protected JButton createIncreaseButton(int orientation) {
//                 return createZeroButton();
//             }

//             private JButton createZeroButton() {
//                 JButton button = new JButton();
//                 button.setPreferredSize(new Dimension(0, 0));
//                 button.setMinimumSize(new Dimension(0, 0));
//                 button.setMaximumSize(new Dimension(0, 0));
//                 return button;
//             }

//             @Override
//             protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
//                 Graphics2D g2 = (Graphics2D) g.create();
//                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                 g2.setPaint(thumbColor);
//                 g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
//                 g2.dispose();
//             }

//             @Override
//             protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
//                 // Minimal track
//             }
//         });
//         verticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));

//         contentP.add(scrollPane, BorderLayout.CENTER);
//         mainPanel.add(contentP, BorderLayout.CENTER);
//         add(mainPanel, BorderLayout.CENTER);

//         // Load initial table data
//         loadTableData();

//         // Sự kiện nhấp chuột để chọn hàng
//         table.addMouseListener(new java.awt.event.MouseAdapter() {
//             @Override
//             public void mouseClicked(java.awt.event.MouseEvent evt) {
//                 int row = table.getSelectedRow();
//                 if (row >= 0) {
//                     table.setRowSelectionInterval(row, row);
//                     btnInfo.setEnabled(true);
//                 }
//             }
//         });

//         new PhongEventHandler(this);
//     }

//     private void loadLoaiPhong() {
//         Set<String> loaiPhongSet = new HashSet<>();
//         for (PhongDTO p : phongBUS.getAllPhong()) {
//             if (p != null && p.getLoaiP() != null) {
//                 loaiPhongSet.add(p.getLoaiP());
//             }
//         }
//         cbLoaiPhong.removeAllItems();
//         cbLoaiPhong.addItem("Tất cả");
//         for (String loai : loaiPhongSet) {
//             cbLoaiPhong.addItem(loai);
//         }
//     }

//     private void updateGiaPhong() {
//         cbGiaPhong.removeAllItems();
//         String selectedLoaiPhong = cbLoaiPhong.getSelectedItem() != null ? cbLoaiPhong.getSelectedItem().toString() : "Tất cả";
//         ArrayList<PhongDTO> rooms = new ArrayList<>();
//         if (selectedLoaiPhong.equals("Tất cả")) {
//             rooms.addAll(phongBUS.getAllPhong());
//         } else {
//             for (PhongDTO p : phongBUS.getAllPhong()) {
//                 if (p != null && p.getLoaiP() != null && p.getLoaiP().equals(selectedLoaiPhong)) {
//                     rooms.add(p);
//                 }
//             }
//         }
//         System.out.println("Rooms for price filtering (Loại Phòng: " + selectedLoaiPhong + "): " + rooms.size());
//         if (!rooms.isEmpty()) {
//             cbGiaPhong.addItem("Tất cả");
//             int minPrice = Integer.MAX_VALUE;
//             int maxPrice = Integer.MIN_VALUE;
//             for (PhongDTO p : rooms) {
//                 if (p != null) {
//                     if (p.getGiaP() < minPrice) minPrice = p.getGiaP();
//                     if (p.getGiaP() > maxPrice) maxPrice = p.getGiaP();
//                 }
//             }
//             int range = (maxPrice - minPrice) / 3;
//             if (range > 0) {
//                 cbGiaPhong.addItem("< " + (minPrice + range));
//                 cbGiaPhong.addItem((minPrice + range) + " - " + (minPrice + 2 * range));
//                 cbGiaPhong.addItem("> " + (minPrice + 2 * range));
//             } else {
//                 cbGiaPhong.addItem("= " + minPrice);
//             }
//         }
//     }

//     private void applyFilters() {
//         String selectedLoaiPhong = cbLoaiPhong.getSelectedItem() != null ? cbLoaiPhong.getSelectedItem().toString() : "Tất cả";
//         String selectedGiaPhong = cbGiaPhong.getSelectedItem() != null ? cbGiaPhong.getSelectedItem().toString() : "Tất cả";

//         // Start with all rooms from the database
//         ArrayList<PhongDTO> allRooms = phongBUS.getAllPhong();
//         if (allRooms == null) {
//             System.out.println("Error: phongBUS.getAllPhong() returned null in applyFilters. Check PhongBUS implementation.");
//             allRooms = new ArrayList<>();
//         }

//         filteredRooms = new ArrayList<>();
//         for (PhongDTO p : allRooms) {
//             if (p == null) continue;

//             // Room type filter
//             boolean matchesLoaiPhong = selectedLoaiPhong.equals("Tất cả") || (p.getLoaiP() != null && p.getLoaiP().equals(selectedLoaiPhong));

//             // Price filter
//             boolean matchesGiaPhong = true;
//             if (!selectedGiaPhong.equals("Tất cả")) {
//                 if (selectedGiaPhong.startsWith("< ")) {
//                     int price = Integer.parseInt(selectedGiaPhong.replace("< ", ""));
//                     matchesGiaPhong = p.getGiaP() < price;
//                 } else if (selectedGiaPhong.startsWith("> ")) {
//                     int price = Integer.parseInt(selectedGiaPhong.replace("> ", ""));
//                     matchesGiaPhong = p.getGiaP() > price;
//                 } else if (selectedGiaPhong.startsWith("= ")) {
//                     int price = Integer.parseInt(selectedGiaPhong.replace("= ", ""));
//                     matchesGiaPhong = p.getGiaP() == price;
//                 } else {
//                     String[] range = selectedGiaPhong.split(" - ");
//                     int minPrice = Integer.parseInt(range[0]);
//                     int maxPrice = Integer.parseInt(range[1]);
//                     matchesGiaPhong = p.getGiaP() >= minPrice && p.getGiaP() <= maxPrice;
//                 }
//             }

//             if (matchesLoaiPhong && matchesGiaPhong) {
//                 filteredRooms.add(p);
//             }
//         }

//         System.out.println("Filtered rooms (Loại Phòng: " + selectedLoaiPhong + ", Giá Phòng: " + selectedGiaPhong + "): " + filteredRooms.size());
//         updateTable();
//     }

//     private void updateTable() {
//         DefaultTableModel model = (DefaultTableModel) table.getModel();
//         model.setRowCount(0);

//         if (filteredRooms == null) {
//             System.out.println("Error: filteredRooms is null in updateTable.");
//             return;
//         }

//         int stt = 1;
//         for (PhongDTO p : filteredRooms) {
//             if (p != null) {
//                 model.addRow(new Object[]{
//                     stt++,
//                     p.getMaP(),
//                     p.getTenP(),
//                     p.getLoaiP(),
//                     p.getGiaP() + "đ",
//                     p.getChiTietLoaiPhong(),
//                     p.getTinhTrang() == 1 ? "Đã đặt" : "Trống"
//                 });
//             }
//         }
//         System.out.println("Table updated with " + (stt - 1) + " rows.");
//     }

//     private void loadTableData() {
//         filteredRooms = phongBUS.getAllPhong();
//         if (filteredRooms == null) {
//             System.out.println("Error: phongBUS.getAllPhong() returned null in loadTableData. Check PhongBUS implementation.");
//             filteredRooms = new ArrayList<>();
//         } else if (filteredRooms.isEmpty()) {
//             System.out.println("Warning: No rooms loaded from the database in loadTableData. Check your database connection or data.");
//         } else {
//             System.out.println("Successfully loaded " + filteredRooms.size() + " rooms from the database in loadTableData.");
//             for (PhongDTO p : filteredRooms) {
//                 System.out.println("Room: " + p.getMaP() + ", " + p.getTenP() + ", " + p.getLoaiP() + ", " + p.getGiaP());
//             }
//         }

//         // Update table with all data first
//         updateTable();

//         // Populate dropdowns, but do not apply filters yet
//         loadLoaiPhong();
//         updateGiaPhong();
//     }

//     private void openDetailDialog() {
//         int row = table.getSelectedRow();
//         if (row >= 0) {
//             String maP = (String) table.getValueAt(row, 1);
//             PhongDTO room = phongBUS.getPhongById(maP);
//             if (room != null) {
//                 new PhongDetailDialog(this, room).setVisible(true);
//             }
//         }
//     }

//     public JTable getTable() { return table; }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> new FormPhong().setVisible(true));
//     }
// }

// package GUI_PHONG;

// import DTO.PhongDTO;
// import BUS.PhongBUS;

// import javax.swing.*;
// import javax.swing.event.DocumentEvent;
// import javax.swing.event.DocumentListener;
// import javax.swing.plaf.basic.BasicScrollBarUI;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.table.JTableHeader;
// import javax.swing.table.DefaultTableCellRenderer;
// import java.awt.*;
// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.Set;

// public class FormPhong extends JFrame {
//     private JTable table;
//     private PhongBUS phongBUS;
//     private JTextField txtSearch;
//     private JComboBox<String> cbLoaiPhong;
//     private JComboBox<String> cbGiaPhong;
//     private ArrayList<PhongDTO> filteredRooms;
//     private DefaultTableCellRenderer centerRenderer;

//     public FormPhong() {
//         setTitle("Quản lý phòng");
//         setSize(10000, 6000);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setLayout(new BorderLayout());
//         setBackground(new Color(240, 245, 245));

//         phongBUS = new PhongBUS();
//         filteredRooms = phongBUS.getAllPhong();

//         // Debug: Check initial data load
//         if (filteredRooms == null) {
//             System.out.println("Error: phongBUS.getAllPhong() returned null in constructor. Check PhongBUS implementation.");
//             filteredRooms = new ArrayList<>();
//         } else if (filteredRooms.isEmpty()) {
//             System.out.println("Warning: No rooms loaded from the database in constructor. Check your database connection or data.");
//         } else {
//             System.out.println("Successfully loaded " + filteredRooms.size() + " rooms from the database in constructor.");
//             for (PhongDTO p : filteredRooms) {
//                 System.out.println("Room: " + p.getMaP() + ", " + p.getTenP() + ", " + p.getLoaiP() + ", " + p.getGiaP());
//             }
//         }

//         // Thanh menu trên cùng
//         JPanel menuPanel = new JPanel();
//         menuPanel.setBackground(new Color(240, 245, 245));
//         menuPanel.setLayout(new BorderLayout());
//         menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

//         // Panel for left side (Info button and search field)
//         JPanel leftPanel = new JPanel();
//         leftPanel.setBackground(new Color(240, 245, 245));
//         leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

//         // Panel for "CHI TIẾT" with icon
//         JPanel infoPanel = new JPanel();
//         infoPanel.setBackground(new Color(240, 245, 245));
//         infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

//         JButton btnInfo = new JButton();
//         try {
//             ImageIcon icon = new ImageIcon("./src/icons/info.png");
//             Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//             btnInfo.setIcon(new ImageIcon(scaledImage));
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         btnInfo.setBackground(new Color(240, 245, 245));
//         btnInfo.setBorder(BorderFactory.createEmptyBorder());
//         btnInfo.setFocusPainted(false);
//         btnInfo.setEnabled(false);
//         btnInfo.addActionListener(e -> openDetailDialog());
//         btnInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
//         infoPanel.add(btnInfo);

//         JLabel lblChiTiet = new JLabel("CHI TIẾT");
//         lblChiTiet.setFont(new Font("SansSerif", Font.PLAIN, 16));
//         lblChiTiet.setAlignmentX(Component.CENTER_ALIGNMENT);
//         infoPanel.add(lblChiTiet);

//         leftPanel.add(infoPanel);

//         txtSearch = new JTextField(30);
//         txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 16));
//         txtSearch.setText("Nhập nội dung tìm kiếm...");
//         txtSearch.setForeground(Color.GRAY);
//         txtSearch.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(new Color(209, 207, 207)),
//             BorderFactory.createEmptyBorder(5, 15, 5, 15)
//         ));
//         txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
//             @Override
//             public void focusGained(java.awt.event.FocusEvent e) {
//                 if (txtSearch.getText().equals("Nhập nội dung tìm kiếm...")) {
//                     txtSearch.setText("");
//                     txtSearch.setForeground(Color.BLACK);
//                 }
//             }
//             @Override
//             public void focusLost(java.awt.event.FocusEvent e) {
//                 if (txtSearch.getText().isEmpty()) {
//                     txtSearch.setText("Nhập nội dung tìm kiếm...");
//                     txtSearch.setForeground(Color.GRAY);
//                 }
//             }
//         });
//         // Add DocumentListener for real-time search
//         txtSearch.getDocument().addDocumentListener(new DocumentListener() {
//             @Override
//             public void insertUpdate(DocumentEvent e) {
//                 applyFilters();
//             }

//             @Override
//             public void removeUpdate(DocumentEvent e) {
//                 applyFilters();
//             }

//             @Override
//             public void changedUpdate(DocumentEvent e) {
//                 applyFilters();
//             }
//         });
//         // Add ActionListener for Enter key press
//         txtSearch.addActionListener(e -> applyFilters());
//         leftPanel.add(txtSearch);

//         menuPanel.add(leftPanel, BorderLayout.WEST);

//         // Panel for right side (Refresh and filter components)
//         JPanel rightPanel = new JPanel();
//         rightPanel.setBackground(new Color(240, 245, 245));
//         rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));

//         // Panel for "Làm mới" with icon
//         JPanel refreshPanel = new JPanel();
//         refreshPanel.setBackground(new Color(240, 245, 245));
//         refreshPanel.setLayout(new BoxLayout(refreshPanel, BoxLayout.Y_AXIS));

//         JButton btnRefresh = new JButton();
//         try {
//             ImageIcon icon = new ImageIcon("./src/icons/reload.png");
//             Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//             btnRefresh.setIcon(new ImageIcon(scaledImage));
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         btnRefresh.setBackground(new Color(240, 245, 245));
//         btnRefresh.setBorder(BorderFactory.createEmptyBorder());
//         btnRefresh.setFocusPainted(false);
//         btnRefresh.addActionListener(e -> loadTableData());
//         btnRefresh.setAlignmentX(Component.CENTER_ALIGNMENT);
//         refreshPanel.add(btnRefresh);

//         JLabel lblLamMoi = new JLabel("LÀM MỚI");
//         lblLamMoi.setFont(new Font("SansSerif", Font.PLAIN, 16));
//         lblLamMoi.setAlignmentX(Component.CENTER_ALIGNMENT);
//         refreshPanel.add(lblLamMoi);

//         rightPanel.add(refreshPanel);

//         JButton btnFilter = new JButton();
//         try {
//             ImageIcon icon = new ImageIcon("./src/icons/filter.png");
//             Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//             btnFilter.setIcon(new ImageIcon(scaledImage));
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         btnFilter.setBackground(new Color(240, 245, 245));
//         btnFilter.setBorder(BorderFactory.createEmptyBorder());
//         btnFilter.setFocusPainted(false);
//         rightPanel.add(btnFilter);

//         JLabel lblLoaiPhong = new JLabel("Loại Phòng: ");
//         lblLoaiPhong.setFont(new Font("SansSerif", Font.PLAIN, 16));
//         rightPanel.add(lblLoaiPhong);

//         cbLoaiPhong = new JComboBox<>();
//         cbLoaiPhong.setFont(new Font("SansSerif", Font.PLAIN, 16));
//         loadLoaiPhong();
//         cbLoaiPhong.addActionListener(e -> {
//             updateGiaPhong();
//             applyFilters();
//         });
//         rightPanel.add(cbLoaiPhong);

//         JLabel lblGiaPhong = new JLabel("Giá Phòng: ");
//         lblGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 16));
//         rightPanel.add(lblGiaPhong);

//         cbGiaPhong = new JComboBox<>();
//         cbGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 16));
//         updateGiaPhong();
//         cbGiaPhong.addActionListener(e -> applyFilters());
//         rightPanel.add(cbGiaPhong);

//         menuPanel.add(rightPanel, BorderLayout.EAST);

//         add(menuPanel, BorderLayout.NORTH);

//         // Panel chính
//         JPanel mainPanel = new JPanel(new BorderLayout());
//         mainPanel.setBackground(new Color(240, 245, 245));

//         // Panel nội dung chính
//         JPanel contentP = new JPanel(new BorderLayout());
//         contentP.setBackground(new Color(240, 245, 245));
//         contentP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

//         // Khởi tạo renderer căn giữa
//         centerRenderer = new DefaultTableCellRenderer();
//         centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

//         // Table model
//         DefaultTableModel tableModel = new DefaultTableModel(
//             new String[]{"STT", "Mã Phòng", "Tên Phòng", "Loại Phòng", "Giá Phòng", "Chi Tiết Loại Phòng", "Tình Trạng"}, 0
//         ) {
//             @Override
//             public boolean isCellEditable(int row, int column) {
//                 return false;
//             }
//         };

//         // JTable
//         table = new JTable(tableModel) {
//             @Override
//             public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
//                 Component c = super.prepareRenderer(renderer, row, column);
//                 c.setBackground(new Color(183, 228, 199));
//                 c.setForeground(column == 6 && getValueAt(row, column).toString().equals("Đã đặt") ? Color.RED :
//                     column == 6 && getValueAt(row, column).toString().equals("Trống") ? new Color(0, 128, 0) : Color.BLACK);
//                 ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
//                 return c;
//             }

//             @Override
//             public JTableHeader getTableHeader() {
//                 JTableHeader header = super.getTableHeader();
//                 header.setPreferredSize(new Dimension(0, 40));
//                 return header;
//             }
//         };

//         table.setRowHeight(40);
//         table.setFont(new Font("SansSerif", Font.PLAIN, 16));
//         table.setShowVerticalLines(false);
//         table.setShowHorizontalLines(false);
//         table.setIntercellSpacing(new Dimension(0, 0));
//         table.setBorder(BorderFactory.createEmptyBorder());

//         // Header style
//         JTableHeader header = table.getTableHeader();
//         header.setFont(new Font("SansSerif", Font.BOLD, 17));
//         header.setBackground(Color.WHITE);
//         header.setForeground(Color.BLACK);
//         header.setReorderingAllowed(false);
//         header.setBorder(BorderFactory.createEmptyBorder());
//         UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());
//         header.setOpaque(false);

//         // Column widths
//         table.getColumnModel().getColumn(0).setPreferredWidth(50);
//         table.getColumnModel().getColumn(1).setPreferredWidth(100);
//         table.getColumnModel().getColumn(2).setPreferredWidth(150);
//         table.getColumnModel().getColumn(3).setPreferredWidth(100);
//         table.getColumnModel().getColumn(4).setPreferredWidth(100);
//         table.getColumnModel().getColumn(5).setPreferredWidth(200);
//         table.getColumnModel().getColumn(6).setPreferredWidth(100);

//         // ScrollPane
//         JScrollPane scrollPane = new JScrollPane(table);
//         scrollPane.setBorder(BorderFactory.createEmptyBorder());
//         scrollPane.getViewport().setBackground(new Color(240, 245, 245));

//         // Custom scroll bar
//         JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
//         verticalScrollBar.setUI(new BasicScrollBarUI() {
//             @Override
//             protected void configureScrollBarColors() {
//                 this.thumbColor = new Color(209, 207, 207);
//                 this.trackColor = new Color(245, 245, 245);
//             }

//             @Override
//             protected JButton createDecreaseButton(int orientation) {
//                 return createZeroButton();
//             }

//             @Override
//             protected JButton createIncreaseButton(int orientation) {
//                 return createZeroButton();
//             }

//             private JButton createZeroButton() {
//                 JButton button = new JButton();
//                 button.setPreferredSize(new Dimension(0, 0));
//                 button.setMinimumSize(new Dimension(0, 0));
//                 button.setMaximumSize(new Dimension(0, 0));
//                 return button;
//             }

//             @Override
//             protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
//                 Graphics2D g2 = (Graphics2D) g.create();
//                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                 g2.setPaint(thumbColor);
//                 g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
//                 g2.dispose();
//             }

//             @Override
//             protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
//                 // Minimal track
//             }
//         });
//         verticalScrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));

//         contentP.add(scrollPane, BorderLayout.CENTER);
//         mainPanel.add(contentP, BorderLayout.CENTER);
//         add(mainPanel, BorderLayout.CENTER);

//         // Load initial table data
//         loadTableData();

//         // Sự kiện nhấp chuột để chọn hàng
//         table.addMouseListener(new java.awt.event.MouseAdapter() {
//             @Override
//             public void mouseClicked(java.awt.event.MouseEvent evt) {
//                 int row = table.getSelectedRow();
//                 if (row >= 0) {
//                     table.setRowSelectionInterval(row, row);
//                     btnInfo.setEnabled(true);
//                 }
//             }
//         });

//         new PhongEventHandler(this);
//     }

//     private void loadLoaiPhong() {
//         Set<String> loaiPhongSet = new HashSet<>();
//         for (PhongDTO p : phongBUS.getAllPhong()) {
//             if (p != null && p.getLoaiP() != null) {
//                 loaiPhongSet.add(p.getLoaiP());
//             }
//         }
//         cbLoaiPhong.removeAllItems();
//         cbLoaiPhong.addItem("Tất cả");
//         for (String loai : loaiPhongSet) {
//             cbLoaiPhong.addItem(loai);
//         }
//     }

//     private void updateGiaPhong() {
//         cbGiaPhong.removeAllItems();
//         String selectedLoaiPhong = cbLoaiPhong.getSelectedItem() != null ? cbLoaiPhong.getSelectedItem().toString() : "Tất cả";
//         ArrayList<PhongDTO> rooms = new ArrayList<>();
//         if (selectedLoaiPhong.equals("Tất cả")) {
//             rooms.addAll(phongBUS.getAllPhong());
//         } else {
//             for (PhongDTO p : phongBUS.getAllPhong()) {
//                 if (p != null && p.getLoaiP() != null && p.getLoaiP().equals(selectedLoaiPhong)) {
//                     rooms.add(p);
//                 }
//             }
//         }
//         System.out.println("Rooms for price filtering (Loại Phòng: " + selectedLoaiPhong + "): " + rooms.size());
//         if (!rooms.isEmpty()) {
//             cbGiaPhong.addItem("Tất cả");
//             int minPrice = Integer.MAX_VALUE;
//             int maxPrice = Integer.MIN_VALUE;
//             for (PhongDTO p : rooms) {
//                 if (p != null) {
//                     if (p.getGiaP() < minPrice) minPrice = p.getGiaP();
//                     if (p.getGiaP() > maxPrice) maxPrice = p.getGiaP();
//                 }
//             }
//             int range = (maxPrice - minPrice) / 3;
//             if (range > 0) {
//                 cbGiaPhong.addItem("< " + (minPrice + range));
//                 cbGiaPhong.addItem((minPrice + range) + " - " + (minPrice + 2 * range));
//                 cbGiaPhong.addItem("> " + (minPrice + 2 * range));
//             } else {
//                 cbGiaPhong.addItem("= " + minPrice);
//             }
//         }
//     }

//     private void applyFilters() {
//         String selectedLoaiPhong = cbLoaiPhong.getSelectedItem() != null ? cbLoaiPhong.getSelectedItem().toString() : "Tất cả";
//         String selectedGiaPhong = cbGiaPhong.getSelectedItem() != null ? cbGiaPhong.getSelectedItem().toString() : "Tất cả";
//         String keyword = txtSearch.getText().trim().toLowerCase();
//         if (keyword.equals("Nhập nội dung tìm kiếm...")) {
//             keyword = "";
//         }

//         // Start with all rooms from the database
//         ArrayList<PhongDTO> allRooms = phongBUS.getAllPhong();
//         if (allRooms == null) {
//             System.out.println("Error: phongBUS.getAllPhong() returned null in applyFilters. Check PhongBUS implementation.");
//             allRooms = new ArrayList<>();
//         }

//         filteredRooms = new ArrayList<>();
//         for (PhongDTO p : allRooms) {
//             if (p == null) continue;

//             // Room type filter
//             boolean matchesLoaiPhong = selectedLoaiPhong.equals("Tất cả") || (p.getLoaiP() != null && p.getLoaiP().equals(selectedLoaiPhong));

//             // Price filter
//             boolean matchesGiaPhong = true;
//             if (!selectedGiaPhong.equals("Tất cả")) {
//                 if (selectedGiaPhong.startsWith("< ")) {
//                     int price = Integer.parseInt(selectedGiaPhong.replace("< ", ""));
//                     matchesGiaPhong = p.getGiaP() < price;
//                 } else if (selectedGiaPhong.startsWith("> ")) {
//                     int price = Integer.parseInt(selectedGiaPhong.replace("> ", ""));
//                     matchesGiaPhong = p.getGiaP() > price;
//                 } else if (selectedGiaPhong.startsWith("= ")) {
//                     int price = Integer.parseInt(selectedGiaPhong.replace("= ", ""));
//                     matchesGiaPhong = p.getGiaP() == price;
//                 } else {
//                     String[] range = selectedGiaPhong.split(" - ");
//                     int minPrice = Integer.parseInt(range[0]);
//                     int maxPrice = Integer.parseInt(range[1]);
//                     matchesGiaPhong = p.getGiaP() >= minPrice && p.getGiaP() <= maxPrice;
//                 }
//             }

//             // Keyword filter
//             boolean matchesKeyword = keyword.isEmpty() ||
//                 (p.getMaP() != null && p.getMaP().toLowerCase().contains(keyword)) ||
//                 (p.getTenP() != null && p.getTenP().toLowerCase().contains(keyword)) ||
//                 (p.getLoaiP() != null && p.getLoaiP().toLowerCase().contains(keyword)) ||
//                 String.valueOf(p.getGiaP()).contains(keyword) ||
//                 (p.getChiTietLoaiPhong() != null && p.getChiTietLoaiPhong().toLowerCase().contains(keyword));

//             if (matchesLoaiPhong && matchesGiaPhong && matchesKeyword) {
//                 filteredRooms.add(p);
//             }
//         }

//         System.out.println("Filtered rooms (Loại Phòng: " + selectedLoaiPhong + ", Giá Phòng: " + selectedGiaPhong + ", Keyword: " + keyword + "): " + filteredRooms.size());
//         updateTable();
//     }

//     private void updateTable() {
//         DefaultTableModel model = (DefaultTableModel) table.getModel();
//         model.setRowCount(0);

//         if (filteredRooms == null) {
//             System.out.println("Error: filteredRooms is null in updateTable.");
//             return;
//         }

//         int stt = 1;
//         for (PhongDTO p : filteredRooms) {
//             if (p != null) {
//                 model.addRow(new Object[]{
//                     stt++,
//                     p.getMaP(),
//                     p.getTenP(),
//                     p.getLoaiP(),
//                     p.getGiaP() + "đ",
//                     p.getChiTietLoaiPhong(),
//                     p.getTinhTrang() == 1 ? "Đã đặt" : "Trống"
//                 });
//             }
//         }
//         System.out.println("Table updated with " + (stt - 1) + " rows.");
//     }

//     private void loadTableData() {
//         filteredRooms = phongBUS.getAllPhong();
//         if (filteredRooms == null) {
//             System.out.println("Error: phongBUS.getAllPhong() returned null in loadTableData. Check PhongBUS implementation.");
//             filteredRooms = new ArrayList<>();
//         } else if (filteredRooms.isEmpty()) {
//             System.out.println("Warning: No rooms loaded from the database in loadTableData. Check your database connection or data.");
//         } else {
//             System.out.println("Successfully loaded " + filteredRooms.size() + " rooms from the database in loadTableData.");
//             for (PhongDTO p : filteredRooms) {
//                 System.out.println("Room: " + p.getMaP() + ", " + p.getTenP() + ", " + p.getLoaiP() + ", " + p.getGiaP());
//             }
//         }

//         // Update table with all data first
//         updateTable();

//         // Populate dropdowns, but do not apply filters yet
//         loadLoaiPhong();
//         updateGiaPhong();
//     }

//     private void openDetailDialog() {
//         int row = table.getSelectedRow();
//         if (row >= 0) {
//             String maP = (String) table.getValueAt(row, 1);
//             PhongDTO room = phongBUS.getPhongById(maP);
//             if (room != null) {
//                 new PhongDetailDialog(this, room).setVisible(true);
//             }
//         }
//     }

//     public JTable getTable() { return table; }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> new FormPhong().setVisible(true));
//     }
// }

package GUI_PHONG;

import DTO.PhongDTO;
import BUS.PhongBUS;

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

public class FormPhong extends JFrame {
    private JTable table;
    private PhongBUS phongBUS;
    private JTextField txtSearch;
    private JComboBox<String> cbLoaiPhong;
    private JComboBox<String> cbGiaPhong;
    private ArrayList<PhongDTO> filteredRooms;
    private ArrayList<PhongDTO> searchedRooms; // List to store search results
    private DefaultTableCellRenderer centerRenderer;
    private DocumentListener searchListener; // To control search listener
    private java.awt.event.ActionListener loaiPhongListener; // To control LoaiPhong listener
    private java.awt.event.ActionListener giaPhongListener; // To control GiaPhong listener

    public FormPhong() {
        setTitle("Quản lý phòng");
        setSize(10000, 6000); // Adjusted size for better visibility
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 245));

        phongBUS = new PhongBUS();
        filteredRooms = phongBUS.getAllPhong();
        searchedRooms = new ArrayList<>(); // Initialize searchedRooms

        // Debug: Check initial data load
        if (filteredRooms == null) {
            System.out.println("Error: phongBUS.getAllPhong() returned null in constructor. Check PhongBUS implementation.");
            filteredRooms = new ArrayList<>();
        } else if (filteredRooms.isEmpty()) {
            System.out.println("Warning: No rooms loaded from the database in constructor. Check your database connection or data.");
        } else {
            System.out.println("Successfully loaded " + filteredRooms.size() + " rooms from the database in constructor.");
            for (PhongDTO p : filteredRooms) {
                System.out.println("Room: " + p.getMaP() + ", " + p.getTenP() + ", " + p.getLoaiP() + ", " + p.getGiaP());
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

        // Panel for "Làm mới" with icon (moved to leftPanel)
        JPanel refreshPanel = new JPanel();
        refreshPanel.setBackground(new Color(240, 245, 245));
        refreshPanel.setLayout(new BoxLayout(refreshPanel, BoxLayout.Y_AXIS));

        JButton btnRefresh = new JButton();
        try {
            ImageIcon icon = new ImageIcon("./src/icons/reload.png");
            Image scaledImage = icon.getImage().getScaledInstance(50, 50 , Image.SCALE_SMOOTH);
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

        leftPanel.add(refreshPanel); // Add "Làm mới" button to leftPanel first

        // Panel for "CHI TIẾT" with icon
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(240, 245, 245));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JButton btnInfo = new JButton();
        try {
            ImageIcon icon = new ImageIcon("./src/icons/info.png");
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
        // Add ActionListener for Enter key press (will enable later)
        txtSearch.addActionListener(e -> {
            performSearch();
            applyFilters();
        });
        leftPanel.add(txtSearch);

        menuPanel.add(leftPanel, BorderLayout.WEST);

        // Panel for right side (Filter components only)
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

        JLabel lblLoaiPhong = new JLabel("Loại Phòng: ");
        lblLoaiPhong.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rightPanel.add(lblLoaiPhong);

        cbLoaiPhong = new JComboBox<>();
        cbLoaiPhong.setFont(new Font("SansSerif", Font.PLAIN, 16));
        // Define the listener but don't add it yet
        loaiPhongListener = e -> {
            updateGiaPhong();
            applyFilters();
        };
        rightPanel.add(cbLoaiPhong);

        JLabel lblGiaPhong = new JLabel("Giá Phòng: ");
        lblGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rightPanel.add(lblGiaPhong);

        cbGiaPhong = new JComboBox<>();
        cbGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 16));
        // Define the listener but don't add it yet
        giaPhongListener = e -> applyFilters();
        rightPanel.add(cbGiaPhong);

        menuPanel.add(rightPanel, BorderLayout.EAST);

        add(menuPanel, BorderLayout.NORTH);

        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 245, 245));

        // Panel nội dung chính
        JPanel contentP = new JPanel(new BorderLayout());
        contentP.setBackground(new Color(240, 245, 245));
        contentP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Khởi tạo renderer căn giữa
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Table model
        DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"STT", "Mã Phòng", "Tên Phòng", "Loại Phòng", "Giá Phòng", "Chi Tiết Loại Phòng", "Tình Trạng"}, 0
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
                c.setBackground(new Color(183, 228, 199));
                c.setForeground(column == 6 && getValueAt(row, column).toString().equals("Đã đặt") ? Color.RED :
                    column == 6 && getValueAt(row, column).toString().equals("Trống") ? new Color(0, 128, 0) : Color.BLACK);
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
        table.getColumnModel().getColumn(5).setPreferredWidth(200);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(240, 245, 245));

        // Custom scroll bar
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

        new PhongEventHandler(this);
    }

    private void loadLoaiPhong() {
        Set<String> loaiPhongSet = new HashSet<>();
        for (PhongDTO p : searchedRooms) { // Use searchedRooms
            if (p != null && p.getLoaiP() != null) {
                loaiPhongSet.add(p.getLoaiP());
            }
        }
        cbLoaiPhong.removeAllItems();
        cbLoaiPhong.addItem("Tất cả");
        for (String loai : loaiPhongSet) {
            cbLoaiPhong.addItem(loai);
        }
        cbLoaiPhong.setSelectedItem("Tất cả"); // Ensure default selection
    }

    private void updateGiaPhong() {
        cbGiaPhong.removeAllItems();
        String selectedLoaiPhong = cbLoaiPhong.getSelectedItem() != null ? cbLoaiPhong.getSelectedItem().toString() : "Tất cả";
        ArrayList<PhongDTO> rooms = new ArrayList<>();
        if (selectedLoaiPhong.equals("Tất cả")) {
            rooms.addAll(searchedRooms);
        } else {
            for (PhongDTO p : searchedRooms) {
                if (p != null && p.getLoaiP() != null && p.getLoaiP().equals(selectedLoaiPhong)) {
                    rooms.add(p);
                }
            }
        }
        System.out.println("Rooms for price filtering (Loại Phòng: " + selectedLoaiPhong + "): " + rooms.size());
        if (!rooms.isEmpty()) {
            cbGiaPhong.addItem("Tất cả");
            int minPrice = Integer.MAX_VALUE;
            int maxPrice = Integer.MIN_VALUE;
            for (PhongDTO p : rooms) {
                if (p != null) {
                    if (p.getGiaP() < minPrice) minPrice = p.getGiaP();
                    if (p.getGiaP() > maxPrice) maxPrice = p.getGiaP();
                }
            }
            int range = (maxPrice - minPrice) / 3;
            if (range > 0) {
                cbGiaPhong.addItem("< " + (minPrice + range));
                cbGiaPhong.addItem((minPrice + range) + " - " + (minPrice + 2 * range));
                cbGiaPhong.addItem("> " + (minPrice + 2 * range));
            } else {
                cbGiaPhong.addItem("= " + minPrice);
            }
        }
        cbGiaPhong.setSelectedItem("Tất cả"); // Ensure default selection
    }

    private void performSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.equals("Nhập nội dung tìm kiếm...")) {
            keyword = "";
        }

        ArrayList<PhongDTO> allRooms = phongBUS.getAllPhong();
        if (allRooms == null) {
            System.out.println("Error: phongBUS.getAllPhong() returned null in performSearch. Check PhongBUS implementation.");
            allRooms = new ArrayList<>();
        }

        searchedRooms.clear();
        for (PhongDTO p : allRooms) {
            if (p == null) continue;

            boolean matchesKeyword = keyword.isEmpty() ||
                (p.getMaP() != null && p.getMaP().toLowerCase().contains(keyword)) ||
                (p.getTenP() != null && p.getTenP().toLowerCase().contains(keyword)) ||
                (p.getLoaiP() != null && p.getLoaiP().toLowerCase().contains(keyword)) ||
                String.valueOf(p.getGiaP()).contains(keyword) ||
                (p.getChiTietLoaiPhong() != null && p.getChiTietLoaiPhong().toLowerCase().contains(keyword));

            if (matchesKeyword) {
                searchedRooms.add(p);
            }
        }

        System.out.println("Searched rooms (Keyword: " + keyword + "): " + searchedRooms.size());
    }

    private void applyFilters() {
        String selectedLoaiPhong = cbLoaiPhong.getSelectedItem() != null ? cbLoaiPhong.getSelectedItem().toString() : "Tất cả";
        String selectedGiaPhong = cbGiaPhong.getSelectedItem() != null ? cbGiaPhong.getSelectedItem().toString() : "Tất cả";

        filteredRooms = new ArrayList<>();
        for (PhongDTO p : searchedRooms) {
            if (p == null) continue;

            // Room type filter
            boolean matchesLoaiPhong = selectedLoaiPhong.equals("Tất cả") || (p.getLoaiP() != null && p.getLoaiP().equals(selectedLoaiPhong));

            // Price filter
            boolean matchesGiaPhong = true;
            if (!selectedGiaPhong.equals("Tất cả")) {
                if (selectedGiaPhong.startsWith("< ")) {
                    int price = Integer.parseInt(selectedGiaPhong.replace("< ", ""));
                    matchesGiaPhong = p.getGiaP() < price;
                } else if (selectedGiaPhong.startsWith("> ")) {
                    int price = Integer.parseInt(selectedGiaPhong.replace("> ", ""));
                    matchesGiaPhong = p.getGiaP() > price;
                } else if (selectedGiaPhong.startsWith("= ")) {
                    int price = Integer.parseInt(selectedGiaPhong.replace("= ", ""));
                    matchesGiaPhong = p.getGiaP() == price;
                } else {
                    String[] range = selectedGiaPhong.split(" - ");
                    int minPrice = Integer.parseInt(range[0]);
                    int maxPrice = Integer.parseInt(range[1]);
                    matchesGiaPhong = p.getGiaP() >= minPrice && p.getGiaP() <= maxPrice;
                }
            }

            if (matchesLoaiPhong && matchesGiaPhong) {
                filteredRooms.add(p);
            }
        }

        System.out.println("Filtered rooms (Loại Phòng: " + selectedLoaiPhong + ", Giá Phòng: " + selectedGiaPhong + "): " + filteredRooms.size());
        updateTable();
    }

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (filteredRooms == null) {
            System.out.println("Error: filteredRooms is null in updateTable.");
            return;
        }

        int stt = 1;
        for (PhongDTO p : filteredRooms) {
            if (p != null) {
                model.addRow(new Object[]{
                    stt++,
                    p.getMaP(),
                    p.getTenP(),
                    p.getLoaiP(),
                    p.getGiaP() + "đ",
                    p.getChiTietLoaiPhong(),
                    p.getTinhTrang() == 1 ? "Đã đặt" : "Trống"
                });
            }
        }
        System.out.println("Table updated with " + (stt - 1) + " rows.");
    }

    private void loadTableData() {
        // Load all rooms
        ArrayList<PhongDTO> allRooms = phongBUS.getAllPhong();
        if (allRooms == null) {
            System.out.println("Error: phongBUS.getAllPhong() returned null in loadTableData. Check PhongBUS implementation.");
            allRooms = new ArrayList<>();
        } else if (allRooms.isEmpty()) {
            System.out.println("Warning: No rooms loaded from the database in loadTableData. Check your database connection or data.");
        } else {
            System.out.println("Successfully loaded " + allRooms.size() + " rooms from the database in loadTableData.");
            for (PhongDTO p : allRooms) {
                System.out.println("Room: " + p.getMaP() + ", " + p.getTenP() + ", " + p.getLoaiP() + ", " + p.getGiaP());
            }
        }

        // Set both searchedRooms and filteredRooms to all rooms
        searchedRooms.clear();
        searchedRooms.addAll(allRooms);
        filteredRooms.clear();
        filteredRooms.addAll(allRooms);

        // Reset search field
        txtSearch.getDocument().removeDocumentListener(searchListener);
        txtSearch.setText("Nhập nội dung tìm kiếm...");
        txtSearch.setForeground(Color.GRAY);
        txtSearch.getDocument().addDocumentListener(searchListener);

        // Reset dropdowns without triggering listeners
        cbLoaiPhong.removeActionListener(loaiPhongListener);
        cbGiaPhong.removeActionListener(giaPhongListener);

        // Update table with all data
        updateTable();

        // Populate and reset dropdowns
        loadLoaiPhong();
        updateGiaPhong();

        // Re-add listeners
        cbLoaiPhong.addActionListener(loaiPhongListener);
        cbGiaPhong.addActionListener(giaPhongListener);
    }

    private void openDetailDialog() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String maP = (String) table.getValueAt(row, 1);
            PhongDTO room = phongBUS.getPhongById(maP);
            if (room != null) {
                new PhongDetailDialog(this, room).setVisible(true);
            }
        }
    }

    public JTable getTable() { return table; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPhong().setVisible(true));
    }
}
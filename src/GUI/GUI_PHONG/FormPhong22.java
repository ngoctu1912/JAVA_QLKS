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

//         // Thanh menu trên cùng
//         JPanel menuPanel = new JPanel();
//         menuPanel.setBackground(new Color(240, 245, 245));
//         menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
//         menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

//         // Nút "Thông tin" với icon
//         JButton btnInfo = new JButton();
//         try {
//             ImageIcon icon = new ImageIcon("./src/icons/file-circle-info.png");
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
//         menuPanel.add(btnInfo);

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
//         menuPanel.add(txtSearch);

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
//         menuPanel.add(btnRefresh);

//         JButton btnFilter = new JButton();
//         try {
//             ImageIcon icon = new ImageIcon("./src/icons/filter-list.png");
//             Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
//             btnFilter.setIcon(new ImageIcon(scaledImage));
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         btnFilter.setBackground(new Color(240, 245, 245));
//         btnFilter.setBorder(BorderFactory.createEmptyBorder());
//         btnFilter.setFocusPainted(false);
//         // Remove toggle action since filter panel is always visible
//         menuPanel.add(btnFilter);

//         // Filter components directly in menuPanel
//         JLabel lblLoaiPhong = new JLabel("Loại Phòng: ");
//         lblLoaiPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         menuPanel.add(lblLoaiPhong);

//         cbLoaiPhong = new JComboBox<>();
//         cbLoaiPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         loadLoaiPhong();
//         cbLoaiPhong.addActionListener(e -> {
//             filterRooms();
//             updateGiaPhong();
//         });
//         menuPanel.add(cbLoaiPhong);

//         JLabel lblGiaPhong = new JLabel("Giá Phòng: ");
//         lblGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         menuPanel.add(lblGiaPhong);

//         cbGiaPhong = new JComboBox<>();
//         cbGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
//         updateGiaPhong();
//         cbGiaPhong.addActionListener(e -> filterRoomsByPrice());
//         menuPanel.add(cbGiaPhong);

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

//         // Sự kiện tìm kiếm
//         txtSearch.addActionListener(e -> {
//             String keyword = txtSearch.getText().trim().toLowerCase();
//             if (keyword.isEmpty() || keyword.equals("Nhập nội dung tìm kiếm...")) {
//                 loadTableData();
//             } else {
//                 searchRooms(keyword);
//             }
//         });

//         new PhongEventHandler(this);
//     }

//     private void loadLoaiPhong() {
//         Set<String> loaiPhongSet = new HashSet<>();
//         for (PhongDTO p : phongBUS.getAllPhong()) {
//             loaiPhongSet.add(p.getLoaiP());
//         }
//         cbLoaiPhong.addItem("Tất cả");
//         for (String loai : loaiPhongSet) {
//             cbLoaiPhong.addItem(loai);
//         }
//     }

//     private void updateGiaPhong() {
//         cbGiaPhong.removeAllItems();
//         String selectedLoaiPhong = cbLoaiPhong.getSelectedItem().toString();
//         ArrayList<PhongDTO> rooms = new ArrayList<>();
//         if (selectedLoaiPhong.equals("Tất cả")) {
//             rooms.addAll(phongBUS.getAllPhong());
//         } else {
//             for (PhongDTO p : phongBUS.getAllPhong()) {
//                 if (p.getLoaiP().equals(selectedLoaiPhong)) {
//                     rooms.add(p);
//                 }
//             }
//         }
//         if (!rooms.isEmpty()) {
//             cbGiaPhong.addItem("Tất cả");
//             int minPrice = Integer.MAX_VALUE;
//             int maxPrice = Integer.MIN_VALUE;
//             for (PhongDTO p : rooms) {
//                 if (p.getGiaP() < minPrice) minPrice = p.getGiaP();
//                 if (p.getGiaP() > maxPrice) maxPrice = p.getGiaP();
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

//     private void filterRooms() {
//         String selectedLoaiPhong = cbLoaiPhong.getSelectedItem().toString();
//         String keyword = txtSearch.getText().trim().toLowerCase();
//         filteredRooms = new ArrayList<>();
//         for (PhongDTO p : phongBUS.getAllPhong()) {
//             boolean matchesLoaiPhong = selectedLoaiPhong.equals("Tất cả") || p.getLoaiP().equals(selectedLoaiPhong);
//             boolean matchesKeyword = keyword.isEmpty() || keyword.equals("Nhập nội dung tìm kiếm...") ||
//                 p.getMaP().toLowerCase().contains(keyword) ||
//                 p.getTenP().toLowerCase().contains(keyword) ||
//                 p.getLoaiP().toLowerCase().contains(keyword) ||
//                 String.valueOf(p.getGiaP()).contains(keyword) ||
//                 p.getChiTietLoaiPhong().toLowerCase().contains(keyword);
//             if (matchesLoaiPhong && matchesKeyword) {
//                 filteredRooms.add(p);
//             }
//         }
//         filterRoomsByPrice();
//     }

//     private void filterRoomsByPrice() {
//         String selectedGiaPhong = cbGiaPhong.getSelectedItem() != null ? cbGiaPhong.getSelectedItem().toString() : "Tất cả";
//         String selectedLoaiPhong = cbLoaiPhong.getSelectedItem().toString();
//         String keyword = txtSearch.getText().trim().toLowerCase();
//         filteredRooms = new ArrayList<>();
//         for (PhongDTO p : phongBUS.getAllPhong()) {
//             boolean matchesLoaiPhong = selectedLoaiPhong.equals("Tất cả") || p.getLoaiP().equals(selectedLoaiPhong);
//             boolean matchesKeyword = keyword.isEmpty() || keyword.equals("Nhập nội dung tìm kiếm...") ||
//                 p.getMaP().toLowerCase().contains(keyword) ||
//                 p.getTenP().toLowerCase().contains(keyword) ||
//                 p.getLoaiP().toLowerCase().contains(keyword) ||
//                 String.valueOf(p.getGiaP()).contains(keyword) ||
//                 p.getChiTietLoaiPhong().toLowerCase().contains(keyword);
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
//             if (matchesLoaiPhong && matchesKeyword && matchesGiaPhong) {
//                 filteredRooms.add(p);
//             }
//         }
//         updateTable();
//     }

//     private void updateTable() {
//         DefaultTableModel model = (DefaultTableModel) table.getModel();
//         model.setRowCount(0);

//         int stt = 1;
//         for (PhongDTO p : filteredRooms) {
//             model.addRow(new Object[]{
//                 stt++,
//                 p.getMaP(),
//                 p.getTenP(),
//                 p.getLoaiP(),
//                 p.getGiaP() + "đ",
//                 p.getChiTietLoaiPhong(),
//                 p.getTinhTrang() == 1 ? "Đã đặt" : "Trống"
//             });
//         }
//     }

//     private void loadTableData() {
//         filteredRooms = phongBUS.getAllPhong();
//         updateTable();
//     }

//     private void searchRooms(String keyword) {
//         filterRooms();
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
// // package GUI_PHANQUYEN;

// // import BUS.NhomQuyenBUS;
// // import DTO.ChiTietQuyenDTO;
// // import DTO.DanhMucChucNangDTO;
// // import DTO.NhomQuyenDTO;
// // import DAO.DanhMucChucNangDAO;
// // import javax.swing.*;
// // import javax.swing.table.DefaultTableCellRenderer;
// // import javax.swing.table.DefaultTableModel;
// // import javax.swing.table.JTableHeader;
// // import javax.swing.table.TableCellRenderer;
// // import java.awt.*;
// // import java.util.ArrayList;

// // public class PhanQuyenDialog extends JDialog {
// //     private NhomQuyenBUS nhomQuyenBUS;
// //     private PhanQuyenGUI gui;
// //     private ArrayList<DanhMucChucNangDTO> danhMucChucNangList;
// //     private NhomQuyenDTO nhomQuyen;
// //     private String type;
// //     private JTextField tenField;
// //     private DefaultTableModel permissionTableModel;
// //     private JTable permissionTable;

// //     public PhanQuyenDialog(NhomQuyenBUS nhomQuyenBUS, PhanQuyenGUI parent, Component owner, String title, boolean modal,
// //             String type, NhomQuyenDTO nhomQuyen) {
// //         super(parent, title, modal);
// //         this.nhomQuyenBUS = nhomQuyenBUS;
// //         this.gui = parent;
// //         this.danhMucChucNangList = DanhMucChucNangDAO.getInstance().selectAll();
// //         this.nhomQuyen = nhomQuyen;
// //         this.type = type;
// //         initComponents();
// //         setLocationRelativeTo(owner);
// //     }

// //     private void initComponents() {
// //         try {
// //             setSize(1000, 550);
// //             setLayout(new BorderLayout());
// //             getContentPane().setBackground(Color.WHITE);

// //             // Main panel with padding
// //             JPanel mainPanel = new JPanel(new BorderLayout());
// //             mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
// //             mainPanel.setBackground(Color.WHITE);

// //             // Role Name Panel
// //             JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
// //             formPanel.setBackground(Color.WHITE);
// //             formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

// //             JLabel roleLabel = new JLabel("Tên nhóm quyền:");
// //             roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
// //             formPanel.add(roleLabel);

// //             tenField = new JTextField(75);
// //             tenField.setText(nhomQuyen != null ? nhomQuyen.getTEN() : "");
// //             tenField.setFont(new Font("Arial", Font.PLAIN, 14));
// //             tenField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
// //             if (type.equals("view")) {
// //                 tenField.setEditable(false);
// //             }
// //             formPanel.add(tenField);

// //             mainPanel.add(formPanel, BorderLayout.NORTH);

// //             // Permissions Table
// //             String[] columnNames = { "Danh mục chức năng", "Xem", "Tạo mới", "Cập nhật", "Xóa" };
// //             permissionTableModel = new DefaultTableModel(columnNames, 0) {
// //                 @Override
// //                 public Class<?> getColumnClass(int columnIndex) {
// //                     return columnIndex == 0 ? String.class : Boolean.class;
// //                 }

// //                 @Override
// //                 public boolean isCellEditable(int row, int column) {
// //                     return column != 0 && !type.equals("view");
// //                 }
// //             };

// //             // Populate the table with functions
// //             if (danhMucChucNangList == null || danhMucChucNangList.isEmpty()) {
// //                 System.out.println("Danh sách danh mục chức năng rỗng!");
// //             } else {
// //                 for (DanhMucChucNangDTO dm : danhMucChucNangList) {
// //                     Object[] row = new Object[5];
// //                     row[0] = dm.getTENCN();
// //                     row[1] = false; // Xem
// //                     row[2] = false; // Tạo mới
// //                     row[3] = false; // Cập nhật
// //                     row[4] = false; // Xóa
// //                     permissionTableModel.addRow(row);
// //                 }
// //             }

// //             // Load existing permissions if editing or viewing
// //             if (nhomQuyen != null) {
// //                 ArrayList<ChiTietQuyenDTO> chiTietQuyenList = nhomQuyenBUS
// //                         .getChiTietQuyen(String.valueOf(nhomQuyen.getMNQ()));
// //                 System.out.println("Số lượng chi tiết quyền: " + chiTietQuyenList.size()); // Debug
// //                 for (ChiTietQuyenDTO ctq : chiTietQuyenList) {
// //                     int mcn = ctq.getMCN();
// //                     String hanhDong = ctq.getHANHDONG();
// //                     System.out.println("Quyền: MCN=" + mcn + ", Hành động=" + hanhDong); // Debug
// //                     for (int i = 0; i < permissionTableModel.getRowCount(); i++) {
// //                         String chucNang = (String) permissionTableModel.getValueAt(i, 0);
// //                         int rowMcn = danhMucChucNangList.stream()
// //                                 .filter(dm -> dm.getTENCN().equals(chucNang))
// //                                 .findFirst().map(DanhMucChucNangDTO::getMCN).orElse(-1);
// //                         if (rowMcn == mcn) {
// //                             switch (hanhDong.toLowerCase()) { // Chuẩn hóa hành động
// //                                 case "view":
// //                                     permissionTableModel.setValueAt(true, i, 1);
// //                                     break;
// //                                 case "create":
// //                                     permissionTableModel.setValueAt(true, i, 2);
// //                                     break;
// //                                 case "update":
// //                                     permissionTableModel.setValueAt(true, i, 3);
// //                                     break;
// //                                 case "delete":
// //                                     permissionTableModel.setValueAt(true, i, 4);
// //                                     break;
// //                                 default:
// //                                     System.out.println("Hành động không hợp lệ: " + hanhDong);
// //                                     break;
// //                             }
// //                         }
// //                     }
// //                 }
// //                 permissionTableModel.fireTableDataChanged(); // Làm mới bảng
// //             }

// //             permissionTable = new JTable(permissionTableModel) {
// //                 @Override
// //                 public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
// //                     Component c = super.prepareRenderer(renderer, row, column);
// //                     c.setBackground(null); // Mặc định theo FlatLaf
// //                     c.setForeground(Color.BLACK); // Màu chữ đen
// //                     if (c instanceof JComponent) {
// //                         ((JComponent) c).setBorder(BorderFactory.createEmptyBorder()); // Không viền
// //                     }
// //                     return c;
// //                 }
// //             };
// //             permissionTable.getColumnModel().getColumn(0).setPreferredWidth(200);
// //             for (int i = 1; i < permissionTable.getColumnCount(); i++) {
// //                 permissionTable.getColumnModel().getColumn(i).setPreferredWidth(80);
// //             }
// //             permissionTable.setRowHeight(25);

// //             // Căn giữa các checkbox
// //             DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
// //             centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
// //             for (int i = 1; i < permissionTable.getColumnCount(); i++) {
// //                 permissionTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
// //             }

// //             // Căn trái cột đầu tiên (Tên chức năng)
// //             DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
// //             leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
// //             permissionTable.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);

// //             // Thêm border cho từng ô
// //             permissionTable.setShowGrid(true);
// //             permissionTable.setGridColor(Color.LIGHT_GRAY);
// //             permissionTable.setIntercellSpacing(new Dimension(1, 1));

// //             permissionTable.setShowGrid(false); // Tắt lưới
// //             permissionTable.setIntercellSpacing(new Dimension(0, 0)); // Xóa khoảng cách
// //             permissionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
// //             permissionTable.setCellSelectionEnabled(true);
// //             permissionTable.setSelectionBackground(new Color(0, 0, 0, 0)); // Trong suốt
// //             permissionTable.setSelectionForeground(Color.BLACK);
// //             permissionTable.setFocusable(false); // Ngăn viền focus

// //             // Remove borders between column headers
// //             JTableHeader header = permissionTable.getTableHeader();
// //             header.setBorder(BorderFactory.createEmptyBorder());
// //             header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
// //                 @Override
// //                 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
// //                         boolean hasFocus, int row, int column) {
// //                     Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
// //                     c.setBackground(null); // Mặc định theo FlatLaf
// //                     c.setForeground(Color.BLACK);
// //                     setBorder(BorderFactory.createEmptyBorder());
// //                     setHorizontalAlignment(SwingConstants.CENTER);
// //                     return c;
// //                 }
// //             });

// //             JScrollPane permissionScroll = new JScrollPane(permissionTable);
// //             permissionScroll.setPreferredSize(new Dimension(500, 300));
// //             permissionScroll.setBorder(BorderFactory.createEmptyBorder());
// //             mainPanel.add(permissionScroll, BorderLayout.CENTER);

// //             // Button Panel
// //             JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
// //             buttonPanel.setBackground(Color.WHITE);

// //             if (!type.equals("view")) {
// //                 JButton saveButton = new JButton(type.equals("create") ? "Thêm nhóm quyền" : "Cập nhật nhóm quyền");
// //                 saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
// //                 saveButton.setBackground(new Color(66, 139, 202));
// //                 saveButton.setForeground(Color.WHITE);
// //                 saveButton.setBorderPainted(false);
// //                 saveButton.addActionListener(e -> saveNhomQuyen());
// //                 buttonPanel.add(saveButton);
// //             }

// //             JButton closeButton = new JButton(type.equals("view") ? "Đóng" : "Hủy bỏ");
// //             closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
// //             closeButton.setBackground(new Color(217, 83, 79));
// //             closeButton.setForeground(Color.WHITE);
// //             closeButton.setBorderPainted(false);
// //             closeButton.addActionListener(e -> dispose());
// //             buttonPanel.add(closeButton);

// //             add(mainPanel, BorderLayout.CENTER);
// //             add(buttonPanel, BorderLayout.SOUTH);
// //         } catch (Exception ex) {
// //             ex.printStackTrace();
// //             JOptionPane.showMessageDialog(this, "Lỗi khi khởi tạo dialog: " + ex.getMessage(), "Lỗi",
// //                     JOptionPane.ERROR_MESSAGE);
// //         }
// //     }

// //     private void saveNhomQuyen() {
// //         String ten = tenField.getText();
// //         if (ten.isEmpty()) {
// //             JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhóm quyền!");
// //             return;
// //         }

// //         ArrayList<ChiTietQuyenDTO> chiTietQuyenList = new ArrayList<>();
// //         for (int i = 0; i < permissionTableModel.getRowCount(); i++) {
// //             String chucNang = (String) permissionTableModel.getValueAt(i, 0);
// //             int mcn = danhMucChucNangList.stream()
// //                     .filter(dm -> dm.getTENCN().equals(chucNang))
// //                     .findFirst().map(DanhMucChucNangDTO::getMCN).orElse(0);

// //             if ((Boolean) permissionTableModel.getValueAt(i, 1)) {
// //                 chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "view"));
// //             }
// //             if ((Boolean) permissionTableModel.getValueAt(i, 2)) {
// //                 chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "create"));
// //             }
// //             if ((Boolean) permissionTableModel.getValueAt(i, 3)) {
// //                 chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "update"));
// //             }
// //             if ((Boolean) permissionTableModel.getValueAt(i, 4)) {
// //                 chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "delete"));
// //             }
// //         }

// //         if (type.equals("create")) {
// //             nhomQuyenBUS.add(ten, chiTietQuyenList);
// //         } else if (type.equals("update")) {
// //             nhomQuyen.setTEN(ten);
// //             nhomQuyenBUS.update(nhomQuyen, chiTietQuyenList, nhomQuyenBUS.getAll().indexOf(nhomQuyen));
// //         }
// //         gui.loadNhomQuyenData();
// //         dispose();
// //     }
// // }

// package GUI_PHANQUYEN;

// import BUS.NhomQuyenBUS;
// import DTO.ChiTietQuyenDTO;
// import DTO.DanhMucChucNangDTO;
// import DTO.NhomQuyenDTO;
// import DAO.DanhMucChucNangDAO;
// import javax.swing.*;
// import javax.swing.table.DefaultTableCellRenderer;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.table.JTableHeader;
// import javax.swing.table.TableCellRenderer;
// import java.awt.*;
// import java.util.ArrayList;

// public class PhanQuyenDialog extends JDialog {
//     private NhomQuyenBUS nhomQuyenBUS;
//     private PhanQuyenGUI gui;
//     private ArrayList<DanhMucChucNangDTO> danhMucChucNangList;
//     private NhomQuyenDTO nhomQuyen;
//     private String type;
//     private JTextField tenField;
//     private DefaultTableModel permissionTableModel;
//     private JTable permissionTable;

//     public PhanQuyenDialog(NhomQuyenBUS nhomQuyenBUS, PhanQuyenGUI gui, JFrame owner, String title, boolean modal,
//             String type, NhomQuyenDTO nhomQuyen) {
//         super(owner, title, modal);
//         this.nhomQuyenBUS = nhomQuyenBUS;
//         this.gui = gui;
//         this.danhMucChucNangList = DanhMucChucNangDAO.getInstance().selectAll();
//         this.nhomQuyen = nhomQuyen;
//         this.type = type;
//         initComponents();
//         setLocationRelativeTo(owner);
//     }

//     private void initComponents() {
//         try {
//             setSize(1000, 550);
//             setLayout(new BorderLayout());
//             getContentPane().setBackground(Color.WHITE);

//             // Main panel with padding
//             JPanel mainPanel = new JPanel(new BorderLayout());
//             mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//             mainPanel.setBackground(Color.WHITE);

//             // Role Name Panel
//             JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
//             formPanel.setBackground(Color.WHITE);
//             formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

//             JLabel roleLabel = new JLabel("Tên nhóm quyền:");
//             roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
//             formPanel.add(roleLabel);

//             tenField = new JTextField(75);
//             tenField.setText(nhomQuyen != null ? nhomQuyen.getTEN() : "");
//             tenField.setFont(new Font("Arial", Font.PLAIN, 14));
//             tenField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//             if (type.equals("view")) {
//                 tenField.setEditable(false);
//             }
//             formPanel.add(tenField);

//             mainPanel.add(formPanel, BorderLayout.NORTH);

//             // Permissions Table
//             String[] columnNames = { "Danh mục chức năng", "Xem", "Tạo mới", "Cập nhật", "Xóa" };
//             permissionTableModel = new DefaultTableModel(columnNames, 0) {
//                 @Override
//                 public Class<?> getColumnClass(int columnIndex) {
//                     return columnIndex == 0 ? String.class : Boolean.class;
//                 }

//                 @Override
//                 public boolean isCellEditable(int row, int column) {
//                     return column != 0 && !type.equals("view");
//                 }
//             };

//             // Populate the table with functions
//             if (danhMucChucNangList == null || danhMucChucNangList.isEmpty()) {
//                 System.out.println("Danh sách danh mục chức năng rỗng!");
//             } else {
//                 for (DanhMucChucNangDTO dm : danhMucChucNangList) {
//                     Object[] row = new Object[5];
//                     row[0] = dm.getTENCN();
//                     row[1] = false; // Xem
//                     row[2] = false; // Tạo mới
//                     row[3] = false; // Cập nhật
//                     row[4] = false; // Xóa
//                     permissionTableModel.addRow(row);
//                 }
//             }

//             // Load existing permissions if editing or viewing
//             if (nhomQuyen != null) {
//                 ArrayList<ChiTietQuyenDTO> chiTietQuyenList = nhomQuyenBUS
//                         .getChiTietQuyen(String.valueOf(nhomQuyen.getMNQ()));
//                 System.out.println("Số lượng chi tiết quyền: " + chiTietQuyenList.size()); // Debug
//                 for (ChiTietQuyenDTO ctq : chiTietQuyenList) {
//                     int mcn = ctq.getMCN();
//                     String hanhDong = ctq.getHANHDONG();
//                     System.out.println("Quyền: MCN=" + mcn + ", Hành động=" + hanhDong); // Debug
//                     for (int i = 0; i < permissionTableModel.getRowCount(); i++) {
//                         String chucNang = (String) permissionTableModel.getValueAt(i, 0);
//                         int rowMcn = danhMucChucNangList.stream()
//                                 .filter(dm -> dm.getTENCN().equals(chucNang))
//                                 .findFirst().map(DanhMucChucNangDTO::getMCN).orElse(-1);
//                         if (rowMcn == mcn) {
//                             switch (hanhDong.toLowerCase()) { // Chuẩn hóa hành động
//                                 case "view":
//                                     permissionTableModel.setValueAt(true, i, 1);
//                                     break;
//                                 case "create":
//                                     permissionTableModel.setValueAt(true, i, 2);
//                                     break;
//                                 case "update":
//                                     permissionTableModel.setValueAt(true, i, 3);
//                                     break;
//                                 case "delete":
//                                     permissionTableModel.setValueAt(true, i, 4);
//                                     break;
//                                 default:
//                                     System.out.println("Hành động không hợp lệ: " + hanhDong);
//                                     break;
//                             }
//                         }
//                     }
//                 }
//                 permissionTableModel.fireTableDataChanged(); // Làm mới bảng
//             }

//             permissionTable = new JTable(permissionTableModel) {
//                 @Override
//                 public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//                     Component c = super.prepareRenderer(renderer, row, column);
//                     c.setBackground(null); // Mặc định theo FlatLaf
//                     c.setForeground(Color.BLACK); // Màu chữ đen
//                     if (c instanceof JComponent) {
//                         ((JComponent) c).setBorder(BorderFactory.createEmptyBorder()); // Không viền
//                     }
//                     return c;
//                 }
//             };
//             permissionTable.getColumnModel().getColumn(0).setPreferredWidth(200);
//             for (int i = 1; i < permissionTable.getColumnCount(); i++) {
//                 permissionTable.getColumnModel().getColumn(i).setPreferredWidth(80);
//             }
//             permissionTable.setRowHeight(25);

//             // Căn giữa các checkbox
//             DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//             centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
//             for (int i = 1; i < permissionTable.getColumnCount(); i++) {
//                 permissionTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
//             }

//             // Căn trái cột đầu tiên (Tên chức năng)
//             DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
//             leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
//             permissionTable.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);

//             // Thêm border cho từng ô
//             permissionTable.setShowGrid(true);
//             permissionTable.setGridColor(Color.LIGHT_GRAY);
//             permissionTable.setIntercellSpacing(new Dimension(1, 1));

//             permissionTable.setShowGrid(false); // Tắt lưới
//             permissionTable.setIntercellSpacing(new Dimension(0, 0)); // Xóa khoảng cách
//             permissionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//             permissionTable.setCellSelectionEnabled(true);
//             permissionTable.setSelectionBackground(new Color(0, 0, 0, 0)); // Trong suốt
//             permissionTable.setSelectionForeground(Color.BLACK);
//             permissionTable.setFocusable(false); // Ngăn viền focus

//             // Remove borders between column headers
//             JTableHeader header = permissionTable.getTableHeader();
//             header.setBorder(BorderFactory.createEmptyBorder());
//             header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
//                 @Override
//                 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//                         boolean hasFocus, int row, int column) {
//                     Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                     c.setBackground(null); // Mặc định theo FlatLaf
//                     c.setForeground(Color.BLACK);
//                     setBorder(BorderFactory.createEmptyBorder());
//                     setHorizontalAlignment(SwingConstants.CENTER);
//                     return c;
//                 }
//             });

//             JScrollPane permissionScroll = new JScrollPane(permissionTable);
//             permissionScroll.setPreferredSize(new Dimension(500, 300));
//             permissionScroll.setBorder(BorderFactory.createEmptyBorder());
//             mainPanel.add(permissionScroll, BorderLayout.CENTER);

//             // Button Panel
//             JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
//             buttonPanel.setBackground(Color.WHITE);

//             if (!type.equals("view")) {
//                 JButton saveButton = new JButton(type.equals("create") ? "Thêm nhóm quyền" : "Cập nhật nhóm quyền");
//                 saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
//                 saveButton.setBackground(new Color(66, 139, 202));
//                 saveButton.setForeground(Color.WHITE);
//                 saveButton.setBorderPainted(false);
//                 saveButton.addActionListener(e -> saveNhomQuyen());
//                 buttonPanel.add(saveButton);
//             }

//             JButton closeButton = new JButton(type.equals("view") ? "Đóng" : "Hủy bỏ");
//             closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
//             closeButton.setBackground(new Color(217, 83, 79));
//             closeButton.setForeground(Color.WHITE);
//             closeButton.setBorderPainted(false);
//             closeButton.addActionListener(e -> dispose());
//             buttonPanel.add(closeButton);

//             add(mainPanel, BorderLayout.CENTER);
//             add(buttonPanel, BorderLayout.SOUTH);
//         } catch (Exception ex) {
//             ex.printStackTrace();
//             JOptionPane.showMessageDialog(this, "Lỗi khi khởi tạo dialog: " + ex.getMessage(), "Lỗi",
//                     JOptionPane.ERROR_MESSAGE);
//         }
//     }

//     private void saveNhomQuyen() {
//         String ten = tenField.getText();
//         if (ten.isEmpty()) {
//             JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhóm quyền!");
//             return;
//         }

//         ArrayList<ChiTietQuyenDTO> chiTietQuyenList = new ArrayList<>();
//         for (int i = 0; i < permissionTableModel.getRowCount(); i++) {
//             String chucNang = (String) permissionTableModel.getValueAt(i, 0);
//             int mcn = danhMucChucNangList.stream()
//                     .filter(dm -> dm.getTENCN().equals(chucNang))
//                     .findFirst().map(DanhMucChucNangDTO::getMCN).orElse(0);

//             if ((Boolean) permissionTableModel.getValueAt(i, 1)) {
//                 chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "view"));
//             }
//             if ((Boolean) permissionTableModel.getValueAt(i, 2)) {
//                 chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "create"));
//             }
//             if ((Boolean) permissionTableModel.getValueAt(i, 3)) {
//                 chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "update"));
//             }
//             if ((Boolean) permissionTableModel.getValueAt(i, 4)) {
//                 chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "delete"));
//             }
//         }

//         if (type.equals("create")) {
//             nhomQuyenBUS.add(ten, chiTietQuyenList);
//         } else if (type.equals("update")) {
//             nhomQuyen.setTEN(ten);
//             nhomQuyenBUS.update(nhomQuyen, chiTietQuyenList, nhomQuyenBUS.getAll().indexOf(nhomQuyen));
//         }
//         gui.loadNhomQuyenData();
//         dispose();
//     }
// }


package GUI_PHANQUYEN;

import BUS.NhomQuyenBUS;
import DTO.ChiTietQuyenDTO;
import DTO.DanhMucChucNangDTO;
import DTO.NhomQuyenDTO;
import DAO.DanhMucChucNangDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class PhanQuyenDialog extends JDialog {
    private NhomQuyenBUS nhomQuyenBUS;
    private PhanQuyenGUI gui;
    private ArrayList<DanhMucChucNangDTO> danhMucChucNangList;
    private NhomQuyenDTO nhomQuyen;
    private String type;
    private JTextField tenField;
    private DefaultTableModel permissionTableModel;
    private JTable permissionTable;

    public PhanQuyenDialog(NhomQuyenBUS nhomQuyenBUS, PhanQuyenGUI gui, JFrame owner, String title, boolean modal,
            String type, NhomQuyenDTO nhomQuyen) {
        super(owner, title, modal);
        this.nhomQuyenBUS = nhomQuyenBUS;
        this.gui = gui;
        this.danhMucChucNangList = DanhMucChucNangDAO.getInstance().selectAll();
        this.nhomQuyen = nhomQuyen;
        this.type = type;
        initComponents();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        try {
            setSize(1000, 550);
            setLayout(new BorderLayout());
            getContentPane().setBackground(Color.WHITE);

            // Main panel with padding
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            mainPanel.setBackground(Color.WHITE);

            // Role Name Panel
            JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            formPanel.setBackground(Color.WHITE);
            formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

            JLabel roleLabel = new JLabel("Tên nhóm quyền:");
            roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            formPanel.add(roleLabel);

            tenField = new JTextField(75);
            tenField.setText(nhomQuyen != null ? nhomQuyen.getTEN() : "");
            tenField.setFont(new Font("Arial", Font.PLAIN, 14));
            tenField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            if (type.equals("view")) {
                tenField.setEditable(false);
            }
            formPanel.add(tenField);

            mainPanel.add(formPanel, BorderLayout.NORTH);

            // Permissions Table
            String[] columnNames = { "Danh mục chức năng", "Xem", "Tạo mới", "Cập nhật", "Xóa" };
            permissionTableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnIndex == 0 ? String.class : Boolean.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column != 0 && !type.equals("view");
                }
            };

            // Populate the table with functions
            if (danhMucChucNangList == null || danhMucChucNangList.isEmpty()) {
                System.out.println("Danh sách danh mục chức năng rỗng!");
            } else {
                for (DanhMucChucNangDTO dm : danhMucChucNangList) {
                    Object[] row = new Object[5];
                    row[0] = dm.getTENCN();
                    row[1] = false; // Xem
                    row[2] = false; // Tạo mới
                    row[3] = false; // Cập nhật
                    row[4] = false; // Xóa
                    permissionTableModel.addRow(row);
                }
            }

            // Load existing permissions if editing or viewing
            if (nhomQuyen != null) {
                ArrayList<ChiTietQuyenDTO> chiTietQuyenList = nhomQuyenBUS
                        .getChiTietQuyen(String.valueOf(nhomQuyen.getMNQ()));
                System.out.println("Số lượng chi tiết quyền: " + chiTietQuyenList.size()); // Debug
                for (ChiTietQuyenDTO ctq : chiTietQuyenList) {
                    int mcn = ctq.getMCN();
                    String hanhDong = ctq.getHANHDONG();
                    System.out.println("Quyền: MCN=" + mcn + ", Hành động=" + hanhDong); // Debug
                    for (int i = 0; i < permissionTableModel.getRowCount(); i++) {
                        String chucNang = (String) permissionTableModel.getValueAt(i, 0);
                        int rowMcn = danhMucChucNangList.stream()
                                .filter(dm -> dm.getTENCN().equals(chucNang))
                                .findFirst().map(DanhMucChucNangDTO::getMCN).orElse(-1);
                        if (rowMcn == mcn) {
                            switch (hanhDong.toLowerCase()) { // Chuẩn hóa hành động
                                case "view":
                                    permissionTableModel.setValueAt(true, i, 1);
                                    break;
                                case "create":
                                    permissionTableModel.setValueAt(true, i, 2);
                                    break;
                                case "update":
                                    permissionTableModel.setValueAt(true, i, 3);
                                    break;
                                case "delete":
                                    permissionTableModel.setValueAt(true, i, 4);
                                    break;
                                default:
                                    System.out.println("Hành động không hợp lệ: " + hanhDong);
                                    break;
                            }
                        }
                    }
                }
                permissionTableModel.fireTableDataChanged(); // Làm mới bảng
            }

            permissionTable = new JTable(permissionTableModel) {
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                    Component c = super.prepareRenderer(renderer, row, column);
                    c.setBackground(null); // Mặc định theo FlatLaf
                    c.setForeground(Color.BLACK); // Màu chữ đen
                    if (c instanceof JComponent) {
                        ((JComponent) c).setBorder(BorderFactory.createEmptyBorder()); // Không viền
                    }
                    return c;
                }
            };
            permissionTable.getColumnModel().getColumn(0).setPreferredWidth(200);
            for (int i = 1; i < permissionTable.getColumnCount(); i++) {
                permissionTable.getColumnModel().getColumn(i).setPreferredWidth(80);
            }
            permissionTable.setRowHeight(25);

            // Căn trái cột đầu tiên (Tên chức năng)
            DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
            leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
            permissionTable.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);

            // Không đặt renderer cho cột Boolean để giữ checkbox mặc định
            permissionTable.setShowGrid(false);
            permissionTable.setIntercellSpacing(new Dimension(0, 0));
            permissionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            permissionTable.setCellSelectionEnabled(true);
            permissionTable.setSelectionBackground(new Color(0, 0, 0, 0)); // Trong suốt
            permissionTable.setSelectionForeground(Color.BLACK);
            permissionTable.setFocusable(false); // Ngăn viền focus

            // Remove borders between column headers
            JTableHeader header = permissionTable.getTableHeader();
            header.setBorder(BorderFactory.createEmptyBorder());
            header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    c.setBackground(null); // Mặc định theo FlatLaf
                    c.setForeground(Color.BLACK);
                    setBorder(BorderFactory.createEmptyBorder());
                    setHorizontalAlignment(SwingConstants.CENTER);
                    return c;
                }
            });

            JScrollPane permissionScroll = new JScrollPane(permissionTable);
            permissionScroll.setPreferredSize(new Dimension(500, 300));
            permissionScroll.setBorder(BorderFactory.createEmptyBorder());
            mainPanel.add(permissionScroll, BorderLayout.CENTER);

            // Button Panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(Color.WHITE);

            if (!type.equals("view")) {
                JButton saveButton = new JButton(type.equals("create") ? "Thêm nhóm quyền" : "Cập nhật nhóm quyền");
                saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
                saveButton.setBackground(new Color(66, 139, 202));
                saveButton.setForeground(Color.WHITE);
                saveButton.setBorderPainted(false);
                saveButton.addActionListener(e -> saveNhomQuyen());
                buttonPanel.add(saveButton);
            }

            JButton closeButton = new JButton(type.equals("view") ? "Đóng" : "Hủy bỏ");
            closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
            closeButton.setBackground(new Color(217, 83, 79));
            closeButton.setForeground(Color.WHITE);
            closeButton.setBorderPainted(false);
            closeButton.addActionListener(e -> dispose());
            buttonPanel.add(closeButton);

            add(mainPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi khởi tạo dialog: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveNhomQuyen() {
        String ten = tenField.getText();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhóm quyền!");
            return;
        }

        ArrayList<ChiTietQuyenDTO> chiTietQuyenList = new ArrayList<>();
        for (int i = 0; i < permissionTableModel.getRowCount(); i++) {
            String chucNang = (String) permissionTableModel.getValueAt(i, 0);
            int mcn = danhMucChucNangList.stream()
                    .filter(dm -> dm.getTENCN().equals(chucNang))
                    .findFirst().map(DanhMucChucNangDTO::getMCN).orElse(0);

            if ((Boolean) permissionTableModel.getValueAt(i, 1)) {
                chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "view"));
            }
            if ((Boolean) permissionTableModel.getValueAt(i, 2)) {
                chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "create"));
            }
            if ((Boolean) permissionTableModel.getValueAt(i, 3)) {
                chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "update"));
            }
            if ((Boolean) permissionTableModel.getValueAt(i, 4)) {
                chiTietQuyenList.add(new ChiTietQuyenDTO(nhomQuyen != null ? nhomQuyen.getMNQ() : 0, mcn, "delete"));
            }
        }

        if (type.equals("create")) {
            nhomQuyenBUS.add(ten, chiTietQuyenList);
        } else if (type.equals("update")) {
            nhomQuyen.setTEN(ten);
            nhomQuyenBUS.update(nhomQuyen, chiTietQuyenList, nhomQuyenBUS.getAll().indexOf(nhomQuyen));
        }
        gui.loadNhomQuyenData();
        dispose();
    }
}
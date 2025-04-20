package GUI_DATPHONG.DatPhong;

import BUS.DatPhongBUS;
import Component.*;
import DTO.DatPhongDTO;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import helper.JTableExporter;

public class DatPhongFrame extends JPanel implements ActionListener {
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable tableDatPhong;
    private JScrollPane scrollTableDatPhong;
    private SidebarPanel sidebarPanel;
    private DefaultTableModel tblModel;
    private DatPhongBUS datPhongBUS;
    private ArrayList<DatPhongDTO> listDP;
    private Color BackgroundColor = new Color(193, 237, 220);

    public DatPhongFrame() {
        datPhongBUS = new DatPhongBUS();
        listDP = datPhongBUS.selectAll();
        initComponent();
        loadDataTable(listDP);
    }

    private void initComponent() {
        this.setBackground(new Color(220, 245, 218)); // Match provided SidebarPanel background
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1300, 650)); // Match provided SidebarPanel size
    
        // Wrapper panel for function bar
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(new Color(220, 245, 218));
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 10)); // Match provided SidebarPanel padding
        wrapperPanel.setLayout(new BorderLayout());
    
        // Create SidebarPanel
        sidebarPanel = new SidebarPanel(
            e -> handleAdd(),           // Add
            e -> handleEdit(),          // Edit
            e -> handleDelete(),        // Delete
            e -> handleDetails(),       // Details
            e -> handleExport(),        // Export
            e -> handleRefresh(),       // Refresh
            createSearchDocumentListener() // Search
        );
    
        // Adjust button sizes and margins in SidebarPanel
        JPanel buttonPanel = (JPanel) sidebarPanel.getComponent(0); // Get buttonPanel (WEST)
        Component[] buttons = buttonPanel.getComponents();
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] instanceof JButton) {
                JButton button = (JButton) buttonPanel.getComponent(i);
                button.setPreferredSize(new Dimension(80, 80)); // Keep button size
                // Set font to PLAIN for "THÊM", "SỬA", "XÓA", "CHI TIẾT", "XUẤT EXCEL" (indices 0-4)
                if (i <= 4) { // Include index 4 for "Xuất Excel"
                    button.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Not bold
                    if (i < 4) { // Only first 4 buttons have no border
                        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                    }
                } else {
                    button.setFont(new Font("Times New Roman", Font.BOLD, 14)); // Bold for others (e.g., "Làm Mới")
                }
            }
        }
    
        // Adjust reset button in IntegratedSearch
        JButton resetButton = sidebarPanel.getSearchPanel().btnReset;
        resetButton.setPreferredSize(new Dimension(120, 100)); // Wider reset button
        resetButton.setFont(new Font("Times New Roman", Font.BOLD, 14)); // Keep bold for reset
    
        wrapperPanel.add(sidebarPanel, BorderLayout.CENTER);
        this.add(wrapperPanel, BorderLayout.NORTH);
    
        // Main panel for table (adapted from new initComponents)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 245, 245)); // Match new initComponents
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(240, 245, 245)); // Match new initComponents
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Table setup
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    
        tblModel = new DefaultTableModel(
            new String[] {"Mã Đặt Phòng", "Mã Khách Hàng", "Ngày Lập Phiếu", "Tiền Đặt Cọc", "Tình Trạng Xử Lý", "Xử Lý"},
            0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    
        tableDatPhong = new JTable(tblModel);
        tableDatPhong.setBackground(new Color(0xA1D6E2)); // Match new initComponents
        tableDatPhong.setFocusable(false);
        tableDatPhong.setAutoCreateRowSorter(true);
        tableDatPhong.setRowHeight(30);
        tableDatPhong.setShowGrid(false);
        tableDatPhong.setShowHorizontalLines(true);
        tableDatPhong.setShowVerticalLines(false);
        tableDatPhong.setGridColor(Color.WHITE);
    
        // Customize table header
        JTableHeader header = tableDatPhong.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
                label.setBorder(BorderFactory.createEmptyBorder());
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });
    
        // Set column alignment and widths
        TableColumnModel columnModel = tableDatPhong.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(120); // Mã Đặt Phòng
        columnModel.getColumn(1).setPreferredWidth(100); // Mã Khách Hàng
        columnModel.getColumn(2).setPreferredWidth(150); // Ngày Lập Phiếu
        columnModel.getColumn(3).setPreferredWidth(100); // Tiền Đặt Cọc
        columnModel.getColumn(4).setPreferredWidth(100); // Tình Trạng Xử Lý
        columnModel.getColumn(5).setPreferredWidth(100); // Xử Lý
    
        for (int i = 0; i < tblModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
    
        // Custom renderer for status columns (adapted for Tình Trạng Xử Lý and Xử Lý)
        tableDatPhong.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 4 || column == 5) { // Tình Trạng Xử Lý or Xử Lý
                    c.setForeground(value.toString().equals("Đã xử lý") ? Color.RED : value.toString().equals("Chưa xử lý") ? new Color(0, 128, 0) : Color.BLACK);
                } else {
                    c.setForeground(Color.BLACK);
                }
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });
    
        // Scroll pane with customized scroll bars
        scrollTableDatPhong = new JScrollPane(tableDatPhong);
        scrollTableDatPhong.getVerticalScrollBar().setPreferredSize(new Dimension(6, Integer.MAX_VALUE));
        scrollTableDatPhong.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 6));
        tablePanel.add(scrollTableDatPhong, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
    
        this.add(mainPanel, BorderLayout.CENTER);
    
        // Mouse listener for double-click
        tableDatPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tableDatPhong.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    tableDatPhong.setRowSelectionInterval(row, row);
                    if (e.getClickCount() == 2) {
                        handleDetails();
                    }
                }
            }
        });
    
        // Restore original sorting
        TableSorter.configureTableColumnSorter(tableDatPhong, 0, TableSorter.STRING_COMPARATOR); // maDP
        TableSorter.configureTableColumnSorter(tableDatPhong, 1, TableSorter.INTEGER_COMPARATOR); // maKH
        TableSorter.configureTableColumnSorter(tableDatPhong, 2, TableSorter.DATE_COMPARATOR); // ngayLapPhieu
        TableSorter.configureTableColumnSorter(tableDatPhong, 3, TableSorter.INTEGER_COMPARATOR); // tienDatCoc
        TableSorter.configureTableColumnSorter(tableDatPhong, 4, TableSorter.INTEGER_COMPARATOR); // tinhTrangXuLy
        TableSorter.configureTableColumnSorter(tableDatPhong, 5, TableSorter.INTEGER_COMPARATOR); // xuLy
    }

    private void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }

    public void loadDataTable(ArrayList<DatPhongDTO> result) {
        tblModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (DatPhongDTO dp : result) {
            tblModel.addRow(new Object[] {
                    dp.getMaDP(),
                    dp.getMaKH(),
                    dp.getNgayLapPhieu() != null ? dateFormat.format(dp.getNgayLapPhieu()) : "",
                    dp.getTienDatCoc(),
                    dp.getTinhTrangXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý",
                    dp.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý"
            });
        }
    }

    private void handleAdd() {
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(owner, "Thêm Đặt Phòng", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(owner);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input fields
        InputForm maDPForm = new InputForm("Mã Đặt Phòng");
        InputForm maKHForm = new InputForm("Mã Khách Hàng");
        InputForm tienDatCocForm = new InputForm("Tiền Đặt Cọc");
        InputForm tinhTrangXuLyForm = new InputForm("Tình Trạng Xử Lý");
        InputForm xuLyForm = new InputForm("Xử Lý");

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(maDPForm, gbc);
        gbc.gridy = 1;
        dialog.add(maKHForm, gbc);
        gbc.gridy = 2;
        dialog.add(tienDatCocForm, gbc);
        gbc.gridy = 3;
        dialog.add(tinhTrangXuLyForm, gbc);
        gbc.gridy = 4;
        dialog.add(xuLyForm, gbc);

        // Buttons
        ButtonCustom saveButton = new ButtonCustom("Lưu", "success", 14);
        ButtonCustom cancelButton = new ButtonCustom("Hủy", "danger", 14);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        gbc.gridy = 5;
        dialog.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            try {
                DatPhongDTO dp = new DatPhongDTO();
                dp.setMaDP(maDPForm.getText());
                dp.setMaKH(Integer.parseInt(maKHForm.getText()));
                dp.setTienDatCoc(Integer.parseInt(tienDatCocForm.getText()));
                dp.setTinhTrangXuLy(Integer.parseInt(tinhTrangXuLyForm.getText()));
                dp.setXuLy(Integer.parseInt(xuLyForm.getText()));
                dp.setNgayLapPhieu(new java.util.Date());

                if (datPhongBUS.checkExists(dp.getMaDP())) {
                    JOptionPane.showMessageDialog(dialog, "Mã đặt phòng đã tồn tại!");
                    return;
                }

                int result = datPhongBUS.add(dp);
                if (result > 0) {
                    JOptionPane.showMessageDialog(dialog, "Thêm đặt phòng thành công!");
                    listDP = datPhongBUS.selectAll();
                    loadDataTable(listDP);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm đặt phòng thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Dữ liệu không hợp lệ: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void handleEdit() {
        int index = getRowSelected();
        if (index == -1)
            return;

        DatPhongDTO dp = listDP.get(index);
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(owner, "Sửa Đặt Phòng", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(owner);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input fields
        InputForm maDPForm = new InputForm("Mã Đặt Phòng");
        maDPForm.setText(dp.getMaDP());
        maDPForm.setEditable(false);
        InputForm maKHForm = new InputForm("Mã Khách Hàng");
        maKHForm.setText(String.valueOf(dp.getMaKH()));
        InputForm tienDatCocForm = new InputForm("Tiền Đặt Cọc");
        tienDatCocForm.setText(String.valueOf(dp.getTienDatCoc()));
        InputForm tinhTrangXuLyForm = new InputForm("Tình Trạng Xử Lý");
        tinhTrangXuLyForm.setText(String.valueOf(dp.getTinhTrangXuLy()));
        InputForm xuLyForm = new InputForm("Xử Lý");
        xuLyForm.setText(String.valueOf(dp.getXuLy()));

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(maDPForm, gbc);
        gbc.gridy = 1;
        dialog.add(maKHForm, gbc);
        gbc.gridy = 2;
        dialog.add(tienDatCocForm, gbc);
        gbc.gridy = 3;
        dialog.add(tinhTrangXuLyForm, gbc);
        gbc.gridy = 4;
        dialog.add(xuLyForm, gbc);

        // Buttons
        ButtonCustom saveButton = new ButtonCustom("Lưu", "success", 14);
        ButtonCustom cancelButton = new ButtonCustom("Hủy", "danger", 14);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        gbc.gridy = 5;
        dialog.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            try {
                dp.setMaKH(Integer.parseInt(maKHForm.getText()));
                dp.setTienDatCoc(Integer.parseInt(tienDatCocForm.getText()));
                dp.setTinhTrangXuLy(Integer.parseInt(tinhTrangXuLyForm.getText()));
                dp.setXuLy(Integer.parseInt(xuLyForm.getText()));

                int result = datPhongBUS.update(dp);
                if (result > 0) {
                    JOptionPane.showMessageDialog(dialog, "Sửa đặt phòng thành công!");
                    listDP = datPhongBUS.selectAll();
                    loadDataTable(listDP);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Sửa đặt phòng thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Dữ liệu không hợp lệ: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void handleDelete() {
        int index = getRowSelected();
        if (index == -1)
            return;

        DatPhongDTO dp = listDP.get(index);
        int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa đặt phòng " + dp.getMaDP() + "?",
                "Xóa đặt phòng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            int result = datPhongBUS.delete(dp.getMaDP());
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Xóa đặt phòng thành công!");
                listDP = datPhongBUS.selectAll();
                loadDataTable(listDP);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa đặt phòng thất bại!");
            }
        }
    }

    private void handleDetails() {
        int index = getRowSelected();
        if (index == -1)
            return;

        DatPhongDTO dp = listDP.get(index);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String details = String.format(
                "Mã Đặt Phòng: %s\nMã Khách Hàng: %d\nNgày Lập Phiếu: %s\nTiền Đặt Cọc: %d\nTình Trạng Xử Lý: %s\nXử Lý: %s",
                dp.getMaDP(),
                dp.getMaKH(),
                dp.getNgayLapPhieu() != null ? dateFormat.format(dp.getNgayLapPhieu()) : "N/A",
                dp.getTienDatCoc(),
                dp.getTinhTrangXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý",
                dp.getXuLy() == 1 ? "Đã xử lý" : "Chưa xử lý");

        JOptionPane.showMessageDialog(this, details, "Chi Tiết Đặt Phòng", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleExport() {
        try {
            JTableExporter.exportJTableToExcel(tableDatPhong);
        } catch (IOException ex) {
            Logger.getLogger(DatPhongFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleRefresh() {
        // Xóa nội dung ô tìm kiếm
        sidebarPanel.getSearchField().setText("");
        
        // Tải lại toàn bộ dữ liệu từ BUS
        listDP = datPhongBUS.selectAll();
        
        // Cập nhật bảng
        loadDataTable(listDP);
    }

    private DocumentListener createSearchDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchTable();
            }
    
            @Override
            public void removeUpdate(DocumentEvent e) {
                searchTable();
            }
    
            @Override
            public void changedUpdate(DocumentEvent e) {
                searchTable();
            }
    
            private void searchTable() {
                String searchText = sidebarPanel.getSearchField().getText().trim().toLowerCase();
                ArrayList<DatPhongDTO> filteredList = new ArrayList<>();
    
                // Nếu ô tìm kiếm rỗng, hiển thị toàn bộ danh sách
                if (searchText.isEmpty()) {
                    filteredList.addAll(datPhongBUS.selectAll());
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    for (DatPhongDTO dp : datPhongBUS.selectAll()) {
                        // Tìm kiếm trên các trường
                        if (dp.getMaDP().toLowerCase().contains(searchText) ||
                            String.valueOf(dp.getMaKH()).contains(searchText) ||
                            String.valueOf(dp.getTienDatCoc()).contains(searchText) ||
                            (dp.getNgayLapPhieu() != null && dateFormat.format(dp.getNgayLapPhieu()).toLowerCase().contains(searchText)) ||
                            (dp.getTinhTrangXuLy() == 1 ? "đã xử lý" : "chưa xử lý").toLowerCase().contains(searchText) ||
                            (dp.getXuLy() == 1 ? "đã xử lý" : "chưa xử lý").toLowerCase().contains(searchText)) {
                            filteredList.add(dp);
                        }
                    }
                }
    
                listDP = filteredList;
                loadDataTable(listDP);
            }
        };
    }

    public int getRowSelected() {
        int index = tableDatPhong.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đặt phòng");
        }
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Actions are handled by SidebarPanel buttons
    }
}
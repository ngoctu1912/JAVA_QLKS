// package GUI_PHONG;

// import DTO.PhongDTO;
// import BUS.PhongBUS;
// import Component.SidebarPanel;
// import Component.IntegratedSearch;

// import javax.swing.*;
// import javax.swing.event.DocumentListener;
// import javax.swing.event.DocumentEvent;
// import javax.swing.plaf.basic.BasicScrollBarUI;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.table.JTableHeader;
// import javax.swing.table.DefaultTableCellRenderer;
// import javax.swing.table.TableColumnModel;
// import java.awt.*;
// import java.util.ArrayList;

// public class FormPhong extends JFrame {
//     private JTable table;
//     private PhongBUS phongBUS;
//     private ArrayList<PhongDTO> filteredRooms;
//     private DefaultTableCellRenderer centerRenderer;
//     private SidebarPanel sidebarPanel;
//     private PhongEventHandler eventHandler;
//     private DocumentListener searchListener;

//     public FormPhong() {
//         setTitle("Quản lý phòng");
//         setSize(1300, 800);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setLayout(new BorderLayout());

//         try {
//             UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         phongBUS = new PhongBUS();
//         filteredRooms = loadAllRooms();
//         logRoomData("constructor");

//         initComponents();
//         loadTableData();
//         eventHandler = new PhongEventHandler(this);
//     }

//     private ArrayList<PhongDTO> loadAllRooms() {
//         ArrayList<PhongDTO> rooms = phongBUS.getAllPhong();
//         if (rooms == null) {
//             System.out.println("Error: phongBUS.getAllPhong() returned null.");
//             return new ArrayList<>();
//         }
//         return rooms;
//     }

//     private void logRoomData(String context) {
//         if (filteredRooms.isEmpty()) {
//             System.out.println("Warning: No rooms loaded from the database in " + context + ".");
//         } else {
//             System.out.println("Successfully loaded " + filteredRooms.size() + " rooms from the database in " + context + ".");
//             int bookedCount = 0;
//             int availableCount = 0;
//             for (PhongDTO p : filteredRooms) {
//                 if (p != null) {
//                     String status = p.getTinhTrang() == 1 ? "Đã đặt" : "Trống";
//                     if (status.equals("Đã đặt")) bookedCount++;
//                     else availableCount++;
//                     System.out.println("Room: " + p.getMaP() + ", " + p.getTenP() + ", " + p.getLoaiP() + ", " + p.getGiaP() + ", Status: " + status);
//                 }
//             }
//             System.out.println(context + " - Booked: " + bookedCount + ", Available: " + availableCount);
//         }
//     }

//     private void initComponents() {
//         // Main panel
//         JPanel mainPanel = new JPanel(new BorderLayout());
//         mainPanel.setBackground(new Color(240, 245, 245));
//         mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//         // Sidebar panel
//         sidebarPanel = new SidebarPanel(
//             e -> eventHandler.openAddDialog(),
//             e -> eventHandler.openEditDialog(),
//             e -> eventHandler.deleteRoom(),
//             e -> openDetailDialog(),
//             e -> eventHandler.exportToExcel(),
//             e -> loadTableData(),
//             getSearchListener()
//         );
//         mainPanel.add(sidebarPanel, BorderLayout.NORTH);

//         // Table panel
//         JPanel tablePanel = new JPanel(new BorderLayout());
//         tablePanel.setBackground(new Color(240, 245, 245));
//         tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//         centerRenderer = new DefaultTableCellRenderer();
//         centerRenderer.setHorizontalAlignment(JLabel.CENTER);

//         DefaultTableModel tableModel = new DefaultTableModel(
//             new String[]{"STT", "Mã Phòng", "Tên Phòng", "Loại Phòng", "Giá Phòng", "Chi Tiết Loại Phòng", "Tình Trạng"}, 0
//         ) {
//             @Override
//             public boolean isCellEditable(int row, int column) {
//                 return false;
//             }
//         };

//         table = new JTable(tableModel);
//         table.setBackground(new Color(0xA1D6E2));
//         table.setFocusable(false);
//         table.setAutoCreateRowSorter(true);
//         table.setRowHeight(30);
//         table.setShowGrid(false);
//         table.setShowHorizontalLines(true);
//         table.setShowVerticalLines(false);
//         table.setGridColor(Color.WHITE);

//         // Customize table header
//         JTableHeader header = table.getTableHeader();
//         header.setBackground(Color.WHITE);
//         header.setForeground(Color.BLACK);
//         header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));
//         header.setDefaultRenderer(new DefaultTableCellRenderer() {
//             @Override
//             public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                 JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                 label.setBackground(Color.WHITE);
//                 label.setForeground(Color.BLACK);
//                 label.setBorder(BorderFactory.createEmptyBorder());
//                 label.setHorizontalAlignment(JLabel.CENTER);
//                 return label;
//             }
//         });

//         // Set column alignment and widths
//         TableColumnModel columnModel = table.getColumnModel();
//         columnModel.getColumn(0).setPreferredWidth(50);
//         columnModel.getColumn(1).setPreferredWidth(100);
//         columnModel.getColumn(2).setPreferredWidth(180);
//         columnModel.getColumn(3).setPreferredWidth(100);
//         columnModel.getColumn(4).setPreferredWidth(100);
//         columnModel.getColumn(5).setPreferredWidth(200);
//         columnModel.getColumn(6).setPreferredWidth(100);

//         for (int i = 0; i < tableModel.getColumnCount(); i++) {
//             if (i != 2) {
//                 columnModel.getColumn(i).setCellRenderer(centerRenderer);
//             }
//         }

//         // Custom renderer for status column
//         table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//             @Override
//             public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                 Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                 if (column == 6) {
//                     c.setForeground(value.toString().equals("Đã đặt") ? Color.RED : value.toString().equals("Trống") ? new Color(0, 128, 0) : Color.BLACK);
//                 } else {
//                     c.setForeground(Color.BLACK);
//                 }
//                 ((JLabel) c).setHorizontalAlignment(column == 2 ? JLabel.LEFT : JLabel.CENTER);
//                 return c;
//             }
//         });

//         // Scroll pane with customized scroll bars
//         JScrollPane scrollPane = new JScrollPane(table);
//         customizeScrollBar(scrollPane.getVerticalScrollBar());
//         customizeScrollBar(scrollPane.getHorizontalScrollBar());
//         tablePanel.add(scrollPane, BorderLayout.CENTER);
//         mainPanel.add(tablePanel, BorderLayout.CENTER);
//         add(mainPanel, BorderLayout.CENTER);

//         // Mouse listener for double-click
//         table.addMouseListener(new java.awt.event.MouseAdapter() {
//             @Override
//             public void mouseClicked(java.awt.event.MouseEvent e) {
//                 int row = table.rowAtPoint(e.getPoint());
//                 if (row >= 0) {
//                     table.setRowSelectionInterval(row, row);
//                     if (e.getClickCount() == 2) {
//                         openDetailDialog();
//                     }
//                 }
//             }
//         });
//     }

//     private void customizeScrollBar(JScrollBar scrollBar) {
//         scrollBar.setUI(new BasicScrollBarUI() {
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
//                 // Minimal track rendering
//             }
//         });
//         scrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));
//     }

//     private DocumentListener getSearchListener() {
//         if (searchListener == null) {
//             searchListener = new DocumentListener() {
//                 @Override
//                 public void insertUpdate(DocumentEvent e) {
//                     performSearch();
//                 }

//                 @Override
//                 public void removeUpdate(DocumentEvent e) {
//                     performSearch();
//                 }

//                 @Override
//                 public void changedUpdate(DocumentEvent e) {
//                     performSearch();
//                 }
//             };
//         }
//         return searchListener;
//     }

//     private void performSearch() {
//         String keyword = sidebarPanel.getSearchField().getText().trim().toLowerCase();
//         filteredRooms = loadAllRooms();

//         if (!keyword.isEmpty() && !keyword.equals("nhập nội dung tìm kiếm...")) {
//             ArrayList<PhongDTO> temp = new ArrayList<>();
//             for (PhongDTO p : filteredRooms) {
//                 if (p == null) continue;
//                 boolean matchesKeyword =
//                     (p.getMaP() != null && p.getMaP().toLowerCase().contains(keyword)) ||
//                     (p.getTenP() != null && p.getTenP().toLowerCase().contains(keyword)) ||
//                     (p.getLoaiP() != null && p.getLoaiP().toLowerCase().contains(keyword)) ||
//                     String.valueOf(p.getGiaP()).toLowerCase().contains(keyword) ||
//                     (p.getChiTietLoaiPhong() != null && p.getChiTietLoaiPhong().toLowerCase().contains(keyword)) ||
//                     (p.getTinhTrang() == 1 && "đã đặt".contains(keyword)) ||
//                     (p.getTinhTrang() == 0 && "trống".contains(keyword));
//                 if (matchesKeyword) {
//                     temp.add(p);
//                 }
//             }
//             filteredRooms = temp;
//             System.out.println("Searched rooms (Keyword: " + keyword + "): " + filteredRooms.size());
//         }

//         logRoomData("performSearch");
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
//                 String status = p.getTinhTrang() == 1 ? "Đã đặt" : "Trống";
//                 model.addRow(new Object[]{
//                     stt++,
//                     p.getMaP(),
//                     p.getTenP(),
//                     p.getLoaiP(),
//                     p.getGiaP() + "đ",
//                     p.getChiTietLoaiPhong(),
//                     status
//                 });
//             }
//         }
//         System.out.println("Table updated with " + (stt - 1) + " rows.");
//     }

//     public void loadTableData() {
//         filteredRooms = loadAllRooms();
//         logRoomData("loadTableData");

//         JTextField searchField = sidebarPanel.getSearchField();
//         searchField.getDocument().removeDocumentListener(searchListener);
//         searchField.setText("");
//         searchField.getDocument().addDocumentListener(searchListener);

//         updateTable();
//     }

//     private void openDetailDialog() {
//         int row = table.getSelectedRow();
//         if (row >= 0) {
//             String maP = (String) table.getValueAt(row, 1);
//             PhongDTO room = phongBUS.getPhongById(maP);
//             if (room != null) {
//                 // Open PhongDialog in view mode (isEditMode = false, isViewMode = true)
//                 new PhongDialog(this, room, false, true).setVisible(true);
//             } else {
//                 JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//             }
//         }
//     }

//     public JTable getTable() { return table; }
//     public SidebarPanel getSidebarPanel() { return sidebarPanel; }
//     public PhongBUS getPhongBUS() { return phongBUS; }
//     public ArrayList<PhongDTO> getFilteredRooms() { return filteredRooms; }
//     public void setFilteredRooms(ArrayList<PhongDTO> rooms) { this.filteredRooms = rooms; }

//     public static void main(String[] args) {
//         try {
//             UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         SwingUtilities.invokeLater(() -> new FormPhong().setVisible(true));
//     }
// }

package GUI_PHONG;

import DTO.PhongDTO;
import BUS.PhongBUS;
import Component.SidebarPanel;
import Component.IntegratedSearch;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class FormPhong extends JPanel {
    private JTable table;
    private PhongBUS phongBUS;
    private ArrayList<PhongDTO> filteredRooms;
    private DefaultTableCellRenderer centerRenderer;
    private SidebarPanel sidebarPanel;
    private PhongEventHandler eventHandler;
    private DocumentListener searchListener;

    public FormPhong() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 245));

        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        phongBUS = new PhongBUS();
        filteredRooms = loadAllRooms();
        logRoomData("constructor");

        initComponents();
        loadTableData();
        eventHandler = new PhongEventHandler(this);
    }

    private ArrayList<PhongDTO> loadAllRooms() {
        ArrayList<PhongDTO> rooms = phongBUS.getAllPhong();
        if (rooms == null) {
            System.out.println("Error: phongBUS.getAllPhong() returned null.");
            return new ArrayList<>();
        }
        return rooms;
    }

    private void logRoomData(String context) {
        if (filteredRooms.isEmpty()) {
            System.out.println("Warning: No rooms loaded from the database in " + context + ".");
        } else {
            System.out.println(
                    "Successfully loaded " + filteredRooms.size() + " rooms from the database in " + context + ".");
            int bookedCount = 0;
            int availableCount = 0;
            for (PhongDTO p : filteredRooms) {
                if (p != null) {
                    String status = p.getTinhTrang() == 1 ? "Đã đặt" : "Trống";
                    if (status.equals("Đã đặt"))
                        bookedCount++;
                    else
                        availableCount++;
                    System.out.println("Room: " + p.getMaP() + ", " + p.getTenP() + ", " + p.getLoaiP() + ", "
                            + p.getGiaP() + ", Status: " + status);
                }
            }
            System.out.println(context + " - Booked: " + bookedCount + ", Available: " + availableCount);
        }
    }

    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Sidebar panel
        sidebarPanel = new SidebarPanel(
                e -> eventHandler.openAddDialog(),
                e -> eventHandler.openEditDialog(),
                e -> eventHandler.deleteRoom(),
                e -> openDetailDialog(),
                e -> eventHandler.exportToExcel(),
                e -> loadTableData(),
                getSearchListener());
        mainPanel.add(sidebarPanel, BorderLayout.NORTH);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(240, 245, 245));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableModel tableModel = new DefaultTableModel(
                new String[] { "STT", "Mã Phòng", "Tên Phòng", "Loại Phòng", "Giá Phòng", "Chi Tiết Loại Phòng",
                        "Tình Trạng" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setBackground(new Color(0xA1D6E2));
        table.setFocusable(false);
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(Color.WHITE);

        // Customize table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
                label.setBorder(BorderFactory.createEmptyBorder());
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });

        // Set column alignment and widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(180);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(200);
        columnModel.getColumn(6).setPreferredWidth(100);

        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        // Custom renderer for status column
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 6) {
                    c.setForeground(value.toString().equals("Đã đặt") ? Color.RED
                            : value.toString().equals("Trống") ? new Color(0, 128, 0) : Color.BLACK);
                } else {
                    c.setForeground(Color.BLACK);
                }
                ((JLabel) c).setHorizontalAlignment(column == 2 ? JLabel.LEFT : JLabel.CENTER);
                return c;
            }
        });

        // Scroll pane with customized scroll bars
        JScrollPane scrollPane = new JScrollPane(table);
        customizeScrollBar(scrollPane.getVerticalScrollBar());
        customizeScrollBar(scrollPane.getHorizontalScrollBar());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Mouse listener for double-click
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    table.setRowSelectionInterval(row, row);
                    if (e.getClickCount() == 2) {
                        openDetailDialog();
                    }
                }
            }
        });
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
                // Minimal track rendering
            }
        });
        scrollBar.setPreferredSize(new Dimension(6, Integer.MAX_VALUE));
    }

    private DocumentListener getSearchListener() {
        if (searchListener == null) {
            searchListener = new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    performSearch();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    performSearch();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    performSearch();
                }
            };
        }
        return searchListener;
    }

    private void performSearch() {
        String keyword = sidebarPanel.getSearchField().getText().trim().toLowerCase();
        filteredRooms = loadAllRooms();

        if (!keyword.isEmpty() && !keyword.equals("nhập nội dung tìm kiếm...")) {
            ArrayList<PhongDTO> temp = new ArrayList<>();
            for (PhongDTO p : filteredRooms) {
                if (p == null)
                    continue;
                boolean matchesKeyword = (p.getMaP() != null && p.getMaP().toLowerCase().contains(keyword)) ||
                        (p.getTenP() != null && p.getTenP().toLowerCase().contains(keyword)) ||
                        (p.getLoaiP() != null && p.getLoaiP().toLowerCase().contains(keyword)) ||
                        String.valueOf(p.getGiaP()).toLowerCase().contains(keyword) ||
                        (p.getChiTietLoaiPhong() != null && p.getChiTietLoaiPhong().toLowerCase().contains(keyword)) ||
                        (p.getTinhTrang() == 1 && "đã đặt".contains(keyword)) ||
                        (p.getTinhTrang() == 0 && "trống".contains(keyword));
                if (matchesKeyword) {
                    temp.add(p);
                }
            }
            filteredRooms = temp;
            System.out.println("Searched rooms (Keyword: " + keyword + "): " + filteredRooms.size());
        }

        logRoomData("performSearch");
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
                String status = p.getTinhTrang() == 1 ? "Đã đặt" : "Trống";
                model.addRow(new Object[] {
                        stt++,
                        p.getMaP(),
                        p.getTenP(),
                        p.getLoaiP(),
                        p.getGiaP() + "đ",
                        p.getChiTietLoaiPhong(),
                        status
                });
            }
        }
        System.out.println("Table updated with " + (stt - 1) + " rows.");
    }

    public void loadTableData() {
        filteredRooms = loadAllRooms();
        logRoomData("loadTableData");

        JTextField searchField = sidebarPanel.getSearchField();
        searchField.getDocument().removeDocumentListener(searchListener);
        searchField.setText("");
        searchField.getDocument().addDocumentListener(searchListener);

        updateTable();
    }

    private void openDetailDialog() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String maP = (String) table.getValueAt(row, 1);
            PhongDTO room = phongBUS.getPhongById(maP);
            if (room != null) {
                // Open PhongDialog in view mode (isEditMode = false, isViewMode = true)
                new PhongDialog(SwingUtilities.getWindowAncestor(this), room, false, true).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin phòng!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JTable getTable() {
        return table;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public PhongBUS getPhongBUS() {
        return phongBUS;
    }

    public ArrayList<PhongDTO> getFilteredRooms() {
        return filteredRooms;
    }

    public void setFilteredRooms(ArrayList<PhongDTO> rooms) {
        this.filteredRooms = rooms;
    }
}
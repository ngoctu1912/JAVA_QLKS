// package GUI_DATPHONG.DatPhong;

// import BUS.DatPhongBUS;
// import Component.*;
// import DTO.DatPhongDTO;
// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import javax.swing.table.DefaultTableModel;

// import java.awt.*;
// import java.util.ArrayList;

// public class DatPhongFrame extends JPanel {
//     private PanelBorderRadius main, functionBar;
//     private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
//     private JTable tableDatPhong;
//     private JScrollPane scrollTableDatPhong;
//     private SidebarPanel sidebarPanel;
//     private IntegratedSearch search;
//     private DefaultTableModel tblModel;
//     private DatPhongBUS datPhongBUS;
//     private ArrayList<DatPhongDTO> listDP;
//     private Color BackgroundColor = new Color(193, 237, 220);
//     private DatPhongUIComponents uiComponents;
//     private DatPhongEventHandler eventHandler;

//     public DatPhongFrame() {
//         datPhongBUS = new DatPhongBUS();
//         listDP = datPhongBUS.selectAll();
//         uiComponents = new DatPhongUIComponents(this);
//         eventHandler = new DatPhongEventHandler(this, datPhongBUS, listDP);
//         initComponent();
//         eventHandler.loadDataTable(listDP);
//     }

//     private void initComponent() {
//         this.setBackground(new Color(220, 245, 218));
//         this.setLayout(new BorderLayout());
//         this.setPreferredSize(new Dimension(1300, 650));

//         // Initialize UI components
//         tableDatPhong = uiComponents.createTable();
//         scrollTableDatPhong = new JScrollPane(tableDatPhong);
//         tblModel = (DefaultTableModel) tableDatPhong.getModel();
//         scrollTableDatPhong.setBackground(Color.WHITE);
//         scrollTableDatPhong.getVerticalScrollBar().setPreferredSize(new Dimension(6, Integer.MAX_VALUE));
//         scrollTableDatPhong.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 6));

//         initPadding();

//         contentCenter = new JPanel();
//         contentCenter.setBackground(new Color(240, 245, 245));
//         contentCenter.setLayout(new BorderLayout(10, 10));
//         this.add(contentCenter, BorderLayout.CENTER);

//         // Function bar with SidebarPanel and IntegratedSearch
//         functionBar = new PanelBorderRadius();
//         functionBar.setPreferredSize(new Dimension(0, 100));
//         functionBar.setLayout(new GridLayout(1, 2, 50, 0));
//         functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

//         // Initialize SidebarPanel and Search
//         sidebarPanel = uiComponents.createSidebarPanel(eventHandler);
//         search = uiComponents.createSearchComponent(eventHandler);
//         functionBar.add(sidebarPanel);
//         functionBar.add(search);

//         contentCenter.add(functionBar, BorderLayout.NORTH);

//         // Main panel containing the table
//         main = new PanelBorderRadius();
//         BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
//         main.setLayout(boxly);
//         main.setBorder(new EmptyBorder(0, 0, 0, 0));
//         contentCenter.add(main, BorderLayout.CENTER);
//         main.add(scrollTableDatPhong);

//         // Mouse listener for double-click
//         tableDatPhong.addMouseListener(eventHandler.getDoubleClickListener());
//     }

//     private void initPadding() {
//         pnlBorder1 = new JPanel();
//         pnlBorder1.setPreferredSize(new Dimension(0, 10));
//         pnlBorder1.setBackground(BackgroundColor);
//         this.add(pnlBorder1, BorderLayout.NORTH);

//         pnlBorder2 = new JPanel();
//         pnlBorder2.setPreferredSize(new Dimension(0, 10));
//         pnlBorder2.setBackground(BackgroundColor);
//         this.add(pnlBorder2, BorderLayout.SOUTH);

//         pnlBorder3 = new JPanel();
//         pnlBorder3.setPreferredSize(new Dimension(10, 0));
//         pnlBorder3.setBackground(BackgroundColor);
//         this.add(pnlBorder3, BorderLayout.EAST);

//         pnlBorder4 = new JPanel();
//         pnlBorder4.setPreferredSize(new Dimension(10, 0));
//         pnlBorder4.setBackground(BackgroundColor);
//         this.add(pnlBorder4, BorderLayout.WEST);
//     }

//     // Getters for use in other classes
//     public JTable getTableDatPhong() {
//         return tableDatPhong;
//     }

//     public DefaultTableModel getTableModel() {
//         return tblModel;
//     }

//     public ArrayList<DatPhongDTO> getListDP() {
//         return listDP;
//     }

//     public void setListDP(ArrayList<DatPhongDTO> listDP) {
//         this.listDP = listDP;
//     }

//     public IntegratedSearch getSearch() {
//         return search;
//     }
// }

package GUI_DATPHONG.DatPhong;

import BUS.DatPhongBUS;
import Component.*;
import DTO.DatPhongDTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;

public class DatPhongFrame extends JPanel {
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable tableDatPhong;
    private JScrollPane scrollTableDatPhong;
    private SidebarPanel sidebarPanel;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private DatPhongBUS datPhongBUS;
    private ArrayList<DatPhongDTO> listDP;
    private Color BackgroundColor = new Color(240, 245, 245); // Giống FormPhong
    private DatPhongUIComponents uiComponents;
    private DatPhongEventHandler eventHandler;

    public DatPhongFrame() {
        datPhongBUS = new DatPhongBUS();
        listDP = datPhongBUS.selectAll();
        uiComponents = new DatPhongUIComponents(this);
        eventHandler = new DatPhongEventHandler(this, datPhongBUS, listDP);
        initComponent();
        eventHandler.loadDataTable(listDP);
    }

    private void initComponent() {
        this.setBackground(BackgroundColor); // Giống FormPhong
        this.setLayout(new BorderLayout(0, 0)); // Giống FormPhong
        this.setOpaque(true); // Giống FormPhong

        // Initialize UI components
        tableDatPhong = uiComponents.createTable();
        scrollTableDatPhong = new JScrollPane(tableDatPhong);
        tblModel = (DefaultTableModel) tableDatPhong.getModel();
        scrollTableDatPhong.setBackground(Color.WHITE); // Giống FormPhong

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor); // Giống FormPhong
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // Function bar with SidebarPanel and IntegratedSearch
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Initialize SidebarPanel and Search
        sidebarPanel = uiComponents.createSidebarPanel(eventHandler);
        search = uiComponents.createSearchComponent(eventHandler);
        functionBar.add(sidebarPanel);
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        // Main panel containing the table
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableDatPhong);

        // Mouse listener for double-click
        tableDatPhong.addMouseListener(eventHandler.getDoubleClickListener());
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

    // Getters for use in other classes
    public JTable getTableDatPhong() {
        return tableDatPhong;
    }

    public DefaultTableModel getTableModel() {
        return tblModel;
    }

    public ArrayList<DatPhongDTO> getListDP() {
        return listDP;
    }

    public void setListDP(ArrayList<DatPhongDTO> listDP) {
        this.listDP = listDP;
    }

    public IntegratedSearch getSearch() {
        return search;
    }
}
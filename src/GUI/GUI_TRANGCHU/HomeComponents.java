// package GUI_TRANGCHU;

// import GUI_PHONG.PhongGUI;
// import GUI_MENU.MenuViewFrame;
// import javax.swing.JPanel;

// import GUI_DATPHONG.FindRoom;

// import java.awt.CardLayout;
// import java.util.HashMap;

// public class HomeComponents {
//     private JPanel contentPanel;
//     private JPanel menuPanel;
//     private CardLayout cardLayout;
//     private HashMap<String, JPanel> panels;

//     public HomeComponents(HomeFrame homeFrame) {
//         initComponents(homeFrame);
//     }

//     private void initComponents(HomeFrame homeFrame) {
//         panels = new HashMap<>();

//         // Khởi tạo CardLayout cho nội dung
//         cardLayout = new CardLayout();
//         contentPanel = new JPanel(cardLayout);

//         // Thêm các panel nội dung
//         JPanel trangChuContent = new MainContentFrame();
//         JPanel phongContent = new PhongGUI();
//         JPanel datphongContent = new FindRoom();

//         panels.put("TrangChuGUI", trangChuContent);
//         panels.put("PhongGUI", phongContent);
//         panels.put("DatPhongGUI", phongContent);

//         contentPanel.add(trangChuContent, "TrangChuGUI");
//         contentPanel.add(phongContent, "PhongGUI");
//         contentPanel.add(datphongContent, "DatPhongGUI");

//         // Thêm thanh menu
//         menuPanel = new MenuViewFrame(homeFrame); 
//     }

//     // Getter để truy cập các thành phần
//     public JPanel getContentPanel() {
//         return contentPanel;
//     }

//     public JPanel getMenuPanel() {
//         return menuPanel;
//     }

//     // Phương thức để chuyển đổi panel
//     public void showPanel(String panelName) {
//         if (panels.containsKey(panelName)) {
//             cardLayout.show(contentPanel, panelName);
//         } else {
//             System.err.println("Error: Panel " + panelName + " not found");
//         }
//     }
// }

package GUI_TRANGCHU;

import GUI_MENU.MenuViewFrame;
import javax.swing.JPanel;
import GUI_DATPHONG.FindRoom;
import java.awt.CardLayout;
import java.util.HashMap;

public class HomeComponents {
    private JPanel contentPanel;
    private JPanel menuPanel;
    private CardLayout cardLayout;
    private HashMap<String, JPanel> panels;

    public HomeComponents(HomeFrame homeFrame) {
        initComponents(homeFrame);
    }

    private void initComponents(HomeFrame homeFrame) {
        panels = new HashMap<>();

        // Khởi tạo CardLayout cho nội dung
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Thêm các panel nội dung
        JPanel trangChuContent = new MainContentFrame();
        JPanel datphongContent = new FindRoom();

        panels.put("TrangChu", trangChuContent);
        panels.put("DatPhong", datphongContent);

        contentPanel.add(trangChuContent, "TrangChu");
        contentPanel.add(datphongContent, "DatPhong");

        // Thêm thanh menu
        menuPanel = new MenuViewFrame(homeFrame); 
    }

    // Getter để truy cập các thành phần
    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    // Phương thức để chuyển đổi panel
    public void showPanel(String panelName) {
        if (panels.containsKey(panelName)) {
            cardLayout.show(contentPanel, panelName);
        } else {
            System.err.println("Error: Panel " + panelName + " not found");
        }
    }
}
package GUI_PHANQUYEN;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

import com.formdev.flatlaf.extras.FlatSVGIcon;

public class SidebarPanel extends JPanel {
    private IntegratedSearch searchPanel;
    public SidebarPanel(ActionListener addListener, ActionListener editListener,
                       ActionListener deleteListener, ActionListener detailsListener,
                       ActionListener exportListener, ActionListener refreshListener,
                       DocumentListener searchListener) {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1300, 100)); // Reduced height to fit smaller search bar

        // Button Panel (Left)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        // Add Button
        JButton addButton = createButton("THÊM", "icons/add.svg", addListener);
        JButton editButton = createButton("SỬA", "icons/edit.svg", editListener);
        JButton deleteButton = createButton("XÓA", "icons/delete.svg", deleteListener);
        JButton detailsButton = createButton("CHI TIẾT", "icons/detail.svg", detailsListener);
        JButton exportButton = createButton("XUẤT EXCEL", "icons/export_excel.svg", exportListener);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(exportButton);

        // Search Panel (Right)
        searchPanel = new IntegratedSearch();
        searchPanel.btnReset.addActionListener(refreshListener);

        // Add components to main panel
        add(buttonPanel, BorderLayout.WEST);
        add(searchPanel, BorderLayout.EAST);
    }

    private JButton createButton(String text, String iconPath, ActionListener listener) {
        JButton button = new JButton(text);
        button.setIcon(new FlatSVGIcon(iconPath));
        button.setForeground(Color.BLACK);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setBorder(null);
        button.setPreferredSize(new Dimension(80, 80)); // Keep button size unchanged
        button.addActionListener(listener);
        return button;
    }

    public JTextField getSearchField() {
        return searchPanel.getSearchField();
    }
}
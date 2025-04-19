package GUI_PHANQUYEN;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class IntegratedSearch extends JPanel {

    public JButton btnReset;
    public JTextField txtSearchForm;

    public IntegratedSearch() {
        initComponent();
    }

    private void initComponent() {
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(5, 15, 5, 15)); // Reduced padding
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // Use BoxLayout for precise control

        // TextField
        txtSearchForm = new JTextField("Nhập nội dung tìm kiếm...");
        txtSearchForm.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 12));
        txtSearchForm.setPreferredSize(new Dimension(200, 30));
        txtSearchForm.setMinimumSize(new Dimension(200, 30));
        txtSearchForm.setMaximumSize(new Dimension(200, 30));
        txtSearchForm.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtSearchForm.getText().equals("Nhập nội dung tìm kiếm...")) {
                    txtSearchForm.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtSearchForm.getText().isEmpty()) {
                    txtSearchForm.setText("Nhập nội dung tìm kiếm...");
                }
            }
        });

        add(txtSearchForm);
        add(Box.createHorizontalStrut(10)); // Space between components

        // Reset Button
        btnReset = new JButton("Làm mới");
        btnReset.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 12));
        btnReset.setIcon(new FlatSVGIcon("icons/refresh.svg"));
        btnReset.setPreferredSize(new Dimension(120, 30));
        btnReset.setMinimumSize(new Dimension(120, 30));
        btnReset.setMaximumSize(new Dimension(120, 30));
        btnReset.addActionListener(e -> {
            txtSearchForm.setText("Nhập nội dung tìm kiếm...");
        });
        add(btnReset);
    }

    public JTextField getSearchField() {
        return txtSearchForm;
    }
}
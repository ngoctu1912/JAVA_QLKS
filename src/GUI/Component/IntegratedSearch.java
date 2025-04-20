package Component;

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
        this.setBackground(Color.WHITE);
        BoxLayout bx = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(bx);

        JPanel jpSearch = new JPanel(new BorderLayout(5, 10));
        jpSearch.setBorder(new EmptyBorder(18, 15, 18, 15));
        jpSearch.setBackground(Color.WHITE);

        txtSearchForm = new JTextField();
        txtSearchForm.setFont(new Font(FlatRobotoFont.FAMILY, 0, 13));
        txtSearchForm.putClientProperty("JTextField.placeholderText", "Nhập nội dung tìm kiếm...");
        txtSearchForm.putClientProperty("JTextField.showClearButton", true);
        txtSearchForm.setPreferredSize(new Dimension(200, 30)); // Tăng chiều dài ô tìm kiếm
        txtSearchForm.setMaximumSize(new Dimension(300, 30));  // Giới hạn chiều dài tối đa
        jpSearch.add(txtSearchForm, BorderLayout.CENTER);

        btnReset = new JButton("Làm mới");
        btnReset.setFont(new Font(FlatRobotoFont.FAMILY, 0, 14));
        btnReset.setIcon(new FlatSVGIcon("icons/refresh.svg"));
        btnReset.setPreferredSize(new Dimension(125, 30)); // Giữ nguyên kích thước nút
        jpSearch.add(btnReset, BorderLayout.EAST);

        this.add(jpSearch);
    }

    public JTextField getSearchField() {
        return txtSearchForm;
    }
}


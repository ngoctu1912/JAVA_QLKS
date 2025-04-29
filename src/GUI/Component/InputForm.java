package Component;

import javax.swing.*;
import java.awt.*;

public class InputForm extends JPanel {
    private JLabel lblTitle;
    private JTextField txtForm;

    public InputForm(String title) {
        initComponents(title);
    }

    private void initComponents(String title) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label
        lblTitle = new JLabel(title + ":");
        Font timesNewRomanBold = Font.getFont("Times New Roman");
        if (timesNewRomanBold != null) {
            lblTitle.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lblTitle.setFont(new Font(Font.SERIF, Font.BOLD, 20)); // Fallback to Serif if Times New Roman is not available
        }
        lblTitle.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        add(lblTitle, gbc);

        // Text field
        txtForm = new JTextField();
        Font timesNewRomanPlain = Font.getFont("Times New Roman");
        if (timesNewRomanPlain != null) {
            txtForm.setFont(new Font(timesNewRomanPlain.getFamily(), Font.PLAIN, 20));
        } else {
            txtForm.setFont(new Font(Font.SERIF, Font.PLAIN, 20)); // Fallback to Serif
        }
        txtForm.setForeground(new Color(51, 51, 51));
        txtForm.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        txtForm.setBackground(Color.WHITE);
        txtForm.setPreferredSize(new Dimension(200, 32));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        add(txtForm, gbc);
    }

    public void setText(String text) {
        txtForm.setText(text);
    }

    public String getText() {
        return txtForm.getText();
    }

    public void setEditable(boolean editable) {
        txtForm.setEditable(editable);
    }

    public JTextField getTxtForm() {
        return txtForm;
    }
}
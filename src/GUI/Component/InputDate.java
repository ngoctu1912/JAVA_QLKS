package Component;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class InputDate extends JPanel {

    JLabel lbltitle;
    public JDateChooser date;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    private final SimpleDateFormat dateFormat;

    public InputDate(String title) {
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        lbltitle = new JLabel(title);
        // Định dạng font, cỡ chữ, kiểu chữ cho nhãn
        Font timesNewRomanBold = Font.getFont("Times New Roman");
        if (timesNewRomanBold != null) {
            lbltitle.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lbltitle.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        }
        lbltitle.setForeground(new Color(0, 102, 153)); // Màu xanh đậm
        date = new JDateChooser();
        date.setDateFormatString("dd/MM/yyyy");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.add(lbltitle);
        this.add(date);
    }

    public InputDate(String title, int w, int h) {
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(w, h));
        lbltitle = new JLabel(title);
        // Định dạng font, cỡ chữ, kiểu chữ cho nhãn
        Font timesNewRomanBold = Font.getFont("Times New Roman");
        if (timesNewRomanBold != null) {
            lbltitle.setFont(new Font(timesNewRomanBold.getFamily(), Font.BOLD, 20));
        } else {
            lbltitle.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        }
        lbltitle.setForeground(new Color(0, 102, 153)); // Màu xanh đậm
        date = new JDateChooser();
        date.setDateFormatString("dd/MM/yyyy");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.add(lbltitle);
        this.add(date);
    }

    public JDateChooser getDateChooser() {
        return this.date;
    }

    public Date getDate() throws ParseException {
        Date dt = date.getDate();
        if (dt != null) {
            String txt_date = dateFormat.format(dt);
            return dateFormat.parse(txt_date);
        } else {
            return null;
        }
    }

    public void setDate(JDateChooser date) {
        this.date = date;
    }

    public void setDate(Date date) {
        this.date.setDate(date);
    }

    public void setDisable() {
        JTextFieldDateEditor editor = (JTextFieldDateEditor) date.getDateEditor();
        editor.setEditable(false);
    }
}

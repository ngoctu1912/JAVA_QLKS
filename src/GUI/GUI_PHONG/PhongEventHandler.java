package GUI_PHONG;

import BUS.PhongBUS;
import DTO.PhongDTO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhongEventHandler {
    private FormPhong form;
    private PhongBUS phongBUS;

    public PhongEventHandler(FormPhong form) {
        this.form = form;
        this.phongBUS = new PhongBUS();

        // Gắn sự kiện bấm đúp vào bảng để mở chi tiết phòng
        form.getTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Bấm đúp
                    int row = form.getTable().getSelectedRow();
                    if (row >= 0) {
                        String maP = form.getTable().getValueAt(row, 0).toString();
                        PhongDTO room = phongBUS.getPhongById(maP);
                        if (room != null) {
                            new PhongDetailDialog((JFrame) SwingUtilities.getWindowAncestor(form.getTable()), room).setVisible(true);
                        }
                    }
                }
            }
        });
    }
}
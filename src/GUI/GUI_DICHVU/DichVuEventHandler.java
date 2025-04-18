package GUI_DICHVU;

import BUS.DichVuBUS;
import DTO.DichVuDTO;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DichVuEventHandler {
    private FormDichVu form;
    private DichVuBUS dichVuBUS;

    public DichVuEventHandler(FormDichVu form) {
        this.form = form;
        this.dichVuBUS = new DichVuBUS();

        // Gắn sự kiện bấm đúp vào bảng để mở chi tiết dịch vụ
        form.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Bấm đúp
                    int row = form.getTable().getSelectedRow();
                    if (row >= 0) {
                        String maDV = form.getTable().getValueAt(row, 1).toString(); // MaDV in column 1
                        DichVuDTO dichVu = dichVuBUS.getDichVuById(maDV);
                        if (dichVu != null) {
                            new DichVuDetailDialog((JFrame) SwingUtilities.getWindowAncestor(form.getTable()), dichVu).setVisible(true);
                        }
                    }
                }
            }
        });
    }
}
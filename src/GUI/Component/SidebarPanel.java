package Component;

import BUS.NhomQuyenBUS;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public final class SidebarPanel extends JToolBar {

    public ButtonToolBar btnAdd, btnDelete, btnEdit, btnDetail, btnNhapExcel, btnXuatExcel, btnHuyPhieu;
    public JSeparator separator1;
    private IntegratedSearch searchComponent;
    public HashMap<String, ButtonToolBar> btn = new HashMap<>();
    private final NhomQuyenBUS nhomquyenBus = new NhomQuyenBUS();

    public SidebarPanel(int manhomquyen, String chucnang, String[] listBtn) {
        initData();
        initComponent(manhomquyen, chucnang, listBtn);
    }

    public void initData() {
        btn.put("create", new ButtonToolBar("THÊM", "add.svg", "create"));
        btn.put("delete", new ButtonToolBar("XÓA", "delete.svg", "delete"));
        btn.put("update", new ButtonToolBar("SỬA", "edit.svg", "update"));
        btn.put("cancel", new ButtonToolBar("HUỶ", "cancel.svg", "delete"));
        btn.put("detail", new ButtonToolBar("CHI TIẾT", "detail.svg", "view"));
        btn.put("export", new ButtonToolBar("XUẤT EXCEL", "export_excel.svg", "view"));
        // Thêm các button khác nếu cần
    }

    private void initComponent(int manhomquyen, String chucnang, String[] listBtn) {
        this.setBackground(Color.WHITE);
        this.setRollover(true);
        
        for (String btnKey : listBtn) {
            ButtonToolBar button = btn.get(btnKey);
            if (button != null) {
                // Kiểm tra quyền cho từng action (create, update, delete...)
                if (nhomquyenBus.checkPermission(manhomquyen, chucnang, button.getPermisson())) {
                    this.add(button);
                }
                // Ẩn nút nếu không có quyền
            }
        }
    }
    
    public void addSearchComponent(String[] searchOptions) {
        this.searchComponent = new IntegratedSearch(searchOptions);
        this.add(searchComponent);
    }

    public JTextField getSearchField() {
        return this.searchComponent.txtSearchForm;
    }

    public JButton getResetButton() {
        return this.searchComponent.btnReset;
    }

    public IntegratedSearch getSearchComponent() {
        return this.searchComponent;
    }
}

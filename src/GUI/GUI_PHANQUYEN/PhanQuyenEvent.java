package GUI_PHANQUYEN;

import BUS.NhomQuyenBUS;
import Component.IntegratedSearch;
import DTO.NhomQuyenDTO;
import DTO.DanhMucChucNangDTO;
import helper.JTableExporter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class PhanQuyenEvent {
    private PhanQuyenGUI gui;
    private NhomQuyenBUS nhomQuyenBUS;
    private ArrayList<DanhMucChucNangDTO> danhMucChucNangList;

    public PhanQuyenEvent(PhanQuyenGUI gui) {
        this.gui = gui;
        this.nhomQuyenBUS = gui.getNhomQuyenBUS();
        this.danhMucChucNangList = DAO.DanhMucChucNangDAO.getInstance().selectAll();

        gui.getNhomQuyenTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    NhomQuyenDTO selected = gui.getSelectedNhomQuyen();
                    if (selected != null) {
                        openDetailDialog(selected);
                    }
                }
            }
        });

        gui.getSidebarPanel().btn.get("create").addActionListener(e -> openAddDialog());
        gui.getSidebarPanel().btn.get("update").addActionListener(e -> openEditDialog());
        gui.getSidebarPanel().btn.get("delete").addActionListener(e -> deleteNhomQuyen());
        gui.getSidebarPanel().btn.get("detail").addActionListener(e -> {
            NhomQuyenDTO selected = gui.getSelectedNhomQuyen();
            if (selected != null) {
                openDetailDialog(selected);
            }
        });
        gui.getSidebarPanel().btn.get("export").addActionListener(e -> exportToExcel());
    }

    public void openAddDialog() {
        new PhanQuyenDialog(gui, null, false, false);
        gui.loadNhomQuyenData();
    }

    public void openEditDialog() {
        NhomQuyenDTO selected = gui.getSelectedNhomQuyen();
        if (selected != null) {
            new PhanQuyenDialog(gui, selected, true, false);
            gui.loadNhomQuyenData();
        }
    }

    public void deleteNhomQuyen() {
        NhomQuyenDTO selected = gui.getSelectedNhomQuyen();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(gui, "Bạn có chắc chắn muốn xóa nhóm quyền này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    nhomQuyenBUS.delete(selected);
                    gui.loadNhomQuyenData();
                    JOptionPane.showMessageDialog(gui, "Xóa nhóm quyền thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(gui, "Lỗi khi xóa nhóm quyền: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        }
    }

    public void openDetailDialog(NhomQuyenDTO nhomQuyen) {
        new PhanQuyenDialog(gui, nhomQuyen, false, true);
    }

    public void exportToExcel() {
        try {
            boolean success = JTableExporter.exportJTableToExcel(gui.getNhomQuyenTable());
            if (success) {
                JOptionPane.showMessageDialog(gui, "Xuất file Excel thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void search() {
        IntegratedSearch search = gui.getSearch();
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String type = (String) search.cbxChoose.getSelectedItem();
        ArrayList<NhomQuyenDTO> result = new ArrayList<>();

        if (type.equals("Tất cả")) {
            result = nhomQuyenBUS.getAll();
            result.removeIf(nq -> {
                return !String.valueOf(nq.getMNQ()).contains(keyword) &&
                       !nq.getTEN().toLowerCase().contains(keyword);
            });
        } else if (type.equals("Mã Nhóm Quyền")) {
            result = nhomQuyenBUS.getAll();
            result.removeIf(nq -> !String.valueOf(nq.getMNQ()).contains(keyword));
        } else if (type.equals("Tên Nhóm Quyền")) {
            result = nhomQuyenBUS.getAll();
            result.removeIf(nq -> !nq.getTEN().toLowerCase().contains(keyword));
        }

        DefaultTableModel tblModel = gui.getTableModel();
        tblModel.setRowCount(0);
        int stt = 1;
        for (NhomQuyenDTO nq : result) {
            tblModel.addRow(new Object[] {
                    stt++,
                    "NQ" + String.format("%03d", nq.getMNQ()),
                    nq.getTEN()
            });
        }
    }
}
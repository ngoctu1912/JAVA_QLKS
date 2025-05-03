// package BUS;

// import DAO.NhanVienDAO;
// import DAO.TaiKhoanDAO;
// import DTO.NhanVienDTO;
// import GUI_NHANVIEN.NhanVienGUI;
// import helper.Validation;
// import java.awt.Desktop;
// import java.io.BufferedInputStream;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.Date;
// import javax.swing.JFileChooser;
// import javax.swing.JFrame;
// import javax.swing.JOptionPane;
// import org.apache.poi.ss.usermodel.*;
// import org.apache.poi.xssf.usermodel.XSSFRow;
// import org.apache.poi.xssf.usermodel.XSSFSheet;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// public class NhanVienBUS {
//     private NhanVienGUI gui;
//     private ArrayList<NhanVienDTO> listNv = NhanVienDAO.getInstance().selectAll();
//     private NhanVienDAO nhanVienDAO = NhanVienDAO.getInstance();
//     private TaiKhoanDAO taiKhoanDAO;
//     private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//     public NhanVienBUS(NhanVienGUI parent) {
//         this.gui = parent;
//         try {
//             Connection conn = getConnection();
//             this.taiKhoanDAO = TaiKhoanDAO.getInstance();
//         } catch (SQLException e) {
//             e.printStackTrace();
//             JOptionPane.showMessageDialog(nhanVienGUI, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//         }
//     }

//     private Connection getConnection() throws SQLException {
//         String url = "jdbc:mysql://localhost:3306/quanlykhachsan";
//         String user = "root";
//         String password = "12345678";
//         return DriverManager.getConnection(url, user, password);
//     }

//     public ArrayList<NhanVienDTO> getAll() {
//         return this.listNv;
//     }

//     public NhanVienDTO getByIndex(int index) {
//         return this.listNv.get(index);
//     }

//     public int getIndexById(int manv) {
//         int i = 0;
//         int vitri = -1;
//         int size = this.listNv.size();
//         while (i < size && vitri == -1) {
//             if (this.listNv.get(i).getMNV() == manv) {
//                 vitri = i;
//             } else {
//                 i++;
//             }
//         }
//         return vitri;
//     }

//     public String getNameById(int manv) {
//         return nhanVienDAO.selectById(manv + "").getHOTEN();
//     }

//     public String[] getArrTenNhanVien() {
//         int size = listNv.size();
//         String[] result = new String[size];
//         for (int i = 0; i < size; i++) {
//             result[i] = listNv.get(i).getHOTEN();
//         }
//         return result;
//     }

//     public void insertNv(NhanVienDTO nv) {
//         nhanVienDAO.add(nv);
//         listNv.add(nv);
//         gui.loadNhanVienData();
//     }

//     public void updateNv(int index, NhanVienDTO nv) {
//         nhanVienDAO.update(nv);
//         listNv.set(index, nv);
//         gui.loadNhanVienData();
//     }

//     // public void deleteNv(NhanVienDTO nv) {
//     //     nhanVienDAO.delete(nv.getMNV() + "");
//     //     taiKhoanDAO.delete(nv.getMNV());
//     //     listNv.removeIf(n -> n.getMNV() == nv.getMNV());
//     //     gui.loadNhanVienData();
//     // }

//     public void deleteNv(NhanVienDTO nv) {
//         nhanVienDAO.delete(nv.getMNV() + "");
//         taiKhoanDAO.delete(String.valueOf(nv.getMNV())); // Convert int to String
//         listNv.removeIf(n -> n.getMNV() == nv.getMNV());
//         gui.loadNhanVienData();
//     }

//     public ArrayList<NhanVienDTO> search(String text) {
//         text = text.toLowerCase();
//         ArrayList<NhanVienDTO> result = new ArrayList<>();
//         for (NhanVienDTO i : this.listNv) {
//             if (i.getHOTEN().toLowerCase().contains(text) || 
//                 i.getEMAIL().toLowerCase().contains(text) ||
//                 i.getSDT().toLowerCase().contains(text)) {
//                 result.add(i);
//             }
//         }
//         return result;
//     }

//     public void exportExcel() {
//         try {
//             if (!listNv.isEmpty()) {
//                 JFileChooser jFileChooser = new JFileChooser();
//                 jFileChooser.showSaveDialog(gui);
//                 File saveFile = jFileChooser.getSelectedFile();
//                 if (saveFile != null) {
//                     saveFile = new File(saveFile.toString() + ".xlsx");
//                     Workbook wb = new XSSFWorkbook();
//                     Sheet sheet = wb.createSheet("Nhân viên");

//                     String[] header = {"Mã NV", "Họ Tên", "Email", "SĐT", "Giới Tính", "Ngày Sinh"};
//                     writeHeader(header, sheet, 0);
//                     int rowIndex = 1;
//                     for (NhanVienDTO nv : listNv) {
//                         Row row = sheet.createRow(rowIndex++);
//                         writeNhanVien(nv, row);
//                     }
//                     FileOutputStream out = new FileOutputStream(saveFile);
//                     wb.write(out);
//                     wb.close();
//                     out.close();
//                     openFile(saveFile.toString());
//                 }
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//             JOptionPane.showMessageDialog(gui, "Lỗi khi xuất Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//         }
//     }

//     public void importExcel() {
//         File excelFile;
//         FileInputStream excelFIS = null;
//         BufferedInputStream excelBIS = null;
//         XSSFWorkbook excelJTableImport = null;
//         JFileChooser jf = new JFileChooser();
//         int result = jf.showOpenDialog(null);
//         jf.setDialogTitle("Open file");
//         int k = 0;
//         if (result == JFileChooser.APPROVE_OPTION) {
//             try {
//                 excelFile = jf.getSelectedFile();
//                 excelFIS = new FileInputStream(excelFile);
//                 excelBIS = new BufferedInputStream(excelFIS);
//                 excelJTableImport = new XSSFWorkbook(excelBIS);
//                 XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);

//                 for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
//                     int check = 1;
//                     int gt;
//                     XSSFRow excelRow = excelSheet.getRow(row);
//                     int id = nhanVienDAO.getAutoIncrement();
//                     String tennv = excelRow.getCell(0).getStringCellValue();
//                     String gioitinh = excelRow.getCell(1).getStringCellValue();
//                     if (gioitinh.equalsIgnoreCase("Nam")) {
//                         gt = 1;
//                     } else {
//                         gt = 0;
//                     }
//                     String sdt = excelRow.getCell(3).getStringCellValue();
//                     Date ngaysinh = excelRow.getCell(2).getDateCellValue();
//                     java.sql.Date birth = ngaysinh != null ? new java.sql.Date(ngaysinh.getTime()) : null;
//                     String email = excelRow.getCell(4).getStringCellValue();
//                     if (Validation.isEmpty(tennv) || Validation.isEmpty(email) ||
//                         !Validation.isEmail(email) || Validation.isEmpty(sdt) ||
//                         !Validation.isPhoneNumber(sdt) || sdt.length() != 10) {
//                         check = 0;
//                     }
//                     if (check == 0) {
//                         k += 1;
//                     } else {
//                         NhanVienDTO nvdto = new NhanVienDTO(id, tennv, gt, birth, sdt, 1, email, 0, new java.sql.Date(System.currentTimeMillis()), 0);
//                         nhanVienDAO.add(nvdto);
//                         listNv.add(nvdto);
//                     }
//                 }
//                 JOptionPane.showMessageDialog(gui, "Nhập thành công");
//                 gui.loadNhanVienData();

//             } catch (Exception ex) {
//                 ex.printStackTrace();
//                 JOptionPane.showMessageDialog(gui, "Lỗi đọc file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//             } finally {
//                 try {
//                     if (excelBIS != null) excelBIS.close();
//                     if (excelFIS != null) excelFIS.close();
//                     if (excelJTableImport != null) excelJTableImport.close();
//                 } catch (IOException ex) {
//                     ex.printStackTrace();
//                 }
//             }
//         }
//         if (k != 0) {
//             JOptionPane.showMessageDialog(gui, "Những dữ liệu không chuẩn không được thêm vào");
//         }
//     }

//     private static void writeHeader(String[] list, Sheet sheet, int rowIndex) {
//         CellStyle cellStyle = createStyleForHeader(sheet);
//         Row row = sheet.createRow(rowIndex);
//         Cell cell;
//         for (int i = 0; i < list.length; i++) {
//             cell = row.createCell(i);
//             cell.setCellStyle(cellStyle);
//             cell.setCellValue(list[i]);
//             sheet.autoSizeColumn(i);
//         }
//     }

//     private static CellStyle createStyleForHeader(Sheet sheet) {
//         Font font = sheet.getWorkbook().createFont();
//         font.setFontName("Times New Roman");
//         font.setBold(true);
//         font.setFontHeightInPoints((short) 14);
//         font.setColor(IndexedColors.WHITE.getIndex());

//         CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
//         cellStyle.setFont(font);
//         cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
//         cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//         cellStyle.setBorderBottom(BorderStyle.THIN);
//         return cellStyle;
//     }

//     private void writeNhanVien(NhanVienDTO nv, Row row) {
//         Cell cell = row.createCell(0);
//         cell.setCellValue(nv.getMNV());

//         cell = row.createCell(1);
//         cell.setCellValue(nv.getHOTEN());

//         cell = row.createCell(2);
//         cell.setCellValue(nv.getEMAIL());

//         cell = row.createCell(3);
//         cell.setCellValue(nv.getSDT());

//         cell = row.createCell(4);
//         cell.setCellValue(nv.getGIOITINH() == 1 ? "Nam" : "Nữ");

//         cell = row.createCell(5);
//         cell.setCellValue(nv.getNGAYSINH() != null ? dateFormat.format(nv.getNGAYSINH()) : "");
//     }

//     private void openFile(String file) {
//         try {
//             File path = new File(file);
//             Desktop.getDesktop().open(path);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }

package BUS;

import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import DTO.NhanVienDTO;
import GUI_NHANVIEN.NhanVienGUI;
import helper.Validation;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NhanVienBUS {
    private NhanVienGUI gui;
    private ArrayList<NhanVienDTO> listNv = NhanVienDAO.getInstance().selectAll();
    private NhanVienDAO nhanVienDAO = NhanVienDAO.getInstance();
    private TaiKhoanDAO taiKhoanDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public NhanVienBUS(NhanVienGUI parent) {
        this.gui = parent;
        try {
            Connection conn = getConnection();
            this.taiKhoanDAO = TaiKhoanDAO.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gui, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/quanlykhachsan";
        String user = "root";
        String password = "12345678";
        return DriverManager.getConnection(url, user, password);
    }

    public ArrayList<NhanVienDTO> getAll() {
        return this.listNv;
    }

    public NhanVienDTO getByIndex(int index) {
        return this.listNv.get(index);
    }

    public int getIndexById(int manv) {
        int i = 0;
        int vitri = -1;
        int size = this.listNv.size();
        while (i < size && vitri == -1) {
            if (this.listNv.get(i).getMNV() == manv) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public String getNameById(int manv) {
        return nhanVienDAO.selectById(manv + "").getHOTEN();
    }

    public String[] getArrTenNhanVien() {
        int size = listNv.size();
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = listNv.get(i).getHOTEN();
        }
        return result;
    }

    public void insertNv(NhanVienDTO nv) {
        nhanVienDAO.add(nv);
        listNv.add(nv);
        gui.loadNhanVienData();
    }

    public void updateNv(int index, NhanVienDTO nv) {
        nhanVienDAO.update(nv);
        listNv.set(index, nv);
        gui.loadNhanVienData();
    }

    public void deleteNv(NhanVienDTO nv) {
        nhanVienDAO.delete(nv.getMNV() + "");
        taiKhoanDAO.delete(String.valueOf(nv.getMNV()));
        listNv.removeIf(n -> n.getMNV() == nv.getMNV());
        gui.loadNhanVienData();
    }

    public ArrayList<NhanVienDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        for (NhanVienDTO i : this.listNv) {
            if (i.getHOTEN().toLowerCase().contains(text) || 
                i.getEMAIL().toLowerCase().contains(text) ||
                i.getSDT().toLowerCase().contains(text)) {
                result.add(i);
            }
        }
        return result;
    }

    public void exportExcel() {
        try {
            if (!listNv.isEmpty()) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showSaveDialog(gui);
                File saveFile = jFileChooser.getSelectedFile();
                if (saveFile != null) {
                    saveFile = new File(saveFile.toString() + ".xlsx");
                    Workbook wb = new XSSFWorkbook();
                    Sheet sheet = wb.createSheet("Nhân viên");

                    String[] header = {"Mã NV", "Họ Tên", "Email", "SĐT", "Giới Tính", "Ngày Sinh"};
                    writeHeader(header, sheet, 0);
                    int rowIndex = 1;
                    for (NhanVienDTO nv : listNv) {
                        Row row = sheet.createRow(rowIndex++);
                        writeNhanVien(nv, row);
                    }
                    FileOutputStream out = new FileOutputStream(saveFile);
                    wb.write(out);
                    wb.close();
                    out.close();
                    openFile(saveFile.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gui, "Lỗi khi xuất Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void importExcel() {
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        JFileChooser jf = new JFileChooser();
        int result = jf.showOpenDialog(null);
        jf.setDialogTitle("Open file");
        int k = 0;
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);

                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    int check = 1;
                    int gt;
                    XSSFRow excelRow = excelSheet.getRow(row);
                    int id = nhanVienDAO.getAutoIncrement();
                    String tennv = excelRow.getCell(0).getStringCellValue();
                    String gioitinh = excelRow.getCell(1).getStringCellValue();
                    if (gioitinh.equalsIgnoreCase("Nam")) {
                        gt = 1;
                    } else {
                        gt = 0;
                    }
                    String sdt = excelRow.getCell(3).getStringCellValue();
                    Date ngaysinh = excelRow.getCell(2).getDateCellValue();
                    java.sql.Date birth = ngaysinh != null ? new java.sql.Date(ngaysinh.getTime()) : null;
                    String email = excelRow.getCell(4).getStringCellValue();
                    if (Validation.isEmpty(tennv) || Validation.isEmpty(email) ||
                        !Validation.isEmail(email) || Validation.isEmpty(sdt) ||
                        !Validation.isPhoneNumber(sdt) || sdt.length() != 10) {
                        check = 0;
                    }
                    if (check == 0) {
                        k += 1;
                    } else {
                        NhanVienDTO nvdto = new NhanVienDTO(id, tennv, gt, birth, sdt, 1, email);
                        nhanVienDAO.add(nvdto);
                        listNv.add(nvdto);
                    }
                }
                JOptionPane.showMessageDialog(gui, "Nhập thành công");
                gui.loadNhanVienData();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(gui, "Lỗi đọc file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (excelBIS != null) excelBIS.close();
                    if (excelFIS != null) excelFIS.close();
                    if (excelJTableImport != null) excelJTableImport.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (k != 0) {
            JOptionPane.showMessageDialog(gui, "Những dữ liệu không chuẩn không được thêm vào");
        }
    }

    private static void writeHeader(String[] list, Sheet sheet, int rowIndex) {
        CellStyle cellStyle = createStyleForHeader(sheet);
        Row row = sheet.createRow(rowIndex);
        Cell cell;
        for (int i = 0; i < list.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(list[i]);
            sheet.autoSizeColumn(i);
        }
    }

    private static CellStyle createStyleForHeader(Sheet sheet) {
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setColor(IndexedColors.WHITE.getIndex());

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    private void writeNhanVien(NhanVienDTO nv, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(nv.getMNV());

        cell = row.createCell(1);
        cell.setCellValue(nv.getHOTEN());

        cell = row.createCell(2);
        cell.setCellValue(nv.getEMAIL());

        cell = row.createCell(3);
        cell.setCellValue(nv.getSDT());

        cell = row.createCell(4);
        cell.setCellValue(nv.getGIOITINH() == 1 ? "Nam" : "Nữ");

        cell = row.createCell(5);
        cell.setCellValue(nv.getNGAYSINH() != null ? dateFormat.format(nv.getNGAYSINH()) : "");
    }

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
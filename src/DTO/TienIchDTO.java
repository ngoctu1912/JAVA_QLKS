package DTO;

public class TienIchDTO {
    private String maTI;
    private String tenTI;
    private int totalQuantity; // Số lượng tổng
    private int remainingQuantity; // Số lượng còn lại
    private int xuLy;

    // Constructor
    public TienIchDTO() {
    }

    public TienIchDTO(String maTI, String tenTI, int totalQuantity, int xuLy) {
        this.maTI = maTI;
        this.tenTI = tenTI;
        this.totalQuantity = totalQuantity;
        this.xuLy = xuLy;
    }

    // Getters and Setters
    public String getMaTI() {
        return maTI;
    }

    public void setMaTI(String maTI) {
        this.maTI = maTI;
    }

    public String getTenTI() {
        return tenTI;
    }

    public void setTenTI(String tenTI) {
        this.tenTI = tenTI;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public int getXuLy() {
        return xuLy;
    }

    public void setXuLy(int xuLy) {
        this.xuLy = xuLy;
    }
}
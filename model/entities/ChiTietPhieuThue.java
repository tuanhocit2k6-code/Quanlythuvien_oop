package model.entities;

import java.time.LocalDate;
import model.interfaces.ITinhTienPhatStrategy;

public class ChiTietPhieuThue {
    private Sach sach;
    private int soLuongThue;
    private double tienPhat;

    public ChiTietPhieuThue(Sach sach, int soLuongThue) {
        if (sach == null || soLuongThue <= 0) {
            throw new IllegalArgumentException("Chi tiết phiếu thuê không hợp lệ.");
        }
        this.sach = sach;
        this.soLuongThue = soLuongThue;
        this.tienPhat = 0.0;
    }

    public double tinhTienDongGop() {
        return sach.getGiaThue() * soLuongThue;
    }

    public void tinhTienPhat(LocalDate ngayHenTra, LocalDate ngayTraThucTe, ITinhTienPhatStrategy strategy) {
        this.tienPhat = strategy.tinhTienPhat(ngayHenTra, ngayTraThucTe) * soLuongThue;
    }

    public Sach getSach() { return sach; }
    public int getSoLuongThue() { return soLuongThue; }
    public double getTienPhat() { return tienPhat; }
}

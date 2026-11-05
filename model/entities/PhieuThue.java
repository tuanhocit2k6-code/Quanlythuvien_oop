package model.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PhieuThue {
    private String maPhieu;
    private String usernameThanhVien;
    private LocalDate ngayThue;
    private LocalDate ngayHenTra;
    private double tongTienGoc;
    private double giamGia;
    private double tongTienPhaiTra;
    private String trangThai;
    private List<ChiTietPhieuThue> dsChiTiet;
    private double tongTienPhat;

    public PhieuThue(String maPhieu, String usernameThanhVien, int soNgayThue) {
        if (maPhieu == null || maPhieu.trim().isEmpty() || usernameThanhVien == null
                || usernameThanhVien.trim().isEmpty() || soNgayThue <= 0) {
            throw new IllegalArgumentException("Thông tin phiếu thuê không hợp lệ.");
        }
        this.maPhieu = maPhieu;
        this.usernameThanhVien = usernameThanhVien;
        this.ngayThue = LocalDate.now();
        this.ngayHenTra = this.ngayThue.plusDays(soNgayThue);
        this.trangThai = "DANG_THUE";
        this.dsChiTiet = new ArrayList<>();
        this.giamGia = 0.0;
    }

    public void themChiTiet(ChiTietPhieuThue ct) {
        dsChiTiet.add(ct);
        tinhToanHoaDon();
    }

    public void tinhToanHoaDon() {
        this.tongTienGoc = 0;
        this.tongTienPhat = 0;
        for (ChiTietPhieuThue ct : dsChiTiet) {
            this.tongTienGoc += ct.tinhTienDongGop();
            this.tongTienPhat += ct.getTienPhat();
        }
        this.tongTienPhaiTra = this.tongTienGoc - (this.tongTienGoc * this.giamGia / 100.0)
                + this.tongTienPhat;
    }

    public void apDungGiamGia(double phanTram) {
        this.giamGia = phanTram;
        tinhToanHoaDon();
    }

    public void hienThiPhieu() {
        System.out.println("--- PHIẾU THUÊ: " + maPhieu + " ---");
        System.out.println("Thành viên: " + usernameThanhVien);
        System.out.println("Ngày thuê: " + ngayThue + " | Hạn trả: " + ngayHenTra);
        System.out.println("Trạng thái: " + trangThai);
        System.out.println("Chi tiết sách thuê:");
        for (ChiTietPhieuThue ct : dsChiTiet) {
            System.out.println("- " + ct.getSach().getTenSach() + " | SL: " + ct.getSoLuongThue() + " | Tiền phạt: " + ct.getTienPhat());
        }
        System.out.println("Tổng tiền gốc: " + tongTienGoc + " | Giảm giá: " + giamGia + "%");
        System.out.println("Tổng phải trả: " + tongTienPhaiTra);
    }

    public String getMaPhieu() { return maPhieu; }
    public String getUsernameThanhVien() { return usernameThanhVien; }
    public double getTongTienPhaiTra() { return tongTienPhaiTra; }
    public double getTongTienPhat() { return tongTienPhat; }
    public List<ChiTietPhieuThue> getDsChiTiet() { return dsChiTiet; }
    public LocalDate getNgayThue() { return ngayThue; }
    public LocalDate getNgayHenTra() { return ngayHenTra; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}

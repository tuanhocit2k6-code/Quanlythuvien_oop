package model.entities;

public class Sach {
    private String maSach;
    private String tenSach;
    private double giaThue;
    private int soLuongTon;

    public Sach(String maSach, String tenSach, double giaThue, int soLuongTon) {
        if (maSach == null || maSach.trim().isEmpty() || tenSach == null || tenSach.trim().isEmpty()
                || !Double.isFinite(giaThue) || giaThue < 0 || soLuongTon < 0) {
            throw new IllegalArgumentException("Thông tin sách không hợp lệ.");
        }
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.soLuongTon = soLuongTon;
    }

    public String getMaSach() { return maSach; }
    public String getTenSach() { return tenSach; }
    public double getGiaThue() { return giaThue; }
    public int getSoLuongTon() { return soLuongTon; }

    public boolean giamKho(int soLuong) {
        if (soLuong > 0 && this.soLuongTon >= soLuong) {
            this.soLuongTon -= soLuong;
            return true;
        }
        return false;
    }

    public void tangKho(int soLuong) {
        if (soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng nhập kho phải lớn hơn 0.");
        }
        this.soLuongTon += soLuong;
    }
}

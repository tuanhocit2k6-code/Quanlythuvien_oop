package model.entities;

public class ThanhVien  extends  NguoiDung{
    private double viTien;
    private   int soLanDaThue;
public ThanhVien(String username, String password , String hoTen , String email, double viTien){
super(username, password, hoTen, email);
this.viTien =viTien;
this.soLanDaThue=0;
}
    public double getViTien() { return viTien; }
    public void truTien(double sotien) {
        if (!Double.isFinite(sotien) || sotien < 0) {
            throw new IllegalArgumentException("Số tiền phải trừ không hợp lệ.");
        }
        this.viTien -= sotien;
    }
    public int getSoLanDaThue() { return soLanDaThue; }
    public void tangSoLanThue() { this.soLanDaThue++; }
    public void resetSoLanThue() { this.soLanDaThue = 0; }
    public void xemSach(){
        System.out.println("Thanh vien "+hoTen +"dang duyet danh muc sach");
    }

}

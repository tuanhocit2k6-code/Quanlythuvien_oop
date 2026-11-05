package model.entities;

public class NhanVien extends NguoiDung {
    private String vaiTro;

    public NhanVien(String username, String password, String hoTen, String email, String vaiTro) {
        super(username, password, hoTen, email);
        this.vaiTro = vaiTro;
    }

    public String getVaiTro() { return vaiTro; }
}

package model.interfaces;

import model.entities.NguoiDung;

public interface IQuanLyNguoiDung {
    boolean dangKy(NguoiDung user);
    boolean dangNhap(String username, String password);
    void dangXuat();
    NguoiDung getCurrentUser();
}

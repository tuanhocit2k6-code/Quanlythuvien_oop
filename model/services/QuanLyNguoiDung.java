package model.services;

import java.util.HashMap;
import java.util.Map;
import model.entities.NguoiDung;
import model.entities.NhanVien;
import model.entities.ThanhVien;
import model.interfaces.IQuanLyNguoiDung;

public class QuanLyNguoiDung implements IQuanLyNguoiDung {
    private static QuanLyNguoiDung instance;
    private Map<String, NguoiDung> dsNguoiDung;
    private NguoiDung currentUser;

    private QuanLyNguoiDung() {
        dsNguoiDung = new HashMap<>();
    }

    public static synchronized QuanLyNguoiDung getInstance() {
        if (instance == null) {
            instance = new QuanLyNguoiDung();
        }
        return instance;
    }

    @Override
    public boolean dangKy(NguoiDung user) {
        if (!(user instanceof ThanhVien)) return false;
        return luuNguoiDung(user);
    }

    public boolean dangKyNhanVien(NhanVien user) {
        return luuNguoiDung(user);
    }

    private boolean luuNguoiDung(NguoiDung user) {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getHoTen() == null || user.getHoTen().trim().isEmpty()
                || user.getEmail() == null || user.getEmail().trim().isEmpty()
                || dsNguoiDung.containsKey(user.getUsername())) {
            return false;
        }
        dsNguoiDung.put(user.getUsername(), user);
        return true;
    }

    @Override
    public boolean dangNhap(String username, String password) {
        currentUser = null;
        if (username == null || password == null) return false;
        if (dsNguoiDung.containsKey(username)) {
            NguoiDung nd = dsNguoiDung.get(username);
            if (nd.getPassword().equals(password)) {
                this.currentUser = nd;
                return true;
            }
        }
        return false;
    }

    @Override
    public void dangXuat() {
        currentUser = null;
    }

    @Override
    public NguoiDung getCurrentUser() { return currentUser; }
}

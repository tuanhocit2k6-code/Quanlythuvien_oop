package model.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entities.Sach;
import model.interfaces.IQuanLySach;

public class QuanLySach implements IQuanLySach {
    private static QuanLySach instance;
    private Map<String, Sach> dsSach;

    private QuanLySach() {
        dsSach = new HashMap<>();
    }

    public static synchronized QuanLySach getInstance() {
        if (instance == null) {
            instance = new QuanLySach();
        }
        return instance;
    }

    @Override
    public boolean themSach(Sach s) {
        if (s == null || dsSach.containsKey(s.getMaSach())) return false;
        dsSach.put(s.getMaSach(), s);
        return true;
    }

    @Override
    public boolean xoaSach(String maSach) {
        return dsSach.remove(maSach) != null;
    }

    @Override
    public Sach timSach(String maSach) {
        return dsSach.get(maSach);
    }

    @Override
    public List<Sach> getDanhSachSach() {
        return new ArrayList<>(dsSach.values());
    }
}

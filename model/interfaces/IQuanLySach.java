package model.interfaces;

import java.util.List;
import model.entities.Sach;

public interface IQuanLySach {
    boolean themSach(Sach s);
    boolean xoaSach(String maSach);
    Sach timSach(String maSach);
    List<Sach> getDanhSachSach();
}

package model.interfaces;

import java.time.LocalDate;
import java.util.List;
import model.entities.PhieuThue;
import model.entities.ThanhVien;

public interface IQuanLyMuonTra {
    boolean taoPhieuThue(PhieuThue pt, ThanhVien tv);
    PhieuThue xuLyTraSach(String maPhieu, LocalDate ngayTraThucTe, ThanhVien tv);
    List<PhieuThue> layLichSuTheoThanhVien(String usernameThanhVien);
}

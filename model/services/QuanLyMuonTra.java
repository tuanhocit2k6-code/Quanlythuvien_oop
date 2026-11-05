package model.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import model.entities.ChiTietPhieuThue;
import model.entities.Sach;
import model.entities.PhieuThue;
import model.entities.ThanhVien;
import model.interfaces.IQuanLyMuonTra;

public class QuanLyMuonTra implements IQuanLyMuonTra {
    private static QuanLyMuonTra instance;
    private List<PhieuThue> dsPhieuThue;

    private QuanLyMuonTra() {
        dsPhieuThue = new ArrayList<>();
    }

    public static synchronized QuanLyMuonTra getInstance() {
        if (instance == null) {
            instance = new QuanLyMuonTra();
        }
        return instance;
    }

    public synchronized String taoMaPhieuTiepTheo() {
        int next = 1;
        while (true) {
            String maPhieu = "PT" + String.format("%03d", next);
            boolean daTonTai = false;
            for (PhieuThue existing : dsPhieuThue) {
                if (existing.getMaPhieu().equals(maPhieu)) {
                    daTonTai = true;
                    break;
                }
            }
            if (!daTonTai) {
                return maPhieu;
            }
            next++;
        }
    }

    @Override
    public synchronized List<PhieuThue> layLichSuTheoThanhVien(String usernameThanhVien) {
        List<PhieuThue> ketQua = new ArrayList<>();
        if (usernameThanhVien == null || usernameThanhVien.trim().isEmpty()) {
            return ketQua;
        }
        for (PhieuThue pt : dsPhieuThue) {
            if (pt.getUsernameThanhVien().equals(usernameThanhVien)) {
                ketQua.add(pt);
            }
        }
        return ketQua;
    }

    public synchronized List<PhieuThue> getDanhSachPhieuThue() {
        return new ArrayList<>(dsPhieuThue);
    }

    @Override
    public synchronized boolean taoPhieuThue(PhieuThue pt, ThanhVien tv) {
        if (pt == null || tv == null || pt.getDsChiTiet().isEmpty()
                || tv.getViTien() < pt.getTongTienPhaiTra()) {
            return false;
        }
        for (PhieuThue existing : dsPhieuThue) {
            if (existing.getMaPhieu().equals(pt.getMaPhieu())) return false;
        }
        IdentityHashMap<Sach, Integer> soLuongTheoSach = new IdentityHashMap<>();
        for (ChiTietPhieuThue chiTiet : pt.getDsChiTiet()) {
            Sach sach = chiTiet.getSach();
            soLuongTheoSach.put(sach, soLuongTheoSach.getOrDefault(sach, 0) + chiTiet.getSoLuongThue());
        }
        for (java.util.Map.Entry<Sach, Integer> entry : soLuongTheoSach.entrySet()) {
            if (entry.getKey().getSoLuongTon() < entry.getValue()) return false;
        }
        for (java.util.Map.Entry<Sach, Integer> entry : soLuongTheoSach.entrySet()) {
            if (!entry.getKey().giamKho(entry.getValue())) {
                throw new IllegalStateException("Tồn kho thay đổi trong khi xử lý phiếu thuê.");
            }
        }
        tv.truTien(pt.getTongTienPhaiTra());
        tv.tangSoLanThue();
        dsPhieuThue.add(pt);
        return true;
    }

    @Override
    public synchronized PhieuThue xuLyTraSach(String maPhieu, LocalDate ngayTraThucTe, ThanhVien tv) {
        if (maPhieu == null || ngayTraThucTe == null || tv == null) return null;
        for (PhieuThue pt : dsPhieuThue) {
            if (pt.getMaPhieu().equals(maPhieu) && pt.getUsernameThanhVien().equals(tv.getUsername())
                    && !ngayTraThucTe.isBefore(pt.getNgayThue())
                    && "DANG_THUE".equals(pt.getTrangThai())) {
                TinhTienPhatMacDinh strategy = new TinhTienPhatMacDinh();
                for (ChiTietPhieuThue ct : pt.getDsChiTiet()) {
                    ct.tinhTienPhat(pt.getNgayHenTra(), ngayTraThucTe, strategy);
                    ct.getSach().tangKho(ct.getSoLuongThue());
                }
                pt.tinhToanHoaDon();
                tv.truTien(pt.getTongTienPhat());
                pt.setTrangThai("DA_TRA");
                return pt;
            }
        }
        return null;
    }
}

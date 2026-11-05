package controller.commands;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import controller.Command;
import model.entities.ChiTietPhieuThue;
import model.entities.PhieuThue;
import model.entities.Sach;
import model.services.QuanLyMuonTra;
import model.services.QuanLySach;

public class ThongKeCommand implements Command {
    @Override
    public void execute() {
        List<Sach> danhSachSach = QuanLySach.getInstance().getDanhSachSach();
        List<PhieuThue> danhSachPhieu = QuanLyMuonTra.getInstance().getDanhSachPhieuThue();

        long tongSoBanTrongKho = 0;
        for (Sach sach : danhSachSach) {
            tongSoBanTrongKho += sach.getSoLuongTon();
        }

        int soPhieuDangThue = 0;
        int soPhieuDaTra = 0;
        long soBanDangDuocThue = 0;
        long tongSoLuotThueSach = 0;
        double tongTienPhat = 0;
        Map<String, Long> soLuotTheoSach = new LinkedHashMap<>();
        Map<String, String> tenTheoMaSach = new LinkedHashMap<>();

        for (PhieuThue phieu : danhSachPhieu) {
            if ("DANG_THUE".equals(phieu.getTrangThai())) {
                soPhieuDangThue++;
            } else if ("DA_TRA".equals(phieu.getTrangThai())) {
                soPhieuDaTra++;
            }
            tongTienPhat += phieu.getTongTienPhat();

            for (ChiTietPhieuThue chiTiet : phieu.getDsChiTiet()) {
                Sach sach = chiTiet.getSach();
                String maSach = sach.getMaSach();
                long soLuongThue = chiTiet.getSoLuongThue();
                tongSoLuotThueSach += soLuongThue;
                soLuotTheoSach.put(maSach, soLuotTheoSach.getOrDefault(maSach, 0L) + soLuongThue);
                tenTheoMaSach.put(maSach, sach.getTenSach());
                if ("DANG_THUE".equals(phieu.getTrangThai())) {
                    soBanDangDuocThue += soLuongThue;
                }
            }
        }

        String sachDuocThueNhieuNhat = "Chưa có dữ liệu";
        long soLuotCaoNhat = 0;
        for (Map.Entry<String, Long> entry : soLuotTheoSach.entrySet()) {
            if (entry.getValue() > soLuotCaoNhat) {
                soLuotCaoNhat = entry.getValue();
                sachDuocThueNhieuNhat = tenTheoMaSach.get(entry.getKey())
                        + " (" + entry.getKey() + ") - " + String.format("%,d", soLuotCaoNhat) + " bản";
            }
        }

        String baoCao = "--- BÁO CÁO THỐNG KÊ HỆ THỐNG THƯ VIỆN ---\n"
                + "Đầu sách hiện có: " + String.format("%,d", danhSachSach.size()) + "\n"
                + "Số bản hiện còn trong kho: " + String.format("%,d", tongSoBanTrongKho) + "\n"
                + "Tổng số phiếu thuê: " + String.format("%,d", danhSachPhieu.size()) + "\n"
                + "Phiếu đang thuê: " + String.format("%,d", soPhieuDangThue) + "\n"
                + "Phiếu đã trả: " + String.format("%,d", soPhieuDaTra) + "\n"
                + "Số bản đang được thuê: " + String.format("%,d", soBanDangDuocThue) + "\n"
                + "Tổng số bản đã được thuê: " + String.format("%,d", tongSoLuotThueSach) + "\n"
                + "Sách được thuê nhiều nhất: " + sachDuocThueNhieuNhat + "\n"
                + "Tổng tiền phạt đã ghi nhận: " + String.format("%,.0f", tongTienPhat) + " VNĐ";

        System.out.println(baoCao);
        JOptionPane.showMessageDialog(null, baoCao, "Thống kê thư viện", JOptionPane.INFORMATION_MESSAGE);
    }
}

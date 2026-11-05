package model.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import model.interfaces.ITinhTienPhatStrategy;

public class TinhTienPhatMacDinh implements ITinhTienPhatStrategy {
    @Override
    public double tinhTienPhat(LocalDate ngayHenTra, LocalDate ngayTraThucTe) {
        if (ngayTraThucTe.isAfter(ngayHenTra)) {
            long soNgayTre = ChronoUnit.DAYS.between(ngayHenTra, ngayTraThucTe);
            return soNgayTre * 5000.0; // Phạt 5,000đ mỗi ngày trễ hạn
        }
        return 0.0;
    }
}

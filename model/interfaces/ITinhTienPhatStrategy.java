package model.interfaces;

import java.time.LocalDate;

public interface ITinhTienPhatStrategy {
    double tinhTienPhat(LocalDate ngayHenTra, LocalDate ngayTraThucTe);
}

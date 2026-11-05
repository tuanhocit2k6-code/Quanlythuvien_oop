package controller.commands;

import controller.Command;
import model.entities.Sach;
import model.interfaces.IQuanLySach;
import model.services.QuanLySach;
import view.SachView;

public class ThemSachCommand implements Command {
    private final Sach sach;
    private final SachView view;
    private final IQuanLySach service;

    public ThemSachCommand(Sach sach, SachView v) {
        this.sach = sach;
        this.view = v;
        this.service = QuanLySach.getInstance();
    }

    @Override
    public void execute() {
        if (service.themSach(sach)) {
            view.showThongBao("Đã thêm sách '" + sach.getTenSach() + "' vào hệ thống.");
            view.hienThiDanhSachSach(service.getDanhSachSach());
        } else {
            view.showThongBao("Mã sách đã tồn tại hoặc thông tin sách không hợp lệ.");
        }
    }
}

package controller.commands;

import controller.Command;
import model.entities.Sach;
import model.interfaces.IQuanLySach;
import model.services.QuanLySach;
import view.SachView;

public class TimKiemSachCommand implements Command {
    private String maSach;
    private SachView view;
    private IQuanLySach service;

    public TimKiemSachCommand(String maSach, SachView v) {
        this.maSach = maSach;
        this.view = v;
        this.service = QuanLySach.getInstance();
    }

    @Override
    public void execute() {
        Sach s = service.timSach(maSach);
        if(s != null) {
            view.showThongBao("Tìm thấy: " + s.getTenSach() + " | Tồn: " + s.getSoLuongTon());
        } else {
            view.showThongBao("Không tìm thấy quyển sách này!");
        }
    }
}

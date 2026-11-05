package controller.commands;

import controller.Command;
import model.interfaces.IQuanLySach;
import model.services.QuanLySach;
import view.SachView;

public class XoaSachCommand implements Command {
    private String maSach;
    private SachView view;
    private IQuanLySach service;

    public XoaSachCommand(String maSach, SachView v) {
        this.maSach = maSach;
        this.view = v;
        this.service = QuanLySach.getInstance();
    }

    @Override
    public void execute() {
        if (service.xoaSach(maSach)) {
            view.showThongBao("Đã xóa sách có mã: " + maSach);
        } else {
            view.showThongBao("Không tìm thấy sách có mã: " + maSach);
        }
    }
}

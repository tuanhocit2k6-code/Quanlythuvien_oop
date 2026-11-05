package controller.commands;

import controller.Command;
import javax.swing.JOptionPane;
import model.entities.NguoiDung;
import model.interfaces.IQuanLyNguoiDung;
import model.services.QuanLyNguoiDung;
import view.MainMenuView;

public class DangKyCommand implements Command {
    private NguoiDung nd;
    private MainMenuView view;
    private IQuanLyNguoiDung service;

    public DangKyCommand(NguoiDung nd, MainMenuView v) {
        this.nd = nd;
        this.view = v;
        this.service = QuanLyNguoiDung.getInstance();
    }

    @Override
    public void execute() {
        boolean success = service.dangKy(nd);
        if(success) {
            JOptionPane.showMessageDialog(view, "Đăng ký tài khoản thành công!");
        } else {
            JOptionPane.showMessageDialog(view, "Tên đăng nhập đã tồn tại.",
                    "Đăng ký thất bại", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package controller.commands;

import controller.Command;
import javax.swing.JOptionPane;
import model.interfaces.IQuanLyNguoiDung;
import model.services.QuanLyNguoiDung;
import view.MainMenuView;

public class DangNhapCommand implements Command {
    private String user;
    private String pass;
    private MainMenuView view;
    private IQuanLyNguoiDung service;

    public DangNhapCommand(String u, String p, MainMenuView v) {
        this.user = u;
        this.pass = p;
        this.view = v;
        this.service = QuanLyNguoiDung.getInstance();
    }

    @Override
    public void execute() {
        boolean success = service.dangNhap(user, pass);
        if(success) {
            JOptionPane.showMessageDialog(view, "Đăng nhập thành công! Xin chào "
                    + service.getCurrentUser().getHoTen());
        } else {
            JOptionPane.showMessageDialog(view, "Sai tài khoản hoặc mật khẩu.",
                    "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
        }
    }
}

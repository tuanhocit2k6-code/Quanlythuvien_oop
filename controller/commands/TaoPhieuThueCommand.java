package controller.commands;

import controller.Command;
import model.entities.PhieuThue;
import model.entities.ThanhVien;
import model.interfaces.IQuanLyMuonTra;
import model.services.QuanLyMuonTra;
import view.MuonTraView;

public class TaoPhieuThueCommand implements Command {
    private PhieuThue pt;
    private ThanhVien tv;
    private MuonTraView view;
    private IQuanLyMuonTra service;

    public TaoPhieuThueCommand(PhieuThue pt, ThanhVien tv, MuonTraView v) {
        this.pt = pt;
        this.tv = tv;
        this.view = v;
        this.service = QuanLyMuonTra.getInstance();
    }

    @Override
    public void execute() {
        boolean success = service.taoPhieuThue(pt, tv);
        if (success) {
            view.hienThiPhieuThue(pt);
        } else {
            view.showThongBao("Không thể thuê sách: hãy kiểm tra số dư, tồn kho hoặc dữ liệu thuê.");
        }
    }
}

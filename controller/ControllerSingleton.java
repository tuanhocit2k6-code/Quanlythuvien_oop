package controller;

import java.util.Map;
import javax.swing.JOptionPane;
import controller.commands.*;
import model.entities.*;
import model.services.*;
import view.*;

public class ControllerSingleton {
    private static ControllerSingleton instance;
    private CommandProcessor processor;
    private MainMenuView viewMenu;
    private SachView viewSach;
    private MuonTraView viewMuonTra;

    private ControllerSingleton() {
        this.processor = new CommandProcessor();
        this.viewMenu = new MainMenuView();
        // Gắn frame gốc của viewMenu vào các sub-view để làm anchor định vị thông báo UI
        this.viewSach = new SachView(viewMenu);
        this.viewMuonTra = new MuonTraView(viewMenu);
    }

    public static synchronized ControllerSingleton getInstance() {
        if (instance == null) {
            instance = new ControllerSingleton();
        }
        return instance;
    }

    public void runDemo() {
        // Mock Data dữ liệu thử nghiệm ban đầu
        QuanLySach.getInstance().themSach(new Sach("S01", "Lap Trinh Java Swing", 10000, 5));
        QuanLySach.getInstance().themSach(new Sach("S02", "Kien Truc Clean Code", 15000, 3));
        QuanLyNguoiDung.getInstance().dangKy(new ThanhVien("user1", "123", "Nguyen Van GUI", "gui@gmail.com", 100000));
        QuanLyNguoiDung.getInstance().dangKyNhanVien(
                new NhanVien("admin", "123", "Tran Nhan Vien", "admin@lib.com", "QuanLy"));

        // Hiển thị Form Giao diện đồ họa Swing lên màn hình
        java.awt.EventQueue.invokeLater(() -> {
            capNhatTrangThaiGiaoDien();
            viewMenu.setVisible(true);
        });
    }

    // Bộ bắt sự kiện (Event-driven) xử lý tương tác trực tiếp từ các nút nhấn GUI
    public void handleGiaoDien(String action) {
        NguoiDung current = QuanLyNguoiDung.getInstance().getCurrentUser();

        switch (action) {
            case "LOGIN_FORM":
                Map<String, String> loginData = viewMenu.inputDangNhap();
                if (loginData != null && !loginData.isEmpty()) {
                    Command login = new DangNhapCommand(loginData.get("username"), loginData.get("password"), viewMenu);
                    processor.executeCommand(login);
                    capNhatTrangThaiGiaoDien();
                }
                break;

            case "REGISTER_FORM":
                Map<String, String> regData = viewMenu.inputDangKy();
                if (regData != null && !regData.isEmpty()) {
                    NguoiDung newNd = new ThanhVien(regData.get("username"), regData.get("password"),
                            regData.get("hoTen"), regData.get("email"), 50000.0);
                    Command signup = new DangKyCommand(newNd, viewMenu);
                    processor.executeCommand(signup);
                }
                break;

            case "TIM_SACH":
                if (!(current instanceof ThanhVien)) {
                    viewSach.showThongBao("Vui lòng đăng nhập bằng tài khoản thành viên.");
                    break;
                }
                String ma = JOptionPane.showInputDialog(viewMenu, "Nhập mã sách cần tìm kiếm:");
                if (ma != null) {
                    Command search = new TimKiemSachCommand(ma, viewSach);
                    processor.executeCommand(search);
                }
                break;

            case "THUE_SACH":
                if (!(current instanceof ThanhVien)) {
                    viewSach.showThongBao("Vui lòng đăng nhập bằng tài khoản thành viên.");
                    break;
                }
                Map<String, Object> req = viewMuonTra.inputThongTinThue();
                if (req != null) {
                    Sach s = QuanLySach.getInstance().timSach((String) req.get("maSach"));
                    if (s != null) {
                        String maPhieu = QuanLyMuonTra.getInstance().taoMaPhieuTiepTheo();
                        PhieuThue pt = new PhieuThue(maPhieu, current.getUsername(), (int) req.get("soNgayThue"));
                        pt.themChiTiet(new ChiTietPhieuThue(s, (int) req.get("soLuong")));
                        Command rent = new TaoPhieuThueCommand(pt, (ThanhVien) current, viewMuonTra);
                        processor.executeCommand(rent);
                        capNhatTrangThaiGiaoDien();
                    } else {
                        viewSach.showThongBao("Không tìm thấy sách có mã đã nhập.");
                    }
                }
                break;

            case "TRA_SACH":
                if (!(current instanceof ThanhVien)) {
                    viewSach.showThongBao("Vui lòng đăng nhập bằng tài khoản thành viên.");
                    break;
                }
                String maPhieu = viewMuonTra.inputMaPhieuTra();
                if (maPhieu != null && !maPhieu.isEmpty()) {
                    java.time.LocalDate ngayTra = viewMuonTra.inputNgayTraThucTe();
                    if (ngayTra != null) {
                        PhieuThue phieuDaTra = QuanLyMuonTra.getInstance().xuLyTraSach(
                                maPhieu, ngayTra, (ThanhVien) current);
                        if (phieuDaTra != null) {
                            viewMuonTra.showThongBao("Đã trả sách. Tiền phạt: "
                                    + String.format("%.0f", phieuDaTra.getTongTienPhat())
                                    + " VNĐ. Số dư ví: "
                                    + String.format("%.0f", ((ThanhVien) current).getViTien()) + " VNĐ.");
                            capNhatTrangThaiGiaoDien();
                        } else {
                            viewMuonTra.showThongBao("Không tìm thấy phiếu đang thuê của bạn, "
                                    + "hoặc ngày trả không hợp lệ.");
                        }
                    }
                }
                break;

            case "THEM_SACH":
                if (!(current instanceof NhanVien)) {
                    viewSach.showThongBao("Chức năng này chỉ dành cho nhân viên.");
                    break;
                }
                Map<String, Object> bookData = viewSach.inputThongTinSach();
                if (bookData != null) {
                    Sach newSach = new Sach((String) bookData.get("maSach"), (String) bookData.get("tenSach"), 
                            (double) bookData.get("giaThue"), (int) bookData.get("soLuongTon"));
                    Command add = new ThemSachCommand(newSach, viewSach);
                    processor.executeCommand(add);
                    capNhatTrangThaiGiaoDien();
                }
                break;

            case "XOA_SACH":
                if (!(current instanceof NhanVien)) {
                    viewSach.showThongBao("Chức năng này chỉ dành cho nhân viên.");
                    break;
                }
                String maXoa = JOptionPane.showInputDialog(viewMenu, "Nhập mã sách muốn gỡ bỏ:");
                if (maXoa != null) {
                    Command del = new XoaSachCommand(maXoa, viewSach);
                    processor.executeCommand(del);
                    capNhatTrangThaiGiaoDien();
                }
                break;

            case "THONG_KE":
                if (!(current instanceof NhanVien)) {
                    viewSach.showThongBao("Chức năng này chỉ dành cho nhân viên.");
                    break;
                }
                Command stat = new ThongKeCommand();
                processor.executeCommand(stat);
                break;

            case "DANH_SACH_SACH":
                viewSach.hienThiDanhSachSach(QuanLySach.getInstance().getDanhSachSach());
                break;

            case "LICH_SU_THUE":
                if (!(current instanceof ThanhVien)) {
                    viewSach.showThongBao("Chức năng này chỉ dành cho thành viên.");
                    break;
                }
                viewMuonTra.hienThiLichSuThue(QuanLyMuonTra.getInstance().layLichSuTheoThanhVien(current.getUsername()));
                break;

            case "DANG_XUAT":
                QuanLyNguoiDung.getInstance().dangXuat();
                capNhatTrangThaiGiaoDien();
                JOptionPane.showMessageDialog(viewMenu, "Đã đăng xuất tài khoản thành công!");
                break;
        }
    }

    private void capNhatTrangThaiGiaoDien() {
        NguoiDung current = QuanLyNguoiDung.getInstance().getCurrentUser();
        java.util.List<PhieuThue> loans = current instanceof ThanhVien
                ? QuanLyMuonTra.getInstance().layLichSuTheoThanhVien(current.getUsername())
                : java.util.Collections.<PhieuThue>emptyList();
        viewMenu.showDashboard(current, QuanLySach.getInstance().getDanhSachSach(), loans);
    }
    
    // Giữ nguyên tương thích với sơ đồ lớp cũ
    public void handleMenu() { capNhatTrangThaiGiaoDien(); } 
}

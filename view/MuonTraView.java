package view;

import javax.swing.*;
import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import model.entities.PhieuThue;
import model.entities.ChiTietPhieuThue;

public class MuonTraView {
    private Component parent;

    public MuonTraView(Component parent) {
        this.parent = parent;
    }

    public Map<String, Object> inputThongTinThue() {
        Map<String, Object> res = new HashMap<>();
        JTextField txtMaSach = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtSoNgay = new JTextField();

        Object[] message = {
            "Mã Sách Thuê:", txtMaSach,
            "Số Lượng Thuê:", txtSoLuong,
            "Số Ngày Thuê:", txtSoNgay
        };
        int option = JOptionPane.showConfirmDialog(parent, message, "ĐĂNG KÝ THUÊ SÁCH", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
                int soNgayThue = Integer.parseInt(txtSoNgay.getText().trim());
                if (txtMaSach.getText().trim().isEmpty() || soLuong <= 0 || soNgayThue <= 0) {
                    throw new IllegalArgumentException();
                }
                res.put("maSach", txtMaSach.getText().trim());
                res.put("soLuong", soLuong);
                res.put("soNgayThue", soNgayThue);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(parent, "Dữ liệu nhập sai định dạng số!");
                return null;
            }
        } else {
            return null;
        }
        return res;
    }

    public void hienThiPhieuThue(PhieuThue pt) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- PHIẾU THUÊ HỆ THỐNG ---\n");
        sb.append("Mã Phiếu: ").append(pt.getMaPhieu()).append("\n");
        sb.append("Thành viên: ").append(pt.getUsernameThanhVien()).append("\n");
        sb.append("Hạn trả: ").append(pt.getNgayHenTra()).append("\n");
        sb.append("Danh sách chi tiết:\n");
        for (ChiTietPhieuThue ct : pt.getDsChiTiet()) {
            sb.append("- ").append(ct.getSach().getTenSach()).append(" | SL: ").append(ct.getSoLuongThue()).append("\n");
        }
        sb.append("TỔNG PHẢI TRẢ: ").append(pt.getTongTienPhaiTra()).append(" VNĐ");
        
        JOptionPane.showMessageDialog(parent, sb.toString(), "CHI TIẾT PHIẾU THUÊ", JOptionPane.INFORMATION_MESSAGE);
    }

    public void hienThiLichSuThue(java.util.List<PhieuThue> dsPhieu) {
        String[] columnNames = {"Mã phiếu", "Ngày thuê", "Hạn trả", "Trạng thái", "Tổng tiền"};
        Object[][] data;

        if (dsPhieu == null || dsPhieu.isEmpty()) {
            data = new Object[0][5];
        } else {
            data = new Object[dsPhieu.size()][5];
            for (int i = 0; i < dsPhieu.size(); i++) {
                PhieuThue pt = dsPhieu.get(i);
                data[i][0] = pt.getMaPhieu();
                data[i][1] = pt.getNgayThue();
                data[i][2] = pt.getNgayHenTra();
                data[i][3] = pt.getTrangThai();
                data[i][4] = String.format("%.0f", pt.getTongTienPhaiTra()) + " VNĐ";
            }
        }

        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(34);
        table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        table.setForeground(new java.awt.Color(30, 41, 59));
        table.setSelectionBackground(new java.awt.Color(219, 234, 254));
        table.setSelectionForeground(new java.awt.Color(15, 23, 42));
        table.setGridColor(new java.awt.Color(226, 232, 240));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        table.getTableHeader().setForeground(new java.awt.Color(51, 65, 85));
        table.getTableHeader().setBackground(new java.awt.Color(248, 250, 252));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new java.awt.Dimension(700, 260));

        if (dsPhieu == null || dsPhieu.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Bạn chưa có phiếu thuê nào.", "LỊCH SỬ THUÊ SÁCH", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(parent, scrollPane, "LỊCH SỬ THUÊ SÁCH", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void showThongBao(String message) {
        JOptionPane.showMessageDialog(parent, message, "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
    }

    public String inputMaPhieuTra() {
        String maPhieu = JOptionPane.showInputDialog(parent, "Nhập mã phiếu thuê cần trả sách:",
                "TRẢ SÁCH", JOptionPane.QUESTION_MESSAGE);
        return maPhieu == null ? null : maPhieu.trim();
    }

    public LocalDate inputNgayTraThucTe() {
        String ngayTra = JOptionPane.showInputDialog(parent, "Ngày trả thực tế (yyyy-MM-dd):",
                LocalDate.now().toString());
        if (ngayTra == null) {
            return null;
        }
        try {
            LocalDate parsedDate = LocalDate.parse(ngayTra.trim());
            if (parsedDate.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(parent, "Ngày trả không được ở tương lai!");
                return null;
            }
            return parsedDate;
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(parent, "Ngày không hợp lệ. Hãy nhập theo định dạng yyyy-MM-dd.");
            return null;
        }
    }
}

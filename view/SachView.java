package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entities.Sach;

public class SachView {
    private final Component parent;

    public SachView(Component parent) {
        this.parent = parent;
    }

    public void hienThiDanhSachSach(List<Sach> ds) {
        String[] columnNames = {"Mã sách", "Tên sách", "Giá thuê", "Số lượng"};
        Object[][] data;

        if (ds == null || ds.isEmpty()) {
            data = new Object[0][4];
        } else {
            data = new Object[ds.size()][4];
            for (int i = 0; i < ds.size(); i++) {
                Sach s = ds.get(i);
                data[i][0] = s.getMaSach();
                data[i][1] = s.getTenSach();
                data[i][2] = String.format("%.0fđ", s.getGiaThue());
                data[i][3] = s.getSoLuongTon();
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
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
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new java.awt.Dimension(650, 260));

        JOptionPane.showMessageDialog(parent, scrollPane, "DANH SÁCH SÁCH CÓ SỐ LƯỢNG TỒN", JOptionPane.INFORMATION_MESSAGE);
    }

    public Map<String, Object> inputThongTinSach() {
        Map<String, Object> res = new HashMap<>();
        JTextField txtMa = new JTextField();
        JTextField txtTen = new JTextField();
        JTextField txtGia = new JTextField();
        JTextField txtTon = new JTextField();

        Object[] message = {
            "Mã Sách:", txtMa, "Tên Sách:", txtTen,
            "Giá Thuê:", txtGia, "Số Lượng Tồn:", txtTon
        };
        int option = JOptionPane.showConfirmDialog(parent, message, "THÊM SÁCH MỚI", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                double giaThue = Double.parseDouble(txtGia.getText().trim());
                int soLuongTon = Integer.parseInt(txtTon.getText().trim());
                if (txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty()
                        || !Double.isFinite(giaThue) || giaThue < 0 || soLuongTon < 0) {
                    throw new IllegalArgumentException();
                }
                res.put("maSach", txtMa.getText().trim());
                res.put("tenSach", txtTen.getText().trim());
                res.put("giaThue", giaThue);
                res.put("soLuongTon", soLuongTon);
            } catch (IllegalArgumentException e) {
                showThongBao("Dữ liệu số nhập vào không hợp lệ!");
                return null;
            }
        } else {
            return null;
        }
        return res;
    }

    public void showThongBao(String msg) {
        JOptionPane.showMessageDialog(parent, msg, "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
    }
}

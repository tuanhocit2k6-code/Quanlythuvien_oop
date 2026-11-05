package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entities.NguoiDung;
import model.entities.PhieuThue;
import model.entities.Sach;

public final class MainMenuView extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Color BG_START = new Color(239, 246, 255);
    private static final Color BG_END = new Color(248, 250, 252);
    private static final Color PANEL_BG = Color.WHITE;
    private static final Color ACCENT = new Color(37, 99, 235);
    private static final Color ACCENT_HOVER = new Color(239, 246, 255);
    private static final Color TEXT_PRIMARY = new Color(15, 23, 42);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);

    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private final JPanel actionsPanel = new JPanel(new GridLayout(0, 1, 0, 10));
    private final DefaultTableModel catalogModel = new DefaultTableModel(
            new Object[]{"Mã sách", "Tên sách", "Còn lại"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JLabel welcomeLabel;
    private JLabel roleLabel;
    private JLabel titlesValue;
    private JLabel copiesValue;
    private JLabel activityTitle;
    private JLabel activityValue;

    public MainMenuView() {
        super("HỆ THỐNG QUẢN LÝ THƯ VIỆN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 650));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);
        mainPanel.add(createDashboardPanel(), "DASHBOARD");

        JPanel root = createRootPanel();
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(mainPanel, BorderLayout.CENTER);
        root.add(createFooter(), BorderLayout.SOUTH);
        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    private JPanel createRootPanel() {
        JPanel root = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, BG_START, 0, getHeight(), BG_END));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        root.setPreferredSize(new Dimension(1080, 760));
        root.setBorder(new EmptyBorder(20, 32, 16, 32));
        return root;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 4, 12, 4));

        JLabel brand = new JLabel("LIBRARY  /  QUẢN LÝ THƯ VIỆN");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 14));
        brand.setForeground(new Color(30, 64, 175));

        JLabel status = new JLabel("HỆ THỐNG QUẢN LÝ MƯỢN TRẢ");
        status.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        status.setForeground(TEXT_MUTED);

        header.add(brand, BorderLayout.WEST);
        header.add(status, BorderLayout.EAST);
        return header;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(12, 4, 0, 4));

        JLabel note = new JLabel("Không gian quản lý thư viện");
        note.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        note.setForeground(TEXT_MUTED);
        footer.add(note, BorderLayout.WEST);
        return footer;
    }

    public void showMenuKhach() {
        controller.ControllerSingleton.getInstance().handleMenu();
    }

    public void showMenuThanhVien() { controller.ControllerSingleton.getInstance().handleMenu(); }
    public void showMenuNhanVien() { controller.ControllerSingleton.getInstance().handleMenu(); }

    public void showDashboard(NguoiDung user, List<Sach> books, List<PhieuThue> loans) {
        List<Sach> safeBooks = books == null ? Collections.<Sach>emptyList() : books;
        List<PhieuThue> safeLoans = loans == null ? Collections.<PhieuThue>emptyList() : loans;
        String role;
        String[] labels;
        String[] actions;

        if (user instanceof model.entities.NhanVien) {
            role = "Nhân viên";
            activityTitle.setText("VAI TRÒ");
            activityValue.setText("Quản lý");
            labels = new String[]{"＋  Thêm sách mới", "−  Xóa sách", "▤  Xem thống kê",
                    "▦  Xem kho sách", "↪  Đăng xuất"};
            actions = new String[]{"THEM_SACH", "XOA_SACH", "THONG_KE",
                    "DANH_SACH_SACH", "DANG_XUAT"};
        } else if (user instanceof model.entities.ThanhVien) {
            role = "Thành viên";
            activityTitle.setText("LƯỢT THUÊ");
            activityValue.setText(String.valueOf(safeLoans.size()));
            labels = new String[]{"⌕  Tìm kiếm sách", "＋  Thuê sách", "↩  Trả sách",
                    "▦  Xem kho sách", "▤  Lịch sử thuê", "↪  Đăng xuất"};
            actions = new String[]{"TIM_SACH", "THUE_SACH", "TRA_SACH",
                    "DANH_SACH_SACH", "LICH_SU_THUE", "DANG_XUAT"};
        } else {
            role = "Khách";
            activityTitle.setText("TRẠNG THÁI");
            activityValue.setText("Chào mừng");
            labels = new String[]{"→  Đăng nhập", "＋  Tạo tài khoản"};
            actions = new String[]{"LOGIN_FORM", "REGISTER_FORM"};
        }

        welcomeLabel.setText(user == null || user.getHoTen() == null || user.getHoTen().trim().isEmpty()
                ? "Chào mừng đến với thư viện" : "Xin chào, " + user.getHoTen());
        roleLabel.setText("BẢNG ĐIỀU KHIỂN  /  " + role.toUpperCase());
        titlesValue.setText(String.valueOf(safeBooks.size()));
        int availableCopies = 0;
        for (Sach book : safeBooks) {
            availableCopies += book.getSoLuongTon();
        }
        copiesValue.setText(String.valueOf(availableCopies));

        actionsPanel.removeAll();
        for (int i = 0; i < labels.length; i++) {
            JButton button = createActionButton(labels[i]);
            button.setPreferredSize(new Dimension(310, 54));
            final String action = actions[i];
            button.addActionListener(e -> controller.ControllerSingleton.getInstance().handleGiaoDien(action));
            actionsPanel.add(button);
        }

        catalogModel.setRowCount(0);
        for (Sach book : safeBooks) {
            catalogModel.addRow(new Object[]{book.getMaSach(), book.getTenSach(), book.getSoLuongTon()});
        }
        actionsPanel.revalidate();
        actionsPanel.repaint();
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    private JPanel createDashboardPanel() {
        JPanel dashboard = new JPanel(new BorderLayout(0, 16));
        dashboard.setOpaque(false);
        dashboard.setBorder(new EmptyBorder(8, 0, 8, 0));

        JPanel overview = new JPanel(new BorderLayout(0, 14));
        overview.setOpaque(false);
        overview.add(createWelcomeCard(), BorderLayout.NORTH);

        JPanel stats = new JPanel(new GridLayout(1, 3, 12, 0));
        stats.setOpaque(false);
        JPanel titlesCard = createStatCard("ĐẦU SÁCH", "0", "Tựa sách trong danh mục");
        titlesValue = (JLabel) titlesCard.getClientProperty("value");
        stats.add(titlesCard);
        JPanel copiesCard = createStatCard("SÁCH SẴN CÓ", "0", "Tổng số bản có thể mượn");
        copiesValue = (JLabel) copiesCard.getClientProperty("value");
        stats.add(copiesCard);
        JPanel activityCard = createStatCard("TRẠNG THÁI", "Chào mừng", "Tài khoản hiện tại");
        activityTitle = (JLabel) activityCard.getClientProperty("title");
        activityValue = (JLabel) activityCard.getClientProperty("value");
        stats.add(activityCard);
        overview.add(stats, BorderLayout.CENTER);
        dashboard.add(overview, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1, 2, 14, 0));
        content.setOpaque(false);
        content.add(createSectionCard("TRUY CẬP NHANH", createActionList()));
        content.add(createSectionCard("DANH MỤC SÁCH", createCatalogTable()));
        dashboard.add(content, BorderLayout.CENTER);
        return dashboard;
    }

    private JPanel createWelcomeCard() {
        JPanel banner = new JPanel(new BorderLayout(10, 0));
        banner.setBackground(new Color(30, 64, 175));
        banner.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel copy = new JPanel();
        copy.setOpaque(false);
        copy.setLayout(new BoxLayout(copy, BoxLayout.Y_AXIS));
        roleLabel = new JLabel("BẢNG ĐIỀU KHIỂN");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        roleLabel.setForeground(new Color(191, 219, 254));
        welcomeLabel = new JLabel("Chào mừng đến với thư viện");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(new EmptyBorder(5, 0, 4, 0));
        JLabel description = new JLabel("Quản lý sách và hoạt động mượn trả tại một nơi.");
        description.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        description.setForeground(new Color(219, 234, 254));
        copy.add(roleLabel);
        copy.add(welcomeLabel);
        copy.add(description);
        banner.add(copy, BorderLayout.CENTER);
        return banner;
    }

    private JPanel createStatCard(String title, String value, String detail) {
        JPanel card = new JPanel();
        card.setBackground(PANEL_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(14, 16, 14, 16)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        titleLabel.setForeground(TEXT_MUTED);
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(TEXT_PRIMARY);
        JLabel detailLabel = new JLabel(detail);
        detailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        detailLabel.setForeground(TEXT_MUTED);
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(2));
        card.add(detailLabel);
        card.putClientProperty("title", titleLabel);
        card.putClientProperty("value", valueLabel);
        return card;
    }

    private JComponent createActionList() {
        actionsPanel.setOpaque(false);
        actionsPanel.setBorder(new EmptyBorder(14, 14, 14, 14));
        JScrollPane scrollPane = new JScrollPane(actionsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        return scrollPane;
    }

    private JComponent createCatalogTable() {
        JTable table = new JTable(catalogModel);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(new Color(30, 41, 59));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setGridColor(new Color(226, 232, 240));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setForeground(new Color(51, 65, 85));
        table.getTableHeader().setBackground(new Color(248, 250, 252));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }

    private JPanel createSectionCard(String title, JComponent content) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(PANEL_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(16, 16, 16, 16)
        ));
        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 12));
        heading.setForeground(TEXT_PRIMARY);
        card.add(heading, BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? ACCENT_HOVER : PANEL_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(getModel().isRollover() ? new Color(147, 197, 253) : new Color(226, 232, 240));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(TEXT_PRIMARY);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(12, 18, 12, 18));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setRolloverEnabled(true);
        return button;
    }

    public Map<String, String> inputDangNhap() {
        Map<String, String> res = new HashMap<>();
        JTextField txtUser = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        Object[] message = {"Username:", txtUser, "Password:", txtPass};
        int option = JOptionPane.showConfirmDialog(this, message, "ĐĂNG NHẬP", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            res.put("username", txtUser.getText());
            res.put("password", new String(txtPass.getPassword()));
        }
        return res;
    }

    public Map<String, String> inputDangKy() {
        Map<String, String> res = new HashMap<>();
        JTextField txtUser = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JTextField txtName = new JTextField();
        JTextField txtEmail = new JTextField();
        Object[] message = {
            "Username:", txtUser, "Password:", txtPass,
            "Họ Tên:", txtName, "Email:", txtEmail
        };
        int option = JOptionPane.showConfirmDialog(this, message, "ĐĂNG KÝ TÀI KHOẢN", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (txtUser.getText().trim().isEmpty() || txtPass.getPassword().length == 0
                    || txtName.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin đăng ký.");
                return null;
            }
            res.put("username", txtUser.getText().trim());
            res.put("password", new String(txtPass.getPassword()));
            res.put("hoTen", txtName.getText().trim());
            res.put("email", txtEmail.getText().trim());
        } else {
            return null;
        }
        return res;
    }
}

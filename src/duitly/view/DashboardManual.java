package duitly.View;

import java.awt.*;
import javax.swing.*;

public class DashboardManual extends JFrame {

    public DashboardManual() {
        setTitle("Money Manager Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        Color bgColor = new Color(233, 238, 238);
        Color menuColor = new Color(244, 246, 248);
        JPanel background = new JPanel();
        background.setBackground(bgColor);
        background.setBounds(0, 0, getWidth(), getHeight());
        background.setLayout(null);
        add(background);

        // Floating Sidebar
        RoundedPanel sidebar = new RoundedPanel(30);
        sidebar.setBackground(menuColor);
        sidebar.setBounds(40, 40, 250, 500);
        sidebar.setLayout(null);

        JPanel profilePanel = new JPanel(null);
        profilePanel.setOpaque(false);
        profilePanel.setBounds(20, 20, 210, 60);

        CirclePanel avatar = new CirclePanel();
        avatar.setBackground(new Color(100, 150, 200));
        avatar.setBounds(0, 0, 50, 50);

        JLabel username = new JLabel("Hello, User");
        username.setFont(new Font("SansSerif", Font.BOLD, 16));
        username.setForeground(Color.DARK_GRAY);
        username.setBounds(60, 15, 150, 20);

        profilePanel.add(avatar);
        profilePanel.add(username);

        // Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(menuColor);
        menuPanel.setLayout(new GridLayout(5, 1, 0, 10));
        menuPanel.setBounds(20, 100, 210, 200);

        String[] menuItems = {"Dashboard", "Transaksi", "Tambah", "Edit", "Pengaturan"};

        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(220, 225, 225));
            btn.setBorderPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            menuPanel.add(btn);
        }

        // Add to sidebar
        sidebar.add(profilePanel);
        sidebar.add(menuPanel);

        // Add sidebar last so it floats over background
        background.add(sidebar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardManual().setVisible(true));
    }
}

// RoundedPanel Class
class RoundedPanel extends JPanel {
    private int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
    }
}

// CirclePanel Class (avatar)
class CirclePanel extends JPanel {
    public CirclePanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int diameter = Math.min(getWidth(), getHeight());
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillOval(0, 0, diameter, diameter);
        super.paintComponent(g);
    }
}
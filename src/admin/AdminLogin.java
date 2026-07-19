package admin;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DatabaseConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.MainLoginChoice;

public class AdminLogin extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private AdminDashboard AdminDashboard;

    public AdminLogin() {
        setTitle("Admin Login");
        setSize(350, 300);
        setLayout(null);
        getContentPane().setBackground(new Color(173, 216, 230));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Admin Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBounds(50, 20, 250, 30);
        add(title);

        addLabel("Username:", 50, 80);
        usernameField = addText(140, 80);
        addLabel("Password:", 50, 120);
        passwordField = new JPasswordField();
        passwordField.setBounds(140, 120, 150, 25);
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(70, 180, 90, 30);
        add(loginBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(180, 180, 90, 30);
        add(backBtn);

        loginBtn.addActionListener(e -> loginAdmin());
        backBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainLoginChoice mainLoginChoice = new MainLoginChoice();
            }
        });

        setVisible(true);
    }

    private void addLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, 80, 25);
        add(lbl);
    }

    private JTextField addText(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 150, 25);
        add(txt);
        return txt;
    }

    private void loginAdmin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=? AND role='admin'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Admin Login Successful!");
                dispose();
                AdminDashboard /*adminDashboard*/ = new AdminDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }
}

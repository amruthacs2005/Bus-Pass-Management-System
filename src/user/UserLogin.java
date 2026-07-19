package user;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DatabaseConnection;
import main.MainLoginChoice;

public class UserLogin extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private MainLoginChoice MainLoginChoice;

    public UserLogin() {
        setTitle("User Login");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(173, 216, 230));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("User Login");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setBounds(150, 30, 200, 30);
        add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(70, 100, 100, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 100, 150, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(70, 140, 100, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 140, 150, 25);
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(90, 190, 100, 30);
        add(loginBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(210, 190, 100, 30);
        add(backBtn);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(150, 230, 100, 25);
        add(registerBtn);

        loginBtn.addActionListener(e -> userLogin());
        backBtn.addActionListener(e -> {
            dispose();
            MainLoginChoice /*mainLoginChoice*/ = new MainLoginChoice();
        });

        registerBtn.addActionListener(e -> {
            dispose();
            new UserRegister();
        });

        setVisible(true);
    }

    private void userLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=? AND role='user'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();
                new UserDashboard(userId);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
}

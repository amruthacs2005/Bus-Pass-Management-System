package user;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DatabaseConnection;
import java.awt.event.ActionEvent;

public class UserRegister extends JFrame {
    private final JTextField firstName, lastName, username, mobile;
    private final JPasswordField password;

    public UserRegister() {
        setTitle("User Registration");
        setSize(450, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(173, 216, 230));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("User Registration");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(140, 30, 250, 30);
        add(title);

        JLabel fnameLabel = new JLabel("First Name:");
        fnameLabel.setBounds(80, 90, 100, 25);
        add(fnameLabel);
        firstName = new JTextField();
        firstName.setBounds(200, 90, 150, 25);
        add(firstName);

        JLabel lnameLabel = new JLabel("Last Name:");
        lnameLabel.setBounds(80, 130, 100, 25);
        add(lnameLabel);
        lastName = new JTextField();
        lastName.setBounds(200, 130, 150, 25);
        add(lastName);

        JLabel unameLabel = new JLabel("Username:");
        unameLabel.setBounds(80, 170, 100, 25);
        add(unameLabel);
        username = new JTextField();
        username.setBounds(200, 170, 150, 25);
        add(username);

        JLabel mobileLabel = new JLabel("Mobile:");
        mobileLabel.setBounds(80, 210, 100, 25);
        add(mobileLabel);
        mobile = new JTextField();
        mobile.setBounds(200, 210, 150, 25);
        add(mobile);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(80, 250, 100, 25);
        add(passLabel);
        password = new JPasswordField();
        password.setBounds(200, 250, 150, 25);
        add(password);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(130, 300, 100, 30);
        add(registerBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(250, 300, 100, 30);
        add(backBtn);

        registerBtn.addActionListener(e -> registerUser());
        backBtn.addActionListener((ActionEvent e) -> {
            dispose();
            new UserLogin();
        });

        setVisible(true);
    }

    private void registerUser() {
        String fname = firstName.getText();
        String lname = lastName.getText();
        String uname = username.getText();
        String mob = mobile.getText();
        String pass = new String(password.getPassword());

        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (firstname, lastname, username, mobile, password, role) VALUES (?, ?, ?, ?, ?, 'user')";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, fname);
            pst.setString(2, lname);
            pst.setString(3, uname);
            pst.setString(4, mob);
            pst.setString(5, pass);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            dispose();
            new UserLogin();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}

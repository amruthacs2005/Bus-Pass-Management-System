package main;

import javax.swing.*;
import java.awt.*;
import admin.AdminLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import user.UserLogin;

public class MainLoginChoice extends JFrame {
    private static MainLoginChoice MainLoginChoice;
    public MainLoginChoice() {
        setTitle("Bus Pass Management System");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(173, 216, 230));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Welcome to Bus Pass System");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setBounds(70, 30, 300, 30);
        add(title);

        JButton userBtn = new JButton("User Login");
        userBtn.setBounds(130, 100, 120, 30);
        add(userBtn);

        JButton adminBtn = new JButton("Admin Login");
        adminBtn.setBounds(130, 150, 120, 30);
        add(adminBtn);

        userBtn.addActionListener(new ActionListener() {
            private UserLogin UserLogin;

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserLogin /*userLogin*/ = new UserLogin();
            }
        });

        adminBtn.addActionListener(new ActionListener() {
            private AdminLogin AdminLogin;

            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminLogin /*adminLogin*/ = new AdminLogin();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        MainLoginChoice /*mainLoginChoice*/ = new MainLoginChoice();
    }
}

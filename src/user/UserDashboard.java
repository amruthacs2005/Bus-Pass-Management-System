package user;

import javax.swing.*;
import java.awt.*;
import main.MainLoginChoice;

public class UserDashboard extends JFrame {

    public UserDashboard(int userId) {
        setTitle("User Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(173, 216, 230));

        JLabel title = new JLabel("Welcome User", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(100, 30, 200, 30);
        add(title);

        JButton applyBtn = new JButton("Apply New Pass");
        applyBtn.setBounds(120, 80, 150, 30);
        add(applyBtn);

        JButton viewBtn = new JButton("View My Passes");
        viewBtn.setBounds(120, 130, 150, 30);
        add(viewBtn);

        JButton backBtn = new JButton("Logout");
        backBtn.setBounds(120, 180, 150, 30);
        add(backBtn);

        applyBtn.addActionListener(e -> {
            dispose();
            new ApplyNewPass(userId);
        });

        viewBtn.addActionListener(e -> {
            dispose();
            new ViewPasses(userId);
        });

        backBtn.addActionListener(e -> {
            dispose();
            new MainLoginChoice();
        });

        setVisible(true);
    }
}

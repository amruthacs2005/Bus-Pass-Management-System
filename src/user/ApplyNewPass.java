package user;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import db.DatabaseConnection;

public class ApplyNewPass extends JFrame {
    private final JTextField nameField, fromField, toField;
    private final JComboBox<String> passTypeBox;
    private final int userId;

    public ApplyNewPass(int userId) {
        this.userId = userId;

        setTitle("Apply New Pass");
        setSize(400, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(173, 216, 230));

        JLabel title = new JLabel("Apply Bus Pass", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(100, 20, 200, 30);
        add(title);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 80, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(160, 80, 150, 25);
        add(nameField);

        JLabel fromLabel = new JLabel("From:");
        fromLabel.setBounds(50, 120, 100, 25);
        add(fromLabel);

        fromField = new JTextField();
        fromField.setBounds(160, 120, 150, 25);
        add(fromField);

        JLabel toLabel = new JLabel("To:");
        toLabel.setBounds(50, 160, 100, 25);
        add(toLabel);

        toField = new JTextField();
        toField.setBounds(160, 160, 150, 25);
        add(toField);

        JLabel typeLabel = new JLabel("Pass Type:");
        typeLabel.setBounds(50, 200, 100, 25);
        add(typeLabel);

        passTypeBox = new JComboBox<>(new String[]{"Monthly", "Quarterly", "Yearly"});
        passTypeBox.setBounds(160, 200, 150, 25);
        add(passTypeBox);

        JButton applyBtn = new JButton("Apply");
        applyBtn.setBounds(80, 260, 100, 30);
        add(applyBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(200, 260, 100, 30);
        add(backBtn);

        applyBtn.addActionListener(e -> applyPass());
        backBtn.addActionListener(e -> {
            dispose();
            new UserDashboard(userId);
        });

        setVisible(true);
    }

    private void applyPass() {
        String name = nameField.getText();
        String from = fromField.getText();
        String to = toField.getText();
        String type = (String) passTypeBox.getSelectedItem();

        if (name.isEmpty() || from.isEmpty() || to.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        int duration = type.equals("Quarterly") ? 3 : type.equals("Yearly") ? 12 : 1;
        double amount = type.equals("Quarterly") ? 1200 : type.equals("Yearly") ? 4000 : 500;
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(duration);

        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO passes (user_id, name, from_location, to_location, pass_type, start_date, end_date, duration, amount, status) VALUES (?,?,?,?,?,?,?,?,?,'Pending')";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setString(2, name);
            pst.setString(3, from);
            pst.setString(4, to);
            pst.setString(5, type);
            pst.setDate(6, Date.valueOf(start));
            pst.setDate(7, Date.valueOf(end));
            pst.setInt(8, duration);
            pst.setDouble(9, amount);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Pass Applied Successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }
}

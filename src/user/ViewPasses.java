package user;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DatabaseConnection;
import javax.swing.table.DefaultTableModel;

public class ViewPasses extends JFrame {
    private final int userId;

    public ViewPasses(int userId) {
        this.userId = userId;
        setTitle("View Passes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(173, 216, 230));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {"ID", "Name", "From", "To", "Type", "Start", "End", "Amount", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            dispose();
            new UserDashboard(userId);
        });
        add(backBtn, BorderLayout.SOUTH);

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM passes WHERE user_id=?");
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("from_location"), rs.getString("to_location"),
                        rs.getString("pass_type"), rs.getDate("start_date"),
                        rs.getDate("end_date"), rs.getDouble("amount"), rs.getString("status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }

        setVisible(true);
    }
}

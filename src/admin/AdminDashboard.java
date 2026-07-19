package admin;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DatabaseConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import main.MainLoginChoice;

public class AdminDashboard extends JFrame {
    private final JTable table;
    private final DefaultTableModel model;
    private MainLoginChoice MainLoginChoice;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(173, 216, 230));

        model = new DefaultTableModel(new String[]{"ID", "Name", "From", "To", "Type", "Amount", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");
        JButton logoutBtn = new JButton("Logout");
        buttonPanel.add(approveBtn);
        buttonPanel.add(rejectBtn);
        buttonPanel.add(logoutBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        approveBtn.addActionListener(e -> updateStatus("Approved"));
        rejectBtn.addActionListener(e -> updateStatus("Rejected"));
        logoutBtn.addActionListener((ActionEvent e) -> {
            dispose();
            MainLoginChoice /*mainLoginChoice*/ = new MainLoginChoice();
        });

        loadPasses();
        setVisible(true);
    }

    private void loadPasses() {
        try (Connection con = DatabaseConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM passes");
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("from_location"),
                        rs.getString("to_location"),
                        rs.getString("pass_type"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }

    private void updateStatus(String status) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record!");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("UPDATE passes SET status=? WHERE id=?");
            pst.setString(1, status);
            pst.setInt(2, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Pass " + status + "!");
            loadPasses();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }
}

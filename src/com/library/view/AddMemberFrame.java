package com.library.view;

import com.library.database.DatabaseHandler;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddMemberFrame extends JFrame {
    private JTextField nameField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JButton saveButton = new JButton("Register Member");

    public AddMemberFrame() {
        setTitle("Register New Member");
        setLayout(new GridLayout(3, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("  Name:"));
        add(nameField);
        add(new JLabel("  Email:"));
        add(emailField);
        add(new JLabel(""));
        add(saveButton);

        saveButton.addActionListener(e -> saveMemberToDB());

        pack();
        setLocationRelativeTo(null);
    }

    private void saveMemberToDB() {
        String query = "INSERT INTO members(name, email) VALUES(?,?)";
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nameField.getText());
            pstmt.setString(2, emailField.getText());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Member registered successfully!");
            nameField.setText("");
            emailField.setText("");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }
}
package com.library.view;

import com.library.database.DatabaseHandler;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBookFrame extends JFrame {
    // UI Components
    private JTextField titleField = new JTextField(20);
    private JTextField authorField = new JTextField(20);
    private JTextField isbnField = new JTextField(20);
    private JButton saveButton = new JButton("Save Book");

    public AddBookFrame() {
        setTitle("Add New Book");
        setLayout(new GridLayout(4, 2, 10, 10)); // Grid layout for clean alignment
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Adding components to the window
        add(new JLabel(" Title:"));
        add(titleField);
        add(new JLabel(" Author:"));
        add(authorField);
        add(new JLabel(" ISBN:"));
        add(isbnField);
        add(new JLabel(""));
        add(saveButton);

        // Action when button is clicked
        saveButton.addActionListener(e -> saveBookToDB());

        pack(); // Auto-size window
        setLocationRelativeTo(null); // Center on screen
    }

    private void saveBookToDB() {
        String query = "INSERT INTO books(title, author, isbn) VALUES(?,?,?)";

        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, titleField.getText());
            pstmt.setString(2, authorField.getText());
            pstmt.setString(3, isbnField.getText());

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Book saved successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving book: " + ex.getMessage());
        }
    }
}
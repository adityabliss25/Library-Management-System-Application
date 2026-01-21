package com.library.view;

import com.library.database.DatabaseHandler;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReturnBookFrame extends JFrame {
    private JTextField bookIdField = new JTextField(15);
    private JButton returnButton = new JButton("Confirm Return");

    public ReturnBookFrame() {
        setTitle("Return Book");
        setLayout(new GridLayout(2, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("  Enter Book ID:"));
        add(bookIdField);
        add(new JLabel(""));
        add(returnButton);

        returnButton.addActionListener(e -> processReturn());

        pack();
        setLocationRelativeTo(null);
    }

    private void processReturn() {
        String bookId = bookIdField.getText();

        // SQL 1: Mark book as available again
        String updateBookSql = "UPDATE books SET isAvailable = 1 WHERE id = ?";
        // SQL 2: Remove the issue record (or mark as returned)
        String deleteIssueSql = "DELETE FROM issue_records WHERE book_id = ?";

        try (Connection conn = DatabaseHandler.getConnection()) {
            conn.setAutoCommit(false); // Enable transaction for data integrity

            try (PreparedStatement pstmt1 = conn.prepareStatement(updateBookSql);
                 PreparedStatement pstmt2 = conn.prepareStatement(deleteIssueSql)) {

                pstmt1.setInt(1, Integer.parseInt(bookId));
                pstmt1.executeUpdate();

                pstmt2.setInt(1, Integer.parseInt(bookId));
                pstmt2.executeUpdate();

                conn.commit(); // Save both changes
                JOptionPane.showMessageDialog(this, "✅ Book ID " + bookId + " returned successfully!");
                bookIdField.setText("");

            } catch (SQLException ex) {
                conn.rollback(); // Undo changes if error occurs
                throw ex;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error returning book: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Please enter a valid numeric ID.");
        }
    }
}
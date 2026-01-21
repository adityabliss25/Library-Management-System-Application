package com.library.view;

import com.library.database.DatabaseHandler;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

/**
 * Handles the business logic for borrowing books.
 * It performs a database transaction to update both the
 * 'issue_records' and 'books' tables simultaneously.
 */
public class IssueBookFrame extends JFrame {
    private JTextField bookIdField = new JTextField(10);
    private JTextField memberIdField = new JTextField(10);
    private JButton issueButton = new JButton("Issue Book");

    public IssueBookFrame() {
        setTitle("Issue Book (Loan)");
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("  Book ID:"));
        add(bookIdField);
        add(new JLabel("  Member ID:"));
        add(memberIdField);
        add(new JLabel(""));
        add(issueButton);

        issueButton.addActionListener(e -> processIssue());
        pack();
        setLocationRelativeTo(null);
    }

    private void processIssue() {
        int bId = Integer.parseInt(bookIdField.getText());
        int mId = Integer.parseInt(memberIdField.getText());
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(14); // 2-week loan period

        String issueSql = "INSERT INTO issue_records(book_id, member_id, issue_date, due_date) VALUES(?,?,?,?)";
        String updateBookSql = "UPDATE books SET isAvailable = 0 WHERE id = ?";

        try (Connection conn = DatabaseHandler.getConnection()) {
            conn.setAutoCommit(false); // Requirement: Data validation & Error handling

            try (PreparedStatement pstmtIssue = conn.prepareStatement(issueSql);
                 PreparedStatement pstmtUpdate = conn.prepareStatement(updateBookSql)) {

                // 1. Create issue record
                pstmtIssue.setInt(1, bId);
                pstmtIssue.setInt(2, mId);
                pstmtIssue.setString(3, today.toString());
                pstmtIssue.setString(4, dueDate.toString());
                pstmtIssue.executeUpdate();

                // 2. Mark book as unavailable
                pstmtUpdate.setInt(1, bId);
                pstmtUpdate.executeUpdate();

                conn.commit(); // Save both changes at once
                JOptionPane.showMessageDialog(this, "✅ Book Issued! Due date: " + dueDate);
            } catch (SQLException ex) {
                conn.rollback(); // Undo if something fails
                throw ex;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }
}
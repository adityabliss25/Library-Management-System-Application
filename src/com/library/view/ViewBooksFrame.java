package com.library.view;

import com.library.database.DatabaseHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewBooksFrame extends JFrame {
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public ViewBooksFrame() {
        setTitle("All Books in Library");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Define table columns
        String[] columnNames = {"ID", "Title", "Author", "ISBN", "Available"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);

        loadDataFromDB();

        add(new JScrollPane(bookTable), BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private void loadDataFromDB() {
        String query = "SELECT * FROM books";
        try (Connection conn = DatabaseHandler.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getBoolean("isAvailable") ? "Yes" : "No"
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
}
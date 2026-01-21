package com.library.database;

import java.sql.*;

/**
 * Utility class for managing the SQLite database connection.
 * It handles the initialization of tables and provides the
 * Connection object for all JDBC operations.
 */
public class DatabaseHandler {
    // This creates the database file in your project folder
    private static final String DB_URL = "jdbc:sqlite:library.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Books Table (Already exists)
            String booksTable = "CREATE TABLE IF NOT EXISTS books ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "title TEXT NOT NULL, author TEXT NOT NULL, isbn TEXT UNIQUE, isAvailable BOOLEAN DEFAULT 1);";
            stmt.execute(booksTable);

            // Members Table
            String membersTable = "CREATE TABLE IF NOT EXISTS members ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "email TEXT UNIQUE);";
            stmt.execute(membersTable);

            // Transactions Table (To track borrowing/returning with due dates)
            String issueTable = "CREATE TABLE IF NOT EXISTS issue_records ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "book_id INTEGER, member_id INTEGER,"
                    + "issue_date DATE, due_date DATE,"
                    + "FOREIGN KEY(book_id) REFERENCES books(id),"
                    + "FOREIGN KEY(member_id) REFERENCES members(id));";
            stmt.execute(issueTable);

            System.out.println("✅ Database tables (Books, Members, Issues) ready.");
        } catch (SQLException e) {
            System.err.println("❌ Database Error: " + e.getMessage());
        }
    }
}
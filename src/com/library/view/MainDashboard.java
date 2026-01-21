package com.library.view;

import javax.swing.*;
import java.awt.*;
/**
 * The main entry point for the user interface.
 * This Dashboard coordinates the navigation between adding books,
 * registering members, and processing loans/returns.
 */
public class MainDashboard extends JFrame {
    public MainDashboard() {
        setTitle("Library Management System - Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton btnAdd = new JButton("Add New Book");
        JButton btnView = new JButton("View Book Collection");
        JButton btnAddMember = new JButton("Add New Member");
        JButton btnIssue = new JButton("Issue Book (Loan)");
        JButton btnReturn = new JButton("Return Book");

        btnAdd.addActionListener(e -> new AddBookFrame().setVisible(true));
        btnView.addActionListener(e -> new ViewBooksFrame().setVisible(true));
        btnAddMember.addActionListener(e -> new AddMemberFrame().setVisible(true));
        btnIssue.addActionListener(e -> new IssueBookFrame().setVisible(true));
        btnReturn.addActionListener(e -> new ReturnBookFrame().setVisible(true));

        add(btnAdd);
        add(btnView);
        add(btnAddMember);
        add(btnIssue);
        add(btnReturn);
        setLocationRelativeTo(null);
    }
}
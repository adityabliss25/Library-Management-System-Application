package com.library.model;

/**
 * Model class representing a Book in the Library System.
 * Following the MVC pattern to separate data from logic and UI.
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable; // Flag to track availability status

    // Constructor for creating a new Book object
    public Book(String title, String author, String isbn, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = isAvailable; // Default value is usually true (1) for new books
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // Logic to check if a book can be borrowed
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
package model;

import java.util.Date;

public class BookLost {
    public int id;
    public String isbn;
    public Date created_at;

    // relation with table Book
    private Book book;

    public BookLost() {}

    public BookLost(int id, String isbn, Date created_at) {
        this.id = id;
        this.isbn = isbn;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

package model;

import java.util.Date;

public class BookLost {
    public int id;
    private Book book;
    public Date created_at;

    public BookLost() {}

    public BookLost(int id, Book book, Date created_at) {
        this.id = id;
        this.book = book;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

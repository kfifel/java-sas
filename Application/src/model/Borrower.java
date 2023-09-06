package model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Borrower {
    private int id;
    private String full_name;
    private Date created_at;

    private List<BookBorrow> bookBorrowList;

    public Borrower(int id, String full_name, Date created_at) {
        this.id = id;
        this.full_name = full_name;
        this.created_at = created_at;
        bookBorrowList = new ArrayList<>();
    }

    public Borrower() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String toString() {
        return "Borrower{id=" + this.id + ", full_name='" + this.full_name + '\'' + ", created_at=" + this.created_at + '}';
    }

    public List<BookBorrow> getBookBorrowList() {
        return bookBorrowList;
    }

    public void addBookBorrow(BookBorrow bookBorrowList) {
        this.bookBorrowList.add(bookBorrowList);
    }
}

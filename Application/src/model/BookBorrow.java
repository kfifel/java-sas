
package model;

import java.util.Date;

public class BookBorrow
{
    private int borrow_id;
    private String isbn;
    private int borrower_id;
    private String status;
    private Date borrow_date;
    private Date return_date;
    private Date created_at;

    // relation with tables book and borrower
    private Book book = null;
    private Borrower borrower = null;

    public BookBorrow() {}
    public BookBorrow(int borrow_id, String isbn, int borrower_id, String status, Date borrow_date, Date return_date, Date created_at) {
        this.borrow_id = borrow_id;
        this.isbn = isbn;
        this.borrower_id = borrower_id;
        this.status = status;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
        this.created_at = created_at;
    }

    public int getBorrow_id() {
        return borrow_id;
    }

    public void setBorrow_id(int borrow_id) {
        this.borrow_id = borrow_id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getBorrower_id() {
        return borrower_id;
    }

    public void setBorrower_id(int borrower_id) {
        this.borrower_id = borrower_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(Date borrow_date) {
        this.borrow_date = borrow_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
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

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }
}
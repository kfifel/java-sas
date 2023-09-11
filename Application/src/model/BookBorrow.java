
package model;

import ennumiration.BookBorrowStatus;

import java.util.Date;

public class BookBorrow
{
    private int id;
    private Book book;
    private Borrower borrower;
    private BookBorrowStatus status;
    private Date borrow_date;
    private Date return_date;
    private Date created_at;


    public BookBorrow() {}
    public BookBorrow(int id, Book isbn, Borrower borrower_id, BookBorrowStatus status, Date borrow_date, Date return_date, Date created_at) {
        this.id = id;
        this.book = isbn;
        this.borrower = borrower_id;
        this.status = status;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public BookBorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BookBorrowStatus status) {
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
}